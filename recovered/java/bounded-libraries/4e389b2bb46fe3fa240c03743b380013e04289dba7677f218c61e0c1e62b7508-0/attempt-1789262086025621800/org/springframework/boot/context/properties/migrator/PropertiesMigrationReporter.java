/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.configurationmetadata.ConfigurationMetadataProperty
 *  org.springframework.boot.configurationmetadata.ConfigurationMetadataRepository
 *  org.springframework.boot.context.properties.source.ConfigurationProperty
 *  org.springframework.boot.context.properties.source.ConfigurationPropertyName
 *  org.springframework.boot.context.properties.source.ConfigurationPropertySource
 *  org.springframework.boot.context.properties.source.ConfigurationPropertySources
 *  org.springframework.boot.env.OriginTrackedMapPropertySource
 *  org.springframework.boot.origin.Origin
 *  org.springframework.boot.origin.OriginTrackedValue
 *  org.springframework.core.env.ConfigurableEnvironment
 *  org.springframework.core.env.Environment
 *  org.springframework.core.env.PropertySource
 *  org.springframework.util.LinkedMultiValueMap
 *  org.springframework.util.MultiValueMap
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.context.properties.migrator;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.springframework.boot.configurationmetadata.ConfigurationMetadataProperty;
import org.springframework.boot.configurationmetadata.ConfigurationMetadataRepository;
import org.springframework.boot.context.properties.migrator.PropertiesMigrationReport;
import org.springframework.boot.context.properties.migrator.PropertyMigration;
import org.springframework.boot.context.properties.source.ConfigurationProperty;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.boot.origin.Origin;
import org.springframework.boot.origin.OriginTrackedValue;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

class PropertiesMigrationReporter {
    private final Map<String, ConfigurationMetadataProperty> allProperties;
    private final ConfigurableEnvironment environment;

    PropertiesMigrationReporter(ConfigurationMetadataRepository metadataRepository, ConfigurableEnvironment environment) {
        this.allProperties = Collections.unmodifiableMap(metadataRepository.getAllProperties());
        this.environment = environment;
    }

    PropertiesMigrationReport getReport() {
        PropertiesMigrationReport report = new PropertiesMigrationReport();
        Map<String, List<PropertyMigration>> properties = this.getMatchingProperties(ConfigurationMetadataProperty::isDeprecated);
        if (properties.isEmpty()) {
            return report;
        }
        properties.forEach((name, candidates) -> {
            PropertySource<?> propertySource = this.mapPropertiesWithReplacement(report, (String)name, (List<PropertyMigration>)candidates);
            if (propertySource != null) {
                this.environment.getPropertySources().addBefore(name, propertySource);
            }
        });
        return report;
    }

    private PropertySource<?> mapPropertiesWithReplacement(PropertiesMigrationReport report, String name, List<PropertyMigration> properties) {
        report.add(name, properties);
        List renamed = properties.stream().filter(PropertyMigration::isCompatibleType).collect(Collectors.toList());
        if (renamed.isEmpty()) {
            return null;
        }
        String target = "migrate-" + name;
        LinkedHashMap<String, OriginTrackedValue> content = new LinkedHashMap<String, OriginTrackedValue>();
        for (PropertyMigration candidate : renamed) {
            OriginTrackedValue value = OriginTrackedValue.of((Object)candidate.getProperty().getValue(), (Origin)candidate.getProperty().getOrigin());
            content.put(candidate.getMetadata().getDeprecation().getReplacement(), value);
        }
        return new OriginTrackedMapPropertySource(target, content);
    }

    private Map<String, List<PropertyMigration>> getMatchingProperties(Predicate<ConfigurationMetadataProperty> filter) {
        LinkedMultiValueMap result = new LinkedMultiValueMap();
        List candidates = this.allProperties.values().stream().filter(filter).collect(Collectors.toList());
        this.getPropertySourcesAsMap().forEach((arg_0, arg_1) -> this.lambda$getMatchingProperties$2(candidates, (MultiValueMap)result, arg_0, arg_1));
        return result;
    }

    private ConfigurationMetadataProperty determineReplacementMetadata(ConfigurationMetadataProperty metadata) {
        String replacementId = metadata.getDeprecation().getReplacement();
        if (StringUtils.hasText((String)replacementId)) {
            ConfigurationMetadataProperty replacement = this.allProperties.get(replacementId);
            if (replacement != null) {
                return replacement;
            }
            return this.detectMapValueReplacement(replacementId);
        }
        return null;
    }

    private ConfigurationMetadataProperty detectMapValueReplacement(String fullId) {
        int lastDot = fullId.lastIndexOf(46);
        if (lastDot != -1) {
            return this.allProperties.get(fullId.substring(0, lastDot));
        }
        return null;
    }

    private Map<String, ConfigurationPropertySource> getPropertySourcesAsMap() {
        LinkedHashMap<String, ConfigurationPropertySource> map = new LinkedHashMap<String, ConfigurationPropertySource>();
        for (ConfigurationPropertySource source : ConfigurationPropertySources.get((Environment)this.environment)) {
            map.put(this.determinePropertySourceName(source), source);
        }
        return map;
    }

    private String determinePropertySourceName(ConfigurationPropertySource source) {
        if (source.getUnderlyingSource() instanceof PropertySource) {
            return ((PropertySource)source.getUnderlyingSource()).getName();
        }
        return source.getUnderlyingSource().toString();
    }

    private /* synthetic */ void lambda$getMatchingProperties$2(List candidates, MultiValueMap result, String name, ConfigurationPropertySource source) {
        candidates.forEach(metadata -> {
            ConfigurationProperty configurationProperty = source.getConfigurationProperty(ConfigurationPropertyName.of((CharSequence)metadata.getId()));
            if (configurationProperty != null) {
                result.add((Object)name, (Object)new PropertyMigration(configurationProperty, (ConfigurationMetadataProperty)metadata, this.determineReplacementMetadata((ConfigurationMetadataProperty)metadata)));
            }
        });
    }
}

