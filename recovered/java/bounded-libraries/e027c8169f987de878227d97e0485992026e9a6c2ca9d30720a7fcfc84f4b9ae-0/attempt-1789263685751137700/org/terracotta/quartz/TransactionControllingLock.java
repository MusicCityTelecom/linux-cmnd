/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.terracotta.toolkit.ToolkitFeatureTypeInternal
 *  org.terracotta.toolkit.atomic.ToolkitTransaction
 *  org.terracotta.toolkit.atomic.ToolkitTransactionController
 *  org.terracotta.toolkit.atomic.ToolkitTransactionType
 *  org.terracotta.toolkit.concurrent.locks.ToolkitLock
 *  org.terracotta.toolkit.concurrent.locks.ToolkitLockType
 *  org.terracotta.toolkit.internal.ToolkitInternal
 *  org.terracotta.toolkit.rejoin.RejoinException
 */
package org.terracotta.quartz;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import org.terracotta.toolkit.ToolkitFeatureTypeInternal;
import org.terracotta.toolkit.atomic.ToolkitTransaction;
import org.terracotta.toolkit.atomic.ToolkitTransactionController;
import org.terracotta.toolkit.atomic.ToolkitTransactionType;
import org.terracotta.toolkit.concurrent.locks.ToolkitLock;
import org.terracotta.toolkit.concurrent.locks.ToolkitLockType;
import org.terracotta.toolkit.internal.ToolkitInternal;
import org.terracotta.toolkit.rejoin.RejoinException;

class TransactionControllingLock
implements ToolkitLock {
    private final ThreadLocal<HoldState> threadState = new ThreadLocal<HoldState>(){

        @Override
        protected HoldState initialValue() {
            return new HoldState();
        }
    };
    private final ToolkitTransactionController txnController;
    private final ToolkitTransactionType txnType;
    private final ToolkitLock delegate;

    public TransactionControllingLock(ToolkitInternal toolkit, ToolkitLock lock, ToolkitTransactionType txnType) {
        this.txnController = (ToolkitTransactionController)toolkit.getFeature(ToolkitFeatureTypeInternal.TRANSACTION);
        this.txnType = txnType;
        this.delegate = lock;
    }

    public Condition newCondition() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    public Condition getCondition() {
        throw new UnsupportedOperationException();
    }

    public ToolkitLockType getLockType() {
        return this.delegate.getLockType();
    }

    public boolean isHeldByCurrentThread() {
        return this.delegate.isHeldByCurrentThread();
    }

    public void lock() {
        this.delegate.lock();
        try {
            this.threadState.get().lock();
        }
        catch (RejoinException e) {
            this.delegate.unlock();
        }
    }

    public void lockInterruptibly() throws InterruptedException {
        this.delegate.lockInterruptibly();
        try {
            this.threadState.get().lock();
        }
        catch (RejoinException e) {
            this.delegate.unlock();
        }
    }

    public boolean tryLock() {
        if (this.delegate.tryLock()) {
            try {
                this.threadState.get().lock();
            }
            catch (RejoinException e) {
                this.delegate.unlock();
            }
            return true;
        }
        return false;
    }

    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        if (this.delegate.tryLock(time, unit)) {
            try {
                this.threadState.get().lock();
            }
            catch (RejoinException e) {
                this.delegate.unlock();
            }
            return true;
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void unlock() {
        try {
            this.threadState.get().unlock();
        }
        finally {
            this.delegate.unlock();
        }
    }

    public String getName() {
        return this.delegate.getName();
    }

    class HoldState {
        private ToolkitTransaction txnHandle;
        private int holdCount = 0;

        HoldState() {
        }

        void lock() {
            if (this.holdCount++ == 0) {
                if (this.txnHandle == null) {
                    this.txnHandle = TransactionControllingLock.this.txnController.beginTransaction(TransactionControllingLock.this.txnType);
                } else {
                    throw new AssertionError();
                }
            }
        }

        void unlock() {
            if (--this.holdCount <= 0) {
                try {
                    this.txnHandle.commit();
                }
                catch (RejoinException e) {
                    throw new RejoinException("Exception caught during commit, transaction may or may not have committed.", (Throwable)e);
                }
                finally {
                    TransactionControllingLock.this.threadState.remove();
                }
            }
        }
    }
}

