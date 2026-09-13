/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.impl.matchers;

import org.quartz.Matcher;
import org.quartz.utils.Key;

public class OrMatcher<T extends Key<?>>
implements Matcher<T> {
    private static final long serialVersionUID = -2867392824539403712L;
    protected Matcher<T> leftOperand;
    protected Matcher<T> rightOperand;

    protected OrMatcher(Matcher<T> leftOperand, Matcher<T> rightOperand) {
        if (leftOperand == null || rightOperand == null) {
            throw new IllegalArgumentException("Two non-null operands required!");
        }
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    public static <U extends Key<?>> OrMatcher<U> or(Matcher<U> leftOperand, Matcher<U> rightOperand) {
        return new OrMatcher<U>(leftOperand, rightOperand);
    }

    @Override
    public boolean isMatch(T key) {
        return this.leftOperand.isMatch(key) || this.rightOperand.isMatch(key);
    }

    public Matcher<T> getLeftOperand() {
        return this.leftOperand;
    }

    public Matcher<T> getRightOperand() {
        return this.rightOperand;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.leftOperand == null ? 0 : this.leftOperand.hashCode());
        result = 31 * result + (this.rightOperand == null ? 0 : this.rightOperand.hashCode());
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
        OrMatcher other = (OrMatcher)obj;
        if (this.leftOperand == null ? other.leftOperand != null : !this.leftOperand.equals(other.leftOperand)) {
            return false;
        }
        return !(this.rightOperand == null ? other.rightOperand != null : !this.rightOperand.equals(other.rightOperand));
    }
}

