/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Supplier;

public interface Timed<T>
extends Supplier<T> {
    @Override
    public T get();

    public Duration elapsed();

    public Duration elapsedSinceSubscription();

    public Instant timestamp();
}

