/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.support.consent;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.BaseRestEndpointProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-consent-rest")
@JsonFilter(value="RestfulConsentProperties")
public class RestfulConsentProperties
extends BaseRestEndpointProperties {
    private static final long serialVersionUID = -6909617495470495341L;
}

