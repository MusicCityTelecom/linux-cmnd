/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.json;

import java.util.List;
import java.util.Map;
import org.springframework.boot.json.JsonParseException;

public interface JsonParser {
    public Map<String, Object> parseMap(String var1) throws JsonParseException;

    public List<Object> parseList(String var1) throws JsonParseException;
}

