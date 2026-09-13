/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.principal.Principal
 */
package org.apereo.cas.support.events.authentication;

import lombok.Generated;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.support.events.AbstractCasEvent;

public class CasAuthenticationPrincipalResolvedEvent
extends AbstractCasEvent {
    private static final long serialVersionUID = -1862937393594313844L;
    private final Principal principal;

    public CasAuthenticationPrincipalResolvedEvent(Object source, Principal p) {
        super(source);
        this.principal = p;
    }

    @Override
    @Generated
    public String toString() {
        return "CasAuthenticationPrincipalResolvedEvent(super=" + super.toString() + ", principal=" + this.principal + ")";
    }

    @Generated
    public Principal getPrincipal() {
        return this.principal;
    }
}

