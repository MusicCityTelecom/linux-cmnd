/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.logout;

import java.util.List;
import org.apereo.cas.logout.SingleLogoutExecutionRequest;
import org.apereo.cas.logout.slo.SingleLogoutRequestContext;

@FunctionalInterface
public interface LogoutManager {
    public static final String DEFAULT_BEAN_NAME = "logoutManager";

    public List<SingleLogoutRequestContext> performLogout(SingleLogoutExecutionRequest var1);
}

