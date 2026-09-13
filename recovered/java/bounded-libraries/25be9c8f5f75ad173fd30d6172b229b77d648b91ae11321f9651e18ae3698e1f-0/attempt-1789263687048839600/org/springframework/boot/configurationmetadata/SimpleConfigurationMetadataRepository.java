/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.configurationmetadata;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.configurationmetadata.ConfigurationMetadataGroup;
import org.springframework.boot.configurationmetadata.ConfigurationMetadataProperty;
import org.springframework.boot.configurationmetadata.ConfigurationMetadataRepository;
import org.springframework.boot.configurationmetadata.ConfigurationMetadataSource;

public class SimpleConfigurationMetadataRepository
implements ConfigurationMetadataRepository,
Serializable {
    private final Map<String, ConfigurationMetadataGroup> allGroups = new HashMap<String, ConfigurationMetadataGroup>();

    @Override
    public Map<String, ConfigurationMetadataGroup> getAllGroups() {
        return Collections.unmodifiableMap(this.allGroups);
    }

    @Override
    public Map<String, ConfigurationMetadataProperty> getAllProperties() {
        HashMap<String, ConfigurationMetadataProperty> properties = new HashMap<String, ConfigurationMetadataProperty>();
        for (ConfigurationMetadataGroup group : this.allGroups.values()) {
            properties.putAll(group.getProperties());
        }
        return properties;
    }

    public void add(Collection<ConfigurationMetadataSource> sources) {
        for (ConfigurationMetadataSource source : sources) {
            String sourceType;
            String groupId = source.getGroupId();
            ConfigurationMetadataGroup group = this.allGroups.get(groupId);
            if (group == null) {
                group = new ConfigurationMetadataGroup(groupId);
                this.allGroups.put(groupId, group);
            }
            if ((sourceType = source.getType()) == null) continue;
            this.addOrMergeSource(group.getSources(), sourceType, source);
        }
    }

    public void add(ConfigurationMetadataProperty property, ConfigurationMetadataSource source) {
        if (source != null) {
            this.putIfAbsent(source.getProperties(), property.getId(), property);
        }
        this.putIfAbsent(this.getGroup(source).getProperties(), property.getId(), property);
    }

    public void include(ConfigurationMetadataRepository repository) {
        for (ConfigurationMetadataGroup group : repository.getAllGroups().values()) {
            ConfigurationMetadataGroup existingGroup = this.allGroups.get(group.getId());
            if (existingGroup == null) {
                this.allGroups.put(group.getId(), group);
                continue;
            }
            group.getProperties().forEach((name, value) -> this.putIfAbsent((Map)existingGroup.getProperties(), (String)name, (Object)value));
            group.getSources().forEach((name, value) -> this.addOrMergeSource(existingGroup.getSources(), (String)name, (ConfigurationMetadataSource)value));
        }
    }

    private ConfigurationMetadataGroup getGroup(ConfigurationMetadataSource source) {
        if (source == null) {
            ConfigurationMetadataGroup rootGroup = this.allGroups.get("_ROOT_GROUP_");
            if (rootGroup == null) {
                rootGroup = new ConfigurationMetadataGroup("_ROOT_GROUP_");
                this.allGroups.put("_ROOT_GROUP_", rootGroup);
            }
            return rootGroup;
        }
        return this.allGroups.get(source.getGroupId());
    }

    private void addOrMergeSource(Map<String, ConfigurationMetadataSource> sources, String name, ConfigurationMetadataSource source) {
        ConfigurationMetadataSource existingSource = sources.get(name);
        if (existingSource == null) {
            sources.put(name, source);
        } else {
            source.getProperties().forEach((k, v) -> this.putIfAbsent((Map)existingSource.getProperties(), (String)k, (Object)v));
        }
    }

    private <V> void putIfAbsent(Map<String, V> map, String key, V value) {
        if (!map.containsKey(key)) {
            map.put(key, value);
        }
    }
}

