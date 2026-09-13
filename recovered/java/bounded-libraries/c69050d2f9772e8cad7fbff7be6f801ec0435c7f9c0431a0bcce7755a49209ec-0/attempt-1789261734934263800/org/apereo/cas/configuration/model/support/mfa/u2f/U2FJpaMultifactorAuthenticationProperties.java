/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.support.mfa.u2f;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.support.jpa.AbstractJpaProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-u2f-jpa")
@JsonFilter(value="U2FJpaMultifactorAuthenticationProperties")
public class U2FJpaMultifactorAuthenticationProperties
extends AbstractJpaProperties {
    private static final long serialVersionUID = -4334840263678287815L;
}

