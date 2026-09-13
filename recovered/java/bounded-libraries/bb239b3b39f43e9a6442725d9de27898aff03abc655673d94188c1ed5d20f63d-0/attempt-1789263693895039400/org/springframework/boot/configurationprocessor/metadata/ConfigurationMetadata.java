/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.configurationprocessor.metadata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.springframework.boot.configurationprocessor.metadata.ItemDeprecation;
import org.springframework.boot.configurationprocessor.metadata.ItemHint;
import org.springframework.boot.configurationprocessor.metadata.ItemMetadata;

public class ConfigurationMetadata {
    private static final Set<Character> SEPARATORS;
    private final Map<String, List<ItemMetadata>> items;
    private final Map<String, List<ItemHint>> hints;

    public ConfigurationMetadata() {
        this.items = new LinkedHashMap<String, List<ItemMetadata>>();
        this.hints = new LinkedHashMap<String, List<ItemHint>>();
    }

    public ConfigurationMetadata(ConfigurationMetadata metadata) {
        this.items = new LinkedHashMap<String, List<ItemMetadata>>(metadata.items);
        this.hints = new LinkedHashMap<String, List<ItemHint>>(metadata.hints);
    }

    public void add(ItemMetadata itemMetadata) {
        this.add(this.items, itemMetadata.getName(), itemMetadata, false);
    }

    public void addIfMissing(ItemMetadata itemMetadata) {
        this.add(this.items, itemMetadata.getName(), itemMetadata, true);
    }

    public void add(ItemHint itemHint) {
        this.add(this.hints, itemHint.getName(), itemHint, false);
    }

    public void merge(ConfigurationMetadata metadata) {
        for (ItemMetadata additionalItem : metadata.getItems()) {
            this.mergeItemMetadata(additionalItem);
        }
        for (ItemHint itemHint : metadata.getHints()) {
            this.add(itemHint);
        }
    }

    public List<ItemMetadata> getItems() {
        return ConfigurationMetadata.flattenValues(this.items);
    }

    public List<ItemHint> getHints() {
        return ConfigurationMetadata.flattenValues(this.hints);
    }

    protected void mergeItemMetadata(ItemMetadata metadata) {
        ItemMetadata matching = this.findMatchingItemMetadata(metadata);
        if (matching != null) {
            if (metadata.getDescription() != null) {
                matching.setDescription(metadata.getDescription());
            }
            if (metadata.getDefaultValue() != null) {
                matching.setDefaultValue(metadata.getDefaultValue());
            }
            ItemDeprecation deprecation = metadata.getDeprecation();
            ItemDeprecation matchingDeprecation = matching.getDeprecation();
            if (deprecation != null) {
                if (matchingDeprecation == null) {
                    matching.setDeprecation(deprecation);
                } else {
                    if (deprecation.getReason() != null) {
                        matchingDeprecation.setReason(deprecation.getReason());
                    }
                    if (deprecation.getReplacement() != null) {
                        matchingDeprecation.setReplacement(deprecation.getReplacement());
                    }
                    if (deprecation.getLevel() != null) {
                        matchingDeprecation.setLevel(deprecation.getLevel());
                    }
                }
            }
        } else {
            this.add(this.items, metadata.getName(), metadata, false);
        }
    }

    private <K, V> void add(Map<K, List<V>> map, K key, V value, boolean ifMissing) {
        List values = map.computeIfAbsent(key, k -> new ArrayList());
        if (!ifMissing || values.isEmpty()) {
            values.add(value);
        }
    }

    private ItemMetadata findMatchingItemMetadata(ItemMetadata metadata) {
        List<ItemMetadata> candidates = this.items.get(metadata.getName());
        if (candidates == null || candidates.isEmpty()) {
            return null;
        }
        candidates = new ArrayList<ItemMetadata>(candidates);
        candidates.removeIf(itemMetadata -> !itemMetadata.hasSameType(metadata));
        if (candidates.size() > 1 && metadata.getType() != null) {
            candidates.removeIf(itemMetadata -> !metadata.getType().equals(itemMetadata.getType()));
        }
        if (candidates.size() == 1) {
            return candidates.get(0);
        }
        for (ItemMetadata candidate : candidates) {
            if (!this.nullSafeEquals(candidate.getSourceType(), metadata.getSourceType())) continue;
            return candidate;
        }
        return null;
    }

    private boolean nullSafeEquals(Object o1, Object o2) {
        if (o1 == o2) {
            return true;
        }
        return o1 != null && o1.equals(o2);
    }

    public static String nestedPrefix(String prefix, String name) {
        String nestedPrefix = prefix != null ? prefix : "";
        String dashedName = ConfigurationMetadata.toDashedCase(name);
        nestedPrefix = nestedPrefix + (nestedPrefix == null || nestedPrefix.isEmpty() ? dashedName : "." + dashedName);
        return nestedPrefix;
    }

    static String toDashedCase(String name) {
        StringBuilder dashed = new StringBuilder();
        Character previous = null;
        for (int i = 0; i < name.length(); ++i) {
            char current = name.charAt(i);
            if (SEPARATORS.contains(Character.valueOf(current))) {
                dashed.append("-");
            } else if (Character.isUpperCase(current) && previous != null && !SEPARATORS.contains(previous)) {
                dashed.append("-").append(current);
            } else {
                dashed.append(current);
            }
            previous = Character.valueOf(current);
        }
        return dashed.toString().toLowerCase(Locale.ENGLISH);
    }

    private static <T extends Comparable<T>> List<T> flattenValues(Map<?, List<T>> map) {
        ArrayList<T> content = new ArrayList<T>();
        for (List<T> values : map.values()) {
            content.addAll(values);
        }
        Collections.sort(content);
        return content;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(String.format("items: %n", new Object[0]));
        this.items.values().forEach(itemMetadata -> result.append("\t").append(String.format("%s%n", itemMetadata)));
        return result.toString();
    }

    static {
        List<Character> chars = Arrays.asList(Character.valueOf('-'), Character.valueOf('_'));
        SEPARATORS = Collections.unmodifiableSet(new HashSet<Character>(chars));
    }
}

