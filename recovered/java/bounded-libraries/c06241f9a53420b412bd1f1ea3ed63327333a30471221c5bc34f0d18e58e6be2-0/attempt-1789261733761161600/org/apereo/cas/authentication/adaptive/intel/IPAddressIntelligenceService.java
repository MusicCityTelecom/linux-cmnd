/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.webflow.execution.RequestContext
 */
package org.apereo.cas.authentication.adaptive.intel;

import org.apereo.cas.authentication.adaptive.intel.IPAddressIntelligenceResponse;
import org.springframework.webflow.execution.RequestContext;

@FunctionalInterface
public interface IPAddressIntelligenceService {
    public IPAddressIntelligenceResponse examine(RequestContext var1, String var2);

    public static IPAddressIntelligenceService allowed() {
        return (context, clientIpAddress) -> IPAddressIntelligenceResponse.allowed();
    }

    public static IPAddressIntelligenceService banned() {
        return (context, clientIpAddress) -> IPAddressIntelligenceResponse.banned();
    }
}

