package com.amex.srt.content.loader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.amex.srt.content.ContentItem;
import com.amex.srt.content.ContentLoadReport;
import com.amex.srt.content.ContentManager;
import com.amex.srt.content.Metadata;
import com.amex.srt.content.repo.ContentRepoAccessDao;
import com.amex.srt.content.repo.RepoFileData;

public class BaseContentItemLoader implements ContentItemTypeLoader {
	/**
	 * Logger for this class
	 */
	private static final Log log = LogFactory.getLog(BaseContentItemLoader.class);

	@Override
	public RepoFileData processContentItem(String originalContentAsXmlString, ContentItem contentItem, String filePath,
			File file, ContentManager contentManager, ContentLoadReport contentLoadReport) {

		Map<String, Object> props = new HashMap<String, Object>();

		String localeString = filePath.substring("/xml/".length());
		localeString = localeString.substring(0, localeString.indexOf('/'));

		props.put("amex:Locale", localeString);
		props.put("amex:Type", contentItem.getContentType().name());
		props.put("amex:CMS_ID", contentItem.getCmsId());

		Metadata metadata = contentItem.getMetadata();

		if (metadata != null) {
			setProperty(props, "amex:PublishedDate", metadata.getPublishedDate());
		}

		RepoFileData repoFileData = null;

		try {

			repoFileData = new RepoFileData(file, ContentRepoAccessDao.REPO_AMEX_CONTENT_ITEM_TYPE, props);

			repoFileData.setOriginalContentAsXmlString(originalContentAsXmlString);
		}
		catch (FileNotFoundException fileNotFoundException) {
			log.error(fileNotFoundException);
		}

		return repoFileData;
	}


	protected void setProperty(Map<String, Object> props, String propName, Object propValue) {

		if (propValue != null) {
			props.put(propName, propValue);
		}
	}
}
