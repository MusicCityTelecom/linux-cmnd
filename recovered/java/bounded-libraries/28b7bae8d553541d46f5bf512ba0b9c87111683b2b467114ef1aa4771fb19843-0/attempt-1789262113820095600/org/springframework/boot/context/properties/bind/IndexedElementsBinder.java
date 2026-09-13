/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.ResolvableType
 *  org.springframework.util.LinkedMultiValueMap
 *  org.springframework.util.MultiValueMap
 */
package org.springframework.boot.context.properties.bind;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.TreeSet;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.springframework.boot.context.properties.bind.AggregateBinder;
import org.springframework.boot.context.properties.bind.AggregateElementBinder;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.bind.UnboundConfigurationPropertiesException;
import org.springframework.boot.context.properties.source.ConfigurationProperty;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.IterableConfigurationPropertySource;
import org.springframework.core.ResolvableType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

abstract class IndexedElementsBinder<T>
extends AggregateBinder<T> {
    private static final String INDEX_ZERO = "[0]";

    IndexedElementsBinder(Binder.Context context) {
        super(context);
    }

    @Override
    protected boolean isAllowRecursiveBinding(ConfigurationPropertySource source) {
        return source == null || source instanceof IterableConfigurationPropertySource;
    }

    protected final void bindIndexed(ConfigurationPropertyName name, Bindable<?> target, AggregateElementBinder elementBinder, ResolvableType aggregateType, ResolvableType elementType, IndexedCollectionSupplier result) {
        for (ConfigurationPropertySource source : this.getContext().getSources()) {
            this.bindIndexed(source, name, target, elementBinder, result, aggregateType, elementType);
            if (!result.wasSupplied() || result.get() == null) continue;
            return;
        }
    }

    private void bindIndexed(ConfigurationPropertySource source, ConfigurationPropertyName root, Bindable<?> target, AggregateElementBinder elementBinder, IndexedCollectionSupplier collection, ResolvableType aggregateType, ResolvableType elementType) {
        ConfigurationProperty property = source.getConfigurationProperty(root);
        if (property != null) {
            this.getContext().setConfigurationProperty(property);
            this.bindValue(target, (Collection)collection.get(), aggregateType, elementType, property.getValue());
        } else {
            this.bindIndexed(source, root, elementBinder, collection, elementType);
        }
    }

    private void bindValue(Bindable<?> target, Collection<Object> collection, ResolvableType aggregateType, ResolvableType elementType, Object value) {
        if (value == null || value instanceof CharSequence && ((CharSequence)value).length() == 0) {
            return;
        }
        Object aggregate = this.convert(value, aggregateType, target.getAnnotations());
        ResolvableType collectionType = ResolvableType.forClassWithGenerics(collection.getClass(), (ResolvableType[])new ResolvableType[]{elementType});
        Collection elements = (Collection)this.convert(aggregate, collectionType, new Annotation[0]);
        collection.addAll(elements);
    }

    private void bindIndexed(ConfigurationPropertySource source, ConfigurationPropertyName root, AggregateElementBinder elementBinder, IndexedCollectionSupplier collection, ResolvableType elementType) {
        ConfigurationPropertyName name;
        Object value;
        MultiValueMap<String, ConfigurationPropertyName> knownIndexedChildren = this.getKnownIndexedChildren(source, root);
        for (int i = 0; i < Integer.MAX_VALUE && (value = elementBinder.bind(name = root.append(i != 0 ? "[" + i + "]" : INDEX_ZERO), Bindable.of(elementType), source)) != null; ++i) {
            knownIndexedChildren.remove((Object)name.getLastElement(ConfigurationPropertyName.Form.UNIFORM));
            ((Collection)collection.get()).add(value);
        }
        this.assertNoUnboundChildren(source, knownIndexedChildren);
    }

    private MultiValueMap<String, ConfigurationPropertyName> getKnownIndexedChildren(ConfigurationPropertySource source, ConfigurationPropertyName root) {
        LinkedMultiValueMap children = new LinkedMultiValueMap();
        if (!(source instanceof IterableConfigurationPropertySource)) {
            return children;
        }
        for (ConfigurationPropertyName name : (IterableConfigurationPropertySource)source.filter(root::isAncestorOf)) {
            ConfigurationPropertyName choppedName = name.chop(root.getNumberOfElements() + 1);
            if (!choppedName.isLastElementIndexed()) continue;
            String key = choppedName.getLastElement(ConfigurationPropertyName.Form.UNIFORM);
            children.add((Object)key, (Object)name);
        }
        return children;
    }

    private void assertNoUnboundChildren(ConfigurationPropertySource source, MultiValueMap<String, ConfigurationPropertyName> children) {
        if (!children.isEmpty()) {
            throw new UnboundConfigurationPropertiesException(children.values().stream().flatMap(Collection::stream).map(source::getConfigurationProperty).collect(Collectors.toCollection(TreeSet::new)));
        }
    }

    private <C> C convert(Object value, ResolvableType type, Annotation ... annotations) {
        value = this.getContext().getPlaceholdersResolver().resolvePlaceholders(value);
        return (C)this.getContext().getConverter().convert(value, type, annotations);
    }

    protected static class IndexedCollectionSupplier
    extends AggregateBinder.AggregateSupplier<Collection<Object>> {
        public IndexedCollectionSupplier(Supplier<Collection<Object>> supplier) {
            super(supplier);
        }
    }
}

