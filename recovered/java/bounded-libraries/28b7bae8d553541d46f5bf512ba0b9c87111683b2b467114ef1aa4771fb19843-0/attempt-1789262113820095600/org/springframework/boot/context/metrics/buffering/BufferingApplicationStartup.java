/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.metrics.ApplicationStartup
 *  org.springframework.core.metrics.StartupStep
 *  org.springframework.util.Assert
 */
package org.springframework.boot.context.metrics.buffering;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import org.springframework.boot.context.metrics.buffering.BufferedStartupStep;
import org.springframework.boot.context.metrics.buffering.StartupTimeline;
import org.springframework.core.metrics.ApplicationStartup;
import org.springframework.core.metrics.StartupStep;
import org.springframework.util.Assert;

public class BufferingApplicationStartup
implements ApplicationStartup {
    private final int capacity;
    private final Clock clock;
    private Instant startTime;
    private final AtomicInteger idSeq = new AtomicInteger();
    private Predicate<StartupStep> filter = step -> true;
    private final AtomicReference<BufferedStartupStep> current = new AtomicReference();
    private final AtomicInteger estimatedSize = new AtomicInteger();
    private final ConcurrentLinkedQueue<StartupTimeline.TimelineEvent> events = new ConcurrentLinkedQueue();

    public BufferingApplicationStartup(int capacity) {
        this(capacity, Clock.systemDefaultZone());
    }

    BufferingApplicationStartup(int capacity, Clock clock) {
        this.capacity = capacity;
        this.clock = clock;
        this.startTime = clock.instant();
    }

    public void startRecording() {
        Assert.state((boolean)this.events.isEmpty(), (String)"Cannot restart recording once steps have been buffered.");
        this.startTime = this.clock.instant();
    }

    public void addFilter(Predicate<StartupStep> filter) {
        this.filter = this.filter.and(filter);
    }

    public StartupStep start(String name) {
        BufferedStartupStep parent;
        BufferedStartupStep next;
        BufferedStartupStep current;
        int id = this.idSeq.getAndIncrement();
        Instant start = this.clock.instant();
        while (!this.current.compareAndSet(current = this.current.get(), next = new BufferedStartupStep(parent = this.getLatestActive(current), name, id, start, this::record))) {
        }
        return next;
    }

    private void record(BufferedStartupStep step) {
        BufferedStartupStep next;
        BufferedStartupStep current;
        if (this.filter.test(step) && this.estimatedSize.get() < this.capacity) {
            this.estimatedSize.incrementAndGet();
            this.events.add(new StartupTimeline.TimelineEvent(step, this.clock.instant()));
        }
        while (!this.current.compareAndSet(current = this.current.get(), next = this.getLatestActive(current))) {
        }
    }

    private BufferedStartupStep getLatestActive(BufferedStartupStep step) {
        while (step != null && step.isEnded()) {
            step = step.getParent();
        }
        return step;
    }

    public StartupTimeline getBufferedTimeline() {
        return new StartupTimeline(this.startTime, new ArrayList<StartupTimeline.TimelineEvent>(this.events));
    }

    public StartupTimeline drainBufferedTimeline() {
        ArrayList<StartupTimeline.TimelineEvent> events = new ArrayList<StartupTimeline.TimelineEvent>();
        Iterator<StartupTimeline.TimelineEvent> iterator = this.events.iterator();
        while (iterator.hasNext()) {
            events.add(iterator.next());
            iterator.remove();
        }
        this.estimatedSize.set(0);
        return new StartupTimeline(this.startTime, events);
    }
}

