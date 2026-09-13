/*
 * Decompiled with CFR 0.152.
 */
package groovy.lang;

import groovy.lang.Tuple;

public final class Tuple4<T1, T2, T3, T4>
extends Tuple {
    private static final long serialVersionUID = -7788878731471377207L;
    private final T1 v1;
    private final T2 v2;
    private final T3 v3;
    private final T4 v4;

    public Tuple4(T1 v1, T2 v2, T3 v3, T4 v4) {
        super(v1, v2, v3, v4);
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        this.v4 = v4;
    }

    public Tuple4(Tuple4<T1, T2, T3, T4> tuple) {
        this(tuple.v1, tuple.v2, tuple.v3, tuple.v4);
    }

    @Deprecated
    public T1 getFirst() {
        return this.v1;
    }

    @Deprecated
    public T2 getSecond() {
        return this.v2;
    }

    @Deprecated
    public T3 getThird() {
        return this.v3;
    }

    @Deprecated
    public T4 getFourth() {
        return this.v4;
    }

    public T1 getV1() {
        return this.v1;
    }

    public T2 getV2() {
        return this.v2;
    }

    public T3 getV3() {
        return this.v3;
    }

    public T4 getV4() {
        return this.v4;
    }

    @Override
    public Tuple4<T1, T2, T3, T4> clone() {
        return new Tuple4<T1, T2, T3, T4>(this);
    }
}

