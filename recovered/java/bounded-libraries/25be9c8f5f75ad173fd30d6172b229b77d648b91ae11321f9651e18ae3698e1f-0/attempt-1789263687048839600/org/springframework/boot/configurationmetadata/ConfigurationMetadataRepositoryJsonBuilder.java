/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.configurationmetadata;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.boot.configurationmetadata.ConfigurationMetadataHint;
import org.springframework.boot.configurationmetadata.ConfigurationMetadataItem;
import org.springframework.boot.configurationmetadata.ConfigurationMetadataProperty;
import org.springframework.boot.configurationmetadata.ConfigurationMetadataRepository;
import org.springframework.boot.configurationmetadata.ConfigurationMetadataSource;
import org.springframework.boot.configurationmetadata.JsonReader;
import org.springframework.boot.configurationmetadata.RawConfigurationMetadata;
import org.springframework.boot.configurationmetadata.SimpleConfigurationMetadataRepository;

public final class ConfigurationMetadataRepositoryJsonBuilder {
    private Charset defaultCharset = StandardCharsets.UTF_8;
    private final JsonReader reader = new JsonReader();
    private final List<SimpleConfigurationMetadataRepository> repositories = new ArrayList<SimpleConfigurationMetadataRepository>();

    private ConfigurationMetadataRepositoryJsonBuilder(Charset defaultCharset) {
        this.defaultCharset = defaultCharset;
    }

    public ConfigurationMetadataRepositoryJsonBuilder withJsonResource(InputStream inputStream) throws IOException {
        return this.withJsonResource(inputStream, this.defaultCharset);
    }

    public ConfigurationMetadataRepositoryJsonBuilder withJsonResource(InputStream inputStream, Charset charset) throws IOException {
        if (inputStream == null) {
            throw new IllegalArgumentException("InputStream must not be null.");
        }
        this.repositories.add(this.add(inputStream, charset));
        return this;
    }

    public ConfigurationMetadataRepository build() {
        SimpleConfigurationMetadataRepository result = new SimpleConfigurationMetadataRepository();
        for (SimpleConfigurationMetadataRepository repository : this.repositories) {
            result.include(repository);
        }
        return result;
    }

    private SimpleConfigurationMetadataRepository add(InputStream in, Charset charset) {
        try {
            RawConfigurationMetadata metadata = this.reader.read(in, charset);
            return this.create(metadata);
        }
        catch (Exception ex) {
            throw new IllegalStateException("Failed to read configuration metadata", ex);
        }
    }

    private SimpleConfigurationMetadataRepository create(RawConfigurationMetadata metadata) {
        SimpleConfigurationMetadataRepository repository = new SimpleConfigurationMetadataRepository();
        repository.add(metadata.getSources());
        for (ConfigurationMetadataItem item : metadata.getItems()) {
            ConfigurationMetadataSource source = metadata.getSource(item);
            repository.add(item, source);
        }
        Map<String, ConfigurationMetadataProperty> allProperties = repository.getAllProperties();
        for (ConfigurationMetadataHint hint : metadata.getHints()) {
            ConfigurationMetadataProperty property = allProperties.get(hint.getId());
            if (property != null) {
                this.addValueHints(property, hint);
                continue;
            }
            String id = hint.resolveId();
            property = allProperties.get(id);
            if (property == null) continue;
            if (hint.isMapKeyHints()) {
                this.addMapHints(property, hint);
                continue;
            }
            this.addValueHints(property, hint);
        }
        return repository;
    }

    private void addValueHints(ConfigurationMetadataProperty property, ConfigurationMetadataHint hint) {
        property.getHints().getValueHints().addAll(hint.getValueHints());
        property.getHints().getValueProviders().addAll(hint.getValueProviders());
    }

    private void addMapHints(ConfigurationMetadataProperty property, ConfigurationMetadataHint hint) {
        property.getHints().getKeyHints().addAll(hint.getValueHints());
        property.getHints().getKeyProviders().addAll(hint.getValueProviders());
    }

    public static ConfigurationMetadataRepositoryJsonBuilder create(InputStream ... inputStreams) throws IOException {
        ConfigurationMetadataRepositoryJsonBuilder builder = ConfigurationMetadataRepositoryJsonBuilder.create();
        for (InputStream inputStream : inputStreams) {
            builder = builder.withJsonResource(inputStream);
        }
        return builder;
    }

    public static ConfigurationMetadataRepositoryJsonBuilder create() {
        return ConfigurationMetadataRepositoryJsonBuilder.create(StandardCharsets.UTF_8);
    }

    public static ConfigurationMetadataRepositoryJsonBuilder create(Charset defaultCharset) {
        return new ConfigurationMetadataRepositoryJsonBuilder(defaultCharset);
    }
}

