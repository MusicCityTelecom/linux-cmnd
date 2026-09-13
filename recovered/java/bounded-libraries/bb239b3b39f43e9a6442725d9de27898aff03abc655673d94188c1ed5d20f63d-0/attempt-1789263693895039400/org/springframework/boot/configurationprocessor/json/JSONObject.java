/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.configurationprocessor.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.boot.configurationprocessor.json.JSON;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONStringer;
import org.springframework.boot.configurationprocessor.json.JSONTokener;

public class JSONObject {
    private static final Double NEGATIVE_ZERO = -0.0;
    public static final Object NULL = new Object(){

        public boolean equals(Object o) {
            return o == this || o == null;
        }

        public String toString() {
            return "null";
        }
    };
    private final Map<String, Object> nameValuePairs;

    public JSONObject() {
        this.nameValuePairs = new LinkedHashMap<String, Object>();
    }

    public JSONObject(Map copyFrom) {
        this();
        Map contentsTyped = copyFrom;
        for (Map.Entry entry : contentsTyped.entrySet()) {
            String key = (String)entry.getKey();
            if (key == null) {
                throw new NullPointerException("key == null");
            }
            this.nameValuePairs.put(key, JSONObject.wrap(entry.getValue()));
        }
    }

    public JSONObject(JSONTokener readFrom) throws JSONException {
        Object object = readFrom.nextValue();
        if (!(object instanceof JSONObject)) {
            throw JSON.typeMismatch(object, "JSONObject");
        }
        this.nameValuePairs = ((JSONObject)object).nameValuePairs;
    }

    public JSONObject(String json) throws JSONException {
        this(new JSONTokener(json));
    }

    public JSONObject(JSONObject copyFrom, String[] names) throws JSONException {
        this();
        for (String name : names) {
            Object value = copyFrom.opt(name);
            if (value == null) continue;
            this.nameValuePairs.put(name, value);
        }
    }

    public int length() {
        return this.nameValuePairs.size();
    }

    public JSONObject put(String name, boolean value) throws JSONException {
        this.nameValuePairs.put(this.checkName(name), value);
        return this;
    }

    public JSONObject put(String name, double value) throws JSONException {
        this.nameValuePairs.put(this.checkName(name), JSON.checkDouble(value));
        return this;
    }

    public JSONObject put(String name, int value) throws JSONException {
        this.nameValuePairs.put(this.checkName(name), value);
        return this;
    }

    public JSONObject put(String name, long value) throws JSONException {
        this.nameValuePairs.put(this.checkName(name), value);
        return this;
    }

    public JSONObject put(String name, Object value) throws JSONException {
        if (value == null) {
            this.nameValuePairs.remove(name);
            return this;
        }
        if (value instanceof Number) {
            JSON.checkDouble(((Number)value).doubleValue());
        }
        this.nameValuePairs.put(this.checkName(name), value);
        return this;
    }

    public JSONObject putOpt(String name, Object value) throws JSONException {
        if (name == null || value == null) {
            return this;
        }
        return this.put(name, value);
    }

    public JSONObject accumulate(String name, Object value) throws JSONException {
        Object current = this.nameValuePairs.get(this.checkName(name));
        if (current == null) {
            return this.put(name, value);
        }
        if (value instanceof Number) {
            JSON.checkDouble(((Number)value).doubleValue());
        }
        if (current instanceof JSONArray) {
            JSONArray array = (JSONArray)current;
            array.put(value);
        } else {
            JSONArray array = new JSONArray();
            array.put(current);
            array.put(value);
            this.nameValuePairs.put(name, array);
        }
        return this;
    }

    String checkName(String name) throws JSONException {
        if (name == null) {
            throw new JSONException("Names must be non-null");
        }
        return name;
    }

    public Object remove(String name) {
        return this.nameValuePairs.remove(name);
    }

    public boolean isNull(String name) {
        Object value = this.nameValuePairs.get(name);
        return value == null || value == NULL;
    }

    public boolean has(String name) {
        return this.nameValuePairs.containsKey(name);
    }

    public Object get(String name) throws JSONException {
        Object result = this.nameValuePairs.get(name);
        if (result == null) {
            throw new JSONException("No value for " + name);
        }
        return result;
    }

    public Object opt(String name) {
        return this.nameValuePairs.get(name);
    }

    public boolean getBoolean(String name) throws JSONException {
        Object object = this.get(name);
        Boolean result = JSON.toBoolean(object);
        if (result == null) {
            throw JSON.typeMismatch(name, object, "boolean");
        }
        return result;
    }

    public boolean optBoolean(String name) {
        return this.optBoolean(name, false);
    }

    public boolean optBoolean(String name, boolean fallback) {
        Object object = this.opt(name);
        Boolean result = JSON.toBoolean(object);
        return result != null ? result : fallback;
    }

    public double getDouble(String name) throws JSONException {
        Object object = this.get(name);
        Double result = JSON.toDouble(object);
        if (result == null) {
            throw JSON.typeMismatch(name, object, "double");
        }
        return result;
    }

    public double optDouble(String name) {
        return this.optDouble(name, Double.NaN);
    }

    public double optDouble(String name, double fallback) {
        Object object = this.opt(name);
        Double result = JSON.toDouble(object);
        return result != null ? result : fallback;
    }

