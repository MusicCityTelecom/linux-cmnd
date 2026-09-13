/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.support.mfa;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.RestEndpointProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-core-authentication", automated=true)
@JsonFilter(value="RestfulMultifactorAuthenticationProperties")
public class RestfulMultifactorAuthenticationProperties
extends RestEndpointProperties {
    private static final long serialVersionUID = 3659099897056632608L;
}

