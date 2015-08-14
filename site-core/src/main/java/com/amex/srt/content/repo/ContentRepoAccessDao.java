package com.amex.srt.content.repo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jcr.Binary;
import javax.jcr.Item;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.PropertyType;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.UnsupportedRepositoryOperationException;
import javax.jcr.Value;
import javax.jcr.ValueFactory;
import javax.jcr.ValueFormatException;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;
import javax.jcr.version.VersionException;
import javax.jcr.version.VersionManager;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.jackrabbit.JcrConstants;
import org.apache.jackrabbit.commons.cnd.CompactNodeTypeDefReader;
import org.apache.jackrabbit.commons.cnd.ParseException;
import org.apache.jackrabbit.core.nodetype.InvalidNodeTypeDefException;
import org.apache.jackrabbit.core.nodetype.NodeTypeManagerImpl;
import org.apache.jackrabbit.core.nodetype.NodeTypeRegistry;
import org.apache.jackrabbit.spi.QNodeTypeDefinition;
import org.apache.jackrabbit.spi.QPropertyDefinition;
import org.apache.jackrabbit.spi.commons.namespace.NamespaceMapping;
import org.apache.jackrabbit.spi.commons.nodetype.QDefinitionBuilderFactory;
import org.apache.jackrabbit.util.ISO8601;
import org.springframework.beans.factory.InitializingBean;
import org.springmodules.jcr.JcrCallback;
import org.springmodules.jcr.support.JcrDaoSupport;

import eu.medsea.mimeutil.MimeUtil;

public class ContentRepoAccessDao extends JcrDaoSupport implements InitializingBean {

	static {
		MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
	}

	/**
	 * Logger for this class
	 */
	private static final Log log = LogFactory.getLog(ContentRepoAccessDao.class);

	public static final String REPO_NAMESPACE_PREFIX = "amex";
	public static final String REPO_NAMESPACE = "http://www.amex.com/ns/repo";

	public static final String REPO_AMEX_NAVIGATION_TYPE = "amex:Navigation";
	public static final String REPO_AMEX_TAGS_DEFINITION_TYPE = "amex:TagDefinitions";

	public static final String REPO_AMEX_CONTENT_ITEM_TYPE = "amex:ContentItem";
	public static final String REPO_AMEX_PAGE_TYPE = "amex:Page";
	public static final String REPO_ENT_TILE_TYPE = "amex:Tile";

	public static final String REPO_CMS_ID = "amex:CMS_ID";

	private Map<String, QPropertyDefinition> propertyDefinitionsMap = new HashMap<String, QPropertyDefinition>();
	
	
	public void updateFile(String filePath, byte[] fileContentAsByteArray, Map<String, Object> props) {
		putFile(filePath, null, fileContentAsByteArray, props, false, true);
	}

	public void putFile(String filePath, String fileType, byte[] fileContentAsByteArray, Map<String, Object> props,
			final boolean versioned) {
		putFile(filePath, fileType, fileContentAsByteArray, props, versioned, false);
	}

	public void putFile(String filePath, final String fileType, final byte[] fileContentAsByteArray,
			final Map<String, Object> props, final boolean versioned, final boolean update) {

		String[] names = filePath.split("/");

		final String finalFileName = names[names.length - 1];
		String folderPath = "";

		for (int idx = 1; idx < names.length - 1; ++idx) {
			folderPath = folderPath + "/" + names[idx];
		}

		final String finalFolderPath = folderPath;

		getTemplate().execute(new JcrCallback() {

			public Object doInJcr(Session session) throws IOException, RepositoryException {

				Node folderNode = getFolder(session, finalFolderPath);

				ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(fileContentAsByteArray);
				importFile(session, folderNode, finalFileName, fileType, byteArrayInputStream, props, versioned, update);

				return null;
			}
		});
	}

	public void putFile(String folderPath, String fileName, InputStream inputStream, final Map<String, Object> props) {
		putFile(folderPath, fileName, JcrConstants.NT_FILE, inputStream, props);
	}

	public void putFile(final String folderPath, final String fileName, final String fileType,
			final InputStream inputStream, final Map<String, Object> props) {

		getTemplate().execute(new JcrCallback() {

			public Object doInJcr(Session session) throws IOException, RepositoryException {

				Node folderNode = getFolder(session, folderPath);
				importFile(session, folderNode, fileName, fileType, inputStream, props, false, false);

				session.save();

				return null;
			}
		});
	}

