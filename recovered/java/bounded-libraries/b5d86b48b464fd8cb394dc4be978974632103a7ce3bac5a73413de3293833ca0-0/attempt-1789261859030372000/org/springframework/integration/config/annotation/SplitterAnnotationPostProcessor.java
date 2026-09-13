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
import org.springframework.integration.annotation.Splitter;
import org.springframework.integration.config.annotation.AbstractMethodAnnotationPostProcessor;
import org.springframework.integration.splitter.AbstractMessageSplitter;
import org.springframework.integration.splitter.MethodInvokingSplitter;
import org.springframework.integration.util.MessagingAnnotationUtils;
import org.springframework.messaging.MessageHandler;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class SplitterAnnotationPostProcessor
extends AbstractMethodAnnotationPostProcessor<Splitter> {
    public SplitterAnnotationPostProcessor(ConfigurableListableBeanFactory beanFactory) {
        super(beanFactory);
        this.messageHandlerAttributes.addAll(Arrays.asList("outputChannel", "applySequence", "adviceChain"));
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected MessageHandler createHandler(Object bean, Method method, List<Annotation> annotations) {
        AbstractMessageSplitter splitter;
        String applySequence = MessagingAnnotationUtils.resolveAttribute(annotations, "applySequence", String.class);
        if (AnnotatedElementUtils.isAnnotated((AnnotatedElement)method, (String)Bean.class.getName())) {
            Object target = this.resolveTargetBeanFromMethodWithBeanAnnotation(method);
            splitter = this.extractTypeIfPossible(target, AbstractMessageSplitter.class);
            if (splitter != null) {
                this.checkMessageHandlerAttributes(this.resolveTargetBeanName(method), annotations);
                return splitter;
            }
            if (target instanceof MessageHandler) {
                Assert.hasText((String)applySequence, (String)("'applySequence' can be applied to 'AbstractMessageSplitter', but target handler is: " + target.getClass()));
                return (MessageHandler)target;
            }
            splitter = new MethodInvokingSplitter(target);
        } else {
            splitter = new MethodInvokingSplitter(bean, method);
        }
        if (StringUtils.hasText((String)applySequence)) {
            splitter.setApplySequence(this.resolveAttributeToBoolean(applySequence));
        }
        this.setOutputChannelIfPresent(annotations, splitter);
        return splitter;
    }
}

