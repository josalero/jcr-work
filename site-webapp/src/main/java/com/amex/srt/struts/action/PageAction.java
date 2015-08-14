package com.amex.srt.struts.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.amex.srt.content.ContentItem;
import com.amex.srt.content.ContentManager;
import com.amex.srt.content.CreditPage;
import com.opensymphony.xwork2.ActionContext;

// TODO: Auto-generated Javadoc
/**
 * PageAction encapsulates core logic and functionality that occurs across all
 * subpages.
 * 
 * @author christopher.tai@starcomworldwide.com
 */
@SuppressWarnings("serial")
public class PageAction extends AbstractBaseAction {

	/** The Constant log. */
	private static final Log log = LogFactory.getLog(PageAction.class);

    @Autowired
	private ContentManager contentManager;
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bac.oee.struts.action.AbstractBaseAction#execute()
	 */
	@Override
	public String execute() {

		ContentItem contentItem = contentManager.retrievePageByURL("/amex/srt/index.html");

		if (contentItem != null){
			actionContext = ActionContext.getContext();
			
			actionContext.put("index", contentItem);
			actionContext.put("creditPage", (CreditPage)contentItem.getContent());
		}
	
		return SUCCESS;

	}

}
