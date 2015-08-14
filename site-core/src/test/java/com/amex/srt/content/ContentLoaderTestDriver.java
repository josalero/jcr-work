package com.amex.srt.content;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.amex.srt.util.MicrosoftStringUtils;

@SuppressWarnings("unused")
public class ContentLoaderTestDriver {

	/**
	 * Logger for this class
	 */
	private static final Log log = LogFactory
			.getLog(ContentLoaderTestDriver.class);

	private ApplicationContext applicationContext;

	public static void main(String[] args) {
		ContentLoaderTestDriver testDriver = new ContentLoaderTestDriver();
		testDriver.init();
		 testDriver.testFullReload();
		 try {
		 Thread.sleep(600000);
		 }
		 catch (InterruptedException interruptedException) {
		
		 }finally{
				testDriver.testScrub();
				testDriver.destroy();
		 }
	
	}

	private void init() {
		applicationContext = new ClassPathXmlApplicationContext(
				"applicationContext-test.xml");
	}

	private void destroy() {
		((ClassPathXmlApplicationContext) applicationContext).destroy();
	}

	private void testRefresh(ContentLoader contentLoader, ContentManager contentManager, ContentLoadReport contentLoadReport) {
		log.info("Entry ...");

		try {

			log.info("LOAD STATUS: " + contentLoadReport.getStatus());

			log.info("   ADDS:");
			for (ContentLoadReportEntry reportEntry : contentLoadReport
					.getSuccessfulAddedEntries()) {
				log.info(" => " + reportEntry.getContentItemPath());
			}

			log.info("   UPDATES:");
			for (ContentLoadReportEntry reportEntry : contentLoadReport
					.getSuccessfulUpdatedEntries()) {
				log.info(" => " + reportEntry.getContentItemPath());
			}

			log.info("   DELETES:");
			for (ContentLoadReportEntry reportEntry : contentLoadReport
					.getSuccessfulDeletedEntries()) {
				log.info(" => " + reportEntry.getContentItemPath());
			}

			log.info("   FAILED ENTRIES:");
			for (ContentLoadReportEntry reportEntry : contentLoadReport
					.getFailedEntries()) {
				log.info(" => " + reportEntry.getContentItemPath()
						+ ": outcome = " + reportEntry.getOutcome());
			}

			// contentManager.getContentRepoAccessDao().listFolder("/xml", log,
			// "");
		} catch (Exception exception) {
			log.error("ERROR", exception);
		}
	}

	private void testFullReload() {
		log.info("Entry ...");
		ContentLoader contentLoader = (ContentLoader) applicationContext
				.getBean("contentLoader");
		ContentManager contentManager = (ContentManager) applicationContext
				.getBean("contentManager");

		try {
			contentManager.listFolder("/xml");
			// contentManager.clearAll();
			ContentLoadReport contentLoadReport = contentLoader.cleanAndReloadContent();
			log.info("After load");
			contentManager.listFolder("/xml");
			testRefresh(contentLoader, contentManager, contentLoadReport);
		} catch (Exception exception) {
			log.error("ERROR", exception);
		}
	}
	
	private void testScrub() {
		String unscrubbedData = "wineries. Theyâ€™ve survived";
		String cleanData = MicrosoftStringUtils.scrub(unscrubbedData);
		log.info("CLEAN DATA: " + cleanData);
	}
}
