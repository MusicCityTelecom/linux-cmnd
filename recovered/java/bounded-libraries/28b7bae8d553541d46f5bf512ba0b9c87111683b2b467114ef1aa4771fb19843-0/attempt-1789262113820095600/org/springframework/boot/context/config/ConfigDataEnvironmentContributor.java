/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.env.PropertySource
 *  org.springframework.util.CollectionUtils
 */
package org.springframework.boot.context.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.springframework.boot.context.config.ConfigData;
import org.springframework.boot.context.config.ConfigDataActivationContext;
import org.springframework.boot.context.config.ConfigDataEnvironmentContributorPlaceholdersResolver;
import org.springframework.boot.context.config.ConfigDataLocation;
import org.springframework.boot.context.config.ConfigDataProperties;
import org.springframework.boot.context.config.ConfigDataResource;
import org.springframework.boot.context.config.UseLegacyConfigProcessingException;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.util.CollectionUtils;

class ConfigDataEnvironmentContributor
implements Iterable<ConfigDataEnvironmentContributor> {
    private static final ConfigData.Options EMPTY_LOCATION_OPTIONS = ConfigData.Options.of(ConfigData.Option.IGNORE_IMPORTS);
    private final ConfigDataLocation location;
    private final ConfigDataResource resource;
    private final boolean fromProfileSpecificImport;
    private final PropertySource<?> propertySource;
    private final ConfigurationPropertySource configurationPropertySource;
    private final ConfigDataProperties properties;
    private final ConfigData.Options configDataOptions;
    private final Map<ImportPhase, List<ConfigDataEnvironmentContributor>> children;
    private final Kind kind;

    ConfigDataEnvironmentContributor(Kind kind, ConfigDataLocation location, ConfigDataResource resource, boolean fromProfileSpecificImport, PropertySource<?> propertySource, ConfigurationPropertySource configurationPropertySource, ConfigDataProperties properties, ConfigData.Options configDataOptions, Map<ImportPhase, List<ConfigDataEnvironmentContributor>> children) {
        this.kind = kind;
        this.location = location;
        this.resource = resource;
        this.fromProfileSpecificImport = fromProfileSpecificImport;
        this.properties = properties;
        this.propertySource = propertySource;
        this.configurationPropertySource = configurationPropertySource;
        this.configDataOptions = configDataOptions != null ? configDataOptions : ConfigData.Options.NONE;
        this.children = children != null ? children : Collections.emptyMap();
    }

    Kind getKind() {
        return this.kind;
    }

    ConfigDataLocation getLocation() {
        return this.location;
    }

    boolean isActive(ConfigDataActivationContext activationContext) {
        if (this.kind == Kind.UNBOUND_IMPORT) {
            return false;
        }
        return this.properties == null || this.properties.isActive(activationContext);
    }

    ConfigDataResource getResource() {
        return this.resource;
    }

    boolean isFromProfileSpecificImport() {
        return this.fromProfileSpecificImport;
    }

    PropertySource<?> getPropertySource() {
        return this.propertySource;
    }

    ConfigurationPropertySource getConfigurationPropertySource() {
        return this.configurationPropertySource;
    }

    boolean hasConfigDataOption(ConfigData.Option option) {
        return this.configDataOptions.contains(option);
    }

    ConfigDataEnvironmentContributor withoutConfigDataOption(ConfigData.Option option) {
        return new ConfigDataEnvironmentContributor(this.kind, this.location, this.resource, this.fromProfileSpecificImport, this.propertySource, this.configurationPropertySource, this.properties, this.configDataOptions.without(option), this.children);
    }

    List<ConfigDataLocation> getImports() {
        return this.properties != null ? this.properties.getImports() : Collections.emptyList();
    }

    boolean hasUnprocessedImports(ImportPhase importPhase) {
        if (this.getImports().isEmpty()) {
            return false;
        }
        return !this.children.containsKey((Object)importPhase);
    }

    List<ConfigDataEnvironmentContributor> getChildren(ImportPhase importPhase) {
        return this.children.getOrDefault((Object)importPhase, Collections.emptyList());
    }

    Stream<ConfigDataEnvironmentContributor> stream() {
        return StreamSupport.stream(this.spliterator(), false);
    }

    @Override
    public Iterator<ConfigDataEnvironmentContributor> iterator() {
        return new ContributorIterator();
    }

    ConfigDataEnvironmentContributor withBoundProperties(Iterable<ConfigDataEnvironmentContributor> contributors, ConfigDataActivationContext activationContext) {
        Set<ConfigurationPropertySource> sources = Collections.singleton(this.getConfigurationPropertySource());
        ConfigDataEnvironmentContributorPlaceholdersResolver placeholdersResolver = new ConfigDataEnvironmentContributorPlaceholdersResolver(contributors, activationContext, this, true);
        Binder binder = new Binder(sources, placeholdersResolver, null, null, null);
        UseLegacyConfigProcessingException.throwIfRequested(binder);
        ConfigDataProperties properties = ConfigDataProperties.get(binder);
        if (properties != null && this.configDataOptions.contains(ConfigData.Option.IGNORE_IMPORTS)) {
            properties = properties.withoutImports();
        }
        return new ConfigDataEnvironmentContributor(Kind.BOUND_IMPORT, this.location, this.resource, this.fromProfileSpecificImport, this.propertySource, this.configurationPropertySource, properties, this.configDataOptions, null);
    }

    ConfigDataEnvironmentContributor withChildren(ImportPhase importPhase, List<ConfigDataEnvironmentContributor> children) {
        LinkedHashMap<ImportPhase, List<ConfigDataEnvironmentContributor>> updatedChildren = new LinkedHashMap<ImportPhase, List<ConfigDataEnvironmentContributor>>(this.children);
        updatedChildren.put(importPhase, children);
        if (importPhase == ImportPhase.AFTER_PROFILE_ACTIVATION) {
            this.moveProfileSpecific(updatedChildren);
        }
        return new ConfigDataEnvironmentContributor(this.kind, this.location, this.resource, this.fromProfileSpecificImport, this.propertySource, this.configurationPropertySource, this.properties, this.configDataOptions, updatedChildren);
    }

    private void moveProfileSpecific(Map<ImportPhase, List<ConfigDataEnvironmentContributor>> children) {
        List<ConfigDataEnvironmentContributor> before = children.get((Object)ImportPhase.BEFORE_PROFILE_ACTIVATION);
        if (!this.hasAnyProfileSpecificChildren(before)) {
            return;
        }
        ArrayList<ConfigDataEnvironmentContributor> updatedBefore = new ArrayList<ConfigDataEnvironmentContributor>(before.size());
        ArrayList<ConfigDataEnvironmentContributor> updatedAfter = new ArrayList<ConfigDataEnvironmentContributor>();
        for (ConfigDataEnvironmentContributor contributor : before) {
            updatedBefore.add(this.moveProfileSpecificChildren(contributor, updatedAfter));
        }
        updatedAfter.addAll(children.getOrDefault((Object)ImportPhase.AFTER_PROFILE_ACTIVATION, Collections.emptyList()));
        children.put(ImportPhase.BEFORE_PROFILE_ACTIVATION, updatedBefore);
        children.put(ImportPhase.AFTER_PROFILE_ACTIVATION, updatedAfter);
    }

    private ConfigDataEnvironmentContributor moveProfileSpecificChildren(ConfigDataEnvironmentContributor contributor, List<ConfigDataEnvironmentContributor> removed) {
        for (ImportPhase importPhase : ImportPhase.values()) {
            List<ConfigDataEnvironmentContributor> children = contributor.getChildren(importPhase);
            ArrayList<ConfigDataEnvironmentContributor> updatedChildren = new ArrayList<ConfigDataEnvironmentContributor>(children.size());
            for (ConfigDataEnvironmentContributor child : children) {
                if (child.hasConfigDataOption(ConfigData.Option.PROFILE_SPECIFIC)) {
                    removed.add(child.withoutConfigDataOption(ConfigData.Option.PROFILE_SPECIFIC));
                    continue;
                }
                updatedChildren.add(child);
            }
            contributor = contributor.withChildren(importPhase, updatedChildren);
        }
        return contributor;
    }

    private boolean hasAnyProfileSpecificChildren(List<ConfigDataEnvironmentContributor> contributors) {
        if (CollectionUtils.isEmpty(contributors)) {
            return false;
        }
        for (ConfigDataEnvironmentContributor contributor : contributors) {
            for (ImportPhase importPhase : ImportPhase.values()) {
                if (!contributor.getChildren(importPhase).stream().anyMatch(child -> child.hasConfigDataOption(ConfigData.Option.PROFILE_SPECIFIC))) continue;
                return true;
            }
        }
        return false;
    }

    ConfigDataEnvironmentContributor withReplacement(ConfigDataEnvironmentContributor existing, ConfigDataEnvironmentContributor replacement) {
        if (this == existing) {
            return replacement;
        }
        LinkedHashMap<ImportPhase, List<ConfigDataEnvironmentContributor>> updatedChildren = new LinkedHashMap<ImportPhase, List<ConfigDataEnvironmentContributor>>(this.children.size());
        this.children.forEach((? super K importPhase, ? super V contributors) -> {
            ArrayList<ConfigDataEnvironmentContributor> updatedContributors = new ArrayList<ConfigDataEnvironmentContributor>(contributors.size());
            for (ConfigDataEnvironmentContributor contributor : contributors) {
                updatedContributors.add(contributor.withReplacement(existing, replacement));
            }
            updatedChildren.put((ImportPhase)((Object)importPhase), Collections.unmodifiableList(updatedContributors));
        });
        return new ConfigDataEnvironmentContributor(this.kind, this.location, this.resource, this.fromProfileSpecificImport, this.propertySource, this.configurationPropertySource, this.properties, this.configDataOptions, updatedChildren);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        this.buildToString("", builder);
        return builder.toString();
    }

    private void buildToString(String prefix, StringBuilder builder) {
        builder.append(prefix);
        builder.append((Object)this.kind);
        builder.append(" ");
        builder.append(this.location);
        builder.append(" ");
        builder.append(this.resource);
        builder.append(" ");
        builder.append(this.configDataOptions);
        builder.append("\n");
        for (ConfigDataEnvironmentContributor child : this.children.getOrDefault((Object)ImportPhase.BEFORE_PROFILE_ACTIVATION, Collections.emptyList())) {
            child.buildToString(prefix + "    ", builder);
        }
        for (ConfigDataEnvironmentContributor child : this.children.getOrDefault((Object)ImportPhase.AFTER_PROFILE_ACTIVATION, Collections.emptyList())) {
            child.buildToString(prefix + "    ", builder);
        }
    }

    static ConfigDataEnvironmentContributor of(List<ConfigDataEnvironmentContributor> contributors) {
        LinkedHashMap<ImportPhase, List<ConfigDataEnvironmentContributor>> children = new LinkedHashMap<ImportPhase, List<ConfigDataEnvironmentContributor>>();
        children.put(ImportPhase.BEFORE_PROFILE_ACTIVATION, Collections.unmodifiableList(contributors));
        return new ConfigDataEnvironmentContributor(Kind.ROOT, null, null, false, null, null, null, null, children);
    }

    static ConfigDataEnvironmentContributor ofInitialImport(ConfigDataLocation initialImport) {
        List<ConfigDataLocation> imports = Collections.singletonList(initialImport);
        ConfigDataProperties properties = new ConfigDataProperties(imports, null);
        return new ConfigDataEnvironmentContributor(Kind.INITIAL_IMPORT, null, null, false, null, null, properties, null, null);
    }

    static ConfigDataEnvironmentContributor ofExisting(PropertySource<?> propertySource) {
        return new ConfigDataEnvironmentContributor(Kind.EXISTING, null, null, false, propertySource, ConfigurationPropertySource.from(propertySource), null, null, null);
    }

    static ConfigDataEnvironmentContributor ofUnboundImport(ConfigDataLocation location, ConfigDataResource resource, boolean profileSpecific, ConfigData configData, int propertySourceIndex) {
        PropertySource<?> propertySource = configData.getPropertySources().get(propertySourceIndex);
        ConfigData.Options options = configData.getOptions(propertySource);
        ConfigurationPropertySource configurationPropertySource = ConfigurationPropertySource.from(propertySource);
        return new ConfigDataEnvironmentContributor(Kind.UNBOUND_IMPORT, location, resource, profileSpecific, propertySource, configurationPropertySource, null, options, null);
    }

    static ConfigDataEnvironmentContributor ofEmptyLocation(ConfigDataLocation location, boolean profileSpecific) {
        return new ConfigDataEnvironmentContributor(Kind.EMPTY_LOCATION, location, null, profileSpecific, null, null, null, EMPTY_LOCATION_OPTIONS, null);
    }

    private final class ContributorIterator
    implements Iterator<ConfigDataEnvironmentContributor> {
        private ImportPhase phase = ImportPhase.AFTER_PROFILE_ACTIVATION;
        private Iterator<ConfigDataEnvironmentContributor> children;
        private Iterator<ConfigDataEnvironmentContributor> current;
        private ConfigDataEnvironmentContributor next;

        private ContributorIterator() {
            this.children = ConfigDataEnvironmentContributor.this.getChildren(this.phase).iterator();
            this.current = Collections.emptyIterator();
        }

        @Override
        public boolean hasNext() {
            return this.fetchIfNecessary() != null;
        }

        @Override
        public ConfigDataEnvironmentContributor next() {
            ConfigDataEnvironmentContributor next = this.fetchIfNecessary();
            if (next == null) {
                throw new NoSuchElementException();
            }
            this.next = null;
            return next;
        }

        private ConfigDataEnvironmentContributor fetchIfNecessary() {
            if (this.next != null) {
                return this.next;
            }
            if (this.current.hasNext()) {
                this.next = this.current.next();
                return this.next;
            }
            if (this.children.hasNext()) {
                this.current = this.children.next().iterator();
                return this.fetchIfNecessary();
            }
            if (this.phase == ImportPhase.AFTER_PROFILE_ACTIVATION) {
                this.phase = ImportPhase.BEFORE_PROFILE_ACTIVATION;
                this.children = ConfigDataEnvironmentContributor.this.getChildren(this.phase).iterator();
                return this.fetchIfNecessary();
            }
            if (this.phase == ImportPhase.BEFORE_PROFILE_ACTIVATION) {
                this.phase = null;
                this.next = ConfigDataEnvironmentContributor.this;
                return this.next;
            }
            return null;
        }
    }

    static enum ImportPhase {
        BEFORE_PROFILE_ACTIVATION,
        AFTER_PROFILE_ACTIVATION;


        static ImportPhase get(ConfigDataActivationContext activationContext) {
            if (activationContext != null && activationContext.getProfiles() != null) {
                return AFTER_PROFILE_ACTIVATION;
            }
            return BEFORE_PROFILE_ACTIVATION;
        }
    }

    static enum Kind {
        ROOT,
        INITIAL_IMPORT,
        EXISTING,
        UNBOUND_IMPORT,
        BOUND_IMPORT,
        EMPTY_LOCATION;

    }
}

