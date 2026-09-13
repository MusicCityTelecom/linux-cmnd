/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.PrincipalException
 */
package org.apereo.cas.authentication.exceptions;

import java.util.HashMap;
import java.util.Map;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.PrincipalException;

public class UnresolvedPrincipalException
extends PrincipalException {
    private static final long serialVersionUID = 380456166081802820L;
    private static final String UNRESOLVED_PRINCIPAL = "No resolver produced a principal.";

    public UnresolvedPrincipalException(Authentication authentication) {
        super(UNRESOLVED_PRINCIPAL, authentication.getFailures(), authentication.getSuccesses());
    }

    public UnresolvedPrincipalException() {
        super(UNRESOLVED_PRINCIPAL, new HashMap(0), new HashMap(0));
    }

    public UnresolvedPrincipalException(Exception e) {
        super(e.getMessage(), new HashMap(0), new HashMap(0));
    }

    public UnresolvedPrincipalException(Map<String, Throwable> handlerErrors) {
        super(UNRESOLVED_PRINCIPAL, handlerErrors, new HashMap(0));
    }

    public UnresolvedPrincipalException(Authentication authentication, Exception cause) {
        super(cause.getMessage(), authentication.getFailures(), authentication.getSuccesses());
    }
}

