/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletContext
 *  javax.servlet.http.HttpSession
 *  javax.servlet.http.HttpSessionEvent
 *  javax.servlet.http.HttpSessionIdListener
 *  javax.servlet.http.HttpSessionListener
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationEvent
 *  org.springframework.core.log.LogMessage
 */
package org.springframework.security.web.session;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionIdListener;
import javax.servlet.http.HttpSessionListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.core.log.LogMessage;
import org.springframework.security.web.context.support.SecurityWebApplicationContextUtils;
import org.springframework.security.web.session.HttpSessionCreatedEvent;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;
import org.springframework.security.web.session.HttpSessionIdChangedEvent;

public class HttpSessionEventPublisher
implements HttpSessionListener,
HttpSessionIdListener {
    private static final String LOGGER_NAME = HttpSessionEventPublisher.class.getName();

    ApplicationContext getContext(ServletContext servletContext) {
        return SecurityWebApplicationContextUtils.findRequiredWebApplicationContext(servletContext);
    }

    public void sessionCreated(HttpSessionEvent event) {
        this.extracted(event.getSession(), (ApplicationEvent)new HttpSessionCreatedEvent(event.getSession()));
    }

    public void sessionDestroyed(HttpSessionEvent event) {
        this.extracted(event.getSession(), (ApplicationEvent)new HttpSessionDestroyedEvent(event.getSession()));
    }

    public void sessionIdChanged(HttpSessionEvent event, String oldSessionId) {
        this.extracted(event.getSession(), (ApplicationEvent)new HttpSessionIdChangedEvent(event.getSession(), oldSessionId));
    }

    private void extracted(HttpSession session, ApplicationEvent e) {
        Log log = LogFactory.getLog((String)LOGGER_NAME);
        log.debug((Object)LogMessage.format((String)"Publishing event: %s", (Object)e));
        this.getContext(session.getServletContext()).publishEvent(e);
    }
}

