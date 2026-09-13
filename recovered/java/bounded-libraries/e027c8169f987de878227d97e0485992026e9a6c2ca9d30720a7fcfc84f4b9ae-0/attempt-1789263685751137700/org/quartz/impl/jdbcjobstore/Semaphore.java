/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.impl.jdbcjobstore;

import java.sql.Connection;
import org.quartz.impl.jdbcjobstore.LockException;

public interface Semaphore {
    public boolean obtainLock(Connection var1, String var2) throws LockException;

    public void releaseLock(String var1) throws LockException;

    public boolean requiresConnection();
}

