/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda;

import java.util.concurrent.ForkJoinPool;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleSupplier;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongSupplier;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongToIntFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.ObjDoubleConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.ObjLongConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleBiFunction;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntBiFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongBiFunction;
import java.util.function.ToLongFunction;
import java.util.function.UnaryOperator;

public final class Blocking {
    public static Runnable runnable(Runnable runnable) {
        return () -> Blocking.supplier(() -> {
            runnable.run();
            return null;
        }).get();
    }

    public static <T, U> BiConsumer<T, U> biConsumer(BiConsumer<? super T, ? super U> biConsumer) {
        return (t, u) -> Blocking.runnable(() -> biConsumer.accept(t, u)).run();
    }

    public static <T, U, R> BiFunction<T, U, R> biFunction(BiFunction<? super T, ? super U, ? extends R> biFunction) {
        return (t, u) -> Blocking.supplier(() -> biFunction.apply(t, u)).get();
    }

    public static <T, U> BiPredicate<T, U> biPredicate(BiPredicate<? super T, ? super U> biPredicate) {
        return (t, u) -> Blocking.supplier(() -> biPredicate.test(t, u)).get();
    }

    public static <T> BinaryOperator<T> binaryOperator(BinaryOperator<T> binaryOperator) {
        return (t1, t2) -> Blocking.supplier(() -> binaryOperator.apply(t1, t2)).get();
    }

    public static BooleanSupplier booleanSupplier(BooleanSupplier booleanSupplier) {
        return () -> Blocking.supplier(() -> booleanSupplier.getAsBoolean()).get();
    }

    public static <T> Consumer<T> consumer(Consumer<? super T> consumer) {
        return t -> Blocking.runnable(() -> consumer.accept(t)).run();
    }

    public static DoubleBinaryOperator doubleBinaryOperator(DoubleBinaryOperator doubleBinaryOperator) {
        return (d1, d2) -> Blocking.supplier(() -> doubleBinaryOperator.applyAsDouble(d1, d2)).get();
    }

    public static DoubleConsumer doubleConsumer(DoubleConsumer doubleConsumer) {
        return d -> Blocking.runnable(() -> doubleConsumer.accept(d)).run();
    }

    public static <R> DoubleFunction<R> doubleFunction(DoubleFunction<? extends R> doubleFunction) {
        return d -> Blocking.supplier(() -> doubleFunction.apply(d)).get();
    }

    public static DoublePredicate doublePredicate(DoublePredicate doublePredicate) {
        return d -> Blocking.supplier(() -> doublePredicate.test(d)).get();
    }

    public static DoubleSupplier doubleSupplier(DoubleSupplier doubleSupplier) {
        return () -> Blocking.supplier(() -> doubleSupplier.getAsDouble()).get();
    }

    public static DoubleToIntFunction doubleToIntFunction(DoubleToIntFunction doubleToIntFunction) {
        return d -> Blocking.supplier(() -> doubleToIntFunction.applyAsInt(d)).get();
    }

    public static DoubleToLongFunction doubleToLongFunction(DoubleToLongFunction doubleToLongFunction) {
        return d -> Blocking.supplier(() -> doubleToLongFunction.applyAsLong(d)).get();
    }

    public static DoubleUnaryOperator doubleUnaryOperator(DoubleUnaryOperator doubleUnaryOperator) {
        return d -> Blocking.supplier(() -> doubleUnaryOperator.applyAsDouble(d)).get();
    }

    public static <T, R> Function<T, R> function(Function<? super T, ? extends R> function) {
        return t -> Blocking.supplier(() -> function.apply(t)).get();
    }

    public static IntBinaryOperator intBinaryOperator(IntBinaryOperator intBinaryOperator) {
        return (i1, i2) -> Blocking.supplier(() -> intBinaryOperator.applyAsInt(i1, i2)).get();
    }

    public static IntConsumer intConsumer(IntConsumer intConsumer) {
        return i -> Blocking.runnable(() -> intConsumer.accept(i)).run();
    }

    public static <R> IntFunction<R> intFunction(IntFunction<? extends R> intFunction) {
        return i -> Blocking.supplier(() -> intFunction.apply(i)).get();
    }

    public static IntPredicate intPredicate(IntPredicate intPredicate) {
        return i -> Blocking.supplier(() -> intPredicate.test(i)).get();
    }

    public static IntSupplier intSupplier(IntSupplier intSupplier) {
        return () -> Blocking.supplier(() -> intSupplier.getAsInt()).get();
    }

    public static IntToDoubleFunction intToDoubleFunction(IntToDoubleFunction intToDoubleFunction) {
        return i -> Blocking.supplier(() -> intToDoubleFunction.applyAsDouble(i)).get();
    }

    public static IntToLongFunction intToLongFunction(IntToLongFunction intToLongFunction) {
        return i -> Blocking.supplier(() -> intToLongFunction.applyAsLong(i)).get();
    }

    public static IntUnaryOperator intUnaryOperator(IntUnaryOperator intUnaryOperator) {
        return i -> Blocking.supplier(() -> intUnaryOperator.applyAsInt(i)).get();
    }

