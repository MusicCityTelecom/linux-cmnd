/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.AuthenticationServiceSelectionPlan
 *  org.apereo.cas.authentication.AuthenticationServiceSelectionStrategy
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.util.spring.beans.BeanSupplier
 *  org.springframework.core.annotation.AnnotationAwareOrderComparator
 */
package org.apereo.cas.authentication;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Generated;
import org.apereo.cas.authentication.AuthenticationServiceSelectionPlan;
import org.apereo.cas.authentication.AuthenticationServiceSelectionStrategy;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.util.spring.beans.BeanSupplier;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

public class DefaultAuthenticationServiceSelectionPlan
implements AuthenticationServiceSelectionPlan {
    private final List<AuthenticationServiceSelectionStrategy> strategies;

    public DefaultAuthenticationServiceSelectionPlan(AuthenticationServiceSelectionStrategy ... strategies) {
        this.strategies = Arrays.stream(strategies).collect(Collectors.toList());
        AnnotationAwareOrderComparator.sort(this.strategies);
    }

    public void registerStrategy(AuthenticationServiceSelectionStrategy strategy) {
        if (BeanSupplier.isNotProxy((Object)strategy)) {
            this.strategies.add(strategy);
            AnnotationAwareOrderComparator.sort(this.strategies);
        }
    }

    public Service resolveService(Service service) {
        Optional<AuthenticationServiceSelectionStrategy> strategy = this.strategies.stream().filter(s -> s.supports(service)).findFirst();
        if (strategy.isPresent()) {
            AuthenticationServiceSelectionStrategy result = strategy.get();
            return result.resolveServiceFrom(service);
        }
        return null;
    }

    public <T extends Service> T resolveService(Service service, Class<T> clazz) {
        Service result = this.resolveService(service);
        if (result == null) {
            return null;
        }
        if (!clazz.isAssignableFrom(result.getClass())) {
            throw new ClassCastException("Object [" + result + " is of type " + result.getClass() + " when we were expecting " + clazz);
        }
        return (T)result;
    }

    @Generated
    public DefaultAuthenticationServiceSelectionPlan(List<AuthenticationServiceSelectionStrategy> strategies) {
        this.strategies = strategies;
    }
}

