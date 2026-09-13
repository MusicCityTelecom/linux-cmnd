/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.misc;

import groovyjarjarantlr4.v4.runtime.misc.Tuple;

public class Tuple2<T1, T2> {
    private final T1 item1;
    private final T2 item2;

    public Tuple2(T1 item1, T2 item2) {
        this.item1 = item1;
        this.item2 = item2;
    }

    public final T1 getItem1() {
        return this.item1;
    }

    public final T2 getItem2() {
        return this.item2;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Tuple2)) {
            return false;
        }
        Tuple2 other = (Tuple2)obj;
        return Tuple.equals(this.item1, other.item1) && Tuple.equals(this.item2, other.item2);
    }

    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + (this.item1 != null ? this.item1.hashCode() : 0);
        hash = 79 * hash + (this.item2 != null ? this.item2.hashCode() : 0);
        return hash;
    }

    public String toString() {
        return String.format("(%s, %s)", this.item1, this.item2);
    }
}

