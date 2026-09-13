/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.support.mfa.webauthn;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.SpringResourceProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-webauthn")
@JsonFilter(value="WebAuthnJsonMultifactorProperties")
public class WebAuthnJsonMultifactorProperties
extends SpringResourceProperties {
    private static final long serialVersionUID = -1283660787308509919L;
}

