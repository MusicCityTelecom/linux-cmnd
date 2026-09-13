/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.RandomUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.authentication.AuthenticationHandler
 *  org.apereo.cas.authentication.Credential
 *  org.apereo.cas.authentication.principal.PrincipalFactory
 *  org.apereo.cas.configuration.model.core.authentication.AuthenticationHandlerStates
 *  org.apereo.cas.services.ServicesManager
 */
package org.apereo.cas.authentication;

import java.util.Objects;
import java.util.function.Predicate;
import lombok.Generated;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.AuthenticationHandler;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.principal.DefaultPrincipalFactory;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.configuration.model.core.authentication.AuthenticationHandlerStates;
import org.apereo.cas.services.ServicesManager;

public abstract class AbstractAuthenticationHandler
implements AuthenticationHandler {
    protected final PrincipalFactory principalFactory;
    private final ServicesManager servicesManager;
    private Predicate<Credential> credentialSelectionPredicate = credential -> true;
    private final String name;
    private final int order;
    private AuthenticationHandlerStates state = AuthenticationHandlerStates.ACTIVE;

    protected AbstractAuthenticationHandler(String name, ServicesManager servicesManager, PrincipalFactory principalFactory, Integer order) {
        this.name = StringUtils.isNotBlank((CharSequence)name) ? name : this.getClass().getSimpleName();
        this.servicesManager = servicesManager;
        this.principalFactory = Objects.requireNonNullElseGet(principalFactory, DefaultPrincipalFactory::new);
        this.order = Objects.requireNonNullElseGet(order, () -> RandomUtils.nextInt((int)1, (int)Integer.MAX_VALUE));
    }

    @Generated
    public PrincipalFactory getPrincipalFactory() {
        return this.principalFactory;
    }

    @Generated
    public ServicesManager getServicesManager() {
        return this.servicesManager;
    }

    @Generated
    public Predicate<Credential> getCredentialSelectionPredicate() {
        return this.credentialSelectionPredicate;
    }

    @Generated
    public String getName() {
        return this.name;
    }

    @Generated
    public int getOrder() {
        return this.order;
    }

    @Generated
    public AuthenticationHandlerStates getState() {
        return this.state;
    }

    @Generated
    public void setCredentialSelectionPredicate(Predicate<Credential> credentialSelectionPredicate) {
        this.credentialSelectionPredicate = credentialSelectionPredicate;
    }

    @Generated
    public void setState(AuthenticationHandlerStates state) {
        this.state = state;
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AbstractAuthenticationHandler)) {
            return false;
        }
        AbstractAuthenticationHandler other = (AbstractAuthenticationHandler)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.order != other.order) {
            return false;
        }
        String this$name = this.name;
        String other$name = other.name;
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
            return false;
        }
        AuthenticationHandlerStates this$state = this.state;
        AuthenticationHandlerStates other$state = other.state;
        return !(this$state == null ? other$state != null : !this$state.equals(other$state));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof AbstractAuthenticationHandler;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.order;
        String $name = this.name;
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        AuthenticationHandlerStates $state = this.state;
        result = result * 59 + ($state == null ? 43 : $state.hashCode());
        return result;
    }
}

