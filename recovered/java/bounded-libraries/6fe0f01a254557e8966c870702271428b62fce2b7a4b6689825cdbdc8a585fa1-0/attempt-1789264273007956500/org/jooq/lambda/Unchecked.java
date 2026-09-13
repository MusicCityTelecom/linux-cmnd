/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Comparator;
import java.util.concurrent.Callable;
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
import org.jooq.lambda.SeqUtils;
import org.jooq.lambda.UncheckedException;
import org.jooq.lambda.fi.lang.CheckedRunnable;
import org.jooq.lambda.fi.util.CheckedComparator;
import org.jooq.lambda.fi.util.concurrent.CheckedCallable;
import org.jooq.lambda.fi.util.function.CheckedBiConsumer;
import org.jooq.lambda.fi.util.function.CheckedBiFunction;
import org.jooq.lambda.fi.util.function.CheckedBiPredicate;
import org.jooq.lambda.fi.util.function.CheckedBinaryOperator;
import org.jooq.lambda.fi.util.function.CheckedBooleanSupplier;
import org.jooq.lambda.fi.util.function.CheckedConsumer;
import org.jooq.lambda.fi.util.function.CheckedDoubleBinaryOperator;
import org.jooq.lambda.fi.util.function.CheckedDoubleConsumer;
import org.jooq.lambda.fi.util.function.CheckedDoubleFunction;
import org.jooq.lambda.fi.util.function.CheckedDoublePredicate;
import org.jooq.lambda.fi.util.function.CheckedDoubleSupplier;
import org.jooq.lambda.fi.util.function.CheckedDoubleToIntFunction;
import org.jooq.lambda.fi.util.function.CheckedDoubleToLongFunction;
import org.jooq.lambda.fi.util.function.CheckedDoubleUnaryOperator;
import org.jooq.lambda.fi.util.function.CheckedFunction;
import org.jooq.lambda.fi.util.function.CheckedIntBinaryOperator;
import org.jooq.lambda.fi.util.function.CheckedIntConsumer;
import org.jooq.lambda.fi.util.function.CheckedIntFunction;
import org.jooq.lambda.fi.util.function.CheckedIntPredicate;
import org.jooq.lambda.fi.util.function.CheckedIntSupplier;
import org.jooq.lambda.fi.util.function.CheckedIntToDoubleFunction;
import org.jooq.lambda.fi.util.function.CheckedIntToLongFunction;
import org.jooq.lambda.fi.util.function.CheckedIntUnaryOperator;
import org.jooq.lambda.fi.util.function.CheckedLongBinaryOperator;
import org.jooq.lambda.fi.util.function.CheckedLongConsumer;
import org.jooq.lambda.fi.util.function.CheckedLongFunction;
import org.jooq.lambda.fi.util.function.CheckedLongPredicate;
import org.jooq.lambda.fi.util.function.CheckedLongSupplier;
import org.jooq.lambda.fi.util.function.CheckedLongToDoubleFunction;
import org.jooq.lambda.fi.util.function.CheckedLongToIntFunction;
import org.jooq.lambda.fi.util.function.CheckedLongUnaryOperator;
import org.jooq.lambda.fi.util.function.CheckedObjDoubleConsumer;
import org.jooq.lambda.fi.util.function.CheckedObjIntConsumer;
import org.jooq.lambda.fi.util.function.CheckedObjLongConsumer;
import org.jooq.lambda.fi.util.function.CheckedPredicate;
import org.jooq.lambda.fi.util.function.CheckedSupplier;
import org.jooq.lambda.fi.util.function.CheckedToDoubleBiFunction;
import org.jooq.lambda.fi.util.function.CheckedToDoubleFunction;
import org.jooq.lambda.fi.util.function.CheckedToIntBiFunction;
import org.jooq.lambda.fi.util.function.CheckedToIntFunction;
import org.jooq.lambda.fi.util.function.CheckedToLongBiFunction;
import org.jooq.lambda.fi.util.function.CheckedToLongFunction;
import org.jooq.lambda.fi.util.function.CheckedUnaryOperator;

