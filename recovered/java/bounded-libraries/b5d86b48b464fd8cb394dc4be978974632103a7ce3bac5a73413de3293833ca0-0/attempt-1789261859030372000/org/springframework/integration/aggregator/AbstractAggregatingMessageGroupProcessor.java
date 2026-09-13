/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.messaging.Message
 *  org.springframework.util.Assert
 */
package org.springframework.integration.aggregator;

import java.util.Map;
import java.util.function.Function;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.integration.aggregator.DefaultAggregateHeadersFunction;
import org.springframework.integration.aggregator.MessageGroupProcessor;
import org.springframework.integration.store.MessageGroup;
import org.springframework.integration.support.AbstractIntegrationMessageBuilder;
import org.springframework.integration.support.DefaultMessageBuilderFactory;
import org.springframework.integration.support.MessageBuilderFactory;
import org.springframework.integration.support.utils.IntegrationUtils;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;

public abstract class AbstractAggregatingMessageGroupProcessor
implements MessageGroupProcessor,
BeanFactoryAware {
    protected final Log logger = LogFactory.getLog(this.getClass());
    private Function<MessageGroup, Map<String, Object>> headersFunction = new DefaultAggregateHeadersFunction();
    private MessageBuilderFactory messageBuilderFactory = new DefaultMessageBuilderFactory();
    private boolean messageBuilderFactorySet;
    private BeanFactory beanFactory;

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public void setHeadersFunction(Function<MessageGroup, Map<String, Object>> headersFunction) {
        Assert.notNull(headersFunction, (String)"'headersFunction' must not be null");
        this.headersFunction = headersFunction;
    }

    protected Function<MessageGroup, Map<String, Object>> getHeadersFunction() {
        return this.headersFunction;
    }

    protected MessageBuilderFactory getMessageBuilderFactory() {
        if (!this.messageBuilderFactorySet) {
            if (this.beanFactory != null) {
                this.messageBuilderFactory = IntegrationUtils.getMessageBuilderFactory(this.beanFactory);
            }
            this.messageBuilderFactorySet = true;
        }
        return this.messageBuilderFactory;
    }

    @Override
    public final Object processMessageGroup(MessageGroup group) {
        Assert.notNull((Object)group, (String)"MessageGroup must not be null");
        Map<String, Object> headers = this.aggregateHeaders(group);
        Object payload = this.aggregatePayloads(group, headers);
        AbstractIntegrationMessageBuilder<Object> builder = payload instanceof Message ? this.getMessageBuilderFactory().fromMessage((Message)payload) : (payload instanceof AbstractIntegrationMessageBuilder ? (AbstractIntegrationMessageBuilder<Object>)payload : this.getMessageBuilderFactory().withPayload(payload));
        return builder.copyHeadersIfAbsent(headers);
    }

    protected Map<String, Object> aggregateHeaders(MessageGroup group) {
        return this.getHeadersFunction().apply(group);
    }

    protected abstract Object aggregatePayloads(MessageGroup var1, Map<String, Object> var2);
}

