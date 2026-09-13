/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda;

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
import org.jooq.lambda.Unchecked;
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

public final class Sneaky {
    public static void throwChecked(Throwable t) {
        SeqUtils.sneakyThrow(t);
    }

    public static Runnable runnable(CheckedRunnable runnable) {
        return Unchecked.runnable(runnable, Unchecked.RETHROW_ALL);
    }

    public static <T> Callable<T> callable(CheckedCallable<T> callable) {
        return Unchecked.callable(callable, Unchecked.RETHROW_ALL);
    }

    public static <T> Comparator<T> comparator(CheckedComparator<T> comparator) {
        return Unchecked.comparator(comparator, Unchecked.RETHROW_ALL);
    }

    public static <T, U> BiConsumer<T, U> biConsumer(CheckedBiConsumer<T, U> consumer) {
        return Unchecked.biConsumer(consumer, Unchecked.RETHROW_ALL);
    }

    public static <T> ObjIntConsumer<T> objIntConsumer(CheckedObjIntConsumer<T> consumer) {
        return Unchecked.objIntConsumer(consumer, Unchecked.RETHROW_ALL);
    }

    public static <T> ObjLongConsumer<T> objLongConsumer(CheckedObjLongConsumer<T> consumer) {
        return Unchecked.objLongConsumer(consumer, Unchecked.RETHROW_ALL);
    }

    public static <T> ObjDoubleConsumer<T> objDoubleConsumer(CheckedObjDoubleConsumer<T> consumer) {
        return Unchecked.objDoubleConsumer(consumer, Unchecked.RETHROW_ALL);
    }

    public static <T, U, R> BiFunction<T, U, R> biFunction(CheckedBiFunction<T, U, R> function) {
        return Unchecked.biFunction(function, Unchecked.RETHROW_ALL);
    }

    public static <T, U> ToIntBiFunction<T, U> toIntBiFunction(CheckedToIntBiFunction<T, U> function) {
        return Unchecked.toIntBiFunction(function, Unchecked.RETHROW_ALL);
    }

    public static <T, U> ToLongBiFunction<T, U> toLongBiFunction(CheckedToLongBiFunction<T, U> function) {
        return Unchecked.toLongBiFunction(function, Unchecked.RETHROW_ALL);
    }

    public static <T, U> ToDoubleBiFunction<T, U> toDoubleBiFunction(CheckedToDoubleBiFunction<T, U> function) {
        return Unchecked.toDoubleBiFunction(function, Unchecked.RETHROW_ALL);
    }

    public static <T, U> BiPredicate<T, U> biPredicate(CheckedBiPredicate<T, U> predicate) {
        return Unchecked.biPredicate(predicate, Unchecked.RETHROW_ALL);
    }

    public static <T> BinaryOperator<T> binaryOperator(CheckedBinaryOperator<T> operator) {
        return Unchecked.binaryOperator(operator, Unchecked.RETHROW_ALL);
    }

    public static IntBinaryOperator intBinaryOperator(CheckedIntBinaryOperator operator) {
        return Unchecked.intBinaryOperator(operator, Unchecked.RETHROW_ALL);
    }

    public static LongBinaryOperator longBinaryOperator(CheckedLongBinaryOperator operator) {
        return Unchecked.longBinaryOperator(operator, Unchecked.RETHROW_ALL);
    }

    public static DoubleBinaryOperator doubleBinaryOperator(CheckedDoubleBinaryOperator operator) {
        return Unchecked.doubleBinaryOperator(operator, Unchecked.RETHROW_ALL);
    }

    public static <T> Consumer<T> consumer(CheckedConsumer<T> consumer) {
        return Unchecked.consumer(consumer, Unchecked.RETHROW_ALL);
    }

    public static IntConsumer intConsumer(CheckedIntConsumer consumer) {
        return Unchecked.intConsumer(consumer, Unchecked.RETHROW_ALL);
    }

    public static LongConsumer longConsumer(CheckedLongConsumer consumer) {
        return Unchecked.longConsumer(consumer, Unchecked.RETHROW_ALL);
    }

    public static DoubleConsumer doubleConsumer(CheckedDoubleConsumer consumer) {
        return Unchecked.doubleConsumer(consumer, Unchecked.RETHROW_ALL);
    }

    public static <T, R> Function<T, R> function(CheckedFunction<T, R> function) {
        return Unchecked.function(function, Unchecked.RETHROW_ALL);
    }

    public static <T> ToIntFunction<T> toIntFunction(CheckedToIntFunction<T> function) {
        return Unchecked.toIntFunction(function, Unchecked.RETHROW_ALL);
    }

