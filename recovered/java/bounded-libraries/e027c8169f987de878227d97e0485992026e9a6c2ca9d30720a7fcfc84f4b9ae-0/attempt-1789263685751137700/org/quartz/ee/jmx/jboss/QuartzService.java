/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jboss.naming.NonSerializableFactory
 *  org.jboss.system.ServiceMBeanSupport
 */
package org.quartz.ee.jmx.jboss;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import javax.naming.InitialContext;
import javax.naming.Name;
import javax.naming.NamingException;
import org.jboss.naming.NonSerializableFactory;
import org.jboss.system.ServiceMBeanSupport;
import org.quartz.Scheduler;
import org.quartz.SchedulerConfigException;
import org.quartz.SchedulerException;
import org.quartz.ee.jmx.jboss.QuartzServiceMBean;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzService
extends ServiceMBeanSupport
implements QuartzServiceMBean {
    private Properties properties = new Properties();
    private StdSchedulerFactory schedulerFactory;
    private String jndiName = "Quartz";
    private String propertiesFile = "";
    private boolean error = false;
    private boolean useProperties = false;
    private boolean usePropertiesFile = false;
    private boolean startScheduler = true;

    @Override
    public void setJndiName(String jndiName) throws Exception {
        String oldName = this.jndiName;
        this.jndiName = jndiName;
        if (super.getState() == 3) {
            this.unbind(oldName);
            try {
                this.rebind();
            }
            catch (NamingException ne) {
                this.log.error((Object)"Failed to rebind Scheduler", (Throwable)ne);
                throw new SchedulerConfigException("Failed to rebind Scheduler - ", ne);
            }
        }
    }

    @Override
    public String getJndiName() {
        return this.jndiName;
    }

    public String getName() {
        return "QuartzService(" + this.jndiName + ")";
    }

    @Override
    public void setProperties(String properties) {
        if (this.usePropertiesFile) {
            this.log.error((Object)"Must specify only one of 'Properties' or 'PropertiesFile'");
            this.error = true;
            return;
        }
        this.useProperties = true;
        try {
            properties = properties.replace(File.separator, "/");
            ByteArrayInputStream bais = new ByteArrayInputStream(properties.getBytes());
            this.properties = new Properties();
            this.properties.load(bais);
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    public String getProperties() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            this.properties.store(baos, "");
            return new String(baos.toByteArray());
        }
        catch (IOException ioe) {
            return "";
        }
    }

    @Override
    public void setPropertiesFile(String propertiesFile) {
        if (this.useProperties) {
            this.log.error((Object)"Must specify only one of 'Properties' or 'PropertiesFile'");
            this.error = true;
            return;
        }
        this.usePropertiesFile = true;
        this.propertiesFile = propertiesFile;
    }

    public String getPropertiesFile() {
        return this.propertiesFile;
    }

    @Override
    public void setStartScheduler(boolean startScheduler) {
        this.startScheduler = startScheduler;
    }

    public boolean getStartScheduler() {
        return this.startScheduler;
    }

    public void createService() throws Exception {
        this.log.info((Object)("Create QuartzService(" + this.jndiName + ")..."));
        if (this.error) {
            this.log.error((Object)"Must specify only one of 'Properties' or 'PropertiesFile'");
            throw new Exception("Must specify only one of 'Properties' or 'PropertiesFile'");
        }
        this.schedulerFactory = new StdSchedulerFactory();
        try {
            if (this.useProperties) {
                this.schedulerFactory.initialize(this.properties);
            }
            if (this.usePropertiesFile) {
                this.schedulerFactory.initialize(this.propertiesFile);
            }
        }
        catch (Exception e) {
            this.log.error((Object)"Failed to initialize Scheduler", (Throwable)e);
            throw new SchedulerConfigException("Failed to initialize Scheduler - ", e);
        }
        this.log.info((Object)("QuartzService(" + this.jndiName + ") created."));
    }

    public void destroyService() throws Exception {
        this.log.info((Object)("Destroy QuartzService(" + this.jndiName + ")..."));
        this.schedulerFactory = null;
        this.log.info((Object)("QuartzService(" + this.jndiName + ") destroyed."));
    }

    public void startService() throws Exception {
        this.log.info((Object)("Start QuartzService(" + this.jndiName + ")..."));
        try {
            this.rebind();
        }
        catch (NamingException ne) {
            this.log.error((Object)"Failed to rebind Scheduler", (Throwable)ne);
            throw new SchedulerConfigException("Failed to rebind Scheduler - ", ne);
        }
        try {
            Scheduler scheduler = this.schedulerFactory.getScheduler();
            if (this.startScheduler) {
                scheduler.start();
            } else {
                this.log.info((Object)"Skipping starting the scheduler (will not run jobs).");
            }
        }
        catch (Exception e) {
            this.log.error((Object)"Failed to start Scheduler", (Throwable)e);
            throw new SchedulerConfigException("Failed to start Scheduler - ", e);
        }
        this.log.info((Object)("QuartzService(" + this.jndiName + ") started."));
    }

    public void stopService() throws Exception {
        this.log.info((Object)("Stop QuartzService(" + this.jndiName + ")..."));
        try {
            Scheduler scheduler = this.schedulerFactory.getScheduler();
            scheduler.shutdown();
        }
        catch (Exception e) {
            this.log.error((Object)"Failed to shutdown Scheduler", (Throwable)e);
            throw new SchedulerConfigException("Failed to shutdown Scheduler - ", e);
        }
        this.unbind(this.jndiName);
        this.log.info((Object)("QuartzService(" + this.jndiName + ") stopped."));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void rebind() throws NamingException, SchedulerException {
        InitialContext rootCtx = null;
        try {
            rootCtx = new InitialContext();
            Name fullName = rootCtx.getNameParser("").parse(this.jndiName);
            Scheduler scheduler = this.schedulerFactory.getScheduler();
            NonSerializableFactory.rebind((Name)fullName, (Object)scheduler, (boolean)true);
        }
        finally {
            if (rootCtx != null) {
                try {
                    rootCtx.close();
                }
                catch (NamingException namingException) {}
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void unbind(String name) {
        InitialContext rootCtx = null;
        try {
            rootCtx = new InitialContext();
            rootCtx.unbind(name);
            NonSerializableFactory.unbind((String)name);
        }
        catch (NamingException e) {
            this.log.warn((Object)("Failed to unbind scheduler with jndiName: " + name), (Throwable)e);
        }
        finally {
            if (rootCtx != null) {
                try {
                    rootCtx.close();
                }
                catch (NamingException namingException) {}
            }
        }
    }
}

