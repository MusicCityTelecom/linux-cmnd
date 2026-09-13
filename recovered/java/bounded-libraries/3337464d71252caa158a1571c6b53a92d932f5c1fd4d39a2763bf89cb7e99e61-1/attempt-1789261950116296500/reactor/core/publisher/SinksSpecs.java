/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.time.Duration;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import reactor.core.Disposable;
import reactor.core.publisher.ContextHolder;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.ReplayProcessor;
import reactor.core.publisher.SinkEmptyMulticast;
import reactor.core.publisher.SinkEmptySerialized;
import reactor.core.publisher.SinkManyBestEffort;
import reactor.core.publisher.SinkManySerialized;
import reactor.core.publisher.SinkOneMulticast;
import reactor.core.publisher.SinkOneSerialized;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.UnicastManySinkNoBackpressure;
import reactor.core.publisher.UnicastProcessor;
import reactor.core.scheduler.Scheduler;
import reactor.util.concurrent.Queues;

final class SinksSpecs {
    static final Sinks.RootSpec UNSAFE_ROOT_SPEC = new UnsafeSpecImpl();
    static final DefaultSinksSpecs DEFAULT_SINKS = new DefaultSinksSpecs();

    SinksSpecs() {
    }

    static final class UnicastSpecImpl
    implements Sinks.UnicastSpec {
        final boolean serialized;

        UnicastSpecImpl(boolean serialized) {
            this.serialized = serialized;
        }

        <T, MANY extends Sinks.Many<T> & ContextHolder> Sinks.Many<T> wrapMany(MANY original) {
            if (this.serialized) {
                return new SinkManySerialized(original, original);
            }
            return original;
        }

        @Override
        public <T> Sinks.Many<T> onBackpressureBuffer() {
            UnicastProcessor original = UnicastProcessor.create();
            return this.wrapMany((Sinks.Many<T> & ContextHolder)original);
        }

        @Override
        public <T> Sinks.Many<T> onBackpressureBuffer(Queue<T> queue) {
            UnicastProcessor<T> original = UnicastProcessor.create(queue);
            return this.wrapMany((Sinks.Many<T> & ContextHolder)original);
        }

        @Override
        public <T> Sinks.Many<T> onBackpressureBuffer(Queue<T> queue, Disposable endCallback) {
            UnicastProcessor<T> original = UnicastProcessor.create(queue, endCallback);
            return this.wrapMany((Sinks.Many<T> & ContextHolder)original);
        }

        @Override
        public <T> Sinks.Many<T> onBackpressureError() {
            UnicastManySinkNoBackpressure original = UnicastManySinkNoBackpressure.create();
            return this.wrapMany((Sinks.Many<T> & ContextHolder)original);
        }
    }

