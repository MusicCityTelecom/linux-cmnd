/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.micrometer.core.instrument.Clock
 *  io.micrometer.core.instrument.MeterRegistry
 *  io.micrometer.core.instrument.Tags
 *  io.micrometer.core.instrument.Timer
 *  io.micrometer.core.instrument.Timer$Sample
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.Timer;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.FluxMetrics;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalMonoOperator;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;
import reactor.util.Metrics;

final class MonoMetrics<T>
extends InternalMonoOperator<T, T> {
    final String name;
    final Tags tags;
    final MeterRegistry registryCandidate;

    MonoMetrics(Mono<? extends T> mono) {
        super(mono);
        this.name = FluxMetrics.resolveName(mono);
        this.tags = FluxMetrics.resolveTags(mono, FluxMetrics.DEFAULT_TAGS_MONO);
        this.registryCandidate = Metrics.MicrometerConfiguration.getRegistry();
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        return new MetricsSubscriber<T>(actual, this.registryCandidate, Clock.SYSTEM, this.name, this.tags);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static class MetricsSubscriber<T>
    implements InnerOperator<T, T> {
        final CoreSubscriber<? super T> actual;
        final Clock clock;
        final String sequenceName;
        final Tags commonTags;
        final MeterRegistry registry;
        Timer.Sample subscribeToTerminateSample;
        boolean done;
        Subscription s;

        MetricsSubscriber(CoreSubscriber<? super T> actual, MeterRegistry registry, Clock clock, String sequenceName, Tags commonTags) {
            this.actual = actual;
            this.clock = clock;
            this.sequenceName = sequenceName;
            this.commonTags = commonTags;
            this.registry = registry;
        }

        @Override
        public final CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        public final void cancel() {
            FluxMetrics.recordCancel(this.sequenceName, this.commonTags, this.registry, this.subscribeToTerminateSample);
            this.s.cancel();
        }

        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            FluxMetrics.recordOnCompleteEmpty(this.sequenceName, this.commonTags, this.registry, this.subscribeToTerminateSample);
            this.actual.onComplete();
        }

        public final void onError(Throwable e) {
            if (this.done) {
                FluxMetrics.recordMalformed(this.sequenceName, this.commonTags, this.registry);
                Operators.onErrorDropped(e, this.actual.currentContext());
                return;
            }
            this.done = true;
            FluxMetrics.recordOnError(this.sequenceName, this.commonTags, this.registry, this.subscribeToTerminateSample, e);
            this.actual.onError(e);
        }

        public void onNext(T t) {
            if (this.done) {
                FluxMetrics.recordMalformed(this.sequenceName, this.commonTags, this.registry);
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            this.done = true;
            FluxMetrics.recordOnComplete(this.sequenceName, this.commonTags, this.registry, this.subscribeToTerminateSample);
            this.actual.onNext(t);
            this.actual.onComplete();
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.s, s)) {
                FluxMetrics.recordOnSubscribe(this.sequenceName, this.commonTags, this.registry);
                this.subscribeToTerminateSample = Timer.start((Clock)this.clock);
                this.s = s;
                this.actual.onSubscribe(this);
            }
        }

        public final void request(long l) {
            if (Operators.validate(l)) {
                this.s.request(l);
            }
        }

        @Override
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }
    }
}

