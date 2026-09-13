/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.authentication.exceptions;

import javax.security.auth.login.AccountException;
import lombok.Generated;

public class InvalidLoginTimeException
extends AccountException {
    private static final long serialVersionUID = -6699752791525619208L;

    public InvalidLoginTimeException(String message) {
        super(message);
    }

    @Generated
    public InvalidLoginTimeException() {
    }
}

