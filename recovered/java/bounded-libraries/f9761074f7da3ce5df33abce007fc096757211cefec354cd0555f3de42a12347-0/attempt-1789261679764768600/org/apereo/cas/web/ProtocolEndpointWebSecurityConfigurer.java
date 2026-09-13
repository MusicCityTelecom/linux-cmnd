/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.errorprone.annotations.CanIgnoreReturnValue
 *  org.springframework.core.Ordered
 */
package org.apereo.cas.web;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.List;
import org.springframework.core.Ordered;

public interface ProtocolEndpointWebSecurityConfigurer<T>
extends Ordered {
    default public int getOrder() {
        return Integer.MAX_VALUE;
    }

    default public List<String> getIgnoredEndpoints() {
        return List.of();
    }

    @CanIgnoreReturnValue
    default public ProtocolEndpointWebSecurityConfigurer<T> configure(T object) {
        return this;
    }
}

