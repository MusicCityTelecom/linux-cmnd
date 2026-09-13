/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscriber
 *  org.reactivestreams.Subscription
 */
package reactor.core;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.util.context.Context;

public interface CoreSubscriber<T>
extends Subscriber<T> {
    default public Context currentContext() {
        return Context.empty();
    }

    public void onSubscribe(Subscription var1);
}

