/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.aopalliance.aop.Advice
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.aop.framework.Advised
 *  org.springframework.aop.framework.AopProxyUtils
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.beans.factory.BeanInitializationException
 *  org.springframework.beans.factory.BeanNameAware
 *  org.springframework.beans.factory.FactoryBean
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationContextAware
 *  org.springframework.context.ApplicationEventPublisher
 *  org.springframework.context.ApplicationEventPublisherAware
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.MessageHandler
 *  org.springframework.messaging.core.DestinationResolver
 *  org.springframework.util.Assert
 *  org.springframework.util.CollectionUtils
 */
package org.springframework.integration.config;

import java.util.List;
import org.aopalliance.aop.Advice;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.integration.JavaUtils;
import org.springframework.integration.context.IntegrationObjectSupport;
import org.springframework.integration.context.Orderable;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.handler.AbstractMessageProducingHandler;
import org.springframework.integration.handler.AbstractReplyProducingMessageHandler;
import org.springframework.integration.support.context.NamedComponent;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.core.DestinationResolver;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

public abstract class AbstractSimpleMessageHandlerFactoryBean<H extends MessageHandler>
implements FactoryBean<MessageHandler>,
ApplicationContextAware,
BeanFactoryAware,
BeanNameAware,
ApplicationEventPublisherAware {
    protected final Log logger = LogFactory.getLog(this.getClass());
    private final Object initializationMonitor = new Object();
    private BeanFactory beanFactory;
    private H handler;
    private MessageChannel outputChannel;
    private String outputChannelName;
    private Integer order;
    private List<Advice> adviceChain;
    private String componentName;
    private ApplicationContext applicationContext;
    private String beanName;
    private ApplicationEventPublisher applicationEventPublisher;
    private DestinationResolver<MessageChannel> channelResolver;
    private Boolean async;
    private boolean initialized;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void setChannelResolver(DestinationResolver<MessageChannel> channelResolver) {
        this.channelResolver = channelResolver;
    }

    public void setOutputChannel(MessageChannel outputChannel) {
        this.outputChannel = outputChannel;
    }

    public void setOutputChannelName(String outputChannelName) {
        this.outputChannelName = outputChannelName;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    protected BeanFactory getBeanFactory() {
        return this.beanFactory;
    }

    public void setAdviceChain(List<Advice> adviceChain) {
        this.adviceChain = adviceChain;
    }

    public void setAsync(Boolean async) {
        this.async = async;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public H getObject() {
        if (this.handler == null) {
            this.handler = this.createHandlerInternal();
            Assert.notNull(this.handler, (String)"failed to create MessageHandler");
        }
        return this.handler;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected final H createHandlerInternal() {
        Object object = this.initializationMonitor;
        synchronized (object) {
            if (this.initialized) {
                return null;
            }
            this.handler = this.createHandler();
            JavaUtils.INSTANCE.acceptIfCondition(this.handler instanceof ApplicationContextAware && this.applicationContext != null, this.applicationContext, context -> ((ApplicationContextAware)this.handler).setApplicationContext(this.applicationContext)).acceptIfCondition(this.handler instanceof BeanFactoryAware && this.getBeanFactory() != null, this.getBeanFactory(), factory -> ((BeanFactoryAware)this.handler).setBeanFactory(factory)).acceptIfCondition(this.handler instanceof BeanNameAware && this.beanName != null, this.beanName, name -> ((BeanNameAware)this.handler).setBeanName(this.beanName)).acceptIfCondition(this.handler instanceof ApplicationEventPublisherAware && this.applicationEventPublisher != null, this.applicationEventPublisher, publisher -> ((ApplicationEventPublisherAware)this.handler).setApplicationEventPublisher(publisher));
            this.configureOutputChannelIfAny();
            Object actualHandler = AbstractSimpleMessageHandlerFactoryBean.extractTarget(this.handler);
            if (actualHandler == null) {
                actualHandler = this.handler;
            }
            Object handlerToConfigure = actualHandler;
            this.integrationObjectSupport(actualHandler, handlerToConfigure);
            this.adviceChain(actualHandler);
            JavaUtils.INSTANCE.acceptIfCondition(this.async != null && actualHandler instanceof AbstractMessageProducingHandler, this.async, asyncValue -> ((AbstractMessageProducingHandler)handlerToConfigure).setAsync((boolean)asyncValue)).acceptIfCondition(this.handler instanceof Orderable && this.order != null, this.order, theOrder -> ((Orderable)this.handler).setOrder((int)theOrder));
            this.initialized = true;
        }
        this.initializingBean();
        return this.handler;
    }

    private void integrationObjectSupport(Object actualHandler, Object handlerToConfigure) {
        if (actualHandler instanceof IntegrationObjectSupport) {
            JavaUtils.INSTANCE.acceptIfNotNull(this.componentName, name -> ((IntegrationObjectSupport)handlerToConfigure).setComponentName((String)name)).acceptIfNotNull(this.channelResolver, resolver -> ((IntegrationObjectSupport)handlerToConfigure).setChannelResolver((DestinationResolver<MessageChannel>)resolver));
        }
    }

    private void adviceChain(Object actualHandler) {
        if (!CollectionUtils.isEmpty(this.adviceChain)) {
            if (actualHandler instanceof AbstractReplyProducingMessageHandler) {
                ((AbstractReplyProducingMessageHandler)actualHandler).setAdviceChain(this.adviceChain);
            } else if (this.logger.isDebugEnabled()) {
                String name = this.componentName;
                if (name == null && actualHandler instanceof NamedComponent) {
                    name = ((NamedComponent)actualHandler).getBeanName();
                }
                this.logger.debug((Object)("adviceChain can only be set on an AbstractReplyProducingMessageHandler" + (name == null ? "" : ", " + name) + "."));
            }
        }
    }

    private void initializingBean() {
        if (this.handler instanceof InitializingBean) {
            try {
                ((InitializingBean)this.handler).afterPropertiesSet();
            }
            catch (Exception e) {
                throw new BeanInitializationException("failed to initialize MessageHandler", (Throwable)e);
            }
        }
    }

    private void configureOutputChannelIfAny() {
        if (this.handler instanceof MessageProducer) {
            MessageProducer messageProducer = (MessageProducer)this.handler;
            if (this.outputChannel != null) {
                messageProducer.setOutputChannel(this.outputChannel);
            } else if (this.outputChannelName != null) {
                messageProducer.setOutputChannelName(this.outputChannelName);
            }
        }
    }

    protected abstract H createHandler();

    public Class<? extends MessageHandler> getObjectType() {
        if (this.handler != null) {
            return this.handler.getClass();
        }
        return this.getPreCreationHandlerType();
    }

    protected Class<? extends MessageHandler> getPreCreationHandlerType() {
        return MessageHandler.class;
    }

    public boolean isSingleton() {
        return true;
    }

    private static Object extractTarget(Object object) {
        if (!(object instanceof Advised)) {
            return object;
        }
        return AbstractSimpleMessageHandlerFactoryBean.extractTarget(AopProxyUtils.getSingletonTarget((Object)object));
    }
}

