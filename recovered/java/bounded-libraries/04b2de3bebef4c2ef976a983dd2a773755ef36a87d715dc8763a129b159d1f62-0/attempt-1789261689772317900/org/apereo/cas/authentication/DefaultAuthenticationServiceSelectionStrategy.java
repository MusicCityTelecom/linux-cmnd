/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.AuthenticationServiceSelectionStrategy
 *  org.apereo.cas.authentication.principal.Service
 */
package org.apereo.cas.authentication;

import lombok.Generated;
import org.apereo.cas.authentication.AuthenticationServiceSelectionStrategy;
import org.apereo.cas.authentication.principal.Service;

public class DefaultAuthenticationServiceSelectionStrategy
implements AuthenticationServiceSelectionStrategy {
    private static final long serialVersionUID = -7458940344679793681L;
    private int order = Integer.MAX_VALUE;

    public Service resolveServiceFrom(Service service) {
        return service;
    }

    public boolean supports(Service service) {
        return true;
    }

    @Generated
    public void setOrder(int order) {
        this.order = order;
    }

    @Generated
    public int getOrder() {
        return this.order;
    }
}

