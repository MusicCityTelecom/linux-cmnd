/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.support.mfa.trusteddevice;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.RestEndpointProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-trusted-mfa-rest")
@JsonFilter(value="RestfulTrustedDevicesMultifactorProperties")
public class RestfulTrustedDevicesMultifactorProperties
extends RestEndpointProperties {
    private static final long serialVersionUID = 3659099897056632608L;
}

