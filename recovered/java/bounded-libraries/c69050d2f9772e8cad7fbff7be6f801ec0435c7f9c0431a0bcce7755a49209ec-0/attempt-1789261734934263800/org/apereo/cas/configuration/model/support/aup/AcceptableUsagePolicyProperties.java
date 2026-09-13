/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.aup;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.aup.AcceptableUsagePolicyCoreProperties;
import org.apereo.cas.configuration.model.support.aup.CouchDbAcceptableUsagePolicyProperties;
import org.apereo.cas.configuration.model.support.aup.CouchbaseAcceptableUsagePolicyProperties;
import org.apereo.cas.configuration.model.support.aup.GroovyAcceptableUsagePolicyProperties;
import org.apereo.cas.configuration.model.support.aup.InMemoryAcceptableUsagePolicyProperties;
import org.apereo.cas.configuration.model.support.aup.JdbcAcceptableUsagePolicyProperties;
import org.apereo.cas.configuration.model.support.aup.LdapAcceptableUsagePolicyProperties;
import org.apereo.cas.configuration.model.support.aup.MongoDbAcceptableUsagePolicyProperties;
import org.apereo.cas.configuration.model.support.aup.RedisAcceptableUsagePolicyProperties;
import org.apereo.cas.configuration.model.support.aup.RestAcceptableUsagePolicyProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-aup-webflow")
@JsonFilter(value="AcceptableUsagePolicyProperties")
public class AcceptableUsagePolicyProperties
implements Serializable {
    private static final long serialVersionUID = -7703477581675908899L;
    private List<LdapAcceptableUsagePolicyProperties> ldap = new ArrayList<LdapAcceptableUsagePolicyProperties>();
    @NestedConfigurationProperty
    private JdbcAcceptableUsagePolicyProperties jdbc = new JdbcAcceptableUsagePolicyProperties();
    @NestedConfigurationProperty
    private RestAcceptableUsagePolicyProperties rest = new RestAcceptableUsagePolicyProperties();
    @NestedConfigurationProperty
    private CouchDbAcceptableUsagePolicyProperties couchDb = new CouchDbAcceptableUsagePolicyProperties();
    @NestedConfigurationProperty
    private CouchbaseAcceptableUsagePolicyProperties couchbase = new CouchbaseAcceptableUsagePolicyProperties();
    @NestedConfigurationProperty
    private MongoDbAcceptableUsagePolicyProperties mongo = new MongoDbAcceptableUsagePolicyProperties();
    @NestedConfigurationProperty
    private GroovyAcceptableUsagePolicyProperties groovy = new GroovyAcceptableUsagePolicyProperties();
    @NestedConfigurationProperty
    private RedisAcceptableUsagePolicyProperties redis = new RedisAcceptableUsagePolicyProperties();
    @NestedConfigurationProperty
    private InMemoryAcceptableUsagePolicyProperties inMemory = new InMemoryAcceptableUsagePolicyProperties();
    @NestedConfigurationProperty
    private AcceptableUsagePolicyCoreProperties core = new AcceptableUsagePolicyCoreProperties();

    @Generated
    public List<LdapAcceptableUsagePolicyProperties> getLdap() {
        return this.ldap;
    }

    @Generated
    public JdbcAcceptableUsagePolicyProperties getJdbc() {
        return this.jdbc;
    }

    @Generated
    public RestAcceptableUsagePolicyProperties getRest() {
        return this.rest;
    }

    @Generated
    public CouchDbAcceptableUsagePolicyProperties getCouchDb() {
        return this.couchDb;
    }

    @Generated
    public CouchbaseAcceptableUsagePolicyProperties getCouchbase() {
        return this.couchbase;
    }

    @Generated
    public MongoDbAcceptableUsagePolicyProperties getMongo() {
        return this.mongo;
    }

    @Generated
    public GroovyAcceptableUsagePolicyProperties getGroovy() {
        return this.groovy;
    }

    @Generated
    public RedisAcceptableUsagePolicyProperties getRedis() {
        return this.redis;
    }

    @Generated
    public InMemoryAcceptableUsagePolicyProperties getInMemory() {
        return this.inMemory;
    }

    @Generated
    public AcceptableUsagePolicyCoreProperties getCore() {
        return this.core;
    }

    @Generated
    public AcceptableUsagePolicyProperties setLdap(List<LdapAcceptableUsagePolicyProperties> ldap) {
        this.ldap = ldap;
        return this;
    }

    @Generated
    public AcceptableUsagePolicyProperties setJdbc(JdbcAcceptableUsagePolicyProperties jdbc) {
        this.jdbc = jdbc;
        return this;
    }

    @Generated
    public AcceptableUsagePolicyProperties setRest(RestAcceptableUsagePolicyProperties rest) {
        this.rest = rest;
        return this;
    }

    @Generated
    public AcceptableUsagePolicyProperties setCouchDb(CouchDbAcceptableUsagePolicyProperties couchDb) {
        this.couchDb = couchDb;
        return this;
    }

    @Generated
    public AcceptableUsagePolicyProperties setCouchbase(CouchbaseAcceptableUsagePolicyProperties couchbase) {
        this.couchbase = couchbase;
        return this;
    }

    @Generated
    public AcceptableUsagePolicyProperties setMongo(MongoDbAcceptableUsagePolicyProperties mongo) {
        this.mongo = mongo;
        return this;
    }

    @Generated
    public AcceptableUsagePolicyProperties setGroovy(GroovyAcceptableUsagePolicyProperties groovy) {
        this.groovy = groovy;
        return this;
    }

    @Generated
    public AcceptableUsagePolicyProperties setRedis(RedisAcceptableUsagePolicyProperties redis) {
        this.redis = redis;
        return this;
    }

    @Generated
    public AcceptableUsagePolicyProperties setInMemory(InMemoryAcceptableUsagePolicyProperties inMemory) {
        this.inMemory = inMemory;
        return this;
    }

    @Generated
    public AcceptableUsagePolicyProperties setCore(AcceptableUsagePolicyCoreProperties core) {
        this.core = core;
        return this;
    }
}

