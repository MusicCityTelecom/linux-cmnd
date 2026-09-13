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
import reactor.core.publisher.FluxFirstWithSignal;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SourceProducer;
import reactor.util.annotation.Nullable;

final class MonoFirstWithSignal<T>
extends Mono<T>
implements SourceProducer<T> {
    final Mono<? extends T>[] array;
    final Iterable<? extends Mono<? extends T>> iterable;

    @SafeVarargs
    MonoFirstWithSignal(Mono<? extends T> ... array) {
        this.array = Objects.requireNonNull(array, "array");
        this.iterable = null;
    }

    MonoFirstWithSignal(Iterable<? extends Mono<? extends T>> iterable) {
        this.array = null;
        this.iterable = Objects.requireNonNull(iterable);
    }

    @Nullable
    Mono<T> orAdditionalSource(Mono<? extends T> other) {
        if (this.array != null) {
            int n = this.array.length;
            Mono[] newArray = new Mono[n + 1];
            System.arraycopy(this.array, 0, newArray, 0, n);
            newArray[n] = other;
            return new MonoFirstWithSignal<T>(newArray);
        }
        return null;
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
        FluxFirstWithSignal.RaceCoordinator<? extends T> coordinator = new FluxFirstWithSignal.RaceCoordinator<T>(n);
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

