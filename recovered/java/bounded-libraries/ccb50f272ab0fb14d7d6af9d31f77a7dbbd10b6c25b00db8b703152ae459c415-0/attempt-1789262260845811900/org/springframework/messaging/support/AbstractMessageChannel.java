/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.beans.factory.BeanNameAware
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 *  org.springframework.util.ObjectUtils
 */
package org.springframework.messaging.support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.InterceptableChannel;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

public abstract class AbstractMessageChannel
implements MessageChannel,
InterceptableChannel,
BeanNameAware {
    protected Log logger = LogFactory.getLog(this.getClass());
    private String beanName;
    private final List<ChannelInterceptor> interceptors = new ArrayList<ChannelInterceptor>(5);

    public AbstractMessageChannel() {
        this.beanName = this.getClass().getSimpleName() + "@" + ObjectUtils.getIdentityHexString((Object)this);
    }

    public void setLogger(Log logger) {
        this.logger = logger;
    }

    public Log getLogger() {
        return this.logger;
    }

    public void setBeanName(String name) {
        this.beanName = name;
    }

    public String getBeanName() {
        return this.beanName;
    }

    @Override
    public void setInterceptors(List<ChannelInterceptor> interceptors) {
        Assert.noNullElements(interceptors, (String)"'interceptors' must not contain null elements");
        this.interceptors.clear();
        this.interceptors.addAll(interceptors);
    }

    @Override
    public void addInterceptor(ChannelInterceptor interceptor) {
        Assert.notNull((Object)interceptor, (String)"'interceptor' must not be null");
        this.interceptors.add(interceptor);
    }

    @Override
    public void addInterceptor(int index, ChannelInterceptor interceptor) {
        Assert.notNull((Object)interceptor, (String)"'interceptor' must not be null");
        this.interceptors.add(index, interceptor);
    }

    @Override
    public List<ChannelInterceptor> getInterceptors() {
        return Collections.unmodifiableList(this.interceptors);
    }

    @Override
    public boolean removeInterceptor(ChannelInterceptor interceptor) {
        return this.interceptors.remove(interceptor);
    }

    @Override
    public ChannelInterceptor removeInterceptor(int index) {
        return this.interceptors.remove(index);
    }

    @Override
    public final boolean send(Message<?> message) {
        return this.send(message, -1L);
    }

    @Override
    public final boolean send(Message<?> message, long timeout) {
        Assert.notNull(message, (String)"Message must not be null");
        Message<?> messageToUse = message;
        ChannelInterceptorChain chain = new ChannelInterceptorChain();
        boolean sent = false;
        try {
            messageToUse = chain.applyPreSend(messageToUse, this);
            if (messageToUse == null) {
                return false;
            }
            sent = this.sendInternal(messageToUse, timeout);
            chain.applyPostSend(messageToUse, this, sent);
            chain.triggerAfterSendCompletion(messageToUse, this, sent, null);
            return sent;
        }
        catch (Exception ex) {
            chain.triggerAfterSendCompletion(messageToUse, this, sent, ex);
            if (ex instanceof MessagingException) {
                throw (MessagingException)((Object)ex);
            }
            throw new MessageDeliveryException(messageToUse, "Failed to send message to " + this, ex);
        }
        catch (Throwable err) {
            MessageDeliveryException ex2 = new MessageDeliveryException(messageToUse, "Failed to send message to " + this, err);
            chain.triggerAfterSendCompletion(messageToUse, this, sent, (Exception)((Object)ex2));
            throw ex2;
        }
    }

    protected abstract boolean sendInternal(Message<?> var1, long var2);

    public String toString() {
        return this.getClass().getSimpleName() + "[" + this.beanName + "]";
    }

    protected class ChannelInterceptorChain {
        private int sendInterceptorIndex = -1;
        private int receiveInterceptorIndex = -1;

        protected ChannelInterceptorChain() {
        }

        @Nullable
        public Message<?> applyPreSend(Message<?> message, MessageChannel channel) {
            Message<?> messageToUse = message;
            for (ChannelInterceptor interceptor : AbstractMessageChannel.this.interceptors) {
                Message<?> resolvedMessage = interceptor.preSend(messageToUse, channel);
                if (resolvedMessage == null) {
                    String name = interceptor.getClass().getSimpleName();
                    if (AbstractMessageChannel.this.logger.isDebugEnabled()) {
                        AbstractMessageChannel.this.logger.debug((Object)(name + " returned null from preSend, i.e. precluding the send."));
                    }
                    this.triggerAfterSendCompletion(messageToUse, channel, false, null);
                    return null;
                }
                messageToUse = resolvedMessage;
                ++this.sendInterceptorIndex;
            }
            return messageToUse;
        }

        public void applyPostSend(Message<?> message, MessageChannel channel, boolean sent) {
            for (ChannelInterceptor interceptor : AbstractMessageChannel.this.interceptors) {
                interceptor.postSend(message, channel, sent);
            }
        }

        public void triggerAfterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, @Nullable Exception ex) {
            for (int i2 = this.sendInterceptorIndex; i2 >= 0; --i2) {
                ChannelInterceptor interceptor = (ChannelInterceptor)AbstractMessageChannel.this.interceptors.get(i2);
                try {
                    interceptor.afterSendCompletion(message, channel, sent, ex);
                    continue;
                }
                catch (Throwable ex2) {
                    AbstractMessageChannel.this.logger.error((Object)("Exception from afterSendCompletion in " + interceptor), ex2);
                }
            }
        }

        public boolean applyPreReceive(MessageChannel channel) {
            for (ChannelInterceptor interceptor : AbstractMessageChannel.this.interceptors) {
                if (!interceptor.preReceive(channel)) {
                    this.triggerAfterReceiveCompletion(null, channel, null);
                    return false;
                }
                ++this.receiveInterceptorIndex;
            }
            return true;
        }

        @Nullable
        public Message<?> applyPostReceive(Message<?> message, MessageChannel channel) {
            Message<?> messageToUse = message;
            for (ChannelInterceptor interceptor : AbstractMessageChannel.this.interceptors) {
                messageToUse = interceptor.postReceive(messageToUse, channel);
                if (messageToUse != null) continue;
                return null;
            }
            return messageToUse;
        }

        public void triggerAfterReceiveCompletion(@Nullable Message<?> message, MessageChannel channel, @Nullable Exception ex) {
            for (int i2 = this.receiveInterceptorIndex; i2 >= 0; --i2) {
                ChannelInterceptor interceptor = (ChannelInterceptor)AbstractMessageChannel.this.interceptors.get(i2);
                try {
                    interceptor.afterReceiveCompletion(message, channel, ex);
                    continue;
                }
                catch (Throwable ex2) {
                    if (!AbstractMessageChannel.this.logger.isErrorEnabled()) continue;
                    AbstractMessageChannel.this.logger.error((Object)("Exception from afterReceiveCompletion in " + interceptor), ex2);
                }
            }
        }
    }
}

