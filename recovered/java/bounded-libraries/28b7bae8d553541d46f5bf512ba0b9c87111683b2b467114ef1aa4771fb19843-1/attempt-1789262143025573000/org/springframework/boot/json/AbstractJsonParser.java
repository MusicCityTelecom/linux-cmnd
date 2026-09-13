/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.ReflectionUtils
 */
package org.springframework.boot.json;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Function;
import org.springframework.boot.json.JsonParseException;
import org.springframework.boot.json.JsonParser;
import org.springframework.util.ReflectionUtils;

public abstract class AbstractJsonParser
implements JsonParser {
    protected final Map<String, Object> parseMap(String json, Function<String, Map<String, Object>> parser) {
        return this.trimParse(json, "{", parser);
    }

    protected final List<Object> parseList(String json, Function<String, List<Object>> parser) {
        return this.trimParse(json, "[", parser);
    }

    protected final <T> T trimParse(String json, String prefix, Function<String, T> parser) {
        String trimmed;
        String string = trimmed = json != null ? json.trim() : "";
        if (trimmed.startsWith(prefix)) {
            return parser.apply(trimmed);
        }
        throw new JsonParseException();
    }

    protected final <T> T tryParse(Callable<T> parser, Class<? extends Exception> check) {
        try {
            return parser.call();
        }
        catch (Exception ex) {
            if (check.isAssignableFrom(ex.getClass())) {
                throw new JsonParseException(ex);
            }
            ReflectionUtils.rethrowRuntimeException((Throwable)ex);
            throw new IllegalStateException(ex);
        }
    }
}

