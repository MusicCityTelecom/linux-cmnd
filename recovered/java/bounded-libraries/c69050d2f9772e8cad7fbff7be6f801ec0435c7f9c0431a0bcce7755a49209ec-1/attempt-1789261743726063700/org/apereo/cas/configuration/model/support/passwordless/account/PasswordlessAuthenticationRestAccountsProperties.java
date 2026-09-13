/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.support.passwordless.account;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.RestEndpointProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-passwordless")
@JsonFilter(value="PasswordlessAuthenticationRestAccountsProperties")
public class PasswordlessAuthenticationRestAccountsProperties
extends RestEndpointProperties {
    private static final long serialVersionUID = -8102345678378393382L;
}

