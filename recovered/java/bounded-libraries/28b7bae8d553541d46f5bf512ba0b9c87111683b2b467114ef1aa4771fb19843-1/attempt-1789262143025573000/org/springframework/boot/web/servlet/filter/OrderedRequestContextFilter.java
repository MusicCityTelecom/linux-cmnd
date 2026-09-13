/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.filter.RequestContextFilter
 */
package org.springframework.boot.web.servlet.filter;

import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.web.filter.RequestContextFilter;

public class OrderedRequestContextFilter
extends RequestContextFilter
implements OrderedFilter {
    private int order = -105;

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}