	@SuppressWarnings("unchecked")
	public List<NodeRef> addOrUpdateFiles(final List<RepoFileData> repoFileDataList) {

		List<NodeRef> addedOrUpdatedFiles = null;

		addedOrUpdatedFiles = (List<NodeRef>) getTemplate().execute(new JcrCallback() {

			public Object doInJcr(Session session) throws IOException, RepositoryException {

				List<NodeRef> files = new ArrayList<NodeRef>();

				for (RepoFileData fileData : repoFileDataList) {

					Node folderNode = getFolder(session, fileData.getFolderPath());

					String fileName = fileData.getFileName();
					String fileType = fileData.getFileType();
					InputStream inputStream = fileData.getInputStream();
					Map<String, Object> props = fileData.getProps();

					NodeRef nodeRef = importOrUpdateFile(session, fileData.getFilePath(), folderNode, fileName,
							fileType, inputStream, props, fileData.isVersioned());

					if (nodeRef == null) {
					
						throw new IllegalStateException("importOrUpdateFile returned NULL for " + fileName);
					}
					
					files.add(nodeRef);
				}

				session.save();

				return files;
			}
		});

		return addedOrUpdatedFiles;
	}

	private Node getFolder(Session session, String folderPath) throws RepositoryException {

		Node folderNode = null;

		String[] folderNames = folderPath.split("/");

		Node currentNode = session.getRootNode();

		for (int idx = 1; idx < folderNames.length; ++idx) {

			String folderName = folderNames[idx];
			Node aNode = null;

			try {
				aNode = currentNode.getNode(folderName);
			}
			catch (PathNotFoundException notFoundException) {
			}

			if (aNode == null) {
				aNode = currentNode.addNode(folderName, "nt:folder");
			}

			currentNode = aNode;
		}

		folderNode = currentNode;

		return folderNode;
	}

	public NodeRef getFile(String filePath) {
		try {
			return getFile(filePath, null);
		}
		catch (Throwable e) {
			throw new RuntimeException("Error getting file: " + filePath, e);
		}
	}

	public NodeRef getFile(final String filePath, final Set<String> propNames) {

		NodeRef nodeRef = (NodeRef) getTemplate().execute(new JcrCallback() {

			public Object doInJcr(Session session) throws IOException, RepositoryException {

				Item item = null;

				try {

					session.itemExists(filePath);
					item = session.getItem(filePath);
				}
				catch (Throwable exception) {
					log.error(exception);
					return null;
				}

				Node node = (Node) item;

				NodeRef nodeRef = getNodeRef(propNames, node);

				if (nodeRef == null) {
					throw new FileNotFoundException();
				}

				return nodeRef;
			}
		});

		return nodeRef;
	}

	private NodeRef getNodeRef(final Set<String> propNames, Node node) throws UnsupportedRepositoryOperationException,
		RepositoryException, ValueFormatException, PathNotFoundException, IOException {

		return getNodeRef(propNames, node, true, true);
	}
	
	private NodeRef getNodeRef(final Set<String> propNames, Node node, boolean fetchContent, boolean fetchProperties) throws UnsupportedRepositoryOperationException,
			RepositoryException, ValueFormatException, PathNotFoundException, IOException {

		log.debug("Node " + node.getName());

		String uuid = node.getProperty(JcrConstants.JCR_UUID).getString();

		String path = node.getPath();
		String name = node.getName();
		String folder = path.substring(0, path.lastIndexOf('/'));

		byte[] fileContent = null;
		Date modifiedDate = null;
		
		Node contentNode;
	
		try {
			
			contentNode = node.getNode(JcrConstants.JCR_CONTENT);
			modifiedDate = contentNode.getProperty(JcrConstants.JCR_LASTMODIFIED).getDate().getTime();
		}
		catch (PathNotFoundException e) {
			throw new PathNotFoundException("Path not found accessing: " + node.getPath());
		}

		if (fetchContent) {
				
			Property data = contentNode.getProperty(JcrConstants.JCR_DATA);

			if (data != null) {

				InputStream inputStream = data.getBinary().getStream();
				fileContent = readStreamIntoByteArray(inputStream);
			}
		}

		NodeRef nodeRef = 
				new NodeRef(node.getIdentifier(), path, name, folder, uuid, fileContent, modifiedDate);


		if (fetchProperties && propNames != null) {
			
			for (String propName : propNames) {
			
				try {
				
					Property property = node.getProperty(propName);
							
					if (property.isMultiple()) {

						Value[] values = property.getValues();
						
						List<String> propValues = new ArrayList<String>();
						for (int idx = 0; idx < propValues.size(); ++idx) {
							propValues.add(values[idx].getString());
						}

						nodeRef.getProperties().put(propName, propValues);
					}
					else {
						String propValue = property.getString();
						nodeRef.getProperties().put(propName, propValue);
					}
				}
				catch (Exception exception) {
					log.error(exception);
				}
			}
		}

		return nodeRef;
	}

