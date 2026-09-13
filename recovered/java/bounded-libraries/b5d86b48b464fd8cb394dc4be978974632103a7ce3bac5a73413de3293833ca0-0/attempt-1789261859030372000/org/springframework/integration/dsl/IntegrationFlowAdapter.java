/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.util.Assert
 */
package org.springframework.integration.dsl;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.reactivestreams.Publisher;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.GatewayProxySpec;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlowBuilder;
import org.springframework.integration.dsl.IntegrationFlowDefinition;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannelSpec;
import org.springframework.integration.dsl.MessageProducerSpec;
import org.springframework.integration.dsl.MessageSourceSpec;
import org.springframework.integration.dsl.MessagingGatewaySpec;
import org.springframework.integration.dsl.SourcePollingChannelAdapterSpec;
import org.springframework.integration.dsl.StandardIntegrationFlow;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.gateway.MessagingGatewaySupport;
import org.springframework.integration.support.management.ManageableSmartLifecycle;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.util.Assert;

public abstract class IntegrationFlowAdapter
implements IntegrationFlow,
ManageableSmartLifecycle {
    private final AtomicBoolean running = new AtomicBoolean();
    private StandardIntegrationFlow targetIntegrationFlow;

    @Override
    public final void configure(IntegrationFlowDefinition<?> flow) {
        IntegrationFlowDefinition<?> targetFlow = this.buildFlow();
        Assert.state((targetFlow != null ? 1 : 0) != 0, (String)"the 'buildFlow()' must not return null");
        flow.integrationComponents.clear();
        flow.integrationComponents.putAll(targetFlow.integrationComponents);
        this.targetIntegrationFlow = flow.get();
    }

    @Override
    public MessageChannel getInputChannel() {
        this.assertTargetIntegrationFlow();
        return this.targetIntegrationFlow.getInputChannel();
    }

    @Override
    public Map<Object, String> getIntegrationComponents() {
        return this.targetIntegrationFlow.getIntegrationComponents();
    }

    @Override
    public void start() {
        this.assertTargetIntegrationFlow();
        if (!this.running.getAndSet(true)) {
            this.targetIntegrationFlow.start();
        }
    }

    private void assertTargetIntegrationFlow() {
        Assert.state((this.targetIntegrationFlow != null ? 1 : 0) != 0, (String)(this + " hasn't been initialized properly via BeanFactory.\nMissed @EnableIntegration ?"));
    }

    public void stop(Runnable callback) {
        this.assertTargetIntegrationFlow();
        if (this.running.getAndSet(false)) {
            this.targetIntegrationFlow.stop(callback);
        } else {
            callback.run();
        }
    }

    @Override
    public void stop() {
        this.assertTargetIntegrationFlow();
        if (this.running.getAndSet(false)) {
            this.targetIntegrationFlow.stop();
        }
    }

    @Override
    public boolean isRunning() {
        return this.running.get();
    }

    public boolean isAutoStartup() {
        return false;
    }

    public int getPhase() {
        return 0;
    }

    protected IntegrationFlowDefinition<?> from(String messageChannelName) {
        return IntegrationFlows.from(messageChannelName);
    }

    protected IntegrationFlowDefinition<?> from(MessageChannel messageChannel) {
        return IntegrationFlows.from(messageChannel);
    }

    protected IntegrationFlowDefinition<?> from(String messageChannelName, boolean fixedSubscriber) {
        return IntegrationFlows.from(messageChannelName, fixedSubscriber);
    }

    protected IntegrationFlowDefinition<?> from(MessageSourceSpec<?, ? extends MessageSource<?>> messageSourceSpec, Consumer<SourcePollingChannelAdapterSpec> endpointConfigurer) {
        return IntegrationFlows.from(messageSourceSpec, endpointConfigurer);
    }

    protected IntegrationFlowDefinition<?> from(MessageSource<?> messageSource, Consumer<SourcePollingChannelAdapterSpec> endpointConfigurer) {
        return IntegrationFlows.from(messageSource, endpointConfigurer);
    }

    protected IntegrationFlowDefinition<?> from(MessageProducerSupport messageProducer) {
        return IntegrationFlows.from(messageProducer);
    }

    protected IntegrationFlowDefinition<?> from(MessageSource<?> messageSource) {
        return IntegrationFlows.from(messageSource);
    }

    protected IntegrationFlowDefinition<?> from(MessagingGatewaySupport inboundGateway) {
        return IntegrationFlows.from(inboundGateway);
    }

    protected IntegrationFlowDefinition<?> from(MessageChannelSpec<?, ?> messageChannelSpec) {
        return IntegrationFlows.from(messageChannelSpec);
    }

    protected IntegrationFlowDefinition<?> from(MessageProducerSpec<?, ?> messageProducerSpec) {
        return IntegrationFlows.from(messageProducerSpec);
    }

    protected IntegrationFlowDefinition<?> from(MessageSourceSpec<?, ? extends MessageSource<?>> messageSourceSpec) {
        return IntegrationFlows.from(messageSourceSpec);
    }

    protected IntegrationFlowDefinition<?> from(MessagingGatewaySpec<?, ?> inboundGatewaySpec) {
        return IntegrationFlows.from(inboundGatewaySpec);
    }

    protected <T> IntegrationFlowBuilder fromSupplier(Supplier<T> messageSource) {
        return IntegrationFlows.fromSupplier(messageSource);
    }

    protected <T> IntegrationFlowBuilder fromSupplier(Supplier<T> messageSource, Consumer<SourcePollingChannelAdapterSpec> endpointConfigurer) {
        return IntegrationFlows.fromSupplier(messageSource, endpointConfigurer);
    }

    protected IntegrationFlowBuilder from(Class<?> serviceInterface) {
        return IntegrationFlows.from(serviceInterface);
    }

    protected IntegrationFlowBuilder from(Class<?> serviceInterface, @Nullable Consumer<GatewayProxySpec> endpointConfigurer) {
        return IntegrationFlows.from(serviceInterface, endpointConfigurer);
    }

    protected IntegrationFlowBuilder from(Publisher<? extends Message<?>> publisher) {
        return IntegrationFlows.from(publisher);
    }

    protected abstract IntegrationFlowDefinition<?> buildFlow();
}

