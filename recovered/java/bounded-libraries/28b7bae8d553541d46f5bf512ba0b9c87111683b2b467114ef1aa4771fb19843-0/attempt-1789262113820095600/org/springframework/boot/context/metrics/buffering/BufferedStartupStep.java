/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.metrics.StartupStep
 *  org.springframework.core.metrics.StartupStep$Tag
 *  org.springframework.core.metrics.StartupStep$Tags
 *  org.springframework.util.Assert
 */
package org.springframework.boot.context.metrics.buffering;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.springframework.core.metrics.StartupStep;
import org.springframework.util.Assert;

class BufferedStartupStep
implements StartupStep {
    private final String name;
    private final long id;
    private final BufferedStartupStep parent;
    private final List<StartupStep.Tag> tags = new ArrayList<StartupStep.Tag>();
    private final Consumer<BufferedStartupStep> recorder;
    private final Instant startTime;
    private final AtomicBoolean ended = new AtomicBoolean();

    BufferedStartupStep(BufferedStartupStep parent, String name, long id, Instant startTime, Consumer<BufferedStartupStep> recorder) {
        this.parent = parent;
        this.name = name;
        this.id = id;
        this.startTime = startTime;
        this.recorder = recorder;
    }

    BufferedStartupStep getParent() {
        return this.parent;
    }

    public String getName() {
        return this.name;
    }

    public long getId() {
        return this.id;
    }

    Instant getStartTime() {
        return this.startTime;
    }

    public Long getParentId() {
        return this.parent != null ? Long.valueOf(this.parent.getId()) : null;
    }

    public StartupStep.Tags getTags() {
        return Collections.unmodifiableList(this.tags)::iterator;
    }

    public StartupStep tag(String key, Supplier<String> value) {
        return this.tag(key, value.get());
    }

    public StartupStep tag(String key, String value) {
        Assert.state((!this.ended.get() ? 1 : 0) != 0, (String)"StartupStep has already ended.");
        this.tags.add(new DefaultTag(key, value));
        return this;
    }

    public void end() {
        this.ended.set(true);
        this.recorder.accept(this);
    }

    boolean isEnded() {
        return this.ended.get();
    }

    static class DefaultTag
    implements StartupStep.Tag {
        private final String key;
        private final String value;

        DefaultTag(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return this.key;
        }

        public String getValue() {
            return this.value;
        }
    }
}

