/*
 * Decompiled with CFR 0.152.
 */
package io.micrometer.core.instrument.step;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.Measurement;
import io.micrometer.core.instrument.Statistic;
import io.micrometer.core.instrument.step.StepDouble;
import java.util.concurrent.atomic.DoubleAdder;
import java.util.function.Supplier;

class StepMeasurement
extends Measurement {
    private final StepDouble value;
    private final DoubleAdder lastCount = new DoubleAdder();
    private final Supplier<Double> f;

    public StepMeasurement(Supplier<Double> f, Statistic statistic, Clock clock, long stepMillis) {
        super(f, statistic);
        this.f = f;
        this.value = new StepDouble(clock, stepMillis);
    }

    @Override
    public double getValue() {
        double absoluteCount = this.f.get();
        double inc = Math.max(0.0, absoluteCount - this.lastCount.sum());
        this.lastCount.add(inc);
        this.value.getCurrent().add(inc);
        return (Double)this.value.poll();
    }
}

