/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.util.collection;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

@Deprecated
public interface TransferQueue<E>
extends BlockingQueue<E> {
    public boolean tryTransfer(E var1);

    public void transfer(E var1) throws InterruptedException;

    public boolean tryTransfer(E var1, long var2, TimeUnit var4) throws InterruptedException;

    public boolean hasWaitingConsumer();

    public int getWaitingConsumerCount();
}

