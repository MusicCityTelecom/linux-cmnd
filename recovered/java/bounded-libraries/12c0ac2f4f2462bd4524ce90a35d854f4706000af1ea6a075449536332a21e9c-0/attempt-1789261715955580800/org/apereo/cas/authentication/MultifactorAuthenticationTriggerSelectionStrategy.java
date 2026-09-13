/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.services.RegisteredService
 */
package org.apereo.cas.authentication;

import java.util.Collection;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.MultifactorAuthenticationProvider;
import org.apereo.cas.authentication.MultifactorAuthenticationTrigger;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.services.RegisteredService;

public interface MultifactorAuthenticationTriggerSelectionStrategy {
    public static final String BEAN_NAME = "defaultMultifactorTriggerSelectionStrategy";

    public Optional<MultifactorAuthenticationProvider> resolve(HttpServletRequest var1, HttpServletResponse var2, RegisteredService var3, Authentication var4, Service var5);

    public Collection<MultifactorAuthenticationTrigger> getMultifactorAuthenticationTriggers();
}

