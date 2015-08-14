package com.amex.srt.web.servlet;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

// TODO: Auto-generated Javadoc
/**
 * The Class EnterpriseSiteWrappedRequest.
 */
public class EnterpriseSiteWrappedRequest extends HttpServletRequestWrapper {

	/** The modifiable parameters. */
	private final Map<String, String[]> modifiableParameters;

	/** The all parameters. */
	private Map<String, String[]> allParameters = null;

	/**
	 * Instantiates a new enterprise site wrapped request.
	 * 
	 * @param request
	 *            the request
	 * @param additionalParams
	 *            the additional params
	 */
	public EnterpriseSiteWrappedRequest(HttpServletRequest request,
			final Map<String, String[]> additionalParams) {
		super(request);

		modifiableParameters = new TreeMap<String, String[]>();
		modifiableParameters.putAll(additionalParams);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletRequestWrapper#getParameter(java.lang.String)
	 */
	@Override
	public String getParameter(final String name) {
		String[] strings = getParameterMap().get(name);
		if (strings != null) {
			return strings[0];
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletRequestWrapper#getParameterMap()
	 */
	@Override
	public Map<String, String[]> getParameterMap() {
		if (allParameters == null) {
			allParameters = new TreeMap<String, String[]>();
			allParameters.putAll(super.getParameterMap());
			allParameters.putAll(modifiableParameters);
		}
		// Return an unmodifiable collection because we need to uphold the
		// interface contract.
		return Collections.unmodifiableMap(allParameters);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletRequestWrapper#getParameterNames()
	 */
	@Override
	public Enumeration<String> getParameterNames() {
		return Collections.enumeration(getParameterMap().keySet());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.ServletRequestWrapper#getParameterValues(java.lang.String)
	 */
	@Override
	public String[] getParameterValues(final String name) {
		return getParameterMap().get(name);
	}
}
