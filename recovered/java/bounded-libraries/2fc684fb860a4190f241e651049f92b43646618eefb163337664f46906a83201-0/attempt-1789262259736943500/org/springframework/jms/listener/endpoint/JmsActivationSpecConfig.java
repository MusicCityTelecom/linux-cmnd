/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.Session
 *  org.springframework.core.Constants
 *  org.springframework.lang.Nullable
 */
package org.springframework.jms.listener.endpoint;

import javax.jms.Session;
import org.springframework.core.Constants;
import org.springframework.jms.support.QosSettings;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.lang.Nullable;

public class JmsActivationSpecConfig {
    private static final Constants sessionConstants = new Constants(Session.class);
    @Nullable
    private String destinationName;
    private boolean pubSubDomain = false;
    @Nullable
    private Boolean replyPubSubDomain;
    @Nullable
    private QosSettings replyQosSettings;
    private boolean subscriptionDurable = false;
    private boolean subscriptionShared = false;
    @Nullable
    private String subscriptionName;
    @Nullable
    private String clientId;
    @Nullable
    private String messageSelector;
    private int acknowledgeMode = 1;
    private int maxConcurrency = -1;
    private int prefetchSize = -1;
    @Nullable
    private MessageConverter messageConverter;

    public void setDestinationName(@Nullable String destinationName) {
        this.destinationName = destinationName;
    }

    @Nullable
    public String getDestinationName() {
        return this.destinationName;
    }

    public void setPubSubDomain(boolean pubSubDomain) {
        this.pubSubDomain = pubSubDomain;
    }

    public boolean isPubSubDomain() {
        return this.pubSubDomain;
    }

    public void setReplyPubSubDomain(boolean replyPubSubDomain) {
        this.replyPubSubDomain = replyPubSubDomain;
    }

    public boolean isReplyPubSubDomain() {
        if (this.replyPubSubDomain != null) {
            return this.replyPubSubDomain;
        }
        return this.isPubSubDomain();
    }

    public void setReplyQosSettings(@Nullable QosSettings replyQosSettings) {
        this.replyQosSettings = replyQosSettings;
    }

    @Nullable
    public QosSettings getReplyQosSettings() {
        return this.replyQosSettings;
    }

    public void setSubscriptionDurable(boolean subscriptionDurable) {
        this.subscriptionDurable = subscriptionDurable;
        if (subscriptionDurable) {
            this.pubSubDomain = true;
        }
    }

    public boolean isSubscriptionDurable() {
        return this.subscriptionDurable;
    }

    public void setSubscriptionShared(boolean subscriptionShared) {
        this.subscriptionShared = subscriptionShared;
        if (subscriptionShared) {
            this.pubSubDomain = true;
        }
    }

    public boolean isSubscriptionShared() {
        return this.subscriptionShared;
    }

    public void setSubscriptionName(@Nullable String subscriptionName) {
        this.subscriptionName = subscriptionName;
    }

    @Nullable
    public String getSubscriptionName() {
        return this.subscriptionName;
    }

    public void setDurableSubscriptionName(@Nullable String durableSubscriptionName) {
        this.subscriptionName = durableSubscriptionName;
        this.subscriptionDurable = durableSubscriptionName != null;
    }

    @Nullable
    public String getDurableSubscriptionName() {
        return this.subscriptionDurable ? this.subscriptionName : null;
    }

    public void setClientId(@Nullable String clientId) {
        this.clientId = clientId;
    }

    @Nullable
    public String getClientId() {
        return this.clientId;
    }

    public void setMessageSelector(@Nullable String messageSelector) {
        this.messageSelector = messageSelector;
    }

    @Nullable
    public String getMessageSelector() {
        return this.messageSelector;
    }

    public void setAcknowledgeModeName(String constantName) {
        this.setAcknowledgeMode(sessionConstants.asNumber(constantName).intValue());
    }

    public void setAcknowledgeMode(int acknowledgeMode) {
        this.acknowledgeMode = acknowledgeMode;
    }

    public int getAcknowledgeMode() {
        return this.acknowledgeMode;
    }

    public void setConcurrency(String concurrency) {
        try {
            int separatorIndex = concurrency.indexOf(45);
            if (separatorIndex != -1) {
                this.setMaxConcurrency(Integer.parseInt(concurrency.substring(separatorIndex + 1)));
            } else {
                this.setMaxConcurrency(Integer.parseInt(concurrency));
            }
        }
        catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Invalid concurrency value [" + concurrency + "]: only single maximum integer (e.g. \"5\") and minimum-maximum combo (e.g. \"3-5\") supported. Note that JmsActivationSpecConfig will effectively ignore the minimum value and scale from zero up to the number of consumers according to the maximum value.");
        }
    }

    public void setMaxConcurrency(int maxConcurrency) {
        this.maxConcurrency = maxConcurrency;
    }

    public int getMaxConcurrency() {
        return this.maxConcurrency;
    }

    public void setPrefetchSize(int prefetchSize) {
        this.prefetchSize = prefetchSize;
    }

    public int getPrefetchSize() {
        return this.prefetchSize;
    }

    public void setMessageConverter(@Nullable MessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }

    @Nullable
    public MessageConverter getMessageConverter() {
        return this.messageConverter;
    }
}

