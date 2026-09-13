/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 */
package org.springframework.security.web.header.writers.frameoptions;

import javax.servlet.http.HttpServletRequest;

@Deprecated
public interface AllowFromStrategy {
    public String getAllowFromValue(HttpServletRequest var1);
}

