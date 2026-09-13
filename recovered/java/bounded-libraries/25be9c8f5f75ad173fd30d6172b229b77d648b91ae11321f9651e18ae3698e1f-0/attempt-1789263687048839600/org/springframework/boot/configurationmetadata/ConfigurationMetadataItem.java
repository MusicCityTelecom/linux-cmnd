/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.configurationmetadata;

import org.springframework.boot.configurationmetadata.ConfigurationMetadataProperty;

class ConfigurationMetadataItem
extends ConfigurationMetadataProperty {
    private String sourceType;
    private String sourceMethod;

    ConfigurationMetadataItem() {
    }

    String getSourceType() {
        return this.sourceType;
    }

    void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    String getSourceMethod() {
        return this.sourceMethod;
    }

    void setSourceMethod(String sourceMethod) {
        this.sourceMethod = sourceMethod;
    }
}

