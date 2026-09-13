/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.xml.BeanDefinitionParser
 */
package org.springframework.integration.config.xml;

import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.integration.config.xml.AbstractIntegrationNamespaceHandler;
import org.springframework.integration.config.xml.AggregatorParser;
import org.springframework.integration.config.xml.AnnotationConfigParser;
import org.springframework.integration.config.xml.ApplicationEventMulticasterParser;
import org.springframework.integration.config.xml.BarrierParser;
import org.springframework.integration.config.xml.BridgeParser;
import org.springframework.integration.config.xml.ChainParser;
import org.springframework.integration.config.xml.ClaimCheckInParser;
import org.springframework.integration.config.xml.ClaimCheckOutParser;
import org.springframework.integration.config.xml.ControlBusParser;
import org.springframework.integration.config.xml.ConverterParser;
import org.springframework.integration.config.xml.DefaultInboundChannelAdapterParser;
import org.springframework.integration.config.xml.DefaultOutboundChannelAdapterParser;
import org.springframework.integration.config.xml.DefaultRouterParser;
import org.springframework.integration.config.xml.DelayerParser;
import org.springframework.integration.config.xml.EnricherParser;
import org.springframework.integration.config.xml.ErrorMessageExceptionTypeRouterParser;
import org.springframework.integration.config.xml.FilterParser;
import org.springframework.integration.config.xml.GatewayParser;
import org.springframework.integration.config.xml.GlobalChannelInterceptorParser;
import org.springframework.integration.config.xml.GlobalWireTapParser;
import org.springframework.integration.config.xml.HeaderFilterParser;
import org.springframework.integration.config.xml.HeaderValueRouterParser;
import org.springframework.integration.config.xml.IdempotentReceiverInterceptorParser;
import org.springframework.integration.config.xml.IntegrationManagementParser;
import org.springframework.integration.config.xml.JsonToObjectTransformerParser;
import org.springframework.integration.config.xml.LoggingChannelAdapterParser;
import org.springframework.integration.config.xml.MapToObjectTransformerParser;
import org.springframework.integration.config.xml.MessageHistoryParser;
import org.springframework.integration.config.xml.ObjectToJsonTransformerParser;
import org.springframework.integration.config.xml.ObjectToMapTransformerParser;
import org.springframework.integration.config.xml.ObjectToStringTransformerParser;
import org.springframework.integration.config.xml.PayloadDeserializingTransformerParser;
import org.springframework.integration.config.xml.PayloadSerializingTransformerParser;
import org.springframework.integration.config.xml.PayloadTypeRouterParser;
import org.springframework.integration.config.xml.PointToPointChannelParser;
import org.springframework.integration.config.xml.PollerParser;
import org.springframework.integration.config.xml.PublishSubscribeChannelParser;
import org.springframework.integration.config.xml.PublishingInterceptorParser;
import org.springframework.integration.config.xml.RecipientListRouterParser;
import org.springframework.integration.config.xml.ResequencerParser;
import org.springframework.integration.config.xml.ResourceInboundChannelAdapterParser;
import org.springframework.integration.config.xml.RetryAdviceParser;
import org.springframework.integration.config.xml.ScatterGatherParser;
import org.springframework.integration.config.xml.SelectorChainParser;
import org.springframework.integration.config.xml.SelectorParser;
import org.springframework.integration.config.xml.ServiceActivatorParser;
import org.springframework.integration.config.xml.SpelFunctionParser;
import org.springframework.integration.config.xml.SpelPropertyAccessorsParser;
import org.springframework.integration.config.xml.SplitterParser;
import org.springframework.integration.config.xml.StandardHeaderEnricherParser;
import org.springframework.integration.config.xml.StreamTransformerParser;
import org.springframework.integration.config.xml.SyslogToMapTransformerParser;
import org.springframework.integration.config.xml.TransactionSynchronizationFactoryParser;
import org.springframework.integration.config.xml.TransformerParser;

