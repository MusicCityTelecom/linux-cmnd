/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.account.provision;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.account.provision.GroovyAccountManagementRegistrationProvisioningProperties;
import org.apereo.cas.configuration.model.support.account.provision.RestfulAccountManagementRegistrationProvisioningProperties;
import org.apereo.cas.configuration.model.support.account.provision.ScimAccountManagementRegistrationProvisioningProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-account-mgmt")
@JsonFilter(value="AccountManagementRegistrationProvisioningProperties")
public class AccountManagementRegistrationProvisioningProperties
implements Serializable {
    private static final long serialVersionUID = -1279683905942523034L;
    @NestedConfigurationProperty
    private RestfulAccountManagementRegistrationProvisioningProperties rest = new RestfulAccountManagementRegistrationProvisioningProperties();
    @NestedConfigurationProperty
    private GroovyAccountManagementRegistrationProvisioningProperties groovy = new GroovyAccountManagementRegistrationProvisioningProperties();
    @NestedConfigurationProperty
    private ScimAccountManagementRegistrationProvisioningProperties scim = new ScimAccountManagementRegistrationProvisioningProperties();

    @Generated
    public RestfulAccountManagementRegistrationProvisioningProperties getRest() {
        return this.rest;
    }

    @Generated
    public GroovyAccountManagementRegistrationProvisioningProperties getGroovy() {
        return this.groovy;
    }

    @Generated
    public ScimAccountManagementRegistrationProvisioningProperties getScim() {
        return this.scim;
    }

    @Generated
    public AccountManagementRegistrationProvisioningProperties setRest(RestfulAccountManagementRegistrationProvisioningProperties rest) {
        this.rest = rest;
        return this;
    }

    @Generated
    public AccountManagementRegistrationProvisioningProperties setGroovy(GroovyAccountManagementRegistrationProvisioningProperties groovy) {
        this.groovy = groovy;
        return this;
    }

    @Generated
    public AccountManagementRegistrationProvisioningProperties setScim(ScimAccountManagementRegistrationProvisioningProperties scim) {
        this.scim = scim;
        return this;
    }
}

