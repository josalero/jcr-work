package com.amex.srt;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;

// TODO: Auto-generated Javadoc
/**
 * Sets up the application context, initializing various dependencies (see the
 * various initXXX methods below). This class is invoked from a listener
 * declaration in the web application deployment descriptor.
 * 
 * @author christopher.tai@starcomworldwide.com
 */
public class ApplicationManager implements ServletContextListener {

	/** The Constant log. */
	private static final Log log = LogFactory.getLog(ApplicationManager.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.
	 * ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		destroyLog4j(servletContextEvent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.ServletContextListener#contextInitialized(javax.servlet
	 * .ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		initLog4j(servletContextEvent);
		log.info("initialized context '"
				+ servletContextEvent.getServletContext()
						.getServletContextName() + "'");
	}

	/**
	 * Initializes the logging subsystem.
	 * 
	 * @param event
	 *            the event
	 */
	protected void initLog4j(ServletContextEvent event) {
		PropertyConfigurator.configureAndWatch(event.getServletContext()
				.getRealPath("/WEB-INF/classes/log4j.properties"));
		log.info("log4j initialized...");
	}

	/**
	 * Shuts down the logging subsystem.
	 * 
	 * @param event
	 *            the event
	 */
	protected void destroyLog4j(ServletContextEvent event) {
		log.info("shutting down log4j...");
		LogManager.shutdown();
	}
}
