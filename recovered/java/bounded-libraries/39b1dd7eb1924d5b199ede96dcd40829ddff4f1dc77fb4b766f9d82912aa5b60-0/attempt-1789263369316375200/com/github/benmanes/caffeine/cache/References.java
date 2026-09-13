/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.checkerframework.checker.nullness.qual.Nullable
 */
package com.github.benmanes.caffeine.cache;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

final class References {
    private References() {
    }

    static final class SoftValueReference<V>
    extends SoftReference<V>
    implements InternalReference<V> {
        private Object keyReference;

        public SoftValueReference(Object keyReference, @Nullable V value, @Nullable ReferenceQueue<V> queue) {
            super(value, queue);
            this.keyReference = keyReference;
        }

        @Override
        public Object getKeyReference() {
            return this.keyReference;
        }

        public void setKeyReference(Object keyReference) {
            this.keyReference = keyReference;
        }

        public boolean equals(Object object) {
            return this.referenceEquals(object);
        }

        public int hashCode() {
            Object value = this.get();
            return value == null ? 0 : value.hashCode();
        }

        public String toString() {
            return String.format("%s{value=%s}", this.getClass().getSimpleName(), this.get());
        }
    }

    static final class WeakValueReference<V>
    extends WeakReference<V>
    implements InternalReference<V> {
        private Object keyReference;

        public WeakValueReference(Object keyReference, @Nullable V value, @Nullable ReferenceQueue<V> queue) {
            super(value, queue);
            this.keyReference = keyReference;
        }

        @Override
        public Object getKeyReference() {
            return this.keyReference;
        }

        public void setKeyReference(Object keyReference) {
            this.keyReference = keyReference;
        }

        public boolean equals(Object object) {
            return this.referenceEquals(object);
        }

        public int hashCode() {
            Object value = this.get();
            return value == null ? 0 : value.hashCode();
        }

        public String toString() {
            return String.format("%s{value=%s}", this.getClass().getSimpleName(), this.get());
        }
    }

    static final class WeakKeyEqualsReference<K>
    extends WeakReference<K>
    implements InternalReference<K> {
        private final int hashCode;

        public WeakKeyEqualsReference(K key, @Nullable ReferenceQueue<K> queue) {
            super(key, queue);
            this.hashCode = key.hashCode();
        }

        @Override
        public Object getKeyReference() {
            return this;
        }

        public boolean equals(Object object) {
            return this.objectEquals(object);
        }

        public int hashCode() {
            return this.hashCode;
        }

        public String toString() {
            return String.format("%s{key=%s, hashCode=%d}", this.getClass().getSimpleName(), this.get(), this.hashCode);
        }
    }

    static class WeakKeyReference<K>
    extends WeakReference<K>
    implements InternalReference<K> {
        private final int hashCode;

        public WeakKeyReference(@Nullable K key, @Nullable ReferenceQueue<K> queue) {
            super(key, queue);
            this.hashCode = System.identityHashCode(key);
        }

        @Override
        public Object getKeyReference() {
            return this;
        }

        public boolean equals(Object object) {
            return this.referenceEquals(object);
        }

        public int hashCode() {
            return this.hashCode;
        }

        public String toString() {
            return String.format("%s{key=%s, hashCode=%d}", this.getClass().getSimpleName(), this.get(), this.hashCode);
        }
    }

    static final class LookupKeyEqualsReference<K>
    implements InternalReference<K> {
        private final int hashCode;
        private final K key;

        public LookupKeyEqualsReference(K key) {
            this.hashCode = key.hashCode();
            this.key = Objects.requireNonNull(key);
        }

        @Override
        public K get() {
            return this.key;
        }

        @Override
        public Object getKeyReference() {
            return this;
        }

        public boolean equals(Object object) {
            return this.objectEquals(object);
        }

        public int hashCode() {
            return this.hashCode;
        }

        public String toString() {
            return String.format("%s{key=%s, hashCode=%d}", this.getClass().getSimpleName(), this.get(), this.hashCode);
        }
    }

    static final class LookupKeyReference<K>
    implements InternalReference<K> {
        private final int hashCode;
        private final K key;

        public LookupKeyReference(K key) {
            this.hashCode = System.identityHashCode(key);
            this.key = Objects.requireNonNull(key);
        }

        @Override
        public K get() {
            return this.key;
        }

        @Override
        public Object getKeyReference() {
            return this;
        }

        public boolean equals(Object object) {
            return this.referenceEquals(object);
        }

        public int hashCode() {
            return this.hashCode;
        }

        public String toString() {
            return String.format("%s{key=%s, hashCode=%d}", this.getClass().getSimpleName(), this.get(), this.hashCode);
        }
    }

    static interface InternalReference<E> {
        public @Nullable E get();

        public Object getKeyReference();

        default public boolean referenceEquals(@Nullable Object object) {
            if (object == this) {
                return true;
            }
            if (object instanceof InternalReference) {
                InternalReference referent = (InternalReference)object;
                return this.get() == referent.get();
            }
            return false;
        }

        default public boolean objectEquals(Object object) {
            if (object == this) {
                return true;
            }
            if (object instanceof InternalReference) {
                InternalReference referent = (InternalReference)object;
                return Objects.equals(this.get(), referent.get());
            }
            return false;
        }
    }
}

