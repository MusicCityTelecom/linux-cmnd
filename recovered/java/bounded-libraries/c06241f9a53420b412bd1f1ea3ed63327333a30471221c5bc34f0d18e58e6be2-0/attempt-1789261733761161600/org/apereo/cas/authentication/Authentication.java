/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 */
package org.apereo.cas.authentication;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import org.apereo.cas.authentication.AuthenticationHandlerExecutionResult;
import org.apereo.cas.authentication.CredentialMetaData;
import org.apereo.cas.authentication.MessageDescriptor;
import org.apereo.cas.authentication.principal.Principal;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public interface Authentication
extends Serializable {
    public Principal getPrincipal();

    public ZonedDateTime getAuthenticationDate();

    public Map<String, List<Object>> getAttributes();

    public void addAttribute(String var1, Object var2);

    public boolean containsAttribute(String var1);

    public List<CredentialMetaData> getCredentials();

    public List<MessageDescriptor> getWarnings();

    public Map<String, AuthenticationHandlerExecutionResult> getSuccesses();

    public Map<String, Throwable> getFailures();

    public void update(Authentication var1);

    public void updateAll(Authentication var1);

    public boolean isEqualTo(Authentication var1);
}

