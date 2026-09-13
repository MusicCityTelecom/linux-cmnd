/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.util.MimeType
 */
package org.springframework.messaging.converter;

import org.springframework.lang.Nullable;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.ContentTypeResolver;
import org.springframework.util.MimeType;

public class DefaultContentTypeResolver
implements ContentTypeResolver {
    @Nullable
    private MimeType defaultMimeType;

    public void setDefaultMimeType(@Nullable MimeType defaultMimeType) {
        this.defaultMimeType = defaultMimeType;
    }

    @Nullable
    public MimeType getDefaultMimeType() {
        return this.defaultMimeType;
    }

    @Override
    @Nullable
    public MimeType resolve(@Nullable MessageHeaders headers) {
        if (headers == null || headers.get("contentType") == null) {
            return this.defaultMimeType;
        }
        Object value = headers.get("contentType");
        if (value == null) {
            return null;
        }
        if (value instanceof MimeType) {
            return (MimeType)value;
        }
        if (value instanceof String) {
            return MimeType.valueOf((String)((String)value));
        }
        throw new IllegalArgumentException("Unknown type for contentType header value: " + value.getClass());
    }

    public String toString() {
        return "DefaultContentTypeResolver[defaultMimeType=" + this.defaultMimeType + "]";
    }
}

