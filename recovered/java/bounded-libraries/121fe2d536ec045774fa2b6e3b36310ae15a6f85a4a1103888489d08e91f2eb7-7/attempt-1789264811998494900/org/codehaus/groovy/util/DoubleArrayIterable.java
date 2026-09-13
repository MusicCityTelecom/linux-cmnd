/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.util;

import java.util.Iterator;
import org.codehaus.groovy.util.DoubleArrayIterator;

public class DoubleArrayIterable
implements Iterable<Double> {
    private final double[] array;

    public DoubleArrayIterable(double[] array) {
        this.array = array;
    }

    @Override
    public Iterator<Double> iterator() {
        return new DoubleArrayIterator(this.array);
    }
}

