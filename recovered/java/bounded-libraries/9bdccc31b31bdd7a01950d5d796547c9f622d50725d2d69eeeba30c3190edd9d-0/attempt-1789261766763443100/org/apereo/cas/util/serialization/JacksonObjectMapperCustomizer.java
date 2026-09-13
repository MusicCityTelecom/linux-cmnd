/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  org.springframework.core.Ordered
 */
package org.apereo.cas.util.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import org.springframework.core.Ordered;

public interface JacksonObjectMapperCustomizer
extends Ordered {
    public static JacksonObjectMapperCustomizer noOp() {
        return new JacksonObjectMapperCustomizer(){};
    }

    default public Map<String, ?> getInjectableValues() {
        return new HashMap();
    }

    default public void customize(ObjectMapper objectMapper) {
    }

    default public int getOrder() {
        return Integer.MAX_VALUE;
    }
}

