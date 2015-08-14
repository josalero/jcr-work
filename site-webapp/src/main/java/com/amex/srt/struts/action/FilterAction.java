package com.amex.srt.struts.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



// TODO: Auto-generated Javadoc
/**
 * The Class FilterAction.
 */
public class FilterAction extends AbstractBaseAction {

	/** The Constant log. */
	private static final Log log = LogFactory.getLog(FilterAction.class);

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -262084431237948953L;

	/** The referrer http header. */
	private static String REFERRER_HTTP_HEADER = "Referrer";

	/** The referrer param. */
	private static String REFERRER_PARAM = "referrer";


	// @Autowired
	// private BrandMappingsManager brandMappingsManager;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bac.oee.struts.action.AbstractBaseAction#execute()
	 */
	@Override
	public String execute() {

		return "index";

	}


}