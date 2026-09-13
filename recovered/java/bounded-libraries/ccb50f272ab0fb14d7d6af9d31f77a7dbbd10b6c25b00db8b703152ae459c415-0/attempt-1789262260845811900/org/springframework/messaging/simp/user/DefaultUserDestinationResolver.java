/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 *  org.springframework.util.PathMatcher
 *  org.springframework.util.StringUtils
 */
package org.springframework.messaging.simp.user;

import java.security.Principal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpLogging;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.user.SimpSession;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.messaging.simp.user.UserDestinationResolver;
import org.springframework.messaging.simp.user.UserDestinationResult;
import org.springframework.util.Assert;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;

public class DefaultUserDestinationResolver
implements UserDestinationResolver {
    private static final Log logger = SimpLogging.forLogName(DefaultUserDestinationResolver.class);
    private final SimpUserRegistry userRegistry;
    private String prefix = "/user/";
    private boolean removeLeadingSlash = false;

    public DefaultUserDestinationResolver(SimpUserRegistry userRegistry) {
        Assert.notNull((Object)userRegistry, (String)"SimpUserRegistry must not be null");
        this.userRegistry = userRegistry;
    }

    public SimpUserRegistry getSimpUserRegistry() {
        return this.userRegistry;
    }

    public void setUserDestinationPrefix(String prefix) {
        Assert.hasText((String)prefix, (String)"Prefix must not be empty");
        this.prefix = prefix.endsWith("/") ? prefix : prefix + "/";
    }

    public String getDestinationPrefix() {
        return this.prefix;
    }

    public void setRemoveLeadingSlash(boolean remove) {
        this.removeLeadingSlash = remove;
    }

    public boolean isRemoveLeadingSlash() {
        return this.removeLeadingSlash;
    }

    @Deprecated
    public void setPathMatcher(@Nullable PathMatcher pathMatcher) {
    }

    @Override
    @Nullable
    public UserDestinationResult resolveDestination(Message<?> message) {
        ParseResult parseResult = this.parse(message);
        if (parseResult == null) {
            return null;
        }
        String user = parseResult.getUser();
        String sourceDestination = parseResult.getSourceDestination();
        HashSet<String> targetSet = new HashSet<String>();
        for (String sessionId : parseResult.getSessionIds()) {
            String actualDestination = parseResult.getActualDestination();
            String targetDestination = this.getTargetDestination(sourceDestination, actualDestination, sessionId, user);
            if (targetDestination == null) continue;
            targetSet.add(targetDestination);
        }
        String subscribeDestination = parseResult.getSubscribeDestination();
        return new UserDestinationResult(sourceDestination, targetSet, subscribeDestination, user);
    }

    @Nullable
    private ParseResult parse(Message<?> message) {
        MessageHeaders headers = message.getHeaders();
        String sourceDestination = SimpMessageHeaderAccessor.getDestination(headers);
        if (sourceDestination == null || !this.checkDestination(sourceDestination, this.prefix)) {
            return null;
        }
        SimpMessageType messageType = SimpMessageHeaderAccessor.getMessageType(headers);
        if (messageType != null) {
            switch (messageType) {
                case SUBSCRIBE: 
                case UNSUBSCRIBE: {
                    return this.parseSubscriptionMessage(message, sourceDestination);
                }
                case MESSAGE: {
                    return this.parseMessage(headers, sourceDestination);
                }
            }
        }
        return null;
    }

    @Nullable
    private ParseResult parseSubscriptionMessage(Message<?> message, String sourceDestination) {
        Principal principal;
        MessageHeaders headers = message.getHeaders();
        String sessionId = SimpMessageHeaderAccessor.getSessionId(headers);
        if (sessionId == null) {
            logger.error((Object)("No session id. Ignoring " + message));
            return null;
        }
        int prefixEnd = this.prefix.length() - 1;
        String actualDestination = sourceDestination.substring(prefixEnd);
        if (this.isRemoveLeadingSlash()) {
            actualDestination = actualDestination.substring(1);
        }
        String user = (principal = SimpMessageHeaderAccessor.getUser(headers)) != null ? principal.getName() : null;
        Assert.isTrue((user == null || !user.contains("%2F") ? 1 : 0) != 0, (String)("Invalid sequence \"%2F\" in user name: " + user));
        Set<String> sessionIds = Collections.singleton(sessionId);
        return new ParseResult(sourceDestination, actualDestination, sourceDestination, sessionIds, user);
    }

    private ParseResult parseMessage(MessageHeaders headers, String sourceDest) {
        Set<String> sessionIds;
        int prefixEnd = this.prefix.length();
        int userEnd = sourceDest.indexOf(47, prefixEnd);
        Assert.isTrue((userEnd > 0 ? 1 : 0) != 0, (String)"Expected destination pattern \"/user/{userId}/**\"");
        String actualDest = sourceDest.substring(userEnd);
        String subscribeDest = this.prefix.substring(0, prefixEnd - 1) + actualDest;
        String userName = sourceDest.substring(prefixEnd, userEnd);
        userName = StringUtils.replace((String)userName, (String)"%2F", (String)"/");
        String sessionId = SimpMessageHeaderAccessor.getSessionId(headers);
        if (userName.equals(sessionId)) {
            userName = null;
            sessionIds = Collections.singleton(sessionId);
        } else {
            sessionIds = this.getSessionIdsByUser(userName, sessionId);
        }
        if (this.isRemoveLeadingSlash()) {
            actualDest = actualDest.substring(1);
        }
        return new ParseResult(sourceDest, actualDest, subscribeDest, sessionIds, userName);
    }

    private Set<String> getSessionIdsByUser(String userName, @Nullable String sessionId) {
        Set<String> sessionIds;
        SimpUser user = this.userRegistry.getUser(userName);
        if (user != null) {
            if (sessionId != null && user.getSession(sessionId) != null) {
                sessionIds = Collections.singleton(sessionId);
            } else {
                Set<SimpSession> sessions = user.getSessions();
                sessionIds = new HashSet<String>(sessions.size());
                for (SimpSession session : sessions) {
                    sessionIds.add(session.getId());
                }
            }
        } else {
            sessionIds = Collections.emptySet();
        }
        return sessionIds;
    }

    protected boolean checkDestination(String destination, String requiredPrefix) {
        return destination.startsWith(requiredPrefix);
    }

    @Nullable
    protected String getTargetDestination(String sourceDestination, String actualDestination, String sessionId, @Nullable String user) {
        return actualDestination + "-user" + sessionId;
    }

    public String toString() {
        return "DefaultUserDestinationResolver[prefix=" + this.prefix + "]";
    }

    private static class ParseResult {
        private final String sourceDestination;
        private final String actualDestination;
        private final String subscribeDestination;
        private final Set<String> sessionIds;
        @Nullable
        private final String user;

        public ParseResult(String sourceDest, String actualDest, String subscribeDest, Set<String> sessionIds, @Nullable String user) {
            this.sourceDestination = sourceDest;
            this.actualDestination = actualDest;
            this.subscribeDestination = subscribeDest;
            this.sessionIds = sessionIds;
            this.user = user;
        }

        public String getSourceDestination() {
            return this.sourceDestination;
        }

        public String getActualDestination() {
            return this.actualDestination;
        }

        public String getSubscribeDestination() {
            return this.subscribeDestination;
        }

        public Set<String> getSessionIds() {
            return this.sessionIds;
        }

        @Nullable
        public String getUser() {
            return this.user;
        }
    }
}

