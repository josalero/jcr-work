/**
 * 
 */
package com.amex.srt.struts.action;

import javax.servlet.http.HttpServletResponse;

// TODO: Auto-generated Javadoc
/**
 * The Class PageNotFoundAction.
 * 
 * @author jose aleman
 */
@SuppressWarnings("serial")
public class PageNotFoundAction extends AbstractBaseAction {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bac.oee.struts.action.AbstractBaseAction#execute()
	 */
	@Override
	public String execute() {
		String result = super.execute();
		//getServletResponse().setStatus(HttpServletResponse.SC_NOT_FOUND);
		return result;
	}
}
