/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.util.Assert
 */
package org.springframework.security.web.firewall;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.security.web.firewall.RequestRejectedHandler;
import org.springframework.util.Assert;

public final class CompositeRequestRejectedHandler
implements RequestRejectedHandler {
    private final List<RequestRejectedHandler> requestRejectedhandlers;

    public CompositeRequestRejectedHandler(RequestRejectedHandler ... requestRejectedhandlers) {
        Assert.notEmpty((Object[])requestRejectedhandlers, (String)"requestRejectedhandlers cannot be empty");
        this.requestRejectedhandlers = Arrays.asList(requestRejectedhandlers);
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, RequestRejectedException requestRejectedException) throws IOException, ServletException {
        for (RequestRejectedHandler requestRejectedhandler : this.requestRejectedhandlers) {
            requestRejectedhandler.handle(request, response, requestRejectedException);
        }
    }
}

