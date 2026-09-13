/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.springframework.aop.framework.Advised
 *  org.springframework.aop.support.AopUtils
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.BeanCreationException
 *  org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor
 *  org.springframework.expression.Expression
 *  org.springframework.expression.spel.standard.SpelExpressionParser
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.MessageHandler
 *  org.springframework.messaging.support.ChannelInterceptor
 *  org.springframework.messaging.support.InterceptableChannel
 *  org.springframework.util.Assert
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.StringUtils
 *  reactor.core.publisher.Flux
 *  reactor.util.function.Tuple2
 */
package org.springframework.integration.dsl;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Function;
import org.reactivestreams.Publisher;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.integration.aggregator.AggregatingMessageHandler;
import org.springframework.integration.channel.BroadcastCapableChannel;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.FixedSubscriberChannel;
import org.springframework.integration.channel.FluxMessageChannel;
import org.springframework.integration.channel.interceptor.WireTap;
import org.springframework.integration.config.ConsumerEndpointFactoryBean;
import org.springframework.integration.config.SourcePollingChannelAdapterFactoryBean;
import org.springframework.integration.core.GenericSelector;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.core.MessageSelector;
import org.springframework.integration.dsl.AbstractRouterSpec;
import org.springframework.integration.dsl.AggregatorSpec;
import org.springframework.integration.dsl.BarrierSpec;
import org.springframework.integration.dsl.BroadcastPublishSubscribeSpec;
import org.springframework.integration.dsl.Channels;
import org.springframework.integration.dsl.ComponentsRegistration;
import org.springframework.integration.dsl.ConsumerEndpointSpec;
import org.springframework.integration.dsl.DelayerEndpointSpec;
import org.springframework.integration.dsl.EnricherSpec;
import org.springframework.integration.dsl.FilterEndpointSpec;
import org.springframework.integration.dsl.GatewayEndpointSpec;
import org.springframework.integration.dsl.GenericEndpointSpec;
import org.springframework.integration.dsl.HeaderEnricherSpec;
import org.springframework.integration.dsl.IntegrationDsl;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlowBuilder;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannelSpec;
import org.springframework.integration.dsl.MessageHandlerSpec;
import org.springframework.integration.dsl.MessageProcessorSpec;
import org.springframework.integration.dsl.PublishSubscribeSpec;
import org.springframework.integration.dsl.PublisherIntegrationFlow;
import org.springframework.integration.dsl.RecipientListRouterSpec;
import org.springframework.integration.dsl.ResequencerSpec;
import org.springframework.integration.dsl.RouterSpec;
import org.springframework.integration.dsl.ScatterGatherSpec;
import org.springframework.integration.dsl.SourcePollingChannelAdapterSpec;
import org.springframework.integration.dsl.SplitterEndpointSpec;
import org.springframework.integration.dsl.StandardIntegrationFlow;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.dsl.WireTapSpec;
import org.springframework.integration.dsl.support.FixedSubscriberChannelPrototype;
import org.springframework.integration.dsl.support.MessageChannelReference;
import org.springframework.integration.expression.ControlBusMethodFilter;
import org.springframework.integration.expression.FunctionExpression;
import org.springframework.integration.filter.ExpressionEvaluatingSelector;
import org.springframework.integration.filter.MessageFilter;
import org.springframework.integration.filter.MethodInvokingSelector;
import org.springframework.integration.handler.AbstractMessageProducingHandler;
import org.springframework.integration.handler.BeanNameMessageProcessor;
import org.springframework.integration.handler.BridgeHandler;
import org.springframework.integration.handler.DelayHandler;
import org.springframework.integration.handler.ExpressionCommandMessageProcessor;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.integration.handler.LambdaMessageProcessor;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.handler.MessageProcessor;
import org.springframework.integration.handler.MessageTriggerAction;
import org.springframework.integration.handler.ServiceActivatingHandler;
import org.springframework.integration.router.AbstractMessageRouter;
import org.springframework.integration.router.ErrorMessageExceptionTypeRouter;
import org.springframework.integration.router.ExpressionEvaluatingRouter;
import org.springframework.integration.router.MethodInvokingRouter;
import org.springframework.integration.router.RecipientListRouter;
import org.springframework.integration.scattergather.ScatterGatherHandler;
import org.springframework.integration.splitter.AbstractMessageSplitter;
import org.springframework.integration.splitter.DefaultMessageSplitter;
import org.springframework.integration.splitter.ExpressionEvaluatingSplitter;
import org.springframework.integration.splitter.MethodInvokingSplitter;
import org.springframework.integration.store.MessageStore;
import org.springframework.integration.support.MapBuilder;
import org.springframework.integration.transformer.ClaimCheckInTransformer;
import org.springframework.integration.transformer.ClaimCheckOutTransformer;
import org.springframework.integration.transformer.ExpressionEvaluatingTransformer;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.integration.transformer.HeaderFilter;
import org.springframework.integration.transformer.MessageTransformingHandler;
import org.springframework.integration.transformer.MethodInvokingTransformer;
import org.springframework.integration.transformer.Transformer;
import org.springframework.integration.util.ClassUtils;
import org.springframework.integration.util.IntegrationReactiveUtils;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.InterceptableChannel;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

@IntegrationDsl
public abstract class BaseIntegrationFlowDefinition<B extends BaseIntegrationFlowDefinition<B>> {
    private static final String UNCHECKED = "unchecked";
    private static final String FUNCTION_MUST_NOT_BE_NULL = "'function' must not be null";
    private static final String MESSAGE_PROCESSOR_SPEC_MUST_NOT_BE_NULL = "'messageProcessorSpec' must not be null";
    private static final Set<MessageProducer> REFERENCED_REPLY_PRODUCERS = new HashSet<MessageProducer>();
    protected static final SpelExpressionParser PARSER = new SpelExpressionParser();
    protected final Map<Object, String> integrationComponents = new LinkedHashMap<Object, String>();
    private MessageChannel currentMessageChannel;
    private Object currentComponent;
    private boolean implicitChannel;
    private StandardIntegrationFlow integrationFlow;

    protected BaseIntegrationFlowDefinition() {
    }

    protected B addComponent(Object component) {
        return this.addComponent(component, null);
    }

    protected B addComponent(Object component, @Nullable String beanName) {
        this.integrationComponents.put(component, beanName);
        return this._this();
    }

    protected B addComponents(Map<Object, String> components) {
        if (components != null) {
            this.integrationComponents.putAll(components);
        }
        return this._this();
    }

    protected Map<Object, String> getIntegrationComponents() {
        return this.integrationComponents;
    }

    protected B currentComponent(@Nullable Object component) {
        this.currentComponent = component;
        return this._this();
    }

    @Nullable
    protected Object getCurrentComponent() {
        return this.currentComponent;
    }

    protected B currentMessageChannel(@Nullable MessageChannel currentMessageChannel) {
        this.currentMessageChannel = currentMessageChannel;
        return this._this();
    }

