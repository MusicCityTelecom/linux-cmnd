/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.ServletRequest
 *  javax.servlet.ServletResponse
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.core.log.LogMessage
 *  org.springframework.http.HttpStatus
 *  org.springframework.security.access.AccessDeniedException
 *  org.springframework.util.Assert
 */
package org.springframework.security.web.access;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.log.LogMessage;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.util.Assert;

public class AccessDeniedHandlerImpl
implements AccessDeniedHandler {
    protected static final Log logger = LogFactory.getLog(AccessDeniedHandlerImpl.class);
    private String errorPage;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        if (response.isCommitted()) {
            logger.trace((Object)"Did not write to response since already committed");
            return;
        }
        if (this.errorPage == null) {
            logger.debug((Object)"Responding with 403 status code");
            response.sendError(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase());
            return;
        }
        request.setAttribute("SPRING_SECURITY_403_EXCEPTION", (Object)accessDeniedException);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        if (logger.isDebugEnabled()) {
            logger.debug((Object)LogMessage.format((String)"Forwarding to %s with status code 403", (Object)this.errorPage));
        }
        request.getRequestDispatcher(this.errorPage).forward((ServletRequest)request, (ServletResponse)response);
    }

    public void setErrorPage(String errorPage) {
        Assert.isTrue((errorPage == null || errorPage.startsWith("/") ? 1 : 0) != 0, (String)"errorPage must begin with '/'");
        this.errorPage = errorPage;
    }
}

