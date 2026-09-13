/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.authentication;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-authentication", automated=true)
@JsonFilter(value="RegisteredServiceAuthenticationHandlerResolution")
public class RegisteredServiceAuthenticationHandlerResolutionProperties
implements Serializable {
    private static final long serialVersionUID = 8079027843747126083L;
    private int order;

    @Generated
    public int getOrder() {
        return this.order;
    }

    @Generated
    public RegisteredServiceAuthenticationHandlerResolutionProperties setOrder(int order) {
        this.order = order;
        return this;
    }
}

