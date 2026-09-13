/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.pool2;

import java.io.Closeable;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.apache.commons.pool2.DestroyMode;

public interface KeyedObjectPool<K, V>
extends Closeable {
    public void addObject(K var1) throws Exception, IllegalStateException, UnsupportedOperationException;

    default public void addObjects(Collection<K> keys, int count) throws Exception, IllegalArgumentException {
        if (keys == null) {
            throw new IllegalArgumentException("keys must not be null.");
        }
        Iterator<K> iter = keys.iterator();
        while (iter.hasNext()) {
            this.addObjects(iter.next(), count);
        }
    }

    default public void addObjects(K key, int count) throws Exception, IllegalArgumentException {
        if (key == null) {
            throw new IllegalArgumentException("key must not be null.");
        }
        for (int i = 0; i < count; ++i) {
            this.addObject(key);
        }
    }

    public V borrowObject(K var1) throws Exception, NoSuchElementException, IllegalStateException;

    public void clear() throws Exception, UnsupportedOperationException;

    public void clear(K var1) throws Exception, UnsupportedOperationException;

    @Override
    public void close();

    public int getNumActive();

    public int getNumActive(K var1);

    public int getNumIdle();

    public int getNumIdle(K var1);

    public void invalidateObject(K var1, V var2) throws Exception;

    default public void invalidateObject(K key, V obj, DestroyMode destroyMode) throws Exception {
        this.invalidateObject(key, obj);
    }

    public void returnObject(K var1, V var2) throws Exception;
}

