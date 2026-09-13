/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.oidc;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-oidc")
@JsonFilter(value="OidcDiscoveryProperties")
public class OidcDiscoveryProperties
implements Serializable {
    private static final long serialVersionUID = 813028615694269276L;
    private boolean claimsParameterSupported = true;
    private boolean requestParameterSupported = true;
    private boolean requestUriParameterSupported = true;
    private boolean authorizationResponseIssuerParameterSupported;
    private boolean requirePushedAuthorizationRequests;
    private List<String> scopes = Stream.of("openid", "profile", "email", "address", "phone", "offline_access", "client_configuration_scope", "uma_authorization", "uma_protection", "client_registration_scope").collect(Collectors.toList());
    private List<String> claims = Stream.of("sub", "acr", "name", "preferred_username", "family_name", "given_name", "middle_name", "given_name", "profile", "picture", "nickname", "website", "zoneinfo", "locale", "updated_at", "birthdate", "email", "email_verified", "phone_number", "phone_number_verified", "address", "gender").collect(Collectors.toList());
    private List<String> subjectTypes = Stream.of("public", "pairwise").collect(Collectors.toList());
    private List<String> responseTypesSupported = Stream.of("code", "token", "id_token", "id_token token", "device_code").collect(Collectors.toList());
    private List<String> responseModesSupported = Stream.of("query", "fragment", "form_post").collect(Collectors.toList());
    private List<String> promptValuesSupported = Stream.of("none", "login", "consent").collect(Collectors.toList());
    private List<String> introspectionSupportedAuthenticationMethods = Stream.of("client_secret_basic").collect(Collectors.toList());
    private List<String> claimTypesSupported = Stream.of("normal").collect(Collectors.toList());
    private List<String> grantTypesSupported = Stream.of("authorization_code", "password", "client_credentials", "refresh_token", "urn:ietf:params:oauth:grant-type:uma-ticket").collect(Collectors.toList());
    private List<String> dpopSigningAlgValuesSupported = Stream.of("RS256", "RS384", "RS512", "ES256", "ES384", "ES512").collect(Collectors.toList());
    private List<String> idTokenSigningAlgValuesSupported = Stream.of("none", "RS256", "RS384", "RS512", "PS256", "PS384", "PS512", "ES256", "ES384", "ES512", "HS256", "HS384", "HS512").collect(Collectors.toList());
    private List<String> idTokenEncryptionAlgValuesSupported = Stream.of("RSA1_5", "RSA-OAEP", "RSA-OAEP-256", "A128KW", "A192KW", "A256KW", "A128GCMKW", "A192GCMKW", "A256GCMKW", "ECDH-ES", "ECDH-ES+A128KW", "ECDH-ES+A192KW", "ECDH-ES+A256KW").collect(Collectors.toList());
    private List<String> idTokenEncryptionEncodingValuesSupported = Stream.of("A128CBC-HS256", "A192CBC-HS384", "A256CBC-HS512", "A128GCM", "A192GCM", "A256GCM").collect(Collectors.toList());
    private List<String> userInfoSigningAlgValuesSupported = Stream.of("none", "RS256", "RS384", "RS512", "PS256", "PS384", "PS512", "ES256", "ES384", "ES512", "HS256", "HS384", "HS512").collect(Collectors.toList());
    private List<String> userInfoEncryptionAlgValuesSupported = Stream.of("RSA1_5", "RSA-OAEP", "RSA-OAEP-256", "A128KW", "A192KW", "A256KW", "A128GCMKW", "A192GCMKW", "A256GCMKW", "ECDH-ES", "ECDH-ES+A128KW", "ECDH-ES+A192KW", "ECDH-ES+A256KW").collect(Collectors.toList());
    private List<String> userInfoEncryptionEncodingValuesSupported = Stream.of("A128CBC-HS256", "A192CBC-HS384", "A256CBC-HS512", "A128GCM", "A192GCM", "A256GCM").collect(Collectors.toList());
    private List<String> tokenEndpointAuthMethodsSupported = Stream.of("client_secret_basic", "client_secret_post", "client_secret_jwt", "private_key_jwt").collect(Collectors.toList());
    private List<String> codeChallengeMethodsSupported = Stream.of("plain", "S256").collect(Collectors.toList());
    private List<String> acrValuesSupported = new ArrayList<String>();
    private List<String> requestObjectSigningAlgValuesSupported = Stream.of("none", "RS256", "RS384", "RS512", "PS256", "PS384", "PS512", "ES256", "ES384", "ES512", "HS256", "HS384", "HS512").collect(Collectors.toList());
    private List<String> requestObjectEncryptionAlgValuesSupported = Stream.of("RSA1_5", "RSA-OAEP", "RSA-OAEP-256", "A128KW", "A192KW", "A256KW", "A128GCMKW", "A192GCMKW", "A256GCMKW", "ECDH-ES", "ECDH-ES+A128KW", "ECDH-ES+A192KW", "ECDH-ES+A256KW").collect(Collectors.toList());
    private List<String> requestObjectEncryptionEncodingValuesSupported = Stream.of("A128CBC-HS256", "A192CBC-HS384", "A256CBC-HS512", "A128GCM", "A192GCM", "A256GCM").collect(Collectors.toList());

    @Generated
    public boolean isClaimsParameterSupported() {
        return this.claimsParameterSupported;
    }

    @Generated
    public boolean isRequestParameterSupported() {
        return this.requestParameterSupported;
    }

    @Generated
    public boolean isRequestUriParameterSupported() {
        return this.requestUriParameterSupported;
    }

    @Generated
    public boolean isAuthorizationResponseIssuerParameterSupported() {
        return this.authorizationResponseIssuerParameterSupported;
    }

    @Generated
    public boolean isRequirePushedAuthorizationRequests() {
        return this.requirePushedAuthorizationRequests;
    }

    @Generated
    public List<String> getScopes() {
        return this.scopes;
    }

    @Generated
    public List<String> getClaims() {
        return this.claims;
    }

    @Generated
    public List<String> getSubjectTypes() {
        return this.subjectTypes;
    }

    @Generated
    public List<String> getResponseTypesSupported() {
        return this.responseTypesSupported;
    }

    @Generated
    public List<String> getResponseModesSupported() {
        return this.responseModesSupported;
    }

    @Generated
    public List<String> getPromptValuesSupported() {
        return this.promptValuesSupported;
    }

    @Generated
    public List<String> getIntrospectionSupportedAuthenticationMethods() {
        return this.introspectionSupportedAuthenticationMethods;
    }

    @Generated
    public List<String> getClaimTypesSupported() {
        return this.claimTypesSupported;
    }

    @Generated
    public List<String> getGrantTypesSupported() {
        return this.grantTypesSupported;
    }

    @Generated
    public List<String> getDpopSigningAlgValuesSupported() {
        return this.dpopSigningAlgValuesSupported;
    }

    @Generated
    public List<String> getIdTokenSigningAlgValuesSupported() {
        return this.idTokenSigningAlgValuesSupported;
    }

    @Generated
    public List<String> getIdTokenEncryptionAlgValuesSupported() {
        return this.idTokenEncryptionAlgValuesSupported;
    }

    @Generated
    public List<String> getIdTokenEncryptionEncodingValuesSupported() {
        return this.idTokenEncryptionEncodingValuesSupported;
    }

    @Generated
    public List<String> getUserInfoSigningAlgValuesSupported() {
        return this.userInfoSigningAlgValuesSupported;
    }

    @Generated
    public List<String> getUserInfoEncryptionAlgValuesSupported() {
        return this.userInfoEncryptionAlgValuesSupported;
    }

    @Generated
    public List<String> getUserInfoEncryptionEncodingValuesSupported() {
        return this.userInfoEncryptionEncodingValuesSupported;
    }

    @Generated
    public List<String> getTokenEndpointAuthMethodsSupported() {
        return this.tokenEndpointAuthMethodsSupported;
    }

    @Generated
    public List<String> getCodeChallengeMethodsSupported() {
        return this.codeChallengeMethodsSupported;
    }

    @Generated
    public List<String> getAcrValuesSupported() {
        return this.acrValuesSupported;
    }

    @Generated
    public List<String> getRequestObjectSigningAlgValuesSupported() {
        return this.requestObjectSigningAlgValuesSupported;
    }

    @Generated
    public List<String> getRequestObjectEncryptionAlgValuesSupported() {
        return this.requestObjectEncryptionAlgValuesSupported;
    }

    @Generated
    public List<String> getRequestObjectEncryptionEncodingValuesSupported() {
        return this.requestObjectEncryptionEncodingValuesSupported;
    }

    @Generated
    public OidcDiscoveryProperties setClaimsParameterSupported(boolean claimsParameterSupported) {
        this.claimsParameterSupported = claimsParameterSupported;
        return this;
    }

    @Generated
    public OidcDiscoveryProperties setRequestParameterSupported(boolean requestParameterSupported) {
        this.requestParameterSupported = requestParameterSupported;
        return this;
    }

    @Generated
    public OidcDiscoveryProperties setRequestUriParameterSupported(boolean requestUriParameterSupported) {
        this.requestUriParameterSupported = requestUriParameterSupported;
        return this;
    }

    @Generated
    public OidcDiscoveryProperties setAuthorizationResponseIssuerParameterSupported(boolean authorizationResponseIssuerParameterSupported) {
        this.authorizationResponseIssuerParameterSupported = authorizationResponseIssuerParameterSupported;
        return this;
    }

    @Generated
    public OidcDiscoveryProperties setRequirePushedAuthorizationRequests(boolean requirePushedAuthorizationRequests) {
        this.requirePushedAuthorizationRequests = requirePushedAuthorizationRequests;
        return this;
    }

    @Generated
    public OidcDiscoveryProperties setScopes(List<String> scopes) {
        this.scopes = scopes;
        return this;
    }

    @Generated
    public OidcDiscoveryProperties setClaims(List<String> claims) {
        this.claims = claims;
        return this;
    }

    @Generated
    public OidcDiscoveryProperties setSubjectTypes(List<String> subjectTypes) {
        this.subjectTypes = subjectTypes;
        return this;
    }

    @Generated
    public OidcDiscoveryProperties setResponseTypesSupported(List<String> responseTypesSupported) {
        this.responseTypesSupported = responseTypesSupported;
        return this;
    }

    @Generated
    public OidcDiscoveryProperties setResponseModesSupported(List<String> responseModesSupported) {
        this.responseModesSupported = responseModesSupported;
        return this;
    }

    @Generated
    public OidcDiscoveryProperties setPromptValuesSupported(List<String> promptValuesSupported) {
        this.promptValuesSupported = promptValuesSupported;
        return this;
    }

    @Generated
    public OidcDiscoveryProperties setIntrospectionSupportedAuthenticationMethods(List<String> introspectionSupportedAuthenticationMethods) {
        this.introspectionSupportedAuthenticationMethods = introspectionSupportedAuthenticationMethods;
        return this;
    }

    @Generated
    public OidcDiscoveryProperties setClaimTypesSupported(List<String> claimTypesSupported) {
        this.claimTypesSupported = claimTypesSupported;
        return this;
    }

    @Generated
    public OidcDiscoveryProperties setGrantTypesSupported(List<String> grantTypesSupported) {
        this.grantTypesSupported = grantTypesSupported;
        return this;
    }

    @Generated
    public OidcDiscoveryProperties setDpopSigningAlgValuesSupported(List<String> dpopSigningAlgValuesSupported) {
        this.dpopSigningAlgValuesSupported = dpopSigningAlgValuesSupported;
        return this;
    }

    @Generated
    public OidcDiscoveryProperties setIdTokenSigningAlgValuesSupported(List<String> idTokenSigningAlgValuesSupported) {
        this.idTokenSigningAlgValuesSupported = idTokenSigningAlgValuesSupported;
        return this;
    }

    @Generated
    public OidcDiscoveryProperties setIdTokenEncryptionAlgValuesSupported(List<String> idTokenEncryptionAlgValuesSupported) {
        this.idTokenEncryptionAlgValuesSupported = idTokenEncryptionAlgValuesSupported;
        return this;
    }

    @Generated
    public OidcDiscoveryProperties setIdTokenEncryptionEncodingValuesSupported(List<String> idTokenEncryptionEncodingValuesSupported) {
        this.idTokenEncryptionEncodingValuesSupported = idTokenEncryptionEncodingValuesSupported;
        return this;
    }

    @Generated
    public OidcDiscoveryProperties setUserInfoSigningAlgValuesSupported(List<String> userInfoSigningAlgValuesSupported) {
        this.userInfoSigningAlgValuesSupported = userInfoSigningAlgValuesSupported;
        return this;
    }

    @Generated
    public OidcDiscoveryProperties setUserInfoEncryptionAlgValuesSupported(List<String> userInfoEncryptionAlgValuesSupported) {
        this.userInfoEncryptionAlgValuesSupported = userInfoEncryptionAlgValuesSupported;
        return this;
    }

    @Generated
    public OidcDiscoveryProperties setUserInfoEncryptionEncodingValuesSupported(List<String> userInfoEncryptionEncodingValuesSupported) {
        this.userInfoEncryptionEncodingValuesSupported = userInfoEncryptionEncodingValuesSupported;
        return this;
    }

    @Generated
    public OidcDiscoveryProperties setTokenEndpointAuthMethodsSupported(List<String> tokenEndpointAuthMethodsSupported) {
        this.tokenEndpointAuthMethodsSupported = tokenEndpointAuthMethodsSupported;
        return this;
    }

    @Generated
    public OidcDiscoveryProperties setCodeChallengeMethodsSupported(List<String> codeChallengeMethodsSupported) {
        this.codeChallengeMethodsSupported = codeChallengeMethodsSupported;
        return this;
    }

    @Generated
    public OidcDiscoveryProperties setAcrValuesSupported(List<String> acrValuesSupported) {
        this.acrValuesSupported = acrValuesSupported;
        return this;
    }

    @Generated
    public OidcDiscoveryProperties setRequestObjectSigningAlgValuesSupported(List<String> requestObjectSigningAlgValuesSupported) {
        this.requestObjectSigningAlgValuesSupported = requestObjectSigningAlgValuesSupported;
        return this;
    }

    @Generated
    public OidcDiscoveryProperties setRequestObjectEncryptionAlgValuesSupported(List<String> requestObjectEncryptionAlgValuesSupported) {
        this.requestObjectEncryptionAlgValuesSupported = requestObjectEncryptionAlgValuesSupported;
        return this;
    }

    @Generated
    public OidcDiscoveryProperties setRequestObjectEncryptionEncodingValuesSupported(List<String> requestObjectEncryptionEncodingValuesSupported) {
        this.requestObjectEncryptionEncodingValuesSupported = requestObjectEncryptionEncodingValuesSupported;
        return this;
    }
}

