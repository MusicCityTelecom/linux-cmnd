/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.zaxxer.hikari.HikariConfigMXBean
 *  com.zaxxer.hikari.HikariDataSource
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.apache.tomcat.jdbc.pool.DataSourceProxy
 *  org.apache.tomcat.jdbc.pool.PoolConfiguration
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.boot.jdbc.DataSourceUnwrapper
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.jmx.export.MBeanExporter
 */
package org.springframework.boot.autoconfigure.jdbc;

import com.zaxxer.hikari.HikariConfigMXBean;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.jdbc.pool.DataSourceProxy;
import org.apache.tomcat.jdbc.pool.PoolConfiguration;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.jdbc.DataSourceUnwrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.export.MBeanExporter;

@Configuration(proxyBeanMethods=false)
@ConditionalOnProperty(prefix="spring.jmx", name={"enabled"}, havingValue="true", matchIfMissing=true)
class DataSourceJmxConfiguration {
    private static final Log logger = LogFactory.getLog(DataSourceJmxConfiguration.class);

    DataSourceJmxConfiguration() {
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnProperty(prefix="spring.datasource.tomcat", name={"jmx-enabled"})
    @ConditionalOnClass(value={DataSourceProxy.class})
    @ConditionalOnSingleCandidate(value=DataSource.class)
    static class TomcatDataSourceJmxConfiguration {
        TomcatDataSourceJmxConfiguration() {
        }

        @Bean
        @ConditionalOnMissingBean(name={"dataSourceMBean"})
        Object dataSourceMBean(DataSource dataSource) {
            DataSourceProxy dataSourceProxy = (DataSourceProxy)DataSourceUnwrapper.unwrap((DataSource)dataSource, PoolConfiguration.class, DataSourceProxy.class);
            if (dataSourceProxy != null) {
                try {
                    return dataSourceProxy.createPool().getJmxPool();
                }
                catch (SQLException ex) {
                    logger.warn((Object)"Cannot expose DataSource to JMX (could not connect)");
                }
            }
            return null;
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={HikariDataSource.class})
    @ConditionalOnSingleCandidate(value=DataSource.class)
    static class Hikari {
        private final DataSource dataSource;
        private final ObjectProvider<MBeanExporter> mBeanExporter;

        Hikari(DataSource dataSource, ObjectProvider<MBeanExporter> mBeanExporter) {
            this.dataSource = dataSource;
            this.mBeanExporter = mBeanExporter;
            this.validateMBeans();
        }

        private void validateMBeans() {
            HikariDataSource hikariDataSource = (HikariDataSource)DataSourceUnwrapper.unwrap((DataSource)this.dataSource, HikariConfigMXBean.class, HikariDataSource.class);
            if (hikariDataSource != null && hikariDataSource.isRegisterMbeans()) {
                this.mBeanExporter.ifUnique(exporter -> exporter.addExcludedBean("dataSource"));
            }
        }
    }
}