    @Nullable
    protected MessageChannel getCurrentMessageChannel() {
        return this.currentMessageChannel;
    }

    protected InterceptableChannel currentInterceptableChannel() {
        MessageChannel currentChannel = this.getCurrentMessageChannel();
        if (currentChannel instanceof InterceptableChannel) {
            return (InterceptableChannel)currentChannel;
        }
        DirectChannel newCurrentChannel = new DirectChannel();
        this.channel(newCurrentChannel);
        this.setImplicitChannel(true);
        return newCurrentChannel;
    }

    protected void setImplicitChannel(boolean implicitChannel) {
        this.implicitChannel = implicitChannel;
    }

    protected boolean isImplicitChannel() {
        return this.implicitChannel;
    }

    public B fixedSubscriberChannel() {
        return this.fixedSubscriberChannel(null);
    }

    public B fixedSubscriberChannel(String messageChannelName) {
        return this.channel(new FixedSubscriberChannelPrototype(messageChannelName));
    }

    public B channel(String messageChannelName) {
        return this.channel(new MessageChannelReference(messageChannelName));
    }

    public B channel(MessageChannelSpec<?, ?> messageChannelSpec) {
        Assert.notNull(messageChannelSpec, (String)"'messageChannelSpec' must not be null");
        return this.channel((MessageChannel)messageChannelSpec.get());
    }

    public B channel(MessageChannel messageChannel) {
        Assert.notNull((Object)messageChannel, (String)"'messageChannel' must not be null");
        this.setImplicitChannel(false);
        if (this.getCurrentMessageChannel() != null) {
            this.bridge();
        }
        this.currentMessageChannel(messageChannel);
        return this.registerOutputChannelIfCan(messageChannel);
    }

    public B channel(Function<Channels, MessageChannelSpec<?, ?>> channels) {
        Assert.notNull(channels, (String)"'channels' must not be null");
        return this.channel(channels.apply(Channels.INSTANCE));
    }

    public B publishSubscribeChannel(Consumer<PublishSubscribeSpec> publishSubscribeChannelConfigurer) {
        return this.publishSubscribeChannel((Executor)null, publishSubscribeChannelConfigurer);
    }

    public B publishSubscribeChannel(Executor executor, Consumer<PublishSubscribeSpec> publishSubscribeChannelConfigurer) {
        Assert.notNull(publishSubscribeChannelConfigurer, (String)"'publishSubscribeChannelConfigurer' must not be null");
        PublishSubscribeSpec spec = new PublishSubscribeSpec(executor);
        publishSubscribeChannelConfigurer.accept(spec);
        return ((BaseIntegrationFlowDefinition)this.addComponents(spec.getComponentsToRegister())).channel(spec);
    }

    public B publishSubscribeChannel(BroadcastCapableChannel broadcastCapableChannel, Consumer<BroadcastPublishSubscribeSpec> publishSubscribeChannelConfigurer) {
        Assert.notNull(publishSubscribeChannelConfigurer, (String)"'publishSubscribeChannelConfigurer' must not be null");
        BroadcastPublishSubscribeSpec spec = new BroadcastPublishSubscribeSpec(broadcastCapableChannel);
        publishSubscribeChannelConfigurer.accept(spec);
        return ((BaseIntegrationFlowDefinition)this.addComponents(spec.getComponentsToRegister())).channel((MessageChannel)broadcastCapableChannel);
    }

    public B wireTap(IntegrationFlow flow) {
        return this.wireTap(flow, null);
    }

    public B wireTap(String wireTapChannel) {
        return this.wireTap(wireTapChannel, null);
    }

    public B wireTap(MessageChannel wireTapChannel) {
        return this.wireTap(wireTapChannel, null);
    }

    public B wireTap(IntegrationFlow flow, Consumer<WireTapSpec> wireTapConfigurer) {
        MessageChannel wireTapChannel = this.obtainInputChannelFromFlow(flow);
        return this.wireTap(wireTapChannel, wireTapConfigurer);
    }

    protected MessageChannel obtainInputChannelFromFlow(IntegrationFlow flow) {
        Assert.notNull((Object)flow, (String)"'flow' must not be null");
        MessageChannel messageChannel = flow.getInputChannel();
        if (messageChannel == null) {
            messageChannel = new DirectChannel();
            IntegrationFlowBuilder flowBuilder2 = IntegrationFlows.from(messageChannel);
            flow.configure(flowBuilder2);
            this.addComponent(((BaseIntegrationFlowDefinition)flowBuilder2).get());
        } else {
            this.addComponent(flow);
        }
        return messageChannel;
    }

    public B wireTap(String wireTapChannel, Consumer<WireTapSpec> wireTapConfigurer) {
        DirectChannel internalWireTapChannel = new DirectChannel();
        this.addComponent(((IntegrationFlowBuilder)IntegrationFlows.from(internalWireTapChannel).channel(wireTapChannel)).get());
        return this.wireTap(internalWireTapChannel, wireTapConfigurer);
    }

    public B wireTap(MessageChannel wireTapChannel, Consumer<WireTapSpec> wireTapConfigurer) {
        WireTapSpec wireTapSpec = new WireTapSpec(wireTapChannel);
        if (wireTapConfigurer != null) {
            wireTapConfigurer.accept(wireTapSpec);
        }
        this.addComponent(wireTapChannel);
        return this.wireTap(wireTapSpec);
    }

    public B wireTap(WireTapSpec wireTapSpec) {
        WireTap interceptor = (WireTap)wireTapSpec.get();
        InterceptableChannel currentChannel = this.currentInterceptableChannel();
        this.addComponent(wireTapSpec);
        currentChannel.addInterceptor((ChannelInterceptor)interceptor);
        return this._this();
    }

    public B controlBus() {
        return this.controlBus(null);
    }

    public B controlBus(Consumer<GenericEndpointSpec<ServiceActivatingHandler>> endpointConfigurer) {
        return this.handle(new ServiceActivatingHandler(new ExpressionCommandMessageProcessor(new ControlBusMethodFilter())), endpointConfigurer);
    }

    public B transform(String expression) {
        return this.transform(expression, (Consumer<GenericEndpointSpec<MessageTransformingHandler>>)null);
    }

    public B transform(String expression, Consumer<GenericEndpointSpec<MessageTransformingHandler>> endpointConfigurer) {
        Assert.hasText((String)expression, (String)"'expression' must not be empty");
        return this.transform(null, new ExpressionEvaluatingTransformer(PARSER.parseExpression(expression)), endpointConfigurer);
    }

    public B transform(Object service) {
        return this.transform(service, null);
    }

    public B transform(Object service, String methodName) {
        return this.transform(service, methodName, null);
    }

    public B transform(Object service, String methodName, Consumer<GenericEndpointSpec<MessageTransformingHandler>> endpointConfigurer) {
        MethodInvokingTransformer transformer = StringUtils.hasText((String)methodName) ? new MethodInvokingTransformer(service, methodName) : new MethodInvokingTransformer(service);
        return this.transform(null, transformer, endpointConfigurer);
    }

