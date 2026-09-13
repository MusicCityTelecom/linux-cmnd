/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.Lifecycle
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.MessageHandler
 *  org.springframework.messaging.SubscribableChannel
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.endpoint;

import org.springframework.context.Lifecycle;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.endpoint.AbstractEndpoint;
import org.springframework.integration.endpoint.IntegrationConsumer;
import org.springframework.integration.router.MessageRouter;
import org.springframework.integration.support.context.NamedComponent;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class EventDrivenConsumer
extends AbstractEndpoint
implements IntegrationConsumer {
    private final SubscribableChannel inputChannel;
    private final MessageHandler handler;

    public EventDrivenConsumer(SubscribableChannel inputChannel, MessageHandler handler) {
        Assert.notNull((Object)inputChannel, (String)"inputChannel must not be null");
        Assert.notNull((Object)handler, (String)"handler must not be null");
        this.inputChannel = inputChannel;
        this.handler = handler;
        this.setPhase(Integer.MIN_VALUE);
    }

    @Override
    public MessageChannel getInputChannel() {
        return this.inputChannel;
    }

    @Override
    public MessageChannel getOutputChannel() {
        if (this.handler instanceof MessageProducer) {
            return ((MessageProducer)this.handler).getOutputChannel();
        }
        if (this.handler instanceof MessageRouter) {
            return ((MessageRouter)this.handler).getDefaultOutputChannel();
        }
        return null;
    }

    @Override
    public MessageHandler getHandler() {
        return this.handler;
    }

    @Override
    protected void doStart() {
        this.logComponentSubscriptionEvent(true);
        this.inputChannel.subscribe(this.handler);
        if (this.handler instanceof Lifecycle) {
            ((Lifecycle)this.handler).start();
        }
    }

    @Override
    protected void doStop() {
        this.logComponentSubscriptionEvent(false);
        this.inputChannel.unsubscribe(this.handler);
        if (this.handler instanceof Lifecycle) {
            ((Lifecycle)this.handler).stop();
        }
    }

    private void logComponentSubscriptionEvent(boolean add) {
        if (this.handler instanceof NamedComponent && this.inputChannel instanceof NamedComponent) {
            String channelName = ((NamedComponent)this.inputChannel).getComponentName();
            String componentType = ((NamedComponent)this.handler).getComponentType();
            componentType = StringUtils.hasText((String)componentType) ? componentType : "";
            String componentName = this.getComponentName();
            componentName = StringUtils.hasText((String)componentName) && componentName.contains("#") ? "" : ":" + componentName;
            StringBuffer buffer = new StringBuffer();
            buffer.append("{" + componentType + componentName + "} as a subscriber to the '" + channelName + "' channel");
            if (add) {
                buffer.insert(0, "Adding ");
            } else {
                buffer.insert(0, "Removing ");
            }
            this.logger.info((CharSequence)buffer.toString());
        }
    }
}

