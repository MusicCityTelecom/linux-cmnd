/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.springframework.core.env.ConfigurableEnvironment
 *  org.springframework.core.env.Environment
 *  org.springframework.core.env.MutablePropertySources
 *  org.springframework.core.env.PropertySource
 *  org.springframework.core.io.ResourceLoader
 *  org.springframework.core.log.LogMessage
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.context.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.springframework.boot.BootstrapRegistry;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.DefaultPropertiesPropertySource;
import org.springframework.boot.context.config.ConfigData;
import org.springframework.boot.context.config.ConfigDataActivationContext;
import org.springframework.boot.context.config.ConfigDataEnvironmentContributor;
import org.springframework.boot.context.config.ConfigDataEnvironmentContributorPlaceholdersResolver;
import org.springframework.boot.context.config.ConfigDataEnvironmentContributors;
import org.springframework.boot.context.config.ConfigDataEnvironmentUpdateListener;
import org.springframework.boot.context.config.ConfigDataImporter;
import org.springframework.boot.context.config.ConfigDataLoaders;
import org.springframework.boot.context.config.ConfigDataLocation;
import org.springframework.boot.context.config.ConfigDataLocationNotFoundException;
import org.springframework.boot.context.config.ConfigDataLocationResolvers;
import org.springframework.boot.context.config.ConfigDataNotFoundAction;
import org.springframework.boot.context.config.InactiveConfigDataAccessException;
import org.springframework.boot.context.config.InvalidConfigDataPropertyException;
import org.springframework.boot.context.config.Profiles;
import org.springframework.boot.context.config.UseLegacyConfigProcessingException;
import org.springframework.boot.context.properties.bind.BindException;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.logging.DeferredLogFactory;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.log.LogMessage;
import org.springframework.util.StringUtils;

class ConfigDataEnvironment {
    static final String LOCATION_PROPERTY = "spring.config.location";
    static final String ADDITIONAL_LOCATION_PROPERTY = "spring.config.additional-location";
    static final String IMPORT_PROPERTY = "spring.config.import";
    static final String ON_NOT_FOUND_PROPERTY = "spring.config.on-not-found";
    static final ConfigDataLocation[] DEFAULT_SEARCH_LOCATIONS;
    private static final ConfigDataLocation[] EMPTY_LOCATIONS;
    private static final Bindable<ConfigDataLocation[]> CONFIG_DATA_LOCATION_ARRAY;
    private static final Bindable<List<String>> STRING_LIST;
    private static final ConfigDataEnvironmentContributors.BinderOption[] ALLOW_INACTIVE_BINDING;
    private static final ConfigDataEnvironmentContributors.BinderOption[] DENY_INACTIVE_BINDING;
    private final DeferredLogFactory logFactory;
    private final Log logger;
    private final ConfigDataNotFoundAction notFoundAction;
    private final ConfigurableBootstrapContext bootstrapContext;
    private final ConfigurableEnvironment environment;
    private final ConfigDataLocationResolvers resolvers;
    private final Collection<String> additionalProfiles;
    private final ConfigDataEnvironmentUpdateListener environmentUpdateListener;
    private final ConfigDataLoaders loaders;
    private final ConfigDataEnvironmentContributors contributors;

    ConfigDataEnvironment(DeferredLogFactory logFactory, ConfigurableBootstrapContext bootstrapContext, ConfigurableEnvironment environment, ResourceLoader resourceLoader, Collection<String> additionalProfiles, ConfigDataEnvironmentUpdateListener environmentUpdateListener) {
        Binder binder = Binder.get((Environment)environment);
        UseLegacyConfigProcessingException.throwIfRequested(binder);
        this.logFactory = logFactory;
        this.logger = logFactory.getLog(this.getClass());
        this.notFoundAction = binder.bind(ON_NOT_FOUND_PROPERTY, ConfigDataNotFoundAction.class).orElse(ConfigDataNotFoundAction.FAIL);
        this.bootstrapContext = bootstrapContext;
        this.environment = environment;
        this.resolvers = this.createConfigDataLocationResolvers(logFactory, bootstrapContext, binder, resourceLoader);
        this.additionalProfiles = additionalProfiles;
        this.environmentUpdateListener = environmentUpdateListener != null ? environmentUpdateListener : ConfigDataEnvironmentUpdateListener.NONE;
        this.loaders = new ConfigDataLoaders(logFactory, bootstrapContext, resourceLoader.getClassLoader());
        this.contributors = this.createContributors(binder);
    }

