/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.health;

import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.boot.actuate.health.AdditionalHealthEndpointPath;
import org.springframework.boot.actuate.health.HttpCodeStatusMapper;
import org.springframework.boot.actuate.health.StatusAggregator;

public interface HealthEndpointGroup {
    public boolean isMember(String var1);

    public boolean showComponents(SecurityContext var1);

    public boolean showDetails(SecurityContext var1);

    public StatusAggregator getStatusAggregator();

    public HttpCodeStatusMapper getHttpCodeStatusMapper();

    public AdditionalHealthEndpointPath getAdditionalPath();
}