public final class Unchecked {
    public static final Consumer<Throwable> THROWABLE_TO_RUNTIME_EXCEPTION = t -> {
        if (t instanceof Error) {
            throw (Error)t;
        }
        if (t instanceof RuntimeException) {
            throw (RuntimeException)t;
        }
        if (t instanceof IOException) {
            throw new UncheckedIOException((IOException)t);
        }
        if (t instanceof InterruptedException) {
            Thread.currentThread().interrupt();
        }
        throw new UncheckedException((Throwable)t);
    };
    public static final Consumer<Throwable> RETHROW_ALL = SeqUtils::sneakyThrow;

    public static void throwChecked(Throwable t) {
        SeqUtils.sneakyThrow(t);
    }

    public static Runnable runnable(CheckedRunnable runnable) {
        return Unchecked.runnable(runnable, THROWABLE_TO_RUNTIME_EXCEPTION);
    }

    public static Runnable runnable(CheckedRunnable runnable, Consumer<Throwable> handler) {
        return () -> {
            try {
                runnable.run();
            }
            catch (Throwable e) {
                handler.accept(e);
                throw new IllegalStateException("Exception handler must throw a RuntimeException", e);
            }
        };
    }

    public static <T> Callable<T> callable(CheckedCallable<T> callable) {
        return Unchecked.callable(callable, THROWABLE_TO_RUNTIME_EXCEPTION);
    }

    public static <T> Callable<T> callable(CheckedCallable<T> callable, Consumer<Throwable> handler) {
        return () -> {
            try {
                return callable.call();
            }
            catch (Throwable e) {
                handler.accept(e);
                throw new IllegalStateException("Exception handler must throw a RuntimeException", e);
            }
        };
    }

    public static <T> Comparator<T> comparator(CheckedComparator<T> comparator) {
        return Unchecked.comparator(comparator, THROWABLE_TO_RUNTIME_EXCEPTION);
    }

    public static <T> Comparator<T> comparator(CheckedComparator<T> comparator, Consumer<Throwable> handler) {
        return (t1, t2) -> {
            try {
                return comparator.compare(t1, t2);
            }
            catch (Throwable e) {
                handler.accept(e);
                throw new IllegalStateException("Exception handler must throw a RuntimeException", e);
            }
        };
    }

    public static <T, U> BiConsumer<T, U> biConsumer(CheckedBiConsumer<T, U> consumer) {
        return Unchecked.biConsumer(consumer, THROWABLE_TO_RUNTIME_EXCEPTION);
    }

    public static <T, U> BiConsumer<T, U> biConsumer(CheckedBiConsumer<T, U> consumer, Consumer<Throwable> handler) {
        return (t, u) -> {
            try {
                consumer.accept(t, u);
            }
            catch (Throwable e) {
                handler.accept(e);
                throw new IllegalStateException("Exception handler must throw a RuntimeException", e);
            }
        };
    }

    public static <T> ObjIntConsumer<T> objIntConsumer(CheckedObjIntConsumer<T> consumer) {
        return Unchecked.objIntConsumer(consumer, THROWABLE_TO_RUNTIME_EXCEPTION);
    }

    public static <T> ObjIntConsumer<T> objIntConsumer(CheckedObjIntConsumer<T> consumer, Consumer<Throwable> handler) {
        return (t, u) -> {
            try {
                consumer.accept(t, u);
            }
            catch (Throwable e) {
                handler.accept(e);
                throw new IllegalStateException("Exception handler must throw a RuntimeException", e);
            }
        };
    }

    public static <T> ObjLongConsumer<T> objLongConsumer(CheckedObjLongConsumer<T> consumer) {
        return Unchecked.objLongConsumer(consumer, THROWABLE_TO_RUNTIME_EXCEPTION);
    }

