/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.AuthenticationHandlerExecutionResult
 *  org.apereo.cas.authentication.Credential
 *  org.apereo.cas.authentication.PreventedException
 *  org.apereo.cas.authentication.credential.UsernamePasswordCredential
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.authentication.principal.PrincipalFactory
 *  org.apereo.cas.services.ServicesManager
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.adaptors.jdbc;

import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.util.ArrayList;
import javax.security.auth.login.FailedLoginException;
import javax.sql.DataSource;
import lombok.Generated;
import org.apereo.cas.adaptors.jdbc.AbstractJdbcUsernamePasswordAuthenticationHandler;
import org.apereo.cas.authentication.AuthenticationHandlerExecutionResult;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.PreventedException;
import org.apereo.cas.authentication.credential.UsernamePasswordCredential;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.services.ServicesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BindModeSearchDatabaseAuthenticationHandler
extends AbstractJdbcUsernamePasswordAuthenticationHandler {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(BindModeSearchDatabaseAuthenticationHandler.class);

    public BindModeSearchDatabaseAuthenticationHandler(String name, ServicesManager servicesManager, PrincipalFactory principalFactory, Integer order, DataSource dataSource) {
        super(name, servicesManager, principalFactory, order, dataSource);
    }

    protected AuthenticationHandlerExecutionResult authenticateUsernamePasswordInternal(UsernamePasswordCredential credential, String originalPassword) throws GeneralSecurityException, PreventedException {
        AuthenticationHandlerExecutionResult authenticationHandlerExecutionResult;
        block8: {
            String username = credential.getUsername();
            String password = credential.toPassword();
            Connection connection = this.getDataSource().getConnection(username, password);
            try {
                LOGGER.trace("Established connection to schema [{}]", (Object)connection.getSchema());
                Principal principal = this.principalFactory.createPrincipal(username);
                authenticationHandlerExecutionResult = this.createHandlerResult((Credential)credential, principal, new ArrayList(0));
                if (connection == null) break block8;
            }
            catch (Throwable throwable) {
                try {
                    if (connection != null) {
                        try {
                            connection.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                catch (Exception e) {
                    throw new FailedLoginException(e.getMessage());
                }
            }
            connection.close();
        }
        return authenticationHandlerExecutionResult;
    }
}

