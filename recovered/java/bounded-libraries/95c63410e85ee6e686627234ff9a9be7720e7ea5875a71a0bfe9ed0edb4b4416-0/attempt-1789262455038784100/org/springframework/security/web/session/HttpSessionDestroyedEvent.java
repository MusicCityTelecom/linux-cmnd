/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpSession
 *  org.springframework.security.core.context.SecurityContext
 *  org.springframework.security.core.session.SessionDestroyedEvent
 */
package org.springframework.security.web.session;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.session.SessionDestroyedEvent;

public class HttpSessionDestroyedEvent
extends SessionDestroyedEvent {
    public HttpSessionDestroyedEvent(HttpSession session) {
        super((Object)session);
    }

    public HttpSession getSession() {
        return (HttpSession)this.getSource();
    }

    public List<SecurityContext> getSecurityContexts() {
        HttpSession session = this.getSession();
        Enumeration attributes = session.getAttributeNames();
        ArrayList<SecurityContext> contexts = new ArrayList<SecurityContext>();
        while (attributes.hasMoreElements()) {
            String attributeName = (String)attributes.nextElement();
            Object attributeValue = session.getAttribute(attributeName);
            if (!(attributeValue instanceof SecurityContext)) continue;
            contexts.add((SecurityContext)attributeValue);
        }
        return contexts;
    }

    public String getId() {
        return this.getSession().getId();
    }
}

