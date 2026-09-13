/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.aggregator;

import java.util.function.BiFunction;
import org.springframework.messaging.Message;

@FunctionalInterface
public interface GroupConditionProvider {
    public BiFunction<Message<?>, String, String> getGroupConditionSupplier();
}

