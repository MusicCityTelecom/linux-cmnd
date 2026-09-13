/*
 * Decompiled with CFR 0.152.
 */
package org.apache.groovy.parser.antlr4.internal.atnmanager;

import groovyjarjarantlr4.v4.runtime.atn.ATN;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.apache.groovy.util.SystemUtil;

public abstract class AtnManager {
    private static final ReentrantReadWriteLock RRWL = new ReentrantReadWriteLock(true);
    private static final ReentrantReadWriteLock.WriteLock WRITE_LOCK = RRWL.writeLock();
    public static final ReentrantReadWriteLock.ReadLock READ_LOCK = RRWL.readLock();
    private static final String DFA_CACHE_THRESHOLD_OPT = "groovy.antlr4.cache.threshold";
    private static final long DFA_CACHE_THRESHOLD;

    public abstract ATN getATN();

    protected abstract boolean shouldClearDfaCache();

    static {
        long t = SystemUtil.getLongSafe(DFA_CACHE_THRESHOLD_OPT, 64L);
        if (t <= 0L) {
            t = Long.MAX_VALUE;
        }
        DFA_CACHE_THRESHOLD = t;
    }

    protected class AtnWrapper {
        private final ATN atn;
        private final AtomicLong counter = new AtomicLong(0L);

        public AtnWrapper(ATN atn) {
            this.atn = atn;
        }

        public ATN checkAndClear() {
            if (!AtnManager.this.shouldClearDfaCache()) {
                return this.atn;
            }
            if (0L != this.counter.incrementAndGet() % DFA_CACHE_THRESHOLD) {
                return this.atn;
            }
            WRITE_LOCK.lock();
            try {
                this.atn.clearDFA();
            }
            finally {
                WRITE_LOCK.unlock();
            }
            return this.atn;
        }
    }
}

