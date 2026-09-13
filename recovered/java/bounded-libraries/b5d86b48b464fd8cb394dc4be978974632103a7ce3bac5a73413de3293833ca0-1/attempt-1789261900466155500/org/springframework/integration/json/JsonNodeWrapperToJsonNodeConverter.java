/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  org.springframework.core.convert.TypeDescriptor
 *  org.springframework.core.convert.converter.GenericConverter
 *  org.springframework.core.convert.converter.GenericConverter$ConvertiblePair
 *  org.springframework.lang.Nullable
 */
package org.springframework.integration.json;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.Collections;
import java.util.Set;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.integration.json.JsonPropertyAccessor;
import org.springframework.lang.Nullable;

public class JsonNodeWrapperToJsonNodeConverter
implements GenericConverter {
    public Set<GenericConverter.ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new GenericConverter.ConvertiblePair(JsonPropertyAccessor.JsonNodeWrapper.class, JsonNode.class));
    }

    @Nullable
    public Object convert(@Nullable Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (source != null) {
            return targetType.getObjectType().cast(((JsonPropertyAccessor.JsonNodeWrapper)source).getRealNode());
        }
        return null;
    }
}

