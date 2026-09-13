/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.graphql.server.GraphQlRSocketHandler
 *  org.springframework.messaging.handler.annotation.MessageMapping
 *  org.springframework.stereotype.Controller
 *  reactor.core.publisher.Flux
 *  reactor.core.publisher.Mono
 */
package org.springframework.boot.autoconfigure.graphql.rsocket;

import java.util.Map;
import org.springframework.graphql.server.GraphQlRSocketHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
class GraphQlRSocketController {
    private final GraphQlRSocketHandler handler;

    GraphQlRSocketController(GraphQlRSocketHandler handler) {
        this.handler = handler;
    }

    @MessageMapping(value={"${spring.graphql.rsocket.mapping}"})
    Mono<Map<String, Object>> handle(Map<String, Object> payload) {
        return this.handler.handle(payload);
    }

    @MessageMapping(value={"${spring.graphql.rsocket.mapping}"})
    Flux<Map<String, Object>> handleSubscription(Map<String, Object> payload) {
        return this.handler.handleSubscription(payload);
    }
}

