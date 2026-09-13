/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.services.RegisteredService
 */
package org.apereo.cas.authentication;

import java.util.Collection;
import org.apereo.cas.authentication.MultifactorAuthenticationProvider;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.services.RegisteredService;

@FunctionalInterface
public interface MultifactorAuthenticationProviderSelector {
    public static final String BEAN_NAME = "multifactorAuthenticationProviderSelector";

    public MultifactorAuthenticationProvider resolve(Collection<MultifactorAuthenticationProvider> var1, RegisteredService var2, Principal var3);
}

