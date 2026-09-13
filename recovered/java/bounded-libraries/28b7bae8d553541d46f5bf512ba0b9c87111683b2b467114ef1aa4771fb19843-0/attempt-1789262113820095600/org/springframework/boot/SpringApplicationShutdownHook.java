/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationListener
 *  org.springframework.context.ConfigurableApplicationContext
 *  org.springframework.context.event.ContextClosedEvent
 *  org.springframework.util.Assert
 */
package org.springframework.boot;

import java.security.AccessControlException;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplicationShutdownHandlers;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.util.Assert;

class SpringApplicationShutdownHook
implements Runnable {
    private static final int SLEEP = 50;
    private static final long TIMEOUT = TimeUnit.MINUTES.toMillis(10L);
    private static final Log logger = LogFactory.getLog(SpringApplicationShutdownHook.class);
    private final Handlers handlers = new Handlers();
    private final Set<ConfigurableApplicationContext> contexts = new LinkedHashSet<ConfigurableApplicationContext>();
    private final Set<ConfigurableApplicationContext> closedContexts = Collections.newSetFromMap(new WeakHashMap());
    private final ApplicationContextClosedListener contextCloseListener = new ApplicationContextClosedListener();
    private final AtomicBoolean shutdownHookAdded = new AtomicBoolean();
    private boolean inProgress;

    SpringApplicationShutdownHook() {
    }

    SpringApplicationShutdownHandlers getHandlers() {
        return this.handlers;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void registerApplicationContext(ConfigurableApplicationContext context) {
        this.addRuntimeShutdownHookIfNecessary();
        Class<SpringApplicationShutdownHook> clazz = SpringApplicationShutdownHook.class;
        synchronized (SpringApplicationShutdownHook.class) {
            this.assertNotInProgress();
            context.addApplicationListener((ApplicationListener)this.contextCloseListener);
            this.contexts.add(context);
            // ** MonitorExit[var2_2] (shouldn't be in output)
            return;
        }
    }

    private void addRuntimeShutdownHookIfNecessary() {
        if (this.shutdownHookAdded.compareAndSet(false, true)) {
            this.addRuntimeShutdownHook();
        }
    }

    void addRuntimeShutdownHook() {
        try {
            Runtime.getRuntime().addShutdownHook(new Thread((Runnable)this, "SpringApplicationShutdownHook"));
        }
        catch (AccessControlException accessControlException) {
            // empty catch block
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void deregisterFailedApplicationContext(ConfigurableApplicationContext applicationContext) {
        Class<SpringApplicationShutdownHook> clazz = SpringApplicationShutdownHook.class;
        synchronized (SpringApplicationShutdownHook.class) {
            Assert.state((!applicationContext.isActive() ? 1 : 0) != 0, (String)"Cannot unregister active application context");
            this.contexts.remove(applicationContext);
            // ** MonitorExit[var2_2] (shouldn't be in output)
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        Class<SpringApplicationShutdownHook> clazz = SpringApplicationShutdownHook.class;
        synchronized (SpringApplicationShutdownHook.class) {
            this.inProgress = true;
            LinkedHashSet<ConfigurableApplicationContext> contexts = new LinkedHashSet<ConfigurableApplicationContext>(this.contexts);
            LinkedHashSet<ConfigurableApplicationContext> closedContexts = new LinkedHashSet<ConfigurableApplicationContext>(this.closedContexts);
            LinkedHashSet<Runnable> actions = new LinkedHashSet<Runnable>(this.handlers.getActions());
            // ** MonitorExit[var4_1] (shouldn't be in output)
            contexts.forEach(this::closeAndWait);
            closedContexts.forEach(this::closeAndWait);
            actions.forEach(Runnable::run);
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    boolean isApplicationContextRegistered(ConfigurableApplicationContext context) {
        Class<SpringApplicationShutdownHook> clazz = SpringApplicationShutdownHook.class;
        synchronized (SpringApplicationShutdownHook.class) {
            // ** MonitorExit[var2_2] (shouldn't be in output)
            return this.contexts.contains(context);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void reset() {
        Class<SpringApplicationShutdownHook> clazz = SpringApplicationShutdownHook.class;
        synchronized (SpringApplicationShutdownHook.class) {
            this.contexts.clear();
            this.closedContexts.clear();
            this.handlers.getActions().clear();
            this.inProgress = false;
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return;
        }
    }

    private void closeAndWait(ConfigurableApplicationContext context) {
        if (!context.isActive()) {
            return;
        }
        context.close();
        try {
            int waited = 0;
            while (context.isActive()) {
                if ((long)waited > TIMEOUT) {
                    throw new TimeoutException();
                }
                Thread.sleep(50L);
                waited += 50;
            }
        }
        catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            logger.warn((Object)("Interrupted waiting for application context " + context + " to become inactive"));
        }
        catch (TimeoutException ex) {
            logger.warn((Object)("Timed out waiting for application context " + context + " to become inactive"), (Throwable)ex);
        }
    }

    private void assertNotInProgress() {
        Assert.state((!this.inProgress ? 1 : 0) != 0, (String)"Shutdown in progress");
    }

    private class ApplicationContextClosedListener
    implements ApplicationListener<ContextClosedEvent> {
        private ApplicationContextClosedListener() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void onApplicationEvent(ContextClosedEvent event) {
            Class<SpringApplicationShutdownHook> clazz = SpringApplicationShutdownHook.class;
            synchronized (SpringApplicationShutdownHook.class) {
                ApplicationContext applicationContext = event.getApplicationContext();
                SpringApplicationShutdownHook.this.contexts.remove(applicationContext);
                SpringApplicationShutdownHook.this.closedContexts.add((ConfigurableApplicationContext)applicationContext);
                // ** MonitorExit[var2_2] (shouldn't be in output)
                return;
            }
        }
    }

    private class Handlers
    implements SpringApplicationShutdownHandlers {
        private final Set<Runnable> actions = Collections.newSetFromMap(new IdentityHashMap());

        private Handlers() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void add(Runnable action) {
            Assert.notNull((Object)action, (String)"Action must not be null");
            Class<SpringApplicationShutdownHook> clazz = SpringApplicationShutdownHook.class;
            synchronized (SpringApplicationShutdownHook.class) {
                SpringApplicationShutdownHook.this.assertNotInProgress();
                this.actions.add(action);
                // ** MonitorExit[var2_2] (shouldn't be in output)
                return;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void remove(Runnable action) {
            Assert.notNull((Object)action, (String)"Action must not be null");
            Class<SpringApplicationShutdownHook> clazz = SpringApplicationShutdownHook.class;
            synchronized (SpringApplicationShutdownHook.class) {
                SpringApplicationShutdownHook.this.assertNotInProgress();
                this.actions.remove(action);
                // ** MonitorExit[var2_2] (shouldn't be in output)
                return;
            }
        }

        Set<Runnable> getActions() {
            return this.actions;
        }
    }
}

