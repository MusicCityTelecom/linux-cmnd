/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.impl.matchers;

import org.quartz.Matcher;
import org.quartz.utils.Key;

public abstract class StringMatcher<T extends Key<?>>
implements Matcher<T> {
    private static final long serialVersionUID = -2757924162611145836L;
    protected String compareTo;
    protected StringOperatorName compareWith;

    protected StringMatcher(String compareTo, StringOperatorName compareWith) {
        if (compareTo == null) {
            throw new IllegalArgumentException("CompareTo value cannot be null!");
        }
        if (compareWith == null) {
            throw new IllegalArgumentException("CompareWith operator cannot be null!");
        }
        this.compareTo = compareTo;
        this.compareWith = compareWith;
    }

    protected abstract String getValue(T var1);

    @Override
    public boolean isMatch(T key) {
        return this.compareWith.evaluate(this.getValue(key), this.compareTo);
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.compareTo == null ? 0 : this.compareTo.hashCode());
        result = 31 * result + (this.compareWith == null ? 0 : this.compareWith.hashCode());
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
        StringMatcher other = (StringMatcher)obj;
        if (this.compareTo == null ? other.compareTo != null : !this.compareTo.equals(other.compareTo)) {
            return false;
        }
        return !(this.compareWith == null ? other.compareWith != null : !this.compareWith.equals((Object)other.compareWith));
    }

    public String getCompareToValue() {
        return this.compareTo;
    }

    public StringOperatorName getCompareWithOperator() {
        return this.compareWith;
    }

    public static enum StringOperatorName {
        EQUALS{

            @Override
            public boolean evaluate(String value, String compareTo) {
                return value.equals(compareTo);
            }
        }
        ,
        STARTS_WITH{

            @Override
            public boolean evaluate(String value, String compareTo) {
                return value.startsWith(compareTo);
            }
        }
        ,
        ENDS_WITH{

            @Override
            public boolean evaluate(String value, String compareTo) {
                return value.endsWith(compareTo);
            }
        }
        ,
        CONTAINS{

            @Override
            public boolean evaluate(String value, String compareTo) {
                return value.contains(compareTo);
            }
        }
        ,
        ANYTHING{

            @Override
            public boolean evaluate(String value, String compareTo) {
                return true;
            }
        };


        public abstract boolean evaluate(String var1, String var2);
    }
}

