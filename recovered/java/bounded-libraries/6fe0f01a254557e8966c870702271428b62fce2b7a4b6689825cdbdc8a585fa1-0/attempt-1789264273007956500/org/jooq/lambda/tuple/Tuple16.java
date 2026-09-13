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
import org.jooq.lambda.function.Function16;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple0;
import org.jooq.lambda.tuple.Tuple1;
import org.jooq.lambda.tuple.Tuple10;
import org.jooq.lambda.tuple.Tuple11;
import org.jooq.lambda.tuple.Tuple12;
import org.jooq.lambda.tuple.Tuple13;
import org.jooq.lambda.tuple.Tuple14;
import org.jooq.lambda.tuple.Tuple15;
import org.jooq.lambda.tuple.Tuple2;
import org.jooq.lambda.tuple.Tuple3;
import org.jooq.lambda.tuple.Tuple4;
import org.jooq.lambda.tuple.Tuple5;
import org.jooq.lambda.tuple.Tuple6;
import org.jooq.lambda.tuple.Tuple7;
import org.jooq.lambda.tuple.Tuple8;
import org.jooq.lambda.tuple.Tuple9;
import org.jooq.lambda.tuple.Tuples;

public class Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>
implements Tuple,
Comparable<Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>>,
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
    public final T12 v12;
    public final T13 v13;
    public final T14 v14;
    public final T15 v15;
    public final T16 v16;

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

    public T12 v12() {
        return this.v12;
    }

    public T13 v13() {
        return this.v13;
    }

    public T14 v14() {
        return this.v14;
    }

    public T15 v15() {
        return this.v15;
    }

    public T16 v16() {
        return this.v16;
    }

    public Tuple16(Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> tuple) {
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
        this.v12 = tuple.v12;
        this.v13 = tuple.v13;
        this.v14 = tuple.v14;
        this.v15 = tuple.v15;
        this.v16 = tuple.v16;
    }

    public Tuple16(T1 v1, T2 v2, T3 v3, T4 v4, T5 v5, T6 v6, T7 v7, T8 v8, T9 v9, T10 v10, T11 v11, T12 v12, T13 v13, T14 v14, T15 v15, T16 v16) {
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
        this.v12 = v12;
        this.v13 = v13;
        this.v14 = v14;
        this.v15 = v15;
        this.v16 = v16;
    }

    public final Tuple2<Tuple0, Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> split0() {
        return new Tuple2<Tuple0, Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>>(this.limit0(), this.skip0());
    }

    public final Tuple2<Tuple1<T1>, Tuple15<T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> split1() {
        return new Tuple2<Tuple1<T1>, Tuple15<T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>>(this.limit1(), this.skip1());
    }

    public final Tuple2<Tuple2<T1, T2>, Tuple14<T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> split2() {
        return new Tuple2<Tuple2<T1, T2>, Tuple14<T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>>(this.limit2(), this.skip2());
    }

    public final Tuple2<Tuple3<T1, T2, T3>, Tuple13<T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> split3() {
        return new Tuple2<Tuple3<T1, T2, T3>, Tuple13<T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>>(this.limit3(), this.skip3());
    }

    public final Tuple2<Tuple4<T1, T2, T3, T4>, Tuple12<T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> split4() {
        return new Tuple2<Tuple4<T1, T2, T3, T4>, Tuple12<T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>>(this.limit4(), this.skip4());
    }

    public final Tuple2<Tuple5<T1, T2, T3, T4, T5>, Tuple11<T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> split5() {
        return new Tuple2<Tuple5<T1, T2, T3, T4, T5>, Tuple11<T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>>(this.limit5(), this.skip5());
    }

    public final Tuple2<Tuple6<T1, T2, T3, T4, T5, T6>, Tuple10<T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> split6() {
        return new Tuple2<Tuple6<T1, T2, T3, T4, T5, T6>, Tuple10<T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>>(this.limit6(), this.skip6());
    }

    public final Tuple2<Tuple7<T1, T2, T3, T4, T5, T6, T7>, Tuple9<T8, T9, T10, T11, T12, T13, T14, T15, T16>> split7() {
        return new Tuple2<Tuple7<T1, T2, T3, T4, T5, T6, T7>, Tuple9<T8, T9, T10, T11, T12, T13, T14, T15, T16>>(this.limit7(), this.skip7());
    }

    public final Tuple2<Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>, Tuple8<T9, T10, T11, T12, T13, T14, T15, T16>> split8() {
        return new Tuple2<Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>, Tuple8<T9, T10, T11, T12, T13, T14, T15, T16>>(this.limit8(), this.skip8());
    }

    public final Tuple2<Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>, Tuple7<T10, T11, T12, T13, T14, T15, T16>> split9() {
        return new Tuple2<Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>, Tuple7<T10, T11, T12, T13, T14, T15, T16>>(this.limit9(), this.skip9());
    }

    public final Tuple2<Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>, Tuple6<T11, T12, T13, T14, T15, T16>> split10() {
        return new Tuple2<Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>, Tuple6<T11, T12, T13, T14, T15, T16>>(this.limit10(), this.skip10());
    }

    public final Tuple2<Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>, Tuple5<T12, T13, T14, T15, T16>> split11() {
        return new Tuple2<Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>, Tuple5<T12, T13, T14, T15, T16>>(this.limit11(), this.skip11());
    }

    public final Tuple2<Tuple12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>, Tuple4<T13, T14, T15, T16>> split12() {
        return new Tuple2<Tuple12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>, Tuple4<T13, T14, T15, T16>>(this.limit12(), this.skip12());
    }

    public final Tuple2<Tuple13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>, Tuple3<T14, T15, T16>> split13() {
        return new Tuple2<Tuple13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>, Tuple3<T14, T15, T16>>(this.limit13(), this.skip13());
    }

    public final Tuple2<Tuple14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>, Tuple2<T15, T16>> split14() {
        return new Tuple2<Tuple14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>, Tuple2<T15, T16>>(this.limit14(), this.skip14());
    }

    public final Tuple2<Tuple15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>, Tuple1<T16>> split15() {
        return new Tuple2<Tuple15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>, Tuple1<T16>>(this.limit15(), this.skip15());
    }

    public final Tuple2<Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>, Tuple0> split16() {
        return new Tuple2<Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>, Tuple0>(this.limit16(), this.skip16());
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
        return new Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>(this.v1, this.v2, this.v3, this.v4, this.v5, this.v6, this.v7, this.v8, this.v9, this.v10, this.v11);
    }

    public final Tuple12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> limit12() {
        return new Tuple12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>(this.v1, this.v2, this.v3, this.v4, this.v5, this.v6, this.v7, this.v8, this.v9, this.v10, this.v11, this.v12);
    }

    public final Tuple13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> limit13() {
        return new Tuple13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>(this.v1, this.v2, this.v3, this.v4, this.v5, this.v6, this.v7, this.v8, this.v9, this.v10, this.v11, this.v12, this.v13);
    }

    public final Tuple14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> limit14() {
        return new Tuple14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>(this.v1, this.v2, this.v3, this.v4, this.v5, this.v6, this.v7, this.v8, this.v9, this.v10, this.v11, this.v12, this.v13, this.v14);
    }

    public final Tuple15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> limit15() {
        return new Tuple15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>(this.v1, this.v2, this.v3, this.v4, this.v5, this.v6, this.v7, this.v8, this.v9, this.v10, this.v11, this.v12, this.v13, this.v14, this.v15);
    }

    public final Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> limit16() {
        return this;
    }

    public final Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> skip0() {
        return this;
    }

    public final Tuple15<T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> skip1() {
        return new Tuple15<T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>(this.v2, this.v3, this.v4, this.v5, this.v6, this.v7, this.v8, this.v9, this.v10, this.v11, this.v12, this.v13, this.v14, this.v15, this.v16);
    }

    public final Tuple14<T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> skip2() {
        return new Tuple14<T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>(this.v3, this.v4, this.v5, this.v6, this.v7, this.v8, this.v9, this.v10, this.v11, this.v12, this.v13, this.v14, this.v15, this.v16);
    }

    public final Tuple13<T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> skip3() {
        return new Tuple13<T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>(this.v4, this.v5, this.v6, this.v7, this.v8, this.v9, this.v10, this.v11, this.v12, this.v13, this.v14, this.v15, this.v16);
    }

    public final Tuple12<T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> skip4() {
        return new Tuple12<T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>(this.v5, this.v6, this.v7, this.v8, this.v9, this.v10, this.v11, this.v12, this.v13, this.v14, this.v15, this.v16);
    }

    public final Tuple11<T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> skip5() {
        return new Tuple11<T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>(this.v6, this.v7, this.v8, this.v9, this.v10, this.v11, this.v12, this.v13, this.v14, this.v15, this.v16);
    }

    public final Tuple10<T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> skip6() {
        return new Tuple10<T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>(this.v7, this.v8, this.v9, this.v10, this.v11, this.v12, this.v13, this.v14, this.v15, this.v16);
    }

    public final Tuple9<T8, T9, T10, T11, T12, T13, T14, T15, T16> skip7() {
        return new Tuple9<T8, T9, T10, T11, T12, T13, T14, T15, T16>(this.v8, this.v9, this.v10, this.v11, this.v12, this.v13, this.v14, this.v15, this.v16);
    }

    public final Tuple8<T9, T10, T11, T12, T13, T14, T15, T16> skip8() {
        return new Tuple8<T9, T10, T11, T12, T13, T14, T15, T16>(this.v9, this.v10, this.v11, this.v12, this.v13, this.v14, this.v15, this.v16);
    }

    public final Tuple7<T10, T11, T12, T13, T14, T15, T16> skip9() {
        return new Tuple7<T10, T11, T12, T13, T14, T15, T16>(this.v10, this.v11, this.v12, this.v13, this.v14, this.v15, this.v16);
    }

    public final Tuple6<T11, T12, T13, T14, T15, T16> skip10() {
        return new Tuple6<T11, T12, T13, T14, T15, T16>(this.v11, this.v12, this.v13, this.v14, this.v15, this.v16);
    }

    public final Tuple5<T12, T13, T14, T15, T16> skip11() {
        return new Tuple5<T12, T13, T14, T15, T16>(this.v12, this.v13, this.v14, this.v15, this.v16);
    }

    public final Tuple4<T13, T14, T15, T16> skip12() {
        return new Tuple4<T13, T14, T15, T16>(this.v13, this.v14, this.v15, this.v16);
    }

    public final Tuple3<T14, T15, T16> skip13() {
        return new Tuple3<T14, T15, T16>(this.v14, this.v15, this.v16);
    }

    public final Tuple2<T15, T16> skip14() {
        return new Tuple2<T15, T16>(this.v15, this.v16);
    }

    public final Tuple1<T16> skip15() {
        return new Tuple1<T16>(this.v16);
    }

    public final Tuple0 skip16() {
        return new Tuple0();
    }

    public final <R> R map(Function16<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? super T14, ? super T15, ? super T16, ? extends R> function) {
        return function.apply(this.v1, this.v2, this.v3, this.v4, this.v5, this.v6, this.v7, this.v8, this.v9, this.v10, this.v11, this.v12, this.v13, this.v14, this.v15, this.v16);
    }

    public final <U1> Tuple16<U1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> map1(Function<? super T1, ? extends U1> function) {
        return Tuple.tuple(function.apply(this.v1), this.v2, this.v3, this.v4, this.v5, this.v6, this.v7, this.v8, this.v9, this.v10, this.v11, this.v12, this.v13, this.v14, this.v15, this.v16);
    }

    public final <U2> Tuple16<T1, U2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> map2(Function<? super T2, ? extends U2> function) {
        return Tuple.tuple(this.v1, function.apply(this.v2), this.v3, this.v4, this.v5, this.v6, this.v7, this.v8, this.v9, this.v10, this.v11, this.v12, this.v13, this.v14, this.v15, this.v16);
    }

    public final <U3> Tuple16<T1, T2, U3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> map3(Function<? super T3, ? extends U3> function) {
        return Tuple.tuple(this.v1, this.v2, function.apply(this.v3), this.v4, this.v5, this.v6, this.v7, this.v8, this.v9, this.v10, this.v11, this.v12, this.v13, this.v14, this.v15, this.v16);
    }

    public final <U4> Tuple16<T1, T2, T3, U4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> map4(Function<? super T4, ? extends U4> function) {
        return Tuple.tuple(this.v1, this.v2, this.v3, function.apply(this.v4), this.v5, this.v6, this.v7, this.v8, this.v9, this.v10, this.v11, this.v12, this.v13, this.v14, this.v15, this.v16);
    }

    public final <U5> Tuple16<T1, T2, T3, T4, U5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> map5(Function<? super T5, ? extends U5> function) {
        return Tuple.tuple(this.v1, this.v2, this.v3, this.v4, function.apply(this.v5), this.v6, this.v7, this.v8, this.v9, this.v10, this.v11, this.v12, this.v13, this.v14, this.v15, this.v16);
    }

    public final <U6> Tuple16<T1, T2, T3, T4, T5, U6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> map6(Function<? super T6, ? extends U6> function) {
        return Tuple.tuple(this.v1, this.v2, this.v3, this.v4, this.v5, function.apply(this.v6), this.v7, this.v8, this.v9, this.v10, this.v11, this.v12, this.v13, this.v14, this.v15, this.v16);
    }

    public final <U7> Tuple16<T1, T2, T3, T4, T5, T6, U7, T8, T9, T10, T11, T12, T13, T14, T15, T16> map7(Function<? super T7, ? extends U7> function) {
        return Tuple.tuple(this.v1, this.v2, this.v3, this.v4, this.v5, this.v6, function.apply(this.v7), this.v8, this.v9, this.v10, this.v11, this.v12, this.v13, this.v14, this.v15, this.v16);
    }

    public final <U8> Tuple16<T1, T2, T3, T4, T5, T6, T7, U8, T9, T10, T11, T12, T13, T14, T15, T16> map8(Function<? super T8, ? extends U8> function) {
        return Tuple.tuple(this.v1, this.v2, this.v3, this.v4, this.v5, this.v6, this.v7, function.apply(this.v8), this.v9, this.v10, this.v11, this.v12, this.v13, this.v14, this.v15, this.v16);
    }

    public final <U9> Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, U9, T10, T11, T12, T13, T14, T15, T16> map9(Function<? super T9, ? extends U9> function) {
        return Tuple.tuple(this.v1, this.v2, this.v3, this.v4, this.v5, this.v6, this.v7, this.v8, function.apply(this.v9), this.v10, this.v11, this.v12, this.v13, this.v14, this.v15, this.v16);
    }

    public final <U10> Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, U10, T11, T12, T13, T14, T15, T16> map10(Function<? super T10, ? extends U10> function) {
        return Tuple.tuple(this.v1, this.v2, this.v3, this.v4, this.v5, this.v6, this.v7, this.v8, this.v9, function.apply(this.v10), this.v11, this.v12, this.v13, this.v14, this.v15, this.v16);
    }

    public final <U11> Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, U11, T12, T13, T14, T15, T16> map11(Function<? super T11, ? extends U11> function) {
        return Tuple.tuple(this.v1, this.v2, this.v3, this.v4, this.v5, this.v6, this.v7, this.v8, this.v9, this.v10, function.apply(this.v11), this.v12, this.v13, this.v14, this.v15, this.v16);
    }

    public final <U12> Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, U12, T13, T14, T15, T16> map12(Function<? super T12, ? extends U12> function) {
        return Tuple.tuple(this.v1, this.v2, this.v3, this.v4, this.v5, this.v6, this.v7, this.v8, this.v9, this.v10, this.v11, function.apply(this.v12), this.v13, this.v14, this.v15, this.v16);
    }

    public final <U13> Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, U13, T14, T15, T16> map13(Function<? super T13, ? extends U13> function) {
        return Tuple.tuple(this.v1, this.v2, this.v3, this.v4, this.v5, this.v6, this.v7, this.v8, this.v9, this.v10, this.v11, this.v12, function.apply(this.v13), this.v14, this.v15, this.v16);
    }

    public final <U14> Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, U14, T15, T16> map14(Function<? super T14, ? extends U14> function) {
        return Tuple.tuple(this.v1, this.v2, this.v3, this.v4, this.v5, this.v6, this.v7, this.v8, this.v9, this.v10, this.v11, this.v12, this.v13, function.apply(this.v14), this.v15, this.v16);
    }

    public final <U15> Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, U15, T16> map15(Function<? super T15, ? extends U15> function) {
        return Tuple.tuple(this.v1, this.v2, this.v3, this.v4, this.v5, this.v6, this.v7, this.v8, this.v9, this.v10, this.v11, this.v12, this.v13, this.v14, function.apply(this.v15), this.v16);
    }

    public final <U16> Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, U16> map16(Function<? super T16, ? extends U16> function) {
        return Tuple.tuple(this.v1, this.v2, this.v3, this.v4, this.v5, this.v6, this.v7, this.v8, this.v9, this.v10, this.v11, this.v12, this.v13, this.v14, this.v15, function.apply(this.v16));
    }

    @Override
    @Deprecated
    public final Object[] array() {
        return this.toArray();
    }

    @Override
    public final Object[] toArray() {
        return new Object[]{this.v1, this.v2, this.v3, this.v4, this.v5, this.v6, this.v7, this.v8, this.v9, this.v10, this.v11, this.v12, this.v13, this.v14, this.v15, this.v16};
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

    public final <K> Map<K, ?> toMap(Supplier<? extends K> keySupplier1, Supplier<? extends K> keySupplier2, Supplier<? extends K> keySupplier3, Supplier<? extends K> keySupplier4, Supplier<? extends K> keySupplier5, Supplier<? extends K> keySupplier6, Supplier<? extends K> keySupplier7, Supplier<? extends K> keySupplier8, Supplier<? extends K> keySupplier9, Supplier<? extends K> keySupplier10, Supplier<? extends K> keySupplier11, Supplier<? extends K> keySupplier12, Supplier<? extends K> keySupplier13, Supplier<? extends K> keySupplier14, Supplier<? extends K> keySupplier15, Supplier<? extends K> keySupplier16) {
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
        result.put(keySupplier12.get(), this.v12);
        result.put(keySupplier13.get(), this.v13);
        result.put(keySupplier14.get(), this.v14);
        result.put(keySupplier15.get(), this.v15);
        result.put(keySupplier16.get(), this.v16);
        return result;
    }

    public final <K> Map<K, ?> toMap(K key1, K key2, K key3, K key4, K key5, K key6, K key7, K key8, K key9, K key10, K key11, K key12, K key13, K key14, K key15, K key16) {
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
        result.put(key12, this.v12);
        result.put(key13, this.v13);
        result.put(key14, this.v14);
        result.put(key15, this.v15);
        result.put(key16, this.v16);
        return result;
    }

    @Override
    public final int degree() {
        return 16;
    }

    @Override
    public final Iterator<Object> iterator() {
        return this.list().iterator();
    }

    @Override
    public int compareTo(Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> other) {
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
        result = Tuples.compare(this.v12, other.v12);
        if (result != 0) {
            return result;
        }
        result = Tuples.compare(this.v13, other.v13);
        if (result != 0) {
            return result;
        }
        result = Tuples.compare(this.v14, other.v14);
        if (result != 0) {
            return result;
        }
        result = Tuples.compare(this.v15, other.v15);
        if (result != 0) {
            return result;
        }
        result = Tuples.compare(this.v16, other.v16);
        if (result != 0) {
            return result;
        }
        return result;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tuple16)) {
            return false;
        }
        Tuple16 that = (Tuple16)o;
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
        if (!Objects.equals(this.v11, that.v11)) {
            return false;
        }
        if (!Objects.equals(this.v12, that.v12)) {
            return false;
        }
        if (!Objects.equals(this.v13, that.v13)) {
            return false;
        }
        if (!Objects.equals(this.v14, that.v14)) {
            return false;
        }
        if (!Objects.equals(this.v15, that.v15)) {
            return false;
        }
        return Objects.equals(this.v16, that.v16);
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
        result = 31 * result + (this.v12 == null ? 0 : this.v12.hashCode());
        result = 31 * result + (this.v13 == null ? 0 : this.v13.hashCode());
        result = 31 * result + (this.v14 == null ? 0 : this.v14.hashCode());
        result = 31 * result + (this.v15 == null ? 0 : this.v15.hashCode());
        result = 31 * result + (this.v16 == null ? 0 : this.v16.hashCode());
        return result;
    }

    public String toString() {
        return "(" + this.v1 + ", " + this.v2 + ", " + this.v3 + ", " + this.v4 + ", " + this.v5 + ", " + this.v6 + ", " + this.v7 + ", " + this.v8 + ", " + this.v9 + ", " + this.v10 + ", " + this.v11 + ", " + this.v12 + ", " + this.v13 + ", " + this.v14 + ", " + this.v15 + ", " + this.v16 + ")";
    }

    public Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> clone() {
        return new Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>(this);
    }
}

