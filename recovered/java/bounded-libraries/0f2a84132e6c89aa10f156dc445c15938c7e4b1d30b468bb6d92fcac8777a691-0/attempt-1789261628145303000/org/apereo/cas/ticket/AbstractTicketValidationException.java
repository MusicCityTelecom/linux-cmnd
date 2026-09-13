/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.ticket.AbstractTicketException
 */
package org.apereo.cas.ticket;

import java.util.List;
import lombok.Generated;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.ticket.AbstractTicketException;

public abstract class AbstractTicketValidationException
extends AbstractTicketException {
    protected static final String CODE = "INVALID_TICKET";
    private static final long serialVersionUID = 3257004341537093175L;
    private final Service service;

    protected AbstractTicketValidationException(Service service) {
        this(CODE, service);
    }

    protected AbstractTicketValidationException(String code, Service service) {
        super(code);
        this.service = service;
    }

    protected AbstractTicketValidationException(String code, String msg, List<Object> args, Service service) {
        super(code, msg, args);
        this.service = service;
    }

    protected AbstractTicketValidationException(String code, Throwable throwable, List<Object> args, Service service) {
        super(code, throwable, args);
        this.service = service;
    }

    @Generated
    public Service getService() {
        return this.service;
    }
}