    public B transform(MessageProcessorSpec<?> messageProcessorSpec) {
        return this.transform(messageProcessorSpec, (Consumer<GenericEndpointSpec<MessageTransformingHandler>>)null);
    }

    public B transform(MessageProcessorSpec<?> messageProcessorSpec, Consumer<GenericEndpointSpec<MessageTransformingHandler>> endpointConfigurer) {
        Assert.notNull(messageProcessorSpec, (String)MESSAGE_PROCESSOR_SPEC_MUST_NOT_BE_NULL);
        MessageProcessor processor = (MessageProcessor)messageProcessorSpec.get();
        return ((BaseIntegrationFlowDefinition)this.addComponent(processor)).transform(null, new MethodInvokingTransformer((Object)processor), endpointConfigurer);
    }

    public <P> B convert(Class<P> payloadType) {
        Assert.isTrue((!payloadType.equals(Message.class) ? 1 : 0) != 0, (String)".convert() does not support Message as an explicit type");
        return this.transform(payloadType, (P p) -> p);
    }

    public <P, T> B transform(Class<P> expectedType, GenericTransformer<P, T> genericTransformer) {
        return this.transform(expectedType, genericTransformer, null);
    }

    public <P> B convert(Class<P> payloadType, Consumer<GenericEndpointSpec<MessageTransformingHandler>> endpointConfigurer) {
        Assert.isTrue((!payloadType.equals(Message.class) ? 1 : 0) != 0, (String)".convert() does not support Message");
        return this.transform(payloadType, (P p) -> p, endpointConfigurer);
    }

    public <P, T> B transform(Class<P> expectedType, GenericTransformer<P, T> genericTransformer, Consumer<GenericEndpointSpec<MessageTransformingHandler>> endpointConfigurer) {
        Assert.notNull(genericTransformer, (String)"'genericTransformer' must not be null");
        Transformer transformer = genericTransformer instanceof Transformer ? (Transformer)genericTransformer : (ClassUtils.isLambda(genericTransformer.getClass()) ? new MethodInvokingTransformer((Object)new LambdaMessageProcessor(genericTransformer, expectedType)) : new MethodInvokingTransformer(genericTransformer, ClassUtils.TRANSFORMER_TRANSFORM_METHOD));
        return ((BaseIntegrationFlowDefinition)this.addComponent(transformer)).handle((MessageTransformingHandler)new MessageTransformingHandler(transformer), endpointConfigurer);
    }

    public B filter(String expression) {
        return this.filter(expression, (Consumer<FilterEndpointSpec>)null);
    }

    public B filter(String expression, Consumer<FilterEndpointSpec> endpointConfigurer) {
        Assert.hasText((String)expression, (String)"'expression' must not be empty");
        return this.filter(null, new ExpressionEvaluatingSelector(expression), endpointConfigurer);
    }

    public B filter(Object service) {
        return this.filter(service, null);
    }

    public B filter(Object service, String methodName) {
        return this.filter(service, methodName, null);
    }

    public B filter(Object service, String methodName, Consumer<FilterEndpointSpec> endpointConfigurer) {
        MethodInvokingSelector selector = StringUtils.hasText((String)methodName) ? new MethodInvokingSelector(service, methodName) : new MethodInvokingSelector(service);
        return this.filter(null, selector, endpointConfigurer);
    }

    public B filter(MessageProcessorSpec<?> messageProcessorSpec) {
        return this.filter(messageProcessorSpec, (Consumer<FilterEndpointSpec>)null);
    }

    public B filter(MessageProcessorSpec<?> messageProcessorSpec, Consumer<FilterEndpointSpec> endpointConfigurer) {
        Assert.notNull(messageProcessorSpec, (String)MESSAGE_PROCESSOR_SPEC_MUST_NOT_BE_NULL);
        MessageProcessor processor = (MessageProcessor)messageProcessorSpec.get();
        return ((BaseIntegrationFlowDefinition)this.addComponent(processor)).filter(null, new MethodInvokingSelector((Object)processor), endpointConfigurer);
    }

    public <P> B filter(Class<P> expectedType, GenericSelector<P> genericSelector) {
        return this.filter(expectedType, genericSelector, null);
    }

    public <P> B filter(Class<P> expectedType, GenericSelector<P> genericSelector, Consumer<FilterEndpointSpec> endpointConfigurer) {
        Assert.notNull(genericSelector, (String)"'genericSelector' must not be null");
        MessageSelector selector = genericSelector instanceof MessageSelector ? (MessageSelector)genericSelector : (ClassUtils.isLambda(genericSelector.getClass()) ? new MethodInvokingSelector((Object)new LambdaMessageProcessor(genericSelector, expectedType)) : new MethodInvokingSelector(genericSelector, ClassUtils.SELECTOR_ACCEPT_METHOD));
        return this.register(new FilterEndpointSpec(new MessageFilter(selector)), endpointConfigurer);
    }

    public <H extends MessageHandler> B handle(MessageHandlerSpec<?, H> messageHandlerSpec) {
        return this.handle((H)messageHandlerSpec, (Consumer<GenericEndpointSpec<H>>)null);
    }

    public B handle(MessageHandler messageHandler) {
        return this.handle(messageHandler, (Consumer)null);
    }

    public B handle(String beanName, String methodName) {
        return this.handle(beanName, methodName, null);
    }

    public B handle(String beanName, String methodName, Consumer<GenericEndpointSpec<ServiceActivatingHandler>> endpointConfigurer) {
        return this.handle(new ServiceActivatingHandler(new BeanNameMessageProcessor(beanName, methodName)), endpointConfigurer);
    }

    public B handle(Object service) {
        return this.handle(service, null);
    }

    public B handle(Object service, String methodName) {
        return this.handle(service, methodName, null);
    }

    public B handle(Object service, String methodName, Consumer<GenericEndpointSpec<ServiceActivatingHandler>> endpointConfigurer) {
        ServiceActivatingHandler handler = StringUtils.hasText((String)methodName) ? new ServiceActivatingHandler(service, methodName) : new ServiceActivatingHandler(service);
        return this.handle(handler, endpointConfigurer);
    }

    public <P> B handle(Class<P> expectedType, GenericHandler<P> handler) {
        return this.handle(expectedType, handler, null);
    }

    public <P> B handle(Class<P> expectedType, GenericHandler<P> handler, Consumer<GenericEndpointSpec<ServiceActivatingHandler>> endpointConfigurer) {
        ServiceActivatingHandler serviceActivatingHandler;
        if (ClassUtils.isLambda(handler.getClass())) {
            serviceActivatingHandler = new ServiceActivatingHandler(new LambdaMessageProcessor(handler, expectedType));
        } else {
            if (expectedType != null) {
                return this.handle(expectedType, handler::handle, endpointConfigurer);
            }
            serviceActivatingHandler = new ServiceActivatingHandler(handler, ClassUtils.HANDLER_HANDLE_METHOD);
        }
        return this.handle(serviceActivatingHandler, endpointConfigurer);
    }

