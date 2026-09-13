/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.CollectionFactory
 *  org.springframework.core.ResolvableType
 */
package org.springframework.boot.context.properties.bind;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import org.springframework.boot.context.properties.bind.AggregateElementBinder;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.bind.IndexedElementsBinder;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.core.CollectionFactory;
import org.springframework.core.ResolvableType;

class CollectionBinder
extends IndexedElementsBinder<Collection<Object>> {
    CollectionBinder(Binder.Context context) {
        super(context);
    }

    @Override
    protected Object bindAggregate(ConfigurationPropertyName name, Bindable<?> target, AggregateElementBinder elementBinder) {
        Class collectionType = target.getValue() != null ? List.class : target.getType().resolve(Object.class);
        ResolvableType aggregateType = ResolvableType.forClassWithGenerics(List.class, (ResolvableType[])target.getType().asCollection().getGenerics());
        ResolvableType elementType = target.getType().asCollection().getGeneric(new int[0]);
        IndexedElementsBinder.IndexedCollectionSupplier result = new IndexedElementsBinder.IndexedCollectionSupplier(() -> CollectionFactory.createCollection((Class)collectionType, (Class)elementType.resolve(), (int)0));
        this.bindIndexed(name, target, elementBinder, aggregateType, elementType, result);
        if (result.wasSupplied()) {
            return result.get();
        }
        return null;
    }

    @Override
    protected Collection<Object> merge(Supplier<Collection<Object>> existing, Collection<Object> additional) {
        Collection<Object> existingCollection = this.getExistingIfPossible(existing);
        if (existingCollection == null) {
            return additional;
        }
        try {
            existingCollection.clear();
            existingCollection.addAll(additional);
            return this.copyIfPossible(existingCollection);
        }
        catch (UnsupportedOperationException ex) {
            return this.createNewCollection(additional);
        }
    }

    private Collection<Object> getExistingIfPossible(Supplier<Collection<Object>> existing) {
        try {
            return existing.get();
        }
        catch (Exception ex) {
            return null;
        }
    }

    private Collection<Object> copyIfPossible(Collection<Object> collection) {
        try {
            return this.createNewCollection(collection);
        }
        catch (Exception ex) {
            return collection;
        }
    }

    private Collection<Object> createNewCollection(Collection<Object> collection) {
        Collection result = CollectionFactory.createCollection(collection.getClass(), (int)collection.size());
        result.addAll(collection);
        return result;
    }
}

