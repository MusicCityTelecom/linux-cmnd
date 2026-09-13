/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.quartz.impl.jdbcjobstore;

import java.sql.Connection;
import java.util.HashSet;
import org.quartz.impl.jdbcjobstore.Semaphore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleSemaphore
implements Semaphore {
    ThreadLocal<HashSet<String>> lockOwners = new ThreadLocal();
    HashSet<String> locks = new HashSet();
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    protected Logger getLog() {
        return this.log;
    }

    private HashSet<String> getThreadLocks() {
        HashSet<String> threadLocks = this.lockOwners.get();
        if (threadLocks == null) {
            threadLocks = new HashSet();
            this.lockOwners.set(threadLocks);
        }
        return threadLocks;
    }

    @Override
    public synchronized boolean obtainLock(Connection conn, String lockName) {
        lockName = lockName.intern();
        if (this.log.isDebugEnabled()) {
            this.log.debug("Lock '" + lockName + "' is desired by: " + Thread.currentThread().getName());
        }
        if (!this.isLockOwner(lockName)) {
            if (this.log.isDebugEnabled()) {
                this.log.debug("Lock '" + lockName + "' is being obtained: " + Thread.currentThread().getName());
            }
            while (this.locks.contains(lockName)) {
                try {
                    this.wait();
                }
                catch (InterruptedException ie) {
                    if (!this.log.isDebugEnabled()) continue;
                    this.log.debug("Lock '" + lockName + "' was not obtained by: " + Thread.currentThread().getName());
                }
            }
            if (this.log.isDebugEnabled()) {
                this.log.debug("Lock '" + lockName + "' given to: " + Thread.currentThread().getName());
            }
            this.getThreadLocks().add(lockName);
            this.locks.add(lockName);
        } else if (this.log.isDebugEnabled()) {
            this.log.debug("Lock '" + lockName + "' already owned by: " + Thread.currentThread().getName() + " -- but not owner!", (Throwable)new Exception("stack-trace of wrongful returner"));
        }
        return true;
    }

    @Override
    public synchronized void releaseLock(String lockName) {
        if (this.isLockOwner(lockName = lockName.intern())) {
            if (this.getLog().isDebugEnabled()) {
                this.getLog().debug("Lock '" + lockName + "' retuned by: " + Thread.currentThread().getName());
            }
            this.getThreadLocks().remove(lockName);
            this.locks.remove(lockName);
            this.notifyAll();
        } else if (this.getLog().isDebugEnabled()) {
            this.getLog().debug("Lock '" + lockName + "' attempt to retun by: " + Thread.currentThread().getName() + " -- but not owner!", (Throwable)new Exception("stack-trace of wrongful returner"));
        }
    }

    public synchronized boolean isLockOwner(String lockName) {
        lockName = lockName.intern();
        return this.getThreadLocks().contains(lockName);
    }

    @Override
    public boolean requiresConnection() {
        return false;
    }
}

