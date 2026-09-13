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
import java.util.function.Supplier;
import org.jooq.lambda.Seq;
import org.jooq.lambda.function.Function6;
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
import org.jooq.lambda.tuple.Tuple2;
import org.jooq.lambda.tuple.Tuple3;
import org.jooq.lambda.tuple.Tuple4;
import org.jooq.lambda.tuple.Tuple5;
import org.jooq.lambda.tuple.Tuple7;
import org.jooq.lambda.tuple.Tuple8;
import org.jooq.lambda.tuple.Tuple9;
import org.jooq.lambda.tuple.Tuples;

public class Tuple6<T1, T2, T3, T4, T5, T6>
implements Tuple,
Comparable<Tuple6<T1, T2, T3, T4, T5, T6>>,
Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    public final T1 v1;
    public final T2 v2;
    public final T3 v3;
    public final T4 v4;
    public final T5 v5;
    public final T6 v6;

    public T1 v1() {
        return this.v1;
    }

    public T2 v2() {
        return this.v2;
    }

    public T3 v3() {
        return this.v3;
    }

    public T4 v4() {
        return this.v4;
    }

    public T5 v5() {
        return this.v5;
    }

    public T6 v6() {
        return this.v6;
    }

    public Tuple6(Tuple6<T1, T2, T3, T4, T5, T6> tuple) {
        this.v1 = tuple.v1;
        this.v2 = tuple.v2;
        this.v3 = tuple.v3;
        this.v4 = tuple.v4;
        this.v5 = tuple.v5;
        this.v6 = tuple.v6;
    }

    public Tuple6(T1 v1, T2 v2, T3 v3, T4 v4, T5 v5, T6 v6) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        this.v4 = v4;
        this.v5 = v5;
        this.v6 = v6;
    }

    public final <T7> Tuple7<T1, T2, T3, T4, T5, T6, T7> concat(T7 value) {
        return new Tuple7<T1, T2, T3, T4, T5, T6, T7>(this.v1, this.v2, this.v3, this.v4, this.v5, this.v6, value);
    }

    public final <T7> Tuple7<T1, T2, T3, T4, T5, T6, T7> concat(Tuple1<T7> tuple) {
        return new Tuple7(this.v1, this.v2, this.v3, this.v4, this.v5, this.v6, tuple.v1);
    }

    public final <T7, T8> Tuple8<T1, T2, T3, T4, T5, T6, T7, T8> concat(Tuple2<T7, T8> tuple) {
        return new Tuple8(this.v1, this.v2, this.v3, this.v4, this.v5, this.v6, tuple.v1, tuple.v2);
    }

    public final <T7, T8, T9> Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9> concat(Tuple3<T7, T8, T9> tuple) {
        return new Tuple9(this.v1, this.v2, this.v3, this.v4, this.v5, this.v6, tuple.v1, tuple.v2, tuple.v3);
    }

    public final <T7, T8, T9, T10> Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> concat(Tuple4<T7, T8, T9, T10> tuple) {
        return new Tuple10(this.v1, this.v2, this.v3, this.v4, this.v5, this.v6, tuple.v1, tuple.v2, tuple.v3, tuple.v4);
    }

    public final <T7, T8, T9, T10, T11> Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> concat(Tuple5<T7, T8, T9, T10, T11> tuple) {
        return new Tuple11(this.v1, this.v2, this.v3, this.v4, this.v5, this.v6, tuple.v1, tuple.v2, tuple.v3, tuple.v4, tuple.v5);
    }

    public final <T7, T8, T9, T10, T11, T12> Tuple12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> concat(Tuple6<T7, T8, T9, T10, T11, T12> tuple) {
        return new Tuple12<T1, T2, T3, T4, T5, T6, T1, T2, T3, T4, T5, T6>(this.v1, this.v2, this.v3, this.v4, this.v5, this.v6, tuple.v1, tuple.v2, tuple.v3, tuple.v4, tuple.v5, tuple.v6);
    }

    public final <T7, T8, T9, T10, T11, T12, T13> Tuple13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> concat(Tuple7<T7, T8, T9, T10, T11, T12, T13> tuple) {
        return new Tuple13(this.v1, this.v2, this.v3, this.v4, this.v5, this.v6, tuple.v1, tuple.v2, tuple.v3, tuple.v4, tuple.v5, tuple.v6, tuple.v7);
    }

    public final <T7, T8, T9, T10, T11, T12, T13, T14> Tuple14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> concat(Tuple8<T7, T8, T9, T10, T11, T12, T13, T14> tuple) {
        return new Tuple14(this.v1, this.v2, this.v3, this.v4, this.v5, this.v6, tuple.v1, tuple.v2, tuple.v3, tuple.v4, tuple.v5, tuple.v6, tuple.v7, tuple.v8);
    }

    public final <T7, T8, T9, T10, T11, T12, T13, T14, T15> Tuple15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> concat(Tuple9<T7, T8, T9, T10, T11, T12, T13, T14, T15> tuple) {
        return new Tuple15(this.v1, this.v2, this.v3, this.v4, this.v5, this.v6, tuple.v1, tuple.v2, tuple.v3, tuple.v4, tuple.v5, tuple.v6, tuple.v7, tuple.v8, tuple.v9);
    }

    public final <T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> concat(Tuple10<T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> tuple) {
        return new Tuple16(this.v1, this.v2, this.v3, this.v4, this.v5, this.v6, tuple.v1, tuple.v2, tuple.v3, tuple.v4, tuple.v5, tuple.v6, tuple.v7, tuple.v8, tuple.v9, tuple.v10);
    }

    public final Tuple2<Tuple0, Tuple6<T1, T2, T3, T4, T5, T6>> split0() {
        return new Tuple2<Tuple0, Tuple6<T1, T2, T3, T4, T5, T6>>(this.limit0(), this.skip0());
    }

    public final Tuple2<Tuple1<T1>, Tuple5<T2, T3, T4, T5, T6>> split1() {
        return new Tuple2<Tuple1<T1>, Tuple5<T2, T3, T4, T5, T6>>(this.limit1(), this.skip1());
    }

    public final Tuple2<Tuple2<T1, T2>, Tuple4<T3, T4, T5, T6>> split2() {
        return new Tuple2<Tuple2<T1, T2>, Tuple4<T3, T4, T5, T6>>(this.limit2(), this.skip2());
    }

    public final Tuple2<Tuple3<T1, T2, T3>, Tuple3<T4, T5, T6>> split3() {
        return new Tuple2<Tuple3<T1, T2, T3>, Tuple3<T4, T5, T6>>(this.limit3(), this.skip3());
    }

    public final Tuple2<Tuple4<T1, T2, T3, T4>, Tuple2<T5, T6>> split4() {
        return new Tuple2<Tuple4<T1, T2, T3, T4>, Tuple2<T5, T6>>(this.limit4(), this.skip4());
    }

    public final Tuple2<Tuple5<T1, T2, T3, T4, T5>, Tuple1<T6>> split5() {
        return new Tuple2<Tuple5<T1, T2, T3, T4, T5>, Tuple1<T6>>(this.limit5(), this.skip5());
    }

    public final Tuple2<Tuple6<T1, T2, T3, T4, T5, T6>, Tuple0> split6() {
        return new Tuple2<Tuple6<T1, T2, T3, T4, T5, T6>, Tuple0>(this.limit6(), this.skip6());
    }

    public final Tuple0 limit0() {
        return new Tuple0();
    }

    public final Tuple1<T1> limit1() {
        return new Tuple1<T1>(this.v1);
    }

    public final Tuple2<T1, T2> limit2() {
        return new Tuple2<T1, T2>(this.v1, this.v2);
    }

    public final Tuple3<T1, T2, T3> limit3() {
        return new Tuple3<T1, T2, T3>(this.v1, this.v2, this.v3);
    }

    public final Tuple4<T1, T2, T3, T4> limit4() {
        return new Tuple4<T1, T2, T3, T4>(this.v1, this.v2, this.v3, this.v4);
    }

    public final Tuple5<T1, T2, T3, T4, T5> limit5() {
        return new Tuple5<T1, T2, T3, T4, T5>(this.v1, this.v2, this.v3, this.v4, this.v5);
    }

    public final Tuple6<T1, T2, T3, T4, T5, T6> limit6() {
        return this;
    }

    public final Tuple6<T1, T2, T3, T4, T5, T6> skip0() {
        return this;
    }

    public final Tuple5<T2, T3, T4, T5, T6> skip1() {
        return new Tuple5<T2, T3, T4, T5, T6>(this.v2, this.v3, this.v4, this.v5, this.v6);
    }

    public final Tuple4<T3, T4, T5, T6> skip2() {
        return new Tuple4<T3, T4, T5, T6>(this.v3, this.v4, this.v5, this.v6);
    }

    public final Tuple3<T4, T5, T6> skip3() {
        return new Tuple3<T4, T5, T6>(this.v4, this.v5, this.v6);
    }

    public final Tuple2<T5, T6> skip4() {
        return new Tuple2<T5, T6>(this.v5, this.v6);
    }

    public final Tuple1<T6> skip5() {
        return new Tuple1<T6>(this.v6);
    }

    public final Tuple0 skip6() {
        return new Tuple0();
    }

    public final <R> R map(Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? extends R> function) {
        return function.apply(this.v1, this.v2, this.v3, this.v4, this.v5, this.v6);
    }

    public final <U1> Tuple6<U1, T2, T3, T4, T5, T6> map1(Function<? super T1, ? extends U1> function) {
        return Tuple.tuple(function.apply(this.v1), this.v2, this.v3, this.v4, this.v5, this.v6);
    }

    public final <U2> Tuple6<T1, U2, T3, T4, T5, T6> map2(Function<? super T2, ? extends U2> function) {
        return Tuple.tuple(this.v1, function.apply(this.v2), this.v3, this.v4, this.v5, this.v6);
    }

    public final <U3> Tuple6<T1, T2, U3, T4, T5, T6> map3(Function<? super T3, ? extends U3> function) {
        return Tuple.tuple(this.v1, this.v2, function.apply(this.v3), this.v4, this.v5, this.v6);
    }

    public final <U4> Tuple6<T1, T2, T3, U4, T5, T6> map4(Function<? super T4, ? extends U4> function) {
        return Tuple.tuple(this.v1, this.v2, this.v3, function.apply(this.v4), this.v5, this.v6);
    }

    public final <U5> Tuple6<T1, T2, T3, T4, U5, T6> map5(Function<? super T5, ? extends U5> function) {
        return Tuple.tuple(this.v1, this.v2, this.v3, this.v4, function.apply(this.v5), this.v6);
    }

    public final <U6> Tuple6<T1, T2, T3, T4, T5, U6> map6(Function<? super T6, ? extends U6> function) {
        return Tuple.tuple(this.v1, this.v2, this.v3, this.v4, this.v5, function.apply(this.v6));
    }

    @Override
    @Deprecated
    public final Object[] array() {
        return this.toArray();
    }

    @Override
    public final Object[] toArray() {
        return new Object[]{this.v1, this.v2, this.v3, this.v4, this.v5, this.v6};
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

    public final <K> Map<K, ?> toMap(Supplier<? extends K> keySupplier1, Supplier<? extends K> keySupplier2, Supplier<? extends K> keySupplier3, Supplier<? extends K> keySupplier4, Supplier<? extends K> keySupplier5, Supplier<? extends K> keySupplier6) {
        LinkedHashMap<K, Object> result = new LinkedHashMap<K, Object>();
        result.put(keySupplier1.get(), this.v1);
        result.put(keySupplier2.get(), this.v2);
        result.put(keySupplier3.get(), this.v3);
        result.put(keySupplier4.get(), this.v4);
        result.put(keySupplier5.get(), this.v5);
        result.put(keySupplier6.get(), this.v6);
        return result;
    }

    public final <K> Map<K, ?> toMap(K key1, K key2, K key3, K key4, K key5, K key6) {
        LinkedHashMap<K, Object> result = new LinkedHashMap<K, Object>();
        result.put(key1, this.v1);
        result.put(key2, this.v2);
        result.put(key3, this.v3);
        result.put(key4, this.v4);
        result.put(key5, this.v5);
        result.put(key6, this.v6);
        return result;
    }

    @Override
    public final int degree() {
        return 6;
    }

    @Override
    public final Iterator<Object> iterator() {
        return this.list().iterator();
    }

    @Override
    public int compareTo(Tuple6<T1, T2, T3, T4, T5, T6> other) {
        int result = 0;
        result = Tuples.compare(this.v1, other.v1);
        if (result != 0) {
            return result;
        }
        result = Tuples.compare(this.v2, other.v2);
        if (result != 0) {
            return result;
        }
        result = Tuples.compare(this.v3, other.v3);
        if (result != 0) {
            return result;
        }
        result = Tuples.compare(this.v4, other.v4);
        if (result != 0) {
            return result;
        }
        result = Tuples.compare(this.v5, other.v5);
        if (result != 0) {
            return result;
        }
        result = Tuples.compare(this.v6, other.v6);
        if (result != 0) {
            return result;
        }
        return result;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tuple6)) {
            return false;
        }
        Tuple6 that = (Tuple6)o;
        if (!Objects.equals(this.v1, that.v1)) {
            return false;
        }
        if (!Objects.equals(this.v2, that.v2)) {
            return false;
        }
        if (!Objects.equals(this.v3, that.v3)) {
            return false;
        }
        if (!Objects.equals(this.v4, that.v4)) {
            return false;
        }
        if (!Objects.equals(this.v5, that.v5)) {
            return false;
        }
        return Objects.equals(this.v6, that.v6);
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.v1 == null ? 0 : this.v1.hashCode());
        result = 31 * result + (this.v2 == null ? 0 : this.v2.hashCode());
        result = 31 * result + (this.v3 == null ? 0 : this.v3.hashCode());
        result = 31 * result + (this.v4 == null ? 0 : this.v4.hashCode());
        result = 31 * result + (this.v5 == null ? 0 : this.v5.hashCode());
        result = 31 * result + (this.v6 == null ? 0 : this.v6.hashCode());
        return result;
    }

    public String toString() {
        return "(" + this.v1 + ", " + this.v2 + ", " + this.v3 + ", " + this.v4 + ", " + this.v5 + ", " + this.v6 + ")";
    }

    public Tuple6<T1, T2, T3, T4, T5, T6> clone() {
        return new Tuple6<T1, T2, T3, T4, T5, T6>(this);
    }
}

