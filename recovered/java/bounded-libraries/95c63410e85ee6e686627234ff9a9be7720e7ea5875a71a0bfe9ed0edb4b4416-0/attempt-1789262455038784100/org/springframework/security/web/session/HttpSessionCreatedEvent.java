/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpSession
 *  org.springframework.security.core.session.SessionCreationEvent
 */
package org.springframework.security.web.session;

import javax.servlet.http.HttpSession;
import org.springframework.security.core.session.SessionCreationEvent;

public class HttpSessionCreatedEvent
extends SessionCreationEvent {
    public HttpSessionCreatedEvent(HttpSession session) {
        super((Object)session);
    }

    public HttpSession getSession() {
        return (HttpSession)this.getSource();
    }
}

