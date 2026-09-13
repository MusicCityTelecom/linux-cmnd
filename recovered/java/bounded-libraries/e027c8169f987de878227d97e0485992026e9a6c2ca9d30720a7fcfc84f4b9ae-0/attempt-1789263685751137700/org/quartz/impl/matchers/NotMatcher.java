/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.impl.matchers;

import org.quartz.Matcher;
import org.quartz.utils.Key;

public class NotMatcher<T extends Key<?>>
implements Matcher<T> {
    private static final long serialVersionUID = -2856769076151741391L;
    protected Matcher<T> operand;

    protected NotMatcher(Matcher<T> operand) {
        if (operand == null) {
            throw new IllegalArgumentException("Non-null operand required!");
        }
        this.operand = operand;
    }

    public static <U extends Key<?>> NotMatcher<U> not(Matcher<U> operand) {
        return new NotMatcher<U>(operand);
    }

    @Override
    public boolean isMatch(T key) {
        return !this.operand.isMatch(key);
    }

    public Matcher<T> getOperand() {
        return this.operand;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.operand == null ? 0 : this.operand.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        NotMatcher other = (NotMatcher)obj;
        return !(this.operand == null ? other.operand != null : !this.operand.equals(other.operand));
    }
}

