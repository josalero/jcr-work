package com.amex.srt.webservice;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;

import com.amex.srt.content.ContentLoader;

// TODO: Auto-generated Javadoc
/**
 * The Class ContentLoaderDataService.
 */
@Path("/content/")
@Produces("application/json")
public class ContentLoaderDataService {

	/** Logger for this class. */
	private static final Logger log = Logger
			.getLogger(ContentLoaderDataService.class);

	/** The content loader. */
	private ContentLoader contentLoader;

	/** The loading is in progress. */
	private static boolean loadingIsInProgress = false;

	/**
	 * Sets the content loader.
	 * 
	 * @param contentLoader
	 *            the new content loader
	 */
	public void setContentLoader(ContentLoader contentLoader) {
		this.contentLoader = contentLoader;
	}

	/**
	 * Sets the in progress.
	 */
	private static synchronized void setInProgress() {
		loadingIsInProgress = true;
	}

	/**
	 * Checks if is in progress.
	 * 
	 * @return true, if is in progress
	 */
	private static synchronized boolean isInProgress() {
		return loadingIsInProgress;
	}

	/**
	 * Sets the done.
	 */
	private static synchronized void setDone() {
		loadingIsInProgress = false;
	}

	/**
	 * Load.
	 * 
	 * @param key
	 *            the key
	 */
	@GET
	@Path("/load")
	public synchronized void load(@PathParam("key") String key) {

		if (isInProgress()) {
			return;
		}

		setInProgress();

		log.info("Load Content");

		try {

			contentLoader.refreshContent();
		} catch (Exception exception) {

			log.error("Unable to refresh content", exception);
		} finally {
			setDone();
		}
	}

	/**
	 * Inits the.
	 */
	public void init() {
	}
}
