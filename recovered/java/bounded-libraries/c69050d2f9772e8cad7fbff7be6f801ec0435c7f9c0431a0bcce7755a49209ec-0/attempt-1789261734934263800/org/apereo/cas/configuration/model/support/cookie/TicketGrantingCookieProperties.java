/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.cookie;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.util.EncryptionJwtSigningJwtCryptographyProperties;
import org.apereo.cas.configuration.model.support.cookie.PinnableCookieProperties;
import org.apereo.cas.configuration.support.DurationCapable;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-core-cookie", automated=true)
@JsonFilter(value="TicketGrantingCookieProperties")
public class TicketGrantingCookieProperties
extends PinnableCookieProperties {
    private static final long serialVersionUID = 7392972818105536350L;
    @DurationCapable
    private String rememberMeMaxAge = "P14D";
    private boolean autoConfigureCookiePath = true;
    @NestedConfigurationProperty
    private EncryptionJwtSigningJwtCryptographyProperties crypto = new EncryptionJwtSigningJwtCryptographyProperties();

    public TicketGrantingCookieProperties() {
        super.setName("TGC");
        this.crypto.getEncryption().setKeySize(256);
        this.crypto.getSigning().setKeySize(512);
    }

    @Generated
    public String getRememberMeMaxAge() {
        return this.rememberMeMaxAge;
    }

    @Generated
    public boolean isAutoConfigureCookiePath() {
        return this.autoConfigureCookiePath;
    }

    @Generated
    public EncryptionJwtSigningJwtCryptographyProperties getCrypto() {
        return this.crypto;
    }

    @Generated
    public TicketGrantingCookieProperties setRememberMeMaxAge(String rememberMeMaxAge) {
        this.rememberMeMaxAge = rememberMeMaxAge;
        return this;
    }

    @Generated
    public TicketGrantingCookieProperties setAutoConfigureCookiePath(boolean autoConfigureCookiePath) {
        this.autoConfigureCookiePath = autoConfigureCookiePath;
        return this;
    }

    @Generated
    public TicketGrantingCookieProperties setCrypto(EncryptionJwtSigningJwtCryptographyProperties crypto) {
        this.crypto = crypto;
        return this;
    }
}

