/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.time.Duration;
import java.util.function.Consumer;
import reactor.core.Disposable;
import reactor.core.Fuseable;
import reactor.core.publisher.ConnectableFluxHide;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxAutoConnect;
import reactor.core.publisher.FluxAutoConnectFuseable;
import reactor.core.publisher.FluxRefCount;
import reactor.core.publisher.FluxRefCountGrace;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

public abstract class ConnectableFlux<T>
extends Flux<T> {
    static final Consumer<Disposable> NOOP_DISCONNECT = runnable -> {};

    public final Flux<T> autoConnect() {
        return this.autoConnect(1);
    }

    public final Flux<T> autoConnect(int minSubscribers) {
        return this.autoConnect(minSubscribers, NOOP_DISCONNECT);
    }

    public final Flux<T> autoConnect(int minSubscribers, Consumer<? super Disposable> cancelSupport) {
        if (minSubscribers == 0) {
            this.connect(cancelSupport);
            return this;
        }
        if (this instanceof Fuseable) {
            return ConnectableFlux.onAssembly(new FluxAutoConnectFuseable(this, minSubscribers, cancelSupport));
        }
        return ConnectableFlux.onAssembly(new FluxAutoConnect(this, minSubscribers, cancelSupport));
    }

    public final Disposable connect() {
        Disposable[] out = new Disposable[]{null};
        this.connect(r -> {
            out[0] = r;
        });
        return out[0];
    }

    public abstract void connect(Consumer<? super Disposable> var1);

    @Override
    public final ConnectableFlux<T> hide() {
        return new ConnectableFluxHide(this);
    }

    public final Flux<T> refCount() {
        return this.refCount(1);
    }

    public final Flux<T> refCount(int minSubscribers) {
        return ConnectableFlux.onAssembly(new FluxRefCount(this, minSubscribers));
    }

    public final Flux<T> refCount(int minSubscribers, Duration gracePeriod) {
        return this.refCount(minSubscribers, gracePeriod, Schedulers.parallel());
    }

    public final Flux<T> refCount(int minSubscribers, Duration gracePeriod, Scheduler scheduler) {
        return ConnectableFlux.onAssembly(new FluxRefCountGrace(this, minSubscribers, gracePeriod, scheduler));
    }
}

