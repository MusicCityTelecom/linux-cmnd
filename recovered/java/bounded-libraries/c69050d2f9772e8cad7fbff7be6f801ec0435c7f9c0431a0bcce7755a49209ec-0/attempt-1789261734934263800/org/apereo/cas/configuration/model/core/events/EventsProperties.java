/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.core.events;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.events.CoreEventsProperties;
import org.apereo.cas.configuration.model.core.events.CouchDbEventsProperties;
import org.apereo.cas.configuration.model.core.events.DynamoDbEventsProperties;
import org.apereo.cas.configuration.model.core.events.InfluxDbEventsProperties;
import org.apereo.cas.configuration.model.core.events.JpaEventsProperties;
import org.apereo.cas.configuration.model.core.events.MongoDbEventsProperties;
import org.apereo.cas.configuration.model.core.events.RedisEventsProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-core-events", automated=true)
@JsonFilter(value="EventsProperties")
public class EventsProperties
implements Serializable {
    private static final long serialVersionUID = 1734523424737956370L;
    @NestedConfigurationProperty
    private CoreEventsProperties core = new CoreEventsProperties();
    @NestedConfigurationProperty
    private JpaEventsProperties jpa = new JpaEventsProperties();
    @NestedConfigurationProperty
    private InfluxDbEventsProperties influxDb = new InfluxDbEventsProperties();
    @NestedConfigurationProperty
    private MongoDbEventsProperties mongo = new MongoDbEventsProperties();
    @NestedConfigurationProperty
    private CouchDbEventsProperties couchDb = new CouchDbEventsProperties();
    @NestedConfigurationProperty
    private DynamoDbEventsProperties dynamoDb = new DynamoDbEventsProperties();
    @NestedConfigurationProperty
    private RedisEventsProperties redis = new RedisEventsProperties();

    @Generated
    public CoreEventsProperties getCore() {
        return this.core;
    }

    @Generated
    public JpaEventsProperties getJpa() {
        return this.jpa;
    }

    @Generated
    public InfluxDbEventsProperties getInfluxDb() {
        return this.influxDb;
    }

    @Generated
    public MongoDbEventsProperties getMongo() {
        return this.mongo;
    }

    @Generated
    public CouchDbEventsProperties getCouchDb() {
        return this.couchDb;
    }

    @Generated
    public DynamoDbEventsProperties getDynamoDb() {
        return this.dynamoDb;
    }

    @Generated
    public RedisEventsProperties getRedis() {
        return this.redis;
    }

    @Generated
    public EventsProperties setCore(CoreEventsProperties core) {
        this.core = core;
        return this;
    }

    @Generated
    public EventsProperties setJpa(JpaEventsProperties jpa) {
        this.jpa = jpa;
        return this;
    }

    @Generated
    public EventsProperties setInfluxDb(InfluxDbEventsProperties influxDb) {
        this.influxDb = influxDb;
        return this;
    }

    @Generated
    public EventsProperties setMongo(MongoDbEventsProperties mongo) {
        this.mongo = mongo;
        return this;
    }

    @Generated
    public EventsProperties setCouchDb(CouchDbEventsProperties couchDb) {
        this.couchDb = couchDb;
        return this;
    }

    @Generated
    public EventsProperties setDynamoDb(DynamoDbEventsProperties dynamoDb) {
        this.dynamoDb = dynamoDb;
        return this;
    }

    @Generated
    public EventsProperties setRedis(RedisEventsProperties redis) {
        this.redis = redis;
        return this;
    }
}

