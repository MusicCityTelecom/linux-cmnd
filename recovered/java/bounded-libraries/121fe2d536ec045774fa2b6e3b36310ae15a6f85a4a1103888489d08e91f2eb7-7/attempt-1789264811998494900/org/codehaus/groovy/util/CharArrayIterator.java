/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.util;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class CharArrayIterator
implements Iterator<Character> {
    private final char[] array;
    private final int length;
    private int index;

    public CharArrayIterator(char[] array) {
        this.array = array;
        this.length = Array.getLength(array);
    }

    @Override
    public boolean hasNext() {
        return this.index < this.length;
    }

    @Override
    public Character next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        return Character.valueOf(this.array[this.index++]);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Remove not supported for arrays");
    }
}

