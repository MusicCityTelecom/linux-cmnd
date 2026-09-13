/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 */
package reactor.core;

import org.reactivestreams.Publisher;
import reactor.core.CoreSubscriber;

public interface CorePublisher<T>
extends Publisher<T> {
    public void subscribe(CoreSubscriber<? super T> var1);
}

