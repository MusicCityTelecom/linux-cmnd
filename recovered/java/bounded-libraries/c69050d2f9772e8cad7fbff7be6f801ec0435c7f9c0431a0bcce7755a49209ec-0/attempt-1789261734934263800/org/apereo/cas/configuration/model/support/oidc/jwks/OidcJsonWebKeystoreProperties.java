/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.oidc.jwks;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.SpringResourceProperties;
import org.apereo.cas.configuration.model.support.oidc.jwks.FileSystemOidcJsonWebKeystoreProperties;
import org.apereo.cas.configuration.model.support.oidc.jwks.JpaOidcJsonWebKeystoreProperties;
import org.apereo.cas.configuration.model.support.oidc.jwks.MongoDbOidcJsonWebKeystoreProperties;
import org.apereo.cas.configuration.model.support.oidc.jwks.OidcJsonWebKeyStoreRevocationProperties;
import org.apereo.cas.configuration.model.support.oidc.jwks.OidcJsonWebKeyStoreRotationProperties;
import org.apereo.cas.configuration.model.support.oidc.jwks.OidcJsonWebKeystoreCoreProperties;
import org.apereo.cas.configuration.model.support.oidc.jwks.RestfulOidcJsonWebKeystoreProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-oidc")
@JsonFilter(value="OidcJsonWebKeystoreProperties")
public class OidcJsonWebKeystoreProperties
implements Serializable {
    private static final long serialVersionUID = -1696060572027445151L;
    @NestedConfigurationProperty
    private OidcJsonWebKeystoreCoreProperties core = new OidcJsonWebKeystoreCoreProperties();
    @NestedConfigurationProperty
    private FileSystemOidcJsonWebKeystoreProperties fileSystem = new FileSystemOidcJsonWebKeystoreProperties();
    @NestedConfigurationProperty
    private RestfulOidcJsonWebKeystoreProperties rest = new RestfulOidcJsonWebKeystoreProperties();
    @NestedConfigurationProperty
    private SpringResourceProperties groovy = new SpringResourceProperties();
    @NestedConfigurationProperty
    private MongoDbOidcJsonWebKeystoreProperties mongo = new MongoDbOidcJsonWebKeystoreProperties();
    @NestedConfigurationProperty
    private JpaOidcJsonWebKeystoreProperties jpa = new JpaOidcJsonWebKeystoreProperties();
    @NestedConfigurationProperty
    private OidcJsonWebKeyStoreRotationProperties rotation = new OidcJsonWebKeyStoreRotationProperties();
    @NestedConfigurationProperty
    private OidcJsonWebKeyStoreRevocationProperties revocation = new OidcJsonWebKeyStoreRevocationProperties();

    @Generated
    public OidcJsonWebKeystoreCoreProperties getCore() {
        return this.core;
    }

    @Generated
    public FileSystemOidcJsonWebKeystoreProperties getFileSystem() {
        return this.fileSystem;
    }

    @Generated
    public RestfulOidcJsonWebKeystoreProperties getRest() {
        return this.rest;
    }

    @Generated
    public SpringResourceProperties getGroovy() {
        return this.groovy;
    }

    @Generated
    public MongoDbOidcJsonWebKeystoreProperties getMongo() {
        return this.mongo;
    }

    @Generated
    public JpaOidcJsonWebKeystoreProperties getJpa() {
        return this.jpa;
    }

    @Generated
    public OidcJsonWebKeyStoreRotationProperties getRotation() {
        return this.rotation;
    }

    @Generated
    public OidcJsonWebKeyStoreRevocationProperties getRevocation() {
        return this.revocation;
    }

    @Generated
    public OidcJsonWebKeystoreProperties setCore(OidcJsonWebKeystoreCoreProperties core) {
        this.core = core;
        return this;
    }

    @Generated
    public OidcJsonWebKeystoreProperties setFileSystem(FileSystemOidcJsonWebKeystoreProperties fileSystem) {
        this.fileSystem = fileSystem;
        return this;
    }

    @Generated
    public OidcJsonWebKeystoreProperties setRest(RestfulOidcJsonWebKeystoreProperties rest) {
        this.rest = rest;
        return this;
    }

    @Generated
    public OidcJsonWebKeystoreProperties setGroovy(SpringResourceProperties groovy) {
        this.groovy = groovy;
        return this;
    }

    @Generated
    public OidcJsonWebKeystoreProperties setMongo(MongoDbOidcJsonWebKeystoreProperties mongo) {
        this.mongo = mongo;
        return this;
    }

    @Generated
    public OidcJsonWebKeystoreProperties setJpa(JpaOidcJsonWebKeystoreProperties jpa) {
        this.jpa = jpa;
        return this;
    }

    @Generated
    public OidcJsonWebKeystoreProperties setRotation(OidcJsonWebKeyStoreRotationProperties rotation) {
        this.rotation = rotation;
        return this;
    }

    @Generated
    public OidcJsonWebKeystoreProperties setRevocation(OidcJsonWebKeyStoreRevocationProperties revocation) {
        this.revocation = revocation;
        return this;
    }
}

