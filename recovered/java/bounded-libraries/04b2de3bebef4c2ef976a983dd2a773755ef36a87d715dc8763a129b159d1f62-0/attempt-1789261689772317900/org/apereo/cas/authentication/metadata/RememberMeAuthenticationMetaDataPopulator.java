/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.authentication.AuthenticationBuilder
 *  org.apereo.cas.authentication.AuthenticationTransaction
 *  org.apereo.cas.authentication.Credential
 *  org.apereo.cas.authentication.RememberMeCredential
 *  org.apereo.cas.configuration.model.core.ticket.RememberMeAuthenticationProperties
 *  org.apereo.cas.util.RegexUtils
 *  org.apereo.inspektr.common.web.ClientInfo
 *  org.apereo.inspektr.common.web.ClientInfoHolder
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.authentication.metadata;

import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.AuthenticationBuilder;
import org.apereo.cas.authentication.AuthenticationTransaction;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.RememberMeCredential;
import org.apereo.cas.authentication.metadata.BaseAuthenticationMetaDataPopulator;
import org.apereo.cas.configuration.model.core.ticket.RememberMeAuthenticationProperties;
import org.apereo.cas.util.RegexUtils;
import org.apereo.inspektr.common.web.ClientInfo;
import org.apereo.inspektr.common.web.ClientInfoHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RememberMeAuthenticationMetaDataPopulator
extends BaseAuthenticationMetaDataPopulator {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(RememberMeAuthenticationMetaDataPopulator.class);
    private final RememberMeAuthenticationProperties properties;

    public void populateAttributes(AuthenticationBuilder builder, AuthenticationTransaction transaction) {
        transaction.getPrimaryCredential().ifPresent(r -> {
            if (((RememberMeCredential)RememberMeCredential.class.cast(r)).isRememberMe()) {
                LOGGER.debug("Credential is configured to be remembered. Captured this as [{}] attribute", (Object)"org.apereo.cas.authentication.principal.REMEMBER_ME");
                boolean rememberMe = true;
                ClientInfo clientInfo = ClientInfoHolder.getClientInfo();
                if (clientInfo != null) {
                    if (StringUtils.isNotBlank((CharSequence)this.properties.getSupportedUserAgents()) && StringUtils.isNotBlank((CharSequence)this.properties.getSupportedIpAddresses())) {
                        rememberMe = RegexUtils.find((String)this.properties.getSupportedUserAgents(), (String)clientInfo.getUserAgent()) && RegexUtils.find((String)this.properties.getSupportedIpAddresses(), (String)clientInfo.getClientIpAddress());
                    } else if (StringUtils.isNotBlank((CharSequence)this.properties.getSupportedUserAgents())) {
                        rememberMe = RegexUtils.find((String)this.properties.getSupportedUserAgents(), (String)clientInfo.getUserAgent());
                    } else if (StringUtils.isNotBlank((CharSequence)this.properties.getSupportedIpAddresses())) {
                        rememberMe = RegexUtils.find((String)this.properties.getSupportedIpAddresses(), (String)clientInfo.getClientIpAddress());
                    }
                }
                if (rememberMe) {
                    builder.addAttribute("org.apereo.cas.authentication.principal.REMEMBER_ME", (Object)Boolean.TRUE);
                }
            }
        });
    }

    public boolean supports(Credential credential) {
        return credential instanceof RememberMeCredential;
    }

    @Override
    @Generated
    public String toString() {
        return "RememberMeAuthenticationMetaDataPopulator(super=" + super.toString() + ", properties=" + this.properties + ")";
    }

    @Generated
    public RememberMeAuthenticationMetaDataPopulator(RememberMeAuthenticationProperties properties) {
        this.properties = properties;
    }
}

