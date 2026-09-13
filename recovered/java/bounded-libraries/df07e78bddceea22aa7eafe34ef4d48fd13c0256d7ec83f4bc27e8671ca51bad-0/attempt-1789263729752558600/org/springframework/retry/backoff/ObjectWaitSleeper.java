/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.retry.backoff;

import org.springframework.retry.backoff.Sleeper;

@Deprecated
public class ObjectWaitSleeper
implements Sleeper {
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void sleep(long backOffPeriod) throws InterruptedException {
        Object mutex;
        Object object = mutex = new Object();
        synchronized (object) {
            mutex.wait(backOffPeriod);
        }
    }
}

