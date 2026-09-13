/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.authentication.principal.Principal
 */
package org.apereo.cas.authentication.principal;

import java.util.List;
import java.util.Map;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.authentication.principal.RegisteredServicePrincipalAttributesRepository;
import org.apereo.cas.services.RegisteredService;

public interface PrincipalAttributesRepositoryCache {
    public static final String DEFAULT_BEAN_NAME = "principalAttributesRepositoryCache";

    public void invalidate();

    public Map<String, List<Object>> fetchAttributes(RegisteredService var1, RegisteredServicePrincipalAttributesRepository var2, Principal var3);

    public void putAttributes(RegisteredService var1, RegisteredServicePrincipalAttributesRepository var2, String var3, Map<String, List<Object>> var4);
}

