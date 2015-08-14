package com.amex.srt.content;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.XmlMappingException;
import org.springframework.stereotype.Service;
import org.springframework.xml.transform.StringSource;
import org.xml.sax.InputSource;

import com.amex.srt.content.loader.ContentItemTypeLoader;
import com.amex.srt.content.loader.PageXMLContentLoader;
import com.amex.srt.content.repo.ContentRepoAccessDao;
import com.amex.srt.content.repo.NodeRef;
import com.amex.srt.content.repo.RepoFileData;
import com.amex.srt.util.ContentUtils;
import com.amex.srt.util.FilePathUtils;

/**
 * ContentLoader encapsulates the logic that refreshes the content delivered by
 * OpenDeploy into the web application.
 * 
 * @author christopher.tai@starcomworldwide.com
 */
@Service
public class ContentLoader implements InitializingBean {

	private static final Log log = LogFactory.getLog(ContentLoader.class);

	@Autowired
	protected ContentManager contentManager;

	@Autowired
	@Qualifier("open.deploy.receiver.directory.path")
	protected String openDeployReceiverDirectoryPath;

	@Autowired
	@Qualifier("contentItemUnmarshaller")
	protected Unmarshaller contentItemUnmarshaller;

	private XPathFactory xPathFactory;

	private static Map<ContentTypeEnum, ContentItemTypeLoader> loaderMap;

	private boolean loadingIsInProgress = false;

	static {
		
		loaderMap = new HashMap<ContentTypeEnum, ContentItemTypeLoader>();

		loaderMap.put(ContentTypeEnum.PAGE, new PageXMLContentLoader());
	}

	public ContentLoader() {
		super();
		xPathFactory = XPathFactory.newInstance();
	}

	public ContentManager getContentManager() {
		return contentManager;
	}

	private synchronized void setInProgress() {
		loadingIsInProgress = true;
	}

	private synchronized boolean isInProgress() {
		return loadingIsInProgress;
	}

	private synchronized void setDone() {
		loadingIsInProgress = false;
	}

	public void setContentManager(ContentManager contentManager) throws Exception {
		this.contentManager = contentManager;
	}

	public synchronized ContentLoadReport refreshContent() {

		ContentLoadReport contentLoadReport = new ContentLoadReport();
		
		if (isInProgress()) {
			contentLoadReport.setStatus("ANOTHER LOAD IS IN PROGRESS");
			return contentLoadReport;
		}

		setInProgress();

		try {

			contentLoadReport = refreshContentInternal();
		}
		catch (Exception exception) {

			log.error("Unable to refresh content", exception);
		}
		finally {
			setDone();
		}
		
		return contentLoadReport;
	}

	protected ContentLoadReport refreshContentInternal() throws Exception {

		ContentLoadReport contentLoadReport = new ContentLoadReport();
		
		Map<String, File> fileSystemMap = new HashMap<String, File>();

		try {

			contentManager.clearRuntimeContentCache();
			contentManager.clearRuntimePageCache();

			File openDeployReceiverDirectory = new File(openDeployReceiverDirectoryPath + "/xml");

			long start = System.currentTimeMillis();
			log.info("Building File System Map: Entry ...");

			fileSystemMap = new HashMap<String, File>();
			buildFileSystemMap(openDeployReceiverDirectory, fileSystemMap);

			log.info("Finished Building File System Map: elapsed = " + (System.currentTimeMillis() - start));

			Map<String, NodeRef> repositoryNodeMap = contentManager.getAllContentNodes("/xml");

			Map<String, String> cmsIDMap = new HashMap<String, String>();
			for (NodeRef nodeRef : repositoryNodeMap.values()) {
				// log.info("NODE REF CMS ID: " + nodeRef.getCmsId());
				
				if (StringUtils.isNotEmpty(nodeRef.getCmsId())) {
					cmsIDMap.put(nodeRef.getCmsId(), nodeRef.getPath());
				}
			}

			contentLoadReport = addOrUpdateRepositoryContent(fileSystemMap, repositoryNodeMap, cmsIDMap);

			contentLoadReport.setStatus("COMPLETED");
		}
		catch (Exception exception) {

			log.error("Content refresh failed", exception);
			
			contentLoadReport.setStatus("FAILED");
		}
		finally {

			fileSystemMap.clear();
			fileSystemMap = null;
		}

		log.info("Content loading completed");
		
		return contentLoadReport;
	}

	public synchronized ContentLoadReport cleanAndReloadContent() {

		ContentLoadReport contentLoadReport = new ContentLoadReport();

		if (isInProgress()) {
			contentLoadReport.setStatus("ANOTHER CONTENT LOAD IS IN PROGRESS");
			return contentLoadReport;
		}

		setInProgress();

		try {

			contentLoadReport = cleanAndReloadContentInternal();
		}
		catch (Exception exception) {

			log.error("Unable to refresh content", exception);
		}
		finally {
			setDone();
		}
		
		return contentLoadReport;
	}

