/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  javax.persistence.Transient
 *  lombok.Generated
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.RegisteredServiceMatchingStrategy
 *  org.apereo.cas.util.RegexUtils
 *  org.springframework.data.annotation.Transient
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.regex.Pattern;
import lombok.Generated;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.RegisteredServiceMatchingStrategy;
import org.apereo.cas.util.RegexUtils;
import org.springframework.data.annotation.Transient;

@JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
public class PartialRegexRegisteredServiceMatchingStrategy
implements RegisteredServiceMatchingStrategy {
    private static final long serialVersionUID = -8345895859210185565L;
    @JsonIgnore
    @Transient
    @javax.persistence.Transient
    private transient Pattern servicePattern;

    public boolean matches(RegisteredService registeredService, String serviceId) {
        if (this.servicePattern == null) {
            this.servicePattern = RegexUtils.createPattern((String)registeredService.getServiceId());
        }
        return this.servicePattern.matcher(serviceId).find();
    }

    @Generated
    public String toString() {
        return "PartialRegexRegisteredServiceMatchingStrategy(servicePattern=" + this.servicePattern + ")";
    }

    @Generated
    public PartialRegexRegisteredServiceMatchingStrategy() {
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PartialRegexRegisteredServiceMatchingStrategy)) {
            return false;
        }
        PartialRegexRegisteredServiceMatchingStrategy other = (PartialRegexRegisteredServiceMatchingStrategy)o;
        return other.canEqual(this);
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof PartialRegexRegisteredServiceMatchingStrategy;
    }

    @Generated
    public int hashCode() {
        boolean result = true;
        return 1;
    }

    @Generated
    public PartialRegexRegisteredServiceMatchingStrategy(Pattern servicePattern) {
        this.servicePattern = servicePattern;
    }
}

