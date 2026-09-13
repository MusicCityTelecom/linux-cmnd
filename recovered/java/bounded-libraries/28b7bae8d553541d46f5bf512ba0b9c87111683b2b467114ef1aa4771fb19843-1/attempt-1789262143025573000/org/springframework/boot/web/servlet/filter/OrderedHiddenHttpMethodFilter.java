/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.filter.HiddenHttpMethodFilter
 */
package org.springframework.boot.web.servlet.filter;

import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;

public class OrderedHiddenHttpMethodFilter
extends HiddenHttpMethodFilter
implements OrderedFilter {
    public static final int DEFAULT_ORDER = -10000;
    private int order = -10000;

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}

