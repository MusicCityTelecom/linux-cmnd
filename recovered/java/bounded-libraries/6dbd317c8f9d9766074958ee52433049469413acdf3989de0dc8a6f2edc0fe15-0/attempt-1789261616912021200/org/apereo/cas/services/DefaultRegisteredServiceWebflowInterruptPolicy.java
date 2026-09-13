/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  lombok.Generated
 *  org.apereo.cas.services.RegisteredServiceWebflowInterruptPolicy
 *  org.apereo.cas.util.model.TriStateBoolean
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Generated;
import org.apereo.cas.services.RegisteredServiceWebflowInterruptPolicy;
import org.apereo.cas.util.model.TriStateBoolean;

@JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
public class DefaultRegisteredServiceWebflowInterruptPolicy
implements RegisteredServiceWebflowInterruptPolicy {
    private static final long serialVersionUID = -9011530431859480167L;
    private boolean enabled = true;
    private TriStateBoolean forceExecution = TriStateBoolean.UNDEFINED;

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public TriStateBoolean getForceExecution() {
        return this.forceExecution;
    }

    @Generated
    public DefaultRegisteredServiceWebflowInterruptPolicy setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Generated
    public DefaultRegisteredServiceWebflowInterruptPolicy setForceExecution(TriStateBoolean forceExecution) {
        this.forceExecution = forceExecution;
        return this;
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DefaultRegisteredServiceWebflowInterruptPolicy)) {
            return false;
        }
        DefaultRegisteredServiceWebflowInterruptPolicy other = (DefaultRegisteredServiceWebflowInterruptPolicy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.enabled != other.enabled) {
            return false;
        }
        TriStateBoolean this$forceExecution = this.forceExecution;
        TriStateBoolean other$forceExecution = other.forceExecution;
        return !(this$forceExecution == null ? other$forceExecution != null : !this$forceExecution.equals(other$forceExecution));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof DefaultRegisteredServiceWebflowInterruptPolicy;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.enabled ? 79 : 97);
        TriStateBoolean $forceExecution = this.forceExecution;
        result = result * 59 + ($forceExecution == null ? 43 : $forceExecution.hashCode());
        return result;
    }

    @Generated
    public DefaultRegisteredServiceWebflowInterruptPolicy() {
    }
}

