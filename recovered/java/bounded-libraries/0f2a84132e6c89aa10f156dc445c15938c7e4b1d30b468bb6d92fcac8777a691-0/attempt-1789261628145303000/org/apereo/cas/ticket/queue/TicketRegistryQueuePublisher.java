/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.ticket.queue;

import org.apereo.cas.ticket.queue.BaseMessageQueueCommand;

@FunctionalInterface
public interface TicketRegistryQueuePublisher {
    public static TicketRegistryQueuePublisher noOp() {
        return cmd -> {};
    }

    public void publishMessageToQueue(BaseMessageQueueCommand var1);
}

