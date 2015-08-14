package com.amex.srt.web.jcr;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.jackrabbit.core.SessionFactory;
import org.apache.jackrabbit.webdav.DavException;
import org.apache.jackrabbit.webdav.DavSession;
import org.apache.jackrabbit.webdav.DavSessionProvider;
import org.apache.jackrabbit.webdav.WebdavRequest;
import org.apache.jackrabbit.webdav.simple.DavSessionImpl;
import org.springmodules.jcr.JcrSessionFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class JCRSessionProviderImpl.
 */
public class JCRSessionProviderImpl implements DavSessionProvider {

	/** The session factory. */
	private JcrSessionFactory sessionFactory;

	/**
	 * Instantiates a new jCR session provider impl.
	 * 
	 * @param sessionFactory
	 *            the session factory
	 */
	public JCRSessionProviderImpl(JcrSessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.jackrabbit.webdav.DavSessionProvider#attachSession(org.apache
	 * .jackrabbit.webdav.WebdavRequest)
	 */
	@Override
	public boolean attachSession(WebdavRequest request) throws DavException {
		DavSession ds;
		try {
			ds = new DavSessionImpl(sessionFactory.getSession());
			request.setDavSession(ds);
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.jackrabbit.webdav.DavSessionProvider#releaseSession(org.apache
	 * .jackrabbit.webdav.WebdavRequest)
	 */
	@Override
	public void releaseSession(WebdavRequest request) {
		// TODO Auto-generated method stub
		DavSession ds = request.getDavSession();
		if (ds != null && ds instanceof DavSessionImpl) {
			Session repSession = ((DavSessionImpl) ds).getRepositorySession();
			for (String lockToken : repSession.getLockTokens()) {
				repSession.removeLockToken(lockToken);
			}
			sessionFactory.getSessionHolder(repSession).reset();
		} // else : session is null. nothing to be done.
		request.setDavSession(null);
	}
}
