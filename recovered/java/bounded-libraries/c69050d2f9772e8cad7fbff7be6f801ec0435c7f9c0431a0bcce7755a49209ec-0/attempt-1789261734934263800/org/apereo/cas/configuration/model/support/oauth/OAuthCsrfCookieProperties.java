/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.support.oauth;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.support.cookie.CookieProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-core-cookie", automated=true)
@JsonFilter(value="OAuthCsrfCookieProperties")
public class OAuthCsrfCookieProperties
extends CookieProperties {
    private static final long serialVersionUID = 5298598088218873282L;

    public OAuthCsrfCookieProperties() {
        this.setSecure(false);
        this.setHttpOnly(false);
    }
}

