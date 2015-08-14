package com.amex.srt.content.repo;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

import com.amex.srt.util.FilePathUtils;

public class RepoFileData {
	
	private String filePath;
	private String folderPath;
	private String fileName;
	private String fileType;
	private InputStream inputStream;
	private byte[] fileContentAsByteArray;
	private long lastModified;
	private Map<String, Object> props;
	private boolean versioned;
	private boolean update;
	
	private String originalContentAsXmlString;

	public RepoFileData(String filePath, String fileType, byte[] fileContentAsByteArray, Map<String, Object> props, boolean versioned, boolean update) {
		
		super();		
		
		setFilePath(filePath);
		
		this.fileType = fileType;		
		setFileContentAsByteArray(fileContentAsByteArray);
		
		this.props = props;
		this.versioned = versioned;
		this.update = update;
	}

	public RepoFileData(String folderPath, String fileName, String fileType, InputStream inputStream, Map<String, Object> props, long lastModified) {
		
		super();		
		
		this.folderPath = folderPath;		
		this.fileName = fileName;
		this.fileType = fileType;
		this.inputStream = inputStream;
		this.props = props;		
		this.filePath = folderPath + "/" + fileName;		
		this.lastModified = lastModified;
	}
	
	public RepoFileData(File file, String fileType, Map<String, Object> props) throws FileNotFoundException {
		
		super();
		
		this.folderPath = FilePathUtils.getFolderPath(file.getPath(), true);
		this.fileName = file.getName();
		this.fileType = fileType;		
		this.inputStream = new FileInputStream(file.getPath());		
		this.props = props;			
		this.filePath = this.folderPath + "/" + file.getName();		
		this.lastModified = file.lastModified();
	}
	
	public RepoFileData(File file, String fileType, Map<String, Object> props, String folderPath) throws FileNotFoundException {
		
		super();
		
		this.folderPath = folderPath;
		this.fileName = file.getName();
		this.fileType = fileType;		
		this.inputStream = new FileInputStream(file.getPath());		
		this.props = props;			
		this.filePath = this.folderPath + "/" + file.getName();		
		this.lastModified = file.lastModified();
	}	
	
	public long getLastModified() {
		return lastModified;
	}

	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}

	public String getFolderPath() {
		return folderPath;
	}

	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public Map<String, Object> getProps() {
		return props;
	}

	public void setProps(Map<String, Object> props) {
		this.props = props;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;	
		String[] names = this.filePath.split("/");
		String fileName = names[names.length - 1];
		String folderPath = "";

		for (int idx = 1; idx < names.length - 1; ++idx) {
			folderPath = folderPath + "/" + names[idx];
		}

		setFolderPath(folderPath);
		setFileName(fileName);
	}

	public byte[] getFileContentAsByteArray() {
		return fileContentAsByteArray;
	}

	public void setFileContentAsByteArray(byte[] fileContentAsByteArray) {
		this.fileContentAsByteArray = fileContentAsByteArray;
		setInputStream(new ByteArrayInputStream(fileContentAsByteArray));
	}

	public boolean isVersioned() {
		return versioned;
	}

	public void setVersioned(boolean versioned) {
		this.versioned = versioned;
	}

	public boolean isUpdate() {
		return update;
	}

	public void setUpdate(boolean update) {
		this.update = update;
	}

	public String getOriginalContentAsXmlString() {
		return originalContentAsXmlString;
	}

	public void setOriginalContentAsXmlString(String originalContentAsXmlString) {
		this.originalContentAsXmlString = originalContentAsXmlString;
	}
}
