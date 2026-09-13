/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.leader.event;

import org.springframework.integration.leader.Context;
import org.springframework.integration.leader.event.AbstractLeaderEvent;

public class OnFailedToAcquireMutexEvent
extends AbstractLeaderEvent {
    public OnFailedToAcquireMutexEvent(Object source, Context context, String role) {
        super(source, context, role);
    }
}

