/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.ObjectMapper
 */
package org.springframework.boot.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import org.springframework.boot.json.AbstractJsonParser;

public class JacksonJsonParser
extends AbstractJsonParser {
    private static final MapTypeReference MAP_TYPE = new MapTypeReference();
    private static final ListTypeReference LIST_TYPE = new ListTypeReference();
    private ObjectMapper objectMapper;

    public JacksonJsonParser(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public JacksonJsonParser() {
    }

    @Override
    public Map<String, Object> parseMap(String json) {
        return this.tryParse(() -> (Map)this.getObjectMapper().readValue(json, (TypeReference)MAP_TYPE), Exception.class);
    }

    @Override
    public List<Object> parseList(String json) {
        return this.tryParse(() -> (List)this.getObjectMapper().readValue(json, (TypeReference)LIST_TYPE), Exception.class);
    }

    private ObjectMapper getObjectMapper() {
        if (this.objectMapper == null) {
            this.objectMapper = new ObjectMapper();
        }
        return this.objectMapper;
    }

    private static class ListTypeReference
    extends TypeReference<List<Object>> {
        private ListTypeReference() {
        }
    }

    private static class MapTypeReference
    extends TypeReference<Map<String, Object>> {
        private MapTypeReference() {
        }
    }
}

