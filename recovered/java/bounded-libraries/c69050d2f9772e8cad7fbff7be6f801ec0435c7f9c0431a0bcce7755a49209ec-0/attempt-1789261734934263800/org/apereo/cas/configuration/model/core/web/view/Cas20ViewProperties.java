/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.core.web.view;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.web.view.Cas20ProxyViewProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-core-web", automated=true)
@JsonFilter(value="Cas20ViewProperties")
public class Cas20ViewProperties
implements Serializable {
    private static final long serialVersionUID = -7954879759474698003L;
    private String success = "protocol/2.0/casServiceValidationSuccess";
    private String failure = "protocol/2.0/casServiceValidationFailure";
    private boolean v3ForwardCompatible = true;
    @NestedConfigurationProperty
    private Cas20ProxyViewProperties proxy = new Cas20ProxyViewProperties();

    @Generated
    public String getSuccess() {
        return this.success;
    }

    @Generated
    public String getFailure() {
        return this.failure;
    }

    @Generated
    public boolean isV3ForwardCompatible() {
        return this.v3ForwardCompatible;
    }

    @Generated
    public Cas20ProxyViewProperties getProxy() {
        return this.proxy;
    }

    @Generated
    public Cas20ViewProperties setSuccess(String success) {
        this.success = success;
        return this;
    }

    @Generated
    public Cas20ViewProperties setFailure(String failure) {
        this.failure = failure;
        return this;
    }

    @Generated
    public Cas20ViewProperties setV3ForwardCompatible(boolean v3ForwardCompatible) {
        this.v3ForwardCompatible = v3ForwardCompatible;
        return this;
    }

    @Generated
    public Cas20ViewProperties setProxy(Cas20ProxyViewProperties proxy) {
        this.proxy = proxy;
        return this;
    }
}

