/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpSession
 *  org.springframework.security.core.session.SessionIdChangedEvent
 */
package org.springframework.security.web.session;

import javax.servlet.http.HttpSession;
import org.springframework.security.core.session.SessionIdChangedEvent;

public class HttpSessionIdChangedEvent
extends SessionIdChangedEvent {
    private final String oldSessionId;
    private final String newSessionId;

    public HttpSessionIdChangedEvent(HttpSession session, String oldSessionId) {
        super((Object)session);
        this.oldSessionId = oldSessionId;
        this.newSessionId = session.getId();
    }

    public String getOldSessionId() {
        return this.oldSessionId;
    }

    public String getNewSessionId() {
        return this.newSessionId;
    }
}

