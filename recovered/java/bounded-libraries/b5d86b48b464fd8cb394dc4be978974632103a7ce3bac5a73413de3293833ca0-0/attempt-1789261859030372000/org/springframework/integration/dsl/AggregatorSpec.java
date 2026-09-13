/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.dsl;

import java.util.Map;
import java.util.function.Function;
import org.springframework.integration.aggregator.AbstractAggregatingMessageGroupProcessor;
import org.springframework.integration.aggregator.AggregatingMessageHandler;
import org.springframework.integration.aggregator.DefaultAggregatingMessageGroupProcessor;
import org.springframework.integration.aggregator.DelegatingMessageGroupProcessor;
import org.springframework.integration.aggregator.ExpressionEvaluatingMessageGroupProcessor;
import org.springframework.integration.aggregator.MessageGroupProcessor;
import org.springframework.integration.aggregator.MethodInvokingMessageGroupProcessor;
import org.springframework.integration.dsl.CorrelationHandlerSpec;
import org.springframework.integration.store.MessageGroup;

public class AggregatorSpec
extends CorrelationHandlerSpec<AggregatorSpec, AggregatingMessageHandler> {
    private Function<MessageGroup, Map<String, Object>> headersFunction;

    protected AggregatorSpec() {
        super(new AggregatingMessageHandler(new DefaultAggregatingMessageGroupProcessor()));
    }

    @Override
    public AggregatorSpec processor(Object target) {
        return this.processor(target, null);
    }

    public AggregatorSpec processor(Object target, String methodName) {
        return ((AggregatorSpec)super.processor(target)).outputProcessor(methodName != null ? new MethodInvokingMessageGroupProcessor(target, methodName) : (target instanceof MessageGroupProcessor ? (MessageGroupProcessor)target : new MethodInvokingMessageGroupProcessor(target)));
    }

    public AggregatorSpec outputExpression(String expression) {
        return this.outputProcessor(new ExpressionEvaluatingMessageGroupProcessor(expression));
    }

    public AggregatorSpec outputProcessor(MessageGroupProcessor outputProcessor) {
        ((AggregatingMessageHandler)this.handler).setOutputProcessor(outputProcessor);
        return (AggregatorSpec)this._this();
    }

    public AggregatorSpec expireGroupsUponCompletion(boolean expireGroupsUponCompletion) {
        ((AggregatingMessageHandler)this.handler).setExpireGroupsUponCompletion(expireGroupsUponCompletion);
        return (AggregatorSpec)this._this();
    }

    public AggregatorSpec headersFunction(Function<MessageGroup, Map<String, Object>> headersFunction) {
        this.headersFunction = headersFunction;
        return (AggregatorSpec)this._this();
    }

    @Override
    public Map<Object, String> getComponentsToRegister() {
        if (this.headersFunction != null) {
            MessageGroupProcessor outputProcessor = ((AggregatingMessageHandler)this.handler).getOutputProcessor();
            if (outputProcessor instanceof AbstractAggregatingMessageGroupProcessor) {
                ((AbstractAggregatingMessageGroupProcessor)outputProcessor).setHeadersFunction(this.headersFunction);
            } else {
                ((AggregatingMessageHandler)this.handler).setOutputProcessor(new DelegatingMessageGroupProcessor(outputProcessor, this.headersFunction));
            }
        }
        return super.getComponentsToRegister();
    }
}

