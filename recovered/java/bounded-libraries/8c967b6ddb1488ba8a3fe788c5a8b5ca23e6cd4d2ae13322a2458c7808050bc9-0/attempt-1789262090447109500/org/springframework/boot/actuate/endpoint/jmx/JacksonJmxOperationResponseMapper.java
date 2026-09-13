/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.ObjectMapper
 */
package org.springframework.boot.actuate.endpoint.jmx;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.boot.actuate.endpoint.jmx.JmxOperationResponseMapper;

public class JacksonJmxOperationResponseMapper
implements JmxOperationResponseMapper {
    private final ObjectMapper objectMapper;
    private final JavaType listType;
    private final JavaType mapType;

    public JacksonJmxOperationResponseMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper != null ? objectMapper : new ObjectMapper();
        this.listType = this.objectMapper.getTypeFactory().constructParametricType(List.class, new Class[]{Object.class});
        this.mapType = this.objectMapper.getTypeFactory().constructParametricType(Map.class, new Class[]{String.class, Object.class});
    }

    @Override
    public Class<?> mapResponseType(Class<?> responseType) {
        if (CharSequence.class.isAssignableFrom(responseType)) {
            return String.class;
        }
        if (responseType.isArray() || Collection.class.isAssignableFrom(responseType)) {
            return List.class;
        }
        return Map.class;
    }

    @Override
    public Object mapResponse(Object response) {
        if (response == null) {
            return null;
        }
        if (response instanceof CharSequence) {
            return response.toString();
        }
        if (response.getClass().isArray() || response instanceof Collection) {
            return this.objectMapper.convertValue(response, this.listType);
        }
        return this.objectMapper.convertValue(response, this.mapType);
    }
}

