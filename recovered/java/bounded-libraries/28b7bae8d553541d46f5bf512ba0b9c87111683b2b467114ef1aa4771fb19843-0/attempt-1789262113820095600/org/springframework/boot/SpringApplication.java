/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.beans.BeanUtils
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.config.BeanFactoryPostProcessor
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 *  org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.beans.factory.support.BeanNameGenerator
 *  org.springframework.beans.factory.support.DefaultListableBeanFactory
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationContextInitializer
 *  org.springframework.context.ApplicationEvent
 *  org.springframework.context.ApplicationListener
 *  org.springframework.context.ConfigurableApplicationContext
 *  org.springframework.context.support.AbstractApplicationContext
 *  org.springframework.context.support.GenericApplicationContext
 *  org.springframework.core.GenericTypeResolver
 *  org.springframework.core.Ordered
 *  org.springframework.core.annotation.AnnotationAwareOrderComparator
 *  org.springframework.core.convert.ConversionService
 *  org.springframework.core.convert.support.ConfigurableConversionService
 *  org.springframework.core.env.CompositePropertySource
 *  org.springframework.core.env.ConfigurableEnvironment
 *  org.springframework.core.env.Environment
 *  org.springframework.core.env.MutablePropertySources
 *  org.springframework.core.env.PropertySource
 *  org.springframework.core.env.SimpleCommandLinePropertySource
 *  org.springframework.core.env.StandardEnvironment
 *  org.springframework.core.io.DefaultResourceLoader
 *  org.springframework.core.io.ResourceLoader
 *  org.springframework.core.io.support.SpringFactoriesLoader
 *  org.springframework.core.metrics.ApplicationStartup
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.ObjectUtils
 *  org.springframework.util.ReflectionUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot;

import java.lang.reflect.Constructor;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationContextFactory;
import org.springframework.boot.ApplicationEnvironment;
import org.springframework.boot.ApplicationReactiveWebEnvironment;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.ApplicationServletEnvironment;
import org.springframework.boot.Banner;
import org.springframework.boot.BeanDefinitionLoader;
import org.springframework.boot.BootstrapRegistryInitializer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.DefaultBootstrapContext;
import org.springframework.boot.DefaultPropertiesPropertySource;
import org.springframework.boot.EnvironmentConverter;
import org.springframework.boot.ExitCodeEvent;
import org.springframework.boot.ExitCodeExceptionMapper;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.ExitCodeGenerators;
import org.springframework.boot.LazyInitializationBeanFactoryPostProcessor;
import org.springframework.boot.SpringApplicationBannerPrinter;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.boot.SpringApplicationRunListeners;
import org.springframework.boot.SpringApplicationShutdownHandlers;
import org.springframework.boot.SpringApplicationShutdownHook;
import org.springframework.boot.SpringBootExceptionHandler;
import org.springframework.boot.SpringBootExceptionReporter;
import org.springframework.boot.StartupInfoLogger;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.GenericTypeResolver;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.core.metrics.ApplicationStartup;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

public class SpringApplication {
    public static final String BANNER_LOCATION_PROPERTY_VALUE = "banner.txt";
    public static final String BANNER_LOCATION_PROPERTY = "spring.banner.location";
    private static final String SYSTEM_PROPERTY_JAVA_AWT_HEADLESS = "java.awt.headless";
    private static final Log logger = LogFactory.getLog(SpringApplication.class);
    static final SpringApplicationShutdownHook shutdownHook = new SpringApplicationShutdownHook();
    private Set<Class<?>> primarySources;
    private Set<String> sources = new LinkedHashSet<String>();
    private Class<?> mainApplicationClass;
    private Banner.Mode bannerMode = Banner.Mode.CONSOLE;
    private boolean logStartupInfo = true;
    private boolean addCommandLineProperties = true;
    private boolean addConversionService = true;
    private Banner banner;
    private ResourceLoader resourceLoader;
    private BeanNameGenerator beanNameGenerator;
    private ConfigurableEnvironment environment;
    private WebApplicationType webApplicationType;
    private boolean headless = true;
    private boolean registerShutdownHook = true;
    private List<ApplicationContextInitializer<?>> initializers;
    private List<ApplicationListener<?>> listeners;
    private Map<String, Object> defaultProperties;
    private List<BootstrapRegistryInitializer> bootstrapRegistryInitializers;
    private Set<String> additionalProfiles = Collections.emptySet();
    private boolean allowBeanDefinitionOverriding;
    private boolean allowCircularReferences;
    private boolean isCustomEnvironment = false;
    private boolean lazyInitialization = false;
    private String environmentPrefix;
    private ApplicationContextFactory applicationContextFactory = ApplicationContextFactory.DEFAULT;
    private ApplicationStartup applicationStartup = ApplicationStartup.DEFAULT;

