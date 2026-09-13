/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.oidc;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-oidc")
@JsonFilter(value="OidcLogoutProperties")
public class OidcLogoutProperties
implements Serializable {
    private static final long serialVersionUID = 4988981831781991817L;
    private boolean backchannelLogoutSupported = true;
    private boolean frontchannelLogoutSupported = true;

    @Generated
    public boolean isBackchannelLogoutSupported() {
        return this.backchannelLogoutSupported;
    }

    @Generated
    public boolean isFrontchannelLogoutSupported() {
        return this.frontchannelLogoutSupported;
    }

    @Generated
    public OidcLogoutProperties setBackchannelLogoutSupported(boolean backchannelLogoutSupported) {
        this.backchannelLogoutSupported = backchannelLogoutSupported;
        return this;
    }

    @Generated
    public OidcLogoutProperties setFrontchannelLogoutSupported(boolean frontchannelLogoutSupported) {
        this.frontchannelLogoutSupported = frontchannelLogoutSupported;
        return this;
    }
}

