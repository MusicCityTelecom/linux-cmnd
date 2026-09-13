/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.authentication.adaptive.intel.IPAddressIntelligenceResponse
 *  org.apereo.cas.configuration.model.core.authentication.AdaptiveAuthenticationProperties
 *  org.springframework.webflow.execution.RequestContext
 */
package org.apereo.cas.authentication.adaptive.intel;

import org.apereo.cas.authentication.adaptive.intel.BaseIPAddressIntelligenceService;
import org.apereo.cas.authentication.adaptive.intel.IPAddressIntelligenceResponse;
import org.apereo.cas.configuration.model.core.authentication.AdaptiveAuthenticationProperties;
import org.springframework.webflow.execution.RequestContext;

public class DefaultIPAddressIntelligenceService
extends BaseIPAddressIntelligenceService {
    public DefaultIPAddressIntelligenceService(AdaptiveAuthenticationProperties adaptiveAuthenticationProperties) {
        super(adaptiveAuthenticationProperties);
    }

    @Override
    public IPAddressIntelligenceResponse examineInternal(RequestContext context, String clientIpAddress) {
        return IPAddressIntelligenceResponse.allowed();
    }
}

