/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.adaptive.intel.IPAddressIntelligenceResponse
 *  org.apereo.cas.configuration.model.core.authentication.AdaptiveAuthenticationProperties
 *  org.apereo.cas.util.scripting.WatchableGroovyScriptResource
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.core.io.Resource
 *  org.springframework.webflow.execution.RequestContext
 */
package org.apereo.cas.authentication.adaptive.intel;

import lombok.Generated;
import org.apereo.cas.authentication.adaptive.intel.BaseIPAddressIntelligenceService;
import org.apereo.cas.authentication.adaptive.intel.IPAddressIntelligenceResponse;
import org.apereo.cas.configuration.model.core.authentication.AdaptiveAuthenticationProperties;
import org.apereo.cas.util.scripting.WatchableGroovyScriptResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.webflow.execution.RequestContext;

public class GroovyIPAddressIntelligenceService
extends BaseIPAddressIntelligenceService {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(GroovyIPAddressIntelligenceService.class);
    private final transient WatchableGroovyScriptResource watchableScript;

    public GroovyIPAddressIntelligenceService(AdaptiveAuthenticationProperties adaptiveAuthenticationProperties) {
        super(adaptiveAuthenticationProperties);
        Resource groovyScript = adaptiveAuthenticationProperties.getIpIntel().getGroovy().getLocation();
        this.watchableScript = new WatchableGroovyScriptResource(groovyScript);
    }

    @Override
    public IPAddressIntelligenceResponse examineInternal(RequestContext context, String clientIpAddress) {
        Object[] args = new Object[]{context, clientIpAddress, LOGGER};
        return (IPAddressIntelligenceResponse)this.watchableScript.execute(args, IPAddressIntelligenceResponse.class);
    }
}

