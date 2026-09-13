/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  lombok.Generated
 *  org.apereo.cas.authentication.AuthenticationPolicy
 *  org.apereo.cas.authentication.policy.GroovyScriptAuthenticationPolicy
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.RegisteredServiceAuthenticationPolicyCriteria
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Generated;
import org.apereo.cas.authentication.AuthenticationPolicy;
import org.apereo.cas.authentication.policy.GroovyScriptAuthenticationPolicy;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.RegisteredServiceAuthenticationPolicyCriteria;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
@JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
public class GroovyRegisteredServiceAuthenticationPolicyCriteria
implements RegisteredServiceAuthenticationPolicyCriteria {
    private static final long serialVersionUID = -1905826778096374574L;
    private String script;

    public AuthenticationPolicy toAuthenticationPolicy(RegisteredService registeredService) {
        return new GroovyScriptAuthenticationPolicy(this.script);
    }

    @Generated
    public String toString() {
        return "GroovyRegisteredServiceAuthenticationPolicyCriteria(script=" + this.script + ")";
    }

    @Generated
    public String getScript() {
        return this.script;
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof GroovyRegisteredServiceAuthenticationPolicyCriteria)) {
            return false;
        }
        GroovyRegisteredServiceAuthenticationPolicyCriteria other = (GroovyRegisteredServiceAuthenticationPolicyCriteria)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$script = this.script;
        String other$script = other.script;
        return !(this$script == null ? other$script != null : !this$script.equals(other$script));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof GroovyRegisteredServiceAuthenticationPolicyCriteria;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $script = this.script;
        result = result * 59 + ($script == null ? 43 : $script.hashCode());
        return result;
    }

    @Generated
    public GroovyRegisteredServiceAuthenticationPolicyCriteria setScript(String script) {
        this.script = script;
        return this;
    }
}

