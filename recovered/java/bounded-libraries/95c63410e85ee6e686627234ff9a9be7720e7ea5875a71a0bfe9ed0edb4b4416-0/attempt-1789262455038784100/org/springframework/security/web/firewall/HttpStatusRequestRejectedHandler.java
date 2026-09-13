/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.core.log.LogMessage
 */
package org.springframework.security.web.firewall;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.log.LogMessage;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.security.web.firewall.RequestRejectedHandler;

public class HttpStatusRequestRejectedHandler
implements RequestRejectedHandler {
    private static final Log logger = LogFactory.getLog(HttpStatusRequestRejectedHandler.class);
    private final int httpError;

    public HttpStatusRequestRejectedHandler() {
        this.httpError = 400;
    }

    public HttpStatusRequestRejectedHandler(int httpError) {
        this.httpError = httpError;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, RequestRejectedException requestRejectedException) throws IOException {
        logger.debug((Object)LogMessage.format((String)"Rejecting request due to: %s", (Object)requestRejectedException.getMessage()), (Throwable)requestRejectedException);
        response.sendError(this.httpError);
    }
}

