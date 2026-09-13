/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.jmx.export.annotation.ManagedAttribute
 *  org.springframework.jmx.export.annotation.ManagedOperation
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.core.DestinationResolver
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.router;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.integration.IntegrationPatternType;
import org.springframework.integration.core.MessageSelector;
import org.springframework.integration.filter.ExpressionEvaluatingSelector;
import org.springframework.integration.router.AbstractMessageRouter;
import org.springframework.integration.router.RecipientListRouterManagement;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.core.DestinationResolver;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class RecipientListRouter
extends AbstractMessageRouter
implements RecipientListRouterManagement {
    private volatile Queue<Recipient> recipients = new ConcurrentLinkedQueue<Recipient>();

    public void setChannels(List<MessageChannel> channels) {
        Assert.notEmpty(channels, (String)"'channels' must not be empty");
        this.setRecipients(channels.stream().map(Recipient::new).collect(Collectors.toList()));
    }

    public void setRecipients(List<Recipient> recipients) {
        Assert.notEmpty(recipients, (String)"'recipients' must not be empty");
        ConcurrentLinkedQueue<Recipient> newRecipients = new ConcurrentLinkedQueue<Recipient>(recipients);
        newRecipients.forEach(this::setupRecipient);
        this.logger.debug(() -> "Channel Recipients: " + this.recipients + " replaced with: " + newRecipients);
        this.recipients = newRecipients;
    }

    @Override
    @ManagedAttribute
    public void setRecipientMappings(Map<String, String> recipientMappings) {
        Assert.notEmpty(recipientMappings, (String)"'recipientMappings' must not be empty");
        Assert.noNullElements((Object[])recipientMappings.keySet().toArray(), (String)"'recipientMappings' cannot have null keys.");
        ConcurrentLinkedQueue<Recipient> newRecipients = new ConcurrentLinkedQueue<Recipient>();
        for (Map.Entry<String, String> next : recipientMappings.entrySet()) {
            if (StringUtils.hasText((String)next.getValue())) {
                this.addRecipient(next.getKey(), next.getValue(), newRecipients);
                continue;
            }
            this.addRecipient(next.getKey(), (MessageSelector)null, newRecipients);
        }
        this.logger.debug(() -> "Channel Recipients: " + this.recipients + " replaced with: " + newRecipients);
        this.recipients = newRecipients;
    }

    @Override
    @ManagedOperation
    public void addRecipient(String channelName, String selectorExpression) {
        this.addRecipient(channelName, selectorExpression, this.recipients);
    }

    private void addRecipient(String channelName, String selectorExpression, Queue<Recipient> recipientsToAdd) {
        Assert.hasText((String)channelName, (String)"'channelName' must not be empty.");
        Assert.hasText((String)selectorExpression, (String)"'selectorExpression' must not be empty.");
        ExpressionEvaluatingSelector expressionEvaluatingSelector = new ExpressionEvaluatingSelector(selectorExpression);
        expressionEvaluatingSelector.setBeanFactory(this.getBeanFactory());
        Recipient recipient = new Recipient(channelName, (MessageSelector)expressionEvaluatingSelector);
        this.setupRecipient(recipient);
        recipientsToAdd.add(recipient);
    }

    @Override
    @ManagedOperation
    public void addRecipient(String channelName) {
        this.addRecipient(channelName, (MessageSelector)null);
    }

    public void addRecipient(String channelName, MessageSelector selector) {
        this.addRecipient(channelName, selector, this.recipients);
    }

    private void addRecipient(String channelName, MessageSelector selector, Queue<Recipient> recipientsToAdd) {
        Assert.hasText((String)channelName, (String)"'channelName' must not be empty.");
        Recipient recipient = new Recipient(channelName, selector);
        this.setupRecipient(recipient);
        recipientsToAdd.add(recipient);
    }

    public void addRecipient(MessageChannel channel) {
        this.addRecipient(channel, null);
    }

    public void addRecipient(MessageChannel channel, MessageSelector selector) {
        Recipient recipient = new Recipient(channel, selector);
        this.setupRecipient(recipient);
        this.recipients.add(recipient);
    }

    private void setupRecipient(Recipient recipient) {
        BeanFactory beanFactory = this.getBeanFactory();
        if (beanFactory != null) {
            recipient.setChannelResolver(this.getChannelResolver());
            if (recipient.selector instanceof BeanFactoryAware) {
                ((BeanFactoryAware)recipient.selector).setBeanFactory(beanFactory);
            }
        }
    }

    @Override
    @ManagedOperation
    public int removeRecipient(String channelName) {
        int counter = 0;
        MessageChannel channel = (MessageChannel)this.getChannelResolver().resolveDestination(channelName);
        Iterator it = this.recipients.iterator();
        while (it.hasNext()) {
            if (!channel.equals(((Recipient)it.next()).getChannel())) continue;
            it.remove();
            ++counter;
        }
        return counter;
    }

    @Override
    @ManagedOperation
    public int removeRecipient(String channelName, String selectorExpression) {
        int counter = 0;
        MessageChannel targetChannel = (MessageChannel)this.getChannelResolver().resolveDestination(channelName);
        Iterator it = this.recipients.iterator();
        while (it.hasNext()) {
            Recipient next = (Recipient)it.next();
            MessageSelector selector = next.getSelector();
            MessageChannel channel = next.getChannel();
            if (!(selector instanceof ExpressionEvaluatingSelector) || !targetChannel.equals(channel) || !((ExpressionEvaluatingSelector)selector).getExpressionString().equals(selectorExpression)) continue;
            it.remove();
            ++counter;
        }
        return counter;
    }

    @Override
    @ManagedOperation
    public void replaceRecipients(Properties recipientMappings) {
        Assert.notEmpty((Map)recipientMappings, (String)"'recipientMappings' must not be empty");
        Set<String> keys = recipientMappings.stringPropertyNames();
        Queue<Recipient> originalRecipients = this.recipients;
        this.recipients.clear();
        for (String key : keys) {
            Assert.notNull((Object)key, (String)"channelName can't be null.");
            if (StringUtils.hasText((String)recipientMappings.getProperty(key))) {
                this.addRecipient(key, recipientMappings.getProperty(key));
                continue;
            }
            this.addRecipient(key);
        }
        this.logger.debug(() -> "Channel Recipients: " + originalRecipients + " replaced with: " + this.recipients);
    }

    @ManagedAttribute
    public Collection<Recipient> getRecipients() {
        return Collections.unmodifiableCollection(this.recipients);
    }

    @Override
    public String getComponentType() {
        return "recipient-list-router";
    }

    @Override
    public IntegrationPatternType getIntegrationPatternType() {
        return IntegrationPatternType.recipient_list_router;
    }

    @Override
    protected Collection<MessageChannel> determineTargetChannels(Message<?> message) {
        ArrayList<MessageChannel> result = new ArrayList<MessageChannel>();
        for (Recipient recipient : this.recipients) {
            if (!recipient.accept(message)) continue;
            result.add(recipient.getChannel());
        }
        return result;
    }

    @Override
    protected void onInit() {
        super.onInit();
        this.recipients.forEach(this::setupRecipient);
    }

    public static class Recipient {
        private final MessageSelector selector;
        private MessageChannel channel;
        private String channelName;
        private DestinationResolver<MessageChannel> channelResolver;

        public Recipient(MessageChannel channel) {
            this(channel, null);
        }

        public Recipient(MessageChannel channel, MessageSelector selector) {
            this.channel = channel;
            this.selector = selector;
        }

        public Recipient(String channelName) {
            this(channelName, null);
        }

        public Recipient(String channelName, MessageSelector selector) {
            this.channelName = channelName;
            this.selector = selector;
        }

        public void setChannelResolver(DestinationResolver<MessageChannel> channelResolver) {
            this.channelResolver = channelResolver;
        }

        private MessageSelector getSelector() {
            return this.selector;
        }

        @Nullable
        public MessageChannel getChannel() {
            String channelNameForInitialization;
            if (this.channel == null && (channelNameForInitialization = this.channelName) != null && this.channelResolver != null) {
                this.channel = (MessageChannel)this.channelResolver.resolveDestination(channelNameForInitialization);
                this.channelName = null;
            }
            return this.channel;
        }

        public boolean accept(Message<?> message) {
            return this.selector == null || this.selector.accept(message);
        }
    }
}

