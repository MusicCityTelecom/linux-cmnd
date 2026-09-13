/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import reactor.core.publisher.SinkManyBestEffort;

interface DirectInnerContainer<T> {
    public boolean add(SinkManyBestEffort.DirectInner<T> var1);

    public void remove(SinkManyBestEffort.DirectInner<T> var1);
}

