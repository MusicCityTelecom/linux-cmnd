/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.support.mfa.u2f;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.SpringResourceProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-u2f")
@JsonFilter(value="U2FGroovyMultifactorAuthenticationProperties")
public class U2FGroovyMultifactorAuthenticationProperties
extends SpringResourceProperties {
    private static final long serialVersionUID = -1261683393319585262L;
}

