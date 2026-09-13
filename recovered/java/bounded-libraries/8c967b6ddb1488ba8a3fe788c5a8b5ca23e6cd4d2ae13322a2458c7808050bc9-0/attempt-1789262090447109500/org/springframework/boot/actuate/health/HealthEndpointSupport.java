/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.boot.convert.DurationStyle
 *  org.springframework.core.log.LogMessage
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.actuate.health;

import java.time.Duration;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.actuate.endpoint.ApiVersion;
import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.boot.actuate.endpoint.web.WebServerNamespace;
import org.springframework.boot.actuate.health.AdditionalHealthEndpointPath;
import org.springframework.boot.actuate.health.CompositeHealth;
import org.springframework.boot.actuate.health.ContributorRegistry;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthComponent;
import org.springframework.boot.actuate.health.HealthEndpointGroup;
import org.springframework.boot.actuate.health.HealthEndpointGroups;
import org.springframework.boot.actuate.health.NamedContributor;
import org.springframework.boot.actuate.health.NamedContributors;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.actuate.health.StatusAggregator;
import org.springframework.boot.actuate.health.SystemHealth;
import org.springframework.boot.convert.DurationStyle;
import org.springframework.core.log.LogMessage;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

abstract class HealthEndpointSupport<C, T> {
    private static final Log logger = LogFactory.getLog(HealthEndpointSupport.class);
    static final Health DEFAULT_HEALTH = Health.up().build();
    private final ContributorRegistry<C> registry;
    private final HealthEndpointGroups groups;
    private final Duration slowIndicatorLoggingThreshold;

    HealthEndpointSupport(ContributorRegistry<C> registry, HealthEndpointGroups groups, Duration slowIndicatorLoggingThreshold) {
        Assert.notNull(registry, (String)"Registry must not be null");
        Assert.notNull((Object)groups, (String)"Groups must not be null");
        this.registry = registry;
        this.groups = groups;
        this.slowIndicatorLoggingThreshold = slowIndicatorLoggingThreshold;
    }

    HealthResult<T> getHealth(ApiVersion apiVersion, WebServerNamespace serverNamespace, SecurityContext securityContext, boolean showAll, String ... path) {
        HealthEndpointGroup group;
        if (path.length > 0 && (group = this.getHealthGroup(serverNamespace, path)) != null) {
            return this.getHealth(apiVersion, group, securityContext, showAll, path, 1);
        }
        return this.getHealth(apiVersion, this.groups.getPrimary(), securityContext, showAll, path, 0);
    }

    private HealthEndpointGroup getHealthGroup(WebServerNamespace serverNamespace, String ... path) {
        if (this.groups.get(path[0]) != null) {
            return this.groups.get(path[0]);
        }
        if (serverNamespace != null) {
            AdditionalHealthEndpointPath additionalPath = AdditionalHealthEndpointPath.of(serverNamespace, path[0]);
            return this.groups.get(additionalPath);
        }
        return null;
    }

    private HealthResult<T> getHealth(ApiVersion apiVersion, HealthEndpointGroup group, SecurityContext securityContext, boolean showAll, String[] path, int pathOffset) {
        boolean isRoot;
        boolean showComponents = showAll || group.showComponents(securityContext);
        boolean showDetails = showAll || group.showDetails(securityContext);
        boolean isSystemHealth = group == this.groups.getPrimary() && pathOffset == 0;
        boolean bl = isRoot = path.length - pathOffset == 0;
        if (!showComponents && !isRoot) {
            return null;
        }
        Object contributor = this.getContributor(path, pathOffset);
        if (contributor == null) {
            return null;
        }
        String name = this.getName(path, pathOffset);
        Set<String> groupNames = isSystemHealth ? this.groups.getNames() : null;
        T health = this.getContribution(apiVersion, group, name, contributor, showComponents, showDetails, groupNames);
        return health != null ? new HealthResult<T>(health, group) : null;
    }

    private Object getContributor(String[] path, int pathOffset) {
        ContributorRegistry<C> contributor = this.registry;
        while (pathOffset < path.length) {
            if (!(contributor instanceof NamedContributors)) {
                return null;
            }
            contributor = ((NamedContributors)contributor).getContributor(path[pathOffset]);
            ++pathOffset;
        }
        return contributor;
    }

