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
import java.util.List;
import org.apereo.cas.authentication.CredentialMetaData;
import org.apereo.cas.authentication.MessageDescriptor;
import org.apereo.cas.authentication.principal.Principal;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public interface AuthenticationHandlerExecutionResult
extends Serializable {
    public String getHandlerName();

    public CredentialMetaData getCredentialMetaData();

    public Principal getPrincipal();

    public List<MessageDescriptor> getWarnings();

    public AuthenticationHandlerExecutionResult addWarning(MessageDescriptor var1);

    public AuthenticationHandlerExecutionResult clearWarnings();
}

