/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.MessageChannel
 */
package org.springframework.integration.dsl;

import java.util.Map;
import org.springframework.integration.dsl.IntegrationFlowDefinition;
import org.springframework.messaging.MessageChannel;

@FunctionalInterface
public interface IntegrationFlow {
    public void configure(IntegrationFlowDefinition<?> var1);

    default public MessageChannel getInputChannel() {
        return null;
    }

    default public Map<Object, String> getIntegrationComponents() {
        return null;
    }
}

