/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.Filter
 *  org.springframework.core.Ordered
 */
package org.springframework.boot.web.servlet.filter;

import javax.servlet.Filter;
import org.springframework.core.Ordered;

public interface OrderedFilter
extends Filter,
Ordered {
    public static final int REQUEST_WRAPPER_FILTER_MAX_ORDER = 0;
}

