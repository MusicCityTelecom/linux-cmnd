/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.inject.hk2;

import javax.inject.Provider;
import org.glassfish.hk2.api.Factory;
import org.glassfish.jersey.internal.util.collection.Ref;
import org.glassfish.jersey.internal.util.collection.Refs;

public abstract class Hk2ReferencingFactory<T>
implements Factory<T> {
    private final Provider<Ref<T>> referenceFactory;

    public Hk2ReferencingFactory(Provider<Ref<T>> referenceFactory) {
        this.referenceFactory = referenceFactory;
    }

    @Override
    public T provide() {
        return this.referenceFactory.get().get();
    }

    @Override
    public void dispose(T instance) {
    }

    public static <T> Factory<Ref<T>> referenceFactory() {
        return new EmptyReferenceFactory();
    }

    public static <T> Factory<Ref<T>> referenceFactory(T initialValue) {
        if (initialValue == null) {
            return new EmptyReferenceFactory();
        }
        return new InitializedReferenceFactory<T>(initialValue);
    }

    private static class InitializedReferenceFactory<T>
    implements Factory<Ref<T>> {
        private final T initialValue;

        public InitializedReferenceFactory(T initialValue) {
            this.initialValue = initialValue;
        }

        @Override
        public Ref<T> provide() {
            return Refs.of(this.initialValue);
        }

        @Override
        public void dispose(Ref<T> instance) {
        }
    }

    private static class EmptyReferenceFactory<T>
    implements Factory<Ref<T>> {
        private EmptyReferenceFactory() {
        }

        @Override
        public Ref<T> provide() {
            return Refs.emptyRef();
        }

        @Override
        public void dispose(Ref<T> instance) {
        }
    }
}

