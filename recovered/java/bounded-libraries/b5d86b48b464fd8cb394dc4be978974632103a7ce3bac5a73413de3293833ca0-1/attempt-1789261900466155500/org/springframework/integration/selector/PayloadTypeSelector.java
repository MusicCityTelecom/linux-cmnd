/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 *  org.springframework.util.Assert
 */
package org.springframework.integration.selector;

import java.util.ArrayList;
import java.util.List;
import org.springframework.integration.core.MessageSelector;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;

public class PayloadTypeSelector
implements MessageSelector {
    private final List<Class<?>> acceptedTypes = new ArrayList();

    public PayloadTypeSelector(Class<?> ... types) {
        Assert.notEmpty((Object[])types, (String)"at least one type is required");
        for (Class<?> type : types) {
            this.acceptedTypes.add(type);
        }
    }

    @Override
    public boolean accept(Message<?> message) {
        Assert.notNull(message, (String)"'message' must not be null");
        Object payload = message.getPayload();
        Assert.notNull((Object)payload, (String)"'payload' must not be null");
        for (Class<?> type : this.acceptedTypes) {
            if (!type.isAssignableFrom(payload.getClass())) continue;
            return true;
        }
        return false;
    }
}

