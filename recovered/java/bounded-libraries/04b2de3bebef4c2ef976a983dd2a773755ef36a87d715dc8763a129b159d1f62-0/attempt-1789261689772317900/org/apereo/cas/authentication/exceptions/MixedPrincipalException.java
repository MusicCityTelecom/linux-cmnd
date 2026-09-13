/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.PrincipalException
 *  org.apereo.cas.authentication.principal.Principal
 */
package org.apereo.cas.authentication.exceptions;

import lombok.Generated;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.PrincipalException;
import org.apereo.cas.authentication.principal.Principal;

public class MixedPrincipalException
extends PrincipalException {
    private static final long serialVersionUID = -9040132618070273997L;
    private final Principal first;
    private final Principal second;

    public MixedPrincipalException(Authentication authentication, Principal a, Principal b) {
        super(a + " != " + b, authentication.getFailures(), authentication.getSuccesses());
        this.first = a;
        this.second = b;
    }

    @Generated
    public Principal getFirst() {
        return this.first;
    }

    @Generated
    public Principal getSecond() {
        return this.second;
    }
}

