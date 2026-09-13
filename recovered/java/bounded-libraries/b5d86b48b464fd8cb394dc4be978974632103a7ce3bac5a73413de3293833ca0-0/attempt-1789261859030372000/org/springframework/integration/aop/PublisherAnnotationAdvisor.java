/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.aopalliance.aop.Advice
 *  org.springframework.aop.ClassFilter
 *  org.springframework.aop.MethodMatcher
 *  org.springframework.aop.Pointcut
 *  org.springframework.aop.support.AbstractPointcutAdvisor
 *  org.springframework.aop.support.ComposablePointcut
 *  org.springframework.aop.support.annotation.AnnotationClassFilter
 *  org.springframework.aop.support.annotation.AnnotationMethodMatcher
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.util.Assert
 */
package org.springframework.integration.aop;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.aopalliance.aop.Advice;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.annotation.AnnotationClassFilter;
import org.springframework.aop.support.annotation.AnnotationMethodMatcher;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.integration.annotation.Publisher;
import org.springframework.integration.aop.MessagePublishingInterceptor;
import org.springframework.integration.aop.MethodAnnotationPublisherMetadataSource;
import org.springframework.util.Assert;

public class PublisherAnnotationAdvisor
extends AbstractPointcutAdvisor
implements BeanFactoryAware {
    private static final long serialVersionUID = -5387975397101845222L;
    private final transient MessagePublishingInterceptor interceptor;
    private final Pointcut pointcut;

    public PublisherAnnotationAdvisor() {
        this(Publisher.class);
    }

    @SafeVarargs
    public PublisherAnnotationAdvisor(Class<? extends Annotation> ... publisherAnnotationTypes) {
        MethodAnnotationPublisherMetadataSource metadataSource = new MethodAnnotationPublisherMetadataSource(Arrays.stream(publisherAnnotationTypes).collect(Collectors.toSet()));
        this.interceptor = new MessagePublishingInterceptor(metadataSource);
        this.pointcut = PublisherAnnotationAdvisor.buildPointcut(publisherAnnotationTypes);
    }

    public void setDefaultChannelName(String defaultChannelName) {
        this.interceptor.setDefaultChannelName(defaultChannelName);
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.interceptor.setBeanFactory(beanFactory);
    }

    public Advice getAdvice() {
        return this.interceptor;
    }

    public Pointcut getPointcut() {
        return this.pointcut;
    }

    private static Pointcut buildPointcut(Class<? extends Annotation>[] publisherAnnotationTypes) {
        ComposablePointcut result = null;
        for (Class<? extends Annotation> publisherAnnotationType : publisherAnnotationTypes) {
            MetaAnnotationMatchingPointcut cpc = new MetaAnnotationMatchingPointcut(publisherAnnotationType, true);
            MetaAnnotationMatchingPointcut mpc = new MetaAnnotationMatchingPointcut(null, publisherAnnotationType);
            if (result == null) {
                result = new ComposablePointcut((Pointcut)cpc).union((Pointcut)mpc);
                continue;
            }
            result.union((Pointcut)cpc).union((Pointcut)mpc);
        }
        return result;
    }

    private static final class MetaAnnotationMatchingPointcut
    implements Pointcut {
        private final ClassFilter classFilter;
        private final MethodMatcher methodMatcher;

        MetaAnnotationMatchingPointcut(Class<? extends Annotation> classAnnotationType, boolean checkInherited) {
            this.classFilter = new AnnotationClassFilter(classAnnotationType, checkInherited);
            this.methodMatcher = MethodMatcher.TRUE;
        }

        MetaAnnotationMatchingPointcut(Class<? extends Annotation> classAnnotationType, Class<? extends Annotation> methodAnnotationType) {
            Assert.isTrue((classAnnotationType != null || methodAnnotationType != null ? 1 : 0) != 0, (String)"Either Class annotation type or Method annotation type needs to be specified (or both)");
            this.classFilter = classAnnotationType != null ? new AnnotationClassFilter(classAnnotationType) : ClassFilter.TRUE;
            this.methodMatcher = methodAnnotationType != null ? new AnnotationMethodMatcher(methodAnnotationType, true) : MethodMatcher.TRUE;
        }

        public ClassFilter getClassFilter() {
            return this.classFilter;
        }

        public MethodMatcher getMethodMatcher() {
            return this.methodMatcher;
        }
    }
}

