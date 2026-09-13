/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanClassLoaderAware
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.boot.jdbc.EmbeddedDatabaseConnection
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.jdbc.datasource.embedded.EmbeddedDatabase
 *  org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
 */
package org.springframework.boot.autoconfigure.jdbc;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

@Configuration(proxyBeanMethods=false)
@EnableConfigurationProperties(value={DataSourceProperties.class})
public class EmbeddedDataSourceConfiguration
implements BeanClassLoaderAware {
    private ClassLoader classLoader;

    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Bean(destroyMethod="shutdown")
    public EmbeddedDatabase dataSource(DataSourceProperties properties) {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseConnection.get((ClassLoader)this.classLoader).getType()).setName(properties.determineDatabaseName()).build();
    }
}

