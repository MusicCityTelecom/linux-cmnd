/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.support.mfa.trusteddevice;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.SpringResourceProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-trusted-mfa")
@JsonFilter(value="JsonTrustedDevicesMultifactorProperties")
public class JsonTrustedDevicesMultifactorProperties
extends SpringResourceProperties {
    private static final long serialVersionUID = -8690563713141571620L;
}

