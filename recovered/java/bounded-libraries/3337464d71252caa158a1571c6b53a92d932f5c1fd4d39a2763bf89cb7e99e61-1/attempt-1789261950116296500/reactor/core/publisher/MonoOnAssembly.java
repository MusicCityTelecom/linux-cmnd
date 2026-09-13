/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 */
package reactor.core.publisher;

import org.reactivestreams.Publisher;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.AssemblyOp;
import reactor.core.publisher.FluxOnAssembly;
import reactor.core.publisher.InternalMonoOperator;
import reactor.core.publisher.Mono;

final class MonoOnAssembly<T>
extends InternalMonoOperator<T, T>
implements Fuseable,
AssemblyOp {
    final FluxOnAssembly.AssemblySnapshot stacktrace;

    MonoOnAssembly(Mono<? extends T> source, FluxOnAssembly.AssemblySnapshot stacktrace) {
        super(source);
        this.stacktrace = stacktrace;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        if (actual instanceof Fuseable.ConditionalSubscriber) {
            Fuseable.ConditionalSubscriber cs = (Fuseable.ConditionalSubscriber)actual;
            return new FluxOnAssembly.OnAssemblyConditionalSubscriber(cs, this.stacktrace, (Publisher<?>)this.source, (Publisher<?>)this);
        }
        return new FluxOnAssembly.OnAssemblySubscriber<T>(actual, this.stacktrace, this.source, this);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.ACTUAL_METADATA) {
            return !this.stacktrace.isCheckpoint;
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    @Override
    public String stepName() {
        return this.stacktrace.operatorAssemblyInformation();
    }

    @Override
    public String toString() {
        return this.stacktrace.operatorAssemblyInformation();
    }
}