    protected ConfigDataLocationResolvers createConfigDataLocationResolvers(DeferredLogFactory logFactory, ConfigurableBootstrapContext bootstrapContext, Binder binder, ResourceLoader resourceLoader) {
        return new ConfigDataLocationResolvers(logFactory, bootstrapContext, binder, resourceLoader);
    }

    private ConfigDataEnvironmentContributors createContributors(Binder binder) {
        this.logger.trace((Object)"Building config data environment contributors");
        MutablePropertySources propertySources = this.environment.getPropertySources();
        ArrayList<ConfigDataEnvironmentContributor> contributors = new ArrayList<ConfigDataEnvironmentContributor>(propertySources.size() + 10);
        PropertySource defaultPropertySource = null;
        for (PropertySource propertySource : propertySources) {
            if (DefaultPropertiesPropertySource.hasMatchingName(propertySource)) {
                defaultPropertySource = propertySource;
                continue;
            }
            this.logger.trace((Object)LogMessage.format((String)"Creating wrapped config data contributor for '%s'", (Object)propertySource.getName()));
            contributors.add(ConfigDataEnvironmentContributor.ofExisting(propertySource));
        }
        contributors.addAll(this.getInitialImportContributors(binder));
        if (defaultPropertySource != null) {
            this.logger.trace((Object)"Creating wrapped config data contributor for default property source");
            contributors.add(ConfigDataEnvironmentContributor.ofExisting(defaultPropertySource));
        }
        return this.createContributors(contributors);
    }

    protected ConfigDataEnvironmentContributors createContributors(List<ConfigDataEnvironmentContributor> contributors) {
        return new ConfigDataEnvironmentContributors(this.logFactory, this.bootstrapContext, contributors);
    }

    ConfigDataEnvironmentContributors getContributors() {
        return this.contributors;
    }

    private List<ConfigDataEnvironmentContributor> getInitialImportContributors(Binder binder) {
        ArrayList<ConfigDataEnvironmentContributor> initialContributors = new ArrayList<ConfigDataEnvironmentContributor>();
        this.addInitialImportContributors(initialContributors, this.bindLocations(binder, IMPORT_PROPERTY, EMPTY_LOCATIONS));
        this.addInitialImportContributors(initialContributors, this.bindLocations(binder, ADDITIONAL_LOCATION_PROPERTY, EMPTY_LOCATIONS));
        this.addInitialImportContributors(initialContributors, this.bindLocations(binder, LOCATION_PROPERTY, DEFAULT_SEARCH_LOCATIONS));
        return initialContributors;
    }

    private ConfigDataLocation[] bindLocations(Binder binder, String propertyName, ConfigDataLocation[] other) {
        return binder.bind(propertyName, CONFIG_DATA_LOCATION_ARRAY).orElse(other);
    }

    private void addInitialImportContributors(List<ConfigDataEnvironmentContributor> initialContributors, ConfigDataLocation[] locations) {
        for (int i = locations.length - 1; i >= 0; --i) {
            initialContributors.add(this.createInitialImportContributor(locations[i]));
        }
    }

    private ConfigDataEnvironmentContributor createInitialImportContributor(ConfigDataLocation location) {
        this.logger.trace((Object)LogMessage.format((String)"Adding initial config data import from location '%s'", (Object)location));
        return ConfigDataEnvironmentContributor.ofInitialImport(location);
    }

    void processAndApply() {
        ConfigDataImporter importer = new ConfigDataImporter(this.logFactory, this.notFoundAction, this.resolvers, this.loaders);
        this.registerBootstrapBinder(this.contributors, null, DENY_INACTIVE_BINDING);
        ConfigDataEnvironmentContributors contributors = this.processInitial(this.contributors, importer);
        ConfigDataActivationContext activationContext = this.createActivationContext(contributors.getBinder(null, ConfigDataEnvironmentContributors.BinderOption.FAIL_ON_BIND_TO_INACTIVE_SOURCE));
        contributors = this.processWithoutProfiles(contributors, importer, activationContext);
        activationContext = this.withProfiles(contributors, activationContext);
        contributors = this.processWithProfiles(contributors, importer, activationContext);
        this.applyToEnvironment(contributors, activationContext, importer.getLoadedLocations(), importer.getOptionalLocations());
    }

    private ConfigDataEnvironmentContributors processInitial(ConfigDataEnvironmentContributors contributors, ConfigDataImporter importer) {
        this.logger.trace((Object)"Processing initial config data environment contributors without activation context");
        contributors = contributors.withProcessedImports(importer, null);
        this.registerBootstrapBinder(contributors, null, DENY_INACTIVE_BINDING);
        return contributors;
    }

