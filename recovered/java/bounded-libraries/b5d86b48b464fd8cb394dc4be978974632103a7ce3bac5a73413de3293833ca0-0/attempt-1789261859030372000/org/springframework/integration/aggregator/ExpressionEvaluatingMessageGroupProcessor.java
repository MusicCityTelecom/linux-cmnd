/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.core.convert.ConversionService
 */
package org.springframework.integration.aggregator;

import java.util.Map;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.integration.aggregator.AbstractAggregatingMessageGroupProcessor;
import org.springframework.integration.aggregator.ExpressionEvaluatingMessageListProcessor;
import org.springframework.integration.store.MessageGroup;

public class ExpressionEvaluatingMessageGroupProcessor
extends AbstractAggregatingMessageGroupProcessor {
    private final ExpressionEvaluatingMessageListProcessor processor;

    public ExpressionEvaluatingMessageGroupProcessor(String expression) {
        this.processor = new ExpressionEvaluatingMessageListProcessor(expression);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        super.setBeanFactory(beanFactory);
        this.processor.setBeanFactory(beanFactory);
    }

    public void setConversionService(ConversionService conversionService) {
        this.processor.setConversionService(conversionService);
    }

    public void setExpectedType(Class<?> expectedType) {
        this.processor.setExpectedType(expectedType);
    }

    @Override
    protected Object aggregatePayloads(MessageGroup group, Map<String, Object> headers) {
        return this.processor.process(group.getMessages());
    }
}