    public static <T> ToLongFunction<T> toLongFunction(CheckedToLongFunction<T> function) {
        return Unchecked.toLongFunction(function, Unchecked.RETHROW_ALL);
    }

    public static <T> ToDoubleFunction<T> toDoubleFunction(CheckedToDoubleFunction<T> function) {
        return Unchecked.toDoubleFunction(function, Unchecked.RETHROW_ALL);
    }

    public static <R> IntFunction<R> intFunction(CheckedIntFunction<R> function) {
        return Unchecked.intFunction(function, Unchecked.RETHROW_ALL);
    }

    public static IntToLongFunction intToLongFunction(CheckedIntToLongFunction function) {
        return Unchecked.intToLongFunction(function, Unchecked.RETHROW_ALL);
    }

    public static IntToDoubleFunction intToDoubleFunction(CheckedIntToDoubleFunction function) {
        return Unchecked.intToDoubleFunction(function, Unchecked.RETHROW_ALL);
    }

    public static <R> LongFunction<R> longFunction(CheckedLongFunction<R> function) {
        return Unchecked.longFunction(function, Unchecked.RETHROW_ALL);
    }

    public static LongToIntFunction longToIntFunction(CheckedLongToIntFunction function) {
        return Unchecked.longToIntFunction(function, Unchecked.RETHROW_ALL);
    }

    public static LongToDoubleFunction longToDoubleFunction(CheckedLongToDoubleFunction function) {
        return Unchecked.longToDoubleFunction(function, Unchecked.RETHROW_ALL);
    }

    public static <R> DoubleFunction<R> doubleFunction(CheckedDoubleFunction<R> function) {
        return Unchecked.doubleFunction(function, Unchecked.RETHROW_ALL);
    }

    public static DoubleToIntFunction doubleToIntFunction(CheckedDoubleToIntFunction function) {
        return Unchecked.doubleToIntFunction(function, Unchecked.RETHROW_ALL);
    }

    public static DoubleToLongFunction doubleToLongFunction(CheckedDoubleToLongFunction function) {
        return Unchecked.doubleToLongFunction(function, Unchecked.RETHROW_ALL);
    }

    public static <T> Predicate<T> predicate(CheckedPredicate<T> predicate) {
        return Unchecked.predicate(predicate, Unchecked.RETHROW_ALL);
    }

    public static IntPredicate intPredicate(CheckedIntPredicate predicate) {
        return Unchecked.intPredicate(predicate, Unchecked.RETHROW_ALL);
    }

    public static LongPredicate longPredicate(CheckedLongPredicate predicate) {
        return Unchecked.longPredicate(predicate, Unchecked.RETHROW_ALL);
    }

    public static DoublePredicate doublePredicate(CheckedDoublePredicate predicate) {
        return Unchecked.doublePredicate(predicate, Unchecked.RETHROW_ALL);
    }

    public static <T> Supplier<T> supplier(CheckedSupplier<T> supplier) {
        return Unchecked.supplier(supplier, Unchecked.RETHROW_ALL);
    }

    public static IntSupplier intSupplier(CheckedIntSupplier supplier) {
        return Unchecked.intSupplier(supplier, Unchecked.RETHROW_ALL);
    }

    public static LongSupplier longSupplier(CheckedLongSupplier supplier) {
        return Unchecked.longSupplier(supplier, Unchecked.RETHROW_ALL);
    }

    public static DoubleSupplier doubleSupplier(CheckedDoubleSupplier supplier) {
        return Unchecked.doubleSupplier(supplier, Unchecked.RETHROW_ALL);
    }

    public static BooleanSupplier booleanSupplier(CheckedBooleanSupplier supplier) {
        return Unchecked.booleanSupplier(supplier, Unchecked.RETHROW_ALL);
    }

    public static <T> UnaryOperator<T> unaryOperator(CheckedUnaryOperator<T> operator) {
        return Unchecked.unaryOperator(operator, Unchecked.RETHROW_ALL);
    }

    public static IntUnaryOperator intUnaryOperator(CheckedIntUnaryOperator operator) {
        return Unchecked.intUnaryOperator(operator, Unchecked.RETHROW_ALL);
    }

    public static LongUnaryOperator longUnaryOperator(CheckedLongUnaryOperator operator) {
        return Unchecked.longUnaryOperator(operator, Unchecked.RETHROW_ALL);
    }

    public static DoubleUnaryOperator doubleUnaryOperator(CheckedDoubleUnaryOperator operator) {
        return Unchecked.doubleUnaryOperator(operator, Unchecked.RETHROW_ALL);
    }

    private Sneaky() {
    }
}

