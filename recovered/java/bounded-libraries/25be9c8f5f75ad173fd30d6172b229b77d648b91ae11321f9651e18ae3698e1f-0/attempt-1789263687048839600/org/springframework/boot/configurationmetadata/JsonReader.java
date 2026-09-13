/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package org.springframework.boot.configurationmetadata;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.configurationmetadata.ConfigurationMetadataHint;
import org.springframework.boot.configurationmetadata.ConfigurationMetadataItem;
import org.springframework.boot.configurationmetadata.ConfigurationMetadataSource;
import org.springframework.boot.configurationmetadata.Deprecation;
import org.springframework.boot.configurationmetadata.RawConfigurationMetadata;
import org.springframework.boot.configurationmetadata.SentenceExtractor;
import org.springframework.boot.configurationmetadata.ValueHint;
import org.springframework.boot.configurationmetadata.ValueProvider;

class JsonReader {
    private static final int BUFFER_SIZE = 4096;
    private final SentenceExtractor sentenceExtractor = new SentenceExtractor();

    JsonReader() {
    }

    RawConfigurationMetadata read(InputStream in, Charset charset) throws IOException {
        try {
            JSONObject json = this.readJson(in, charset);
            List<ConfigurationMetadataSource> groups = this.parseAllSources(json);
            List<ConfigurationMetadataItem> items = this.parseAllItems(json);
            List<ConfigurationMetadataHint> hints = this.parseAllHints(json);
            return new RawConfigurationMetadata(groups, items, hints);
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

    private List<ConfigurationMetadataSource> parseAllSources(JSONObject root) throws Exception {
        ArrayList<ConfigurationMetadataSource> result = new ArrayList<ConfigurationMetadataSource>();
        if (!root.has("groups")) {
            return result;
        }
        JSONArray sources = root.getJSONArray("groups");
        for (int i = 0; i < sources.length(); ++i) {
            JSONObject source = sources.getJSONObject(i);
            result.add(this.parseSource(source));
        }
        return result;
    }

    private List<ConfigurationMetadataItem> parseAllItems(JSONObject root) throws Exception {
        ArrayList<ConfigurationMetadataItem> result = new ArrayList<ConfigurationMetadataItem>();
        if (!root.has("properties")) {
            return result;
        }
        JSONArray items = root.getJSONArray("properties");
        for (int i = 0; i < items.length(); ++i) {
            JSONObject item = items.getJSONObject(i);
            result.add(this.parseItem(item));
        }
        return result;
    }

    private List<ConfigurationMetadataHint> parseAllHints(JSONObject root) throws Exception {
        ArrayList<ConfigurationMetadataHint> result = new ArrayList<ConfigurationMetadataHint>();
        if (!root.has("hints")) {
            return result;
        }
        JSONArray items = root.getJSONArray("hints");
        for (int i = 0; i < items.length(); ++i) {
            JSONObject item = items.getJSONObject(i);
            result.add(this.parseHint(item));
        }
        return result;
    }

    private ConfigurationMetadataSource parseSource(JSONObject json) throws Exception {
        ConfigurationMetadataSource source = new ConfigurationMetadataSource();
        source.setGroupId(json.getString("name"));
        source.setType(json.optString("type", null));
        String description = json.optString("description", null);
        source.setDescription(description);
        source.setShortDescription(this.sentenceExtractor.getFirstSentence(description));
        source.setSourceType(json.optString("sourceType", null));
        source.setSourceMethod(json.optString("sourceMethod", null));
        return source;
    }

    private ConfigurationMetadataItem parseItem(JSONObject json) throws Exception {
        ConfigurationMetadataItem item = new ConfigurationMetadataItem();
        item.setId(json.getString("name"));
        item.setType(json.optString("type", null));
        String description = json.optString("description", null);
        item.setDescription(description);
        item.setShortDescription(this.sentenceExtractor.getFirstSentence(description));
        item.setDefaultValue(this.readItemValue(json.opt("defaultValue")));
        item.setDeprecation(this.parseDeprecation(json));
        item.setSourceType(json.optString("sourceType", null));
        item.setSourceMethod(json.optString("sourceMethod", null));
        return item;
    }

    private ConfigurationMetadataHint parseHint(JSONObject json) throws Exception {
        int i;
        ConfigurationMetadataHint hint = new ConfigurationMetadataHint();
        hint.setId(json.getString("name"));
        if (json.has("values")) {
            JSONArray values = json.getJSONArray("values");
            for (i = 0; i < values.length(); ++i) {
                JSONObject value = values.getJSONObject(i);
                ValueHint valueHint = new ValueHint();
                valueHint.setValue(this.readItemValue(value.get("value")));
                String description = value.optString("description", null);
                valueHint.setDescription(description);
                valueHint.setShortDescription(this.sentenceExtractor.getFirstSentence(description));
                hint.getValueHints().add(valueHint);
            }
        }
        if (json.has("providers")) {
            JSONArray providers = json.getJSONArray("providers");
            for (i = 0; i < providers.length(); ++i) {
                JSONObject provider = providers.getJSONObject(i);
                ValueProvider valueProvider = new ValueProvider();
                valueProvider.setName(provider.getString("name"));
                if (provider.has("parameters")) {
                    JSONObject parameters = provider.getJSONObject("parameters");
                    Iterator keys = parameters.keys();
                    while (keys.hasNext()) {
                        String key = (String)keys.next();
                        valueProvider.getParameters().put(key, this.readItemValue(parameters.get(key)));
                    }
                }
                hint.getValueProviders().add(valueProvider);
            }
        }
        return hint;
    }

    private Deprecation parseDeprecation(JSONObject object) throws Exception {
        if (object.has("deprecation")) {
            JSONObject deprecationJsonObject = object.getJSONObject("deprecation");
            Deprecation deprecation = new Deprecation();
            deprecation.setLevel(this.parseDeprecationLevel(deprecationJsonObject.optString("level", null)));
            String reason = deprecationJsonObject.optString("reason", null);
            deprecation.setReason(reason);
            deprecation.setShortReason(this.sentenceExtractor.getFirstSentence(reason));
            deprecation.setReplacement(deprecationJsonObject.optString("replacement", null));
            return deprecation;
        }
        return object.optBoolean("deprecated") ? new Deprecation() : null;
    }

    private Deprecation.Level parseDeprecationLevel(String value) {
        if (value != null) {
            try {
                return Deprecation.Level.valueOf(value.toUpperCase(Locale.ENGLISH));
            }
            catch (IllegalArgumentException illegalArgumentException) {
                // empty catch block
            }
        }
        return Deprecation.Level.WARNING;
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

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private JSONObject readJson(InputStream in, Charset charset) throws Exception {
        try {
            int bytesRead;
            StringBuilder out = new StringBuilder();
            InputStreamReader reader = new InputStreamReader(in, charset);
            char[] buffer = new char[4096];
            while ((bytesRead = reader.read(buffer)) != -1) {
                out.append(buffer, 0, bytesRead);
            }
            JSONObject jSONObject = new JSONObject(out.toString());
            return jSONObject;
        }
        finally {
            in.close();
        }
    }
}

