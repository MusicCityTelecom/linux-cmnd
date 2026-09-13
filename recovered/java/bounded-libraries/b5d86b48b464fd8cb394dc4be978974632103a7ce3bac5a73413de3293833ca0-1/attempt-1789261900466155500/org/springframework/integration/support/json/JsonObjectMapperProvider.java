/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.support.json;

import org.springframework.integration.support.json.Jackson2JsonObjectMapper;
import org.springframework.integration.support.json.JacksonPresent;
import org.springframework.integration.support.json.JsonObjectMapper;

public final class JsonObjectMapperProvider {
    private JsonObjectMapperProvider() {
    }

    public static JsonObjectMapper<?, ?> newInstance() {
        if (JacksonPresent.isJackson2Present()) {
            return new Jackson2JsonObjectMapper();
        }
        throw new IllegalStateException("No jackson-databind.jar is present in the classpath.");
    }

    public static boolean jsonAvailable() {
        return JacksonPresent.isJackson2Present();
    }
}

