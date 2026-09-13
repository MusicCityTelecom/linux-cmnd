/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.authentication.AuthenticationHandler
 *  org.apereo.cas.authentication.Credential
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.authentication.principal.PrincipalResolver
 *  org.apereo.services.persondir.IPersonAttributeDao
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.authentication.principal.resolvers;

import java.util.Optional;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.AuthenticationHandler;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.authentication.principal.PrincipalResolver;
import org.apereo.services.persondir.IPersonAttributeDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EchoingPrincipalResolver
implements PrincipalResolver {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(EchoingPrincipalResolver.class);

    public Principal resolve(Credential credential, Optional<Principal> principal, Optional<AuthenticationHandler> handler) {
        LOGGER.debug("Echoing back the authenticated principal [{}]", principal);
        return principal.orElse(null);
    }

    public boolean supports(Credential credential) {
        return StringUtils.isNotBlank((CharSequence)credential.getId());
    }

    public IPersonAttributeDao getAttributeRepository() {
        return null;
    }

    @Generated
    public String toString() {
        return "EchoingPrincipalResolver()";
    }
}

