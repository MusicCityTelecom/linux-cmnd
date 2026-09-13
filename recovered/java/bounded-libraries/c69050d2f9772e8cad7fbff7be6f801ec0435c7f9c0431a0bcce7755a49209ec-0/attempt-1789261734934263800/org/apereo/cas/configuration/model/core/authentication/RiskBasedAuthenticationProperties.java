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
import org.apereo.cas.configuration.model.support.email.EmailProperties;
import org.apereo.cas.configuration.model.support.sms.SmsProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-electrofence")
public class RiskBasedAuthenticationProperties
implements Serializable {
    private static final long serialVersionUID = 3826749727400569308L;
    private IpAddress ip = new IpAddress();
    private Agent agent = new Agent();
    private GeoLocation geoLocation = new GeoLocation();
    private DateTime dateTime = new DateTime();
    private Response response = new Response();
    private double threshold = 0.6;
    private long daysInRecentHistory = 30L;

    @Generated
    public IpAddress getIp() {
        return this.ip;
    }

    @Generated
    public Agent getAgent() {
        return this.agent;
    }

    @Generated
    public GeoLocation getGeoLocation() {
        return this.geoLocation;
    }

    @Generated
    public DateTime getDateTime() {
        return this.dateTime;
    }

    @Generated
    public Response getResponse() {
        return this.response;
    }

    @Generated
    public double getThreshold() {
        return this.threshold;
    }

    @Generated
    public long getDaysInRecentHistory() {
        return this.daysInRecentHistory;
    }

    @Generated
    public RiskBasedAuthenticationProperties setIp(IpAddress ip) {
        this.ip = ip;
        return this;
    }

    @Generated
    public RiskBasedAuthenticationProperties setAgent(Agent agent) {
        this.agent = agent;
        return this;
    }

    @Generated
    public RiskBasedAuthenticationProperties setGeoLocation(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
        return this;
    }

    @Generated
    public RiskBasedAuthenticationProperties setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    @Generated
    public RiskBasedAuthenticationProperties setResponse(Response response) {
        this.response = response;
        return this;
    }

    @Generated
    public RiskBasedAuthenticationProperties setThreshold(double threshold) {
        this.threshold = threshold;
        return this;
    }

    @Generated
    public RiskBasedAuthenticationProperties setDaysInRecentHistory(long daysInRecentHistory) {
        this.daysInRecentHistory = daysInRecentHistory;
        return this;
    }

    @RequiresModule(name="cas-server-support-electrofence")
    public static class Response
    implements Serializable {
        private static final long serialVersionUID = 8254082561120701582L;
        private boolean blockAttempt;
        private String mfaProvider;
        private String riskyAuthenticationAttribute = "triggeredRiskBasedAuthentication";
        @NestedConfigurationProperty
        private EmailProperties mail = new EmailProperties();
        @NestedConfigurationProperty
        private SmsProperties sms = new SmsProperties();

        @Generated
        public boolean isBlockAttempt() {
            return this.blockAttempt;
        }

        @Generated
        public String getMfaProvider() {
            return this.mfaProvider;
        }

        @Generated
        public String getRiskyAuthenticationAttribute() {
            return this.riskyAuthenticationAttribute;
        }

        @Generated
        public EmailProperties getMail() {
            return this.mail;
        }

        @Generated
        public SmsProperties getSms() {
            return this.sms;
        }

        @Generated
        public Response setBlockAttempt(boolean blockAttempt) {
            this.blockAttempt = blockAttempt;
            return this;
        }

        @Generated
        public Response setMfaProvider(String mfaProvider) {
            this.mfaProvider = mfaProvider;
            return this;
        }

        @Generated
        public Response setRiskyAuthenticationAttribute(String riskyAuthenticationAttribute) {
            this.riskyAuthenticationAttribute = riskyAuthenticationAttribute;
            return this;
        }

        @Generated
        public Response setMail(EmailProperties mail) {
            this.mail = mail;
            return this;
        }

        @Generated
        public Response setSms(SmsProperties sms) {
            this.sms = sms;
            return this;
        }
    }

    @RequiresModule(name="cas-server-support-electrofence")
    public static class DateTime
    implements Serializable {
        private static final long serialVersionUID = -3776875583039922050L;
        private boolean enabled;
        private int windowInHours = 2;

        @Generated
        public boolean isEnabled() {
            return this.enabled;
        }

        @Generated
        public int getWindowInHours() {
            return this.windowInHours;
        }

        @Generated
        public DateTime setEnabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        @Generated
        public DateTime setWindowInHours(int windowInHours) {
            this.windowInHours = windowInHours;
            return this;
        }
    }

    @RequiresModule(name="cas-server-support-electrofence")
    public static class GeoLocation
    implements Serializable {
        private static final long serialVersionUID = 4115333388680538358L;
        private boolean enabled;

        @Generated
        public boolean isEnabled() {
            return this.enabled;
        }

        @Generated
        public GeoLocation setEnabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }
    }

    @RequiresModule(name="cas-server-support-electrofence")
    public static class Agent
    implements Serializable {
        private static final long serialVersionUID = 7766080681971729400L;
        private boolean enabled;

        @Generated
        public boolean isEnabled() {
            return this.enabled;
        }

        @Generated
        public Agent setEnabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }
    }

    @RequiresModule(name="cas-server-support-electrofence")
    public static class IpAddress
    implements Serializable {
        private static final long serialVersionUID = 577801361041617794L;
        private boolean enabled;

        @Generated
        public boolean isEnabled() {
            return this.enabled;
        }

        @Generated
        public IpAddress setEnabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }
    }
}

