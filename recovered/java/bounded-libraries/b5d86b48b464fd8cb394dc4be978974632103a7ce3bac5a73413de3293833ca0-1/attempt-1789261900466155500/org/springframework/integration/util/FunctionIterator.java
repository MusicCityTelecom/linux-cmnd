/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 */
package org.springframework.integration.util;

import java.util.Iterator;
import java.util.function.Function;
import org.springframework.integration.util.CloseableIterator;
import org.springframework.lang.Nullable;

public class FunctionIterator<T, V>
implements CloseableIterator<V> {
    private final AutoCloseable closeable;
    private final Iterator<T> iterator;
    private final Function<? super T, ? extends V> function;

    public FunctionIterator(Iterable<T> iterable, Function<? super T, ? extends V> function) {
        this(null, iterable.iterator(), function);
    }

    public FunctionIterator(@Nullable AutoCloseable closeable, Iterable<T> iterable, Function<? super T, ? extends V> function) {
        this(closeable, iterable.iterator(), function);
    }

    public FunctionIterator(Iterator<T> newIterator, Function<? super T, ? extends V> function) {
        this(null, newIterator, function);
    }

    public FunctionIterator(@Nullable AutoCloseable closeable, Iterator<T> newIterator, Function<? super T, ? extends V> function) {
        this.closeable = closeable;
        this.iterator = newIterator;
        this.function = function;
    }

    @Override
    public boolean hasNext() {
        return this.iterator.hasNext();
    }

    @Override
    public V next() {
        return this.function.apply(this.iterator.next());
    }

    @Override
    public void close() {
        if (this.iterator instanceof AutoCloseable) {
            try {
                ((AutoCloseable)((Object)this.iterator)).close();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (this.closeable != null) {
            try {
                this.closeable.close();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }
}

