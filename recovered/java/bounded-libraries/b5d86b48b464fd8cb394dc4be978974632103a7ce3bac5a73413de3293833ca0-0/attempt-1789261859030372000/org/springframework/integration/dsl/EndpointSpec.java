/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanNameAware
 *  org.springframework.beans.factory.FactoryBean
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.util.Assert
 *  reactor.util.function.Tuple2
 *  reactor.util.function.Tuples
 */
package org.springframework.integration.dsl;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.BaseIntegrationFlowDefinition;
import org.springframework.integration.dsl.ComponentsRegistration;
import org.springframework.integration.dsl.IntegrationComponentSpec;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlowBuilder;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.PollerFactory;
import org.springframework.integration.dsl.PollerSpec;
import org.springframework.integration.endpoint.AbstractEndpoint;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.MessageChannel;
import org.springframework.util.Assert;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

public abstract class EndpointSpec<S extends EndpointSpec<S, F, H>, F extends BeanNameAware & FactoryBean<? extends AbstractEndpoint>, H>
extends IntegrationComponentSpec<S, Tuple2<F, H>>
implements ComponentsRegistration {
    protected final Map<Object, String> componentsToRegister = new LinkedHashMap<Object, String>();
    protected final F endpointFactoryBean;
    protected H handler;

    protected EndpointSpec(H handler, F endpointFactoryBean) {
        this.endpointFactoryBean = endpointFactoryBean;
        this.handler = handler;
    }

    @Override
    public S id(String id) {
        this.endpointFactoryBean.setBeanName(id);
        return (S)((EndpointSpec)super.id(id));
    }

    public S poller(Function<PollerFactory, PollerSpec> pollers) {
        return this.poller(pollers.apply(new PollerFactory()));
    }

    public S poller(PollerSpec pollerMetadataSpec) {
        Map<Object, String> components = pollerMetadataSpec.getComponentsToRegister();
        if (components != null) {
            this.componentsToRegister.putAll(components);
        }
        return this.poller((PollerMetadata)pollerMetadataSpec.get());
    }

    public abstract S poller(PollerMetadata var1);

    public abstract S phase(int var1);

    public abstract S autoStartup(boolean var1);

    public abstract S role(String var1);

    @Override
    public Map<Object, String> getComponentsToRegister() {
        return this.componentsToRegister.isEmpty() ? null : this.componentsToRegister;
    }

    @Override
    protected Tuple2<F, H> doGet() {
        return Tuples.of(this.endpointFactoryBean, this.handler);
    }

    protected void assertHandler() {
        Assert.state((this.handler != null ? 1 : 0) != 0, (String)"'this.handler' must not be null.");
    }

    protected MessageChannel obtainInputChannelFromFlow(IntegrationFlow subFlow) {
        return this.obtainInputChannelFromFlow(subFlow, true);
    }

    protected MessageChannel obtainInputChannelFromFlow(IntegrationFlow subFlow, boolean evaluateInternalBuilder) {
        Assert.notNull((Object)subFlow, (String)"'subFlow' must not be null");
        MessageChannel messageChannel = subFlow.getInputChannel();
        if (messageChannel == null) {
            messageChannel = new DirectChannel();
            IntegrationFlowBuilder flowBuilder2 = IntegrationFlows.from(messageChannel);
            subFlow.configure(flowBuilder2);
            this.componentsToRegister.put(evaluateInternalBuilder ? ((BaseIntegrationFlowDefinition)flowBuilder2).get() : flowBuilder2, null);
        } else {
            this.componentsToRegister.put(subFlow, null);
        }
        return messageChannel;
    }
}