    public SpringApplication(Class<?> ... primarySources) {
        this((ResourceLoader)null, primarySources);
    }

    public SpringApplication(ResourceLoader resourceLoader, Class<?> ... primarySources) {
        this.resourceLoader = resourceLoader;
        Assert.notNull(primarySources, (String)"PrimarySources must not be null");
        this.primarySources = new LinkedHashSet(Arrays.asList(primarySources));
        this.webApplicationType = WebApplicationType.deduceFromClasspath();
        this.bootstrapRegistryInitializers = new ArrayList<BootstrapRegistryInitializer>(this.getSpringFactoriesInstances(BootstrapRegistryInitializer.class));
        this.setInitializers(this.getSpringFactoriesInstances(ApplicationContextInitializer.class));
        this.setListeners(this.getSpringFactoriesInstances(ApplicationListener.class));
        this.mainApplicationClass = this.deduceMainApplicationClass();
    }

    private Class<?> deduceMainApplicationClass() {
        try {
            StackTraceElement[] stackTrace;
            for (StackTraceElement stackTraceElement : stackTrace = new RuntimeException().getStackTrace()) {
                if (!"main".equals(stackTraceElement.getMethodName())) continue;
                return Class.forName(stackTraceElement.getClassName());
            }
        }
        catch (ClassNotFoundException classNotFoundException) {
            // empty catch block
        }
        return null;
    }

    public ConfigurableApplicationContext run(String ... args) {
        long startTime = System.nanoTime();
        DefaultBootstrapContext bootstrapContext = this.createBootstrapContext();
        ConfigurableApplicationContext context = null;
        this.configureHeadlessProperty();
        SpringApplicationRunListeners listeners = this.getRunListeners(args);
        listeners.starting(bootstrapContext, this.mainApplicationClass);
        try {
            DefaultApplicationArguments applicationArguments = new DefaultApplicationArguments(args);
            ConfigurableEnvironment environment = this.prepareEnvironment(listeners, bootstrapContext, applicationArguments);
            this.configureIgnoreBeanInfo(environment);
            Banner printedBanner = this.printBanner(environment);
            context = this.createApplicationContext();
            context.setApplicationStartup(this.applicationStartup);
            this.prepareContext(bootstrapContext, context, environment, listeners, applicationArguments, printedBanner);
            this.refreshContext(context);
            this.afterRefresh(context, applicationArguments);
            Duration timeTakenToStartup = Duration.ofNanos(System.nanoTime() - startTime);
            if (this.logStartupInfo) {
                new StartupInfoLogger(this.mainApplicationClass).logStarted(this.getApplicationLog(), timeTakenToStartup);
            }
            listeners.started(context, timeTakenToStartup);
            this.callRunners((ApplicationContext)context, applicationArguments);
        }
        catch (Throwable ex) {
            this.handleRunFailure(context, ex, listeners);
            throw new IllegalStateException(ex);
        }
        try {
            Duration timeTakenToReady = Duration.ofNanos(System.nanoTime() - startTime);
            listeners.ready(context, timeTakenToReady);
        }
        catch (Throwable ex) {
            this.handleRunFailure(context, ex, null);
            throw new IllegalStateException(ex);
        }
        return context;
    }

    private DefaultBootstrapContext createBootstrapContext() {
        DefaultBootstrapContext bootstrapContext = new DefaultBootstrapContext();
        this.bootstrapRegistryInitializers.forEach(initializer -> initializer.initialize(bootstrapContext));
        return bootstrapContext;
    }

