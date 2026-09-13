/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.x509;

import lombok.Generated;
import org.apereo.cas.configuration.model.support.ldap.AbstractLdapSearchProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-x509-webflow")
public class X509LdapProperties
extends AbstractLdapSearchProperties {
    private static final long serialVersionUID = -1655068554291000206L;
    private String certificateAttribute = "certificateRevocationList";

    @Generated
    public String getCertificateAttribute() {
        return this.certificateAttribute;
    }

    @Generated
    public void setCertificateAttribute(String certificateAttribute) {
        this.certificateAttribute = certificateAttribute;
    }
}

