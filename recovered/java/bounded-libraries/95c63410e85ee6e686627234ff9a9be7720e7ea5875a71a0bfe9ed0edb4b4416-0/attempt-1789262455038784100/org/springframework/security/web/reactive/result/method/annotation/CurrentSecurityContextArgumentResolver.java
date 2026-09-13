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
 *  org.springframework.security.core.annotation.CurrentSecurityContext
 *  org.springframework.security.core.context.ReactiveSecurityContextHolder
 *  org.springframework.security.core.context.SecurityContext
 *  org.springframework.util.Assert
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
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.BindingContext;
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolverSupport;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class CurrentSecurityContextArgumentResolver
extends HandlerMethodArgumentResolverSupport {
    private ExpressionParser parser = new SpelExpressionParser();
    private BeanResolver beanResolver;

    public CurrentSecurityContextArgumentResolver(ReactiveAdapterRegistry adapterRegistry) {
        super(adapterRegistry);
    }

    public void setBeanResolver(BeanResolver beanResolver) {
        Assert.notNull((Object)beanResolver, (String)"beanResolver cannot be null");
        this.beanResolver = beanResolver;
    }

    public boolean supportsParameter(MethodParameter parameter) {
        return this.findMethodAnnotation(CurrentSecurityContext.class, parameter) != null;
    }

    public Mono<Object> resolveArgument(MethodParameter parameter, BindingContext bindingContext, ServerWebExchange exchange) {
        ReactiveAdapter adapter = this.getAdapterRegistry().getAdapter(parameter.getParameterType());
        Mono reactiveSecurityContext = ReactiveSecurityContextHolder.getContext();
        if (reactiveSecurityContext == null) {
            return null;
        }
        return reactiveSecurityContext.flatMap(securityContext -> {
            Mono resolvedSecurityContext = Mono.justOrEmpty((Object)this.resolveSecurityContext(parameter, (SecurityContext)securityContext));
            return adapter != null ? Mono.just((Object)adapter.fromPublisher((Publisher)resolvedSecurityContext)) : resolvedSecurityContext;
        });
    }

    private Object resolveSecurityContext(MethodParameter parameter, SecurityContext securityContext) {
        CurrentSecurityContext annotation = this.findMethodAnnotation(CurrentSecurityContext.class, parameter);
        Object securityContextResult = securityContext;
        String expressionToParse = annotation.expression();
        if (StringUtils.hasLength((String)expressionToParse)) {
            StandardEvaluationContext context = new StandardEvaluationContext();
            context.setRootObject((Object)securityContext);
            context.setVariable("this", (Object)securityContext);
            context.setBeanResolver(this.beanResolver);
            Expression expression = this.parser.parseExpression(expressionToParse);
            securityContextResult = expression.getValue((EvaluationContext)context);
        }
        if (this.isInvalidType(parameter, securityContextResult)) {
            if (annotation.errorOnInvalidType()) {
                throw new ClassCastException(securityContextResult + " is not assignable to " + parameter.getParameterType());
            }
            return null;
        }
        return securityContextResult;
    }

    private boolean isInvalidType(MethodParameter parameter, Object reactiveSecurityContext) {
        if (reactiveSecurityContext == null) {
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
        return !typeToCheck.isAssignableFrom(reactiveSecurityContext.getClass());
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

