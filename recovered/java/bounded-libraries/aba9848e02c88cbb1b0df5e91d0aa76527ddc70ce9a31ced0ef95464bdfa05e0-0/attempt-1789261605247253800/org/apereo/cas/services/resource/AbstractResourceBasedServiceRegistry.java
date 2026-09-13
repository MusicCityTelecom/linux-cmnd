/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.io.FileUtils
 *  org.apache.commons.lang3.ObjectUtils
 *  org.apache.commons.lang3.math.NumberUtils
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.ResourceBasedServiceRegistry
 *  org.apereo.cas.services.ServiceRegistry
 *  org.apereo.cas.services.ServiceRegistryListener
 *  org.apereo.cas.support.events.service.CasRegisteredServiceDeletedEvent
 *  org.apereo.cas.support.events.service.CasRegisteredServiceLoadedEvent
 *  org.apereo.cas.support.events.service.CasRegisteredServicePreDeleteEvent
 *  org.apereo.cas.util.CollectionUtils
 *  org.apereo.cas.util.LoggingUtils
 *  org.apereo.cas.util.RegexUtils
 *  org.apereo.cas.util.ResourceUtils
 *  org.apereo.cas.util.function.FunctionUtils
 *  org.apereo.cas.util.io.PathWatcherService
 *  org.apereo.cas.util.io.WatcherService
 *  org.apereo.cas.util.serialization.StringSerializer
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.DisposableBean
 *  org.springframework.context.ApplicationEvent
 *  org.springframework.context.ConfigurableApplicationContext
 *  org.springframework.core.io.Resource
 *  org.springframework.util.Assert
 */
package org.apereo.cas.services.resource;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Generated;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apereo.cas.services.AbstractServiceRegistry;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.ResourceBasedServiceRegistry;
import org.apereo.cas.services.ServiceRegistry;
import org.apereo.cas.services.ServiceRegistryListener;
import org.apereo.cas.services.replication.NoOpRegisteredServiceReplicationStrategy;
import org.apereo.cas.services.replication.RegisteredServiceReplicationStrategy;
import org.apereo.cas.services.resource.BaseResourceBasedRegisteredServiceWatcher;
import org.apereo.cas.services.resource.CreateResourceBasedRegisteredServiceWatcher;
import org.apereo.cas.services.resource.DefaultRegisteredServiceResourceNamingStrategy;
import org.apereo.cas.services.resource.DeleteResourceBasedRegisteredServiceWatcher;
import org.apereo.cas.services.resource.ModifyResourceBasedRegisteredServiceWatcher;
import org.apereo.cas.services.resource.RegisteredServiceResourceNamingStrategy;
import org.apereo.cas.support.events.service.CasRegisteredServiceDeletedEvent;
import org.apereo.cas.support.events.service.CasRegisteredServiceLoadedEvent;
import org.apereo.cas.support.events.service.CasRegisteredServicePreDeleteEvent;
import org.apereo.cas.util.CollectionUtils;
import org.apereo.cas.util.LoggingUtils;
import org.apereo.cas.util.RegexUtils;
import org.apereo.cas.util.ResourceUtils;
import org.apereo.cas.util.function.FunctionUtils;
import org.apereo.cas.util.io.PathWatcherService;
import org.apereo.cas.util.io.WatcherService;
import org.apereo.cas.util.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

