/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.micrometer.core.instrument.Clock
 *  io.micrometer.core.instrument.MeterRegistry
 *  io.micrometer.core.instrument.Tags
 *  io.micrometer.core.instrument.Timer
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.Timer;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.FluxMetrics;
import reactor.core.publisher.InternalMonoOperator;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoMetrics;
import reactor.core.publisher.Operators;
import reactor.util.Metrics;
import reactor.util.annotation.Nullable;

final class MonoMetricsFuseable<T>
extends InternalMonoOperator<T, T>
implements Fuseable {
    final String name;
    final Tags tags;
    final MeterRegistry registryCandidate;

    MonoMetricsFuseable(Mono<? extends T> mono) {
        super(mono);
        this.name = FluxMetrics.resolveName(mono);
        this.tags = FluxMetrics.resolveTags(mono, FluxMetrics.DEFAULT_TAGS_MONO);
        this.registryCandidate = Metrics.MicrometerConfiguration.getRegistry();
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        return new MetricsFuseableSubscriber<T>(actual, this.registryCandidate, Clock.SYSTEM, this.name, this.tags);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class MetricsFuseableSubscriber<T>
    extends MonoMetrics.MetricsSubscriber<T>
    implements Fuseable,
    Fuseable.QueueSubscription<T> {
        int mode;
        @Nullable
        Fuseable.QueueSubscription<T> qs;

        MetricsFuseableSubscriber(CoreSubscriber<? super T> actual, MeterRegistry registry, Clock clock, String sequenceName, Tags sequenceTags) {
            super(actual, registry, clock, sequenceName, sequenceTags);
        }

        @Override
        public void clear() {
            if (this.qs != null) {
                this.qs.clear();
            }
        }

        @Override
        public void onComplete() {
            if (this.mode == 2) {
                if (!this.done) {
                    FluxMetrics.recordOnCompleteEmpty(this.sequenceName, this.commonTags, this.registry, this.subscribeToTerminateSample);
                }
                this.actual.onComplete();
            } else {
                if (this.done) {
                    return;
                }
                this.done = true;
                FluxMetrics.recordOnCompleteEmpty(this.sequenceName, this.commonTags, this.registry, this.subscribeToTerminateSample);
                this.actual.onComplete();
            }
        }

        @Override
        public void onNext(T t) {
            if (this.mode == 2) {
                this.actual.onNext(null);
            } else {
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
        }

        @Override
        public boolean isEmpty() {
            return this.qs == null || this.qs.isEmpty();
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.s, s)) {
                FluxMetrics.recordOnSubscribe(this.sequenceName, this.commonTags, this.registry);
                this.subscribeToTerminateSample = Timer.start((Clock)this.clock);
                this.qs = Operators.as(s);
                this.s = s;
                this.actual.onSubscribe(this);
            }
        }

        @Override
        @Nullable
        public T poll() {
            if (this.qs == null) {
                return null;
            }
            try {
                Object v = this.qs.poll();
                if (!this.done) {
                    if (v == null && this.mode == 1) {
                        FluxMetrics.recordOnCompleteEmpty(this.sequenceName, this.commonTags, this.registry, this.subscribeToTerminateSample);
                    } else if (v != null) {
                        FluxMetrics.recordOnComplete(this.sequenceName, this.commonTags, this.registry, this.subscribeToTerminateSample);
                    }
                }
                this.done = true;
                return (T)v;
            }
            catch (Throwable e) {
                FluxMetrics.recordOnError(this.sequenceName, this.commonTags, this.registry, this.subscribeToTerminateSample, e);
                throw e;
            }
        }

        @Override
        public int requestFusion(int mode) {
            if (this.qs != null) {
                this.mode = this.qs.requestFusion(mode);
                return this.mode;
            }
            return 0;
        }

        @Override
        public int size() {
            return this.qs == null ? 0 : this.qs.size();
        }

        @Override
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return super.scanUnsafe(key);
        }
    }
}

