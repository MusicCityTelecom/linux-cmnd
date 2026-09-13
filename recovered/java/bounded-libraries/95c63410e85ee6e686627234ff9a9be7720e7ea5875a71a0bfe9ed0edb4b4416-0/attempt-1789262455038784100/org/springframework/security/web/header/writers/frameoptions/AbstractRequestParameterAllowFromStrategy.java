/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.core.log.LogMessage
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.security.web.header.writers.frameoptions;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.log.LogMessage;
import org.springframework.security.web.header.writers.frameoptions.AllowFromStrategy;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Deprecated
public abstract class AbstractRequestParameterAllowFromStrategy
implements AllowFromStrategy {
    private static final String DEFAULT_ORIGIN_REQUEST_PARAMETER = "x-frames-allow-from";
    private String allowFromParameterName = "x-frames-allow-from";
    protected final Log log = LogFactory.getLog(this.getClass());

    AbstractRequestParameterAllowFromStrategy() {
    }

    @Override
    public String getAllowFromValue(HttpServletRequest request) {
        String allowFromOrigin = request.getParameter(this.allowFromParameterName);
        this.log.debug((Object)LogMessage.format((String)"Supplied origin '%s'", (Object)allowFromOrigin));
        if (StringUtils.hasText((String)allowFromOrigin) && this.allowed(allowFromOrigin)) {
            return allowFromOrigin;
        }
        return "DENY";
    }

    public void setAllowFromParameterName(String allowFromParameterName) {
        Assert.notNull((Object)allowFromParameterName, (String)"allowFromParameterName cannot be null");
        this.allowFromParameterName = allowFromParameterName;
    }

    protected abstract boolean allowed(String var1);
}

