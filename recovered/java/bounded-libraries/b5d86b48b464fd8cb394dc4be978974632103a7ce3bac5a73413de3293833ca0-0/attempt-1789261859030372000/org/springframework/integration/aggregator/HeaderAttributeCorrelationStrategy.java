/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 *  org.springframework.util.Assert
 */
package org.springframework.integration.aggregator;

import org.springframework.integration.aggregator.CorrelationStrategy;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;

public class HeaderAttributeCorrelationStrategy
implements CorrelationStrategy {
    private final String attributeName;

    public HeaderAttributeCorrelationStrategy(String attributeName) {
        Assert.hasText((String)attributeName, (String)"the 'attributeName' must not be empty");
        this.attributeName = attributeName;
    }

    @Override
    public Object getCorrelationKey(Message<?> message) {
        return message.getHeaders().get((Object)this.attributeName);
    }
}

