/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.quartz.impl;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessControlException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import org.quartz.JobKey;
import org.quartz.JobListener;
import org.quartz.Matcher;
import org.quartz.Scheduler;
import org.quartz.SchedulerConfigException;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerKey;
import org.quartz.TriggerListener;
import org.quartz.core.JobRunShellFactory;
import org.quartz.core.QuartzScheduler;
import org.quartz.core.QuartzSchedulerResources;
import org.quartz.ee.jta.JTAAnnotationAwareJobRunShellFactory;
import org.quartz.ee.jta.JTAJobRunShellFactory;
import org.quartz.ee.jta.UserTransactionHelper;
import org.quartz.impl.DefaultThreadExecutor;
import org.quartz.impl.RemoteMBeanScheduler;
import org.quartz.impl.RemoteScheduler;
import org.quartz.impl.SchedulerDetailsSetter;
import org.quartz.impl.SchedulerRepository;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.jdbcjobstore.JobStoreSupport;
import org.quartz.impl.jdbcjobstore.Semaphore;
import org.quartz.impl.jdbcjobstore.TablePrefixAware;
import org.quartz.impl.matchers.EverythingMatcher;
import org.quartz.management.ManagementRESTServiceConfiguration;
import org.quartz.simpl.RAMJobStore;
import org.quartz.simpl.SimpleThreadPool;
import org.quartz.spi.ClassLoadHelper;
import org.quartz.spi.InstanceIdGenerator;
import org.quartz.spi.JobFactory;
import org.quartz.spi.JobStore;
import org.quartz.spi.SchedulerPlugin;
import org.quartz.spi.ThreadExecutor;
import org.quartz.spi.ThreadPool;
import org.quartz.utils.C3p0PoolingConnectionProvider;
import org.quartz.utils.ConnectionProvider;
import org.quartz.utils.DBConnectionManager;
import org.quartz.utils.JNDIConnectionProvider;
import org.quartz.utils.PoolingConnectionProvider;
import org.quartz.utils.PropertiesParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StdSchedulerFactory
implements SchedulerFactory {
    public static final String PROPERTIES_FILE = "org.quartz.properties";
    public static final String PROP_SCHED_INSTANCE_NAME = "org.quartz.scheduler.instanceName";
    public static final String PROP_SCHED_INSTANCE_ID = "org.quartz.scheduler.instanceId";
    public static final String PROP_SCHED_INSTANCE_ID_GENERATOR_PREFIX = "org.quartz.scheduler.instanceIdGenerator";
    public static final String PROP_SCHED_INSTANCE_ID_GENERATOR_CLASS = "org.quartz.scheduler.instanceIdGenerator.class";
    public static final String PROP_SCHED_THREAD_NAME = "org.quartz.scheduler.threadName";
    public static final String PROP_SCHED_BATCH_TIME_WINDOW = "org.quartz.scheduler.batchTriggerAcquisitionFireAheadTimeWindow";
    public static final String PROP_SCHED_MAX_BATCH_SIZE = "org.quartz.scheduler.batchTriggerAcquisitionMaxCount";
    public static final String PROP_SCHED_JMX_EXPORT = "org.quartz.scheduler.jmx.export";
    public static final String PROP_SCHED_JMX_OBJECT_NAME = "org.quartz.scheduler.jmx.objectName";
    public static final String PROP_SCHED_JMX_PROXY = "org.quartz.scheduler.jmx.proxy";
    public static final String PROP_SCHED_JMX_PROXY_CLASS = "org.quartz.scheduler.jmx.proxy.class";
    public static final String PROP_SCHED_RMI_EXPORT = "org.quartz.scheduler.rmi.export";
    public static final String PROP_SCHED_RMI_PROXY = "org.quartz.scheduler.rmi.proxy";
    public static final String PROP_SCHED_RMI_HOST = "org.quartz.scheduler.rmi.registryHost";
    public static final String PROP_SCHED_RMI_PORT = "org.quartz.scheduler.rmi.registryPort";
    public static final String PROP_SCHED_RMI_SERVER_PORT = "org.quartz.scheduler.rmi.serverPort";
    public static final String PROP_SCHED_RMI_CREATE_REGISTRY = "org.quartz.scheduler.rmi.createRegistry";
    public static final String PROP_SCHED_RMI_BIND_NAME = "org.quartz.scheduler.rmi.bindName";
    public static final String PROP_SCHED_WRAP_JOB_IN_USER_TX = "org.quartz.scheduler.wrapJobExecutionInUserTransaction";
    public static final String PROP_SCHED_USER_TX_URL = "org.quartz.scheduler.userTransactionURL";
    public static final String PROP_SCHED_IDLE_WAIT_TIME = "org.quartz.scheduler.idleWaitTime";
    public static final String PROP_SCHED_DB_FAILURE_RETRY_INTERVAL = "org.quartz.scheduler.dbFailureRetryInterval";
    public static final String PROP_SCHED_MAKE_SCHEDULER_THREAD_DAEMON = "org.quartz.scheduler.makeSchedulerThreadDaemon";
    public static final String PROP_SCHED_SCHEDULER_THREADS_INHERIT_CONTEXT_CLASS_LOADER_OF_INITIALIZING_THREAD = "org.quartz.scheduler.threadsInheritContextClassLoaderOfInitializer";
    public static final String PROP_SCHED_CLASS_LOAD_HELPER_CLASS = "org.quartz.scheduler.classLoadHelper.class";
    public static final String PROP_SCHED_JOB_FACTORY_CLASS = "org.quartz.scheduler.jobFactory.class";
    public static final String PROP_SCHED_JOB_FACTORY_PREFIX = "org.quartz.scheduler.jobFactory";
    public static final String PROP_SCHED_INTERRUPT_JOBS_ON_SHUTDOWN = "org.quartz.scheduler.interruptJobsOnShutdown";
    public static final String PROP_SCHED_INTERRUPT_JOBS_ON_SHUTDOWN_WITH_WAIT = "org.quartz.scheduler.interruptJobsOnShutdownWithWait";
    public static final String PROP_SCHED_CONTEXT_PREFIX = "org.quartz.context.key";
    public static final String PROP_THREAD_POOL_PREFIX = "org.quartz.threadPool";
    public static final String PROP_THREAD_POOL_CLASS = "org.quartz.threadPool.class";
    public static final String PROP_JOB_STORE_PREFIX = "org.quartz.jobStore";
    public static final String PROP_JOB_STORE_LOCK_HANDLER_PREFIX = "org.quartz.jobStore.lockHandler";
    public static final String PROP_JOB_STORE_LOCK_HANDLER_CLASS = "org.quartz.jobStore.lockHandler.class";
    public static final String PROP_TABLE_PREFIX = "tablePrefix";
    public static final String PROP_SCHED_NAME = "schedName";
    public static final String PROP_JOB_STORE_CLASS = "org.quartz.jobStore.class";
    public static final String PROP_JOB_STORE_USE_PROP = "org.quartz.jobStore.useProperties";
    public static final String PROP_DATASOURCE_PREFIX = "org.quartz.dataSource";
    public static final String PROP_CONNECTION_PROVIDER_CLASS = "connectionProvider.class";
    @Deprecated
    public static final String PROP_DATASOURCE_DRIVER = "driver";
    @Deprecated
    public static final String PROP_DATASOURCE_URL = "URL";
    @Deprecated
    public static final String PROP_DATASOURCE_USER = "user";
    @Deprecated
    public static final String PROP_DATASOURCE_PASSWORD = "password";
    @Deprecated
    public static final String PROP_DATASOURCE_MAX_CONNECTIONS = "maxConnections";
    @Deprecated
    public static final String PROP_DATASOURCE_VALIDATION_QUERY = "validationQuery";
    public static final String PROP_DATASOURCE_JNDI_URL = "jndiURL";
    public static final String PROP_DATASOURCE_JNDI_ALWAYS_LOOKUP = "jndiAlwaysLookup";
    public static final String PROP_DATASOURCE_JNDI_INITIAL = "java.naming.factory.initial";
    public static final String PROP_DATASOURCE_JNDI_PROVDER = "java.naming.provider.url";
    public static final String PROP_DATASOURCE_JNDI_PRINCIPAL = "java.naming.security.principal";
    public static final String PROP_DATASOURCE_JNDI_CREDENTIALS = "java.naming.security.credentials";
    public static final String PROP_PLUGIN_PREFIX = "org.quartz.plugin";
    public static final String PROP_PLUGIN_CLASS = "class";
    public static final String PROP_JOB_LISTENER_PREFIX = "org.quartz.jobListener";
    public static final String PROP_TRIGGER_LISTENER_PREFIX = "org.quartz.triggerListener";
    public static final String PROP_LISTENER_CLASS = "class";
    public static final String DEFAULT_INSTANCE_ID = "NON_CLUSTERED";
    public static final String AUTO_GENERATE_INSTANCE_ID = "AUTO";
    public static final String PROP_THREAD_EXECUTOR = "org.quartz.threadExecutor";
    public static final String PROP_THREAD_EXECUTOR_CLASS = "org.quartz.threadExecutor.class";
    public static final String SYSTEM_PROPERTY_AS_INSTANCE_ID = "SYS_PROP";
    public static final String MANAGEMENT_REST_SERVICE_ENABLED = "org.quartz.managementRESTService.enabled";
    public static final String MANAGEMENT_REST_SERVICE_HOST_PORT = "org.quartz.managementRESTService.bind";
    private SchedulerException initException = null;
    private String propSrc = null;
    private PropertiesParser cfg;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public StdSchedulerFactory() {
    }

    public StdSchedulerFactory(Properties props) throws SchedulerException {
        this.initialize(props);
    }

    public StdSchedulerFactory(String fileName) throws SchedulerException {
        this.initialize(fileName);
    }

    public Logger getLog() {
        return this.log;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void initialize() throws SchedulerException {
        Properties props;
        block24: {
            if (this.cfg != null) {
                return;
            }
            if (this.initException != null) {
                throw this.initException;
            }
            String requestedFile = System.getProperty(PROPERTIES_FILE);
            String propFileName = requestedFile != null ? requestedFile : "quartz.properties";
            File propFile = new File(propFileName);
            props = new Properties();
            InputStream in = null;
            try {
                if (propFile.exists()) {
                    try {
                        this.propSrc = requestedFile != null ? "specified file: '" + requestedFile + "'" : "default file in current working dir: 'quartz.properties'";
                        in = new BufferedInputStream(new FileInputStream(propFileName));
                        props.load(in);
                        break block24;
                    }
                    catch (IOException ioe) {
                        this.initException = new SchedulerException("Properties file: '" + propFileName + "' could not be read.", ioe);
                        throw this.initException;
                    }
                }
                if (requestedFile != null) {
                    in = Thread.currentThread().getContextClassLoader().getResourceAsStream(requestedFile);
                    if (in == null) {
                        this.initException = new SchedulerException("Properties file: '" + requestedFile + "' could not be found.");
                        throw this.initException;
                    }
                    this.propSrc = "specified file: '" + requestedFile + "' in the class resource path.";
                    in = new BufferedInputStream(in);
                    try {
                        props.load(in);
                        break block24;
                    }
                    catch (IOException ioe) {
                        this.initException = new SchedulerException("Properties file: '" + requestedFile + "' could not be read.", ioe);
                        throw this.initException;
                    }
                }
                this.propSrc = "default resource file in Quartz package: 'quartz.properties'";
                ClassLoader cl = this.getClass().getClassLoader();
                if (cl == null) {
                    cl = this.findClassloader();
                }
                if (cl == null) {
                    throw new SchedulerConfigException("Unable to find a class loader on the current thread or class.");
                }
                in = cl.getResourceAsStream("quartz.properties");
                if (in == null) {
                    in = cl.getResourceAsStream("/quartz.properties");
                }
                if (in == null) {
                    in = cl.getResourceAsStream("org/quartz/quartz.properties");
                }
                if (in == null) {
                    this.initException = new SchedulerException("Default quartz.properties not found in class path");
                    throw this.initException;
                }
                try {
                    props.load(in);
                }
                catch (IOException ioe) {
                    this.initException = new SchedulerException("Resource properties file: 'org/quartz/quartz.properties' could not be read from the classpath.", ioe);
                    throw this.initException;
                }
            }
            finally {
                if (in != null) {
                    try {
                        in.close();
                    }
                    catch (IOException iOException) {}
                }
            }
        }
        this.initialize(StdSchedulerFactory.overrideWithSysProps(props, this.getLog()));
    }

    static Properties overrideWithSysProps(Properties props, Logger log) {
        Properties sysProps = null;
        try {
            sysProps = System.getProperties();
        }
        catch (AccessControlException e) {
            log.warn("Skipping overriding quartz properties with System properties during initialization because of an AccessControlException.  This is likely due to not having read/write access for java.util.PropertyPermission as required by java.lang.System.getProperties().  To resolve this warning, either add this permission to your policy file or use a non-default version of initialize().", (Throwable)e);
        }
        if (sysProps != null) {
            Enumeration<?> en = sysProps.propertyNames();
            while (en.hasMoreElements()) {
                Object name = en.nextElement();
                Object value = sysProps.get(name);
                if (!(name instanceof String) || !(value instanceof String)) continue;
                props.setProperty((String)name, (String)value);
            }
        }
        return props;
    }

    public void initialize(String filename) throws SchedulerException {
        if (this.cfg != null) {
            return;
        }
        if (this.initException != null) {
            throw this.initException;
        }
        InputStream is = null;
        Properties props = new Properties();
        is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
        try {
            if (is != null) {
                is = new BufferedInputStream(is);
                this.propSrc = "the specified file : '" + filename + "' from the class resource path.";
            } else {
                is = new BufferedInputStream(new FileInputStream(filename));
                this.propSrc = "the specified file : '" + filename + "'";
            }
            props.load(is);
        }
        catch (IOException ioe) {
            this.initException = new SchedulerException("Properties file: '" + filename + "' could not be read.", ioe);
            throw this.initException;
        }
        finally {
            if (is != null) {
                try {
                    is.close();
                }
                catch (IOException iOException) {}
            }
        }
        this.initialize(props);
    }

    public void initialize(InputStream propertiesStream) throws SchedulerException {
        if (this.cfg != null) {
            return;
        }
        if (this.initException != null) {
            throw this.initException;
        }
        Properties props = new Properties();
        if (propertiesStream != null) {
            try {
                props.load(propertiesStream);
                this.propSrc = "an externally opened InputStream.";
            }
            catch (IOException e) {
                this.initException = new SchedulerException("Error loading property data from InputStream", e);
                throw this.initException;
            }
        } else {
            this.initException = new SchedulerException("Error loading property data from InputStream - InputStream is null.");
            throw this.initException;
        }
        this.initialize(props);
    }

    public void initialize(Properties props) throws SchedulerException {
        if (this.propSrc == null) {
            this.propSrc = "an externally provided properties instance.";
        }
        this.cfg = new PropertiesParser(props);
    }

    private Scheduler instantiate() throws SchedulerException {
        ThreadExecutor threadExecutor;
        String lockHandlerClass;
        String tpClass;
        if (this.cfg == null) {
            this.initialize();
        }
        if (this.initException != null) {
            throw this.initException;
        }
        JobStore js = null;
        ThreadPool tp = null;
        QuartzScheduler qs = null;
        DBConnectionManager dbMgr = null;
        String instanceIdGeneratorClass = null;
        Properties tProps = null;
        String userTXLocation = null;
        boolean wrapJobInTx = false;
        boolean autoId = false;
        long idleWaitTime = -1L;
        long dbFailureRetry = 15000L;
        SchedulerRepository schedRep = SchedulerRepository.getInstance();
        String schedName = this.cfg.getStringProperty(PROP_SCHED_INSTANCE_NAME, "QuartzScheduler");
        String threadName = this.cfg.getStringProperty(PROP_SCHED_THREAD_NAME, schedName + "_QuartzSchedulerThread");
        String schedInstId = this.cfg.getStringProperty(PROP_SCHED_INSTANCE_ID, DEFAULT_INSTANCE_ID);
        if (schedInstId.equals(AUTO_GENERATE_INSTANCE_ID)) {
            autoId = true;
            instanceIdGeneratorClass = this.cfg.getStringProperty(PROP_SCHED_INSTANCE_ID_GENERATOR_CLASS, "org.quartz.simpl.SimpleInstanceIdGenerator");
        } else if (schedInstId.equals(SYSTEM_PROPERTY_AS_INSTANCE_ID)) {
            autoId = true;
            instanceIdGeneratorClass = "org.quartz.simpl.SystemPropertyInstanceIdGenerator";
        }
        userTXLocation = this.cfg.getStringProperty(PROP_SCHED_USER_TX_URL, userTXLocation);
        if (userTXLocation != null && userTXLocation.trim().length() == 0) {
            userTXLocation = null;
        }
        String classLoadHelperClass = this.cfg.getStringProperty(PROP_SCHED_CLASS_LOAD_HELPER_CLASS, "org.quartz.simpl.CascadingClassLoadHelper");
        wrapJobInTx = this.cfg.getBooleanProperty(PROP_SCHED_WRAP_JOB_IN_USER_TX, wrapJobInTx);
        String jobFactoryClass = this.cfg.getStringProperty(PROP_SCHED_JOB_FACTORY_CLASS, null);
        if ((idleWaitTime = this.cfg.getLongProperty(PROP_SCHED_IDLE_WAIT_TIME, idleWaitTime)) > -1L && idleWaitTime < 1000L) {
            throw new SchedulerException("org.quartz.scheduler.idleWaitTime of less than 1000ms is not legal.");
        }
        if ((dbFailureRetry = this.cfg.getLongProperty(PROP_SCHED_DB_FAILURE_RETRY_INTERVAL, dbFailureRetry)) < 0L) {
            throw new SchedulerException("org.quartz.scheduler.dbFailureRetryInterval of less than 0 ms is not legal.");
        }
        boolean makeSchedulerThreadDaemon = this.cfg.getBooleanProperty(PROP_SCHED_MAKE_SCHEDULER_THREAD_DAEMON);
        boolean threadsInheritInitalizersClassLoader = this.cfg.getBooleanProperty(PROP_SCHED_SCHEDULER_THREADS_INHERIT_CONTEXT_CLASS_LOADER_OF_INITIALIZING_THREAD);
        long batchTimeWindow = this.cfg.getLongProperty(PROP_SCHED_BATCH_TIME_WINDOW, 0L);
        int maxBatchSize = this.cfg.getIntProperty(PROP_SCHED_MAX_BATCH_SIZE, 1);
        boolean interruptJobsOnShutdown = this.cfg.getBooleanProperty(PROP_SCHED_INTERRUPT_JOBS_ON_SHUTDOWN, false);
        boolean interruptJobsOnShutdownWithWait = this.cfg.getBooleanProperty(PROP_SCHED_INTERRUPT_JOBS_ON_SHUTDOWN_WITH_WAIT, false);
        boolean jmxExport = this.cfg.getBooleanProperty(PROP_SCHED_JMX_EXPORT);
        String jmxObjectName = this.cfg.getStringProperty(PROP_SCHED_JMX_OBJECT_NAME);
        boolean jmxProxy = this.cfg.getBooleanProperty(PROP_SCHED_JMX_PROXY);
        String jmxProxyClass = this.cfg.getStringProperty(PROP_SCHED_JMX_PROXY_CLASS);
        boolean rmiExport = this.cfg.getBooleanProperty(PROP_SCHED_RMI_EXPORT, false);
        boolean rmiProxy = this.cfg.getBooleanProperty(PROP_SCHED_RMI_PROXY, false);
        String rmiHost = this.cfg.getStringProperty(PROP_SCHED_RMI_HOST, "localhost");
        int rmiPort = this.cfg.getIntProperty(PROP_SCHED_RMI_PORT, 1099);
        int rmiServerPort = this.cfg.getIntProperty(PROP_SCHED_RMI_SERVER_PORT, -1);
        String rmiCreateRegistry = this.cfg.getStringProperty(PROP_SCHED_RMI_CREATE_REGISTRY, "never");
        String rmiBindName = this.cfg.getStringProperty(PROP_SCHED_RMI_BIND_NAME);
        if (jmxProxy && rmiProxy) {
            throw new SchedulerConfigException("Cannot proxy both RMI and JMX.");
        }
        boolean managementRESTServiceEnabled = this.cfg.getBooleanProperty(MANAGEMENT_REST_SERVICE_ENABLED, false);
        String managementRESTServiceHostAndPort = this.cfg.getStringProperty(MANAGEMENT_REST_SERVICE_HOST_PORT, "0.0.0.0:9889");
        Properties schedCtxtProps = this.cfg.getPropertyGroup(PROP_SCHED_CONTEXT_PREFIX, true);
        if (rmiProxy) {
            if (autoId) {
                schedInstId = DEFAULT_INSTANCE_ID;
            }
            String uid = rmiBindName == null ? QuartzSchedulerResources.getUniqueIdentifier(schedName, schedInstId) : rmiBindName;
            RemoteScheduler remoteScheduler = new RemoteScheduler(uid, rmiHost, rmiPort);
            schedRep.bind(remoteScheduler);
            return remoteScheduler;
        }
        ClassLoadHelper loadHelper = null;
        try {
            loadHelper = (ClassLoadHelper)this.loadClass(classLoadHelperClass).newInstance();
        }
        catch (Exception e) {
            throw new SchedulerConfigException("Unable to instantiate class load helper class: " + e.getMessage(), e);
        }
        loadHelper.initialize();
        if (jmxProxy) {
            if (autoId) {
                schedInstId = DEFAULT_INSTANCE_ID;
            }
            if (jmxProxyClass == null) {
                throw new SchedulerConfigException("No JMX Proxy Scheduler class provided");
            }
            RemoteMBeanScheduler jmxScheduler = null;
            try {
                jmxScheduler = (RemoteMBeanScheduler)loadHelper.loadClass(jmxProxyClass).newInstance();
            }
            catch (Exception e) {
                throw new SchedulerConfigException("Unable to instantiate RemoteMBeanScheduler class.", e);
            }
            if (jmxObjectName == null) {
                jmxObjectName = QuartzSchedulerResources.generateJMXObjectName(schedName, schedInstId);
            }
            jmxScheduler.setSchedulerObjectName(jmxObjectName);
            tProps = this.cfg.getPropertyGroup(PROP_SCHED_JMX_PROXY, true);
            try {
                this.setBeanProps(jmxScheduler, tProps);
            }
            catch (Exception e) {
                this.initException = new SchedulerException("RemoteMBeanScheduler class '" + jmxProxyClass + "' props could not be configured.", e);
                throw this.initException;
            }
            jmxScheduler.initialize();
            schedRep.bind(jmxScheduler);
            return jmxScheduler;
        }
        JobFactory jobFactory = null;
        if (jobFactoryClass != null) {
            try {
                jobFactory = (JobFactory)loadHelper.loadClass(jobFactoryClass).newInstance();
            }
            catch (Exception e) {
                throw new SchedulerConfigException("Unable to instantiate JobFactory class: " + e.getMessage(), e);
            }
            tProps = this.cfg.getPropertyGroup(PROP_SCHED_JOB_FACTORY_PREFIX, true);
            try {
                this.setBeanProps(jobFactory, tProps);
            }
            catch (Exception e) {
                this.initException = new SchedulerException("JobFactory class '" + jobFactoryClass + "' props could not be configured.", e);
                throw this.initException;
            }
        }
        InstanceIdGenerator instanceIdGenerator = null;
        if (instanceIdGeneratorClass != null) {
            try {
                instanceIdGenerator = (InstanceIdGenerator)loadHelper.loadClass(instanceIdGeneratorClass).newInstance();
            }
            catch (Exception e) {
                throw new SchedulerConfigException("Unable to instantiate InstanceIdGenerator class: " + e.getMessage(), e);
            }
            tProps = this.cfg.getPropertyGroup(PROP_SCHED_INSTANCE_ID_GENERATOR_PREFIX, true);
            try {
                this.setBeanProps(instanceIdGenerator, tProps);
            }
            catch (Exception e) {
                this.initException = new SchedulerException("InstanceIdGenerator class '" + instanceIdGeneratorClass + "' props could not be configured.", e);
                throw this.initException;
            }
        }
        if ((tpClass = this.cfg.getStringProperty(PROP_THREAD_POOL_CLASS, SimpleThreadPool.class.getName())) == null) {
            this.initException = new SchedulerException("ThreadPool class not specified. ");
            throw this.initException;
        }
        try {
            tp = (ThreadPool)loadHelper.loadClass(tpClass).newInstance();
        }
        catch (Exception e) {
            this.initException = new SchedulerException("ThreadPool class '" + tpClass + "' could not be instantiated.", e);
            throw this.initException;
        }
        tProps = this.cfg.getPropertyGroup(PROP_THREAD_POOL_PREFIX, true);
        try {
            this.setBeanProps(tp, tProps);
        }
        catch (Exception e) {
            this.initException = new SchedulerException("ThreadPool class '" + tpClass + "' props could not be configured.", e);
            throw this.initException;
        }
        String jsClass = this.cfg.getStringProperty(PROP_JOB_STORE_CLASS, RAMJobStore.class.getName());
        if (jsClass == null) {
            this.initException = new SchedulerException("JobStore class not specified. ");
            throw this.initException;
        }
        try {
            js = (JobStore)loadHelper.loadClass(jsClass).newInstance();
        }
        catch (Exception e) {
            this.initException = new SchedulerException("JobStore class '" + jsClass + "' could not be instantiated.", e);
            throw this.initException;
        }
        SchedulerDetailsSetter.setDetails(js, schedName, schedInstId);
        tProps = this.cfg.getPropertyGroup(PROP_JOB_STORE_PREFIX, true, new String[]{PROP_JOB_STORE_LOCK_HANDLER_PREFIX});
        try {
            this.setBeanProps(js, tProps);
        }
        catch (Exception e) {
            this.initException = new SchedulerException("JobStore class '" + jsClass + "' props could not be configured.", e);
            throw this.initException;
        }
        if (js instanceof JobStoreSupport && (lockHandlerClass = this.cfg.getStringProperty(PROP_JOB_STORE_LOCK_HANDLER_CLASS)) != null) {
            try {
                Semaphore lockHandler = (Semaphore)loadHelper.loadClass(lockHandlerClass).newInstance();
                tProps = this.cfg.getPropertyGroup(PROP_JOB_STORE_LOCK_HANDLER_PREFIX, true);
                if (lockHandler instanceof TablePrefixAware) {
                    tProps.setProperty(PROP_TABLE_PREFIX, ((JobStoreSupport)js).getTablePrefix());
                    tProps.setProperty(PROP_SCHED_NAME, schedName);
                }
                try {
                    this.setBeanProps(lockHandler, tProps);
                }
                catch (Exception e) {
                    this.initException = new SchedulerException("JobStore LockHandler class '" + lockHandlerClass + "' props could not be configured.", e);
                    throw this.initException;
                }
                ((JobStoreSupport)js).setLockHandler(lockHandler);
                this.getLog().info("Using custom data access locking (synchronization): " + lockHandlerClass);
            }
            catch (Exception e) {
                this.initException = new SchedulerException("JobStore LockHandler class '" + lockHandlerClass + "' could not be instantiated.", e);
                throw this.initException;
            }
        }
        String[] dsNames = this.cfg.getPropertyGroups(PROP_DATASOURCE_PREFIX);
        for (int i = 0; i < dsNames.length; ++i) {
            PropertiesParser pp = new PropertiesParser(this.cfg.getPropertyGroup("org.quartz.dataSource." + dsNames[i], true));
            String cpClass = pp.getStringProperty(PROP_CONNECTION_PROVIDER_CLASS, null);
            if (cpClass != null) {
                ConnectionProvider cp = null;
                try {
                    cp = (ConnectionProvider)loadHelper.loadClass(cpClass).newInstance();
                }
                catch (Exception e) {
                    this.initException = new SchedulerException("ConnectionProvider class '" + cpClass + "' could not be instantiated.", e);
                    throw this.initException;
                }
                try {
                    pp.getUnderlyingProperties().remove(PROP_CONNECTION_PROVIDER_CLASS);
                    if (cp instanceof PoolingConnectionProvider) {
                        this.populateProviderWithExtraProps((PoolingConnectionProvider)cp, pp.getUnderlyingProperties());
                    } else {
                        this.setBeanProps(cp, pp.getUnderlyingProperties());
                    }
                    cp.initialize();
                }
                catch (Exception e) {
                    this.initException = new SchedulerException("ConnectionProvider class '" + cpClass + "' props could not be configured.", e);
                    throw this.initException;
                }
                dbMgr = DBConnectionManager.getInstance();
                dbMgr.addConnectionProvider(dsNames[i], cp);
                continue;
            }
            String dsJndi = pp.getStringProperty(PROP_DATASOURCE_JNDI_URL, null);
            if (dsJndi != null) {
                boolean dsAlwaysLookup = pp.getBooleanProperty(PROP_DATASOURCE_JNDI_ALWAYS_LOOKUP);
                String dsJndiInitial = pp.getStringProperty(PROP_DATASOURCE_JNDI_INITIAL);
                String dsJndiProvider = pp.getStringProperty(PROP_DATASOURCE_JNDI_PROVDER);
                String dsJndiPrincipal = pp.getStringProperty(PROP_DATASOURCE_JNDI_PRINCIPAL);
                String dsJndiCredentials = pp.getStringProperty(PROP_DATASOURCE_JNDI_CREDENTIALS);
                Properties props = null;
                if (null != dsJndiInitial || null != dsJndiProvider || null != dsJndiPrincipal || null != dsJndiCredentials) {
                    props = new Properties();
                    if (dsJndiInitial != null) {
                        props.put(PROP_DATASOURCE_JNDI_INITIAL, dsJndiInitial);
                    }
                    if (dsJndiProvider != null) {
                        props.put(PROP_DATASOURCE_JNDI_PROVDER, dsJndiProvider);
                    }
                    if (dsJndiPrincipal != null) {
                        props.put(PROP_DATASOURCE_JNDI_PRINCIPAL, dsJndiPrincipal);
                    }
                    if (dsJndiCredentials != null) {
                        props.put(PROP_DATASOURCE_JNDI_CREDENTIALS, dsJndiCredentials);
                    }
                }
                JNDIConnectionProvider cp = new JNDIConnectionProvider(dsJndi, props, dsAlwaysLookup);
                dbMgr = DBConnectionManager.getInstance();
                dbMgr.addConnectionProvider(dsNames[i], cp);
                continue;
            }
            String poolingProvider = pp.getStringProperty("provider");
            String dsDriver = pp.getStringProperty(PROP_DATASOURCE_DRIVER);
            String dsURL = pp.getStringProperty(PROP_DATASOURCE_URL);
            if (dsDriver == null) {
                this.initException = new SchedulerException("Driver not specified for DataSource: " + dsNames[i]);
                throw this.initException;
            }
            if (dsURL == null) {
                this.initException = new SchedulerException("DB URL not specified for DataSource: " + dsNames[i]);
                throw this.initException;
            }
            cpClass = poolingProvider != null && poolingProvider.equals("hikaricp") ? "org.quartz.utils.HikariCpPoolingConnectionProvider" : "org.quartz.utils.C3p0PoolingConnectionProvider";
            this.log.info("Using ConnectionProvider class '" + cpClass + "' for data source '" + dsNames[i] + "'");
            try {
                ConnectionProvider cp = null;
                try {
                    Constructor<?> constructor = loadHelper.loadClass(cpClass).getConstructor(Properties.class);
                    cp = (ConnectionProvider)constructor.newInstance(pp.getUnderlyingProperties());
                }
                catch (Exception e) {
                    this.initException = new SchedulerException("ConnectionProvider class '" + cpClass + "' could not be instantiated.", e);
                    throw this.initException;
                }
                dbMgr = DBConnectionManager.getInstance();
                dbMgr.addConnectionProvider(dsNames[i], cp);
                this.populateProviderWithExtraProps((PoolingConnectionProvider)cp, pp.getUnderlyingProperties());
                continue;
            }
            catch (Exception sqle) {
                this.initException = new SchedulerException("Could not initialize DataSource: " + dsNames[i], sqle);
                throw this.initException;
            }
        }
        String[] pluginNames = this.cfg.getPropertyGroups(PROP_PLUGIN_PREFIX);
        SchedulerPlugin[] plugins = new SchedulerPlugin[pluginNames.length];
        for (int i = 0; i < pluginNames.length; ++i) {
            Properties pp = this.cfg.getPropertyGroup("org.quartz.plugin." + pluginNames[i], true);
            String plugInClass = pp.getProperty("class", null);
            if (plugInClass == null) {
                this.initException = new SchedulerException("SchedulerPlugin class not specified for plugin '" + pluginNames[i] + "'");
                throw this.initException;
            }
            SchedulerPlugin plugin = null;
            try {
                plugin = (SchedulerPlugin)loadHelper.loadClass(plugInClass).newInstance();
            }
            catch (Exception e) {
                this.initException = new SchedulerException("SchedulerPlugin class '" + plugInClass + "' could not be instantiated.", e);
                throw this.initException;
            }
            try {
                this.setBeanProps(plugin, pp);
            }
            catch (Exception e) {
                this.initException = new SchedulerException("JobStore SchedulerPlugin '" + plugInClass + "' props could not be configured.", e);
                throw this.initException;
            }
            plugins[i] = plugin;
        }
        Class[] strArg = new Class[]{String.class};
        String[] jobListenerNames = this.cfg.getPropertyGroups(PROP_JOB_LISTENER_PREFIX);
        JobListener[] jobListeners = new JobListener[jobListenerNames.length];
        for (int i = 0; i < jobListenerNames.length; ++i) {
            Properties lp = this.cfg.getPropertyGroup("org.quartz.jobListener." + jobListenerNames[i], true);
            String listenerClass = lp.getProperty("class", null);
            if (listenerClass == null) {
                this.initException = new SchedulerException("JobListener class not specified for listener '" + jobListenerNames[i] + "'");
                throw this.initException;
            }
            JobListener listener = null;
            try {
                listener = (JobListener)loadHelper.loadClass(listenerClass).newInstance();
            }
            catch (Exception e) {
                this.initException = new SchedulerException("JobListener class '" + listenerClass + "' could not be instantiated.", e);
                throw this.initException;
            }
            try {
                Method nameSetter = null;
                try {
                    nameSetter = listener.getClass().getMethod("setName", strArg);
                }
                catch (NoSuchMethodException cp) {
                    // empty catch block
                }
                if (nameSetter != null) {
                    nameSetter.invoke(listener, jobListenerNames[i]);
                }
                this.setBeanProps(listener, lp);
            }
            catch (Exception e) {
                this.initException = new SchedulerException("JobListener '" + listenerClass + "' props could not be configured.", e);
                throw this.initException;
            }
            jobListeners[i] = listener;
        }
        String[] triggerListenerNames = this.cfg.getPropertyGroups(PROP_TRIGGER_LISTENER_PREFIX);
        TriggerListener[] triggerListeners = new TriggerListener[triggerListenerNames.length];
        for (int i = 0; i < triggerListenerNames.length; ++i) {
            Properties lp = this.cfg.getPropertyGroup("org.quartz.triggerListener." + triggerListenerNames[i], true);
            String listenerClass = lp.getProperty("class", null);
            if (listenerClass == null) {
                this.initException = new SchedulerException("TriggerListener class not specified for listener '" + triggerListenerNames[i] + "'");
                throw this.initException;
            }
            TriggerListener listener = null;
            try {
                listener = (TriggerListener)loadHelper.loadClass(listenerClass).newInstance();
            }
            catch (Exception e) {
                this.initException = new SchedulerException("TriggerListener class '" + listenerClass + "' could not be instantiated.", e);
                throw this.initException;
            }
            try {
                Method nameSetter = null;
                try {
                    nameSetter = listener.getClass().getMethod("setName", strArg);
                }
                catch (NoSuchMethodException noSuchMethodException) {
                    // empty catch block
                }
                if (nameSetter != null) {
                    nameSetter.invoke(listener, triggerListenerNames[i]);
                }
                this.setBeanProps(listener, lp);
            }
            catch (Exception e) {
                this.initException = new SchedulerException("TriggerListener '" + listenerClass + "' props could not be configured.", e);
                throw this.initException;
            }
            triggerListeners[i] = listener;
        }
        boolean tpInited = false;
        boolean qsInited = false;
        String threadExecutorClass = this.cfg.getStringProperty(PROP_THREAD_EXECUTOR_CLASS);
        if (threadExecutorClass != null) {
            tProps = this.cfg.getPropertyGroup(PROP_THREAD_EXECUTOR, true);
            try {
                threadExecutor = (ThreadExecutor)loadHelper.loadClass(threadExecutorClass).newInstance();
                this.log.info("Using custom implementation for ThreadExecutor: " + threadExecutorClass);
                this.setBeanProps(threadExecutor, tProps);
            }
            catch (Exception e) {
                this.initException = new SchedulerException("ThreadExecutor class '" + threadExecutorClass + "' could not be instantiated.", e);
                throw this.initException;
            }
        } else {
            this.log.info("Using default implementation for ThreadExecutor");
            threadExecutor = new DefaultThreadExecutor();
        }
        try {
            int i;
            JobRunShellFactory jrsf = null;
            if (userTXLocation != null) {
                UserTransactionHelper.setUserTxLocation(userTXLocation);
            }
            jrsf = wrapJobInTx ? new JTAJobRunShellFactory() : new JTAAnnotationAwareJobRunShellFactory();
            if (autoId) {
                try {
                    schedInstId = DEFAULT_INSTANCE_ID;
                    if (js.isClustered()) {
                        schedInstId = instanceIdGenerator.generateInstanceId();
                    }
                }
                catch (Exception e) {
                    this.getLog().error("Couldn't generate instance Id!", (Throwable)e);
                    throw new IllegalStateException("Cannot run without an instance id.");
                }
            }
            if (js.getClass().getName().startsWith("org.terracotta.quartz")) {
                try {
                    String uuid = (String)js.getClass().getMethod("getUUID", new Class[0]).invoke(js, new Object[0]);
                    if (schedInstId.equals(DEFAULT_INSTANCE_ID)) {
                        schedInstId = "TERRACOTTA_CLUSTERED,node=" + uuid;
                        if (jmxObjectName == null) {
                            jmxObjectName = QuartzSchedulerResources.generateJMXObjectName(schedName, schedInstId);
                        }
                    } else if (jmxObjectName == null) {
                        jmxObjectName = QuartzSchedulerResources.generateJMXObjectName(schedName, schedInstId + ",node=" + uuid);
                    }
                }
                catch (Exception e) {
                    throw new RuntimeException("Problem obtaining node id from TerracottaJobStore.", e);
                }
                if (null == this.cfg.getStringProperty(PROP_SCHED_JMX_EXPORT)) {
                    jmxExport = true;
                }
            }
            if (js instanceof JobStoreSupport) {
                JobStoreSupport jjs = (JobStoreSupport)js;
                jjs.setDbRetryInterval(dbFailureRetry);
                if (threadsInheritInitalizersClassLoader) {
                    jjs.setThreadsInheritInitializersClassLoadContext(threadsInheritInitalizersClassLoader);
                }
                jjs.setThreadExecutor(threadExecutor);
            }
            QuartzSchedulerResources rsrcs = new QuartzSchedulerResources();
            rsrcs.setName(schedName);
            rsrcs.setThreadName(threadName);
            rsrcs.setInstanceId(schedInstId);
            rsrcs.setJobRunShellFactory(jrsf);
            rsrcs.setMakeSchedulerThreadDaemon(makeSchedulerThreadDaemon);
            rsrcs.setThreadsInheritInitializersClassLoadContext(threadsInheritInitalizersClassLoader);
            rsrcs.setBatchTimeWindow(batchTimeWindow);
            rsrcs.setMaxBatchSize(maxBatchSize);
            rsrcs.setInterruptJobsOnShutdown(interruptJobsOnShutdown);
            rsrcs.setInterruptJobsOnShutdownWithWait(interruptJobsOnShutdownWithWait);
            rsrcs.setJMXExport(jmxExport);
            rsrcs.setJMXObjectName(jmxObjectName);
            if (managementRESTServiceEnabled) {
                ManagementRESTServiceConfiguration managementRESTServiceConfiguration = new ManagementRESTServiceConfiguration();
                managementRESTServiceConfiguration.setBind(managementRESTServiceHostAndPort);
                managementRESTServiceConfiguration.setEnabled(managementRESTServiceEnabled);
                rsrcs.setManagementRESTServiceConfiguration(managementRESTServiceConfiguration);
            }
            if (rmiExport) {
                rsrcs.setRMIRegistryHost(rmiHost);
                rsrcs.setRMIRegistryPort(rmiPort);
                rsrcs.setRMIServerPort(rmiServerPort);
                rsrcs.setRMICreateRegistryStrategy(rmiCreateRegistry);
                rsrcs.setRMIBindName(rmiBindName);
            }
            SchedulerDetailsSetter.setDetails(tp, schedName, schedInstId);
            rsrcs.setThreadExecutor(threadExecutor);
            threadExecutor.initialize();
            rsrcs.setThreadPool(tp);
            if (tp instanceof SimpleThreadPool && threadsInheritInitalizersClassLoader) {
                ((SimpleThreadPool)tp).setThreadsInheritContextClassLoaderOfInitializingThread(threadsInheritInitalizersClassLoader);
            }
            tp.initialize();
            tpInited = true;
            rsrcs.setJobStore(js);
            for (int i2 = 0; i2 < plugins.length; ++i2) {
                rsrcs.addSchedulerPlugin(plugins[i2]);
            }
            qs = new QuartzScheduler(rsrcs, idleWaitTime, dbFailureRetry);
            qsInited = true;
            Scheduler scheduler = this.instantiate(rsrcs, qs);
            if (jobFactory != null) {
                qs.setJobFactory(jobFactory);
            }
            for (i = 0; i < plugins.length; ++i) {
                plugins[i].initialize(pluginNames[i], scheduler, loadHelper);
            }
            for (i = 0; i < jobListeners.length; ++i) {
                qs.getListenerManager().addJobListener(jobListeners[i], (Matcher<JobKey>)EverythingMatcher.allJobs());
            }
            for (i = 0; i < triggerListeners.length; ++i) {
                qs.getListenerManager().addTriggerListener(triggerListeners[i], (Matcher<TriggerKey>)EverythingMatcher.allTriggers());
            }
            for (Object key : schedCtxtProps.keySet()) {
                String val = schedCtxtProps.getProperty((String)key);
                scheduler.getContext().put((String)key, val);
            }
            js.setInstanceId(schedInstId);
            js.setInstanceName(schedName);
            js.setThreadPoolSize(tp.getPoolSize());
            js.initialize(loadHelper, qs.getSchedulerSignaler());
            jrsf.initialize(scheduler);
            qs.initialize();
            this.getLog().info("Quartz scheduler '" + scheduler.getSchedulerName() + "' initialized from " + this.propSrc);
            this.getLog().info("Quartz scheduler version: " + qs.getVersion());
            qs.addNoGCObject(schedRep);
            if (dbMgr != null) {
                qs.addNoGCObject(dbMgr);
            }
            schedRep.bind(scheduler);
            return scheduler;
        }
        catch (SchedulerException e) {
            this.shutdownFromInstantiateException(tp, qs, tpInited, qsInited);
            throw e;
        }
        catch (RuntimeException re) {
            this.shutdownFromInstantiateException(tp, qs, tpInited, qsInited);
            throw re;
        }
        catch (Error re) {
            this.shutdownFromInstantiateException(tp, qs, tpInited, qsInited);
            throw re;
        }
    }

    private void populateProviderWithExtraProps(PoolingConnectionProvider cp, Properties props) throws Exception {
        Properties copyProps = new Properties();
        copyProps.putAll((Map<?, ?>)props);
        copyProps.remove(PROP_DATASOURCE_DRIVER);
        copyProps.remove(PROP_DATASOURCE_URL);
        copyProps.remove(PROP_DATASOURCE_USER);
        copyProps.remove(PROP_DATASOURCE_PASSWORD);
        copyProps.remove(PROP_DATASOURCE_MAX_CONNECTIONS);
        copyProps.remove(PROP_DATASOURCE_VALIDATION_QUERY);
        copyProps.remove("provider");
        if (cp instanceof C3p0PoolingConnectionProvider) {
            copyProps.remove("maxCachedStatementsPerConnection");
            copyProps.remove("validateOnCheckout");
            copyProps.remove("idleConnectionValidationSeconds");
            copyProps.remove("maxIdleTime");
        }
        this.setBeanProps(cp.getDataSource(), copyProps);
    }

    private void shutdownFromInstantiateException(ThreadPool tp, QuartzScheduler qs, boolean tpInited, boolean qsInited) {
        try {
            if (qsInited) {
                qs.shutdown(false);
            } else if (tpInited) {
                tp.shutdown(false);
            }
        }
        catch (Exception e) {
            this.getLog().error("Got another exception while shutting down after instantiation exception", (Throwable)e);
        }
    }

    protected Scheduler instantiate(QuartzSchedulerResources rsrcs, QuartzScheduler qs) {
        StdScheduler scheduler = new StdScheduler(qs);
        return scheduler;
    }

    private void setBeanProps(Object obj, Properties props) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, IntrospectionException, SchedulerConfigException {
        props.remove("class");
        props.remove("provider");
        BeanInfo bi = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propDescs = bi.getPropertyDescriptors();
        PropertiesParser pp = new PropertiesParser(props);
        Enumeration<Object> keys = props.keys();
        while (keys.hasMoreElements()) {
            String name = (String)keys.nextElement();
            String c = name.substring(0, 1).toUpperCase(Locale.US);
            String methName = "set" + c + name.substring(1);
            Method setMeth = this.getSetMethod(methName, propDescs);
            try {
                if (setMeth == null) {
                    throw new NoSuchMethodException("No setter for property '" + name + "'");
                }
                Class<?>[] params = setMeth.getParameterTypes();
                if (params.length != 1) {
                    throw new NoSuchMethodException("No 1-argument setter for property '" + name + "'");
                }
                PropertiesParser refProps = pp;
                String refName = pp.getStringProperty(name);
                if (refName != null && refName.startsWith("$@")) {
                    refName = refName.substring(2);
                    refProps = this.cfg;
                } else {
                    refName = name;
                }
                if (params[0].equals(Integer.TYPE)) {
                    setMeth.invoke(obj, refProps.getIntProperty(refName));
                    continue;
                }
                if (params[0].equals(Long.TYPE)) {
                    setMeth.invoke(obj, refProps.getLongProperty(refName));
                    continue;
                }
                if (params[0].equals(Float.TYPE)) {
                    setMeth.invoke(obj, Float.valueOf(refProps.getFloatProperty(refName)));
                    continue;
                }
                if (params[0].equals(Double.TYPE)) {
                    setMeth.invoke(obj, refProps.getDoubleProperty(refName));
                    continue;
                }
                if (params[0].equals(Boolean.TYPE)) {
                    setMeth.invoke(obj, refProps.getBooleanProperty(refName));
                    continue;
                }
                if (params[0].equals(String.class)) {
                    setMeth.invoke(obj, refProps.getStringProperty(refName));
                    continue;
                }
                throw new NoSuchMethodException("No primitive-type setter for property '" + name + "'");
            }
            catch (NumberFormatException nfe) {
                throw new SchedulerConfigException("Could not parse property '" + name + "' into correct data type: " + nfe.toString());
            }
        }
    }

    private Method getSetMethod(String name, PropertyDescriptor[] props) {
        for (int i = 0; i < props.length; ++i) {
            Method wMeth = props[i].getWriteMethod();
            if (wMeth == null || !wMeth.getName().equals(name)) continue;
            return wMeth;
        }
        return null;
    }

    private Class<?> loadClass(String className) throws ClassNotFoundException, SchedulerConfigException {
        try {
            ClassLoader cl = this.findClassloader();
            if (cl != null) {
                return cl.loadClass(className);
            }
            throw new SchedulerConfigException("Unable to find a class loader on the current thread or class.");
        }
        catch (ClassNotFoundException e) {
            if (this.getClass().getClassLoader() != null) {
                return this.getClass().getClassLoader().loadClass(className);
            }
            throw e;
        }
    }

    private ClassLoader findClassloader() {
        if (Thread.currentThread().getContextClassLoader() == null && this.getClass().getClassLoader() != null) {
            Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
        }
        return Thread.currentThread().getContextClassLoader();
    }

    private String getSchedulerName() {
        return this.cfg.getStringProperty(PROP_SCHED_INSTANCE_NAME, "QuartzScheduler");
    }

    @Override
    public Scheduler getScheduler() throws SchedulerException {
        SchedulerRepository schedRep;
        Scheduler sched;
        if (this.cfg == null) {
            this.initialize();
        }
        if ((sched = (schedRep = SchedulerRepository.getInstance()).lookup(this.getSchedulerName())) != null) {
            if (sched.isShutdown()) {
                schedRep.remove(this.getSchedulerName());
            } else {
                return sched;
            }
        }
        sched = this.instantiate();
        return sched;
    }

    public static Scheduler getDefaultScheduler() throws SchedulerException {
        StdSchedulerFactory fact = new StdSchedulerFactory();
        return fact.getScheduler();
    }

    @Override
    public Scheduler getScheduler(String schedName) throws SchedulerException {
        return SchedulerRepository.getInstance().lookup(schedName);
    }

    @Override
    public Collection<Scheduler> getAllSchedulers() throws SchedulerException {
        return SchedulerRepository.getInstance().lookupAll();
    }
}

