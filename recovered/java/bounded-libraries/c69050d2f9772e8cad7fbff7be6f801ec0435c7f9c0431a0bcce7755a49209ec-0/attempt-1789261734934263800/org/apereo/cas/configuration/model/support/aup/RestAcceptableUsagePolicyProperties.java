/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.support.aup;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.RestEndpointProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-aup-rest")
@JsonFilter(value="RestAcceptableUsagePolicyProperties")
public class RestAcceptableUsagePolicyProperties
extends RestEndpointProperties {
    private static final long serialVersionUID = -8102345678378393382L;
}