    public static <T> ObjLongConsumer<T> objLongConsumer(CheckedObjLongConsumer<T> consumer, Consumer<Throwable> handler) {
        return (t, u) -> {
            try {
                consumer.accept(t, u);
            }
            catch (Throwable e) {
                handler.accept(e);
                throw new IllegalStateException("Exception handler must throw a RuntimeException", e);
            }
        };
    }

    public static <T> ObjDoubleConsumer<T> objDoubleConsumer(CheckedObjDoubleConsumer<T> consumer) {
        return Unchecked.objDoubleConsumer(consumer, THROWABLE_TO_RUNTIME_EXCEPTION);
    }

    public static <T> ObjDoubleConsumer<T> objDoubleConsumer(CheckedObjDoubleConsumer<T> consumer, Consumer<Throwable> handler) {
        return (t, u) -> {
            try {
                consumer.accept(t, u);
            }
            catch (Throwable e) {
                handler.accept(e);
                throw new IllegalStateException("Exception handler must throw a RuntimeException", e);
            }
        };
    }

    public static <T, U, R> BiFunction<T, U, R> biFunction(CheckedBiFunction<T, U, R> function) {
        return Unchecked.biFunction(function, THROWABLE_TO_RUNTIME_EXCEPTION);
    }

    public static <T, U, R> BiFunction<T, U, R> biFunction(CheckedBiFunction<T, U, R> function, Consumer<Throwable> handler) {
        return (t, u) -> {
            try {
                return function.apply(t, u);
            }
            catch (Throwable e) {
                handler.accept(e);
                throw new IllegalStateException("Exception handler must throw a RuntimeException", e);
            }
        };
    }

    public static <T, U> ToIntBiFunction<T, U> toIntBiFunction(CheckedToIntBiFunction<T, U> function) {
        return Unchecked.toIntBiFunction(function, THROWABLE_TO_RUNTIME_EXCEPTION);
    }

    public static <T, U> ToIntBiFunction<T, U> toIntBiFunction(CheckedToIntBiFunction<T, U> function, Consumer<Throwable> handler) {
        return (t, u) -> {
            try {
                return function.applyAsInt(t, u);
            }
            catch (Throwable e) {
                handler.accept(e);
                throw new IllegalStateException("Exception handler must throw a RuntimeException", e);
            }
        };
    }

    public static <T, U> ToLongBiFunction<T, U> toLongBiFunction(CheckedToLongBiFunction<T, U> function) {
        return Unchecked.toLongBiFunction(function, THROWABLE_TO_RUNTIME_EXCEPTION);
    }

    public static <T, U> ToLongBiFunction<T, U> toLongBiFunction(CheckedToLongBiFunction<T, U> function, Consumer<Throwable> handler) {
        return (t, u) -> {
            try {
                return function.applyAsLong(t, u);
            }
            catch (Throwable e) {
                handler.accept(e);
                throw new IllegalStateException("Exception handler must throw a RuntimeException", e);
            }
        };
    }

    public static <T, U> ToDoubleBiFunction<T, U> toDoubleBiFunction(CheckedToDoubleBiFunction<T, U> function) {
        return Unchecked.toDoubleBiFunction(function, THROWABLE_TO_RUNTIME_EXCEPTION);
    }

    public static <T, U> ToDoubleBiFunction<T, U> toDoubleBiFunction(CheckedToDoubleBiFunction<T, U> function, Consumer<Throwable> handler) {
        return (t, u) -> {
            try {
                return function.applyAsDouble(t, u);
            }
            catch (Throwable e) {
                handler.accept(e);
                throw new IllegalStateException("Exception handler must throw a RuntimeException", e);
            }
        };
    }

    public static <T, U> BiPredicate<T, U> biPredicate(CheckedBiPredicate<T, U> predicate) {
        return Unchecked.biPredicate(predicate, THROWABLE_TO_RUNTIME_EXCEPTION);
    }

