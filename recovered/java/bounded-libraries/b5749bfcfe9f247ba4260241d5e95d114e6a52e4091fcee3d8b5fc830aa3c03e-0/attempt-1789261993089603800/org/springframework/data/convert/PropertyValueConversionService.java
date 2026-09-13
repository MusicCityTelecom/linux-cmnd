/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.NonNull
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 */
package org.springframework.data.convert;

import org.springframework.data.convert.CustomConversions;
import org.springframework.data.convert.PropertyValueConversions;
import org.springframework.data.convert.PropertyValueConverter;
import org.springframework.data.convert.ValueConversionContext;
import org.springframework.data.mapping.PersistentProperty;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

public class PropertyValueConversionService {
    private final PropertyValueConversions conversions;

    public PropertyValueConversionService(@NonNull CustomConversions conversions) {
        Assert.notNull((Object)conversions, (String)"CustomConversions must not be null");
        PropertyValueConversions valueConversions = conversions.getPropertyValueConversions();
        this.conversions = valueConversions != null ? valueConversions : NoOpPropertyValueConversions.INSTANCE;
    }

    public boolean hasConverter(PersistentProperty<?> property) {
        return this.conversions.hasValueConverter(property);
    }

    @Nullable
    public <P extends PersistentProperty<P>, VCC extends ValueConversionContext<P>> Object read(@Nullable Object value, P property, VCC context) {
        PropertyValueConverter converter = this.conversions.getValueConverter(property);
        return value != null ? converter.read(value, context) : converter.readNull(context);
    }

    @Nullable
    public <P extends PersistentProperty<P>, VCC extends ValueConversionContext<P>> Object write(@Nullable Object value, P property, VCC context) {
        PropertyValueConverter converter = this.conversions.getValueConverter(property);
        return value != null ? converter.write(value, context) : converter.writeNull(context);
    }

    static enum NoOpPropertyValueConversions implements PropertyValueConversions
    {
        INSTANCE;


        @Override
        public boolean hasValueConverter(PersistentProperty<?> property) {
            return false;
        }

        @Override
        public <DV, SV, P extends PersistentProperty<P>, VCC extends ValueConversionContext<P>> PropertyValueConverter<DV, SV, VCC> getValueConverter(P property) {
            throw new UnsupportedOperationException("No PropertyValueConversions was configured");
        }
    }
}

