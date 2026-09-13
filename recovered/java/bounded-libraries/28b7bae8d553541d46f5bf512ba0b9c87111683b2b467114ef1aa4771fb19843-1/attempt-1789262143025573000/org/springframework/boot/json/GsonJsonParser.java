/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.reflect.TypeToken
 */
package org.springframework.boot.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.util.List;
import java.util.Map;
import org.springframework.boot.json.AbstractJsonParser;

public class GsonJsonParser
extends AbstractJsonParser {
    private static final TypeToken<?> MAP_TYPE = new MapTypeToken();
    private static final TypeToken<?> LIST_TYPE = new ListTypeToken();
    private Gson gson = new GsonBuilder().create();

    @Override
    public Map<String, Object> parseMap(String json) {
        return this.tryParse(() -> this.parseMap(json, trimmed -> (Map)this.gson.fromJson(trimmed, MAP_TYPE.getType())), Exception.class);
    }

    @Override
    public List<Object> parseList(String json) {
        return this.tryParse(() -> this.parseList(json, trimmed -> (List)this.gson.fromJson(trimmed, LIST_TYPE.getType())), Exception.class);
    }

    private static final class ListTypeToken
    extends TypeToken<List<Object>> {
        private ListTypeToken() {
        }
    }

    private static final class MapTypeToken
    extends TypeToken<Map<String, Object>> {
        private MapTypeToken() {
        }
    }
}

