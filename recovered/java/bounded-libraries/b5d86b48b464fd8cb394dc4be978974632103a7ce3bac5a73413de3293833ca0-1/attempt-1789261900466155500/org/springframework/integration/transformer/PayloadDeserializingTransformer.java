/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.serializer.Deserializer
 *  org.springframework.util.Assert
 */
package org.springframework.integration.transformer;

import org.springframework.core.serializer.Deserializer;
import org.springframework.integration.support.converter.AllowListDeserializingConverter;
import org.springframework.integration.transformer.PayloadTypeConvertingTransformer;
import org.springframework.util.Assert;

public class PayloadDeserializingTransformer
extends PayloadTypeConvertingTransformer<byte[], Object> {
    public PayloadDeserializingTransformer() {
        this.doSetConverter(new AllowListDeserializingConverter());
    }

    public void setDeserializer(Deserializer<Object> deserializer) {
        this.setConverter(new AllowListDeserializingConverter(deserializer));
    }

    public void setAllowedPatterns(String ... patterns) {
        Assert.isTrue((boolean)(this.getConverter() instanceof AllowListDeserializingConverter), (String)"Patterns can only be provided when using a 'AllowListDeserializingConverter'");
        ((AllowListDeserializingConverter)this.getConverter()).setAllowedPatterns(patterns);
    }
}

