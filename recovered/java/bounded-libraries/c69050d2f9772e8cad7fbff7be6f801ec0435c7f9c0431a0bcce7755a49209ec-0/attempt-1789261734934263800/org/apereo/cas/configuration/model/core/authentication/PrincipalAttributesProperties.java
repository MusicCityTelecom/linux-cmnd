/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.core.authentication;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.authentication.AttributeDefinitionStoreProperties;
import org.apereo.cas.configuration.model.core.authentication.GroovyPrincipalAttributesProperties;
import org.apereo.cas.configuration.model.core.authentication.GrouperPrincipalAttributesProperties;
import org.apereo.cas.configuration.model.core.authentication.JsonPrincipalAttributesProperties;
import org.apereo.cas.configuration.model.core.authentication.PrincipalAttributesCoreProperties;
import org.apereo.cas.configuration.model.core.authentication.RestPrincipalAttributesProperties;
import org.apereo.cas.configuration.model.core.authentication.ScriptedPrincipalAttributesProperties;
import org.apereo.cas.configuration.model.core.authentication.StubPrincipalAttributesProperties;
import org.apereo.cas.configuration.model.support.azuread.AzureActiveDirectoryAttributesProperties;
import org.apereo.cas.configuration.model.support.couchbase.authentication.CouchbasePrincipalAttributesProperties;
import org.apereo.cas.configuration.model.support.jdbc.JdbcPrincipalAttributesProperties;
import org.apereo.cas.configuration.model.support.ldap.LdapPrincipalAttributesProperties;
import org.apereo.cas.configuration.model.support.okta.OktaPrincipalAttributesProperties;
import org.apereo.cas.configuration.model.support.redis.RedisPrincipalAttributesProperties;
import org.apereo.cas.configuration.model.support.syncope.SyncopePrincipalAttributesProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-core-authentication", automated=true)
@JsonFilter(value="PrincipalAttributesProperties")
public class PrincipalAttributesProperties
implements Serializable {
    private static final long serialVersionUID = -4515569588579072890L;
    @NestedConfigurationProperty
    private PrincipalAttributesCoreProperties core = new PrincipalAttributesCoreProperties();
    private List<JdbcPrincipalAttributesProperties> jdbc = new ArrayList<JdbcPrincipalAttributesProperties>(0);
    private List<AzureActiveDirectoryAttributesProperties> azureActiveDirectory = new ArrayList<AzureActiveDirectoryAttributesProperties>(0);
    private List<RestPrincipalAttributesProperties> rest = new ArrayList<RestPrincipalAttributesProperties>(0);
    private List<GroovyPrincipalAttributesProperties> groovy = new ArrayList<GroovyPrincipalAttributesProperties>(0);
    private List<LdapPrincipalAttributesProperties> ldap = new ArrayList<LdapPrincipalAttributesProperties>(0);
    private List<JsonPrincipalAttributesProperties> json = new ArrayList<JsonPrincipalAttributesProperties>(0);
    private List<RedisPrincipalAttributesProperties> redis = new ArrayList<RedisPrincipalAttributesProperties>(0);
    @NestedConfigurationProperty
    private CouchbasePrincipalAttributesProperties couchbase = new CouchbasePrincipalAttributesProperties();
    @Deprecated(since="6.2")
    private List<ScriptedPrincipalAttributesProperties> script = new ArrayList<ScriptedPrincipalAttributesProperties>(0);
    @NestedConfigurationProperty
    private StubPrincipalAttributesProperties stub = new StubPrincipalAttributesProperties();
    @NestedConfigurationProperty
    private GrouperPrincipalAttributesProperties grouper = new GrouperPrincipalAttributesProperties();
    @NestedConfigurationProperty
    private AttributeDefinitionStoreProperties attributeDefinitionStore = new AttributeDefinitionStoreProperties();
    @NestedConfigurationProperty
    private OktaPrincipalAttributesProperties okta = new OktaPrincipalAttributesProperties();
    @NestedConfigurationProperty
    private SyncopePrincipalAttributesProperties syncope = new SyncopePrincipalAttributesProperties();

    @Generated
    public PrincipalAttributesCoreProperties getCore() {
        return this.core;
    }

    @Generated
    public List<JdbcPrincipalAttributesProperties> getJdbc() {
        return this.jdbc;
    }

    @Generated
    public List<AzureActiveDirectoryAttributesProperties> getAzureActiveDirectory() {
        return this.azureActiveDirectory;
    }

    @Generated
    public List<RestPrincipalAttributesProperties> getRest() {
        return this.rest;
    }

    @Generated
    public List<GroovyPrincipalAttributesProperties> getGroovy() {
        return this.groovy;
    }

    @Generated
    public List<LdapPrincipalAttributesProperties> getLdap() {
        return this.ldap;
    }

    @Generated
    public List<JsonPrincipalAttributesProperties> getJson() {
        return this.json;
    }

    @Generated
    public List<RedisPrincipalAttributesProperties> getRedis() {
        return this.redis;
    }

    @Generated
    public CouchbasePrincipalAttributesProperties getCouchbase() {
        return this.couchbase;
    }

    @Deprecated
    @Generated
    public List<ScriptedPrincipalAttributesProperties> getScript() {
        return this.script;
    }

    @Generated
    public StubPrincipalAttributesProperties getStub() {
        return this.stub;
    }

    @Generated
    public GrouperPrincipalAttributesProperties getGrouper() {
        return this.grouper;
    }

    @Generated
    public AttributeDefinitionStoreProperties getAttributeDefinitionStore() {
        return this.attributeDefinitionStore;
    }

    @Generated
    public OktaPrincipalAttributesProperties getOkta() {
        return this.okta;
    }

    @Generated
    public SyncopePrincipalAttributesProperties getSyncope() {
        return this.syncope;
    }

    @Generated
    public PrincipalAttributesProperties setCore(PrincipalAttributesCoreProperties core) {
        this.core = core;
        return this;
    }

    @Generated
    public PrincipalAttributesProperties setJdbc(List<JdbcPrincipalAttributesProperties> jdbc) {
        this.jdbc = jdbc;
        return this;
    }

    @Generated
    public PrincipalAttributesProperties setAzureActiveDirectory(List<AzureActiveDirectoryAttributesProperties> azureActiveDirectory) {
        this.azureActiveDirectory = azureActiveDirectory;
        return this;
    }

    @Generated
    public PrincipalAttributesProperties setRest(List<RestPrincipalAttributesProperties> rest) {
        this.rest = rest;
        return this;
    }

    @Generated
    public PrincipalAttributesProperties setGroovy(List<GroovyPrincipalAttributesProperties> groovy) {
        this.groovy = groovy;
        return this;
    }

    @Generated
    public PrincipalAttributesProperties setLdap(List<LdapPrincipalAttributesProperties> ldap) {
        this.ldap = ldap;
        return this;
    }

    @Generated
    public PrincipalAttributesProperties setJson(List<JsonPrincipalAttributesProperties> json) {
        this.json = json;
        return this;
    }

    @Generated
    public PrincipalAttributesProperties setRedis(List<RedisPrincipalAttributesProperties> redis) {
        this.redis = redis;
        return this;
    }

    @Generated
    public PrincipalAttributesProperties setCouchbase(CouchbasePrincipalAttributesProperties couchbase) {
        this.couchbase = couchbase;
        return this;
    }

    @Deprecated
    @Generated
    public PrincipalAttributesProperties setScript(List<ScriptedPrincipalAttributesProperties> script) {
        this.script = script;
        return this;
    }

    @Generated
    public PrincipalAttributesProperties setStub(StubPrincipalAttributesProperties stub) {
        this.stub = stub;
        return this;
    }

    @Generated
    public PrincipalAttributesProperties setGrouper(GrouperPrincipalAttributesProperties grouper) {
        this.grouper = grouper;
        return this;
    }

    @Generated
    public PrincipalAttributesProperties setAttributeDefinitionStore(AttributeDefinitionStoreProperties attributeDefinitionStore) {
        this.attributeDefinitionStore = attributeDefinitionStore;
        return this;
    }

    @Generated
    public PrincipalAttributesProperties setOkta(OktaPrincipalAttributesProperties okta) {
        this.okta = okta;
        return this;
    }

    @Generated
    public PrincipalAttributesProperties setSyncope(SyncopePrincipalAttributesProperties syncope) {
        this.syncope = syncope;
        return this;
    }
}

