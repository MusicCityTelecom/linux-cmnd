/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.support.mfa.webauthn;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.RestEndpointProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-webauthn-rest")
@JsonFilter(value="WebAuthnRestfulMultifactorProperties")
public class WebAuthnRestfulMultifactorProperties
extends RestEndpointProperties {
    private static final long serialVersionUID = -77291036299848782L;
}

