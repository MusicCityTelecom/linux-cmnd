/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.dsl;

import java.util.function.Consumer;
import java.util.function.Function;
import org.springframework.integration.core.GenericSelector;
import org.springframework.integration.dsl.BaseIntegrationFlowDefinition;
import org.springframework.integration.dsl.FilterEndpointSpec;
import org.springframework.integration.dsl.GenericEndpointSpec;
import org.springframework.integration.dsl.RouterSpec;
import org.springframework.integration.dsl.SplitterEndpointSpec;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.integration.handler.ServiceActivatingHandler;
import org.springframework.integration.router.MethodInvokingRouter;
import org.springframework.integration.splitter.MethodInvokingSplitter;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.integration.transformer.MessageTransformingHandler;

public abstract class IntegrationFlowDefinition<B extends IntegrationFlowDefinition<B>>
extends BaseIntegrationFlowDefinition<B> {
    IntegrationFlowDefinition() {
    }

    public <S, T> B transform(GenericTransformer<S, T> genericTransformer) {
        return (B)((IntegrationFlowDefinition)this.transform(null, genericTransformer));
    }

    public <S, T> B transform(GenericTransformer<S, T> genericTransformer, Consumer<GenericEndpointSpec<MessageTransformingHandler>> endpointConfigurer) {
        return (B)((IntegrationFlowDefinition)this.transform(null, genericTransformer, endpointConfigurer));
    }

    @Override
    public <P> B filter(GenericSelector<P> genericSelector) {
        return (B)((IntegrationFlowDefinition)this.filter(null, genericSelector));
    }

    @Override
    public <P> B filter(GenericSelector<P> genericSelector, Consumer<FilterEndpointSpec> endpointConfigurer) {
        return (B)((IntegrationFlowDefinition)this.filter(null, genericSelector, endpointConfigurer));
    }

    @Override
    public <P> B handle(GenericHandler<P> handler) {
        return (B)((IntegrationFlowDefinition)this.handle(null, handler));
    }

    @Override
    public <P> B handle(GenericHandler<P> handler, Consumer<GenericEndpointSpec<ServiceActivatingHandler>> endpointConfigurer) {
        return (B)((IntegrationFlowDefinition)this.handle(null, handler, endpointConfigurer));
    }

    @Override
    public <P> B split(Function<P, ?> splitter, Consumer<SplitterEndpointSpec<MethodInvokingSplitter>> endpointConfigurer) {
        return (B)((IntegrationFlowDefinition)this.split(null, splitter, endpointConfigurer));
    }

    public <S, T> B route(Function<S, T> router) {
        return (B)((IntegrationFlowDefinition)this.route(null, router));
    }

    @Override
    public <S, T> B route(Function<S, T> router, Consumer<RouterSpec<T, MethodInvokingRouter>> routerConfigurer) {
        return (B)((IntegrationFlowDefinition)this.route(null, router, routerConfigurer));
    }
}

