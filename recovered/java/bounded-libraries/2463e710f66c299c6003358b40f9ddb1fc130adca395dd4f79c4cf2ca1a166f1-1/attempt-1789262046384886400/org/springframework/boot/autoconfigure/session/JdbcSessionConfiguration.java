/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.boot.sql.init.dependency.DatabaseInitializationDependencyConfigurer
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Conditional
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.annotation.Import
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.session.SessionRepository
 *  org.springframework.session.jdbc.JdbcIndexedSessionRepository
 *  org.springframework.session.jdbc.config.annotation.SpringSessionDataSource
 *  org.springframework.session.jdbc.config.annotation.web.http.JdbcHttpSessionConfiguration
 */
package org.springframework.boot.autoconfigure.session;

import java.time.Duration;
import javax.sql.DataSource;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.session.JdbcSessionDataSourceInitializer;
import org.springframework.boot.autoconfigure.session.JdbcSessionDataSourceScriptDatabaseInitializer;
import org.springframework.boot.autoconfigure.session.JdbcSessionProperties;
import org.springframework.boot.autoconfigure.session.ServletSessionCondition;
import org.springframework.boot.autoconfigure.session.SessionProperties;
import org.springframework.boot.autoconfigure.sql.init.OnDatabaseInitializationCondition;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.sql.init.dependency.DatabaseInitializationDependencyConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.session.SessionRepository;
import org.springframework.session.jdbc.JdbcIndexedSessionRepository;
import org.springframework.session.jdbc.config.annotation.SpringSessionDataSource;
import org.springframework.session.jdbc.config.annotation.web.http.JdbcHttpSessionConfiguration;

@Configuration(proxyBeanMethods=false)
@ConditionalOnClass(value={JdbcTemplate.class, JdbcIndexedSessionRepository.class})
@ConditionalOnMissingBean(value={SessionRepository.class})
@ConditionalOnBean(value={DataSource.class})
@Conditional(value={ServletSessionCondition.class})
@EnableConfigurationProperties(value={JdbcSessionProperties.class})
@Import(value={DatabaseInitializationDependencyConfigurer.class})
class JdbcSessionConfiguration {
    JdbcSessionConfiguration() {
    }

    @Bean
    @ConditionalOnMissingBean(value={JdbcSessionDataSourceScriptDatabaseInitializer.class, JdbcSessionDataSourceInitializer.class})
    @Conditional(value={OnJdbcSessionDatasourceInitializationCondition.class})
    JdbcSessionDataSourceScriptDatabaseInitializer jdbcSessionDataSourceScriptDatabaseInitializer(@SpringSessionDataSource ObjectProvider<DataSource> sessionDataSource, ObjectProvider<DataSource> dataSource, JdbcSessionProperties properties) {
        DataSource dataSourceToInitialize = (DataSource)sessionDataSource.getIfAvailable(() -> dataSource.getObject());
        return new JdbcSessionDataSourceScriptDatabaseInitializer(dataSourceToInitialize, properties);
    }

    static class OnJdbcSessionDatasourceInitializationCondition
    extends OnDatabaseInitializationCondition {
        OnJdbcSessionDatasourceInitializationCondition() {
            super("Jdbc Session", "spring.session.jdbc.initialize-schema");
        }
    }

    @Configuration(proxyBeanMethods=false)
    static class SpringBootJdbcHttpSessionConfiguration
    extends JdbcHttpSessionConfiguration {
        SpringBootJdbcHttpSessionConfiguration() {
        }

        @Autowired
        void customize(SessionProperties sessionProperties, JdbcSessionProperties jdbcSessionProperties, ServerProperties serverProperties) {
            Duration timeout = sessionProperties.determineTimeout(() -> serverProperties.getServlet().getSession().getTimeout());
            if (timeout != null) {
                this.setMaxInactiveIntervalInSeconds((int)timeout.getSeconds());
            }
            this.setTableName(jdbcSessionProperties.getTableName());
            this.setCleanupCron(jdbcSessionProperties.getCleanupCron());
            this.setFlushMode(jdbcSessionProperties.getFlushMode());
            this.setSaveMode(jdbcSessionProperties.getSaveMode());
        }
    }
}

