/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  javax.servlet.http.HttpSession
 *  org.springframework.context.MessageSource
 *  org.springframework.context.MessageSourceAware
 *  org.springframework.context.support.MessageSourceAccessor
 *  org.springframework.security.core.Authentication
 *  org.springframework.security.core.SpringSecurityMessageSource
 *  org.springframework.security.core.session.SessionInformation
 *  org.springframework.security.core.session.SessionRegistry
 *  org.springframework.util.Assert
 */
package org.springframework.security.web.authentication.session;

import java.util.Comparator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.util.Assert;

public class ConcurrentSessionControlAuthenticationStrategy
implements MessageSourceAware,
SessionAuthenticationStrategy {
    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    private final SessionRegistry sessionRegistry;
    private boolean exceptionIfMaximumExceeded = false;
    private int maximumSessions = 1;

    public ConcurrentSessionControlAuthenticationStrategy(SessionRegistry sessionRegistry) {
        Assert.notNull((Object)sessionRegistry, (String)"The sessionRegistry cannot be null");
        this.sessionRegistry = sessionRegistry;
    }

    @Override
    public void onAuthentication(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session;
        int allowedSessions = this.getMaximumSessionsForThisUser(authentication);
        if (allowedSessions == -1) {
            return;
        }
        List sessions = this.sessionRegistry.getAllSessions(authentication.getPrincipal(), false);
        int sessionCount = sessions.size();
        if (sessionCount < allowedSessions) {
            return;
        }
        if (sessionCount == allowedSessions && (session = request.getSession(false)) != null) {
            for (SessionInformation si : sessions) {
                if (!si.getSessionId().equals(session.getId())) continue;
                return;
            }
        }
        this.allowableSessionsExceeded(sessions, allowedSessions, this.sessionRegistry);
    }

    protected int getMaximumSessionsForThisUser(Authentication authentication) {
        return this.maximumSessions;
    }

    protected void allowableSessionsExceeded(List<SessionInformation> sessions, int allowableSessions, SessionRegistry registry) throws SessionAuthenticationException {
        if (this.exceptionIfMaximumExceeded || sessions == null) {
            throw new SessionAuthenticationException(this.messages.getMessage("ConcurrentSessionControlAuthenticationStrategy.exceededAllowed", new Object[]{allowableSessions}, "Maximum sessions of {0} for this principal exceeded"));
        }
        sessions.sort(Comparator.comparing(SessionInformation::getLastRequest));
        int maximumSessionsExceededBy = sessions.size() - allowableSessions + 1;
        List<SessionInformation> sessionsToBeExpired = sessions.subList(0, maximumSessionsExceededBy);
        for (SessionInformation session : sessionsToBeExpired) {
            session.expireNow();
        }
    }

    public void setExceptionIfMaximumExceeded(boolean exceptionIfMaximumExceeded) {
        this.exceptionIfMaximumExceeded = exceptionIfMaximumExceeded;
    }

    public void setMaximumSessions(int maximumSessions) {
        Assert.isTrue((maximumSessions != 0 ? 1 : 0) != 0, (String)"MaximumLogins must be either -1 to allow unlimited logins, or a positive integer to specify a maximum");
        this.maximumSessions = maximumSessions;
    }

    public void setMessageSource(MessageSource messageSource) {
        Assert.notNull((Object)messageSource, (String)"messageSource cannot be null");
        this.messages = new MessageSourceAccessor(messageSource);
    }
}

