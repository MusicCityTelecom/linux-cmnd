/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client.internal.inject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.ext.ParamConverter;
import org.glassfish.jersey.client.inject.ParameterUpdater;
import org.glassfish.jersey.client.internal.LocalizationMessages;
import org.glassfish.jersey.client.internal.inject.AbstractParamValueUpdater;

abstract class CollectionUpdater<T>
extends AbstractParamValueUpdater<T>
implements ParameterUpdater<Collection<T>, Collection<String>> {
    protected CollectionUpdater(ParamConverter<T> converter, String parameterName, String defaultValue) {
        super(converter, parameterName, defaultValue);
    }

    @Override
    public Collection<String> update(Collection<T> values) {
        Collection<String> results = Collections.EMPTY_LIST;
        if (values != null) {
            results = values.stream().map(item -> this.toString(item)).collect(Collectors.toList());
        } else if (this.isDefaultValueRegistered()) {
            results = Collections.singletonList(this.getDefaultValueString());
        }
        return results;
    }

    protected abstract Collection<String> newCollection();

    public static <T> CollectionUpdater getInstance(Class<?> collectionType, ParamConverter<T> converter, String parameterName, String defaultValue) {
        if (List.class == collectionType) {
            return new ListValueOf<T>(converter, parameterName, defaultValue);
        }
        if (Set.class == collectionType) {
            return new SetValueOf<T>(converter, parameterName, defaultValue);
        }
        if (SortedSet.class == collectionType) {
            return new SortedSetValueOf<T>(converter, parameterName, defaultValue);
        }
        throw new ProcessingException(LocalizationMessages.COLLECTION_UPDATER_TYPE_UNSUPPORTED());
    }

    private static final class SortedSetValueOf<T>
    extends CollectionUpdater<T> {
        SortedSetValueOf(ParamConverter<T> converter, String parameter, String defaultValue) {
            super(converter, parameter, defaultValue);
        }

        protected SortedSet<String> newCollection() {
            return new TreeSet<String>();
        }
    }

    private static final class SetValueOf<T>
    extends CollectionUpdater<T> {
        SetValueOf(ParamConverter<T> converter, String parameter, String defaultValue) {
            super(converter, parameter, defaultValue);
        }

        protected Set<String> newCollection() {
            return new HashSet<String>();
        }
    }

    private static final class ListValueOf<T>
    extends CollectionUpdater<T> {
        ListValueOf(ParamConverter<T> converter, String parameter, String defaultValue) {
            super(converter, parameter, defaultValue);
        }

        protected List<String> newCollection() {
            return new ArrayList<String>();
        }
    }
}