    public static <T, U> BiPredicate<T, U> biPredicate(CheckedBiPredicate<T, U> predicate, Consumer<Throwable> handler) {
        return (t, u) -> {
            try {
                return predicate.test(t, u);
            }
            catch (Throwable e) {
                handler.accept(e);
                throw new IllegalStateException("Exception handler must throw a RuntimeException", e);
            }
        };
    }

    public static <T> BinaryOperator<T> binaryOperator(CheckedBinaryOperator<T> operator) {
        return Unchecked.binaryOperator(operator, THROWABLE_TO_RUNTIME_EXCEPTION);
    }

    public static <T> BinaryOperator<T> binaryOperator(CheckedBinaryOperator<T> operator, Consumer<Throwable> handler) {
        return (t1, t2) -> {
            try {
                return operator.apply(t1, t2);
            }
            catch (Throwable e) {
                handler.accept(e);
                throw new IllegalStateException("Exception handler must throw a RuntimeException", e);
            }
        };
    }

    public static IntBinaryOperator intBinaryOperator(CheckedIntBinaryOperator operator) {
        return Unchecked.intBinaryOperator(operator, THROWABLE_TO_RUNTIME_EXCEPTION);
    }

    public static IntBinaryOperator intBinaryOperator(CheckedIntBinaryOperator operator, Consumer<Throwable> handler) {
        return (i1, i2) -> {
            try {
                return operator.applyAsInt(i1, i2);
            }
            catch (Throwable e) {
                handler.accept(e);
                throw new IllegalStateException("Exception handler must throw a RuntimeException", e);
            }
        };
    }

    public static LongBinaryOperator longBinaryOperator(CheckedLongBinaryOperator operator) {
        return Unchecked.longBinaryOperator(operator, THROWABLE_TO_RUNTIME_EXCEPTION);
    }

    public static LongBinaryOperator longBinaryOperator(CheckedLongBinaryOperator operator, Consumer<Throwable> handler) {
        return (l1, l2) -> {
            try {
                return operator.applyAsLong(l1, l2);
            }
            catch (Throwable e) {
                handler.accept(e);
                throw new IllegalStateException("Exception handler must throw a RuntimeException", e);
            }
        };
    }

    public static DoubleBinaryOperator doubleBinaryOperator(CheckedDoubleBinaryOperator operator) {
        return Unchecked.doubleBinaryOperator(operator, THROWABLE_TO_RUNTIME_EXCEPTION);
    }

    public static DoubleBinaryOperator doubleBinaryOperator(CheckedDoubleBinaryOperator operator, Consumer<Throwable> handler) {
        return (d1, d2) -> {
            try {
                return operator.applyAsDouble(d1, d2);
            }
            catch (Throwable e) {
                handler.accept(e);
                throw new IllegalStateException("Exception handler must throw a RuntimeException", e);
            }
        };
    }

    public static <T> Consumer<T> consumer(CheckedConsumer<T> consumer) {
        return Unchecked.consumer(consumer, THROWABLE_TO_RUNTIME_EXCEPTION);
    }

    public static <T> Consumer<T> consumer(CheckedConsumer<T> consumer, Consumer<Throwable> handler) {
        return t -> {
            try {
                consumer.accept(t);
            }
            catch (Throwable e) {
                handler.accept(e);
                throw new IllegalStateException("Exception handler must throw a RuntimeException", e);
            }
        };
    }

    public static IntConsumer intConsumer(CheckedIntConsumer consumer) {
        return Unchecked.intConsumer(consumer, THROWABLE_TO_RUNTIME_EXCEPTION);
    }

    public static IntConsumer intConsumer(CheckedIntConsumer consumer, Consumer<Throwable> handler) {
        return i -> {
            try {
                consumer.accept(i);
            }
            catch (Throwable e) {
                handler.accept(e);
                throw new IllegalStateException("Exception handler must throw a RuntimeException", e);
            }
        };
    }