    private ConfigDataActivationContext createActivationContext(Binder initialBinder) {
        this.logger.trace((Object)"Creating config data activation context from initial contributions");
        try {
            return new ConfigDataActivationContext((Environment)this.environment, initialBinder);
        }
        catch (BindException ex) {
            if (ex.getCause() instanceof InactiveConfigDataAccessException) {
                throw (InactiveConfigDataAccessException)ex.getCause();
            }
            throw ex;
        }
    }

    private ConfigDataEnvironmentContributors processWithoutProfiles(ConfigDataEnvironmentContributors contributors, ConfigDataImporter importer, ConfigDataActivationContext activationContext) {
        this.logger.trace((Object)"Processing config data environment contributors with initial activation context");
        contributors = contributors.withProcessedImports(importer, activationContext);
        this.registerBootstrapBinder(contributors, activationContext, DENY_INACTIVE_BINDING);
        return contributors;
    }

    private ConfigDataActivationContext withProfiles(ConfigDataEnvironmentContributors contributors, ConfigDataActivationContext activationContext) {
        this.logger.trace((Object)"Deducing profiles from current config data environment contributors");
        Binder binder = contributors.getBinder(activationContext, contributor -> !contributor.hasConfigDataOption(ConfigData.Option.IGNORE_PROFILES), ConfigDataEnvironmentContributors.BinderOption.FAIL_ON_BIND_TO_INACTIVE_SOURCE);
        try {
            LinkedHashSet<String> additionalProfiles = new LinkedHashSet<String>(this.additionalProfiles);
            additionalProfiles.addAll(this.getIncludedProfiles(contributors, activationContext));
            Profiles profiles = new Profiles((Environment)this.environment, binder, additionalProfiles);
            return activationContext.withProfiles(profiles);
        }
        catch (BindException ex) {
            if (ex.getCause() instanceof InactiveConfigDataAccessException) {
                throw (InactiveConfigDataAccessException)ex.getCause();
            }
            throw ex;
        }
    }

    private Collection<? extends String> getIncludedProfiles(ConfigDataEnvironmentContributors contributors, ConfigDataActivationContext activationContext) {
        ConfigDataEnvironmentContributorPlaceholdersResolver placeholdersResolver = new ConfigDataEnvironmentContributorPlaceholdersResolver(contributors, activationContext, null, true);
        LinkedHashSet result = new LinkedHashSet();
        for (ConfigDataEnvironmentContributor contributor : contributors) {
            ConfigurationPropertySource source = contributor.getConfigurationPropertySource();
            if (source == null || contributor.hasConfigDataOption(ConfigData.Option.IGNORE_PROFILES)) continue;
            Binder binder = new Binder(Collections.singleton(source), placeholdersResolver);
            binder.bind(Profiles.INCLUDE_PROFILES, STRING_LIST).ifBound(includes -> {
                if (!contributor.isActive(activationContext)) {
                    InactiveConfigDataAccessException.throwIfPropertyFound(contributor, Profiles.INCLUDE_PROFILES);
                    InactiveConfigDataAccessException.throwIfPropertyFound(contributor, Profiles.INCLUDE_PROFILES.append("[0]"));
                }
                result.addAll(includes);
            });
        }
        return result;
    }

    private ConfigDataEnvironmentContributors processWithProfiles(ConfigDataEnvironmentContributors contributors, ConfigDataImporter importer, ConfigDataActivationContext activationContext) {
        this.logger.trace((Object)"Processing config data environment contributors with profile activation context");
        contributors = contributors.withProcessedImports(importer, activationContext);
        this.registerBootstrapBinder(contributors, activationContext, ALLOW_INACTIVE_BINDING);
        return contributors;
    }

    private void registerBootstrapBinder(ConfigDataEnvironmentContributors contributors, ConfigDataActivationContext activationContext, ConfigDataEnvironmentContributors.BinderOption ... binderOptions) {
        this.bootstrapContext.register(Binder.class, BootstrapRegistry.InstanceSupplier.from(() -> contributors.getBinder(activationContext, binderOptions)).withScope(BootstrapRegistry.Scope.PROTOTYPE));
    }

