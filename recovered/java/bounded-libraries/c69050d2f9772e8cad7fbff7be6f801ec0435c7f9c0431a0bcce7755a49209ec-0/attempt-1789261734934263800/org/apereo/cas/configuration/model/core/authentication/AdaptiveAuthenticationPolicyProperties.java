/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.authentication;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.authentication.TimeBasedAuthenticationProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-core-authentication", automated=true)
@JsonFilter(value="AdaptiveAuthenticationPolicyProperties")
public class AdaptiveAuthenticationPolicyProperties
implements Serializable {
    private static final long serialVersionUID = -1840174229142982880L;
    private String rejectCountries;
    private String rejectBrowsers;
    private String rejectIpAddresses;
    private Map<String, String> requireMultifactor = new HashMap<String, String>(0);
    private List<TimeBasedAuthenticationProperties> requireTimedMultifactor = new ArrayList<TimeBasedAuthenticationProperties>(0);

    @Generated
    public String getRejectCountries() {
        return this.rejectCountries;
    }

    @Generated
    public String getRejectBrowsers() {
        return this.rejectBrowsers;
    }

    @Generated
    public String getRejectIpAddresses() {
        return this.rejectIpAddresses;
    }

    @Generated
    public Map<String, String> getRequireMultifactor() {
        return this.requireMultifactor;
    }

    @Generated
    public List<TimeBasedAuthenticationProperties> getRequireTimedMultifactor() {
        return this.requireTimedMultifactor;
    }

    @Generated
    public AdaptiveAuthenticationPolicyProperties setRejectCountries(String rejectCountries) {
        this.rejectCountries = rejectCountries;
        return this;
    }

    @Generated
    public AdaptiveAuthenticationPolicyProperties setRejectBrowsers(String rejectBrowsers) {
        this.rejectBrowsers = rejectBrowsers;
        return this;
    }

    @Generated
    public AdaptiveAuthenticationPolicyProperties setRejectIpAddresses(String rejectIpAddresses) {
        this.rejectIpAddresses = rejectIpAddresses;
        return this;
    }

    @Generated
    public AdaptiveAuthenticationPolicyProperties setRequireMultifactor(Map<String, String> requireMultifactor) {
        this.requireMultifactor = requireMultifactor;
        return this;
    }

    @Generated
    public AdaptiveAuthenticationPolicyProperties setRequireTimedMultifactor(List<TimeBasedAuthenticationProperties> requireTimedMultifactor) {
        this.requireTimedMultifactor = requireTimedMultifactor;
        return this;
    }
}

