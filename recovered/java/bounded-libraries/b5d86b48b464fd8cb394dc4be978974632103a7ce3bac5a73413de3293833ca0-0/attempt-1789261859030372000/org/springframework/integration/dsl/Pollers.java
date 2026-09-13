/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.scheduling.Trigger
 *  org.springframework.scheduling.support.CronTrigger
 *  org.springframework.scheduling.support.PeriodicTrigger
 */
package org.springframework.integration.dsl;

import java.time.Duration;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import org.springframework.integration.dsl.PollerSpec;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.PeriodicTrigger;

public final class Pollers {
    public static PollerSpec trigger(Trigger trigger2) {
        return new PollerSpec(trigger2);
    }

    public static PollerSpec fixedRate(Duration period) {
        return Pollers.fixedRate(period.toMillis());
    }

    public static PollerSpec fixedRate(long period) {
        return Pollers.fixedRate(period, null);
    }

    public static PollerSpec fixedRate(long period, TimeUnit timeUnit) {
        return Pollers.fixedRate(period, timeUnit, 0L);
    }

    public static PollerSpec fixedRate(Duration period, Duration initialDelay) {
        return Pollers.fixedRate(period.toMillis(), initialDelay.toMillis());
    }

    public static PollerSpec fixedRate(long period, long initialDelay) {
        return Pollers.periodicTrigger(period, null, true, initialDelay);
    }

    public static PollerSpec fixedRate(long period, TimeUnit timeUnit, long initialDelay) {
        return Pollers.periodicTrigger(period, timeUnit, true, initialDelay);
    }

    public static PollerSpec fixedDelay(Duration period) {
        return Pollers.fixedDelay(period.toMillis());
    }

    public static PollerSpec fixedDelay(long period) {
        return Pollers.fixedDelay(period, null);
    }

    public static PollerSpec fixedDelay(Duration period, Duration initialDelay) {
        return Pollers.fixedDelay(period.toMillis(), initialDelay.toMillis());
    }

    public static PollerSpec fixedDelay(long period, TimeUnit timeUnit) {
        return Pollers.fixedDelay(period, timeUnit, 0L);
    }

    public static PollerSpec fixedDelay(long period, long initialDelay) {
        return Pollers.periodicTrigger(period, null, false, initialDelay);
    }

    public static PollerSpec fixedDelay(long period, TimeUnit timeUnit, long initialDelay) {
        return Pollers.periodicTrigger(period, timeUnit, false, initialDelay);
    }

    private static PollerSpec periodicTrigger(long period, TimeUnit timeUnit, boolean fixedRate, long initialDelay) {
        PeriodicTrigger periodicTrigger = new PeriodicTrigger(period, timeUnit);
        periodicTrigger.setFixedRate(fixedRate);
        periodicTrigger.setInitialDelay(initialDelay);
        return new PollerSpec((Trigger)periodicTrigger);
    }

    public static PollerSpec cron(String cronExpression) {
        return Pollers.cron(cronExpression, TimeZone.getDefault());
    }

    public static PollerSpec cron(String cronExpression, TimeZone timeZone) {
        return new PollerSpec((Trigger)new CronTrigger(cronExpression, timeZone));
    }

    private Pollers() {
    }
}

