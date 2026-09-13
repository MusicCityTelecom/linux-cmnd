/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.authentication.AuthenticationHandlerExecutionResult
 *  org.apereo.cas.authentication.AuthenticationPasswordPolicyHandlingStrategy
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.authentication.principal.PrincipalFactory
 *  org.apereo.cas.services.ServicesManager
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.authentication;

import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.FailedLoginException;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.AuthenticationHandlerExecutionResult;
import org.apereo.cas.authentication.AuthenticationPasswordPolicyHandlingStrategy;
import org.apereo.cas.authentication.credential.UsernamePasswordCredential;
import org.apereo.cas.authentication.handler.support.AbstractUsernamePasswordAuthenticationHandler;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.authentication.principal.PrincipalFactoryUtils;
import org.apereo.cas.services.ServicesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AcceptUsersAuthenticationHandler
extends AbstractUsernamePasswordAuthenticationHandler {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(AcceptUsersAuthenticationHandler.class);
    private Map<String, String> users;

    public AcceptUsersAuthenticationHandler(Map<String, String> users) {
        this(null, null, PrincipalFactoryUtils.newPrincipalFactory(), Integer.MAX_VALUE, users);
    }

    public AcceptUsersAuthenticationHandler(String name) {
        this(name, null, PrincipalFactoryUtils.newPrincipalFactory(), Integer.MAX_VALUE, new HashMap<String, String>(0));
    }

    public AcceptUsersAuthenticationHandler(String name, ServicesManager servicesManager, PrincipalFactory principalFactory, Integer order, Map<String, String> users) {
        super(name, servicesManager, principalFactory, order);
        this.users = users;
    }

    @Override
    protected AuthenticationHandlerExecutionResult authenticateUsernamePasswordInternal(UsernamePasswordCredential credential, String originalPassword) throws GeneralSecurityException {
        if (this.users == null || this.users.isEmpty()) {
            throw new FailedLoginException("No user can be accepted because none is defined");
        }
        String username = credential.getUsername();
        String cachedPassword = this.users.get(username);
        if (cachedPassword == null) {
            LOGGER.debug("[{}] was not found in the map.", (Object)username);
            throw new AccountNotFoundException(username + " not found in backing map.");
        }
        if (!StringUtils.equals((CharSequence)credential.toPassword(), (CharSequence)cachedPassword)) {
            throw new FailedLoginException();
        }
        AuthenticationPasswordPolicyHandlingStrategy strategy = this.getPasswordPolicyHandlingStrategy();
        if (strategy != null && StringUtils.isNotBlank((CharSequence)username)) {
            LOGGER.debug("Attempting to examine and handle password policy via [{}]", (Object)strategy.getClass().getSimpleName());
            Principal principal = this.principalFactory.createPrincipal(username);
            List messageList = strategy.handle((Object)principal, (Object)this.getPasswordPolicyConfiguration());
            return this.createHandlerResult(credential, principal, messageList);
        }
        throw new FailedLoginException("Unable to authenticate " + credential.getId());
    }

    @Generated
    public void setUsers(Map<String, String> users) {
        this.users = users;
    }
}

