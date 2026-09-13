/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.saml.idp.metadata;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.saml.idp.metadata.AmazonS3SamlMetadataProperties;
import org.apereo.cas.configuration.model.support.saml.idp.metadata.CoreSamlMetadataProperties;
import org.apereo.cas.configuration.model.support.saml.idp.metadata.CouchDbSamlMetadataProperties;
import org.apereo.cas.configuration.model.support.saml.idp.metadata.FileSystemSamlMetadataProperties;
import org.apereo.cas.configuration.model.support.saml.idp.metadata.GitSamlMetadataProperties;
import org.apereo.cas.configuration.model.support.saml.idp.metadata.HttpSamlMetadataProperties;
import org.apereo.cas.configuration.model.support.saml.idp.metadata.JpaSamlMetadataProperties;
import org.apereo.cas.configuration.model.support.saml.idp.metadata.MDQSamlMetadataProperties;
import org.apereo.cas.configuration.model.support.saml.idp.metadata.MongoDbSamlMetadataProperties;
import org.apereo.cas.configuration.model.support.saml.idp.metadata.RedisSamlMetadataProperties;
import org.apereo.cas.configuration.model.support.saml.idp.metadata.RestSamlMetadataProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-saml-idp")
@JsonFilter(value="SamlIdPMetadataProperties")
public class SamlIdPMetadataProperties
implements Serializable {
    private static final long serialVersionUID = -1020542741768471305L;
    @NestedConfigurationProperty
    private CoreSamlMetadataProperties core = new CoreSamlMetadataProperties();
    @NestedConfigurationProperty
    private HttpSamlMetadataProperties http = new HttpSamlMetadataProperties();
    @NestedConfigurationProperty
    private FileSystemSamlMetadataProperties fileSystem = new FileSystemSamlMetadataProperties();
    @NestedConfigurationProperty
    private MongoDbSamlMetadataProperties mongo = new MongoDbSamlMetadataProperties();
    @NestedConfigurationProperty
    private RedisSamlMetadataProperties redis = new RedisSamlMetadataProperties();
    @NestedConfigurationProperty
    private GitSamlMetadataProperties git = new GitSamlMetadataProperties();
    @NestedConfigurationProperty
    private JpaSamlMetadataProperties jpa = new JpaSamlMetadataProperties();
    @NestedConfigurationProperty
    private RestSamlMetadataProperties rest = new RestSamlMetadataProperties();
    @NestedConfigurationProperty
    private AmazonS3SamlMetadataProperties amazonS3 = new AmazonS3SamlMetadataProperties();
    @NestedConfigurationProperty
    private CouchDbSamlMetadataProperties couchDb = new CouchDbSamlMetadataProperties();
    @NestedConfigurationProperty
    private MDQSamlMetadataProperties mdq = new MDQSamlMetadataProperties();

    @Generated
    public CoreSamlMetadataProperties getCore() {
        return this.core;
    }

    @Generated
    public HttpSamlMetadataProperties getHttp() {
        return this.http;
    }

    @Generated
    public FileSystemSamlMetadataProperties getFileSystem() {
        return this.fileSystem;
    }

    @Generated
    public MongoDbSamlMetadataProperties getMongo() {
        return this.mongo;
    }

    @Generated
    public RedisSamlMetadataProperties getRedis() {
        return this.redis;
    }

    @Generated
    public GitSamlMetadataProperties getGit() {
        return this.git;
    }

    @Generated
    public JpaSamlMetadataProperties getJpa() {
        return this.jpa;
    }

    @Generated
    public RestSamlMetadataProperties getRest() {
        return this.rest;
    }

    @Generated
    public AmazonS3SamlMetadataProperties getAmazonS3() {
        return this.amazonS3;
    }

    @Generated
    public CouchDbSamlMetadataProperties getCouchDb() {
        return this.couchDb;
    }

    @Generated
    public MDQSamlMetadataProperties getMdq() {
        return this.mdq;
    }

    @Generated
    public SamlIdPMetadataProperties setCore(CoreSamlMetadataProperties core) {
        this.core = core;
        return this;
    }

    @Generated
    public SamlIdPMetadataProperties setHttp(HttpSamlMetadataProperties http) {
        this.http = http;
        return this;
    }

    @Generated
    public SamlIdPMetadataProperties setFileSystem(FileSystemSamlMetadataProperties fileSystem) {
        this.fileSystem = fileSystem;
        return this;
    }

    @Generated
    public SamlIdPMetadataProperties setMongo(MongoDbSamlMetadataProperties mongo) {
        this.mongo = mongo;
        return this;
    }

    @Generated
    public SamlIdPMetadataProperties setRedis(RedisSamlMetadataProperties redis) {
        this.redis = redis;
        return this;
    }

    @Generated
    public SamlIdPMetadataProperties setGit(GitSamlMetadataProperties git) {
        this.git = git;
        return this;
    }

    @Generated
    public SamlIdPMetadataProperties setJpa(JpaSamlMetadataProperties jpa) {
        this.jpa = jpa;
        return this;
    }

    @Generated
    public SamlIdPMetadataProperties setRest(RestSamlMetadataProperties rest) {
        this.rest = rest;
        return this;
    }

    @Generated
    public SamlIdPMetadataProperties setAmazonS3(AmazonS3SamlMetadataProperties amazonS3) {
        this.amazonS3 = amazonS3;
        return this;
    }

    @Generated
    public SamlIdPMetadataProperties setCouchDb(CouchDbSamlMetadataProperties couchDb) {
        this.couchDb = couchDb;
        return this;
    }

    @Generated
    public SamlIdPMetadataProperties setMdq(MDQSamlMetadataProperties mdq) {
        this.mdq = mdq;
        return this;
    }
}

