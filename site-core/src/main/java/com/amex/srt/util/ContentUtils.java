package com.amex.srt.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ContentUtils {
	
	private static final Log log = LogFactory.getLog(ContentUtils.class);
	
	public static String convertStreamIntoString(InputStream inputStream) throws IOException {		
	
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		StringBuffer buffer = new StringBuffer();
		String line;
		
		while ((line = bufferedReader.readLine()) != null) {
			buffer.append(line).append('\n');
		}

		return buffer.toString();
	}
	
	private static final Pattern CONTENT_ITEM_PATTERN = 
			Pattern.compile("<content-item [^>]*>", Pattern.MULTILINE);
	
	private static final Pattern NAVIGATION_PATTERN = 
			Pattern.compile("<navigation [^>]*>", Pattern.MULTILINE);
	
	private static final Pattern TAG_CONTAINER_PATTERN = 
			Pattern.compile("<tag-container [^>]*>", Pattern.MULTILINE);
	
	private static final Pattern INTERSTITIAL_WHITELIST_PATTERN = 
			Pattern.compile("<interstitial-whitelist [^>]*>", Pattern.MULTILINE);
	
	public static String cleanXmlTagsForMatching(String contentAsXmlString) {
		
		Matcher matcher;

		String cleanContentAsXmlString = contentAsXmlString;

		matcher = CONTENT_ITEM_PATTERN.matcher(cleanContentAsXmlString);
		cleanContentAsXmlString = matcher.replaceFirst("<content-item>");
		
		matcher = NAVIGATION_PATTERN.matcher(cleanContentAsXmlString);
		cleanContentAsXmlString = matcher.replaceFirst("<navigation>");
		
		matcher = TAG_CONTAINER_PATTERN.matcher(cleanContentAsXmlString);
		cleanContentAsXmlString = matcher.replaceFirst("<tag-container>");

		matcher = INTERSTITIAL_WHITELIST_PATTERN.matcher(cleanContentAsXmlString);
		cleanContentAsXmlString = matcher.replaceFirst("<interstitial-whitelist>");

		return cleanContentAsXmlString;
	}
}
