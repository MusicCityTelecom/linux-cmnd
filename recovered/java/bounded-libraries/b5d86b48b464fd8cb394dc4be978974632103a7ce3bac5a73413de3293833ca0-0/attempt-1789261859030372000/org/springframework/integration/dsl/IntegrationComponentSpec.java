/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.DisposableBean
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.beans.factory.config.AbstractFactoryBean
 *  org.springframework.context.Lifecycle
 *  org.springframework.context.SmartLifecycle
 *  org.springframework.expression.spel.standard.SpelExpressionParser
 */
package org.springframework.integration.dsl;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.context.Lifecycle;
import org.springframework.context.SmartLifecycle;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.integration.dsl.IntegrationDsl;

@IntegrationDsl
public abstract class IntegrationComponentSpec<S extends IntegrationComponentSpec<S, T>, T>
extends AbstractFactoryBean<T>
implements SmartLifecycle {
    protected static final SpelExpressionParser PARSER = new SpelExpressionParser();
    protected volatile T target;
    private String id;

    protected S id(String idToSet) {
        this.id = idToSet;
        return this._this();
    }

    public final String getId() {
        return this.id;
    }

    public T get() {
        if (this.target == null) {
            this.target = this.doGet();
        }
        return this.target;
    }

    public Class<?> getObjectType() {
        return this.get().getClass();
    }

    protected T createInstance() {
        T instance = this.get();
        if (instance instanceof InitializingBean) {
            try {
                ((InitializingBean)instance).afterPropertiesSet();
            }
            catch (Exception e) {
                throw new IllegalStateException("Cannot initialize bean: " + instance, e);
            }
        }
        return instance;
    }

    protected void destroyInstance(T instance) {
        if (instance instanceof DisposableBean) {
            try {
                ((DisposableBean)instance).destroy();
            }
            catch (Exception e) {
                throw new IllegalStateException("Cannot destroy bean: " + instance, e);
            }
        }
    }

    public void start() {
        T instance = this.get();
        if (instance instanceof Lifecycle) {
            ((Lifecycle)instance).start();
        }
    }

    public void stop() {
        T instance = this.get();
        if (instance instanceof Lifecycle) {
            ((Lifecycle)instance).stop();
        }
    }

    public boolean isRunning() {
        T instance = this.get();
        return !(instance instanceof Lifecycle) || ((Lifecycle)instance).isRunning();
    }

    public boolean isAutoStartup() {
        T instance = this.get();
        return instance instanceof SmartLifecycle && ((SmartLifecycle)instance).isAutoStartup();
    }

    public void stop(Runnable callback) {
        T instance = this.get();
        if (instance instanceof SmartLifecycle) {
            ((SmartLifecycle)instance).stop(callback);
        } else {
            callback.run();
        }
    }

    public int getPhase() {
        T instance = this.get();
        if (instance instanceof SmartLifecycle) {
            return ((SmartLifecycle)instance).getPhase();
        }
        return 0;
    }

    protected final S _this() {
        return (S)((Object)this);
    }

    protected T doGet() {
        throw new UnsupportedOperationException();
    }
}

