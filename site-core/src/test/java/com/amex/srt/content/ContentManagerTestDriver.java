package com.amex.srt.content;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.amex.srt.content.ContentItem;
import com.amex.srt.content.ContentManager;
import com.amex.srt.content.Page;

@SuppressWarnings("unused")
public class ContentManagerTestDriver {

	/**
	 * Logger for this class
	 */
	private static final Log log= LogFactory.getLog(ContentManagerTestDriver.class);

	private ApplicationContext applicationContext;
	
	public static void main(String[] args) {
		
		ContentManagerTestDriver testDriver = new ContentManagerTestDriver();
		
		testDriver.init();

//		testDriver.testNavigationContentRetrieval();
		testDriver.testPageContentRetrieval();
		
//		testDriver.testRetrieveByCmsId();
//		testDriver.testRetrieveByTileId();
		
//		testDriver.runReport();
		testDriver.destroy();
	}

	private void runReport() {

		ContentManager contentManager = (ContentManager)applicationContext.getBean("contentManager");
		
		contentManager.listFolder("/xml/en_US/");		
	}

	private void init() {
		
		applicationContext = new ClassPathXmlApplicationContext("applicationContext-test.xml");
	}

	private void destroy() {
		
		((ClassPathXmlApplicationContext)applicationContext).destroy();
	}
	

	private void testPageContentRetrieval() {
		
		log.info("Entry ...");

		ContentManager contentManager = (ContentManager)applicationContext.getBean("contentManager");
		
		try {
			
			long timestamp;
			
			String localeString = "en_US";
			
			// First retrieval
			timestamp = System.currentTimeMillis();
			/*Page page = contentManager.retrieveIndexPageWithReferences("index", "index", localeString);
			
			log.info("First retrieval: elapsed = " + (System.currentTimeMillis() - timestamp));
			 
			log.info("Page Name        = " + page.getPageName());
			log.info("Parent Page Name = " + page.getPageParentName());
			
			List<Page> pageList=page.getPages().getPageList();
			
			if(pageList!=null){
				log.info("Page Count = " + pageList.size());
				int counter=0;
				for(Page refPage:pageList){
					log.info("==Page Index = " + counter++);
					log.info("====Name = " + refPage.getPageName());	
				}
			}else{
				log.info("No reference pages to retrieve ");
			}*/
			
			
			testRetrieveByCmsId();
			// Second retrieval
			timestamp = System.currentTimeMillis();
			testRetrieveByURL();
			
			log.info("Second retrieval: elapsed = " + (System.currentTimeMillis() - timestamp));
		}
		catch (Exception exception) {
			log.error("ERROR", exception);
		}
	}
	
	
	public void testRetrieveByCmsId(){
		log.info("Entry ... testRetrieveByCmsId()");

		ContentManager contentManager = (ContentManager)applicationContext.getBean("contentManager");
		
		try {
			
			long timestamp;
			
			String localeString = "en_US";
			
			// First retrieval
			timestamp = System.currentTimeMillis();
			ContentItem pageContentItem = contentManager.retrievePage("1234");
			
			log.info("First retrieval: elapsed = " + (System.currentTimeMillis() - timestamp));

			Page page = (Page)pageContentItem.getContent();
			
			log.info("\tCMS_ID        = " + pageContentItem.getCmsId());
			log.info("\tPage Name        = " + page.getPageName());
			log.info("\tPage path = " + pageContentItem.getJcrPath());
			log.info("\tTemplate = " + pageContentItem.getTemplate());
			log.info("\tType = " + pageContentItem.getContentType());

		}
		catch (Exception exception) {
			log.error("ERROR", exception);
		}
		
		log.info("Exit: testRetrieveByCmsId");
		
	}
	
	
	public void testRetrieveByURL(){
		log.info("Entry ... testRetrieveByCmsId()");

		ContentManager contentManager = (ContentManager)applicationContext.getBean("contentManager");
		
		try {
			
			long timestamp;
			
			String localeString = "en_US";
			
			// First retrieval
			timestamp = System.currentTimeMillis();
			ContentItem pageContentItem = contentManager.retrievePageByURL("/amex/srt/index.html");
			
			log.info("First retrieval: elapsed = " + (System.currentTimeMillis() - timestamp));

			Page page = (Page)pageContentItem.getContent();
			
			log.info("\tCMS_ID        = " + pageContentItem.getCmsId());
			log.info("\tPage Name        = " + page.getPageName());
			log.info("\tPage path = " + pageContentItem.getJcrPath());
			log.info("\tTemplate = " + pageContentItem.getTemplate());
			log.info("\tType = " + pageContentItem.getContentType());

		}
		catch (Exception exception) {
			log.error("ERROR", exception);
		}
		
		log.info("Exit: testRetrieveByCmsId");
		
	}
}
