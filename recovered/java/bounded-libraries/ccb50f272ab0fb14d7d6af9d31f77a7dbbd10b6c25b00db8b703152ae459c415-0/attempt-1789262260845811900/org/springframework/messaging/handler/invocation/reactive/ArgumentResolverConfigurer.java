/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.messaging.handler.invocation.reactive;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.messaging.handler.invocation.reactive.HandlerMethodArgumentResolver;
import org.springframework.util.Assert;

public class ArgumentResolverConfigurer {
    private final List<HandlerMethodArgumentResolver> customResolvers = new ArrayList<HandlerMethodArgumentResolver>(8);

    public void addCustomResolver(HandlerMethodArgumentResolver ... resolver) {
        Assert.notNull((Object)resolver, (String)"'resolvers' must not be null");
        this.customResolvers.addAll(Arrays.asList(resolver));
    }

    public List<HandlerMethodArgumentResolver> getCustomResolvers() {
        return this.customResolvers;
    }
}

