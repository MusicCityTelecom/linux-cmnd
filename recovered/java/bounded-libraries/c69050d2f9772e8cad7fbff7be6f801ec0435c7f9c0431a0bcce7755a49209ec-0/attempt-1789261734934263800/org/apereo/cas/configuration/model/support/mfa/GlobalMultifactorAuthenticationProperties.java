/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.mfa;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-core-authentication", automated=true)
@JsonFilter(value="GlobalMultifactorAuthenticationProperties")
public class GlobalMultifactorAuthenticationProperties
implements Serializable {
    private static final long serialVersionUID = 5426522468929733907L;
    private String globalProviderId;

    @Generated
    public String getGlobalProviderId() {
        return this.globalProviderId;
    }

    @Generated
    public GlobalMultifactorAuthenticationProperties setGlobalProviderId(String globalProviderId) {
        this.globalProviderId = globalProviderId;
        return this;
    }
}