    public B handle(MessageProcessorSpec<?> messageProcessorSpec) {
        return this.handle((MessageHandler)messageProcessorSpec, (Consumer)null);
    }

    public B handle(MessageProcessorSpec<?> messageProcessorSpec, Consumer<GenericEndpointSpec<ServiceActivatingHandler>> endpointConfigurer) {
        Assert.notNull(messageProcessorSpec, (String)MESSAGE_PROCESSOR_SPEC_MUST_NOT_BE_NULL);
        MessageProcessor processor = (MessageProcessor)messageProcessorSpec.get();
        return ((BaseIntegrationFlowDefinition)this.addComponent(processor)).handle((ServiceActivatingHandler)new ServiceActivatingHandler(processor), endpointConfigurer);
    }

    public <H extends MessageHandler> B handle(MessageHandlerSpec<?, H> messageHandlerSpec, Consumer<GenericEndpointSpec<H>> endpointConfigurer) {
        Assert.notNull(messageHandlerSpec, (String)"'messageHandlerSpec' must not be null");
        if (messageHandlerSpec instanceof ComponentsRegistration) {
            this.addComponents(((ComponentsRegistration)((Object)messageHandlerSpec)).getComponentsToRegister());
        }
        return this.handle((MessageHandler)messageHandlerSpec.get(), endpointConfigurer);
    }

    public <H extends MessageHandler> B handle(H messageHandler, Consumer<GenericEndpointSpec<H>> endpointConfigurer) {
        Assert.notNull(messageHandler, (String)"'messageHandler' must not be null");
        return this.register(new GenericEndpointSpec<H>(messageHandler), endpointConfigurer);
    }

    public B bridge() {
        return this.bridge(null);
    }

    public B bridge(Consumer<GenericEndpointSpec<BridgeHandler>> endpointConfigurer) {
        return this.register(new GenericEndpointSpec<BridgeHandler>(new BridgeHandler()), endpointConfigurer);
    }

    public B delay(String groupId) {
        return this.delay(groupId, null);
    }

    public B delay(String groupId, Consumer<DelayerEndpointSpec> endpointConfigurer) {
        return this.register(new DelayerEndpointSpec(new DelayHandler(groupId)), endpointConfigurer);
    }

    public B enrich(Consumer<EnricherSpec> enricherConfigurer) {
        return this.register(new EnricherSpec(), enricherConfigurer);
    }

    public B enrichHeaders(MapBuilder<?, String, Object> headers) {
        return this.enrichHeaders(headers, null);
    }

    public B enrichHeaders(MapBuilder<?, String, Object> headers, Consumer<GenericEndpointSpec<MessageTransformingHandler>> endpointConfigurer) {
        return this.enrichHeaders(headers.get(), endpointConfigurer);
    }

    public B enrichHeaders(Map<String, Object> headers) {
        return this.enrichHeaders(headers, null);
    }

    public B enrichHeaders(Map<String, Object> headers, Consumer<GenericEndpointSpec<MessageTransformingHandler>> endpointConfigurer) {
        HeaderEnricherSpec headerEnricherSpec = new HeaderEnricherSpec();
        headerEnricherSpec.headers(headers);
        Tuple2 tuple2 = (Tuple2)headerEnricherSpec.get();
        return ((BaseIntegrationFlowDefinition)this.addComponents(headerEnricherSpec.getComponentsToRegister())).handle((MessageTransformingHandler)((MessageTransformingHandler)tuple2.getT2()), endpointConfigurer);
    }

    public B enrichHeaders(Consumer<HeaderEnricherSpec> headerEnricherConfigurer) {
        Assert.notNull(headerEnricherConfigurer, (String)"'headerEnricherConfigurer' must not be null");
        return this.register(new HeaderEnricherSpec(), headerEnricherConfigurer);
    }

    public B split() {
        return this.split((Consumer<SplitterEndpointSpec<DefaultMessageSplitter>>)null);
    }

    public B split(Consumer<SplitterEndpointSpec<DefaultMessageSplitter>> endpointConfigurer) {
        return this.split(new DefaultMessageSplitter(), endpointConfigurer);
    }

    public B split(String expression) {
        return this.split((AbstractMessageSplitter)((Object)expression), (Consumer)null);
    }

    public B split(String expression, Consumer<SplitterEndpointSpec<ExpressionEvaluatingSplitter>> endpointConfigurer) {
        Assert.hasText((String)expression, (String)"'expression' must not be empty");
        return this.split(new ExpressionEvaluatingSplitter(PARSER.parseExpression(expression)), endpointConfigurer);
    }

    public B split(Object service) {
        return this.split(service, null);
    }

    public B split(Object service, String methodName) {
        return this.split(service, methodName, null);
    }

    public B split(Object service, String methodName, Consumer<SplitterEndpointSpec<MethodInvokingSplitter>> endpointConfigurer) {
        MethodInvokingSplitter splitter = StringUtils.hasText((String)methodName) ? new MethodInvokingSplitter(service, methodName) : new MethodInvokingSplitter(service);
        return this.split(splitter, endpointConfigurer);
    }

    public B split(String beanName, String methodName) {
        return this.split(beanName, methodName, null);
    }

    public B split(String beanName, String methodName, Consumer<SplitterEndpointSpec<MethodInvokingSplitter>> endpointConfigurer) {
        return this.split(new MethodInvokingSplitter((Object)new BeanNameMessageProcessor(beanName, methodName)), endpointConfigurer);
    }

    public B split(MessageProcessorSpec<?> messageProcessorSpec) {
        return this.split((AbstractMessageSplitter)((Object)messageProcessorSpec), (Consumer)null);
    }

    public B split(MessageProcessorSpec<?> messageProcessorSpec, Consumer<SplitterEndpointSpec<MethodInvokingSplitter>> endpointConfigurer) {
        Assert.notNull(messageProcessorSpec, (String)MESSAGE_PROCESSOR_SPEC_MUST_NOT_BE_NULL);
        MessageProcessor processor = (MessageProcessor)messageProcessorSpec.get();
        return ((BaseIntegrationFlowDefinition)this.addComponent(processor)).split((MethodInvokingSplitter)new MethodInvokingSplitter((Object)processor), endpointConfigurer);
    }

    public <P> B split(Class<P> expectedType, Function<P, ?> splitter) {
        return this.split(expectedType, splitter, null);
    }

    public <P> B split(Class<P> expectedType, Function<P, ?> splitter, Consumer<SplitterEndpointSpec<MethodInvokingSplitter>> endpointConfigurer) {
        MethodInvokingSplitter split2 = ClassUtils.isLambda(splitter.getClass()) ? new MethodInvokingSplitter((Object)new LambdaMessageProcessor(splitter, expectedType)) : new MethodInvokingSplitter(splitter, ClassUtils.FUNCTION_APPLY_METHOD);
        return this.split(split2, endpointConfigurer);
    }

