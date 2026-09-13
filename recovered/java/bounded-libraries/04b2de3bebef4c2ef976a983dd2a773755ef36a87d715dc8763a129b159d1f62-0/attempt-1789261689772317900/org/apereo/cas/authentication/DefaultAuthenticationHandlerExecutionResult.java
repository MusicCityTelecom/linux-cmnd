/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.errorprone.annotations.CanIgnoreReturnValue
 *  lombok.Generated
 *  lombok.NonNull
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.authentication.AuthenticationHandler
 *  org.apereo.cas.authentication.AuthenticationHandlerExecutionResult
 *  org.apereo.cas.authentication.CredentialMetaData
 *  org.apereo.cas.authentication.MessageDescriptor
 *  org.apereo.cas.authentication.principal.Principal
 */
package org.apereo.cas.authentication;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.AuthenticationHandler;
import org.apereo.cas.authentication.AuthenticationHandlerExecutionResult;
import org.apereo.cas.authentication.CredentialMetaData;
import org.apereo.cas.authentication.MessageDescriptor;
import org.apereo.cas.authentication.principal.Principal;

public class DefaultAuthenticationHandlerExecutionResult
implements AuthenticationHandlerExecutionResult {
    private static final long serialVersionUID = -3113998493287982485L;
    private String handlerName;
    private CredentialMetaData credentialMetaData;
    private Principal principal;
    private List<MessageDescriptor> warnings = new ArrayList<MessageDescriptor>(0);

    public DefaultAuthenticationHandlerExecutionResult(AuthenticationHandler source, CredentialMetaData metaData) {
        this(source, metaData, null, new ArrayList<MessageDescriptor>(0));
    }

    public DefaultAuthenticationHandlerExecutionResult(AuthenticationHandler source, CredentialMetaData metaData, Principal p) {
        this(source, metaData, p, new ArrayList<MessageDescriptor>(0));
    }

    public DefaultAuthenticationHandlerExecutionResult(AuthenticationHandler source, CredentialMetaData metaData, @NonNull List<MessageDescriptor> warnings) {
        this(source, metaData, null, warnings);
        if (warnings == null) {
            throw new NullPointerException("warnings is marked non-null but is null");
        }
    }

    public DefaultAuthenticationHandlerExecutionResult(AuthenticationHandler source, CredentialMetaData metaData, Principal p, @NonNull List<MessageDescriptor> warnings) {
        this(StringUtils.isBlank((CharSequence)source.getName()) ? source.getClass().getSimpleName() : source.getName(), metaData, p, warnings);
        if (warnings == null) {
            throw new NullPointerException("warnings is marked non-null but is null");
        }
    }

    @CanIgnoreReturnValue
    public AuthenticationHandlerExecutionResult addWarning(MessageDescriptor message) {
        this.warnings.add(message);
        return this;
    }

    @CanIgnoreReturnValue
    public AuthenticationHandlerExecutionResult clearWarnings() {
        this.warnings.clear();
        return this;
    }

    @Generated
    public String toString() {
        return "DefaultAuthenticationHandlerExecutionResult(handlerName=" + this.handlerName + ", credentialMetaData=" + this.credentialMetaData + ", principal=" + this.principal + ", warnings=" + this.warnings + ")";
    }

    @Generated
    public DefaultAuthenticationHandlerExecutionResult() {
    }

    @Generated
    public String getHandlerName() {
        return this.handlerName;
    }

    @Generated
    public CredentialMetaData getCredentialMetaData() {
        return this.credentialMetaData;
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
    public void setHandlerName(String handlerName) {
        this.handlerName = handlerName;
    }

    @Generated
    public void setCredentialMetaData(CredentialMetaData credentialMetaData) {
        this.credentialMetaData = credentialMetaData;
    }

    @Generated
    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }

    @Generated
    public void setWarnings(List<MessageDescriptor> warnings) {
        this.warnings = warnings;
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DefaultAuthenticationHandlerExecutionResult)) {
            return false;
        }
        DefaultAuthenticationHandlerExecutionResult other = (DefaultAuthenticationHandlerExecutionResult)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$handlerName = this.handlerName;
        String other$handlerName = other.handlerName;
        if (this$handlerName == null ? other$handlerName != null : !this$handlerName.equals(other$handlerName)) {
            return false;
        }
        CredentialMetaData this$credentialMetaData = this.credentialMetaData;
        CredentialMetaData other$credentialMetaData = other.credentialMetaData;
        if (this$credentialMetaData == null ? other$credentialMetaData != null : !this$credentialMetaData.equals(other$credentialMetaData)) {
            return false;
        }
        Principal this$principal = this.principal;
        Principal other$principal = other.principal;
        if (this$principal == null ? other$principal != null : !this$principal.equals(other$principal)) {
            return false;
        }
        List<MessageDescriptor> this$warnings = this.warnings;
        List<MessageDescriptor> other$warnings = other.warnings;
        return !(this$warnings == null ? other$warnings != null : !((Object)this$warnings).equals(other$warnings));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof DefaultAuthenticationHandlerExecutionResult;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $handlerName = this.handlerName;
        result = result * 59 + ($handlerName == null ? 43 : $handlerName.hashCode());
        CredentialMetaData $credentialMetaData = this.credentialMetaData;
        result = result * 59 + ($credentialMetaData == null ? 43 : $credentialMetaData.hashCode());
        Principal $principal = this.principal;
        result = result * 59 + ($principal == null ? 43 : $principal.hashCode());
        List<MessageDescriptor> $warnings = this.warnings;
        result = result * 59 + ($warnings == null ? 43 : ((Object)$warnings).hashCode());
        return result;
    }

    @Generated
    public DefaultAuthenticationHandlerExecutionResult(String handlerName, CredentialMetaData credentialMetaData, Principal principal, List<MessageDescriptor> warnings) {
        this.handlerName = handlerName;
        this.credentialMetaData = credentialMetaData;
        this.principal = principal;
        this.warnings = warnings;
    }
}

