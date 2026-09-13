/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.web.support;

import java.io.Serializable;
import java.time.Clock;
import java.time.ZonedDateTime;
import lombok.Generated;

public class ThrottledSubmission
implements Serializable {
    private static final long serialVersionUID = -853401483455717926L;
    private final String key;
    private final ZonedDateTime value;
    private final String username;
    private final String clientIpAddress;
    private final ZonedDateTime expiration;

    @Generated
    private static ZonedDateTime $default$value() {
        return ZonedDateTime.now(Clock.systemUTC());
    }

    @Generated
    protected ThrottledSubmission(ThrottledSubmissionBuilder<?, ?> b) {
        this.key = b.key;
        this.value = b.value$set ? b.value$value : ThrottledSubmission.$default$value();
        this.username = b.username;
        this.clientIpAddress = b.clientIpAddress;
        this.expiration = b.expiration;
    }

    @Generated
    public static ThrottledSubmissionBuilder<?, ?> builder() {
        return new ThrottledSubmissionBuilderImpl();
    }

    @Generated
    public String getKey() {
        return this.key;
    }

    @Generated
    public ZonedDateTime getValue() {
        return this.value;
    }

    @Generated
    public String getUsername() {
        return this.username;
    }

    @Generated
    public String getClientIpAddress() {
        return this.clientIpAddress;
    }

    @Generated
    public ZonedDateTime getExpiration() {
        return this.expiration;
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ThrottledSubmission)) {
            return false;
        }
        ThrottledSubmission other = (ThrottledSubmission)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$key = this.key;
        String other$key = other.key;
        if (this$key == null ? other$key != null : !this$key.equals(other$key)) {
            return false;
        }
        ZonedDateTime this$value = this.value;
        ZonedDateTime other$value = other.value;
        if (this$value == null ? other$value != null : !((Object)this$value).equals(other$value)) {
            return false;
        }
        String this$username = this.username;
        String other$username = other.username;
        if (this$username == null ? other$username != null : !this$username.equals(other$username)) {
            return false;
        }
        String this$clientIpAddress = this.clientIpAddress;
        String other$clientIpAddress = other.clientIpAddress;
        if (this$clientIpAddress == null ? other$clientIpAddress != null : !this$clientIpAddress.equals(other$clientIpAddress)) {
            return false;
        }
        ZonedDateTime this$expiration = this.expiration;
        ZonedDateTime other$expiration = other.expiration;
        return !(this$expiration == null ? other$expiration != null : !((Object)this$expiration).equals(other$expiration));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof ThrottledSubmission;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $key = this.key;
        result = result * 59 + ($key == null ? 43 : $key.hashCode());
        ZonedDateTime $value = this.value;
        result = result * 59 + ($value == null ? 43 : ((Object)$value).hashCode());
        String $username = this.username;
        result = result * 59 + ($username == null ? 43 : $username.hashCode());
        String $clientIpAddress = this.clientIpAddress;
        result = result * 59 + ($clientIpAddress == null ? 43 : $clientIpAddress.hashCode());
        ZonedDateTime $expiration = this.expiration;
        result = result * 59 + ($expiration == null ? 43 : ((Object)$expiration).hashCode());
        return result;
    }

    @Generated
    public String toString() {
        return "ThrottledSubmission(key=" + this.key + ", value=" + this.value + ", username=" + this.username + ", clientIpAddress=" + this.clientIpAddress + ", expiration=" + this.expiration + ")";
    }

    @Generated
    private static final class ThrottledSubmissionBuilderImpl
    extends ThrottledSubmissionBuilder<ThrottledSubmission, ThrottledSubmissionBuilderImpl> {
        @Generated
        private ThrottledSubmissionBuilderImpl() {
        }

        @Override
        @Generated
        protected ThrottledSubmissionBuilderImpl self() {
            return this;
        }

        @Override
        @Generated
        public ThrottledSubmission build() {
            return new ThrottledSubmission(this);
        }
    }

    @Generated
    public static abstract class ThrottledSubmissionBuilder<C extends ThrottledSubmission, B extends ThrottledSubmissionBuilder<C, B>> {
        @Generated
        private String key;
        @Generated
        private boolean value$set;
        @Generated
        private ZonedDateTime value$value;
        @Generated
        private String username;
        @Generated
        private String clientIpAddress;
        @Generated
        private ZonedDateTime expiration;

        @Generated
        protected abstract B self();

        @Generated
        public abstract C build();

        @Generated
        public B key(String key) {
            this.key = key;
            return this.self();
        }

        @Generated
        public B value(ZonedDateTime value) {
            this.value$value = value;
            this.value$set = true;
            return this.self();
        }

        @Generated
        public B username(String username) {
            this.username = username;
            return this.self();
        }

        @Generated
        public B clientIpAddress(String clientIpAddress) {
            this.clientIpAddress = clientIpAddress;
            return this.self();
        }

        @Generated
        public B expiration(ZonedDateTime expiration) {
            this.expiration = expiration;
            return this.self();
        }

        @Generated
        public String toString() {
            return "ThrottledSubmission.ThrottledSubmissionBuilder(key=" + this.key + ", value$value=" + this.value$value + ", username=" + this.username + ", clientIpAddress=" + this.clientIpAddress + ", expiration=" + this.expiration + ")";
        }
    }
}

