/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanClassLoaderAware
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.beans.factory.BeanNameAware
 *  org.springframework.beans.factory.DisposableBean
 *  org.springframework.beans.factory.FactoryBean
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.beans.factory.config.ConfigurableBeanFactory
 *  org.springframework.context.SmartLifecycle
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.core.BeanFactoryMessageChannelDestinationResolver
 *  org.springframework.messaging.core.DestinationResolver
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.config;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.SmartLifecycle;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.endpoint.SourcePollingChannelAdapter;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.core.BeanFactoryMessageChannelDestinationResolver;
import org.springframework.messaging.core.DestinationResolver;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class SourcePollingChannelAdapterFactoryBean
implements FactoryBean<SourcePollingChannelAdapter>,
BeanFactoryAware,
BeanNameAware,
BeanClassLoaderAware,
InitializingBean,
SmartLifecycle,
DisposableBean {
    private final Object initializationMonitor = new Object();
    private MessageSource<?> source;
    private MessageChannel outputChannel;
    private String outputChannelName;
    private PollerMetadata pollerMetadata;
    private Boolean autoStartup;
    private int phase = 0x3FFFFFFF;
    private Long sendTimeout;
    private String beanName;
    private ConfigurableBeanFactory beanFactory;
    private ClassLoader beanClassLoader;
    private DestinationResolver<MessageChannel> channelResolver;
    private String role;
    private volatile SourcePollingChannelAdapter adapter;
    private volatile boolean initialized;

    public void setSource(MessageSource<?> source) {
        this.source = source;
    }

    public void setSendTimeout(long sendTimeout) {
        this.sendTimeout = sendTimeout;
    }

    public void setOutputChannel(MessageChannel outputChannel) {
        this.outputChannel = outputChannel;
    }

    public void setOutputChannelName(String outputChannelName) {
        this.outputChannelName = outputChannelName;
    }

    public void setPollerMetadata(PollerMetadata pollerMetadata) {
        this.pollerMetadata = pollerMetadata;
    }

    public void setAutoStartup(Boolean autoStartup) {
        this.autoStartup = autoStartup;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setChannelResolver(DestinationResolver<MessageChannel> channelResolver) {
        Assert.notNull(channelResolver, (String)"'channelResolver' must not be null");
        this.channelResolver = channelResolver;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        Assert.isInstanceOf(ConfigurableBeanFactory.class, (Object)beanFactory, (String)"a ConfigurableBeanFactory is required");
        this.beanFactory = (ConfigurableBeanFactory)beanFactory;
    }

    public void setBeanClassLoader(ClassLoader classLoader) {
        this.beanClassLoader = classLoader;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public void afterPropertiesSet() {
        if (this.channelResolver == null) {
            this.channelResolver = new BeanFactoryMessageChannelDestinationResolver((BeanFactory)this.beanFactory);
        }
        this.initializeAdapter();
    }

    public SourcePollingChannelAdapter getObject() {
        if (this.adapter == null) {
            this.initializeAdapter();
        }
        return this.adapter;
    }

    public Class<?> getObjectType() {
        return SourcePollingChannelAdapter.class;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void initializeAdapter() {
        Object object = this.initializationMonitor;
        synchronized (object) {
            if (this.initialized) {
                return;
            }
            Assert.notNull(this.source, (String)"source is required");
            SourcePollingChannelAdapter spca = new SourcePollingChannelAdapter();
            spca.setSource(this.source);
            if (StringUtils.hasText((String)this.outputChannelName)) {
                Assert.isNull((Object)this.outputChannel, (String)"'outputChannelName' and 'outputChannel' are mutually exclusive.");
                spca.setOutputChannelName(this.outputChannelName);
            } else {
                Assert.notNull((Object)this.outputChannel, (String)"outputChannel is required");
                spca.setOutputChannel(this.outputChannel);
            }
            if (this.pollerMetadata == null) {
                this.pollerMetadata = PollerMetadata.getDefaultPollerMetadata((BeanFactory)this.beanFactory);
                Assert.notNull((Object)this.pollerMetadata, () -> "No poller has been defined for channel-adapter '" + this.beanName + "', and no default poller is available within the context.");
            }
            if (this.pollerMetadata.getMaxMessagesPerPoll() == Integer.MIN_VALUE) {
                this.pollerMetadata.setMaxMessagesPerPoll(1L);
            }
            spca.setMaxMessagesPerPoll(this.pollerMetadata.getMaxMessagesPerPoll());
            if (this.sendTimeout != null) {
                spca.setSendTimeout(this.sendTimeout);
            }
            spca.setTaskExecutor(this.pollerMetadata.getTaskExecutor());
            spca.setAdviceChain(this.pollerMetadata.getAdviceChain());
            spca.setTrigger(this.pollerMetadata.getTrigger());
            spca.setErrorHandler(this.pollerMetadata.getErrorHandler());
            spca.setBeanClassLoader(this.beanClassLoader);
            if (this.autoStartup != null) {
                spca.setAutoStartup(this.autoStartup);
            }
            spca.setPhase(this.phase);
            spca.setRole(this.role);
            spca.setBeanName(this.beanName);
            spca.setBeanFactory((BeanFactory)this.beanFactory);
            spca.setTransactionSynchronizationFactory(this.pollerMetadata.getTransactionSynchronizationFactory());
            spca.afterPropertiesSet();
            this.adapter = spca;
            this.initialized = true;
        }
    }

    public boolean isAutoStartup() {
        return this.adapter == null || this.adapter.isAutoStartup();
    }

    public int getPhase() {
        return this.adapter != null ? this.adapter.getPhase() : 0;
    }

    public boolean isRunning() {
        return this.adapter != null && this.adapter.isRunning();
    }

    public void start() {
        if (this.adapter != null) {
            this.adapter.start();
        }
    }

    public void stop() {
        if (this.adapter != null) {
            this.adapter.stop();
        }
    }

    public void stop(Runnable callback) {
        if (this.adapter != null) {
            this.adapter.stop(callback);
        } else {
            callback.run();
        }
    }

    public void destroy() {
        if (this.adapter != null) {
            this.adapter.destroy();
        }
    }
}

