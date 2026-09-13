/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.util.gen;

import java.util.concurrent.atomic.AtomicLong;
import org.apereo.cas.util.gen.LongNumericGenerator;

public class DefaultLongNumericGenerator
implements LongNumericGenerator {
    private static final int MAX_STRING_LENGTH = Long.toString(Long.MAX_VALUE).length();
    private static final int MIN_STRING_LENGTH = 1;
    private final AtomicLong count;

    public DefaultLongNumericGenerator() {
        this(0L);
    }

    public DefaultLongNumericGenerator(long initialValue) {
        this.count = new AtomicLong(initialValue);
    }

    @Override
    public long getNextLong() {
        return this.getNextValue();
    }

    @Override
    public String getNextNumberAsString() {
        return Long.toString(this.getNextValue());
    }

    @Override
    public int maxLength() {
        return MAX_STRING_LENGTH;
    }

    @Override
    public int minLength() {
        return 1;
    }

    protected long getNextValue() {
        if (this.count.compareAndSet(Long.MAX_VALUE, 0L)) {
            return Long.MAX_VALUE;
        }
        return this.count.getAndIncrement();
    }
}

