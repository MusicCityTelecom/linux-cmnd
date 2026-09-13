/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.misc;

public class MutableInt
extends Number
implements Comparable<Number> {
    public int v;

    public MutableInt(int v) {
        this.v = v;
    }

    public boolean equals(Object o) {
        if (o instanceof Number) {
            return this.v == ((Number)o).intValue();
        }
        return false;
    }

    public int hashCode() {
        return this.v;
    }

    @Override
    public int compareTo(Number o) {
        return this.v - o.intValue();
    }

    @Override
    public int intValue() {
        return this.v;
    }

    @Override
    public long longValue() {
        return this.v;
    }

    @Override
    public float floatValue() {
        return this.v;
    }

    @Override
    public double doubleValue() {
        return this.v;
    }

    public String toString() {
        return String.valueOf(this.v);
    }
}

