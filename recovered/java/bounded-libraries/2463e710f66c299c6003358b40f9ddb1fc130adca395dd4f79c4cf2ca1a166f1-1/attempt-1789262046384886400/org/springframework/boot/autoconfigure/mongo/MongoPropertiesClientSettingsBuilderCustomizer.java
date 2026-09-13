/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mongodb.ConnectionString
 *  com.mongodb.MongoClientSettings$Builder
 *  com.mongodb.MongoCredential
 *  com.mongodb.ServerAddress
 *  org.springframework.core.Ordered
 *  org.springframework.core.env.Environment
 */
package org.springframework.boot.autoconfigure.mongo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import java.util.Collections;
import org.springframework.boot.autoconfigure.mongo.MongoClientSettingsBuilderCustomizer;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;

public class MongoPropertiesClientSettingsBuilderCustomizer
implements MongoClientSettingsBuilderCustomizer,
Ordered {
    private final MongoProperties properties;
    private final Environment environment;
    private int order = 0;

    public MongoPropertiesClientSettingsBuilderCustomizer(MongoProperties properties, Environment environment) {
        this.properties = properties;
        this.environment = environment;
    }

    @Override
    public void customize(MongoClientSettings.Builder settingsBuilder) {
        this.applyUuidRepresentation(settingsBuilder);
        this.applyHostAndPort(settingsBuilder);
        this.applyCredentials(settingsBuilder);
        this.applyReplicaSet(settingsBuilder);
    }

    private void applyUuidRepresentation(MongoClientSettings.Builder settingsBuilder) {
        settingsBuilder.uuidRepresentation(this.properties.getUuidRepresentation());
    }

    private void applyHostAndPort(MongoClientSettings.Builder settings) {
        if (this.getEmbeddedPort() != null) {
            settings.applyConnectionString(new ConnectionString("mongodb://localhost:" + this.getEmbeddedPort()));
            return;
        }
        if (this.properties.getUri() != null) {
            settings.applyConnectionString(new ConnectionString(this.properties.getUri()));
            return;
        }
        if (this.properties.getHost() != null || this.properties.getPort() != null) {
            String host = this.getOrDefault(this.properties.getHost(), "localhost");
            int port = this.getOrDefault(this.properties.getPort(), 27017);
            ServerAddress serverAddress = new ServerAddress(host, port);
            settings.applyToClusterSettings(cluster -> cluster.hosts(Collections.singletonList(serverAddress)));
            return;
        }
        settings.applyConnectionString(new ConnectionString("mongodb://localhost/test"));
    }

    private void applyCredentials(MongoClientSettings.Builder builder) {
        if (this.properties.getUri() == null && this.properties.getUsername() != null && this.properties.getPassword() != null) {
            String database = this.properties.getAuthenticationDatabase() != null ? this.properties.getAuthenticationDatabase() : this.properties.getMongoClientDatabase();
            builder.credential(MongoCredential.createCredential((String)this.properties.getUsername(), (String)database, (char[])this.properties.getPassword()));
        }
    }

    private void applyReplicaSet(MongoClientSettings.Builder builder) {
        if (this.properties.getReplicaSetName() != null) {
            builder.applyToClusterSettings(cluster -> cluster.requiredReplicaSetName(this.properties.getReplicaSetName()));
        }
    }

    private <V> V getOrDefault(V value, V defaultValue) {
        return value != null ? value : defaultValue;
    }

    private Integer getEmbeddedPort() {
        String localPort;
        if (this.environment != null && (localPort = this.environment.getProperty("local.mongo.port")) != null) {
            return Integer.valueOf(localPort);
        }
        return null;
    }

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}

