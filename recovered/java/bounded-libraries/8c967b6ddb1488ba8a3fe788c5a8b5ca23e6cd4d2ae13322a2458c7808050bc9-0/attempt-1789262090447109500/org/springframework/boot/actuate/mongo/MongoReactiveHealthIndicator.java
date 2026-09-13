/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bson.Document
 *  org.springframework.data.mongodb.core.ReactiveMongoTemplate
 *  org.springframework.util.Assert
 *  reactor.core.publisher.Mono
 */
package org.springframework.boot.actuate.mongo;

import org.bson.Document;
import org.springframework.boot.actuate.health.AbstractReactiveHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;

public class MongoReactiveHealthIndicator
extends AbstractReactiveHealthIndicator {
    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public MongoReactiveHealthIndicator(ReactiveMongoTemplate reactiveMongoTemplate) {
        super("Mongo health check failed");
        Assert.notNull((Object)reactiveMongoTemplate, (String)"ReactiveMongoTemplate must not be null");
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    @Override
    protected Mono<Health> doHealthCheck(Health.Builder builder) {
        Mono buildInfo = this.reactiveMongoTemplate.executeCommand("{ buildInfo: 1 }");
        return buildInfo.map(document -> this.up(builder, (Document)document));
    }

    private Health up(Health.Builder builder, Document document) {
        return builder.up().withDetail("version", document.getString((Object)"version")).build();
    }
}

