/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.authentication.AuthenticationBuilder
 *  org.apereo.cas.authentication.AuthenticationTransaction
 *  org.apereo.cas.authentication.Credential
 *  org.apereo.cas.util.function.FunctionUtils
 *  org.apereo.inspektr.common.web.ClientInfo
 *  org.apereo.inspektr.common.web.ClientInfoHolder
 */
package org.apereo.cas.authentication.metadata;

import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.AuthenticationBuilder;
import org.apereo.cas.authentication.AuthenticationTransaction;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.metadata.BaseAuthenticationMetaDataPopulator;
import org.apereo.cas.util.function.FunctionUtils;
import org.apereo.inspektr.common.web.ClientInfo;
import org.apereo.inspektr.common.web.ClientInfoHolder;

public class ClientInfoAuthenticationMetaDataPopulator
extends BaseAuthenticationMetaDataPopulator {
    public static final String ATTRIBUTE_CLIENT_IP_ADDRESS = "clientIpAddress";
    public static final String ATTRIBUTE_SERVER_IP_ADDRESS = "serverIpAddress";
    public static final String ATTRIBUTE_USER_AGENT = "userAgent";
    public static final String ATTRIBUTE_GEO_LOCATION = "geoLocation";

    private static void addAttribute(AuthenticationBuilder builder, String name, String value) {
        FunctionUtils.doIf((boolean)StringUtils.isNotBlank((CharSequence)value), v -> builder.mergeAttribute(name, v)).accept(value);
    }

    public void populateAttributes(AuthenticationBuilder builder, AuthenticationTransaction transaction) {
        ClientInfo clientInfo = ClientInfoHolder.getClientInfo();
        if (clientInfo != null) {
            transaction.getPrimaryCredential().ifPresent(c -> {
                ClientInfoAuthenticationMetaDataPopulator.addAttribute(builder, ATTRIBUTE_CLIENT_IP_ADDRESS, clientInfo.getClientIpAddress());
                ClientInfoAuthenticationMetaDataPopulator.addAttribute(builder, ATTRIBUTE_SERVER_IP_ADDRESS, clientInfo.getServerIpAddress());
                ClientInfoAuthenticationMetaDataPopulator.addAttribute(builder, ATTRIBUTE_USER_AGENT, clientInfo.getUserAgent());
                ClientInfoAuthenticationMetaDataPopulator.addAttribute(builder, ATTRIBUTE_GEO_LOCATION, clientInfo.getGeoLocation());
            });
        }
    }

    public boolean supports(Credential credential) {
        return credential != null && ClientInfoHolder.getClientInfo() != null;
    }
}

