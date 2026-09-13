/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.aopalliance.aop.Advice
 *  org.springframework.aop.ClassFilter
 *  org.springframework.aop.IntroductionAdvisor
 *  org.springframework.aop.MethodMatcher
 *  org.springframework.aop.Pointcut
 *  org.springframework.aop.support.AbstractPointcutAdvisor
 *  org.springframework.aop.support.ComposablePointcut
 *  org.springframework.aop.support.StaticMethodMatcherPointcut
 *  org.springframework.aop.support.annotation.AnnotationClassFilter
 *  org.springframework.aop.support.annotation.AnnotationMethodMatcher
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.beans.factory.ListableBeanFactory
 *  org.springframework.context.annotation.Role
 *  org.springframework.core.OrderComparator
 *  org.springframework.core.annotation.AnnotationUtils
 *  org.springframework.stereotype.Component
 *  org.springframework.util.ObjectUtils
 *  org.springframework.util.ReflectionUtils
 *  org.springframework.util.ReflectionUtils$MethodCallback
 */
package org.springframework.retry.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import org.aopalliance.aop.Advice;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.IntroductionAdvisor;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.aop.support.annotation.AnnotationClassFilter;
import org.springframework.aop.support.annotation.AnnotationMethodMatcher;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.annotation.Role;
import org.springframework.core.OrderComparator;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.retry.RetryListener;
import org.springframework.retry.annotation.AnnotationAwareRetryOperationsInterceptor;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.backoff.Sleeper;
import org.springframework.retry.interceptor.MethodArgumentsKeyGenerator;
import org.springframework.retry.interceptor.NewMethodArgumentsIdentifier;
import org.springframework.retry.policy.RetryContextCache;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;

