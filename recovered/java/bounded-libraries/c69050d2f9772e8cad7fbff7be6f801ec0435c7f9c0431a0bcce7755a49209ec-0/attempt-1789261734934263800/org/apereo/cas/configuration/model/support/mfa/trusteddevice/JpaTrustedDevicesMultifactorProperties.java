/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.support.mfa.trusteddevice;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.support.jpa.AbstractJpaProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-trusted-mfa-jdbc")
@JsonFilter(value="JpaTrustedDevicesMultifactorProperties")
public class JpaTrustedDevicesMultifactorProperties
extends AbstractJpaProperties {
    private static final long serialVersionUID = -8329950619696176349L;
}

