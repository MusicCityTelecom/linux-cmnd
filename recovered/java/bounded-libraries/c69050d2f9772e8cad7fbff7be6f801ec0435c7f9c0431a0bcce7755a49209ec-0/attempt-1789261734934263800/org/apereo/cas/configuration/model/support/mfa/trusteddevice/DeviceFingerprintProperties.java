/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.mfa.trusteddevice;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import java.time.Duration;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.util.EncryptionJwtSigningJwtCryptographyProperties;
import org.apereo.cas.configuration.model.support.cookie.PinnableCookieProperties;
import org.apereo.cas.configuration.model.support.mfa.trusteddevice.BaseDeviceFingerprintComponentProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-trusted-mfa")
@JsonFilter(value="DeviceFingerprintProperties")
public class DeviceFingerprintProperties
implements Serializable {
    private static final long serialVersionUID = 747021103142441353L;
    private String componentSeparator = "@";
    private ClientIp clientIp = new ClientIp();
    private Cookie cookie = new Cookie();
    private UserAgent userAgent = new UserAgent();
    private GeoLocation geolocation = new GeoLocation();

    @Generated
    public String getComponentSeparator() {
        return this.componentSeparator;
    }

    @Generated
    public ClientIp getClientIp() {
        return this.clientIp;
    }

    @Generated
    public Cookie getCookie() {
        return this.cookie;
    }

    @Generated
    public UserAgent getUserAgent() {
        return this.userAgent;
    }

    @Generated
    public GeoLocation getGeolocation() {
        return this.geolocation;
    }

    @Generated
    public DeviceFingerprintProperties setComponentSeparator(String componentSeparator) {
        this.componentSeparator = componentSeparator;
        return this;
    }

    @Generated
    public DeviceFingerprintProperties setClientIp(ClientIp clientIp) {
        this.clientIp = clientIp;
        return this;
    }

    @Generated
    public DeviceFingerprintProperties setCookie(Cookie cookie) {
        this.cookie = cookie;
        return this;
    }

    @Generated
    public DeviceFingerprintProperties setUserAgent(UserAgent userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    @Generated
    public DeviceFingerprintProperties setGeolocation(GeoLocation geolocation) {
        this.geolocation = geolocation;
        return this;
    }

    @RequiresModule(name="cas-server-support-trusted-mfa")
    public static class GeoLocation
    extends BaseDeviceFingerprintComponentProperties {
        private static final long serialVersionUID = -4125531035180836136L;
        private static final int DEFAULT_ORDER = 4;

        public GeoLocation() {
            this.setEnabled(false);
            this.setOrder(4);
        }
    }

    @RequiresModule(name="cas-server-support-trusted-mfa")
    public static class UserAgent
    extends BaseDeviceFingerprintComponentProperties {
        private static final long serialVersionUID = -5325531035180836136L;
        private static final int DEFAULT_ORDER = 3;

        public UserAgent() {
            this.setEnabled(false);
            this.setOrder(3);
        }
    }

    @RequiresModule(name="cas-server-support-trusted-mfa")
    public static class Cookie
    extends PinnableCookieProperties {
        private static final long serialVersionUID = -9022498833437602657L;
        private static final int DEFAULT_MAX_AGE_DAYS = 30;
        private boolean enabled = true;
        private int order = 1;
        @NestedConfigurationProperty
        private EncryptionJwtSigningJwtCryptographyProperties crypto = new EncryptionJwtSigningJwtCryptographyProperties();

        public Cookie() {
            this.setName("MFATRUSTED");
            this.setMaxAge((int)Duration.ofDays(30L).getSeconds());
            this.crypto.getEncryption().setKeySize(256);
            this.crypto.getSigning().setKeySize(512);
        }

        @Generated
        public boolean isEnabled() {
            return this.enabled;
        }

        @Generated
        public int getOrder() {
            return this.order;
        }

        @Generated
        public EncryptionJwtSigningJwtCryptographyProperties getCrypto() {
            return this.crypto;
        }

        @Generated
        public Cookie setEnabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        @Generated
        public Cookie setOrder(int order) {
            this.order = order;
            return this;
        }

        @Generated
        public Cookie setCrypto(EncryptionJwtSigningJwtCryptographyProperties crypto) {
            this.crypto = crypto;
            return this;
        }
    }

    @RequiresModule(name="cas-server-support-trusted-mfa")
    public static class ClientIp
    extends BaseDeviceFingerprintComponentProperties {
        private static final long serialVersionUID = 785014133279201757L;

        public ClientIp() {
            this.setEnabled(true);
            this.setOrder(2);
        }
    }
}

