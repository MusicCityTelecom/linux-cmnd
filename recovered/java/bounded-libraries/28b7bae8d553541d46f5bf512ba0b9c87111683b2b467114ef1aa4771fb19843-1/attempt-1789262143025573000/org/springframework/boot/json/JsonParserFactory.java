/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.ClassUtils
 */
package org.springframework.boot.json;

import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.YamlJsonParser;
import org.springframework.util.ClassUtils;

public abstract class JsonParserFactory {
    public static JsonParser getJsonParser() {
        if (ClassUtils.isPresent((String)"com.fasterxml.jackson.databind.ObjectMapper", null)) {
            return new JacksonJsonParser();
        }
        if (ClassUtils.isPresent((String)"com.google.gson.Gson", null)) {
            return new GsonJsonParser();
        }
        if (ClassUtils.isPresent((String)"org.yaml.snakeyaml.Yaml", null)) {
            return new YamlJsonParser();
        }
        return new BasicJsonParser();
    }
}

