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
@JsonFilter(value="AuthenticationAttributeMultifactorAuthenticationProperties")
public class AuthenticationAttributeMultifactorAuthenticationProperties
implements Serializable {
    private static final long serialVersionUID = 6426521468929733907L;
    private String globalAuthenticationAttributeNameTriggers;
    private String globalAuthenticationAttributeValueRegex;

    @Generated
    public String getGlobalAuthenticationAttributeNameTriggers() {
        return this.globalAuthenticationAttributeNameTriggers;
    }

    @Generated
    public String getGlobalAuthenticationAttributeValueRegex() {
        return this.globalAuthenticationAttributeValueRegex;
    }

    @Generated
    public AuthenticationAttributeMultifactorAuthenticationProperties setGlobalAuthenticationAttributeNameTriggers(String globalAuthenticationAttributeNameTriggers) {
        this.globalAuthenticationAttributeNameTriggers = globalAuthenticationAttributeNameTriggers;
        return this;
    }

    @Generated
    public AuthenticationAttributeMultifactorAuthenticationProperties setGlobalAuthenticationAttributeValueRegex(String globalAuthenticationAttributeValueRegex) {
        this.globalAuthenticationAttributeValueRegex = globalAuthenticationAttributeValueRegex;
        return this;
    }
}

