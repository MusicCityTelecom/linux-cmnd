/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package org.apereo.cas.logout.slo;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apereo.cas.logout.slo.SingleLogoutRequestContext;

@FunctionalInterface
public interface SingleLogoutRequestExecutor {
    public static final String BEAN_NAME = "defaultSingleLogoutRequestExecutor";

    public List<SingleLogoutRequestContext> execute(String var1, HttpServletRequest var2, HttpServletResponse var3);
}

