/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  liquibase.change.DatabaseChange
 *  liquibase.integration.spring.SpringLiquibase
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.boot.jdbc.DataSourceBuilder
 *  org.springframework.boot.sql.init.dependency.DatabaseInitializationDependencyConfigurer
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Conditional
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.annotation.ConfigurationCondition$ConfigurationPhase
 *  org.springframework.context.annotation.Import
 *  org.springframework.jdbc.core.ConnectionCallback
 *  org.springframework.jdbc.datasource.SimpleDriverDataSource
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.autoconfigure.liquibase;

import javax.sql.DataSource;
import liquibase.change.DatabaseChange;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.AnyNestedCondition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.DataSourceClosingSpringLiquibase;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseDataSource;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseSchemaManagementProvider;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.sql.init.dependency.DatabaseInitializationDependencyConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ConfigurationCondition;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@AutoConfiguration(after={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@ConditionalOnClass(value={SpringLiquibase.class, DatabaseChange.class})
@ConditionalOnProperty(prefix="spring.liquibase", name={"enabled"}, matchIfMissing=true)
@Conditional(value={LiquibaseDataSourceCondition.class})
@Import(value={DatabaseInitializationDependencyConfigurer.class})
public class LiquibaseAutoConfiguration {
    @Bean
    public LiquibaseSchemaManagementProvider liquibaseDefaultDdlModeProvider(ObjectProvider<SpringLiquibase> liquibases) {
        return new LiquibaseSchemaManagementProvider(liquibases);
    }

    static final class LiquibaseDataSourceCondition
    extends AnyNestedCondition {
        LiquibaseDataSourceCondition() {
            super(ConfigurationCondition.ConfigurationPhase.REGISTER_BEAN);
        }

        @ConditionalOnProperty(prefix="spring.liquibase", name={"url"})
        private static final class LiquibaseUrlCondition {
            private LiquibaseUrlCondition() {
            }
        }

        @ConditionalOnBean(value={DataSource.class})
        private static final class DataSourceBeanCondition {
            private DataSourceBeanCondition() {
            }
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={ConnectionCallback.class})
    @ConditionalOnMissingBean(value={SpringLiquibase.class})
    @EnableConfigurationProperties(value={LiquibaseProperties.class})
    public static class LiquibaseConfiguration {
        private final LiquibaseProperties properties;

        public LiquibaseConfiguration(LiquibaseProperties properties) {
            this.properties = properties;
        }

        @Bean
        public SpringLiquibase liquibase(ObjectProvider<DataSource> dataSource, @LiquibaseDataSource ObjectProvider<DataSource> liquibaseDataSource) {
            SpringLiquibase liquibase = this.createSpringLiquibase((DataSource)liquibaseDataSource.getIfAvailable(), (DataSource)dataSource.getIfUnique());
            liquibase.setChangeLog(this.properties.getChangeLog());
            liquibase.setClearCheckSums(this.properties.isClearChecksums());
            liquibase.setContexts(this.properties.getContexts());
            liquibase.setDefaultSchema(this.properties.getDefaultSchema());
            liquibase.setLiquibaseSchema(this.properties.getLiquibaseSchema());
            liquibase.setLiquibaseTablespace(this.properties.getLiquibaseTablespace());
            liquibase.setDatabaseChangeLogTable(this.properties.getDatabaseChangeLogTable());
            liquibase.setDatabaseChangeLogLockTable(this.properties.getDatabaseChangeLogLockTable());
            liquibase.setDropFirst(this.properties.isDropFirst());
            liquibase.setShouldRun(this.properties.isEnabled());
            liquibase.setLabels(this.properties.getLabels());
            liquibase.setChangeLogParameters(this.properties.getParameters());
            liquibase.setRollbackFile(this.properties.getRollbackFile());
            liquibase.setTestRollbackOnUpdate(this.properties.isTestRollbackOnUpdate());
            liquibase.setTag(this.properties.getTag());
            return liquibase;
        }

        private SpringLiquibase createSpringLiquibase(DataSource liquibaseDataSource, DataSource dataSource) {
            LiquibaseProperties properties = this.properties;
            DataSource migrationDataSource = this.getMigrationDataSource(liquibaseDataSource, dataSource, properties);
            SpringLiquibase liquibase = migrationDataSource == liquibaseDataSource || migrationDataSource == dataSource ? new SpringLiquibase() : new DataSourceClosingSpringLiquibase();
            liquibase.setDataSource(migrationDataSource);
            return liquibase;
        }

        private DataSource getMigrationDataSource(DataSource liquibaseDataSource, DataSource dataSource, LiquibaseProperties properties) {
            if (liquibaseDataSource != null) {
                return liquibaseDataSource;
            }
            if (properties.getUrl() != null) {
                DataSourceBuilder builder = DataSourceBuilder.create().type(SimpleDriverDataSource.class);
                builder.url(properties.getUrl());
                this.applyCommonBuilderProperties(properties, builder);
                return builder.build();
            }
            if (properties.getUser() != null && dataSource != null) {
                DataSourceBuilder builder = DataSourceBuilder.derivedFrom((DataSource)dataSource).type(SimpleDriverDataSource.class);
                this.applyCommonBuilderProperties(properties, builder);
                return builder.build();
            }
            Assert.state((dataSource != null ? 1 : 0) != 0, (String)"Liquibase migration DataSource missing");
            return dataSource;
        }

        private void applyCommonBuilderProperties(LiquibaseProperties properties, DataSourceBuilder<?> builder) {
            builder.username(properties.getUser());
            builder.password(properties.getPassword());
            if (StringUtils.hasText((String)properties.getDriverClassName())) {
                builder.driverClassName(properties.getDriverClassName());
            }
        }
    }
}

