/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.aop.TargetSource
 *  org.springframework.aop.framework.Advised
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationContextAware
 *  org.springframework.context.Lifecycle
 *  org.springframework.integration.channel.AbstractMessageChannel
 *  org.springframework.integration.channel.QueueChannel
 *  org.springframework.integration.config.IntegrationManagementConfigurer
 *  org.springframework.integration.context.OrderlyShutdownCapable
 *  org.springframework.integration.core.MessageProducer
 *  org.springframework.integration.core.MessageSource
 *  org.springframework.integration.endpoint.AbstractEndpoint
 *  org.springframework.integration.endpoint.AbstractMessageSource
 *  org.springframework.integration.endpoint.IntegrationConsumer
 *  org.springframework.integration.endpoint.SourcePollingChannelAdapter
 *  org.springframework.integration.gateway.MessagingGatewaySupport
 *  org.springframework.integration.handler.AbstractMessageHandler
 *  org.springframework.integration.handler.AbstractMessageProducingHandler
 *  org.springframework.integration.history.MessageHistoryConfigurer
 *  org.springframework.integration.support.context.NamedComponent
 *  org.springframework.integration.support.management.IntegrationInboundManagement
 *  org.springframework.integration.support.management.IntegrationManagement
 *  org.springframework.integration.support.management.ManageableLifecycle
 *  org.springframework.integration.support.utils.PatternMatchUtils
 *  org.springframework.jmx.export.MBeanExporter
 *  org.springframework.jmx.export.UnableToRegisterMBeanException
 *  org.springframework.jmx.export.annotation.ManagedAttribute
 *  org.springframework.jmx.export.annotation.ManagedMetric
 *  org.springframework.jmx.export.annotation.ManagedOperation
 *  org.springframework.jmx.export.annotation.ManagedResource
 *  org.springframework.jmx.export.assembler.MBeanInfoAssembler
 *  org.springframework.jmx.export.assembler.MetadataMBeanInfoAssembler
 *  org.springframework.jmx.export.metadata.JmxAttributeSource
 *  org.springframework.jmx.export.naming.MetadataNamingStrategy
 *  org.springframework.jmx.export.naming.ObjectNamingStrategy
 *  org.springframework.jmx.support.MetricType
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.MessageHandler
 *  org.springframework.util.Assert
 *  org.springframework.util.ReflectionUtils
 */
package org.springframework.integration.monitor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import javax.lang.model.SourceVersion;
import javax.management.DynamicMBean;
import javax.management.JMException;
import javax.management.ObjectName;
import javax.management.modelmbean.ModelMBean;
import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.Advised;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.Lifecycle;
import org.springframework.integration.channel.AbstractMessageChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.IntegrationManagementConfigurer;
import org.springframework.integration.context.OrderlyShutdownCapable;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.endpoint.AbstractEndpoint;
import org.springframework.integration.endpoint.AbstractMessageSource;
import org.springframework.integration.endpoint.IntegrationConsumer;
import org.springframework.integration.endpoint.SourcePollingChannelAdapter;
import org.springframework.integration.gateway.MessagingGatewaySupport;
import org.springframework.integration.handler.AbstractMessageHandler;
import org.springframework.integration.handler.AbstractMessageProducingHandler;
import org.springframework.integration.history.MessageHistoryConfigurer;
import org.springframework.integration.monitor.IntegrationJmxAttributeSource;
import org.springframework.integration.support.context.NamedComponent;
import org.springframework.integration.support.management.IntegrationInboundManagement;
import org.springframework.integration.support.management.IntegrationManagement;
import org.springframework.integration.support.management.ManageableLifecycle;
import org.springframework.integration.support.utils.PatternMatchUtils;
import org.springframework.jmx.export.MBeanExporter;
import org.springframework.jmx.export.UnableToRegisterMBeanException;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedMetric;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.jmx.export.assembler.MBeanInfoAssembler;
import org.springframework.jmx.export.assembler.MetadataMBeanInfoAssembler;
import org.springframework.jmx.export.metadata.JmxAttributeSource;
import org.springframework.jmx.export.naming.MetadataNamingStrategy;
import org.springframework.jmx.export.naming.ObjectNamingStrategy;
import org.springframework.jmx.support.MetricType;
import org.springframework.lang.Nullable;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

