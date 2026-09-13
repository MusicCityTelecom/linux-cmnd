/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.beans.factory.config.ConfigurableBeanFactory
 *  org.springframework.core.convert.ConversionService
 *  org.springframework.format.support.DefaultFormattingConversionService
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 *  org.springframework.validation.Validator
 */
package org.springframework.messaging.handler.annotation.support;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.lang.Nullable;
import org.springframework.messaging.converter.GenericMessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.annotation.support.HeaderMethodArgumentResolver;
import org.springframework.messaging.handler.annotation.support.HeadersMethodArgumentResolver;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageMethodArgumentResolver;
import org.springframework.messaging.handler.annotation.support.PayloadMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolverComposite;
import org.springframework.messaging.handler.invocation.InvocableHandlerMethod;
import org.springframework.util.Assert;
import org.springframework.validation.Validator;

public class DefaultMessageHandlerMethodFactory
implements MessageHandlerMethodFactory,
BeanFactoryAware,
InitializingBean {
    private ConversionService conversionService = new DefaultFormattingConversionService();
    @Nullable
    private MessageConverter messageConverter;
    @Nullable
    private Validator validator;
    @Nullable
    private List<HandlerMethodArgumentResolver> customArgumentResolvers;
    private final HandlerMethodArgumentResolverComposite argumentResolvers = new HandlerMethodArgumentResolverComposite();
    @Nullable
    private BeanFactory beanFactory;

    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    public void setMessageConverter(MessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public void setCustomArgumentResolvers(List<HandlerMethodArgumentResolver> customArgumentResolvers) {
        this.customArgumentResolvers = customArgumentResolvers;
    }

    public void setArgumentResolvers(@Nullable List<HandlerMethodArgumentResolver> argumentResolvers) {
        if (argumentResolvers == null) {
            this.argumentResolvers.clear();
            return;
        }
        this.argumentResolvers.addResolvers(argumentResolvers);
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void afterPropertiesSet() {
        if (this.messageConverter == null) {
            this.messageConverter = new GenericMessageConverter(this.conversionService);
        }
        if (this.argumentResolvers.getResolvers().isEmpty()) {
            this.argumentResolvers.addResolvers(this.initArgumentResolvers());
        }
    }

    @Override
    public InvocableHandlerMethod createInvocableHandlerMethod(Object bean, Method method) {
        InvocableHandlerMethod handlerMethod = new InvocableHandlerMethod(bean, method);
        handlerMethod.setMessageMethodArgumentResolvers(this.argumentResolvers);
        return handlerMethod;
    }

    protected List<HandlerMethodArgumentResolver> initArgumentResolvers() {
        ArrayList<HandlerMethodArgumentResolver> resolvers = new ArrayList<HandlerMethodArgumentResolver>();
        ConfigurableBeanFactory beanFactory = this.beanFactory instanceof ConfigurableBeanFactory ? (ConfigurableBeanFactory)this.beanFactory : null;
        resolvers.add(new HeaderMethodArgumentResolver(this.conversionService, beanFactory));
        resolvers.add(new HeadersMethodArgumentResolver());
        resolvers.add(new MessageMethodArgumentResolver(this.messageConverter));
        if (this.customArgumentResolvers != null) {
            resolvers.addAll(this.customArgumentResolvers);
        }
        Assert.notNull((Object)this.messageConverter, (String)"MessageConverter not configured");
        resolvers.add(new PayloadMethodArgumentResolver(this.messageConverter, this.validator));
        return resolvers;
    }
}

