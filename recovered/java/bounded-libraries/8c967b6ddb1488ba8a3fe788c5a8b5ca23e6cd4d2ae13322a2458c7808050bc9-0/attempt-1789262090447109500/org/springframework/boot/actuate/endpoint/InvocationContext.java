/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.boot.actuate.endpoint;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.springframework.boot.actuate.endpoint.ApiVersion;
import org.springframework.boot.actuate.endpoint.OperationArgumentResolver;
import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.util.Assert;

public class InvocationContext {
    private final Map<String, Object> arguments;
    private final List<OperationArgumentResolver> argumentResolvers;

    public InvocationContext(SecurityContext securityContext, Map<String, Object> arguments, OperationArgumentResolver ... argumentResolvers) {
        Assert.notNull((Object)securityContext, (String)"SecurityContext must not be null");
        Assert.notNull(arguments, (String)"Arguments must not be null");
        this.arguments = arguments;
        this.argumentResolvers = new ArrayList<OperationArgumentResolver>();
        if (argumentResolvers != null) {
            this.argumentResolvers.addAll(Arrays.asList(argumentResolvers));
        }
        this.argumentResolvers.add(OperationArgumentResolver.of(SecurityContext.class, () -> securityContext));
        this.argumentResolvers.add(OperationArgumentResolver.of(Principal.class, securityContext::getPrincipal));
        this.argumentResolvers.add(OperationArgumentResolver.of(ApiVersion.class, () -> ApiVersion.LATEST));
    }

    public Map<String, Object> getArguments() {
        return this.arguments;
    }

    public <T> T resolveArgument(Class<T> argumentType) {
        for (OperationArgumentResolver argumentResolver : this.argumentResolvers) {
            T result;
            if (!argumentResolver.canResolve(argumentType) || (result = argumentResolver.resolve(argumentType)) == null) continue;
            return result;
        }
        return null;
    }

    public boolean canResolve(Class<?> type) {
        for (OperationArgumentResolver argumentResolver : this.argumentResolvers) {
            if (!argumentResolver.canResolve(type)) continue;
            return true;
        }
        return false;
    }
}

