/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package org.springframework.integration.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CallerBlocksPolicy
implements RejectedExecutionHandler {
    private static final Log LOGGER = LogFactory.getLog(CallerBlocksPolicy.class);
    private final long maxWait;

    public CallerBlocksPolicy(long maxWait) {
        this.maxWait = maxWait;
    }

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        if (!executor.isShutdown()) {
            try {
                BlockingQueue<Runnable> queue = executor.getQueue();
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug((Object)("Attempting to queue task execution for " + this.maxWait + " milliseconds"));
                }
                if (!queue.offer(r, this.maxWait, TimeUnit.MILLISECONDS)) {
                    throw new RejectedExecutionException("Max wait time expired to queue task");
                }
                LOGGER.debug((Object)"Task execution queued");
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RejectedExecutionException("Interrupted", e);
            }
        } else {
            throw new RejectedExecutionException("Executor has been shut down");
        }
    }
}

