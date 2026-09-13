/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.PrincipalElectionStrategy
 *  org.apereo.cas.authentication.PrincipalElectionStrategyConflictResolver
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.authentication.principal.PrincipalFactory
 *  org.apereo.services.persondir.support.merger.IAttributeMerger
 *  org.apereo.services.persondir.support.merger.ReplacingAttributeAdder
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.authentication.principal;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.Generated;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.PrincipalElectionStrategy;
import org.apereo.cas.authentication.PrincipalElectionStrategyConflictResolver;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.authentication.principal.PrincipalFactoryUtils;
import org.apereo.services.persondir.support.merger.IAttributeMerger;
import org.apereo.services.persondir.support.merger.ReplacingAttributeAdder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultPrincipalElectionStrategy
implements PrincipalElectionStrategy {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultPrincipalElectionStrategy.class);
    private static final long serialVersionUID = 6704726217030836315L;
    private IAttributeMerger attributeMerger = new ReplacingAttributeAdder();
    private final PrincipalFactory principalFactory;
    private int order = Integer.MAX_VALUE;
    private final PrincipalElectionStrategyConflictResolver principalElectionConflictResolver;

    public DefaultPrincipalElectionStrategy() {
        this(PrincipalFactoryUtils.newPrincipalFactory(), PrincipalElectionStrategyConflictResolver.last());
    }

    public DefaultPrincipalElectionStrategy(PrincipalElectionStrategyConflictResolver principalElectionConflictResolver) {
        this(PrincipalFactoryUtils.newPrincipalFactory(), principalElectionConflictResolver);
    }

    public Principal nominate(Collection<Authentication> authentications, Map<String, List<Object>> principalAttributes) {
        Principal principal = this.getPrincipalFromAuthentication(authentications);
        Map<String, List<Object>> attributes = this.getPrincipalAttributesForPrincipal(principal, principalAttributes);
        Principal finalPrincipal = this.principalFactory.createPrincipal(principal.getId(), attributes);
        LOGGER.debug("Nominated [{}] as the primary principal", (Object)finalPrincipal);
        return finalPrincipal;
    }

    public Principal nominate(List<Principal> principals, Map<String, List<Object>> attributes) {
        LinkedHashSet principalIds = principals.stream().filter(Objects::nonNull).map(p -> p.getId().trim().toLowerCase()).collect(Collectors.toCollection(LinkedHashSet::new));
        int count = principalIds.size();
        if (count > 1) {
            LOGGER.debug("Principal resolvers produced [{}] distinct principals [{}]", (Object)count, (Object)principalIds);
        }
        String principalId = this.principalElectionConflictResolver.resolve(principals, attributes);
        Principal finalPrincipal = this.principalFactory.createPrincipal(principalId, attributes);
        LOGGER.debug("Final principal constructed by the chain of resolvers is [{}]", (Object)finalPrincipal);
        return finalPrincipal;
    }

    protected Map<String, List<Object>> getPrincipalAttributesForPrincipal(Principal principal, Map<String, List<Object>> principalAttributes) {
        return principalAttributes;
    }

    protected Principal getPrincipalFromAuthentication(Collection<Authentication> authentications) {
        return authentications.iterator().next().getPrincipal();
    }

    @Generated
    public DefaultPrincipalElectionStrategy(PrincipalFactory principalFactory, PrincipalElectionStrategyConflictResolver principalElectionConflictResolver) {
        this.principalFactory = principalFactory;
        this.principalElectionConflictResolver = principalElectionConflictResolver;
    }

    @Generated
    public void setAttributeMerger(IAttributeMerger attributeMerger) {
        this.attributeMerger = attributeMerger;
    }

    @Generated
    public void setOrder(int order) {
        this.order = order;
    }

    @Generated
    public IAttributeMerger getAttributeMerger() {
        return this.attributeMerger;
    }

    @Generated
    public PrincipalFactory getPrincipalFactory() {
        return this.principalFactory;
    }

    @Generated
    public int getOrder() {
        return this.order;
    }

    @Generated
    public PrincipalElectionStrategyConflictResolver getPrincipalElectionConflictResolver() {
        return this.principalElectionConflictResolver;
    }
}

