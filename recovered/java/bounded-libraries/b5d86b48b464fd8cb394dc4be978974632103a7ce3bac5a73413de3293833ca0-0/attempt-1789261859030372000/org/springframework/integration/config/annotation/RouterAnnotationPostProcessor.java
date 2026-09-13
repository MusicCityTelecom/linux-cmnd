/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 *  org.springframework.context.annotation.Bean
 *  org.springframework.core.annotation.AnnotatedElementUtils
 *  org.springframework.core.convert.TypeDescriptor
 *  org.springframework.messaging.MessageHandler
 *  org.springframework.util.Assert
 *  org.springframework.util.ObjectUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.config.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.integration.annotation.Router;
import org.springframework.integration.config.annotation.AbstractMethodAnnotationPostProcessor;
import org.springframework.integration.router.AbstractMessageRouter;
import org.springframework.integration.router.MethodInvokingRouter;
import org.springframework.integration.util.MessagingAnnotationUtils;
import org.springframework.messaging.MessageHandler;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class RouterAnnotationPostProcessor
extends AbstractMethodAnnotationPostProcessor<Router> {
    public RouterAnnotationPostProcessor(ConfigurableListableBeanFactory beanFactory) {
        super(beanFactory);
        this.messageHandlerAttributes.addAll(Arrays.asList("defaultOutputChannel", "applySequence", "ignoreSendFailures", "resolutionRequired", "channelMappings", "prefix", "suffix"));
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected MessageHandler createHandler(Object bean, Method method, List<Annotation> annotations) {
        String ignoreSendFailures;
        String applySequence;
        String defaultOutputChannelName;
        AbstractMessageRouter router;
        if (AnnotatedElementUtils.isAnnotated((AnnotatedElement)method, (String)Bean.class.getName())) {
            Object target = this.resolveTargetBeanFromMethodWithBeanAnnotation(method);
            router = this.extractTypeIfPossible(target, AbstractMessageRouter.class);
            if (router != null) {
                this.checkMessageHandlerAttributes(this.resolveTargetBeanName(method), annotations);
                return router;
            }
            if (target instanceof MessageHandler) {
                Assert.isTrue((boolean)this.routerAttributesProvided(annotations), (String)("'defaultOutputChannel', 'applySequence', 'ignoreSendFailures', 'resolutionRequired', 'channelMappings', 'prefix' and 'suffix' can be applied to 'AbstractMessageRouter' implementations, but target handler is: " + target.getClass()));
                return (MessageHandler)target;
            }
            router = new MethodInvokingRouter(target);
        } else {
            router = new MethodInvokingRouter(bean, method);
        }
        if (StringUtils.hasText((String)(defaultOutputChannelName = MessagingAnnotationUtils.resolveAttribute(annotations, "defaultOutputChannel", String.class)))) {
            router.setDefaultOutputChannelName(defaultOutputChannelName);
        }
        if (StringUtils.hasText((String)(applySequence = MessagingAnnotationUtils.resolveAttribute(annotations, "applySequence", String.class)))) {
            router.setApplySequence(this.resolveAttributeToBoolean(applySequence));
        }
        if (StringUtils.hasText((String)(ignoreSendFailures = MessagingAnnotationUtils.resolveAttribute(annotations, "ignoreSendFailures", String.class)))) {
            router.setIgnoreSendFailures(this.resolveAttributeToBoolean(ignoreSendFailures));
        }
        this.routerAttributes(annotations, router);
        return router;
    }

    private void routerAttributes(List<Annotation> annotations, AbstractMessageRouter router) {
        if (this.routerAttributesProvided(annotations)) {
            Object[] channelMappings;
            String suffix;
            String prefix;
            MethodInvokingRouter methodInvokingRouter = (MethodInvokingRouter)router;
            String resolutionRequired = MessagingAnnotationUtils.resolveAttribute(annotations, "resolutionRequired", String.class);
            if (StringUtils.hasText((String)resolutionRequired)) {
                methodInvokingRouter.setResolutionRequired(this.resolveAttributeToBoolean(resolutionRequired));
            }
            if (StringUtils.hasText((String)(prefix = MessagingAnnotationUtils.resolveAttribute(annotations, "prefix", String.class)))) {
                methodInvokingRouter.setPrefix(this.beanFactory.resolveEmbeddedValue(prefix));
            }
            if (StringUtils.hasText((String)(suffix = MessagingAnnotationUtils.resolveAttribute(annotations, "suffix", String.class)))) {
                methodInvokingRouter.setSuffix(this.beanFactory.resolveEmbeddedValue(suffix));
            }
            if (!ObjectUtils.isEmpty((Object[])(channelMappings = MessagingAnnotationUtils.resolveAttribute(annotations, "channelMappings", String[].class)))) {
                StringBuilder mappings = new StringBuilder();
                for (Object channelMapping : channelMappings) {
                    mappings.append((String)channelMapping).append("\n");
                }
                Properties properties = (Properties)this.conversionService.convert((Object)mappings.toString(), TypeDescriptor.valueOf(String.class), TypeDescriptor.valueOf(Properties.class));
                methodInvokingRouter.replaceChannelMappings(properties);
            }
        }
    }

    private boolean routerAttributesProvided(List<Annotation> annotations) {
        String defaultOutputChannel = MessagingAnnotationUtils.resolveAttribute(annotations, "defaultOutputChannel", String.class);
        Object[] channelMappings = MessagingAnnotationUtils.resolveAttribute(annotations, "channelMappings", String[].class);
        String prefix = MessagingAnnotationUtils.resolveAttribute(annotations, "prefix", String.class);
        String suffix = MessagingAnnotationUtils.resolveAttribute(annotations, "suffix", String.class);
        String resolutionRequired = MessagingAnnotationUtils.resolveAttribute(annotations, "resolutionRequired", String.class);
        String applySequence = MessagingAnnotationUtils.resolveAttribute(annotations, "applySequence", String.class);
        String ignoreSendFailures = MessagingAnnotationUtils.resolveAttribute(annotations, "ignoreSendFailures", String.class);
        return StringUtils.hasText((String)defaultOutputChannel) || !ObjectUtils.isEmpty((Object[])channelMappings) || StringUtils.hasText((String)prefix) || StringUtils.hasText((String)suffix) || StringUtils.hasText((String)resolutionRequired) || StringUtils.hasText((String)applySequence) || StringUtils.hasText((String)ignoreSendFailures);
    }
}

