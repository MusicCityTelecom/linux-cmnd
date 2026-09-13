/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.errorprone.annotations.CanIgnoreReturnValue
 *  lombok.Generated
 *  lombok.NonNull
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.AuthenticationBuilder
 *  org.apereo.cas.authentication.AuthenticationHandlerExecutionResult
 *  org.apereo.cas.authentication.CredentialMetaData
 *  org.apereo.cas.authentication.DetailedCredentialMetaData
 *  org.apereo.cas.authentication.MessageDescriptor
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.authentication.principal.PrincipalFactory
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.util.CollectionUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.authentication;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import lombok.Generated;
import lombok.NonNull;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.AuthenticationBuilder;
import org.apereo.cas.authentication.AuthenticationHandlerExecutionResult;
import org.apereo.cas.authentication.CredentialMetaData;
import org.apereo.cas.authentication.DefaultAuthentication;
import org.apereo.cas.authentication.DetailedCredentialMetaData;
import org.apereo.cas.authentication.MessageDescriptor;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultAuthenticationBuilder
implements AuthenticationBuilder {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultAuthenticationBuilder.class);
    private static final long serialVersionUID = -8504842011648432398L;
    private final List<CredentialMetaData> credentials = new ArrayList<CredentialMetaData>();
    private final List<MessageDescriptor> warnings = new ArrayList<MessageDescriptor>();
    private final Map<String, List<Object>> attributes = new LinkedHashMap<String, List<Object>>();
    private final Map<String, AuthenticationHandlerExecutionResult> successes = new LinkedHashMap<String, AuthenticationHandlerExecutionResult>();
    private final Map<String, Throwable> failures = new LinkedHashMap<String, Throwable>();
    private Principal principal;
    private ZonedDateTime authenticationDate = ZonedDateTime.now(ZoneOffset.UTC);

    public DefaultAuthenticationBuilder() {
    }

    public DefaultAuthenticationBuilder(Principal p) {
        this();
        this.principal = p;
    }

    public static AuthenticationBuilder newInstance(Authentication source) {
        DefaultAuthenticationBuilder builder = new DefaultAuthenticationBuilder(source.getPrincipal());
        builder.setAuthenticationDate(source.getAuthenticationDate());
        builder.setCredentials(source.getCredentials());
        builder.setSuccesses(source.getSuccesses());
        builder.setFailures(source.getFailures());
        builder.setAttributes(source.getAttributes());
        builder.setWarnings(source.getWarnings());
        return builder;
    }

    public static AuthenticationBuilder newInstance() {
        return new DefaultAuthenticationBuilder();
    }

    public static AuthenticationBuilder of(Principal principal, PrincipalFactory principalFactory, Map<String, List<Object>> principalAttributes, Service service, RegisteredService registeredService, Authentication authentication) {
        String principalId = registeredService.getUsernameAttributeProvider().resolveUsername(principal, service, registeredService);
        Principal newPrincipal = principalFactory.createPrincipal(principalId, principalAttributes);
        return DefaultAuthenticationBuilder.newInstance(authentication).setPrincipal(newPrincipal);
    }

    @CanIgnoreReturnValue
    public AuthenticationBuilder setPrincipal(Principal p) {
        this.principal = p;
        return this;
    }

    @CanIgnoreReturnValue
    public AuthenticationBuilder addCredentials(List<CredentialMetaData> credentials) {
        this.credentials.addAll(credentials);
        return this;
    }

    @CanIgnoreReturnValue
    public AuthenticationBuilder addCredential(CredentialMetaData credential) {
        this.credentials.add(credential);
        return this;
    }

    @CanIgnoreReturnValue
    public AuthenticationBuilder addWarnings(List<MessageDescriptor> warning) {
        this.warnings.addAll(warning);
        return this;
    }

    @CanIgnoreReturnValue
    public AuthenticationBuilder addWarning(MessageDescriptor warning) {
        this.warnings.add(warning);
        return this;
    }

    @CanIgnoreReturnValue
    public AuthenticationBuilder setWarnings(List<MessageDescriptor> warning) {
        this.warnings.clear();
        this.warnings.addAll(warning);
        return this;
    }

    @CanIgnoreReturnValue
    public AuthenticationBuilder addAttribute(String key, List<Object> value) {
        this.attributes.put(key, value);
        return this;
    }

    @CanIgnoreReturnValue
    public AuthenticationBuilder addAttribute(String key, Object value) {
        return this.addAttribute(key, (List)CollectionUtils.toCollection((Object)value, ArrayList.class));
    }

    @CanIgnoreReturnValue
    public AuthenticationBuilder setSuccesses(@NonNull Map<String, AuthenticationHandlerExecutionResult> successes) {
        if (successes == null) {
            throw new NullPointerException("successes is marked non-null but is null");
        }
        this.successes.clear();
        return this.addSuccesses(successes);
    }

    @CanIgnoreReturnValue
    public AuthenticationBuilder addAttributes(Map<String, Object> attributes) {
        attributes.forEach(this::addAttribute);
        return this;
    }

    @CanIgnoreReturnValue
    public AuthenticationBuilder addSuccesses(@NonNull Map<String, AuthenticationHandlerExecutionResult> successes) {
        if (successes == null) {
            throw new NullPointerException("successes is marked non-null but is null");
        }
        if (successes != null) {
            successes.forEach(this::addSuccess);
        }
        return this;
    }

    @CanIgnoreReturnValue
    public AuthenticationBuilder addFailures(@NonNull Map<String, Throwable> failures) {
        if (failures == null) {
            throw new NullPointerException("failures is marked non-null but is null");
        }
        if (failures != null) {
            failures.forEach(this::addFailure);
        }
        return this;
    }

    @CanIgnoreReturnValue
    public AuthenticationBuilder addSuccess(String key, AuthenticationHandlerExecutionResult value) {
        LOGGER.trace("Recording authentication handler result success under key [{}]", (Object)key);
        if (this.successes.containsKey(key)) {
            LOGGER.trace("Key mapped to authentication handler result [{}] is already recorded in the list of successful attempts. Overriding...", (Object)key);
        }
        this.successes.put(key, value);
        return this;
    }

    @CanIgnoreReturnValue
    public AuthenticationBuilder setAuthenticationDate(ZonedDateTime d) {
        if (d != null) {
            this.authenticationDate = d;
        }
        return this;
    }

    @CanIgnoreReturnValue
    public Authentication build() {
        LinkedHashMap resultingCredentials = new LinkedHashMap();
        this.credentials.stream().filter(credential -> credential instanceof DetailedCredentialMetaData).map(DetailedCredentialMetaData.class::cast).forEach(credential -> {
            String key = credential.getId() + "#" + credential.getCredentialClass().getName();
            if (resultingCredentials.containsKey(key)) {
                DetailedCredentialMetaData current = (DetailedCredentialMetaData)resultingCredentials.get(key);
                current.getProperties().putAll(credential.getProperties());
                resultingCredentials.put(key, current);
            } else {
                resultingCredentials.put(key, credential);
            }
        });
        return new DefaultAuthentication(this.authenticationDate, this.principal, this.warnings, new ArrayList<CredentialMetaData>(resultingCredentials.values()), this.attributes, this.successes, this.failures);
    }

    @CanIgnoreReturnValue
    public AuthenticationBuilder setFailures(@NonNull Map<String, Throwable> failures) {
        if (failures == null) {
            throw new NullPointerException("failures is marked non-null but is null");
        }
        this.failures.clear();
        return this.addFailures(failures);
    }

    @CanIgnoreReturnValue
    public AuthenticationBuilder addFailure(String key, Throwable value) {
        LOGGER.trace("Recording authentication handler failure under key [{}]", (Object)key);
        if (this.successes.containsKey(key)) {
            String newKey = key + System.currentTimeMillis();
            LOGGER.trace("Key mapped to authentication handler failure [{}] is recorded in the list of failed attempts. Overriding with [{}]", (Object)key, (Object)newKey);
            this.failures.put(newKey, value);
        } else {
            this.failures.put(key, value);
        }
        return this;
    }

    @CanIgnoreReturnValue
    public AuthenticationBuilder setAttributes(Map<String, List<Object>> attributes) {
        this.attributes.clear();
        this.attributes.putAll(attributes);
        return this;
    }

    @CanIgnoreReturnValue
    public AuthenticationBuilder mergeAttribute(String key, Object value) {
        return this.mergeAttribute(key, (List)CollectionUtils.toCollection((Object)value, ArrayList.class));
    }

    @CanIgnoreReturnValue
    public AuthenticationBuilder mergeAttribute(String key, List<Object> value) {
        List<Object> currentValue = this.attributes.get(key);
        if (currentValue == null) {
            return this.addAttribute(key, value);
        }
        ArrayList collection = (ArrayList)CollectionUtils.toCollection(currentValue, ArrayList.class);
        collection.addAll(CollectionUtils.toCollection(value));
        return this.addAttribute(key, collection);
    }

    @CanIgnoreReturnValue
    public boolean hasAttribute(String name, Predicate<Object> predicate) {
        if (this.attributes.containsKey(name)) {
            List<Object> value = this.attributes.get(name);
            Set valueCol = CollectionUtils.toCollection(value);
            return valueCol.stream().anyMatch(predicate);
        }
        return false;
    }

    @CanIgnoreReturnValue
    public AuthenticationBuilder setCredentials(@NonNull List<CredentialMetaData> credentials) {
        if (credentials == null) {
            throw new NullPointerException("credentials is marked non-null but is null");
        }
        this.credentials.clear();
        this.credentials.addAll(credentials);
        return this;
    }

    @Generated
    public List<CredentialMetaData> getCredentials() {
        return this.credentials;
    }

    @Generated
    public List<MessageDescriptor> getWarnings() {
        return this.warnings;
    }

    @Generated
    public Map<String, List<Object>> getAttributes() {
        return this.attributes;
    }

    @Generated
    public Map<String, AuthenticationHandlerExecutionResult> getSuccesses() {
        return this.successes;
    }

    @Generated
    public Map<String, Throwable> getFailures() {
        return this.failures;
    }

    @Generated
    public Principal getPrincipal() {
        return this.principal;
    }

    @Generated
    public ZonedDateTime getAuthenticationDate() {
        return this.authenticationDate;
    }
}

