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
import org.jooq.lambda.function.Function11;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple0;
import org.jooq.lambda.tuple.Tuple1;
import org.jooq.lambda.tuple.Tuple10;
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

public class Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>
implements Tuple,
Comparable<Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>>,
Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    public final T1 v1;
    public final T2 v2;
    public final T3 v3;
    public final T4 v4;
    public final T5 v5;
    public final T6 v6;
    public final T7 v7;
    public final T8 v8;
    public final T9 v9;
    public final T10 v10;
    public final T11 v11;

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

    public T7 v7() {
        return this.v7;
    }

    public T8 v8() {
        return this.v8;
    }

    public T9 v9() {
        return this.v9;
    }

    public T10 v10() {
        return this.v10;
    }

    public T11 v11() {
        return this.v11;
    }

    public Tuple11(Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> tuple) {
        this.v1 = tuple.v1;
        this.v2 = tuple.v2;
        this.v3 = tuple.v3;
        this.v4 = tuple.v4;
        this.v5 = tuple.v5;
        this.v6 = tuple.v6;
        this.v7 = tuple.v7;
        this.v8 = tuple.v8;
        this.v9 = tuple.v9;
        this.v10 = tuple.v10;
        this.v11 = tuple.v11;
    }

    public Tuple11(T1 v1, T2 v2, T3 v3, T4 v4, T5 v5, T6 v6, T7 v7, T8 v8, T9 v9, T10 v10, T11 v11) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        this.v4 = v4;
        this.v5 = v5;
        this.v6 = v6;
        this.v7 = v7;
        this.v8 = v8;
        this.v9 = v9;
        this.v10 = v10;
        this.v11 = v11;
    }

    public final <T12> Tuple12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> concat(T12 value) {
        return new Tuple12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>(this.v1, this.v2, this.v3, this.v4, this.v5, this.v6, this.v7, this.v8, this.v9, this.v10, this.v11, value);
    }

    public final <T12> Tuple12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> concat(Tuple1<T12> tuple) {
        return new Tuple12(this.v1, this.v2, this.v3, this.v4, this.v5, this.v6, this.v7, this.v8, this.v9, this.v10, this.v11, tuple.v1);
    }

    public final <T12, T13> Tuple13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> concat(Tuple2<T12, T13> tuple) {
        return new Tuple13(this.v1, this.v2, this.v3, this.v4, this.v5, this.v6, this.v7, this.v8, this.v9, this.v10, this.v11, tuple.v1, tuple.v2);
    }

    public final <T12, T13, T14> Tuple14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> concat(Tuple3<T12, T13, T14> tuple) {
        return new Tuple14(this.v1, this.v2, this.v3, this.v4, this.v5, this.v6, this.v7, this.v8, this.v9, this.v10, this.v11, tuple.v1, tuple.v2, tuple.v3);
    }

    public final <T12, T13, T14, T15> Tuple15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> concat(Tuple4<T12, T13, T14, T15> tuple) {
        return new Tuple15(this.v1, this.v2, this.v3, this.v4, this.v5, this.v6, this.v7, this.v8, this.v9, this.v10, this.v11, tuple.v1, tuple.v2, tuple.v3, tuple.v4);
    }

    public final <T12, T13, T14, T15, T16> Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> concat(Tuple5<T12, T13, T14, T15, T16> tuple) {
        return new Tuple16(this.v1, this.v2, this.v3, this.v4, this.v5, this.v6, this.v7, this.v8, this.v9, this.v10, this.v11, tuple.v1, tuple.v2, tuple.v3, tuple.v4, tuple.v5);
    }

    public final Tuple2<Tuple0, Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> split0() {
        return new Tuple2<Tuple0, Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>>(this.limit0(), this.skip0());
    }

    public final Tuple2<Tuple1<T1>, Tuple10<T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> split1() {
        return new Tuple2<Tuple1<T1>, Tuple10<T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>>(this.limit1(), this.skip1());
    }

    public final Tuple2<Tuple2<T1, T2>, Tuple9<T3, T4, T5, T6, T7, T8, T9, T10, T11>> split2() {
        return new Tuple2<Tuple2<T1, T2>, Tuple9<T3, T4, T5, T6, T7, T8, T9, T10, T11>>(this.limit2(), this.skip2());
    }

    public final Tuple2<Tuple3<T1, T2, T3>, Tuple8<T4, T5, T6, T7, T8, T9, T10, T11>> split3() {
        return new Tuple2<Tuple3<T1, T2, T3>, Tuple8<T4, T5, T6, T7, T8, T9, T10, T11>>(this.limit3(), this.skip3());
    }

    public final Tuple2<Tuple4<T1, T2, T3, T4>, Tuple7<T5, T6, T7, T8, T9, T10, T11>> split4() {
        return new Tuple2<Tuple4<T1, T2, T3, T4>, Tuple7<T5, T6, T7, T8, T9, T10, T11>>(this.limit4(), this.skip4());
    }

    public final Tuple2<Tuple5<T1, T2, T3, T4, T5>, Tuple6<T6, T7, T8, T9, T10, T11>> split5() {
        return new Tuple2<Tuple5<T1, T2, T3, T4, T5>, Tuple6<T6, T7, T8, T9, T10, T11>>(this.limit5(), this.skip5());
    }

    public final Tuple2<Tuple6<T1, T2, T3, T4, T5, T6>, Tuple5<T7, T8, T9, T10, T11>> split6() {
        return new Tuple2<Tuple6<T1, T2, T3, T4, T5, T6>, Tuple5<T7, T8, T9, T10, T11>>(this.limit6(), this.skip6());
    }

    public final Tuple2<Tuple7<T1, T2, T3, T4, T5, T6, T7>, Tuple4<T8, T9, T10, T11>> split7() {
        return new Tuple2<Tuple7<T1, T2, T3, T4, T5, T6, T7>, Tuple4<T8, T9, T10, T11>>(this.limit7(), this.skip7());
    }

    public final Tuple2<Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>, Tuple3<T9, T10, T11>> split8() {
        return new Tuple2<Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>, Tuple3<T9, T10, T11>>(this.limit8(), this.skip8());
    }

    public final Tuple2<Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>, Tuple2<T10, T11>> split9() {
        return new Tuple2<Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>, Tuple2<T10, T11>>(this.limit9(), this.skip9());
    }

    public final Tuple2<Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>, Tuple1<T11>> split10() {
        return new Tuple2<Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>, Tuple1<T11>>(this.limit10(), this.skip10());
    }

    public final Tuple2<Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>, Tuple0> split11() {
        return new Tuple2<Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>, Tuple0>(this.limit11(), this.skip11());
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
        return new Tuple6<T1, T2, T3, T4, T5, T6>(this.v1, this.v2, this.v3, this.v4, this.v5, this.v6);
    }

    public final Tuple7<T1, T2, T3, T4, T5, T6, T7> limit7() {
        return new Tuple7<T1, T2, T3, T4, T5, T6, T7>(this.v1, this.v2, this.v3, this.v4, this.v5, this.v6, this.v7);
    }

    public final Tuple8<T1, T2, T3, T4, T5, T6, T7, T8> limit8() {
        return new Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>(this.v1, this.v2, this.v3, this.v4, this.v5, this.v6, this.v7, this.v8);
    }

    public final Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9> limit9() {
        return new Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>(this.v1, this.v2, this.v3, this.v4, this.v5, this.v6, this.v7, this.v8, this.v9);
    }

    public final Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> limit10() {
        return new Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>(this.v1, this.v2, this.v3, this.v4, this.v5, this.v6, this.v7, this.v8, this.v9, this.v10);
    }

    public final Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> limit11() {
        return this;
    }

    public final Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> skip0() {
        return this;
    }

    public final Tuple10<T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> skip1() {
        return new Tuple10<T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>(this.v2, this.v3, this.v4, this.v5, this.v6, this.v7, this.v8, this.v9, this.v10, this.v11);
    }

    public final Tuple9<T3, T4, T5, T6, T7, T8, T9, T10, T11> skip2() {
        return new Tuple9<T3, T4, T5, T6, T7, T8, T9, T10, T11>(this.v3, this.v4, this.v5, this.v6, this.v7, this.v8, this.v9, this.v10, this.v11);
    }

    public final Tuple8<T4, T5, T6, T7, T8, T9, T10, T11> skip3() {
        return new Tuple8<T4, T5, T6, T7, T8, T9, T10, T11>(this.v4, this.v5, this.v6, this.v7, this.v8, this.v9, this.v10, this.v11);
    }

    public final Tuple7<T5, T6, T7, T8, T9, T10, T11> skip4() {
        return new Tuple7<T5, T6, T7, T8, T9, T10, T11>(this.v5, this.v6, this.v7, this.v8, this.v9, this.v10, this.v11);
    }

    public final Tuple6<T6, T7, T8, T9, T10, T11> skip5() {
        return new Tuple6<T6, T7, T8, T9, T10, T11>(this.v6, this.v7, this.v8, this.v9, this.v10, this.v11);
    }

    public final Tuple5<T7, T8, T9, T10, T11> skip6() {
        return new Tuple5<T7, T8, T9, T10, T11>(this.v7, this.v8, this.v9, this.v10, this.v11);
    }

    public final Tuple4<T8, T9, T10, T11> skip7() {
        return new Tuple4<T8, T9, T10, T11>(this.v8, this.v9, this.v10, this.v11);
    }

    public final Tuple3<T9, T10, T11> skip8() {
        return new Tuple3<T9, T10, T11>(this.v9, this.v10, this.v11);
    }

    public final Tuple2<T10, T11> skip9() {
        return new Tuple2<T10, T11>(this.v10, this.v11);
    }

    public final Tuple1<T11> skip10() {
        return new Tuple1<T11>(this.v11);
    }

    public final Tuple0 skip11() {
        return new Tuple0();
    }

    public final <R> R map(Function11<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? extends R> function) {
        return function.apply(this.v1, this.v2, this.v3, this.v4, this.v5, this.v6, this.v7, this.v8, this.v9, this.v10, this.v11);
    }

    public final <U1> Tuple11<U1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> map1(Function<? super T1, ? extends U1> function) {
        return Tuple.tuple(function.apply(this.v1), this.v2, this.v3, this.v4, this.v5, this.v6, this.v7, this.v8, this.v9, this.v10, this.v11);
    }

    public final <U2> Tuple11<T1, U2, T3, T4, T5, T6, T7, T8, T9, T10, T11> map2(Function<? super T2, ? extends U2> function) {
        return Tuple.tuple(this.v1, function.apply(this.v2), this.v3, this.v4, this.v5, this.v6, this.v7, this.v8, this.v9, this.v10, this.v11);
    }

    public final <U3> Tuple11<T1, T2, U3, T4, T5, T6, T7, T8, T9, T10, T11> map3(Function<? super T3, ? extends U3> function) {
        return Tuple.tuple(this.v1, this.v2, function.apply(this.v3), this.v4, this.v5, this.v6, this.v7, this.v8, this.v9, this.v10, this.v11);
    }

    public final <U4> Tuple11<T1, T2, T3, U4, T5, T6, T7, T8, T9, T10, T11> map4(Function<? super T4, ? extends U4> function) {
        return Tuple.tuple(this.v1, this.v2, this.v3, function.apply(this.v4), this.v5, this.v6, this.v7, this.v8, this.v9, this.v10, this.v11);
    }

    public final <U5> Tuple11<T1, T2, T3, T4, U5, T6, T7, T8, T9, T10, T11> map5(Function<? super T5, ? extends U5> function) {
        return Tuple.tuple(this.v1, this.v2, this.v3, this.v4, function.apply(this.v5), this.v6, this.v7, this.v8, this.v9, this.v10, this.v11);
    }

    public final <U6> Tuple11<T1, T2, T3, T4, T5, U6, T7, T8, T9, T10, T11> map6(Function<? super T6, ? extends U6> function) {
        return Tuple.tuple(this.v1, this.v2, this.v3, this.v4, this.v5, function.apply(this.v6), this.v7, this.v8, this.v9, this.v10, this.v11);
    }

    public final <U7> Tuple11<T1, T2, T3, T4, T5, T6, U7, T8, T9, T10, T11> map7(Function<? super T7, ? extends U7> function) {
        return Tuple.tuple(this.v1, this.v2, this.v3, this.v4, this.v5, this.v6, function.apply(this.v7), this.v8, this.v9, this.v10, this.v11);
    }

    public final <U8> Tuple11<T1, T2, T3, T4, T5, T6, T7, U8, T9, T10, T11> map8(Function<? super T8, ? extends U8> function) {
        return Tuple.tuple(this.v1, this.v2, this.v3, this.v4, this.v5, this.v6, this.v7, function.apply(this.v8), this.v9, this.v10, this.v11);
    }

    public final <U9> Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, U9, T10, T11> map9(Function<? super T9, ? extends U9> function) {
        return Tuple.tuple(this.v1, this.v2, this.v3, this.v4, this.v5, this.v6, this.v7, this.v8, function.apply(this.v9), this.v10, this.v11);
    }

    public final <U10> Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, U10, T11> map10(Function<? super T10, ? extends U10> function) {
        return Tuple.tuple(this.v1, this.v2, this.v3, this.v4, this.v5, this.v6, this.v7, this.v8, this.v9, function.apply(this.v10), this.v11);
    }

    public final <U11> Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, U11> map11(Function<? super T11, ? extends U11> function) {
        return Tuple.tuple(this.v1, this.v2, this.v3, this.v4, this.v5, this.v6, this.v7, this.v8, this.v9, this.v10, function.apply(this.v11));
    }

    @Override
    @Deprecated
    public final Object[] array() {
        return this.toArray();
    }

    @Override
    public final Object[] toArray() {
        return new Object[]{this.v1, this.v2, this.v3, this.v4, this.v5, this.v6, this.v7, this.v8, this.v9, this.v10, this.v11};
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

    public final <K> Map<K, ?> toMap(Supplier<? extends K> keySupplier1, Supplier<? extends K> keySupplier2, Supplier<? extends K> keySupplier3, Supplier<? extends K> keySupplier4, Supplier<? extends K> keySupplier5, Supplier<? extends K> keySupplier6, Supplier<? extends K> keySupplier7, Supplier<? extends K> keySupplier8, Supplier<? extends K> keySupplier9, Supplier<? extends K> keySupplier10, Supplier<? extends K> keySupplier11) {
        LinkedHashMap<K, Object> result = new LinkedHashMap<K, Object>();
        result.put(keySupplier1.get(), this.v1);
        result.put(keySupplier2.get(), this.v2);
        result.put(keySupplier3.get(), this.v3);
        result.put(keySupplier4.get(), this.v4);
        result.put(keySupplier5.get(), this.v5);
        result.put(keySupplier6.get(), this.v6);
        result.put(keySupplier7.get(), this.v7);
        result.put(keySupplier8.get(), this.v8);
        result.put(keySupplier9.get(), this.v9);
        result.put(keySupplier10.get(), this.v10);
        result.put(keySupplier11.get(), this.v11);
        return result;
    }

    public final <K> Map<K, ?> toMap(K key1, K key2, K key3, K key4, K key5, K key6, K key7, K key8, K key9, K key10, K key11) {
        LinkedHashMap<K, Object> result = new LinkedHashMap<K, Object>();
        result.put(key1, this.v1);
        result.put(key2, this.v2);
        result.put(key3, this.v3);
        result.put(key4, this.v4);
        result.put(key5, this.v5);
        result.put(key6, this.v6);
        result.put(key7, this.v7);
        result.put(key8, this.v8);
        result.put(key9, this.v9);
        result.put(key10, this.v10);
        result.put(key11, this.v11);
        return result;
    }

    @Override
    public final int degree() {
        return 11;
    }

    @Override
    public final Iterator<Object> iterator() {
        return this.list().iterator();
    }

    @Override
    public int compareTo(Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> other) {
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
        result = Tuples.compare(this.v7, other.v7);
        if (result != 0) {
            return result;
        }
        result = Tuples.compare(this.v8, other.v8);
        if (result != 0) {
            return result;
        }
        result = Tuples.compare(this.v9, other.v9);
        if (result != 0) {
            return result;
        }
        result = Tuples.compare(this.v10, other.v10);
        if (result != 0) {
            return result;
        }
        result = Tuples.compare(this.v11, other.v11);
        if (result != 0) {
            return result;
        }
        return result;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tuple11)) {
            return false;
        }
        Tuple11 that = (Tuple11)o;
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
        if (!Objects.equals(this.v6, that.v6)) {
            return false;
        }
        if (!Objects.equals(this.v7, that.v7)) {
            return false;
        }
        if (!Objects.equals(this.v8, that.v8)) {
            return false;
        }
        if (!Objects.equals(this.v9, that.v9)) {
            return false;
        }
        if (!Objects.equals(this.v10, that.v10)) {
            return false;
        }
        return Objects.equals(this.v11, that.v11);
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
        result = 31 * result + (this.v7 == null ? 0 : this.v7.hashCode());
        result = 31 * result + (this.v8 == null ? 0 : this.v8.hashCode());
        result = 31 * result + (this.v9 == null ? 0 : this.v9.hashCode());
        result = 31 * result + (this.v10 == null ? 0 : this.v10.hashCode());
        result = 31 * result + (this.v11 == null ? 0 : this.v11.hashCode());
        return result;
    }

    public String toString() {
        return "(" + this.v1 + ", " + this.v2 + ", " + this.v3 + ", " + this.v4 + ", " + this.v5 + ", " + this.v6 + ", " + this.v7 + ", " + this.v8 + ", " + this.v9 + ", " + this.v10 + ", " + this.v11 + ")";
    }

    public Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> clone() {
        return new Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>(this);
    }
}

