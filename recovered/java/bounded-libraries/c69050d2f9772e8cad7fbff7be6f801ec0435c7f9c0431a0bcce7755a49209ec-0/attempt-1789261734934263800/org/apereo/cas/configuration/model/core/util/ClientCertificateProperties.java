/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.util;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.SpringResourceProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-core-util", automated=true)
@JsonFilter(value="ClientCertificateProperties")
public class ClientCertificateProperties
implements Serializable {
    private static final long serialVersionUID = -8004292720523993292L;
    @RequiredProperty
    private transient SpringResourceProperties certificate = new SpringResourceProperties();
    @RequiredProperty
    private String passphrase;

    @Generated
    public SpringResourceProperties getCertificate() {
        return this.certificate;
    }

    @Generated
    public String getPassphrase() {
        return this.passphrase;
    }

    @Generated
    public ClientCertificateProperties setCertificate(SpringResourceProperties certificate) {
        this.certificate = certificate;
        return this;
    }

    @Generated
    public ClientCertificateProperties setPassphrase(String passphrase) {
        this.passphrase = passphrase;
        return this;
    }
}

