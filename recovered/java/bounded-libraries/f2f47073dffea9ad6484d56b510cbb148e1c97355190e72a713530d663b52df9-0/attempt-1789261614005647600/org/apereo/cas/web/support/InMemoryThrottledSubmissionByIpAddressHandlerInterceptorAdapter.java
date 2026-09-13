/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.apereo.inspektr.common.web.ClientInfoHolder
 */
package org.apereo.cas.web.support;

import javax.servlet.http.HttpServletRequest;
import org.apereo.cas.web.support.AbstractInMemoryThrottledSubmissionHandlerInterceptorAdapter;
import org.apereo.cas.web.support.ThrottledSubmissionHandlerConfigurationContext;
import org.apereo.inspektr.common.web.ClientInfoHolder;

public class InMemoryThrottledSubmissionByIpAddressHandlerInterceptorAdapter
extends AbstractInMemoryThrottledSubmissionHandlerInterceptorAdapter {
    public InMemoryThrottledSubmissionByIpAddressHandlerInterceptorAdapter(ThrottledSubmissionHandlerConfigurationContext configurationContext) {
        super(configurationContext);
    }

    public String constructKey(HttpServletRequest request) {
        return ClientInfoHolder.getClientInfo().getClientIpAddress();
    }

    public String getName() {
        return "inMemoryIpAddressThrottle";
    }
}

