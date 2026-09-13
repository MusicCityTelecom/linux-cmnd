/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.batch.core.BatchStatus
 *  org.springframework.batch.core.Job
 *  org.springframework.batch.core.JobExecution
 *  org.springframework.batch.core.JobExecutionException
 *  org.springframework.batch.core.JobParameters
 *  org.springframework.batch.core.JobParametersBuilder
 *  org.springframework.batch.core.JobParametersInvalidException
 *  org.springframework.batch.core.configuration.JobRegistry
 *  org.springframework.batch.core.converter.DefaultJobParametersConverter
 *  org.springframework.batch.core.converter.JobParametersConverter
 *  org.springframework.batch.core.explore.JobExplorer
 *  org.springframework.batch.core.launch.JobLauncher
 *  org.springframework.batch.core.launch.JobParametersNotFoundException
 *  org.springframework.batch.core.launch.NoSuchJobException
 *  org.springframework.batch.core.repository.JobExecutionAlreadyRunningException
 *  org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException
 *  org.springframework.batch.core.repository.JobRepository
 *  org.springframework.batch.core.repository.JobRestartException
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.boot.ApplicationArguments
 *  org.springframework.boot.ApplicationRunner
 *  org.springframework.context.ApplicationEvent
 *  org.springframework.context.ApplicationEventPublisher
 *  org.springframework.context.ApplicationEventPublisherAware
 *  org.springframework.core.Ordered
 *  org.springframework.core.log.LogMessage
 *  org.springframework.util.Assert
 *  org.springframework.util.PatternMatchUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.autoconfigure.batch;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.converter.DefaultJobParametersConverter;
import org.springframework.batch.core.converter.JobParametersConverter;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobParametersNotFoundException;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.batch.JobExecutionEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.core.Ordered;
import org.springframework.core.log.LogMessage;
import org.springframework.util.Assert;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StringUtils;

public class JobLauncherApplicationRunner
implements ApplicationRunner,
Ordered,
ApplicationEventPublisherAware {
    public static final int DEFAULT_ORDER = 0;
    private static final Log logger = LogFactory.getLog(JobLauncherApplicationRunner.class);
    private JobParametersConverter converter = new DefaultJobParametersConverter();
    private final JobLauncher jobLauncher;
    private final JobExplorer jobExplorer;
    private final JobRepository jobRepository;
    private JobRegistry jobRegistry;
    private String jobNames;
    private Collection<Job> jobs = Collections.emptySet();
    private int order = 0;
    private ApplicationEventPublisher publisher;

    public JobLauncherApplicationRunner(JobLauncher jobLauncher, JobExplorer jobExplorer, JobRepository jobRepository) {
        Assert.notNull((Object)jobLauncher, (String)"JobLauncher must not be null");
        Assert.notNull((Object)jobExplorer, (String)"JobExplorer must not be null");
        Assert.notNull((Object)jobRepository, (String)"JobRepository must not be null");
        this.jobLauncher = jobLauncher;
        this.jobExplorer = jobExplorer;
        this.jobRepository = jobRepository;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getOrder() {
        return this.order;
    }

    public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Autowired(required=false)
    public void setJobRegistry(JobRegistry jobRegistry) {
        this.jobRegistry = jobRegistry;
    }

    public void setJobNames(String jobNames) {
        this.jobNames = jobNames;
    }

    @Autowired(required=false)
    public void setJobParametersConverter(JobParametersConverter converter) {
        this.converter = converter;
    }

    @Autowired(required=false)
    public void setJobs(Collection<Job> jobs) {
        this.jobs = jobs;
    }

    public void run(ApplicationArguments args) throws Exception {
        String[] jobArguments = args.getNonOptionArgs().toArray(new String[0]);
        this.run(jobArguments);
    }

    public void run(String ... args) throws JobExecutionException {
        logger.info((Object)("Running default command line with: " + Arrays.asList(args)));
        this.launchJobFromProperties(StringUtils.splitArrayElementsIntoProperties((String[])args, (String)"="));
    }

    protected void launchJobFromProperties(Properties properties) throws JobExecutionException {
        JobParameters jobParameters = this.converter.getJobParameters(properties);
        this.executeLocalJobs(jobParameters);
        this.executeRegisteredJobs(jobParameters);
    }

    private void executeLocalJobs(JobParameters jobParameters) throws JobExecutionException {
        for (Job job : this.jobs) {
            String[] jobsToRun;
            if (StringUtils.hasText((String)this.jobNames) && !PatternMatchUtils.simpleMatch((String[])(jobsToRun = this.jobNames.split(",")), (String)job.getName())) {
                logger.debug((Object)LogMessage.format((String)"Skipped job: %s", (Object)job.getName()));
                continue;
            }
            this.execute(job, jobParameters);
        }
    }

    private void executeRegisteredJobs(JobParameters jobParameters) throws JobExecutionException {
        if (this.jobRegistry != null && StringUtils.hasText((String)this.jobNames)) {
            String[] jobsToRun;
            for (String jobName : jobsToRun = this.jobNames.split(",")) {
                try {
                    Job job = this.jobRegistry.getJob(jobName);
                    if (this.jobs.contains(job)) continue;
                    this.execute(job, jobParameters);
                }
                catch (NoSuchJobException ex) {
                    logger.debug((Object)LogMessage.format((String)"No job found in registry for job name: %s", (Object)jobName));
                }
            }
        }
    }

    protected void execute(Job job, JobParameters jobParameters) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException, JobParametersNotFoundException {
        JobParameters parameters = this.getNextJobParameters(job, jobParameters);
        JobExecution execution = this.jobLauncher.run(job, parameters);
        if (this.publisher != null) {
            this.publisher.publishEvent((ApplicationEvent)new JobExecutionEvent(execution));
        }
    }

    private JobParameters getNextJobParameters(Job job, JobParameters jobParameters) {
        if (this.jobRepository != null && this.jobRepository.isJobInstanceExists(job.getName(), jobParameters)) {
            return this.getNextJobParametersForExisting(job, jobParameters);
        }
        if (job.getJobParametersIncrementer() == null) {
            return jobParameters;
        }
        JobParameters nextParameters = new JobParametersBuilder(jobParameters, this.jobExplorer).getNextJobParameters(job).toJobParameters();
        return this.merge(nextParameters, jobParameters);
    }

    private JobParameters getNextJobParametersForExisting(Job job, JobParameters jobParameters) {
        JobExecution lastExecution = this.jobRepository.getLastJobExecution(job.getName(), jobParameters);
        if (this.isStoppedOrFailed(lastExecution) && job.isRestartable()) {
            JobParameters previousIdentifyingParameters = this.getGetIdentifying(lastExecution.getJobParameters());
            return this.merge(previousIdentifyingParameters, jobParameters);
        }
        return jobParameters;
    }

    private boolean isStoppedOrFailed(JobExecution execution) {
        BatchStatus status = execution != null ? execution.getStatus() : null;
        return status == BatchStatus.STOPPED || status == BatchStatus.FAILED;
    }

    private JobParameters getGetIdentifying(JobParameters parameters) {
        LinkedHashMap nonIdentifying = new LinkedHashMap(parameters.getParameters().size());
        parameters.getParameters().forEach((key, value) -> {
            if (value.isIdentifying()) {
                nonIdentifying.put(key, value);
            }
        });
        return new JobParameters(nonIdentifying);
    }

    private JobParameters merge(JobParameters parameters, JobParameters additionals) {
        LinkedHashMap merged = new LinkedHashMap();
        merged.putAll(parameters.getParameters());
        merged.putAll(additionals.getParameters());
        return new JobParameters(merged);
    }
}

