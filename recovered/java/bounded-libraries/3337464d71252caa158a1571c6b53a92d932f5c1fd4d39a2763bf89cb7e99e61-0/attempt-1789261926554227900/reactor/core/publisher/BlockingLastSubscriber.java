/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import reactor.core.publisher.BlockingSingleSubscriber;

final class BlockingLastSubscriber<T>
extends BlockingSingleSubscriber<T> {
    BlockingLastSubscriber() {
    }

    public void onNext(T t) {
        this.value = t;
    }

    public void onError(Throwable t) {
        this.value = null;
        this.error = t;
        this.countDown();
    }

    @Override
    public String stepName() {
        return "blockLast";
    }
}

