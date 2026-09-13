/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.expression.Expression
 *  org.springframework.messaging.Message
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 *  reactor.util.function.Tuple2
 */
package org.springframework.integration.dsl;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import org.springframework.expression.Expression;
import org.springframework.integration.config.ConsumerEndpointFactoryBean;
import org.springframework.integration.dsl.ConsumerEndpointSpec;
import org.springframework.integration.expression.FunctionExpression;
import org.springframework.integration.handler.BeanNameMessageProcessor;
import org.springframework.integration.handler.ExpressionEvaluatingMessageProcessor;
import org.springframework.integration.handler.MessageProcessor;
import org.springframework.integration.support.MapBuilder;
import org.springframework.integration.support.StringStringMapBuilder;
import org.springframework.integration.transformer.HeaderEnricher;
import org.springframework.integration.transformer.MessageTransformingHandler;
import org.springframework.integration.transformer.support.ExpressionEvaluatingHeaderValueMessageProcessor;
import org.springframework.integration.transformer.support.HeaderValueMessageProcessor;
import org.springframework.integration.transformer.support.RoutingSlipHeaderValueMessageProcessor;
import org.springframework.integration.transformer.support.StaticHeaderValueMessageProcessor;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import reactor.util.function.Tuple2;

public class HeaderEnricherSpec
extends ConsumerEndpointSpec<HeaderEnricherSpec, MessageTransformingHandler> {
    private static final String HEADERS_MUST_NOT_BE_NULL = "'headers' must not be null";
    protected final Map<String, HeaderValueMessageProcessor<?>> headerToAdd = new HashMap();
    protected final HeaderEnricher headerEnricher = new HeaderEnricher(this.headerToAdd);

    protected HeaderEnricherSpec() {
        super(null);
        this.handler = new MessageTransformingHandler(this.headerEnricher);
    }

    public HeaderEnricherSpec defaultOverwrite(boolean defaultOverwrite) {
        this.headerEnricher.setDefaultOverwrite(defaultOverwrite);
        return (HeaderEnricherSpec)this._this();
    }

    public HeaderEnricherSpec shouldSkipNulls(boolean shouldSkipNulls) {
        this.headerEnricher.setShouldSkipNulls(shouldSkipNulls);
        return (HeaderEnricherSpec)this._this();
    }

    public HeaderEnricherSpec messageProcessor(MessageProcessor<?> messageProcessor) {
        this.headerEnricher.setMessageProcessor(messageProcessor);
        return (HeaderEnricherSpec)this._this();
    }

    public HeaderEnricherSpec messageProcessor(String expression) {
        return this.messageProcessor(new ExpressionEvaluatingMessageProcessor(expression));
    }

    public HeaderEnricherSpec messageProcessor(String beanName, String methodName) {
        return this.messageProcessor(new BeanNameMessageProcessor(beanName, methodName));
    }

    public HeaderEnricherSpec headers(MapBuilder<?, String, Object> headers) {
        return this.headers(headers, null);
    }

    public HeaderEnricherSpec headers(MapBuilder<?, String, Object> headers, Boolean overwrite) {
        Assert.notNull(headers, (String)HEADERS_MUST_NOT_BE_NULL);
        return this.headers(headers.get(), overwrite);
    }

    public HeaderEnricherSpec headers(Map<String, Object> headers) {
        return this.headers(headers, null);
    }

    public HeaderEnricherSpec headers(Map<String, Object> headers, Boolean overwrite) {
        Assert.notNull(headers, (String)HEADERS_MUST_NOT_BE_NULL);
        for (Map.Entry<String, Object> entry : headers.entrySet()) {
            String name = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof Expression) {
                ExpressionEvaluatingHeaderValueMessageProcessor processor = new ExpressionEvaluatingHeaderValueMessageProcessor((Expression)value, null);
                processor.setOverwrite(overwrite);
                this.header(name, processor);
                continue;
            }
            this.header(name, value, overwrite);
        }
        return this;
    }

    public HeaderEnricherSpec headerExpressions(MapBuilder<?, String, String> headers) {
        return this.headerExpressions(headers, null);
    }

    public HeaderEnricherSpec headerExpressions(MapBuilder<?, String, String> headers, Boolean overwrite) {
        Assert.notNull(headers, (String)HEADERS_MUST_NOT_BE_NULL);
        return this.headerExpressions(headers.get(), overwrite);
    }

    public HeaderEnricherSpec headerExpressions(Consumer<StringStringMapBuilder> configurer) {
        return this.headerExpressions(configurer, null);
    }

    public HeaderEnricherSpec headerExpressions(Consumer<StringStringMapBuilder> configurer, Boolean overwrite) {
        Assert.notNull(configurer, (String)"'configurer' must not be null");
        StringStringMapBuilder builder = new StringStringMapBuilder();
        configurer.accept(builder);
        return this.headerExpressions(builder.get(), overwrite);
    }

    public HeaderEnricherSpec headerExpressions(Map<String, String> headers) {
        return this.headerExpressions(headers, null);
    }

    public HeaderEnricherSpec headerExpressions(Map<String, String> headers, Boolean overwrite) {
        Assert.notNull(headers, (String)HEADERS_MUST_NOT_BE_NULL);
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            ExpressionEvaluatingHeaderValueMessageProcessor processor = new ExpressionEvaluatingHeaderValueMessageProcessor(entry.getValue(), null);
            processor.setOverwrite(overwrite);
            this.header(entry.getKey(), processor);
        }
        return this;
    }

    public HeaderEnricherSpec correlationId(Object correlationId) {
        return this.correlationId(correlationId, null);
    }

    public HeaderEnricherSpec correlationId(Object correlationId, Boolean overwrite) {
        return this.header("correlationId", correlationId, overwrite);
    }

    public HeaderEnricherSpec correlationIdExpression(String correlationIdExpression) {
        return this.correlationIdExpression(correlationIdExpression, null);
    }

    public HeaderEnricherSpec correlationIdExpression(String correlationIdExpression, Boolean overwrite) {
        return this.headerExpression("correlationId", correlationIdExpression, overwrite);
    }

    public <P> HeaderEnricherSpec correlationIdFunction(Function<Message<P>, Object> correlationIdFunction) {
        return this.correlationIdFunction(correlationIdFunction, null);
    }

    public <P> HeaderEnricherSpec correlationIdFunction(Function<Message<P>, ?> correlationIdFunction, Boolean overwrite) {
        return this.headerFunction("correlationId", correlationIdFunction, overwrite);
    }

    public HeaderEnricherSpec replyChannel(Object replyChannel) {
        return this.replyChannel(replyChannel, null);
    }

    public HeaderEnricherSpec replyChannel(Object replyChannel, Boolean overwrite) {
        return this.header("replyChannel", replyChannel, overwrite);
    }

    public HeaderEnricherSpec replyChannelExpression(String replyChannelExpression) {
        return this.replyChannelExpression(replyChannelExpression, null);
    }

    public HeaderEnricherSpec replyChannelExpression(String replyChannelExpression, Boolean overwrite) {
        return this.headerExpression("replyChannel", replyChannelExpression, overwrite);
    }

    public <P> HeaderEnricherSpec replyChannelFunction(Function<Message<P>, Object> replyChannelFunction) {
        return this.replyChannelFunction(replyChannelFunction, null);
    }

    public <P> HeaderEnricherSpec replyChannelFunction(Function<Message<P>, ?> replyChannelFunction, Boolean overwrite) {
        return this.headerFunction("replyChannel", replyChannelFunction, overwrite);
    }

    public HeaderEnricherSpec errorChannel(Object errorChannel) {
        return this.errorChannel(errorChannel, null);
    }

    public HeaderEnricherSpec errorChannel(Object errorChannel, Boolean overwrite) {
        return this.header("errorChannel", errorChannel, overwrite);
    }

    public HeaderEnricherSpec errorChannelExpression(String errorChannelExpression) {
        return this.errorChannelExpression(errorChannelExpression, null);
    }

    public HeaderEnricherSpec errorChannelExpression(String errorChannelExpression, Boolean overwrite) {
        return this.headerExpression("errorChannel", errorChannelExpression, overwrite);
    }

    public <P> HeaderEnricherSpec errorChannelFunction(Function<Message<P>, Object> errorChannelFunction) {
        return this.errorChannelFunction(errorChannelFunction, null);
    }

    public <P> HeaderEnricherSpec errorChannelFunction(Function<Message<P>, ?> errorChannelFunction, Boolean overwrite) {
        return this.headerFunction("errorChannel", errorChannelFunction, overwrite);
    }

    public HeaderEnricherSpec priority(Number priority) {
        return this.priority(priority, null);
    }

    public HeaderEnricherSpec priority(Number priority, Boolean overwrite) {
        return this.header("priority", priority, overwrite);
    }

    public HeaderEnricherSpec priorityExpression(String priorityExpression) {
        return this.priorityExpression(priorityExpression, null);
    }

    public HeaderEnricherSpec priorityExpression(String priorityExpression, Boolean overwrite) {
        return this.headerExpression("priority", priorityExpression, overwrite);
    }

    public <P> HeaderEnricherSpec priorityFunction(Function<Message<P>, Object> priorityFunction) {
        return this.priorityFunction(priorityFunction, null);
    }

    public <P> HeaderEnricherSpec priorityFunction(Function<Message<P>, ?> priorityFunction, Boolean overwrite) {
        return this.headerFunction("priority", priorityFunction, overwrite);
    }

    public HeaderEnricherSpec expirationDate(Object expirationDate) {
        return this.expirationDate(expirationDate, null);
    }

    public HeaderEnricherSpec expirationDate(Object expirationDate, Boolean overwrite) {
        return this.header("expirationDate", expirationDate, overwrite);
    }

    public HeaderEnricherSpec expirationDateExpression(String expirationDateExpression) {
        return this.expirationDateExpression(expirationDateExpression, null);
    }

    public HeaderEnricherSpec expirationDateExpression(String expirationDateExpression, Boolean overwrite) {
        return this.headerExpression("expirationDate", expirationDateExpression, overwrite);
    }

    public <P> HeaderEnricherSpec expirationDateFunction(Function<Message<P>, Object> expirationDateFunction) {
        return this.expirationDateFunction(expirationDateFunction, null);
    }

    public <P> HeaderEnricherSpec expirationDateFunction(Function<Message<P>, ?> expirationDateFunction, Boolean overwrite) {
        return this.headerFunction("expirationDate", expirationDateFunction, overwrite);
    }

    public HeaderEnricherSpec routingSlip(Object ... routingSlipPath) {
        return this.routingSlip(null, routingSlipPath);
    }

    public HeaderEnricherSpec routingSlip(Boolean overwrite, Object ... routingSlipPath) {
        RoutingSlipHeaderValueMessageProcessor routingSlipHeaderValueMessageProcessor = new RoutingSlipHeaderValueMessageProcessor(routingSlipPath);
        routingSlipHeaderValueMessageProcessor.setOverwrite(overwrite);
        return this.header("routingSlip", routingSlipHeaderValueMessageProcessor);
    }

    public <V> HeaderEnricherSpec header(String name, V value) {
        return this.header(name, value, null);
    }

    public <V> HeaderEnricherSpec header(String name, V value, Boolean overwrite) {
        StaticHeaderValueMessageProcessor<V> headerValueMessageProcessor = new StaticHeaderValueMessageProcessor<V>(value);
        headerValueMessageProcessor.setOverwrite(overwrite);
        return this.header(name, (HeaderValueMessageProcessor<V>)headerValueMessageProcessor);
    }

    public HeaderEnricherSpec headerExpression(String name, String expression) {
        return this.headerExpression(name, expression, null);
    }

    public HeaderEnricherSpec headerExpression(String name, String expression, Boolean overwrite) {
        Assert.hasText((String)expression, (String)"'expression' must not be empty");
        return this.headerExpression(name, PARSER.parseExpression(expression), overwrite);
    }

    public <P> HeaderEnricherSpec headerFunction(String name, Function<Message<P>, ?> function) {
        return this.headerFunction(name, function, null);
    }

    public <P> HeaderEnricherSpec headerFunction(String name, Function<Message<P>, ?> function, Boolean overwrite) {
        return this.headerExpression(name, new FunctionExpression<Message<P>>(function), overwrite);
    }

    private HeaderEnricherSpec headerExpression(String name, Expression expression, Boolean overwrite) {
        ExpressionEvaluatingHeaderValueMessageProcessor headerValueMessageProcessor = new ExpressionEvaluatingHeaderValueMessageProcessor(expression, null);
        headerValueMessageProcessor.setOverwrite(overwrite);
        return this.header(name, headerValueMessageProcessor);
    }

    public <V> HeaderEnricherSpec header(String headerName, HeaderValueMessageProcessor<V> headerValueMessageProcessor) {
        Assert.hasText((String)headerName, (String)"'headerName' must not be empty");
        this.headerToAdd.put(headerName, headerValueMessageProcessor);
        return (HeaderEnricherSpec)this._this();
    }

    public HeaderEnricherSpec headerChannelsToString() {
        return this.headerChannelsToString(null);
    }

    public HeaderEnricherSpec headerChannelsToString(String timeToLiveExpression) {
        return this.headerExpression("replyChannel", "@integrationHeaderChannelRegistry.channelToChannelName(headers.replyChannel" + (StringUtils.hasText((String)timeToLiveExpression) ? ", " + timeToLiveExpression : "") + ")", (Boolean)true).headerExpression("errorChannel", "@integrationHeaderChannelRegistry.channelToChannelName(headers.errorChannel" + (StringUtils.hasText((String)timeToLiveExpression) ? ", " + timeToLiveExpression : "") + ")", (Boolean)true);
    }

    @Override
    protected Tuple2<ConsumerEndpointFactoryBean, MessageTransformingHandler> doGet() {
        this.componentsToRegister.put(this.headerEnricher, null);
        return super.doGet();
    }
}

