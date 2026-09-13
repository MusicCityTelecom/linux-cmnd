/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.support.account.provision;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.SpringResourceProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-account-mgmt")
@JsonFilter(value="GroovyAccountManagementRegistrationProvisioningProperties")
public class GroovyAccountManagementRegistrationProvisioningProperties
extends SpringResourceProperties {
    private static final long serialVersionUID = 6855936824474022021L;
}

