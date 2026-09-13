/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.configurationprocessor.metadata;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.configurationprocessor.metadata.ConfigurationMetadata;
import org.springframework.boot.configurationprocessor.metadata.ItemDeprecation;
import org.springframework.boot.configurationprocessor.metadata.ItemHint;
import org.springframework.boot.configurationprocessor.metadata.ItemMetadata;
import org.springframework.boot.configurationprocessor.metadata.JsonConverter;

public class JsonMarshaller {
    private static final int BUFFER_SIZE = 4098;

    public void write(ConfigurationMetadata metadata, OutputStream outputStream) throws IOException {
        try {
            JSONObject object = new JSONObject();
            JsonConverter converter = new JsonConverter();
            object.put("groups", converter.toJsonArray(metadata, ItemMetadata.ItemType.GROUP));
            object.put("properties", converter.toJsonArray(metadata, ItemMetadata.ItemType.PROPERTY));
            object.put("hints", converter.toJsonArray(metadata.getHints()));
            outputStream.write(object.toString(2).getBytes(StandardCharsets.UTF_8));
        }
        catch (Exception ex) {
            if (ex instanceof IOException) {
                throw (IOException)ex;
            }
            if (ex instanceof RuntimeException) {
                throw (RuntimeException)ex;
            }
            throw new IllegalStateException(ex);
        }
    }

    public ConfigurationMetadata read(InputStream inputStream) throws Exception {
        JSONArray hints;
        JSONArray properties;
        ConfigurationMetadata metadata = new ConfigurationMetadata();
        JSONObject object = new JSONObject(this.toString(inputStream));
        JSONArray groups = object.optJSONArray("groups");
        if (groups != null) {
            for (int i = 0; i < groups.length(); ++i) {
                metadata.add(this.toItemMetadata((JSONObject)groups.get(i), ItemMetadata.ItemType.GROUP));
            }
        }
        if ((properties = object.optJSONArray("properties")) != null) {
            for (int i = 0; i < properties.length(); ++i) {
                metadata.add(this.toItemMetadata((JSONObject)properties.get(i), ItemMetadata.ItemType.PROPERTY));
            }
        }
        if ((hints = object.optJSONArray("hints")) != null) {
            for (int i = 0; i < hints.length(); ++i) {
                metadata.add(this.toItemHint((JSONObject)hints.get(i)));
            }
        }
        return metadata;
    }

    private ItemMetadata toItemMetadata(JSONObject object, ItemMetadata.ItemType itemType) throws Exception {
        String name = object.getString("name");
        String type = object.optString("type", null);
        String description = object.optString("description", null);
        String sourceType = object.optString("sourceType", null);
        String sourceMethod = object.optString("sourceMethod", null);
        Object defaultValue = this.readItemValue(object.opt("defaultValue"));
        ItemDeprecation deprecation = this.toItemDeprecation(object);
        return new ItemMetadata(itemType, name, null, type, sourceType, sourceMethod, description, defaultValue, deprecation);
    }

    private ItemDeprecation toItemDeprecation(JSONObject object) throws Exception {
        if (object.has("deprecation")) {
            JSONObject deprecationJsonObject = object.getJSONObject("deprecation");
            ItemDeprecation deprecation = new ItemDeprecation();
            deprecation.setLevel(deprecationJsonObject.optString("level", null));
            deprecation.setReason(deprecationJsonObject.optString("reason", null));
            deprecation.setReplacement(deprecationJsonObject.optString("replacement", null));
            return deprecation;
        }
        return object.optBoolean("deprecated") ? new ItemDeprecation() : null;
    }

    private ItemHint toItemHint(JSONObject object) throws Exception {
        String name = object.getString("name");
        ArrayList<ItemHint.ValueHint> values = new ArrayList<ItemHint.ValueHint>();
        if (object.has("values")) {
            JSONArray valuesArray = object.getJSONArray("values");
            for (int i = 0; i < valuesArray.length(); ++i) {
                values.add(this.toValueHint((JSONObject)valuesArray.get(i)));
            }
        }
        ArrayList<ItemHint.ValueProvider> providers = new ArrayList<ItemHint.ValueProvider>();
        if (object.has("providers")) {
            JSONArray providersObject = object.getJSONArray("providers");
            for (int i = 0; i < providersObject.length(); ++i) {
                providers.add(this.toValueProvider((JSONObject)providersObject.get(i)));
            }
        }
        return new ItemHint(name, values, providers);
    }

    private ItemHint.ValueHint toValueHint(JSONObject object) throws Exception {
        Object value = this.readItemValue(object.get("value"));
        String description = object.optString("description", null);
        return new ItemHint.ValueHint(value, description);
    }

    private ItemHint.ValueProvider toValueProvider(JSONObject object) throws Exception {
        String name = object.getString("name");
        HashMap<String, Object> parameters = new HashMap<String, Object>();
        if (object.has("parameters")) {
            JSONObject parametersObject = object.getJSONObject("parameters");
            Iterator iterator = parametersObject.keys();
            while (iterator.hasNext()) {
                String key = (String)iterator.next();
                Object value = this.readItemValue(parametersObject.get(key));
                parameters.put(key, value);
            }
        }
        return new ItemHint.ValueProvider(name, parameters);
    }

    private Object readItemValue(Object value) throws Exception {
        if (value instanceof JSONArray) {
            JSONArray array = (JSONArray)value;
            Object[] content = new Object[array.length()];
            for (int i = 0; i < array.length(); ++i) {
                content[i] = array.get(i);
            }
            return content;
        }
        return value;
    }

    private String toString(InputStream inputStream) throws IOException {
        int bytesRead;
        StringBuilder out = new StringBuilder();
        InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        char[] buffer = new char[4098];
        while ((bytesRead = reader.read(buffer)) != -1) {
            out.append(buffer, 0, bytesRead);
        }
        return out.toString();
    }
}

