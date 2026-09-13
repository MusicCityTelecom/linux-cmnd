/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 */
package reactor.core.publisher;

import java.time.Duration;
import java.util.Queue;
import org.reactivestreams.Publisher;
import reactor.core.Disposable;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;
import reactor.core.publisher.SinksSpecs;
import reactor.core.scheduler.Scheduler;
import reactor.util.annotation.Nullable;

public final class Sinks {
    private Sinks() {
    }

    public static <T> Empty<T> empty() {
        return SinksSpecs.DEFAULT_SINKS.empty();
    }

    public static <T> One<T> one() {
        return SinksSpecs.DEFAULT_SINKS.one();
    }

    public static ManySpec many() {
        return SinksSpecs.DEFAULT_SINKS.many();
    }

    public static RootSpec unsafe() {
        return SinksSpecs.UNSAFE_ROOT_SPEC;
    }

    public static interface One<T>
    extends Empty<T> {
        public EmitResult tryEmitValue(@Nullable T var1);

        public void emitValue(@Nullable T var1, EmitFailureHandler var2);
    }

    public static interface Empty<T>
    extends Scannable {
        public EmitResult tryEmitEmpty();

        public EmitResult tryEmitError(Throwable var1);

        public void emitEmpty(EmitFailureHandler var1);

        public void emitError(Throwable var1, EmitFailureHandler var2);

        public int currentSubscriberCount();

        public Mono<T> asMono();
    }

    public static interface ManyWithUpstream<T>
    extends Many<T> {
        public Disposable subscribeTo(Publisher<? extends T> var1);
    }

    public static interface Many<T>
    extends Scannable {
        public EmitResult tryEmitNext(T var1);

        public EmitResult tryEmitComplete();

        public EmitResult tryEmitError(Throwable var1);

        public void emitNext(T var1, EmitFailureHandler var2);

        public void emitComplete(EmitFailureHandler var1);

        public void emitError(Throwable var1, EmitFailureHandler var2);

        public int currentSubscriberCount();

        public Flux<T> asFlux();
    }

    public static interface MulticastReplaySpec {
        public <T> Many<T> all();

        public <T> Many<T> all(int var1);

        public <T> Many<T> latest();

        public <T> Many<T> latestOrDefault(T var1);

        public <T> Many<T> limit(int var1);

        public <T> Many<T> limit(Duration var1);

        public <T> Many<T> limit(Duration var1, Scheduler var2);

        public <T> Many<T> limit(int var1, Duration var2);

        public <T> Many<T> limit(int var1, Duration var2, Scheduler var3);
    }

    public static interface MulticastSpec {
        public <T> Many<T> onBackpressureBuffer();

        public <T> Many<T> onBackpressureBuffer(int var1);

        public <T> Many<T> onBackpressureBuffer(int var1, boolean var2);

        public <T> Many<T> directAllOrNothing();

        public <T> Many<T> directBestEffort();
    }

    public static interface UnicastSpec {
        public <T> Many<T> onBackpressureBuffer();

        public <T> Many<T> onBackpressureBuffer(Queue<T> var1);

        public <T> Many<T> onBackpressureBuffer(Queue<T> var1, Disposable var2);

        public <T> Many<T> onBackpressureError();
    }

    public static interface ManyWithUpstreamUnsafeSpec {
        public <T> ManyWithUpstream<T> multicastOnBackpressureBuffer();

        public <T> ManyWithUpstream<T> multicastOnBackpressureBuffer(int var1, boolean var2);
    }

    public static interface ManySpec {
        public UnicastSpec unicast();

        public MulticastSpec multicast();

        public MulticastReplaySpec replay();
    }

    public static interface RootSpec {
        public <T> Empty<T> empty();

        public <T> One<T> one();

        public ManySpec many();

        public ManyWithUpstreamUnsafeSpec manyWithUpstream();
    }

    public static interface EmitFailureHandler {
        public static final EmitFailureHandler FAIL_FAST = (signalType, emission) -> false;

        public static EmitFailureHandler busyLooping(Duration duration) {
            return new OptimisticEmitFailureHandler(duration);
        }

        public boolean onEmitFailure(SignalType var1, EmitResult var2);
    }

    static class OptimisticEmitFailureHandler
    implements EmitFailureHandler {
        private final long deadline;

        OptimisticEmitFailureHandler(Duration duration) {
            this.deadline = System.nanoTime() + duration.toNanos();
        }

        @Override
        public boolean onEmitFailure(SignalType signalType, EmitResult emitResult) {
            return emitResult.equals((Object)EmitResult.FAIL_NON_SERIALIZED) && System.nanoTime() < this.deadline;
        }
    }

    public static final class EmissionException
    extends IllegalStateException {
        final EmitResult reason;

        public EmissionException(EmitResult reason) {
            this(reason, "Sink emission failed with " + (Object)((Object)reason));
        }

        public EmissionException(Throwable cause, EmitResult reason) {
            super("Sink emission failed with " + (Object)((Object)reason), cause);
            this.reason = reason;
        }

        public EmissionException(EmitResult reason, String message) {
            super(message);
            this.reason = reason;
        }

        public EmitResult getReason() {
            return this.reason;
        }
    }

    public static enum EmitResult {
        OK,
        FAIL_TERMINATED,
        FAIL_OVERFLOW,
        FAIL_CANCELLED,
        FAIL_NON_SERIALIZED,
        FAIL_ZERO_SUBSCRIBER;


        public boolean isSuccess() {
            return this == OK;
        }

        public boolean isFailure() {
            return this != OK;
        }

        public void orThrow() {
            if (this == OK) {
                return;
            }
            throw new EmissionException(this);
        }

        public void orThrowWithCause(Throwable cause) {
            if (this == OK) {
                return;
            }
            throw new EmissionException(cause, this);
        }
    }
}

