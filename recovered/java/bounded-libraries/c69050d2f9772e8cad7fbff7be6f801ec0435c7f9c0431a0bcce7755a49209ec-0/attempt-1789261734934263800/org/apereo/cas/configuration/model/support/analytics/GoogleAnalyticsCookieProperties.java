/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.analytics;

import lombok.Generated;
import org.apereo.cas.configuration.model.support.cookie.CookieProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-google-analytics")
public class GoogleAnalyticsCookieProperties
extends CookieProperties {
    private static final long serialVersionUID = -5432498833437602657L;
    private String attributeName;
    private String attributeValuePattern = ".+";

    public GoogleAnalyticsCookieProperties() {
        this.setName("CasGoogleAnalytics");
    }

    @Generated
    public String getAttributeName() {
        return this.attributeName;
    }

    @Generated
    public String getAttributeValuePattern() {
        return this.attributeValuePattern;
    }

    @Generated
    public GoogleAnalyticsCookieProperties setAttributeName(String attributeName) {
        this.attributeName = attributeName;
        return this;
    }

    @Generated
    public GoogleAnalyticsCookieProperties setAttributeValuePattern(String attributeValuePattern) {
        this.attributeValuePattern = attributeValuePattern;
        return this;
    }
}