@Role(value=2)
@Component
public class RetryConfiguration
extends AbstractPointcutAdvisor
implements IntroductionAdvisor,
BeanFactoryAware,
InitializingBean {
    private Advice advice;
    private Pointcut pointcut;
    private RetryContextCache retryContextCache;
    private List<RetryListener> retryListeners;
    private MethodArgumentsKeyGenerator methodArgumentsKeyGenerator;
    private NewMethodArgumentsIdentifier newMethodArgumentsIdentifier;
    private Sleeper sleeper;
    private BeanFactory beanFactory;

    public void afterPropertiesSet() throws Exception {
        this.retryContextCache = this.findBean(RetryContextCache.class);
        this.methodArgumentsKeyGenerator = this.findBean(MethodArgumentsKeyGenerator.class);
        this.newMethodArgumentsIdentifier = this.findBean(NewMethodArgumentsIdentifier.class);
        this.retryListeners = this.findBeans(RetryListener.class);
        this.sleeper = this.findBean(Sleeper.class);
        LinkedHashSet<Class<? extends Annotation>> retryableAnnotationTypes = new LinkedHashSet<Class<? extends Annotation>>(1);
        retryableAnnotationTypes.add(Retryable.class);
        this.pointcut = this.buildPointcut(retryableAnnotationTypes);
        this.advice = this.buildAdvice();
        if (this.advice instanceof BeanFactoryAware) {
            ((BeanFactoryAware)this.advice).setBeanFactory(this.beanFactory);
        }
    }

    private <T> List<T> findBeans(Class<? extends T> type) {
        ListableBeanFactory listable;
        if (this.beanFactory instanceof ListableBeanFactory && (listable = (ListableBeanFactory)this.beanFactory).getBeanNamesForType(type).length > 0) {
            ArrayList list = new ArrayList(listable.getBeansOfType(type, false, false).values());
            OrderComparator.sort(list);
            return list;
        }
        return null;
    }

    private <T> T findBean(Class<? extends T> type) {
        ListableBeanFactory listable;
        if (this.beanFactory instanceof ListableBeanFactory && (listable = (ListableBeanFactory)this.beanFactory).getBeanNamesForType(type, false, false).length == 1) {
            return (T)listable.getBean(type);
        }
        return null;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public ClassFilter getClassFilter() {
        return this.pointcut.getClassFilter();
    }

    public Class<?>[] getInterfaces() {
        return new Class[]{org.springframework.retry.interceptor.Retryable.class};
    }

    public void validateInterfaces() throws IllegalArgumentException {
    }

    public Advice getAdvice() {
        return this.advice;
    }

    public Pointcut getPointcut() {
        return this.pointcut;
    }

    protected Advice buildAdvice() {
        AnnotationAwareRetryOperationsInterceptor interceptor = new AnnotationAwareRetryOperationsInterceptor();
        if (this.retryContextCache != null) {
            interceptor.setRetryContextCache(this.retryContextCache);
        }
        if (this.retryListeners != null) {
            interceptor.setListeners(this.retryListeners);
        }
        if (this.methodArgumentsKeyGenerator != null) {
            interceptor.setKeyGenerator(this.methodArgumentsKeyGenerator);
        }
        if (this.newMethodArgumentsIdentifier != null) {
            interceptor.setNewItemIdentifier(this.newMethodArgumentsIdentifier);
        }
        if (this.sleeper != null) {
            interceptor.setSleeper(this.sleeper);
        }
        return interceptor;
    }

    protected Pointcut buildPointcut(Set<Class<? extends Annotation>> retryAnnotationTypes) {
        ComposablePointcut result = null;
        for (Class<? extends Annotation> retryAnnotationType : retryAnnotationTypes) {
            AnnotationClassOrMethodPointcut filter = new AnnotationClassOrMethodPointcut(retryAnnotationType);
            if (result == null) {
                result = new ComposablePointcut((Pointcut)filter);
                continue;
            }
            result.union((Pointcut)filter);
        }
        return result;
    }

    private static class AnnotationMethodsResolver {
        private Class<? extends Annotation> annotationType;

        public AnnotationMethodsResolver(Class<? extends Annotation> annotationType) {
            this.annotationType = annotationType;
        }

        public boolean hasAnnotatedMethods(Class<?> clazz) {
            final AtomicBoolean found = new AtomicBoolean(false);
            ReflectionUtils.doWithMethods(clazz, (ReflectionUtils.MethodCallback)new ReflectionUtils.MethodCallback(){

                public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
                    if (found.get()) {
                        return;
                    }
                    Annotation annotation = AnnotationUtils.findAnnotation((Method)method, (Class)AnnotationMethodsResolver.this.annotationType);
                    if (annotation != null) {
                        found.set(true);
                    }
                }
            });
            return found.get();
        }
    }

    private final class AnnotationClassOrMethodFilter
    extends AnnotationClassFilter {
        private final AnnotationMethodsResolver methodResolver;

        AnnotationClassOrMethodFilter(Class<? extends Annotation> annotationType) {
            super(annotationType, true);
            this.methodResolver = new AnnotationMethodsResolver(annotationType);
        }

        public boolean matches(Class<?> clazz) {
            return super.matches(clazz) || this.methodResolver.hasAnnotatedMethods(clazz);
        }
    }

    private final class AnnotationClassOrMethodPointcut
    extends StaticMethodMatcherPointcut {
        private final MethodMatcher methodResolver;

        AnnotationClassOrMethodPointcut(Class<? extends Annotation> annotationType) {
            this.methodResolver = new AnnotationMethodMatcher(annotationType);
            this.setClassFilter((ClassFilter)new AnnotationClassOrMethodFilter(annotationType));
        }

        public boolean matches(Method method, Class<?> targetClass) {
            return this.getClassFilter().matches(targetClass) || this.methodResolver.matches(method, targetClass);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof AnnotationClassOrMethodPointcut)) {
                return false;
            }
            AnnotationClassOrMethodPointcut otherAdvisor = (AnnotationClassOrMethodPointcut)((Object)other);
            return ObjectUtils.nullSafeEquals((Object)this.methodResolver, (Object)otherAdvisor.methodResolver);
        }
    }
}

