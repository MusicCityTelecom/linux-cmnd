/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.text.StringEscapeUtils
 *  org.apereo.cas.util.function.FunctionUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.throttle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.apereo.cas.throttle.ThrottledRequestResponseHandler;
import org.apereo.cas.util.function.FunctionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultThrottledRequestResponseHandler
implements ThrottledRequestResponseHandler {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultThrottledRequestResponseHandler.class);
    private final String usernameParameter;

    @Override
    public boolean handle(HttpServletRequest request, HttpServletResponse response) {
        return (Boolean)FunctionUtils.doUnchecked(() -> {
            String username = StringUtils.isNotBlank((CharSequence)this.usernameParameter) ? StringUtils.defaultString((String)request.getParameter(this.usernameParameter), (String)"N/A") : "N/A";
            String msg = "Access Denied for user [" + StringEscapeUtils.escapeHtml4((String)username) + "] from IP Address [" + request.getRemoteAddr() + "]";
            response.sendError(423, msg);
            LOGGER.warn(msg);
            return false;
        });
    }

    @Generated
    public DefaultThrottledRequestResponseHandler(String usernameParameter) {
        this.usernameParameter = usernameParameter;
    }
}