    public static LongConsumer longConsumer(CheckedLongConsumer consumer) {
        return Unchecked.longConsumer(consumer, THROWABLE_TO_RUNTIME_EXCEPTION);
    }

    public static LongConsumer longConsumer(CheckedLongConsumer consumer, Consumer<Throwable> handler) {
        return l -> {
            try {
                consumer.accept(l);
            }
            catch (Throwable e) {
                handler.accept(e);
                throw new IllegalStateException("Exception handler must throw a RuntimeException", e);
            }
        };
    }

    public static DoubleConsumer doubleConsumer(CheckedDoubleConsumer consumer) {
        return Unchecked.doubleConsumer(consumer, THROWABLE_TO_RUNTIME_EXCEPTION);
    }

    public static DoubleConsumer doubleConsumer(CheckedDoubleConsumer consumer, Consumer<Throwable> handler) {
        return d -> {
            try {
                consumer.accept(d);
            }
            catch (Throwable e) {
                handler.accept(e);
                throw new IllegalStateException("Exception handler must throw a RuntimeException", e);
            }
        };
    }

    public static <T, R> Function<T, R> function(CheckedFunction<T, R> function) {
        return Unchecked.function(function, THROWABLE_TO_RUNTIME_EXCEPTION);
    }

    public static <T, R> Function<T, R> function(CheckedFunction<T, R> function, Consumer<Throwable> handler) {
        return t -> {
            try {
                return function.apply(t);
            }
            catch (Throwable e) {
                handler.accept(e);
                throw new IllegalStateException("Exception handler must throw a RuntimeException", e);
            }
        };
    }

    public static <T> ToIntFunction<T> toIntFunction(CheckedToIntFunction<T> function) {
        return Unchecked.toIntFunction(function, THROWABLE_TO_RUNTIME_EXCEPTION);
    }

    public static <T> ToIntFunction<T> toIntFunction(CheckedToIntFunction<T> function, Consumer<Throwable> handler) {
        return t -> {
            try {
                return function.applyAsInt(t);
            }
            catch (Throwable e) {
                handler.accept(e);
                throw new IllegalStateException("Exception handler must throw a RuntimeException", e);
            }
        };
    }

    public static <T> ToLongFunction<T> toLongFunction(CheckedToLongFunction<T> function) {
        return Unchecked.toLongFunction(function, THROWABLE_TO_RUNTIME_EXCEPTION);
    }

    public static <T> ToLongFunction<T> toLongFunction(CheckedToLongFunction<T> function, Consumer<Throwable> handler) {
        return t -> {
            try {
                return function.applyAsLong(t);
            }
            catch (Throwable e) {
                handler.accept(e);
                throw new IllegalStateException("Exception handler must throw a RuntimeException", e);
            }
        };
    }

    public static <T> ToDoubleFunction<T> toDoubleFunction(CheckedToDoubleFunction<T> function) {
        return Unchecked.toDoubleFunction(function, THROWABLE_TO_RUNTIME_EXCEPTION);
    }

    public static <T> ToDoubleFunction<T> toDoubleFunction(CheckedToDoubleFunction<T> function, Consumer<Throwable> handler) {
        return t -> {
            try {
                return function.applyAsDouble(t);
            }
            catch (Throwable e) {
                handler.accept(e);
                throw new IllegalStateException("Exception handler must throw a RuntimeException", e);
            }
        };
    }

    public static <R> IntFunction<R> intFunction(CheckedIntFunction<R> function) {
        return Unchecked.intFunction(function, THROWABLE_TO_RUNTIME_EXCEPTION);
    }

    public static <R> IntFunction<R> intFunction(CheckedIntFunction<R> function, Consumer<Throwable> handler) {
        return t -> {
            try {
                return function.apply(t);
            }
            catch (Throwable e) {
                handler.accept(e);
                throw new IllegalStateException("Exception handler must throw a RuntimeException", e);
            }
        };
    }