    public <S extends AbstractMessageSplitter> B split(MessageHandlerSpec<?, S> splitterMessageHandlerSpec) {
        return this.split((S)((Object)splitterMessageHandlerSpec), (Consumer<SplitterEndpointSpec<S>>)null);
    }

    public <S extends AbstractMessageSplitter> B split(MessageHandlerSpec<?, S> splitterMessageHandlerSpec, Consumer<SplitterEndpointSpec<S>> endpointConfigurer) {
        Assert.notNull(splitterMessageHandlerSpec, (String)"'splitterMessageHandlerSpec' must not be null");
        return this.split((AbstractMessageSplitter)splitterMessageHandlerSpec.get(), endpointConfigurer);
    }

    public B split(AbstractMessageSplitter splitter) {
        return this.split(splitter, (Consumer)null);
    }

    public <S extends AbstractMessageSplitter> B split(S splitter, Consumer<SplitterEndpointSpec<S>> endpointConfigurer) {
        Assert.notNull(splitter, (String)"'splitter' must not be null");
        return this.register(new SplitterEndpointSpec<S>(splitter), endpointConfigurer);
    }

    public B headerFilter(String ... headersToRemove) {
        return this.headerFilter(new HeaderFilter(headersToRemove), null);
    }

    public B headerFilter(String headersToRemove, boolean patternMatch) {
        HeaderFilter headerFilter = new HeaderFilter(StringUtils.delimitedListToStringArray((String)headersToRemove, (String)",", (String)" "));
        headerFilter.setPatternMatch(patternMatch);
        return this.headerFilter(headerFilter, null);
    }

    public B headerFilter(HeaderFilter headerFilter, Consumer<GenericEndpointSpec<MessageTransformingHandler>> endpointConfigurer) {
        return this.transform(null, headerFilter, endpointConfigurer);
    }

    public B claimCheckIn(MessageStore messageStore) {
        return this.claimCheckIn(messageStore, null);
    }

    public B claimCheckIn(MessageStore messageStore, Consumer<GenericEndpointSpec<MessageTransformingHandler>> endpointConfigurer) {
        return this.transform(null, new ClaimCheckInTransformer(messageStore), endpointConfigurer);
    }

    public B claimCheckOut(MessageStore messageStore) {
        return this.claimCheckOut(messageStore, false);
    }

    public B claimCheckOut(MessageStore messageStore, boolean removeMessage) {
        return this.claimCheckOut(messageStore, removeMessage, null);
    }

    public B claimCheckOut(MessageStore messageStore, boolean removeMessage, Consumer<GenericEndpointSpec<MessageTransformingHandler>> endpointConfigurer) {
        ClaimCheckOutTransformer claimCheckOutTransformer = new ClaimCheckOutTransformer(messageStore);
        claimCheckOutTransformer.setRemoveMessage(removeMessage);
        return this.transform(null, claimCheckOutTransformer, endpointConfigurer);
    }

    public B resequence() {
        return this.resequence(null);
    }

    public B resequence(Consumer<ResequencerSpec> resequencer) {
        return this.register(new ResequencerSpec(), resequencer);
    }

    public B aggregate() {
        return this.aggregate(null);
    }

    public B aggregate(Object aggregatorProcessor) {
        return this.aggregate((AggregatorSpec aggregator) -> aggregator.processor(aggregatorProcessor));
    }

    public B aggregate(Consumer<AggregatorSpec> aggregator) {
        return this.register(new AggregatorSpec(), aggregator);
    }

    public B route(String beanName, String method) {
        return this.route(beanName, method, null);
    }

    public B route(String beanName, String method, Consumer<RouterSpec<Object, MethodInvokingRouter>> routerConfigurer) {
        MethodInvokingRouter methodInvokingRouter = new MethodInvokingRouter((Object)new BeanNameMessageProcessor(beanName, method));
        return this.route((AbstractMessageRouter)((Object)new RouterSpec(methodInvokingRouter)), (Consumer)routerConfigurer);
    }

    public B route(Object service) {
        return this.route(service, null);
    }

    public B route(Object service, String methodName) {
        return this.route(service, methodName, null);
    }

    public B route(Object service, String methodName, Consumer<RouterSpec<Object, MethodInvokingRouter>> routerConfigurer) {
        MethodInvokingRouter router = StringUtils.hasText((String)methodName) ? new MethodInvokingRouter(service, methodName) : new MethodInvokingRouter(service);
        return this.route((AbstractMessageRouter)((Object)new RouterSpec(router)), (Consumer)routerConfigurer);
    }

    public B route(String expression) {
        return this.route((AbstractMessageRouter)((Object)expression), (Consumer)null);
    }

    public <T> B route(String expression, Consumer<RouterSpec<T, ExpressionEvaluatingRouter>> routerConfigurer) {
        return this.route((AbstractMessageRouter)((Object)new RouterSpec(new ExpressionEvaluatingRouter(PARSER.parseExpression(expression)))), (Consumer)routerConfigurer);
    }

    public <S, T> B route(Class<S> expectedType, Function<S, T> router) {
        return this.route(expectedType, router, null);
    }

    public <P, T> B route(Class<P> expectedType, Function<P, T> router, Consumer<RouterSpec<T, MethodInvokingRouter>> routerConfigurer) {
        MethodInvokingRouter methodInvokingRouter = ClassUtils.isLambda(router.getClass()) ? new MethodInvokingRouter((Object)new LambdaMessageProcessor(router, expectedType)) : new MethodInvokingRouter(router, ClassUtils.FUNCTION_APPLY_METHOD);
        return this.route((AbstractMessageRouter)((Object)new RouterSpec(methodInvokingRouter)), (Consumer)routerConfigurer);
    }

    public B route(MessageProcessorSpec<?> messageProcessorSpec) {
        return this.route((AbstractMessageRouter)((Object)messageProcessorSpec), (Consumer)null);
    }

    public B route(MessageProcessorSpec<?> messageProcessorSpec, Consumer<RouterSpec<Object, MethodInvokingRouter>> routerConfigurer) {
        Assert.notNull(messageProcessorSpec, (String)MESSAGE_PROCESSOR_SPEC_MUST_NOT_BE_NULL);
        MessageProcessor processor = (MessageProcessor)messageProcessorSpec.get();
        this.addComponent(processor);
        return this.route((AbstractMessageRouter)((Object)new RouterSpec(new MethodInvokingRouter((Object)processor))), (Consumer)routerConfigurer);
    }

