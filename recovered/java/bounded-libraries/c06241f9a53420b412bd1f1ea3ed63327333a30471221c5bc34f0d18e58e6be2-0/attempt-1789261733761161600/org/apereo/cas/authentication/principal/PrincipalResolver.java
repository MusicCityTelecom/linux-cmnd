/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.services.persondir.IPersonAttributeDao
 *  org.springframework.core.Ordered
 */
package org.apereo.cas.authentication.principal;

import java.util.Optional;
import org.apereo.cas.authentication.AuthenticationHandler;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.services.persondir.IPersonAttributeDao;
import org.springframework.core.Ordered;

public interface PrincipalResolver
extends Ordered {
    public static final String BEAN_NAME_PRINCIPAL_RESOLVER = "defaultPrincipalResolver";
    public static final String BEAN_NAME_ATTRIBUTE_REPOSITORY = "attributeRepository";
    public static final String BEAN_NAME_GLOBAL_PRINCIPAL_ATTRIBUTE_REPOSITORY = "globalPrincipalAttributeRepository";

    default public Principal resolve(Credential credential) {
        return this.resolve(credential, Optional.empty(), Optional.empty());
    }

    default public Principal resolve(Credential credential, Optional<AuthenticationHandler> handler) {
        return this.resolve(credential, Optional.empty(), handler);
    }

    public Principal resolve(Credential var1, Optional<Principal> var2, Optional<AuthenticationHandler> var3);

    public boolean supports(Credential var1);

    public IPersonAttributeDao getAttributeRepository();

    default public String getName() {
        return this.getClass().getSimpleName();
    }

    default public int getOrder() {
        return 0;
    }
}