public abstract class AbstractResourceBasedServiceRegistry
extends AbstractServiceRegistry
implements ResourceBasedServiceRegistry,
DisposableBean {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractResourceBasedServiceRegistry.class);
    protected Path serviceRegistryDirectory;
    protected Map<Long, RegisteredService> services = new ConcurrentHashMap<Long, RegisteredService>();
    private Collection<StringSerializer<RegisteredService>> registeredServiceSerializers;
    private WatcherService serviceRegistryWatcherService;
    private RegisteredServiceReplicationStrategy registeredServiceReplicationStrategy;
    private RegisteredServiceResourceNamingStrategy resourceNamingStrategy;
    private Pattern serviceFileNamePattern;

    protected AbstractResourceBasedServiceRegistry(Resource configDirectory, Collection<StringSerializer<RegisteredService>> serializers, ConfigurableApplicationContext applicationContext, Collection<ServiceRegistryListener> serviceRegistryListeners) throws Exception {
        this(configDirectory, serializers, applicationContext, (RegisteredServiceReplicationStrategy)new NoOpRegisteredServiceReplicationStrategy(), (RegisteredServiceResourceNamingStrategy)new DefaultRegisteredServiceResourceNamingStrategy(), serviceRegistryListeners, WatcherService.noOp());
    }

    protected AbstractResourceBasedServiceRegistry(Resource configDirectory, Collection<StringSerializer<RegisteredService>> serializers, ConfigurableApplicationContext applicationContext, Collection<ServiceRegistryListener> serviceRegistryListeners, WatcherService serviceRegistryConfigWatcher) throws Exception {
        this(configDirectory, serializers, applicationContext, (RegisteredServiceReplicationStrategy)new NoOpRegisteredServiceReplicationStrategy(), (RegisteredServiceResourceNamingStrategy)new DefaultRegisteredServiceResourceNamingStrategy(), serviceRegistryListeners, serviceRegistryConfigWatcher);
    }

    protected AbstractResourceBasedServiceRegistry(Path configDirectory, StringSerializer<RegisteredService> serializer, ConfigurableApplicationContext applicationContext, RegisteredServiceReplicationStrategy registeredServiceReplicationStrategy, RegisteredServiceResourceNamingStrategy resourceNamingStrategy, Collection<ServiceRegistryListener> serviceRegistryListeners, WatcherService serviceRegistryConfigWatcher) {
        this(configDirectory, (Collection<StringSerializer<RegisteredService>>)CollectionUtils.wrap(serializer), applicationContext, registeredServiceReplicationStrategy, resourceNamingStrategy, serviceRegistryListeners, serviceRegistryConfigWatcher);
    }

    protected AbstractResourceBasedServiceRegistry(Path configDirectory, Collection<StringSerializer<RegisteredService>> serializers, ConfigurableApplicationContext applicationContext, RegisteredServiceReplicationStrategy registeredServiceReplicationStrategy, RegisteredServiceResourceNamingStrategy resourceNamingStrategy, Collection<ServiceRegistryListener> serviceRegistryListeners, WatcherService serviceRegistryConfigWatcher) {
        super(applicationContext, serviceRegistryListeners);
        this.initializeRegistry(configDirectory, serializers, registeredServiceReplicationStrategy, resourceNamingStrategy, serviceRegistryConfigWatcher);
    }

    protected AbstractResourceBasedServiceRegistry(Resource configDirectory, Collection<StringSerializer<RegisteredService>> serializers, ConfigurableApplicationContext applicationContext, RegisteredServiceReplicationStrategy registeredServiceReplicationStrategy, RegisteredServiceResourceNamingStrategy resourceNamingStrategy, Collection<ServiceRegistryListener> serviceRegistryListeners, WatcherService serviceRegistryConfigWatcher) throws Exception {
        super(applicationContext, serviceRegistryListeners);
        LOGGER.trace("Provided service registry directory is specified at [{}]", (Object)configDirectory);
        String pattern = String.join((CharSequence)"|", this.getExtensions());
        Resource servicesDirectory = Objects.requireNonNull(ResourceUtils.prepareClasspathResourceIfNeeded((Resource)configDirectory, (boolean)true, (String)pattern), () -> "Could not determine the services configuration directory from " + configDirectory);
        File file = servicesDirectory.getFile();
        LOGGER.trace("Prepared service registry directory is specified at [{}]", (Object)file);
        this.initializeRegistry(Paths.get(file.getCanonicalPath(), new String[0]), serializers, registeredServiceReplicationStrategy, resourceNamingStrategy, serviceRegistryConfigWatcher);
    }

    public void enableDefaultWatcherService() {
        LOGGER.info("Watching service registry directory at [{}]", (Object)this.serviceRegistryDirectory);
        this.serviceRegistryWatcherService.close();
        CreateResourceBasedRegisteredServiceWatcher onCreate = new CreateResourceBasedRegisteredServiceWatcher(this);
        DeleteResourceBasedRegisteredServiceWatcher onDelete = new DeleteResourceBasedRegisteredServiceWatcher(this);
        ModifyResourceBasedRegisteredServiceWatcher onModify = new ModifyResourceBasedRegisteredServiceWatcher(this);
        this.serviceRegistryWatcherService = new PathWatcherService(this.serviceRegistryDirectory, (Consumer)onCreate, (Consumer)onModify, (Consumer)onDelete);
        this.serviceRegistryWatcherService.start(this.getClass().getSimpleName());
    }

    public RegisteredService save(RegisteredService service) {
        if (service.getId() == -1L) {
            LOGGER.debug("Service id not set. Calculating id based on system time...");
            service.setId(System.currentTimeMillis());
        }
        File f = this.getRegisteredServiceFileName(service);
        try (OutputStream out = Files.newOutputStream(f.toPath(), new OpenOption[0]);){
            this.invokeServiceRegistryListenerPreSave(service);
            boolean result = this.registeredServiceSerializers.stream().anyMatch(s -> {
                try {
                    s.to(out, (Object)service);
                    return true;
                }
                catch (Exception e) {
                    LOGGER.debug(e.getMessage(), (Throwable)e);
                    return false;
                }
            });
            if (!result) {
                throw new IOException("The service definition file could not be saved at " + f.getCanonicalPath());
            }
            if (this.services.containsKey(service.getId())) {
                LOGGER.debug("Found existing service definition by id [{}]. Saving...", (Object)service.getId());
            }
            this.services.put(service.getId(), service);
            LOGGER.debug("Saved service to [{}]", (Object)f.getCanonicalPath());
        }
        catch (IOException e) {
            throw new IllegalArgumentException("IO error opening file stream.", e);
        }
        return this.findServiceById(service.getId());
    }

    public synchronized boolean delete(RegisteredService service) {
        return (Boolean)FunctionUtils.doUnchecked(() -> {
            boolean result;
            File f = this.getRegisteredServiceFileName(service);
            this.publishEvent((ApplicationEvent)new CasRegisteredServicePreDeleteEvent((Object)this, service));
            boolean bl = result = !f.exists() || f.delete();
            if (!result) {
                LOGGER.warn("Failed to delete service definition file [{}]", (Object)f.getCanonicalPath());
            } else {
                this.removeRegisteredService(service);
                LOGGER.debug("Successfully deleted service definition file [{}]", (Object)f.getCanonicalPath());
            }
            this.publishEvent((ApplicationEvent)new CasRegisteredServiceDeletedEvent((Object)this, service));
            return result;
        });
    }

    public void deleteAll() {
        Collection files = FileUtils.listFiles((File)this.serviceRegistryDirectory.toFile(), (String[])this.getExtensions(), (boolean)true);
        files.forEach(File::delete);
    }

    public synchronized Collection<RegisteredService> load() {
        LOGGER.trace("Loading files from [{}]", (Object)this.serviceRegistryDirectory);
        Collection files = FileUtils.listFiles((File)this.serviceRegistryDirectory.toFile(), (String[])this.getExtensions(), (boolean)true);
        LOGGER.trace("Located [{}] files from [{}] are [{}]", new Object[]{this.getExtensions(), this.serviceRegistryDirectory, files});
        this.services = files.stream().map(this::load).filter(Objects::nonNull).flatMap(Collection::stream).sorted().collect(Collectors.toMap(RegisteredService::getId, Function.identity(), (s1, s2) -> {
            BaseResourceBasedRegisteredServiceWatcher.LOG_SERVICE_DUPLICATE.accept((RegisteredService)s2);
            return s1;
        }, LinkedHashMap::new));
        ArrayList<RegisteredService> listedServices = new ArrayList<RegisteredService>(this.services.values());
        List<RegisteredService> results = this.registeredServiceReplicationStrategy.updateLoadedRegisteredServicesFromCache(listedServices, this);
        results.forEach(service -> this.publishEvent((ApplicationEvent)new CasRegisteredServiceLoadedEvent((Object)this, service)));
        return results;
    }

    public Collection<RegisteredService> load(File file) {
        Collection collection;
        block14: {
            String fileName = file.getName();
            if (!file.canRead()) {
                LOGGER.warn("[{}] is not readable. Check file permissions", (Object)fileName);
                return new ArrayList<RegisteredService>(0);
            }
            if (!file.exists()) {
                LOGGER.warn("[{}] is not found at the path specified", (Object)fileName);
                return new ArrayList<RegisteredService>(0);
            }
            if (file.length() == 0L) {
                LOGGER.debug("[{}] appears to be empty so no service definition will be loaded", (Object)fileName);
                return new ArrayList<RegisteredService>(0);
            }
            if (fileName.startsWith(".")) {
                LOGGER.debug("[{}] starts with ., ignoring", (Object)fileName);
                return new ArrayList<RegisteredService>(0);
            }
            if (Arrays.stream(this.getExtensions()).noneMatch(fileName::endsWith)) {
                LOGGER.debug("[{}] doesn't end with valid extension, ignoring", (Object)fileName);
                return new ArrayList<RegisteredService>(0);
            }
            if (!RegexUtils.matches((Pattern)this.serviceFileNamePattern, (String)fileName)) {
                LOGGER.warn("[{}] does not match the recommended pattern [{}]. While CAS tries to be forgiving as much as possible, it's recommended that you rename the file to match the requested pattern to avoid issues with duplicate service loading. Future CAS versions may try to strictly force the naming syntax, refusing to load the file.", (Object)fileName, (Object)this.serviceFileNamePattern.pattern());
            }
            LOGGER.debug("Attempting to read and parse [{}]", (Object)file.getAbsoluteFile());
            BufferedReader in = Files.newBufferedReader(file.toPath());
            try {
                collection = this.registeredServiceSerializers.stream().filter(s -> s.supports(file)).map(s -> s.load((Reader)in)).filter(Objects::nonNull).flatMap(Collection::stream).map(this::invokeServiceRegistryListenerPostLoad).filter(Objects::nonNull).collect(Collectors.toList());
                if (in == null) break block14;
            }
            catch (Throwable throwable) {
                try {
                    if (in != null) {
                        try {
                            in.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                catch (Exception e) {
                    LOGGER.error("Error reading configuration file [{}]", (Object)fileName);
                    LoggingUtils.error((Logger)LOGGER, (Throwable)e);
                    return new ArrayList<RegisteredService>(0);
                }
            }
            in.close();
        }
        return collection;
    }

    public Stream<? extends RegisteredService> getServicesStream() {
        return this.services.values().stream();
    }

    public RegisteredService findServiceById(long id) {
        RegisteredService service = this.services.get(id);
        return this.registeredServiceReplicationStrategy.getRegisteredServiceFromCacheIfAny(service, id, (ServiceRegistry)this);
    }

    public long size() {
        return this.services.size();
    }

    public void update(RegisteredService service) {
        this.services.put(service.getId(), service);
    }

    public void destroy() {
        this.serviceRegistryWatcherService.close();
    }

    private void initializeRegistry(Path configDirectory, Collection<StringSerializer<RegisteredService>> serializers, RegisteredServiceReplicationStrategy registeredServiceReplicationStrategy, RegisteredServiceResourceNamingStrategy resourceNamingStrategy, WatcherService serviceRegistryConfigWatcher) {
        this.registeredServiceReplicationStrategy = (RegisteredServiceReplicationStrategy)ObjectUtils.defaultIfNull((Object)registeredServiceReplicationStrategy, (Object)new NoOpRegisteredServiceReplicationStrategy());
        this.resourceNamingStrategy = (RegisteredServiceResourceNamingStrategy)ObjectUtils.defaultIfNull((Object)resourceNamingStrategy, (Object)new DefaultRegisteredServiceResourceNamingStrategy());
        this.registeredServiceSerializers = serializers;
        this.serviceFileNamePattern = resourceNamingStrategy.buildNamingPattern(this.getExtensions());
        LOGGER.trace("Constructed service name file pattern [{}]", (Object)this.serviceFileNamePattern.pattern());
        this.serviceRegistryDirectory = configDirectory;
        File file = this.serviceRegistryDirectory.toFile();
        Assert.isTrue((boolean)file.exists(), (String)(this.serviceRegistryDirectory + " does not exist"));
        Assert.isTrue((boolean)file.isDirectory(), (String)(this.serviceRegistryDirectory + " is not a directory"));
        LOGGER.trace("Service registry directory is specified at [{}]", (Object)file);
        this.serviceRegistryWatcherService = serviceRegistryConfigWatcher;
        this.serviceRegistryWatcherService.start(this.getClass().getSimpleName());
    }

    protected void removeRegisteredService(RegisteredService service) {
        this.services.remove(service.getId());
    }

    protected RegisteredService getRegisteredServiceFromFile(File file) {
        String fileName = file.getName();
        if (fileName.startsWith(".")) {
            LOGGER.trace("[{}] starts with ., ignoring...", (Object)fileName);
            return null;
        }
        if (Arrays.stream(this.getExtensions()).noneMatch(fileName::endsWith)) {
            LOGGER.trace("[{}] doesn't end with valid extension, ignoring", (Object)fileName);
            return null;
        }
        Matcher matcher = this.serviceFileNamePattern.matcher(fileName);
        if (matcher.find()) {
            String serviceId = matcher.group(2);
            if (NumberUtils.isCreatable((String)serviceId)) {
                long id = Long.parseLong(serviceId);
                return this.findServiceById(id);
            }
            String serviceName = matcher.group(1);
            return this.findServiceByExactServiceName(serviceName);
        }
        LOGGER.warn("Provided file [{}] does not match the recommended service definition file pattern [{}]", (Object)file.getName(), (Object)this.serviceFileNamePattern.pattern());
        return null;
    }

    protected File getRegisteredServiceFileName(RegisteredService service) {
        String fileName = this.resourceNamingStrategy.build(service, this.getExtensions()[0]);
        File svcFile = new File(this.serviceRegistryDirectory.toFile(), fileName);
        LOGGER.debug("Using [{}] as the service definition file", (Object)svcFile.getAbsolutePath());
        return svcFile;
    }

    protected abstract String[] getExtensions();

    @Generated
    public String toString() {
        return "AbstractResourceBasedServiceRegistry(serviceRegistryDirectory=" + this.serviceRegistryDirectory + ", services=" + this.services + ", registeredServiceSerializers=" + this.registeredServiceSerializers + ", serviceRegistryWatcherService=" + this.serviceRegistryWatcherService + ", registeredServiceReplicationStrategy=" + this.registeredServiceReplicationStrategy + ", resourceNamingStrategy=" + this.resourceNamingStrategy + ", serviceFileNamePattern=" + this.serviceFileNamePattern + ")";
    }

    @Generated
    public Path getServiceRegistryDirectory() {
        return this.serviceRegistryDirectory;
    }

    @Generated
    public Map<Long, RegisteredService> getServices() {
        return this.services;
    }

    @Generated
    public void setServiceRegistryWatcherService(WatcherService serviceRegistryWatcherService) {
        this.serviceRegistryWatcherService = serviceRegistryWatcherService;
    }
}