    public static LongBinaryOperator longBinaryOperator(LongBinaryOperator longBinaryOperator) {
        return (l1, l2) -> Blocking.supplier(() -> longBinaryOperator.applyAsLong(l1, l2)).get();
    }

    public static LongConsumer longConsumer(LongConsumer longConsumer) {
        return l -> Blocking.runnable(() -> longConsumer.accept(l)).run();
    }

    public static <R> LongFunction<R> longFunction(LongFunction<? extends R> longFunction) {
        return l -> Blocking.supplier(() -> longFunction.apply(l)).get();
    }

    public static LongPredicate longPredicate(LongPredicate longPredicate) {
        return l -> Blocking.supplier(() -> longPredicate.test(l)).get();
    }

    public static LongSupplier longSupplier(LongSupplier longSupplier) {
        return () -> Blocking.supplier(() -> longSupplier.getAsLong()).get();
    }

    public static LongToDoubleFunction longToDoubleFunction(LongToDoubleFunction longToDoubleFunction) {
        return l -> Blocking.supplier(() -> longToDoubleFunction.applyAsDouble(l)).get();
    }

    public static LongToIntFunction longToIntFunction(LongToIntFunction longToIntFunction) {
        return l -> Blocking.supplier(() -> longToIntFunction.applyAsInt(l)).get();
    }

    public static LongUnaryOperator longUnaryOperator(LongUnaryOperator longUnaryOperator) {
        return l -> Blocking.supplier(() -> longUnaryOperator.applyAsLong(l)).get();
    }

    public static <T> ObjDoubleConsumer<T> objDoubleConsumer(ObjDoubleConsumer<T> objDoubleConsumer) {
        return (o, d) -> Blocking.runnable(() -> objDoubleConsumer.accept(o, d)).run();
    }

    public static <T> ObjIntConsumer<T> objIntConsumer(ObjIntConsumer<T> objIntConsumer) {
        return (o, i) -> Blocking.runnable(() -> objIntConsumer.accept(o, i)).run();
    }

    public static <T> ObjLongConsumer<T> objLongConsumer(ObjLongConsumer<T> objLongConsumer) {
        return (o, l) -> Blocking.runnable(() -> objLongConsumer.accept(o, l)).run();
    }

    public static <T> Predicate<T> predicate(Predicate<? super T> predicate) {
        return t -> Blocking.supplier(() -> predicate.test(t)).get();
    }

    public static <T> Supplier<T> supplier(Supplier<? extends T> supplier) {
        return new BlockingSupplier<T>(supplier);
    }

    public static <T, U> ToDoubleBiFunction<T, U> toDoubleBiFunction(ToDoubleBiFunction<? super T, ? super U> toDoubleBiFunction) {
        return (t, u) -> Blocking.supplier(() -> toDoubleBiFunction.applyAsDouble(t, u)).get();
    }

    public static <T> ToDoubleFunction<T> toDoubleFunction(ToDoubleFunction<? super T> toDoubleFunction) {
        return t -> Blocking.supplier(() -> toDoubleFunction.applyAsDouble(t)).get();
    }

    public static <T, U> ToIntBiFunction<T, U> toIntBiFunction(ToIntBiFunction<? super T, ? super U> toIntBiFunction) {
        return (t, u) -> Blocking.supplier(() -> toIntBiFunction.applyAsInt(t, u)).get();
    }

    public static <T> ToIntFunction<T> toIntFunction(ToIntFunction<? super T> toIntFunction) {
        return t -> Blocking.supplier(() -> toIntFunction.applyAsInt(t)).get();
    }

    public static <T, U> ToLongBiFunction<T, U> toLongBiFunction(ToLongBiFunction<? super T, ? super U> toLongBiFunction) {
        return (t, u) -> Blocking.supplier(() -> toLongBiFunction.applyAsLong(t, u)).get();
    }

    public static <T> ToLongFunction<T> toLongFunction(ToLongFunction<? super T> toLongFunction) {
        return t -> Blocking.supplier(() -> toLongFunction.applyAsLong(t)).get();
    }

    public static <T> UnaryOperator<T> unaryOperator(UnaryOperator<T> unaryOperator) {
        return t -> Blocking.supplier(() -> unaryOperator.apply(t)).get();
    }

    private Blocking() {
    }

    static class BlockingSupplier<T>
    implements Supplier<T> {
        private static final Object NULL = new Object();
        volatile T result = NULL;
        final Supplier<? extends T> supplier;

        BlockingSupplier(Supplier<? extends T> supplier) {
            this.supplier = supplier;
        }

        @Override
        public T get() {
            try {
                ForkJoinPool.managedBlock(new ForkJoinPool.ManagedBlocker(){

                    @Override
                    public boolean block() throws InterruptedException {
                        result = supplier.get();
                        return true;
                    }

                    @Override
                    public boolean isReleasable() {
                        return result != NULL;
                    }
                });
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return this.result;
        }
    }
}

