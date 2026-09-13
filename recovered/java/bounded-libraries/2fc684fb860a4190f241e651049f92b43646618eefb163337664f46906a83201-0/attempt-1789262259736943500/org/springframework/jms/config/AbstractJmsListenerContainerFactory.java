/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.ConnectionFactory
 *  javax.jms.ExceptionListener
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.lang.Nullable
 *  org.springframework.util.ErrorHandler
 */
package org.springframework.jms.config;

import javax.jms.ConnectionFactory;
import javax.jms.ExceptionListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerEndpoint;
import org.springframework.jms.listener.AbstractJmsListeningContainer;
import org.springframework.jms.listener.AbstractMessageListenerContainer;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.jms.support.JmsAccessor;
import org.springframework.jms.support.QosSettings;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.destination.DestinationResolver;
import org.springframework.jms.support.destination.JmsDestinationAccessor;
import org.springframework.lang.Nullable;
import org.springframework.util.ErrorHandler;

public abstract class AbstractJmsListenerContainerFactory<C extends AbstractMessageListenerContainer>
implements JmsListenerContainerFactory<C> {
    protected final Log logger = LogFactory.getLog(this.getClass());
    @Nullable
    private ConnectionFactory connectionFactory;
    @Nullable
    private DestinationResolver destinationResolver;
    @Nullable
    private MessageConverter messageConverter;
    @Nullable
    private ExceptionListener exceptionListener;
    @Nullable
    private ErrorHandler errorHandler;
    @Nullable
    private Boolean sessionTransacted;
    @Nullable
    private Integer sessionAcknowledgeMode;
    @Nullable
    private Boolean pubSubDomain;
    @Nullable
    private Boolean replyPubSubDomain;
    @Nullable
    private QosSettings replyQosSettings;
    @Nullable
    private Boolean subscriptionDurable;
    @Nullable
    private Boolean subscriptionShared;
    @Nullable
    private String clientId;
    @Nullable
    private Integer phase;
    @Nullable
    private Boolean autoStartup;

    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void setDestinationResolver(DestinationResolver destinationResolver) {
        this.destinationResolver = destinationResolver;
    }

    public void setMessageConverter(MessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }

    public void setExceptionListener(ExceptionListener exceptionListener) {
        this.exceptionListener = exceptionListener;
    }

    public void setErrorHandler(ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    public void setSessionTransacted(Boolean sessionTransacted) {
        this.sessionTransacted = sessionTransacted;
    }

    public void setSessionAcknowledgeMode(Integer sessionAcknowledgeMode) {
        this.sessionAcknowledgeMode = sessionAcknowledgeMode;
    }

    public void setPubSubDomain(Boolean pubSubDomain) {
        this.pubSubDomain = pubSubDomain;
    }

    public void setReplyPubSubDomain(Boolean replyPubSubDomain) {
        this.replyPubSubDomain = replyPubSubDomain;
    }

    public void setReplyQosSettings(QosSettings replyQosSettings) {
        this.replyQosSettings = replyQosSettings;
    }

    public void setSubscriptionDurable(Boolean subscriptionDurable) {
        this.subscriptionDurable = subscriptionDurable;
    }

    public void setSubscriptionShared(Boolean subscriptionShared) {
        this.subscriptionShared = subscriptionShared;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    public void setAutoStartup(boolean autoStartup) {
        this.autoStartup = autoStartup;
    }

    @Override
    public C createListenerContainer(JmsListenerEndpoint endpoint) {
        C instance = this.createContainerInstance();
        if (this.connectionFactory != null) {
            ((JmsAccessor)instance).setConnectionFactory(this.connectionFactory);
        }
        if (this.destinationResolver != null) {
            ((JmsDestinationAccessor)instance).setDestinationResolver(this.destinationResolver);
        }
        if (this.messageConverter != null) {
            ((AbstractMessageListenerContainer)instance).setMessageConverter(this.messageConverter);
        }
        if (this.exceptionListener != null) {
            ((AbstractMessageListenerContainer)instance).setExceptionListener(this.exceptionListener);
        }
        if (this.errorHandler != null) {
            ((AbstractMessageListenerContainer)instance).setErrorHandler(this.errorHandler);
        }
        if (this.sessionTransacted != null) {
            ((JmsAccessor)instance).setSessionTransacted(this.sessionTransacted);
        }
        if (this.sessionAcknowledgeMode != null) {
            ((JmsAccessor)instance).setSessionAcknowledgeMode(this.sessionAcknowledgeMode);
        }
        if (this.pubSubDomain != null) {
            ((JmsDestinationAccessor)instance).setPubSubDomain(this.pubSubDomain);
        }
        if (this.replyPubSubDomain != null) {
            ((AbstractMessageListenerContainer)instance).setReplyPubSubDomain(this.replyPubSubDomain);
        }
        if (this.replyQosSettings != null) {
            ((AbstractMessageListenerContainer)instance).setReplyQosSettings(this.replyQosSettings);
        }
        if (this.subscriptionDurable != null) {
            ((AbstractMessageListenerContainer)instance).setSubscriptionDurable(this.subscriptionDurable);
        }
        if (this.subscriptionShared != null) {
            ((AbstractMessageListenerContainer)instance).setSubscriptionShared(this.subscriptionShared);
        }
        if (this.clientId != null) {
            ((AbstractJmsListeningContainer)instance).setClientId(this.clientId);
        }
        if (this.phase != null) {
            ((AbstractJmsListeningContainer)instance).setPhase(this.phase);
        }
        if (this.autoStartup != null) {
            ((AbstractJmsListeningContainer)instance).setAutoStartup(this.autoStartup);
        }
        this.initializeContainer(instance);
        endpoint.setupListenerContainer((MessageListenerContainer)instance);
        return instance;
    }

    protected abstract C createContainerInstance();

    protected void initializeContainer(C instance) {
    }
}

