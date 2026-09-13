/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.authentication.principal.Service
 */
package org.apereo.cas.ticket;

import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.ticket.AbstractTicketValidationException;

public class InvalidProxyGrantingTicketForServiceTicketException
extends AbstractTicketValidationException {
    private static final long serialVersionUID = 2120177571513373134L;
    private static final String CODE = "INVALID_PROXY_GRANTING_TICKET";

    public InvalidProxyGrantingTicketForServiceTicketException(Service service) {
        this(CODE, service);
    }

    public InvalidProxyGrantingTicketForServiceTicketException(String code, Service service) {
        super(code, service);
    }
}