    private ConfigurableEnvironment prepareEnvironment(SpringApplicationRunListeners listeners, DefaultBootstrapContext bootstrapContext, ApplicationArguments applicationArguments) {
        ConfigurableEnvironment environment = this.getOrCreateEnvironment();
        this.configureEnvironment(environment, applicationArguments.getSourceArgs());
        ConfigurationPropertySources.attach((Environment)environment);
        listeners.environmentPrepared(bootstrapContext, environment);
        DefaultPropertiesPropertySource.moveToEnd(environment);
        Assert.state((!environment.containsProperty("spring.main.environment-prefix") ? 1 : 0) != 0, (String)"Environment prefix cannot be set via properties.");
        this.bindToSpringApplication(environment);
        if (!this.isCustomEnvironment) {
            EnvironmentConverter environmentConverter = new EnvironmentConverter(this.getClassLoader());
            environment = environmentConverter.convertEnvironmentIfNecessary(environment, this.deduceEnvironmentClass());
        }
        ConfigurationPropertySources.attach((Environment)environment);
        return environment;
    }

    private Class<? extends StandardEnvironment> deduceEnvironmentClass() {
        switch (this.webApplicationType) {
            case SERVLET: {
                return ApplicationServletEnvironment.class;
            }
            case REACTIVE: {
                return ApplicationReactiveWebEnvironment.class;
            }
        }
        return ApplicationEnvironment.class;
    }

    private void prepareContext(DefaultBootstrapContext bootstrapContext, ConfigurableApplicationContext context, ConfigurableEnvironment environment, SpringApplicationRunListeners listeners, ApplicationArguments applicationArguments, Banner printedBanner) {
        context.setEnvironment(environment);
        this.postProcessApplicationContext(context);
        this.applyInitializers(context);
        listeners.contextPrepared(context);
        bootstrapContext.close(context);
        if (this.logStartupInfo) {
            this.logStartupInfo(context.getParent() == null);
            this.logStartupProfileInfo(context);
        }
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        beanFactory.registerSingleton("springApplicationArguments", (Object)applicationArguments);
        if (printedBanner != null) {
            beanFactory.registerSingleton("springBootBanner", (Object)printedBanner);
        }
        if (beanFactory instanceof AbstractAutowireCapableBeanFactory) {
            ((AbstractAutowireCapableBeanFactory)beanFactory).setAllowCircularReferences(this.allowCircularReferences);
            if (beanFactory instanceof DefaultListableBeanFactory) {
                ((DefaultListableBeanFactory)beanFactory).setAllowBeanDefinitionOverriding(this.allowBeanDefinitionOverriding);
            }
        }
        if (this.lazyInitialization) {
            context.addBeanFactoryPostProcessor((BeanFactoryPostProcessor)new LazyInitializationBeanFactoryPostProcessor());
        }
        context.addBeanFactoryPostProcessor((BeanFactoryPostProcessor)new PropertySourceOrderingBeanFactoryPostProcessor(context));
        Set<Object> sources = this.getAllSources();
        Assert.notEmpty(sources, (String)"Sources must not be empty");
        this.load((ApplicationContext)context, sources.toArray(new Object[0]));
        listeners.contextLoaded(context);
    }

    private void refreshContext(ConfigurableApplicationContext context) {
        if (this.registerShutdownHook) {
            shutdownHook.registerApplicationContext(context);
        }
        this.refresh(context);
    }

    private void configureHeadlessProperty() {
        System.setProperty(SYSTEM_PROPERTY_JAVA_AWT_HEADLESS, System.getProperty(SYSTEM_PROPERTY_JAVA_AWT_HEADLESS, Boolean.toString(this.headless)));
    }

    private SpringApplicationRunListeners getRunListeners(String[] args) {
        Class[] types = new Class[]{SpringApplication.class, String[].class};
        return new SpringApplicationRunListeners(logger, this.getSpringFactoriesInstances(SpringApplicationRunListener.class, types, this, args), this.applicationStartup);
    }

    private <T> Collection<T> getSpringFactoriesInstances(Class<T> type) {
        return this.getSpringFactoriesInstances(type, new Class[0], new Object[0]);
    }

    private <T> Collection<T> getSpringFactoriesInstances(Class<T> type, Class<?>[] parameterTypes, Object ... args) {
        ClassLoader classLoader = this.getClassLoader();
        LinkedHashSet<String> names = new LinkedHashSet<String>(SpringFactoriesLoader.loadFactoryNames(type, (ClassLoader)classLoader));
        List<T> instances = this.createSpringFactoriesInstances(type, parameterTypes, classLoader, args, names);
        AnnotationAwareOrderComparator.sort(instances);
        return instances;
    }

