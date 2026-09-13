/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.filter.reactive.HiddenHttpMethodFilter
 */
package org.springframework.boot.web.reactive.filter;

import org.springframework.boot.web.reactive.filter.OrderedWebFilter;
import org.springframework.web.filter.reactive.HiddenHttpMethodFilter;

public class OrderedHiddenHttpMethodFilter
extends HiddenHttpMethodFilter
implements OrderedWebFilter {
    public static final int DEFAULT_ORDER = -10000;
    private int order = -10000;

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}

