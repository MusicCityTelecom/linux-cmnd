/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.support.oidc.jwks;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.support.jpa.AbstractJpaProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-oidc")
@JsonFilter(value="JpaOidcJsonWebKeystoreProperties")
public class JpaOidcJsonWebKeystoreProperties
extends AbstractJpaProperties {
    private static final long serialVersionUID = 1633689616653363554L;

    public JpaOidcJsonWebKeystoreProperties() {
        this.setUrl(null);
    }
}