    static final class DefaultSinksSpecs
    implements Sinks.ManySpec,
    Sinks.MulticastSpec,
    Sinks.MulticastReplaySpec {
        final Sinks.UnicastSpec unicastSpec = new UnicastSpecImpl(true);

        DefaultSinksSpecs() {
        }

        <T, EMPTY extends Sinks.Empty<T> & ContextHolder> Sinks.Empty<T> wrapEmpty(EMPTY original) {
            return new SinkEmptySerialized(original, original);
        }

        <T, ONE extends Sinks.One<T> & ContextHolder> Sinks.One<T> wrapOne(ONE original) {
            return new SinkOneSerialized(original, (ContextHolder)original);
        }

        <T, MANY extends Sinks.Many<T> & ContextHolder> Sinks.Many<T> wrapMany(MANY original) {
            return new SinkManySerialized(original, original);
        }

        Sinks.ManySpec many() {
            return this;
        }

        <T> Sinks.Empty<T> empty() {
            return this.wrapEmpty((Sinks.Empty<T> & ContextHolder)new SinkEmptyMulticast());
        }

        <T> Sinks.One<T> one() {
            return this.wrapOne((Sinks.One<T> & ContextHolder)new SinkOneMulticast());
        }

        @Override
        public Sinks.UnicastSpec unicast() {
            return this.unicastSpec;
        }

        @Override
        public Sinks.MulticastSpec multicast() {
            return this;
        }

        @Override
        public Sinks.MulticastReplaySpec replay() {
            return this;
        }

        @Override
        public <T> Sinks.Many<T> onBackpressureBuffer() {
            EmitterProcessor original = EmitterProcessor.create();
            return this.wrapMany((Sinks.Many<T> & ContextHolder)original);
        }

        @Override
        public <T> Sinks.Many<T> onBackpressureBuffer(int bufferSize) {
            EmitterProcessor original = EmitterProcessor.create(bufferSize);
            return this.wrapMany((Sinks.Many<T> & ContextHolder)original);
        }

        @Override
        public <T> Sinks.Many<T> onBackpressureBuffer(int bufferSize, boolean autoCancel) {
            EmitterProcessor original = EmitterProcessor.create(bufferSize, autoCancel);
            return this.wrapMany((Sinks.Many<T> & ContextHolder)original);
        }

        @Override
        public <T> Sinks.Many<T> directAllOrNothing() {
            SinkManyBestEffort original = SinkManyBestEffort.createAllOrNothing();
            return this.wrapMany((Sinks.Many<T> & ContextHolder)original);
        }

        @Override
        public <T> Sinks.Many<T> directBestEffort() {
            SinkManyBestEffort original = SinkManyBestEffort.createBestEffort();
            return this.wrapMany((Sinks.Many<T> & ContextHolder)original);
        }

        @Override
        public <T> Sinks.Many<T> all() {
            ReplayProcessor original = ReplayProcessor.create();
            return this.wrapMany((Sinks.Many<T> & ContextHolder)original);
        }

        @Override
        public <T> Sinks.Many<T> all(int batchSize) {
            ReplayProcessor original = ReplayProcessor.create(batchSize, true);
            return this.wrapMany((Sinks.Many<T> & ContextHolder)original);
        }

        @Override
        public <T> Sinks.Many<T> latest() {
            ReplayProcessor original = ReplayProcessor.cacheLast();
            return this.wrapMany((Sinks.Many<T> & ContextHolder)original);
        }

        @Override
        public <T> Sinks.Many<T> latestOrDefault(T value) {
            ReplayProcessor<T> original = ReplayProcessor.cacheLastOrDefault(value);
            return this.wrapMany((Sinks.Many<T> & ContextHolder)original);
        }

        @Override
        public <T> Sinks.Many<T> limit(int historySize) {
            if (historySize <= 0) {
                throw new IllegalArgumentException("historySize must be > 0");
            }
            ReplayProcessor original = ReplayProcessor.create(historySize);
            return this.wrapMany((Sinks.Many<T> & ContextHolder)original);
        }

        @Override
        public <T> Sinks.Many<T> limit(Duration maxAge) {
            ReplayProcessor original = ReplayProcessor.createTimeout(maxAge);
            return this.wrapMany((Sinks.Many<T> & ContextHolder)original);
        }

        @Override
        public <T> Sinks.Many<T> limit(Duration maxAge, Scheduler scheduler) {
            ReplayProcessor original = ReplayProcessor.createTimeout(maxAge, scheduler);
            return this.wrapMany((Sinks.Many<T> & ContextHolder)original);
        }

        @Override
        public <T> Sinks.Many<T> limit(int historySize, Duration maxAge) {
            if (historySize <= 0) {
                throw new IllegalArgumentException("historySize must be > 0");
            }
            ReplayProcessor original = ReplayProcessor.createSizeAndTimeout(historySize, maxAge);
            return this.wrapMany((Sinks.Many<T> & ContextHolder)original);
        }

        @Override
        public <T> Sinks.Many<T> limit(int historySize, Duration maxAge, Scheduler scheduler) {
            if (historySize <= 0) {
                throw new IllegalArgumentException("historySize must be > 0");
            }
            ReplayProcessor original = ReplayProcessor.createSizeAndTimeout(historySize, maxAge, scheduler);
            return this.wrapMany((Sinks.Many<T> & ContextHolder)original);
        }
    }

