/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.transformer;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.integration.support.json.JsonObjectMapper;
import org.springframework.integration.support.json.JsonObjectMapperProvider;
import org.springframework.integration.transformer.AbstractPayloadTransformer;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class ObjectToMapTransformer
extends AbstractPayloadTransformer<Object, Map<?, ?>> {
    private final JsonObjectMapper<?, ?> jsonObjectMapper;
    private volatile boolean shouldFlattenKeys = true;

    public ObjectToMapTransformer() {
        this(JsonObjectMapperProvider.newInstance());
    }

    public ObjectToMapTransformer(JsonObjectMapper<?, ?> jsonObjectMapper) {
        Assert.notNull(jsonObjectMapper, (String)"'jsonObjectMapper' must not be null");
        this.jsonObjectMapper = jsonObjectMapper;
    }

    public void setShouldFlattenKeys(boolean shouldFlattenKeys) {
        this.shouldFlattenKeys = shouldFlattenKeys;
    }

    @Override
    protected Map<String, Object> transformPayload(Object payload) {
        Map<String, Object> result;
        try {
            result = this.jsonObjectMapper.fromJson((Object)this.jsonObjectMapper.toJson(payload), Map.class);
        }
        catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        if (this.shouldFlattenKeys) {
            result = this.flattenMap(result);
        }
        return result;
    }

    @Override
    public String getComponentType() {
        return "object-to-map-transformer";
    }

    private void doProcessElement(String propertyPrefix, Object element, Map<String, Object> resultMap) {
        if (element instanceof Map) {
            this.doFlatten(propertyPrefix, (Map)element, resultMap);
        } else if (element instanceof Collection) {
            this.doProcessCollection(propertyPrefix, (Collection)element, resultMap);
        } else if (element != null && element.getClass().isArray()) {
            List collection = CollectionUtils.arrayToList((Object)element);
            this.doProcessCollection(propertyPrefix, collection, resultMap);
        } else {
            resultMap.put(propertyPrefix, element);
        }
    }

    private Map<String, Object> flattenMap(Map<String, Object> result) {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        this.doFlatten("", result, resultMap);
        return resultMap;
    }

    private void doFlatten(String propertyPrefixArg, Map<String, Object> inputMap, Map<String, Object> resultMap) {
        String propertyPrefix = propertyPrefixArg;
        if (StringUtils.hasText((String)propertyPrefix)) {
            propertyPrefix = propertyPrefix + ".";
        }
        for (Map.Entry<String, Object> entry : inputMap.entrySet()) {
            this.doProcessElement(propertyPrefix + entry.getKey(), entry.getValue(), resultMap);
        }
    }

    private void doProcessCollection(String propertyPrefix, Collection<?> list, Map<String, Object> resultMap) {
        int counter = 0;
        for (Object element : list) {
            this.doProcessElement(propertyPrefix + "[" + counter + "]", element, resultMap);
            ++counter;
        }
    }
}

