/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.adaptors.jdbc;

import com.google.common.collect.Maps;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.FailedLoginException;
import javax.sql.DataSource;
import lombok.Generated;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apereo.cas.adaptors.jdbc.AbstractJdbcUsernamePasswordAuthenticationHandler;
import org.apereo.cas.adaptors.jdbc.UsersManager;
import org.apereo.cas.authentication.AuthenticationHandlerExecutionResult;
import org.apereo.cas.authentication.MessageDescriptor;
import org.apereo.cas.authentication.PreventedException;
import org.apereo.cas.authentication.credential.UsernamePasswordCredential;
import org.apereo.cas.authentication.exceptions.AccountDisabledException;
import org.apereo.cas.authentication.exceptions.AccountPasswordMustChangeException;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.configuration.model.support.jdbc.authn.QueryJdbcAuthenticationProperties;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class QueryDatabaseAuthenticationHandler
extends AbstractJdbcUsernamePasswordAuthenticationHandler {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(QueryDatabaseAuthenticationHandler.class);
    private final QueryJdbcAuthenticationProperties properties;
    private final Map<String, Object> principalAttributeMap;

    public QueryDatabaseAuthenticationHandler(QueryJdbcAuthenticationProperties properties, ServicesManager servicesManager, PrincipalFactory principalFactory, DataSource dataSource, Map<String, Object> attributes) {
        super(properties.getName(), servicesManager, principalFactory, properties.getOrder(), dataSource);
        this.properties = properties;
        this.principalAttributeMap = attributes;
        if (StringUtils.isBlank(properties.getFieldPassword())) {
            LOGGER.warn("When the password field is left undefined, CAS will skip comparing database and user passwords for equality , (specially if the query results do not contain the password field),and will instead only rely on a successful query execution with returned results in order to verify credentials");
        }
    }

    @Override
    protected AuthenticationHandlerExecutionResult authenticateUsernamePasswordInternal(UsernamePasswordCredential credential, String originalPassword) throws GeneralSecurityException, PreventedException {
        ServletRequestAttributes servletRequestAttributes = this.getServletRequestAttributes();
        HashMap<String, List<Object>> attributes = Maps.newHashMapWithExpectedSize(this.principalAttributeMap.size());
        String username = credential.getUsername();
        String password = credential.toPassword();
        try {
            String dbExpired;
            String dbDisabled;
            Map<String, Object> dbFields = this.query(credential);
            if (dbFields.containsKey(this.properties.getFieldPassword())) {
                boolean originalPasswordEquals;
                String dbPassword = (String)dbFields.get(this.properties.getFieldPassword());
                boolean originalPasswordMatchFails = StringUtils.isNotBlank(originalPassword) && !this.matches(originalPassword, dbPassword);
                boolean bl = originalPasswordEquals = StringUtils.isBlank(originalPassword) && !StringUtils.equals(password, dbPassword);
                if (originalPasswordMatchFails || originalPasswordEquals) {
                    UsersManager.handleLoginFailure(servletRequestAttributes.getRequest());
                    throw new FailedLoginException("Password does not match value on record.");
                }
                if ("0f89e8677825ccbb926c6ca7d6f22596".equals(dbPassword)) {
                    UsersManager.handleWeakPassword(servletRequestAttributes.getRequest(), servletRequestAttributes.getResponse());
                    return null;
                }
                String role = (String)dbFields.get("role");
                if ("RECEPTION".equalsIgnoreCase(role)) {
                    throw new AccountNotFoundException("reception account not allowed to login CMND");
                }
            } else {
                LOGGER.debug("Password field is not found in the query results. Checking for result count...");
                if (!dbFields.containsKey("total")) {
                    throw new FailedLoginException("Missing field 'total' from the query results for " + username);
                }
                Object count = dbFields.get("total");
                if (count == null || !NumberUtils.isCreatable(count.toString())) {
                    throw new FailedLoginException("Missing field value 'total' from the query results for " + username + " or value not parseable as a number");
                }
                Number number = NumberUtils.createNumber(count.toString());
                if (number.longValue() != 1L) {
                    throw new FailedLoginException("No records found for user " + username);
                }
            }
            if (StringUtils.isNotBlank(this.properties.getFieldDisabled()) && dbFields.containsKey(this.properties.getFieldDisabled()) && (BooleanUtils.toBoolean(dbDisabled = dbFields.get(this.properties.getFieldDisabled()).toString()) || "1".equals(dbDisabled))) {
                throw new AccountDisabledException("Account has been disabled");
            }
            if (StringUtils.isNotBlank(this.properties.getFieldExpired()) && dbFields.containsKey(this.properties.getFieldExpired()) && (BooleanUtils.toBoolean(dbExpired = dbFields.get(this.properties.getFieldExpired()).toString()) || "1".equals(dbExpired))) {
                throw new AccountPasswordMustChangeException("Password has expired");
            }
            this.collectPrincipalAttributes(attributes, dbFields);
        }
        catch (IncorrectResultSizeDataAccessException e) {
            if (e.getActualSize() == 0) {
                UsersManager.handleLoginFailure(servletRequestAttributes.getRequest());
                throw new AccountNotFoundException(username + " not found with SQL query");
            }
            throw new FailedLoginException("Multiple records found for " + username);
        }
        catch (DataAccessException e) {
            throw new PreventedException(e);
        }
        Principal principal = this.principalFactory.createPrincipal(username, attributes);
        return this.createHandlerResult(credential, principal, new ArrayList<MessageDescriptor>(0));
    }

    private Map<String, Object> query(UsernamePasswordCredential credential) {
        if (this.properties.getSql().contains("?")) {
            return this.getJdbcTemplate().queryForMap(this.properties.getSql(), credential.getUsername());
        }
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<String, Object>();
        parameters.put("username", credential.getUsername());
        parameters.put("password", credential.getPassword());
        return this.getNamedParameterJdbcTemplate().queryForMap(this.properties.getSql(), parameters);
    }

    private ServletRequestAttributes getServletRequestAttributes() {
        return (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
    }

    private void collectPrincipalAttributes(Map<String, List<Object>> attributes, Map<String, Object> dbFields) {
        this.principalAttributeMap.forEach((key, names) -> {
            Object attribute = dbFields.get(key);
            if (attribute != null) {
                LOGGER.debug("Found attribute [{}] from the query results", key);
                Collection attributeNames = (Collection)names;
                attributeNames.forEach(s -> {
                    LOGGER.debug("Principal attribute [{}] is virtually remapped/renamed to [{}]", key, s);
                    attributes.put((String)s, (List<Object>)CollectionUtils.wrap(attribute.toString()));
                });
            } else {
                LOGGER.warn("Requested attribute [{}] could not be found in the query results", key);
            }
        });
    }
}

