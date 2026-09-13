/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.util;

import java.util.Iterator;
import org.codehaus.groovy.util.LongArrayIterator;

public class LongArrayIterable
implements Iterable<Long> {
    private final long[] array;

    public LongArrayIterable(long[] array) {
        this.array = array;
    }

    @Override
    public Iterator<Long> iterator() {
        return new LongArrayIterator(this.array);
    }
}

