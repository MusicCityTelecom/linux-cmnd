/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.micrometer.core.instrument.MeterRegistry
 *  io.micrometer.core.instrument.Tag
 *  io.micrometer.core.instrument.binder.MeterBinder
 *  io.micrometer.core.instrument.binder.system.DiskSpaceMetrics
 *  org.springframework.util.Assert
 */
package org.springframework.boot.actuate.metrics.system;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.binder.MeterBinder;
import io.micrometer.core.instrument.binder.system.DiskSpaceMetrics;
import java.io.File;
import java.util.List;
import org.springframework.util.Assert;

public class DiskSpaceMetricsBinder
implements MeterBinder {
    private final List<File> paths;
    private final Iterable<Tag> tags;

    public DiskSpaceMetricsBinder(List<File> paths, Iterable<Tag> tags) {
        Assert.notEmpty(paths, (String)"Paths must not be empty");
        this.paths = paths;
        this.tags = tags;
    }

    public void bindTo(MeterRegistry registry) {
        this.paths.forEach(path -> new DiskSpaceMetrics(path, this.tags).bindTo(registry));
    }
}

