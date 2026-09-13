/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.selector;

import org.springframework.integration.IntegrationMessageHeaderAccessor;
import org.springframework.integration.core.MessageSelector;
import org.springframework.messaging.Message;

public class UnexpiredMessageSelector
implements MessageSelector {
    @Override
    public boolean accept(Message<?> message) {
        Long expirationDate = new IntegrationMessageHeaderAccessor(message).getExpirationDate();
        if (expirationDate == null) {
            return true;
        }
        return expirationDate > System.currentTimeMillis();
    }
}

