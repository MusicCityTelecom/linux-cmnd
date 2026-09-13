/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.annotation.Bean
 *  org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
 *  org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup
 *  org.springframework.jmx.export.MBeanExporter
 *  org.springframework.jmx.support.JmxUtils
 */
package org.springframework.boot.autoconfigure.jdbc;

import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.jdbc.XADataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.jmx.export.MBeanExporter;
import org.springframework.jmx.support.JmxUtils;

@AutoConfiguration(before={XADataSourceAutoConfiguration.class, DataSourceAutoConfiguration.class})
@ConditionalOnClass(value={DataSource.class, EmbeddedDatabaseType.class})
@ConditionalOnProperty(prefix="spring.datasource", name={"jndi-name"})
@EnableConfigurationProperties(value={DataSourceProperties.class})
public class JndiDataSourceAutoConfiguration {
    @Bean(destroyMethod="")
    @ConditionalOnMissingBean
    public DataSource dataSource(DataSourceProperties properties, ApplicationContext context) {
        JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
        DataSource dataSource = dataSourceLookup.getDataSource(properties.getJndiName());
        this.excludeMBeanIfNecessary(dataSource, "dataSource", context);
        return dataSource;
    }

    private void excludeMBeanIfNecessary(Object candidate, String beanName, ApplicationContext context) {
        for (MBeanExporter mbeanExporter : context.getBeansOfType(MBeanExporter.class).values()) {
            if (!JmxUtils.isMBean(candidate.getClass())) continue;
            mbeanExporter.addExcludedBean(beanName);
        }
    }
}

