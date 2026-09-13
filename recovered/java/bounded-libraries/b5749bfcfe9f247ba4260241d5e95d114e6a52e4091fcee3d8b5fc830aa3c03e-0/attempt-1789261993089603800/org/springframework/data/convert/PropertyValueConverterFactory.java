/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 */
package org.springframework.data.convert;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.data.convert.AnnotatedPropertyValueConverterAccessor;
import org.springframework.data.convert.PropertyValueConverter;
import org.springframework.data.convert.PropertyValueConverterFactories;
import org.springframework.data.convert.ValueConversionContext;
import org.springframework.data.convert.ValueConverterRegistry;
import org.springframework.data.mapping.PersistentProperty;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

public interface PropertyValueConverterFactory {
    @Nullable
    default public <DV, SV, P extends ValueConversionContext<?>> PropertyValueConverter<DV, SV, P> getConverter(PersistentProperty<?> property) {
        AnnotatedPropertyValueConverterAccessor accessor = new AnnotatedPropertyValueConverterAccessor(property);
        if (!accessor.hasValueConverter()) {
            return null;
        }
        return this.getConverter(accessor.getValueConverterType());
    }

    @Nullable
    public <DV, SV, C extends ValueConversionContext<?>> PropertyValueConverter<DV, SV, C> getConverter(Class<? extends PropertyValueConverter<DV, SV, C>> var1);

    public static PropertyValueConverterFactory simple() {
        return new PropertyValueConverterFactories.SimplePropertyConverterFactory();
    }

    public static PropertyValueConverterFactory beanFactoryAware(BeanFactory beanFactory) {
        return new PropertyValueConverterFactories.BeanFactoryAwarePropertyValueConverterFactory(beanFactory);
    }

    public static PropertyValueConverterFactory configuredInstance(ValueConverterRegistry<?> registrar) {
        return new PropertyValueConverterFactories.ConfiguredInstanceServingValueConverterFactory(registrar);
    }

    public static PropertyValueConverterFactory chained(PropertyValueConverterFactory ... factories) {
        return PropertyValueConverterFactory.chained(Arrays.asList(factories));
    }

    public static PropertyValueConverterFactory chained(List<PropertyValueConverterFactory> factoryList) {
        Assert.noNullElements(factoryList, (String)"FactoryList must not contain null elements");
        if (factoryList.size() == 1) {
            return factoryList.iterator().next();
        }
        return new PropertyValueConverterFactories.ChainedPropertyValueConverterFactory(factoryList);
    }

    public static PropertyValueConverterFactory caching(PropertyValueConverterFactory factory) {
        return new PropertyValueConverterFactories.CachingPropertyValueConverterFactory(factory);
    }
}

