/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.services;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-core-services", automated=true)
@JsonFilter(value="ServiceRegistryCoreProperties")
public class ServiceRegistryCoreProperties
implements Serializable {
    private static final long serialVersionUID = -268826011744304210L;
    private boolean initFromJson;
    private ServiceManagementTypes managementType = ServiceManagementTypes.DEFAULT;

    @Generated
    public boolean isInitFromJson() {
        return this.initFromJson;
    }

    @Generated
    public ServiceManagementTypes getManagementType() {
        return this.managementType;
    }

    @Generated
    public ServiceRegistryCoreProperties setInitFromJson(boolean initFromJson) {
        this.initFromJson = initFromJson;
        return this;
    }

    @Generated
    public ServiceRegistryCoreProperties setManagementType(ServiceManagementTypes managementType) {
        this.managementType = managementType;
        return this;
    }

    public static enum ServiceManagementTypes {
        DOMAIN,
        DEFAULT;

    }
}

