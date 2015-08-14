package com.bac.oee.webservice;

import javax.jws.WebService;

/**
 * ContentLoaderWebService is the web service that triggers content to be
 * re-loaded into the application.
 * 
 * @author christopher.tai@starcomworldwide.com
 */

@WebService
public interface ContentLoaderWebService {

	public void refreshContent();
}
