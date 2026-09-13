/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.services.RegisteredService
 */
package org.apereo.cas.authentication;

import java.util.Optional;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.MultifactorAuthenticationContextValidationResult;
import org.apereo.cas.services.RegisteredService;

@FunctionalInterface
public interface MultifactorAuthenticationContextValidator {
    public static final String BEAN_NAME = "authenticationContextValidator";

    public MultifactorAuthenticationContextValidationResult validate(Authentication var1, String var2, Optional<RegisteredService> var3);
}

