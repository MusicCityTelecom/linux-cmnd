/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.inject;

import java.util.function.Supplier;
import javax.inject.Provider;
import org.glassfish.jersey.internal.util.collection.Ref;
import org.glassfish.jersey.internal.util.collection.Refs;

public abstract class ReferencingFactory<T>
implements Supplier<T> {
    private final Provider<Ref<T>> referenceFactory;

    public ReferencingFactory(Provider<Ref<T>> referenceFactory) {
        this.referenceFactory = referenceFactory;
    }

    @Override
    public T get() {
        return this.referenceFactory.get().get();
    }

    public static <T> Supplier<Ref<T>> referenceFactory() {
        return new EmptyReferenceFactory();
    }

    public static <T> Supplier<Ref<T>> referenceFactory(T initialValue) {
        if (initialValue == null) {
            return new EmptyReferenceFactory();
        }
        return new InitializedReferenceFactory<T>(initialValue);
    }

    private static class InitializedReferenceFactory<T>
    implements Supplier<Ref<T>> {
        private final T initialValue;

        public InitializedReferenceFactory(T initialValue) {
            this.initialValue = initialValue;
        }

        @Override
        public Ref<T> get() {
            return Refs.of(this.initialValue);
        }
    }

    private static class EmptyReferenceFactory<T>
    implements Supplier<Ref<T>> {
        private EmptyReferenceFactory() {
        }

        @Override
        public Ref<T> get() {
            return Refs.emptyRef();
        }
    }
}

