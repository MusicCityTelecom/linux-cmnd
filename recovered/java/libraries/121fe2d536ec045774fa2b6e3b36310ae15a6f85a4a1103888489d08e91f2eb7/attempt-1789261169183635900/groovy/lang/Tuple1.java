/*
 * Decompiled with CFR 0.152.
 */
package groovy.lang;

import groovy.lang.Tuple;

public final class Tuple1<T1>
extends Tuple {
    private static final long serialVersionUID = -4647790147461409603L;
    private final T1 v1;

    public Tuple1(T1 t1) {
        super(t1);
        this.v1 = t1;
    }

    public Tuple1(Tuple1<T1> tuple) {
        this(tuple.v1);
    }

    @Deprecated
    public T1 getFirst() {
        return this.v1;
    }

    public T1 getV1() {
        return this.v1;
    }

    @Override
    public Tuple1<T1> clone() {
        return new Tuple1<T1>(this);
    }
}

