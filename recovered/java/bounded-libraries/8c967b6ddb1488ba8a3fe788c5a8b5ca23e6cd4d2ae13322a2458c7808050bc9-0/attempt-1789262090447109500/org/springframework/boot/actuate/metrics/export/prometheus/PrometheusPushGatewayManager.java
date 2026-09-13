/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.prometheus.client.CollectorRegistry
 *  io.prometheus.client.exporter.PushGateway
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.scheduling.TaskScheduler
 *  org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
 *  org.springframework.util.Assert
 */
package org.springframework.boot.actuate.metrics.export.prometheus;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.exporter.PushGateway;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.util.Assert;

public class PrometheusPushGatewayManager {
    private static final Log logger = LogFactory.getLog(PrometheusPushGatewayManager.class);
    private final PushGateway pushGateway;
    private final CollectorRegistry registry;
    private final String job;
    private final Map<String, String> groupingKey;
    private final ShutdownOperation shutdownOperation;
    private final TaskScheduler scheduler;
    private ScheduledFuture<?> scheduled;

    public PrometheusPushGatewayManager(PushGateway pushGateway, CollectorRegistry registry, Duration pushRate, String job, Map<String, String> groupingKeys, ShutdownOperation shutdownOperation) {
        this(pushGateway, registry, (TaskScheduler)new PushGatewayTaskScheduler(), pushRate, job, groupingKeys, shutdownOperation);
    }

    public PrometheusPushGatewayManager(PushGateway pushGateway, CollectorRegistry registry, TaskScheduler scheduler, Duration pushRate, String job, Map<String, String> groupingKey, ShutdownOperation shutdownOperation) {
        Assert.notNull((Object)pushGateway, (String)"PushGateway must not be null");
        Assert.notNull((Object)registry, (String)"Registry must not be null");
        Assert.notNull((Object)scheduler, (String)"Scheduler must not be null");
        Assert.notNull((Object)pushRate, (String)"PushRate must not be null");
        Assert.hasLength((String)job, (String)"Job must not be empty");
        this.pushGateway = pushGateway;
        this.registry = registry;
        this.job = job;
        this.groupingKey = groupingKey;
        this.shutdownOperation = shutdownOperation != null ? shutdownOperation : ShutdownOperation.NONE;
        this.scheduler = scheduler;
        this.scheduled = this.scheduler.scheduleAtFixedRate(this::push, pushRate);
    }

    private void push() {
        try {
            this.pushGateway.pushAdd(this.registry, this.job, this.groupingKey);
        }
        catch (Throwable ex) {
            logger.warn((Object)"Unexpected exception thrown while pushing metrics to Prometheus Pushgateway", ex);
        }
    }

    private void delete() {
        try {
            this.pushGateway.delete(this.job, this.groupingKey);
        }
        catch (Throwable ex) {
            logger.warn((Object)"Unexpected exception thrown while deleting metrics from Prometheus Pushgateway", ex);
        }
    }

    public void shutdown() {
        this.shutdown(this.shutdownOperation);
    }

    private void shutdown(ShutdownOperation shutdownOperation) {
        if (this.scheduler instanceof PushGatewayTaskScheduler) {
            ((PushGatewayTaskScheduler)this.scheduler).shutdown();
        }
        this.scheduled.cancel(false);
        switch (shutdownOperation) {
            case PUSH: {
                this.push();
                break;
            }
            case DELETE: {
                this.delete();
            }
        }
    }

    static class PushGatewayTaskScheduler
    extends ThreadPoolTaskScheduler {
        PushGatewayTaskScheduler() {
            this.setPoolSize(1);
            this.setDaemon(true);
            this.setThreadGroupName("prometheus-push-gateway");
        }

        public ScheduledExecutorService getScheduledExecutor() throws IllegalStateException {
            return Executors.newSingleThreadScheduledExecutor(arg_0 -> ((PushGatewayTaskScheduler)this).newThread(arg_0));
        }
    }

    public static enum ShutdownOperation {
        NONE,
        PUSH,
        DELETE;

    }
}