    protected <R extends AbstractMessageRouter, S extends AbstractRouterSpec<? super S, R>> B route(S routerSpec, Consumer<S> routerConfigurer) {
        if (routerConfigurer != null) {
            routerConfigurer.accept(routerSpec);
        }
        BridgeHandler bridgeHandler = new BridgeHandler();
        boolean registerSubflowBridge = false;
        LinkedHashMap<Object, String> componentsToRegister = null;
        Map<Object, String> routerComponents = routerSpec.getComponentsToRegister();
        if (routerComponents != null) {
            componentsToRegister = new LinkedHashMap<Object, String>(routerComponents);
            routerComponents.clear();
        }
        this.register(routerSpec, null);
        if (!CollectionUtils.isEmpty(componentsToRegister)) {
            for (Map.Entry entry : componentsToRegister.entrySet()) {
                Object component = entry.getKey();
                if (component instanceof BaseIntegrationFlowDefinition) {
                    BaseIntegrationFlowDefinition flowBuilder2 = (BaseIntegrationFlowDefinition)component;
                    if (flowBuilder2.isOutputChannelRequired()) {
                        registerSubflowBridge = true;
                        flowBuilder2.channel((MessageChannel)new FixedSubscriberChannel(bridgeHandler));
                    }
                    this.addComponent(flowBuilder2.get());
                    continue;
                }
                this.addComponent(component, (String)entry.getValue());
            }
        }
        if (routerSpec.isDefaultToParentFlow()) {
            routerSpec.defaultOutputChannel((MessageChannel)new FixedSubscriberChannel(bridgeHandler));
            registerSubflowBridge = true;
        }
        if (registerSubflowBridge) {
            ((BaseIntegrationFlowDefinition)this.currentComponent(null)).handle(bridgeHandler);
        }
        return this._this();
    }

    public B routeToRecipients(Consumer<RecipientListRouterSpec> routerConfigurer) {
        return this.route((AbstractMessageRouter)((Object)new RecipientListRouterSpec()), (Consumer)routerConfigurer);
    }

    public B routeByException(Consumer<RouterSpec<Class<? extends Throwable>, ErrorMessageExceptionTypeRouter>> routerConfigurer) {
        return this.route((AbstractMessageRouter)((Object)new RouterSpec(new ErrorMessageExceptionTypeRouter())), (Consumer)routerConfigurer);
    }

    public B route(AbstractMessageRouter router) {
        return this.route(router, (Consumer)null);
    }

    public <R extends AbstractMessageRouter> B route(R router, Consumer<GenericEndpointSpec<R>> endpointConfigurer) {
        return this.handle(router, endpointConfigurer);
    }

    public B gateway(String requestChannel) {
        return this.gateway(requestChannel, null);
    }

    public B gateway(String requestChannel, Consumer<GatewayEndpointSpec> endpointConfigurer) {
        return this.register(new GatewayEndpointSpec(requestChannel), endpointConfigurer);
    }

    public B gateway(MessageChannel requestChannel) {
        return this.gateway(requestChannel, null);
    }

    public B gateway(MessageChannel requestChannel, Consumer<GatewayEndpointSpec> endpointConfigurer) {
        return this.register(new GatewayEndpointSpec(requestChannel), endpointConfigurer);
    }

    public B gateway(IntegrationFlow flow) {
        return this.gateway(flow, null);
    }

    public B gateway(IntegrationFlow flow, Consumer<GatewayEndpointSpec> endpointConfigurer) {
        MessageChannel requestChannel = this.obtainInputChannelFromFlow(flow);
        return this.gateway(requestChannel, endpointConfigurer);
    }

    public B log() {
        return this.log(LoggingHandler.Level.INFO);
    }

    public B log(LoggingHandler.Level level) {
        return this.log(level, (String)null);
    }

    public B log(String category) {
        return this.log(LoggingHandler.Level.INFO, category);
    }

    public B log(LoggingHandler.Level level, String category) {
        return this.log(level, category, (Expression)null);
    }

    public B log(LoggingHandler.Level level, String category, String logExpression) {
        Assert.hasText((String)logExpression, (String)"'logExpression' must not be empty");
        return this.log(level, category, PARSER.parseExpression(logExpression));
    }

    public <P> B log(Function<Message<P>, Object> function) {
        Assert.notNull(function, (String)FUNCTION_MUST_NOT_BE_NULL);
        return this.log(new FunctionExpression<Message<P>>(function));
    }

    public B log(Expression logExpression) {
        return this.log(LoggingHandler.Level.INFO, logExpression);
    }

    public B log(LoggingHandler.Level level, Expression logExpression) {
        return this.log(level, null, logExpression);
    }

    public B log(String category, Expression logExpression) {
        return this.log(LoggingHandler.Level.INFO, category, logExpression);
    }

    public <P> B log(LoggingHandler.Level level, Function<Message<P>, Object> function) {
        return this.log(level, null, function);
    }

    public <P> B log(String category, Function<Message<P>, Object> function) {
        return this.log(LoggingHandler.Level.INFO, category, function);
    }

    public <P> B log(LoggingHandler.Level level, String category, Function<Message<P>, Object> function) {
        Assert.notNull(function, (String)FUNCTION_MUST_NOT_BE_NULL);
        return this.log(level, category, new FunctionExpression<Message<P>>(function));
    }

    public B log(LoggingHandler.Level level, String category, Expression logExpression) {
        LoggingHandler loggingHandler = new LoggingHandler(level);
        if (StringUtils.hasText((String)category)) {
            loggingHandler.setLoggerName(category);
        }
        if (logExpression != null) {
            loggingHandler.setLogExpression(logExpression);
        } else {
            loggingHandler.setShouldLogFullMessage(true);
        }
        this.addComponent(loggingHandler);
        FixedSubscriberChannel loggerChannel = new FixedSubscriberChannel(loggingHandler);
        return this.wireTap((MessageChannel)loggerChannel);
    }

    public IntegrationFlow logAndReply() {
        return this.logAndReply(LoggingHandler.Level.INFO);
    }

    public IntegrationFlow logAndReply(LoggingHandler.Level level) {
        return this.logAndReply(level, (String)null);
    }

    public IntegrationFlow logAndReply(String category) {
        return this.logAndReply(LoggingHandler.Level.INFO, category);
    }

    public IntegrationFlow logAndReply(LoggingHandler.Level level, String category) {
        return this.logAndReply(level, category, (Expression)null);
    }

    public IntegrationFlow logAndReply(LoggingHandler.Level level, String category, String logExpression) {
        Assert.hasText((String)logExpression, (String)"'logExpression' must not be empty");
        return this.logAndReply(level, category, PARSER.parseExpression(logExpression));
    }

    public <P> IntegrationFlow logAndReply(Function<Message<P>, Object> function) {
        Assert.notNull(function, (String)FUNCTION_MUST_NOT_BE_NULL);
        return this.logAndReply(new FunctionExpression<Message<P>>(function));
    }

    public IntegrationFlow logAndReply(Expression logExpression) {
        return this.logAndReply(LoggingHandler.Level.INFO, logExpression);
    }

    public IntegrationFlow logAndReply(LoggingHandler.Level level, Expression logExpression) {
        return this.logAndReply(level, null, logExpression);
    }

    public IntegrationFlow logAndReply(String category, Expression logExpression) {
        return this.logAndReply(LoggingHandler.Level.INFO, category, logExpression);
    }

