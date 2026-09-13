/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.consent;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.SpringResourceProperties;
import org.apereo.cas.configuration.model.support.consent.ConsentCoreProperties;
import org.apereo.cas.configuration.model.support.consent.CouchDbConsentProperties;
import org.apereo.cas.configuration.model.support.consent.DynamoDbConsentProperties;
import org.apereo.cas.configuration.model.support.consent.GroovyConsentProperties;
import org.apereo.cas.configuration.model.support.consent.JpaConsentProperties;
import org.apereo.cas.configuration.model.support.consent.JsonConsentProperties;
import org.apereo.cas.configuration.model.support.consent.LdapConsentProperties;
import org.apereo.cas.configuration.model.support.consent.MongoDbConsentProperties;
import org.apereo.cas.configuration.model.support.consent.RedisConsentProperties;
import org.apereo.cas.configuration.model.support.consent.RestfulConsentProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-consent-webflow")
@JsonFilter(value="ConsentProperties")
public class ConsentProperties
implements Serializable {
    private static final long serialVersionUID = 5201308051524438384L;
    @NestedConfigurationProperty
    private SpringResourceProperties activationStrategyGroovyScript = new SpringResourceProperties();
    @NestedConfigurationProperty
    private ConsentCoreProperties core = new ConsentCoreProperties();
    @NestedConfigurationProperty
    private RestfulConsentProperties rest = new RestfulConsentProperties();
    @NestedConfigurationProperty
    private LdapConsentProperties ldap = new LdapConsentProperties();
    @NestedConfigurationProperty
    private JpaConsentProperties jpa = new JpaConsentProperties();
    @NestedConfigurationProperty
    private JsonConsentProperties json = new JsonConsentProperties();
    @NestedConfigurationProperty
    private RedisConsentProperties redis = new RedisConsentProperties();
    @NestedConfigurationProperty
    private GroovyConsentProperties groovy = new GroovyConsentProperties();
    @NestedConfigurationProperty
    private MongoDbConsentProperties mongo = new MongoDbConsentProperties();
    @NestedConfigurationProperty
    private CouchDbConsentProperties couchDb = new CouchDbConsentProperties();
    @NestedConfigurationProperty
    private DynamoDbConsentProperties dynamoDb = new DynamoDbConsentProperties();

    @Generated
    public SpringResourceProperties getActivationStrategyGroovyScript() {
        return this.activationStrategyGroovyScript;
    }

    @Generated
    public ConsentCoreProperties getCore() {
        return this.core;
    }

    @Generated
    public RestfulConsentProperties getRest() {
        return this.rest;
    }

    @Generated
    public LdapConsentProperties getLdap() {
        return this.ldap;
    }

    @Generated
    public JpaConsentProperties getJpa() {
        return this.jpa;
    }

    @Generated
    public JsonConsentProperties getJson() {
        return this.json;
    }

    @Generated
    public RedisConsentProperties getRedis() {
        return this.redis;
    }

    @Generated
    public GroovyConsentProperties getGroovy() {
        return this.groovy;
    }

    @Generated
    public MongoDbConsentProperties getMongo() {
        return this.mongo;
    }

    @Generated
    public CouchDbConsentProperties getCouchDb() {
        return this.couchDb;
    }

    @Generated
    public DynamoDbConsentProperties getDynamoDb() {
        return this.dynamoDb;
    }

    @Generated
    public ConsentProperties setActivationStrategyGroovyScript(SpringResourceProperties activationStrategyGroovyScript) {
        this.activationStrategyGroovyScript = activationStrategyGroovyScript;
        return this;
    }

    @Generated
    public ConsentProperties setCore(ConsentCoreProperties core) {
        this.core = core;
        return this;
    }

    @Generated
    public ConsentProperties setRest(RestfulConsentProperties rest) {
        this.rest = rest;
        return this;
    }

    @Generated
    public ConsentProperties setLdap(LdapConsentProperties ldap) {
        this.ldap = ldap;
        return this;
    }

    @Generated
    public ConsentProperties setJpa(JpaConsentProperties jpa) {
        this.jpa = jpa;
        return this;
    }

    @Generated
    public ConsentProperties setJson(JsonConsentProperties json) {
        this.json = json;
        return this;
    }

    @Generated
    public ConsentProperties setRedis(RedisConsentProperties redis) {
        this.redis = redis;
        return this;
    }

    @Generated
    public ConsentProperties setGroovy(GroovyConsentProperties groovy) {
        this.groovy = groovy;
        return this;
    }

    @Generated
    public ConsentProperties setMongo(MongoDbConsentProperties mongo) {
        this.mongo = mongo;
        return this;
    }

    @Generated
    public ConsentProperties setCouchDb(CouchDbConsentProperties couchDb) {
        this.couchDb = couchDb;
        return this;
    }

    @Generated
    public ConsentProperties setDynamoDb(DynamoDbConsentProperties dynamoDb) {
        this.dynamoDb = dynamoDb;
        return this;
    }
}

