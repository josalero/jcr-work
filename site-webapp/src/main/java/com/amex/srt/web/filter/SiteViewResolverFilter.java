package com.amex.srt.web.filter;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.amex.srt.AppConstants;

// TODO: Auto-generated Javadoc
/**
 * SiteViewResolverFilter handles view resolving with the CMS system.
 * 
 * @author christopher.tai@starcomworldwide.com
 */
public class SiteViewResolverFilter implements Filter {

	/** The Constant log. */
	private static final Log log = LogFactory
			.getLog(SiteViewResolverFilter.class);

	/** The servlet context. */
	private ServletContext servletContext;

	/** The application context. */
	private ApplicationContext applicationContext;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		this.applicationContext = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

		String requestURI = httpServletRequest.getRequestURI();

		boolean needToRedirect = false;

		if ((httpServletRequest.getQueryString() != null)
				&& (httpServletRequest.getQueryString().length() > 0)) {
			requestURI = requestURI.concat("?").concat(
					httpServletRequest.getQueryString());
		}

		HttpSession httpSession = httpServletRequest.getSession();
		log.debug(">>>> SESSION ID: " + httpSession.getId());
		String relativePagePath = requestURI.substring(httpServletRequest
				.getContextPath().length() + 1);
		log.debug("PAGE PATH: " + relativePagePath);

		String pageLocalePrefix = null;
		Locale locale = (Locale) httpSession
				.getAttribute(AppConstants.USER_LOCALE);

		if (locale != null) {
			pageLocalePrefix = (locale.getLanguage() + '-' + locale
					.getCountry());
			pageLocalePrefix = pageLocalePrefix.toLowerCase();
		} else {
			locale = Locale.getDefault();
			log.debug("Locale: " + locale);

			if (locale != Locale.US) {
				locale = Locale.US;
			}

			httpSession.setAttribute(AppConstants.USER_LOCALE, locale);
			pageLocalePrefix = locale.getLanguage() + '-' + locale.getCountry();
			pageLocalePrefix = pageLocalePrefix.toLowerCase();
			httpSession.setAttribute(AppConstants.LOCALE, pageLocalePrefix);
		}

		log.debug("USER LOCALE: " + locale + ", prefix = " + pageLocalePrefix);

		if (!relativePagePath.contains(pageLocalePrefix.toLowerCase())
				&& !relativePagePath.contains("ajax")) {
			String redirectUri = "/" + pageLocalePrefix + "/"
					+ relativePagePath;
			log.debug("Redirect URI: " + redirectUri);

			httpServletResponse
					.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);

			httpServletResponse.setHeader("Location", redirectUri);
			httpServletResponse.setHeader("Connection", "close");

			// httpServletResponse.sendRedirect(redirectUri);
			// Need to set this flag to true because we don't need to call
			// doFilter if Locale is
			// not present in the URL.
			// This flag is meant to avoid calling pages twice.
			needToRedirect = true;
		}

		if (!needToRedirect) {
			filterChain.doFilter(servletRequest, servletResponse);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.servletContext = filterConfig.getServletContext();
		this.applicationContext = WebApplicationContextUtils
				.getRequiredWebApplicationContext(servletContext);
	}

	/**
	 * Gets the application context.
	 * 
	 * @return the application context
	 */
	protected ApplicationContext getApplicationContext() {
		return applicationContext;
	}

}
