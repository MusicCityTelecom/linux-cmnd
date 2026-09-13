/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.openid;

import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.authentication.PersonDirectoryPrincipalResolverProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-openid-webflow")
@Deprecated(since="6.2.0")
public class OpenIdProperties
implements Serializable {
    private static final long serialVersionUID = -2935759289483632610L;
    @NestedConfigurationProperty
    @Deprecated(since="6.2.0")
    private PersonDirectoryPrincipalResolverProperties principal = new PersonDirectoryPrincipalResolverProperties();
    @Deprecated(since="6.2.0")
    private boolean enforceRpId;
    @Deprecated(since="6.2.0")
    private String name;
    @Deprecated(since="6.2.0")
    private Integer order;

    @Deprecated
    @Generated
    public PersonDirectoryPrincipalResolverProperties getPrincipal() {
        return this.principal;
    }

    @Deprecated
    @Generated
    public boolean isEnforceRpId() {
        return this.enforceRpId;
    }

    @Deprecated
    @Generated
    public String getName() {
        return this.name;
    }

    @Deprecated
    @Generated
    public Integer getOrder() {
        return this.order;
    }

    @Deprecated
    @Generated
    public OpenIdProperties setPrincipal(PersonDirectoryPrincipalResolverProperties principal) {
        this.principal = principal;
        return this;
    }

    @Deprecated
    @Generated
    public OpenIdProperties setEnforceRpId(boolean enforceRpId) {
        this.enforceRpId = enforceRpId;
        return this;
    }

    @Deprecated
    @Generated
    public OpenIdProperties setName(String name) {
        this.name = name;
        return this;
    }

    @Deprecated
    @Generated
    public OpenIdProperties setOrder(Integer order) {
        this.order = order;
        return this;
    }
}

