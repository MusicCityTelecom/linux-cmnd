/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.Filter
 *  javax.servlet.http.HttpServletRequest
 */
package org.springframework.security.web;

import java.util.List;
import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;

public interface SecurityFilterChain {
    public boolean matches(HttpServletRequest var1);

    public List<Filter> getFilters();
}

