/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 *  org.springframework.context.annotation.Bean
 *  org.springframework.core.annotation.AnnotatedElementUtils
 *  org.springframework.messaging.MessageHandler
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.config.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.integration.annotation.Filter;
import org.springframework.integration.config.annotation.AbstractMethodAnnotationPostProcessor;
import org.springframework.integration.core.MessageSelector;
import org.springframework.integration.filter.MessageFilter;
import org.springframework.integration.filter.MethodInvokingSelector;
import org.springframework.integration.util.MessagingAnnotationUtils;
import org.springframework.messaging.MessageHandler;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class FilterAnnotationPostProcessor
extends AbstractMethodAnnotationPostProcessor<Filter> {
    public FilterAnnotationPostProcessor(ConfigurableListableBeanFactory beanFactory) {
        super(beanFactory);
        this.messageHandlerAttributes.addAll(Arrays.asList("discardChannel", "throwExceptionOnRejection", "adviceChain", "discardWithinAdvice"));
    }

    @Override
    protected MessageHandler createHandler(Object bean, Method method, List<Annotation> annotations) {
        String discardChannelName;
        String throwExceptionOnRejection;
        MessageSelector selector;
        if (AnnotatedElementUtils.isAnnotated((AnnotatedElement)method, (String)Bean.class.getName())) {
            Object target = this.resolveTargetBeanFromMethodWithBeanAnnotation(method);
            if (target instanceof MessageSelector) {
                selector = (MessageSelector)target;
            } else {
                if (this.extractTypeIfPossible(target, MessageFilter.class) != null) {
                    this.checkMessageHandlerAttributes(this.resolveTargetBeanName(method), annotations);
                    return (MessageHandler)target;
                }
                selector = new MethodInvokingSelector(target);
            }
        } else {
            Assert.isTrue((Boolean.TYPE.equals(method.getReturnType()) || Boolean.class.equals(method.getReturnType()) ? 1 : 0) != 0, (String)"The Filter annotation may only be applied to methods with a boolean return type.");
            selector = new MethodInvokingSelector(bean, method);
        }
        MessageFilter filter2 = new MessageFilter(selector);
        String discardWithinAdvice = MessagingAnnotationUtils.resolveAttribute(annotations, "discardWithinAdvice", String.class);
        if (StringUtils.hasText((String)discardWithinAdvice)) {
            filter2.setDiscardWithinAdvice(this.resolveAttributeToBoolean(discardWithinAdvice));
        }
        if (StringUtils.hasText((String)(throwExceptionOnRejection = MessagingAnnotationUtils.resolveAttribute(annotations, "throwExceptionOnRejection", String.class)))) {
            filter2.setThrowExceptionOnRejection(this.resolveAttributeToBoolean(throwExceptionOnRejection));
        }
        if (StringUtils.hasText((String)(discardChannelName = MessagingAnnotationUtils.resolveAttribute(annotations, "discardChannel", String.class)))) {
            filter2.setDiscardChannelName(discardChannelName);
        }
        this.setOutputChannelIfPresent(annotations, filter2);
        return filter2;
    }
}

