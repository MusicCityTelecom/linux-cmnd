/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.configurationmetadata.ConfigurationMetadataProperty
 *  org.springframework.boot.configurationmetadata.Deprecation
 *  org.springframework.boot.context.properties.source.ConfigurationProperty
 *  org.springframework.boot.origin.Origin
 *  org.springframework.boot.origin.TextResourceOrigin
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.context.properties.migrator;

import java.time.Duration;
import java.util.Comparator;
import java.util.Map;
import org.springframework.boot.configurationmetadata.ConfigurationMetadataProperty;
import org.springframework.boot.configurationmetadata.Deprecation;
import org.springframework.boot.context.properties.source.ConfigurationProperty;
import org.springframework.boot.origin.Origin;
import org.springframework.boot.origin.TextResourceOrigin;
import org.springframework.util.StringUtils;

class PropertyMigration {
    public static final Comparator<PropertyMigration> COMPARATOR = Comparator.comparing(property -> property.getMetadata().getId());
    private final ConfigurationProperty property;
    private final Integer lineNumber;
    private final ConfigurationMetadataProperty metadata;
    private final ConfigurationMetadataProperty replacementMetadata;
    private final boolean compatibleType;

    PropertyMigration(ConfigurationProperty property, ConfigurationMetadataProperty metadata, ConfigurationMetadataProperty replacementMetadata) {
        this.property = property;
        this.lineNumber = PropertyMigration.determineLineNumber(property);
        this.metadata = metadata;
        this.replacementMetadata = replacementMetadata;
        this.compatibleType = PropertyMigration.determineCompatibleType(metadata, replacementMetadata);
    }

    private static Integer determineLineNumber(ConfigurationProperty property) {
        TextResourceOrigin textOrigin;
        Origin origin = property.getOrigin();
        if (origin instanceof TextResourceOrigin && (textOrigin = (TextResourceOrigin)origin).getLocation() != null) {
            return textOrigin.getLocation().getLine() + 1;
        }
        return null;
    }

    private static boolean determineCompatibleType(ConfigurationMetadataProperty metadata, ConfigurationMetadataProperty replacementMetadata) {
        String currentType = metadata.getType();
        String replacementType = PropertyMigration.determineReplacementType(replacementMetadata);
        if (replacementType == null || currentType == null) {
            return false;
        }
        if (replacementType.equals(currentType)) {
            return true;
        }
        return replacementType.equals(Duration.class.getName()) && (currentType.equals(Long.class.getName()) || currentType.equals(Integer.class.getName()));
    }

    private static String determineReplacementType(ConfigurationMetadataProperty replacementMetadata) {
        int lastComma;
        if (replacementMetadata == null || replacementMetadata.getType() == null) {
            return null;
        }
        String candidate = replacementMetadata.getType();
        if (candidate.startsWith(Map.class.getName()) && (lastComma = candidate.lastIndexOf(44)) != -1) {
            return candidate.substring(lastComma + 1, candidate.length() - 1).trim();
        }
        return candidate;
    }

    ConfigurationProperty getProperty() {
        return this.property;
    }

    Integer getLineNumber() {
        return this.lineNumber;
    }

    ConfigurationMetadataProperty getMetadata() {
        return this.metadata;
    }

    boolean isCompatibleType() {
        return this.compatibleType;
    }

    String determineReason() {
        if (this.compatibleType) {
            return "Replacement: " + this.metadata.getDeprecation().getReplacement();
        }
        Deprecation deprecation = this.metadata.getDeprecation();
        if (StringUtils.hasText((String)deprecation.getShortReason())) {
            return "Reason: " + deprecation.getShortReason();
        }
        if (StringUtils.hasText((String)deprecation.getReplacement())) {
            if (this.replacementMetadata != null) {
                return String.format("Reason: Replacement key '%s' uses an incompatible target type", deprecation.getReplacement());
            }
            return String.format("Reason: No metadata found for replacement key '%s'", deprecation.getReplacement());
        }
        return "Reason: none";
    }
}

