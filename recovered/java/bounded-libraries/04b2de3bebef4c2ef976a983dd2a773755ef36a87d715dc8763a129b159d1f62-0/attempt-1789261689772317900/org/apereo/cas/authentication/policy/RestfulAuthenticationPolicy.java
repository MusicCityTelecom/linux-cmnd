/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  lombok.Generated
 *  org.apache.http.HttpResponse
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.AuthenticationHandler
 *  org.apereo.cas.authentication.AuthenticationPolicyExecutionResult
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.configuration.model.core.authentication.RestAuthenticationPolicyProperties
 *  org.apereo.cas.util.CollectionUtils
 *  org.apereo.cas.util.HttpUtils
 *  org.apereo.cas.util.HttpUtils$HttpExecutionRequest
 *  org.apereo.cas.util.serialization.JacksonObjectMapperFactory
 *  org.springframework.context.ConfigurableApplicationContext
 *  org.springframework.http.HttpMethod
 *  org.springframework.http.HttpStatus
 */
package org.apereo.cas.authentication.policy;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.util.Optional;
import java.util.Set;
import javax.security.auth.login.AccountExpiredException;
import javax.security.auth.login.AccountLockedException;
import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.FailedLoginException;
import lombok.Generated;
import org.apache.http.HttpResponse;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.AuthenticationHandler;
import org.apereo.cas.authentication.AuthenticationPolicyExecutionResult;
import org.apereo.cas.authentication.exceptions.AccountDisabledException;
import org.apereo.cas.authentication.exceptions.AccountPasswordMustChangeException;
import org.apereo.cas.authentication.policy.BaseAuthenticationPolicy;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.configuration.model.core.authentication.RestAuthenticationPolicyProperties;
import org.apereo.cas.util.CollectionUtils;
import org.apereo.cas.util.HttpUtils;
import org.apereo.cas.util.serialization.JacksonObjectMapperFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public class RestfulAuthenticationPolicy
extends BaseAuthenticationPolicy {
    private static final long serialVersionUID = -7688729533538097898L;
    private static final ObjectMapper MAPPER = JacksonObjectMapperFactory.builder().defaultTypingEnabled(false).build().toObjectMapper();
    private final RestAuthenticationPolicyProperties properties;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public AuthenticationPolicyExecutionResult isSatisfiedBy(Authentication authentication, Set<AuthenticationHandler> authenticationHandlers, ConfigurableApplicationContext applicationContext, Optional<Serializable> assertion) throws Exception {
        AuthenticationPolicyExecutionResult authenticationPolicyExecutionResult;
        HttpResponse response = null;
        Principal principal = authentication.getPrincipal();
        try {
            String entity = MAPPER.writeValueAsString((Object)principal);
            HttpUtils.HttpExecutionRequest exec = HttpUtils.HttpExecutionRequest.builder().url(this.properties.getUrl()).basicAuthPassword(this.properties.getBasicAuthUsername()).basicAuthUsername(this.properties.getBasicAuthPassword()).method(HttpMethod.POST).entity(entity).headers(CollectionUtils.wrap((String)"Content-Type", (Object)"application/json")).build();
            response = HttpUtils.execute((HttpUtils.HttpExecutionRequest)exec);
            HttpStatus statusCode = HttpStatus.valueOf((int)response.getStatusLine().getStatusCode());
            if (statusCode != HttpStatus.OK) {
                Exception ex = RestfulAuthenticationPolicy.handleResponseStatusCode(statusCode, principal);
                throw new GeneralSecurityException(ex);
            }
            authenticationPolicyExecutionResult = AuthenticationPolicyExecutionResult.success();
        }
        catch (Throwable throwable) {
            HttpUtils.close(response);
            throw throwable;
        }
        HttpUtils.close((HttpResponse)response);
        return authenticationPolicyExecutionResult;
    }

    private static Exception handleResponseStatusCode(HttpStatus statusCode, Principal p) {
        if (statusCode == HttpStatus.FORBIDDEN || statusCode == HttpStatus.METHOD_NOT_ALLOWED) {
            return new AccountDisabledException("Could not authenticate forbidden account for " + p.getId());
        }
        if (statusCode == HttpStatus.UNAUTHORIZED) {
            return new FailedLoginException("Could not authenticate account for " + p.getId());
        }
        if (statusCode == HttpStatus.NOT_FOUND) {
            return new AccountNotFoundException("Could not locate account for " + p.getId());
        }
        if (statusCode == HttpStatus.LOCKED) {
            return new AccountLockedException("Could not authenticate locked account for " + p.getId());
        }
        if (statusCode == HttpStatus.PRECONDITION_FAILED) {
            return new AccountExpiredException("Could not authenticate expired account for " + p.getId());
        }
        if (statusCode == HttpStatus.PRECONDITION_REQUIRED) {
            return new AccountPasswordMustChangeException("Account password must change for " + p.getId());
        }
        return new FailedLoginException("Rest endpoint returned an unknown status code " + statusCode);
    }

    @Generated
    public RestfulAuthenticationPolicy() {
        this.properties = null;
    }

    @Override
    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RestfulAuthenticationPolicy)) {
            return false;
        }
        RestfulAuthenticationPolicy other = (RestfulAuthenticationPolicy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof RestfulAuthenticationPolicy;
    }

    @Override
    @Generated
    public int hashCode() {
        int result = super.hashCode();
        return result;
    }

    @Generated
    public RestAuthenticationPolicyProperties getProperties() {
        return this.properties;
    }

    @Generated
    public RestfulAuthenticationPolicy(RestAuthenticationPolicyProperties properties) {
        this.properties = properties;
    }
}

