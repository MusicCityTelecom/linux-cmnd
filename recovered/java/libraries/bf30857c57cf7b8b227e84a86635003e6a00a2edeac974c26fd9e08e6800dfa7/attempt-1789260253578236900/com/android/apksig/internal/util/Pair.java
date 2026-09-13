/*
 * Decompiled with CFR 0.152.
 */
package com.android.apksig.internal.util;

public final class Pair<A, B> {
    private final A mFirst;
    private final B mSecond;

    private Pair(A first, B second) {
        this.mFirst = first;
        this.mSecond = second;
    }

    public static <A, B> Pair<A, B> of(A first, B second) {
        return new Pair<A, B>(first, second);
    }

    public A getFirst() {
        return this.mFirst;
    }

    public B getSecond() {
        return this.mSecond;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.mFirst == null ? 0 : this.mFirst.hashCode());
        result = 31 * result + (this.mSecond == null ? 0 : this.mSecond.hashCode());
        return result;
    }

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
        Pair other = (Pair)obj;
        if (this.mFirst == null ? other.mFirst != null : !this.mFirst.equals(other.mFirst)) {
            return false;
        }
        return !(this.mSecond == null ? other.mSecond != null : !this.mSecond.equals(other.mSecond));
    }
}

