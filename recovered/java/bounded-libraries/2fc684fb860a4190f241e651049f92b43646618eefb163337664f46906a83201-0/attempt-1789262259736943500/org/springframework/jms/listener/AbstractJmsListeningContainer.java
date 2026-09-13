/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.Connection
 *  javax.jms.IllegalStateException
 *  javax.jms.JMSException
 *  org.springframework.beans.factory.BeanNameAware
 *  org.springframework.beans.factory.DisposableBean
 *  org.springframework.context.SmartLifecycle
 *  org.springframework.lang.Nullable
 *  org.springframework.util.ClassUtils
 */
package org.springframework.jms.listener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.jms.Connection;
import javax.jms.IllegalStateException;
import javax.jms.JMSException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.SmartLifecycle;
import org.springframework.jms.JmsException;
import org.springframework.jms.connection.ConnectionFactoryUtils;
import org.springframework.jms.support.JmsUtils;
import org.springframework.jms.support.destination.JmsDestinationAccessor;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;

public abstract class AbstractJmsListeningContainer
extends JmsDestinationAccessor
implements BeanNameAware,
DisposableBean,
SmartLifecycle {
    @Nullable
    private String clientId;
    private boolean autoStartup = true;
    private int phase = Integer.MAX_VALUE;
    @Nullable
    private String beanName;
    @Nullable
    private Connection sharedConnection;
    private boolean sharedConnectionStarted = false;
    protected final Object sharedConnectionMonitor = new Object();
    private boolean active = false;
    private volatile boolean running;
    private final List<Object> pausedTasks = new ArrayList<Object>();
    protected final Object lifecycleMonitor = new Object();

    public void setClientId(@Nullable String clientId) {
        this.clientId = clientId;
    }

    @Nullable
    public String getClientId() {
        return this.clientId;
    }

    public void setAutoStartup(boolean autoStartup) {
        this.autoStartup = autoStartup;
    }

    public boolean isAutoStartup() {
        return this.autoStartup;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    public int getPhase() {
        return this.phase;
    }

    public void setBeanName(@Nullable String beanName) {
        this.beanName = beanName;
    }

    @Nullable
    protected final String getBeanName() {
        return this.beanName;
    }

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        this.validateConfiguration();
        this.initialize();
    }

    protected void validateConfiguration() {
    }

    public void destroy() {
        this.shutdown();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void initialize() throws JmsException {
        try {
            Object object = this.lifecycleMonitor;
            synchronized (object) {
                this.active = true;
                this.lifecycleMonitor.notifyAll();
            }
            this.doInitialize();
        }
        catch (JMSException ex) {
            Object object = this.sharedConnectionMonitor;
            synchronized (object) {
                ConnectionFactoryUtils.releaseConnection(this.sharedConnection, this.getConnectionFactory(), this.autoStartup);
                this.sharedConnection = null;
            }
            throw this.convertJmsAccessException(ex);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void shutdown() throws JmsException {
        boolean wasRunning;
        this.logger.debug((Object)"Shutting down JMS listener container");
        Object object = this.lifecycleMonitor;
        synchronized (object) {
            wasRunning = this.running;
            this.running = false;
            this.active = false;
            this.pausedTasks.clear();
            this.lifecycleMonitor.notifyAll();
        }
        if (wasRunning && this.sharedConnectionEnabled()) {
            try {
                this.stopSharedConnection();
            }
            catch (Throwable ex) {
                this.logger.debug((Object)"Could not stop JMS Connection on shutdown", ex);
            }
        }
        try {
            this.doShutdown();
        }
        catch (JMSException ex) {
            throw this.convertJmsAccessException(ex);
        }
        finally {
            if (this.sharedConnectionEnabled()) {
                Object object2 = this.sharedConnectionMonitor;
                synchronized (object2) {
                    ConnectionFactoryUtils.releaseConnection(this.sharedConnection, this.getConnectionFactory(), false);
                    this.sharedConnection = null;
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final boolean isActive() {
        Object object = this.lifecycleMonitor;
        synchronized (object) {
            return this.active;
        }
    }

    public void start() throws JmsException {
        try {
            this.doStart();
        }
        catch (JMSException ex) {
            throw this.convertJmsAccessException(ex);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void doStart() throws JMSException {
        if (this.sharedConnectionEnabled()) {
            this.establishSharedConnection();
        }
        Object object = this.lifecycleMonitor;
        synchronized (object) {
            this.running = true;
            this.lifecycleMonitor.notifyAll();
            this.resumePausedTasks();
        }
        if (this.sharedConnectionEnabled()) {
            this.startSharedConnection();
        }
    }

    public void stop() throws JmsException {
        try {
            this.doStop();
        }
        catch (JMSException ex) {
            throw this.convertJmsAccessException(ex);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void doStop() throws JMSException {
        Object object = this.lifecycleMonitor;
        synchronized (object) {
            this.running = false;
            this.lifecycleMonitor.notifyAll();
        }
        if (this.sharedConnectionEnabled()) {
            this.stopSharedConnection();
        }
    }

    public final boolean isRunning() {
        return this.running && this.runningAllowed();
    }

    protected boolean runningAllowed() {
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void establishSharedConnection() throws JMSException {
        Object object = this.sharedConnectionMonitor;
        synchronized (object) {
            if (this.sharedConnection == null) {
                this.sharedConnection = this.createSharedConnection();
                this.logger.debug((Object)"Established shared JMS Connection");
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected final void refreshSharedConnection() throws JMSException {
        Object object = this.sharedConnectionMonitor;
        synchronized (object) {
            ConnectionFactoryUtils.releaseConnection(this.sharedConnection, this.getConnectionFactory(), this.sharedConnectionStarted);
            this.sharedConnection = null;
            this.sharedConnection = this.createSharedConnection();
            if (this.sharedConnectionStarted) {
                this.sharedConnection.start();
            }
        }
    }

    protected Connection createSharedConnection() throws JMSException {
        Connection con = this.createConnection();
        try {
            this.prepareSharedConnection(con);
            return con;
        }
        catch (JMSException ex) {
            JmsUtils.closeConnection(con);
            throw ex;
        }
    }

    protected void prepareSharedConnection(Connection connection) throws JMSException {
        String clientId = this.getClientId();
        if (clientId != null) {
            connection.setClientID(clientId);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void startSharedConnection() throws JMSException {
        Object object = this.sharedConnectionMonitor;
        synchronized (object) {
            this.sharedConnectionStarted = true;
            if (this.sharedConnection != null) {
                try {
                    this.sharedConnection.start();
                }
                catch (IllegalStateException ex) {
                    this.logger.debug((Object)("Ignoring Connection start exception - assuming already started: " + (Object)((Object)ex)));
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void stopSharedConnection() throws JMSException {
        Object object = this.sharedConnectionMonitor;
        synchronized (object) {
            this.sharedConnectionStarted = false;
            if (this.sharedConnection != null) {
                try {
                    this.sharedConnection.stop();
                }
                catch (IllegalStateException ex) {
                    this.logger.debug((Object)("Ignoring Connection stop exception - assuming already stopped: " + (Object)((Object)ex)));
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected final Connection getSharedConnection() {
        if (!this.sharedConnectionEnabled()) {
            throw new java.lang.IllegalStateException("This listener container does not maintain a shared Connection");
        }
        Object object = this.sharedConnectionMonitor;
        synchronized (object) {
            if (this.sharedConnection == null) {
                throw new SharedConnectionNotInitializedException("This listener container's shared Connection has not been initialized yet");
            }
            return this.sharedConnection;
        }
    }

    protected final boolean rescheduleTaskIfNecessary(Object task) {
        if (this.running) {
            try {
                this.doRescheduleTask(task);
            }
            catch (RuntimeException ex) {
                this.logRejectedTask(task, ex);
                this.pausedTasks.add(task);
            }
            return true;
        }
        if (this.active) {
            this.pausedTasks.add(task);
            return true;
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void resumePausedTasks() {
        Object object = this.lifecycleMonitor;
        synchronized (object) {
            if (!this.pausedTasks.isEmpty()) {
                Iterator<Object> it = this.pausedTasks.iterator();
                while (it.hasNext()) {
                    Object task = it.next();
                    try {
                        this.doRescheduleTask(task);
                        it.remove();
                        if (!this.logger.isDebugEnabled()) continue;
                        this.logger.debug((Object)("Resumed paused task: " + task));
                    }
                    catch (RuntimeException ex) {
                        this.logRejectedTask(task, ex);
                    }
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int getPausedTaskCount() {
        Object object = this.lifecycleMonitor;
        synchronized (object) {
            return this.pausedTasks.size();
        }
    }

    protected void doRescheduleTask(Object task) {
        throw new UnsupportedOperationException(ClassUtils.getShortName(this.getClass()) + " does not support rescheduling of tasks");
    }

    protected void logRejectedTask(Object task, RuntimeException ex) {
        if (this.logger.isWarnEnabled()) {
            this.logger.warn((Object)("Listener container task [" + task + "] has been rejected and paused: " + ex));
        }
    }

    protected abstract boolean sharedConnectionEnabled();

    protected abstract void doInitialize() throws JMSException;

    protected abstract void doShutdown() throws JMSException;

    public static class SharedConnectionNotInitializedException
    extends RuntimeException {
        protected SharedConnectionNotInitializedException(String msg) {
            super(msg);
        }
    }
}

