/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.core.ticket.registry;

import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.ticket.registry.InMemoryTicketRegistryProperties;
import org.apereo.cas.configuration.model.core.ticket.registry.TicketRegistryCoreProperties;
import org.apereo.cas.configuration.model.support.cassandra.ticketregistry.CassandraTicketRegistryProperties;
import org.apereo.cas.configuration.model.support.couchbase.ticketregistry.CouchbaseTicketRegistryProperties;
import org.apereo.cas.configuration.model.support.couchdb.ticketregistry.CouchDbTicketRegistryProperties;
import org.apereo.cas.configuration.model.support.dynamodb.DynamoDbTicketRegistryProperties;
import org.apereo.cas.configuration.model.support.ehcache.Ehcache3Properties;
import org.apereo.cas.configuration.model.support.ehcache.EhcacheProperties;
import org.apereo.cas.configuration.model.support.hazelcast.HazelcastTicketRegistryProperties;
import org.apereo.cas.configuration.model.support.ignite.IgniteProperties;
import org.apereo.cas.configuration.model.support.infinispan.InfinispanProperties;
import org.apereo.cas.configuration.model.support.jms.JmsTicketRegistryProperties;
import org.apereo.cas.configuration.model.support.jpa.ticketregistry.JpaTicketRegistryProperties;
import org.apereo.cas.configuration.model.support.memcached.MemcachedTicketRegistryProperties;
import org.apereo.cas.configuration.model.support.mongo.ticketregistry.MongoDbTicketRegistryProperties;
import org.apereo.cas.configuration.model.support.quartz.ScheduledJobProperties;
import org.apereo.cas.configuration.model.support.redis.RedisTicketRegistryProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-core-tickets", automated=true)
public class TicketRegistryProperties
implements Serializable {
    private static final long serialVersionUID = -4735458476452635679L;
    @NestedConfigurationProperty
    private JmsTicketRegistryProperties jms = new JmsTicketRegistryProperties();
    @NestedConfigurationProperty
    private DynamoDbTicketRegistryProperties dynamoDb = new DynamoDbTicketRegistryProperties();
    @NestedConfigurationProperty
    private InfinispanProperties infinispan = new InfinispanProperties();
    @NestedConfigurationProperty
    private CouchbaseTicketRegistryProperties couchbase = new CouchbaseTicketRegistryProperties();
    @NestedConfigurationProperty
    private MongoDbTicketRegistryProperties mongo = new MongoDbTicketRegistryProperties();
    @NestedConfigurationProperty
    private EhcacheProperties ehcache = new EhcacheProperties();
    @NestedConfigurationProperty
    private Ehcache3Properties ehcache3 = new Ehcache3Properties();
    @NestedConfigurationProperty
    private HazelcastTicketRegistryProperties hazelcast = new HazelcastTicketRegistryProperties();
    @NestedConfigurationProperty
    private IgniteProperties ignite = new IgniteProperties();
    @NestedConfigurationProperty
    private JpaTicketRegistryProperties jpa = new JpaTicketRegistryProperties();
    @NestedConfigurationProperty
    private MemcachedTicketRegistryProperties memcached = new MemcachedTicketRegistryProperties();
    @NestedConfigurationProperty
    private RedisTicketRegistryProperties redis = new RedisTicketRegistryProperties();
    @NestedConfigurationProperty
    private CassandraTicketRegistryProperties cassandra = new CassandraTicketRegistryProperties();
    @NestedConfigurationProperty
    private InMemoryTicketRegistryProperties inMemory = new InMemoryTicketRegistryProperties();
    @NestedConfigurationProperty
    private CouchDbTicketRegistryProperties couchDb = new CouchDbTicketRegistryProperties();
    @NestedConfigurationProperty
    private ScheduledJobProperties cleaner = new ScheduledJobProperties("PT10S", "PT1M");
    @NestedConfigurationProperty
    private TicketRegistryCoreProperties core = new TicketRegistryCoreProperties();

    @Generated
    public JmsTicketRegistryProperties getJms() {
        return this.jms;
    }

    @Generated
    public DynamoDbTicketRegistryProperties getDynamoDb() {
        return this.dynamoDb;
    }

    @Generated
    public InfinispanProperties getInfinispan() {
        return this.infinispan;
    }

    @Generated
    public CouchbaseTicketRegistryProperties getCouchbase() {
        return this.couchbase;
    }

    @Generated
    public MongoDbTicketRegistryProperties getMongo() {
        return this.mongo;
    }

    @Generated
    public EhcacheProperties getEhcache() {
        return this.ehcache;
    }

    @Generated
    public Ehcache3Properties getEhcache3() {
        return this.ehcache3;
    }

    @Generated
    public HazelcastTicketRegistryProperties getHazelcast() {
        return this.hazelcast;
    }

    @Generated
    public IgniteProperties getIgnite() {
        return this.ignite;
    }

    @Generated
    public JpaTicketRegistryProperties getJpa() {
        return this.jpa;
    }

    @Generated
    public MemcachedTicketRegistryProperties getMemcached() {
        return this.memcached;
    }

    @Generated
    public RedisTicketRegistryProperties getRedis() {
        return this.redis;
    }

    @Generated
    public CassandraTicketRegistryProperties getCassandra() {
        return this.cassandra;
    }

    @Generated
    public InMemoryTicketRegistryProperties getInMemory() {
        return this.inMemory;
    }

    @Generated
    public CouchDbTicketRegistryProperties getCouchDb() {
        return this.couchDb;
    }

    @Generated
    public ScheduledJobProperties getCleaner() {
        return this.cleaner;
    }

    @Generated
    public TicketRegistryCoreProperties getCore() {
        return this.core;
    }

    @Generated
    public TicketRegistryProperties setJms(JmsTicketRegistryProperties jms) {
        this.jms = jms;
        return this;
    }

    @Generated
    public TicketRegistryProperties setDynamoDb(DynamoDbTicketRegistryProperties dynamoDb) {
        this.dynamoDb = dynamoDb;
        return this;
    }

    @Generated
    public TicketRegistryProperties setInfinispan(InfinispanProperties infinispan) {
        this.infinispan = infinispan;
        return this;
    }

    @Generated
    public TicketRegistryProperties setCouchbase(CouchbaseTicketRegistryProperties couchbase) {
        this.couchbase = couchbase;
        return this;
    }

    @Generated
    public TicketRegistryProperties setMongo(MongoDbTicketRegistryProperties mongo) {
        this.mongo = mongo;
        return this;
    }

    @Generated
    public TicketRegistryProperties setEhcache(EhcacheProperties ehcache) {
        this.ehcache = ehcache;
        return this;
    }

    @Generated
    public TicketRegistryProperties setEhcache3(Ehcache3Properties ehcache3) {
        this.ehcache3 = ehcache3;
        return this;
    }

    @Generated
    public TicketRegistryProperties setHazelcast(HazelcastTicketRegistryProperties hazelcast) {
        this.hazelcast = hazelcast;
        return this;
    }

    @Generated
    public TicketRegistryProperties setIgnite(IgniteProperties ignite) {
        this.ignite = ignite;
        return this;
    }

    @Generated
    public TicketRegistryProperties setJpa(JpaTicketRegistryProperties jpa) {
        this.jpa = jpa;
        return this;
    }

    @Generated
    public TicketRegistryProperties setMemcached(MemcachedTicketRegistryProperties memcached) {
        this.memcached = memcached;
        return this;
    }

    @Generated
    public TicketRegistryProperties setRedis(RedisTicketRegistryProperties redis) {
        this.redis = redis;
        return this;
    }

    @Generated
    public TicketRegistryProperties setCassandra(CassandraTicketRegistryProperties cassandra) {
        this.cassandra = cassandra;
        return this;
    }

    @Generated
    public TicketRegistryProperties setInMemory(InMemoryTicketRegistryProperties inMemory) {
        this.inMemory = inMemory;
        return this;
    }

    @Generated
    public TicketRegistryProperties setCouchDb(CouchDbTicketRegistryProperties couchDb) {
        this.couchDb = couchDb;
        return this;
    }

    @Generated
    public TicketRegistryProperties setCleaner(ScheduledJobProperties cleaner) {
        this.cleaner = cleaner;
        return this;
    }

    @Generated
    public TicketRegistryProperties setCore(TicketRegistryCoreProperties core) {
        this.core = core;
        return this;
    }
}