    public static IntToLongFunction intToLongFunction(CheckedIntToLongFunction function) {
        return Unchecked.intToLongFunction(function, THROWABLE_TO_RUNTIME_EXCEPTION);
    }

    public static IntToLongFunction intToLongFunction(CheckedIntToLongFunction function, Consumer<Throwable> handler) {
        return t -> {
            try {
                return function.applyAsLong(t);
            }
            catch (Throwable e) {
                handler.accept(e);
                throw new IllegalStateException("Exception handler must throw a RuntimeException", e);
            }
        };
    }

    public static IntToDoubleFunction intToDoubleFunction(CheckedIntToDoubleFunction function) {
        return Unchecked.intToDoubleFunction(function, THROWABLE_TO_RUNTIME_EXCEPTION);
    }

    public static IntToDoubleFunction intToDoubleFunction(CheckedIntToDoubleFunction function, Consumer<Throwable> handler) {
        return t -> {
            try {
                return function.applyAsDouble(t);
            }
            catch (Throwable e) {
                handler.accept(e);
                throw new IllegalStateException("Exception handler must throw a RuntimeException", e);
            }
        };
    }

    public static <R> LongFunction<R> longFunction(CheckedLongFunction<R> function) {
        return Unchecked.longFunction(function, THROWABLE_TO_RUNTIME_EXCEPTION);
    }

    public static <R> LongFunction<R> longFunction(CheckedLongFunction<R> function, Consumer<Throwable> handler) {
        return t -> {
            try {
                return function.apply(t);
            }
            catch (Throwable e) {
                handler.accept(e);
                throw new IllegalStateException("Exception handler must throw a RuntimeException", e);
            }
        };
    }

    public static LongToIntFunction longToIntFunction(CheckedLongToIntFunction function) {
        return Unchecked.longToIntFunction(function, THROWABLE_TO_RUNTIME_EXCEPTION);
    }

    public static LongToIntFunction longToIntFunction(CheckedLongToIntFunction function, Consumer<Throwable> handler) {
        return t -> {
            try {
                return function.applyAsInt(t);
            }
            catch (Throwable e) {
                handler.accept(e);
                throw new IllegalStateException("Exception handler must throw a RuntimeException", e);
            }
        };
    }

    public static LongToDoubleFunction longToDoubleFunction(CheckedLongToDoubleFunction function) {
        return Unchecked.longToDoubleFunction(function, THROWABLE_TO_RUNTIME_EXCEPTION);
    }

    public static LongToDoubleFunction longToDoubleFunction(CheckedLongToDoubleFunction function, Consumer<Throwable> handler) {
        return t -> {
            try {
                return function.applyAsDouble(t);
            }
            catch (Throwable e) {
                handler.accept(e);
                throw new IllegalStateException("Exception handler must throw a RuntimeException", e);
            }
        };
    }

    public static <R> DoubleFunction<R> doubleFunction(CheckedDoubleFunction<R> function) {
        return Unchecked.doubleFunction(function, THROWABLE_TO_RUNTIME_EXCEPTION);
    }

    public static <R> DoubleFunction<R> doubleFunction(CheckedDoubleFunction<R> function, Consumer<Throwable> handler) {
        return t -> {
            try {
                return function.apply(t);
            }
            catch (Throwable e) {
                handler.accept(e);
                throw new IllegalStateException("Exception handler must throw a RuntimeException", e);
            }
        };
    }

    public static DoubleToIntFunction doubleToIntFunction(CheckedDoubleToIntFunction function) {
        return Unchecked.doubleToIntFunction(function, THROWABLE_TO_RUNTIME_EXCEPTION);
    }

    public static DoubleToIntFunction doubleToIntFunction(CheckedDoubleToIntFunction function, Consumer<Throwable> handler) {
        return t -> {
            try {
                return function.applyAsInt(t);
            }
            catch (Throwable e) {
                handler.accept(e);
                throw new IllegalStateException("Exception handler must throw a RuntimeException", e);
            }
        };
    }

