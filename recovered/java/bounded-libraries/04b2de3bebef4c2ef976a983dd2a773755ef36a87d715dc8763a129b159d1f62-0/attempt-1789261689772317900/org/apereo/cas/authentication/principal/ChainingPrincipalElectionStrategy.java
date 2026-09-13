/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.PrincipalElectionStrategy
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.services.persondir.support.merger.IAttributeMerger
 *  org.apereo.services.persondir.support.merger.ReplacingAttributeAdder
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.core.annotation.AnnotationAwareOrderComparator
 */
package org.apereo.cas.authentication.principal;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Generated;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.PrincipalElectionStrategy;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.services.persondir.support.merger.IAttributeMerger;
import org.apereo.services.persondir.support.merger.ReplacingAttributeAdder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

public class ChainingPrincipalElectionStrategy
implements PrincipalElectionStrategy {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(ChainingPrincipalElectionStrategy.class);
    private static final long serialVersionUID = 3686895489996430873L;
    private final List<PrincipalElectionStrategy> chain;
    private IAttributeMerger attributeMerger = new ReplacingAttributeAdder();

    public ChainingPrincipalElectionStrategy(PrincipalElectionStrategy ... chain) {
        this.chain = Stream.of(chain).collect(Collectors.toList());
    }

    public void registerElectionStrategy(PrincipalElectionStrategy factory) {
        this.chain.add(factory);
        AnnotationAwareOrderComparator.sort(this.chain);
    }

    public Principal nominate(Collection<Authentication> authentications, Map<String, List<Object>> principalAttributes) {
        Principal principal = this.chain.stream().sorted(Comparator.comparing(PrincipalElectionStrategy::getOrder)).map(strategy -> strategy.nominate(authentications, principalAttributes)).filter(Objects::nonNull).findFirst().orElseThrow();
        LOGGER.trace("Nominated principal [{}] from authentication chain [{}]", (Object)principal, authentications);
        return principal;
    }

    public Principal nominate(List<Principal> principals, Map<String, List<Object>> attributes) {
        Principal principal = this.chain.stream().sorted(Comparator.comparing(PrincipalElectionStrategy::getOrder)).map(strategy -> strategy.nominate(principals, attributes)).findFirst().orElseThrow();
        LOGGER.trace("Nominated principal [{}] from principal chain [{}]", (Object)principal, principals);
        return principal;
    }

    @Generated
    public ChainingPrincipalElectionStrategy(List<PrincipalElectionStrategy> chain) {
        this.chain = chain;
    }

    @Generated
    public List<PrincipalElectionStrategy> getChain() {
        return this.chain;
    }

    @Generated
    public IAttributeMerger getAttributeMerger() {
        return this.attributeMerger;
    }

    @Generated
    public void setAttributeMerger(IAttributeMerger attributeMerger) {
        this.attributeMerger = attributeMerger;
    }
}

