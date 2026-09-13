/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.support.mfa.webauthn;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.support.jpa.AbstractJpaProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-webauthn-jpa")
@JsonFilter(value="WebAuthnJpaMultifactorProperties")
public class WebAuthnJpaMultifactorProperties
extends AbstractJpaProperties {
    private static final long serialVersionUID = -4114840263678287815L;
}

