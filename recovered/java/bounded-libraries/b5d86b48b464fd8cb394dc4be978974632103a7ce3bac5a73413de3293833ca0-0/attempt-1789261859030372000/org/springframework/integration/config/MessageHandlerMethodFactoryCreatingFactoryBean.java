/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.beans.factory.BeanInitializationException
 *  org.springframework.beans.factory.FactoryBean
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.messaging.converter.MessageConverter
 *  org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory
 *  org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory
 *  org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver
 */
package org.springframework.integration.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.integration.handler.support.CollectionArgumentResolver;
import org.springframework.integration.handler.support.MapArgumentResolver;
import org.springframework.integration.handler.support.PayloadExpressionArgumentResolver;
import org.springframework.integration.handler.support.PayloadsArgumentResolver;
import org.springframework.integration.support.NullAwarePayloadArgumentResolver;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;

class MessageHandlerMethodFactoryCreatingFactoryBean
implements FactoryBean<MessageHandlerMethodFactory>,
BeanFactoryAware {
    private final boolean listCapable;
    private MessageConverter argumentResolverMessageConverter;
    private BeanFactory beanFactory;

    MessageHandlerMethodFactoryCreatingFactoryBean(boolean listCapable) {
        this.listCapable = listCapable;
    }

    public void setArgumentResolverMessageConverter(MessageConverter argumentResolverMessageConverter) {
        this.argumentResolverMessageConverter = argumentResolverMessageConverter;
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public Class<?> getObjectType() {
        return MessageHandlerMethodFactory.class;
    }

    public MessageHandlerMethodFactory getObject() {
        DefaultMessageHandlerMethodFactory handlerMethodFactory = new DefaultMessageHandlerMethodFactory();
        handlerMethodFactory.setBeanFactory(this.beanFactory);
        handlerMethodFactory.setMessageConverter(this.argumentResolverMessageConverter);
        handlerMethodFactory.setCustomArgumentResolvers(this.buildArgumentResolvers(this.listCapable));
        handlerMethodFactory.afterPropertiesSet();
        return handlerMethodFactory;
    }

    private List<HandlerMethodArgumentResolver> buildArgumentResolvers(boolean listCapable) {
        ArrayList<HandlerMethodArgumentResolver> resolvers = new ArrayList<HandlerMethodArgumentResolver>();
        resolvers.add(new PayloadExpressionArgumentResolver());
        resolvers.add((HandlerMethodArgumentResolver)new NullAwarePayloadArgumentResolver(this.argumentResolverMessageConverter));
        resolvers.add(new PayloadsArgumentResolver());
        if (listCapable) {
            resolvers.add(new CollectionArgumentResolver(true));
        }
        resolvers.add(new MapArgumentResolver());
        for (HandlerMethodArgumentResolver resolver : resolvers) {
            if (resolver instanceof BeanFactoryAware) {
                ((BeanFactoryAware)resolver).setBeanFactory(this.beanFactory);
            }
            if (!(resolver instanceof InitializingBean)) continue;
            try {
                ((InitializingBean)resolver).afterPropertiesSet();
            }
            catch (Exception ex) {
                throw new BeanInitializationException("Cannot initialize 'HandlerMethodArgumentResolver'", (Throwable)ex);
            }
        }
        return resolvers;
    }
}

