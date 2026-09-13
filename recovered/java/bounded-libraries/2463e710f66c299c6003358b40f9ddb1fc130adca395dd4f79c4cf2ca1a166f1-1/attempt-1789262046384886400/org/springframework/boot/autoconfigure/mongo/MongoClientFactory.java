/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mongodb.client.MongoClient
 *  com.mongodb.client.MongoClients
 */
package org.springframework.boot.autoconfigure.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import java.util.List;
import org.springframework.boot.autoconfigure.mongo.MongoClientFactorySupport;
import org.springframework.boot.autoconfigure.mongo.MongoClientSettingsBuilderCustomizer;

public class MongoClientFactory
extends MongoClientFactorySupport<MongoClient> {
    public MongoClientFactory(List<MongoClientSettingsBuilderCustomizer> builderCustomizers) {
        super(builderCustomizers, MongoClients::create);
    }
}