	public ContentLoadReport cleanAndReloadContentInternal() {

		ContentLoadReport contentLoadReport = new ContentLoadReport();

		try {

			File openDeployReceiverDirectory = new File(openDeployReceiverDirectoryPath + "/xml");

			Map<String, File> fileSystemMap = new HashMap<String, File>();
			buildFileSystemMap(openDeployReceiverDirectory, fileSystemMap);

			// Delete all content items
			contentManager.clearAll();

			contentManager.clearRuntimeContentCache();
			contentManager.clearRuntimePageCache();

			Map<String, NodeRef> repositoryNodeMap = new HashMap<String, NodeRef>();

			Map<String, String> cmsIDMap = new HashMap<String, String>();
			for (NodeRef nodeRef : repositoryNodeMap.values()) {
				
				if (StringUtils.isNotEmpty(nodeRef.getCmsId())) {
					cmsIDMap.put(nodeRef.getCmsId(), nodeRef.getPath());
				}
			}
			
			contentLoadReport = addOrUpdateRepositoryContent(fileSystemMap, repositoryNodeMap, cmsIDMap);

			contentLoadReport.setStatus("COMPLETED");
		}
		catch (Exception exception) {

			log.error("Full reload failed", exception);
			contentLoadReport.setStatus("FAILED");
		}
		
		return contentLoadReport;
	}

