/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package de.cronn.reflection.util.immutable.collection;

import de.cronn.reflection.util.immutable.Immutable;
import de.cronn.reflection.util.immutable.ImmutableProxy;
import de.cronn.reflection.util.immutable.ImmutableProxyOption;
import de.cronn.reflection.util.immutable.collection.ImmutableIterator;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public class DeepImmutableCollection<E>
extends AbstractCollection<E>
implements Collection<E>,
Immutable,
Serializable {
    private static final long serialVersionUID = 1L;
    private final String immutableMessage;
    private final Collection<E> delegate;
    private final Map<E, E> immutableProxyCache = new IdentityHashMap<E, E>();

    public DeepImmutableCollection(Collection<E> delegate) {
        this(delegate, "This collection is immutable");
    }

    DeepImmutableCollection(Collection<E> delegate, String immutableMessage) {
        this.delegate = Objects.requireNonNull(delegate);
        this.immutableMessage = immutableMessage;
    }

    E getImmutableElement(E element) {
        return (E)this.immutableProxyCache.computeIfAbsent(element, this::createImmutableElement);
    }

    E createImmutableElement(E value) {
        return ImmutableProxy.create(value, new ImmutableProxyOption[0]);
    }

    @Override
    public int size() {
        return this.delegate.size();
    }

    @Override
    public boolean isEmpty() {
        return this.delegate.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return this.delegate.contains(o);
    }

    @Override
    @NotNull
    public Iterator<E> iterator() {
        return new ImmutableIterator<E>(this, this.delegate.iterator(), this.immutableMessage);
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        return this.delegate.containsAll(c);
    }

    @Override
    public boolean equals(Object o) {
        return this.delegate.equals(o);
    }

    @Override
    public int hashCode() {
        return this.delegate.hashCode();
    }

    @Override
    public boolean add(E t) {
        throw new UnsupportedOperationException(this.immutableMessage);
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException(this.immutableMessage);
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends E> c) {
        throw new UnsupportedOperationException(this.immutableMessage);
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        throw new UnsupportedOperationException(this.immutableMessage);
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        throw new UnsupportedOperationException(this.immutableMessage);
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException(this.immutableMessage);
    }
}

