/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.ParameterizedTypeReference
 *  org.springframework.data.elasticsearch.client.reactive.ReactiveElasticsearchClient
 *  org.springframework.web.reactive.function.client.ClientResponse
 *  org.springframework.web.reactive.function.client.WebClient
 *  reactor.core.publisher.Mono
 */
package org.springframework.boot.actuate.elasticsearch;

import java.util.Map;
import org.springframework.boot.actuate.health.AbstractReactiveHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.elasticsearch.client.reactive.ReactiveElasticsearchClient;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class ElasticsearchReactiveHealthIndicator
extends AbstractReactiveHealthIndicator {
    private static final ParameterizedTypeReference<Map<String, Object>> STRING_OBJECT_MAP = new ParameterizedTypeReference<Map<String, Object>>(){};
    private static final String RED_STATUS = "red";
    private final ReactiveElasticsearchClient client;

    public ElasticsearchReactiveHealthIndicator(ReactiveElasticsearchClient client) {
        super("Elasticsearch health check failed");
        this.client = client;
    }

    @Override
    protected Mono<Health> doHealthCheck(Health.Builder builder) {
        return this.client.execute(webClient -> this.getHealth(builder, webClient));
    }

    private Mono<Health> getHealth(Health.Builder builder, WebClient webClient) {
        return webClient.get().uri("/_cluster/health/", new Object[0]).exchangeToMono(response -> this.doHealthCheck(builder, (ClientResponse)response));
    }

    private Mono<Health> doHealthCheck(Health.Builder builder, ClientResponse response) {
        if (response.statusCode().is2xxSuccessful()) {
            return response.bodyToMono(STRING_OBJECT_MAP).map(body -> this.getHealth(builder, (Map<String, Object>)body));
        }
        builder.down();
        builder.withDetail("statusCode", response.rawStatusCode());
        builder.withDetail("reasonPhrase", response.statusCode().getReasonPhrase());
        return response.releaseBody().thenReturn((Object)builder.build());
    }

    private Health getHealth(Health.Builder builder, Map<String, Object> body) {
        String status = (String)body.get("status");
        builder.status(RED_STATUS.equals(status) ? Status.OUT_OF_SERVICE : Status.UP);
        builder.withDetails(body);
        return builder.build();
    }
}

