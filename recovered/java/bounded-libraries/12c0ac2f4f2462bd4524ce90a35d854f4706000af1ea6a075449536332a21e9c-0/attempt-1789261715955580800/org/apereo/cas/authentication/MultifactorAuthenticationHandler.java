/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.authentication.AuthenticationHandler
 *  org.springframework.beans.factory.ObjectProvider
 */
package org.apereo.cas.authentication;

import org.apereo.cas.authentication.AuthenticationHandler;
import org.apereo.cas.authentication.MultifactorAuthenticationProvider;
import org.springframework.beans.factory.ObjectProvider;

public interface MultifactorAuthenticationHandler
extends AuthenticationHandler {
    public ObjectProvider<? extends MultifactorAuthenticationProvider> getMultifactorAuthenticationProvider();
}

