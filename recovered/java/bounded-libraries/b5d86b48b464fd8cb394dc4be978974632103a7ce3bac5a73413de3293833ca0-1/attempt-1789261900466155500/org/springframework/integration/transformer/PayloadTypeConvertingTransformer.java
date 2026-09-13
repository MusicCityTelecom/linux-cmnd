/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.convert.converter.Converter
 *  org.springframework.util.Assert
 */
package org.springframework.integration.transformer;

import org.springframework.core.convert.converter.Converter;
import org.springframework.integration.transformer.AbstractPayloadTransformer;
import org.springframework.util.Assert;

public class PayloadTypeConvertingTransformer<T, U>
extends AbstractPayloadTransformer<T, U> {
    private Converter<T, U> converter;

    public void setConverter(Converter<T, U> converter) {
        this.doSetConverter(converter);
    }

    protected final void doSetConverter(Converter<T, U> converter) {
        Assert.notNull(converter, (String)"'converter' must not be null");
        this.converter = converter;
    }

    protected Converter<T, U> getConverter() {
        return this.converter;
    }

    @Override
    protected void onInit() {
        super.onInit();
        Assert.notNull(this.converter, () -> this.getClass().getName() + " requires a Converter<Object, Object>");
    }

    @Override
    protected U transformPayload(T payload) {
        return (U)this.converter.convert(payload);
    }
}

