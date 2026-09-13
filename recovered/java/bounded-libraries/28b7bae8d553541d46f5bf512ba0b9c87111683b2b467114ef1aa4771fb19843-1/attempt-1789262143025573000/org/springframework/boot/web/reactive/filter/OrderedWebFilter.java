/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.Ordered
 *  org.springframework.web.server.WebFilter
 */
package org.springframework.boot.web.reactive.filter;

import org.springframework.core.Ordered;
import org.springframework.web.server.WebFilter;

public interface OrderedWebFilter
extends WebFilter,
Ordered {
    public static final int REQUEST_WRAPPER_FILTER_MAX_ORDER = 0;
}

