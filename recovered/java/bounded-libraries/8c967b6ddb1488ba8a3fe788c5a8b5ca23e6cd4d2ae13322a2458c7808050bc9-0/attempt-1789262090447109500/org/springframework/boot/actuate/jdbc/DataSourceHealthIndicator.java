/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.dao.support.DataAccessUtils
 *  org.springframework.jdbc.IncorrectResultSetColumnCountException
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowMapper
 *  org.springframework.jdbc.support.JdbcUtils
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.actuate.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.IncorrectResultSetColumnCountException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class DataSourceHealthIndicator
extends AbstractHealthIndicator
implements InitializingBean {
    private DataSource dataSource;
    private String query;
    private JdbcTemplate jdbcTemplate;

    public DataSourceHealthIndicator() {
        this(null, null);
    }

    public DataSourceHealthIndicator(DataSource dataSource) {
        this(dataSource, null);
    }

    public DataSourceHealthIndicator(DataSource dataSource, String query) {
        super("DataSource health check failed");
        this.dataSource = dataSource;
        this.query = query;
        this.jdbcTemplate = dataSource != null ? new JdbcTemplate(dataSource) : null;
    }

    public void afterPropertiesSet() throws Exception {
        Assert.state((this.dataSource != null ? 1 : 0) != 0, (String)"DataSource for DataSourceHealthIndicator must be specified");
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        if (this.dataSource == null) {
            builder.up().withDetail("database", "unknown");
        } else {
            this.doDataSourceHealthCheck(builder);
        }
    }

    private void doDataSourceHealthCheck(Health.Builder builder) throws Exception {
        builder.up().withDetail("database", this.getProduct());
        String validationQuery = this.query;
        if (StringUtils.hasText((String)validationQuery)) {
            builder.withDetail("validationQuery", validationQuery);
            List results = this.jdbcTemplate.query(validationQuery, (RowMapper)new SingleColumnRowMapper());
            Object result = DataAccessUtils.requiredSingleResult((Collection)results);
            builder.withDetail("result", result);
        } else {
            builder.withDetail("validationQuery", "isValid()");
            boolean valid = this.isConnectionValid();
            builder.status(valid ? Status.UP : Status.DOWN);
        }
    }

    private String getProduct() {
        return (String)this.jdbcTemplate.execute(this::getProduct);
    }

    private String getProduct(Connection connection) throws SQLException {
        return connection.getMetaData().getDatabaseProductName();
    }

    private Boolean isConnectionValid() {
        return (Boolean)this.jdbcTemplate.execute(this::isConnectionValid);
    }

    private Boolean isConnectionValid(Connection connection) throws SQLException {
        return connection.isValid(0);
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return this.query;
    }

    private static class SingleColumnRowMapper
    implements RowMapper<Object> {
        private SingleColumnRowMapper() {
        }

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            ResultSetMetaData metaData = rs.getMetaData();
            int columns = metaData.getColumnCount();
            if (columns != 1) {
                throw new IncorrectResultSetColumnCountException(1, columns);
            }
            return JdbcUtils.getResultSetValue((ResultSet)rs, (int)1);
        }
    }
}

