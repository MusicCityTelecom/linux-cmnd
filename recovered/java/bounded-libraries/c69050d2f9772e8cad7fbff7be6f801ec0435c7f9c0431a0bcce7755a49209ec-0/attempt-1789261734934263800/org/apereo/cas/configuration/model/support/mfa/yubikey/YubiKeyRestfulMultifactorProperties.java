/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.support.mfa.yubikey;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.RestEndpointProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-yubikey")
@JsonFilter(value="YubiKeyRestfulMultifactorProperties")
public class YubiKeyRestfulMultifactorProperties
extends RestEndpointProperties {
    private static final long serialVersionUID = -33291036299848782L;
}

