/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.services.RegisteredService
 *  org.springframework.core.Ordered
 */
package org.apereo.cas.authentication;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.MultifactorAuthenticationProvider;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.services.RegisteredService;
import org.springframework.core.Ordered;

@FunctionalInterface
public interface MultifactorAuthenticationTrigger
extends Ordered {
    public Optional<MultifactorAuthenticationProvider> isActivated(Authentication var1, RegisteredService var2, HttpServletRequest var3, HttpServletResponse var4, Service var5);

    default public boolean supports(HttpServletRequest request, RegisteredService registeredService, Authentication authentication, Service service) {
        return true;
    }

    default public int getOrder() {
        return Integer.MAX_VALUE;
    }

    default public String getName() {
        return this.getClass().getSimpleName();
    }
}

