/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.authentication.principal.Service
 */
package org.apereo.cas.ticket;

import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.ticket.AbstractTicketValidationException;

public class UnsatisfiedAuthenticationContextTicketValidationException
extends AbstractTicketValidationException {
    protected static final String CODE = "INVALID_AUTHENTICATION_CONTEXT";
    private static final long serialVersionUID = -8076771862820008358L;

    public UnsatisfiedAuthenticationContextTicketValidationException(Service service) {
        super(CODE, service);
    }
}

