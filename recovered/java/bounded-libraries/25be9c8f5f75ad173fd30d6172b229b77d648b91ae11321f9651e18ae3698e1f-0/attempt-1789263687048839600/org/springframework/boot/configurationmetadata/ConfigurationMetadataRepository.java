/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.configurationmetadata;

import java.util.Map;
import org.springframework.boot.configurationmetadata.ConfigurationMetadataGroup;
import org.springframework.boot.configurationmetadata.ConfigurationMetadataProperty;

public interface ConfigurationMetadataRepository {
    public static final String ROOT_GROUP = "_ROOT_GROUP_";

    public Map<String, ConfigurationMetadataGroup> getAllGroups();

    public Map<String, ConfigurationMetadataProperty> getAllProperties();
}

