/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.gua;

import lombok.Generated;
import org.apereo.cas.configuration.model.support.ldap.AbstractLdapSearchProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-gua")
public class LdapGraphicalUserAuthenticationProperties
extends AbstractLdapSearchProperties {
    private static final long serialVersionUID = 4666838063728336692L;
    @RequiredProperty
    private String imageAttribute;

    @Generated
    public String getImageAttribute() {
        return this.imageAttribute;
    }

    @Generated
    public LdapGraphicalUserAuthenticationProperties setImageAttribute(String imageAttribute) {
        this.imageAttribute = imageAttribute;
        return this;
    }
}

