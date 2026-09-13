/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.context.properties.bind;

import java.util.function.Supplier;
import org.springframework.boot.context.properties.bind.AggregateElementBinder;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;

abstract class AggregateBinder<T> {
    private final Binder.Context context;

    AggregateBinder(Binder.Context context) {
        this.context = context;
    }

    protected abstract boolean isAllowRecursiveBinding(ConfigurationPropertySource var1);

    final Object bind(ConfigurationPropertyName name, Bindable<?> target, AggregateElementBinder elementBinder) {
        Object result = this.bindAggregate(name, target, elementBinder);
        Supplier<?> value = target.getValue();
        if (result == null || value == null) {
            return result;
        }
        return this.merge(value, result);
    }

    protected abstract Object bindAggregate(ConfigurationPropertyName var1, Bindable<?> var2, AggregateElementBinder var3);

    protected abstract T merge(Supplier<T> var1, T var2);

    protected final Binder.Context getContext() {
        return this.context;
    }

    protected static class AggregateSupplier<T> {
        private final Supplier<T> supplier;
        private T supplied;

        public AggregateSupplier(Supplier<T> supplier) {
            this.supplier = supplier;
        }

        public T get() {
            if (this.supplied == null) {
                this.supplied = this.supplier.get();
            }
            return this.supplied;
        }

        public boolean wasSupplied() {
            return this.supplied != null;
        }
    }
}

