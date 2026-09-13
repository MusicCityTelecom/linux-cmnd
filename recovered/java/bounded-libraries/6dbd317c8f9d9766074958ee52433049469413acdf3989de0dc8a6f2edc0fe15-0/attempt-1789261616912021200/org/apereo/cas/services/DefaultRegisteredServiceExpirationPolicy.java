/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.services.RegisteredServiceExpirationPolicy
 *  org.apereo.cas.util.DateTimeUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.services.RegisteredServiceExpirationPolicy;
import org.apereo.cas.util.DateTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
@JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
public class DefaultRegisteredServiceExpirationPolicy
implements RegisteredServiceExpirationPolicy {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultRegisteredServiceExpirationPolicy.class);
    private static final long serialVersionUID = 5106652807554743500L;
    private boolean deleteWhenExpired;
    private boolean notifyWhenDeleted;
    private boolean notifyWhenExpired;
    private String expirationDate;

    public DefaultRegisteredServiceExpirationPolicy(String expirationDate) {
        this(false, false, false, expirationDate);
    }

    public DefaultRegisteredServiceExpirationPolicy(boolean deleteWhenExpired, String expirationDate) {
        this(deleteWhenExpired, false, false, expirationDate);
    }

    public DefaultRegisteredServiceExpirationPolicy(boolean deleteWhenExpired, LocalDate expirationDate) {
        this(deleteWhenExpired, false, false, expirationDate.toString());
    }

    public DefaultRegisteredServiceExpirationPolicy(boolean deleteWhenExpired, LocalDateTime expirationDate) {
        this(deleteWhenExpired, false, false, expirationDate.toString());
    }

    public boolean isExpired() {
        if (StringUtils.isBlank((CharSequence)this.expirationDate)) {
            return false;
        }
        LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());
        LocalDateTime expDate = DateTimeUtils.localDateTimeOf((String)this.expirationDate);
        LOGGER.debug("Service expiration date is [{}] while now is [{}]", (Object)this.expirationDate, (Object)now);
        return now.isEqual(expDate) || now.isAfter(expDate);
    }

    @Generated
    public String toString() {
        return "DefaultRegisteredServiceExpirationPolicy(deleteWhenExpired=" + this.deleteWhenExpired + ", notifyWhenDeleted=" + this.notifyWhenDeleted + ", notifyWhenExpired=" + this.notifyWhenExpired + ", expirationDate=" + this.expirationDate + ")";
    }

    @Generated
    public boolean isDeleteWhenExpired() {
        return this.deleteWhenExpired;
    }

    @Generated
    public boolean isNotifyWhenDeleted() {
        return this.notifyWhenDeleted;
    }

    @Generated
    public boolean isNotifyWhenExpired() {
        return this.notifyWhenExpired;
    }

    @Generated
    public String getExpirationDate() {
        return this.expirationDate;
    }

    @Generated
    public DefaultRegisteredServiceExpirationPolicy setDeleteWhenExpired(boolean deleteWhenExpired) {
        this.deleteWhenExpired = deleteWhenExpired;
        return this;
    }

    @Generated
    public DefaultRegisteredServiceExpirationPolicy setNotifyWhenDeleted(boolean notifyWhenDeleted) {
        this.notifyWhenDeleted = notifyWhenDeleted;
        return this;
    }

    @Generated
    public DefaultRegisteredServiceExpirationPolicy setNotifyWhenExpired(boolean notifyWhenExpired) {
        this.notifyWhenExpired = notifyWhenExpired;
        return this;
    }

    @Generated
    public DefaultRegisteredServiceExpirationPolicy setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    @Generated
    public DefaultRegisteredServiceExpirationPolicy() {
    }

    @Generated
    public DefaultRegisteredServiceExpirationPolicy(boolean deleteWhenExpired, boolean notifyWhenDeleted, boolean notifyWhenExpired, String expirationDate) {
        this.deleteWhenExpired = deleteWhenExpired;
        this.notifyWhenDeleted = notifyWhenDeleted;
        this.notifyWhenExpired = notifyWhenExpired;
        this.expirationDate = expirationDate;
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DefaultRegisteredServiceExpirationPolicy)) {
            return false;
        }
        DefaultRegisteredServiceExpirationPolicy other = (DefaultRegisteredServiceExpirationPolicy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.deleteWhenExpired != other.deleteWhenExpired) {
            return false;
        }
        if (this.notifyWhenDeleted != other.notifyWhenDeleted) {
            return false;
        }
        if (this.notifyWhenExpired != other.notifyWhenExpired) {
            return false;
        }
        String this$expirationDate = this.expirationDate;
        String other$expirationDate = other.expirationDate;
        return !(this$expirationDate == null ? other$expirationDate != null : !this$expirationDate.equals(other$expirationDate));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof DefaultRegisteredServiceExpirationPolicy;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.deleteWhenExpired ? 79 : 97);
        result = result * 59 + (this.notifyWhenDeleted ? 79 : 97);
        result = result * 59 + (this.notifyWhenExpired ? 79 : 97);
        String $expirationDate = this.expirationDate;
        result = result * 59 + ($expirationDate == null ? 43 : $expirationDate.hashCode());
        return result;
    }
}

