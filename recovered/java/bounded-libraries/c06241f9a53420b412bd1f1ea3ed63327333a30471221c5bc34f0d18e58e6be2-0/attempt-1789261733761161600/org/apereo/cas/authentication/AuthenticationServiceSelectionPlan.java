/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.authentication;

import org.apereo.cas.authentication.AuthenticationServiceSelectionStrategy;
import org.apereo.cas.authentication.principal.Service;

public interface AuthenticationServiceSelectionPlan {
    public static final String BEAN_NAME = "authenticationServiceSelectionPlan";

    public void registerStrategy(AuthenticationServiceSelectionStrategy var1);

    public Service resolveService(Service var1);

    public <T extends Service> T resolveService(Service var1, Class<T> var2);
}

