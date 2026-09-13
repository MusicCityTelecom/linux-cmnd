/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.x509;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-x509-webflow")
@JsonFilter(value="BaseAlternativePrincipalResolverProperties")
public abstract class BaseAlternativePrincipalResolverProperties
implements Serializable {
    private static final long serialVersionUID = 4770829035414038072L;
    private String alternatePrincipalAttribute;

    @Generated
    public String getAlternatePrincipalAttribute() {
        return this.alternatePrincipalAttribute;
    }

    @Generated
    public BaseAlternativePrincipalResolverProperties setAlternatePrincipalAttribute(String alternatePrincipalAttribute) {
        this.alternatePrincipalAttribute = alternatePrincipalAttribute;
        return this;
    }
}

