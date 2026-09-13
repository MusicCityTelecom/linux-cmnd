/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.beans.factory.BeanInitializationException
 *  org.springframework.beans.factory.DisposableBean
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationContextAware
 *  org.springframework.context.ApplicationListener
 *  org.springframework.context.SmartLifecycle
 *  org.springframework.context.event.ContextRefreshedEvent
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 */
package org.springframework.jms.config;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerEndpoint;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

public class JmsListenerEndpointRegistry
implements DisposableBean,
SmartLifecycle,
ApplicationContextAware,
ApplicationListener<ContextRefreshedEvent> {
    protected final Log logger = LogFactory.getLog(this.getClass());
    private final Map<String, MessageListenerContainer> listenerContainers = new ConcurrentHashMap<String, MessageListenerContainer>();
    private int phase = Integer.MAX_VALUE;
    @Nullable
    private ApplicationContext applicationContext;
    private boolean contextRefreshed;

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext() == this.applicationContext) {
            this.contextRefreshed = true;
        }
    }

    @Nullable
    public MessageListenerContainer getListenerContainer(String id) {
        Assert.notNull((Object)id, (String)"Container identifier must not be null");
        return this.listenerContainers.get(id);
    }

    public Set<String> getListenerContainerIds() {
        return Collections.unmodifiableSet(this.listenerContainers.keySet());
    }

    public Collection<MessageListenerContainer> getListenerContainers() {
        return Collections.unmodifiableCollection(this.listenerContainers.values());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void registerListenerContainer(JmsListenerEndpoint endpoint, JmsListenerContainerFactory<?> factory, boolean startImmediately) {
        Assert.notNull((Object)endpoint, (String)"Endpoint must not be null");
        Assert.notNull(factory, (String)"Factory must not be null");
        String id = endpoint.getId();
        Assert.hasText((String)id, (String)"Endpoint id must be set");
        Map<String, MessageListenerContainer> map = this.listenerContainers;
        synchronized (map) {
            if (this.listenerContainers.containsKey(id)) {
                throw new IllegalStateException("Another endpoint is already registered with id '" + id + "'");
            }
            MessageListenerContainer container = this.createListenerContainer(endpoint, factory);
            this.listenerContainers.put(id, container);
            if (startImmediately) {
                this.startIfNecessary(container);
            }
        }
    }

    public void registerListenerContainer(JmsListenerEndpoint endpoint, JmsListenerContainerFactory<?> factory) {
        this.registerListenerContainer(endpoint, factory, false);
    }

    protected MessageListenerContainer createListenerContainer(JmsListenerEndpoint endpoint, JmsListenerContainerFactory<?> factory) {
        int containerPhase;
        Object listenerContainer = factory.createListenerContainer(endpoint);
        if (listenerContainer instanceof InitializingBean) {
            try {
                ((InitializingBean)listenerContainer).afterPropertiesSet();
            }
            catch (Exception ex) {
                throw new BeanInitializationException("Failed to initialize message listener container", (Throwable)ex);
            }
        }
        if ((containerPhase = listenerContainer.getPhase()) < Integer.MAX_VALUE) {
            if (this.phase < Integer.MAX_VALUE && this.phase != containerPhase) {
                throw new IllegalStateException("Encountered phase mismatch between container factory definitions: " + this.phase + " vs " + containerPhase);
            }
            this.phase = listenerContainer.getPhase();
        }
        return listenerContainer;
    }

    public int getPhase() {
        return this.phase;
    }

    public void start() {
        for (MessageListenerContainer listenerContainer : this.getListenerContainers()) {
            this.startIfNecessary(listenerContainer);
        }
    }

    public void stop() {
        for (MessageListenerContainer listenerContainer : this.getListenerContainers()) {
            listenerContainer.stop();
        }
    }

    public void stop(Runnable callback) {
        Collection<MessageListenerContainer> listenerContainers = this.getListenerContainers();
        AggregatingCallback aggregatingCallback = new AggregatingCallback(listenerContainers.size(), callback);
        for (MessageListenerContainer listenerContainer : listenerContainers) {
            listenerContainer.stop(aggregatingCallback);
        }
    }

    public boolean isRunning() {
        for (MessageListenerContainer listenerContainer : this.getListenerContainers()) {
            if (!listenerContainer.isRunning()) continue;
            return true;
        }
        return false;
    }

    private void startIfNecessary(MessageListenerContainer listenerContainer) {
        if (this.contextRefreshed || listenerContainer.isAutoStartup()) {
            listenerContainer.start();
        }
    }

    public void destroy() {
        for (MessageListenerContainer listenerContainer : this.getListenerContainers()) {
            if (!(listenerContainer instanceof DisposableBean)) continue;
            try {
                ((DisposableBean)listenerContainer).destroy();
            }
            catch (Throwable ex) {
                this.logger.warn((Object)"Failed to destroy message listener container", ex);
            }
        }
    }

    private static class AggregatingCallback
    implements Runnable {
        private final AtomicInteger count;
        private final Runnable finishCallback;

        public AggregatingCallback(int count, Runnable finishCallback) {
            this.count = new AtomicInteger(count);
            this.finishCallback = finishCallback;
        }

        @Override
        public void run() {
            if (this.count.decrementAndGet() == 0) {
                this.finishCallback.run();
            }
        }
    }
}