    private <T> List<T> createSpringFactoriesInstances(Class<T> type, Class<?>[] parameterTypes, ClassLoader classLoader, Object[] args, Set<String> names) {
        ArrayList<Object> instances = new ArrayList<Object>(names.size());
        for (String name : names) {
            try {
                Class instanceClass = ClassUtils.forName((String)name, (ClassLoader)classLoader);
                Assert.isAssignable(type, (Class)instanceClass);
                Constructor constructor = instanceClass.getDeclaredConstructor(parameterTypes);
                Object instance = BeanUtils.instantiateClass(constructor, (Object[])args);
                instances.add(instance);
            }
            catch (Throwable ex) {
                throw new IllegalArgumentException("Cannot instantiate " + type + " : " + name, ex);
            }
        }
        return instances;
    }

    private ConfigurableEnvironment getOrCreateEnvironment() {
        if (this.environment != null) {
            return this.environment;
        }
        switch (this.webApplicationType) {
            case SERVLET: {
                return new ApplicationServletEnvironment();
            }
            case REACTIVE: {
                return new ApplicationReactiveWebEnvironment();
            }
        }
        return new ApplicationEnvironment();
    }

    protected void configureEnvironment(ConfigurableEnvironment environment, String[] args) {
        if (this.addConversionService) {
            environment.setConversionService((ConfigurableConversionService)new ApplicationConversionService());
        }
        this.configurePropertySources(environment, args);
        this.configureProfiles(environment, args);
    }

    protected void configurePropertySources(ConfigurableEnvironment environment, String[] args) {
        MutablePropertySources sources = environment.getPropertySources();
        if (!CollectionUtils.isEmpty(this.defaultProperties)) {
            DefaultPropertiesPropertySource.addOrMerge(this.defaultProperties, sources);
        }
        if (this.addCommandLineProperties && args.length > 0) {
            String name = "commandLineArgs";
            if (sources.contains(name)) {
                PropertySource source = sources.get(name);
                CompositePropertySource composite = new CompositePropertySource(name);
                composite.addPropertySource((PropertySource)new SimpleCommandLinePropertySource("springApplicationCommandLineArgs", args));
                composite.addPropertySource(source);
                sources.replace(name, (PropertySource)composite);
            } else {
                sources.addFirst((PropertySource)new SimpleCommandLinePropertySource(args));
            }
        }
    }

    protected void configureProfiles(ConfigurableEnvironment environment, String[] args) {
    }

    private void configureIgnoreBeanInfo(ConfigurableEnvironment environment) {
        if (System.getProperty("spring.beaninfo.ignore") == null) {
            Boolean ignore = (Boolean)environment.getProperty("spring.beaninfo.ignore", Boolean.class, (Object)Boolean.TRUE);
            System.setProperty("spring.beaninfo.ignore", ignore.toString());
        }
    }

    protected void bindToSpringApplication(ConfigurableEnvironment environment) {
        try {
            Binder.get((Environment)environment).bind("spring.main", Bindable.ofInstance(this));
        }
        catch (Exception ex) {
            throw new IllegalStateException("Cannot bind to SpringApplication", ex);
        }
    }

    private Banner printBanner(ConfigurableEnvironment environment) {
        if (this.bannerMode == Banner.Mode.OFF) {
            return null;
        }
        ResourceLoader resourceLoader = this.resourceLoader != null ? this.resourceLoader : new DefaultResourceLoader(null);
        SpringApplicationBannerPrinter bannerPrinter = new SpringApplicationBannerPrinter(resourceLoader, this.banner);
        if (this.bannerMode == Banner.Mode.LOG) {
            return bannerPrinter.print((Environment)environment, this.mainApplicationClass, logger);
        }
        return bannerPrinter.print((Environment)environment, this.mainApplicationClass, System.out);
    }

    protected ConfigurableApplicationContext createApplicationContext() {
        return this.applicationContextFactory.create(this.webApplicationType);
    }

