/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 */
package org.springframework.messaging.simp;

import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.CompositeMessageCondition;
import org.springframework.messaging.handler.DestinationPatternsMessageCondition;
import org.springframework.messaging.handler.MessageCondition;
import org.springframework.messaging.simp.SimpMessageTypeMessageCondition;

public class SimpMessageMappingInfo
implements MessageCondition<SimpMessageMappingInfo> {
    private final CompositeMessageCondition delegate;

    public SimpMessageMappingInfo(SimpMessageTypeMessageCondition messageTypeMessageCondition, DestinationPatternsMessageCondition destinationConditions) {
        this.delegate = new CompositeMessageCondition(messageTypeMessageCondition, destinationConditions);
    }

    private SimpMessageMappingInfo(CompositeMessageCondition delegate) {
        this.delegate = delegate;
    }

    public SimpMessageTypeMessageCondition getMessageTypeMessageCondition() {
        return this.delegate.getCondition(SimpMessageTypeMessageCondition.class);
    }

    public DestinationPatternsMessageCondition getDestinationConditions() {
        return this.delegate.getCondition(DestinationPatternsMessageCondition.class);
    }

    @Override
    public SimpMessageMappingInfo combine(SimpMessageMappingInfo other) {
        return new SimpMessageMappingInfo(this.delegate.combine(other.delegate));
    }

    @Override
    @Nullable
    public SimpMessageMappingInfo getMatchingCondition(Message<?> message) {
        Object condition = this.delegate.getMatchingCondition((Message)message);
        return condition != null ? new SimpMessageMappingInfo((CompositeMessageCondition)condition) : null;
    }

    @Override
    public int compareTo(SimpMessageMappingInfo other, Message<?> message) {
        return this.delegate.compareTo(other.delegate, message);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SimpMessageMappingInfo)) {
            return false;
        }
        return this.delegate.equals(((SimpMessageMappingInfo)other).delegate);
    }

    public int hashCode() {
        return this.delegate.hashCode();
    }

    public String toString() {
        return this.delegate.toString();
    }
}

