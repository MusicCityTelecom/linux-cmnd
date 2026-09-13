/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.jta.atomikos;

import java.time.Duration;
import java.util.Properties;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="spring.jta.atomikos.properties")
public class AtomikosProperties {
    private String service;
    private Duration maxTimeout = Duration.ofMillis(300000L);
    private Duration defaultJtaTimeout = Duration.ofMillis(10000L);
    private int maxActives = 50;
    private boolean enableLogging = true;
    private String transactionManagerUniqueName;
    private boolean serialJtaTransactions = true;
    private boolean allowSubTransactions = true;
    private boolean forceShutdownOnVmExit;
    private long defaultMaxWaitTimeOnShutdown = Long.MAX_VALUE;
    private String logBaseName = "tmlog";
    private String logBaseDir;
    private long checkpointInterval = 500L;
    private boolean threadedTwoPhaseCommit;
    private final Recovery recovery = new Recovery();

    public void setService(String service) {
        this.service = service;
    }

    public String getService() {
        return this.service;
    }

    public void setMaxTimeout(Duration maxTimeout) {
        this.maxTimeout = maxTimeout;
    }

    public Duration getMaxTimeout() {
        return this.maxTimeout;
    }

    public void setDefaultJtaTimeout(Duration defaultJtaTimeout) {
        this.defaultJtaTimeout = defaultJtaTimeout;
    }

    public Duration getDefaultJtaTimeout() {
        return this.defaultJtaTimeout;
    }

    public void setMaxActives(int maxActives) {
        this.maxActives = maxActives;
    }

    public int getMaxActives() {
        return this.maxActives;
    }

    public void setEnableLogging(boolean enableLogging) {
        this.enableLogging = enableLogging;
    }

    public boolean isEnableLogging() {
        return this.enableLogging;
    }

    public void setTransactionManagerUniqueName(String uniqueName) {
        this.transactionManagerUniqueName = uniqueName;
    }

    public String getTransactionManagerUniqueName() {
        return this.transactionManagerUniqueName;
    }

    public void setSerialJtaTransactions(boolean serialJtaTransactions) {
        this.serialJtaTransactions = serialJtaTransactions;
    }

    public boolean isSerialJtaTransactions() {
        return this.serialJtaTransactions;
    }

    public void setAllowSubTransactions(boolean allowSubTransactions) {
        this.allowSubTransactions = allowSubTransactions;
    }

    public boolean isAllowSubTransactions() {
        return this.allowSubTransactions;
    }

    public void setForceShutdownOnVmExit(boolean forceShutdownOnVmExit) {
        this.forceShutdownOnVmExit = forceShutdownOnVmExit;
    }

    public boolean isForceShutdownOnVmExit() {
        return this.forceShutdownOnVmExit;
    }

    public void setDefaultMaxWaitTimeOnShutdown(long defaultMaxWaitTimeOnShutdown) {
        this.defaultMaxWaitTimeOnShutdown = defaultMaxWaitTimeOnShutdown;
    }

    public long getDefaultMaxWaitTimeOnShutdown() {
        return this.defaultMaxWaitTimeOnShutdown;
    }

    public void setLogBaseName(String logBaseName) {
        this.logBaseName = logBaseName;
    }

    public String getLogBaseName() {
        return this.logBaseName;
    }

    public void setLogBaseDir(String logBaseDir) {
        this.logBaseDir = logBaseDir;
    }

    public String getLogBaseDir() {
        return this.logBaseDir;
    }

    public void setCheckpointInterval(long checkpointInterval) {
        this.checkpointInterval = checkpointInterval;
    }

    public long getCheckpointInterval() {
        return this.checkpointInterval;
    }

    public void setThreadedTwoPhaseCommit(boolean threadedTwoPhaseCommit) {
        this.threadedTwoPhaseCommit = threadedTwoPhaseCommit;
    }

    public boolean isThreadedTwoPhaseCommit() {
        return this.threadedTwoPhaseCommit;
    }

    public Recovery getRecovery() {
        return this.recovery;
    }

    public Properties asProperties() {
        Properties properties = new Properties();
        this.set(properties, "service", this.getService());
        this.set(properties, "max_timeout", this.getMaxTimeout());
        this.set(properties, "default_jta_timeout", this.getDefaultJtaTimeout());
        this.set(properties, "max_actives", this.getMaxActives());
        this.set(properties, "enable_logging", this.isEnableLogging());
        this.set(properties, "tm_unique_name", this.getTransactionManagerUniqueName());
        this.set(properties, "serial_jta_transactions", this.isSerialJtaTransactions());
        this.set(properties, "allow_subtransactions", this.isAllowSubTransactions());
        this.set(properties, "force_shutdown_on_vm_exit", this.isForceShutdownOnVmExit());
        this.set(properties, "default_max_wait_time_on_shutdown", this.getDefaultMaxWaitTimeOnShutdown());
        this.set(properties, "log_base_name", this.getLogBaseName());
        this.set(properties, "log_base_dir", this.getLogBaseDir());
        this.set(properties, "checkpoint_interval", this.getCheckpointInterval());
        this.set(properties, "threaded_2pc", this.isThreadedTwoPhaseCommit());
        Recovery recovery = this.getRecovery();
        this.set(properties, "forget_orphaned_log_entries_delay", recovery.getForgetOrphanedLogEntriesDelay());
        this.set(properties, "recovery_delay", recovery.getDelay());
        this.set(properties, "oltp_max_retries", recovery.getMaxRetries());
        this.set(properties, "oltp_retry_interval", recovery.getRetryInterval());
        return properties;
    }

    private void set(Properties properties, String key, Object value) {
        String id = "com.atomikos.icatch." + key;
        if (value != null && !properties.containsKey(id)) {
            properties.setProperty(id, this.asString(value));
        }
    }

    private String asString(Object value) {
        if (value instanceof Duration) {
            return String.valueOf(((Duration)value).toMillis());
        }
        return value.toString();
    }

    public static class Recovery {
        private Duration forgetOrphanedLogEntriesDelay = Duration.ofMillis(86400000L);
        private Duration delay = Duration.ofMillis(10000L);
        private int maxRetries = 5;
        private Duration retryInterval = Duration.ofMillis(10000L);

        public Duration getForgetOrphanedLogEntriesDelay() {
            return this.forgetOrphanedLogEntriesDelay;
        }

        public void setForgetOrphanedLogEntriesDelay(Duration forgetOrphanedLogEntriesDelay) {
            this.forgetOrphanedLogEntriesDelay = forgetOrphanedLogEntriesDelay;
        }

        public Duration getDelay() {
            return this.delay;
        }

        public void setDelay(Duration delay) {
            this.delay = delay;
        }

        public int getMaxRetries() {
            return this.maxRetries;
        }

        public void setMaxRetries(int maxRetries) {
            this.maxRetries = maxRetries;
        }

        public Duration getRetryInterval() {
            return this.retryInterval;
        }

        public void setRetryInterval(Duration retryInterval) {
            this.retryInterval = retryInterval;
        }
    }
}

