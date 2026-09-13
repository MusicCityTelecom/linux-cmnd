/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.ParameterizedTypeReference
 *  org.springframework.lang.Nullable
 *  org.springframework.util.MimeType
 */
package org.springframework.messaging.rsocket;

import java.util.Map;
import java.util.function.BiConsumer;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.lang.Nullable;
import org.springframework.util.MimeType;

public interface MetadataExtractorRegistry {
    default public void metadataToExtract(MimeType mimeType, Class<?> targetType, @Nullable String name) {
        String key = name != null ? name : mimeType.toString();
        this.metadataToExtract(mimeType, targetType, (T value, Map<String, Object> map) -> map.put(key, value));
    }

    default public void metadataToExtract(MimeType mimeType, ParameterizedTypeReference<?> targetType, @Nullable String name) {
        String key = name != null ? name : mimeType.toString();
        this.metadataToExtract(mimeType, targetType, (T value, Map<String, Object> map) -> map.put(key, value));
    }

    public <T> void metadataToExtract(MimeType var1, Class<T> var2, BiConsumer<T, Map<String, Object>> var3);

    public <T> void metadataToExtract(MimeType var1, ParameterizedTypeReference<T> var2, BiConsumer<T, Map<String, Object>> var3);
}

