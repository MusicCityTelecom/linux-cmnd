/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.configurationmetadata;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.configurationmetadata.ConfigurationMetadataProperty;

public class ConfigurationMetadataSource
implements Serializable {
    private String groupId;
    private String type;
    private String description;
    private String shortDescription;
    private String sourceType;
    private String sourceMethod;
    private final Map<String, ConfigurationMetadataProperty> properties = new HashMap<String, ConfigurationMetadataProperty>();

    public String getGroupId() {
        return this.groupId;
    }

    void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getType() {
        return this.type;
    }

    void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return this.description;
    }

    void setDescription(String description) {
        this.description = description;
    }

    public String getShortDescription() {
        return this.shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getSourceType() {
        return this.sourceType;
    }

    void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getSourceMethod() {
        return this.sourceMethod;
    }

    void setSourceMethod(String sourceMethod) {
        this.sourceMethod = sourceMethod;
    }

    public Map<String, ConfigurationMetadataProperty> getProperties() {
        return this.properties;
    }
}

