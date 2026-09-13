/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.DisposableBean
 *  org.springframework.beans.factory.NoSuchBeanDefinitionException
 *  org.springframework.util.PatternMatchUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.endpoint;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.integration.context.IntegrationObjectSupport;
import org.springframework.integration.support.SmartLifecycleRoleController;
import org.springframework.integration.support.management.IntegrationManagedResource;
import org.springframework.integration.support.management.ManageableSmartLifecycle;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StringUtils;

@IntegrationManagedResource
public abstract class AbstractEndpoint
extends IntegrationObjectSupport
implements ManageableSmartLifecycle,
DisposableBean {
    protected final ReentrantLock lifecycleLock = new ReentrantLock();
    protected final Condition lifecycleCondition = this.lifecycleLock.newCondition();
    private String role;
    private SmartLifecycleRoleController roleController;
    private boolean autoStartup = true;
    private boolean autoStartupSetExplicitly;
    private int phase = 0;
    private volatile boolean running;
    private volatile boolean active;

    public void setAutoStartup(boolean autoStartup) {
        this.autoStartup = autoStartup;
        this.autoStartupSetExplicitly = true;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }

    @Override
    protected void onInit() {
        super.onInit();
        if (!this.autoStartupSetExplicitly) {
            String[] endpointNamePatterns;
            for (String pattern : endpointNamePatterns = this.getIntegrationProperty("spring.integration.endpoints.noAutoStartup", String[].class)) {
                if (!PatternMatchUtils.simpleMatch((String)pattern, (String)this.getComponentName())) continue;
                this.autoStartup = false;
                break;
            }
        }
        if (StringUtils.hasText((String)this.role)) {
            try {
                this.roleController = (SmartLifecycleRoleController)this.getBeanFactory().getBean("integrationLifecycleRoleController", SmartLifecycleRoleController.class);
                this.roleController.addLifecycleToRole(this.role, this);
            }
            catch (NoSuchBeanDefinitionException e) {
                this.logger.trace((CharSequence)"No LifecycleRoleController in the context");
            }
        }
    }

    public void destroy() {
        this.stop();
        if (this.roleController != null) {
            this.roleController.removeLifecycle(this);
        }
    }

    public final boolean isAutoStartup() {
        return this.autoStartup;
    }

    public final int getPhase() {
        return this.phase;
    }

    @Override
    public final boolean isRunning() {
        return this.running;
    }

    @Override
    public final void start() {
        this.lifecycleLock.lock();
        try {
            if (!this.running) {
                this.active = true;
                this.doStart();
                this.running = true;
                this.logger.info(() -> "started " + this);
            }
        }
        finally {
            this.lifecycleLock.unlock();
        }
    }

    @Override
    public final void stop() {
        this.lifecycleLock.lock();
        try {
            if (this.running) {
                this.active = false;
                this.doStop();
                this.running = false;
                this.logger.info(() -> "stopped " + this);
            }
        }
        finally {
            this.lifecycleLock.unlock();
        }
    }

    public final void stop(Runnable callback) {
        this.lifecycleLock.lock();
        try {
            if (this.running) {
                this.active = false;
                this.doStop(callback);
                this.running = false;
                this.logger.info(() -> "stopped " + this);
            } else {
                callback.run();
            }
        }
        finally {
            this.lifecycleLock.unlock();
        }
    }

    protected void doStop(Runnable callback) {
        this.doStop();
        callback.run();
    }

    public boolean isActive() {
        return this.active;
    }

    protected abstract void doStart();

    protected abstract void doStop();
}