    protected void postProcessApplicationContext(ConfigurableApplicationContext context) {
        if (this.beanNameGenerator != null) {
            context.getBeanFactory().registerSingleton("org.springframework.context.annotation.internalConfigurationBeanNameGenerator", (Object)this.beanNameGenerator);
        }
        if (this.resourceLoader != null) {
            if (context instanceof GenericApplicationContext) {
                ((GenericApplicationContext)context).setResourceLoader(this.resourceLoader);
            }
            if (context instanceof DefaultResourceLoader) {
                ((DefaultResourceLoader)context).setClassLoader(this.resourceLoader.getClassLoader());
            }
        }
        if (this.addConversionService) {
            context.getBeanFactory().setConversionService((ConversionService)context.getEnvironment().getConversionService());
        }
    }

    protected void applyInitializers(ConfigurableApplicationContext context) {
        for (ApplicationContextInitializer<?> initializer : this.getInitializers()) {
            Class requiredType = GenericTypeResolver.resolveTypeArgument(initializer.getClass(), ApplicationContextInitializer.class);
            Assert.isInstanceOf((Class)requiredType, (Object)context, (String)"Unable to call initializer.");
            initializer.initialize(context);
        }
    }

    protected void logStartupInfo(boolean isRoot) {
        if (isRoot) {
            new StartupInfoLogger(this.mainApplicationClass).logStarting(this.getApplicationLog());
        }
    }

    protected void logStartupProfileInfo(ConfigurableApplicationContext context) {
        Log log = this.getApplicationLog();
        if (log.isInfoEnabled()) {
            List<String> activeProfiles = this.quoteProfiles(context.getEnvironment().getActiveProfiles());
            if (ObjectUtils.isEmpty(activeProfiles)) {
                List<String> defaultProfiles = this.quoteProfiles(context.getEnvironment().getDefaultProfiles());
                String message = String.format("%s default %s: ", defaultProfiles.size(), defaultProfiles.size() <= 1 ? "profile" : "profiles");
                log.info((Object)("No active profile set, falling back to " + message + StringUtils.collectionToDelimitedString(defaultProfiles, (String)", ")));
            } else {
                String message = activeProfiles.size() == 1 ? "1 profile is active: " : activeProfiles.size() + " profiles are active: ";
                log.info((Object)("The following " + message + StringUtils.collectionToDelimitedString(activeProfiles, (String)", ")));
            }
        }
    }

    private List<String> quoteProfiles(String[] profiles) {
        return Arrays.stream(profiles).map(profile -> "\"" + profile + "\"").collect(Collectors.toList());
    }

    protected Log getApplicationLog() {
        if (this.mainApplicationClass == null) {
            return logger;
        }
        return LogFactory.getLog(this.mainApplicationClass);
    }

    protected void load(ApplicationContext context, Object[] sources) {
        if (logger.isDebugEnabled()) {
            logger.debug((Object)("Loading source " + StringUtils.arrayToCommaDelimitedString((Object[])sources)));
        }
        BeanDefinitionLoader loader = this.createBeanDefinitionLoader(this.getBeanDefinitionRegistry(context), sources);
        if (this.beanNameGenerator != null) {
            loader.setBeanNameGenerator(this.beanNameGenerator);
        }
        if (this.resourceLoader != null) {
            loader.setResourceLoader(this.resourceLoader);
        }
        if (this.environment != null) {
            loader.setEnvironment(this.environment);
        }
        loader.load();
    }

    public ResourceLoader getResourceLoader() {
        return this.resourceLoader;
    }

    public ClassLoader getClassLoader() {
        if (this.resourceLoader != null) {
            return this.resourceLoader.getClassLoader();
        }
        return ClassUtils.getDefaultClassLoader();
    }

    private BeanDefinitionRegistry getBeanDefinitionRegistry(ApplicationContext context) {
        if (context instanceof BeanDefinitionRegistry) {
            return (BeanDefinitionRegistry)context;
        }
        if (context instanceof AbstractApplicationContext) {
            return (BeanDefinitionRegistry)((AbstractApplicationContext)context).getBeanFactory();
        }
        throw new IllegalStateException("Could not locate BeanDefinitionRegistry");
    }

    protected BeanDefinitionLoader createBeanDefinitionLoader(BeanDefinitionRegistry registry, Object[] sources) {
        return new BeanDefinitionLoader(registry, sources);
    }

    protected void refresh(ConfigurableApplicationContext applicationContext) {
        applicationContext.refresh();
    }

