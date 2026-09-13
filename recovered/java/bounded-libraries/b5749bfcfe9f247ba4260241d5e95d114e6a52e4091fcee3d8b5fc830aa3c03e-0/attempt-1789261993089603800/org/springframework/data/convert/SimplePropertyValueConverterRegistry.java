/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.ObjectUtils
 */
package org.springframework.data.convert;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.data.convert.PropertyValueConverter;
import org.springframework.data.convert.ValueConversionContext;
import org.springframework.data.convert.ValueConverterRegistry;
import org.springframework.data.mapping.PersistentProperty;
import org.springframework.util.ObjectUtils;

public class SimplePropertyValueConverterRegistry<P extends PersistentProperty<P>>
implements ValueConverterRegistry<P> {
    private final Map<Key, PropertyValueConverter<?, ?, ? extends ValueConversionContext<P>>> converterRegistrationMap = new LinkedHashMap();

    public SimplePropertyValueConverterRegistry() {
    }

    SimplePropertyValueConverterRegistry(SimplePropertyValueConverterRegistry<P> source) {
        this.converterRegistrationMap.putAll(source.converterRegistrationMap);
    }

    @Override
    public void registerConverter(Class<?> type, String path, PropertyValueConverter<?, ?, ? extends ValueConversionContext<P>> converter) {
        this.converterRegistrationMap.put(new Key(type, path), converter);
    }

    public void registerConverterIfAbsent(Class<?> type, String path, PropertyValueConverter<?, ?, ? extends ValueConversionContext<P>> converter) {
        this.converterRegistrationMap.putIfAbsent(new Key(type, path), converter);
    }

    @Override
    public boolean containsConverterFor(Class<?> type, String path) {
        return this.converterRegistrationMap.containsKey(new Key(type, path));
    }

    @Override
    public <S, T> PropertyValueConverter<S, T, ? extends ValueConversionContext<P>> getConverter(Class<?> type, String path) {
        return this.converterRegistrationMap.get(new Key(type, path));
    }

    public int size() {
        return this.converterRegistrationMap.size();
    }

    @Override
    public boolean isEmpty() {
        return this.converterRegistrationMap.isEmpty();
    }

    Map<Key, PropertyValueConverter<?, ?, ? extends ValueConversionContext<P>>> getConverterRegistrationMap() {
        return this.converterRegistrationMap;
    }

    static class Key {
        final Class<?> type;
        final String path;

        public Key(Class<?> type, String path) {
            this.type = type;
            this.path = path;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            Key key = (Key)o;
            if (!ObjectUtils.nullSafeEquals(this.type, key.type)) {
                return false;
            }
            return ObjectUtils.nullSafeEquals((Object)this.path, (Object)key.path);
        }

        public int hashCode() {
            int result = ObjectUtils.nullSafeHashCode(this.type);
            result = 31 * result + ObjectUtils.nullSafeHashCode((Object)this.path);
            return result;
        }
    }
}

