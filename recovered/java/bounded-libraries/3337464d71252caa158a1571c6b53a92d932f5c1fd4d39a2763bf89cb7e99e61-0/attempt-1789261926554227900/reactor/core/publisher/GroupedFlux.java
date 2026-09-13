/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import reactor.core.publisher.Flux;
import reactor.util.annotation.NonNull;

public abstract class GroupedFlux<K, V>
extends Flux<V> {
    @NonNull
    public abstract K key();
}

