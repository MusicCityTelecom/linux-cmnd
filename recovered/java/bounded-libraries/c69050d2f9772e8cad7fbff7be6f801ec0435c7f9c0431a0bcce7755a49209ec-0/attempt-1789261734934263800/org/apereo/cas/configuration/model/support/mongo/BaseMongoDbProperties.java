/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.mongo;

import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.mongo.MongoDbConnectionPoolProperties;
import org.apereo.cas.configuration.support.DurationCapable;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-mongo-core")
public abstract class BaseMongoDbProperties
implements Serializable {
    private static final long serialVersionUID = -2471243083598934186L;
    @NestedConfigurationProperty
    private MongoDbConnectionPoolProperties pool = new MongoDbConnectionPoolProperties();
    @RequiredProperty
    private String clientUri = "";
    @RequiredProperty
    private int port = 27017;
    @RequiredProperty
    private String userId = "";
    @RequiredProperty
    private String password = "";
    @RequiredProperty
    private String host = "localhost";
    @DurationCapable
    private String timeout = "PT5S";
    private String writeConcern = "ACKNOWLEDGED";
    private String readConcern = "AVAILABLE";
    private String readPreference = "PRIMARY";
    @RequiredProperty
    private String databaseName = "";
    private boolean socketKeepAlive;
    private boolean retryWrites;
    private String authenticationDatabaseName;
    private boolean sslEnabled;
    private String replicaSet;

    @Generated
    public MongoDbConnectionPoolProperties getPool() {
        return this.pool;
    }

    @Generated
    public String getClientUri() {
        return this.clientUri;
    }

    @Generated
    public int getPort() {
        return this.port;
    }

    @Generated
    public String getUserId() {
        return this.userId;
    }

    @Generated
    public String getPassword() {
        return this.password;
    }

    @Generated
    public String getHost() {
        return this.host;
    }

    @Generated
    public String getTimeout() {
        return this.timeout;
    }

    @Generated
    public String getWriteConcern() {
        return this.writeConcern;
    }

    @Generated
    public String getReadConcern() {
        return this.readConcern;
    }

    @Generated
    public String getReadPreference() {
        return this.readPreference;
    }

    @Generated
    public String getDatabaseName() {
        return this.databaseName;
    }

    @Generated
    public boolean isSocketKeepAlive() {
        return this.socketKeepAlive;
    }

    @Generated
    public boolean isRetryWrites() {
        return this.retryWrites;
    }

    @Generated
    public String getAuthenticationDatabaseName() {
        return this.authenticationDatabaseName;
    }

    @Generated
    public boolean isSslEnabled() {
        return this.sslEnabled;
    }

    @Generated
    public String getReplicaSet() {
        return this.replicaSet;
    }

    @Generated
    public BaseMongoDbProperties setPool(MongoDbConnectionPoolProperties pool) {
        this.pool = pool;
        return this;
    }

    @Generated
    public BaseMongoDbProperties setClientUri(String clientUri) {
        this.clientUri = clientUri;
        return this;
    }

    @Generated
    public BaseMongoDbProperties setPort(int port) {
        this.port = port;
        return this;
    }

    @Generated
    public BaseMongoDbProperties setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    @Generated
    public BaseMongoDbProperties setPassword(String password) {
        this.password = password;
        return this;
    }

    @Generated
    public BaseMongoDbProperties setHost(String host) {
        this.host = host;
        return this;
    }

    @Generated
    public BaseMongoDbProperties setTimeout(String timeout) {
        this.timeout = timeout;
        return this;
    }

    @Generated
    public BaseMongoDbProperties setWriteConcern(String writeConcern) {
        this.writeConcern = writeConcern;
        return this;
    }

    @Generated
    public BaseMongoDbProperties setReadConcern(String readConcern) {
        this.readConcern = readConcern;
        return this;
    }

    @Generated
    public BaseMongoDbProperties setReadPreference(String readPreference) {
        this.readPreference = readPreference;
        return this;
    }

    @Generated
    public BaseMongoDbProperties setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
        return this;
    }

    @Generated
    public BaseMongoDbProperties setSocketKeepAlive(boolean socketKeepAlive) {
        this.socketKeepAlive = socketKeepAlive;
        return this;
    }

    @Generated
    public BaseMongoDbProperties setRetryWrites(boolean retryWrites) {
        this.retryWrites = retryWrites;
        return this;
    }

    @Generated
    public BaseMongoDbProperties setAuthenticationDatabaseName(String authenticationDatabaseName) {
        this.authenticationDatabaseName = authenticationDatabaseName;
        return this;
    }

    @Generated
    public BaseMongoDbProperties setSslEnabled(boolean sslEnabled) {
        this.sslEnabled = sslEnabled;
        return this;
    }

    @Generated
    public BaseMongoDbProperties setReplicaSet(String replicaSet) {
        this.replicaSet = replicaSet;
        return this;
    }
}

