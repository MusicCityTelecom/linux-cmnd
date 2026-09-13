/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 */
package reactor.core.publisher;

import java.util.Iterator;
import java.util.Objects;
import org.reactivestreams.Publisher;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.FluxFirstWithValue;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SourceProducer;
import reactor.util.annotation.Nullable;

final class MonoFirstWithValue<T>
extends Mono<T>
implements SourceProducer<T> {
    final Mono<? extends T>[] array;
    final Iterable<? extends Mono<? extends T>> iterable;

    private MonoFirstWithValue(Mono<? extends T>[] array) {
        this.array = Objects.requireNonNull(array, "array");
        this.iterable = null;
    }

    @SafeVarargs
    MonoFirstWithValue(Mono<? extends T> first, Mono<? extends T> ... others) {
        Objects.requireNonNull(first, "first");
        Objects.requireNonNull(others, "others");
        Mono[] newArray = new Mono[others.length + 1];
        newArray[0] = first;
        System.arraycopy(others, 0, newArray, 1, others.length);
        this.array = newArray;
        this.iterable = null;
    }

    MonoFirstWithValue(Iterable<? extends Mono<? extends T>> iterable) {
        this.array = null;
        this.iterable = Objects.requireNonNull(iterable);
    }

    @Nullable
    @SafeVarargs
    final MonoFirstWithValue<T> firstValuedAdditionalSources(Mono<? extends T> ... others) {
        Objects.requireNonNull(others, "others");
        if (others.length == 0) {
            return this;
        }
        if (this.array == null) {
            return null;
        }
        int currentSize = this.array.length;
        int otherSize = others.length;
        Mono[] newArray = new Mono[currentSize + otherSize];
        System.arraycopy(this.array, 0, newArray, 0, currentSize);
        System.arraycopy(others, 0, newArray, currentSize, otherSize);
        return new MonoFirstWithValue<T>(newArray);
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        int n;
        Mono<? extends T>[] a;
        block13: {
            a = this.array;
            if (a == null) {
                Iterator<Mono<T>> it;
                n = 0;
                a = new Publisher[8];
                try {
                    it = Objects.requireNonNull(this.iterable.iterator(), "The iterator returned is null");
                }
                catch (Throwable e) {
                    Operators.error(actual, Operators.onOperatorError(e, actual.currentContext()));
                    return;
                }
                while (true) {
                    Publisher p;
                    boolean b;
                    try {
                        b = it.hasNext();
                    }
                    catch (Throwable e) {
                        Operators.error(actual, Operators.onOperatorError(e, actual.currentContext()));
                        return;
                    }
                    if (!b) break block13;
                    try {
                        p = Objects.requireNonNull(it.next(), "The Publisher returned by the iterator is null");
                    }
                    catch (Throwable e) {
                        Operators.error(actual, Operators.onOperatorError(e, actual.currentContext()));
                        return;
                    }
                    if (n == a.length) {
                        Publisher[] c = new Publisher[n + (n >> 2)];
                        System.arraycopy(a, 0, c, 0, n);
                        a = c;
                    }
                    a[n++] = p;
                }
            }
            n = a.length;
        }
        if (n == 0) {
            Operators.complete(actual);
            return;
        }
        if (n == 1) {
            Mono<? extends T> p = a[0];
            if (p == null) {
                Operators.error(actual, Operators.onOperatorError(new NullPointerException("The single source Publisher is null"), actual.currentContext()));
            } else {
                p.subscribe(actual);
            }
            return;
        }
        FluxFirstWithValue.RaceValuesCoordinator<? extends T> coordinator = new FluxFirstWithValue.RaceValuesCoordinator<T>(n);
        coordinator.subscribe(a, n, actual);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }
}

