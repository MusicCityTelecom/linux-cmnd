/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.handler.support.AbstractUsernamePasswordAuthenticationHandler
 *  org.apereo.cas.authentication.principal.PrincipalFactory
 *  org.apereo.cas.services.ServicesManager
 *  org.springframework.jdbc.core.JdbcOperations
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 */
package org.apereo.cas.adaptors.jdbc;

import javax.sql.DataSource;
import lombok.Generated;
import org.apereo.cas.authentication.handler.support.AbstractUsernamePasswordAuthenticationHandler;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.services.ServicesManager;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public abstract class AbstractJdbcUsernamePasswordAuthenticationHandler
extends AbstractUsernamePasswordAuthenticationHandler {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final DataSource dataSource;

    protected AbstractJdbcUsernamePasswordAuthenticationHandler(String name, ServicesManager servicesManager, PrincipalFactory principalFactory, Integer order, DataSource dataSource) {
        super(name, servicesManager, principalFactory, order);
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
    }

    @Generated
    public JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    @Generated
    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        return this.namedParameterJdbcTemplate;
    }

    @Generated
    public DataSource getDataSource() {
        return this.dataSource;
    }
}