    public <P> IntegrationFlow logAndReply(LoggingHandler.Level level, Function<Message<P>, Object> function) {
        return this.logAndReply(level, null, function);
    }

    public <P> IntegrationFlow logAndReply(String category, Function<Message<P>, Object> function) {
        return this.logAndReply(LoggingHandler.Level.INFO, category, function);
    }

    public <P> IntegrationFlow logAndReply(LoggingHandler.Level level, String category, Function<Message<P>, Object> function) {
        Assert.notNull(function, (String)FUNCTION_MUST_NOT_BE_NULL);
        return this.logAndReply(level, category, new FunctionExpression<Message<P>>(function));
    }

    public IntegrationFlow logAndReply(LoggingHandler.Level level, String category, Expression logExpression) {
        return ((BaseIntegrationFlowDefinition)((BaseIntegrationFlowDefinition)this.log(level, category, logExpression)).bridge()).get();
    }

    public B scatterGather(MessageChannel scatterChannel) {
        return this.scatterGather(scatterChannel, null);
    }

    public B scatterGather(MessageChannel scatterChannel, Consumer<AggregatorSpec> gatherer) {
        return this.scatterGather(scatterChannel, gatherer, null);
    }

    public B scatterGather(MessageChannel scatterChannel, Consumer<AggregatorSpec> gatherer, Consumer<ScatterGatherSpec> scatterGather2) {
        AggregatorSpec aggregatorSpec = new AggregatorSpec();
        if (gatherer != null) {
            gatherer.accept(aggregatorSpec);
        }
        AggregatingMessageHandler aggregatingMessageHandler = (AggregatingMessageHandler)((Tuple2)aggregatorSpec.get()).getT2();
        this.addComponent(aggregatingMessageHandler);
        ScatterGatherHandler messageHandler = new ScatterGatherHandler(scatterChannel, (MessageHandler)aggregatingMessageHandler);
        return this.register(new ScatterGatherSpec(messageHandler), scatterGather2);
    }

    public B scatterGather(Consumer<RecipientListRouterSpec> scatterer) {
        return this.scatterGather(scatterer, null);
    }

    public B scatterGather(Consumer<RecipientListRouterSpec> scatterer, @Nullable Consumer<AggregatorSpec> gatherer) {
        return this.scatterGather(scatterer, gatherer, null);
    }

    public B scatterGather(Consumer<RecipientListRouterSpec> scatterer, @Nullable Consumer<AggregatorSpec> gatherer, @Nullable Consumer<ScatterGatherSpec> scatterGather2) {
        Assert.notNull(scatterer, (String)"'scatterer' must not be null");
        RecipientListRouterSpec recipientListRouterSpec = new RecipientListRouterSpec();
        scatterer.accept(recipientListRouterSpec);
        AggregatorSpec aggregatorSpec = new AggregatorSpec();
        if (gatherer != null) {
            gatherer.accept(aggregatorSpec);
        }
        RecipientListRouter recipientListRouter = (RecipientListRouter)((Tuple2)recipientListRouterSpec.get()).getT2();
        ((BaseIntegrationFlowDefinition)this.addComponent(recipientListRouter)).addComponents(recipientListRouterSpec.getComponentsToRegister());
        AggregatingMessageHandler aggregatingMessageHandler = (AggregatingMessageHandler)((Tuple2)aggregatorSpec.get()).getT2();
        this.addComponent(aggregatingMessageHandler);
        ScatterGatherHandler messageHandler = new ScatterGatherHandler(recipientListRouter, (MessageHandler)aggregatingMessageHandler);
        return this.register(new ScatterGatherSpec(messageHandler), scatterGather2);
    }

    public B barrier(long timeout) {
        return this.barrier(timeout, null);
    }

    public B barrier(long timeout, Consumer<BarrierSpec> barrierConfigurer) {
        return this.register(new BarrierSpec(timeout), barrierConfigurer);
    }

    public B trigger(String triggerActionId) {
        return this.trigger(triggerActionId, null);
    }

    public B trigger(String triggerActionId, Consumer<GenericEndpointSpec<ServiceActivatingHandler>> endpointConfigurer) {
        BeanNameMessageProcessor trigger2 = new BeanNameMessageProcessor(triggerActionId, "trigger");
        return this.handle(new ServiceActivatingHandler(trigger2), endpointConfigurer);
    }

    public B trigger(MessageTriggerAction triggerAction) {
        return this.trigger(triggerAction, null);
    }

    public B trigger(MessageTriggerAction triggerAction, Consumer<GenericEndpointSpec<ServiceActivatingHandler>> endpointConfigurer) {
        return this.handle(new ServiceActivatingHandler((Object)triggerAction, "trigger"), endpointConfigurer);
    }

    public B intercept(ChannelInterceptor ... interceptorArray) {
        Assert.notNull((Object)interceptorArray, (String)"'interceptorArray' must not be null");
        Assert.noNullElements((Object[])interceptorArray, (String)"'interceptorArray' must not contain null elements");
        InterceptableChannel currentChannel = this.currentInterceptableChannel();
        for (ChannelInterceptor interceptor : interceptorArray) {
            currentChannel.addInterceptor(interceptor);
        }
        return this._this();
    }

    public <I, O> B fluxTransform(Function<? super Flux<Message<I>>, ? extends Publisher<O>> fluxFunction) {
        MessageChannel currentChannel = this.getCurrentMessageChannel();
        if (!(currentChannel instanceof FluxMessageChannel)) {
            currentChannel = new FluxMessageChannel();
            this.channel(currentChannel);
        }
        Publisher upstream = (Publisher)currentChannel;
        Flux result = Transformers.transformWithFunction(upstream, fluxFunction);
        FluxMessageChannel downstream = new FluxMessageChannel();
        downstream.subscribeTo((Publisher<? extends Message<?>>)result);
        return ((BaseIntegrationFlowDefinition)this.currentMessageChannel(downstream)).addComponent(downstream);
    }

    public IntegrationFlow nullChannel() {
        return ((BaseIntegrationFlowDefinition)this.channel("nullChannel")).get();
    }

    public IntegrationFlow to(IntegrationFlow other) {
        MessageChannel otherFlowInputChannel = this.obtainInputChannelFromFlow(other);
        return ((BaseIntegrationFlowDefinition)this.channel(otherFlowInputChannel)).get();
    }

    protected <T> Publisher<Message<T>> toReactivePublisher() {
        return this.toReactivePublisher(false);
    }

    protected <T> Publisher<Message<T>> toReactivePublisher(boolean autoStartOnSubscribe) {
        Flux publisher;
        MessageChannel channelForPublisher = this.getCurrentMessageChannel();
        Map<Object, String> components = this.getIntegrationComponents();
        if (channelForPublisher instanceof Publisher) {
            publisher = (Flux)channelForPublisher;
        } else if (channelForPublisher != null && components.size() > 1 && !(channelForPublisher instanceof MessageChannelReference) && !(channelForPublisher instanceof FixedSubscriberChannelPrototype)) {
            publisher = IntegrationReactiveUtils.messageChannelToFlux(channelForPublisher);
        } else {
            FluxMessageChannel reactiveChannel = new FluxMessageChannel();
            publisher = reactiveChannel;
            this.channel(reactiveChannel);
        }
        this.setImplicitChannel(false);
        this.get();
        return new PublisherIntegrationFlow(components, publisher, autoStartOnSubscribe);
    }

