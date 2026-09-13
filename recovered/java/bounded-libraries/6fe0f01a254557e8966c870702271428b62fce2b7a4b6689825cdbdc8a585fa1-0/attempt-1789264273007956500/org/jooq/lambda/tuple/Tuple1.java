/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.tuple;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import org.jooq.lambda.Seq;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple0;
import org.jooq.lambda.tuple.Tuple10;
import org.jooq.lambda.tuple.Tuple11;
import org.jooq.lambda.tuple.Tuple12;
import org.jooq.lambda.tuple.Tuple13;
import org.jooq.lambda.tuple.Tuple14;
import org.jooq.lambda.tuple.Tuple15;
import org.jooq.lambda.tuple.Tuple16;
import org.jooq.lambda.tuple.Tuple2;
import org.jooq.lambda.tuple.Tuple3;
import org.jooq.lambda.tuple.Tuple4;
import org.jooq.lambda.tuple.Tuple5;
import org.jooq.lambda.tuple.Tuple6;
import org.jooq.lambda.tuple.Tuple7;
import org.jooq.lambda.tuple.Tuple8;
import org.jooq.lambda.tuple.Tuple9;
import org.jooq.lambda.tuple.Tuples;

public class Tuple1<T1>
implements Tuple,
Comparable<Tuple1<T1>>,
Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    public final T1 v1;

    public T1 v1() {
        return this.v1;
    }

    public Tuple1(Tuple1<T1> tuple) {
        this.v1 = tuple.v1;
    }

    public Tuple1(T1 v1) {
        this.v1 = v1;
    }

    public final <T2> Tuple2<T1, T2> concat(T2 value) {
        return new Tuple2<T1, T2>(this.v1, value);
    }

    public final <T2> Tuple2<T1, T2> concat(Tuple1<T2> tuple) {
        return new Tuple2<T1, T1>(this.v1, tuple.v1);
    }

    public final <T2, T3> Tuple3<T1, T2, T3> concat(Tuple2<T2, T3> tuple) {
        return new Tuple3(this.v1, tuple.v1, tuple.v2);
    }

    public final <T2, T3, T4> Tuple4<T1, T2, T3, T4> concat(Tuple3<T2, T3, T4> tuple) {
        return new Tuple4(this.v1, tuple.v1, tuple.v2, tuple.v3);
    }

    public final <T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> concat(Tuple4<T2, T3, T4, T5> tuple) {
        return new Tuple5(this.v1, tuple.v1, tuple.v2, tuple.v3, tuple.v4);
    }

    public final <T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> concat(Tuple5<T2, T3, T4, T5, T6> tuple) {
        return new Tuple6(this.v1, tuple.v1, tuple.v2, tuple.v3, tuple.v4, tuple.v5);
    }

    public final <T2, T3, T4, T5, T6, T7> Tuple7<T1, T2, T3, T4, T5, T6, T7> concat(Tuple6<T2, T3, T4, T5, T6, T7> tuple) {
        return new Tuple7(this.v1, tuple.v1, tuple.v2, tuple.v3, tuple.v4, tuple.v5, tuple.v6);
    }

    public final <T2, T3, T4, T5, T6, T7, T8> Tuple8<T1, T2, T3, T4, T5, T6, T7, T8> concat(Tuple7<T2, T3, T4, T5, T6, T7, T8> tuple) {
        return new Tuple8(this.v1, tuple.v1, tuple.v2, tuple.v3, tuple.v4, tuple.v5, tuple.v6, tuple.v7);
    }

    public final <T2, T3, T4, T5, T6, T7, T8, T9> Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9> concat(Tuple8<T2, T3, T4, T5, T6, T7, T8, T9> tuple) {
        return new Tuple9(this.v1, tuple.v1, tuple.v2, tuple.v3, tuple.v4, tuple.v5, tuple.v6, tuple.v7, tuple.v8);
    }

    public final <T2, T3, T4, T5, T6, T7, T8, T9, T10> Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> concat(Tuple9<T2, T3, T4, T5, T6, T7, T8, T9, T10> tuple) {
        return new Tuple10(this.v1, tuple.v1, tuple.v2, tuple.v3, tuple.v4, tuple.v5, tuple.v6, tuple.v7, tuple.v8, tuple.v9);
    }

    public final <T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> concat(Tuple10<T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> tuple) {
        return new Tuple11(this.v1, tuple.v1, tuple.v2, tuple.v3, tuple.v4, tuple.v5, tuple.v6, tuple.v7, tuple.v8, tuple.v9, tuple.v10);
    }

    public final <T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Tuple12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> concat(Tuple11<T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> tuple) {
        return new Tuple12(this.v1, tuple.v1, tuple.v2, tuple.v3, tuple.v4, tuple.v5, tuple.v6, tuple.v7, tuple.v8, tuple.v9, tuple.v10, tuple.v11);
    }

    public final <T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Tuple13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> concat(Tuple12<T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> tuple) {
        return new Tuple13(this.v1, tuple.v1, tuple.v2, tuple.v3, tuple.v4, tuple.v5, tuple.v6, tuple.v7, tuple.v8, tuple.v9, tuple.v10, tuple.v11, tuple.v12);
    }

    public final <T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Tuple14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> concat(Tuple13<T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> tuple) {
        return new Tuple14(this.v1, tuple.v1, tuple.v2, tuple.v3, tuple.v4, tuple.v5, tuple.v6, tuple.v7, tuple.v8, tuple.v9, tuple.v10, tuple.v11, tuple.v12, tuple.v13);
    }

    public final <T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Tuple15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> concat(Tuple14<T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> tuple) {
        return new Tuple15(this.v1, tuple.v1, tuple.v2, tuple.v3, tuple.v4, tuple.v5, tuple.v6, tuple.v7, tuple.v8, tuple.v9, tuple.v10, tuple.v11, tuple.v12, tuple.v13, tuple.v14);
    }

    public final <T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> concat(Tuple15<T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> tuple) {
        return new Tuple16(this.v1, tuple.v1, tuple.v2, tuple.v3, tuple.v4, tuple.v5, tuple.v6, tuple.v7, tuple.v8, tuple.v9, tuple.v10, tuple.v11, tuple.v12, tuple.v13, tuple.v14, tuple.v15);
    }

    public final Tuple2<Tuple0, Tuple1<T1>> split0() {
        return new Tuple2<Tuple0, Tuple1<T1>>(this.limit0(), this.skip0());
    }

    public final Tuple2<Tuple1<T1>, Tuple0> split1() {
        return new Tuple2<Tuple1<T1>, Tuple0>(this.limit1(), this.skip1());
    }

    public final Tuple0 limit0() {
        return new Tuple0();
    }

    public final Tuple1<T1> limit1() {
        return this;
    }

    public final Tuple1<T1> skip0() {
        return this;
    }

    public final Tuple0 skip1() {
        return new Tuple0();
    }

    public final <R> R map(Function<? super T1, ? extends R> function) {
        return function.apply(this.v1);
    }

    public final <U1> Tuple1<U1> map1(Function<? super T1, ? extends U1> function) {
        return Tuple.tuple(function.apply(this.v1));
    }

    @Override
    @Deprecated
    public final Object[] array() {
        return this.toArray();
    }

    @Override
    public final Object[] toArray() {
        return new Object[]{this.v1};
    }

    @Override
    @Deprecated
    public final List<?> list() {
        return this.toList();
    }

    @Override
    public final List<?> toList() {
        return Arrays.asList(this.toArray());
    }

    @Override
    public final Seq<?> toSeq() {
        return Seq.seq(this.toList());
    }

    @Override
    public final Map<String, ?> toMap() {
        return this.toMap(i -> "v" + (i + 1));
    }

    @Override
    public final <K> Map<K, ?> toMap(Function<? super Integer, ? extends K> keyMapper) {
        LinkedHashMap<K, Object> result = new LinkedHashMap<K, Object>();
        Object[] array = this.toArray();
        for (int i = 0; i < array.length; ++i) {
            result.put(keyMapper.apply(i), array[i]);
        }
        return result;
    }

    @Override
    public final int degree() {
        return 1;
    }

    @Override
    public final Iterator<Object> iterator() {
        return this.list().iterator();
    }

    @Override
    public int compareTo(Tuple1<T1> other) {
        int result = 0;
        result = Tuples.compare(this.v1, other.v1);
        if (result != 0) {
            return result;
        }
        return result;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tuple1)) {
            return false;
        }
        Tuple1 that = (Tuple1)o;
        return Objects.equals(this.v1, that.v1);
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.v1 == null ? 0 : this.v1.hashCode());
        return result;
    }

    public String toString() {
        return "(" + this.v1 + ")";
    }

    public Tuple1<T1> clone() {
        return new Tuple1<Tuple1>(this);
    }
}

