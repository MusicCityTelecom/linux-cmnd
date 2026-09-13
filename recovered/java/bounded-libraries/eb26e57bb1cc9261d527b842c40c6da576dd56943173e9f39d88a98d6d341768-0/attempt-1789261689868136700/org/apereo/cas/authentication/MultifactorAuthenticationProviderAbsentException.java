/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.AuthenticationException
 */
package org.apereo.cas.authentication;

import lombok.Generated;
import org.apereo.cas.authentication.AuthenticationException;

public class MultifactorAuthenticationProviderAbsentException
extends AuthenticationException {
    private static final long serialVersionUID = 5909155188008680032L;

    public MultifactorAuthenticationProviderAbsentException(String msg) {
        super(msg);
    }

    @Generated
    public MultifactorAuthenticationProviderAbsentException() {
    }
}

