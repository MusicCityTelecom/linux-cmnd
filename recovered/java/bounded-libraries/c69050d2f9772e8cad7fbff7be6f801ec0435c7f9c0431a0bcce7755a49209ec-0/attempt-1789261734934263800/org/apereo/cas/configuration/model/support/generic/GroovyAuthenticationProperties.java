/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.generic;

import lombok.Generated;
import org.apereo.cas.configuration.model.SpringResourceProperties;
import org.apereo.cas.configuration.model.core.authentication.AuthenticationHandlerStates;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-generic")
public class GroovyAuthenticationProperties
extends SpringResourceProperties {
    private static final long serialVersionUID = 2179027841236526083L;
    private String name;
    private Integer order;
    private AuthenticationHandlerStates state = AuthenticationHandlerStates.ACTIVE;

    @Generated
    public String getName() {
        return this.name;
    }

    @Generated
    public Integer getOrder() {
        return this.order;
    }

    @Generated
    public AuthenticationHandlerStates getState() {
        return this.state;
    }

    @Generated
    public GroovyAuthenticationProperties setName(String name) {
        this.name = name;
        return this;
    }

    @Generated
    public GroovyAuthenticationProperties setOrder(Integer order) {
        this.order = order;
        return this;
    }

    @Generated
    public GroovyAuthenticationProperties setState(AuthenticationHandlerStates state) {
        this.state = state;
        return this;
    }
}

