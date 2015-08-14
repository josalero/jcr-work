package com.amex.srt.content.loader;

import java.io.File;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.amex.srt.content.ContentItem;
import com.amex.srt.content.ContentLoadReport;
import com.amex.srt.content.ContentManager;
import com.amex.srt.content.repo.ContentRepoAccessDao;
import com.amex.srt.content.repo.RepoFileData;

public class PageXMLContentLoader extends BaseContentItemLoader implements ContentItemTypeLoader {

	/**
	 * Logger for this class
	 */
	private static final Log log = LogFactory.getLog(PageXMLContentLoader.class);

	@Override
	public RepoFileData processContentItem(String originalContentAsXmlString, ContentItem contentItem, String filePath, File file, ContentManager contentManager, ContentLoadReport contentLoadReport) {
		RepoFileData repoFileData = super.processContentItem(originalContentAsXmlString, contentItem, filePath, file, contentManager, contentLoadReport);
		
		Map<String, Object> props = repoFileData.getProps();

		props.put("amex:URL", contentItem.getUrl());
		log.debug ("\tamex:URL" + contentItem.getUrl());

		repoFileData.setFileType(ContentRepoAccessDao.REPO_AMEX_PAGE_TYPE);
		
		return repoFileData;
	}
}
