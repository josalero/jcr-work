package com.amex.srt.card.serviceapi;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;

import com.amex.srt.content.Page;

/**
 * Generated interface for the Service ContentService.
 */
public interface ContentService {
    public static final String BEAN_ID = "contentService";

    public Page getPageByURL(ServiceContext ctx, String URL);
}
