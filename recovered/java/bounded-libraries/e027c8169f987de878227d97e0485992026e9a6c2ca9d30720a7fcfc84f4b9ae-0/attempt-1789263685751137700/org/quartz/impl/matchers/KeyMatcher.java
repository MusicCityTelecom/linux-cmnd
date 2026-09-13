/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.impl.matchers;

import org.quartz.Matcher;
import org.quartz.utils.Key;

public class KeyMatcher<T extends Key<?>>
implements Matcher<T> {
    private static final long serialVersionUID = 1230009869074992437L;
    protected T compareTo;

    protected KeyMatcher(T compareTo) {
        this.compareTo = compareTo;
    }

    public static <U extends Key<?>> KeyMatcher<U> keyEquals(U compareTo) {
        return new KeyMatcher<U>(compareTo);
    }

    @Override
    public boolean isMatch(T key) {
        return ((Key)this.compareTo).equals(key);
    }

    public T getCompareToValue() {
        return this.compareTo;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.compareTo == null ? 0 : ((Key)this.compareTo).hashCode());
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
        KeyMatcher other = (KeyMatcher)obj;
        return !(this.compareTo == null ? other.compareTo != null : !((Key)this.compareTo).equals(other.compareTo));
    }
}

