/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonFormat$Shape
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  lombok.Generated
 *  org.apache.commons.lang3.BooleanUtils
 *  org.apache.commons.lang3.StringUtils
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.Serializable;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import lombok.Generated;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.services.RegisteredService;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public interface RegisteredServiceProperty
extends Serializable {
    public Set<String> getValues();

    @JsonIgnore
    public String getValue();

    @JsonIgnore
    default public <T> T getValue(Class<T> clazz) {
        String value = this.getValue();
        if (StringUtils.isNotBlank((CharSequence)value)) {
            return clazz.cast(value);
        }
        return null;
    }

    public boolean contains(String var1);

    @JsonIgnore
    default public boolean getBooleanValue() {
        String value = this.getValue();
        return StringUtils.isNotBlank((CharSequence)value) && BooleanUtils.toBoolean((String)value);
    }

    @JsonFormat(shape=JsonFormat.Shape.OBJECT)
    public static enum RegisteredServiceProperties {
        WSFED_RELYING_PARTY_ID("wsfed.relyingPartyIdentifier", "", RegisteredServicePropertyGroups.DELEGATED_AUTHN_WSFED, RegisteredServicePropertyTypes.STRING, "Used when delegating authentication to ADFS to indicate the relying party identifier."),
        TOKEN_AS_SERVICE_TICKET("jwtAsServiceTicket", "false", RegisteredServicePropertyGroups.JWT_SERVICE_TICKETS, RegisteredServicePropertyTypes.BOOLEAN, "Produce a JWT as a response when generating service tickets."),
        TOKEN_AS_SERVICE_TICKET_CIPHER_STRATEGY_TYPE("jwtAsServiceTicketCipherStrategyType", "ENCRYPT_AND_SIGN", RegisteredServicePropertyGroups.JWT_SERVICE_TICKETS, RegisteredServicePropertyTypes.STRING, "Indicate the cipher strategy for JWT service tickets to determine order of signing/encryption operations."),
        TOKEN_AS_SERVICE_TICKET_SIGNING_KEY("jwtAsServiceTicketSigningKey", "", RegisteredServicePropertyGroups.JWT_SERVICE_TICKETS, RegisteredServicePropertyTypes.STRING, "Produce a signed JWT as a response when generating service tickets using the provided signing key."),
        TOKEN_AS_SERVICE_TICKET_ENCRYPTION_KEY("jwtAsServiceTicketEncryptionKey", "", RegisteredServicePropertyGroups.JWT_SERVICE_TICKETS, RegisteredServicePropertyTypes.STRING, "Produce an encrypted JWT as a response when generating service tickets using the provided encryption key."),
        ACCESS_TOKEN_AS_JWT_SIGNING_KEY("accessTokenAsJwtSigningKey", "", RegisteredServicePropertyGroups.JWT_ACCESS_TOKENS, RegisteredServicePropertyTypes.STRING, "Produce a signed JWT as a response when generating access tokens using the provided signing key."),
        ACCESS_TOKEN_AS_JWT_CIPHER_STRATEGY_TYPE("accessTokenAsJwtCipherStrategyType", "ENCRYPT_AND_SIGN", RegisteredServicePropertyGroups.JWT_ACCESS_TOKENS, RegisteredServicePropertyTypes.STRING, "Indicate the cipher strategy for JWTs as access tokens, to determine order of signing/encryption operations."),
        ACCESS_TOKEN_AS_JWT_SIGNING_ENABLED("accessTokenAsJwtSigningEnabled", "true", RegisteredServicePropertyGroups.JWT_ACCESS_TOKENS, RegisteredServicePropertyTypes.BOOLEAN, "Enable signing JWTs as a response when generating access tokens using the provided signing key."),
        ACCESS_TOKEN_AS_JWT_ENCRYPTION_ENABLED("accessTokenAsJwtEncryptionEnabled", "false", RegisteredServicePropertyGroups.JWT_ACCESS_TOKENS, RegisteredServicePropertyTypes.BOOLEAN, "Enable encryption of JWTs as a response when generating access tokens using the provided encryption key."),
        ACCESS_TOKEN_AS_JWT_ENCRYPTION_KEY("accessTokenAsJwtEncryptionKey", "", RegisteredServicePropertyGroups.JWT_ACCESS_TOKENS, RegisteredServicePropertyTypes.STRING, "Produce an encrypted JWT as a response when generating access tokens using the provided encryption key."),
        TOKEN_SECRET_SIGNING("jwtSigningSecret", "", RegisteredServicePropertyGroups.JWT_AUTHENTICATION, RegisteredServicePropertyTypes.STRING, "Jwt signing secret defined for a given service."),
        TOKEN_SECRET_SIGNING_ALG("jwtSigningSecretAlg", "HS256", RegisteredServicePropertyGroups.JWT_AUTHENTICATION, RegisteredServicePropertyTypes.STRING, "Jwt signing secret alg defined for a given service."),
        TOKEN_SECRET_ENCRYPTION("jwtEncryptionSecret", "", RegisteredServicePropertyGroups.JWT_AUTHENTICATION, RegisteredServicePropertyTypes.STRING, "Jwt encryption secret defined for a given service."),
        TOKEN_SECRET_ENCRYPTION_ALG("jwtEncryptionSecretAlg", "", RegisteredServicePropertyGroups.JWT_AUTHENTICATION, RegisteredServicePropertyTypes.STRING, "Jwt encryption secret alg defined for a given service."),
        TOKEN_SECRET_ENCRYPTION_METHOD("jwtEncryptionSecretMethod", "A192CBC-HS384", RegisteredServicePropertyGroups.JWT_AUTHENTICATION, RegisteredServicePropertyTypes.STRING, "Jwt encryption secret method defined for a given service."),
        TOKEN_SECRETS_ARE_BASE64_ENCODED("jwtSecretsAreBase64Encoded", "false", RegisteredServicePropertyGroups.JWT_AUTHENTICATION, RegisteredServicePropertyTypes.BOOLEAN, "Determine whether secrets are Base64 encoded."),
        SKIP_INTERRUPT_NOTIFICATIONS("skipInterrupt", "false", RegisteredServicePropertyGroups.INTERRUPTS, RegisteredServicePropertyTypes.BOOLEAN, "Whether interrupt notifications should be skipped."),
        SKIP_REQUIRED_SERVICE_CHECK("skipRequiredServiceCheck", "false", RegisteredServicePropertyGroups.REGISTERED_SERVICES, RegisteredServicePropertyTypes.BOOLEAN, "Whether this service should skip qualification for required-service pattern checks."),
        HTTP_HEADER_ENABLE_CACHE_CONTROL("httpHeaderEnableCacheControl", "true", RegisteredServicePropertyGroups.HTTP_HEADERS, RegisteredServicePropertyTypes.BOOLEAN, "Whether CAS should inject cache control headers into the response when this service is in process."),
        HTTP_HEADER_ENABLE_XCONTENT_OPTIONS("httpHeaderEnableXContentOptions", "true", RegisteredServicePropertyGroups.HTTP_HEADERS, RegisteredServicePropertyTypes.BOOLEAN, "Whether CAS should inject xcontent options headers into the response when this service is in process."),
        HTTP_HEADER_ENABLE_STRICT_TRANSPORT_SECURITY("httpHeaderEnableStrictTransportSecurity", "true", RegisteredServicePropertyGroups.HTTP_HEADERS, RegisteredServicePropertyTypes.BOOLEAN, "Whether CAS should inject strict transport security headers into the response when this service is in process."),
        HTTP_HEADER_STRICT_TRANSPORT_SECURITY("httpHeaderStrictTransportSecurity", "", RegisteredServicePropertyGroups.HTTP_HEADERS, RegisteredServicePropertyTypes.STRING, "Control the header value CAS should use when injecting strict transport security headers into the response when this service is in process."),
        HTTP_HEADER_ENABLE_XFRAME_OPTIONS("httpHeaderEnableXFrameOptions", "true", RegisteredServicePropertyGroups.HTTP_HEADERS, RegisteredServicePropertyTypes.BOOLEAN, "Whether CAS should inject xframe options headers into the response when this service is in process."),
        HTTP_HEADER_XFRAME_OPTIONS("httpHeaderXFrameOptions", "DENY", RegisteredServicePropertyGroups.HTTP_HEADERS, RegisteredServicePropertyTypes.STRING, "Whether CAS should override xframe options headers into the response when this service is in process."),
        HTTP_HEADER_ENABLE_CONTENT_SECURITY_POLICY("httpHeaderEnableContentSecurityPolicy", "true", RegisteredServicePropertyGroups.HTTP_HEADERS, RegisteredServicePropertyTypes.STRING, "Whether CAS should inject content security policy headers into the response when this service is in process."),
        HTTP_HEADER_ENABLE_XSS_PROTECTION("httpHeaderEnableXSSProtection", "true", RegisteredServicePropertyGroups.HTTP_HEADERS, RegisteredServicePropertyTypes.BOOLEAN, "Whether CAS should inject xss protection headers into the response when this service is in process."),
        CORS_ALLOW_CREDENTIALS("corsAllowCredentials", "false", RegisteredServicePropertyGroups.CORS, RegisteredServicePropertyTypes.BOOLEAN, "Whether CAS should allow credentials in CORS requests."),
        CORS_MAX_AGE("corsMaxAge", "", RegisteredServicePropertyGroups.CORS, RegisteredServicePropertyTypes.INTEGER, "Define the max-age property for CORS requests."),
        CORS_ALLOWED_ORIGINS("corsAllowedOrigins", "", RegisteredServicePropertyGroups.CORS, RegisteredServicePropertyTypes.STRING, "Define allowed origins for CORS requests. Cannot use * when credentials are allowed."),
        CORS_ALLOWED_ORIGIN_PATTERNS("corsAllowedOriginPatterns", "", RegisteredServicePropertyGroups.CORS, RegisteredServicePropertyTypes.STRING, "Define patterns of allowed origins. (e.g.'https://*.example.com') Patterns can be used when credentials are allowed."),
        CORS_ALLOWED_METHODS("corsAllowedMethods", "", RegisteredServicePropertyGroups.CORS, RegisteredServicePropertyTypes.STRING, "Define allowed methods for CORS requests. The special value * allows all methods."),
        CORS_ALLOWED_HEADERS("corsAllowedHeaders", "", RegisteredServicePropertyGroups.CORS, RegisteredServicePropertyTypes.STRING, "Define exposed headers in the response for CORS requests. Set the list of headers that a pre-flight request can list as allowed for use during an actual request. The special value `*` allows actual requests to send any header."),
        CORS_EXPOSED_HEADERS("corsExposedHeaders", "", RegisteredServicePropertyGroups.CORS, RegisteredServicePropertyTypes.STRING, "List of response headers that a response might have and can be exposed. The special value `*` allows all headers to be exposed for non-credentialed requests."),
        DELEGATED_AUTHN_FORCE_AUTHN("forceAuthn", "", RegisteredServicePropertyGroups.DELEGATED_AUTHN, RegisteredServicePropertyTypes.BOOLEAN, "Indicate binding type, when using delegated authentication to saml2 identity providers."),
        DELEGATED_AUTHN_PASSIVE_AUTHN("passiveAuthn", "", RegisteredServicePropertyGroups.DELEGATED_AUTHN, RegisteredServicePropertyTypes.BOOLEAN, "Indicate binding type, when using delegated authentication to saml2 identity providers."),
        DELEGATED_AUTHN_SAML2_AUTHN_REQUEST_BINDING_TYPE("AuthnRequestBindingType", "", RegisteredServicePropertyGroups.DELEGATED_AUTHN_SAML2, RegisteredServicePropertyTypes.STRING, "Indicate binding type, when using delegated authentication to saml2 identity providers."),
        DELEGATED_AUTHN_SAML2_ASSERTION_CONSUMER_SERVICE_INDEX("AssertionConsumerServiceIndex", "", RegisteredServicePropertyGroups.DELEGATED_AUTHN_SAML2, RegisteredServicePropertyTypes.LONG, "Indicate assertion consumer service index, when using delegated authentication to saml2 identity providers."),
        DELEGATED_AUTHN_SAML2_ATTRIBUTE_CONSUMING_SERVICE_INDEX("AttributeConsumingServiceIndex", "", RegisteredServicePropertyGroups.DELEGATED_AUTHN_SAML2, RegisteredServicePropertyTypes.LONG, "Indicate attribute consuming service index, when using delegated authentication to saml2 identity providers."),
        DELEGATED_AUTHN_SAML2_COMPARISON_TYPE("ComparisonType", "", RegisteredServicePropertyGroups.DELEGATED_AUTHN_SAML2, RegisteredServicePropertyTypes.STRING, "Indicate comparison type when using delegated authentication to saml2 identity providers."),
        DELEGATED_AUTHN_SAML2_NAME_ID_POLICY_FORMAT("NameIdPolicyFormat", "", RegisteredServicePropertyGroups.DELEGATED_AUTHN_SAML2, RegisteredServicePropertyTypes.STRING, "Indicate name id policy format, when using delegated authentication to saml2 identity providers."),
        DELEGATED_AUTHN_SAML2_NAME_ID_POLICY_ALLOW_CREATE("NameIdPolicyAllowCreate", "", RegisteredServicePropertyGroups.DELEGATED_AUTHN_SAML2, RegisteredServicePropertyTypes.BOOLEAN, "Indicate name id policy allow create, when using delegated authentication to saml2 identity providers."),
        DELEGATED_AUTHN_SAML2_PROVIDER_NAME("ProviderName", "", RegisteredServicePropertyGroups.DELEGATED_AUTHN_SAML2, RegisteredServicePropertyTypes.STRING, "Indicate provider name, when using delegated authentication to saml2 identity providers."),
        DELEGATED_AUTHN_SAML2_ISSUER_FORMAT("IssuerFormat", "", RegisteredServicePropertyGroups.DELEGATED_AUTHN_SAML2, RegisteredServicePropertyTypes.STRING, "Indicate issuer format, when using delegated authentication to saml2 identity providers."),
        DELEGATED_AUTHN_SAML2_USE_NAME_QUALIFIER("UseNameQualifier", "", RegisteredServicePropertyGroups.DELEGATED_AUTHN_SAML2, RegisteredServicePropertyTypes.BOOLEAN, "Indicate whether name qualifier should be used, when using delegated authentication to saml2 identity providers."),
        DELEGATED_AUTHN_SAML2_AUTHN_CONTEXT_CLASS_REFS("AuthnContextClassRefs", "", RegisteredServicePropertyGroups.DELEGATED_AUTHN_SAML2, RegisteredServicePropertyTypes.SET, "Indicate authn context class refs, when using delegated authentication to saml2 identity providers."),
        DELEGATED_AUTHN_SAML2_NAME_ID_ATTRIBUTE("NameIdAttribute", "", RegisteredServicePropertyGroups.DELEGATED_AUTHN_SAML2, RegisteredServicePropertyTypes.STRING, "Indicate the name id attribute when using delegated authentication to saml2 identity providers."),
        DELEGATED_AUTHN_SAML2_WANTS_ASSERTIONS_SIGNED("WantsAssertionsSigned", "", RegisteredServicePropertyGroups.DELEGATED_AUTHN_SAML2, RegisteredServicePropertyTypes.BOOLEAN, "Indicate whether assertions should be signed, when using delegated authentication to saml2 identity providers."),
        DELEGATED_AUTHN_SAML2_WANTS_RESPONSES_SIGNED("WantsResponsesSigned", "", RegisteredServicePropertyGroups.DELEGATED_AUTHN_SAML2, RegisteredServicePropertyTypes.BOOLEAN, "Indicate whether responses should be signed, when using delegated authentication to saml2 identity providers."),
        DELEGATED_AUTHN_SAML2_MAXIMUM_AUTHN_LIFETIME("MaximumAuthenticationLifetime", "", RegisteredServicePropertyGroups.DELEGATED_AUTHN_SAML2, RegisteredServicePropertyTypes.LONG, "Indicate the maximum authentication lifetime to use, when using delegated authentication to saml2 identity providers."),
        DELEGATED_AUTHN_OIDC_MAX_AGE("max_age", "", RegisteredServicePropertyGroups.DELEGATED_AUTHN_OIDC, RegisteredServicePropertyTypes.INTEGER, "Indicate max_age to use, when using delegated authentication to OIDC OP"),
        DELEGATED_AUTHN_OIDC_SCOPE("scope", "", RegisteredServicePropertyGroups.DELEGATED_AUTHN_OIDC, RegisteredServicePropertyTypes.STRING, "Indicate scope to use, when using delegated authentication to OIDC OP"),
        DELEGATED_AUTHN_OIDC_RESPONSE_TYPE("response_type", "", RegisteredServicePropertyGroups.DELEGATED_AUTHN_OIDC, RegisteredServicePropertyTypes.STRING, "Indicate response_type to use, when using delegated authentication to OIDC OP"),
        DELEGATED_AUTHN_OIDC_RESPONSE_MODE("response_mode", "", RegisteredServicePropertyGroups.DELEGATED_AUTHN_OIDC, RegisteredServicePropertyTypes.STRING, "Indicate response_mode to use, when using delegated authentication to OIDC OP"),
        CAPTCHA_IP_ADDRESS_PATTERN("captchaIPAddressPattern", "true", RegisteredServicePropertyGroups.RECAPTCHA, RegisteredServicePropertyTypes.SET, "Whether reCAPTCHA should be activated when the remote-ip address matches any of the defined pattern(s)."),
        CAPTCHA_ENABLED("captchaEnabled", "true", RegisteredServicePropertyGroups.RECAPTCHA, RegisteredServicePropertyTypes.BOOLEAN, "Whether reCAPTCHA should be enabled."),
        SCIM_OAUTH_TOKEN("scimOAuthToken", "", RegisteredServicePropertyGroups.SCIM, RegisteredServicePropertyTypes.STRING, "Define SCIM OAUTH token"),
        SCIM_USERNAME("scimUsername", "", RegisteredServicePropertyGroups.SCIM, RegisteredServicePropertyTypes.STRING, "Define SCIM username"),
        SCIM_PASSWORD("scimPassword", "", RegisteredServicePropertyGroups.SCIM, RegisteredServicePropertyTypes.STRING, "Define SCIM password"),
        SCIM_TARGET("scimTarget", "", RegisteredServicePropertyGroups.SCIM, RegisteredServicePropertyTypes.STRING, "Define SCIM target");

        private final String propertyName;
        private final String defaultValue;
        private final RegisteredServicePropertyGroups group;
        private final RegisteredServicePropertyTypes type;
        private final String description;

        @JsonIgnore
        public boolean isMemberOf(RegisteredServicePropertyGroups group) {
            return this.group == group;
        }

        @JsonIgnore
        public RegisteredServiceProperty getPropertyValue(RegisteredService service) {
            Optional<Map.Entry> property;
            if (this.isAssignedTo(service) && (property = service.getProperties().entrySet().stream().filter(entry -> ((String)entry.getKey()).equalsIgnoreCase(this.getPropertyName()) && StringUtils.isNotBlank((CharSequence)((RegisteredServiceProperty)entry.getValue()).getValue())).distinct().findFirst()).isPresent()) {
                return (RegisteredServiceProperty)property.get().getValue();
            }
            return null;
        }

        @JsonIgnore
        public <T> T getPropertyValue(RegisteredService service, Class<T> clazz) {
            RegisteredServiceProperty prop;
            if (this.isAssignedTo(service) && (prop = this.getPropertyValue(service)) != null) {
                return clazz.cast(prop.getValue());
            }
            return null;
        }

        @JsonIgnore
        public <T> T getPropertyValues(RegisteredService service, Class<T> clazz) {
            RegisteredServiceProperty prop;
            if (this.isAssignedTo(service) && (prop = this.getPropertyValue(service)) != null) {
                return clazz.cast(prop.getValues());
            }
            return null;
        }

        @JsonIgnore
        public int getPropertyIntegerValue(RegisteredService service) {
            RegisteredServiceProperty prop;
            if (this.isAssignedTo(service) && (prop = this.getPropertyValue(service)) != null) {
                return Integer.parseInt(prop.getValue());
            }
            return Integer.MIN_VALUE;
        }

        @JsonIgnore
        public long getPropertyLongValue(RegisteredService service) {
            RegisteredServiceProperty prop;
            if (this.isAssignedTo(service) && (prop = this.getPropertyValue(service)) != null) {
                return Long.parseLong(prop.getValue());
            }
            return Long.MIN_VALUE;
        }

        @JsonIgnore
        public double getPropertyDoubleValue(RegisteredService service) {
            RegisteredServiceProperty prop;
            if (this.isAssignedTo(service) && (prop = this.getPropertyValue(service)) != null) {
                return Double.parseDouble(prop.getValue());
            }
            return Double.NaN;
        }

        @JsonIgnore
        public boolean getPropertyBooleanValue(RegisteredService service) {
            RegisteredServiceProperty prop;
            if (this.isAssignedTo(service) && (prop = this.getPropertyValue(service)) != null) {
                return BooleanUtils.toBoolean((String)prop.getValue());
            }
            return BooleanUtils.toBoolean((String)this.getDefaultValue());
        }

        @JsonIgnore
        public boolean isAssignedTo(RegisteredService service) {
            return this.isAssignedTo(service, s -> true);
        }

        @JsonIgnore
        public boolean isAssignedTo(RegisteredService service, Predicate<String> valueFilter) {
            return service != null && service.getProperties().entrySet().stream().anyMatch(entry -> ((String)entry.getKey()).equalsIgnoreCase(this.getPropertyName()) && StringUtils.isNotBlank((CharSequence)((RegisteredServiceProperty)entry.getValue()).getValue()) && valueFilter.test(((RegisteredServiceProperty)entry.getValue()).getValue()));
        }

        public Object getTypedPropertyValue(RegisteredService registeredService) {
            switch (this.getType()) {
                case SET: {
                    return this.getPropertyValues(registeredService, Set.class);
                }
                case INTEGER: {
                    return this.getPropertyIntegerValue(registeredService);
                }
                case LONG: {
                    return this.getPropertyLongValue(registeredService);
                }
                case BOOLEAN: {
                    return this.getPropertyBooleanValue(registeredService);
                }
            }
            return this.getPropertyValue(registeredService).getValue();
        }

        @Generated
        public String getPropertyName() {
            return this.propertyName;
        }

        @Generated
        public String getDefaultValue() {
            return this.defaultValue;
        }

        @Generated
        public RegisteredServicePropertyGroups getGroup() {
            return this.group;
        }

        @Generated
        public RegisteredServicePropertyTypes getType() {
            return this.type;
        }

        @Generated
        public String getDescription() {
            return this.description;
        }

        @Generated
        private RegisteredServiceProperties(String propertyName, String defaultValue, RegisteredServicePropertyGroups group, RegisteredServicePropertyTypes type, String description) {
            this.propertyName = propertyName;
            this.defaultValue = defaultValue;
            this.group = group;
            this.type = type;
            this.description = description;
        }
    }

    @JsonFormat(shape=JsonFormat.Shape.OBJECT)
    public static enum RegisteredServicePropertyTypes {
        SET,
        STRING,
        INTEGER,
        BOOLEAN,
        LONG;


        @Generated
        private RegisteredServicePropertyTypes() {
        }
    }

    @JsonFormat(shape=JsonFormat.Shape.OBJECT)
    public static enum RegisteredServicePropertyGroups {
        CORS,
        DELEGATED_AUTHN,
        DELEGATED_AUTHN_SAML2,
        DELEGATED_AUTHN_WSFED,
        DELEGATED_AUTHN_OIDC,
        HTTP_HEADERS,
        INTERRUPTS,
        JWT_AUTHENTICATION,
        JWT_ACCESS_TOKENS,
        JWT_TOKENS,
        JWT_SERVICE_TICKETS,
        REGISTERED_SERVICES,
        RECAPTCHA,
        SCIM;


        @Generated
        private RegisteredServicePropertyGroups() {
        }
    }
}

