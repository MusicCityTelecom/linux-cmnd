/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.filter.CharacterEncodingFilter
 */
package org.springframework.boot.web.servlet.filter;

import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

public class OrderedCharacterEncodingFilter
extends CharacterEncodingFilter
implements OrderedFilter {
    private int order = Integer.MIN_VALUE;

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}

