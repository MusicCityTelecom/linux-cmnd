/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client.authentication;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Priority;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.authentication.AuthenticationUtil;
import org.glassfish.jersey.client.authentication.BasicAuthenticator;
import org.glassfish.jersey.client.authentication.DigestAuthenticator;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.client.authentication.RequestAuthenticationException;
import org.glassfish.jersey.client.internal.LocalizationMessages;

@Priority(value=1000)
class HttpAuthenticationFilter
implements ClientRequestFilter,
ClientResponseFilter {
    private static final String REQUEST_PROPERTY_FILTER_REUSED = "org.glassfish.jersey.client.authentication.HttpAuthenticationFilter.reused";
    private static final String REQUEST_PROPERTY_OPERATION = "org.glassfish.jersey.client.authentication.HttpAuthenticationFilter.operation";
    static final Charset CHARACTER_SET = Charset.forName("iso-8859-1");
    private final HttpAuthenticationFeature.Mode mode;
    private final Map<String, Type> uriCache;
    private final DigestAuthenticator digestAuth;
    private final BasicAuthenticator basicAuth;
    private static final int MAXIMUM_DIGEST_CACHE_SIZE = 10000;

    HttpAuthenticationFilter(HttpAuthenticationFeature.Mode mode, Credentials basicCredentials, Credentials digestCredentials, Configuration configuration) {
        int limit = this.getMaximumCacheLimit(configuration);
        final int uriCacheLimit = limit * 2;
        this.uriCache = Collections.synchronizedMap(new LinkedHashMap<String, Type>(uriCacheLimit){
            private static final long serialVersionUID = 1946245645415625L;

            @Override
            protected boolean removeEldestEntry(Map.Entry<String, Type> eldest) {
                return this.size() > uriCacheLimit;
            }
        });
        this.mode = mode;
        switch (mode) {
            case BASIC_PREEMPTIVE: 
            case BASIC_NON_PREEMPTIVE: {
                this.basicAuth = new BasicAuthenticator(basicCredentials);
                this.digestAuth = null;
                break;
            }
            case DIGEST: {
                this.basicAuth = null;
                this.digestAuth = new DigestAuthenticator(digestCredentials, limit);
                break;
            }
            case UNIVERSAL: {
                this.basicAuth = new BasicAuthenticator(basicCredentials);
                this.digestAuth = new DigestAuthenticator(digestCredentials, limit);
                break;
            }
            default: {
                throw new IllegalStateException("Not implemented.");
            }
        }
    }

    private int getMaximumCacheLimit(Configuration configuration) {
        int limit = ClientProperties.getValue(configuration.getProperties(), "jersey.config.client.digestAuthUriCacheSizeLimit", 10000);
        if (limit < 1) {
            limit = 10000;
        }
        return limit;
    }

    @Override
    public void filter(ClientRequestContext request) throws IOException {
        if ("true".equals(request.getProperty(REQUEST_PROPERTY_FILTER_REUSED))) {
            return;
        }
        if (request.getHeaders().containsKey("Authorization")) {
            return;
        }
        Type operation = null;
        if (this.mode == HttpAuthenticationFeature.Mode.BASIC_PREEMPTIVE) {
            this.basicAuth.filterRequest(request);
            operation = Type.BASIC;
        } else if (this.mode != HttpAuthenticationFeature.Mode.BASIC_NON_PREEMPTIVE) {
            Type lastSuccessfulMethod;
            if (this.mode == HttpAuthenticationFeature.Mode.DIGEST) {
                if (this.digestAuth.filterRequest(request)) {
                    operation = Type.DIGEST;
                }
            } else if (this.mode == HttpAuthenticationFeature.Mode.UNIVERSAL && (lastSuccessfulMethod = this.uriCache.get(this.getCacheKey(request))) != null) {
                request.setProperty(REQUEST_PROPERTY_OPERATION, (Object)lastSuccessfulMethod);
                if (lastSuccessfulMethod == Type.BASIC) {
                    this.basicAuth.filterRequest(request);
                    operation = Type.BASIC;
                } else if (lastSuccessfulMethod == Type.DIGEST && this.digestAuth.filterRequest(request)) {
                    operation = Type.DIGEST;
                }
            }
        }
        if (operation != null) {
            request.setProperty(REQUEST_PROPERTY_OPERATION, (Object)operation);
        }
    }

    @Override
    public void filter(ClientRequestContext request, ClientResponseContext response) throws IOException {
        boolean authenticate;
        if ("true".equals(request.getProperty(REQUEST_PROPERTY_FILTER_REUSED))) {
            return;
        }
        Type result = null;
        if (response.getStatus() == Response.Status.UNAUTHORIZED.getStatusCode()) {
            List authStrings = (List)response.getHeaders().get("WWW-Authenticate");
            if (authStrings != null) {
                for (String authString : authStrings) {
                    String upperCaseAuth = authString.trim().toUpperCase(Locale.ROOT);
                    if (result == null && upperCaseAuth.startsWith("BASIC")) {
                        result = Type.BASIC;
                        continue;
                    }
                    if (!upperCaseAuth.startsWith("DIGEST")) continue;
                    result = Type.DIGEST;
                }
                if (result == null) {
                    return;
                }
            }
            authenticate = true;
        } else {
            authenticate = false;
        }
        if (this.mode != HttpAuthenticationFeature.Mode.BASIC_PREEMPTIVE) {
            if (this.mode == HttpAuthenticationFeature.Mode.BASIC_NON_PREEMPTIVE) {
                if (authenticate && result == Type.BASIC) {
                    this.basicAuth.filterResponseAndAuthenticate(request, response);
                }
            } else if (this.mode == HttpAuthenticationFeature.Mode.DIGEST) {
                if (authenticate && result == Type.DIGEST) {
                    this.digestAuth.filterResponse(request, response);
                }
            } else if (this.mode == HttpAuthenticationFeature.Mode.UNIVERSAL) {
                Type operation = (Type)((Object)request.getProperty(REQUEST_PROPERTY_OPERATION));
                if (operation != null) {
                    this.updateCache(request, !authenticate, operation);
                }
                if (authenticate) {
                    boolean success = false;
                    if (result == Type.BASIC) {
                        success = this.basicAuth.filterResponseAndAuthenticate(request, response);
                    } else if (result == Type.DIGEST) {
                        success = this.digestAuth.filterResponse(request, response);
                    }
                    this.updateCache(request, success, result);
                }
            }
        }
    }

    private String getCacheKey(ClientRequestContext request) {
        return AuthenticationUtil.getCacheKey(request).toString() + ":" + request.getMethod();
    }

    private void updateCache(ClientRequestContext request, boolean success, Type operation) {
        String cacheKey = this.getCacheKey(request);
        if (success) {
            this.uriCache.put(cacheKey, operation);
        } else {
            this.uriCache.remove(cacheKey);
        }
    }

    static boolean repeatRequest(ClientRequestContext request, ClientResponseContext response, String newAuthorizationHeader) {
        if (response.hasEntity()) {
            AuthenticationUtil.discardInputAndClose(response.getEntityStream());
            response.setEntityStream(null);
        }
        Client client = request.getClient();
        String method = request.getMethod();
        MediaType mediaType = request.getMediaType();
        URI lUri = request.getUri();
        WebTarget resourceTarget = client.target(lUri);
        Invocation.Builder builder = resourceTarget.request(mediaType);
        MultivaluedHashMap<String, Object> newHeaders = new MultivaluedHashMap<String, Object>();
        for (Map.Entry entry : request.getHeaders().entrySet()) {
            if ("Authorization".equals(entry.getKey())) continue;
            newHeaders.put((String)entry.getKey(), entry.getValue());
        }
        newHeaders.add("Authorization", newAuthorizationHeader);
        builder.headers(newHeaders);
        builder.property(REQUEST_PROPERTY_FILTER_REUSED, "true");
        for (String propertyName : request.getPropertyNames()) {
            Object propertyValue = request.getProperty(propertyName);
            builder.property(propertyName, propertyValue);
        }
        Invocation invocation = request.getEntity() == null ? builder.build(method) : builder.build(method, Entity.entity(request.getEntity(), request.getMediaType()));
        Response nextResponse = invocation.invoke();
        if (nextResponse.hasEntity()) {
            response.setEntityStream(nextResponse.readEntity(InputStream.class));
        }
        MultivaluedMap<String, String> headers = response.getHeaders();
        headers.clear();
        headers.putAll(nextResponse.getStringHeaders());
        response.setStatus(nextResponse.getStatus());
        return response.getStatus() != Response.Status.UNAUTHORIZED.getStatusCode();
    }

    private static Credentials extractCredentials(ClientRequestContext request, Type type) {
        String usernameKey = null;
        String passwordKey = null;
        if (type == null) {
            usernameKey = "jersey.config.client.http.auth.username";
            passwordKey = "jersey.config.client.http.auth.password";
        } else if (type == Type.BASIC) {
            usernameKey = "jersey.config.client.http.auth.basic.username";
            passwordKey = "jersey.config.client.http.auth.basic.password";
        } else if (type == Type.DIGEST) {
            usernameKey = "jersey.config.client.http.auth.digest.username";
            passwordKey = "jersey.config.client.http.auth.digest.password";
        }
        String userName = (String)request.getProperty(usernameKey);
        if (userName != null && !userName.equals("")) {
            byte[] pwdBytes;
            Object password = request.getProperty(passwordKey);
            if (password instanceof byte[]) {
                pwdBytes = (byte[])password;
            } else if (password instanceof String) {
                pwdBytes = ((String)password).getBytes(CHARACTER_SET);
            } else {
                throw new RequestAuthenticationException(LocalizationMessages.AUTHENTICATION_CREDENTIALS_REQUEST_PASSWORD_UNSUPPORTED());
            }
            return new Credentials(userName, pwdBytes);
        }
        return null;
    }

    static Credentials getCredentials(ClientRequestContext request, Credentials defaultCredentials, Type type) {
        Credentials commonCredentials = HttpAuthenticationFilter.extractCredentials(request, type);
        if (commonCredentials != null) {
            return commonCredentials;
        }
        Credentials specificCredentials = HttpAuthenticationFilter.extractCredentials(request, null);
        return specificCredentials != null ? specificCredentials : defaultCredentials;
    }

    static class Credentials {
        private final String username;
        private final byte[] password;

        Credentials(String username, byte[] password) {
            this.username = username;
            this.password = password;
        }

        Credentials(String username, String password) {
            this.username = username;
            this.password = password == null ? null : password.getBytes(CHARACTER_SET);
        }

        String getUsername() {
            return this.username;
        }

        byte[] getPassword() {
            return this.password;
        }
    }

    static enum Type {
        BASIC,
        DIGEST;

    }
}

