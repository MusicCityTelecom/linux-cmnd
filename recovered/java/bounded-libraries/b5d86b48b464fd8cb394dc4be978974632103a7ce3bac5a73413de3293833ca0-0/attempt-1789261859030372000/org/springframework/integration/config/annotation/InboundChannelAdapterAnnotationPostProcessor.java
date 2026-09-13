/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.NoSuchBeanDefinitionException
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 *  org.springframework.beans.factory.support.RootBeanDefinition
 *  org.springframework.context.annotation.Bean
 *  org.springframework.core.annotation.AnnotatedElementUtils
 *  org.springframework.messaging.MessageHandler
 *  org.springframework.util.Assert
 */
package org.springframework.integration.config.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Supplier;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.config.annotation.AbstractMethodAnnotationPostProcessor;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.endpoint.MethodInvokingMessageSource;
import org.springframework.integration.endpoint.SourcePollingChannelAdapter;
import org.springframework.integration.util.ClassUtils;
import org.springframework.integration.util.MessagingAnnotationUtils;
import org.springframework.messaging.MessageHandler;
import org.springframework.util.Assert;

public class InboundChannelAdapterAnnotationPostProcessor
extends AbstractMethodAnnotationPostProcessor<InboundChannelAdapter> {
    public InboundChannelAdapterAnnotationPostProcessor(ConfigurableListableBeanFactory beanFactory) {
        super(beanFactory);
    }

    @Override
    protected String getInputChannelAttribute() {
        return "value";
    }

    @Override
    public Object postProcess(Object bean, String beanName, Method method, List<Annotation> annotations) {
        MessageSource<?> messageSource;
        String channelName = MessagingAnnotationUtils.resolveAttribute(annotations, "value", String.class);
        Assert.hasText((String)channelName, (String)"The channel ('value' attribute of @InboundChannelAdapter) can't be empty.");
        try {
            messageSource = this.createMessageSource(bean, beanName, method);
        }
        catch (NoSuchBeanDefinitionException e) {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug((Object)("Skipping endpoint creation; " + e.getMessage() + "; perhaps due to some '@Conditional' annotation."));
            }
            return null;
        }
        SourcePollingChannelAdapter adapter = new SourcePollingChannelAdapter();
        adapter.setOutputChannelName(channelName);
        adapter.setSource(messageSource);
        Poller[] pollers = MessagingAnnotationUtils.resolveAttribute(annotations, "poller", Poller[].class);
        this.configurePollingEndpoint(adapter, pollers);
        return adapter;
    }

    private MessageSource<?> createMessageSource(Object beanArg, String beanNameArg, Method methodArg) {
        MessageSource messageSource = null;
        Object bean = beanArg;
        Method method = methodArg;
        String beanName = beanNameArg;
        if (AnnotatedElementUtils.isAnnotated((AnnotatedElement)method, (String)Bean.class.getName())) {
            Object target = this.resolveTargetBeanFromMethodWithBeanAnnotation(method);
            Class<?> targetClass = target.getClass();
            Assert.isTrue((MessageSource.class.isAssignableFrom(targetClass) || Supplier.class.isAssignableFrom(targetClass) || ClassUtils.isKotlinFunction0(targetClass) ? 1 : 0) != 0, () -> "The '" + this.annotationType + "' on @Bean method level is allowed only for: " + MessageSource.class.getName() + ", or " + Supplier.class.getName() + (ClassUtils.KOTLIN_FUNCTION_0_CLASS != null ? ", or " + ClassUtils.KOTLIN_FUNCTION_0_CLASS.getName() : "") + " beans");
            if (target instanceof MessageSource) {
                messageSource = (MessageSource)target;
            } else if (target instanceof Supplier) {
                method = ClassUtils.SUPPLIER_GET_METHOD;
                bean = target;
                beanName = beanName + '.' + methodArg.getName();
            } else if (ClassUtils.KOTLIN_FUNCTION_0_INVOKE_METHOD != null) {
                method = ClassUtils.KOTLIN_FUNCTION_0_INVOKE_METHOD;
                bean = target;
                beanName = beanName + '.' + methodArg.getName();
            }
        }
        if (messageSource == null) {
            MethodInvokingMessageSource methodInvokingMessageSource = new MethodInvokingMessageSource();
            methodInvokingMessageSource.setObject(bean);
            methodInvokingMessageSource.setMethod(method);
            String messageSourceBeanName = this.generateHandlerBeanName(beanName, method);
            this.definitionRegistry.registerBeanDefinition(messageSourceBeanName, (BeanDefinition)new RootBeanDefinition(MethodInvokingMessageSource.class, () -> methodInvokingMessageSource));
            messageSource = (MessageSource)this.beanFactory.getBean(messageSourceBeanName, MessageSource.class);
        }
        return messageSource;
    }

    @Override
    protected String generateHandlerBeanName(String originalBeanName, Method method) {
        return super.generateHandlerBeanName(originalBeanName, method).replaceFirst(".handler$", ".source");
    }

    @Override
    protected MessageHandler createHandler(Object bean, Method method, List<Annotation> annotations) {
        throw new UnsupportedOperationException();
    }
}

