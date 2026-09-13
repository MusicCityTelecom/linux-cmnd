/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 *  org.springframework.util.Assert
 */
package org.springframework.integration.transformer;

import java.util.UUID;
import org.springframework.integration.IntegrationPattern;
import org.springframework.integration.IntegrationPatternType;
import org.springframework.integration.store.MessageStore;
import org.springframework.integration.transformer.AbstractTransformer;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;

public class ClaimCheckInTransformer
extends AbstractTransformer
implements IntegrationPattern {
    private final MessageStore messageStore;

    public ClaimCheckInTransformer(MessageStore messageStore) {
        Assert.notNull((Object)messageStore, (String)"MessageStore must not be null");
        this.messageStore = messageStore;
    }

    @Override
    public String getComponentType() {
        return "claim-check-in";
    }

    @Override
    public IntegrationPatternType getIntegrationPatternType() {
        return IntegrationPatternType.claim_check_in;
    }

    @Override
    protected Object doTransform(Message<?> message) {
        Assert.notNull(message, (String)"message must not be null");
        UUID id = message.getHeaders().getId();
        Assert.notNull((Object)id, (String)"ID header must not be null");
        this.messageStore.addMessage(message);
        return id;
    }
}

