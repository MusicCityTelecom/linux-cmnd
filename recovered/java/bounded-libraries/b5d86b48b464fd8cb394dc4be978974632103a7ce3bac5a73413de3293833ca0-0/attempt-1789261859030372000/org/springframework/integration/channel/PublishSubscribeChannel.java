/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 *  org.springframework.util.ErrorHandler
 */
package org.springframework.integration.channel;

import java.util.concurrent.Executor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.integration.IntegrationPatternType;
import org.springframework.integration.channel.AbstractExecutorChannel;
import org.springframework.integration.channel.BroadcastCapableChannel;
import org.springframework.integration.channel.ChannelUtils;
import org.springframework.integration.dispatcher.BroadcastingDispatcher;
import org.springframework.integration.util.ErrorHandlingTaskExecutor;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ErrorHandler;

public class PublishSubscribeChannel
extends AbstractExecutorChannel
implements BroadcastCapableChannel {
    private ErrorHandler errorHandler;
    private final boolean requireSubscribers;
    private boolean ignoreFailures;
    private boolean applySequence;
    private int minSubscribers;

    public PublishSubscribeChannel() {
        this(false);
    }

    public PublishSubscribeChannel(boolean requireSubscribers) {
        this(null, requireSubscribers);
    }

    public PublishSubscribeChannel(@Nullable Executor executor) {
        this(executor, false);
    }

    public PublishSubscribeChannel(@Nullable Executor executor, boolean requireSubscribers) {
        super(executor);
        this.requireSubscribers = requireSubscribers;
        this.dispatcher = new BroadcastingDispatcher(executor, requireSubscribers);
    }

    @Override
    public String getComponentType() {
        return "publish-subscribe-channel";
    }

    @Override
    public IntegrationPatternType getIntegrationPatternType() {
        return IntegrationPatternType.publish_subscribe_channel;
    }

    public void setErrorHandler(ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    public void setIgnoreFailures(boolean ignoreFailures) {
        this.ignoreFailures = ignoreFailures;
        this.getDispatcher().setIgnoreFailures(ignoreFailures);
    }

    public void setApplySequence(boolean applySequence) {
        this.applySequence = applySequence;
        this.getDispatcher().setApplySequence(applySequence);
    }

    public void setMinSubscribers(int minSubscribers) {
        this.minSubscribers = minSubscribers;
        this.getDispatcher().setMinSubscribers(minSubscribers);
    }

    @Override
    public final void onInit() {
        super.onInit();
        BeanFactory beanFactory = this.getBeanFactory();
        BroadcastingDispatcher dispatcherToUse = this.getDispatcher();
        if (this.executor != null) {
            Assert.state((dispatcherToUse.getHandlerCount() == 0 ? 1 : 0) != 0, (String)"When providing an Executor, you cannot subscribe() until the channel bean is fully initialized by the framework. Do not subscribe in a @Bean definition");
            if (!(this.executor instanceof ErrorHandlingTaskExecutor)) {
                if (this.errorHandler == null) {
                    this.errorHandler = ChannelUtils.getErrorHandler(beanFactory);
                }
                this.executor = new ErrorHandlingTaskExecutor(this.executor, this.errorHandler);
            }
            dispatcherToUse = new BroadcastingDispatcher(this.executor, this.requireSubscribers);
            dispatcherToUse.setIgnoreFailures(this.ignoreFailures);
            dispatcherToUse.setApplySequence(this.applySequence);
            dispatcherToUse.setMinSubscribers(this.minSubscribers);
            this.dispatcher = dispatcherToUse;
        } else if (this.errorHandler != null) {
            this.logger.warn(() -> "The 'errorHandler' is ignored for the '" + this.getComponentName() + "' (an 'executor' is not provided) and exceptions will be thrown directly within the sending Thread");
        }
        if (this.maxSubscribers == null) {
            Integer maxSubscribers = this.getIntegrationProperty("spring.integration.channels.maxBroadcastSubscribers", Integer.class);
            this.setMaxSubscribers(maxSubscribers);
        }
        dispatcherToUse.setBeanFactory(beanFactory);
        dispatcherToUse.setMessageHandlingTaskDecorator(task -> {
            if (this.executorInterceptorsSize > 0) {
                return new AbstractExecutorChannel.MessageHandlingTask(task);
            }
            return task;
        });
    }

    @Override
    protected BroadcastingDispatcher getDispatcher() {
        return (BroadcastingDispatcher)this.dispatcher;
    }
}

