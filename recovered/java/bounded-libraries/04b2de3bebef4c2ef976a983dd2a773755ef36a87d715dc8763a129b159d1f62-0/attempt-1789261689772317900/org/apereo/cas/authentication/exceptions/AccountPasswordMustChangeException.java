/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.authentication.exceptions;

import javax.security.auth.login.CredentialExpiredException;
import lombok.Generated;

public class AccountPasswordMustChangeException
extends CredentialExpiredException {
    private static final long serialVersionUID = 7487835035108753209L;

    public AccountPasswordMustChangeException(String msg) {
        super(msg);
    }

    @Generated
    public AccountPasswordMustChangeException() {
    }
}