	public boolean fileExists(String path) {

		Session session = getSession();

		boolean exists = false;

		try {
			exists = session.itemExists(path);
		}
		catch (RepositoryException repositoryException) {
			log.error(repositoryException);
		}

		releaseSession(session);

		return exists;
	}

	public String getFileAsString(String filePath) {

		final String finalFilePath = filePath;

		String fileContent = (String) getTemplate().execute(new JcrCallback() {

			public Object doInJcr(Session session) throws IOException, RepositoryException {

				Item item = session.getItem(finalFilePath + "/jcr:content");

				Node node = (Node) item;
				Property data = node.getProperty("jcr:data");
				InputStream inputStream = data.getBinary().getStream();

				return new String(readStreamIntoByteArray(inputStream));
			}
		});

		return fileContent;
	}

	public StreamSource getFileAsStreamSource(String filePath) {
		final String finalFilePath = filePath;
		StreamSource fileContent = null;

		try {
			fileContent = (StreamSource) getTemplate().execute(new JcrCallback() {

				public Object doInJcr(Session session) throws IOException, RepositoryException {

					Item item = session.getItem(finalFilePath + "/jcr:content");

					Node node = (Node) item;
					Property data = node.getProperty("jcr:data");
					InputStream inputStream = data.getBinary().getStream();

					return new StreamSource(inputStream);
				}
			});
		}
		catch (Exception e) {
			log.error("Unable to get the filepath: " + filePath + " from the JCR!", e);
		}

		return fileContent;
	}

	/**
	 * Imports a File.
	 * 
	 * @param parentnode
	 *            Parent Repository Node
	 * @param fileType
	 * @param props
	 * @param versioned
	 * @param update
	 * @param file
	 *            File to be imported
	 * @throws RepositoryException
	 *             on repository errors, IOException on io errors
	 */
	public Node importFile(Session session, Node parentnode, String fileName, String fileType, InputStream inputStream,
			Map<String, Object> props, boolean versioned, boolean update) throws RepositoryException, IOException {

		Node fileNode;

		if (!update) {

			if (fileType == null) {
				throw new IllegalArgumentException("File type is not specified");
			}

			fileNode = parentnode.addNode(fileName, fileType);
		}
		else {
			fileNode = parentnode.getNode(fileName);
		}

		VersionManager versionManager = session.getWorkspace().getVersionManager();
		ValueFactory valueFactory = session.getValueFactory();

		if (update && versioned) {
			versionManager.checkout(fileNode.getPath());
		}

		Node resNode;

		if (!update) {
			resNode = fileNode.addNode(JcrConstants.JCR_CONTENT, JcrConstants.NT_RESOURCE);
			resNode.setProperty(JcrConstants.JCR_MIMETYPE, "application/octet-stream");
			resNode.setProperty("jcr:encoding", "utf-8");
			Binary binary = valueFactory.createBinary(inputStream);
			resNode.setProperty(JcrConstants.JCR_DATA, binary);
			Calendar lastModified = Calendar.getInstance();
			resNode.setProperty(JcrConstants.JCR_LASTMODIFIED, lastModified);
		}
		else {
			resNode = fileNode.getNode(JcrConstants.JCR_CONTENT);
			Binary binary = valueFactory.createBinary(inputStream);
			resNode.getProperty(JcrConstants.JCR_DATA).setValue(binary);
			Calendar lastModified = Calendar.getInstance();
			resNode.setProperty(JcrConstants.JCR_LASTMODIFIED, lastModified);
		}

		setNodeProperties(props, fileNode, valueFactory);

		if (update && versioned) {
			session.save();
			versionManager.checkin(fileNode.getPath());
		}
		else {
			if (versioned) {
				fileNode.addMixin(JcrConstants.MIX_VERSIONABLE);
				session.save();
				versionManager.checkin(fileNode.getPath());
			}
			else {
				fileNode.addMixin(JcrConstants.MIX_REFERENCEABLE);
				session.save();
			}
		}

		if (log.isDebugEnabled()) {
			log.debug("importFile(Node, File) - " + fileNode.getPath());
		}

		return fileNode;
	}