    public int getInt(String name) throws JSONException {
        Object object = this.get(name);
        Integer result = JSON.toInteger(object);
        if (result == null) {
            throw JSON.typeMismatch(name, object, "int");
        }
        return result;
    }

    public int optInt(String name) {
        return this.optInt(name, 0);
    }

    public int optInt(String name, int fallback) {
        Object object = this.opt(name);
        Integer result = JSON.toInteger(object);
        return result != null ? result : fallback;
    }

    public long getLong(String name) throws JSONException {
        Object object = this.get(name);
        Long result = JSON.toLong(object);
        if (result == null) {
            throw JSON.typeMismatch(name, object, "long");
        }
        return result;
    }

    public long optLong(String name) {
        return this.optLong(name, 0L);
    }

    public long optLong(String name, long fallback) {
        Object object = this.opt(name);
        Long result = JSON.toLong(object);
        return result != null ? result : fallback;
    }

    public String getString(String name) throws JSONException {
        Object object = this.get(name);
        String result = JSON.toString(object);
        if (result == null) {
            throw JSON.typeMismatch(name, object, "String");
        }
        return result;
    }

    public String optString(String name) {
        return this.optString(name, "");
    }

    public String optString(String name, String fallback) {
        Object object = this.opt(name);
        String result = JSON.toString(object);
        return result != null ? result : fallback;
    }

    public JSONArray getJSONArray(String name) throws JSONException {
        Object object = this.get(name);
        if (object instanceof JSONArray) {
            return (JSONArray)object;
        }
        throw JSON.typeMismatch(name, object, "JSONArray");
    }

    public JSONArray optJSONArray(String name) {
        Object object = this.opt(name);
        return object instanceof JSONArray ? (JSONArray)object : null;
    }

    public JSONObject getJSONObject(String name) throws JSONException {
        Object object = this.get(name);
        if (object instanceof JSONObject) {
            return (JSONObject)object;
        }
        throw JSON.typeMismatch(name, object, "JSONObject");
    }

    public JSONObject optJSONObject(String name) {
        Object object = this.opt(name);
        return object instanceof JSONObject ? (JSONObject)object : null;
    }

    public JSONArray toJSONArray(JSONArray names) {
        JSONArray result = new JSONArray();
        if (names == null) {
            return null;
        }
        int length = names.length();
        if (length == 0) {
            return null;
        }
        for (int i = 0; i < length; ++i) {
            String name = JSON.toString(names.opt(i));
            result.put(this.opt(name));
        }
        return result;
    }

    public Iterator keys() {
        return this.nameValuePairs.keySet().iterator();
    }

    public JSONArray names() {
        return this.nameValuePairs.isEmpty() ? null : new JSONArray(new ArrayList<String>(this.nameValuePairs.keySet()));
    }

    public String toString() {
        try {
            JSONStringer stringer = new JSONStringer();
            this.writeTo(stringer);
            return stringer.toString();
        }
        catch (JSONException e) {
            return null;
        }
    }

    public String toString(int indentSpaces) throws JSONException {
        JSONStringer stringer = new JSONStringer(indentSpaces);
        this.writeTo(stringer);
        return stringer.toString();
    }

    void writeTo(JSONStringer stringer) throws JSONException {
        stringer.object();
        for (Map.Entry<String, Object> entry : this.nameValuePairs.entrySet()) {
            stringer.key(entry.getKey()).value(entry.getValue());
        }
        stringer.endObject();
    }

    public static String numberToString(Number number) throws JSONException {
        if (number == null) {
            throw new JSONException("Number must be non-null");
        }
        double doubleValue = number.doubleValue();
        JSON.checkDouble(doubleValue);
        if (number.equals(NEGATIVE_ZERO)) {
            return "-0";
        }
        long longValue = number.longValue();
        if (doubleValue == (double)longValue) {
            return Long.toString(longValue);
        }
        return number.toString();
    }

    public static String quote(String data) {
        if (data == null) {
            return "\"\"";
        }
        try {
            JSONStringer stringer = new JSONStringer();
            stringer.open(JSONStringer.Scope.NULL, "");
            stringer.value(data);
            stringer.close(JSONStringer.Scope.NULL, JSONStringer.Scope.NULL, "");
            return stringer.toString();
        }
        catch (JSONException e) {
            throw new AssertionError();
        }
    }

    public static Object wrap(Object o) {
        if (o == null) {
            return NULL;
        }
        if (o instanceof JSONArray || o instanceof JSONObject) {
            return o;
        }
        if (o.equals(NULL)) {
            return o;
        }
        try {
            if (o instanceof Collection) {
                return new JSONArray((Collection)o);
            }
            if (o.getClass().isArray()) {
                return new JSONArray(o);
            }
            if (o instanceof Map) {
                return new JSONObject((Map)o);
            }
            if (o instanceof Boolean || o instanceof Byte || o instanceof Character || o instanceof Double || o instanceof Float || o instanceof Integer || o instanceof Long || o instanceof Short || o instanceof String) {
                return o;
            }
            if (o.getClass().getPackage().getName().startsWith("java.")) {
                return o.toString();
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return null;
    }
}

