/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.MethodParameter
 */
package org.springframework.messaging.simp.annotation.support;

import java.security.Principal;
import java.util.Optional;
import org.springframework.core.MethodParameter;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

public class PrincipalMethodArgumentResolver
implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        MethodParameter nestedParameter = parameter.nestedIfOptional();
        Class paramType = nestedParameter.getNestedParameterType();
        return Principal.class.isAssignableFrom(paramType);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, Message<?> message) {
        Principal user = SimpMessageHeaderAccessor.getUser(message.getHeaders());
        return parameter.isOptional() ? Optional.ofNullable(user) : user;
    }
}

