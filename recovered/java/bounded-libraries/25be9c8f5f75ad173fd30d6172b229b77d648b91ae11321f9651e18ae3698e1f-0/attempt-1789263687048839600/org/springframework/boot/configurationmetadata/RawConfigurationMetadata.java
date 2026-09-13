/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.configurationmetadata;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.springframework.boot.configurationmetadata.ConfigurationMetadataHint;
import org.springframework.boot.configurationmetadata.ConfigurationMetadataItem;
import org.springframework.boot.configurationmetadata.ConfigurationMetadataSource;

class RawConfigurationMetadata {
    private final List<ConfigurationMetadataSource> sources;
    private final List<ConfigurationMetadataItem> items;
    private final List<ConfigurationMetadataHint> hints;

    RawConfigurationMetadata(List<ConfigurationMetadataSource> sources, List<ConfigurationMetadataItem> items, List<ConfigurationMetadataHint> hints) {
        this.sources = new ArrayList<ConfigurationMetadataSource>(sources);
        this.items = new ArrayList<ConfigurationMetadataItem>(items);
        this.hints = new ArrayList<ConfigurationMetadataHint>(hints);
        for (ConfigurationMetadataItem item : this.items) {
            this.resolveName(item);
        }
    }

    List<ConfigurationMetadataSource> getSources() {
        return this.sources;
    }

    ConfigurationMetadataSource getSource(ConfigurationMetadataItem item) {
        if (item.getSourceType() == null) {
            return null;
        }
        return this.sources.stream().filter(candidate -> item.getSourceType().equals(candidate.getType()) && item.getId().startsWith(candidate.getGroupId())).max(Comparator.comparingInt(candidate -> candidate.getGroupId().length())).orElse(null);
    }

    List<ConfigurationMetadataItem> getItems() {
        return this.items;
    }

    List<ConfigurationMetadataHint> getHints() {
        return this.hints;
    }

    private void resolveName(ConfigurationMetadataItem item) {
        item.setName(item.getId());
        ConfigurationMetadataSource source = this.getSource(item);
        if (source != null) {
            String groupId = source.getGroupId();
            String dottedPrefix = groupId + ".";
            String id = item.getId();
            if (RawConfigurationMetadata.hasLength(groupId) && id.startsWith(dottedPrefix)) {
                String name = id.substring(dottedPrefix.length());
                item.setName(name);
            }
        }
    }

    private static boolean hasLength(String string) {
        return string != null && !string.isEmpty();
    }
}

