/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  lombok.Generated
 *  org.apereo.cas.configuration.model.support.mfa.BaseMultifactorAuthenticationProviderProperties$MultifactorAuthenticationProviderFailureModes
 *  org.apereo.cas.services.RegisteredServiceMultifactorPolicy
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.mfa.BaseMultifactorAuthenticationProviderProperties;
import org.apereo.cas.services.RegisteredServiceMultifactorPolicy;

@JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
public class DefaultRegisteredServiceMultifactorPolicy
implements RegisteredServiceMultifactorPolicy {
    private static final long serialVersionUID = -3068390754996358337L;
    private Set<String> multifactorAuthenticationProviders = new LinkedHashSet<String>(0);
    private BaseMultifactorAuthenticationProviderProperties.MultifactorAuthenticationProviderFailureModes failureMode = BaseMultifactorAuthenticationProviderProperties.MultifactorAuthenticationProviderFailureModes.UNDEFINED;
    private String principalAttributeNameTrigger;
    private String principalAttributeValueToMatch;
    private boolean bypassEnabled;
    private boolean forceExecution;
    private boolean bypassTrustedDeviceEnabled;
    private String bypassPrincipalAttributeName;
    private String bypassPrincipalAttributeValue;
    private String script;

    @Generated
    public String toString() {
        return "DefaultRegisteredServiceMultifactorPolicy(multifactorAuthenticationProviders=" + this.multifactorAuthenticationProviders + ", failureMode=" + this.failureMode + ", principalAttributeNameTrigger=" + this.principalAttributeNameTrigger + ", principalAttributeValueToMatch=" + this.principalAttributeValueToMatch + ", bypassEnabled=" + this.bypassEnabled + ", forceExecution=" + this.forceExecution + ", bypassTrustedDeviceEnabled=" + this.bypassTrustedDeviceEnabled + ", bypassPrincipalAttributeName=" + this.bypassPrincipalAttributeName + ", bypassPrincipalAttributeValue=" + this.bypassPrincipalAttributeValue + ", script=" + this.script + ")";
    }

    @Generated
    public Set<String> getMultifactorAuthenticationProviders() {
        return this.multifactorAuthenticationProviders;
    }

    @Generated
    public BaseMultifactorAuthenticationProviderProperties.MultifactorAuthenticationProviderFailureModes getFailureMode() {
        return this.failureMode;
    }

    @Generated
    public String getPrincipalAttributeNameTrigger() {
        return this.principalAttributeNameTrigger;
    }

    @Generated
    public String getPrincipalAttributeValueToMatch() {
        return this.principalAttributeValueToMatch;
    }

    @Generated
    public boolean isBypassEnabled() {
        return this.bypassEnabled;
    }

    @Generated
    public boolean isForceExecution() {
        return this.forceExecution;
    }

    @Generated
    public boolean isBypassTrustedDeviceEnabled() {
        return this.bypassTrustedDeviceEnabled;
    }

    @Generated
    public String getBypassPrincipalAttributeName() {
        return this.bypassPrincipalAttributeName;
    }

    @Generated
    public String getBypassPrincipalAttributeValue() {
        return this.bypassPrincipalAttributeValue;
    }

    @Generated
    public String getScript() {
        return this.script;
    }

    @Generated
    public DefaultRegisteredServiceMultifactorPolicy setMultifactorAuthenticationProviders(Set<String> multifactorAuthenticationProviders) {
        this.multifactorAuthenticationProviders = multifactorAuthenticationProviders;
        return this;
    }

    @Generated
    public DefaultRegisteredServiceMultifactorPolicy setFailureMode(BaseMultifactorAuthenticationProviderProperties.MultifactorAuthenticationProviderFailureModes failureMode) {
        this.failureMode = failureMode;
        return this;
    }

    @Generated
    public DefaultRegisteredServiceMultifactorPolicy setPrincipalAttributeNameTrigger(String principalAttributeNameTrigger) {
        this.principalAttributeNameTrigger = principalAttributeNameTrigger;
        return this;
    }

    @Generated
    public DefaultRegisteredServiceMultifactorPolicy setPrincipalAttributeValueToMatch(String principalAttributeValueToMatch) {
        this.principalAttributeValueToMatch = principalAttributeValueToMatch;
        return this;
    }

    @Generated
    public DefaultRegisteredServiceMultifactorPolicy setBypassEnabled(boolean bypassEnabled) {
        this.bypassEnabled = bypassEnabled;
        return this;
    }

    @Generated
    public DefaultRegisteredServiceMultifactorPolicy setForceExecution(boolean forceExecution) {
        this.forceExecution = forceExecution;
        return this;
    }

    @Generated
    public DefaultRegisteredServiceMultifactorPolicy setBypassTrustedDeviceEnabled(boolean bypassTrustedDeviceEnabled) {
        this.bypassTrustedDeviceEnabled = bypassTrustedDeviceEnabled;
        return this;
    }

    @Generated
    public DefaultRegisteredServiceMultifactorPolicy setBypassPrincipalAttributeName(String bypassPrincipalAttributeName) {
        this.bypassPrincipalAttributeName = bypassPrincipalAttributeName;
        return this;
    }

    @Generated
    public DefaultRegisteredServiceMultifactorPolicy setBypassPrincipalAttributeValue(String bypassPrincipalAttributeValue) {
        this.bypassPrincipalAttributeValue = bypassPrincipalAttributeValue;
        return this;
    }

    @Generated
    public DefaultRegisteredServiceMultifactorPolicy setScript(String script) {
        this.script = script;
        return this;
    }

    @Generated
    public DefaultRegisteredServiceMultifactorPolicy() {
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DefaultRegisteredServiceMultifactorPolicy)) {
            return false;
        }
        DefaultRegisteredServiceMultifactorPolicy other = (DefaultRegisteredServiceMultifactorPolicy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.bypassEnabled != other.bypassEnabled) {
            return false;
        }
        if (this.forceExecution != other.forceExecution) {
            return false;
        }
        if (this.bypassTrustedDeviceEnabled != other.bypassTrustedDeviceEnabled) {
            return false;
        }
        Set<String> this$multifactorAuthenticationProviders = this.multifactorAuthenticationProviders;
        Set<String> other$multifactorAuthenticationProviders = other.multifactorAuthenticationProviders;
        if (this$multifactorAuthenticationProviders == null ? other$multifactorAuthenticationProviders != null : !((Object)this$multifactorAuthenticationProviders).equals(other$multifactorAuthenticationProviders)) {
            return false;
        }
        BaseMultifactorAuthenticationProviderProperties.MultifactorAuthenticationProviderFailureModes this$failureMode = this.failureMode;
        BaseMultifactorAuthenticationProviderProperties.MultifactorAuthenticationProviderFailureModes other$failureMode = other.failureMode;
        if (this$failureMode == null ? other$failureMode != null : !this$failureMode.equals(other$failureMode)) {
            return false;
        }
        String this$principalAttributeNameTrigger = this.principalAttributeNameTrigger;
        String other$principalAttributeNameTrigger = other.principalAttributeNameTrigger;
        if (this$principalAttributeNameTrigger == null ? other$principalAttributeNameTrigger != null : !this$principalAttributeNameTrigger.equals(other$principalAttributeNameTrigger)) {
            return false;
        }
        String this$principalAttributeValueToMatch = this.principalAttributeValueToMatch;
        String other$principalAttributeValueToMatch = other.principalAttributeValueToMatch;
        if (this$principalAttributeValueToMatch == null ? other$principalAttributeValueToMatch != null : !this$principalAttributeValueToMatch.equals(other$principalAttributeValueToMatch)) {
            return false;
        }
        String this$bypassPrincipalAttributeName = this.bypassPrincipalAttributeName;
        String other$bypassPrincipalAttributeName = other.bypassPrincipalAttributeName;
        if (this$bypassPrincipalAttributeName == null ? other$bypassPrincipalAttributeName != null : !this$bypassPrincipalAttributeName.equals(other$bypassPrincipalAttributeName)) {
            return false;
        }
        String this$bypassPrincipalAttributeValue = this.bypassPrincipalAttributeValue;
        String other$bypassPrincipalAttributeValue = other.bypassPrincipalAttributeValue;
        if (this$bypassPrincipalAttributeValue == null ? other$bypassPrincipalAttributeValue != null : !this$bypassPrincipalAttributeValue.equals(other$bypassPrincipalAttributeValue)) {
            return false;
        }
        String this$script = this.script;
        String other$script = other.script;
        return !(this$script == null ? other$script != null : !this$script.equals(other$script));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof DefaultRegisteredServiceMultifactorPolicy;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.bypassEnabled ? 79 : 97);
        result = result * 59 + (this.forceExecution ? 79 : 97);
        result = result * 59 + (this.bypassTrustedDeviceEnabled ? 79 : 97);
        Set<String> $multifactorAuthenticationProviders = this.multifactorAuthenticationProviders;
        result = result * 59 + ($multifactorAuthenticationProviders == null ? 43 : ((Object)$multifactorAuthenticationProviders).hashCode());
        BaseMultifactorAuthenticationProviderProperties.MultifactorAuthenticationProviderFailureModes $failureMode = this.failureMode;
        result = result * 59 + ($failureMode == null ? 43 : $failureMode.hashCode());
        String $principalAttributeNameTrigger = this.principalAttributeNameTrigger;
        result = result * 59 + ($principalAttributeNameTrigger == null ? 43 : $principalAttributeNameTrigger.hashCode());
        String $principalAttributeValueToMatch = this.principalAttributeValueToMatch;
        result = result * 59 + ($principalAttributeValueToMatch == null ? 43 : $principalAttributeValueToMatch.hashCode());
        String $bypassPrincipalAttributeName = this.bypassPrincipalAttributeName;
        result = result * 59 + ($bypassPrincipalAttributeName == null ? 43 : $bypassPrincipalAttributeName.hashCode());
        String $bypassPrincipalAttributeValue = this.bypassPrincipalAttributeValue;
        result = result * 59 + ($bypassPrincipalAttributeValue == null ? 43 : $bypassPrincipalAttributeValue.hashCode());
        String $script = this.script;
        result = result * 59 + ($script == null ? 43 : $script.hashCode());
        return result;
    }
}

