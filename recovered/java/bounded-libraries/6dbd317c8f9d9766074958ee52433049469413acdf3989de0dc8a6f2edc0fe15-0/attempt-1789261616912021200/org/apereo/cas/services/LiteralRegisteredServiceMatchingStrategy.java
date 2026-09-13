/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  lombok.Generated
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.RegisteredServiceMatchingStrategy
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Generated;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.RegisteredServiceMatchingStrategy;

@JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
public class LiteralRegisteredServiceMatchingStrategy
implements RegisteredServiceMatchingStrategy {
    private static final long serialVersionUID = -8345895859210185565L;
    private boolean caseInsensitive;

    public boolean matches(RegisteredService registeredService, String serviceId) {
        String assignedId = registeredService.getServiceId().trim();
        if (this.caseInsensitive) {
            return assignedId.equalsIgnoreCase(serviceId);
        }
        return assignedId.equals(serviceId);
    }

    @Generated
    public String toString() {
        return "LiteralRegisteredServiceMatchingStrategy(caseInsensitive=" + this.caseInsensitive + ")";
    }

    @Generated
    public boolean isCaseInsensitive() {
        return this.caseInsensitive;
    }

    @Generated
    public LiteralRegisteredServiceMatchingStrategy setCaseInsensitive(boolean caseInsensitive) {
        this.caseInsensitive = caseInsensitive;
        return this;
    }

    @Generated
    public LiteralRegisteredServiceMatchingStrategy() {
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof LiteralRegisteredServiceMatchingStrategy)) {
            return false;
        }
        LiteralRegisteredServiceMatchingStrategy other = (LiteralRegisteredServiceMatchingStrategy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        return this.caseInsensitive == other.caseInsensitive;
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof LiteralRegisteredServiceMatchingStrategy;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.caseInsensitive ? 79 : 97);
        return result;
    }

    @Generated
    public LiteralRegisteredServiceMatchingStrategy(boolean caseInsensitive) {
        this.caseInsensitive = caseInsensitive;
    }
}

