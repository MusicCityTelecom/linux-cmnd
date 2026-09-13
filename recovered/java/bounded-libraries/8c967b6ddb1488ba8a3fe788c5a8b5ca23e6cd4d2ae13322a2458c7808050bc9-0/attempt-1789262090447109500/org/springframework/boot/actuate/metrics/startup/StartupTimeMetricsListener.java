/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.micrometer.core.instrument.MeterRegistry
 *  io.micrometer.core.instrument.Tag
 *  io.micrometer.core.instrument.Tags
 *  io.micrometer.core.instrument.TimeGauge
 *  org.springframework.boot.SpringApplication
 *  org.springframework.boot.context.event.ApplicationReadyEvent
 *  org.springframework.boot.context.event.ApplicationStartedEvent
 *  org.springframework.context.ApplicationEvent
 *  org.springframework.context.event.SmartApplicationListener
 */
package org.springframework.boot.actuate.metrics.startup;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.TimeGauge;
import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;

public class StartupTimeMetricsListener
implements SmartApplicationListener {
    public static final String APPLICATION_STARTED_TIME_METRIC_NAME = "application.started.time";
    public static final String APPLICATION_READY_TIME_METRIC_NAME = "application.ready.time";
    private final MeterRegistry meterRegistry;
    private final String startedTimeMetricName;
    private final String readyTimeMetricName;
    private final Tags tags;

    public StartupTimeMetricsListener(MeterRegistry meterRegistry) {
        this(meterRegistry, APPLICATION_STARTED_TIME_METRIC_NAME, APPLICATION_READY_TIME_METRIC_NAME, Collections.emptyList());
    }

    public StartupTimeMetricsListener(MeterRegistry meterRegistry, String startedTimeMetricName, String readyTimeMetricName, Iterable<Tag> tags) {
        this.meterRegistry = meterRegistry;
        this.startedTimeMetricName = startedTimeMetricName;
        this.readyTimeMetricName = readyTimeMetricName;
        this.tags = Tags.of(tags);
    }

    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return ApplicationStartedEvent.class.isAssignableFrom(eventType) || ApplicationReadyEvent.class.isAssignableFrom(eventType);
    }

    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ApplicationStartedEvent) {
            this.onApplicationStarted((ApplicationStartedEvent)event);
        }
        if (event instanceof ApplicationReadyEvent) {
            this.onApplicationReady((ApplicationReadyEvent)event);
        }
    }

    private void onApplicationStarted(ApplicationStartedEvent event) {
        this.registerGauge(this.startedTimeMetricName, "Time taken (ms) to start the application", event.getTimeTaken(), event.getSpringApplication());
    }

    private void onApplicationReady(ApplicationReadyEvent event) {
        this.registerGauge(this.readyTimeMetricName, "Time taken (ms) for the application to be ready to service requests", event.getTimeTaken(), event.getSpringApplication());
    }

    private void registerGauge(String name, String description, Duration timeTaken, SpringApplication springApplication) {
        if (timeTaken != null) {
            Iterable<Tag> tags = this.createTagsFrom(springApplication);
            TimeGauge.builder((String)name, timeTaken::toMillis, (TimeUnit)TimeUnit.MILLISECONDS).tags(tags).description(description).register(this.meterRegistry);
        }
    }

    private Iterable<Tag> createTagsFrom(SpringApplication springApplication) {
        Class mainClass = springApplication.getMainApplicationClass();
        return mainClass != null ? this.tags.and("main.application.class", mainClass.getName()) : this.tags;
    }
}

