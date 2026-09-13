/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.expression.EvaluationContext
 *  org.springframework.expression.Expression
 *  org.springframework.expression.ExpressionParser
 *  org.springframework.expression.PropertyAccessor
 *  org.springframework.expression.TypedValue
 *  org.springframework.expression.spel.SpelEvaluationException
 *  org.springframework.expression.spel.standard.SpelExpressionParser
 *  org.springframework.expression.spel.support.SimpleEvaluationContext
 *  org.springframework.lang.Nullable
 *  org.springframework.util.AntPathMatcher
 *  org.springframework.util.Assert
 *  org.springframework.util.LinkedMultiValueMap
 *  org.springframework.util.MultiValueMap
 *  org.springframework.util.PathMatcher
 *  org.springframework.util.StringUtils
 */
package org.springframework.messaging.simp.broker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.PropertyAccessor;
import org.springframework.expression.TypedValue;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.broker.AbstractSubscriptionRegistry;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;

public class DefaultSubscriptionRegistry
extends AbstractSubscriptionRegistry {
    public static final int DEFAULT_CACHE_LIMIT = 1024;
    private static final EvaluationContext messageEvalContext = SimpleEvaluationContext.forPropertyAccessors((PropertyAccessor[])new PropertyAccessor[]{new SimpMessageHeaderPropertyAccessor()}).build();
    private PathMatcher pathMatcher = new AntPathMatcher();
    private int cacheLimit = 1024;
    @Nullable
    private String selectorHeaderName = "selector";
    private volatile boolean selectorHeaderInUse;
    private final ExpressionParser expressionParser = new SpelExpressionParser();
    private final DestinationCache destinationCache = new DestinationCache();
    private final SessionRegistry sessionRegistry = new SessionRegistry();

    public void setPathMatcher(PathMatcher pathMatcher) {
        this.pathMatcher = pathMatcher;
    }

    public PathMatcher getPathMatcher() {
        return this.pathMatcher;
    }

    public void setCacheLimit(int cacheLimit) {
        this.cacheLimit = cacheLimit;
        this.destinationCache.ensureCacheLimit();
    }

    public int getCacheLimit() {
        return this.cacheLimit;
    }

    public void setSelectorHeaderName(@Nullable String selectorHeaderName) {
        this.selectorHeaderName = StringUtils.hasText((String)selectorHeaderName) ? selectorHeaderName : null;
    }

    @Nullable
    public String getSelectorHeaderName() {
        return this.selectorHeaderName;
    }

    @Override
    protected void addSubscriptionInternal(String sessionId, String subscriptionId, String destination, Message<?> message) {
        boolean isPattern = this.pathMatcher.isPattern(destination);
        Expression expression = this.getSelectorExpression(message.getHeaders());
        Subscription subscription = new Subscription(subscriptionId, destination, isPattern, expression);
        this.sessionRegistry.addSubscription(sessionId, subscription);
        this.destinationCache.updateAfterNewSubscription(sessionId, subscription);
    }

    @Nullable
    private Expression getSelectorExpression(MessageHeaders headers) {
        Expression expression;
        block5: {
            if (this.getSelectorHeaderName() == null) {
                return null;
            }
            String selector = SimpMessageHeaderAccessor.getFirstNativeHeader(this.getSelectorHeaderName(), headers);
            if (selector == null) {
                return null;
            }
            expression = null;
            try {
                expression = this.expressionParser.parseExpression(selector);
                this.selectorHeaderInUse = true;
                if (this.logger.isTraceEnabled()) {
                    this.logger.trace((Object)("Subscription selector: [" + selector + "]"));
                }
            }
            catch (Throwable ex) {
                if (!this.logger.isDebugEnabled()) break block5;
                this.logger.debug((Object)("Failed to parse selector: " + selector), ex);
            }
        }
        return expression;
    }

    @Override
    protected void removeSubscriptionInternal(String sessionId, String subscriptionId, Message<?> message) {
        Subscription subscription;
        SessionInfo info = this.sessionRegistry.getSession(sessionId);
        if (info != null && (subscription = info.removeSubscription(subscriptionId)) != null) {
            this.destinationCache.updateAfterRemovedSubscription(sessionId, subscription);
        }
    }

    @Override
    public void unregisterAllSubscriptions(String sessionId) {
        SessionInfo info = this.sessionRegistry.removeSubscriptions(sessionId);
        if (info != null) {
            this.destinationCache.updateAfterRemovedSession(sessionId, info);
        }
    }

    @Override
    protected MultiValueMap<String, String> findSubscriptionsInternal(String destination, Message<?> message) {
        LinkedMultiValueMap<String, String> allMatches = this.destinationCache.getSubscriptions(destination);
        if (!this.selectorHeaderInUse) {
            return allMatches;
        }
        LinkedMultiValueMap result = new LinkedMultiValueMap(allMatches.size());
        allMatches.forEach((arg_0, arg_1) -> this.lambda$findSubscriptionsInternal$0(message, (MultiValueMap)result, arg_0, arg_1));
        return result;
    }

    private boolean evaluateExpression(@Nullable Expression expression, Message<?> message) {
        if (expression == null) {
            return true;
        }
        try {
            Boolean result = (Boolean)expression.getValue(messageEvalContext, message, Boolean.class);
            if (Boolean.TRUE.equals(result)) {
                return true;
            }
        }
        catch (SpelEvaluationException ex) {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug((Object)("Failed to evaluate selector: " + ex.getMessage()));
            }
        }
        catch (Throwable ex) {
            this.logger.debug((Object)"Failed to evaluate selector", ex);
        }
        return false;
    }

    private /* synthetic */ void lambda$findSubscriptionsInternal$0(Message message, MultiValueMap result, String sessionId, List subscriptionIds) {
        SessionInfo info = this.sessionRegistry.getSession(sessionId);
        if (info != null) {
            for (String subscriptionId : subscriptionIds) {
                Subscription subscription = info.getSubscription(subscriptionId);
                if (subscription == null || !this.evaluateExpression(subscription.getSelector(), message)) continue;
                result.add((Object)sessionId, (Object)subscription.getId());
            }
        }
    }

    private static class SimpMessageHeaderPropertyAccessor
    implements PropertyAccessor {
        private SimpMessageHeaderPropertyAccessor() {
        }

        public Class<?>[] getSpecificTargetClasses() {
            return new Class[]{Message.class, MessageHeaders.class};
        }

        public boolean canRead(EvaluationContext context, @Nullable Object target, String name) {
            return true;
        }

        public TypedValue read(EvaluationContext context, @Nullable Object target, String name) {
            Object value;
            if (target instanceof Message) {
                value = name.equals("headers") ? ((Message)target).getHeaders() : null;
            } else if (target instanceof MessageHeaders) {
                MessageHeaders headers = (MessageHeaders)target;
                SimpMessageHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(headers, SimpMessageHeaderAccessor.class);
                Assert.state((accessor != null ? 1 : 0) != 0, (String)"No SimpMessageHeaderAccessor");
                if ("destination".equalsIgnoreCase(name)) {
                    value = accessor.getDestination();
                } else {
                    value = accessor.getFirstNativeHeader(name);
                    if (value == null) {
                        value = headers.get(name);
                    }
                }
            } else {
                throw new IllegalStateException("Expected Message or MessageHeaders.");
            }
            return new TypedValue(value);
        }

        public boolean canWrite(EvaluationContext context, @Nullable Object target, String name) {
            return false;
        }

        public void write(EvaluationContext context, @Nullable Object target, String name, @Nullable Object value) {
        }
    }

    private static final class Subscription {
        private final String id;
        private final String destination;
        private final boolean isPattern;
        @Nullable
        private final Expression selector;

        public Subscription(String id, String destination, boolean isPattern, @Nullable Expression selector) {
            Assert.notNull((Object)id, (String)"Subscription id must not be null");
            Assert.notNull((Object)destination, (String)"Subscription destination must not be null");
            this.id = id;
            this.selector = selector;
            this.destination = destination;
            this.isPattern = isPattern;
        }

        public String getId() {
            return this.id;
        }

        public String getDestination() {
            return this.destination;
        }

        public boolean isPattern() {
            return this.isPattern;
        }

        @Nullable
        public Expression getSelector() {
            return this.selector;
        }

        public boolean equals(@Nullable Object other) {
            return this == other || other instanceof Subscription && this.id.equals(((Subscription)other).id);
        }

        public int hashCode() {
            return this.id.hashCode();
        }

        public String toString() {
            return "subscription(id=" + this.id + ")";
        }
    }

    private static final class SessionInfo {
        private final Map<String, Subscription> subscriptionMap = new ConcurrentHashMap<String, Subscription>();

        private SessionInfo() {
        }

        public Collection<Subscription> getSubscriptions() {
            return this.subscriptionMap.values();
        }

        @Nullable
        public Subscription getSubscription(String subscriptionId) {
            return this.subscriptionMap.get(subscriptionId);
        }

        public void addSubscription(Subscription subscription) {
            this.subscriptionMap.putIfAbsent(subscription.getId(), subscription);
        }

        @Nullable
        public Subscription removeSubscription(String subscriptionId) {
            return this.subscriptionMap.remove(subscriptionId);
        }
    }

    private static final class SessionRegistry {
        private final ConcurrentMap<String, SessionInfo> sessions = new ConcurrentHashMap<String, SessionInfo>();

        private SessionRegistry() {
        }

        @Nullable
        public SessionInfo getSession(String sessionId) {
            return (SessionInfo)this.sessions.get(sessionId);
        }

        public void forEachSubscription(BiConsumer<String, Subscription> consumer) {
            this.sessions.forEach((sessionId, info) -> info.getSubscriptions().forEach(subscription -> consumer.accept((String)sessionId, (Subscription)subscription)));
        }

        public void addSubscription(String sessionId, Subscription subscription) {
            SessionInfo info = this.sessions.computeIfAbsent(sessionId, _sessionId -> new SessionInfo());
            info.addSubscription(subscription);
        }

        @Nullable
        public SessionInfo removeSubscriptions(String sessionId) {
            return (SessionInfo)this.sessions.remove(sessionId);
        }
    }

    private final class DestinationCache {
        private final Map<String, LinkedMultiValueMap<String, String>> destinationCache = new ConcurrentHashMap<String, LinkedMultiValueMap<String, String>>(1024);
        private final AtomicInteger cacheSize = new AtomicInteger();
        private final Queue<String> cacheEvictionPolicy = new ConcurrentLinkedQueue<String>();

        private DestinationCache() {
        }

        public LinkedMultiValueMap<String, String> getSubscriptions(String destination) {
            LinkedMultiValueMap sessionIdToSubscriptionIds = this.destinationCache.get(destination);
            if (sessionIdToSubscriptionIds == null) {
                sessionIdToSubscriptionIds = this.destinationCache.computeIfAbsent(destination, _destination -> {
                    LinkedMultiValueMap<String, String> matches = this.computeMatchingSubscriptions(destination);
                    this.cacheEvictionPolicy.add(destination);
                    this.cacheSize.incrementAndGet();
                    return matches;
                });
                this.ensureCacheLimit();
            }
            return sessionIdToSubscriptionIds;
        }

        private LinkedMultiValueMap<String, String> computeMatchingSubscriptions(String destination) {
            LinkedMultiValueMap sessionIdToSubscriptionIds = new LinkedMultiValueMap();
            DefaultSubscriptionRegistry.this.sessionRegistry.forEachSubscription((sessionId, subscription) -> {
                if (subscription.isPattern()) {
                    if (DefaultSubscriptionRegistry.this.pathMatcher.match(subscription.getDestination(), destination)) {
                        this.addMatchedSubscriptionId((LinkedMultiValueMap<String, String>)sessionIdToSubscriptionIds, (String)sessionId, subscription.getId());
                    }
                } else if (destination.equals(subscription.getDestination())) {
                    this.addMatchedSubscriptionId((LinkedMultiValueMap<String, String>)sessionIdToSubscriptionIds, (String)sessionId, subscription.getId());
                }
            });
            return sessionIdToSubscriptionIds;
        }

        private void addMatchedSubscriptionId(LinkedMultiValueMap<String, String> sessionIdToSubscriptionIds, String sessionId, String subscriptionId) {
            sessionIdToSubscriptionIds.compute((Object)sessionId, (_sessionId, subscriptionIds) -> {
                if (subscriptionIds == null) {
                    return Collections.singletonList(subscriptionId);
                }
                ArrayList<String> result = new ArrayList<String>(subscriptionIds.size() + 1);
                result.addAll((Collection<String>)subscriptionIds);
                result.add(subscriptionId);
                return result;
            });
        }

        private void ensureCacheLimit() {
            int size = this.cacheSize.get();
            if (size > DefaultSubscriptionRegistry.this.cacheLimit) {
                do {
                    if (!this.cacheSize.compareAndSet(size, size - 1)) continue;
                    String head = this.cacheEvictionPolicy.remove();
                    this.destinationCache.remove(head);
                } while ((size = this.cacheSize.get()) > DefaultSubscriptionRegistry.this.cacheLimit);
            }
        }

        public void updateAfterNewSubscription(String sessionId, Subscription subscription) {
            if (subscription.isPattern()) {
                for (String cachedDestination : this.destinationCache.keySet()) {
                    if (!DefaultSubscriptionRegistry.this.pathMatcher.match(subscription.getDestination(), cachedDestination)) continue;
                    this.addToDestination(cachedDestination, sessionId, subscription.getId());
                }
            } else {
                this.addToDestination(subscription.getDestination(), sessionId, subscription.getId());
            }
        }

        private void addToDestination(String destination, String sessionId, String subscriptionId) {
            this.destinationCache.computeIfPresent(destination, (_destination, sessionIdToSubscriptionIds) -> {
                sessionIdToSubscriptionIds = sessionIdToSubscriptionIds.clone();
                this.addMatchedSubscriptionId((LinkedMultiValueMap<String, String>)sessionIdToSubscriptionIds, sessionId, subscriptionId);
                return sessionIdToSubscriptionIds;
            });
        }

        public void updateAfterRemovedSubscription(String sessionId, Subscription subscription) {
            if (subscription.isPattern()) {
                String subscriptionId = subscription.getId();
                this.destinationCache.forEach((destination, sessionIdToSubscriptionIds) -> {
                    List subscriptionIds = sessionIdToSubscriptionIds.get((Object)sessionId);
                    if (subscriptionIds != null && subscriptionIds.contains(subscriptionId)) {
                        this.removeInternal((String)destination, sessionId, subscriptionId);
                    }
                });
            } else {
                this.removeInternal(subscription.getDestination(), sessionId, subscription.getId());
            }
        }

        private void removeInternal(String destination, String sessionId, String subscriptionId) {
            this.destinationCache.computeIfPresent(destination, (_destination, sessionIdToSubscriptionIds) -> {
                sessionIdToSubscriptionIds = sessionIdToSubscriptionIds.clone();
                sessionIdToSubscriptionIds.computeIfPresent((Object)sessionId, (_sessionId, subscriptionIds) -> {
                    if (subscriptionIds.size() == 1 && subscriptionId.equals(subscriptionIds.get(0))) {
                        return null;
                    }
                    subscriptionIds = new ArrayList(subscriptionIds);
                    subscriptionIds.remove(subscriptionId);
                    return subscriptionIds.isEmpty() ? null : subscriptionIds;
                });
                return sessionIdToSubscriptionIds;
            });
        }

        public void updateAfterRemovedSession(String sessionId, SessionInfo info) {
            for (Subscription subscription : info.getSubscriptions()) {
                this.updateAfterRemovedSubscription(sessionId, subscription);
            }
        }
    }
}

