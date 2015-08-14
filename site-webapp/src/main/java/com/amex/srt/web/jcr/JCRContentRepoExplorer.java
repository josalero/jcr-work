package com.amex.srt.web.jcr;

import javax.jcr.Repository;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.jackrabbit.server.BasicCredentialsProvider;
import org.apache.jackrabbit.server.CredentialsProvider;
import org.apache.jackrabbit.webdav.simple.SimpleWebdavServlet;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springmodules.jcr.JcrSessionFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class JCRContentRepoExplorer.
 */
public class JCRContentRepoExplorer extends SimpleWebdavServlet {

	/** The session factory. */
	private JcrSessionFactory sessionFactory;

	/** The Constant CTX_PARAM_THIS. */
	private static final String CTX_PARAM_THIS = "JCR_CONTEXT";

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// check if servlet is defined twice
		// if (getServletContext()!=null &&
		// getServletContext().getAttribute(CTX_PARAM_THIS) != null) {
		// throw new
		// ServletException("Only one repository access servlet allowed per web-app.");
		// }
		// getServletContext().setAttribute(CTX_PARAM_THIS, this);
		super.init(config);
		ServletContext context = this.getServletContext();
		WebApplicationContext wContext = WebApplicationContextUtils
				.getRequiredWebApplicationContext(context);

		this.sessionFactory = (JcrSessionFactory) wContext
				.getBean("sessionFactory");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.jackrabbit.webdav.simple.SimpleWebdavServlet#getRepository()
	 */
	@Override
	public Repository getRepository() {
		// TODO Auto-generated method stub
		Repository repo = sessionFactory.getRepository();
		return repo;
	}

	// @Override
	// public synchronized DavSessionProvider getDavSessionProvider() {
	// // TODO Auto-generated method stub
	// JCRSessionProviderImpl sessionProvider=new
	// JCRSessionProviderImpl(sessionFactory);
	// return sessionProvider;
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.jackrabbit.webdav.simple.SimpleWebdavServlet#
	 * getCredentialsProvider()
	 */
	@Override
	protected CredentialsProvider getCredentialsProvider() {
		// TODO Auto-generated method stub
		return new BasicCredentialsProvider("admin:admin");
	}

}
