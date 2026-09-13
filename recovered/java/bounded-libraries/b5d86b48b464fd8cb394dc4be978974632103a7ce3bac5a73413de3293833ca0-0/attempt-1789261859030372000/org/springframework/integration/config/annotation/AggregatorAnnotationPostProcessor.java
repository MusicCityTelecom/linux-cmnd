/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 *  org.springframework.messaging.MessageHandler
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.config.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.integration.aggregator.AggregatingMessageHandler;
import org.springframework.integration.aggregator.MethodInvokingCorrelationStrategy;
import org.springframework.integration.aggregator.MethodInvokingMessageGroupProcessor;
import org.springframework.integration.aggregator.MethodInvokingReleaseStrategy;
import org.springframework.integration.annotation.Aggregator;
import org.springframework.integration.annotation.CorrelationStrategy;
import org.springframework.integration.annotation.ReleaseStrategy;
import org.springframework.integration.config.annotation.AbstractMethodAnnotationPostProcessor;
import org.springframework.integration.store.SimpleMessageStore;
import org.springframework.integration.util.MessagingAnnotationUtils;
import org.springframework.messaging.MessageHandler;
import org.springframework.util.StringUtils;

public class AggregatorAnnotationPostProcessor
extends AbstractMethodAnnotationPostProcessor<Aggregator> {
    public AggregatorAnnotationPostProcessor(ConfigurableListableBeanFactory beanFactory) {
        super(beanFactory);
    }

    @Override
    protected MessageHandler createHandler(Object bean, Method method, List<Annotation> annotations) {
        String sendPartialResultsOnExpiry;
        String outputChannelName;
        MethodInvokingMessageGroupProcessor processor = new MethodInvokingMessageGroupProcessor(bean, method);
        MethodInvokingReleaseStrategy releaseStrategy = null;
        Method releaseStrategyMethod = MessagingAnnotationUtils.findAnnotatedMethod(bean, ReleaseStrategy.class);
        if (releaseStrategyMethod != null) {
            releaseStrategy = new MethodInvokingReleaseStrategy(bean, releaseStrategyMethod);
        }
        MethodInvokingCorrelationStrategy correlationStrategy = null;
        Method correlationStrategyMethod = MessagingAnnotationUtils.findAnnotatedMethod(bean, CorrelationStrategy.class);
        if (correlationStrategyMethod != null) {
            correlationStrategy = new MethodInvokingCorrelationStrategy(bean, correlationStrategyMethod);
        }
        AggregatingMessageHandler handler = new AggregatingMessageHandler(processor, new SimpleMessageStore(), correlationStrategy, releaseStrategy);
        String discardChannelName = MessagingAnnotationUtils.resolveAttribute(annotations, "discardChannel", String.class);
        if (StringUtils.hasText((String)discardChannelName)) {
            handler.setDiscardChannelName(discardChannelName);
        }
        if (StringUtils.hasText((String)(outputChannelName = MessagingAnnotationUtils.resolveAttribute(annotations, "outputChannel", String.class)))) {
            handler.setOutputChannelName(outputChannelName);
        }
        if ((sendPartialResultsOnExpiry = MessagingAnnotationUtils.resolveAttribute(annotations, "sendPartialResultsOnExpiry", String.class)) != null) {
            handler.setSendPartialResultOnExpiry(this.resolveAttributeToBoolean(sendPartialResultsOnExpiry));
        }
        return handler;
    }

    @Override
    protected boolean beanAnnotationAware() {
        return false;
    }
}