	private void setNodeProperties(Map<String, Object> props, Node fileNode, ValueFactory valueFactory)
			throws ValueFormatException, VersionException, LockException, ConstraintViolationException,
			RepositoryException {

		if (props != null) {

			for (String propName : props.keySet()) {

				boolean isMultiple = false;

				Object propValueObject = props.get(propName);

				if (propValueObject != null) {

					List<Value> propValues = new ArrayList<Value>();
					Value propValue = null;

					if (propValueObject instanceof String) {
						propValue = valueFactory.createValue((String) propValueObject);
					}
					else if (propValueObject instanceof Double) {
						propValue = valueFactory.createValue((Double) propValueObject);
					}
					else if (propValueObject instanceof Date) {
						Calendar cal = Calendar.getInstance();
						cal.setTime((Date) propValueObject);

						String dateValueAsString = ISO8601.format(cal);

						propValue = valueFactory.createValue(dateValueAsString, PropertyType.DATE);
					}
					else if (propValueObject instanceof Boolean) {

						propValue = valueFactory.createValue((Boolean) propValueObject);
					}
					else if (propValueObject instanceof Long) {

						propValue = valueFactory.createValue((Long) propValueObject);
					}else if (propValueObject instanceof List) {

						// We assume this is a List of String values

						@SuppressWarnings("unchecked")
						List<String> propValueObjectAsStringList = (List<String>) propValueObject;

						for (String propValueAsString : propValueObjectAsStringList) {

							propValue = valueFactory.createValue(propValueAsString);
							propValues.add(propValue);
						}

						isMultiple = true;
					}

					if (isMultiple) {

						Value[] values = new Value[propValues.size()];
						int idx = 0;

						for (Value aPropValue : propValues) {
							values[idx] = aPropValue;
							++idx;
						}

						fileNode.setProperty(propName, values);
					}
					else {

						fileNode.setProperty(propName, propValue);
					}
				}
			}
		}
	}

	/**
	 * Imports a File if doesn't exist or updates one if already there.
	 * 
	 * @param parentnode
	 *            Parent Repository Node
	 * @param fileType
	 * @param props
	 * @param versioned
	 * @param update
	 * @param file
	 *            File to be imported
	 * @throws RepositoryException
	 *             on repository errors, IOException on io errors
	 */
	public NodeRef importOrUpdateFile(Session session, String filePath, Node parentnode, String fileName,
			String fileType, InputStream inputStream, Map<String, Object> props, boolean versioned)
			throws RepositoryException, IOException {
		
		boolean update = false;
		boolean exists = false;

		try {
			exists = session.itemExists(filePath);
			update = exists;
		}
		catch (RepositoryException repositoryException) {
			log.error(repositoryException);
		}

		Node fileNode;

		if (!update) {

			if (fileType == null) {
				throw new IllegalArgumentException("File type is not specified");
			}

			fileNode = parentnode.addNode(fileName, fileType);
		}
		else {
			fileNode = parentnode.getNode(fileName);
		}

		VersionManager versionManager = session.getWorkspace().getVersionManager();
		ValueFactory valueFactory = session.getValueFactory();

		if (update && versioned) {
			versionManager.checkout(fileNode.getPath());
		}

		Node resNode;

		if (!update) {
			resNode = fileNode.addNode(JcrConstants.JCR_CONTENT, JcrConstants.NT_RESOURCE);
			resNode.setProperty(JcrConstants.JCR_MIMETYPE, "application/octet-stream");
			resNode.setProperty("jcr:encoding", "utf-8");
			Binary binary = valueFactory.createBinary(inputStream);
			resNode.setProperty(JcrConstants.JCR_DATA, binary);
			Calendar lastModified = Calendar.getInstance();
			resNode.setProperty(JcrConstants.JCR_LASTMODIFIED, lastModified);
		}
		else {
			resNode = fileNode.getNode(JcrConstants.JCR_CONTENT);
			Binary binary = valueFactory.createBinary(inputStream);
			resNode.getProperty(JcrConstants.JCR_DATA).setValue(binary);
			Calendar lastModified = Calendar.getInstance();
			resNode.setProperty(JcrConstants.JCR_LASTMODIFIED, lastModified);
		}

		setNodeProperties(props, fileNode, valueFactory);

		if (update && versioned) {
			session.save();
			versionManager.checkin(fileNode.getPath());
		}
		else {
			if (versioned) {
				fileNode.addMixin(JcrConstants.MIX_VERSIONABLE);
				session.save();
				versionManager.checkin(fileNode.getPath());
			}
			else {
				fileNode.addMixin(JcrConstants.MIX_REFERENCEABLE);
				session.save();
			}
		}

		if (log.isDebugEnabled()) {
			log.debug("importFile(Node, File) - " + fileNode.getPath());
		}

		Set<String> propNames = null;

		if (props != null) {
			propNames = props.keySet();
		}

		NodeRef nodeRef = getNodeRef(propNames, fileNode);

		return nodeRef;
	}

