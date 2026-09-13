/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.ApplicationContext
 *  org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher
 *  org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher$MatchResult
 *  org.springframework.util.Assert
 *  org.springframework.web.server.ServerWebExchange
 *  reactor.core.publisher.Mono
 */
package org.springframework.boot.security.reactive;

import java.util.function.Supplier;
import org.springframework.context.ApplicationContext;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public abstract class ApplicationContextServerWebExchangeMatcher<C>
implements ServerWebExchangeMatcher {
    private final Class<? extends C> contextClass;
    private volatile Supplier<C> context;
    private final Object contextLock = new Object();

    public ApplicationContextServerWebExchangeMatcher(Class<? extends C> contextClass) {
        Assert.notNull(contextClass, (String)"Context class must not be null");
        this.contextClass = contextClass;
    }

    public final Mono<ServerWebExchangeMatcher.MatchResult> matches(ServerWebExchange exchange) {
        if (this.ignoreApplicationContext(exchange.getApplicationContext())) {
            return ServerWebExchangeMatcher.MatchResult.notMatch();
        }
        return this.matches(exchange, this.getContext(exchange));
    }

    protected abstract Mono<ServerWebExchangeMatcher.MatchResult> matches(ServerWebExchange var1, Supplier<C> var2);

    protected boolean ignoreApplicationContext(ApplicationContext applicationContext) {
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected Supplier<C> getContext(ServerWebExchange exchange) {
        if (this.context == null) {
            Object object = this.contextLock;
            synchronized (object) {
                if (this.context == null) {
                    Supplier<C> createdContext = this.createContext(exchange);
                    this.initialized(createdContext);
                    this.context = createdContext;
                }
            }
        }
        return this.context;
    }

    protected void initialized(Supplier<C> context) {
    }

    private Supplier<C> createContext(ServerWebExchange exchange) {
        ApplicationContext context = exchange.getApplicationContext();
        Assert.state((context != null ? 1 : 0) != 0, (String)"No ApplicationContext found on ServerWebExchange.");
        if (this.contextClass.isInstance(context)) {
            return () -> context;
        }
        return () -> context.getBean(this.contextClass);
    }
}

