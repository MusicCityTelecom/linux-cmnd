/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.util;

import java.util.Iterator;
import org.codehaus.groovy.util.IntArrayIterator;

public class IntArrayIterable
implements Iterable<Integer> {
    private final int[] array;

    public IntArrayIterable(int[] array) {
        this.array = array;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new IntArrayIterator(this.array);
    }
}

