/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.services.RegisteredService
 */
package org.apereo.cas.services;

import lombok.Generated;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.services.RegisteredService;

public class ServiceContext {
    private final Service service;
    private final RegisteredService registeredService;

    @Generated
    public Service getService() {
        return this.service;
    }

    @Generated
    public RegisteredService getRegisteredService() {
        return this.registeredService;
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ServiceContext)) {
            return false;
        }
        ServiceContext other = (ServiceContext)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Service this$service = this.service;
        Service other$service = other.service;
        if (this$service == null ? other$service != null : !this$service.equals(other$service)) {
            return false;
        }
        RegisteredService this$registeredService = this.registeredService;
        RegisteredService other$registeredService = other.registeredService;
        return !(this$registeredService == null ? other$registeredService != null : !this$registeredService.equals(other$registeredService));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof ServiceContext;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Service $service = this.service;
        result = result * 59 + ($service == null ? 43 : $service.hashCode());
        RegisteredService $registeredService = this.registeredService;
        result = result * 59 + ($registeredService == null ? 43 : $registeredService.hashCode());
        return result;
    }

    @Generated
    public String toString() {
        return "ServiceContext(service=" + this.service + ", registeredService=" + this.registeredService + ")";
    }

    @Generated
    public ServiceContext(Service service, RegisteredService registeredService) {
        this.service = service;
        this.registeredService = registeredService;
    }
}

