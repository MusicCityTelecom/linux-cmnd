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
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import org.jooq.lambda.Seq;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple0;
import org.jooq.lambda.tuple.Tuple1;
import org.jooq.lambda.tuple.Tuple10;
import org.jooq.lambda.tuple.Tuple11;
import org.jooq.lambda.tuple.Tuple12;
import org.jooq.lambda.tuple.Tuple13;
import org.jooq.lambda.tuple.Tuple14;
import org.jooq.lambda.tuple.Tuple15;
import org.jooq.lambda.tuple.Tuple16;
import org.jooq.lambda.tuple.Tuple3;
import org.jooq.lambda.tuple.Tuple4;
import org.jooq.lambda.tuple.Tuple5;
import org.jooq.lambda.tuple.Tuple6;
import org.jooq.lambda.tuple.Tuple7;
import org.jooq.lambda.tuple.Tuple8;
import org.jooq.lambda.tuple.Tuple9;
import org.jooq.lambda.tuple.Tuples;

public class Tuple2<T1, T2>
implements Tuple,
Comparable<Tuple2<T1, T2>>,
Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    public final T1 v1;
    public final T2 v2;

    public T1 v1() {
        return this.v1;
    }

    public T2 v2() {
        return this.v2;
    }

    public Tuple2(Tuple2<T1, T2> tuple) {
        this.v1 = tuple.v1;
        this.v2 = tuple.v2;
    }

    public Tuple2(T1 v1, T2 v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    public final <T3> Tuple3<T1, T2, T3> concat(T3 value) {
        return new Tuple3<T1, T2, T3>(this.v1, this.v2, value);
    }

    public final <T3> Tuple3<T1, T2, T3> concat(Tuple1<T3> tuple) {
        return new Tuple3(this.v1, this.v2, tuple.v1);
    }

    public final <T3, T4> Tuple4<T1, T2, T3, T4> concat(Tuple2<T3, T4> tuple) {
        return new Tuple4<T1, T2, T1, T2>(this.v1, this.v2, tuple.v1, tuple.v2);
    }

    public final <T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> concat(Tuple3<T3, T4, T5> tuple) {
        return new Tuple5(this.v1, this.v2, tuple.v1, tuple.v2, tuple.v3);
    }

    public final <T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> concat(Tuple4<T3, T4, T5, T6> tuple) {
        return new Tuple6(this.v1, this.v2, tuple.v1, tuple.v2, tuple.v3, tuple.v4);
    }

    public final <T3, T4, T5, T6, T7> Tuple7<T1, T2, T3, T4, T5, T6, T7> concat(Tuple5<T3, T4, T5, T6, T7> tuple) {
        return new Tuple7(this.v1, this.v2, tuple.v1, tuple.v2, tuple.v3, tuple.v4, tuple.v5);
    }

    public final <T3, T4, T5, T6, T7, T8> Tuple8<T1, T2, T3, T4, T5, T6, T7, T8> concat(Tuple6<T3, T4, T5, T6, T7, T8> tuple) {
        return new Tuple8(this.v1, this.v2, tuple.v1, tuple.v2, tuple.v3, tuple.v4, tuple.v5, tuple.v6);
    }

    public final <T3, T4, T5, T6, T7, T8, T9> Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9> concat(Tuple7<T3, T4, T5, T6, T7, T8, T9> tuple) {
        return new Tuple9(this.v1, this.v2, tuple.v1, tuple.v2, tuple.v3, tuple.v4, tuple.v5, tuple.v6, tuple.v7);
    }

    public final <T3, T4, T5, T6, T7, T8, T9, T10> Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> concat(Tuple8<T3, T4, T5, T6, T7, T8, T9, T10> tuple) {
        return new Tuple10(this.v1, this.v2, tuple.v1, tuple.v2, tuple.v3, tuple.v4, tuple.v5, tuple.v6, tuple.v7, tuple.v8);
    }

    public final <T3, T4, T5, T6, T7, T8, T9, T10, T11> Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> concat(Tuple9<T3, T4, T5, T6, T7, T8, T9, T10, T11> tuple) {
        return new Tuple11(this.v1, this.v2, tuple.v1, tuple.v2, tuple.v3, tuple.v4, tuple.v5, tuple.v6, tuple.v7, tuple.v8, tuple.v9);
    }

    public final <T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Tuple12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> concat(Tuple10<T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> tuple) {
        return new Tuple12(this.v1, this.v2, tuple.v1, tuple.v2, tuple.v3, tuple.v4, tuple.v5, tuple.v6, tuple.v7, tuple.v8, tuple.v9, tuple.v10);
    }

    public final <T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Tuple13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> concat(Tuple11<T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> tuple) {
        return new Tuple13(this.v1, this.v2, tuple.v1, tuple.v2, tuple.v3, tuple.v4, tuple.v5, tuple.v6, tuple.v7, tuple.v8, tuple.v9, tuple.v10, tuple.v11);
    }

    public final <T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Tuple14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> concat(Tuple12<T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> tuple) {
        return new Tuple14(this.v1, this.v2, tuple.v1, tuple.v2, tuple.v3, tuple.v4, tuple.v5, tuple.v6, tuple.v7, tuple.v8, tuple.v9, tuple.v10, tuple.v11, tuple.v12);
    }

    public final <T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Tuple15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> concat(Tuple13<T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> tuple) {
        return new Tuple15(this.v1, this.v2, tuple.v1, tuple.v2, tuple.v3, tuple.v4, tuple.v5, tuple.v6, tuple.v7, tuple.v8, tuple.v9, tuple.v10, tuple.v11, tuple.v12, tuple.v13);
    }

    public final <T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> concat(Tuple14<T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> tuple) {
        return new Tuple16(this.v1, this.v2, tuple.v1, tuple.v2, tuple.v3, tuple.v4, tuple.v5, tuple.v6, tuple.v7, tuple.v8, tuple.v9, tuple.v10, tuple.v11, tuple.v12, tuple.v13, tuple.v14);
    }

    public final Tuple2<Tuple0, Tuple2<T1, T2>> split0() {
        return new Tuple2<Tuple0, Tuple2<T1, T2>>(this.limit0(), this.skip0());
    }

    public final Tuple2<Tuple1<T1>, Tuple1<T2>> split1() {
        return new Tuple2<Tuple1<T1>, Tuple1<T2>>(this.limit1(), this.skip1());
    }

    public final Tuple2<Tuple2<T1, T2>, Tuple0> split2() {
        return new Tuple2<Tuple2<T1, T2>, Tuple0>(this.limit2(), this.skip2());
    }

    public final Tuple0 limit0() {
        return new Tuple0();
    }

    public final Tuple1<T1> limit1() {
        return new Tuple1<T1>(this.v1);
    }

    public final Tuple2<T1, T2> limit2() {
        return this;
    }

    public final Tuple2<T1, T2> skip0() {
        return this;
    }

    public final Tuple1<T2> skip1() {
        return new Tuple1<T2>(this.v2);
    }

    public final Tuple0 skip2() {
        return new Tuple0();
    }

    public final Tuple2<T2, T1> swap() {
        return new Tuple2<T2, T1>(this.v2, this.v1);
    }

    public static final <T extends Comparable<? super T>> boolean overlaps(Tuple2<T, T> left, Tuple2<T, T> right) {
        return ((Comparable)left.v1).compareTo(right.v2) <= 0 && ((Comparable)left.v2).compareTo(right.v1) >= 0;
    }

    public static final <T extends Comparable<? super T>> Optional<Tuple2<T, T>> intersect(Tuple2<T, T> left, Tuple2<T, T> right) {
        if (Tuple2.overlaps(left, right)) {
            return Optional.of(new Tuple2<Comparable, Comparable>(((Comparable)left.v1).compareTo(right.v1) >= 0 ? (Comparable)left.v1 : (Comparable)right.v1, ((Comparable)left.v2).compareTo(right.v2) <= 0 ? (Comparable)left.v2 : (Comparable)right.v2));
        }
        return Optional.empty();
    }

    public final <R> R map(BiFunction<? super T1, ? super T2, ? extends R> function) {
        return function.apply(this.v1, this.v2);
    }

    public final <U1> Tuple2<U1, T2> map1(Function<? super T1, ? extends U1> function) {
        return Tuple.tuple(function.apply(this.v1), this.v2);
    }

    public final <U2> Tuple2<T1, U2> map2(Function<? super T2, ? extends U2> function) {
        return Tuple.tuple(this.v1, function.apply(this.v2));
    }

    @Override
    @Deprecated
    public final Object[] array() {
        return this.toArray();
    }

    @Override
    public final Object[] toArray() {
        return new Object[]{this.v1, this.v2};
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

    public final <K> Map<K, ?> toMap(Supplier<? extends K> keySupplier1, Supplier<? extends K> keySupplier2) {
        LinkedHashMap<K, Object> result = new LinkedHashMap<K, Object>();
        result.put(keySupplier1.get(), this.v1);
        result.put(keySupplier2.get(), this.v2);
        return result;
    }

    public final <K> Map<K, ?> toMap(K key1, K key2) {
        LinkedHashMap<K, Object> result = new LinkedHashMap<K, Object>();
        result.put(key1, this.v1);
        result.put(key2, this.v2);
        return result;
    }

    @Override
    public final int degree() {
        return 2;
    }

    @Override
    public final Iterator<Object> iterator() {
        return this.list().iterator();
    }

    @Override
    public int compareTo(Tuple2<T1, T2> other) {
        int result = 0;
        result = Tuples.compare(this.v1, other.v1);
        if (result != 0) {
            return result;
        }
        result = Tuples.compare(this.v2, other.v2);
        if (result != 0) {
            return result;
        }
        return result;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tuple2)) {
            return false;
        }
        Tuple2 that = (Tuple2)o;
        if (!Objects.equals(this.v1, that.v1)) {
            return false;
        }
        return Objects.equals(this.v2, that.v2);
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.v1 == null ? 0 : this.v1.hashCode());
        result = 31 * result + (this.v2 == null ? 0 : this.v2.hashCode());
        return result;
    }

    public String toString() {
        return "(" + this.v1 + ", " + this.v2 + ")";
    }

    public Tuple2<T1, T2> clone() {
        return new Tuple2<T1, T2>(this);
    }
}

