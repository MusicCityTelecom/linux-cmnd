/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.ApplicationEvent
 *  org.springframework.context.event.SmartApplicationListener
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.ObjectUtils
 */
package org.springframework.messaging.simp.user;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.user.SimpSession;
import org.springframework.messaging.simp.user.SimpSubscription;
import org.springframework.messaging.simp.user.SimpSubscriptionMatcher;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

public class MultiServerUserRegistry
implements SimpUserRegistry,
SmartApplicationListener {
    private final String id;
    private final SimpUserRegistry localRegistry;
    private final Map<String, UserRegistrySnapshot> remoteRegistries = new ConcurrentHashMap<String, UserRegistrySnapshot>();
    private final boolean delegateApplicationEvents;
    private final SessionLookup sessionLookup = new SessionLookup();

    public MultiServerUserRegistry(SimpUserRegistry localRegistry) {
        Assert.notNull((Object)localRegistry, (String)"'localRegistry' is required");
        this.id = MultiServerUserRegistry.generateId();
        this.localRegistry = localRegistry;
        this.delegateApplicationEvents = this.localRegistry instanceof SmartApplicationListener;
    }

    private static String generateId() {
        String host;
        try {
            host = InetAddress.getLocalHost().getHostAddress();
        }
        catch (UnknownHostException ex) {
            host = "unknown";
        }
        return host + '-' + UUID.randomUUID();
    }

    public int getOrder() {
        return this.delegateApplicationEvents ? ((SmartApplicationListener)this.localRegistry).getOrder() : Integer.MAX_VALUE;
    }

    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return this.delegateApplicationEvents && ((SmartApplicationListener)this.localRegistry).supportsEventType(eventType);
    }

    public boolean supportsSourceType(@Nullable Class<?> sourceType) {
        return this.delegateApplicationEvents && ((SmartApplicationListener)this.localRegistry).supportsSourceType(sourceType);
    }

    public void onApplicationEvent(ApplicationEvent event) {
        if (this.delegateApplicationEvents) {
            ((SmartApplicationListener)this.localRegistry).onApplicationEvent(event);
        }
    }

    @Override
    @Nullable
    public SimpUser getUser(String userName) {
        for (UserRegistrySnapshot registry : this.remoteRegistries.values()) {
            SimpUser user = registry.getUserMap().get(userName);
            if (user == null) continue;
            return user;
        }
        return this.localRegistry.getUser(userName);
    }

    @Override
    public Set<SimpUser> getUsers() {
        HashSet<SimpUser> result = new HashSet<SimpUser>();
        for (UserRegistrySnapshot registry : this.remoteRegistries.values()) {
            result.addAll(registry.getUserMap().values());
        }
        result.addAll(this.localRegistry.getUsers());
        return result;
    }

    @Override
    public int getUserCount() {
        int userCount = 0;
        for (UserRegistrySnapshot registry : this.remoteRegistries.values()) {
            userCount += registry.getUserMap().size();
        }
        return userCount += this.localRegistry.getUserCount();
    }

    @Override
    public Set<SimpSubscription> findSubscriptions(SimpSubscriptionMatcher matcher) {
        HashSet<SimpSubscription> result = new HashSet<SimpSubscription>();
        for (UserRegistrySnapshot registry : this.remoteRegistries.values()) {
            result.addAll(registry.findSubscriptions(matcher));
        }
        result.addAll(this.localRegistry.findSubscriptions(matcher));
        return result;
    }

    Object getLocalRegistryDto() {
        return new UserRegistrySnapshot(this.id, this.localRegistry);
    }

    void addRemoteRegistryDto(Message<?> message, MessageConverter converter, long expirationPeriod) {
        UserRegistrySnapshot registry = (UserRegistrySnapshot)converter.fromMessage(message, UserRegistrySnapshot.class);
        if (registry != null && !registry.getId().equals(this.id)) {
            registry.init(expirationPeriod, this.sessionLookup);
            this.remoteRegistries.put(registry.getId(), registry);
        }
    }

    void purgeExpiredRegistries() {
        long now = System.currentTimeMillis();
        this.remoteRegistries.entrySet().removeIf(entry -> ((UserRegistrySnapshot)entry.getValue()).isExpired(now));
    }

    public String toString() {
        return "local=[" + this.localRegistry + "], remote=" + this.remoteRegistries;
    }

    private class SessionLookup {
        private SessionLookup() {
        }

        public Map<String, SimpSession> findSessions(String userName) {
            HashMap<String, SimpSession> map = new HashMap<String, SimpSession>(4);
            SimpUser user = MultiServerUserRegistry.this.localRegistry.getUser(userName);
            if (user != null) {
                for (SimpSession session : user.getSessions()) {
                    map.put(session.getId(), session);
                }
            }
            for (UserRegistrySnapshot registry : MultiServerUserRegistry.this.remoteRegistries.values()) {
                TransferSimpUser transferUser = registry.getUserMap().get(userName);
                if (transferUser == null) continue;
                transferUser.addSessions(map);
            }
            return map;
        }
    }

    private static class TransferSimpSubscription
    implements SimpSubscription {
        private String id;
        private TransferSimpSession session;
        private String destination;

        public TransferSimpSubscription() {
            this.id = "";
            this.session = new TransferSimpSession();
            this.destination = "";
        }

        public TransferSimpSubscription(SimpSubscription subscription) {
            this.id = subscription.getId();
            this.session = new TransferSimpSession();
            this.destination = subscription.getDestination();
        }

        public void setId(String id) {
            this.id = id;
        }

        @Override
        public String getId() {
            return this.id;
        }

        public void setSession(TransferSimpSession session) {
            this.session = session;
        }

        @Override
        public TransferSimpSession getSession() {
            return this.session;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        @Override
        public String getDestination() {
            return this.destination;
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof SimpSubscription)) {
                return false;
            }
            SimpSubscription otherSubscription = (SimpSubscription)other;
            return this.getId().equals(otherSubscription.getId()) && ObjectUtils.nullSafeEquals((Object)this.getSession(), (Object)otherSubscription.getSession());
        }

        public int hashCode() {
            return this.getId().hashCode() * 31 + ObjectUtils.nullSafeHashCode((Object)this.getSession());
        }

        public String toString() {
            return "destination=" + this.destination;
        }
    }

    private static class TransferSimpSession
    implements SimpSession {
        private String id;
        private TransferSimpUser user;
        private final Set<TransferSimpSubscription> subscriptions;

        public TransferSimpSession() {
            this.id = "";
            this.user = new TransferSimpUser();
            this.subscriptions = new HashSet<TransferSimpSubscription>(4);
        }

        public TransferSimpSession(SimpSession session) {
            this.id = session.getId();
            this.user = new TransferSimpUser();
            Set<SimpSubscription> subscriptions = session.getSubscriptions();
            this.subscriptions = new HashSet<TransferSimpSubscription>(subscriptions.size());
            for (SimpSubscription subscription : subscriptions) {
                this.subscriptions.add(new TransferSimpSubscription(subscription));
            }
        }

        public void setId(String id) {
            this.id = id;
        }

        @Override
        public String getId() {
            return this.id;
        }

        public void setUser(TransferSimpUser user) {
            this.user = user;
        }

        @Override
        public TransferSimpUser getUser() {
            return this.user;
        }

        public void setSubscriptions(Set<TransferSimpSubscription> subscriptions) {
            this.subscriptions.addAll(subscriptions);
        }

        @Override
        public Set<SimpSubscription> getSubscriptions() {
            return new HashSet<SimpSubscription>(this.subscriptions);
        }

        private void afterDeserialization() {
            for (TransferSimpSubscription subscription : this.subscriptions) {
                subscription.setSession(this);
            }
        }

        public boolean equals(@Nullable Object other) {
            return this == other || other instanceof SimpSession && this.getId().equals(((SimpSession)other).getId());
        }

        public int hashCode() {
            return this.getId().hashCode();
        }

        public String toString() {
            return "id=" + this.id + ", subscriptions=" + this.subscriptions;
        }
    }

    private static class TransferSimpUser
    implements SimpUser {
        private String name = "";
        private Set<TransferSimpSession> sessions;
        @Nullable
        private SessionLookup sessionLookup;

        public TransferSimpUser() {
            this.sessions = new HashSet<TransferSimpSession>(1);
        }

        public TransferSimpUser(SimpUser user) {
            this.name = user.getName();
            Set<SimpSession> sessions = user.getSessions();
            this.sessions = new HashSet<TransferSimpSession>(sessions.size());
            for (SimpSession session : sessions) {
                this.sessions.add(new TransferSimpSession(session));
            }
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        @Nullable
        public Principal getPrincipal() {
            return null;
        }

        @Override
        public boolean hasSessions() {
            if (this.sessionLookup != null) {
                return !this.sessionLookup.findSessions(this.getName()).isEmpty();
            }
            return !this.sessions.isEmpty();
        }

        @Override
        @Nullable
        public SimpSession getSession(String sessionId) {
            if (this.sessionLookup != null) {
                return this.sessionLookup.findSessions(this.getName()).get(sessionId);
            }
            for (TransferSimpSession session : this.sessions) {
                if (!session.getId().equals(sessionId)) continue;
                return session;
            }
            return null;
        }

        public void setSessions(Set<TransferSimpSession> sessions) {
            this.sessions.addAll(sessions);
        }

        @Override
        public Set<SimpSession> getSessions() {
            if (this.sessionLookup != null) {
                Map<String, SimpSession> sessions = this.sessionLookup.findSessions(this.getName());
                return new HashSet<SimpSession>(sessions.values());
            }
            return new HashSet<SimpSession>(this.sessions);
        }

        private void afterDeserialization(SessionLookup sessionLookup) {
            this.sessionLookup = sessionLookup;
            for (TransferSimpSession session : this.sessions) {
                session.setUser(this);
                session.afterDeserialization();
            }
        }

        private void addSessions(Map<String, SimpSession> map) {
            for (SimpSession simpSession : this.sessions) {
                map.put(simpSession.getId(), simpSession);
            }
        }

        public boolean equals(@Nullable Object other) {
            return this == other || other instanceof SimpUser && this.name.equals(((SimpUser)other).getName());
        }

        public int hashCode() {
            return this.name.hashCode();
        }

        public String toString() {
            return "name=" + this.name + ", sessions=" + this.sessions;
        }
    }

    private static class UserRegistrySnapshot {
        private String id = "";
        private Map<String, TransferSimpUser> users = Collections.emptyMap();
        private long expirationTime;

        public UserRegistrySnapshot() {
        }

        public UserRegistrySnapshot(String id, SimpUserRegistry registry) {
            this.id = id;
            Set<SimpUser> users = registry.getUsers();
            this.users = CollectionUtils.newHashMap((int)users.size());
            for (SimpUser user : users) {
                this.users.put(user.getName(), new TransferSimpUser(user));
            }
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }

        public void setUserMap(Map<String, TransferSimpUser> users) {
            this.users = users;
        }

        public Map<String, TransferSimpUser> getUserMap() {
            return this.users;
        }

        public boolean isExpired(long now) {
            return now > this.expirationTime;
        }

        public void init(long expirationPeriod, SessionLookup sessionLookup) {
            this.expirationTime = System.currentTimeMillis() + expirationPeriod;
            for (TransferSimpUser user : this.users.values()) {
                user.afterDeserialization(sessionLookup);
            }
        }

        public Set<SimpSubscription> findSubscriptions(SimpSubscriptionMatcher matcher) {
            HashSet<SimpSubscription> result = new HashSet<SimpSubscription>();
            for (TransferSimpUser user : this.users.values()) {
                for (TransferSimpSession session : user.sessions) {
                    for (SimpSubscription subscription : session.subscriptions) {
                        if (!matcher.match(subscription)) continue;
                        result.add(subscription);
                    }
                }
            }
            return result;
        }

        public String toString() {
            return "id=" + this.id + ", users=" + this.users;
        }
    }
}

