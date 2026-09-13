/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.CollectionUtils
 */
package org.springframework.boot.actuate.health;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.boot.actuate.health.HttpCodeStatusMapper;
import org.springframework.boot.actuate.health.Status;
import org.springframework.util.CollectionUtils;

public class SimpleHttpCodeStatusMapper
implements HttpCodeStatusMapper {
    private static final Map<String, Integer> DEFAULT_MAPPINGS;
    private final Map<String, Integer> mappings;

    public SimpleHttpCodeStatusMapper() {
        this(null);
    }

    public SimpleHttpCodeStatusMapper(Map<String, Integer> mappings) {
        this.mappings = CollectionUtils.isEmpty(mappings) ? DEFAULT_MAPPINGS : SimpleHttpCodeStatusMapper.getUniformMappings(mappings);
    }

    @Override
    public int getStatusCode(Status status) {
        String code = SimpleHttpCodeStatusMapper.getUniformCode(status.getCode());
        return this.mappings.getOrDefault(code, 200);
    }

    private static Map<String, Integer> getUniformMappings(Map<String, Integer> mappings) {
        LinkedHashMap<String, Integer> result = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : mappings.entrySet()) {
            String code = SimpleHttpCodeStatusMapper.getUniformCode(entry.getKey());
            if (code == null) continue;
            result.putIfAbsent(code, entry.getValue());
        }
        return Collections.unmodifiableMap(result);
    }

    private static String getUniformCode(String code) {
        if (code == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < code.length(); ++i) {
            char ch = code.charAt(i);
            if (!Character.isAlphabetic(ch) && !Character.isDigit(ch)) continue;
            builder.append(Character.toLowerCase(ch));
        }
        return builder.toString();
    }

    static {
        HashMap<String, Integer> defaultMappings = new HashMap<String, Integer>();
        defaultMappings.put(Status.DOWN.getCode(), 503);
        defaultMappings.put(Status.OUT_OF_SERVICE.getCode(), 503);
        DEFAULT_MAPPINGS = SimpleHttpCodeStatusMapper.getUniformMappings(defaultMappings);
    }
}

