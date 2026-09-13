/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  reactor.core.publisher.Flux
 *  reactor.core.publisher.Mono
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
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthComponent;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.health.HealthEndpointGroup;
import org.springframework.boot.actuate.health.HealthEndpointGroups;
import org.springframework.boot.actuate.health.HealthEndpointSupport;
import org.springframework.boot.actuate.health.ReactiveHealthContributor;
import org.springframework.boot.actuate.health.ReactiveHealthContributorRegistry;
import org.springframework.boot.actuate.health.ReactiveHealthIndicator;
import org.springframework.boot.actuate.health.StatusAggregator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@EndpointWebExtension(endpoint=HealthEndpoint.class)
public class ReactiveHealthEndpointWebExtension
extends HealthEndpointSupport<ReactiveHealthContributor, Mono<? extends HealthComponent>> {
    private static final String[] NO_PATH = new String[0];

    @Deprecated
    public ReactiveHealthEndpointWebExtension(ReactiveHealthContributorRegistry registry, HealthEndpointGroups groups) {
        super(registry, groups, null);
    }

    public ReactiveHealthEndpointWebExtension(ReactiveHealthContributorRegistry registry, HealthEndpointGroups groups, Duration slowIndicatorLoggingThreshold) {
        super(registry, groups, slowIndicatorLoggingThreshold);
    }

    @ReadOperation
    public Mono<WebEndpointResponse<? extends HealthComponent>> health(ApiVersion apiVersion, WebServerNamespace serverNamespace, SecurityContext securityContext) {
        return this.health(apiVersion, serverNamespace, securityContext, false, NO_PATH);
    }

    @ReadOperation
    public Mono<WebEndpointResponse<? extends HealthComponent>> health(ApiVersion apiVersion, WebServerNamespace serverNamespace, SecurityContext securityContext, String ... path) {
        return this.health(apiVersion, serverNamespace, securityContext, false, path);
    }

    public Mono<WebEndpointResponse<? extends HealthComponent>> health(ApiVersion apiVersion, WebServerNamespace serverNamespace, SecurityContext securityContext, boolean showAll, String ... path) {
        HealthEndpointSupport.HealthResult result = this.getHealth(apiVersion, serverNamespace, securityContext, showAll, path);
        if (result == null) {
            return Arrays.equals(path, NO_PATH) ? Mono.just(new WebEndpointResponse<Health>(DEFAULT_HEALTH, 200)) : Mono.just(new WebEndpointResponse<int>(404));
        }
        HealthEndpointGroup group = result.getGroup();
        return ((Mono)result.getHealth()).map(health -> {
            int statusCode = group.getHttpCodeStatusMapper().getStatusCode(health.getStatus());
            return new WebEndpointResponse<HealthComponent>((HealthComponent)health, statusCode);
        });
    }

    @Override
    protected Mono<? extends HealthComponent> getHealth(ReactiveHealthContributor contributor, boolean includeDetails) {
        return ((ReactiveHealthIndicator)contributor).getHealth(includeDetails);
    }

    @Override
    protected Mono<? extends HealthComponent> aggregateContributions(ApiVersion apiVersion, Map<String, Mono<? extends HealthComponent>> contributions, StatusAggregator statusAggregator, boolean showComponents, Set<String> groupNames) {
        return Flux.fromIterable(contributions.entrySet()).flatMap(NamedHealthComponent::create).collectMap(NamedHealthComponent::getName, NamedHealthComponent::getHealth).map(components -> this.getCompositeHealth(apiVersion, (Map<String, HealthComponent>)components, statusAggregator, showComponents, groupNames));
    }

    private static final class NamedHealthComponent {
        private final String name;
        private final HealthComponent health;

        private NamedHealthComponent(Object ... pair) {
            this.name = (String)pair[0];
            this.health = (HealthComponent)pair[1];
        }

        String getName() {
            return this.name;
        }

        HealthComponent getHealth() {
            return this.health;
        }

        static Mono<NamedHealthComponent> create(Map.Entry<String, Mono<? extends HealthComponent>> entry) {
            Mono name = Mono.just((Object)entry.getKey());
            Mono<? extends HealthComponent> health = entry.getValue();
            return Mono.zip(NamedHealthComponent::new, (Mono[])new Mono[]{name, health});
        }
    }
}

