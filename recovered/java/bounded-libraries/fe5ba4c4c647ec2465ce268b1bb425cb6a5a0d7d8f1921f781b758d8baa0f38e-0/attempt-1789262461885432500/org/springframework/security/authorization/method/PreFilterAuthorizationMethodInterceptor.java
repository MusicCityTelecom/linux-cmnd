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
 *  org.springframework.util.StringUtils
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
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authorization.method.AbstractExpressionAttributeRegistry;
import org.springframework.security.authorization.method.AuthorizationAnnotationUtils;
import org.springframework.security.authorization.method.AuthorizationInterceptorsOrder;
import org.springframework.security.authorization.method.AuthorizationMethodPointcuts;
import org.springframework.security.authorization.method.ExpressionAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public final class PreFilterAuthorizationMethodInterceptor
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
    private final PreFilterExpressionAttributeRegistry registry = new PreFilterExpressionAttributeRegistry();
    private int order = AuthorizationInterceptorsOrder.PRE_FILTER.getOrder();
    private final Pointcut pointcut;
    private MethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();

    public PreFilterAuthorizationMethodInterceptor() {
        this.pointcut = AuthorizationMethodPointcuts.forAnnotations(PreFilter.class);
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
        PreFilterExpressionAttribute attribute = (PreFilterExpressionAttribute)this.registry.getAttribute(mi);
        if (attribute == PreFilterExpressionAttribute.NULL_ATTRIBUTE) {
            return mi.proceed();
        }
        EvaluationContext ctx = this.expressionHandler.createEvaluationContext(AUTHENTICATION_SUPPLIER.get(), mi);
        Object filterTarget = this.findFilterTarget(attribute.filterTarget, ctx, mi);
        this.expressionHandler.filter(filterTarget, attribute.getExpression(), ctx);
        return mi.proceed();
    }

    private Object findFilterTarget(String filterTargetName, EvaluationContext ctx, MethodInvocation methodInvocation) {
        Object filterTarget;
        if (StringUtils.hasText((String)filterTargetName)) {
            filterTarget = ctx.lookupVariable(filterTargetName);
            Assert.notNull((Object)filterTarget, () -> "Filter target was null, or no argument with name '" + filterTargetName + "' found in method.");
        } else {
            Object[] arguments = methodInvocation.getArguments();
            Assert.state((arguments.length == 1 ? 1 : 0) != 0, (String)"Unable to determine the method argument for filtering. Specify the filter target.");
            filterTarget = arguments[0];
            Assert.notNull((Object)filterTarget, (String)"Filter target was null. Make sure you passing the correct value in the method argument.");
        }
        Assert.state((!filterTarget.getClass().isArray() ? 1 : 0) != 0, (String)"Pre-filtering on array types is not supported. Using a Collection will solve this problem.");
        return filterTarget;
    }

    private static final class PreFilterExpressionAttribute
    extends ExpressionAttribute {
        private static final PreFilterExpressionAttribute NULL_ATTRIBUTE = new PreFilterExpressionAttribute(null, null);
        private final String filterTarget;

        private PreFilterExpressionAttribute(Expression expression, String filterTarget) {
            super(expression);
            this.filterTarget = filterTarget;
        }
    }

    private final class PreFilterExpressionAttributeRegistry
    extends AbstractExpressionAttributeRegistry<PreFilterExpressionAttribute> {
        private PreFilterExpressionAttributeRegistry() {
        }

        @Override
        @NonNull
        PreFilterExpressionAttribute resolveAttribute(Method method, Class<?> targetClass) {
            Method specificMethod = AopUtils.getMostSpecificMethod((Method)method, targetClass);
            PreFilter preFilter = this.findPreFilterAnnotation(specificMethod);
            if (preFilter == null) {
                return PreFilterExpressionAttribute.NULL_ATTRIBUTE;
            }
            Expression preFilterExpression = PreFilterAuthorizationMethodInterceptor.this.expressionHandler.getExpressionParser().parseExpression(preFilter.value());
            return new PreFilterExpressionAttribute(preFilterExpression, preFilter.filterTarget());
        }

        private PreFilter findPreFilterAnnotation(Method method) {
            PreFilter preFilter = AuthorizationAnnotationUtils.findUniqueAnnotation(method, PreFilter.class);
            return preFilter != null ? preFilter : AuthorizationAnnotationUtils.findUniqueAnnotation(method.getDeclaringClass(), PreFilter.class);
        }
    }
}

