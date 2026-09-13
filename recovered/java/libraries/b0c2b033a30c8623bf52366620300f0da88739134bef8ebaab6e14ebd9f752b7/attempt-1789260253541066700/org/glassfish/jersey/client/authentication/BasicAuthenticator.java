/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client.authentication;

import java.util.Base64;
import java.util.Locale;
import java.util.logging.Logger;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientResponseContext;
import org.glassfish.jersey.client.authentication.AuthenticationUtil;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFilter;
import org.glassfish.jersey.client.authentication.ResponseAuthenticationException;
import org.glassfish.jersey.client.internal.LocalizationMessages;

final class BasicAuthenticator {
    private static final Logger LOGGER = Logger.getLogger(BasicAuthenticator.class.getName());
    private final HttpAuthenticationFilter.Credentials defaultCredentials;

    BasicAuthenticator(HttpAuthenticationFilter.Credentials defaultCredentials) {
        this.defaultCredentials = defaultCredentials;
    }

    private String calculateAuthentication(HttpAuthenticationFilter.Credentials credentials) {
        String username = credentials.getUsername();
        byte[] password = credentials.getPassword();
        if (username == null) {
            username = "";
        }
        if (password == null) {
            password = new byte[]{};
        }
        byte[] prefix = (username + ":").getBytes(HttpAuthenticationFilter.CHARACTER_SET);
        byte[] usernamePassword = new byte[prefix.length + password.length];
        System.arraycopy(prefix, 0, usernamePassword, 0, prefix.length);
        System.arraycopy(password, 0, usernamePassword, prefix.length, password.length);
        return "Basic " + Base64.getEncoder().encodeToString(usernamePassword);
    }

    public void filterRequest(ClientRequestContext request) {
        HttpAuthenticationFilter.Credentials credentials = HttpAuthenticationFilter.getCredentials(request, this.defaultCredentials, HttpAuthenticationFilter.Type.BASIC);
        if (credentials == null) {
            LOGGER.fine(LocalizationMessages.AUTHENTICATION_CREDENTIALS_NOT_PROVIDED_BASIC());
        } else {
            request.getHeaders().add("Authorization", this.calculateAuthentication(credentials));
        }
    }

    public boolean filterResponseAndAuthenticate(ClientRequestContext request, ClientResponseContext response) {
        String authenticate = response.getHeaders().getFirst("WWW-Authenticate");
        if (authenticate != null && authenticate.trim().toUpperCase(Locale.ROOT).startsWith("BASIC")) {
            HttpAuthenticationFilter.Credentials credentials = HttpAuthenticationFilter.getCredentials(request, this.defaultCredentials, HttpAuthenticationFilter.Type.BASIC);
            if (credentials == null) {
                if (response.hasEntity()) {
                    AuthenticationUtil.discardInputAndClose(response.getEntityStream());
                }
                throw new ResponseAuthenticationException(null, LocalizationMessages.AUTHENTICATION_CREDENTIALS_MISSING_BASIC());
            }
            return HttpAuthenticationFilter.repeatRequest(request, response, this.calculateAuthentication(credentials));
        }
        return false;
    }
}