    protected <S extends ConsumerEndpointSpec<? super S, ? extends MessageHandler>> B register(S endpointSpec, Consumer<S> endpointConfigurer) {
        if (endpointConfigurer != null) {
            endpointConfigurer.accept(endpointSpec);
        }
        Object inputChannel = this.getCurrentMessageChannel();
        this.currentMessageChannel(null);
        if (inputChannel == null) {
            inputChannel = new DirectChannel();
            this.registerOutputChannelIfCan((MessageChannel)inputChannel);
        }
        Tuple2 factoryBeanTuple2 = (Tuple2)endpointSpec.get();
        this.addComponents(endpointSpec.getComponentsToRegister());
        if (inputChannel instanceof MessageChannelReference) {
            ((ConsumerEndpointFactoryBean)factoryBeanTuple2.getT1()).setInputChannelName(((MessageChannelReference)inputChannel).getName());
        } else {
            if (inputChannel instanceof FixedSubscriberChannelPrototype) {
                String beanName = ((FixedSubscriberChannelPrototype)inputChannel).getName();
                inputChannel = new FixedSubscriberChannel((MessageHandler)factoryBeanTuple2.getT2());
                if (beanName != null) {
                    ((FixedSubscriberChannel)inputChannel).setBeanName(beanName);
                }
                this.registerOutputChannelIfCan((MessageChannel)inputChannel);
            }
            ((ConsumerEndpointFactoryBean)factoryBeanTuple2.getT1()).setInputChannel((MessageChannel)inputChannel);
        }
        return ((BaseIntegrationFlowDefinition)this.addComponent(endpointSpec)).currentComponent(factoryBeanTuple2.getT2());
    }

    protected B registerOutputChannelIfCan(MessageChannel outputChannel) {
        if (!(outputChannel instanceof FixedSubscriberChannelPrototype)) {
            this.addComponent(outputChannel, null);
            Object currComponent = this.getCurrentComponent();
            if (currComponent != null) {
                String channelName = null;
                if (outputChannel instanceof MessageChannelReference) {
                    channelName = ((MessageChannelReference)outputChannel).getName();
                }
                if (currComponent instanceof MessageProducer) {
                    MessageProducer messageProducer = (MessageProducer)currComponent;
                    this.checkReuse(messageProducer);
                    if (channelName != null) {
                        messageProducer.setOutputChannelName(channelName);
                    } else {
                        messageProducer.setOutputChannel(outputChannel);
                    }
                } else if (currComponent instanceof SourcePollingChannelAdapterSpec) {
                    SourcePollingChannelAdapterFactoryBean pollingChannelAdapterFactoryBean = (SourcePollingChannelAdapterFactoryBean)((Tuple2)((SourcePollingChannelAdapterSpec)currComponent).get()).getT1();
                    if (channelName != null) {
                        pollingChannelAdapterFactoryBean.setOutputChannelName(channelName);
                    } else {
                        pollingChannelAdapterFactoryBean.setOutputChannel(outputChannel);
                    }
                } else {
                    throw new BeanCreationException("The 'currentComponent' (" + currComponent + ") is a one-way 'MessageHandler' and it isn't appropriate to configure 'outputChannel'. This is the end of the integration flow.");
                }
                this.currentComponent(null);
            }
        }
        return this._this();
    }

    protected boolean isOutputChannelRequired() {
        Object currentElement = this.getCurrentComponent();
        if (currentElement != null) {
            if (AopUtils.isAopProxy((Object)currentElement)) {
                currentElement = BaseIntegrationFlowDefinition.extractProxyTarget(currentElement);
            }
            return currentElement instanceof AbstractMessageProducingHandler || currentElement instanceof SourcePollingChannelAdapterSpec;
        }
        return false;
    }

    protected final B _this() {
        return (B)this;
    }

    protected StandardIntegrationFlow get() {
        if (this.integrationFlow == null) {
            Optional lastComponent;
            MessageChannel currentChannel = this.getCurrentMessageChannel();
            if (currentChannel instanceof FixedSubscriberChannelPrototype) {
                throw new BeanCreationException("The 'currentMessageChannel' (" + currentChannel + ") is a prototype for 'FixedSubscriberChannel' which can't be created without a 'MessageHandler' constructor argument. That means that '.fixedSubscriberChannel()' can't be the last EIP-method in the 'IntegrationFlow' definition.");
            }
            Map<Object, String> components = this.getIntegrationComponents();
            if (components.size() == 1) {
                Object currComponent = this.getCurrentComponent();
                if (currComponent != null) {
                    if (currComponent instanceof SourcePollingChannelAdapterSpec) {
                        throw new BeanCreationException("The 'SourcePollingChannelAdapter' (" + currComponent + ") must be configured with at least one 'MessageChannel' or 'MessageHandler'.");
                    }
                } else if (currentChannel != null) {
                    throw new BeanCreationException("The 'IntegrationFlow' can't consist of only one 'MessageChannel'. Add at least '.bridge()' EIP-method before the end of flow.");
                }
            }
            if (this.isImplicitChannel() && (lastComponent = components.keySet().stream().reduce((first, second) -> second)).get() instanceof WireTapSpec) {
                this.channel("nullChannel");
            }
            this.integrationFlow = new StandardIntegrationFlow(components);
        }
        return this.integrationFlow;
    }

    protected void checkReuse(MessageProducer replyHandler) {
        Assert.isTrue((!REFERENCED_REPLY_PRODUCERS.contains(replyHandler) ? 1 : 0) != 0, (String)("A reply MessageProducer may only be referenced once (" + replyHandler + ") - use @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) on @Bean definition."));
        REFERENCED_REPLY_PRODUCERS.add(replyHandler);
    }

    protected static Object extractProxyTarget(Object target) {
        if (!(target instanceof Advised)) {
            return target;
        }
        Advised advised = (Advised)target;
        try {
            return BaseIntegrationFlowDefinition.extractProxyTarget(advised.getTargetSource().getTarget());
        }
        catch (Exception e) {
            throw new BeanCreationException("Could not extract target", (Throwable)e);
        }
    }

    public static final class ReplyProducerCleaner
    implements DestructionAwareBeanPostProcessor {
        public boolean requiresDestruction(Object bean) {
            return bean instanceof MessageProducer && REFERENCED_REPLY_PRODUCERS.contains(bean);
        }

        public void postProcessBeforeDestruction(Object bean, String beanName) throws BeansException {
            if (bean instanceof MessageProducer) {
                REFERENCED_REPLY_PRODUCERS.remove(bean);
            }
        }
    }
}

