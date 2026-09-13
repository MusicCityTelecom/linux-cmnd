/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import reactor.core.publisher.BlockingSingleSubscriber;

final class BlockingFirstSubscriber<T>
extends BlockingSingleSubscriber<T> {
    BlockingFirstSubscriber() {
    }

    public void onNext(T t) {
        if (this.value == null) {
            this.value = t;
            this.dispose();
            this.countDown();
        }
    }

    public void onError(Throwable t) {
        if (this.value == null) {
            this.error = t;
        }
        this.countDown();
    }

    @Override
    public String stepName() {
        return "blockFirst";
    }
}

