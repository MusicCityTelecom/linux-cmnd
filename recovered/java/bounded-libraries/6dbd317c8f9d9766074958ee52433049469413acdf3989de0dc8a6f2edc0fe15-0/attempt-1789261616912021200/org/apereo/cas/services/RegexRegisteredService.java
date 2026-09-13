/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  lombok.Generated
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Generated;
import org.apereo.cas.services.BaseWebBasedRegisteredService;
import org.apereo.cas.services.CasRegisteredService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
@Deprecated(since="6.6.0")
public class RegexRegisteredService
extends BaseWebBasedRegisteredService {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(RegexRegisteredService.class);
    private static final long serialVersionUID = -8258660210826975771L;

    public RegexRegisteredService() {
        LOGGER.warn("CAS has located a service definition type that is now tagged as [RegexRegisteredService]. This registered service definition type is scheduled for removal and should no longer be used for CAS-enabled applications, and MUST be replaced with [{}] instead. We STRONGLY advise that you update your service definitions and make the replacement to facilitate future CAS upgrades.", (Object)CasRegisteredService.class.getName());
    }

    @JsonIgnore
    public String getFriendlyName() {
        return "CAS Client";
    }

    @Override
    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RegexRegisteredService)) {
            return false;
        }
        RegexRegisteredService other = (RegexRegisteredService)o;
        if (!other.canEqual(this)) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof RegexRegisteredService;
    }

    @Override
    @Generated
    public int hashCode() {
        int result = super.hashCode();
        return result;
    }
}