    static final class UnsafeSpecImpl
    implements Sinks.RootSpec,
    Sinks.ManySpec,
    Sinks.ManyWithUpstreamUnsafeSpec,
    Sinks.MulticastSpec,
    Sinks.MulticastReplaySpec {
        final Sinks.UnicastSpec unicastSpec = new UnicastSpecImpl(false);

        UnsafeSpecImpl() {
        }

        @Override
        public <T> Sinks.Empty<T> empty() {
            return new SinkEmptyMulticast();
        }

        @Override
        public <T> Sinks.One<T> one() {
            return new SinkOneMulticast();
        }

        @Override
        public Sinks.ManySpec many() {
            return this;
        }

        @Override
        public Sinks.UnicastSpec unicast() {
            return this.unicastSpec;
        }

        @Override
        public Sinks.MulticastSpec multicast() {
            return this;
        }

        @Override
        public Sinks.MulticastReplaySpec replay() {
            return this;
        }

        @Override
        public Sinks.ManyWithUpstreamUnsafeSpec manyWithUpstream() {
            return this;
        }

        @Override
        public <T> Sinks.Many<T> onBackpressureBuffer() {
            return new EmitterProcessor(true, Queues.SMALL_BUFFER_SIZE);
        }

        @Override
        public <T> Sinks.Many<T> onBackpressureBuffer(int bufferSize) {
            return new EmitterProcessor(true, bufferSize);
        }

        @Override
        public <T> Sinks.Many<T> onBackpressureBuffer(int bufferSize, boolean autoCancel) {
            return new EmitterProcessor(autoCancel, bufferSize);
        }

        @Override
        public <T> Sinks.Many<T> directAllOrNothing() {
            return new SinkManyBestEffort(true);
        }

        @Override
        public <T> Sinks.Many<T> directBestEffort() {
            return new SinkManyBestEffort(false);
        }

        @Override
        public <T> Sinks.Many<T> all() {
            return ReplayProcessor.create();
        }

        @Override
        public <T> Sinks.Many<T> all(int batchSize) {
            return ReplayProcessor.create(batchSize);
        }

        @Override
        public <T> Sinks.Many<T> latest() {
            return ReplayProcessor.cacheLast();
        }

        @Override
        public <T> Sinks.Many<T> latestOrDefault(T value) {
            return ReplayProcessor.cacheLastOrDefault(value);
        }

        @Override
        public <T> Sinks.Many<T> limit(int historySize) {
            return ReplayProcessor.create(historySize);
        }

        @Override
        public <T> Sinks.Many<T> limit(Duration maxAge) {
            return ReplayProcessor.createTimeout(maxAge);
        }

        @Override
        public <T> Sinks.Many<T> limit(Duration maxAge, Scheduler scheduler) {
            return ReplayProcessor.createTimeout(maxAge, scheduler);
        }

        @Override
        public <T> Sinks.Many<T> limit(int historySize, Duration maxAge) {
            return ReplayProcessor.createSizeAndTimeout(historySize, maxAge);
        }

        @Override
        public <T> Sinks.Many<T> limit(int historySize, Duration maxAge, Scheduler scheduler) {
            return ReplayProcessor.createSizeAndTimeout(historySize, maxAge, scheduler);
        }

        @Override
        public <T> Sinks.ManyWithUpstream<T> multicastOnBackpressureBuffer() {
            return new EmitterProcessor(true, Queues.SMALL_BUFFER_SIZE);
        }

        @Override
        public <T> Sinks.ManyWithUpstream<T> multicastOnBackpressureBuffer(int bufferSize, boolean autoCancel) {
            return new EmitterProcessor(autoCancel, bufferSize);
        }
    }

    static abstract class AbstractSerializedSink {
        volatile int wip;
        static final AtomicIntegerFieldUpdater<AbstractSerializedSink> WIP = AtomicIntegerFieldUpdater.newUpdater(AbstractSerializedSink.class, "wip");
        volatile Thread lockedAt;
        static final AtomicReferenceFieldUpdater<AbstractSerializedSink, Thread> LOCKED_AT = AtomicReferenceFieldUpdater.newUpdater(AbstractSerializedSink.class, Thread.class, "lockedAt");

        AbstractSerializedSink() {
        }

        boolean tryAcquire(Thread currentThread) {
            if (WIP.get(this) == 0 && WIP.compareAndSet(this, 0, 1)) {
                LOCKED_AT.lazySet(this, currentThread);
            } else {
                if (LOCKED_AT.get(this) != currentThread) {
                    return false;
                }
                WIP.incrementAndGet(this);
            }
            return true;
        }
    }
}

