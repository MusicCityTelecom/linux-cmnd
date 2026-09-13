/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.util.collection;

import org.glassfish.jersey.internal.util.collection.LazyUnsafeValue;
import org.glassfish.jersey.internal.util.collection.LazyValue;
import org.glassfish.jersey.internal.util.collection.UnsafeValue;
import org.glassfish.jersey.internal.util.collection.Value;

public final class Values {
    private static final LazyValue EMPTY = new LazyValue(){

        @Override
        public Object get() {
            return null;
        }

        @Override
        public boolean isInitialized() {
            return true;
        }
    };
    private static final LazyUnsafeValue EMPTY_UNSAFE = new LazyUnsafeValue(){

        @Override
        public Object get() {
            return null;
        }

        @Override
        public boolean isInitialized() {
            return true;
        }
    };

    private Values() {
    }

    public static <T> Value<T> empty() {
        return EMPTY;
    }

    public static <T, E extends Throwable> UnsafeValue<T, E> emptyUnsafe() {
        return EMPTY_UNSAFE;
    }

    public static <T> Value<T> of(T value) {
        return value == null ? Values.empty() : new InstanceValue<T>(value);
    }

    public static <T, E extends Throwable> UnsafeValue<T, E> unsafe(T value) {
        return value == null ? Values.emptyUnsafe() : new InstanceUnsafeValue(value);
    }

    public static <T, E extends Throwable> UnsafeValue<T, E> throwing(E throwable) {
        if (throwable == null) {
            throw new NullPointerException("Supplied throwable ");
        }
        return new ExceptionValue(throwable);
    }

    public static <T> LazyValue<T> lazy(Value<T> delegate) {
        return delegate == null ? EMPTY : new LazyValueImpl<T>(delegate);
    }

    public static <T> Value<T> eager(Value<T> delegate) {
        return delegate == null ? Values.empty() : new EagerValue(delegate);
    }

    public static <T, E extends Throwable> LazyUnsafeValue<T, E> lazy(UnsafeValue<T, E> delegate) {
        return delegate == null ? EMPTY_UNSAFE : new LazyUnsafeValueImpl<T, E>(delegate);
    }

    private static class LazyUnsafeValueImpl<T, E extends Throwable>
    implements LazyUnsafeValue<T, E> {
        private final Object lock;
        private final UnsafeValue<T, E> delegate;
        private volatile UnsafeValue<T, E> value;

        public LazyUnsafeValueImpl(UnsafeValue<T, E> delegate) {
            this.delegate = delegate;
            this.lock = new Object();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public T get() throws E {
            UnsafeValue<T, Object> result = this.value;
            if (result == null) {
                Object object = this.lock;
                synchronized (object) {
                    result = this.value;
                    if (result == null) {
                        try {
                            result = Values.unsafe(this.delegate.get());
                        }
                        catch (Throwable e) {
                            result = Values.throwing(e);
                        }
                        this.value = result;
                    }
                }
            }
            return result.get();
        }

        @Override
        public boolean isInitialized() {
            return this.value != null;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            return this.delegate.equals(((LazyUnsafeValueImpl)o).delegate);
        }

        public int hashCode() {
            return this.delegate != null ? this.delegate.hashCode() : 0;
        }

        public String toString() {
            return "LazyValue{delegate=" + this.delegate.toString() + '}';
        }
    }

    private static class LazyValueImpl<T>
    implements LazyValue<T> {
        private final Object lock;
        private final Value<T> delegate;
        private volatile Value<T> value;

        public LazyValueImpl(Value<T> delegate) {
            this.delegate = delegate;
            this.lock = new Object();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public T get() {
            Value<T> result = this.value;
            if (result == null) {
                Object object = this.lock;
                synchronized (object) {
                    result = this.value;
                    if (result == null) {
                        result = Values.of(this.delegate.get());
                        this.value = result;
                    }
                }
            }
            return result.get();
        }

        @Override
        public boolean isInitialized() {
            return this.value != null;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            return this.delegate.equals(((LazyValueImpl)o).delegate);
        }

        public int hashCode() {
            return this.delegate != null ? this.delegate.hashCode() : 0;
        }

        public String toString() {
            return "LazyValue{delegate=" + this.delegate.toString() + '}';
        }
    }

    private static class EagerValue<T>
    implements Value<T> {
        private final T result;

        private EagerValue(Value<T> value) {
            this.result = value.get();
        }

        @Override
        public T get() {
            return this.result;
        }
    }

    private static class ExceptionValue<T, E extends Throwable>
    implements UnsafeValue<T, E> {
        private final E throwable;

        public ExceptionValue(E throwable) {
            this.throwable = throwable;
        }

        @Override
        public T get() throws E {
            throw this.throwable;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            return this.throwable.equals(((ExceptionValue)o).throwable);
        }

        public int hashCode() {
            return this.throwable != null ? this.throwable.hashCode() : 0;
        }

        public String toString() {
            return "ExceptionValue{throwable=" + this.throwable + '}';
        }
    }

    private static class InstanceUnsafeValue<T, E extends Throwable>
    implements UnsafeValue<T, E> {
        private final T value;

        public InstanceUnsafeValue(T value) {
            this.value = value;
        }

        @Override
        public T get() {
            return this.value;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            return this.value.equals(((InstanceUnsafeValue)o).value);
        }

        public int hashCode() {
            return this.value != null ? this.value.hashCode() : 0;
        }

        public String toString() {
            return "InstanceUnsafeValue{value=" + this.value + '}';
        }
    }

    private static class InstanceValue<T>
    implements Value<T> {
        private final T value;

        public InstanceValue(T value) {
            this.value = value;
        }

        @Override
        public T get() {
            return this.value;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            return this.value.equals(((InstanceValue)o).value);
        }

        public int hashCode() {
            return this.value != null ? this.value.hashCode() : 0;
        }

        public String toString() {
            return "InstanceValue{value=" + this.value + '}';
        }
    }
}

