/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.authentication.adaptive.intel.IPAddressIntelligenceResponse
 *  org.apereo.cas.authentication.adaptive.intel.IPAddressIntelligenceService
 *  org.apereo.cas.configuration.model.core.authentication.AdaptiveAuthenticationProperties
 *  org.springframework.webflow.execution.RequestContext
 */
package org.apereo.cas.authentication.adaptive.intel;

import java.util.regex.Pattern;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.adaptive.intel.IPAddressIntelligenceResponse;
import org.apereo.cas.authentication.adaptive.intel.IPAddressIntelligenceService;
import org.apereo.cas.configuration.model.core.authentication.AdaptiveAuthenticationProperties;
import org.springframework.webflow.execution.RequestContext;

public abstract class BaseIPAddressIntelligenceService
implements IPAddressIntelligenceService {
    protected final AdaptiveAuthenticationProperties adaptiveAuthenticationProperties;

    private static void trackResponseInRequestContext(RequestContext context, IPAddressIntelligenceResponse response) {
        context.getFlowScope().put("ipAddressIntelligenceResponse", (Object)response);
    }

    private boolean isClientIpAddressRejected(String clientIp) {
        String rejectIpAddresses = this.adaptiveAuthenticationProperties.getPolicy().getRejectIpAddresses();
        return StringUtils.isNotBlank((CharSequence)rejectIpAddresses) && Pattern.compile(rejectIpAddresses).matcher(clientIp).find();
    }

    public IPAddressIntelligenceResponse examine(RequestContext context, String clientIpAddress) {
        if (this.isClientIpAddressRejected(clientIpAddress)) {
            IPAddressIntelligenceResponse response = IPAddressIntelligenceResponse.banned();
            BaseIPAddressIntelligenceService.trackResponseInRequestContext(context, response);
            return response;
        }
        IPAddressIntelligenceResponse response = this.examineInternal(context, clientIpAddress);
        BaseIPAddressIntelligenceService.trackResponseInRequestContext(context, response);
        return response;
    }

    public abstract IPAddressIntelligenceResponse examineInternal(RequestContext var1, String var2);

    @Generated
    protected BaseIPAddressIntelligenceService(AdaptiveAuthenticationProperties adaptiveAuthenticationProperties) {
        this.adaptiveAuthenticationProperties = adaptiveAuthenticationProperties;
    }
}