    protected void afterRefresh(ConfigurableApplicationContext context, ApplicationArguments args) {
    }

    private void callRunners(ApplicationContext context, ApplicationArguments args) {
        ArrayList runners = new ArrayList();
        runners.addAll(context.getBeansOfType(ApplicationRunner.class).values());
        runners.addAll(context.getBeansOfType(CommandLineRunner.class).values());
        AnnotationAwareOrderComparator.sort(runners);
        for (Object runner : new LinkedHashSet(runners)) {
            if (runner instanceof ApplicationRunner) {
                this.callRunner((ApplicationRunner)runner, args);
            }
            if (!(runner instanceof CommandLineRunner)) continue;
            this.callRunner((CommandLineRunner)runner, args);
        }
    }

    private void callRunner(ApplicationRunner runner, ApplicationArguments args) {
        try {
            runner.run(args);
        }
        catch (Exception ex) {
            throw new IllegalStateException("Failed to execute ApplicationRunner", ex);
        }
    }

    private void callRunner(CommandLineRunner runner, ApplicationArguments args) {
        try {
            runner.run(args.getSourceArgs());
        }
        catch (Exception ex) {
            throw new IllegalStateException("Failed to execute CommandLineRunner", ex);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void handleRunFailure(ConfigurableApplicationContext context, Throwable exception, SpringApplicationRunListeners listeners) {
        try {
            try {
                this.handleExitCode(context, exception);
                if (listeners != null) {
                    listeners.failed(context, exception);
                }
            }
            finally {
                this.reportFailure(this.getExceptionReporters(context), exception);
                if (context != null) {
                    context.close();
                    shutdownHook.deregisterFailedApplicationContext(context);
                }
            }
        }
        catch (Exception ex) {
            logger.warn((Object)"Unable to close ApplicationContext", (Throwable)ex);
        }
        ReflectionUtils.rethrowRuntimeException((Throwable)exception);
    }

    private Collection<SpringBootExceptionReporter> getExceptionReporters(ConfigurableApplicationContext context) {
        try {
            return this.getSpringFactoriesInstances(SpringBootExceptionReporter.class, new Class[]{ConfigurableApplicationContext.class}, context);
        }
        catch (Throwable ex) {
            return Collections.emptyList();
        }
    }

    private void reportFailure(Collection<SpringBootExceptionReporter> exceptionReporters, Throwable failure) {
        try {
            for (SpringBootExceptionReporter reporter : exceptionReporters) {
                if (!reporter.reportException(failure)) continue;
                this.registerLoggedException(failure);
                return;
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        if (logger.isErrorEnabled()) {
            logger.error((Object)"Application run failed", failure);
            this.registerLoggedException(failure);
        }
    }

    protected void registerLoggedException(Throwable exception) {
        SpringBootExceptionHandler handler = this.getSpringBootExceptionHandler();
        if (handler != null) {
            handler.registerLoggedException(exception);
        }
    }

    private void handleExitCode(ConfigurableApplicationContext context, Throwable exception) {
        int exitCode = this.getExitCodeFromException(context, exception);
        if (exitCode != 0) {
            SpringBootExceptionHandler handler;
            if (context != null) {
                context.publishEvent((ApplicationEvent)new ExitCodeEvent(context, exitCode));
            }
            if ((handler = this.getSpringBootExceptionHandler()) != null) {
                handler.registerExitCode(exitCode);
            }
        }
    }

    private int getExitCodeFromException(ConfigurableApplicationContext context, Throwable exception) {
        int exitCode = this.getExitCodeFromMappedException(context, exception);
        if (exitCode == 0) {
            exitCode = this.getExitCodeFromExitCodeGeneratorException(exception);
        }
        return exitCode;
    }

    private int getExitCodeFromMappedException(ConfigurableApplicationContext context, Throwable exception) {
        if (context == null || !context.isActive()) {
            return 0;
        }
        ExitCodeGenerators generators = new ExitCodeGenerators();
        Collection beans = context.getBeansOfType(ExitCodeExceptionMapper.class).values();
        generators.addAll(exception, beans);
        return generators.getExitCode();
    }

    private int getExitCodeFromExitCodeGeneratorException(Throwable exception) {
        if (exception == null) {
            return 0;
        }
        if (exception instanceof ExitCodeGenerator) {
            return ((ExitCodeGenerator)((Object)exception)).getExitCode();
        }
        return this.getExitCodeFromExitCodeGeneratorException(exception.getCause());
    }

    SpringBootExceptionHandler getSpringBootExceptionHandler() {
        if (this.isMainThread(Thread.currentThread())) {
            return SpringBootExceptionHandler.forCurrentThread();
        }
        return null;
    }

    private boolean isMainThread(Thread currentThread) {
        return ("main".equals(currentThread.getName()) || "restartedMain".equals(currentThread.getName())) && "main".equals(currentThread.getThreadGroup().getName());
    }

    public Class<?> getMainApplicationClass() {
        return this.mainApplicationClass;
    }

    public void setMainApplicationClass(Class<?> mainApplicationClass) {
        this.mainApplicationClass = mainApplicationClass;
    }

    public WebApplicationType getWebApplicationType() {
        return this.webApplicationType;
    }

    public void setWebApplicationType(WebApplicationType webApplicationType) {
        Assert.notNull((Object)((Object)webApplicationType), (String)"WebApplicationType must not be null");
        this.webApplicationType = webApplicationType;
    }

    public void setAllowBeanDefinitionOverriding(boolean allowBeanDefinitionOverriding) {
        this.allowBeanDefinitionOverriding = allowBeanDefinitionOverriding;
    }

    public void setAllowCircularReferences(boolean allowCircularReferences) {
        this.allowCircularReferences = allowCircularReferences;
    }

    public void setLazyInitialization(boolean lazyInitialization) {
        this.lazyInitialization = lazyInitialization;
    }

    public void setHeadless(boolean headless) {
        this.headless = headless;
    }

    public void setRegisterShutdownHook(boolean registerShutdownHook) {
        this.registerShutdownHook = registerShutdownHook;
    }

    public void setBanner(Banner banner) {
        this.banner = banner;
    }

    public void setBannerMode(Banner.Mode bannerMode) {
        this.bannerMode = bannerMode;
    }

    public void setLogStartupInfo(boolean logStartupInfo) {
        this.logStartupInfo = logStartupInfo;
    }

    public void setAddCommandLineProperties(boolean addCommandLineProperties) {
        this.addCommandLineProperties = addCommandLineProperties;
    }

    public void setAddConversionService(boolean addConversionService) {
        this.addConversionService = addConversionService;
    }

    public void addBootstrapRegistryInitializer(BootstrapRegistryInitializer bootstrapRegistryInitializer) {
        Assert.notNull((Object)bootstrapRegistryInitializer, (String)"BootstrapRegistryInitializer must not be null");
        this.bootstrapRegistryInitializers.addAll(Arrays.asList(bootstrapRegistryInitializer));
    }

    public void setDefaultProperties(Map<String, Object> defaultProperties) {
        this.defaultProperties = defaultProperties;
    }

    public void setDefaultProperties(Properties defaultProperties) {
        this.defaultProperties = new HashMap<String, Object>();
        for (Object key : Collections.list(defaultProperties.propertyNames())) {
            this.defaultProperties.put((String)key, defaultProperties.get(key));
        }
    }

    public void setAdditionalProfiles(String ... profiles) {
        this.additionalProfiles = Collections.unmodifiableSet(new LinkedHashSet<String>(Arrays.asList(profiles)));
    }

    public Set<String> getAdditionalProfiles() {
        return this.additionalProfiles;
    }

    public void setBeanNameGenerator(BeanNameGenerator beanNameGenerator) {
        this.beanNameGenerator = beanNameGenerator;
    }

    public void setEnvironment(ConfigurableEnvironment environment) {
        this.isCustomEnvironment = true;
        this.environment = environment;
    }

    public void addPrimarySources(Collection<Class<?>> additionalPrimarySources) {
        this.primarySources.addAll(additionalPrimarySources);
    }

    public Set<String> getSources() {
        return this.sources;
    }

    public void setSources(Set<String> sources) {
        Assert.notNull(sources, (String)"Sources must not be null");
        this.sources = new LinkedHashSet<String>(sources);
    }

    public Set<Object> getAllSources() {
        LinkedHashSet<Object> allSources = new LinkedHashSet<Object>();
        if (!CollectionUtils.isEmpty(this.primarySources)) {
            allSources.addAll(this.primarySources);
        }
        if (!CollectionUtils.isEmpty(this.sources)) {
            allSources.addAll(this.sources);
        }
        return Collections.unmodifiableSet(allSources);
    }

    public void setResourceLoader(ResourceLoader resourceLoader) {
        Assert.notNull((Object)resourceLoader, (String)"ResourceLoader must not be null");
        this.resourceLoader = resourceLoader;
    }

    public String getEnvironmentPrefix() {
        return this.environmentPrefix;
    }

    public void setEnvironmentPrefix(String environmentPrefix) {
        this.environmentPrefix = environmentPrefix;
    }

    public void setApplicationContextFactory(ApplicationContextFactory applicationContextFactory) {
        this.applicationContextFactory = applicationContextFactory != null ? applicationContextFactory : ApplicationContextFactory.DEFAULT;
    }

    public void setInitializers(Collection<? extends ApplicationContextInitializer<?>> initializers) {
        this.initializers = new ArrayList(initializers);
    }

    public void addInitializers(ApplicationContextInitializer<?> ... initializers) {
        this.initializers.addAll(Arrays.asList(initializers));
    }

    public Set<ApplicationContextInitializer<?>> getInitializers() {
        return SpringApplication.asUnmodifiableOrderedSet(this.initializers);
    }

    public void setListeners(Collection<? extends ApplicationListener<?>> listeners) {
        this.listeners = new ArrayList(listeners);
    }

    public void addListeners(ApplicationListener<?> ... listeners) {
        this.listeners.addAll(Arrays.asList(listeners));
    }

    public Set<ApplicationListener<?>> getListeners() {
        return SpringApplication.asUnmodifiableOrderedSet(this.listeners);
    }

    public void setApplicationStartup(ApplicationStartup applicationStartup) {
        this.applicationStartup = applicationStartup != null ? applicationStartup : ApplicationStartup.DEFAULT;
    }

    public ApplicationStartup getApplicationStartup() {
        return this.applicationStartup;
    }

    public static SpringApplicationShutdownHandlers getShutdownHandlers() {
        return shutdownHook.getHandlers();
    }

    public static ConfigurableApplicationContext run(Class<?> primarySource, String ... args) {
        return SpringApplication.run(new Class[]{primarySource}, args);
    }

    public static ConfigurableApplicationContext run(Class<?>[] primarySources, String[] args) {
        return new SpringApplication(primarySources).run(args);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(new Class[0], args);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static int exit(ApplicationContext context, ExitCodeGenerator ... exitCodeGenerators) {
        Assert.notNull((Object)context, (String)"Context must not be null");
        int exitCode = 0;
        try {
            try {
                ExitCodeGenerators generators = new ExitCodeGenerators();
                Collection beans = context.getBeansOfType(ExitCodeGenerator.class).values();
                generators.addAll(exitCodeGenerators);
                generators.addAll(beans);
                exitCode = generators.getExitCode();
                if (exitCode != 0) {
                    context.publishEvent((ApplicationEvent)new ExitCodeEvent(context, exitCode));
                }
            }
            finally {
                SpringApplication.close(context);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            exitCode = exitCode != 0 ? exitCode : 1;
        }
        return exitCode;
    }

    private static void close(ApplicationContext context) {
        if (context instanceof ConfigurableApplicationContext) {
            ConfigurableApplicationContext closable = (ConfigurableApplicationContext)context;
            closable.close();
        }
    }

    private static <E> Set<E> asUnmodifiableOrderedSet(Collection<E> elements) {
        ArrayList<E> list = new ArrayList<E>(elements);
        list.sort((Comparator<E>)AnnotationAwareOrderComparator.INSTANCE);
        return new LinkedHashSet<E>(list);
    }

    private static class PropertySourceOrderingBeanFactoryPostProcessor
    implements BeanFactoryPostProcessor,
    Ordered {
        private final ConfigurableApplicationContext context;

        PropertySourceOrderingBeanFactoryPostProcessor(ConfigurableApplicationContext context) {
            this.context = context;
        }

        public int getOrder() {
            return Integer.MIN_VALUE;
        }

        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
            DefaultPropertiesPropertySource.moveToEnd(this.context.getEnvironment());
        }
    }
}

