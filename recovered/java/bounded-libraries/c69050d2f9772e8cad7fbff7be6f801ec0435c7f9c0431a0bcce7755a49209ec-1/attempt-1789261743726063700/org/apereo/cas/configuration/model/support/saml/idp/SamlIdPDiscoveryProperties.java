/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.saml.idp;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;
import org.apereo.cas.configuration.model.SpringResourceProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-saml-idp-discovery")
@JsonFilter(value="SamlIdPDiscoveryProperties")
public class SamlIdPDiscoveryProperties
implements Serializable {
    private static final long serialVersionUID = 3547093517788229284L;
    @NestedConfigurationProperty
    private List<SpringResourceProperties> resource = new ArrayList<SpringResourceProperties>(0);

    @Generated
    public List<SpringResourceProperties> getResource() {
        return this.resource;
    }

    @Generated
    public SamlIdPDiscoveryProperties setResource(List<SpringResourceProperties> resource) {
        this.resource = resource;
        return this;
    }
}

