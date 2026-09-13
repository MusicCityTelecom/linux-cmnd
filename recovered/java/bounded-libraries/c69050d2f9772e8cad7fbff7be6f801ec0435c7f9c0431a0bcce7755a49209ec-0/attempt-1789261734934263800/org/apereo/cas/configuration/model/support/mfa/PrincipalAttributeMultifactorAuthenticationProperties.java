/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.mfa;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.SpringResourceProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-core-authentication", automated=true)
@JsonFilter(value="PrincipalAttributeMultifactorAuthenticationProperties")
public class PrincipalAttributeMultifactorAuthenticationProperties
implements Serializable {
    private static final long serialVersionUID = 7426521468929733907L;
    @NestedConfigurationProperty
    private SpringResourceProperties globalPrincipalAttributePredicate = new SpringResourceProperties();
    private String globalPrincipalAttributeNameTriggers;
    private String globalPrincipalAttributeValueRegex;
    private boolean denyIfUnmatched;

    @Generated
    public SpringResourceProperties getGlobalPrincipalAttributePredicate() {
        return this.globalPrincipalAttributePredicate;
    }

    @Generated
    public String getGlobalPrincipalAttributeNameTriggers() {
        return this.globalPrincipalAttributeNameTriggers;
    }

    @Generated
    public String getGlobalPrincipalAttributeValueRegex() {
        return this.globalPrincipalAttributeValueRegex;
    }

    @Generated
    public boolean isDenyIfUnmatched() {
        return this.denyIfUnmatched;
    }

    @Generated
    public PrincipalAttributeMultifactorAuthenticationProperties setGlobalPrincipalAttributePredicate(SpringResourceProperties globalPrincipalAttributePredicate) {
        this.globalPrincipalAttributePredicate = globalPrincipalAttributePredicate;
        return this;
    }

    @Generated
    public PrincipalAttributeMultifactorAuthenticationProperties setGlobalPrincipalAttributeNameTriggers(String globalPrincipalAttributeNameTriggers) {
        this.globalPrincipalAttributeNameTriggers = globalPrincipalAttributeNameTriggers;
        return this;
    }

    @Generated
    public PrincipalAttributeMultifactorAuthenticationProperties setGlobalPrincipalAttributeValueRegex(String globalPrincipalAttributeValueRegex) {
        this.globalPrincipalAttributeValueRegex = globalPrincipalAttributeValueRegex;
        return this;
    }

    @Generated
    public PrincipalAttributeMultifactorAuthenticationProperties setDenyIfUnmatched(boolean denyIfUnmatched) {
        this.denyIfUnmatched = denyIfUnmatched;
        return this;
    }
}

