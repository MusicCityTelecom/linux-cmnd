/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.transaction.UserTransaction
 *  org.quartz.jobs.FileScanJob
 *  org.quartz.jobs.FileScanListener
 */
package org.quartz.plugins.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import javax.transaction.UserTransaction;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.jobs.FileScanJob;
import org.quartz.jobs.FileScanListener;
import org.quartz.plugins.SchedulerPluginWithUserTransactionSupport;
import org.quartz.spi.ClassLoadHelper;
import org.quartz.xml.XMLSchedulingDataProcessor;

public class XMLSchedulingDataProcessorPlugin
extends SchedulerPluginWithUserTransactionSupport
implements FileScanListener {
    private static final int MAX_JOB_TRIGGER_NAME_LEN = 80;
    private static final String JOB_INITIALIZATION_PLUGIN_NAME = "JobSchedulingDataLoaderPlugin";
    private static final String FILE_NAME_DELIMITERS = ",";
    private boolean failOnFileNotFound = true;
    private String fileNames = "quartz_data.xml";
    private Map<String, JobFile> jobFiles = new LinkedHashMap<String, JobFile>();
    private long scanInterval = 0L;
    boolean started = false;
    protected ClassLoadHelper classLoadHelper = null;
    private Set<String> jobTriggerNameSet = new HashSet<String>();

    public String getFileNames() {
        return this.fileNames;
    }

    public void setFileNames(String fileNames) {
        this.fileNames = fileNames;
    }

    public long getScanInterval() {
        return this.scanInterval / 1000L;
    }

    public void setScanInterval(long scanInterval) {
        this.scanInterval = scanInterval * 1000L;
    }

    public boolean isFailOnFileNotFound() {
        return this.failOnFileNotFound;
    }

    public void setFailOnFileNotFound(boolean failOnFileNotFound) {
        this.failOnFileNotFound = failOnFileNotFound;
    }

    @Override
    public void initialize(String name, Scheduler scheduler, ClassLoadHelper schedulerFactoryClassLoadHelper) throws SchedulerException {
        super.initialize(name, scheduler);
        this.classLoadHelper = schedulerFactoryClassLoadHelper;
        this.getLog().info("Registering Quartz Job Initialization Plug-in.");
        StringTokenizer stok = new StringTokenizer(this.fileNames, FILE_NAME_DELIMITERS);
        while (stok.hasMoreTokens()) {
            String fileName = stok.nextToken();
            JobFile jobFile = new JobFile(fileName);
            this.jobFiles.put(fileName, jobFile);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void start(UserTransaction userTransaction) {
        try {
            if (!this.jobFiles.isEmpty()) {
                if (this.scanInterval > 0L) {
                    this.getScheduler().getContext().put("JobSchedulingDataLoaderPlugin_" + this.getName(), (Object)this);
                }
                for (JobFile jobFile : this.jobFiles.values()) {
                    if (this.scanInterval > 0L) {
                        String jobTriggerName = this.buildJobTriggerName(jobFile.getFileBasename());
                        TriggerKey tKey = new TriggerKey(jobTriggerName, JOB_INITIALIZATION_PLUGIN_NAME);
                        this.getScheduler().unscheduleJob(tKey);
                        JobDetail job = JobBuilder.newJob().withIdentity(jobTriggerName, JOB_INITIALIZATION_PLUGIN_NAME).ofType(FileScanJob.class).usingJobData("FILE_NAME", jobFile.getFileName()).usingJobData("FILE_SCAN_LISTENER_NAME", "JobSchedulingDataLoaderPlugin_" + this.getName()).build();
                        SimpleTrigger trig = TriggerBuilder.newTrigger().withIdentity(tKey).withSchedule(SimpleScheduleBuilder.simpleSchedule().repeatForever().withIntervalInMilliseconds(this.scanInterval)).forJob(job).build();
                        this.getScheduler().scheduleJob(job, trig);
                        this.getLog().debug("Scheduled file scan job for data file: {}, at interval: {}", (Object)jobFile.getFileName(), (Object)this.scanInterval);
                    }
                    this.processFile(jobFile);
                }
            }
        }
        catch (SchedulerException se) {
            this.getLog().error("Error starting background-task for watching jobs file.", (Throwable)se);
        }
        finally {
            this.started = true;
        }
    }

    private String buildJobTriggerName(String fileBasename) {
        String jobTriggerName = "JobSchedulingDataLoaderPlugin_" + this.getName() + '_' + fileBasename.replace('.', '_');
        if (jobTriggerName.length() > 80) {
            jobTriggerName = jobTriggerName.substring(0, 80);
        }
        int currentIndex = 1;
        while (!this.jobTriggerNameSet.add(jobTriggerName)) {
            if (currentIndex > 1) {
                jobTriggerName = jobTriggerName.substring(0, jobTriggerName.lastIndexOf(95));
            }
            String numericSuffix = "_" + currentIndex++;
            if (jobTriggerName.length() > 80 - numericSuffix.length()) {
                jobTriggerName = jobTriggerName.substring(0, 80 - numericSuffix.length());
            }
            jobTriggerName = jobTriggerName + numericSuffix;
        }
        return jobTriggerName;
    }

    @Override
    public void shutdown() {
    }

    private void processFile(JobFile jobFile) {
        if (jobFile == null || !jobFile.getFileFound()) {
            return;
        }
        try {
            XMLSchedulingDataProcessor processor = new XMLSchedulingDataProcessor(this.classLoadHelper);
            processor.addJobGroupToNeverDelete(JOB_INITIALIZATION_PLUGIN_NAME);
            processor.addTriggerGroupToNeverDelete(JOB_INITIALIZATION_PLUGIN_NAME);
            processor.processFileAndScheduleJobs(jobFile.getFileName(), jobFile.getFileName(), this.getScheduler());
        }
        catch (Exception e) {
            this.getLog().error("Error scheduling jobs: " + e.getMessage(), (Throwable)e);
        }
    }

    public void processFile(String filePath) {
        this.processFile(this.jobFiles.get(filePath));
    }

    public void fileUpdated(String fileName) {
        if (this.started) {
            this.processFile(fileName);
        }
    }

    class JobFile {
        private String fileName;
        private String filePath;
        private String fileBasename;
        private boolean fileFound;

        protected JobFile(String fileName) throws SchedulerException {
            this.fileName = fileName;
            this.initialize();
        }

        protected String getFileName() {
            return this.fileName;
        }

        protected boolean getFileFound() {
            return this.fileFound;
        }

        protected String getFilePath() {
            return this.filePath;
        }

        protected String getFileBasename() {
            return this.fileBasename;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void initialize() throws SchedulerException {
            InputStream f = null;
            try {
                String furl = null;
                File file = new File(this.getFileName());
                if (!file.exists()) {
                    URL url = XMLSchedulingDataProcessorPlugin.this.classLoadHelper.getResource(this.getFileName());
                    if (url != null) {
                        try {
                            furl = URLDecoder.decode(url.getPath(), "UTF-8");
                        }
                        catch (UnsupportedEncodingException e) {
                            furl = url.getPath();
                        }
                        file = new File(furl);
                        try {
                            f = url.openStream();
                        }
                        catch (IOException iOException) {}
                    }
                } else {
                    try {
                        f = new FileInputStream(file);
                    }
                    catch (FileNotFoundException fileNotFoundException) {
                        // empty catch block
                    }
                }
                if (f == null) {
                    if (XMLSchedulingDataProcessorPlugin.this.isFailOnFileNotFound()) {
                        throw new SchedulerException("File named '" + this.getFileName() + "' does not exist.");
                    }
                    XMLSchedulingDataProcessorPlugin.this.getLog().warn("File named '" + this.getFileName() + "' does not exist.");
                } else {
                    this.fileFound = true;
                }
                this.filePath = furl != null ? furl : file.getAbsolutePath();
                this.fileBasename = file.getName();
            }
            finally {
                try {
                    if (f != null) {
                        f.close();
                    }
                }
                catch (IOException ioe) {
                    XMLSchedulingDataProcessorPlugin.this.getLog().warn("Error closing jobs file " + this.getFileName(), (Throwable)ioe);
                }
            }
        }
    }
}