public class IntegrationNamespaceHandler
extends AbstractIntegrationNamespaceHandler {
    public void init() {
        this.registerBeanDefinitionParser("channel", (BeanDefinitionParser)new PointToPointChannelParser());
        this.registerBeanDefinitionParser("publish-subscribe-channel", (BeanDefinitionParser)new PublishSubscribeChannelParser());
        this.registerBeanDefinitionParser("service-activator", (BeanDefinitionParser)new ServiceActivatorParser());
        this.registerBeanDefinitionParser("transformer", (BeanDefinitionParser)new TransformerParser());
        this.registerBeanDefinitionParser("enricher", (BeanDefinitionParser)new EnricherParser());
        this.registerBeanDefinitionParser("filter", (BeanDefinitionParser)new FilterParser());
        this.registerBeanDefinitionParser("router", (BeanDefinitionParser)new DefaultRouterParser());
        this.registerBeanDefinitionParser("header-value-router", (BeanDefinitionParser)new HeaderValueRouterParser());
        this.registerBeanDefinitionParser("payload-type-router", (BeanDefinitionParser)new PayloadTypeRouterParser());
        this.registerBeanDefinitionParser("exception-type-router", (BeanDefinitionParser)new ErrorMessageExceptionTypeRouterParser());
        this.registerBeanDefinitionParser("recipient-list-router", (BeanDefinitionParser)new RecipientListRouterParser());
        this.registerBeanDefinitionParser("splitter", (BeanDefinitionParser)new SplitterParser());
        this.registerBeanDefinitionParser("aggregator", (BeanDefinitionParser)new AggregatorParser());
        this.registerBeanDefinitionParser("resequencer", (BeanDefinitionParser)new ResequencerParser());
        this.registerBeanDefinitionParser("header-enricher", (BeanDefinitionParser)new StandardHeaderEnricherParser());
        this.registerBeanDefinitionParser("header-filter", (BeanDefinitionParser)new HeaderFilterParser());
        this.registerBeanDefinitionParser("object-to-string-transformer", (BeanDefinitionParser)new ObjectToStringTransformerParser());
        this.registerBeanDefinitionParser("object-to-map-transformer", (BeanDefinitionParser)new ObjectToMapTransformerParser());
        this.registerBeanDefinitionParser("map-to-object-transformer", (BeanDefinitionParser)new MapToObjectTransformerParser());
        this.registerBeanDefinitionParser("object-to-json-transformer", (BeanDefinitionParser)new ObjectToJsonTransformerParser());
        this.registerBeanDefinitionParser("json-to-object-transformer", (BeanDefinitionParser)new JsonToObjectTransformerParser());
        this.registerBeanDefinitionParser("payload-serializing-transformer", (BeanDefinitionParser)new PayloadSerializingTransformerParser());
        this.registerBeanDefinitionParser("payload-deserializing-transformer", (BeanDefinitionParser)new PayloadDeserializingTransformerParser());
        this.registerBeanDefinitionParser("stream-transformer", (BeanDefinitionParser)new StreamTransformerParser());
        this.registerBeanDefinitionParser("claim-check-in", (BeanDefinitionParser)new ClaimCheckInParser());
        this.registerBeanDefinitionParser("syslog-to-map-transformer", (BeanDefinitionParser)new SyslogToMapTransformerParser());
        this.registerBeanDefinitionParser("claim-check-out", (BeanDefinitionParser)new ClaimCheckOutParser());
        this.registerBeanDefinitionParser("inbound-channel-adapter", (BeanDefinitionParser)new DefaultInboundChannelAdapterParser());
        this.registerBeanDefinitionParser("resource-inbound-channel-adapter", (BeanDefinitionParser)new ResourceInboundChannelAdapterParser());
        this.registerBeanDefinitionParser("outbound-channel-adapter", (BeanDefinitionParser)new DefaultOutboundChannelAdapterParser());
        this.registerBeanDefinitionParser("logging-channel-adapter", (BeanDefinitionParser)new LoggingChannelAdapterParser());
        this.registerBeanDefinitionParser("gateway", new GatewayParser());
        this.registerBeanDefinitionParser("delayer", (BeanDefinitionParser)new DelayerParser());
        this.registerBeanDefinitionParser("bridge", (BeanDefinitionParser)new BridgeParser());
        this.registerBeanDefinitionParser("chain", (BeanDefinitionParser)new ChainParser());
        this.registerBeanDefinitionParser("selector", (BeanDefinitionParser)new SelectorParser());
        this.registerBeanDefinitionParser("selector-chain", (BeanDefinitionParser)new SelectorChainParser());
        this.registerBeanDefinitionParser("poller", (BeanDefinitionParser)new PollerParser());
        this.registerBeanDefinitionParser("annotation-config", new AnnotationConfigParser());
        this.registerBeanDefinitionParser("application-event-multicaster", (BeanDefinitionParser)new ApplicationEventMulticasterParser());
        this.registerBeanDefinitionParser("publishing-interceptor", (BeanDefinitionParser)new PublishingInterceptorParser());
        this.registerBeanDefinitionParser("channel-interceptor", (BeanDefinitionParser)new GlobalChannelInterceptorParser());
        this.registerBeanDefinitionParser("converter", (BeanDefinitionParser)new ConverterParser());
        this.registerBeanDefinitionParser("message-history", new MessageHistoryParser());
        this.registerBeanDefinitionParser("control-bus", (BeanDefinitionParser)new ControlBusParser());
        this.registerBeanDefinitionParser("wire-tap", (BeanDefinitionParser)new GlobalWireTapParser());
        this.registerBeanDefinitionParser("transaction-synchronization-factory", (BeanDefinitionParser)new TransactionSynchronizationFactoryParser());
        this.registerBeanDefinitionParser("spel-function", (BeanDefinitionParser)new SpelFunctionParser());
        this.registerBeanDefinitionParser("spel-property-accessors", new SpelPropertyAccessorsParser());
        RetryAdviceParser retryParser = new RetryAdviceParser();
        this.registerBeanDefinitionParser("handler-retry-advice", (BeanDefinitionParser)retryParser);
        this.registerBeanDefinitionParser("retry-advice", (BeanDefinitionParser)retryParser);
        this.registerBeanDefinitionParser("scatter-gather", (BeanDefinitionParser)new ScatterGatherParser());
        this.registerBeanDefinitionParser("idempotent-receiver", (BeanDefinitionParser)new IdempotentReceiverInterceptorParser());
        this.registerBeanDefinitionParser("management", (BeanDefinitionParser)new IntegrationManagementParser());
        this.registerBeanDefinitionParser("barrier", (BeanDefinitionParser)new BarrierParser());
    }
}

