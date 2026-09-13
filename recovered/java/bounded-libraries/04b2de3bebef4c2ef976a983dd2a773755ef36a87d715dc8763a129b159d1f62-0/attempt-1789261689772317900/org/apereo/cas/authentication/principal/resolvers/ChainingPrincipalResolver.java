/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.AuthenticationHandler
 *  org.apereo.cas.authentication.Credential
 *  org.apereo.cas.authentication.PrincipalElectionStrategy
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.authentication.principal.PrincipalFactory
 *  org.apereo.cas.authentication.principal.PrincipalResolver
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.services.persondir.IPersonAttributeDao
 *  org.apereo.services.persondir.support.MergingPersonAttributeDaoImpl
 *  org.apereo.services.persondir.support.merger.IAttributeMerger
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.authentication.principal.resolvers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Generated;
import org.apereo.cas.authentication.AuthenticationHandler;
import org.apereo.cas.authentication.CoreAuthenticationUtils;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.PrincipalElectionStrategy;
import org.apereo.cas.authentication.principal.NullPrincipal;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.authentication.principal.PrincipalFactoryUtils;
import org.apereo.cas.authentication.principal.PrincipalResolver;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.services.persondir.IPersonAttributeDao;
import org.apereo.services.persondir.support.MergingPersonAttributeDaoImpl;
import org.apereo.services.persondir.support.merger.IAttributeMerger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChainingPrincipalResolver
implements PrincipalResolver {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(ChainingPrincipalResolver.class);
    private final PrincipalFactory principalFactory = PrincipalFactoryUtils.newPrincipalFactory();
    private List<PrincipalResolver> chain;
    private final PrincipalElectionStrategy principalElectionStrategy;
    private final CasConfigurationProperties casProperties;

    public Principal resolve(Credential credential, Optional<Principal> principal, Optional<AuthenticationHandler> handler) {
        ArrayList<Principal> principals = new ArrayList<Principal>(this.chain.size());
        this.chain.stream().filter(resolver -> resolver.supports(credential)).forEach(resolver -> {
            LOGGER.debug("Invoking principal resolver [{}]", (Object)resolver.getName());
            Principal p = resolver.resolve(credential, principal, handler);
            if (p != null) {
                LOGGER.debug("Resolved principal [{}]", (Object)p);
                principals.add(p);
            }
        });
        if (principals.isEmpty()) {
            LOGGER.warn("None of the principal resolvers in the chain were able to produce a principal");
            return NullPrincipal.getInstance();
        }
        HashMap attributes = new HashMap();
        IAttributeMerger merger = CoreAuthenticationUtils.getAttributeMerger(this.casProperties.getAuthn().getAttributeRepository().getCore().getMerger());
        principals.forEach(p -> {
            if (p != null) {
                LOGGER.debug("Resolved principal [{}]", p);
                Map principalAttributes = p.getAttributes();
                if (principalAttributes != null && !principalAttributes.isEmpty()) {
                    LOGGER.debug("Adding attributes [{}] for the final principal", (Object)principalAttributes);
                    attributes.putAll(CoreAuthenticationUtils.mergeAttributes(attributes, principalAttributes, merger));
                }
            }
        });
        return this.principalElectionStrategy.nominate(principals, attributes);
    }

    public boolean supports(Credential credential) {
        return this.chain.stream().anyMatch(r -> r.supports(credential));
    }

    public IPersonAttributeDao getAttributeRepository() {
        MergingPersonAttributeDaoImpl dao = new MergingPersonAttributeDaoImpl();
        dao.setPersonAttributeDaos(this.chain.stream().map(PrincipalResolver::getAttributeRepository).collect(Collectors.toList()));
        return dao;
    }

    @Generated
    public String toString() {
        return "ChainingPrincipalResolver(principalFactory=" + this.principalFactory + ", chain=" + this.chain + ", principalElectionStrategy=" + this.principalElectionStrategy + ", casProperties=" + this.casProperties + ")";
    }

    @Generated
    public void setChain(List<PrincipalResolver> chain) {
        this.chain = chain;
    }

    @Generated
    public ChainingPrincipalResolver(PrincipalElectionStrategy principalElectionStrategy, CasConfigurationProperties casProperties) {
        this.principalElectionStrategy = principalElectionStrategy;
        this.casProperties = casProperties;
    }
}

