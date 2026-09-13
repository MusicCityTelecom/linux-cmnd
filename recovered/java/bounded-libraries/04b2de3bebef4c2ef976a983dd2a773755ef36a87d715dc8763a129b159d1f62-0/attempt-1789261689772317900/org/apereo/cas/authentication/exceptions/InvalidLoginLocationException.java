/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.authentication.exceptions;

import javax.security.auth.login.AccountException;
import lombok.Generated;

public class InvalidLoginLocationException
extends AccountException {
    private static final long serialVersionUID = 5745711263227480194L;

    public InvalidLoginLocationException(String message) {
        super(message);
    }

    @Generated
    public InvalidLoginLocationException() {
    }
}

