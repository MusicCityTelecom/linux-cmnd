/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.Message
 *  javax.jms.MessageListener
 *  javax.resource.ResourceException
 *  javax.resource.spi.UnavailableException
 *  org.springframework.jca.endpoint.AbstractMessageEndpointFactory
 *  org.springframework.jca.endpoint.AbstractMessageEndpointFactory$AbstractMessageEndpoint
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 */
package org.springframework.jms.listener.endpoint;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.resource.ResourceException;
import javax.resource.spi.UnavailableException;
import org.springframework.jca.endpoint.AbstractMessageEndpointFactory;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

public class JmsMessageEndpointFactory
extends AbstractMessageEndpointFactory {
    @Nullable
    private MessageListener messageListener;

    public void setMessageListener(MessageListener messageListener) {
        this.messageListener = messageListener;
    }

    protected MessageListener getMessageListener() {
        Assert.state((this.messageListener != null ? 1 : 0) != 0, (String)"No MessageListener set");
        return this.messageListener;
    }

    protected AbstractMessageEndpointFactory.AbstractMessageEndpoint createEndpointInternal() throws UnavailableException {
        return new JmsMessageEndpoint();
    }

    public static class JmsResourceException
    extends RuntimeException {
        public JmsResourceException(ResourceException cause) {
            super((Throwable)cause);
        }
    }

    private class JmsMessageEndpoint
    extends AbstractMessageEndpointFactory.AbstractMessageEndpoint
    implements MessageListener {
        private JmsMessageEndpoint() {
            super((AbstractMessageEndpointFactory)JmsMessageEndpointFactory.this);
        }

        public void onMessage(Message message) {
            boolean applyDeliveryCalls;
            Throwable endpointEx = null;
            boolean bl = applyDeliveryCalls = !this.hasBeforeDeliveryBeenCalled();
            if (applyDeliveryCalls) {
                try {
                    this.beforeDelivery(null);
                }
                catch (ResourceException ex) {
                    throw new JmsResourceException(ex);
                }
            }
            try {
                JmsMessageEndpointFactory.this.getMessageListener().onMessage(message);
            }
            catch (Error | RuntimeException ex) {
                endpointEx = ex;
                this.onEndpointException(ex);
                throw ex;
            }
            finally {
                block14: {
                    if (applyDeliveryCalls) {
                        try {
                            this.afterDelivery();
                        }
                        catch (ResourceException ex) {
                            if (endpointEx != null) break block14;
                            throw new JmsResourceException(ex);
                        }
                    }
                }
            }
        }

        protected ClassLoader getEndpointClassLoader() {
            return JmsMessageEndpointFactory.this.getMessageListener().getClass().getClassLoader();
        }
    }
}

