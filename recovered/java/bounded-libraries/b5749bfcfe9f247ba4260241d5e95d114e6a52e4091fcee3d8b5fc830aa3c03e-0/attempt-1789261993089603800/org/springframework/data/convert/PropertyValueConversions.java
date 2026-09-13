/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.data.convert;

import java.util.function.Consumer;
import org.springframework.data.convert.PropertyValueConverter;
import org.springframework.data.convert.PropertyValueConverterRegistrar;
import org.springframework.data.convert.SimplePropertyValueConversions;
import org.springframework.data.convert.ValueConversionContext;
import org.springframework.data.mapping.PersistentProperty;

public interface PropertyValueConversions {
    public boolean hasValueConverter(PersistentProperty<?> var1);

    public <DV, SV, P extends PersistentProperty<P>, VCC extends ValueConversionContext<P>> PropertyValueConverter<DV, SV, VCC> getValueConverter(P var1);

    public static <P extends PersistentProperty<P>> PropertyValueConversions simple(Consumer<PropertyValueConverterRegistrar<P>> config) {
        SimplePropertyValueConversions conversions = new SimplePropertyValueConversions();
        PropertyValueConverterRegistrar registrar = new PropertyValueConverterRegistrar();
        config.accept(registrar);
        conversions.setValueConverterRegistry(registrar.buildRegistry());
        conversions.afterPropertiesSet();
        return conversions;
    }
}

