/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.configuration.model.support.throttle.ThrottleCoreProperties
 *  org.apereo.inspektr.common.web.ClientInfoHolder
 */
package org.apereo.cas.web.support;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.configuration.model.support.throttle.ThrottleCoreProperties;
import org.apereo.cas.web.support.AbstractInMemoryThrottledSubmissionHandlerInterceptorAdapter;
import org.apereo.cas.web.support.ThrottledSubmissionHandlerConfigurationContext;
import org.apereo.inspektr.common.web.ClientInfoHolder;

public class InMemoryThrottledSubmissionByIpAddressAndUsernameHandlerInterceptorAdapter
extends AbstractInMemoryThrottledSubmissionHandlerInterceptorAdapter {
    public InMemoryThrottledSubmissionByIpAddressAndUsernameHandlerInterceptorAdapter(ThrottledSubmissionHandlerConfigurationContext configurationContext) {
        super(configurationContext);
    }

    public String constructKey(HttpServletRequest request) {
        ThrottleCoreProperties throttle = this.getConfigurationContext().getCasProperties().getAuthn().getThrottle().getCore();
        String username = request.getParameter(throttle.getUsernameParameter());
        if (StringUtils.isBlank((CharSequence)username)) {
            return request.getRemoteAddr();
        }
        return ClientInfoHolder.getClientInfo().getClientIpAddress() + ";" + username.toLowerCase();
    }

    public String getName() {
        return "inMemoryIpAddressUsernameThrottle";
    }
}

