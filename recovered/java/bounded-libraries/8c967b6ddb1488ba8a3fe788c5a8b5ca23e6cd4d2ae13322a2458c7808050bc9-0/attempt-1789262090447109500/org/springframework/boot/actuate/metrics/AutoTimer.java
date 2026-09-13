/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.micrometer.core.annotation.Timed
 *  io.micrometer.core.instrument.Timer
 *  io.micrometer.core.instrument.Timer$Builder
 *  org.springframework.util.CollectionUtils
 */
package org.springframework.boot.actuate.metrics;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Timer;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.springframework.util.CollectionUtils;

@FunctionalInterface
public interface AutoTimer {
    public static final AutoTimer ENABLED = builder -> {};
    public static final AutoTimer DISABLED = new AutoTimer(){

        @Override
        public boolean isEnabled() {
            return false;
        }

        @Override
        public void apply(Timer.Builder builder) {
            throw new IllegalStateException("AutoTimer is disabled");
        }
    };

    default public boolean isEnabled() {
        return true;
    }

    default public Timer.Builder builder(String name) {
        return this.builder(() -> Timer.builder((String)name));
    }

    default public Timer.Builder builder(Supplier<Timer.Builder> supplier) {
        Timer.Builder builder = supplier.get();
        this.apply(builder);
        return builder;
    }

    public void apply(Timer.Builder var1);

    public static void apply(AutoTimer autoTimer, String metricName, Set<Timed> annotations, Consumer<Timer.Builder> action) {
        if (!CollectionUtils.isEmpty(annotations)) {
            for (Timed annotation : annotations) {
                action.accept(Timer.builder((Timed)annotation, (String)metricName));
            }
        } else if (autoTimer != null && autoTimer.isEnabled()) {
            action.accept(autoTimer.builder(metricName));
        }
    }
}

