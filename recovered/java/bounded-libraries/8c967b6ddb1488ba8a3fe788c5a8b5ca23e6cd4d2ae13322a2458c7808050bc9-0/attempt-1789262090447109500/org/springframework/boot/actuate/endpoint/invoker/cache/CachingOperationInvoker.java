/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.ConcurrentReferenceHashMap
 *  org.springframework.util.ObjectUtils
 *  reactor.core.publisher.Flux
 *  reactor.core.publisher.Mono
 */
package org.springframework.boot.actuate.endpoint.invoker.cache;

import java.security.Principal;
import java.time.Duration;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import org.springframework.boot.actuate.endpoint.ApiVersion;
import org.springframework.boot.actuate.endpoint.InvocationContext;
import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.boot.actuate.endpoint.invoke.OperationInvoker;
import org.springframework.boot.actuate.endpoint.invoke.OperationParameter;
import org.springframework.boot.actuate.endpoint.invoke.OperationParameters;
import org.springframework.boot.actuate.endpoint.web.WebServerNamespace;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ConcurrentReferenceHashMap;
import org.springframework.util.ObjectUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CachingOperationInvoker
implements OperationInvoker {
    private static final boolean IS_REACTOR_PRESENT = ClassUtils.isPresent((String)"reactor.core.publisher.Mono", null);
    private static final int CACHE_CLEANUP_THRESHOLD = 40;
    private final OperationInvoker invoker;
    private final long timeToLive;
    private final Map<CacheKey, CachedResponse> cachedResponses;

    CachingOperationInvoker(OperationInvoker invoker, long timeToLive) {
        Assert.isTrue((timeToLive > 0L ? 1 : 0) != 0, (String)"TimeToLive must be strictly positive");
        this.invoker = invoker;
        this.timeToLive = timeToLive;
        this.cachedResponses = new ConcurrentReferenceHashMap();
    }

    public long getTimeToLive() {
        return this.timeToLive;
    }

    @Override
    public Object invoke(InvocationContext context) {
        CacheKey cacheKey;
        CachedResponse cached;
        if (this.hasInput(context)) {
            return this.invoker.invoke(context);
        }
        long accessTime = System.currentTimeMillis();
        if (this.cachedResponses.size() > 40) {
            this.cleanExpiredCachedResponses(accessTime);
        }
        if ((cached = this.cachedResponses.get(cacheKey = this.getCacheKey(context))) == null || cached.isStale(accessTime, this.timeToLive)) {
            Object response = this.invoker.invoke(context);
            cached = this.createCachedResponse(response, accessTime);
            this.cachedResponses.put(cacheKey, cached);
        }
        return cached.getResponse();
    }

    private CacheKey getCacheKey(InvocationContext context) {
        ApiVersion contextApiVersion = context.resolveArgument(ApiVersion.class);
        Principal principal = context.resolveArgument(Principal.class);
        WebServerNamespace serverNamespace = context.resolveArgument(WebServerNamespace.class);
        return new CacheKey(contextApiVersion, principal, serverNamespace);
    }

    private void cleanExpiredCachedResponses(long accessTime) {
        try {
            Iterator<Map.Entry<CacheKey, CachedResponse>> iterator = this.cachedResponses.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<CacheKey, CachedResponse> entry = iterator.next();
                if (!entry.getValue().isStale(accessTime, this.timeToLive)) continue;
                iterator.remove();
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    private boolean hasInput(InvocationContext context) {
        Map<String, Object> arguments = context.getArguments();
        if (!ObjectUtils.isEmpty(arguments)) {
            return arguments.values().stream().anyMatch(Objects::nonNull);
        }
        return false;
    }

    private CachedResponse createCachedResponse(Object response, long accessTime) {
        if (IS_REACTOR_PRESENT) {
            return new ReactiveCachedResponse(response, accessTime, this.timeToLive);
        }
        return new CachedResponse(response, accessTime);
    }

    static boolean isApplicable(OperationParameters parameters) {
        for (OperationParameter parameter : parameters) {
            if (!parameter.isMandatory() || CacheKey.containsType(parameter.getType())) continue;
            return false;
        }
        return true;
    }

    private static final class CacheKey {
        private static final Class<?>[] CACHEABLE_TYPES = new Class[]{ApiVersion.class, SecurityContext.class, WebServerNamespace.class};
        private final ApiVersion apiVersion;
        private final Principal principal;
        private final WebServerNamespace serverNamespace;

        private CacheKey(ApiVersion apiVersion, Principal principal, WebServerNamespace serverNamespace) {
            this.principal = principal;
            this.apiVersion = apiVersion;
            this.serverNamespace = serverNamespace;
        }

        static boolean containsType(Class<?> type) {
            return Arrays.stream(CACHEABLE_TYPES).anyMatch(c -> c.isAssignableFrom(type));
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || this.getClass() != obj.getClass()) {
                return false;
            }
            CacheKey other = (CacheKey)obj;
            return this.apiVersion.equals(other.apiVersion) && ObjectUtils.nullSafeEquals((Object)this.principal, (Object)other.principal) && ObjectUtils.nullSafeEquals((Object)this.serverNamespace, (Object)other.serverNamespace);
        }

        public int hashCode() {
            int prime = 31;
            int result = 1;
            result = 31 * result + this.apiVersion.hashCode();
            result = 31 * result + ObjectUtils.nullSafeHashCode((Object)this.principal);
            result = 31 * result + ObjectUtils.nullSafeHashCode((Object)this.serverNamespace);
            return result;
        }
    }

    static class ReactiveCachedResponse
    extends CachedResponse {
        ReactiveCachedResponse(Object response, long creationTime, long timeToLive) {
            super(ReactiveCachedResponse.applyCaching(response, timeToLive), creationTime);
        }

        private static Object applyCaching(Object response, long timeToLive) {
            if (response instanceof Mono) {
                return ((Mono)response).cache(Duration.ofMillis(timeToLive));
            }
            if (response instanceof Flux) {
                return ((Flux)response).cache(Duration.ofMillis(timeToLive));
            }
            return response;
        }
    }

    static class CachedResponse {
        private final Object response;
        private final long creationTime;

        CachedResponse(Object response, long creationTime) {
            this.response = response;
            this.creationTime = creationTime;
        }

        boolean isStale(long accessTime, long timeToLive) {
            return accessTime - this.creationTime >= timeToLive;
        }

        Object getResponse() {
            return this.response;
        }
    }
}

