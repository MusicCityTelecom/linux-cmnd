/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.analytics;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.analytics.GoogleAnalyticsCookieProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-google-analytics")
@JsonFilter(value="GoogleAnalyticsProperties")
public class GoogleAnalyticsProperties
implements Serializable {
    private static final long serialVersionUID = 5425678120443123345L;
    @RequiredProperty
    private String googleAnalyticsTrackingId;
    @NestedConfigurationProperty
    private GoogleAnalyticsCookieProperties cookie = new GoogleAnalyticsCookieProperties();

    @Generated
    public String getGoogleAnalyticsTrackingId() {
        return this.googleAnalyticsTrackingId;
    }

    @Generated
    public GoogleAnalyticsCookieProperties getCookie() {
        return this.cookie;
    }

    @Generated
    public GoogleAnalyticsProperties setGoogleAnalyticsTrackingId(String googleAnalyticsTrackingId) {
        this.googleAnalyticsTrackingId = googleAnalyticsTrackingId;
        return this;
    }

    @Generated
    public GoogleAnalyticsProperties setCookie(GoogleAnalyticsCookieProperties cookie) {
        this.cookie = cookie;
        return this;
    }
}

