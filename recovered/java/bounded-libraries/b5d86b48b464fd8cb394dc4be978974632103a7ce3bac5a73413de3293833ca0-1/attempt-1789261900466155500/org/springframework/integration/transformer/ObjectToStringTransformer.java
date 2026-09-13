/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.integration.transformer;

import java.io.UnsupportedEncodingException;
import org.springframework.integration.transformer.AbstractPayloadTransformer;
import org.springframework.util.Assert;

public class ObjectToStringTransformer
extends AbstractPayloadTransformer<Object, String> {
    private final String charset;

    public ObjectToStringTransformer() {
        this.charset = "UTF-8";
    }

    public ObjectToStringTransformer(String charset) {
        Assert.notNull((Object)charset, (String)"'charset' cannot be null");
        this.charset = charset;
    }

    @Override
    public String getComponentType() {
        return "object-to-string-transformer";
    }

    @Override
    protected String transformPayload(Object payload) {
        if (payload instanceof byte[]) {
            try {
                return new String((byte[])payload, this.charset);
            }
            catch (UnsupportedEncodingException e) {
                throw new IllegalStateException(e);
            }
        }
        if (payload instanceof char[]) {
            return new String((char[])payload);
        }
        return payload.toString();
    }
}

