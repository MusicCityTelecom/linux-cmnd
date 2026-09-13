/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  org.springframework.context.ConfigurableApplicationContext
 *  org.springframework.core.Ordered
 */
package org.apereo.cas.authentication;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.Serializable;
import java.util.Optional;
import java.util.Set;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.AuthenticationHandler;
import org.apereo.cas.authentication.AuthenticationPolicyExecutionResult;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;

@FunctionalInterface
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public interface AuthenticationPolicy
extends Ordered,
Serializable {
    public AuthenticationPolicyExecutionResult isSatisfiedBy(Authentication var1, Set<AuthenticationHandler> var2, ConfigurableApplicationContext var3, Optional<Serializable> var4) throws Exception;

    default public int getOrder() {
        return Integer.MAX_VALUE;
    }

    default public String getName() {
        return this.getClass().getSimpleName();
    }

    default public boolean shouldResumeOnFailure(Throwable failure) {
        return failure != null;
    }
}

