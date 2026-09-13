/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.NonNull
 *  org.apereo.cas.authentication.AuthenticationHandlerExecutionResult
 *  org.apereo.cas.authentication.Credential
 *  org.apereo.cas.authentication.CredentialMetaData
 *  org.apereo.cas.authentication.MessageDescriptor
 *  org.apereo.cas.authentication.PrePostAuthenticationHandler
 *  org.apereo.cas.authentication.PreventedException
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.authentication.principal.PrincipalFactory
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.services.ServicesManager
 */
package org.apereo.cas.authentication.handler.support;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import javax.security.auth.login.FailedLoginException;
import lombok.NonNull;
import org.apereo.cas.authentication.AbstractAuthenticationHandler;
import org.apereo.cas.authentication.AuthenticationHandlerExecutionResult;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.CredentialMetaData;
import org.apereo.cas.authentication.DefaultAuthenticationHandlerExecutionResult;
import org.apereo.cas.authentication.MessageDescriptor;
import org.apereo.cas.authentication.PrePostAuthenticationHandler;
import org.apereo.cas.authentication.PreventedException;
import org.apereo.cas.authentication.metadata.BasicCredentialMetaData;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.services.ServicesManager;

public abstract class AbstractPreAndPostProcessingAuthenticationHandler
extends AbstractAuthenticationHandler
implements PrePostAuthenticationHandler {
    protected AbstractPreAndPostProcessingAuthenticationHandler(String name, ServicesManager servicesManager, PrincipalFactory principalFactory, Integer order) {
        super(name, servicesManager, principalFactory, order);
    }

    public AuthenticationHandlerExecutionResult authenticate(Credential credential, Service service) throws GeneralSecurityException, PreventedException {
        if (!this.preAuthenticate(credential)) {
            throw new FailedLoginException();
        }
        return this.postAuthenticate(credential, this.doAuthentication(credential, service));
    }

    protected abstract AuthenticationHandlerExecutionResult doAuthentication(Credential var1, Service var2) throws GeneralSecurityException, PreventedException;

    protected AuthenticationHandlerExecutionResult createHandlerResult(@NonNull Credential credential, @NonNull Principal principal, @NonNull List<MessageDescriptor> warnings) {
        if (credential == null) {
            throw new NullPointerException("credential is marked non-null but is null");
        }
        if (principal == null) {
            throw new NullPointerException("principal is marked non-null but is null");
        }
        if (warnings == null) {
            throw new NullPointerException("warnings is marked non-null but is null");
        }
        return new DefaultAuthenticationHandlerExecutionResult(this, (CredentialMetaData)new BasicCredentialMetaData(credential), principal, warnings);
    }

    protected AuthenticationHandlerExecutionResult createHandlerResult(@NonNull Credential credential, @NonNull Principal principal) {
        if (credential == null) {
            throw new NullPointerException("credential is marked non-null but is null");
        }
        if (principal == null) {
            throw new NullPointerException("principal is marked non-null but is null");
        }
        return new DefaultAuthenticationHandlerExecutionResult(this, (CredentialMetaData)new BasicCredentialMetaData(credential), principal, new ArrayList<MessageDescriptor>(0));
    }
}