    public static DoubleToLongFunction doubleToLongFunction(CheckedDoubleToLongFunction function) {
        return Unchecked.doubleToLongFunction(function, THROWABLE_TO_RUNTIME_EXCEPTION);
    }

    public static DoubleToLongFunction doubleToLongFunction(CheckedDoubleToLongFunction function, Consumer<Throwable> handler) {
        return t -> {
            try {
                return function.applyAsLong(t);
            }
            catch (Throwable e) {
                handler.accept(e);
                throw new IllegalStateException("Exception handler must throw a RuntimeException", e);
            }
        };
    }

    public static <T> Predicate<T> predicate(CheckedPredicate<T> predicate) {
        return Unchecked.predicate(predicate, THROWABLE_TO_RUNTIME_EXCEPTION);
    }

    public static <T> Predicate<T> predicate(CheckedPredicate<T> function, Consumer<Throwable> handler) {
        return t -> {
            try {
                return function.test(t);
            }
            catch (Throwable e) {
                handler.accept(e);
                throw new IllegalStateException("Exception handler must throw a RuntimeException", e);
            }
        };
    }

    public static IntPredicate intPredicate(CheckedIntPredicate predicate) {
        return Unchecked.intPredicate(predicate, THROWABLE_TO_RUNTIME_EXCEPTION);
    }

    public static IntPredicate intPredicate(CheckedIntPredicate function, Consumer<Throwable> handler) {
        return i -> {
            try {
                return function.test(i);
            }
            catch (Throwable e) {
                handler.accept(e);
                throw new IllegalStateException("Exception handler must throw a RuntimeException", e);
            }
        };
    }

    public static LongPredicate longPredicate(CheckedLongPredicate predicate) {
        return Unchecked.longPredicate(predicate, THROWABLE_TO_RUNTIME_EXCEPTION);
    }

    public static LongPredicate longPredicate(CheckedLongPredicate function, Consumer<Throwable> handler) {
        return l -> {
            try {
                return function.test(l);
            }
            catch (Throwable e) {
                handler.accept(e);
                throw new IllegalStateException("Exception handler must throw a RuntimeException", e);
            }
        };
    }

    public static DoublePredicate doublePredicate(CheckedDoublePredicate predicate) {
        return Unchecked.doublePredicate(predicate, THROWABLE_TO_RUNTIME_EXCEPTION);
    }

    public static DoublePredicate doublePredicate(CheckedDoublePredicate function, Consumer<Throwable> handler) {
        return d -> {
            try {
                return function.test(d);
            }
            catch (Throwable e) {
                handler.accept(e);
                throw new IllegalStateException("Exception handler must throw a RuntimeException", e);
            }
        };
    }

    public static <T> Supplier<T> supplier(CheckedSupplier<T> supplier) {
        return Unchecked.supplier(supplier, THROWABLE_TO_RUNTIME_EXCEPTION);
    }

    public static <T> Supplier<T> supplier(CheckedSupplier<T> supplier, Consumer<Throwable> handler) {
        return () -> {
            try {
                return supplier.get();
            }
            catch (Throwable e) {
                handler.accept(e);
                throw new IllegalStateException("Exception handler must throw a RuntimeException", e);
            }
        };
    }

    public static IntSupplier intSupplier(CheckedIntSupplier supplier) {
        return Unchecked.intSupplier(supplier, THROWABLE_TO_RUNTIME_EXCEPTION);
    }

    public static IntSupplier intSupplier(CheckedIntSupplier supplier, Consumer<Throwable> handler) {
        return () -> {
            try {
                return supplier.getAsInt();
            }
            catch (Throwable e) {
                handler.accept(e);
                throw new IllegalStateException("Exception handler must throw a RuntimeException", e);
            }
        };
    }

    public static LongSupplier longSupplier(CheckedLongSupplier supplier) {
        return Unchecked.longSupplier(supplier, THROWABLE_TO_RUNTIME_EXCEPTION);
    }

