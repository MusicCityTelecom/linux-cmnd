/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.saml.idp.profile;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-saml-idp")
@JsonFilter(value="SamlIdPBaseProfileProperties")
public class SamlIdPBaseProfileProperties
implements Serializable {
    private static final long serialVersionUID = -8100516679034234656L;
    private boolean urlDecodeRedirectRequest;

    @Generated
    public boolean isUrlDecodeRedirectRequest() {
        return this.urlDecodeRedirectRequest;
    }

    @Generated
    public SamlIdPBaseProfileProperties setUrlDecodeRedirectRequest(boolean urlDecodeRedirectRequest) {
        this.urlDecodeRedirectRequest = urlDecodeRedirectRequest;
        return this;
    }
}