	public void listFolder(final String folderPath, final Log targetLog, final String propertyFilter)
			throws RepositoryException {

		getTemplate().execute(new JcrCallback() {

			public Object doInJcr(Session session) throws IOException, RepositoryException {

				Node folderNode = getFolder(session, folderPath);
				printNodeToLog(folderNode, targetLog, propertyFilter);

				return null;
			}
		});
	}

	/**
	 * Dumps the contents of the given node to standard output.
	 * 
	 * @param node
	 *            the node to be dumped
	 * @throws RepositoryException
	 *             on repository errors
	 */
	public void dump(Node node, Log targetLog) throws RepositoryException {

		targetLog.info(node.getName() + ": Path - " + node.getPath());

		PropertyIterator properties = node.getProperties();
		while (properties.hasNext()) {

			Property property = properties.nextProperty();

			StringBuilder propLogStringBuilder = new StringBuilder();
			propLogStringBuilder.append("     Property - " + property.getName() + " = ");

			if (property.getDefinition().isMultiple()) {

				Value[] values = property.getValues();
				for (int i = 0; i < values.length; i++) {
					if (i > 0) {
						propLogStringBuilder.append(", ");
					}
					propLogStringBuilder.append(values[i].getString());
				}
			}
			else {

				if (property.getType() == PropertyType.BINARY) {
					propLogStringBuilder.append("<binary>");
				}
				else {
					propLogStringBuilder.append(property.getString());
				}
			}

			targetLog.info(propLogStringBuilder.toString());
		}

		NodeIterator nodes = node.getNodes();
		while (nodes.hasNext()) {
			Node child = nodes.nextNode();
			dump(child, targetLog);
		}
	}

	public void printNodeToLog(Node node, Log targetLog, String propertyFilter) throws RepositoryException {
		if (!node.getName().endsWith("jcr:content")) {
			String type = node.getProperty(JcrConstants.JCR_PRIMARYTYPE).getString();
			
			String nodePath = node.getPath();
			
			PropertyIterator properties = node.getProperties();
			
			if (type.equals(ContentRepoAccessDao.REPO_AMEX_PAGE_TYPE)) {
				targetLog.debug("\tnodePath.getName()" + node.getName());

				while (properties.hasNext()) {

					Property property = properties.nextProperty();

					if (propertyFilter != null && property.getName().startsWith(propertyFilter)) {

						StringBuilder propLogStringBuilder = new StringBuilder();
						propLogStringBuilder.append("     Property - " + property.getName() + " = ");

						if (property.getDefinition().isMultiple()) {

							Value[] values = property.getValues();
							for (int i = 0; i < values.length; i++) {
								if (i > 0) {
									propLogStringBuilder.append(", ");
								}
								propLogStringBuilder.append(values[i].getString());
							}
						}
						else {

							if (property.getType() == PropertyType.BINARY) {
								propLogStringBuilder.append("<binary>");
							}
							else {
								propLogStringBuilder.append(property.getString());
							}
						}

						targetLog.info(propLogStringBuilder.toString());
					}
				}				
			}
			
			NodeIterator nodes = node.getNodes();
			while (nodes.hasNext()) {
				Node child = nodes.nextNode();
				printNodeToLog(child, targetLog, propertyFilter);
			}			
			
		}

	}

