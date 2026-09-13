/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.expression.Expression
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.util.Assert
 *  reactor.util.function.Tuple2
 */
package org.springframework.integration.dsl;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.expression.Expression;
import org.springframework.integration.config.ConsumerEndpointFactoryBean;
import org.springframework.integration.dsl.ConsumerEndpointSpec;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.expression.FunctionExpression;
import org.springframework.integration.expression.ValueExpression;
import org.springframework.integration.transformer.ContentEnricher;
import org.springframework.integration.transformer.support.ExpressionEvaluatingHeaderValueMessageProcessor;
import org.springframework.integration.transformer.support.HeaderValueMessageProcessor;
import org.springframework.integration.transformer.support.StaticHeaderValueMessageProcessor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.util.Assert;
import reactor.util.function.Tuple2;

public class EnricherSpec
extends ConsumerEndpointSpec<EnricherSpec, ContentEnricher> {
    protected final Map<String, Expression> propertyExpressions = new HashMap<String, Expression>();
    protected final Map<String, HeaderValueMessageProcessor<?>> headerExpressions = new HashMap();

    protected EnricherSpec() {
        super(new ContentEnricher());
    }

    public EnricherSpec requestChannel(MessageChannel requestChannel) {
        ((ContentEnricher)this.handler).setRequestChannel(requestChannel);
        return (EnricherSpec)this._this();
    }

    public EnricherSpec requestChannel(String requestChannel) {
        ((ContentEnricher)this.handler).setRequestChannelName(requestChannel);
        return (EnricherSpec)this._this();
    }

    public EnricherSpec replyChannel(MessageChannel replyChannel) {
        ((ContentEnricher)this.handler).setReplyChannel(replyChannel);
        return (EnricherSpec)this._this();
    }

    public EnricherSpec replyChannel(String replyChannel) {
        ((ContentEnricher)this.handler).setReplyChannelName(replyChannel);
        return (EnricherSpec)this._this();
    }

    public EnricherSpec errorChannel(MessageChannel errorChannel) {
        ((ContentEnricher)this.handler).setErrorChannel(errorChannel);
        return (EnricherSpec)this._this();
    }

    public EnricherSpec errorChannel(String errorChannel) {
        ((ContentEnricher)this.handler).setErrorChannelName(errorChannel);
        return (EnricherSpec)this._this();
    }

    public EnricherSpec requestTimeout(Long requestTimeout) {
        ((ContentEnricher)this.handler).setRequestTimeout(requestTimeout);
        return (EnricherSpec)this._this();
    }

    public EnricherSpec replyTimeout(Long replyTimeout) {
        ((ContentEnricher)this.handler).setReplyTimeout(replyTimeout);
        return (EnricherSpec)this._this();
    }

    public EnricherSpec requestPayloadExpression(String requestPayloadExpression) {
        ((ContentEnricher)this.handler).setRequestPayloadExpression(PARSER.parseExpression(requestPayloadExpression));
        return (EnricherSpec)this._this();
    }

    public <P> EnricherSpec requestPayload(Function<Message<P>, ?> requestPayloadFunction) {
        ((ContentEnricher)this.handler).setRequestPayloadExpression(new FunctionExpression<Message<P>>(requestPayloadFunction));
        return (EnricherSpec)this._this();
    }

    public EnricherSpec requestSubFlow(IntegrationFlow subFlow) {
        return this.requestChannel(this.obtainInputChannelFromFlow(subFlow));
    }

    public EnricherSpec shouldClonePayload(boolean shouldClonePayload) {
        ((ContentEnricher)this.handler).setShouldClonePayload(shouldClonePayload);
        return (EnricherSpec)this._this();
    }

    public <V> EnricherSpec property(String key, V value) {
        this.propertyExpressions.put(key, new ValueExpression<V>(value));
        return (EnricherSpec)this._this();
    }

    public EnricherSpec propertyExpression(String key, String expression) {
        Assert.notNull((Object)key, (String)"'key' must not be null");
        this.propertyExpressions.put(key, PARSER.parseExpression(expression));
        return (EnricherSpec)this._this();
    }

    public <P> EnricherSpec propertyFunction(String key, Function<Message<P>, Object> function) {
        this.propertyExpressions.put(key, new FunctionExpression<Message<P>>(function));
        return (EnricherSpec)this._this();
    }

    public <V> EnricherSpec header(String name, V value) {
        return this.header(name, value, null);
    }

    public <V> EnricherSpec header(String name, V value, Boolean overwrite) {
        StaticHeaderValueMessageProcessor<V> headerValueMessageProcessor = new StaticHeaderValueMessageProcessor<V>(value);
        headerValueMessageProcessor.setOverwrite(overwrite);
        return this.header(name, (HeaderValueMessageProcessor<V>)headerValueMessageProcessor);
    }

    public EnricherSpec headerExpression(String name, String expression) {
        return this.headerExpression(name, expression, null);
    }

    public EnricherSpec headerExpression(String name, String expression, Boolean overwrite) {
        Assert.hasText((String)expression, (String)"'expression' must not be empty");
        return this.headerExpression(name, PARSER.parseExpression(expression), overwrite);
    }

    public <P> EnricherSpec headerFunction(String name, Function<Message<P>, Object> function) {
        return this.headerFunction(name, function, null);
    }

    public <P> EnricherSpec headerFunction(String name, Function<Message<P>, Object> function, Boolean overwrite) {
        return this.headerExpression(name, new FunctionExpression<Message<P>>(function), overwrite);
    }

    private EnricherSpec headerExpression(String name, Expression expression, Boolean overwrite) {
        ExpressionEvaluatingHeaderValueMessageProcessor headerValueMessageProcessor = new ExpressionEvaluatingHeaderValueMessageProcessor(expression, null);
        headerValueMessageProcessor.setOverwrite(overwrite);
        return this.header(name, headerValueMessageProcessor);
    }

    public <V> EnricherSpec header(String headerName, HeaderValueMessageProcessor<V> headerValueMessageProcessor) {
        Assert.hasText((String)headerName, (String)"'headerName' must not be empty");
        this.headerExpressions.put(headerName, headerValueMessageProcessor);
        return (EnricherSpec)this._this();
    }

    @Override
    protected Tuple2<ConsumerEndpointFactoryBean, ContentEnricher> doGet() {
        if (!this.propertyExpressions.isEmpty()) {
            ((ContentEnricher)this.handler).setPropertyExpressions(this.propertyExpressions);
        }
        if (!this.headerExpressions.isEmpty()) {
            ((ContentEnricher)this.handler).setHeaderExpressions(this.headerExpressions);
        }
        return super.doGet();
    }
}

