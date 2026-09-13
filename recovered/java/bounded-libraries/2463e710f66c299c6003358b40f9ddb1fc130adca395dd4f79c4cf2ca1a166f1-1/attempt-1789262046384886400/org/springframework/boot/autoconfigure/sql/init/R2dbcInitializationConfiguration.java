/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.r2dbc.spi.ConnectionFactory
 *  org.springframework.boot.r2dbc.ConnectionFactoryBuilder
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.r2dbc.connection.init.DatabasePopulator
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.autoconfigure.sql.init;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.sql.init.SqlDataSourceScriptDatabaseInitializer;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationProperties;
import org.springframework.boot.autoconfigure.sql.init.SqlR2dbcScriptDatabaseInitializer;
import org.springframework.boot.r2dbc.ConnectionFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.r2dbc.connection.init.DatabasePopulator;
import org.springframework.util.StringUtils;

@Configuration(proxyBeanMethods=false)
@ConditionalOnClass(value={ConnectionFactory.class, DatabasePopulator.class})
@ConditionalOnSingleCandidate(value=ConnectionFactory.class)
@ConditionalOnMissingBean(value={SqlR2dbcScriptDatabaseInitializer.class, SqlDataSourceScriptDatabaseInitializer.class})
class R2dbcInitializationConfiguration {
    R2dbcInitializationConfiguration() {
    }

    @Bean
    SqlR2dbcScriptDatabaseInitializer r2dbcScriptDatabaseInitializer(ConnectionFactory connectionFactory, SqlInitializationProperties properties) {
        return new SqlR2dbcScriptDatabaseInitializer(R2dbcInitializationConfiguration.determineConnectionFactory(connectionFactory, properties.getUsername(), properties.getPassword()), properties);
    }

    private static ConnectionFactory determineConnectionFactory(ConnectionFactory connectionFactory, String username, String password) {
        if (StringUtils.hasText((String)username) && StringUtils.hasText((String)password)) {
            return ConnectionFactoryBuilder.derivedFrom((ConnectionFactory)connectionFactory).username(username).password((CharSequence)password).build();
        }
        return connectionFactory;
    }
}

