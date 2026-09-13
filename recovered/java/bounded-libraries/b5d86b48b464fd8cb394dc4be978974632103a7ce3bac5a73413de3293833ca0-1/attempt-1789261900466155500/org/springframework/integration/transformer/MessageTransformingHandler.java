/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.context.Lifecycle
 *  org.springframework.messaging.Message
 *  org.springframework.util.Assert
 */
package org.springframework.integration.transformer;

import java.util.Collection;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.Lifecycle;
import org.springframework.integration.IntegrationPattern;
import org.springframework.integration.IntegrationPatternType;
import org.springframework.integration.handler.AbstractReplyProducingMessageHandler;
import org.springframework.integration.support.context.NamedComponent;
import org.springframework.integration.support.management.ManageableLifecycle;
import org.springframework.integration.transformer.AbstractMessageProcessingTransformer;
import org.springframework.integration.transformer.MessageTransformationException;
import org.springframework.integration.transformer.Transformer;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;

public class MessageTransformingHandler
extends AbstractReplyProducingMessageHandler
implements ManageableLifecycle {
    private final Transformer transformer;

    public MessageTransformingHandler(Transformer transformer) {
        Assert.notNull((Object)transformer, (String)"transformer must not be null");
        this.transformer = transformer;
        this.setRequiresReply(true);
    }

    @Override
    public String getComponentType() {
        return this.transformer instanceof NamedComponent ? ((NamedComponent)((Object)this.transformer)).getComponentType() : "transformer";
    }

    @Override
    public IntegrationPatternType getIntegrationPatternType() {
        return this.transformer instanceof IntegrationPattern ? ((IntegrationPattern)((Object)this.transformer)).getIntegrationPatternType() : IntegrationPatternType.transformer;
    }

    @Override
    public void addNotPropagatedHeaders(String ... headers) {
        super.addNotPropagatedHeaders(headers);
        this.populateNotPropagatedHeadersIfAny();
    }

    @Override
    protected void doInit() {
        BeanFactory beanFactory = this.getBeanFactory();
        if (beanFactory != null && this.transformer instanceof BeanFactoryAware) {
            ((BeanFactoryAware)this.transformer).setBeanFactory(beanFactory);
        }
        this.populateNotPropagatedHeadersIfAny();
    }

    private void populateNotPropagatedHeadersIfAny() {
        Collection<String> notPropagatedHeaders = this.getNotPropagatedHeaders();
        if (this.transformer instanceof AbstractMessageProcessingTransformer && !notPropagatedHeaders.isEmpty()) {
            ((AbstractMessageProcessingTransformer)this.transformer).setNotPropagatedHeaders(notPropagatedHeaders.toArray(new String[0]));
        }
    }

    @Override
    public void start() {
        if (this.transformer instanceof Lifecycle) {
            ((Lifecycle)this.transformer).start();
        }
    }

    @Override
    public void stop() {
        if (this.transformer instanceof Lifecycle) {
            ((Lifecycle)this.transformer).stop();
        }
    }

    @Override
    public boolean isRunning() {
        return !(this.transformer instanceof Lifecycle) || ((Lifecycle)this.transformer).isRunning();
    }

    @Override
    protected Object handleRequestMessage(Message<?> message) {
        try {
            return this.transformer.transform(message);
        }
        catch (Exception e) {
            if (e instanceof MessageTransformationException) {
                throw (MessageTransformationException)((Object)e);
            }
            throw new MessageTransformationException(message, "Failed to transform Message in " + this, e);
        }
    }

    @Override
    protected boolean shouldCopyRequestHeaders() {
        return false;
    }
}

