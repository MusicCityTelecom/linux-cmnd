/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.configuration.support.ExpressionLanguageCapable
 *  org.apereo.cas.util.DateTimeUtils
 *  org.apereo.cas.util.spring.SpringExpressionLanguageValueResolver
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.configuration.support.ExpressionLanguageCapable;
import org.apereo.cas.services.BaseRegisteredServiceAccessStrategy;
import org.apereo.cas.util.DateTimeUtils;
import org.apereo.cas.util.spring.SpringExpressionLanguageValueResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
public class TimeBasedRegisteredServiceAccessStrategy
extends BaseRegisteredServiceAccessStrategy {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(TimeBasedRegisteredServiceAccessStrategy.class);
    private static final long serialVersionUID = -6180748828025837047L;
    @ExpressionLanguageCapable
    private String startingDateTime;
    @ExpressionLanguageCapable
    private String endingDateTime;
    @ExpressionLanguageCapable
    private String zoneId = ZoneOffset.UTC.getId();

    public boolean isServiceAccessAllowed() {
        return this.doesStartingTimeAllowServiceAccess() && this.doesEndingTimeAllowServiceAccess();
    }

    public String getStartingDateTime() {
        return StringUtils.isBlank((CharSequence)this.startingDateTime) ? null : SpringExpressionLanguageValueResolver.getInstance().resolve(this.startingDateTime);
    }

    public String getEndingDateTime() {
        return StringUtils.isBlank((CharSequence)this.endingDateTime) ? null : SpringExpressionLanguageValueResolver.getInstance().resolve(this.endingDateTime);
    }

    public String getZoneId() {
        return StringUtils.isBlank((CharSequence)this.zoneId) ? ZoneOffset.UTC.getId() : SpringExpressionLanguageValueResolver.getInstance().resolve(this.zoneId);
    }

    protected boolean doesEndingTimeAllowServiceAccess() {
        String endDateTime = this.getEndingDateTime();
        if (endDateTime != null) {
            ZonedDateTime et = DateTimeUtils.zonedDateTimeOf((String)endDateTime);
            if (et != null) {
                ZonedDateTime now = ZonedDateTime.now(ZoneId.of(this.getZoneId()));
                if (now.isAfter(et)) {
                    LOGGER.warn("Service access not allowed because it ended at [{}]. Now is [{}]", (Object)endDateTime, (Object)now);
                    return false;
                }
            } else {
                LocalDateTime now;
                LocalDateTime etLocal = DateTimeUtils.localDateTimeOf((String)endDateTime);
                if (etLocal != null && (now = LocalDateTime.now(ZoneId.of(this.getZoneId()))).isAfter(etLocal)) {
                    LOGGER.warn("Service access not allowed because it ended at [{}]. Now is [{}]", (Object)endDateTime, (Object)now);
                    return false;
                }
            }
        }
        return true;
    }

    protected boolean doesStartingTimeAllowServiceAccess() {
        String startDateTime = this.getStartingDateTime();
        if (startDateTime != null) {
            ZonedDateTime st = DateTimeUtils.zonedDateTimeOf((String)startDateTime);
            if (st != null) {
                ZonedDateTime now = ZonedDateTime.now(ZoneId.of(this.getZoneId()));
                if (now.isBefore(st)) {
                    LOGGER.warn("Service access not allowed because it starts at [{}]. Zoned now is [{}]", (Object)startDateTime, (Object)now);
                    return false;
                }
            } else {
                LocalDateTime now;
                LocalDateTime stLocal = DateTimeUtils.localDateTimeOf((String)startDateTime);
                if (stLocal != null && (now = LocalDateTime.now(ZoneId.of(this.getZoneId()))).isBefore(stLocal)) {
                    LOGGER.warn("Service access not allowed because it starts at [{}]. Local now is [{}]", (Object)startDateTime, (Object)now);
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    @Generated
    public String toString() {
        return "TimeBasedRegisteredServiceAccessStrategy(startingDateTime=" + this.startingDateTime + ", endingDateTime=" + this.endingDateTime + ", zoneId=" + this.zoneId + ")";
    }

    @Override
    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TimeBasedRegisteredServiceAccessStrategy)) {
            return false;
        }
        TimeBasedRegisteredServiceAccessStrategy other = (TimeBasedRegisteredServiceAccessStrategy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        String this$startingDateTime = this.startingDateTime;
        String other$startingDateTime = other.startingDateTime;
        if (this$startingDateTime == null ? other$startingDateTime != null : !this$startingDateTime.equals(other$startingDateTime)) {
            return false;
        }
        String this$endingDateTime = this.endingDateTime;
        String other$endingDateTime = other.endingDateTime;
        if (this$endingDateTime == null ? other$endingDateTime != null : !this$endingDateTime.equals(other$endingDateTime)) {
            return false;
        }
        String this$zoneId = this.zoneId;
        String other$zoneId = other.zoneId;
        return !(this$zoneId == null ? other$zoneId != null : !this$zoneId.equals(other$zoneId));
    }

    @Override
    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof TimeBasedRegisteredServiceAccessStrategy;
    }

    @Override
    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        String $startingDateTime = this.startingDateTime;
        result = result * 59 + ($startingDateTime == null ? 43 : $startingDateTime.hashCode());
        String $endingDateTime = this.endingDateTime;
        result = result * 59 + ($endingDateTime == null ? 43 : $endingDateTime.hashCode());
        String $zoneId = this.zoneId;
        result = result * 59 + ($zoneId == null ? 43 : $zoneId.hashCode());
        return result;
    }

    @Generated
    public TimeBasedRegisteredServiceAccessStrategy setStartingDateTime(String startingDateTime) {
        this.startingDateTime = startingDateTime;
        return this;
    }

    @Generated
    public TimeBasedRegisteredServiceAccessStrategy setEndingDateTime(String endingDateTime) {
        this.endingDateTime = endingDateTime;
        return this;
    }

    @Generated
    public TimeBasedRegisteredServiceAccessStrategy setZoneId(String zoneId) {
        this.zoneId = zoneId;
        return this;
    }

    @Generated
    public TimeBasedRegisteredServiceAccessStrategy() {
    }
}

