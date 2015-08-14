package com.amex.srt.struts.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.amex.srt.content.ContentManager;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.conversion.annotations.Conversion;

// TODO: Auto-generated Javadoc
/**
 * AbstractBaseAction encapsulates core functionality that executes on every
 * page.
 * 
 * @author christopher.tai@starcomworldwide.com
 */
@SuppressWarnings("serial")
@Conversion()
public abstract class AbstractBaseAction extends ActionSupport {

	/** The Constant log. */
	private static final Log log = LogFactory.getLog(AbstractBaseAction.class);

	/** The action context. */
	protected ActionContext actionContext;

	/** The page not found action. */
	protected final String PAGE_NOT_FOUND_ACTION = "pageNotFoundAction";

	/** The server error action. */
	protected final String SERVER_ERROR_ACTION = "serverErrorAction";

	/** The content manager. */
	@Autowired
	protected ContentManager contentManager;


	
	/**
	 * Instantiates a new abstract base action.
	 */
	public AbstractBaseAction() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	public String execute() {


		return SUCCESS;
	}

	
}
