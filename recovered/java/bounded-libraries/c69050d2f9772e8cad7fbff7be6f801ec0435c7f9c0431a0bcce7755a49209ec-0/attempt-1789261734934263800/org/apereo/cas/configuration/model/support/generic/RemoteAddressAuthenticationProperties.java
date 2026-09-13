/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.generic;

import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-generic-remote-webflow")
public class RemoteAddressAuthenticationProperties
implements Serializable {
    private static final long serialVersionUID = 573409035023089696L;
    @RequiredProperty
    private String ipAddressRange = "";
    private String name;
    private Integer order;

    @Generated
    public String getIpAddressRange() {
        return this.ipAddressRange;
    }

    @Generated
    public String getName() {
        return this.name;
    }

    @Generated
    public Integer getOrder() {
        return this.order;
    }

    @Generated
    public RemoteAddressAuthenticationProperties setIpAddressRange(String ipAddressRange) {
        this.ipAddressRange = ipAddressRange;
        return this;
    }

    @Generated
    public RemoteAddressAuthenticationProperties setName(String name) {
        this.name = name;
        return this;
    }

    @Generated
    public RemoteAddressAuthenticationProperties setOrder(Integer order) {
        this.order = order;
        return this;
    }
}

