/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.authentication;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.AuthenticationHandlerExecutionResult;
import org.apereo.cas.authentication.CredentialMetaData;
import org.apereo.cas.authentication.MessageDescriptor;
import org.apereo.cas.authentication.principal.Principal;

public interface AuthenticationBuilder
extends Serializable {
    public Principal getPrincipal();

    public AuthenticationBuilder setPrincipal(Principal var1);

    public AuthenticationBuilder addCredentials(List<CredentialMetaData> var1);

    public AuthenticationBuilder addCredential(CredentialMetaData var1);

    public AuthenticationBuilder addWarnings(List<MessageDescriptor> var1);

    public AuthenticationBuilder addWarning(MessageDescriptor var1);

    public AuthenticationBuilder setWarnings(List<MessageDescriptor> var1);

    public AuthenticationBuilder addAttribute(String var1, List<Object> var2);

    public AuthenticationBuilder addAttribute(String var1, Object var2);

    public AuthenticationBuilder addAttributes(Map<String, Object> var1);

    public Map<String, AuthenticationHandlerExecutionResult> getSuccesses();

    public AuthenticationBuilder setSuccesses(Map<String, AuthenticationHandlerExecutionResult> var1);

    public AuthenticationBuilder addSuccesses(Map<String, AuthenticationHandlerExecutionResult> var1);

    public AuthenticationBuilder addFailures(Map<String, Throwable> var1);

    public AuthenticationBuilder addSuccess(String var1, AuthenticationHandlerExecutionResult var2);

    public AuthenticationBuilder setAuthenticationDate(ZonedDateTime var1);

    public Authentication build();

    public Map<String, Throwable> getFailures();

    public AuthenticationBuilder setFailures(Map<String, Throwable> var1);

    public AuthenticationBuilder addFailure(String var1, Throwable var2);

    public AuthenticationBuilder setAttributes(Map<String, List<Object>> var1);

    public AuthenticationBuilder mergeAttribute(String var1, Object var2);

    public AuthenticationBuilder mergeAttribute(String var1, List<Object> var2);

    public ZonedDateTime getAuthenticationDate();

    public boolean hasAttribute(String var1, Predicate<Object> var2);

    default public boolean hasAttribute(String name) {
        return this.hasAttribute(name, o -> true);
    }
}

