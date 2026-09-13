/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.authentication.RootCasException
 */
package org.apereo.cas.ticket;

import java.util.List;
import org.apereo.cas.authentication.RootCasException;

public abstract class AbstractTicketException
extends RootCasException {
    private static final long serialVersionUID = -5128676415951733624L;

    protected AbstractTicketException(String code, Throwable throwable) {
        super(code, throwable);
    }

    protected AbstractTicketException(String code) {
        super(code);
    }

    protected AbstractTicketException(String code, String msg, List<Object> args) {
        super(code, msg, args);
    }

    protected AbstractTicketException(String code, Throwable throwable, List<Object> args) {
        super(code, throwable, args);
    }
}

