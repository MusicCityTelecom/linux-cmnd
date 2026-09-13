/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.authentication.MultifactorAuthenticationFailureModeEvaluator
 *  org.apereo.cas.authentication.MultifactorAuthenticationProvider
 *  org.apereo.cas.authentication.bypass.MultifactorAuthenticationProviderBypassEvaluator
 *  org.apereo.cas.configuration.model.support.mfa.BaseMultifactorAuthenticationProviderProperties$MultifactorAuthenticationProviderFailureModes
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.util.RegexUtils
 */
package org.apereo.cas.authentication;

import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.MultifactorAuthenticationFailureModeEvaluator;
import org.apereo.cas.authentication.MultifactorAuthenticationProvider;
import org.apereo.cas.authentication.bypass.MultifactorAuthenticationProviderBypassEvaluator;
import org.apereo.cas.configuration.model.support.mfa.BaseMultifactorAuthenticationProviderProperties;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.util.RegexUtils;

public abstract class AbstractMultifactorAuthenticationProvider
implements MultifactorAuthenticationProvider {
    private static final long serialVersionUID = 4789727148134156909L;
    private MultifactorAuthenticationProviderBypassEvaluator bypassEvaluator;
    private MultifactorAuthenticationFailureModeEvaluator failureModeEvaluator;
    private BaseMultifactorAuthenticationProviderProperties.MultifactorAuthenticationProviderFailureModes failureMode = BaseMultifactorAuthenticationProviderProperties.MultifactorAuthenticationProviderFailureModes.UNDEFINED;
    private String id;
    private int order;

    public boolean isAvailable(RegisteredService service) {
        return true;
    }

    public boolean matches(String identifier) {
        return StringUtils.isNotBlank((CharSequence)this.getId()) && StringUtils.isNotBlank((CharSequence)identifier) && RegexUtils.find((String)identifier, (String)this.getId());
    }

    @Generated
    public String toString() {
        return "AbstractMultifactorAuthenticationProvider(bypassEvaluator=" + this.bypassEvaluator + ", failureModeEvaluator=" + this.failureModeEvaluator + ", failureMode=" + this.failureMode + ", id=" + this.id + ", order=" + this.order + ")";
    }

    @Generated
    public MultifactorAuthenticationProviderBypassEvaluator getBypassEvaluator() {
        return this.bypassEvaluator;
    }

    @Generated
    public MultifactorAuthenticationFailureModeEvaluator getFailureModeEvaluator() {
        return this.failureModeEvaluator;
    }

    @Generated
    public BaseMultifactorAuthenticationProviderProperties.MultifactorAuthenticationProviderFailureModes getFailureMode() {
        return this.failureMode;
    }

    @Generated
    public String getId() {
        return this.id;
    }

    @Generated
    public int getOrder() {
        return this.order;
    }

    @Generated
    public void setBypassEvaluator(MultifactorAuthenticationProviderBypassEvaluator bypassEvaluator) {
        this.bypassEvaluator = bypassEvaluator;
    }

    @Generated
    public void setFailureModeEvaluator(MultifactorAuthenticationFailureModeEvaluator failureModeEvaluator) {
        this.failureModeEvaluator = failureModeEvaluator;
    }

    @Generated
    public void setFailureMode(BaseMultifactorAuthenticationProviderProperties.MultifactorAuthenticationProviderFailureModes failureMode) {
        this.failureMode = failureMode;
    }

    @Generated
    public void setId(String id) {
        this.id = id;
    }

    @Generated
    public void setOrder(int order) {
        this.order = order;
    }

    @Generated
    protected AbstractMultifactorAuthenticationProvider() {
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AbstractMultifactorAuthenticationProvider)) {
            return false;
        }
        AbstractMultifactorAuthenticationProvider other = (AbstractMultifactorAuthenticationProvider)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.order != other.order) {
            return false;
        }
        String this$id = this.id;
        String other$id = other.id;
        return !(this$id == null ? other$id != null : !this$id.equals(other$id));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof AbstractMultifactorAuthenticationProvider;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.order;
        String $id = this.id;
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        return result;
    }
}