	protected void buildFileSystemMap(File file, Map<String, File> fileSystemMap) {

		if (file.isDirectory()) {

			File files[] = file.listFiles(new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {
					return !name.contains(".svn");
				}
			});

			for (File fileElement : files) {
				buildFileSystemMap(fileElement, fileSystemMap);
			}
		}
		else if (file.isFile()) {

			String key = FilePathUtils.getFolderPath(file.getPath(), true) + "/" + file.getName();
			log.info("KEY: " + key);
			fileSystemMap.put(key, file);
		}
	}

	private ContentLoadReport addOrUpdateRepositoryContent(Map<String, File> fileSystemMap, Map<String, NodeRef> repositoryMap,
			Map<String, String> cmsIDMap) {

		ContentLoadReport contentLoadReport = new ContentLoadReport();
		
		List<RepoFileData> newAndModFiles = new ArrayList<RepoFileData>();

		for (String filePath : fileSystemMap.keySet()) {

			ContentLoadReportEntry contentLoadReportEntry = new ContentLoadReportEntry();
			contentLoadReportEntry.setContentItemPath(filePath);
			
			File file = fileSystemMap.get(filePath);
			
			NodeRef nodeRef = null;

			// Check if this is a tagging spreadsheet, and, if yes, convert to XML
			
			String xmlFileContent = null;
			String xmlFilePath = null;
			
			nodeRef = repositoryMap.get(filePath);

			boolean addOrUpdate = false;
			boolean update = false;
			boolean add = false;

			if (nodeRef == null) {

				addOrUpdate = true;
				add = true;

				contentLoadReportEntry.setOperation("ADD");
			}
			else {

				Date date = new Date(file.lastModified());
				Calendar fileCal = Calendar.getInstance();
				fileCal.setTime(date);

				date = nodeRef.getModifiedDate();
				Calendar repoCal = Calendar.getInstance();
				repoCal.setTime(date);

				if (fileCal.after(repoCal)) {

					addOrUpdate = true;
					update = true;

					contentLoadReportEntry.setOperation("UPDATE");
				}
			}

			if (addOrUpdate) {

				log.info("To-be-" + ((add) ? "added" : "updated") + " file name: " + filePath);

				RepoFileData repoFileData = null;

				try {

					InputStream xmlSourceStream = new FileInputStream(file);

					// If xmlFileContent is not null, then this is a tagging XML file produced from XLS
					String originalContentAsXmlString = (xmlFileContent != null) ? xmlFileContent : null;

					if (originalContentAsXmlString == null) {
						
						try {
							originalContentAsXmlString = ContentUtils.convertStreamIntoString(xmlSourceStream);
						}
						catch (IOException ioException) {
							log.error(ioException);
						}
					}

					String contentAsXmlString = ContentUtils.cleanXmlTagsForMatching(originalContentAsXmlString);

					if (isContentItemType(contentAsXmlString)) {

						StringSource source = new StringSource(originalContentAsXmlString);

						try {

							ContentItem contentItem = (ContentItem) contentItemUnmarshaller.unmarshal(source);
							ContentItemTypeLoader contentItemTypeLoader = loaderMap.get(contentItem.getContentType());

							log.info("    => Processing Content Item: type = " + contentItem.getContentType()
									+ ", loader = " + contentItemTypeLoader);

							if (contentItemTypeLoader != null) {

								try {

									repoFileData = contentItemTypeLoader.processContentItem(originalContentAsXmlString, contentItem, filePath,
											file, contentManager, contentLoadReport);

									// String utfXMLContent = new
									// String(originalContentAsXmlString.getBytes(),"UTF-8");

									// Check for duplicate IDs
									
									String cmsID = (String)repoFileData.getProps().get(ContentRepoAccessDao.REPO_CMS_ID);
									
									boolean isDuplicateID = false;
									String duplicateElementPath = null;
									
									if (StringUtils.isNotEmpty(cmsID)) {
										
										if (add) {
											
											if (cmsIDMap.containsKey(cmsID)) {
												isDuplicateID = true;
												duplicateElementPath = cmsIDMap.get(cmsID);
											}
											else {
												cmsIDMap.put(cmsID, repoFileData.getFilePath());
											}
										}
									}
									
									if (isDuplicateID) {
										
										contentLoadReportEntry.setOutcome("FAILED: Duplicate CMS ID " + cmsID + "; already exists " + duplicateElementPath);
										contentLoadReport.getFailedEntries().add(contentLoadReportEntry);
									}
									else {
										
										InputStream utfXMLContentStream = 
												new ByteArrayInputStream(repoFileData.getOriginalContentAsXmlString().getBytes("UTF-8"));
										
										repoFileData.setInputStream(utfXMLContentStream);	
										
										contentLoadReportEntry.setOutcome("SUCCESS");
										
										if (add) {
											contentLoadReport.getSuccessfulAddedEntries().add(contentLoadReportEntry);
										}
										else {
											contentLoadReport.getSuccessfulUpdatedEntries().add(contentLoadReportEntry);
										}
									}
								}
								catch (IllegalStateException illegalStateException) {

									log.error("Failed to process content item", illegalStateException);
									
									contentLoadReportEntry.setOutcome("FAILED: IllegalStateException - " + illegalStateException.getMessage());
									contentLoadReport.getFailedEntries().add(contentLoadReportEntry);
								}
							}
							else {

								log.warn("Couldn't find Content Item Type Loader for " + contentItem.getContentType());
							}
						}
						catch (XmlMappingException xmlMappingException) {

							contentLoadReportEntry.setOutcome("FAILED: XmlMappingException - " + xmlMappingException.getMessage());
							contentLoadReport.getFailedEntries().add(contentLoadReportEntry);
							
							log.error(xmlMappingException);
						}
						catch (IOException ioException) {

							contentLoadReportEntry.setOutcome("FAILED: IOException - " + ioException.getMessage());
							contentLoadReport.getFailedEntries().add(contentLoadReportEntry);
							
							log.error(ioException);
						}
					}
					else if (isTagContainer(contentAsXmlString)) {
												
						log.info("    => Processing Tagging Defintions file");

						Map<String, Object> props = new HashMap<String, Object>();
						
						repoFileData = 
								new RepoFileData(xmlFilePath, 
										ContentRepoAccessDao.REPO_AMEX_TAGS_DEFINITION_TYPE, 
										xmlFileContent.getBytes(), 
										props, false, false);
					}
					else {

						log.warn("    => Unrecognized content item type: " + filePath);
					}
				}
				catch (FileNotFoundException fileNotFoundException) {

					log.error("Unable to find the file!", fileNotFoundException);
				}

				if (repoFileData != null) {

					if (update) {
						repoFileData.setUpdate(true);
					}
					else {
						repoFileData.setUpdate(false);
					}

					newAndModFiles.add(repoFileData);
				}
			}

			if (xmlFilePath != null) {
				repositoryMap.remove(xmlFilePath);
			}
			
			repositoryMap.remove(filePath);
		}

		contentManager.deleteFiles(repositoryMap.values(), cmsIDMap, contentLoadReport.getSuccessfulDeletedEntries());
		contentManager.addOrUpdateFiles(newAndModFiles);

		for (RepoFileData repoFileData : newAndModFiles) {

			try {
				repoFileData.getInputStream().close();
			}
			catch (IOException ioException) {
				log.error(ioException);
			}
		}
		
		return contentLoadReport;
	}

	private boolean isContentItemType(String contentAsXmlString) {
		return isInstanceOfContentType("content-item", contentAsXmlString);
	}

	private boolean isTagContainer(String contentAsXmlString) {
		return isInstanceOfContentType("tag-container", contentAsXmlString);
	}

	private boolean isInstanceOfContentType(String contentTag, String contentAsXmlString) {
		XPath xpath = xPathFactory.newXPath();

		try {

			XPathExpression expression = xpath.compile("/" + contentTag);
			String value = expression.evaluate(new InputSource(new StringReader(contentAsXmlString)));

			if (!value.isEmpty()) {
				return true;
			}
		}
		catch (XPathExpressionException xPathExpressionException) {
			log.error(xPathExpressionException);
		}

		return false;
	}

	@Override
	public void afterPropertiesSet() throws Exception {

		log.info("openDeployReceiverDirectoryPath = " + openDeployReceiverDirectoryPath);

		log.info("contentManager = " + contentManager);
	}

}