	public byte[] readStreamIntoByteArray(InputStream inputStream) throws IOException {

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

		byte[] buf = new byte[1024];
		int len;
		while ((len = inputStream.read(buf)) > 0) {
			byteArrayOutputStream.write(buf, 0, len);
		}

		return byteArrayOutputStream.toByteArray();
	}

	public void setProperty(String uuid, String propertyName, String propertyValue) {

		final String finalNodeUUID = uuid;
		final String finalPropertyName = propertyName;
		final String finalPropertyValue = propertyValue;

		getTemplate().execute(new JcrCallback() {

			public Object doInJcr(Session session) throws IOException, RepositoryException {

				Node node = session.getNodeByIdentifier(finalNodeUUID);
				node.setProperty(finalPropertyName, finalPropertyValue);
				session.save();

				return null;
			}
		});
	}

	public void createCustomNodeTypes(Session session) throws RepositoryException, IOException, ParseException {

		InputStream inputStream = this.getClass().getClassLoader()
				.getResourceAsStream("META-INF/jcr/amex_srt_nodetypes.cnd");

		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

		CompactNodeTypeDefReader<QNodeTypeDefinition, NamespaceMapping> compactNodeTypeDefReader = new CompactNodeTypeDefReader<QNodeTypeDefinition, NamespaceMapping>(
				inputStreamReader, "", new QDefinitionBuilderFactory());

		List<QNodeTypeDefinition> nodeTypeDefs = compactNodeTypeDefReader.getNodeTypeDefinitions();

		NodeTypeManagerImpl nodeTypeManager = (NodeTypeManagerImpl) session.getWorkspace().getNodeTypeManager();

		NodeTypeRegistry nodeTypeRegistry = nodeTypeManager.getNodeTypeRegistry();

		for (QNodeTypeDefinition nodeTypeDef : nodeTypeDefs) {

			try {
				log.info("nodeTypeDef --> " + nodeTypeDef.getName());
				nodeTypeRegistry.registerNodeType(nodeTypeDef);
			}
			catch (InvalidNodeTypeDefException nodeTypeDefException) {

				try {
					nodeTypeRegistry.reregisterNodeType(nodeTypeDef);
				}
				catch (InvalidNodeTypeDefException ie) {
					log.error(ie);
				}
			}
			
			for (QPropertyDefinition propertyDefinition : nodeTypeDef.getPropertyDefs()) {
				
				log.info("Prop Name = " + propertyDefinition.getName().getLocalName());
				propertyDefinitionsMap.put(propertyDefinition.getName().getLocalName(), propertyDefinition);
			}
		}
	}

	public String getProperty(String filePath, String propName) {

		final String finalFilePath = filePath;
		final String finalPropName = propName;

		String propValue = (String) getTemplate().execute(new JcrCallback() {

			public Object doInJcr(Session session) throws IOException, RepositoryException {

				Item item = null;

				try {
					item = session.getItem(finalFilePath);
				}
				catch (Exception exception) {
					log.error(exception);
					return null;
				}

				Node node = (Node) item;
				Property prop = node.getProperty(finalPropName);

				return prop.getString();
			}
		});

		return propValue;
	}

	public void init() {

		log.info("Init: template = " + getTemplate());

		Session session = getSession();

		try {

			String[] jcrNamespaces = session.getWorkspace().getNamespaceRegistry().getPrefixes();
			boolean createNamespace = true;
			for (int i = 0; i < jcrNamespaces.length; i++) {
				if (jcrNamespaces[i].equals(REPO_NAMESPACE_PREFIX)) {
					createNamespace = false;
				}
			}
			if (createNamespace) {
				session.getWorkspace().getNamespaceRegistry().registerNamespace(REPO_NAMESPACE_PREFIX, REPO_NAMESPACE);
			}

			createCustomNodeTypes(session);
		}
		catch (Exception exception) {
			log.error(exception);
		}

		releaseSession(session);
	}

	public void shutdown() {
		Session session = getSession();
		((org.apache.jackrabbit.core.RepositoryImpl) session.getRepository()).shutdown();
	}

	public static String convertDateToString(Calendar date) {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		String text = df.format(date.getTime());
		String dateString = text.substring(0, 22) + ":" + text.substring(22);

		return dateString;
	}

