/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 *  org.springframework.util.ObjectUtils
 */
package org.springframework.boot.autoconfigure.condition;

import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

public class ConditionOutcome {
    private final boolean match;
    private final ConditionMessage message;

    public ConditionOutcome(boolean match, String message) {
        this(match, ConditionMessage.of(message, new Object[0]));
    }

    public ConditionOutcome(boolean match, ConditionMessage message) {
        Assert.notNull((Object)message, (String)"ConditionMessage must not be null");
        this.match = match;
        this.message = message;
    }

    public static ConditionOutcome match() {
        return ConditionOutcome.match(ConditionMessage.empty());
    }

    public static ConditionOutcome match(String message) {
        return new ConditionOutcome(true, message);
    }

    public static ConditionOutcome match(ConditionMessage message) {
        return new ConditionOutcome(true, message);
    }

    public static ConditionOutcome noMatch(String message) {
        return new ConditionOutcome(false, message);
    }

    public static ConditionOutcome noMatch(ConditionMessage message) {
        return new ConditionOutcome(false, message);
    }

    public boolean isMatch() {
        return this.match;
    }

    public String getMessage() {
        return this.message.isEmpty() ? null : this.message.toString();
    }

    public ConditionMessage getConditionMessage() {
        return this.message;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() == obj.getClass()) {
            ConditionOutcome other = (ConditionOutcome)obj;
            return this.match == other.match && ObjectUtils.nullSafeEquals((Object)this.message, (Object)other.message);
        }
        return super.equals(obj);
    }

    public int hashCode() {
        return Boolean.hashCode(this.match) * 31 + ObjectUtils.nullSafeHashCode((Object)this.message);
    }

    public String toString() {
        return this.message != null ? this.message.toString() : "";
    }

    public static ConditionOutcome inverse(ConditionOutcome outcome) {
        return new ConditionOutcome(!outcome.isMatch(), outcome.getConditionMessage());
    }
}

