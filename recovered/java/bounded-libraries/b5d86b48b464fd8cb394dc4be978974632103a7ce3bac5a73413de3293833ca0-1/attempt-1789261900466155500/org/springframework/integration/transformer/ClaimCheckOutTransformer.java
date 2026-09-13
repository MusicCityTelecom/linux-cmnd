/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.log.LogMessage
 *  org.springframework.messaging.Message
 *  org.springframework.util.Assert
 */
package org.springframework.integration.transformer;

import java.util.Map;
import java.util.UUID;
import org.springframework.core.log.LogMessage;
import org.springframework.integration.IntegrationPattern;
import org.springframework.integration.IntegrationPatternType;
import org.springframework.integration.store.MessageStore;
import org.springframework.integration.support.AbstractIntegrationMessageBuilder;
import org.springframework.integration.transformer.AbstractTransformer;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;

public class ClaimCheckOutTransformer
extends AbstractTransformer
implements IntegrationPattern {
    private final MessageStore messageStore;
    private volatile boolean removeMessage = false;

    public ClaimCheckOutTransformer(MessageStore messageStore) {
        Assert.notNull((Object)messageStore, (String)"MessageStore must not be null");
        this.messageStore = messageStore;
    }

    public void setRemoveMessage(boolean removeMessage) {
        this.removeMessage = removeMessage;
    }

    @Override
    public String getComponentType() {
        return "claim-check-out";
    }

    @Override
    public IntegrationPatternType getIntegrationPatternType() {
        return IntegrationPatternType.claim_check_out;
    }

    @Override
    protected Object doTransform(Message<?> message) {
        Message<?> retrievedMessage;
        Assert.notNull(message, (String)"message must not be null");
        Assert.isTrue((boolean)(message.getPayload() instanceof UUID), (String)"payload must be a UUID");
        UUID id = (UUID)message.getPayload();
        if (this.removeMessage) {
            retrievedMessage = this.messageStore.removeMessage(id);
            this.logger.debug((CharSequence)LogMessage.format((String)"Removed Message with claim-check '%s' from the MessageStore.", (Object)id));
        } else {
            retrievedMessage = this.messageStore.getMessage(id);
        }
        Assert.notNull(retrievedMessage, () -> "unable to locate Message for ID: " + id + " within MessageStore [" + this.messageStore + "]");
        AbstractIntegrationMessageBuilder<?> responseBuilder = this.getMessageBuilderFactory().fromMessage(retrievedMessage);
        responseBuilder.copyHeaders((Map<String, ?>)message.getHeaders());
        return responseBuilder.build();
    }
}

