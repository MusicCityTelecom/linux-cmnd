/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.BooleanUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.shiro.crypto.hash.DefaultHashService
 *  org.apache.shiro.crypto.hash.HashRequest
 *  org.apache.shiro.crypto.hash.HashRequest$Builder
 *  org.apache.shiro.util.ByteSource$Util
 *  org.apereo.cas.authentication.AuthenticationHandlerExecutionResult
 *  org.apereo.cas.authentication.Credential
 *  org.apereo.cas.authentication.PreventedException
 *  org.apereo.cas.authentication.credential.UsernamePasswordCredential
 *  org.apereo.cas.authentication.exceptions.AccountDisabledException
 *  org.apereo.cas.authentication.exceptions.AccountPasswordMustChangeException
 *  org.apereo.cas.authentication.principal.PrincipalFactory
 *  org.apereo.cas.configuration.model.support.jdbc.authn.QueryEncodeJdbcAuthenticationProperties
 *  org.apereo.cas.services.ServicesManager
 *  org.springframework.dao.DataAccessException
 *  org.springframework.dao.IncorrectResultSizeDataAccessException
 */
package org.apereo.cas.adaptors.jdbc;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Map;
import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.FailedLoginException;
import javax.sql.DataSource;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.util.ByteSource;
import org.apereo.cas.adaptors.jdbc.AbstractJdbcUsernamePasswordAuthenticationHandler;
import org.apereo.cas.authentication.AuthenticationHandlerExecutionResult;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.PreventedException;
import org.apereo.cas.authentication.credential.UsernamePasswordCredential;
import org.apereo.cas.authentication.exceptions.AccountDisabledException;
import org.apereo.cas.authentication.exceptions.AccountPasswordMustChangeException;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.configuration.model.support.jdbc.authn.QueryEncodeJdbcAuthenticationProperties;
import org.apereo.cas.services.ServicesManager;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;

public class QueryAndEncodeDatabaseAuthenticationHandler
extends AbstractJdbcUsernamePasswordAuthenticationHandler {
    private final QueryEncodeJdbcAuthenticationProperties properties;

    public QueryAndEncodeDatabaseAuthenticationHandler(QueryEncodeJdbcAuthenticationProperties properties, ServicesManager servicesManager, PrincipalFactory principalFactory, DataSource dataSource) {
        super(properties.getName(), servicesManager, principalFactory, properties.getOrder(), dataSource);
        this.properties = properties;
    }

    protected AuthenticationHandlerExecutionResult authenticateUsernamePasswordInternal(UsernamePasswordCredential transformedCredential, String originalPassword) throws GeneralSecurityException, PreventedException {
        if (StringUtils.isBlank((CharSequence)this.properties.getSql()) || StringUtils.isBlank((CharSequence)this.properties.getAlgorithmName()) || this.getJdbcTemplate() == null) {
            throw new GeneralSecurityException("Authentication handler is not configured correctly");
        }
        String username = transformedCredential.getUsername();
        try {
            String dbDisabled;
            String dbExpired;
            Map<String, Object> values = this.performSqlQuery(username);
            String digestedPassword = this.digestEncodedPassword(transformedCredential.toPassword(), values);
            if (!values.get(this.properties.getPasswordFieldName()).equals(digestedPassword)) {
                throw new FailedLoginException("Password does not match value on record.");
            }
            if (StringUtils.isNotBlank((CharSequence)this.properties.getExpiredFieldName()) && values.containsKey(this.properties.getExpiredFieldName()) && (BooleanUtils.toBoolean((String)(dbExpired = values.get(this.properties.getExpiredFieldName()).toString())) || "1".equals(dbExpired))) {
                throw new AccountPasswordMustChangeException("Password has expired");
            }
            if (StringUtils.isNotBlank((CharSequence)this.properties.getDisabledFieldName()) && values.containsKey(this.properties.getDisabledFieldName()) && (BooleanUtils.toBoolean((String)(dbDisabled = values.get(this.properties.getDisabledFieldName()).toString())) || "1".equals(dbDisabled))) {
                throw new AccountDisabledException("Account has been disabled");
            }
            return this.createHandlerResult((Credential)transformedCredential, this.principalFactory.createPrincipal(username), new ArrayList(0));
        }
        catch (IncorrectResultSizeDataAccessException e) {
            if (e.getActualSize() == 0) {
                throw new AccountNotFoundException(username + " not found with SQL query");
            }
            throw new FailedLoginException("Multiple records found for " + username);
        }
        catch (DataAccessException e) {
            throw new PreventedException((Throwable)e);
        }
    }

    protected Map<String, Object> performSqlQuery(String username) {
        return this.getJdbcTemplate().queryForMap(this.properties.getSql(), new Object[]{username});
    }

    protected String digestEncodedPassword(String encodedPassword, Map<String, Object> values) {
        DefaultHashService hashService = new DefaultHashService();
        if (StringUtils.isNotBlank((CharSequence)this.properties.getStaticSalt())) {
            hashService.setPrivateSalt(ByteSource.Util.bytes((String)this.properties.getStaticSalt()));
        }
        hashService.setHashAlgorithmName(this.properties.getAlgorithmName());
        if (values.containsKey(this.properties.getNumberOfIterationsFieldName())) {
            String longAsStr = values.get(this.properties.getNumberOfIterationsFieldName()).toString();
            hashService.setHashIterations(Integer.parseInt(longAsStr));
        } else {
            hashService.setHashIterations(this.properties.getNumberOfIterations());
        }
        if (!values.containsKey(this.properties.getSaltFieldName())) {
            throw new IllegalArgumentException("Specified field name for salt does not exist in the results");
        }
        String dynaSalt = values.get(this.properties.getSaltFieldName()).toString();
        HashRequest request = new HashRequest.Builder().setSalt((Object)dynaSalt).setSource((Object)encodedPassword).build();
        return hashService.computeHash(request).toHex();
    }
}

