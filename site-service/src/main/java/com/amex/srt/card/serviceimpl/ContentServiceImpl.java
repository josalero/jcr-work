package com.amex.srt.card.serviceimpl;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;

import org.springframework.stereotype.Service;

import com.amex.srt.content.Page;

/**
 * Implementation of ContentService.
 */
@Service("contentService")
public class ContentServiceImpl extends ContentServiceImplBase {
    public ContentServiceImpl() {
    }

    public Page getPageByURL(ServiceContext ctx, String URL) {

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("getPageByURL not implemented");

    }
}
