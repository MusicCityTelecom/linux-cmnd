/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.batch.core.configuration.ListableJobLocator
 *  org.springframework.batch.core.converter.JobParametersConverter
 *  org.springframework.batch.core.explore.JobExplorer
 *  org.springframework.batch.core.launch.JobLauncher
 *  org.springframework.batch.core.launch.JobOperator
 *  org.springframework.batch.core.launch.support.SimpleJobOperator
 *  org.springframework.batch.core.repository.JobRepository
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.boot.ExitCodeGenerator
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.boot.sql.init.dependency.DatabaseInitializationDependencyConfigurer
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Conditional
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.annotation.Import
 *  org.springframework.jdbc.datasource.init.DatabasePopulator
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.autoconfigure.batch;

import javax.sql.DataSource;
import org.springframework.batch.core.configuration.ListableJobLocator;
import org.springframework.batch.core.converter.JobParametersConverter;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.support.SimpleJobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.batch.BatchConfigurerConfiguration;
import org.springframework.boot.autoconfigure.batch.BatchDataSource;
import org.springframework.boot.autoconfigure.batch.BatchDataSourceInitializer;
import org.springframework.boot.autoconfigure.batch.BatchDataSourceScriptDatabaseInitializer;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.boot.autoconfigure.batch.JobExecutionExitCodeGenerator;
import org.springframework.boot.autoconfigure.batch.JobLauncherApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.sql.init.OnDatabaseInitializationCondition;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.sql.init.dependency.DatabaseInitializationDependencyConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.util.StringUtils;

@AutoConfiguration(after={HibernateJpaAutoConfiguration.class})
@ConditionalOnClass(value={JobLauncher.class, DataSource.class})
@ConditionalOnBean(value={JobLauncher.class})
@EnableConfigurationProperties(value={BatchProperties.class})
@Import(value={BatchConfigurerConfiguration.class, DatabaseInitializationDependencyConfigurer.class})
public class BatchAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix="spring.batch.job", name={"enabled"}, havingValue="true", matchIfMissing=true)
    public JobLauncherApplicationRunner jobLauncherApplicationRunner(JobLauncher jobLauncher, JobExplorer jobExplorer, JobRepository jobRepository, BatchProperties properties) {
        JobLauncherApplicationRunner runner = new JobLauncherApplicationRunner(jobLauncher, jobExplorer, jobRepository);
        String jobNames = properties.getJob().getNames();
        if (StringUtils.hasText((String)jobNames)) {
            runner.setJobNames(jobNames);
        }
        return runner;
    }

    @Bean
    @ConditionalOnMissingBean(value={ExitCodeGenerator.class})
    public JobExecutionExitCodeGenerator jobExecutionExitCodeGenerator() {
        return new JobExecutionExitCodeGenerator();
    }

    @Bean
    @ConditionalOnMissingBean(value={JobOperator.class})
    public SimpleJobOperator jobOperator(ObjectProvider<JobParametersConverter> jobParametersConverter, JobExplorer jobExplorer, JobLauncher jobLauncher, ListableJobLocator jobRegistry, JobRepository jobRepository) throws Exception {
        SimpleJobOperator factory = new SimpleJobOperator();
        factory.setJobExplorer(jobExplorer);
        factory.setJobLauncher(jobLauncher);
        factory.setJobRegistry(jobRegistry);
        factory.setJobRepository(jobRepository);
        jobParametersConverter.ifAvailable(arg_0 -> ((SimpleJobOperator)factory).setJobParametersConverter(arg_0));
        return factory;
    }

    static class OnBatchDatasourceInitializationCondition
    extends OnDatabaseInitializationCondition {
        OnBatchDatasourceInitializationCondition() {
            super("Batch", "spring.batch.jdbc.initialize-schema", "spring.batch.initialize-schema");
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnBean(value={DataSource.class})
    @ConditionalOnClass(value={DatabasePopulator.class})
    @Conditional(value={OnBatchDatasourceInitializationCondition.class})
    static class DataSourceInitializerConfiguration {
        DataSourceInitializerConfiguration() {
        }

        @Bean
        @ConditionalOnMissingBean(value={BatchDataSourceScriptDatabaseInitializer.class, BatchDataSourceInitializer.class})
        BatchDataSourceScriptDatabaseInitializer batchDataSourceInitializer(DataSource dataSource, @BatchDataSource ObjectProvider<DataSource> batchDataSource, BatchProperties properties) {
            return new BatchDataSourceScriptDatabaseInitializer((DataSource)batchDataSource.getIfAvailable(() -> dataSource), properties.getJdbc());
        }
    }
}

