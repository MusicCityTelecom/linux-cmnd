/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.micrometer.core.instrument.Counter
 *  io.micrometer.core.instrument.MeterRegistry
 *  io.micrometer.core.instrument.Timer
 *  org.springframework.context.ApplicationContext
 */
package org.springframework.integration.graph;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.springframework.context.ApplicationContext;
import org.springframework.integration.graph.IntegrationNode;
import org.springframework.integration.graph.MessageChannelNode;
import org.springframework.integration.graph.MessageHandlerNode;
import org.springframework.integration.graph.MessageSourceNode;
import org.springframework.integration.graph.PollableChannelNode;
import org.springframework.integration.graph.ReceiveCounters;
import org.springframework.integration.graph.ReceiveCountersAware;
import org.springframework.integration.graph.SendTimers;
import org.springframework.integration.graph.SendTimersAware;
import org.springframework.integration.graph.TimerStats;

public class MicrometerNodeEnhancer {
    private static final String UNUSED = "unused";
    private static final String TAG_TYPE = "type";
    private static final String TAG_NAME = "name";
    private static final String TAG_RESULT = "result";
    private static final TimerStats ZERO_TIMER_STATS = new TimerStats(0L, 0.0, 0.0);
    private final MeterRegistry registry;

    MicrometerNodeEnhancer(ApplicationContext applicationContext) {
        Map registries = applicationContext.getBeansOfType(MeterRegistry.class, false, false);
        this.registry = registries.size() == 1 ? (MeterRegistry)registries.values().iterator().next() : null;
    }

    <T extends IntegrationNode> T enhance(T node) {
        if (this.registry != null) {
            if (node instanceof MessageChannelNode) {
                this.enhanceWithTimers(node, "channel");
            } else if (node instanceof MessageHandlerNode) {
                this.enhanceWithTimers(node, "handler");
            }
            if (node instanceof PollableChannelNode) {
                this.enhanceWithCounts(node, "channel");
            } else if (node instanceof MessageSourceNode) {
                this.enhanceWithCounts(node, "source");
            }
        }
        return node;
    }

    private <T extends IntegrationNode> void enhanceWithTimers(T node, String type) {
        ((SendTimersAware)((Object)node)).sendTimers(() -> this.retrieveTimers(node, type));
    }

    private <T extends IntegrationNode> SendTimers retrieveTimers(T node, String type) {
        Timer successTimer = null;
        try {
            successTimer = this.registry.get("spring.integration.send").tag(TAG_TYPE, type).tag(TAG_NAME, node.getName()).tag(TAG_RESULT, "success").timer();
        }
        catch (Exception exception) {
            // empty catch block
        }
        Timer failureTimer = null;
        try {
            failureTimer = this.registry.get("spring.integration.send").tag(TAG_TYPE, type).tag(TAG_NAME, node.getName()).tag(TAG_RESULT, "failure").timer();
        }
        catch (Exception exception) {
            // empty catch block
        }
        TimerStats successes = successTimer == null ? ZERO_TIMER_STATS : new TimerStats(successTimer.count(), successTimer.mean(TimeUnit.MILLISECONDS), successTimer.max(TimeUnit.MILLISECONDS));
        TimerStats failures = failureTimer == null ? ZERO_TIMER_STATS : new TimerStats(failureTimer.count(), failureTimer.mean(TimeUnit.MILLISECONDS), failureTimer.max(TimeUnit.MILLISECONDS));
        return new SendTimers(successes, failures);
    }

    private <T extends IntegrationNode> void enhanceWithCounts(T node, String type) {
        ((ReceiveCountersAware)((Object)node)).receiveCounters(() -> this.retrieveCounters(node, type));
    }

    private <T extends IntegrationNode> ReceiveCounters retrieveCounters(T node, String type) {
        Counter successes = null;
        String name = node.getName();
        try {
            successes = this.registry.get("spring.integration.receive").tag(TAG_TYPE, type).tag(TAG_NAME, name).tag(TAG_RESULT, "success").counter();
        }
        catch (Exception exception) {
            // empty catch block
        }
        Counter failures = null;
        try {
            failures = this.registry.get("spring.integration.receive").tag(TAG_TYPE, type).tag(TAG_NAME, name).tag(TAG_RESULT, "failure").counter();
        }
        catch (Exception exception) {
            // empty catch block
        }
        return new ReceiveCounters((long)(successes == null ? 0.0 : successes.count()), (long)(failures == null ? 0.0 : failures.count()));
    }
}

