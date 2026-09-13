/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.util;

import java.util.Iterator;
import org.codehaus.groovy.util.ArrayIterator;

public class ArrayIterable<T>
implements Iterable<T> {
    private final T[] array;

    public ArrayIterable(T[] array) {
        this.array = array;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayIterator<T>(this.array);
    }
}

