package com.amex.srt.card.serviceapi;

import org.fornax.cartridges.sculptor.framework.test.AbstractDbUnitJpaTests;
import static org.junit.Assert.fail;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Spring based transactional test with DbUnit support.
 */
public class ContentServiceTest extends AbstractDbUnitJpaTests
    implements ContentServiceTestBase {
    @Autowired
    protected ContentService contentService;

    @Test
    public void testGetPageByURL() throws Exception {
        // TODO Auto-generated method stub
        fail("testGetPageByURL not implemented");
    }
}
