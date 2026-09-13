/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.uma;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.uma.UmaCoreProperties;
import org.apereo.cas.configuration.model.support.uma.UmaPermissionTicketProperties;
import org.apereo.cas.configuration.model.support.uma.UmaRequestingPartyTokenProperties;
import org.apereo.cas.configuration.model.support.uma.UmaResourceSetProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-oauth-uma")
@JsonFilter(value="UmaProperties")
public class UmaProperties
implements Serializable {
    private static final long serialVersionUID = 865028615694269276L;
    @NestedConfigurationProperty
    private UmaCoreProperties core = new UmaCoreProperties();
    @NestedConfigurationProperty
    private UmaPermissionTicketProperties permissionTicket = new UmaPermissionTicketProperties();
    @NestedConfigurationProperty
    private UmaRequestingPartyTokenProperties requestingPartyToken = new UmaRequestingPartyTokenProperties();
    @NestedConfigurationProperty
    private UmaResourceSetProperties resourceSet = new UmaResourceSetProperties();

    @Generated
    public UmaCoreProperties getCore() {
        return this.core;
    }

    @Generated
    public UmaPermissionTicketProperties getPermissionTicket() {
        return this.permissionTicket;
    }

    @Generated
    public UmaRequestingPartyTokenProperties getRequestingPartyToken() {
        return this.requestingPartyToken;
    }

    @Generated
    public UmaResourceSetProperties getResourceSet() {
        return this.resourceSet;
    }

    @Generated
    public UmaProperties setCore(UmaCoreProperties core) {
        this.core = core;
        return this;
    }

    @Generated
    public UmaProperties setPermissionTicket(UmaPermissionTicketProperties permissionTicket) {
        this.permissionTicket = permissionTicket;
        return this;
    }

    @Generated
    public UmaProperties setRequestingPartyToken(UmaRequestingPartyTokenProperties requestingPartyToken) {
        this.requestingPartyToken = requestingPartyToken;
        return this;
    }

    @Generated
    public UmaProperties setResourceSet(UmaResourceSetProperties resourceSet) {
        this.resourceSet = resourceSet;
        return this;
    }
}

