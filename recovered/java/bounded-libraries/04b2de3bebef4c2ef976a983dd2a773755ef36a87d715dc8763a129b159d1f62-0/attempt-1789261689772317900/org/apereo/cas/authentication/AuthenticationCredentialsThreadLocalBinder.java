/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.AuthenticationBuilder
 *  org.apereo.cas.authentication.Credential
 */
package org.apereo.cas.authentication;

import java.util.Arrays;
import java.util.Collection;
import lombok.Generated;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.AuthenticationBuilder;
import org.apereo.cas.authentication.Credential;

public class AuthenticationCredentialsThreadLocalBinder {
    private static final ThreadLocal<Authentication> CURRENT_AUTHENTICATION = new ThreadLocal();
    private static final ThreadLocal<Authentication> IN_PROGRESS_AUTHENTICATION = new ThreadLocal();
    private static final ThreadLocal<AuthenticationBuilder> CURRENT_AUTHENTICATION_BUILDER = new ThreadLocal();
    private static final ThreadLocal<String[]> CURRENT_CREDENTIAL_IDS = new ThreadLocal();

    public static void bindInProgress(Authentication authentication) {
        IN_PROGRESS_AUTHENTICATION.set(authentication);
    }

    public static void bindCurrent(Collection<Credential> credentials) {
        AuthenticationCredentialsThreadLocalBinder.bindCurrent((Credential[])credentials.toArray(Credential[]::new));
    }

    public static void bindCurrent(Credential ... credentials) {
        CURRENT_CREDENTIAL_IDS.set((String[])Arrays.stream(credentials).map(Credential::getId).toArray(String[]::new));
    }

    public static void bindCurrent(Authentication authentication) {
        CURRENT_AUTHENTICATION.set(authentication);
    }

    public static void bindCurrent(AuthenticationBuilder builder) {
        CURRENT_AUTHENTICATION_BUILDER.set(builder);
    }

    public static String[] getCurrentCredentialIds() {
        return CURRENT_CREDENTIAL_IDS.get();
    }

    public static String getCurrentCredentialIdsAsString() {
        return AuthenticationCredentialsThreadLocalBinder.getCurrentCredentialIds() != null ? String.join((CharSequence)", ", AuthenticationCredentialsThreadLocalBinder.getCurrentCredentialIds()) : null;
    }

    public static AuthenticationBuilder getCurrentAuthenticationBuilder() {
        return CURRENT_AUTHENTICATION_BUILDER.get();
    }

    public static Authentication getCurrentAuthentication() {
        return CURRENT_AUTHENTICATION.get();
    }

    public static Authentication getInProgressAuthentication() {
        return IN_PROGRESS_AUTHENTICATION.get();
    }

    public static void clearInProgressAuthentication() {
        IN_PROGRESS_AUTHENTICATION.remove();
    }

    public static void clear() {
        CURRENT_CREDENTIAL_IDS.remove();
        CURRENT_AUTHENTICATION.remove();
        CURRENT_AUTHENTICATION_BUILDER.remove();
        AuthenticationCredentialsThreadLocalBinder.clearInProgressAuthentication();
    }

    @Generated
    public AuthenticationCredentialsThreadLocalBinder() {
    }
}

