/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.util.collection;

import org.glassfish.jersey.internal.util.collection.Ref;

public final class Refs {
    private Refs() {
    }

    public static <T> Ref<T> of(T value) {
        return new DefaultRefImpl<T>(value);
    }

    public static <T> Ref<T> emptyRef() {
        return new DefaultRefImpl();
    }

    public static <T> Ref<T> threadSafe() {
        return new ThreadSafeRefImpl();
    }

    public static <T> Ref<T> threadSafe(T value) {
        return new ThreadSafeRefImpl<T>(value);
    }

    public static <T> Ref<T> immutableRef(T value) {
        return new ImmutableRefImpl<T>(value);
    }

    private static final class ThreadSafeRefImpl<T>
    implements Ref<T> {
        private volatile T reference;

        ThreadSafeRefImpl() {
            this.reference = null;
        }

        ThreadSafeRefImpl(T value) {
            this.reference = value;
        }

        @Override
        public T get() {
            return this.reference;
        }

        @Override
        public void set(T value) throws IllegalStateException {
            this.reference = value;
        }

        public String toString() {
            return "ThreadSafeRefImpl{reference=" + this.reference + '}';
        }

        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (!(obj instanceof Ref)) {
                return false;
            }
            T localRef = this.reference;
            Object otherRef = ((Ref)obj).get();
            return localRef == otherRef || localRef != null && localRef.equals(otherRef);
        }

        public int hashCode() {
            T localRef = this.reference;
            int hash = 5;
            hash = 47 * hash + (localRef != null ? localRef.hashCode() : 0);
            return hash;
        }
    }

    private static final class DefaultRefImpl<T>
    implements Ref<T> {
        private T reference;

        DefaultRefImpl() {
            this.reference = null;
        }

        DefaultRefImpl(T value) {
            this.reference = value;
        }

        @Override
        public T get() {
            return this.reference;
        }

        @Override
        public void set(T value) throws IllegalStateException {
            this.reference = value;
        }

        public String toString() {
            return "DefaultRefImpl{reference=" + this.reference + '}';
        }

        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (!(obj instanceof Ref)) {
                return false;
            }
            T ref = this.reference;
            Object otherRef = ((Ref)obj).get();
            return ref == otherRef || ref != null && ref.equals(otherRef);
        }

        public int hashCode() {
            int hash = 5;
            hash = 47 * hash + (this.reference != null ? this.reference.hashCode() : 0);
            return hash;
        }
    }

    private static final class ImmutableRefImpl<T>
    implements Ref<T> {
        private final T reference;

        ImmutableRefImpl(T value) {
            this.reference = value;
        }

        @Override
        public T get() {
            return this.reference;
        }

        @Override
        public void set(T value) throws IllegalStateException {
            throw new IllegalStateException("This implementation of Ref interface is immutable.");
        }

        public String toString() {
            return "ImmutableRefImpl{reference=" + this.reference + '}';
        }

        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (!(obj instanceof Ref)) {
                return false;
            }
            Object otherRef = ((Ref)obj).get();
            return this.reference == otherRef || this.reference != null && this.reference.equals(otherRef);
        }

        public int hashCode() {
            int hash = 5;
            hash = 47 * hash + (this.reference != null ? this.reference.hashCode() : 0);
            return hash;
        }
    }
}