	@SuppressWarnings("unchecked")
	public List<NodeRef> findFiles(final Set<String> propNames, final String sqlQueryString, final Integer limit,
			final Integer offset) {

		List<NodeRef> result = (List<NodeRef>) getTemplate().execute(new JcrCallback() {

			public Object doInJcr(Session session) throws IOException, RepositoryException {

				List<NodeRef> nodeRefList = new ArrayList<NodeRef>();

				QueryManager queryManager = session.getWorkspace().getQueryManager();
				Query query = queryManager.createQuery(sqlQueryString, Query.JCR_SQL2);

				if (limit != null) {
					query.setLimit(limit);
				}

				if (offset != null) {
					query.setOffset(offset);
				}

				QueryResult queryResult = query.execute();
				NodeIterator nodeIterator = queryResult.getNodes();

				while (nodeIterator.hasNext()) {

					Node node = nodeIterator.nextNode();
					NodeRef nodeRef = getNodeRef(propNames, node);

					if (nodeRef != null) {
						nodeRefList.add(nodeRef);
					}
				}

				return nodeRefList;
			}
		});

		return result;
	}

	@SuppressWarnings("unchecked")
	public Map<String, NodeRef> buildRepoNodeMap(final Set<String> propNames, final String path) {

		long start = System.currentTimeMillis();
		
		log.info("Building Repo Node Map: Entry ...");
		
		Map<String, NodeRef> result = (Map<String, NodeRef>) getTemplate().execute(new JcrCallback() {

			public Object doInJcr(Session session) throws IOException, RepositoryException {

				Node entryNode = getFolder(session, path);

				Map<String, NodeRef> repMap = new HashMap<String, NodeRef>();

				NodeIterator nodes = entryNode.getNodes();

				while (nodes.hasNext()) {

					Node node = nodes.nextNode();

					buildRepoMap(propNames, node, repMap);
				}

				return repMap;
			}
		});

		log.info("Finished Building Repo Node Map: elapsed = " + (System.currentTimeMillis() - start));

		return result;
	}

	private void buildRepoMap(final Set<String> propNames, Node node, Map<String, NodeRef> repMap)
			throws UnsupportedRepositoryOperationException, ValueFormatException, PathNotFoundException,
			RepositoryException, IOException {

		if (node.isNodeType("nt:file")) {

			NodeRef nodeRef = getNodeRef(propNames, node, false, true);

			if (nodeRef != null) {
				repMap.put(nodeRef.getPath(), nodeRef);
			}
		}

		NodeIterator nodes = node.getNodes();

		while (nodes.hasNext()) {

			node = nodes.nextNode();
			buildRepoMap(propNames, node, repMap);
		}

		return;

	}

	public byte[] queryFile(final String queryString, final String queryType) {

		byte[] result = (byte[]) getTemplate().execute(new JcrCallback() {

			public Object doInJcr(Session session) throws IOException, RepositoryException {

				byte[] fileContent = null;

				QueryManager queryManager = session.getWorkspace().getQueryManager();
				Query query = queryManager.createQuery(queryString, queryType);

				QueryResult queryResult = query.execute();
				NodeIterator nodeIterator = queryResult.getNodes();

				Node node = null;

				while (nodeIterator.hasNext()) {
					node = nodeIterator.nextNode();
				}

				Property data = node.getNode(JcrConstants.JCR_CONTENT).getProperty(JcrConstants.JCR_DATA);

				if (data != null) {

					InputStream keyDataInputStream = data.getBinary().getStream();
					fileContent = readStreamIntoByteArray(keyDataInputStream);
				}

				return fileContent;
			}
		});

		return result;
	}

	public void deleteFiles(final Collection<NodeRef> nodes) {

		getTemplate().execute(new JcrCallback() {

			public Object doInJcr(Session session) throws IOException, RepositoryException {

				try {

					for (NodeRef aNodeRef : nodes) {

						Node aNode = session.getNodeByIdentifier(aNodeRef.getIdentifier());
						aNode.remove();
					}

					session.save();
				}
				catch (Exception exception) {
					log.error(exception);
				}

				return null;
			}
		});
	}

	public void deleteFolder(final String folderPath) {

		getTemplate().execute(new JcrCallback() {

			public Object doInJcr(Session session) throws IOException, RepositoryException {

				try {

					Node folderNode = getFolder(session, folderPath);
					folderNode.remove();
					session.save();
				}
				catch (Exception exception) {
					log.error(exception);
				}

				return null;
			}
		});
	}
}
