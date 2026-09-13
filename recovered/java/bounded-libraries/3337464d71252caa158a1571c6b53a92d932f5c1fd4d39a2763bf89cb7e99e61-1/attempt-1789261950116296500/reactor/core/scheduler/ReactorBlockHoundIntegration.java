/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  reactor.blockhound.BlockHound$Builder
 *  reactor.blockhound.integration.BlockHoundIntegration
 */
package reactor.core.scheduler;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import reactor.blockhound.BlockHound;
import reactor.blockhound.integration.BlockHoundIntegration;
import reactor.core.scheduler.BoundedElasticScheduler;
import reactor.core.scheduler.NonBlocking;
import reactor.core.scheduler.SchedulerTask;

public final class ReactorBlockHoundIntegration
implements BlockHoundIntegration {
    public void applyTo(BlockHound.Builder builder) {
        builder.nonBlockingThreadPredicate(current -> current.or(NonBlocking.class::isInstance));
        builder.allowBlockingCallsInside(ScheduledThreadPoolExecutor.class.getName() + "$DelayedWorkQueue", "offer");
        builder.allowBlockingCallsInside(ScheduledThreadPoolExecutor.class.getName() + "$DelayedWorkQueue", "take");
        builder.allowBlockingCallsInside(BoundedElasticScheduler.class.getName() + "$BoundedScheduledExecutorService", "ensureQueueCapacity");
        builder.allowBlockingCallsInside(SchedulerTask.class.getName(), "dispose");
        builder.allowBlockingCallsInside(ThreadPoolExecutor.class.getName(), "processWorkerExit");
    }
}

