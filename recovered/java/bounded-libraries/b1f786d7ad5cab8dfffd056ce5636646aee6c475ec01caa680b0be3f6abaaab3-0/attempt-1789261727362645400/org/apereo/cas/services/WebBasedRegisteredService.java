/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  org.apereo.cas.configuration.support.ExpressionLanguageCapable
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.apereo.cas.configuration.support.ExpressionLanguageCapable;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.RegisteredServiceAcceptableUsagePolicy;
import org.apereo.cas.services.RegisteredServiceLogoutType;
import org.apereo.cas.services.RegisteredServiceSingleSignOnParticipationPolicy;
import org.apereo.cas.services.RegisteredServiceWebflowInterruptPolicy;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public interface WebBasedRegisteredService
extends RegisteredService {
    public RegisteredServiceWebflowInterruptPolicy getWebflowInterruptPolicy();

    public RegisteredServiceAcceptableUsagePolicy getAcceptableUsagePolicy();

    public RegisteredServiceSingleSignOnParticipationPolicy getSingleSignOnParticipationPolicy();

    public String getLogo();

    public String getInformationUrl();

    public String getPrivacyUrl();

    public RegisteredServiceLogoutType getLogoutType();

    public String getLogoutUrl();

    @ExpressionLanguageCapable
    public String getTheme();

    @ExpressionLanguageCapable
    public String getLocale();
}

