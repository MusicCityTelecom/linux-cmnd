/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.configurationmetadata;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.configurationmetadata.ValueHint;
import org.springframework.boot.configurationmetadata.ValueProvider;

class ConfigurationMetadataHint {
    private static final String KEY_SUFFIX = ".keys";
    private static final String VALUE_SUFFIX = ".values";
    private String id;
    private final List<ValueHint> valueHints = new ArrayList<ValueHint>();
    private final List<ValueProvider> valueProviders = new ArrayList<ValueProvider>();

    ConfigurationMetadataHint() {
    }

    boolean isMapKeyHints() {
        return this.id != null && this.id.endsWith(KEY_SUFFIX);
    }

    boolean isMapValueHints() {
        return this.id != null && this.id.endsWith(VALUE_SUFFIX);
    }

    String resolveId() {
        if (this.isMapKeyHints()) {
            return this.id.substring(0, this.id.length() - KEY_SUFFIX.length());
        }
        if (this.isMapValueHints()) {
            return this.id.substring(0, this.id.length() - VALUE_SUFFIX.length());
        }
        return this.id;
    }

    String getId() {
        return this.id;
    }

    void setId(String id) {
        this.id = id;
    }

    List<ValueHint> getValueHints() {
        return this.valueHints;
    }

    List<ValueProvider> getValueProviders() {
        return this.valueProviders;
    }
}

