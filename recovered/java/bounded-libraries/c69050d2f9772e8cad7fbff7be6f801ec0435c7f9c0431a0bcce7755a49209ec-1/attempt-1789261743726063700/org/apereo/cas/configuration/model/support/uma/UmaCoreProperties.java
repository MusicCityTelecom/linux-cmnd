/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.uma;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-oauth-uma")
@JsonFilter(value="UmaCoreProperties")
public class UmaCoreProperties
implements Serializable {
    private static final long serialVersionUID = 865028615694269276L;
    @RequiredProperty
    private String issuer = "http://localhost:8080/cas";

    @Generated
    public String getIssuer() {
        return this.issuer;
    }

    @Generated
    public UmaCoreProperties setIssuer(String issuer) {
        this.issuer = issuer;
        return this;
    }
}

