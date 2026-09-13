/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.authentication.exceptions;

import javax.security.auth.login.AccountException;
import lombok.Generated;

public class AccountDisabledException
extends AccountException {
    private static final long serialVersionUID = 7487835035108753209L;

    public AccountDisabledException(String msg) {
        super(msg);
    }

    @Generated
    public AccountDisabledException() {
    }
}

