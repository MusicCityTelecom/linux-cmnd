/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 */
package org.springframework.messaging.simp;

import java.util.Collection;
import java.util.Collections;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.AbstractMessageCondition;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.util.Assert;

public class SimpMessageTypeMessageCondition
extends AbstractMessageCondition<SimpMessageTypeMessageCondition> {
    public static final SimpMessageTypeMessageCondition MESSAGE = new SimpMessageTypeMessageCondition(SimpMessageType.MESSAGE);
    public static final SimpMessageTypeMessageCondition SUBSCRIBE = new SimpMessageTypeMessageCondition(SimpMessageType.SUBSCRIBE);
    private final SimpMessageType messageType;

    public SimpMessageTypeMessageCondition(SimpMessageType messageType) {
        Assert.notNull((Object)((Object)messageType), (String)"MessageType must not be null");
        this.messageType = messageType;
    }

    public SimpMessageType getMessageType() {
        return this.messageType;
    }

    @Override
    protected Collection<?> getContent() {
        return Collections.singletonList(this.messageType);
    }

    @Override
    protected String getToStringInfix() {
        return " || ";
    }

    @Override
    public SimpMessageTypeMessageCondition combine(SimpMessageTypeMessageCondition other) {
        return other;
    }

    @Override
    @Nullable
    public SimpMessageTypeMessageCondition getMatchingCondition(Message<?> message) {
        SimpMessageType actual = SimpMessageHeaderAccessor.getMessageType(message.getHeaders());
        return actual != null && actual.equals((Object)this.messageType) ? this : null;
    }

    @Override
    public int compareTo(SimpMessageTypeMessageCondition other, Message<?> message) {
        SimpMessageType actual = SimpMessageHeaderAccessor.getMessageType(message.getHeaders());
        if (actual != null) {
            if (((Object)((Object)actual)).equals((Object)this.messageType) && ((Object)((Object)actual)).equals((Object)other.getMessageType())) {
                return 0;
            }
            if (((Object)((Object)actual)).equals((Object)this.messageType)) {
                return -1;
            }
            if (((Object)((Object)actual)).equals((Object)other.getMessageType())) {
                return 1;
            }
        }
        return 0;
    }
}

