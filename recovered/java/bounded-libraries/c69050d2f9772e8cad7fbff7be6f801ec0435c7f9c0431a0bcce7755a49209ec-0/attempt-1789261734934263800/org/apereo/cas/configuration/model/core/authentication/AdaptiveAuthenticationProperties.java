/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.core.authentication;

import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.authentication.AdaptiveAuthenticationIPIntelligenceProperties;
import org.apereo.cas.configuration.model.core.authentication.AdaptiveAuthenticationPolicyProperties;
import org.apereo.cas.configuration.model.core.authentication.RiskBasedAuthenticationProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-core-authentication", automated=true)
public class AdaptiveAuthenticationProperties
implements Serializable {
    private static final long serialVersionUID = -1840174229142982880L;
    @NestedConfigurationProperty
    private AdaptiveAuthenticationPolicyProperties policy = new AdaptiveAuthenticationPolicyProperties();
    @NestedConfigurationProperty
    private RiskBasedAuthenticationProperties risk = new RiskBasedAuthenticationProperties();
    @NestedConfigurationProperty
    private AdaptiveAuthenticationIPIntelligenceProperties ipIntel = new AdaptiveAuthenticationIPIntelligenceProperties();

    @Generated
    public AdaptiveAuthenticationPolicyProperties getPolicy() {
        return this.policy;
    }

    @Generated
    public RiskBasedAuthenticationProperties getRisk() {
        return this.risk;
    }

    @Generated
    public AdaptiveAuthenticationIPIntelligenceProperties getIpIntel() {
        return this.ipIntel;
    }

    @Generated
    public AdaptiveAuthenticationProperties setPolicy(AdaptiveAuthenticationPolicyProperties policy) {
        this.policy = policy;
        return this;
    }

    @Generated
    public AdaptiveAuthenticationProperties setRisk(RiskBasedAuthenticationProperties risk) {
        this.risk = risk;
        return this;
    }

    @Generated
    public AdaptiveAuthenticationProperties setIpIntel(AdaptiveAuthenticationIPIntelligenceProperties ipIntel) {
        this.ipIntel = ipIntel;
        return this;
    }
}

