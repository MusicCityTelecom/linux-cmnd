/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.endpoint.web.annotation;

import org.springframework.boot.actuate.endpoint.annotation.DiscovererEndpointFilter;
import org.springframework.boot.actuate.endpoint.web.annotation.WebEndpointDiscoverer;

class WebEndpointFilter
extends DiscovererEndpointFilter {
    WebEndpointFilter() {
        super(WebEndpointDiscoverer.class);
    }
}

