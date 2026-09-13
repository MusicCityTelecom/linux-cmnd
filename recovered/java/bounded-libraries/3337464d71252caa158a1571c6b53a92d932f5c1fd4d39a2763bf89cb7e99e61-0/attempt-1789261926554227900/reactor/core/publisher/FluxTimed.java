/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.core.publisher.Timed;
import reactor.core.scheduler.Scheduler;
import reactor.util.annotation.Nullable;

final class FluxTimed<T>
extends InternalFluxOperator<T, Timed<T>> {
    final Scheduler clock;

    FluxTimed(Flux<? extends T> source, Scheduler clock) {
        super(source);
        this.clock = clock;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super Timed<T>> actual) {
        return new TimedSubscriber(actual, this.clock);
    }

    @Override
    public int getPrefetch() {
        return 0;
    }

    @Override
    @Nullable
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class TimedSubscriber<T>
    implements InnerOperator<T, Timed<T>> {
        final CoreSubscriber<? super Timed<T>> actual;
        final Scheduler clock;
        long subscriptionNanos;
        long lastEventNanos;
        boolean done;
        Subscription s;

        TimedSubscriber(CoreSubscriber<? super Timed<T>> actual, Scheduler clock) {
            this.actual = actual;
            this.clock = clock;
        }

        @Override
        public CoreSubscriber<? super Timed<T>> actual() {
            return this.actual;
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.s, s)) {
                this.s = s;
                this.lastEventNanos = this.subscriptionNanos = this.clock.now(TimeUnit.NANOSECONDS);
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.currentContext());
                return;
            }
            long nowNanos = this.clock.now(TimeUnit.NANOSECONDS);
            long timestamp = this.clock.now(TimeUnit.MILLISECONDS);
            ImmutableTimed<T> timed = new ImmutableTimed<T>(nowNanos - this.subscriptionNanos, nowNanos - this.lastEventNanos, timestamp, t);
            this.lastEventNanos = nowNanos;
            this.actual.onNext(timed);
        }

        public void onError(Throwable throwable) {
            if (this.done) {
                Operators.onErrorDropped(throwable, this.currentContext());
                return;
            }
            this.done = true;
            this.actual.onError(throwable);
        }

        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.actual.onComplete();
        }

        public void request(long l) {
            if (Operators.validate(l)) {
                this.s.request(l);
            }
        }

        public void cancel() {
            this.s.cancel();
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }
    }

    static final class ImmutableTimed<T>
    implements Timed<T> {
        final long eventElapsedSinceSubscriptionNanos;
        final long eventElapsedNanos;
        final long eventTimestampEpochMillis;
        final T event;

        ImmutableTimed(long eventElapsedSinceSubscriptionNanos, long eventElapsedNanos, long eventTimestampEpochMillis, T event) {
            this.eventElapsedSinceSubscriptionNanos = eventElapsedSinceSubscriptionNanos;
            this.eventElapsedNanos = eventElapsedNanos;
            this.eventTimestampEpochMillis = eventTimestampEpochMillis;
            this.event = event;
        }

        @Override
        public T get() {
            return this.event;
        }

        @Override
        public Duration elapsed() {
            return Duration.ofNanos(this.eventElapsedNanos);
        }

        @Override
        public Duration elapsedSinceSubscription() {
            return Duration.ofNanos(this.eventElapsedSinceSubscriptionNanos);
        }

        @Override
        public Instant timestamp() {
            return Instant.ofEpochMilli(this.eventTimestampEpochMillis);
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            ImmutableTimed timed = (ImmutableTimed)o;
            return this.eventElapsedSinceSubscriptionNanos == timed.eventElapsedSinceSubscriptionNanos && this.eventElapsedNanos == timed.eventElapsedNanos && this.eventTimestampEpochMillis == timed.eventTimestampEpochMillis && this.event.equals(timed.event);
        }

        public int hashCode() {
            return Objects.hash(this.eventElapsedSinceSubscriptionNanos, this.eventElapsedNanos, this.eventTimestampEpochMillis, this.event);
        }

        public String toString() {
            return "Timed(" + this.event + "){eventElapsedNanos=" + this.eventElapsedNanos + ", eventElapsedSinceSubscriptionNanos=" + this.eventElapsedSinceSubscriptionNanos + ",  eventTimestampEpochMillis=" + this.eventTimestampEpochMillis + '}';
        }
    }
}

