/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.retry.backoff;

import java.io.Serializable;

public interface Sleeper
extends Serializable {
    public void sleep(long var1) throws InterruptedException;
}

