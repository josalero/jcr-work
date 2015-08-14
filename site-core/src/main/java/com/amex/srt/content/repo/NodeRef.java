package com.amex.srt.content.repo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class NodeRef {

	private String cmsId;
	private String identifier;
	
	private String path;
	private String name;
	private String folder;
	
	private String uuid;
	
	private byte[] content;
	
	private Date modifiedDate;
	
	private Map<String, Object> properties = new HashMap<String, Object>();

	public NodeRef(String identifier, String path, String name, String folder, String uuid, byte[] content, Date modifiedDate) {
		
		super();
		
		this.identifier = identifier;
		this.path = path;
		this.name = name;
		this.folder = folder;
		this.uuid = uuid;
		this.content = content;
		this.modifiedDate = modifiedDate;
	}
	
	public String getCmsId() {
		
		if (cmsId == null && properties != null) {
			cmsId = (String)properties.get("oee:CMS_ID");
		}
		
		return cmsId;
	}
	
	public String getIdentifier() {
		return identifier;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}
}
