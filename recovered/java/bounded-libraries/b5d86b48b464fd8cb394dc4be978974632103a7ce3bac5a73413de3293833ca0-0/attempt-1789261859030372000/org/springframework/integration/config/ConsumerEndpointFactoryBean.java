/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.aopalliance.aop.Advice
 *  org.apache.commons.logging.LogFactory
 *  org.reactivestreams.Publisher
 *  org.springframework.aop.Advisor
 *  org.springframework.aop.framework.Advised
 *  org.springframework.aop.framework.ProxyFactory
 *  org.springframework.aop.support.AopUtils
 *  org.springframework.aop.support.NameMatchMethodPointcutAdvisor
 *  org.springframework.beans.factory.BeanClassLoaderAware
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.beans.factory.BeanNameAware
 *  org.springframework.beans.factory.DisposableBean
 *  org.springframework.beans.factory.FactoryBean
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.beans.factory.config.ConfigurableBeanFactory
 *  org.springframework.context.SmartLifecycle
 *  org.springframework.core.log.LogAccessor
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.MessageHandler
 *  org.springframework.messaging.PollableChannel
 *  org.springframework.messaging.ReactiveMessageHandler
 *  org.springframework.messaging.SubscribableChannel
 *  org.springframework.messaging.core.DestinationResolver
 *  org.springframework.scheduling.TaskScheduler
 *  org.springframework.util.Assert
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.StringUtils
 *  reactor.core.publisher.Flux
 */
package org.springframework.integration.config;

import java.util.List;
import java.util.function.Function;
import org.aopalliance.aop.Advice;
import org.apache.commons.logging.LogFactory;
import org.reactivestreams.Publisher;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.aop.support.NameMatchMethodPointcutAdvisor;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.SmartLifecycle;
import org.springframework.core.log.LogAccessor;
import org.springframework.integration.channel.FixedSubscriberChannel;
import org.springframework.integration.context.IntegrationObjectSupport;
import org.springframework.integration.endpoint.AbstractEndpoint;
import org.springframework.integration.endpoint.EventDrivenConsumer;
import org.springframework.integration.endpoint.PollingConsumer;
import org.springframework.integration.endpoint.ReactiveStreamsConsumer;
import org.springframework.integration.handler.AbstractReplyProducingMessageHandler;
import org.springframework.integration.handler.ReactiveMessageHandlerAdapter;
import org.springframework.integration.handler.advice.HandleMessageAdvice;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.integration.support.channel.ChannelResolverUtils;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.PollableChannel;
import org.springframework.messaging.ReactiveMessageHandler;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.core.DestinationResolver;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;

