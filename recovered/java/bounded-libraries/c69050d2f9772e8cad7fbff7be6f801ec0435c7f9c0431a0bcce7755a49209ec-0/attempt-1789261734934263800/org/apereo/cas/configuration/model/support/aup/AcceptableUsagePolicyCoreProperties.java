/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.aup;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.features.CasFeatureModule;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-aup-webflow")
@JsonFilter(value="AcceptableUsagePolicyCoreProperties")
public class AcceptableUsagePolicyCoreProperties
implements Serializable,
CasFeatureModule {
    private static final long serialVersionUID = -7703477581675908899L;
    @RequiredProperty
    private boolean enabled = true;
    @RequiredProperty
    private String aupAttributeName = "aupAccepted";
    private String aupPolicyTermsAttributeName;
    private boolean aupOmitIfAttributeMissing;

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public String getAupAttributeName() {
        return this.aupAttributeName;
    }

    @Generated
    public String getAupPolicyTermsAttributeName() {
        return this.aupPolicyTermsAttributeName;
    }

    @Generated
    public boolean isAupOmitIfAttributeMissing() {
        return this.aupOmitIfAttributeMissing;
    }

    @Generated
    public AcceptableUsagePolicyCoreProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Generated
    public AcceptableUsagePolicyCoreProperties setAupAttributeName(String aupAttributeName) {
        this.aupAttributeName = aupAttributeName;
        return this;
    }

    @Generated
    public AcceptableUsagePolicyCoreProperties setAupPolicyTermsAttributeName(String aupPolicyTermsAttributeName) {
        this.aupPolicyTermsAttributeName = aupPolicyTermsAttributeName;
        return this;
    }

    @Generated
    public AcceptableUsagePolicyCoreProperties setAupOmitIfAttributeMissing(boolean aupOmitIfAttributeMissing) {
        this.aupOmitIfAttributeMissing = aupOmitIfAttributeMissing;
        return this;
    }
}

