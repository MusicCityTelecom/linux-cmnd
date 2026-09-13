/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  lombok.Generated
 *  org.apache.commons.lang3.builder.EqualsBuilder
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.AuthenticationHandlerExecutionResult
 *  org.apereo.cas.authentication.CredentialMetaData
 *  org.apereo.cas.authentication.MessageDescriptor
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.util.CollectionUtils
 */
package org.apereo.cas.authentication;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.Generated;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.AuthenticationHandlerExecutionResult;
import org.apereo.cas.authentication.CredentialMetaData;
import org.apereo.cas.authentication.MessageDescriptor;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.util.CollectionUtils;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public class DefaultAuthentication
implements Authentication {
    private static final long serialVersionUID = 3206127526058061391L;
    private ZonedDateTime authenticationDate;
    private Principal principal;
    private List<MessageDescriptor> warnings = new ArrayList<MessageDescriptor>();
    private List<CredentialMetaData> credentials = new ArrayList<CredentialMetaData>();
    private Map<String, List<Object>> attributes = new LinkedHashMap<String, List<Object>>();
    private Map<String, AuthenticationHandlerExecutionResult> successes;
    private Map<String, Throwable> failures = new LinkedHashMap<String, Throwable>();

    public void update(Authentication authn) {
        this.attributes.putAll(authn.getAttributes());
        this.authenticationDate = authn.getAuthenticationDate();
    }

    public void updateAll(Authentication authn) {
        this.attributes.clear();
        this.update(authn);
    }

    public void addAttribute(String name, Object value) {
        this.attributes.put(name, (List)CollectionUtils.toCollection((Object)value, ArrayList.class));
    }

    public boolean containsAttribute(String name) {
        return this.attributes.containsKey(name);
    }

    public boolean isEqualTo(Authentication auth2) {
        if (auth2 == null) {
            return false;
        }
        EqualsBuilder builder = new EqualsBuilder();
        builder.append((Object)this.getPrincipal(), (Object)auth2.getPrincipal());
        builder.append(this.getCredentials(), (Object)auth2.getCredentials());
        builder.append(this.getSuccesses(), (Object)auth2.getSuccesses());
        return builder.isEquals();
    }

    @Generated
    public DefaultAuthentication() {
    }

    @Generated
    public ZonedDateTime getAuthenticationDate() {
        return this.authenticationDate;
    }

    @Generated
    public Principal getPrincipal() {
        return this.principal;
    }

    @Generated
    public List<MessageDescriptor> getWarnings() {
        return this.warnings;
    }

    @Generated
    public List<CredentialMetaData> getCredentials() {
        return this.credentials;
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
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DefaultAuthentication)) {
            return false;
        }
        DefaultAuthentication other = (DefaultAuthentication)o;
        if (!other.canEqual(this)) {
            return false;
        }
        ZonedDateTime this$authenticationDate = this.authenticationDate;
        ZonedDateTime other$authenticationDate = other.authenticationDate;
        if (this$authenticationDate == null ? other$authenticationDate != null : !((Object)this$authenticationDate).equals(other$authenticationDate)) {
            return false;
        }
        Principal this$principal = this.principal;
        Principal other$principal = other.principal;
        if (this$principal == null ? other$principal != null : !this$principal.equals(other$principal)) {
            return false;
        }
        List<MessageDescriptor> this$warnings = this.warnings;
        List<MessageDescriptor> other$warnings = other.warnings;
        if (this$warnings == null ? other$warnings != null : !((Object)this$warnings).equals(other$warnings)) {
            return false;
        }
        List<CredentialMetaData> this$credentials = this.credentials;
        List<CredentialMetaData> other$credentials = other.credentials;
        if (this$credentials == null ? other$credentials != null : !((Object)this$credentials).equals(other$credentials)) {
            return false;
        }
        Map<String, List<Object>> this$attributes = this.attributes;
        Map<String, List<Object>> other$attributes = other.attributes;
        if (this$attributes == null ? other$attributes != null : !((Object)this$attributes).equals(other$attributes)) {
            return false;
        }
        Map<String, AuthenticationHandlerExecutionResult> this$successes = this.successes;
        Map<String, AuthenticationHandlerExecutionResult> other$successes = other.successes;
        if (this$successes == null ? other$successes != null : !((Object)this$successes).equals(other$successes)) {
            return false;
        }
        Map<String, Throwable> this$failures = this.failures;
        Map<String, Throwable> other$failures = other.failures;
        return !(this$failures == null ? other$failures != null : !((Object)this$failures).equals(other$failures));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof DefaultAuthentication;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        ZonedDateTime $authenticationDate = this.authenticationDate;
        result = result * 59 + ($authenticationDate == null ? 43 : ((Object)$authenticationDate).hashCode());
        Principal $principal = this.principal;
        result = result * 59 + ($principal == null ? 43 : $principal.hashCode());
        List<MessageDescriptor> $warnings = this.warnings;
        result = result * 59 + ($warnings == null ? 43 : ((Object)$warnings).hashCode());
        List<CredentialMetaData> $credentials = this.credentials;
        result = result * 59 + ($credentials == null ? 43 : ((Object)$credentials).hashCode());
        Map<String, List<Object>> $attributes = this.attributes;
        result = result * 59 + ($attributes == null ? 43 : ((Object)$attributes).hashCode());
        Map<String, AuthenticationHandlerExecutionResult> $successes = this.successes;
        result = result * 59 + ($successes == null ? 43 : ((Object)$successes).hashCode());
        Map<String, Throwable> $failures = this.failures;
        result = result * 59 + ($failures == null ? 43 : ((Object)$failures).hashCode());
        return result;
    }

    @Generated
    public DefaultAuthentication(ZonedDateTime authenticationDate, Principal principal, List<MessageDescriptor> warnings, List<CredentialMetaData> credentials, Map<String, List<Object>> attributes, Map<String, AuthenticationHandlerExecutionResult> successes, Map<String, Throwable> failures) {
        this.authenticationDate = authenticationDate;
        this.principal = principal;
        this.warnings = warnings;
        this.credentials = credentials;
        this.attributes = attributes;
        this.successes = successes;
        this.failures = failures;
    }
}

