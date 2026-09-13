/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.saml.googleapps;

import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-saml-googleapps")
@Deprecated(since="6.2.0")
public class GoogleAppsProperties
implements Serializable {
    private static final long serialVersionUID = -5133482766495375325L;
    @Deprecated(since="6.2.0")
    @RequiredProperty
    private String publicKeyLocation = "file:/etc/cas/public.key";
    @Deprecated(since="6.2.0")
    @RequiredProperty
    private String privateKeyLocation = "file:/etc/cas/private.key";
    @Deprecated(since="6.2.0")
    private String keyAlgorithm = "RSA";

    @Deprecated
    @Generated
    public String getPublicKeyLocation() {
        return this.publicKeyLocation;
    }

    @Deprecated
    @Generated
    public String getPrivateKeyLocation() {
        return this.privateKeyLocation;
    }

    @Deprecated
    @Generated
    public String getKeyAlgorithm() {
        return this.keyAlgorithm;
    }

    @Deprecated
    @Generated
    public GoogleAppsProperties setPublicKeyLocation(String publicKeyLocation) {
        this.publicKeyLocation = publicKeyLocation;
        return this;
    }

    @Deprecated
    @Generated
    public GoogleAppsProperties setPrivateKeyLocation(String privateKeyLocation) {
        this.privateKeyLocation = privateKeyLocation;
        return this;
    }

    @Deprecated
    @Generated
    public GoogleAppsProperties setKeyAlgorithm(String keyAlgorithm) {
        this.keyAlgorithm = keyAlgorithm;
        return this;
    }
}

