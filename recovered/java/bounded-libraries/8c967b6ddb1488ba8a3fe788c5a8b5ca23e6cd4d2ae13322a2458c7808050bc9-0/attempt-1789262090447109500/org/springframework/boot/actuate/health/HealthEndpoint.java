/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.health;

import java.time.Duration;
import java.util.Map;
import java.util.Set;
import org.springframework.boot.actuate.endpoint.ApiVersion;
import org.springframework.boot.actuate.endpoint.EndpointId;
import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.health.HealthComponent;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.actuate.health.HealthContributorRegistry;
import org.springframework.boot.actuate.health.HealthEndpointGroups;
import org.springframework.boot.actuate.health.HealthEndpointSupport;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.StatusAggregator;

@Endpoint(id="health")
public class HealthEndpoint
extends HealthEndpointSupport<HealthContributor, HealthComponent> {
    public static final EndpointId ID = EndpointId.of("health");
    private static final String[] EMPTY_PATH = new String[0];

    @Deprecated
    public HealthEndpoint(HealthContributorRegistry registry, HealthEndpointGroups groups) {
        super(registry, groups, null);
    }

    public HealthEndpoint(HealthContributorRegistry registry, HealthEndpointGroups groups, Duration slowIndicatorLoggingThreshold) {
        super(registry, groups, slowIndicatorLoggingThreshold);
    }

    @ReadOperation
    public HealthComponent health() {
        HealthComponent health = this.health(ApiVersion.V3, EMPTY_PATH);
        return health != null ? health : DEFAULT_HEALTH;
    }

    @ReadOperation
    public HealthComponent healthForPath(String ... path) {
        return this.health(ApiVersion.V3, path);
    }

    private HealthComponent health(ApiVersion apiVersion, String ... path) {
        HealthEndpointSupport.HealthResult result = this.getHealth(apiVersion, null, SecurityContext.NONE, true, path);
        return result != null ? (HealthComponent)result.getHealth() : null;
    }

    @Override
    protected HealthComponent getHealth(HealthContributor contributor, boolean includeDetails) {
        return ((HealthIndicator)contributor).getHealth(includeDetails);
    }

    @Override
    protected HealthComponent aggregateContributions(ApiVersion apiVersion, Map<String, HealthComponent> contributions, StatusAggregator statusAggregator, boolean showComponents, Set<String> groupNames) {
        return this.getCompositeHealth(apiVersion, contributions, statusAggregator, showComponents, groupNames);
    }
}

