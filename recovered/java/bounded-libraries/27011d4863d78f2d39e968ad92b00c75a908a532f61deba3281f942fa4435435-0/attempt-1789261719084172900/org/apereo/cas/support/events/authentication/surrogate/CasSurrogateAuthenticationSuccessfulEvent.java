/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.principal.Principal
 */
package org.apereo.cas.support.events.authentication.surrogate;

import lombok.Generated;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.support.events.AbstractCasEvent;

public class CasSurrogateAuthenticationSuccessfulEvent
extends AbstractCasEvent {
    private static final long serialVersionUID = 8059647975948452375L;
    private final Principal principal;
    private final String surrogate;

    public CasSurrogateAuthenticationSuccessfulEvent(Object source, Principal principal, String surrogate) {
        super(source);
        this.principal = principal;
        this.surrogate = surrogate;
    }

    @Generated
    public Principal getPrincipal() {
        return this.principal;
    }

    @Generated
    public String getSurrogate() {
        return this.surrogate;
    }

    @Override
    @Generated
    public String toString() {
        return "CasSurrogateAuthenticationSuccessfulEvent(super=" + super.toString() + ", principal=" + this.principal + ", surrogate=" + this.surrogate + ")";
    }
}

