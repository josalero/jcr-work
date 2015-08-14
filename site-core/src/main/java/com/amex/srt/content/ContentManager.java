package com.amex.srt.content;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

import javax.jcr.RepositoryException;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.XmlMappingException;

import com.amex.srt.content.repo.ContentRepoAccessDao;
import com.amex.srt.content.repo.NodeRef;
import com.amex.srt.content.repo.RepoFileData;
import com.whirlycott.cache.Cache;
import com.whirlycott.cache.CacheConfiguration;
import com.whirlycott.cache.CacheException;
import com.whirlycott.cache.CacheManager;

public class ContentManager implements InitializingBean,
		ApplicationContextAware {
	private static final Log log = LogFactory.getLog(ContentManager.class);

	@SuppressWarnings("unused")
	private ApplicationContext applicationContext;
 
	private CacheConfiguration cacheConfiguration;

	private Cache runtimeContentCache;
	private Cache runtimePageCache;

	protected ContentRepoAccessDao contentRepoAccessDao;

	protected Unmarshaller contentItemUnmarshaller;

	public ContentManager() {
		super();
	}

	public void init() {

		log.debug("ContentManager.init(): Entry ...");

		cacheConfiguration = new CacheConfiguration();

		cacheConfiguration.setName("EnterpriseRuntimeContentCache");
		cacheConfiguration
				.setBackend("com.whirlycott.cache.impl.ConcurrentHashMapImpl");
		cacheConfiguration.setTunerSleepTime(60);
		cacheConfiguration
				.setPolicy("com.whirlycott.cache.policy.LFUMaintenancePolicy");
		cacheConfiguration.setMaxSize(10000);

		try {
			runtimeContentCache = CacheManager.getInstance().createCache(
					cacheConfiguration);
			runtimePageCache = CacheManager.getInstance().createCache(
					cacheConfiguration);
		} catch (CacheException cacheException) {
			log.error("Failed to initialize Runtime Content Cache",
					cacheException);
		}
	}

	public ContentRepoAccessDao getContentRepoAccessDao() {
		return contentRepoAccessDao;
	}

	public void setContentRepoAccessDao(
			ContentRepoAccessDao contentRepoAccessDao) {
		this.contentRepoAccessDao = contentRepoAccessDao;
	}


	public void setContentItemUnmarshaller(Unmarshaller contentItemUnmarshaller) {
		this.contentItemUnmarshaller = contentItemUnmarshaller;
	}


	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public void afterPropertiesSet() throws Exception {

	}

	public Map<String, NodeRef> getAllContentNodes(String rootFolderPath) {

		Set<String> propNames = new HashSet<String>();
		propNames.add("amex:CMS_ID");

		return contentRepoAccessDao.buildRepoNodeMap(propNames, "/xml");
	}

	public synchronized void addOrUpdateFiles(List<RepoFileData> newAndModFiles) {

		Map<String, RepoFileData> updateFileMap = new HashMap<String, RepoFileData>();
		List<NodeRef> addedOrUpdatedNodeRefs;

		for (RepoFileData aReporFileData : newAndModFiles) {
			if (aReporFileData.isUpdate()) {
				updateFileMap.put(aReporFileData.getFilePath(), aReporFileData);
			}
		}

		long start = System.currentTimeMillis();

		log.debug("Starting content adding/updating in the REPO ...");

		addedOrUpdatedNodeRefs = contentRepoAccessDao
				.addOrUpdateFiles(newAndModFiles);

		log.debug("Added/updated " + addedOrUpdatedNodeRefs.size()
				+ " files: elapsed = " + (System.currentTimeMillis() - start));

		for (NodeRef nodeRef : addedOrUpdatedNodeRefs) {

			if (updateFileMap.get(nodeRef.getPath()) != null) {

				// Update runtime cache if object is there

				String typeName = (String) nodeRef.getProperties().get(
						"amex:Type");

				if (typeName != null && typeName.equals("PAGE")) {

					String pageCacheKey = generatePageCacheKey(nodeRef);

					if (runtimePageCache.retrieve(pageCacheKey) != null) {
						updatePageInRuntimePageCache(pageCacheKey, nodeRef);
					}
				} else if (typeName != null && typeName.equals("NAVIGATION")) {

					String pageCacheKey = nodeRef.getPath();

					if (runtimePageCache.retrieve(pageCacheKey) != null) {
						updatePageInRuntimePageCache(pageCacheKey, nodeRef);
					}
				} else {
					if (runtimeContentCache != null && nodeRef != null
							&& nodeRef.getCmsId() != null) {
						// if (runtimeContentCache.retrieve(nodeRef.getCmsId())
						// != null) {
						// updateContentInRuntimeCache(nodeRef);
						// }
						Object cachedObject = runtimeContentCache
								.retrieve(nodeRef.getCmsId());
						if (cachedObject != null) {
							updateContentInRuntimeCache(nodeRef);
						}
					}
				}
			}
		}
	}

	private String generatePageCacheKey(NodeRef pageNodeRef) {

		String locale = (String) pageNodeRef.getProperties().get(
				"amex:Locale");
		String pagePath = (String) pageNodeRef.getProperties().get(
				"amex:URL");

		String pageCacheKey = locale + "/" + pagePath;

		return pageCacheKey;
	}

	public void deleteFiles(Collection<NodeRef> nodeRefList,
			List<ContentLoadReportEntry> list) {

		for (NodeRef node : nodeRefList) {

			ContentLoadReportEntry contentLoadReportEntry = new ContentLoadReportEntry();

			contentLoadReportEntry.setContentItemPath(node.getPath());
			contentLoadReportEntry.setOperation("DELETE");
			contentLoadReportEntry.setOutcome("SUCCESS");

			log.debug("To-be-removed repo file name: " + node.getPath()
					+ ": identified = " + node.getIdentifier());

			runtimeContentCache.remove(node.getCmsId());

			list.add(contentLoadReportEntry);
		}

		contentRepoAccessDao.deleteFiles(nodeRefList);
	}

	public void deleteFiles(Collection<NodeRef> nodeRefList,
			Map<String, String> cmsIDMap, List<ContentLoadReportEntry> list) {

		for (NodeRef node : nodeRefList) {

			log.debug("To-be-removed repo file name: " + node.getPath()
					+ ": identified = " + node.getIdentifier());

			if (StringUtils.isNotEmpty(node.getCmsId())) {
				cmsIDMap.remove(node.getCmsId());
			}

			runtimeContentCache.remove(node.getCmsId());
		}

		contentRepoAccessDao.deleteFiles(nodeRefList);
	}

	public void updateContentInRuntimeCache(NodeRef nodeRef) {

		log.debug("Updating " + nodeRef.getPath() + ", identifier = "
				+ nodeRef.getIdentifier() + " in Runtime Cache");

		log.debug("    => type = "
				+ nodeRef.getProperties().get("amex:Type"));

		ContentItem contentItem = unmarshalContentItem(nodeRef);
		runtimeContentCache.store(nodeRef.getCmsId(), contentItem);
	}

	public void updatePageInRuntimePageCache(String pageCacheKey,
			NodeRef nodeRef) {

		log.debug("Updating " + nodeRef.getPath() + ", identifier = "
				+ nodeRef.getIdentifier() + " in Runtime Page Cache");

		log.debug("    => type = "
				+ nodeRef.getProperties().get("amex:Type"));

		Object currentCachedObject = runtimePageCache.retrieve(pageCacheKey);

		if (currentCachedObject instanceof ContentItem) {

			ContentItem contentItem = unmarshalContentItem(nodeRef);
			runtimePageCache.store(pageCacheKey, contentItem);
		} else {

			throw new IllegalStateException("Invalid cached object of type: "
					+ currentCachedObject.getClass().getName());
		}
	}

	public String getPageCategory(String filePath) {

		String localeString = filePath.substring("/xml/".length());
		localeString = localeString.substring(0, localeString.indexOf('/'));

		String prefix = "/xml/" + localeString + "/";

		String category = filePath.substring(prefix.length());
		int pos = category.lastIndexOf('.');
		category = category.substring(0, pos);

		StringTokenizer tokenizer = new StringTokenizer(category, "/");

		category = tokenizer.nextToken();

		return category;
	}

	public String getPagePath(String filePath) {
		String localeString = filePath.substring("/xml/".length());
		localeString = localeString.substring(0, localeString.indexOf('/'));

		String prefix = "/xml/" + localeString + "/";

		String category = filePath.substring(prefix.length());
		int pos = category.lastIndexOf('.');
		category = category.substring(0, pos);

		StringTokenizer tokenizer = new StringTokenizer(category, "/");

		category = tokenizer.nextToken();
		String pagePath = category;
		while (tokenizer.hasMoreTokens()) {
			pagePath = tokenizer.nextToken();
		}

		if (!pagePath.equals(category)) {
			pagePath = category + "/" + pagePath;
		}

		return pagePath;
	}

	public ContentItem retrievePage(String cmsID) {
		ContentItem pageContentJCRObject = null;


		pageContentJCRObject = (ContentItem) runtimePageCache
				.retrieve(cmsID);

		if (pageContentJCRObject == null) {
			long timestamp = System.currentTimeMillis();
			
			Set<String> props = new TreeSet<String>();

			props.add("amex:CMS_ID");
			
			NodeRef pageNodeRef = findContentItemByCmsId(cmsID, props);
			log.debug("findPageNodeRef: elapsed = "
					+ (System.currentTimeMillis() - timestamp));

			// Populate and cache
			timestamp = System.currentTimeMillis();

			// returning null when the pageNode not found
			if (null == pageNodeRef) {
				return null;
			}
			pageContentJCRObject = unmarshalContentItem(pageNodeRef);
			pageContentJCRObject.setJcrPath(pageNodeRef.getPath());

			// Process Page data to verify/populate pageName and pageParentName

			Page pageJCRObject = (Page) pageContentJCRObject.getContent();

			runtimePageCache.store(cmsID, pageContentJCRObject);

			log.debug("Page Unmarshal: elapsed = "
					+ (System.currentTimeMillis() - timestamp));
		}

		return pageContentJCRObject;
	}
	
	public ContentItem retrievePageByURL(String URL) {
		ContentItem pageContentJCRObject = null;


		pageContentJCRObject = (ContentItem) runtimePageCache.retrieve(URL);

		List<ContentItem> pageList = new ArrayList<ContentItem>();

		if (pageContentJCRObject == null){
			StringBuilder query = new StringBuilder();
	
			query.append("SELECT * FROM [amex:Page] WHERE ");
			
			query.append("([amex:URL] = '").append(URL).append("')");
	
			log.info ("QUERY ->" + query.toString());
			
			Set<String> propNames = new HashSet<String>();
			propNames.add("amex:CMS_ID");
			propNames.add("amex:URL");
			
			List<NodeRef> nodeRefList = contentRepoAccessDao.findFiles(propNames, query.toString(), null, null);
			
			
			for (NodeRef nodeRef : nodeRefList) {
				ContentItem contentItem = unmarshalContentItem(nodeRef);
	
				if (contentItem.getJcrPath() == null) {
					contentItem.setJcrPath(nodeRef.getPath());
				}
	
				runtimeContentCache.store(nodeRef.getCmsId(), contentItem);
	
				pageList.add(contentItem);
			}			
		}
		return pageList.get(0);
	}
		

	public ContentItem retrieveContentItemByCmsId(String cmsId) {
		ContentItem contentItem = (ContentItem) runtimeContentCache
				.retrieve(cmsId);

		if (contentItem == null) {
			NodeRef nodeRef = findContentItemByCmsId(cmsId, null);

			if (nodeRef != null) {
				contentItem = unmarshalContentItem(nodeRef);
				runtimeContentCache.store(contentItem.getCmsId(), contentItem);
			}
		}

		return contentItem;
	}

	private ContentItem unmarshalContentItem(NodeRef contentItemNodeRef) {

		ContentItem contentItemJCRObject = null;

		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
				contentItemNodeRef.getContent());
		StreamSource streamSource = new StreamSource(byteArrayInputStream);

		try {
			contentItemJCRObject = (ContentItem) contentItemUnmarshaller
					.unmarshal(streamSource);
		} catch (XmlMappingException xmlMappingException) {
			log.error(xmlMappingException);
		} catch (IOException ioException) {
			log.error(ioException);
		}

		return contentItemJCRObject;
	}

	private NodeRef findContentItemByCmsId(String cmsId,
			Set<String> propNamesAttr) {

		Set<String> propNames = propNamesAttr;

		if (propNames == null) {
			propNames = new HashSet<String>();
		}

		String sqlQueryString = "SELECT * FROM [amex:ContentItem] WHERE [amex:CMS_ID] = '"
				+ cmsId + "'";

		propNames.add("amex:Locale");
		propNames.add("amex:Type");
		propNames.add("amex:CMS_ID");

		List<NodeRef> nodeList = contentRepoAccessDao.findFiles(propNames,
				sqlQueryString, null, null);

		if (nodeList == null || nodeList.isEmpty()) {
			return null;
		}

		return nodeList.get(0);
	}

	
	public List<NodeRef> findPageNodeRefs(String locale) {
		return findPageNodeRefs(locale, null);
	}

	public List<NodeRef> findPageNodeRefs(String locale, String orderByClause) {
		Set<String> propNames = new HashSet<String>();
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
				.append("SELECT * FROM [amex:Page] WHERE [amex:Locale] = '")
				.append(locale).append("'");

		if (StringUtils.isNotEmpty(orderByClause)) {
			stringBuilder.append(" ORDER BY ").append(orderByClause);
		}

		String sql = stringBuilder.toString();
		List<NodeRef> nodeList = contentRepoAccessDao.findFiles(propNames, sql,
				null, null);

		if (nodeList == null) {
			log.warn("There were no JCR nodes found for locale " + locale);
			return null;
		}
		
		if (nodeList.isEmpty()) {
			if (log.isDebugEnabled()) {
				log.warn("There were no JCR nodes found for locale " + locale);
			}

			return null;
		}

		return nodeList;
	}


	public void clearRuntimeContentCache() {
		runtimeContentCache.clear();
	}

	public void clearRuntimePageCache() {
		runtimePageCache.clear();
	}

	public void removeItemFromRuntimeContentCache(String cacheKey) {
		runtimeContentCache.remove(cacheKey);
	}

	public void removeItemFromRuntimePageCache(String cacheKey) {
		runtimeContentCache.remove(cacheKey);
	}

	public void clearAll() {

		contentRepoAccessDao.deleteFolder("/xml");
	}

	public void listFolder(String folderPath) {

		try {
			contentRepoAccessDao.listFolder(folderPath, log, "amex:");
		} catch (RepositoryException repositoryException) {
			log.error(repositoryException);
		}
	}
  
	public List<ContentItem> retrieveReferencedPages(ContentItem cItem){
		List<ContentItem> contentItemList=new ArrayList<ContentItem>();
		if(cItem==null){
			return contentItemList;
		}
		CreditPage page=(CreditPage) cItem.getContent();
		
		return contentItemList;
	} 
	
	
	public List<ContentItem> retrieveContentItemByCmsId(List<String> cmsIdList){
		List<ContentItem> contentItemList=new ArrayList<ContentItem>();
		if(cmsIdList==null){
			return contentItemList;
		}
		
		for(String cmsId:cmsIdList){
			contentItemList.add(retrieveContentItemByCmsId(cmsId));
		}
		return contentItemList;
	}
 
	
}
