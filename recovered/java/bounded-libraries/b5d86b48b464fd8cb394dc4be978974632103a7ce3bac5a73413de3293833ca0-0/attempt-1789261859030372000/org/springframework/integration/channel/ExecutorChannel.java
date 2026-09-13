/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 *  org.springframework.util.ErrorHandler
 */
package org.springframework.integration.channel;

import java.util.concurrent.Executor;
import org.springframework.integration.channel.AbstractExecutorChannel;
import org.springframework.integration.channel.ChannelUtils;
import org.springframework.integration.dispatcher.LoadBalancingStrategy;
import org.springframework.integration.dispatcher.RoundRobinLoadBalancingStrategy;
import org.springframework.integration.dispatcher.UnicastingDispatcher;
import org.springframework.integration.util.ErrorHandlingTaskExecutor;
import org.springframework.util.Assert;
import org.springframework.util.ErrorHandler;

public class ExecutorChannel
extends AbstractExecutorChannel {
    private volatile boolean failover = true;
    private volatile LoadBalancingStrategy loadBalancingStrategy;

    public ExecutorChannel(Executor executor) {
        this(executor, new RoundRobinLoadBalancingStrategy());
    }

    public ExecutorChannel(Executor executor, LoadBalancingStrategy loadBalancingStrategy) {
        super(executor);
        Assert.notNull((Object)executor, (String)"executor must not be null");
        UnicastingDispatcher unicastingDispatcher = new UnicastingDispatcher(executor);
        if (loadBalancingStrategy != null) {
            this.loadBalancingStrategy = loadBalancingStrategy;
            unicastingDispatcher.setLoadBalancingStrategy(loadBalancingStrategy);
        }
        this.dispatcher = unicastingDispatcher;
    }

    public void setFailover(boolean failover) {
        this.failover = failover;
        this.getDispatcher().setFailover(failover);
    }

    @Override
    protected UnicastingDispatcher getDispatcher() {
        return (UnicastingDispatcher)this.dispatcher;
    }

    @Override
    public final void onInit() {
        Assert.state((this.getDispatcher().getHandlerCount() == 0 ? 1 : 0) != 0, (String)"You cannot subscribe() until the channel bean is fully initialized by the framework. Do not subscribe in a @Bean definition");
        super.onInit();
        if (!(this.executor instanceof ErrorHandlingTaskExecutor)) {
            ErrorHandler errorHandler = ChannelUtils.getErrorHandler(this.getBeanFactory());
            this.executor = new ErrorHandlingTaskExecutor(this.executor, errorHandler);
        }
        UnicastingDispatcher unicastingDispatcher = new UnicastingDispatcher(this.executor);
        unicastingDispatcher.setFailover(this.failover);
        if (this.maxSubscribers == null) {
            this.maxSubscribers = this.getIntegrationProperty("spring.integration.channels.maxUnicastSubscribers", Integer.class);
        }
        unicastingDispatcher.setMaxSubscribers(this.maxSubscribers);
        if (this.loadBalancingStrategy != null) {
            unicastingDispatcher.setLoadBalancingStrategy(this.loadBalancingStrategy);
        }
        unicastingDispatcher.setMessageHandlingTaskDecorator(task -> {
            if (this.executorInterceptorsSize > 0) {
                return new AbstractExecutorChannel.MessageHandlingTask(task);
            }
            return task;
        });
        this.dispatcher = unicastingDispatcher;
    }
}

