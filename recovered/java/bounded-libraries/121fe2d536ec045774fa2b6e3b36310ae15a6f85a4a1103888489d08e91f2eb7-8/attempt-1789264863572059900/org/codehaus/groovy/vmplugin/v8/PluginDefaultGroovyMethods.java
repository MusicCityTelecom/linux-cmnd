/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.vmplugin.v8;

import groovy.lang.Closure;
import groovy.lang.EmptyRange;
import groovy.lang.GString;
import groovy.lang.IntRange;
import groovy.transform.stc.ClosureParams;
import groovy.transform.stc.FirstParam;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.Set;
import java.util.Spliterator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.BaseStream;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import org.codehaus.groovy.runtime.DefaultGroovyMethodsSupport;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.codehaus.groovy.runtime.NullObject;
import org.codehaus.groovy.runtime.RangeInfo;
import org.codehaus.groovy.runtime.StreamGroovyMethods;

public class PluginDefaultGroovyMethods
extends DefaultGroovyMethodsSupport {
    private PluginDefaultGroovyMethods() {
    }

    public static Object next(Enum self) {
        Object[] values;
        for (Method method : self.getClass().getMethods()) {
            if (!method.getName().equals("next") || method.getParameterCount() != 0) continue;
            return InvokerHelper.invokeMethod(self, "next", InvokerHelper.EMPTY_ARGS);
        }
        int index = Arrays.asList(values = (Object[])InvokerHelper.invokeStaticMethod(self.getClass(), "values", (Object)InvokerHelper.EMPTY_ARGS)).indexOf(self);
        return values[index < values.length - 1 ? index + 1 : 0];
    }

    public static Object previous(Enum self) {
        Object[] values;
        for (Method method : self.getClass().getMethods()) {
            if (!method.getName().equals("previous") || method.getParameterCount() != 0) continue;
            return InvokerHelper.invokeMethod(self, "previous", InvokerHelper.EMPTY_ARGS);
        }
        int index = Arrays.asList(values = (Object[])InvokerHelper.invokeStaticMethod(self.getClass(), "values", (Object)InvokerHelper.EMPTY_ARGS)).indexOf(self);
        return values[index > 0 ? index - 1 : values.length - 1];
    }

    public static <S, T> Future<T> collect(Future<S> self, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure<T> transform) {
        Objects.requireNonNull(self);
        Objects.requireNonNull(transform);
        return new TransformedFuture(self, transform);
    }

    public static boolean asBoolean(Optional<?> optional) {
        return optional.isPresent();
    }

    public static int get(OptionalInt self) {
        return self.getAsInt();
    }

    public static long get(OptionalLong self) {
        return self.getAsLong();
    }

    public static double get(OptionalDouble self) {
        return self.getAsDouble();
    }

    public static <S, T> Optional<T> collect(Optional<S> self, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure<T> transform) {
        Objects.requireNonNull(self);
        Objects.requireNonNull(transform);
        return self.map(transform::call);
    }

    public static <T> Optional<T> filter(Optional<?> self, Class<T> type) {
        return self.filter(type::isInstance).map(type::cast);
    }

    public static OptionalInt filter(OptionalInt self, IntPredicate test) {
        if (!self.isPresent() || !test.test(self.getAsInt())) {
            return OptionalInt.empty();
        }
        return self;
    }

    public static OptionalLong filter(OptionalLong self, LongPredicate test) {
        if (!self.isPresent() || !test.test(self.getAsLong())) {
            return OptionalLong.empty();
        }
        return self;
    }

    public static OptionalDouble filter(OptionalDouble self, DoublePredicate test) {
        if (!self.isPresent() || !test.test(self.getAsDouble())) {
            return OptionalDouble.empty();
        }
        return self;
    }

    public static <T> Optional<T> mapToObj(OptionalInt self, IntFunction<? extends T> mapper) {
        if (!self.isPresent()) {
            return Optional.empty();
        }
        return Optional.ofNullable(mapper.apply(self.getAsInt()));
    }

    public static <T> Optional<T> mapToObj(OptionalLong self, LongFunction<? extends T> mapper) {
        if (!self.isPresent()) {
            return Optional.empty();
        }
        return Optional.ofNullable(mapper.apply(self.getAsLong()));
    }

    public static <T> Optional<T> mapToObj(OptionalDouble self, DoubleFunction<? extends T> mapper) {
        if (!self.isPresent()) {
            return Optional.empty();
        }
        return Optional.ofNullable(mapper.apply(self.getAsDouble()));
    }

    public static <T> OptionalInt mapToInt(Optional<T> self, ToIntFunction<? super T> mapper) {
        return self.map(t -> OptionalInt.of(mapper.applyAsInt(t))).orElseGet(OptionalInt::empty);
    }

    public static <T> OptionalLong mapToLong(Optional<T> self, ToLongFunction<? super T> mapper) {
        return self.map(t -> OptionalLong.of(mapper.applyAsLong(t))).orElseGet(OptionalLong::empty);
    }

    public static <T> OptionalDouble mapToDouble(Optional<T> self, ToDoubleFunction<? super T> mapper) {
        return self.map(t -> OptionalDouble.of(mapper.applyAsDouble(t))).orElseGet(OptionalDouble::empty);
    }

    public static <T> Optional<T> orOptional(Optional<T> self, Supplier<Optional<? extends T>> supplier) {
        if (self.isPresent()) {
            return self;
        }
        return supplier.get();
    }

    public static String getPid(Runtime self) {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        int index = name.indexOf(64);
        if (index == -1) {
            return name;
        }
        return name.substring(0, index);
    }

    public static StringBuilder leftShift(StringBuilder self, Object value) {
        if (value instanceof GString) {
            return self.append(value.toString());
        }
        if (value instanceof CharSequence) {
            return self.append((CharSequence)value);
        }
        return self.append(value);
    }

    public static String plus(StringBuilder self, String value) {
        return self + value;
    }

    public static void putAt(StringBuilder self, EmptyRange range, Object value) {
        RangeInfo info = PluginDefaultGroovyMethods.subListBorders(self.length(), range);
        self.replace(info.from, info.to, value.toString());
    }

    public static void putAt(StringBuilder self, IntRange range, Object value) {
        RangeInfo info = PluginDefaultGroovyMethods.subListBorders(self.length(), range);
        self.replace(info.from, info.to, value.toString());
    }

    @Deprecated
    public static int size(StringBuilder self) {
        return self.length();
    }

    @Deprecated
    public static <T> Stream<T> stream(T self) {
        return Stream.of(self);
    }

    @Deprecated
    public static <T> Stream<T> stream(T[] self) {
        return Arrays.stream(self);
    }

    @Deprecated
    public static Stream<Integer> stream(int[] self) {
        return StreamGroovyMethods.stream(self);
    }

    @Deprecated
    public static Stream<Long> stream(long[] self) {
        return StreamGroovyMethods.stream(self);
    }

    @Deprecated
    public static Stream<Double> stream(double[] self) {
        return StreamGroovyMethods.stream(self);
    }

    @Deprecated
    public static Stream<Character> stream(char[] self) {
        return StreamGroovyMethods.stream(self);
    }

    @Deprecated
    public static Stream<Byte> stream(byte[] self) {
        return StreamGroovyMethods.stream(self);
    }

    @Deprecated
    public static Stream<Short> stream(short[] self) {
        return StreamGroovyMethods.stream(self);
    }

    @Deprecated
    public static Stream<Boolean> stream(boolean[] self) {
        return StreamGroovyMethods.stream(self);
    }

    @Deprecated
    public static Stream<Float> stream(float[] self) {
        return StreamGroovyMethods.stream(self);
    }

    @Deprecated
    public static <T> Stream<T> stream(Enumeration<T> self) {
        return StreamGroovyMethods.stream(self);
    }

    @Deprecated
    public static <T> Stream<T> stream(Iterable<T> self) {
        return StreamGroovyMethods.stream(self);
    }

    @Deprecated
    public static <T> Stream<T> stream(Iterator<T> self) {
        return StreamGroovyMethods.stream(self);
    }

    @Deprecated
    public static <T> Stream<T> stream(Spliterator<T> self) {
        return StreamGroovyMethods.stream(self);
    }

    @Deprecated
    public static <T> Stream<T> stream(NullObject self) {
        return Stream.empty();
    }

    @Deprecated
    public static <T> Stream<T> stream(Optional<T> self) {
        return StreamGroovyMethods.stream(self);
    }

    @Deprecated
    public static IntStream stream(OptionalInt self) {
        return StreamGroovyMethods.stream(self);
    }

    @Deprecated
    public static LongStream stream(OptionalLong self) {
        return StreamGroovyMethods.stream(self);
    }

    @Deprecated
    public static DoubleStream stream(OptionalDouble self) {
        return StreamGroovyMethods.stream(self);
    }

    @Deprecated
    public static IntStream intStream(int[] self) {
        return Arrays.stream(self);
    }

    @Deprecated
    public static LongStream longStream(long[] self) {
        return Arrays.stream(self);
    }

    @Deprecated
    public static DoubleStream doubleStream(double[] self) {
        return Arrays.stream(self);
    }

    @Deprecated
    public static <T> T[] toArray(Stream<? extends T> self, Class<T> type) {
        return StreamGroovyMethods.toArray(self, type);
    }

    @Deprecated
    public static <T> List<T> toList(Stream<T> self) {
        return StreamGroovyMethods.toList(self);
    }

    @Deprecated
    public static <T> List<T> toList(BaseStream<T, ? extends BaseStream> self) {
        return StreamGroovyMethods.toList(self);
    }

    @Deprecated
    public static <T> Set<T> toSet(Stream<T> self) {
        return StreamGroovyMethods.toSet(self);
    }

    @Deprecated
    public static <T> Set<T> toSet(BaseStream<T, ? extends BaseStream> self) {
        return StreamGroovyMethods.toSet(self);
    }

    private static class TransformedFuture<E>
    implements Future<E> {
        private final Future delegate;
        private final Closure<E> transform;

        private TransformedFuture(Future delegate, Closure<E> transform) {
            this.delegate = delegate;
            this.transform = transform;
        }

        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            return this.delegate.cancel(mayInterruptIfRunning);
        }

        @Override
        public boolean isCancelled() {
            return this.delegate.isCancelled();
        }

        @Override
        public boolean isDone() {
            return this.delegate.isDone();
        }

        @Override
        public E get() throws InterruptedException, ExecutionException {
            return this.transform.call(this.delegate.get());
        }

        @Override
        public E get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return this.transform.call(this.delegate.get(timeout, unit));
        }
    }
}

