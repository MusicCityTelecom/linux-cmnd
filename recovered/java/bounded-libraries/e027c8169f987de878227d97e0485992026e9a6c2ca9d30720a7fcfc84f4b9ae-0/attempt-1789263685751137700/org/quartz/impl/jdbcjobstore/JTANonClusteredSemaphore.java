/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.transaction.Synchronization
 *  javax.transaction.SystemException
 *  javax.transaction.Transaction
 *  javax.transaction.TransactionManager
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.quartz.impl.jdbcjobstore;

import java.sql.Connection;
import java.util.HashSet;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.Synchronization;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;
import org.quartz.impl.jdbcjobstore.LockException;
import org.quartz.impl.jdbcjobstore.Semaphore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JTANonClusteredSemaphore
implements Semaphore {
    public static final String DEFAULT_TRANSACTION_MANANGER_LOCATION = "java:TransactionManager";
    ThreadLocal<HashSet<String>> lockOwners = new ThreadLocal();
    HashSet<String> locks = new HashSet();
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private String transactionManagerJNDIName = "java:TransactionManager";

    protected Logger getLog() {
        return this.log;
    }

    public void setTransactionManagerJNDIName(String transactionManagerJNDIName) {
        this.transactionManagerJNDIName = transactionManagerJNDIName;
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
    public synchronized boolean obtainLock(Connection conn, String lockName) throws LockException {
        lockName = lockName.intern();
        if (this.log.isDebugEnabled()) {
            this.log.debug("Lock '" + lockName + "' is desired by: " + Thread.currentThread().getName());
        }
        if (!this.isLockOwner(conn, lockName)) {
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
            Transaction t = this.getTransaction();
            if (t != null) {
                try {
                    t.registerSynchronization((Synchronization)new SemaphoreSynchronization(lockName));
                }
                catch (Exception e) {
                    throw new LockException("Failed to register semaphore with Transaction.", e);
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

    protected Transaction getTransaction() throws LockException {
        InitialContext ic = null;
        try {
            ic = new InitialContext();
            TransactionManager tm = (TransactionManager)ic.lookup(this.transactionManagerJNDIName);
            Transaction transaction = tm.getTransaction();
            return transaction;
        }
        catch (SystemException e) {
            throw new LockException("Failed to get Transaction from TransactionManager", e);
        }
        catch (NamingException e) {
            throw new LockException("Failed to find TransactionManager in JNDI under name: " + this.transactionManagerJNDIName, e);
        }
        finally {
            if (ic != null) {
                try {
                    ic.close();
                }
                catch (NamingException namingException) {}
            }
        }
    }

    @Override
    public synchronized void releaseLock(String lockName) throws LockException {
        this.releaseLock(lockName, false);
    }

    protected synchronized void releaseLock(String lockName, boolean fromSynchronization) throws LockException {
        if (this.isLockOwner(null, lockName = lockName.intern())) {
            Transaction t;
            if (!fromSynchronization && (t = this.getTransaction()) != null) {
                if (this.getLog().isDebugEnabled()) {
                    this.getLog().debug("Lock '" + lockName + "' is in a JTA transaction.  " + "Return deferred by: " + Thread.currentThread().getName());
                }
                return;
            }
            if (this.getLog().isDebugEnabled()) {
                this.getLog().debug("Lock '" + lockName + "' returned by: " + Thread.currentThread().getName());
            }
            this.getThreadLocks().remove(lockName);
            this.locks.remove(lockName);
            this.notify();
        } else if (this.getLog().isDebugEnabled()) {
            this.getLog().debug("Lock '" + lockName + "' attempt to return by: " + Thread.currentThread().getName() + " -- but not owner!", (Throwable)new Exception("stack-trace of wrongful returner"));
        }
    }

    public synchronized boolean isLockOwner(Connection conn, String lockName) {
        lockName = lockName.intern();
        return this.getThreadLocks().contains(lockName);
    }

    @Override
    public boolean requiresConnection() {
        return false;
    }

    private class SemaphoreSynchronization
    implements Synchronization {
        private String lockName;

        public SemaphoreSynchronization(String lockName) {
            this.lockName = lockName;
        }

        public void beforeCompletion() {
        }

        public void afterCompletion(int status) {
            try {
                JTANonClusteredSemaphore.this.releaseLock(this.lockName, true);
            }
            catch (LockException lockException) {
                // empty catch block
            }
        }
    }
}

