/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jayway.jsonpath.JsonPath
 *  com.jayway.jsonpath.Predicate
 */
package org.springframework.integration.json;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Predicate;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public final class JsonPathUtils {
    public static <T> T evaluate(Object json, String jsonPath, Predicate ... predicates) throws IOException {
        if (json instanceof String) {
            return (T)JsonPath.read((String)((String)json), (String)jsonPath, (Predicate[])predicates);
        }
        if (json instanceof byte[]) {
            return (T)JsonPath.read((InputStream)new ByteArrayInputStream((byte[])json), (String)jsonPath, (Predicate[])predicates);
        }
        if (json instanceof File) {
            return (T)JsonPath.read((File)((File)json), (String)jsonPath, (Predicate[])predicates);
        }
        if (json instanceof URL) {
            return (T)JsonPath.read((InputStream)((URL)json).openStream(), (String)jsonPath, (Predicate[])predicates);
        }
        if (json instanceof InputStream) {
            return (T)JsonPath.read((InputStream)((InputStream)json), (String)jsonPath, (Predicate[])predicates);
        }
        return (T)JsonPath.read((Object)json, (String)jsonPath, (Predicate[])predicates);
    }

    private JsonPathUtils() {
    }
}

