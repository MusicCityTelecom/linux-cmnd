/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.OrderComparator
 *  org.springframework.core.Ordered
 *  org.springframework.util.Assert
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.dispatcher;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.springframework.core.OrderComparator;
import org.springframework.core.Ordered;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

class OrderedAwareCopyOnWriteArraySet<E>
implements Set<E> {
    private final OrderComparator comparator = new OrderComparator();
    private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock.ReadLock readLock = this.rwl.readLock();
    private final ReentrantReadWriteLock.WriteLock writeLock = this.rwl.writeLock();
    private final CopyOnWriteArraySet<E> elements = new CopyOnWriteArraySet();
    private final Set<E> unmodifiableElements = Collections.unmodifiableSet(this.elements);

    OrderedAwareCopyOnWriteArraySet() {
    }

    public Set<E> asUnmodifiableSet() {
        return this.unmodifiableElements;
    }

    @Override
    public boolean add(E o) {
        Assert.notNull(o, (String)"Can not add NULL object");
        this.writeLock.lock();
        try {
            if (o instanceof Ordered) {
                boolean bl = this.addOrderedElement((Ordered)o);
                return bl;
            }
            boolean bl = this.elements.add(o);
            return bl;
        }
        finally {
            this.writeLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean addAll(Collection<? extends E> c) {
        Assert.notNull(c, (String)"Can not merge with NULL set");
        this.writeLock.lock();
        try {
            for (E object : c) {
                this.add(object);
            }
            boolean bl = true;
            return bl;
        }
        finally {
            this.writeLock.unlock();
        }
    }

    @Override
    public boolean remove(Object o) {
        this.writeLock.lock();
        try {
            boolean bl = this.elements.remove(o);
            return bl;
        }
        finally {
            this.writeLock.unlock();
        }
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (CollectionUtils.isEmpty(c)) {
            return false;
        }
        this.writeLock.lock();
        try {
            boolean bl = this.elements.removeAll(c);
            return bl;
        }
        finally {
            this.writeLock.unlock();
        }
    }

    @Override
    public <T> T[] toArray(T[] a) {
        this.readLock.lock();
        try {
            T[] TArray = this.elements.toArray(a);
            return TArray;
        }
        finally {
            this.readLock.unlock();
        }
    }

    public String toString() {
        this.readLock.lock();
        try {
            String string = StringUtils.collectionToCommaDelimitedString(this.elements);
            return string;
        }
        finally {
            this.readLock.unlock();
        }
    }

    private boolean addOrderedElement(Ordered adding) {
        boolean added = false;
        Object[] tempUnorderedElements = this.elements.toArray();
        if (this.elements.contains(adding)) {
            return false;
        }
        this.elements.clear();
        if (tempUnorderedElements.length == 0) {
            added = this.elements.add(adding);
        } else {
            LinkedHashSet<Object> tempSet = new LinkedHashSet<Object>();
            for (Object current : tempUnorderedElements) {
                if (current instanceof Ordered) {
                    if (this.comparator.compare((Object)adding, current) < 0) {
                        added = this.elements.add(adding);
                        this.elements.add(current);
                        continue;
                    }
                    this.elements.add(current);
                    continue;
                }
                tempSet.add(current);
            }
            if (!added) {
                added = this.elements.add(adding);
            }
            for (Object e : tempSet) {
                this.elements.add(e);
            }
        }
        return added;
    }

    @Override
    public Iterator<E> iterator() {
        return this.elements.iterator();
    }

    @Override
    public int size() {
        return this.elements.size();
    }

    @Override
    public boolean isEmpty() {
        return this.elements.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return this.elements.contains(o);
    }

    @Override
    public Object[] toArray() {
        return this.elements.toArray();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return this.elements.containsAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return this.elements.retainAll(c);
    }

    @Override
    public void clear() {
        this.elements.clear();
    }
}

