/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  javax.servlet.http.HttpSession
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.context.ApplicationEvent
 *  org.springframework.context.ApplicationEventPublisher
 *  org.springframework.context.ApplicationEventPublisherAware
 *  org.springframework.core.log.LogMessage
 *  org.springframework.security.core.Authentication
 *  org.springframework.util.Assert
 *  org.springframework.web.util.WebUtils
 */
package org.springframework.security.web.authentication.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.core.log.LogMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionFixationProtectionEvent;
import org.springframework.util.Assert;
import org.springframework.web.util.WebUtils;

public abstract class AbstractSessionFixationProtectionStrategy
implements SessionAuthenticationStrategy,
ApplicationEventPublisherAware {
    protected final Log logger = LogFactory.getLog(this.getClass());
    private ApplicationEventPublisher applicationEventPublisher = new NullEventPublisher();
    private boolean alwaysCreateSession;

    AbstractSessionFixationProtectionStrategy() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void onAuthentication(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        boolean hadSessionAlready;
        boolean bl = hadSessionAlready = request.getSession(false) != null;
        if (!hadSessionAlready && !this.alwaysCreateSession) {
            return;
        }
        HttpSession session = request.getSession();
        if (hadSessionAlready && request.isRequestedSessionIdValid()) {
            String newSessionId;
            String originalSessionId;
            Object mutex;
            Object object = mutex = WebUtils.getSessionMutex((HttpSession)session);
            synchronized (object) {
                originalSessionId = session.getId();
                session = this.applySessionFixation(request);
                newSessionId = session.getId();
            }
            if (originalSessionId.equals(newSessionId)) {
                this.logger.warn((Object)"Your servlet container did not change the session ID when a new session was created. You will not be adequately protected against session-fixation attacks");
            } else if (this.logger.isDebugEnabled()) {
                this.logger.debug((Object)LogMessage.format((String)"Changed session id from %s", (Object)originalSessionId));
            }
            this.onSessionChange(originalSessionId, session, authentication);
        }
    }

    abstract HttpSession applySessionFixation(HttpServletRequest var1);

    protected void onSessionChange(String originalSessionId, HttpSession newSession, Authentication auth) {
        this.applicationEventPublisher.publishEvent((ApplicationEvent)new SessionFixationProtectionEvent(auth, originalSessionId, newSession.getId()));
    }

    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        Assert.notNull((Object)applicationEventPublisher, (String)"applicationEventPublisher cannot be null");
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void setAlwaysCreateSession(boolean alwaysCreateSession) {
        this.alwaysCreateSession = alwaysCreateSession;
    }

    protected static final class NullEventPublisher
    implements ApplicationEventPublisher {
        protected NullEventPublisher() {
        }

        public void publishEvent(ApplicationEvent event) {
        }

        public void publishEvent(Object event) {
        }
    }
}

