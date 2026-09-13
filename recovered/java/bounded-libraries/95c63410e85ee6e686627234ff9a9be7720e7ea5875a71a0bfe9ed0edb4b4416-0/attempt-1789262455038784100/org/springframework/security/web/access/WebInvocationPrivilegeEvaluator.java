/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.security.core.Authentication
 */
package org.springframework.security.web.access;

import org.springframework.security.core.Authentication;

public interface WebInvocationPrivilegeEvaluator {
    public boolean isAllowed(String var1, Authentication var2);

    public boolean isAllowed(String var1, String var2, String var3, Authentication var4);
}

