/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.core.services;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.services.RestfulServiceRegistryProperties;
import org.apereo.cas.configuration.model.core.services.ServiceRegistryCacheProperties;
import org.apereo.cas.configuration.model.core.services.ServiceRegistryCoreProperties;
import org.apereo.cas.configuration.model.support.aws.AmazonS3ServiceRegistryProperties;
import org.apereo.cas.configuration.model.support.cassandra.serviceregistry.CassandraServiceRegistryProperties;
import org.apereo.cas.configuration.model.support.cosmosdb.CosmosDbServiceRegistryProperties;
import org.apereo.cas.configuration.model.support.couchbase.serviceregistry.CouchbaseServiceRegistryProperties;
import org.apereo.cas.configuration.model.support.couchdb.serviceregistry.CouchDbServiceRegistryProperties;
import org.apereo.cas.configuration.model.support.dynamodb.DynamoDbServiceRegistryProperties;
import org.apereo.cas.configuration.model.support.email.EmailProperties;
import org.apereo.cas.configuration.model.support.git.services.GitServiceRegistryProperties;
import org.apereo.cas.configuration.model.support.jpa.serviceregistry.JpaServiceRegistryProperties;
import org.apereo.cas.configuration.model.support.ldap.serviceregistry.LdapServiceRegistryProperties;
import org.apereo.cas.configuration.model.support.mongo.serviceregistry.MongoDbServiceRegistryProperties;
import org.apereo.cas.configuration.model.support.quartz.SchedulingProperties;
import org.apereo.cas.configuration.model.support.redis.RedisServiceRegistryProperties;
import org.apereo.cas.configuration.model.support.services.json.JsonServiceRegistryProperties;
import org.apereo.cas.configuration.model.support.services.stream.StreamingServiceRegistryProperties;
import org.apereo.cas.configuration.model.support.services.yaml.YamlServiceRegistryProperties;
import org.apereo.cas.configuration.model.support.sms.SmsProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-core-services", automated=true)
@JsonFilter(value="ServiceRegistryProperties")
public class ServiceRegistryProperties
implements Serializable {
    private static final long serialVersionUID = -368826011744304210L;
    @NestedConfigurationProperty
    private CosmosDbServiceRegistryProperties cosmosDb = new CosmosDbServiceRegistryProperties();
    @NestedConfigurationProperty
    private CassandraServiceRegistryProperties cassandra = new CassandraServiceRegistryProperties();
    @NestedConfigurationProperty
    private GitServiceRegistryProperties git = new GitServiceRegistryProperties();
    @NestedConfigurationProperty
    private CouchDbServiceRegistryProperties couchDb = new CouchDbServiceRegistryProperties();
    @NestedConfigurationProperty
    private RestfulServiceRegistryProperties rest = new RestfulServiceRegistryProperties();
    @NestedConfigurationProperty
    private RedisServiceRegistryProperties redis = new RedisServiceRegistryProperties();
    @NestedConfigurationProperty
    private JsonServiceRegistryProperties json = new JsonServiceRegistryProperties();
    @NestedConfigurationProperty
    private YamlServiceRegistryProperties yaml = new YamlServiceRegistryProperties();
    @NestedConfigurationProperty
    private JpaServiceRegistryProperties jpa = new JpaServiceRegistryProperties();
    @NestedConfigurationProperty
    private LdapServiceRegistryProperties ldap = new LdapServiceRegistryProperties();
    @NestedConfigurationProperty
    private MongoDbServiceRegistryProperties mongo = new MongoDbServiceRegistryProperties();
    @NestedConfigurationProperty
    private CouchbaseServiceRegistryProperties couchbase = new CouchbaseServiceRegistryProperties();
    @NestedConfigurationProperty
    private DynamoDbServiceRegistryProperties dynamoDb = new DynamoDbServiceRegistryProperties();
    @NestedConfigurationProperty
    private AmazonS3ServiceRegistryProperties amazonS3 = new AmazonS3ServiceRegistryProperties();
    @NestedConfigurationProperty
    private StreamingServiceRegistryProperties stream = new StreamingServiceRegistryProperties();
    @NestedConfigurationProperty
    private SchedulingProperties schedule = new SchedulingProperties();
    @NestedConfigurationProperty
    private EmailProperties mail = new EmailProperties();
    @NestedConfigurationProperty
    private SmsProperties sms = new SmsProperties();
    @NestedConfigurationProperty
    private ServiceRegistryCacheProperties cache = new ServiceRegistryCacheProperties();
    @NestedConfigurationProperty
    private ServiceRegistryCoreProperties core = new ServiceRegistryCoreProperties();

    @Generated
    public CosmosDbServiceRegistryProperties getCosmosDb() {
        return this.cosmosDb;
    }

    @Generated
    public CassandraServiceRegistryProperties getCassandra() {
        return this.cassandra;
    }

    @Generated
    public GitServiceRegistryProperties getGit() {
        return this.git;
    }

    @Generated
    public CouchDbServiceRegistryProperties getCouchDb() {
        return this.couchDb;
    }

    @Generated
    public RestfulServiceRegistryProperties getRest() {
        return this.rest;
    }

    @Generated
    public RedisServiceRegistryProperties getRedis() {
        return this.redis;
    }

    @Generated
    public JsonServiceRegistryProperties getJson() {
        return this.json;
    }

    @Generated
    public YamlServiceRegistryProperties getYaml() {
        return this.yaml;
    }

    @Generated
    public JpaServiceRegistryProperties getJpa() {
        return this.jpa;
    }

    @Generated
    public LdapServiceRegistryProperties getLdap() {
        return this.ldap;
    }

    @Generated
    public MongoDbServiceRegistryProperties getMongo() {
        return this.mongo;
    }

    @Generated
    public CouchbaseServiceRegistryProperties getCouchbase() {
        return this.couchbase;
    }

    @Generated
    public DynamoDbServiceRegistryProperties getDynamoDb() {
        return this.dynamoDb;
    }

    @Generated
    public AmazonS3ServiceRegistryProperties getAmazonS3() {
        return this.amazonS3;
    }

    @Generated
    public StreamingServiceRegistryProperties getStream() {
        return this.stream;
    }

    @Generated
    public SchedulingProperties getSchedule() {
        return this.schedule;
    }

    @Generated
    public EmailProperties getMail() {
        return this.mail;
    }

    @Generated
    public SmsProperties getSms() {
        return this.sms;
    }

    @Generated
    public ServiceRegistryCacheProperties getCache() {
        return this.cache;
    }

    @Generated
    public ServiceRegistryCoreProperties getCore() {
        return this.core;
    }

    @Generated
    public ServiceRegistryProperties setCosmosDb(CosmosDbServiceRegistryProperties cosmosDb) {
        this.cosmosDb = cosmosDb;
        return this;
    }

    @Generated
    public ServiceRegistryProperties setCassandra(CassandraServiceRegistryProperties cassandra) {
        this.cassandra = cassandra;
        return this;
    }

    @Generated
    public ServiceRegistryProperties setGit(GitServiceRegistryProperties git) {
        this.git = git;
        return this;
    }

    @Generated
    public ServiceRegistryProperties setCouchDb(CouchDbServiceRegistryProperties couchDb) {
        this.couchDb = couchDb;
        return this;
    }

    @Generated
    public ServiceRegistryProperties setRest(RestfulServiceRegistryProperties rest) {
        this.rest = rest;
        return this;
    }

    @Generated
    public ServiceRegistryProperties setRedis(RedisServiceRegistryProperties redis) {
        this.redis = redis;
        return this;
    }

    @Generated
    public ServiceRegistryProperties setJson(JsonServiceRegistryProperties json) {
        this.json = json;
        return this;
    }

    @Generated
    public ServiceRegistryProperties setYaml(YamlServiceRegistryProperties yaml) {
        this.yaml = yaml;
        return this;
    }

    @Generated
    public ServiceRegistryProperties setJpa(JpaServiceRegistryProperties jpa) {
        this.jpa = jpa;
        return this;
    }

    @Generated
    public ServiceRegistryProperties setLdap(LdapServiceRegistryProperties ldap) {
        this.ldap = ldap;
        return this;
    }

    @Generated
    public ServiceRegistryProperties setMongo(MongoDbServiceRegistryProperties mongo) {
        this.mongo = mongo;
        return this;
    }

    @Generated
    public ServiceRegistryProperties setCouchbase(CouchbaseServiceRegistryProperties couchbase) {
        this.couchbase = couchbase;
        return this;
    }

    @Generated
    public ServiceRegistryProperties setDynamoDb(DynamoDbServiceRegistryProperties dynamoDb) {
        this.dynamoDb = dynamoDb;
        return this;
    }

    @Generated
    public ServiceRegistryProperties setAmazonS3(AmazonS3ServiceRegistryProperties amazonS3) {
        this.amazonS3 = amazonS3;
        return this;
    }

    @Generated
    public ServiceRegistryProperties setStream(StreamingServiceRegistryProperties stream) {
        this.stream = stream;
        return this;
    }

    @Generated
    public ServiceRegistryProperties setSchedule(SchedulingProperties schedule) {
        this.schedule = schedule;
        return this;
    }

    @Generated
    public ServiceRegistryProperties setMail(EmailProperties mail) {
        this.mail = mail;
        return this;
    }

    @Generated
    public ServiceRegistryProperties setSms(SmsProperties sms) {
        this.sms = sms;
        return this;
    }

    @Generated
    public ServiceRegistryProperties setCache(ServiceRegistryCacheProperties cache) {
        this.cache = cache;
        return this;
    }

    @Generated
    public ServiceRegistryProperties setCore(ServiceRegistryCoreProperties core) {
        this.core = core;
        return this;
    }
}

