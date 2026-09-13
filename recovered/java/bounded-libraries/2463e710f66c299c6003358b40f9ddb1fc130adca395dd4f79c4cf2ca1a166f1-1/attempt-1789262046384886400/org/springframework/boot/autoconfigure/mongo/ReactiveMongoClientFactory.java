/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mongodb.reactivestreams.client.MongoClient
 *  com.mongodb.reactivestreams.client.MongoClients
 */
package org.springframework.boot.autoconfigure.mongo;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import java.util.List;
import org.springframework.boot.autoconfigure.mongo.MongoClientFactorySupport;
import org.springframework.boot.autoconfigure.mongo.MongoClientSettingsBuilderCustomizer;

public class ReactiveMongoClientFactory
extends MongoClientFactorySupport<MongoClient> {
    public ReactiveMongoClientFactory(List<MongoClientSettingsBuilderCustomizer> builderCustomizers) {
        super(builderCustomizers, MongoClients::create);
    }
}

