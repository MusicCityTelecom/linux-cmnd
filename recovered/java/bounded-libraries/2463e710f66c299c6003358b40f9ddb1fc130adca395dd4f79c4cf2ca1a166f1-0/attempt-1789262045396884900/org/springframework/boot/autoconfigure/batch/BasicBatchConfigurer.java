/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.batch.core.configuration.annotation.BatchConfigurer
 *  org.springframework.batch.core.explore.JobExplorer
 *  org.springframework.batch.core.explore.support.JobExplorerFactoryBean
 *  org.springframework.batch.core.launch.JobLauncher
 *  org.springframework.batch.core.launch.support.SimpleJobLauncher
 *  org.springframework.batch.core.repository.JobRepository
 *  org.springframework.batch.core.repository.support.JobRepositoryFactoryBean
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.boot.context.properties.PropertyMapper
 *  org.springframework.jdbc.datasource.DataSourceTransactionManager
 *  org.springframework.transaction.PlatformTransactionManager
 */
package org.springframework.boot.autoconfigure.batch;

import javax.sql.DataSource;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.JobExplorerFactoryBean;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

public class BasicBatchConfigurer
implements BatchConfigurer,
InitializingBean {
    private final BatchProperties properties;
    private final DataSource dataSource;
    private PlatformTransactionManager transactionManager;
    private final TransactionManagerCustomizers transactionManagerCustomizers;
    private JobRepository jobRepository;
    private JobLauncher jobLauncher;
    private JobExplorer jobExplorer;

    protected BasicBatchConfigurer(BatchProperties properties, DataSource dataSource, TransactionManagerCustomizers transactionManagerCustomizers) {
        this.properties = properties;
        this.dataSource = dataSource;
        this.transactionManagerCustomizers = transactionManagerCustomizers;
    }

    public JobRepository getJobRepository() {
        return this.jobRepository;
    }

    public PlatformTransactionManager getTransactionManager() {
        return this.transactionManager;
    }

    public JobLauncher getJobLauncher() {
        return this.jobLauncher;
    }

    public JobExplorer getJobExplorer() throws Exception {
        return this.jobExplorer;
    }

    public void afterPropertiesSet() {
        this.initialize();
    }

    public void initialize() {
        try {
            this.transactionManager = this.buildTransactionManager();
            this.jobRepository = this.createJobRepository();
            this.jobLauncher = this.createJobLauncher();
            this.jobExplorer = this.createJobExplorer();
        }
        catch (Exception ex) {
            throw new IllegalStateException("Unable to initialize Spring Batch", ex);
        }
    }

    protected JobExplorer createJobExplorer() throws Exception {
        PropertyMapper map = PropertyMapper.get();
        JobExplorerFactoryBean factory = new JobExplorerFactoryBean();
        factory.setDataSource(this.dataSource);
        map.from(this.properties.getJdbc()::getTablePrefix).whenHasText().to(arg_0 -> ((JobExplorerFactoryBean)factory).setTablePrefix(arg_0));
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    protected JobLauncher createJobLauncher() throws Exception {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(this.getJobRepository());
        jobLauncher.afterPropertiesSet();
        return jobLauncher;
    }

    protected JobRepository createJobRepository() throws Exception {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        PropertyMapper map = PropertyMapper.get();
        map.from((Object)this.dataSource).to(arg_0 -> ((JobRepositoryFactoryBean)factory).setDataSource(arg_0));
        map.from(this::determineIsolationLevel).whenNonNull().to(arg_0 -> ((JobRepositoryFactoryBean)factory).setIsolationLevelForCreate(arg_0));
        map.from(this.properties.getJdbc()::getTablePrefix).whenHasText().to(arg_0 -> ((JobRepositoryFactoryBean)factory).setTablePrefix(arg_0));
        map.from(this::getTransactionManager).to(arg_0 -> ((JobRepositoryFactoryBean)factory).setTransactionManager(arg_0));
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    protected String determineIsolationLevel() {
        BatchProperties.Isolation isolation = this.properties.getJdbc().getIsolationLevelForCreate();
        return isolation != null ? isolation.toIsolationName() : null;
    }

    protected PlatformTransactionManager createTransactionManager() {
        return new DataSourceTransactionManager(this.dataSource);
    }

    private PlatformTransactionManager buildTransactionManager() {
        PlatformTransactionManager transactionManager = this.createTransactionManager();
        if (this.transactionManagerCustomizers != null) {
            this.transactionManagerCustomizers.customize(transactionManager);
        }
        return transactionManager;
    }
}

