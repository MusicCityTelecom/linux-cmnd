/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.springframework.core.log.LogMessage
 *  org.springframework.util.ObjectUtils
 */
package org.springframework.boot.context.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.apache.commons.logging.Log;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.context.config.ConfigData;
import org.springframework.boot.context.config.ConfigDataActivationContext;
import org.springframework.boot.context.config.ConfigDataEnvironmentContributor;
import org.springframework.boot.context.config.ConfigDataEnvironmentContributorPlaceholdersResolver;
import org.springframework.boot.context.config.ConfigDataImporter;
import org.springframework.boot.context.config.ConfigDataLoaderContext;
import org.springframework.boot.context.config.ConfigDataLocation;
import org.springframework.boot.context.config.ConfigDataLocationResolverContext;
import org.springframework.boot.context.config.ConfigDataResolutionResult;
import org.springframework.boot.context.config.ConfigDataResource;
import org.springframework.boot.context.config.InactiveConfigDataAccessException;
import org.springframework.boot.context.properties.bind.BindContext;
import org.springframework.boot.context.properties.bind.BindHandler;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.logging.DeferredLogFactory;
import org.springframework.core.log.LogMessage;
import org.springframework.util.ObjectUtils;

class ConfigDataEnvironmentContributors
implements Iterable<ConfigDataEnvironmentContributor> {
    private static final Predicate<ConfigDataEnvironmentContributor> NO_CONTRIBUTOR_FILTER = contributor -> true;
    private final Log logger;
    private final ConfigDataEnvironmentContributor root;
    private final ConfigurableBootstrapContext bootstrapContext;

    ConfigDataEnvironmentContributors(DeferredLogFactory logFactory, ConfigurableBootstrapContext bootstrapContext, List<ConfigDataEnvironmentContributor> contributors) {
        this.logger = logFactory.getLog(this.getClass());
        this.bootstrapContext = bootstrapContext;
        this.root = ConfigDataEnvironmentContributor.of(contributors);
    }

    private ConfigDataEnvironmentContributors(Log logger, ConfigurableBootstrapContext bootstrapContext, ConfigDataEnvironmentContributor root) {
        this.logger = logger;
        this.bootstrapContext = bootstrapContext;
        this.root = root;
    }

    ConfigDataEnvironmentContributors withProcessedImports(ConfigDataImporter importer, ConfigDataActivationContext activationContext) {
        ConfigDataEnvironmentContributor.ImportPhase importPhase = ConfigDataEnvironmentContributor.ImportPhase.get(activationContext);
        this.logger.trace((Object)LogMessage.format((String)"Processing imports for phase %s. %s", (Object)((Object)importPhase), (Object)(activationContext != null ? activationContext : "no activation context")));
        ConfigDataEnvironmentContributors result = this;
        int processed = 0;
        while (true) {
            ConfigDataEnvironmentContributor contributor;
            if ((contributor = this.getNextToProcess(result, activationContext, importPhase)) == null) {
                this.logger.trace((Object)LogMessage.format((String)"Processed imports for of %d contributors", (Object)processed));
                return result;
            }
            if (contributor.getKind() == ConfigDataEnvironmentContributor.Kind.UNBOUND_IMPORT) {
                ConfigDataEnvironmentContributor bound = contributor.withBoundProperties(result, activationContext);
                result = new ConfigDataEnvironmentContributors(this.logger, this.bootstrapContext, result.getRoot().withReplacement(contributor, bound));
                continue;
            }
            ContributorConfigDataLocationResolverContext locationResolverContext = new ContributorConfigDataLocationResolverContext(result, contributor, activationContext);
            ContributorDataLoaderContext loaderContext = new ContributorDataLoaderContext(this);
            List<ConfigDataLocation> imports = contributor.getImports();
            this.logger.trace((Object)LogMessage.format((String)"Processing imports %s", imports));
            Map<ConfigDataResolutionResult, ConfigData> imported = importer.resolveAndLoad(activationContext, locationResolverContext, loaderContext, imports);
            this.logger.trace((Object)LogMessage.of(() -> this.getImportedMessage(imported.keySet())));
            ConfigDataEnvironmentContributor contributorAndChildren = contributor.withChildren(importPhase, this.asContributors(imported));
            result = new ConfigDataEnvironmentContributors(this.logger, this.bootstrapContext, result.getRoot().withReplacement(contributor, contributorAndChildren));
            ++processed;
        }
    }

    private CharSequence getImportedMessage(Set<ConfigDataResolutionResult> results) {
        if (results.isEmpty()) {
            return "Nothing imported";
        }
        StringBuilder message = new StringBuilder();
        message.append("Imported " + results.size() + " resource" + (results.size() != 1 ? "s " : " "));
        message.append(results.stream().map(ConfigDataResolutionResult::getResource).collect(Collectors.toList()));
        return message;
    }

    protected final ConfigurableBootstrapContext getBootstrapContext() {
        return this.bootstrapContext;
    }

    private ConfigDataEnvironmentContributor getNextToProcess(ConfigDataEnvironmentContributors contributors, ConfigDataActivationContext activationContext, ConfigDataEnvironmentContributor.ImportPhase importPhase) {
        for (ConfigDataEnvironmentContributor contributor : contributors.getRoot()) {
            if (contributor.getKind() != ConfigDataEnvironmentContributor.Kind.UNBOUND_IMPORT && !this.isActiveWithUnprocessedImports(activationContext, importPhase, contributor)) continue;
            return contributor;
        }
        return null;
    }

    private boolean isActiveWithUnprocessedImports(ConfigDataActivationContext activationContext, ConfigDataEnvironmentContributor.ImportPhase importPhase, ConfigDataEnvironmentContributor contributor) {
        return contributor.isActive(activationContext) && contributor.hasUnprocessedImports(importPhase);
    }

    private List<ConfigDataEnvironmentContributor> asContributors(Map<ConfigDataResolutionResult, ConfigData> imported) {
        ArrayList contributors = new ArrayList(imported.size() * 5);
        imported.forEach((? super K resolutionResult, ? super V data) -> {
            ConfigDataLocation location = resolutionResult.getLocation();
            ConfigDataResource resource = resolutionResult.getResource();
            boolean profileSpecific = resolutionResult.isProfileSpecific();
            if (data.getPropertySources().isEmpty()) {
                contributors.add(ConfigDataEnvironmentContributor.ofEmptyLocation(location, profileSpecific));
            } else {
                for (int i = data.getPropertySources().size() - 1; i >= 0; --i) {
                    contributors.add(ConfigDataEnvironmentContributor.ofUnboundImport(location, resource, profileSpecific, data, i));
                }
            }
        });
        return Collections.unmodifiableList(contributors);
    }

    ConfigDataEnvironmentContributor getRoot() {
        return this.root;
    }

    Binder getBinder(ConfigDataActivationContext activationContext, BinderOption ... options) {
        return this.getBinder(activationContext, NO_CONTRIBUTOR_FILTER, options);
    }

    Binder getBinder(ConfigDataActivationContext activationContext, Predicate<ConfigDataEnvironmentContributor> filter, BinderOption ... options) {
        return this.getBinder(activationContext, filter, this.asBinderOptionsSet(options));
    }

    private Set<BinderOption> asBinderOptionsSet(BinderOption ... options) {
        return ObjectUtils.isEmpty((Object[])options) ? EnumSet.noneOf(BinderOption.class) : EnumSet.copyOf(Arrays.asList(options));
    }

    private Binder getBinder(ConfigDataActivationContext activationContext, Predicate<ConfigDataEnvironmentContributor> filter, Set<BinderOption> options) {
        boolean failOnInactiveSource = options.contains((Object)BinderOption.FAIL_ON_BIND_TO_INACTIVE_SOURCE);
        Iterable<ConfigurationPropertySource> sources = () -> this.getBinderSources(filter.and(contributor -> failOnInactiveSource || contributor.isActive(activationContext)));
        ConfigDataEnvironmentContributorPlaceholdersResolver placeholdersResolver = new ConfigDataEnvironmentContributorPlaceholdersResolver(this.root, activationContext, null, failOnInactiveSource);
        InactiveSourceChecker bindHandler = !failOnInactiveSource ? null : new InactiveSourceChecker(activationContext);
        return new Binder(sources, placeholdersResolver, null, null, bindHandler);
    }

    private Iterator<ConfigurationPropertySource> getBinderSources(Predicate<ConfigDataEnvironmentContributor> filter) {
        return this.root.stream().filter(this::hasConfigurationPropertySource).filter(filter).map(ConfigDataEnvironmentContributor::getConfigurationPropertySource).iterator();
    }

    private boolean hasConfigurationPropertySource(ConfigDataEnvironmentContributor contributor) {
        return contributor.getConfigurationPropertySource() != null;
    }

    @Override
    public Iterator<ConfigDataEnvironmentContributor> iterator() {
        return this.root.iterator();
    }

    static enum BinderOption {
        FAIL_ON_BIND_TO_INACTIVE_SOURCE;

    }

    private class InactiveSourceChecker
    implements BindHandler {
        private final ConfigDataActivationContext activationContext;

        InactiveSourceChecker(ConfigDataActivationContext activationContext) {
            this.activationContext = activationContext;
        }

        @Override
        public Object onSuccess(ConfigurationPropertyName name, Bindable<?> target, BindContext context, Object result) {
            for (ConfigDataEnvironmentContributor contributor : ConfigDataEnvironmentContributors.this) {
                if (contributor.isActive(this.activationContext)) continue;
                InactiveConfigDataAccessException.throwIfPropertyFound(contributor, name);
            }
            return result;
        }
    }

    private static class ContributorConfigDataLocationResolverContext
    implements ConfigDataLocationResolverContext {
        private final ConfigDataEnvironmentContributors contributors;
        private final ConfigDataEnvironmentContributor contributor;
        private final ConfigDataActivationContext activationContext;
        private volatile Binder binder;

        ContributorConfigDataLocationResolverContext(ConfigDataEnvironmentContributors contributors, ConfigDataEnvironmentContributor contributor, ConfigDataActivationContext activationContext) {
            this.contributors = contributors;
            this.contributor = contributor;
            this.activationContext = activationContext;
        }

        @Override
        public Binder getBinder() {
            Binder binder = this.binder;
            if (binder == null) {
                this.binder = binder = this.contributors.getBinder(this.activationContext, new BinderOption[0]);
            }
            return binder;
        }

        @Override
        public ConfigDataResource getParent() {
            return this.contributor.getResource();
        }

        @Override
        public ConfigurableBootstrapContext getBootstrapContext() {
            return this.contributors.getBootstrapContext();
        }
    }

    private static class ContributorDataLoaderContext
    implements ConfigDataLoaderContext {
        private final ConfigDataEnvironmentContributors contributors;

        ContributorDataLoaderContext(ConfigDataEnvironmentContributors contributors) {
            this.contributors = contributors;
        }

        @Override
        public ConfigurableBootstrapContext getBootstrapContext() {
            return this.contributors.getBootstrapContext();
        }
    }
}

