/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.MethodParameter
 *  org.springframework.core.annotation.AnnotationUtils
 *  org.springframework.expression.BeanResolver
 *  org.springframework.expression.EvaluationContext
 *  org.springframework.expression.Expression
 *  org.springframework.expression.ExpressionParser
 *  org.springframework.expression.spel.standard.SpelExpressionParser
 *  org.springframework.expression.spel.support.StandardEvaluationContext
 *  org.springframework.security.core.annotation.CurrentSecurityContext
 *  org.springframework.security.core.context.SecurityContext
 *  org.springframework.security.core.context.SecurityContextHolder
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 *  org.springframework.web.bind.support.WebDataBinderFactory
 *  org.springframework.web.context.request.NativeWebRequest
 *  org.springframework.web.method.support.HandlerMethodArgumentResolver
 *  org.springframework.web.method.support.ModelAndViewContainer
 */
package org.springframework.security.web.method.annotation;

import java.lang.annotation.Annotation;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public final class CurrentSecurityContextArgumentResolver
implements HandlerMethodArgumentResolver {
    private ExpressionParser parser = new SpelExpressionParser();
    private BeanResolver beanResolver;

    public boolean supportsParameter(MethodParameter parameter) {
        return this.findMethodAnnotation(CurrentSecurityContext.class, parameter) != null;
    }

    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext == null) {
            return null;
        }
        Object securityContextResult = securityContext;
        CurrentSecurityContext annotation = this.findMethodAnnotation(CurrentSecurityContext.class, parameter);
        String expressionToParse = annotation.expression();
        if (StringUtils.hasLength((String)expressionToParse)) {
            StandardEvaluationContext context = new StandardEvaluationContext();
            context.setRootObject((Object)securityContext);
            context.setVariable("this", (Object)securityContext);
            context.setBeanResolver(this.beanResolver);
            Expression expression = this.parser.parseExpression(expressionToParse);
            securityContextResult = expression.getValue((EvaluationContext)context);
        }
        if (securityContextResult != null && !parameter.getParameterType().isAssignableFrom(securityContextResult.getClass())) {
            if (annotation.errorOnInvalidType()) {
                throw new ClassCastException(securityContextResult + " is not assignable to " + parameter.getParameterType());
            }
            return null;
        }
        return securityContextResult;
    }

    public void setBeanResolver(BeanResolver beanResolver) {
        Assert.notNull((Object)beanResolver, (String)"beanResolver cannot be null");
        this.beanResolver = beanResolver;
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

