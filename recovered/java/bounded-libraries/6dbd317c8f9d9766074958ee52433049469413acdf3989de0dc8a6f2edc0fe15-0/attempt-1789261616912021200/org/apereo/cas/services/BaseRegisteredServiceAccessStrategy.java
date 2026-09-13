/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  lombok.Generated
 *  org.apereo.cas.services.RegisteredServiceAccessStrategy
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Generated;
import org.apereo.cas.services.RegisteredServiceAccessStrategy;

@JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
public abstract class BaseRegisteredServiceAccessStrategy
implements RegisteredServiceAccessStrategy {
    private static final long serialVersionUID = 2068108924325533291L;

    @Generated
    public String toString() {
        return "BaseRegisteredServiceAccessStrategy()";
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BaseRegisteredServiceAccessStrategy)) {
            return false;
        }
        BaseRegisteredServiceAccessStrategy other = (BaseRegisteredServiceAccessStrategy)o;
        return other.canEqual(this);
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof BaseRegisteredServiceAccessStrategy;
    }

    @Generated
    public int hashCode() {
        boolean result = true;
        return 1;
    }
}

