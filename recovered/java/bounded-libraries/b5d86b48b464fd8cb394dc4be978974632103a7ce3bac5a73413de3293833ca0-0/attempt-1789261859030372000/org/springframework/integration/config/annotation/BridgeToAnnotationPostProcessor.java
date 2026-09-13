/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 *  org.springframework.context.annotation.Bean
 *  org.springframework.core.annotation.AnnotatedElementUtils
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.MessageHandler
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.config.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.List;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.integration.annotation.BridgeFrom;
import org.springframework.integration.annotation.BridgeTo;
import org.springframework.integration.config.annotation.AbstractMethodAnnotationPostProcessor;
import org.springframework.integration.endpoint.AbstractEndpoint;
import org.springframework.integration.handler.BridgeHandler;
import org.springframework.integration.util.MessagingAnnotationUtils;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class BridgeToAnnotationPostProcessor
extends AbstractMethodAnnotationPostProcessor<BridgeTo> {
    public BridgeToAnnotationPostProcessor(ConfigurableListableBeanFactory beanFactory) {
        super(beanFactory);
    }

    @Override
    public boolean shouldCreateEndpoint(Method method, List<Annotation> annotations) {
        boolean isBean = AnnotatedElementUtils.isAnnotated((AnnotatedElement)method, (String)Bean.class.getName());
        Assert.isTrue((boolean)isBean, (String)"'@BridgeTo' is eligible only for '@Bean' methods");
        boolean isMessageChannelBean = MessageChannel.class.isAssignableFrom(method.getReturnType());
        Assert.isTrue((boolean)isMessageChannelBean, (String)"'@BridgeTo' is eligible only for 'MessageChannel' '@Bean' methods");
        boolean hasBridgeFrom = AnnotatedElementUtils.isAnnotated((AnnotatedElement)method, (String)BridgeFrom.class.getName());
        Assert.isTrue((!hasBridgeFrom ? 1 : 0) != 0, (String)"'@BridgeFrom' and '@BridgeTo' are mutually exclusive 'MessageChannel' '@Bean' method annotations");
        return true;
    }

    @Override
    protected MessageHandler createHandler(Object bean, Method method, List<Annotation> annotations) {
        BridgeHandler handler = new BridgeHandler();
        String outputChannelName = MessagingAnnotationUtils.resolveAttribute(annotations, "value", String.class);
        if (StringUtils.hasText((String)outputChannelName)) {
            handler.setOutputChannelName(outputChannelName);
        }
        return handler;
    }

    @Override
    protected AbstractEndpoint createEndpoint(MessageHandler handler, Method method, List<Annotation> annotations) {
        Object inputChannel = this.resolveTargetBeanFromMethodWithBeanAnnotation(method);
        Assert.isInstanceOf(MessageChannel.class, (Object)inputChannel);
        return this.doCreateEndpoint(handler, (MessageChannel)inputChannel, annotations);
    }
}

