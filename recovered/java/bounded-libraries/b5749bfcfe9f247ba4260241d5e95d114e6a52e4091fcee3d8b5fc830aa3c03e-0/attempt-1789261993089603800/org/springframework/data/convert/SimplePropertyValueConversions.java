/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.lang.NonNull
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 */
package org.springframework.data.convert;

import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.convert.PropertyValueConversions;
import org.springframework.data.convert.PropertyValueConverter;
import org.springframework.data.convert.PropertyValueConverterFactory;
import org.springframework.data.convert.ValueConversionContext;
import org.springframework.data.convert.ValueConverterRegistry;
import org.springframework.data.mapping.PersistentProperty;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

public class SimplePropertyValueConversions
implements PropertyValueConversions,
InitializingBean {
    private static final String NO_CONVERTER_FACTORY_ERROR_MESSAGE = "PropertyValueConverterFactory is not set; Make sure to either set the converter factory or call afterPropertiesSet() to initialize the object";
    private boolean converterCacheEnabled = true;
    @Nullable
    private PropertyValueConverterFactory converterFactory;
    @Nullable
    private ValueConverterRegistry<?> valueConverterRegistry;

    public void setConverterFactory(@Nullable PropertyValueConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }

    @Nullable
    public PropertyValueConverterFactory getConverterFactory() {
        return this.converterFactory;
    }

    @NonNull
    private PropertyValueConverterFactory requireConverterFactory() {
        PropertyValueConverterFactory factory = this.getConverterFactory();
        Assert.state((factory != null ? 1 : 0) != 0, (String)NO_CONVERTER_FACTORY_ERROR_MESSAGE);
        return factory;
    }

    public void setValueConverterRegistry(@Nullable ValueConverterRegistry<?> valueConverterRegistry) {
        this.valueConverterRegistry = valueConverterRegistry;
    }

    @Nullable
    public ValueConverterRegistry<?> getValueConverterRegistry() {
        return this.valueConverterRegistry;
    }

    public void setConverterCacheEnabled(boolean converterCacheEnabled) {
        this.converterCacheEnabled = converterCacheEnabled;
    }

    @Override
    public boolean hasValueConverter(PersistentProperty<?> property) {
        return this.requireConverterFactory().getConverter(property) != null;
    }

    @NonNull
    public <DV, SV, P extends PersistentProperty<P>, D extends ValueConversionContext<P>> PropertyValueConverter<DV, SV, D> getValueConverter(P property) {
        PropertyValueConverter converter = this.requireConverterFactory().getConverter(property);
        Assert.notNull(converter, (String)String.format("No PropertyValueConverter registered for %s", property));
        return converter;
    }

    public void init() {
        ArrayList<PropertyValueConverterFactory> factoryList = new ArrayList<PropertyValueConverterFactory>(3);
        factoryList.add(this.resolveConverterFactory());
        this.resolveConverterRegistryAsConverterFactory().ifPresent(factoryList::add);
        PropertyValueConverterFactory targetFactory = factoryList.size() > 1 ? PropertyValueConverterFactory.chained(factoryList) : (PropertyValueConverterFactory)factoryList.iterator().next();
        this.converterFactory = this.converterCacheEnabled ? PropertyValueConverterFactory.caching(targetFactory) : targetFactory;
    }

    @NonNull
    private PropertyValueConverterFactory resolveConverterFactory() {
        PropertyValueConverterFactory converterFactory = this.getConverterFactory();
        return converterFactory != null ? converterFactory : PropertyValueConverterFactory.simple();
    }

    private Optional<PropertyValueConverterFactory> resolveConverterRegistryAsConverterFactory() {
        return Optional.ofNullable(this.getValueConverterRegistry()).filter(it -> !it.isEmpty()).map(PropertyValueConverterFactory::configuredInstance);
    }

    public void afterPropertiesSet() {
        this.init();
    }
}

