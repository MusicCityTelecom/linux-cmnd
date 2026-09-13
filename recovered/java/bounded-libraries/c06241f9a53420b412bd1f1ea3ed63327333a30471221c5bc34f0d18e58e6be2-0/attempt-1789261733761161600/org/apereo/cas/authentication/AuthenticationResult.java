/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.authentication;

import java.io.Serializable;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.principal.Service;

public interface AuthenticationResult
extends Serializable {
    public Authentication getAuthentication();

    public Service getService();

    public boolean isCredentialProvided();
}

