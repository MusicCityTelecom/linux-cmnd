/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanNameAware
 *  org.springframework.context.SmartLifecycle
 *  org.springframework.messaging.MessageChannel
 */
package org.springframework.integration.dsl;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.SmartLifecycle;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlowDefinition;
import org.springframework.integration.support.context.NamedComponent;
import org.springframework.messaging.MessageChannel;

public class StandardIntegrationFlow
implements IntegrationFlow,
SmartLifecycle,
BeanNameAware,
NamedComponent {
    private final Map<Object, String> integrationComponents;
    private MessageChannel inputChannel;
    private boolean running;
    private String beanName;

    StandardIntegrationFlow(Map<Object, String> integrationComponents) {
        this.integrationComponents = new LinkedHashMap<Object, String>(integrationComponents);
    }

    public void setBeanName(String name) {
        this.beanName = name;
    }

    @Override
    public String getComponentName() {
        return this.beanName;
    }

    @Override
    public String getComponentType() {
        return "integration-flow";
    }

    @Override
    public void configure(IntegrationFlowDefinition<?> flow) {
        throw new UnsupportedOperationException();
    }

    @Override
    public MessageChannel getInputChannel() {
        if (this.inputChannel == null) {
            this.inputChannel = this.integrationComponents.keySet().stream().filter(MessageChannel.class::isInstance).map(MessageChannel.class::cast).findFirst().orElseThrow(() -> new IllegalStateException("The 'IntegrationFlow' [" + this + "] doesn't start with 'MessageChannel' for direct message sending."));
        }
        return this.inputChannel;
    }

    public void setIntegrationComponents(Map<Object, String> integrationComponents) {
        this.integrationComponents.clear();
        this.integrationComponents.putAll(integrationComponents);
    }

    @Override
    public Map<Object, String> getIntegrationComponents() {
        return Collections.unmodifiableMap(this.integrationComponents);
    }

    public void start() {
        if (!this.running) {
            LinkedList<Object> components = new LinkedList<Object>(this.integrationComponents.keySet());
            ListIterator iterator = components.listIterator(this.integrationComponents.size());
            while (iterator.hasPrevious()) {
                Object component = iterator.previous();
                if (!(component instanceof SmartLifecycle)) continue;
                ((SmartLifecycle)component).start();
            }
            this.running = true;
        }
    }

    public void stop(Runnable callback) {
        AggregatingCallback aggregatingCallback = new AggregatingCallback(this.integrationComponents.size(), callback);
        for (Object component : this.integrationComponents.keySet()) {
            SmartLifecycle lifecycle;
            if (component instanceof SmartLifecycle && (lifecycle = (SmartLifecycle)component).isRunning()) {
                lifecycle.stop((Runnable)aggregatingCallback);
                continue;
            }
            aggregatingCallback.run();
        }
        this.running = false;
    }

    public void stop() {
        for (Object component : this.integrationComponents.keySet()) {
            SmartLifecycle lifecycle;
            if (!(component instanceof SmartLifecycle) || !(lifecycle = (SmartLifecycle)component).isRunning()) continue;
            lifecycle.stop();
        }
        this.running = false;
    }

    public boolean isRunning() {
        return this.running;
    }

    public boolean isAutoStartup() {
        return false;
    }

    public int getPhase() {
        return 0;
    }

    public String toString() {
        return "StandardIntegrationFlow{integrationComponents=" + this.integrationComponents + '}';
    }

    private static final class AggregatingCallback
    implements Runnable {
        private final AtomicInteger count;
        private final Runnable finishCallback;

        AggregatingCallback(int count, Runnable finishCallback) {
            this.count = new AtomicInteger(count);
            this.finishCallback = finishCallback;
        }

        @Override
        public void run() {
            if (this.count.decrementAndGet() <= 0) {
                this.finishCallback.run();
            }
        }
    }
}

