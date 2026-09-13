/*
 * Decompiled with CFR 0.152.
 */
package reactor.util.concurrent;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import reactor.util.concurrent.SpscArrayQueueP2;

class SpscArrayQueueConsumer<T>
extends SpscArrayQueueP2<T> {
    private static final long serialVersionUID = 4075549732218321659L;
    volatile long consumerIndex;
    static final AtomicLongFieldUpdater<SpscArrayQueueConsumer> CONSUMER_INDEX = AtomicLongFieldUpdater.newUpdater(SpscArrayQueueConsumer.class, "consumerIndex");

    SpscArrayQueueConsumer(int length) {
        super(length);
    }
}

