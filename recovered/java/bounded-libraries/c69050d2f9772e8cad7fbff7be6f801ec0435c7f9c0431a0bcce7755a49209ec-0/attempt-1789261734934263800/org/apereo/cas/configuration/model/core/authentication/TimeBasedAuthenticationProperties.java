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
import java.util.List;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-core-authentication", automated=true)
@JsonFilter(value="TimeBasedAuthenticationProperties")
public class TimeBasedAuthenticationProperties
implements Serializable {
    private static final long serialVersionUID = 3826749727400569308L;
    @RequiredProperty
    private String providerId;
    private long onOrAfterHour = 20L;
    private long onOrBeforeHour = 7L;
    private List<String> onDays = new ArrayList<String>(0);

    @Generated
    public String getProviderId() {
        return this.providerId;
    }

    @Generated
    public long getOnOrAfterHour() {
        return this.onOrAfterHour;
    }

    @Generated
    public long getOnOrBeforeHour() {
        return this.onOrBeforeHour;
    }

    @Generated
    public List<String> getOnDays() {
        return this.onDays;
    }

    @Generated
    public TimeBasedAuthenticationProperties setProviderId(String providerId) {
        this.providerId = providerId;
        return this;
    }

    @Generated
    public TimeBasedAuthenticationProperties setOnOrAfterHour(long onOrAfterHour) {
        this.onOrAfterHour = onOrAfterHour;
        return this;
    }

    @Generated
    public TimeBasedAuthenticationProperties setOnOrBeforeHour(long onOrBeforeHour) {
        this.onOrBeforeHour = onOrBeforeHour;
        return this;
    }

    @Generated
    public TimeBasedAuthenticationProperties setOnDays(List<String> onDays) {
        this.onDays = onDays;
        return this;
    }
}

