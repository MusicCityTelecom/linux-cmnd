/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.springframework.core.MethodParameter
 *  org.springframework.core.ReactiveAdapter
 *  org.springframework.core.ReactiveAdapterRegistry
 *  org.springframework.core.ResolvableType
 *  org.springframework.core.annotation.AnnotationUtils
 *  org.springframework.expression.BeanResolver
 *  org.springframework.expression.EvaluationContext
 *  org.springframework.expression.Expression
 *  org.springframework.expression.ExpressionParser
 *  org.springframework.expression.spel.standard.SpelExpressionParser
 *  org.springframework.expression.spel.support.StandardEvaluationContext
 *  org.springframework.security.core.annotation.AuthenticationPrincipal
 *  org.springframework.security.core.context.ReactiveSecurityContextHolder
 *  org.springframework.security.core.context.SecurityContext
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.StringUtils
 *  org.springframework.web.reactive.BindingContext
 *  org.springframework.web.reactive.result.method.HandlerMethodArgumentResolverSupport
 *  org.springframework.web.server.ServerWebExchange
 *  reactor.core.publisher.Mono
 */
package org.springframework.security.web.reactive.result.method.annotation;

import java.lang.annotation.Annotation;
import org.reactivestreams.Publisher;
import org.springframework.core.MethodParameter;
import org.springframework.core.ReactiveAdapter;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.core.ResolvableType;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.BindingContext;
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolverSupport;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class AuthenticationPrincipalArgumentResolver
extends HandlerMethodArgumentResolverSupport {
    private ExpressionParser parser = new SpelExpressionParser();
    private BeanResolver beanResolver;

    public AuthenticationPrincipalArgumentResolver(ReactiveAdapterRegistry adapterRegistry) {
        super(adapterRegistry);
    }

    public void setBeanResolver(BeanResolver beanResolver) {
        this.beanResolver = beanResolver;
    }

    public boolean supportsParameter(MethodParameter parameter) {
        return this.findMethodAnnotation(AuthenticationPrincipal.class, parameter) != null;
    }

    public Mono<Object> resolveArgument(MethodParameter parameter, BindingContext bindingContext, ServerWebExchange exchange) {
        ReactiveAdapter adapter = this.getAdapterRegistry().getAdapter(parameter.getParameterType());
        return ReactiveSecurityContextHolder.getContext().map(SecurityContext::getAuthentication).flatMap(authentication -> {
            Mono principal = Mono.justOrEmpty((Object)this.resolvePrincipal(parameter, authentication.getPrincipal()));
            return adapter != null ? Mono.just((Object)adapter.fromPublisher((Publisher)principal)) : principal;
        });
    }

    private Object resolvePrincipal(MethodParameter parameter, Object principal) {
        AuthenticationPrincipal annotation = this.findMethodAnnotation(AuthenticationPrincipal.class, parameter);
        String expressionToParse = annotation.expression();
        if (StringUtils.hasLength((String)expressionToParse)) {
            StandardEvaluationContext context = new StandardEvaluationContext();
            context.setRootObject(principal);
            context.setVariable("this", principal);
            context.setBeanResolver(this.beanResolver);
            Expression expression = this.parser.parseExpression(expressionToParse);
            principal = expression.getValue((EvaluationContext)context);
        }
        if (this.isInvalidType(parameter, principal)) {
            if (annotation.errorOnInvalidType()) {
                throw new ClassCastException(principal + " is not assignable to " + parameter.getParameterType());
            }
            return null;
        }
        return principal;
    }

    private boolean isInvalidType(MethodParameter parameter, Object principal) {
        if (principal == null) {
            return false;
        }
        Class typeToCheck = parameter.getParameterType();
        boolean isParameterPublisher = Publisher.class.isAssignableFrom(parameter.getParameterType());
        if (isParameterPublisher) {
            ResolvableType resolvableType = ResolvableType.forMethodParameter((MethodParameter)parameter);
            Class genericType = resolvableType.resolveGeneric(new int[]{0});
            if (genericType == null) {
                return false;
            }
            typeToCheck = genericType;
        }
        return !ClassUtils.isAssignable((Class)typeToCheck, principal.getClass());
    }

    private <T extends Annotation> T findMethodAnnotation(Class<T> annotationClass, MethodParameter parameter) {
        Annotation[] annotationsToSearch;
        Annotation annotation = parameter.getParameterAnnotation(annotationClass);
        if (annotation != null) {
            return (T)annotation;
        }
        for (Annotation toSearch : annotationsToSearch = parameter.getParameterAnnotations()) {
            annotation = AnnotationUtils.findAnnotation(toSearch.annotationType(), annotationClass);
            if (annotation == null) continue;
            return (T)annotation;
        }
        return null;
    }
}

