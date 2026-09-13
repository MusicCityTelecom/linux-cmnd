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
@JsonFilter(value="RestfulMultifactorAuthenticationProviderBypassProperties")
public class RestfulMultifactorAuthenticationProviderBypassProperties
extends RestEndpointProperties {
    private static final long serialVersionUID = 1833594332973137011L;
}