public class ConsumerEndpointFactoryBean
implements FactoryBean<AbstractEndpoint>,
BeanFactoryAware,
BeanNameAware,
BeanClassLoaderAware,
InitializingBean,
SmartLifecycle,
DisposableBean {
    private static final LogAccessor LOGGER = new LogAccessor(LogFactory.getLog(ConsumerEndpointFactoryBean.class));
    private final Object initializationMonitor = new Object();
    private final Object handlerMonitor = new Object();
    private MessageHandler handler;
    private String beanName;
    private String inputChannelName;
    private PollerMetadata pollerMetadata;
    @Nullable
    private Function<? super Flux<Message<?>>, ? extends Publisher<Message<?>>> reactiveCustomizer;
    private Boolean autoStartup;
    private int phase = 0;
    private boolean isPhaseSet;
    private String role;
    private MessageChannel inputChannel;
    private ConfigurableBeanFactory beanFactory;
    private ClassLoader beanClassLoader;
    private List<Advice> adviceChain;
    private DestinationResolver<MessageChannel> channelResolver;
    private TaskScheduler taskScheduler;
    private volatile AbstractEndpoint endpoint;
    private volatile boolean initialized;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setHandler(Object handler) {
        Assert.isTrue((handler instanceof MessageHandler || handler instanceof ReactiveMessageHandler ? 1 : 0) != 0, (String)"'handler' must be an instance of 'MessageHandler' or 'ReactiveMessageHandler'");
        Object object = this.handlerMonitor;
        synchronized (object) {
            Assert.isNull((Object)this.handler, (String)"handler cannot be overridden");
            this.handler = handler instanceof ReactiveMessageHandler ? new ReactiveMessageHandlerAdapter((ReactiveMessageHandler)handler) : (MessageHandler)handler;
        }
    }

    public MessageHandler getHandler() {
        return this.handler;
    }

    public void setInputChannel(MessageChannel inputChannel) {
        this.inputChannel = inputChannel;
    }

    public void setInputChannelName(String inputChannelName) {
        this.inputChannelName = inputChannelName;
    }

    public void setPollerMetadata(PollerMetadata pollerMetadata) {
        this.pollerMetadata = pollerMetadata;
    }

    public void setReactiveCustomizer(@Nullable Function<? super Flux<Message<?>>, ? extends Publisher<Message<?>>> reactiveCustomizer) {
        this.reactiveCustomizer = reactiveCustomizer;
    }

    public void setChannelResolver(DestinationResolver<MessageChannel> channelResolver) {
        Assert.notNull(channelResolver, (String)"'channelResolver' must not be null");
        this.channelResolver = channelResolver;
    }

    public void setBeanClassLoader(ClassLoader classLoader) {
        this.beanClassLoader = classLoader;
    }

    public void setAutoStartup(Boolean autoStartup) {
        this.autoStartup = autoStartup;
    }

    public void setPhase(int phase) {
        this.phase = phase;
        this.isPhaseSet = true;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        Assert.isInstanceOf(ConfigurableBeanFactory.class, (Object)beanFactory, (String)"a ConfigurableBeanFactory is required");
        this.beanFactory = (ConfigurableBeanFactory)beanFactory;
    }

    public void setAdviceChain(List<Advice> adviceChain) {
        Assert.notNull(adviceChain, (String)"adviceChain must not be null");
        this.adviceChain = adviceChain;
    }

    public void setTaskScheduler(TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    public void afterPropertiesSet() {
        if (this.beanName == null) {
            LOGGER.error(() -> "The MessageHandler [" + this.handler + "] will be created without a 'componentName'. Consider specifying the 'beanName' property on this ConsumerEndpointFactoryBean.");
        } else {
            this.populateComponentNameIfAny();
        }
        if (!(this.handler instanceof ReactiveMessageHandlerAdapter)) {
            this.handler = this.adviceChain(this.handler);
        } else if (!CollectionUtils.isEmpty(this.adviceChain)) {
            this.handler = new ReactiveMessageHandlerAdapter(this.adviceChain(((ReactiveMessageHandlerAdapter)this.handler).getDelegate()));
        }
        if (this.channelResolver == null) {
            this.channelResolver = ChannelResolverUtils.getChannelResolver((BeanFactory)this.beanFactory);
        }
        this.initializeEndpoint();
    }

    private void populateComponentNameIfAny() {
        try {
            if (!this.beanName.startsWith("org.springframework")) {
                Object target;
                MessageHandler targetHandler = this.handler;
                if (AopUtils.isAopProxy((Object)targetHandler) && (target = ((Advised)targetHandler).getTargetSource().getTarget()) instanceof MessageHandler) {
                    targetHandler = (MessageHandler)target;
                }
                if (targetHandler instanceof IntegrationObjectSupport) {
                    ((IntegrationObjectSupport)targetHandler).setComponentName(this.beanName);
                }
            }
        }
        catch (Exception ex) {
            LOGGER.debug(() -> "Could not set component name for handler " + this.handler + " for " + this.beanName + " :" + ex.getMessage());
        }
    }

    private <H> H adviceChain(H handler) {
        Object theHandler = handler;
        if (!CollectionUtils.isEmpty(this.adviceChain)) {
            Class targetClass = AopUtils.getTargetClass(theHandler);
            boolean replyMessageHandler = AbstractReplyProducingMessageHandler.class.isAssignableFrom(targetClass);
            for (Advice advice : this.adviceChain) {
                if (replyMessageHandler && !(advice instanceof HandleMessageAdvice)) continue;
                NameMatchMethodPointcutAdvisor handlerAdvice = new NameMatchMethodPointcutAdvisor(advice);
                handlerAdvice.addMethodName("handleMessage");
                if (theHandler instanceof Advised) {
                    ((Advised)theHandler).addAdvisor((Advisor)handlerAdvice);
                    continue;
                }
                ProxyFactory proxyFactory = new ProxyFactory(theHandler);
                proxyFactory.addAdvisor((Advisor)handlerAdvice);
                theHandler = proxyFactory.getProxy(this.beanClassLoader);
            }
        }
        return theHandler;
    }

    public AbstractEndpoint getObject() {
        if (!this.initialized) {
            this.initializeEndpoint();
        }
        return this.endpoint;
    }

    public Class<?> getObjectType() {
        if (this.endpoint == null) {
            return AbstractEndpoint.class;
        }
        return this.endpoint.getClass();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void initializeEndpoint() {
        Object object = this.initializationMonitor;
        synchronized (object) {
            if (this.initialized) {
                return;
            }
            MessageChannel channel = this.resolveInputChannel();
            Assert.state((this.reactiveCustomizer == null || this.pollerMetadata == null ? 1 : 0) != 0, (String)"The 'pollerMetadata' and 'reactiveCustomizer' are mutually exclusive.");
            if (channel instanceof Publisher || this.handler instanceof ReactiveMessageHandlerAdapter || this.reactiveCustomizer != null) {
                this.reactiveStreamsConsumer(channel);
            } else if (channel instanceof SubscribableChannel) {
                this.eventDrivenConsumer(channel);
            } else if (channel instanceof PollableChannel) {
                this.pollingConsumer(channel);
            } else {
                throw new IllegalArgumentException("Unsupported 'inputChannel' type: '" + channel.getClass().getName() + "'. Must be one of 'SubscribableChannel', 'PollableChannel' or 'ReactiveStreamsSubscribableChannel'");
            }
            this.endpoint.setBeanName(this.beanName);
            this.endpoint.setBeanFactory((BeanFactory)this.beanFactory);
            this.smartLifecycle();
            this.endpoint.setRole(this.role);
            if (this.taskScheduler != null) {
                this.endpoint.setTaskScheduler(this.taskScheduler);
            }
            this.endpoint.afterPropertiesSet();
            this.initialized = true;
        }
    }

    private MessageChannel resolveInputChannel() {
        MessageChannel channel = null;
        if (StringUtils.hasText((String)this.inputChannelName)) {
            channel = (MessageChannel)this.channelResolver.resolveDestination(this.inputChannelName);
        }
        if (this.inputChannel != null) {
            channel = this.inputChannel;
        }
        Assert.state((channel != null ? 1 : 0) != 0, (String)"one of inputChannelName or inputChannel is required");
        return channel;
    }

    private void reactiveStreamsConsumer(MessageChannel channel) {
        ReactiveStreamsConsumer reactiveStreamsConsumer = this.handler instanceof ReactiveMessageHandlerAdapter ? new ReactiveStreamsConsumer(channel, ((ReactiveMessageHandlerAdapter)this.handler).getDelegate()) : new ReactiveStreamsConsumer(channel, this.handler);
        reactiveStreamsConsumer.setReactiveCustomizer(this.reactiveCustomizer);
        this.endpoint = reactiveStreamsConsumer;
    }

    private void eventDrivenConsumer(MessageChannel channel) {
        Assert.isNull((Object)this.pollerMetadata, () -> "A poller should not be specified for endpoint '" + this.beanName + "', since '" + channel + "' is a SubscribableChannel (not pollable).");
        this.endpoint = new EventDrivenConsumer((SubscribableChannel)channel, this.handler);
        if (Boolean.FALSE.equals(this.autoStartup) && channel instanceof FixedSubscriberChannel) {
            LOGGER.info((CharSequence)"'autoStartup=\"false\"' has no effect when using a FixedSubscriberChannel");
        }
    }

    private void pollingConsumer(MessageChannel channel) {
        PollingConsumer pollingConsumer = new PollingConsumer((PollableChannel)channel, this.handler);
        if (this.pollerMetadata == null) {
            this.pollerMetadata = PollerMetadata.getDefaultPollerMetadata((BeanFactory)this.beanFactory);
            Assert.notNull((Object)this.pollerMetadata, () -> "No poller has been defined for endpoint '" + this.beanName + "', and no default poller is available within the context.");
        }
        pollingConsumer.setTaskExecutor(this.pollerMetadata.getTaskExecutor());
        pollingConsumer.setTrigger(this.pollerMetadata.getTrigger());
        pollingConsumer.setAdviceChain(this.pollerMetadata.getAdviceChain());
        pollingConsumer.setMaxMessagesPerPoll(this.pollerMetadata.getMaxMessagesPerPoll());
        pollingConsumer.setErrorHandler(this.pollerMetadata.getErrorHandler());
        pollingConsumer.setReceiveTimeout(this.pollerMetadata.getReceiveTimeout());
        pollingConsumer.setTransactionSynchronizationFactory(this.pollerMetadata.getTransactionSynchronizationFactory());
        pollingConsumer.setBeanClassLoader(this.beanClassLoader);
        pollingConsumer.setBeanFactory((BeanFactory)this.beanFactory);
        this.endpoint = pollingConsumer;
    }

    private void smartLifecycle() {
        if (this.autoStartup != null) {
            this.endpoint.setAutoStartup(this.autoStartup);
        }
        int phaseToSet = this.phase;
        if (!this.isPhaseSet) {
            phaseToSet = this.endpoint instanceof PollingConsumer ? 0x3FFFFFFF : Integer.MIN_VALUE;
        }
        this.endpoint.setPhase(phaseToSet);
    }

    public boolean isAutoStartup() {
        return this.endpoint == null || this.endpoint.isAutoStartup();
    }

    public int getPhase() {
        return this.endpoint != null ? this.endpoint.getPhase() : 0;
    }

    public boolean isRunning() {
        return this.endpoint != null && this.endpoint.isRunning();
    }

    public void start() {
        if (this.endpoint != null) {
            this.endpoint.start();
        }
    }

    public void stop() {
        if (this.endpoint != null) {
            this.endpoint.stop();
        }
    }

    public void stop(Runnable callback) {
        if (this.endpoint != null) {
            this.endpoint.stop(callback);
        } else {
            callback.run();
        }
    }

    public void destroy() {
        if (this.endpoint != null) {
            this.endpoint.destroy();
        }
    }
}

