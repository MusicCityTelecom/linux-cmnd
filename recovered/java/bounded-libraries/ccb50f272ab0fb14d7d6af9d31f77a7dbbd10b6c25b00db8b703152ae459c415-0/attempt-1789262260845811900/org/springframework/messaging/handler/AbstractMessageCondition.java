/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 */
package org.springframework.messaging.handler;

import java.util.Collection;
import java.util.StringJoiner;
import org.springframework.lang.Nullable;
import org.springframework.messaging.handler.MessageCondition;

public abstract class AbstractMessageCondition<T extends AbstractMessageCondition<T>>
implements MessageCondition<T> {
    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }
        return this.getContent().equals(((AbstractMessageCondition)other).getContent());
    }

    public int hashCode() {
        return this.getContent().hashCode();
    }

    public String toString() {
        StringJoiner joiner = new StringJoiner(this.getToStringInfix(), "[", "]");
        for (Object expression : this.getContent()) {
            joiner.add(expression.toString());
        }
        return joiner.toString();
    }

    protected abstract Collection<?> getContent();

    protected abstract String getToStringInfix();
}

