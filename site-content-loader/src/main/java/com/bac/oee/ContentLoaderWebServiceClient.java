package com.bac.oee;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bac.oee.webservice.ContentLoaderWebService;


/**
 * ContentLoaderWebServiceClient is the proxy stub that invokes our
 * ContentLoaderWebService.
 * 
 * @author christopher.tai@starcomworldwide.com
 */
public class ContentLoaderWebServiceClient {
	private static final Log log = LogFactory.getLog(ContentLoaderWebServiceClient.class);

	public static void main(String[] args) {

		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				WebServiceConstants.APPLICATION_CONTEXT);

		ContentLoaderWebService contentLoaderWebService = (ContentLoaderWebService) applicationContext
				.getBean(WebServiceConstants.CONTENT_LOADER_WEB_SERVICE_CLIENT_BEAN);

		try {

			contentLoaderWebService.refreshContent();
		}
		catch (Exception e) {
			log.error("Unable to invoke the content loader web service!", e);
		}
	}
}
