/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.filter.FormContentFilter
 */
package org.springframework.boot.web.servlet.filter;

import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.web.filter.FormContentFilter;

public class OrderedFormContentFilter
extends FormContentFilter
implements OrderedFilter {
    public static final int DEFAULT_ORDER = -9900;
    private int order = -9900;

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}

