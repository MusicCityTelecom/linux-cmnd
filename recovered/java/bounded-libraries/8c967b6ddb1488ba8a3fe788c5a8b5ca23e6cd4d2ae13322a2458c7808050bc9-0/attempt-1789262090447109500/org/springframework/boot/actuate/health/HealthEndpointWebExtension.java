/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.health;

import java.time.Duration;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import org.springframework.boot.actuate.endpoint.ApiVersion;
import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.web.WebEndpointResponse;
import org.springframework.boot.actuate.endpoint.web.WebServerNamespace;
import org.springframework.boot.actuate.endpoint.web.annotation.EndpointWebExtension;
import org.springframework.boot.actuate.health.HealthComponent;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.actuate.health.HealthContributorRegistry;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.health.HealthEndpointGroup;
import org.springframework.boot.actuate.health.HealthEndpointGroups;
import org.springframework.boot.actuate.health.HealthEndpointSupport;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.StatusAggregator;

@EndpointWebExtension(endpoint=HealthEndpoint.class)
public class HealthEndpointWebExtension
extends HealthEndpointSupport<HealthContributor, HealthComponent> {
    private static final String[] NO_PATH = new String[0];

    @Deprecated
    public HealthEndpointWebExtension(HealthContributorRegistry registry, HealthEndpointGroups groups) {
        super(registry, groups, null);
    }

    public HealthEndpointWebExtension(HealthContributorRegistry registry, HealthEndpointGroups groups, Duration slowIndicatorLoggingThreshold) {
        super(registry, groups, slowIndicatorLoggingThreshold);
    }

    @ReadOperation
    public WebEndpointResponse<HealthComponent> health(ApiVersion apiVersion, WebServerNamespace serverNamespace, SecurityContext securityContext) {
        return this.health(apiVersion, serverNamespace, securityContext, false, NO_PATH);
    }

    @ReadOperation
    public WebEndpointResponse<HealthComponent> health(ApiVersion apiVersion, WebServerNamespace serverNamespace, SecurityContext securityContext, String ... path) {
        return this.health(apiVersion, serverNamespace, securityContext, false, path);
    }

    public WebEndpointResponse<HealthComponent> health(ApiVersion apiVersion, WebServerNamespace serverNamespace, SecurityContext securityContext, boolean showAll, String ... path) {
        HealthEndpointSupport.HealthResult result = this.getHealth(apiVersion, serverNamespace, securityContext, showAll, path);
        if (result == null) {
            return Arrays.equals(path, NO_PATH) ? new WebEndpointResponse<HealthComponent>(DEFAULT_HEALTH, 200) : new WebEndpointResponse<int>(404);
        }
        HealthComponent health = (HealthComponent)result.getHealth();
        HealthEndpointGroup group = result.getGroup();
        int statusCode = group.getHttpCodeStatusMapper().getStatusCode(health.getStatus());
        return new WebEndpointResponse<HealthComponent>(health, statusCode);
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

