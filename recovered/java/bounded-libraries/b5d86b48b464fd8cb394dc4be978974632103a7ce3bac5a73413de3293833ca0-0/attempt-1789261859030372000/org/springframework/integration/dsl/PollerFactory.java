/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.scheduling.Trigger
 */
package org.springframework.integration.dsl;

import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import org.springframework.integration.dsl.PollerSpec;
import org.springframework.integration.dsl.Pollers;
import org.springframework.scheduling.Trigger;

public final class PollerFactory {
    public PollerSpec trigger(Trigger trigger2) {
        return Pollers.trigger(trigger2);
    }

    public PollerSpec cron(String cronExpression) {
        return Pollers.cron(cronExpression);
    }

    public PollerSpec cron(String cronExpression, TimeZone timeZone) {
        return Pollers.cron(cronExpression, timeZone);
    }

    public PollerSpec fixedRate(long period) {
        return Pollers.fixedRate(period);
    }

    public PollerSpec fixedRate(long period, TimeUnit timeUnit) {
        return Pollers.fixedRate(period, timeUnit);
    }

    public PollerSpec fixedRate(long period, long initialDelay) {
        return Pollers.fixedRate(period, initialDelay);
    }

    public PollerSpec fixedDelay(long period, TimeUnit timeUnit, long initialDelay) {
        return Pollers.fixedDelay(period, timeUnit, initialDelay);
    }

    public PollerSpec fixedRate(long period, TimeUnit timeUnit, long initialDelay) {
        return Pollers.fixedRate(period, timeUnit, initialDelay);
    }

    public PollerSpec fixedDelay(long period, TimeUnit timeUnit) {
        return Pollers.fixedDelay(period, timeUnit);
    }

    public PollerSpec fixedDelay(long period, long initialDelay) {
        return Pollers.fixedDelay(period, initialDelay);
    }

    public PollerSpec fixedDelay(long period) {
        return Pollers.fixedDelay(period);
    }

    PollerFactory() {
    }
}

