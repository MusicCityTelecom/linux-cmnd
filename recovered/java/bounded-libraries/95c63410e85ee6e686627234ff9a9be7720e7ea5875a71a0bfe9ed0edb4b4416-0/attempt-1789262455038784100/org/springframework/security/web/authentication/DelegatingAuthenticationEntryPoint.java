/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.core.log.LogMessage
 *  org.springframework.security.core.AuthenticationException
 *  org.springframework.util.Assert
 */
package org.springframework.security.web.authentication;

import java.io.IOException;
import java.util.LinkedHashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.log.LogMessage;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;

public class DelegatingAuthenticationEntryPoint
implements AuthenticationEntryPoint,
InitializingBean {
    private static final Log logger = LogFactory.getLog(DelegatingAuthenticationEntryPoint.class);
    private final LinkedHashMap<RequestMatcher, AuthenticationEntryPoint> entryPoints;
    private AuthenticationEntryPoint defaultEntryPoint;

    public DelegatingAuthenticationEntryPoint(LinkedHashMap<RequestMatcher, AuthenticationEntryPoint> entryPoints) {
        this.entryPoints = entryPoints;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        for (RequestMatcher requestMatcher : this.entryPoints.keySet()) {
            logger.debug((Object)LogMessage.format((String)"Trying to match using %s", (Object)requestMatcher));
            if (!requestMatcher.matches(request)) continue;
            AuthenticationEntryPoint entryPoint = this.entryPoints.get(requestMatcher);
            logger.debug((Object)LogMessage.format((String)"Match found! Executing %s", (Object)entryPoint));
            entryPoint.commence(request, response, authException);
            return;
        }
        logger.debug((Object)LogMessage.format((String)"No match found. Using default entry point %s", (Object)this.defaultEntryPoint));
        this.defaultEntryPoint.commence(request, response, authException);
    }

    public void setDefaultEntryPoint(AuthenticationEntryPoint defaultEntryPoint) {
        this.defaultEntryPoint = defaultEntryPoint;
    }

    public void afterPropertiesSet() {
        Assert.notEmpty(this.entryPoints, (String)"entryPoints must be specified");
        Assert.notNull((Object)this.defaultEntryPoint, (String)"defaultEntryPoint must be specified");
    }
}

