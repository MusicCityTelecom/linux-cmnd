/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.configurationprocessor.metadata;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.configurationprocessor.metadata.ConfigurationMetadata;
import org.springframework.boot.configurationprocessor.metadata.ItemDeprecation;
import org.springframework.boot.configurationprocessor.metadata.ItemHint;
import org.springframework.boot.configurationprocessor.metadata.ItemMetadata;

class JsonConverter {
    private static final ItemMetadataComparator ITEM_COMPARATOR = new ItemMetadataComparator();

    JsonConverter() {
    }

    JSONArray toJsonArray(ConfigurationMetadata metadata, ItemMetadata.ItemType itemType) throws Exception {
        JSONArray jsonArray = new JSONArray();
        List items = metadata.getItems().stream().filter(item -> item.isOfItemType(itemType)).sorted(ITEM_COMPARATOR).collect(Collectors.toList());
        for (ItemMetadata item2 : items) {
            if (!item2.isOfItemType(itemType)) continue;
            jsonArray.put(this.toJsonObject(item2));
        }
        return jsonArray;
    }

    JSONArray toJsonArray(Collection<ItemHint> hints) throws Exception {
        JSONArray jsonArray = new JSONArray();
        for (ItemHint hint : hints) {
            jsonArray.put(this.toJsonObject(hint));
        }
        return jsonArray;
    }

    JSONObject toJsonObject(ItemMetadata item) throws Exception {
        ItemDeprecation deprecation;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", item.getName());
        jsonObject.putOpt("type", item.getType());
        jsonObject.putOpt("description", item.getDescription());
        jsonObject.putOpt("sourceType", item.getSourceType());
        jsonObject.putOpt("sourceMethod", item.getSourceMethod());
        Object defaultValue = item.getDefaultValue();
        if (defaultValue != null) {
            this.putDefaultValue(jsonObject, defaultValue);
        }
        if ((deprecation = item.getDeprecation()) != null) {
            jsonObject.put("deprecated", true);
            JSONObject deprecationJsonObject = new JSONObject();
            if (deprecation.getLevel() != null) {
                deprecationJsonObject.put("level", deprecation.getLevel());
            }
            if (deprecation.getReason() != null) {
                deprecationJsonObject.put("reason", deprecation.getReason());
            }
            if (deprecation.getReplacement() != null) {
                deprecationJsonObject.put("replacement", deprecation.getReplacement());
            }
            jsonObject.put("deprecation", deprecationJsonObject);
        }
        return jsonObject;
    }

    private JSONObject toJsonObject(ItemHint hint) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", hint.getName());
        if (!hint.getValues().isEmpty()) {
            jsonObject.put("values", this.getItemHintValues(hint));
        }
        if (!hint.getProviders().isEmpty()) {
            jsonObject.put("providers", this.getItemHintProviders(hint));
        }
        return jsonObject;
    }

    private JSONArray getItemHintValues(ItemHint hint) throws Exception {
        JSONArray values = new JSONArray();
        for (ItemHint.ValueHint value : hint.getValues()) {
            values.put(this.getItemHintValue(value));
        }
        return values;
    }

    private JSONObject getItemHintValue(ItemHint.ValueHint value) throws Exception {
        JSONObject result = new JSONObject();
        this.putHintValue(result, value.getValue());
        result.putOpt("description", value.getDescription());
        return result;
    }

    private JSONArray getItemHintProviders(ItemHint hint) throws Exception {
        JSONArray providers = new JSONArray();
        for (ItemHint.ValueProvider provider : hint.getProviders()) {
            providers.put(this.getItemHintProvider(provider));
        }
        return providers;
    }

    private JSONObject getItemHintProvider(ItemHint.ValueProvider provider) throws Exception {
        JSONObject result = new JSONObject();
        result.put("name", provider.getName());
        if (provider.getParameters() != null && !provider.getParameters().isEmpty()) {
            JSONObject parameters = new JSONObject();
            for (Map.Entry<String, Object> entry : provider.getParameters().entrySet()) {
                parameters.put(entry.getKey(), this.extractItemValue(entry.getValue()));
            }
            result.put("parameters", parameters);
        }
        return result;
    }

    private void putHintValue(JSONObject jsonObject, Object value) throws Exception {
        Object hintValue = this.extractItemValue(value);
        jsonObject.put("value", hintValue);
    }

    private void putDefaultValue(JSONObject jsonObject, Object value) throws Exception {
        Object defaultValue = this.extractItemValue(value);
        jsonObject.put("defaultValue", defaultValue);
    }

    private Object extractItemValue(Object value) {
        Object defaultValue = value;
        if (value.getClass().isArray()) {
            JSONArray array = new JSONArray();
            int length = Array.getLength(value);
            for (int i = 0; i < length; ++i) {
                array.put(Array.get(value, i));
            }
            defaultValue = array;
        }
        return defaultValue;
    }

    private static class ItemMetadataComparator
    implements Comparator<ItemMetadata> {
        private static final Comparator<ItemMetadata> GROUP = Comparator.comparing(ItemMetadata::getName).thenComparing(ItemMetadata::getSourceType, Comparator.nullsFirst(Comparator.naturalOrder()));
        private static final Comparator<ItemMetadata> ITEM = Comparator.comparing(ItemMetadataComparator::isDeprecated).thenComparing(ItemMetadata::getName).thenComparing(ItemMetadata::getSourceType, Comparator.nullsFirst(Comparator.naturalOrder()));

        private ItemMetadataComparator() {
        }

        @Override
        public int compare(ItemMetadata o1, ItemMetadata o2) {
            if (o1.isOfItemType(ItemMetadata.ItemType.GROUP)) {
                return GROUP.compare(o1, o2);
            }
            return ITEM.compare(o1, o2);
        }

        private static boolean isDeprecated(ItemMetadata item) {
            return item.getDeprecation() != null;
        }
    }
}

