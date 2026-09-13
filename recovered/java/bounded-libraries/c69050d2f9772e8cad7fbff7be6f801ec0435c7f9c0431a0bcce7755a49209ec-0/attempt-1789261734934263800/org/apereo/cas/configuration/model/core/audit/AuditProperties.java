/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.core.audit;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.audit.AuditCouchDbProperties;
import org.apereo.cas.configuration.model.core.audit.AuditCouchbaseProperties;
import org.apereo.cas.configuration.model.core.audit.AuditEngineProperties;
import org.apereo.cas.configuration.model.core.audit.AuditJdbcProperties;
import org.apereo.cas.configuration.model.core.audit.AuditMongoDbProperties;
import org.apereo.cas.configuration.model.core.audit.AuditRestProperties;
import org.apereo.cas.configuration.model.core.audit.AuditSlf4jLogProperties;
import org.apereo.cas.configuration.model.support.dynamodb.AuditDynamoDbProperties;
import org.apereo.cas.configuration.model.support.redis.AuditRedisProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-core-audit", automated=true)
@JsonFilter(value="AuditProperties")
public class AuditProperties
implements Serializable {
    private static final long serialVersionUID = 3946106584608417663L;
    @NestedConfigurationProperty
    private AuditEngineProperties engine = new AuditEngineProperties();
    @NestedConfigurationProperty
    private AuditJdbcProperties jdbc = new AuditJdbcProperties();
    @NestedConfigurationProperty
    private AuditMongoDbProperties mongo = new AuditMongoDbProperties();
    @NestedConfigurationProperty
    private AuditCouchDbProperties couchDb = new AuditCouchDbProperties();
    @NestedConfigurationProperty
    private AuditRedisProperties redis = new AuditRedisProperties();
    @NestedConfigurationProperty
    private AuditRestProperties rest = new AuditRestProperties();
    @NestedConfigurationProperty
    private AuditSlf4jLogProperties slf4j = new AuditSlf4jLogProperties();
    @NestedConfigurationProperty
    private AuditCouchbaseProperties couchbase = new AuditCouchbaseProperties();
    @NestedConfigurationProperty
    private AuditDynamoDbProperties dynamoDb = new AuditDynamoDbProperties();

    @Generated
    public AuditEngineProperties getEngine() {
        return this.engine;
    }

    @Generated
    public AuditJdbcProperties getJdbc() {
        return this.jdbc;
    }

    @Generated
    public AuditMongoDbProperties getMongo() {
        return this.mongo;
    }

    @Generated
    public AuditCouchDbProperties getCouchDb() {
        return this.couchDb;
    }

    @Generated
    public AuditRedisProperties getRedis() {
        return this.redis;
    }

    @Generated
    public AuditRestProperties getRest() {
        return this.rest;
    }

    @Generated
    public AuditSlf4jLogProperties getSlf4j() {
        return this.slf4j;
    }

    @Generated
    public AuditCouchbaseProperties getCouchbase() {
        return this.couchbase;
    }

    @Generated
    public AuditDynamoDbProperties getDynamoDb() {
        return this.dynamoDb;
    }

    @Generated
    public AuditProperties setEngine(AuditEngineProperties engine) {
        this.engine = engine;
        return this;
    }

    @Generated
    public AuditProperties setJdbc(AuditJdbcProperties jdbc) {
        this.jdbc = jdbc;
        return this;
    }

    @Generated
    public AuditProperties setMongo(AuditMongoDbProperties mongo) {
        this.mongo = mongo;
        return this;
    }

    @Generated
    public AuditProperties setCouchDb(AuditCouchDbProperties couchDb) {
        this.couchDb = couchDb;
        return this;
    }

    @Generated
    public AuditProperties setRedis(AuditRedisProperties redis) {
        this.redis = redis;
        return this;
    }

    @Generated
    public AuditProperties setRest(AuditRestProperties rest) {
        this.rest = rest;
        return this;
    }

    @Generated
    public AuditProperties setSlf4j(AuditSlf4jLogProperties slf4j) {
        this.slf4j = slf4j;
        return this;
    }

    @Generated
    public AuditProperties setCouchbase(AuditCouchbaseProperties couchbase) {
        this.couchbase = couchbase;
        return this;
    }

    @Generated
    public AuditProperties setDynamoDb(AuditDynamoDbProperties dynamoDb) {
        this.dynamoDb = dynamoDb;
        return this;
    }
}

