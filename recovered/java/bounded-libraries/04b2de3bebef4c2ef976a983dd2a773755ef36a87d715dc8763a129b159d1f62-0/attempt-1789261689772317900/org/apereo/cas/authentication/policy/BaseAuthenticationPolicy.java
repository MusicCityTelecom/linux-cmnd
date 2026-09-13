/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  lombok.Generated
 *  org.apereo.cas.authentication.AuthenticationPolicy
 */
package org.apereo.cas.authentication.policy;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Generated;
import org.apereo.cas.authentication.AuthenticationPolicy;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public abstract class BaseAuthenticationPolicy
implements AuthenticationPolicy {
    private static final long serialVersionUID = -2825457764398118845L;
    private int order = Integer.MAX_VALUE;
    private String name = this.getClass().getSimpleName();

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BaseAuthenticationPolicy)) {
            return false;
        }
        BaseAuthenticationPolicy other = (BaseAuthenticationPolicy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.order != other.order) {
            return false;
        }
        String this$name = this.name;
        String other$name = other.name;
        return !(this$name == null ? other$name != null : !this$name.equals(other$name));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof BaseAuthenticationPolicy;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.order;
        String $name = this.name;
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        return result;
    }

    @Generated
    public void setOrder(int order) {
        this.order = order;
    }

    @Generated
    public void setName(String name) {
        this.name = name;
    }

    @Generated
    public int getOrder() {
        return this.order;
    }

    @Generated
    public String getName() {
        return this.name;
    }
}