@ManagedResource
public class IntegrationMBeanExporter
extends MBeanExporter
implements ApplicationContextAware,
DestructionAwareBeanPostProcessor {
    public static final String DEFAULT_DOMAIN = "org.springframework.integration";
    private final IntegrationJmxAttributeSource attributeSource = new IntegrationJmxAttributeSource();
    private ApplicationContext applicationContext;
    private final Map<Object, AtomicLong> anonymousHandlerCounters = new HashMap<Object, AtomicLong>();
    private final Map<Object, AtomicLong> anonymousSourceCounters = new HashMap<Object, AtomicLong>();
    private final Map<String, IntegrationManagement> handlers = new HashMap<String, IntegrationManagement>();
    private final Map<String, IntegrationInboundManagement> sources = new HashMap<String, IntegrationInboundManagement>();
    private final Map<IntegrationInboundManagement, ManageableLifecycle> sourceLifecycles = new HashMap<IntegrationInboundManagement, ManageableLifecycle>();
    private final Set<Lifecycle> inboundLifecycleMessageProducers = new HashSet<Lifecycle>();
    private final Map<String, IntegrationManagement> channels = new HashMap<String, IntegrationManagement>();
    private final Map<Object, String> endpointsByMonitor = new HashMap<Object, String>();
    private final Map<Object, ObjectName> objectNames = new HashMap<Object, ObjectName>();
    private final Set<String> endpointNames = new HashSet<String>();
    private final AtomicBoolean shuttingDown = new AtomicBoolean();
    private final Properties objectNameStaticProperties = new Properties();
    private final Set<Object> runtimeBeans = new HashSet<Object>();
    private final MetadataNamingStrategy defaultNamingStrategy = new MetadataNamingStrategy((JmxAttributeSource)this.attributeSource);
    private String domain = "org.springframework.integration";
    private String[] componentNamePatterns = new String[]{"*"};
    private volatile long shutdownDeadline;
    private volatile boolean singletonsInstantiated;

    public IntegrationMBeanExporter() {
        this.setAutodetect(false);
        this.setNamingStrategy((ObjectNamingStrategy)this.defaultNamingStrategy);
        this.setAssembler((MBeanInfoAssembler)new MetadataMBeanInfoAssembler((JmxAttributeSource)this.attributeSource));
    }

    public void setObjectNameStaticProperties(Map<String, String> objectNameStaticProperties) {
        this.objectNameStaticProperties.putAll(objectNameStaticProperties);
    }

    public void setDefaultDomain(String domain) {
        this.domain = domain;
        this.defaultNamingStrategy.setDefaultDomain(domain);
    }

    public void setComponentNamePatterns(String[] componentNamePatterns) {
        Assert.notEmpty((Object[])componentNamePatterns, (String)"componentNamePatterns must not be empty");
        this.componentNamePatterns = Arrays.copyOf(componentNamePatterns, componentNamePatterns.length);
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Assert.notNull((Object)applicationContext, (String)"ApplicationContext may not be null");
        this.applicationContext = applicationContext;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        super.setBeanFactory(beanFactory);
        this.attributeSource.setBeanFactory(beanFactory);
    }

    public void afterSingletonsInstantiated() {
        this.populateMessageHandlers();
        this.populateMessageSources();
        this.populateMessageChannels();
        this.populateMessageProducers();
        super.afterSingletonsInstantiated();
        try {
            Object messageHistoryConfigurer;
            this.registerChannels();
            this.registerHandlers();
            this.registerSources();
            this.registerEndpoints();
            if (this.applicationContext.containsBean("messageHistoryConfigurer") && (messageHistoryConfigurer = this.applicationContext.getBean("messageHistoryConfigurer")) instanceof MessageHistoryConfigurer) {
                this.registerBeanInstance(messageHistoryConfigurer, "messageHistoryConfigurer");
            }
            this.configureManagementConfigurer();
            this.singletonsInstantiated = true;
        }
        catch (RuntimeException e) {
            this.unregisterBeans();
            throw e;
        }
    }

    private void populateMessageHandlers() {
        Map messageHandlers = this.applicationContext.getBeansOfType(MessageHandler.class);
        for (Map.Entry entry : messageHandlers.entrySet()) {
            String beanName = (String)entry.getKey();
            MessageHandler bean = (MessageHandler)entry.getValue();
            if (this.handlerInAnonymousWrapper(bean) != null) {
                if (!this.logger.isDebugEnabled()) continue;
                this.logger.debug((Object)("Skipping " + beanName + " because it wraps another handler"));
                continue;
            }
            MessageHandler monitor = (MessageHandler)this.extractTarget(bean);
            if (!(monitor instanceof IntegrationManagement)) continue;
            this.handlers.put(beanName, (IntegrationManagement)monitor);
        }
    }

    private void populateMessageSources() {
        this.applicationContext.getBeansOfType(IntegrationInboundManagement.class).values().stream().map(this::extractTarget).map(IntegrationInboundManagement.class::cast).forEach(src -> this.sources.put(src.getComponentName(), (IntegrationInboundManagement)src));
    }

    private void populateMessageChannels() {
        this.applicationContext.getBeansOfType(MessageChannel.class).values().stream().map(this::extractTarget).filter(ch -> ch instanceof IntegrationManagement).map(IntegrationManagement.class::cast).forEach(ch -> this.channels.put(ch.getComponentName(), (IntegrationManagement)ch));
    }

    private void populateMessageProducers() {
        this.applicationContext.getBeansOfType(MessageProducer.class).values().stream().filter(Lifecycle.class::isInstance).forEach(this::registerProducer);
    }

    private void configureManagementConfigurer() {
        if (!this.applicationContext.containsBean("integrationManagementConfigurer")) {
            IntegrationManagementConfigurer managementConfigurer = new IntegrationManagementConfigurer();
            managementConfigurer.setApplicationContext(this.applicationContext);
            managementConfigurer.setBeanName("integrationManagementConfigurer");
            managementConfigurer.afterSingletonsInstantiated();
        }
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (this.singletonsInstantiated) {
            try {
                if (bean instanceof MessageChannel) {
                    MessageChannel monitor = (MessageChannel)this.extractTarget(bean);
                    if (monitor instanceof IntegrationManagement) {
                        this.channels.put(beanName, (IntegrationManagement)monitor);
                        this.registerChannel((IntegrationManagement)monitor);
                        this.runtimeBeans.add(bean);
                    }
                } else if (bean instanceof MessageProducer && bean instanceof Lifecycle) {
                    this.registerProducer((MessageProducer)bean);
                    this.runtimeBeans.add(bean);
                } else if (bean instanceof AbstractEndpoint) {
                    this.postProcessAbstractEndpoint(bean);
                }
            }
            catch (Exception e) {
                this.logger.error((Object)("Could not register an MBean for: " + beanName), (Throwable)e);
            }
        }
        return bean;
    }

    private void postProcessAbstractEndpoint(Object bean) {
        SourcePollingChannelAdapter pollingChannelAdapter;
        MessageSource messageSource;
        if (bean instanceof IntegrationConsumer) {
            IntegrationConsumer integrationConsumer = (IntegrationConsumer)bean;
            MessageHandler handler = integrationConsumer.getHandler();
            MessageHandler monitor = (MessageHandler)this.extractTarget(handler);
            if (monitor instanceof IntegrationManagement) {
                this.registerHandler((IntegrationManagement)monitor, integrationConsumer);
                this.handlers.put(((IntegrationManagement)monitor).getComponentName(), (IntegrationManagement)monitor);
                this.runtimeBeans.add(monitor);
            }
            return;
        }
        if (bean instanceof SourcePollingChannelAdapter && (messageSource = (pollingChannelAdapter = (SourcePollingChannelAdapter)bean).getMessageSource()) instanceof IntegrationInboundManagement) {
            IntegrationInboundManagement monitor = (IntegrationInboundManagement)this.extractTarget(messageSource);
            this.registerSource(monitor);
            this.sourceLifecycles.put(monitor, (ManageableLifecycle)pollingChannelAdapter);
            this.runtimeBeans.add(monitor);
            return;
        }
        this.registerEndpoint((AbstractEndpoint)bean);
        this.runtimeBeans.add(bean);
    }

    private void registerProducer(MessageProducer messageProducer) {
        Lifecycle target = (Lifecycle)this.extractTarget(messageProducer);
        if (!(target instanceof AbstractMessageProducingHandler)) {
            this.inboundLifecycleMessageProducers.add(target);
        }
    }

    public boolean requiresDestruction(Object bean) {
        return bean instanceof AbstractMessageChannel || bean instanceof AbstractMessageHandler || bean instanceof AbstractMessageSource || bean instanceof MessageProducer && bean instanceof Lifecycle || bean instanceof AbstractEndpoint;
    }

    public void postProcessBeforeDestruction(Object bean, String beanName) throws BeansException {
        if (this.runtimeBeans.remove(bean)) {
            ObjectName objectName = this.objectNames.remove(bean);
            if (objectName != null) {
                this.doUnregister(objectName);
                if (bean instanceof AbstractEndpoint) {
                    this.endpointNames.remove(((AbstractEndpoint)bean).getComponentName());
                } else {
                    this.endpointsByMonitor.remove(bean);
                    if (bean instanceof IntegrationManagement) {
                        this.channels.remove(((NamedComponent)bean).getComponentName());
                    } else if (bean instanceof IntegrationManagement) {
                        this.handlers.remove(((NamedComponent)bean).getComponentName());
                        this.endpointNames.remove(((NamedComponent)bean).getComponentName());
                    } else if (bean instanceof IntegrationInboundManagement) {
                        this.sources.remove(((NamedComponent)bean).getComponentName());
                        this.endpointNames.remove(((NamedComponent)bean).getComponentName());
                    }
                }
            } else if (bean instanceof MessageProducer && bean instanceof Lifecycle) {
                this.inboundLifecycleMessageProducers.remove(bean);
            }
        }
    }

    private MessageHandler handlerInAnonymousWrapper(Object bean) {
        if (bean != null && bean.getClass().isAnonymousClass()) {
            AtomicReference wrapped = new AtomicReference();
            ReflectionUtils.doWithFields(bean.getClass(), field -> {
                field.setAccessible(true);
                Object handler = field.get(bean);
                if (handler instanceof MessageHandler) {
                    wrapped.set((MessageHandler)handler);
                }
            }, field -> wrapped.get() == null && field.getName().startsWith("val$"));
            return (MessageHandler)wrapped.get();
        }
        return null;
    }

    private ObjectName registerBeanInstance(Object bean, String beanKey) {
        try {
            ObjectName objectName = this.getObjectName(bean, beanKey);
            Object mbeanToExpose = null;
            if (this.isMBean(bean.getClass())) {
                mbeanToExpose = bean;
            } else {
                DynamicMBean adaptedBean = this.adaptMBeanIfPossible(bean);
                if (adaptedBean != null) {
                    mbeanToExpose = adaptedBean;
                }
            }
            if (mbeanToExpose != null) {
                if (this.logger.isInfoEnabled()) {
                    this.logger.info((Object)("Located MBean '" + beanKey + "': registering with JMX server as MBean [" + objectName + "]"));
                }
                this.doRegister(mbeanToExpose, objectName);
            } else {
                if (this.logger.isInfoEnabled()) {
                    this.logger.info((Object)("Located managed bean '" + beanKey + "': registering with JMX server as MBean [" + objectName + "]"));
                }
                ModelMBean mbean = this.createAndConfigureMBean(bean, beanKey);
                this.doRegister(mbean, objectName);
            }
            return objectName;
        }
        catch (JMException e) {
            throw new UnableToRegisterMBeanException("Unable to register MBean [" + bean + "] with key '" + beanKey + "'", (Throwable)e);
        }
    }

    @ManagedOperation
    public void stopActiveComponents(long howLong) {
        if (!this.shuttingDown.compareAndSet(false, true)) {
            this.logger.error((Object)"Shutdown already in process");
            return;
        }
        this.shutdownDeadline = System.currentTimeMillis() + howLong;
        try {
            this.logger.debug((Object)"Running shutdown");
            this.doShutdown();
        }
        catch (Exception e) {
            this.logger.error((Object)"Orderly shutdown failed", (Throwable)e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void doShutdown() {
        try {
            this.orderlyShutdownCapableComponentsBefore();
            this.stopActiveChannels();
            this.stopMessageSources();
            this.stopInboundMessageProducers();
            long timeLeft = this.shutdownDeadline - System.currentTimeMillis();
            if (timeLeft > 0L) {
                try {
                    Thread.sleep(timeLeft);
                }
                catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    this.logger.error((Object)"Interrupted while waiting for quiesce");
                }
            }
            this.orderlyShutdownCapableComponentsAfter();
        }
        finally {
            this.shuttingDown.set(false);
        }
    }

    @ManagedOperation
    public void stopMessageSources() {
        for (Lifecycle lifecycle : this.sourceLifecycles.values()) {
            if (this.logger.isInfoEnabled()) {
                this.logger.info((Object)("Stopping message source " + lifecycle));
            }
            lifecycle.stop();
        }
    }

    @ManagedOperation
    public void stopInboundMessageProducers() {
        for (Lifecycle producer : this.inboundLifecycleMessageProducers) {
            if (producer instanceof OrderlyShutdownCapable) continue;
            if (this.logger.isInfoEnabled()) {
                this.logger.info((Object)("Stopping message producer " + producer));
            }
            producer.stop();
        }
    }

    @ManagedOperation
    public void stopActiveChannels() {
        for (IntegrationManagement metrics : this.channels.values()) {
            IntegrationManagement channel = metrics;
            if (!(channel instanceof Lifecycle)) continue;
            if (this.logger.isInfoEnabled()) {
                this.logger.info((Object)("Stopping channel " + channel));
            }
            ((Lifecycle)channel).stop();
        }
    }

    protected final void orderlyShutdownCapableComponentsBefore() {
        this.logger.debug((Object)"Initiating stop OrderlyShutdownCapable components");
        Map components = this.applicationContext.getBeansOfType(OrderlyShutdownCapable.class);
        for (OrderlyShutdownCapable component : components.values()) {
            int n = component.beforeShutdown();
            if (!this.logger.isInfoEnabled()) continue;
            this.logger.info((Object)("Initiated stop for component " + component + "; it reported " + n + " active messages"));
        }
        this.logger.debug((Object)"Initiated stop OrderlyShutdownCapable components");
    }

    protected final void orderlyShutdownCapableComponentsAfter() {
        this.logger.debug((Object)"Finalizing stop OrderlyShutdownCapable components");
        Map components = this.applicationContext.getBeansOfType(OrderlyShutdownCapable.class);
        for (OrderlyShutdownCapable component : components.values()) {
            int n = component.afterShutdown();
            if (!this.logger.isInfoEnabled()) continue;
            this.logger.info((Object)("Finalized stop for component " + component + "; it reported " + n + " active messages"));
        }
        this.logger.debug((Object)"Finalized stop OrderlyShutdownCapable components");
    }

    @ManagedMetric(metricType=MetricType.COUNTER, displayName="MessageChannel Count")
    public int getChannelCount() {
        return this.channels.size();
    }

    @ManagedMetric(metricType=MetricType.COUNTER, displayName="MessageHandler Count")
    public int getHandlerCount() {
        return this.handlers.size();
    }

    @ManagedMetric(metricType=MetricType.COUNTER, displayName="MessageSource Count")
    public int getSourceCount() {
        return this.sources.size();
    }

    @ManagedAttribute
    public String[] getHandlerNames() {
        return (String[])this.handlers.values().stream().map(hand -> hand.getManagedName()).toArray(String[]::new);
    }

    @Deprecated
    @ManagedMetric(metricType=MetricType.GAUGE, displayName="No longer supported")
    public int getActiveHandlerCount() {
        return 0;
    }

    @Deprecated
    @ManagedMetric(metricType=MetricType.GAUGE, displayName="No longer supported")
    public long getActiveHandlerCountLong() {
        return 0L;
    }

    @ManagedMetric(metricType=MetricType.GAUGE, displayName="Queued Message Count")
    public int getQueuedMessageCount() {
        return this.channels.values().stream().filter(QueueChannel.class::isInstance).map(QueueChannel.class::cast).mapToInt(QueueChannel::getQueueSize).sum();
    }

    @ManagedAttribute
    public String[] getChannelNames() {
        return (String[])this.channels.keySet().stream().toArray(String[]::new);
    }

    @Nullable
    @Deprecated
    public AbstractMessageHandler getHandlerMetrics(String name) {
        return null;
    }

    @Nullable
    public IntegrationManagement getHandler(String name) {
        return this.handlers.get(name);
    }

    @ManagedAttribute
    public String[] getSourceNames() {
        return (String[])this.sources.keySet().stream().toArray(String[]::new);
    }

    @Deprecated
    public IntegrationInboundManagement getSourceMetrics(String name) {
        return this.sources.get(name);
    }

    @Deprecated
    public IntegrationManagement getChannelMetrics(String name) {
        return this.channels.get(name);
    }

    public IntegrationInboundManagement getSource(String name) {
        return this.sources.get(name);
    }

    public IntegrationManagement getChannel(String name) {
        return this.channels.get(name);
    }

    private void registerChannels() {
        this.channels.values().forEach(this::registerChannel);
    }

    private void registerChannel(IntegrationManagement monitor) {
        String name = monitor.getComponentName();
        if (this.matches(this.componentNamePatterns, name)) {
            String beanKey = this.getChannelBeanKey(name);
            if (this.logger.isInfoEnabled()) {
                this.logger.info((Object)("Registering MessageChannel " + name));
            }
            ObjectName objectName = this.registerBeanNameOrInstance(monitor, beanKey);
            this.objectNames.put(monitor, objectName);
        }
    }

    private void registerHandlers() {
        this.handlers.values().forEach(this::registerHandler);
    }

    private void registerHandler(IntegrationManagement monitor) {
        this.registerHandler(monitor, null);
    }

    private void registerHandler(IntegrationManagement monitor2, @Nullable IntegrationConsumer consumer) {
        IntegrationManagement monitor = this.enhanceHandlerMonitor(monitor2, consumer);
        String name = monitor.getComponentName();
        if (!this.objectNames.containsKey(monitor2) && this.matches(this.componentNamePatterns, name)) {
            String beanKey = this.getHandlerBeanKey(monitor);
            if (this.logger.isInfoEnabled()) {
                this.logger.info((Object)("Registering MessageHandler " + name));
            }
            ObjectName objectName = this.registerBeanNameOrInstance(monitor, beanKey);
            this.objectNames.put(monitor2, objectName);
        }
    }

    private void registerSources() {
        this.sources.values().forEach(this::registerSource);
    }

    private void registerSource(IntegrationInboundManagement source) {
        IntegrationInboundManagement monitor = this.enhanceSourceMonitor(source);
        String name = monitor.getManagedName();
        if (!this.objectNames.containsKey(source) && this.matches(this.componentNamePatterns, name)) {
            String beanKey = this.getSourceBeanKey(monitor);
            if (this.logger.isInfoEnabled()) {
                this.logger.info((Object)("Registering MessageSource " + name));
            }
            ObjectName objectName = this.registerBeanNameOrInstance(monitor, beanKey);
            this.objectNames.put(source, objectName);
            Lifecycle lifecycle = (Lifecycle)this.sourceLifecycles.get(source);
            if (lifecycle != null) {
                beanKey = this.getEndpointBeanKey(source.getManagedName() + ".adapter", source.getManagedType());
                if (this.logger.isInfoEnabled()) {
                    this.logger.info((Object)("Registering Endpoint " + beanKey));
                }
                this.objectNames.put(lifecycle, this.registerBeanNameOrInstance(lifecycle, beanKey));
            }
        }
    }

    private void registerEndpoints() {
        String[] names;
        for (String name : names = this.applicationContext.getBeanNamesForType(AbstractEndpoint.class)) {
            if (this.endpointsByMonitor.values().contains(name)) continue;
            AbstractEndpoint endpoint = (AbstractEndpoint)this.applicationContext.getBean(name, AbstractEndpoint.class);
            this.registerEndpoint(endpoint);
        }
    }

    private void registerEndpoint(AbstractEndpoint endpoint) {
        String source;
        String name = endpoint.getComponentName();
        if (name.startsWith("_org.springframework.integration")) {
            name = this.getInternalComponentName(name);
            source = "internal";
        } else {
            source = "endpoint";
        }
        if (this.matches(this.componentNamePatterns, name)) {
            if (this.endpointNames.contains(name)) {
                int count = 0;
                String unique = name + "#" + count;
                while (this.endpointNames.contains(unique)) {
                    unique = name + "#" + ++count;
                }
                name = unique;
            }
            this.endpointNames.add(name);
            String beanKey = this.getEndpointBeanKey(name, source);
            ObjectName objectName = this.registerBeanInstance(endpoint, beanKey);
            this.objectNames.put(endpoint, objectName);
            if (this.logger.isInfoEnabled()) {
                this.logger.info((Object)("Registered endpoint without MessageSource: " + objectName));
            }
        }
    }

    private boolean matches(String[] patterns, String name) {
        Boolean match = PatternMatchUtils.smartMatch((String)name, (String[])patterns);
        return match != null && match != false;
    }

    private Object extractTarget(Object bean) {
        if (!(bean instanceof Advised)) {
            return bean;
        }
        Advised advised = (Advised)bean;
        try {
            return this.extractTarget(advised.getTargetSource().getTarget());
        }
        catch (Exception e) {
            this.logger.error((Object)"Could not extract target", (Throwable)e);
            return null;
        }
    }

    private String getChannelBeanKey(String channel) {
        String extra = "";
        if (channel.startsWith(DEFAULT_DOMAIN)) {
            extra = ",source=anonymous";
        }
        return String.format(this.domain + ":type=MessageChannel,name=%s%s" + this.getStaticNames(), this.quoteIfNecessary(channel), extra);
    }

    private String getHandlerBeanKey(IntegrationManagement monitor) {
        return String.format(this.domain + ":type=MessageHandler,name=%s,bean=%s" + this.getStaticNames(), this.quoteIfNecessary(monitor.getManagedName()), this.quoteIfNecessary(monitor.getManagedType()));
    }

    private String getSourceBeanKey(IntegrationInboundManagement monitor) {
        return String.format(this.domain + ":type=MessageSource,name=%s,bean=%s" + this.getStaticNames(), this.quoteIfNecessary(monitor.getManagedName()), this.quoteIfNecessary(monitor.getManagedType()));
    }

    private String getEndpointBeanKey(String name, String source) {
        return String.format(this.domain + ":type=ManagedEndpoint,name=%s,bean=%s" + this.getStaticNames(), this.quoteIfNecessary(name), source);
    }

    private String quoteIfNecessary(String name) {
        return SourceVersion.isName(name) ? name : ObjectName.quote(name);
    }

    private String getStaticNames() {
        if (this.objectNameStaticProperties.isEmpty()) {
            return "";
        }
        return ',' + this.objectNameStaticProperties.entrySet().stream().map(entry -> entry.getKey() + "=" + entry.getValue()).collect(Collectors.joining(","));
    }

    private IntegrationManagement enhanceHandlerMonitor(IntegrationManagement monitor, @Nullable IntegrationConsumer consumer) {
        if (monitor.getManagedName() != null && monitor.getManagedType() != null) {
            return monitor;
        }
        String endpointName = null;
        String source = "endpoint";
        IntegrationConsumer endpoint = consumer;
        if (endpoint == null) {
            String[] names;
            for (String beanName : names = this.applicationContext.getBeanNamesForType(IntegrationConsumer.class)) {
                endpoint = (IntegrationConsumer)this.applicationContext.getBean(beanName, IntegrationConsumer.class);
                try {
                    MessageHandler handler = endpoint.getHandler();
                    if (!handler.equals(monitor) && !monitor.equals(this.extractTarget(this.handlerInAnonymousWrapper(handler)))) continue;
                    endpointName = beanName;
                    break;
                }
                catch (Exception ex) {
                    this.logger.trace((Object)("Could not get handler from bean = " + beanName), (Throwable)ex);
                    endpoint = null;
                }
            }
        } else {
            endpointName = endpoint.getBeanName();
        }
        IntegrationManagement messageHandlerMetrics = this.buildMessageHandlerMetrics(monitor, endpointName, source, endpoint);
        if (endpointName != null) {
            this.endpointsByMonitor.put(messageHandlerMetrics, endpointName);
        }
        return messageHandlerMetrics;
    }

    private IntegrationManagement buildMessageHandlerMetrics(IntegrationManagement monitor2, String name, String source, IntegrationConsumer endpoint) {
        MessageChannel inputChannel;
        String managedType = source;
        String managedName = name;
        if (managedName != null && managedName.startsWith("_org.springframework.integration")) {
            managedName = this.getInternalComponentName(managedName);
            managedType = "internal";
        }
        if (managedName != null && name.startsWith(DEFAULT_DOMAIN) && (inputChannel = endpoint.getInputChannel()) != null) {
            managedName = this.buildAnonymousManagedName(this.anonymousHandlerCounters, inputChannel);
            managedType = "anonymous";
        }
        if (managedName == null) {
            managedName = monitor2.getComponentName();
            if (managedName == null) {
                managedName = monitor2.toString();
            }
            managedType = "handler";
        }
        monitor2.setManagedType(managedType);
        monitor2.setManagedName(managedName);
        return monitor2;
    }

    private String buildAnonymousManagedName(Map<Object, AtomicLong> anonymousCache, MessageChannel messageChannel) {
        AtomicLong count = anonymousCache.computeIfAbsent(messageChannel, key -> new AtomicLong());
        long total = count.incrementAndGet();
        String channelName = messageChannel instanceof NamedComponent ? ((NamedComponent)messageChannel).getBeanName() : messageChannel.toString();
        return channelName + (total > 1L ? "#" + total : "");
    }

    private String getInternalComponentName(String name) {
        return name.substring("_org.springframework.integration".length() + 1);
    }

    private IntegrationInboundManagement enhanceSourceMonitor(IntegrationInboundManagement source2) {
        if (source2.getManagedName() != null) {
            return source2;
        }
        String endpointName = null;
        String source = "endpoint";
        AbstractEndpoint endpoint = this.getEndpointForMonitor(source2);
        this.sourceLifecycles.put(source2, (ManageableLifecycle)endpoint);
        if (endpoint != null) {
            endpointName = endpoint.getBeanName();
        }
        if (endpointName != null && endpointName.startsWith("_org.springframework.integration")) {
            endpointName = this.getInternalComponentName(endpointName);
            source = "internal";
        }
        IntegrationInboundManagement messageSourceMetrics = this.buildMessageSourceMetricsIfAny(source2, endpointName, source, endpoint);
        if (endpointName != null) {
            this.endpointsByMonitor.put(messageSourceMetrics, endpointName);
        }
        return messageSourceMetrics;
    }

    private AbstractEndpoint getEndpointForMonitor(IntegrationInboundManagement source2) {
        for (AbstractEndpoint endpoint : this.applicationContext.getBeansOfType(AbstractEndpoint.class).values()) {
            IntegrationInboundManagement target = null;
            if (source2 instanceof MessagingGatewaySupport && endpoint.equals(source2)) {
                target = source2;
            } else if (endpoint instanceof SourcePollingChannelAdapter) {
                target = ((SourcePollingChannelAdapter)endpoint).getMessageSource();
            }
            if (!source2.equals(target)) continue;
            return endpoint;
        }
        return null;
    }

    private IntegrationInboundManagement buildMessageSourceMetricsIfAny(IntegrationInboundManagement source2, String name, String source, Object endpoint) {
        String managedType = source;
        String managedName = name;
        if (managedName != null && managedName.startsWith(DEFAULT_DOMAIN)) {
            Object target = endpoint;
            if (endpoint instanceof Advised) {
                TargetSource targetSource = ((Advised)endpoint).getTargetSource();
                try {
                    target = targetSource.getTarget();
                }
                catch (Exception ex) {
                    this.logger.error((Object)("Could not get handler from bean = " + managedName), (Throwable)ex);
                }
            }
            MessageChannel outputChannel = null;
            if (target instanceof MessagingGatewaySupport) {
                outputChannel = ((MessagingGatewaySupport)target).getRequestChannel();
            } else if (target instanceof SourcePollingChannelAdapter) {
                outputChannel = ((SourcePollingChannelAdapter)target).getOutputChannel();
            }
            if (outputChannel != null) {
                managedName = this.buildAnonymousManagedName(this.anonymousSourceCounters, outputChannel);
                managedType = "anonymous";
            }
        }
        if (managedName == null) {
            managedName = source2.toString();
            managedType = "source";
        }
        source2.setManagedType(managedType);
        source2.setManagedName(managedName);
        return source2;
    }
}

