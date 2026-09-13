/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.quartz.Calendar
 *  org.quartz.JobDetail
 *  org.quartz.Scheduler
 *  org.quartz.Trigger
 *  org.quartz.spi.JobFactory
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.boot.sql.init.dependency.DatabaseInitializationDependencyConfigurer
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Conditional
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.annotation.Import
 *  org.springframework.core.annotation.Order
 *  org.springframework.scheduling.quartz.SchedulerFactoryBean
 *  org.springframework.scheduling.quartz.SpringBeanJobFactory
 *  org.springframework.transaction.PlatformTransactionManager
 */
package org.springframework.boot.autoconfigure.quartz;

import java.util.Map;
import java.util.Properties;
import javax.sql.DataSource;
import org.quartz.Calendar;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSourceInitializer;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSourceScriptDatabaseInitializer;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.boot.autoconfigure.quartz.QuartzTransactionManager;
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.boot.autoconfigure.sql.init.OnDatabaseInitializationCondition;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.sql.init.dependency.DatabaseInitializationDependencyConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.transaction.PlatformTransactionManager;

@AutoConfiguration(after={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class, LiquibaseAutoConfiguration.class, FlywayAutoConfiguration.class})
@ConditionalOnClass(value={Scheduler.class, SchedulerFactoryBean.class, PlatformTransactionManager.class})
@EnableConfigurationProperties(value={QuartzProperties.class})
public class QuartzAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public SchedulerFactoryBean quartzScheduler(QuartzProperties properties, ObjectProvider<SchedulerFactoryBeanCustomizer> customizers, ObjectProvider<JobDetail> jobDetails, Map<String, Calendar> calendars, ObjectProvider<Trigger> triggers, ApplicationContext applicationContext) {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        SpringBeanJobFactory jobFactory = new SpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        schedulerFactoryBean.setJobFactory((JobFactory)jobFactory);
        if (properties.getSchedulerName() != null) {
            schedulerFactoryBean.setSchedulerName(properties.getSchedulerName());
        }
        schedulerFactoryBean.setAutoStartup(properties.isAutoStartup());
        schedulerFactoryBean.setStartupDelay((int)properties.getStartupDelay().getSeconds());
        schedulerFactoryBean.setWaitForJobsToCompleteOnShutdown(properties.isWaitForJobsToCompleteOnShutdown());
        schedulerFactoryBean.setOverwriteExistingJobs(properties.isOverwriteExistingJobs());
        if (!properties.getProperties().isEmpty()) {
            schedulerFactoryBean.setQuartzProperties(this.asProperties(properties.getProperties()));
        }
        schedulerFactoryBean.setJobDetails((JobDetail[])jobDetails.orderedStream().toArray(JobDetail[]::new));
        schedulerFactoryBean.setCalendars(calendars);
        schedulerFactoryBean.setTriggers((Trigger[])triggers.orderedStream().toArray(Trigger[]::new));
        customizers.orderedStream().forEach(customizer -> customizer.customize(schedulerFactoryBean));
        return schedulerFactoryBean;
    }

    private Properties asProperties(Map<String, String> source) {
        Properties properties = new Properties();
        properties.putAll(source);
        return properties;
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnSingleCandidate(value=DataSource.class)
    @ConditionalOnProperty(prefix="spring.quartz", name={"job-store-type"}, havingValue="jdbc")
    @Import(value={DatabaseInitializationDependencyConfigurer.class})
    protected static class JdbcStoreTypeConfiguration {
        protected JdbcStoreTypeConfiguration() {
        }

        @Bean
        @Order(value=0)
        public SchedulerFactoryBeanCustomizer dataSourceCustomizer(QuartzProperties properties, DataSource dataSource, @QuartzDataSource ObjectProvider<DataSource> quartzDataSource, ObjectProvider<PlatformTransactionManager> transactionManager, @QuartzTransactionManager ObjectProvider<PlatformTransactionManager> quartzTransactionManager) {
            return schedulerFactoryBean -> {
                DataSource dataSourceToUse = this.getDataSource(dataSource, quartzDataSource);
                schedulerFactoryBean.setDataSource(dataSourceToUse);
                PlatformTransactionManager txManager = this.getTransactionManager(transactionManager, quartzTransactionManager);
                if (txManager != null) {
                    schedulerFactoryBean.setTransactionManager(txManager);
                }
            };
        }

        private DataSource getDataSource(DataSource dataSource, ObjectProvider<DataSource> quartzDataSource) {
            DataSource dataSourceIfAvailable = (DataSource)quartzDataSource.getIfAvailable();
            return dataSourceIfAvailable != null ? dataSourceIfAvailable : dataSource;
        }

        private PlatformTransactionManager getTransactionManager(ObjectProvider<PlatformTransactionManager> transactionManager, ObjectProvider<PlatformTransactionManager> quartzTransactionManager) {
            PlatformTransactionManager transactionManagerIfAvailable = (PlatformTransactionManager)quartzTransactionManager.getIfAvailable();
            return transactionManagerIfAvailable != null ? transactionManagerIfAvailable : (PlatformTransactionManager)transactionManager.getIfUnique();
        }

        @Bean
        @ConditionalOnMissingBean(value={QuartzDataSourceScriptDatabaseInitializer.class, QuartzDataSourceInitializer.class})
        @Conditional(value={OnQuartzDatasourceInitializationCondition.class})
        public QuartzDataSourceScriptDatabaseInitializer quartzDataSourceScriptDatabaseInitializer(DataSource dataSource, @QuartzDataSource ObjectProvider<DataSource> quartzDataSource, QuartzProperties properties) {
            DataSource dataSourceToUse = this.getDataSource(dataSource, quartzDataSource);
            return new QuartzDataSourceScriptDatabaseInitializer(dataSourceToUse, properties);
        }

        static class OnQuartzDatasourceInitializationCondition
        extends OnDatabaseInitializationCondition {
            OnQuartzDatasourceInitializationCondition() {
                super("Quartz", "spring.quartz.jdbc.initialize-schema");
            }
        }
    }
}

