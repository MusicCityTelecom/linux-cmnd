/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bson.Document
 *  org.springframework.data.mongodb.core.MongoTemplate
 *  org.springframework.util.Assert
 */
package org.springframework.boot.actuate.mongo;

import org.bson.Document;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.util.Assert;

public class MongoHealthIndicator
extends AbstractHealthIndicator {
    private final MongoTemplate mongoTemplate;

    public MongoHealthIndicator(MongoTemplate mongoTemplate) {
        super("MongoDB health check failed");
        Assert.notNull((Object)mongoTemplate, (String)"MongoTemplate must not be null");
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        Document result = this.mongoTemplate.executeCommand("{ buildInfo: 1 }");
        builder.up().withDetail("version", result.getString((Object)"version"));
    }
}