    private void applyToEnvironment(ConfigDataEnvironmentContributors contributors, ConfigDataActivationContext activationContext, Set<ConfigDataLocation> loadedLocations, Set<ConfigDataLocation> optionalLocations) {
        this.checkForInvalidProperties(contributors);
        this.checkMandatoryLocations(contributors, activationContext, loadedLocations, optionalLocations);
        MutablePropertySources propertySources = this.environment.getPropertySources();
        this.applyContributor(contributors, activationContext, propertySources);
        DefaultPropertiesPropertySource.moveToEnd(propertySources);
        Profiles profiles = activationContext.getProfiles();
        this.logger.trace((Object)LogMessage.format((String)"Setting default profiles: %s", profiles.getDefault()));
        this.environment.setDefaultProfiles(StringUtils.toStringArray(profiles.getDefault()));
        this.logger.trace((Object)LogMessage.format((String)"Setting active profiles: %s", profiles.getActive()));
        this.environment.setActiveProfiles(StringUtils.toStringArray(profiles.getActive()));
        this.environmentUpdateListener.onSetProfiles(profiles);
    }

    private void applyContributor(ConfigDataEnvironmentContributors contributors, ConfigDataActivationContext activationContext, MutablePropertySources propertySources) {
        this.logger.trace((Object)"Applying config data environment contributions");
        for (ConfigDataEnvironmentContributor contributor : contributors) {
            PropertySource<?> propertySource = contributor.getPropertySource();
            if (contributor.getKind() != ConfigDataEnvironmentContributor.Kind.BOUND_IMPORT || propertySource == null) continue;
            if (!contributor.isActive(activationContext)) {
                this.logger.trace((Object)LogMessage.format((String)"Skipping inactive property source '%s'", (Object)propertySource.getName()));
                continue;
            }
            this.logger.trace((Object)LogMessage.format((String)"Adding imported property source '%s'", (Object)propertySource.getName()));
            propertySources.addLast(propertySource);
            this.environmentUpdateListener.onPropertySourceAdded(propertySource, contributor.getLocation(), contributor.getResource());
        }
    }

    private void checkForInvalidProperties(ConfigDataEnvironmentContributors contributors) {
        for (ConfigDataEnvironmentContributor contributor : contributors) {
            InvalidConfigDataPropertyException.throwOrWarn(this.logger, contributor);
        }
    }

    private void checkMandatoryLocations(ConfigDataEnvironmentContributors contributors, ConfigDataActivationContext activationContext, Set<ConfigDataLocation> loadedLocations, Set<ConfigDataLocation> optionalLocations) {
        LinkedHashSet<ConfigDataLocation> mandatoryLocations = new LinkedHashSet<ConfigDataLocation>();
        for (ConfigDataEnvironmentContributor contributor : contributors) {
            if (!contributor.isActive(activationContext)) continue;
            mandatoryLocations.addAll(this.getMandatoryImports(contributor));
        }
        for (ConfigDataEnvironmentContributor contributor : contributors) {
            if (contributor.getLocation() == null) continue;
            mandatoryLocations.remove(contributor.getLocation());
        }
        mandatoryLocations.removeAll(loadedLocations);
        mandatoryLocations.removeAll(optionalLocations);
        if (!mandatoryLocations.isEmpty()) {
            for (ConfigDataLocation mandatoryLocation : mandatoryLocations) {
                this.notFoundAction.handle(this.logger, new ConfigDataLocationNotFoundException(mandatoryLocation));
            }
        }
    }

    private Set<ConfigDataLocation> getMandatoryImports(ConfigDataEnvironmentContributor contributor) {
        List<ConfigDataLocation> imports = contributor.getImports();
        LinkedHashSet<ConfigDataLocation> mandatoryLocations = new LinkedHashSet<ConfigDataLocation>(imports.size());
        for (ConfigDataLocation location : imports) {
            if (location.isOptional()) continue;
            mandatoryLocations.add(location);
        }
        return mandatoryLocations;
    }

    static {
        ArrayList<ConfigDataLocation> locations = new ArrayList<ConfigDataLocation>();
        locations.add(ConfigDataLocation.of("optional:classpath:/;optional:classpath:/config/"));
        locations.add(ConfigDataLocation.of("optional:file:./;optional:file:./config/;optional:file:./config/*/"));
        DEFAULT_SEARCH_LOCATIONS = locations.toArray(new ConfigDataLocation[0]);
        EMPTY_LOCATIONS = new ConfigDataLocation[0];
        CONFIG_DATA_LOCATION_ARRAY = Bindable.of(ConfigDataLocation[].class);
        STRING_LIST = Bindable.listOf(String.class);
        ALLOW_INACTIVE_BINDING = new ConfigDataEnvironmentContributors.BinderOption[0];
        DENY_INACTIVE_BINDING = new ConfigDataEnvironmentContributors.BinderOption[]{ConfigDataEnvironmentContributors.BinderOption.FAIL_ON_BIND_TO_INACTIVE_SOURCE};
    }
}

