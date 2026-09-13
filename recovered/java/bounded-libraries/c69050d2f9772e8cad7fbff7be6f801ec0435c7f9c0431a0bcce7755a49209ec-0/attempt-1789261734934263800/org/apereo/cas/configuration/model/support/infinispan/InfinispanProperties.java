/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 *  org.springframework.core.io.ClassPathResource
 *  org.springframework.core.io.Resource
 */
package org.apereo.cas.configuration.model.support.infinispan;

import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.util.EncryptionRandomizedSigningJwtCryptographyProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@RequiresModule(name="cas-server-support-infinispan-ticket-registry")
@Deprecated(since="6.6")
public class InfinispanProperties
implements Serializable {
    private static final long serialVersionUID = 1974626726565626634L;
    @RequiredProperty
    private transient Resource configLocation = new ClassPathResource("infinispan.xml");
    @RequiredProperty
    private String cacheName;
    @NestedConfigurationProperty
    private EncryptionRandomizedSigningJwtCryptographyProperties crypto = new EncryptionRandomizedSigningJwtCryptographyProperties();

    public InfinispanProperties() {
        this.crypto.setEnabled(false);
    }

    @Generated
    public Resource getConfigLocation() {
        return this.configLocation;
    }

    @Generated
    public String getCacheName() {
        return this.cacheName;
    }

    @Generated
    public EncryptionRandomizedSigningJwtCryptographyProperties getCrypto() {
        return this.crypto;
    }

    @Generated
    public InfinispanProperties setConfigLocation(Resource configLocation) {
        this.configLocation = configLocation;
        return this;
    }

    @Generated
    public InfinispanProperties setCacheName(String cacheName) {
        this.cacheName = cacheName;
        return this;
    }

    @Generated
    public InfinispanProperties setCrypto(EncryptionRandomizedSigningJwtCryptographyProperties crypto) {
        this.crypto = crypto;
        return this;
    }
}

