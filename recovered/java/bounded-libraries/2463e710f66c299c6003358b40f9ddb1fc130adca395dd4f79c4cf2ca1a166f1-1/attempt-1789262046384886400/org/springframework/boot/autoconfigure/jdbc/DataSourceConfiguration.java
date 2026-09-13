/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.zaxxer.hikari.HikariDataSource
 *  oracle.jdbc.OracleConnection
 *  oracle.ucp.jdbc.PoolDataSourceImpl
 *  org.apache.commons.dbcp2.BasicDataSource
 *  org.apache.tomcat.jdbc.pool.DataSource
 *  org.springframework.boot.context.properties.ConfigurationProperties
 *  org.springframework.boot.jdbc.DatabaseDriver
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.autoconfigure.jdbc;

import com.zaxxer.hikari.HikariDataSource;
import java.sql.SQLException;
import oracle.jdbc.OracleConnection;
import oracle.ucp.jdbc.PoolDataSourceImpl;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

abstract class DataSourceConfiguration {
    DataSourceConfiguration() {
    }

    protected static <T> T createDataSource(DataSourceProperties properties, Class<? extends javax.sql.DataSource> type) {
        return (T)properties.initializeDataSourceBuilder().type(type).build();
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnMissingBean(value={javax.sql.DataSource.class})
    @ConditionalOnProperty(name={"spring.datasource.type"})
    static class Generic {
        Generic() {
        }

        @Bean
        javax.sql.DataSource dataSource(DataSourceProperties properties) {
            return properties.initializeDataSourceBuilder().build();
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={PoolDataSourceImpl.class, OracleConnection.class})
    @ConditionalOnMissingBean(value={javax.sql.DataSource.class})
    @ConditionalOnProperty(name={"spring.datasource.type"}, havingValue="oracle.ucp.jdbc.PoolDataSource", matchIfMissing=true)
    static class OracleUcp {
        OracleUcp() {
        }

        @Bean
        @ConfigurationProperties(prefix="spring.datasource.oracleucp")
        PoolDataSourceImpl dataSource(DataSourceProperties properties) throws SQLException {
            PoolDataSourceImpl dataSource = (PoolDataSourceImpl)DataSourceConfiguration.createDataSource(properties, PoolDataSourceImpl.class);
            dataSource.setValidateConnectionOnBorrow(true);
            if (StringUtils.hasText((String)properties.getName())) {
                dataSource.setConnectionPoolName(properties.getName());
            }
            return dataSource;
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={BasicDataSource.class})
    @ConditionalOnMissingBean(value={javax.sql.DataSource.class})
    @ConditionalOnProperty(name={"spring.datasource.type"}, havingValue="org.apache.commons.dbcp2.BasicDataSource", matchIfMissing=true)
    static class Dbcp2 {
        Dbcp2() {
        }

        @Bean
        @ConfigurationProperties(prefix="spring.datasource.dbcp2")
        BasicDataSource dataSource(DataSourceProperties properties) {
            return (BasicDataSource)DataSourceConfiguration.createDataSource(properties, BasicDataSource.class);
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={HikariDataSource.class})
    @ConditionalOnMissingBean(value={javax.sql.DataSource.class})
    @ConditionalOnProperty(name={"spring.datasource.type"}, havingValue="com.zaxxer.hikari.HikariDataSource", matchIfMissing=true)
    static class Hikari {
        Hikari() {
        }

        @Bean
        @ConfigurationProperties(prefix="spring.datasource.hikari")
        HikariDataSource dataSource(DataSourceProperties properties) {
            HikariDataSource dataSource = (HikariDataSource)DataSourceConfiguration.createDataSource(properties, HikariDataSource.class);
            if (StringUtils.hasText((String)properties.getName())) {
                dataSource.setPoolName(properties.getName());
            }
            return dataSource;
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={DataSource.class})
    @ConditionalOnMissingBean(value={javax.sql.DataSource.class})
    @ConditionalOnProperty(name={"spring.datasource.type"}, havingValue="org.apache.tomcat.jdbc.pool.DataSource", matchIfMissing=true)
    static class Tomcat {
        Tomcat() {
        }

        @Bean
        @ConfigurationProperties(prefix="spring.datasource.tomcat")
        DataSource dataSource(DataSourceProperties properties) {
            DataSource dataSource = (DataSource)DataSourceConfiguration.createDataSource(properties, DataSource.class);
            DatabaseDriver databaseDriver = DatabaseDriver.fromJdbcUrl((String)properties.determineUrl());
            String validationQuery = databaseDriver.getValidationQuery();
            if (validationQuery != null) {
                dataSource.setTestOnBorrow(true);
                dataSource.setValidationQuery(validationQuery);
            }
            return dataSource;
        }
    }
}

