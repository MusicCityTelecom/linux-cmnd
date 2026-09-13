/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.aop.support.AopUtils
 *  org.springframework.expression.EvaluationContext
 *  org.springframework.expression.Expression
 *  org.springframework.util.Assert
 *  reactor.util.annotation.NonNull
 */
package org.springframework.security.authorization.method;

import java.lang.reflect.Method;
import java.util.function.Supplier;
import org.springframework.aop.support.AopUtils;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.security.access.expression.ExpressionUtils;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.authorization.method.AbstractExpressionAttributeRegistry;
import org.springframework.security.authorization.method.AuthorizationAnnotationUtils;
import org.springframework.security.authorization.method.ExpressionAttribute;
import org.springframework.security.authorization.method.ExpressionAttributeAuthorizationDecision;
import org.springframework.security.authorization.method.MethodInvocationResult;
import org.springframework.security.core.Authentication;
import org.springframework.util.Assert;
import reactor.util.annotation.NonNull;

public final class PostAuthorizeAuthorizationManager
implements AuthorizationManager<MethodInvocationResult> {
    private final PostAuthorizeExpressionAttributeRegistry registry = new PostAuthorizeExpressionAttributeRegistry();
    private MethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();

    public void setExpressionHandler(MethodSecurityExpressionHandler expressionHandler) {
        Assert.notNull((Object)expressionHandler, (String)"expressionHandler cannot be null");
        this.expressionHandler = expressionHandler;
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, MethodInvocationResult mi) {
        Object attribute = this.registry.getAttribute(mi.getMethodInvocation());
        if (attribute == ExpressionAttribute.NULL_ATTRIBUTE) {
            return null;
        }
        EvaluationContext ctx = this.expressionHandler.createEvaluationContext(authentication.get(), mi.getMethodInvocation());
        this.expressionHandler.setReturnObject(mi.getResult(), ctx);
        boolean granted = ExpressionUtils.evaluateAsBoolean(((ExpressionAttribute)attribute).getExpression(), ctx);
        return new ExpressionAttributeAuthorizationDecision(granted, (ExpressionAttribute)attribute);
    }

    private final class PostAuthorizeExpressionAttributeRegistry
    extends AbstractExpressionAttributeRegistry<ExpressionAttribute> {
        private PostAuthorizeExpressionAttributeRegistry() {
        }

        @Override
        @NonNull
        ExpressionAttribute resolveAttribute(Method method, Class<?> targetClass) {
            Method specificMethod = AopUtils.getMostSpecificMethod((Method)method, targetClass);
            PostAuthorize postAuthorize = this.findPostAuthorizeAnnotation(specificMethod);
            if (postAuthorize == null) {
                return ExpressionAttribute.NULL_ATTRIBUTE;
            }
            Expression postAuthorizeExpression = PostAuthorizeAuthorizationManager.this.expressionHandler.getExpressionParser().parseExpression(postAuthorize.value());
            return new ExpressionAttribute(postAuthorizeExpression);
        }

        private PostAuthorize findPostAuthorizeAnnotation(Method method) {
            PostAuthorize postAuthorize = AuthorizationAnnotationUtils.findUniqueAnnotation(method, PostAuthorize.class);
            return postAuthorize != null ? postAuthorize : AuthorizationAnnotationUtils.findUniqueAnnotation(method.getDeclaringClass(), PostAuthorize.class);
        }
    }
}

