/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeanUtils
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.config.AutowireCapableBeanFactory
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 */
package org.springframework.data.convert;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.data.convert.AnnotatedPropertyValueConverterAccessor;
import org.springframework.data.convert.PropertyValueConverter;
import org.springframework.data.convert.PropertyValueConverterFactory;
import org.springframework.data.convert.ValueConversionContext;
import org.springframework.data.convert.ValueConverterRegistry;
import org.springframework.data.mapping.PersistentProperty;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

final class PropertyValueConverterFactories {
    PropertyValueConverterFactories() {
    }

    static class CachingPropertyValueConverterFactory
    implements PropertyValueConverterFactory {
        private final PropertyValueConverterFactory delegate;
        private final Cache cache = new Cache();

        CachingPropertyValueConverterFactory(PropertyValueConverterFactory delegate) {
            Assert.notNull((Object)delegate, (String)"Delegate must not be null");
            this.delegate = delegate;
        }

        @Nullable
        public <DV, SV, C extends ValueConversionContext<?>> PropertyValueConverter<DV, SV, C> getConverter(PersistentProperty<?> property) {
            Optional<PropertyValueConverter<?, ?, ValueConversionContext<?>>> converter = this.cache.get(property);
            return converter != null ? (PropertyValueConverter)converter.orElse(null) : this.cache.cache(property, this.delegate.getConverter(property));
        }

        @Override
        public <DV, SV, C extends ValueConversionContext<?>> PropertyValueConverter<DV, SV, C> getConverter(Class<? extends PropertyValueConverter<DV, SV, C>> converterType) {
            Optional<PropertyValueConverter<?, ?, ValueConversionContext<?>>> converter = this.cache.get(converterType);
            return converter != null ? (PropertyValueConverter)converter.orElse(null) : this.cache.cache(converterType, this.delegate.getConverter(converterType));
        }

        static class Cache {
            Map<PersistentProperty<?>, Optional<PropertyValueConverter<?, ?, ? extends ValueConversionContext<?>>>> perPropertyCache = new ConcurrentHashMap();
            Map<Class<?>, Optional<PropertyValueConverter<?, ?, ? extends ValueConversionContext<?>>>> typeCache = new ConcurrentHashMap();

            Cache() {
            }

            Optional<PropertyValueConverter<?, ?, ? extends ValueConversionContext<?>>> get(PersistentProperty<?> property) {
                return this.perPropertyCache.get(property);
            }

            Optional<PropertyValueConverter<?, ?, ? extends ValueConversionContext<?>>> get(Class<?> type) {
                return this.typeCache.get(type);
            }

            <S, T, C extends ValueConversionContext<?>> PropertyValueConverter<S, T, C> cache(PersistentProperty<?> property, @Nullable PropertyValueConverter<S, T, C> converter) {
                this.perPropertyCache.putIfAbsent(property, Optional.ofNullable(converter));
                AnnotatedPropertyValueConverterAccessor accessor = new AnnotatedPropertyValueConverterAccessor(property);
                Class<? extends PropertyValueConverter<?, ?, ? extends ValueConversionContext<? extends PersistentProperty<?>>>> valueConverterType = accessor.getValueConverterType();
                if (valueConverterType != null) {
                    this.cache(valueConverterType, converter);
                }
                return converter;
            }

            <S, T, C extends ValueConversionContext<?>> PropertyValueConverter<S, T, C> cache(Class<?> type, @Nullable PropertyValueConverter<S, T, C> converter) {
                this.typeCache.putIfAbsent(type, Optional.ofNullable(converter));
                return converter;
            }
        }
    }

    static class ConfiguredInstanceServingValueConverterFactory
    implements PropertyValueConverterFactory {
        private final ValueConverterRegistry<?> converterRegistry;

        ConfiguredInstanceServingValueConverterFactory(ValueConverterRegistry<?> converterRegistry) {
            Assert.notNull(converterRegistry, (String)"ConversionsRegistrar must not be null");
            this.converterRegistry = converterRegistry;
        }

        @Nullable
        public <DV, SV, C extends ValueConversionContext<?>> PropertyValueConverter<DV, SV, C> getConverter(PersistentProperty<?> property) {
            return this.converterRegistry.getConverter(property.getOwner().getType(), property.getName());
        }

        public <S, T, C extends ValueConversionContext<?>> PropertyValueConverter<S, T, C> getConverter(Class<? extends PropertyValueConverter<S, T, C>> converterType) {
            return null;
        }
    }

    static class BeanFactoryAwarePropertyValueConverterFactory
    implements PropertyValueConverterFactory {
        private final BeanFactory beanFactory;

        BeanFactoryAwarePropertyValueConverterFactory(BeanFactory beanFactory) {
            this.beanFactory = beanFactory;
        }

        @Override
        public <DV, SV, C extends ValueConversionContext<?>> PropertyValueConverter<DV, SV, C> getConverter(Class<? extends PropertyValueConverter<DV, SV, C>> converterType) {
            Assert.notNull(converterType, (String)"ConverterType must not be null");
            PropertyValueConverter converter = (PropertyValueConverter)this.beanFactory.getBeanProvider(converterType).getIfAvailable();
            if (converter == null && this.beanFactory instanceof AutowireCapableBeanFactory) {
                return (PropertyValueConverter)((AutowireCapableBeanFactory)this.beanFactory).createBean(converterType, 3, false);
            }
            return converter;
        }
    }

    static class SimplePropertyConverterFactory
    implements PropertyValueConverterFactory {
        SimplePropertyConverterFactory() {
        }

        public <S, T, C extends ValueConversionContext<?>> PropertyValueConverter<S, T, C> getConverter(Class<? extends PropertyValueConverter<S, T, C>> converterType) {
            Assert.notNull(converterType, (String)"ConverterType must not be null");
            if (converterType.isEnum()) {
                return (PropertyValueConverter)EnumSet.allOf(converterType).iterator().next();
            }
            return (PropertyValueConverter)BeanUtils.instantiateClass(converterType);
        }
    }

    static class ChainedPropertyValueConverterFactory
    implements PropertyValueConverterFactory {
        private final List<PropertyValueConverterFactory> delegates;

        ChainedPropertyValueConverterFactory(List<PropertyValueConverterFactory> delegates) {
            this.delegates = Collections.unmodifiableList(delegates);
        }

        @Nullable
        public <A, B, C extends ValueConversionContext<?>> PropertyValueConverter<A, B, C> getConverter(PersistentProperty<?> property) {
            return this.delegates.stream().map(it -> it.getConverter(property)).filter(Objects::nonNull).findFirst().orElse(null);
        }

        public <S, T, C extends ValueConversionContext<?>> PropertyValueConverter<S, T, C> getConverter(Class<? extends PropertyValueConverter<S, T, C>> converterType) {
            return this.delegates.stream().filter(it -> it.getConverter(converterType) != null).findFirst().map(it -> it.getConverter(converterType)).orElse(null);
        }
    }
}

