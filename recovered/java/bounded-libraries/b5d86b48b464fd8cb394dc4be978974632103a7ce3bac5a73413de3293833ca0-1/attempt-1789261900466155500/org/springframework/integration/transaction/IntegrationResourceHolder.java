/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 *  org.springframework.transaction.support.ResourceHolder
 */
package org.springframework.integration.transaction;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.springframework.messaging.Message;
import org.springframework.transaction.support.ResourceHolder;

public class IntegrationResourceHolder
implements ResourceHolder {
    public static final String MESSAGE_SOURCE = "messageSource";
    public static final String INPUT_CHANNEL = "inputChannel";
    private volatile Message<?> message;
    private final Map<String, Object> attributes = new HashMap<String, Object>();

    public void setMessage(Message<?> message) {
        this.message = message;
    }

    public Message<?> getMessage() {
        return this.message;
    }

    public void addAttribute(String key, Object value) {
        this.attributes.put(key, value);
    }

    public Map<String, Object> getAttributes() {
        return Collections.unmodifiableMap(this.attributes);
    }

    public void reset() {
    }

    public void unbound() {
    }

    public boolean isVoid() {
        return false;
    }
}

