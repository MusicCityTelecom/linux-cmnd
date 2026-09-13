/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;

interface InnerProducer<O>
extends Scannable,
Subscription {
    public CoreSubscriber<? super O> actual();

    @Override
    @Nullable
    default public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.ACTUAL) {
            return this.actual();
        }
        return null;
    }
}

