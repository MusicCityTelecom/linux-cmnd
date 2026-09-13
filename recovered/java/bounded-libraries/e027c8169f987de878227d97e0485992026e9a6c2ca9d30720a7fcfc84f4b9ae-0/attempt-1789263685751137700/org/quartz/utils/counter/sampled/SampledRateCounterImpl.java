/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.utils.counter.sampled;

import org.quartz.utils.counter.sampled.SampledCounterImpl;
import org.quartz.utils.counter.sampled.SampledRateCounter;
import org.quartz.utils.counter.sampled.SampledRateCounterConfig;

public class SampledRateCounterImpl
extends SampledCounterImpl
implements SampledRateCounter {
    private static final long serialVersionUID = 6531350452676920607L;
    private static final String OPERATION_NOT_SUPPORTED_MSG = "This operation is not supported. Use SampledCounter Or Counter instead";
    private long numeratorValue;
    private long denominatorValue;

    public SampledRateCounterImpl(SampledRateCounterConfig config) {
        super(config);
    }

    @Override
    public synchronized void setValue(long numerator, long denominator) {
        this.numeratorValue = numerator;
        this.denominatorValue = denominator;
    }

    @Override
    public synchronized void increment(long numerator, long denominator) {
        this.numeratorValue += numerator;
        this.denominatorValue += denominator;
    }

    @Override
    public synchronized void decrement(long numerator, long denominator) {
        this.numeratorValue -= numerator;
        this.denominatorValue -= denominator;
    }

    @Override
    public synchronized void setDenominatorValue(long newValue) {
        this.denominatorValue = newValue;
    }

    @Override
    public synchronized void setNumeratorValue(long newValue) {
        this.numeratorValue = newValue;
    }

    @Override
    public synchronized long getValue() {
        return this.denominatorValue == 0L ? 0L : this.numeratorValue / this.denominatorValue;
    }

    @Override
    public synchronized long getAndReset() {
        long prevVal = this.getValue();
        this.setValue(0L, 0L);
        return prevVal;
    }

    @Override
    public long getAndSet(long newValue) {
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_MSG);
    }

    @Override
    public synchronized void setValue(long newValue) {
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_MSG);
    }

    @Override
    public long decrement() {
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_MSG);
    }

    @Override
    public long decrement(long amount) {
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_MSG);
    }

    public long getMaxValue() {
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_MSG);
    }

    public long getMinValue() {
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_MSG);
    }

    @Override
    public long increment() {
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_MSG);
    }

    @Override
    public long increment(long amount) {
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_MSG);
    }
}

