/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  org.apereo.cas.configuration.model.support.mfa.BaseMultifactorAuthenticationProviderProperties$MultifactorAuthenticationProviderFailureModes
 *  org.apereo.cas.configuration.support.ExpressionLanguageCapable
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.Serializable;
import java.util.Set;
import org.apereo.cas.configuration.model.support.mfa.BaseMultifactorAuthenticationProviderProperties;
import org.apereo.cas.configuration.support.ExpressionLanguageCapable;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public interface RegisteredServiceMultifactorPolicy
extends Serializable {
    public Set<String> getMultifactorAuthenticationProviders();

    public BaseMultifactorAuthenticationProviderProperties.MultifactorAuthenticationProviderFailureModes getFailureMode();

    public String getPrincipalAttributeNameTrigger();

    public String getPrincipalAttributeValueToMatch();

    public boolean isBypassEnabled();

    public boolean isForceExecution();

    public boolean isBypassTrustedDeviceEnabled();

    public String getBypassPrincipalAttributeName();

    public String getBypassPrincipalAttributeValue();

    @ExpressionLanguageCapable
    public String getScript();
}

