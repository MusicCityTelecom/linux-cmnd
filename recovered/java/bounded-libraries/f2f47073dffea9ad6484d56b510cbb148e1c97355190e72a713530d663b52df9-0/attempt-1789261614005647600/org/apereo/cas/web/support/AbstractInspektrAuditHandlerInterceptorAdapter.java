/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 */
package org.apereo.cas.web.support;

import javax.servlet.http.HttpServletRequest;
import org.apereo.cas.web.support.AbstractThrottledSubmissionHandlerInterceptorAdapter;
import org.apereo.cas.web.support.ThrottledSubmissionHandlerConfigurationContext;

public abstract class AbstractInspektrAuditHandlerInterceptorAdapter
extends AbstractThrottledSubmissionHandlerInterceptorAdapter {
    protected AbstractInspektrAuditHandlerInterceptorAdapter(ThrottledSubmissionHandlerConfigurationContext configurationContext) {
        super(configurationContext);
    }

    @Override
    protected void recordThrottle(HttpServletRequest request) {
        super.recordThrottle(request);
        this.recordAuditAction(request, "THROTTLED_LOGIN_ATTEMPT");
    }
}

