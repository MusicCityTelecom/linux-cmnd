/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.aopalliance.aop.Advice
 *  org.aopalliance.intercept.MethodInterceptor
 *  org.aopalliance.intercept.MethodInvocation
 *  org.springframework.aop.Pointcut
 *  org.springframework.aop.PointcutAdvisor
 *  org.springframework.aop.framework.AopInfrastructureBean
 *  org.springframework.aop.support.AopUtils
 *  org.springframework.core.Ordered
 *  org.springframework.expression.EvaluationContext
 *  org.springframework.expression.Expression
 *  org.springframework.lang.NonNull
 *  org.springframework.util.Assert
 */
package org.springframework.security.authorization.method;

import java.lang.reflect.Method;
import java.util.function.Supplier;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.framework.AopInfrastructureBean;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.Ordered;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.lang.NonNull;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authorization.method.AbstractExpressionAttributeRegistry;
import org.springframework.security.authorization.method.AuthorizationAnnotationUtils;
import org.springframework.security.authorization.method.AuthorizationInterceptorsOrder;
import org.springframework.security.authorization.method.AuthorizationMethodPointcuts;
import org.springframework.security.authorization.method.ExpressionAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;

public final class PostFilterAuthorizationMethodInterceptor
implements Ordered,
MethodInterceptor,
PointcutAdvisor,
AopInfrastructureBean {
    private static final Supplier<Authentication> AUTHENTICATION_SUPPLIER = () -> {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new AuthenticationCredentialsNotFoundException("An Authentication object was not found in the SecurityContext");
        }
        return authentication;
    };
    private final PostFilterExpressionAttributeRegistry registry = new PostFilterExpressionAttributeRegistry();
    private int order = AuthorizationInterceptorsOrder.POST_FILTER.getOrder();
    private final Pointcut pointcut;
    private MethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();

    public PostFilterAuthorizationMethodInterceptor() {
        this.pointcut = AuthorizationMethodPointcuts.forAnnotations(PostFilter.class);
    }

    public void setExpressionHandler(MethodSecurityExpressionHandler expressionHandler) {
        Assert.notNull((Object)expressionHandler, (String)"expressionHandler cannot be null");
        this.expressionHandler = expressionHandler;
    }

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Pointcut getPointcut() {
        return this.pointcut;
    }

    public Advice getAdvice() {
        return this;
    }

    public boolean isPerInstance() {
        return true;
    }

    public Object invoke(MethodInvocation mi) throws Throwable {
        Object returnedObject = mi.proceed();
        Object attribute = this.registry.getAttribute(mi);
        if (attribute == ExpressionAttribute.NULL_ATTRIBUTE) {
            return returnedObject;
        }
        EvaluationContext ctx = this.expressionHandler.createEvaluationContext(AUTHENTICATION_SUPPLIER.get(), mi);
        return this.expressionHandler.filter(returnedObject, ((ExpressionAttribute)attribute).getExpression(), ctx);
    }

    private final class PostFilterExpressionAttributeRegistry
    extends AbstractExpressionAttributeRegistry<ExpressionAttribute> {
        private PostFilterExpressionAttributeRegistry() {
        }

        @Override
        @NonNull
        ExpressionAttribute resolveAttribute(Method method, Class<?> targetClass) {
            Method specificMethod = AopUtils.getMostSpecificMethod((Method)method, targetClass);
            PostFilter postFilter = this.findPostFilterAnnotation(specificMethod);
            if (postFilter == null) {
                return ExpressionAttribute.NULL_ATTRIBUTE;
            }
            Expression postFilterExpression = PostFilterAuthorizationMethodInterceptor.this.expressionHandler.getExpressionParser().parseExpression(postFilter.value());
            return new ExpressionAttribute(postFilterExpression);
        }

        private PostFilter findPostFilterAnnotation(Method method) {
            PostFilter postFilter = AuthorizationAnnotationUtils.findUniqueAnnotation(method, PostFilter.class);
            return postFilter != null ? postFilter : AuthorizationAnnotationUtils.findUniqueAnnotation(method.getDeclaringClass(), PostFilter.class);
        }
    }
}

