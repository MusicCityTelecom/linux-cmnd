/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.AuthenticationHandlerExecutionResult
 *  org.apereo.cas.authentication.Credential
 *  org.apereo.cas.authentication.PreventedException
 *  org.apereo.cas.authentication.credential.UsernamePasswordCredential
 *  org.apereo.cas.authentication.principal.PrincipalFactory
 *  org.apereo.cas.configuration.model.support.jdbc.authn.SearchJdbcAuthenticationProperties
 *  org.apereo.cas.services.ServicesManager
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.dao.DataAccessException
 */
package org.apereo.cas.adaptors.jdbc;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import javax.security.auth.login.FailedLoginException;
import javax.sql.DataSource;
import lombok.Generated;
import org.apereo.cas.adaptors.jdbc.AbstractJdbcUsernamePasswordAuthenticationHandler;
import org.apereo.cas.authentication.AuthenticationHandlerExecutionResult;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.PreventedException;
import org.apereo.cas.authentication.credential.UsernamePasswordCredential;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.configuration.model.support.jdbc.authn.SearchJdbcAuthenticationProperties;
import org.apereo.cas.services.ServicesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

public class SearchModeSearchDatabaseAuthenticationHandler
extends AbstractJdbcUsernamePasswordAuthenticationHandler {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchModeSearchDatabaseAuthenticationHandler.class);
    private final SearchJdbcAuthenticationProperties properties;

    public SearchModeSearchDatabaseAuthenticationHandler(SearchJdbcAuthenticationProperties properties, ServicesManager servicesManager, PrincipalFactory principalFactory, DataSource datasource) {
        super(properties.getName(), servicesManager, principalFactory, properties.getOrder(), datasource);
        this.properties = properties;
    }

    protected AuthenticationHandlerExecutionResult authenticateUsernamePasswordInternal(UsernamePasswordCredential credential, String originalPassword) throws GeneralSecurityException, PreventedException {
        String sql = "SELECT COUNT('x') FROM ".concat(this.properties.getTableUsers()).concat(" WHERE ").concat(this.properties.getFieldUser()).concat(" = ? AND ").concat(this.properties.getFieldPassword()).concat("= ?");
        String username = credential.getUsername();
        try {
            LOGGER.debug("Executing SQL query [{}]", (Object)sql);
            Integer count = (Integer)this.getJdbcTemplate().queryForObject(sql, Integer.class, new Object[]{username, credential.toPassword()});
            if (count == null || count == 0) {
                throw new FailedLoginException(username + " not found with SQL query.");
            }
            return this.createHandlerResult((Credential)credential, this.principalFactory.createPrincipal(username), new ArrayList(0));
        }
        catch (DataAccessException e) {
            throw new PreventedException((Throwable)e);
        }
    }
}

