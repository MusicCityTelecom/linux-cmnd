/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.x509;

import lombok.Generated;
import org.apereo.cas.configuration.model.support.x509.BaseAlternativePrincipalResolverProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-x509-webflow")
public class CnEdipiPrincipalResolverProperties
extends BaseAlternativePrincipalResolverProperties {
    private static final long serialVersionUID = 2622326703782668141L;
    private boolean extractEdipiAsAttribute;

    @Generated
    public boolean isExtractEdipiAsAttribute() {
        return this.extractEdipiAsAttribute;
    }

    @Generated
    public CnEdipiPrincipalResolverProperties setExtractEdipiAsAttribute(boolean extractEdipiAsAttribute) {
        this.extractEdipiAsAttribute = extractEdipiAsAttribute;
        return this;
    }
}

