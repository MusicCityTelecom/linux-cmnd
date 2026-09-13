/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 */
package org.springframework.messaging.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.MessageCondition;
import org.springframework.util.Assert;

public class CompositeMessageCondition
implements MessageCondition<CompositeMessageCondition> {
    private final List<MessageCondition<?>> messageConditions;

    public CompositeMessageCondition(MessageCondition<?> ... messageConditions) {
        this(Arrays.asList(messageConditions));
    }

    private CompositeMessageCondition(List<MessageCondition<?>> messageConditions) {
        Assert.notEmpty(messageConditions, (String)"No message conditions");
        this.messageConditions = messageConditions;
    }

    public List<MessageCondition<?>> getMessageConditions() {
        return this.messageConditions;
    }

    public <T extends MessageCondition<T>> T getCondition(Class<T> messageConditionType) {
        for (MessageCondition<?> condition : this.messageConditions) {
            if (!messageConditionType.isAssignableFrom(condition.getClass())) continue;
            return (T)condition;
        }
        throw new IllegalStateException("No condition of type: " + messageConditionType);
    }

    @Override
    public CompositeMessageCondition combine(CompositeMessageCondition other) {
        this.checkCompatible(other);
        ArrayList result = new ArrayList(this.messageConditions.size());
        for (int i2 = 0; i2 < this.messageConditions.size(); ++i2) {
            result.add((MessageCondition<?>)this.combine(this.getMessageConditions().get(i2), other.getMessageConditions().get(i2)));
        }
        return new CompositeMessageCondition(result);
    }

    private <T extends MessageCondition<T>> T combine(MessageCondition<?> first, MessageCondition<?> second) {
        return (T)first.combine(second);
    }

    @Override
    public CompositeMessageCondition getMatchingCondition(Message<?> message) {
        ArrayList result = new ArrayList(this.messageConditions.size());
        for (MessageCondition<?> condition : this.messageConditions) {
            MessageCondition matchingCondition = (MessageCondition)condition.getMatchingCondition(message);
            if (matchingCondition == null) {
                return null;
            }
            result.add(matchingCondition);
        }
        return new CompositeMessageCondition(result);
    }

    @Override
    public int compareTo(CompositeMessageCondition other, Message<?> message) {
        this.checkCompatible(other);
        List<MessageCondition<?>> otherConditions = other.getMessageConditions();
        for (int i2 = 0; i2 < this.messageConditions.size(); ++i2) {
            int result = this.compare(this.messageConditions.get(i2), otherConditions.get(i2), message);
            if (result == 0) continue;
            return result;
        }
        return 0;
    }

    private <T extends MessageCondition<T>> int compare(MessageCondition<?> first, MessageCondition<?> second, Message<?> message) {
        return first.compareTo(second, message);
    }

    private void checkCompatible(CompositeMessageCondition other) {
        List<MessageCondition<?>> others = other.getMessageConditions();
        for (int i2 = 0; i2 < this.messageConditions.size(); ++i2) {
            if (i2 < others.size() && this.messageConditions.get(i2).getClass().equals(others.get(i2).getClass())) continue;
            throw new IllegalArgumentException("Mismatched CompositeMessageCondition: " + this.messageConditions + " vs " + others);
        }
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CompositeMessageCondition)) {
            return false;
        }
        CompositeMessageCondition otherComposite = (CompositeMessageCondition)other;
        this.checkCompatible(otherComposite);
        List<MessageCondition<?>> otherConditions = otherComposite.getMessageConditions();
        for (int i2 = 0; i2 < this.messageConditions.size(); ++i2) {
            if (this.messageConditions.get(i2).equals(otherConditions.get(i2))) continue;
            return false;
        }
        return true;
    }

    public int hashCode() {
        int hashCode = 0;
        for (MessageCondition<?> condition : this.messageConditions) {
            hashCode += condition.hashCode() * 31;
        }
        return hashCode;
    }

    public String toString() {
        return this.messageConditions.stream().map(Object::toString).collect(Collectors.joining(",", "{", "}"));
    }
}