    private String getName(String[] path, int pathOffset) {
        StringBuilder name = new StringBuilder();
        while (pathOffset < path.length) {
            name.append(name.length() != 0 ? "/" : "");
            name.append(path[pathOffset]);
            ++pathOffset;
        }
        return name.toString();
    }

    private T getContribution(ApiVersion apiVersion, HealthEndpointGroup group, String name, Object contributor, boolean showComponents, boolean showDetails, Set<String> groupNames) {
        if (contributor instanceof NamedContributors) {
            return this.getAggregateContribution(apiVersion, group, name, (NamedContributors)contributor, showComponents, showDetails, groupNames);
        }
        if (contributor != null && (name.isEmpty() || group.isMember(name))) {
            return this.getLoggedHealth(contributor, name, showDetails);
        }
        return null;
    }

    private T getAggregateContribution(ApiVersion apiVersion, HealthEndpointGroup group, String name, NamedContributors<C> namedContributors, boolean showComponents, boolean showDetails, Set<String> groupNames) {
        String prefix = StringUtils.hasText((String)name) ? name + "/" : "";
        LinkedHashMap<String, T> contributions = new LinkedHashMap<String, T>();
        for (NamedContributor namedContributor : namedContributors) {
            T contribution = this.getContribution(apiVersion, group, prefix + namedContributor.getName(), namedContributor.getContributor(), showComponents, showDetails, null);
            if (contribution == null) continue;
            contributions.put(namedContributor.getName(), contribution);
        }
        if (contributions.isEmpty()) {
            return null;
        }
        return (T)this.aggregateContributions(apiVersion, contributions, group.getStatusAggregator(), showComponents, groupNames);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private T getLoggedHealth(C contributor, String name, boolean showDetails) {
        Duration duration;
        T t;
        Instant start = Instant.now();
        try {
            t = this.getHealth(contributor, showDetails);
        }
        catch (Throwable throwable) {
            Duration duration2;
            if (logger.isWarnEnabled() && this.slowIndicatorLoggingThreshold != null && (duration2 = Duration.between(start, Instant.now())).compareTo(this.slowIndicatorLoggingThreshold) > 0) {
                String contributorClassName = contributor.getClass().getName();
                String contributorIdentifier = !StringUtils.hasLength((String)name) ? contributorClassName : contributorClassName + " (" + name + ")";
                logger.warn((Object)LogMessage.format((String)"Health contributor %s took %s to respond", (Object)contributorIdentifier, (Object)DurationStyle.SIMPLE.print(duration2)));
            }
            throw throwable;
        }
        if (logger.isWarnEnabled() && this.slowIndicatorLoggingThreshold != null && (duration = Duration.between(start, Instant.now())).compareTo(this.slowIndicatorLoggingThreshold) > 0) {
            String contributorClassName = contributor.getClass().getName();
            String contributorIdentifier = !StringUtils.hasLength((String)name) ? contributorClassName : contributorClassName + " (" + name + ")";
            logger.warn((Object)LogMessage.format((String)"Health contributor %s took %s to respond", (Object)contributorIdentifier, (Object)DurationStyle.SIMPLE.print(duration)));
        }
        return t;
    }

    protected abstract T getHealth(C var1, boolean var2);

    protected abstract T aggregateContributions(ApiVersion var1, Map<String, T> var2, StatusAggregator var3, boolean var4, Set<String> var5);

    protected final CompositeHealth getCompositeHealth(ApiVersion apiVersion, Map<String, HealthComponent> components, StatusAggregator statusAggregator, boolean showComponents, Set<String> groupNames) {
        Map<String, HealthComponent> instances;
        Status status = statusAggregator.getAggregateStatus(components.values().stream().map(this::getStatus).collect(Collectors.toSet()));
        Map<String, HealthComponent> map = instances = showComponents ? components : null;
        if (groupNames != null) {
            return new SystemHealth(apiVersion, status, instances, groupNames);
        }
        return new CompositeHealth(apiVersion, status, instances);
    }

    private Status getStatus(HealthComponent component) {
        return component != null ? component.getStatus() : Status.UNKNOWN;
    }

    static class HealthResult<T> {
        private final T health;
        private final HealthEndpointGroup group;

        HealthResult(T health, HealthEndpointGroup group) {
            this.health = health;
            this.group = group;
        }

        T getHealth() {
            return this.health;
        }

        HealthEndpointGroup getGroup() {
            return this.group;
        }
    }
}