    public static LongSupplier longSupplier(CheckedLongSupplier supplier, Consumer<Throwable> handler) {
        return () -> {
            try {
                return supplier.getAsLong();
            }
            catch (Throwable e) {
                handler.accept(e);
                throw new IllegalStateException("Exception handler must throw a RuntimeException", e);
            }
        };
    }

    public static DoubleSupplier doubleSupplier(CheckedDoubleSupplier supplier) {
        return Unchecked.doubleSupplier(supplier, THROWABLE_TO_RUNTIME_EXCEPTION);
    }

    public static DoubleSupplier doubleSupplier(CheckedDoubleSupplier supplier, Consumer<Throwable> handler) {
        return () -> {
            try {
                return supplier.getAsDouble();
            }
            catch (Throwable e) {
                handler.accept(e);
                throw new IllegalStateException("Exception handler must throw a RuntimeException", e);
            }
        };
    }

    public static BooleanSupplier booleanSupplier(CheckedBooleanSupplier supplier) {
        return Unchecked.booleanSupplier(supplier, THROWABLE_TO_RUNTIME_EXCEPTION);
    }

    public static BooleanSupplier booleanSupplier(CheckedBooleanSupplier supplier, Consumer<Throwable> handler) {
        return () -> {
            try {
                return supplier.getAsBoolean();
            }
            catch (Throwable e) {
                handler.accept(e);
                throw new IllegalStateException("Exception handler must throw a RuntimeException", e);
            }
        };
    }

    public static <T> UnaryOperator<T> unaryOperator(CheckedUnaryOperator<T> operator) {
        return Unchecked.unaryOperator(operator, THROWABLE_TO_RUNTIME_EXCEPTION);
    }

    public static <T> UnaryOperator<T> unaryOperator(CheckedUnaryOperator<T> operator, Consumer<Throwable> handler) {
        return t -> {
            try {
                return operator.apply(t);
            }
            catch (Throwable e) {
                handler.accept(e);
                throw new IllegalStateException("Exception handler must throw a RuntimeException", e);
            }
        };
    }

    public static IntUnaryOperator intUnaryOperator(CheckedIntUnaryOperator operator) {
        return Unchecked.intUnaryOperator(operator, THROWABLE_TO_RUNTIME_EXCEPTION);
    }

    public static IntUnaryOperator intUnaryOperator(CheckedIntUnaryOperator operator, Consumer<Throwable> handler) {
        return t -> {
            try {
                return operator.applyAsInt(t);
            }
            catch (Throwable e) {
                handler.accept(e);
                throw new IllegalStateException("Exception handler must throw a RuntimeException", e);
            }
        };
    }

    public static LongUnaryOperator longUnaryOperator(CheckedLongUnaryOperator operator) {
        return Unchecked.longUnaryOperator(operator, THROWABLE_TO_RUNTIME_EXCEPTION);
    }

    public static LongUnaryOperator longUnaryOperator(CheckedLongUnaryOperator operator, Consumer<Throwable> handler) {
        return t -> {
            try {
                return operator.applyAsLong(t);
            }
            catch (Throwable e) {
                handler.accept(e);
                throw new IllegalStateException("Exception handler must throw a RuntimeException", e);
            }
        };
    }

    public static DoubleUnaryOperator doubleUnaryOperator(CheckedDoubleUnaryOperator operator) {
        return Unchecked.doubleUnaryOperator(operator, THROWABLE_TO_RUNTIME_EXCEPTION);
    }

    public static DoubleUnaryOperator doubleUnaryOperator(CheckedDoubleUnaryOperator operator, Consumer<Throwable> handler) {
        return t -> {
            try {
                return operator.applyAsDouble(t);
            }
            catch (Throwable e) {
                handler.accept(e);
                throw new IllegalStateException("Exception handler must throw a RuntimeException", e);
            }
        };
    }

    private Unchecked() {
    }
}

