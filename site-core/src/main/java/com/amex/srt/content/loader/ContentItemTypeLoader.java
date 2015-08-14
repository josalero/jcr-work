package com.amex.srt.content.loader;

import java.io.File;

import com.amex.srt.content.ContentItem;
import com.amex.srt.content.ContentLoadReport;
import com.amex.srt.content.ContentManager;
import com.amex.srt.content.repo.RepoFileData;

public interface ContentItemTypeLoader {

	RepoFileData processContentItem(String originalContentAsXmlString, ContentItem contentItem, String filePath, File file, ContentManager contentManager, ContentLoadReport contentLoadReport);
}
