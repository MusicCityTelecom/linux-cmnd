/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.aopalliance.intercept.MethodInvocation
 *  org.springframework.aop.support.AopUtils
 *  org.springframework.expression.EvaluationContext
 *  org.springframework.expression.Expression
 *  org.springframework.util.Assert
 *  reactor.util.annotation.NonNull
 */
package org.springframework.security.authorization.method;

import java.lang.reflect.Method;
import java.util.function.Supplier;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.AopUtils;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.security.access.expression.ExpressionUtils;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.authorization.method.AbstractExpressionAttributeRegistry;
import org.springframework.security.authorization.method.AuthorizationAnnotationUtils;
import org.springframework.security.authorization.method.ExpressionAttribute;
import org.springframework.security.authorization.method.ExpressionAttributeAuthorizationDecision;
import org.springframework.security.core.Authentication;
import org.springframework.util.Assert;
import reactor.util.annotation.NonNull;

public final class PreAuthorizeAuthorizationManager
implements AuthorizationManager<MethodInvocation> {
    private final PreAuthorizeExpressionAttributeRegistry registry = new PreAuthorizeExpressionAttributeRegistry();
    private MethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();

    public void setExpressionHandler(MethodSecurityExpressionHandler expressionHandler) {
        Assert.notNull((Object)expressionHandler, (String)"expressionHandler cannot be null");
        this.expressionHandler = expressionHandler;
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, MethodInvocation mi) {
        Object attribute = this.registry.getAttribute(mi);
        if (attribute == ExpressionAttribute.NULL_ATTRIBUTE) {
            return null;
        }
        EvaluationContext ctx = this.expressionHandler.createEvaluationContext(authentication.get(), mi);
        boolean granted = ExpressionUtils.evaluateAsBoolean(((ExpressionAttribute)attribute).getExpression(), ctx);
        return new ExpressionAttributeAuthorizationDecision(granted, (ExpressionAttribute)attribute);
    }

    private final class PreAuthorizeExpressionAttributeRegistry
    extends AbstractExpressionAttributeRegistry<ExpressionAttribute> {
        private PreAuthorizeExpressionAttributeRegistry() {
        }

        @Override
        @NonNull
        ExpressionAttribute resolveAttribute(Method method, Class<?> targetClass) {
            Method specificMethod = AopUtils.getMostSpecificMethod((Method)method, targetClass);
            PreAuthorize preAuthorize = this.findPreAuthorizeAnnotation(specificMethod);
            if (preAuthorize == null) {
                return ExpressionAttribute.NULL_ATTRIBUTE;
            }
            Expression preAuthorizeExpression = PreAuthorizeAuthorizationManager.this.expressionHandler.getExpressionParser().parseExpression(preAuthorize.value());
            return new ExpressionAttribute(preAuthorizeExpression);
        }

        private PreAuthorize findPreAuthorizeAnnotation(Method method) {
            PreAuthorize preAuthorize = AuthorizationAnnotationUtils.findUniqueAnnotation(method, PreAuthorize.class);
            return preAuthorize != null ? preAuthorize : AuthorizationAnnotationUtils.findUniqueAnnotation(method.getDeclaringClass(), PreAuthorize.class);
        }
    }
}

