/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.AuthenticationAccountStateHandler
 *  org.apereo.cas.authentication.AuthenticationPasswordPolicyHandlingStrategy
 *  org.apereo.cas.authentication.MessageDescriptor
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.authentication.support.password;

import java.util.ArrayList;
import java.util.List;
import javax.security.auth.login.LoginException;
import lombok.Generated;
import org.apereo.cas.authentication.AuthenticationAccountStateHandler;
import org.apereo.cas.authentication.AuthenticationPasswordPolicyHandlingStrategy;
import org.apereo.cas.authentication.MessageDescriptor;
import org.apereo.cas.authentication.support.password.PasswordPolicyContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultPasswordPolicyHandlingStrategy<AuthnResponse>
implements AuthenticationPasswordPolicyHandlingStrategy<AuthnResponse, PasswordPolicyContext> {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultPasswordPolicyHandlingStrategy.class);

    public List<MessageDescriptor> handle(AuthnResponse response, PasswordPolicyContext configuration) throws LoginException {
        if (configuration == null) {
            LOGGER.debug("No password policy configuration is defined");
            return new ArrayList<MessageDescriptor>(0);
        }
        AuthenticationAccountStateHandler accountStateHandler = configuration.getAccountStateHandler();
        if (accountStateHandler == null) {
            LOGGER.debug("No password policy account state handler is defined");
            return new ArrayList<MessageDescriptor>(0);
        }
        LOGGER.debug("Applying password policy [{}] to [{}]", response, (Object)accountStateHandler);
        return accountStateHandler.handle(response, (Object)configuration);
    }
}

