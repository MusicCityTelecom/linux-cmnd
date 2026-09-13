/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.AuthenticationHandler
 *  org.apereo.cas.authentication.Credential
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.authentication.principal.PrincipalFactory
 *  org.apereo.cas.authentication.principal.PrincipalResolver
 *  org.apereo.services.persondir.IPersonAttributeDao
 */
package org.apereo.cas.authentication.principal.resolvers;

import java.util.Optional;
import lombok.Generated;
import org.apereo.cas.authentication.AuthenticationHandler;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.authentication.principal.PrincipalResolver;
import org.apereo.services.persondir.IPersonAttributeDao;

public class ProxyingPrincipalResolver
implements PrincipalResolver {
    private final PrincipalFactory principalFactory;

    public Principal resolve(Credential credential, Optional<Principal> currentPrincipal, Optional<AuthenticationHandler> handler) {
        return this.principalFactory.createPrincipal(credential.getId());
    }

    public boolean supports(Credential credential) {
        return credential.getId() != null;
    }

    public IPersonAttributeDao getAttributeRepository() {
        return null;
    }

    @Generated
    public String toString() {
        return "ProxyingPrincipalResolver(principalFactory=" + this.principalFactory + ")";
    }

    @Generated
    public ProxyingPrincipalResolver(PrincipalFactory principalFactory) {
        this.principalFactory = principalFactory;
    }
}

