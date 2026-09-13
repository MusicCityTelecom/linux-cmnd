/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.zaxxer.hikari.HikariConfigMXBean
 *  com.zaxxer.hikari.HikariDataSource
 *  oracle.jdbc.OracleConnection
 *  oracle.ucp.jdbc.PoolDataSource
 *  org.apache.commons.dbcp2.BasicDataSource
 *  org.apache.commons.dbcp2.BasicDataSourceMXBean
 *  org.apache.tomcat.jdbc.pool.DataSource
 *  org.apache.tomcat.jdbc.pool.jmx.ConnectionPoolMBean
 *  org.springframework.boot.jdbc.DataSourceUnwrapper
 *  org.springframework.boot.jdbc.metadata.CommonsDbcp2DataSourcePoolMetadata
 *  org.springframework.boot.jdbc.metadata.DataSourcePoolMetadataProvider
 *  org.springframework.boot.jdbc.metadata.HikariDataSourcePoolMetadata
 *  org.springframework.boot.jdbc.metadata.OracleUcpDataSourcePoolMetadata
 *  org.springframework.boot.jdbc.metadata.TomcatDataSourcePoolMetadata
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 */
package org.springframework.boot.autoconfigure.jdbc.metadata;

import com.zaxxer.hikari.HikariConfigMXBean;
import com.zaxxer.hikari.HikariDataSource;
import oracle.jdbc.OracleConnection;
import oracle.ucp.jdbc.PoolDataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceMXBean;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.jmx.ConnectionPoolMBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.jdbc.DataSourceUnwrapper;
import org.springframework.boot.jdbc.metadata.CommonsDbcp2DataSourcePoolMetadata;
import org.springframework.boot.jdbc.metadata.DataSourcePoolMetadataProvider;
import org.springframework.boot.jdbc.metadata.HikariDataSourcePoolMetadata;
import org.springframework.boot.jdbc.metadata.OracleUcpDataSourcePoolMetadata;
import org.springframework.boot.jdbc.metadata.TomcatDataSourcePoolMetadata;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods=false)
public class DataSourcePoolMetadataProvidersConfiguration {

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={PoolDataSource.class, OracleConnection.class})
    static class OracleUcpPoolDataSourceMetadataProviderConfiguration {
        OracleUcpPoolDataSourceMetadataProviderConfiguration() {
        }

        @Bean
        DataSourcePoolMetadataProvider oracleUcpPoolDataSourceMetadataProvider() {
            return dataSource -> {
                PoolDataSource ucpDataSource = (PoolDataSource)DataSourceUnwrapper.unwrap((javax.sql.DataSource)dataSource, PoolDataSource.class);
                if (ucpDataSource != null) {
                    return new OracleUcpDataSourcePoolMetadata(ucpDataSource);
                }
                return null;
            };
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={BasicDataSource.class})
    static class CommonsDbcp2PoolDataSourceMetadataProviderConfiguration {
        CommonsDbcp2PoolDataSourceMetadataProviderConfiguration() {
        }

        @Bean
        DataSourcePoolMetadataProvider commonsDbcp2PoolDataSourceMetadataProvider() {
            return dataSource -> {
                BasicDataSource dbcpDataSource = (BasicDataSource)DataSourceUnwrapper.unwrap((javax.sql.DataSource)dataSource, BasicDataSourceMXBean.class, BasicDataSource.class);
                if (dbcpDataSource != null) {
                    return new CommonsDbcp2DataSourcePoolMetadata(dbcpDataSource);
                }
                return null;
            };
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={HikariDataSource.class})
    static class HikariPoolDataSourceMetadataProviderConfiguration {
        HikariPoolDataSourceMetadataProviderConfiguration() {
        }

        @Bean
        DataSourcePoolMetadataProvider hikariPoolDataSourceMetadataProvider() {
            return dataSource -> {
                HikariDataSource hikariDataSource = (HikariDataSource)DataSourceUnwrapper.unwrap((javax.sql.DataSource)dataSource, HikariConfigMXBean.class, HikariDataSource.class);
                if (hikariDataSource != null) {
                    return new HikariDataSourcePoolMetadata(hikariDataSource);
                }
                return null;
            };
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={DataSource.class})
    static class TomcatDataSourcePoolMetadataProviderConfiguration {
        TomcatDataSourcePoolMetadataProviderConfiguration() {
        }

        @Bean
        DataSourcePoolMetadataProvider tomcatPoolDataSourceMetadataProvider() {
            return dataSource -> {
                DataSource tomcatDataSource = (DataSource)DataSourceUnwrapper.unwrap((javax.sql.DataSource)dataSource, ConnectionPoolMBean.class, DataSource.class);
                if (tomcatDataSource != null) {
                    return new TomcatDataSourcePoolMetadata(tomcatDataSource);
                }
                return null;
            };
        }
    }
}

