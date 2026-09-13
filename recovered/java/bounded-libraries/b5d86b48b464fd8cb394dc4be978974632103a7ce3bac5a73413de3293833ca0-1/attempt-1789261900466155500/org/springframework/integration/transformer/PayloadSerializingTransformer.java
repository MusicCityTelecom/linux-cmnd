/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.serializer.Serializer
 *  org.springframework.core.serializer.support.SerializingConverter
 */
package org.springframework.integration.transformer;

import org.springframework.core.serializer.Serializer;
import org.springframework.core.serializer.support.SerializingConverter;
import org.springframework.integration.transformer.PayloadTypeConvertingTransformer;

public class PayloadSerializingTransformer
extends PayloadTypeConvertingTransformer<Object, byte[]> {
    public PayloadSerializingTransformer() {
        this.doSetConverter(new SerializingConverter());
    }

    public void setSerializer(Serializer<Object> serializer) {
        this.setConverter(new SerializingConverter(serializer));
    }
}

