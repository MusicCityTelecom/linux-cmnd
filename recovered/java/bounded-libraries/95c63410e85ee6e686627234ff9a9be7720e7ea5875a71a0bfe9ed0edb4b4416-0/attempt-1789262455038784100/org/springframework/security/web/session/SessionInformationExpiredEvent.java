/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.context.ApplicationEvent
 *  org.springframework.security.core.session.SessionInformation
 *  org.springframework.util.Assert
 */
package org.springframework.security.web.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationEvent;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.util.Assert;

public final class SessionInformationExpiredEvent
extends ApplicationEvent {
    private final HttpServletRequest request;
    private final HttpServletResponse response;

    public SessionInformationExpiredEvent(SessionInformation sessionInformation, HttpServletRequest request, HttpServletResponse response) {
        super((Object)sessionInformation);
        Assert.notNull((Object)request, (String)"request cannot be null");
        Assert.notNull((Object)response, (String)"response cannot be null");
        this.request = request;
        this.response = response;
    }

    public HttpServletRequest getRequest() {
        return this.request;
    }

    public HttpServletResponse getResponse() {
        return this.response;
    }

    public SessionInformation getSessionInformation() {
        return (SessionInformation)this.getSource();
    }
}

