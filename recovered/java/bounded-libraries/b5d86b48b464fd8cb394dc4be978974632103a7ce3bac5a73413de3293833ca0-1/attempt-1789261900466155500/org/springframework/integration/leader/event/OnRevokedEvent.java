/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.leader.event;

import org.springframework.integration.leader.Context;
import org.springframework.integration.leader.event.AbstractLeaderEvent;

public class OnRevokedEvent
extends AbstractLeaderEvent {
    public OnRevokedEvent(Object source, Context context, String role) {
        super(source, context, role);
    }
}

