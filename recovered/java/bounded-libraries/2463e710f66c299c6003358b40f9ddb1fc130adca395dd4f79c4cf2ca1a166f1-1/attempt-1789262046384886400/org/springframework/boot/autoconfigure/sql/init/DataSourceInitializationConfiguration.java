/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.jdbc.DataSourceBuilder
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.jdbc.datasource.SimpleDriverDataSource
 *  org.springframework.jdbc.datasource.init.DatabasePopulator
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.autoconfigure.sql.init;

import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.sql.init.SqlDataSourceScriptDatabaseInitializer;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationProperties;
import org.springframework.boot.autoconfigure.sql.init.SqlR2dbcScriptDatabaseInitializer;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.util.StringUtils;

@Configuration(proxyBeanMethods=false)
@ConditionalOnMissingBean(value={SqlDataSourceScriptDatabaseInitializer.class, SqlR2dbcScriptDatabaseInitializer.class})
@ConditionalOnSingleCandidate(value=DataSource.class)
@ConditionalOnClass(value={DatabasePopulator.class})
class DataSourceInitializationConfiguration {
    DataSourceInitializationConfiguration() {
    }

    @Bean
    SqlDataSourceScriptDatabaseInitializer dataSourceScriptDatabaseInitializer(DataSource dataSource, SqlInitializationProperties properties) {
        return new SqlDataSourceScriptDatabaseInitializer(DataSourceInitializationConfiguration.determineDataSource(dataSource, properties.getUsername(), properties.getPassword()), properties);
    }

    private static DataSource determineDataSource(DataSource dataSource, String username, String password) {
        if (StringUtils.hasText((String)username) && StringUtils.hasText((String)password)) {
            return DataSourceBuilder.derivedFrom((DataSource)dataSource).username(username).password(password).type(SimpleDriverDataSource.class).build();
        }
        return dataSource;
    }
}

