package com.amex.srt.util;

import java.io.File;

import org.apache.commons.io.FilenameUtils;

public class FilePathUtils {
	public static final String JCR_REPOSITORY_BASE_PATH = "/xml/en_US";
	public static final String JCR_MILITARY_REPOSITORY_BASE_PATH = "/military";

	public static String getFolderPath(String folderPath, boolean isFile) {
		folderPath = FilenameUtils.separatorsToSystem(folderPath);
		String systemRepStartingPath = FilenameUtils
				.separatorsToSystem(JCR_REPOSITORY_BASE_PATH);
		int pos = folderPath.indexOf(systemRepStartingPath);

		if (pos != -1) {
			folderPath = folderPath.substring(pos, folderPath.length());
		}

		if (isFile) {
			pos = FilenameUtils.indexOfLastSeparator(folderPath);

			if (pos != -1) {
				folderPath = folderPath.substring(0, pos);
			}
		}

		return folderPath.replace(String.valueOf(File.separatorChar), "/");
	}
	
	public static String getMilitaryFolderPath(String folderPath, boolean isFile) {
		folderPath = FilenameUtils.separatorsToSystem(folderPath);
		String systemRepStartingPath = FilenameUtils
				.separatorsToSystem(JCR_MILITARY_REPOSITORY_BASE_PATH);
		int pos = folderPath.indexOf(systemRepStartingPath);

		if (pos != -1) {
			folderPath = folderPath.substring(pos, folderPath.length());
		}

		if (isFile) {
			pos = FilenameUtils.indexOfLastSeparator(folderPath);

			if (pos != -1) {
				folderPath = folderPath.substring(0, pos);
			}
		}

		return folderPath.replace(String.valueOf(File.separatorChar), "/");
	}	
}
