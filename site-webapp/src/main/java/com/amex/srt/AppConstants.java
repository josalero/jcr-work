package com.amex.srt;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


// TODO: Auto-generated Javadoc
/**
 * AppConstants class strictly for storing data to the web application context.
 * 
 * @author christopher.tai@starcomworldwide.com
 */

public class AppConstants {

	/** The Constant log. */
	private static final Log log = LogFactory.getLog(AppConstants.class);


	/** The Constant JCR_CONTENT_LOAD_CRON_EXPRESSION. */
	public static final String JCR_CONTENT_LOAD_CRON_EXPRESSION = "*/5 * * * * MON-FRI";

	// Site View Resolver Constants
	/** The Constant JCR_CONTEXT_PATH. */
	public static final String JCR_CONTEXT_PATH = "/xml/en_US/";

	/** The Constant LOCALIZATION_TOKEN. */
	public static final String LOCALIZATION_TOKEN = "localizationToken";

	/** The Constant LOCALE. */
	public static final String LOCALE = "locale";


	/** The Constant configMap. */
	private static final Map<String, Properties> configMap = new HashMap<String, Properties>();


	public static final String USER_LOCALE = "userLocale";

	/**
	 * Gets the config map.
	 * 
	 * @return the config map
	 */
	public static Map<String, Properties> getConfigMap() {
		return configMap;
	}

	/**
	 * Gets the config.
	 * 
	 * @param key
	 *            the key
	 * @return the config
	 */
	public static String getConfig(String key) {
		Properties p = getProperties("appProperties");
		return p.getProperty(key);
	}

	/**
	 * Gets the properties.
	 * 
	 * @param key
	 *            the key
	 * @return the properties
	 */
	public static Properties getProperties(String key) {
		return configMap.get(key);
	}

	static {
		configMap.put("appProperties",
				AppConstants.loadProperties("/application.properties"));
	}

	/**
	 * Load properties.
	 * 
	 * @param fileName
	 *            the file name
	 * @return the properties
	 */
	private static Properties loadProperties(String fileName) {
		Properties properties = new Properties();
		InputStream inputStream = null;

		try {
			inputStream = AppConstants.class.getClassLoader()
					.getResourceAsStream(fileName);
			if (inputStream != null) {
				properties.load(inputStream);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}

		return properties;
	}
}
