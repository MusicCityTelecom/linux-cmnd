/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.config.BeanFactoryPostProcessor
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 *  org.springframework.context.ApplicationEvent
 *  org.springframework.context.ConfigurableApplicationContext
 *  org.springframework.context.event.SmartApplicationListener
 *  org.springframework.core.Ordered
 *  org.springframework.core.env.ConfigurableEnvironment
 *  org.springframework.core.env.Environment
 *  org.springframework.core.env.MutablePropertySources
 *  org.springframework.core.env.Profiles
 *  org.springframework.core.env.PropertySource
 *  org.springframework.core.io.DefaultResourceLoader
 *  org.springframework.core.io.FileSystemResource
 *  org.springframework.core.io.Resource
 *  org.springframework.core.io.ResourceLoader
 *  org.springframework.core.io.support.SpringFactoriesLoader
 *  org.springframework.util.Assert
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.ObjectUtils
 *  org.springframework.util.ResourceUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.context.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import org.apache.commons.logging.Log;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.DefaultPropertiesPropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.config.ConfigDataLocation;
import org.springframework.boot.context.config.FilteredPropertySource;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.bind.PropertySourcesPlaceholdersResolver;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.boot.env.RandomValuePropertySource;
import org.springframework.boot.logging.DeferredLog;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.Profiles;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

@Deprecated
public class ConfigFileApplicationListener
implements EnvironmentPostProcessor,
SmartApplicationListener,
Ordered {
    private static final String DEFAULT_SEARCH_LOCATIONS = "classpath:/,classpath:/config/,file:./,file:./config/*/,file:./config/";
    private static final String DEFAULT_NAMES = "application";
    private static final Set<String> NO_SEARCH_NAMES = Collections.singleton(null);
    private static final Bindable<String[]> STRING_ARRAY = Bindable.of(String[].class);
    private static final Bindable<List<String>> STRING_LIST = Bindable.listOf(String.class);
    private static final Set<String> LOAD_FILTERED_PROPERTY;
    public static final String ACTIVE_PROFILES_PROPERTY = "spring.profiles.active";
    public static final String INCLUDE_PROFILES_PROPERTY = "spring.profiles.include";
    public static final String CONFIG_NAME_PROPERTY = "spring.config.name";
    public static final String CONFIG_LOCATION_PROPERTY = "spring.config.location";
    public static final String CONFIG_ADDITIONAL_LOCATION_PROPERTY = "spring.config.additional-location";
    public static final int DEFAULT_ORDER = -2147483638;
    private final Log logger;
    private static final Resource[] EMPTY_RESOURCES;
    private static final Comparator<File> FILE_COMPARATOR;
    private String searchLocations;
    private String names;
    private int order = -2147483638;

    public ConfigFileApplicationListener() {
        this(new DeferredLog());
    }

    ConfigFileApplicationListener(Log logger) {
        this.logger = logger;
    }

    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return ApplicationEnvironmentPreparedEvent.class.isAssignableFrom(eventType) || ApplicationPreparedEvent.class.isAssignableFrom(eventType);
    }

    public void onApplicationEvent(ApplicationEvent event) {
        throw new IllegalStateException("ConfigFileApplicationListener [" + this.getClass().getName() + "] is deprecated and can only be used as an EnvironmentPostProcessor");
    }

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        this.addPropertySources(environment, application.getResourceLoader());
    }

    protected void addPropertySources(ConfigurableEnvironment environment, ResourceLoader resourceLoader) {
        RandomValuePropertySource.addToEnvironment(environment);
        new Loader(environment, resourceLoader).load();
    }

    protected void addPostProcessors(ConfigurableApplicationContext context) {
        context.addBeanFactoryPostProcessor((BeanFactoryPostProcessor)new PropertySourceOrderingPostProcessor(context));
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getOrder() {
        return this.order;
    }

    public void setSearchLocations(String locations) {
        Assert.hasLength((String)locations, (String)"Locations must not be empty");
        this.searchLocations = locations;
    }

    public void setSearchNames(String names) {
        Assert.hasLength((String)names, (String)"Names must not be empty");
        this.names = names;
    }

    static {
        HashSet<String> filteredProperties = new HashSet<String>();
        filteredProperties.add(ACTIVE_PROFILES_PROPERTY);
        filteredProperties.add(INCLUDE_PROFILES_PROPERTY);
        LOAD_FILTERED_PROPERTY = Collections.unmodifiableSet(filteredProperties);
        EMPTY_RESOURCES = new Resource[0];
        FILE_COMPARATOR = Comparator.comparing(File::getAbsolutePath);
    }

    @FunctionalInterface
    private static interface DocumentConsumer {
        public void accept(Profile var1, Document var2);
    }

    @FunctionalInterface
    private static interface DocumentFilter {
        public boolean match(Document var1);
    }

    @FunctionalInterface
    private static interface DocumentFilterFactory {
        public DocumentFilter getDocumentFilter(Profile var1);
    }

    private static class Document {
        private final PropertySource<?> propertySource;
        private String[] profiles;
        private final Set<Profile> activeProfiles;
        private final Set<Profile> includeProfiles;

        Document(PropertySource<?> propertySource, String[] profiles, Set<Profile> activeProfiles, Set<Profile> includeProfiles) {
            this.propertySource = propertySource;
            this.profiles = profiles;
            this.activeProfiles = activeProfiles;
            this.includeProfiles = includeProfiles;
        }

        PropertySource<?> getPropertySource() {
            return this.propertySource;
        }

        String[] getProfiles() {
            return this.profiles;
        }

        Set<Profile> getActiveProfiles() {
            return this.activeProfiles;
        }

        Set<Profile> getIncludeProfiles() {
            return this.includeProfiles;
        }

        public String toString() {
            return this.propertySource.toString();
        }
    }

    private static class DocumentsCacheKey {
        private final PropertySourceLoader loader;
        private final Resource resource;

        DocumentsCacheKey(PropertySourceLoader loader, Resource resource) {
            this.loader = loader;
            this.resource = resource;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || this.getClass() != obj.getClass()) {
                return false;
            }
            DocumentsCacheKey other = (DocumentsCacheKey)obj;
            return this.loader.equals(other.loader) && this.resource.equals(other.resource);
        }

        public int hashCode() {
            return this.loader.hashCode() * 31 + this.resource.hashCode();
        }
    }

    private static class Profile {
        private final String name;
        private final boolean defaultProfile;

        Profile(String name) {
            this(name, false);
        }

        Profile(String name, boolean defaultProfile) {
            Assert.notNull((Object)name, (String)"Name must not be null");
            this.name = name;
            this.defaultProfile = defaultProfile;
        }

        String getName() {
            return this.name;
        }

        boolean isDefaultProfile() {
            return this.defaultProfile;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || obj.getClass() != this.getClass()) {
                return false;
            }
            return ((Profile)obj).name.equals(this.name);
        }

        public int hashCode() {
            return this.name.hashCode();
        }

        public String toString() {
            return this.name;
        }
    }

    private class Loader {
        private final Log logger;
        private final ConfigurableEnvironment environment;
        private final PropertySourcesPlaceholdersResolver placeholdersResolver;
        private final ResourceLoader resourceLoader;
        private final List<PropertySourceLoader> propertySourceLoaders;
        private Deque<Profile> profiles;
        private List<Profile> processedProfiles;
        private boolean activatedProfiles;
        private Map<Profile, MutablePropertySources> loaded;
        private Map<DocumentsCacheKey, List<Document>> loadDocumentsCache;

        Loader(ConfigurableEnvironment environment, ResourceLoader resourceLoader) {
            this.logger = ConfigFileApplicationListener.this.logger;
            this.loadDocumentsCache = new HashMap<DocumentsCacheKey, List<Document>>();
            this.environment = environment;
            this.placeholdersResolver = new PropertySourcesPlaceholdersResolver((Environment)this.environment);
            this.resourceLoader = resourceLoader != null ? resourceLoader : new DefaultResourceLoader(null);
            this.propertySourceLoaders = SpringFactoriesLoader.loadFactories(PropertySourceLoader.class, (ClassLoader)this.resourceLoader.getClassLoader());
        }

        void load() {
            FilteredPropertySource.apply(this.environment, "defaultProperties", LOAD_FILTERED_PROPERTY, this::loadWithFilteredProperties);
        }

        private void loadWithFilteredProperties(PropertySource<?> defaultProperties) {
            this.profiles = new LinkedList<Profile>();
            this.processedProfiles = new LinkedList<Profile>();
            this.activatedProfiles = false;
            this.loaded = new LinkedHashMap<Profile, MutablePropertySources>();
            this.initializeProfiles();
            while (!this.profiles.isEmpty()) {
                Profile profile = this.profiles.poll();
                if (this.isDefaultProfile(profile)) {
                    this.addProfileToEnvironment(profile.getName());
                }
                this.load(profile, this::getPositiveProfileFilter, this.addToLoaded(MutablePropertySources::addLast, false));
                this.processedProfiles.add(profile);
            }
            this.load(null, this::getNegativeProfileFilter, this.addToLoaded(MutablePropertySources::addFirst, true));
            this.addLoadedPropertySources();
            this.applyActiveProfiles(defaultProperties);
        }

        private void initializeProfiles() {
            this.profiles.add(null);
            Binder binder = Binder.get((Environment)this.environment);
            Set<Profile> activatedViaProperty = this.getProfiles(binder, ConfigFileApplicationListener.ACTIVE_PROFILES_PROPERTY);
            Set<Profile> includedViaProperty = this.getProfiles(binder, ConfigFileApplicationListener.INCLUDE_PROFILES_PROPERTY);
            List<Profile> otherActiveProfiles = this.getOtherActiveProfiles(activatedViaProperty, includedViaProperty);
            this.profiles.addAll(otherActiveProfiles);
            this.profiles.addAll(includedViaProperty);
            this.addActiveProfiles(activatedViaProperty);
            if (this.profiles.size() == 1) {
                for (String defaultProfileName : this.getDefaultProfiles(binder)) {
                    Profile defaultProfile = new Profile(defaultProfileName, true);
                    this.profiles.add(defaultProfile);
                }
            }
        }

        private String[] getDefaultProfiles(Binder binder) {
            return binder.bind("spring.profiles.default", STRING_ARRAY).orElseGet(() -> ((ConfigurableEnvironment)this.environment).getDefaultProfiles());
        }

        private List<Profile> getOtherActiveProfiles(Set<Profile> activatedViaProperty, Set<Profile> includedViaProperty) {
            return Arrays.stream(this.environment.getActiveProfiles()).map(Profile::new).filter(profile -> !activatedViaProperty.contains(profile) && !includedViaProperty.contains(profile)).collect(Collectors.toList());
        }

        void addActiveProfiles(Set<Profile> profiles) {
            if (profiles.isEmpty()) {
                return;
            }
            if (this.activatedProfiles) {
                if (this.logger.isDebugEnabled()) {
                    this.logger.debug((Object)("Profiles already activated, '" + profiles + "' will not be applied"));
                }
                return;
            }
            this.profiles.addAll(profiles);
            if (this.logger.isDebugEnabled()) {
                this.logger.debug((Object)("Activated activeProfiles " + StringUtils.collectionToCommaDelimitedString(profiles)));
            }
            this.activatedProfiles = true;
            this.removeUnprocessedDefaultProfiles();
        }

        private void removeUnprocessedDefaultProfiles() {
            this.profiles.removeIf(profile -> profile != null && profile.isDefaultProfile());
        }

        private DocumentFilter getPositiveProfileFilter(Profile profile) {
            return document -> {
                if (profile == null) {
                    return ObjectUtils.isEmpty((Object[])document.getProfiles());
                }
                return ObjectUtils.containsElement((Object[])document.getProfiles(), (Object)profile.getName()) && this.environment.acceptsProfiles(Profiles.of((String[])document.getProfiles()));
            };
        }

        private DocumentFilter getNegativeProfileFilter(Profile profile) {
            return document -> profile == null && !ObjectUtils.isEmpty((Object[])document.getProfiles()) && this.environment.acceptsProfiles(Profiles.of((String[])document.getProfiles()));
        }

        private DocumentConsumer addToLoaded(BiConsumer<MutablePropertySources, PropertySource<?>> addMethod, boolean checkForExisting) {
            return (profile, document) -> {
                if (checkForExisting) {
                    for (MutablePropertySources merged : this.loaded.values()) {
                        if (!merged.contains(document.getPropertySource().getName())) continue;
                        return;
                    }
                }
                MutablePropertySources merged = this.loaded.computeIfAbsent(profile, k -> new MutablePropertySources());
                addMethod.accept(merged, document.getPropertySource());
            };
        }

        private void load(Profile profile, DocumentFilterFactory filterFactory, DocumentConsumer consumer) {
            this.getSearchLocations().forEach(location -> {
                String nonOptionalLocation = ConfigDataLocation.of(location).getValue();
                boolean isDirectory = location.endsWith("/");
                Set<String> names = isDirectory ? this.getSearchNames() : NO_SEARCH_NAMES;
                names.forEach(name -> this.load(nonOptionalLocation, (String)name, profile, filterFactory, consumer));
            });
        }

        private void load(String location, String name, Profile profile, DocumentFilterFactory filterFactory, DocumentConsumer consumer) {
            if (!StringUtils.hasText((String)name)) {
                for (PropertySourceLoader loader : this.propertySourceLoaders) {
                    if (!this.canLoadFileExtension(loader, location)) continue;
                    this.load(loader, location, profile, filterFactory.getDocumentFilter(profile), consumer);
                    return;
                }
                throw new IllegalStateException("File extension of config file location '" + location + "' is not known to any PropertySourceLoader. If the location is meant to reference a directory, it must end in '/'");
            }
            HashSet<String> processed = new HashSet<String>();
            for (PropertySourceLoader loader : this.propertySourceLoaders) {
                for (String fileExtension : loader.getFileExtensions()) {
                    if (!processed.add(fileExtension)) continue;
                    this.loadForFileExtension(loader, location + name, "." + fileExtension, profile, filterFactory, consumer);
                }
            }
        }

        private boolean canLoadFileExtension(PropertySourceLoader loader, String name) {
            return Arrays.stream(loader.getFileExtensions()).anyMatch(fileExtension -> StringUtils.endsWithIgnoreCase((String)name, (String)fileExtension));
        }

        private void loadForFileExtension(PropertySourceLoader loader, String prefix, String fileExtension, Profile profile, DocumentFilterFactory filterFactory, DocumentConsumer consumer) {
            DocumentFilter defaultFilter = filterFactory.getDocumentFilter(null);
            DocumentFilter profileFilter = filterFactory.getDocumentFilter(profile);
            if (profile != null) {
                String profileSpecificFile = prefix + "-" + profile + fileExtension;
                this.load(loader, profileSpecificFile, profile, defaultFilter, consumer);
                this.load(loader, profileSpecificFile, profile, profileFilter, consumer);
                for (Profile processedProfile : this.processedProfiles) {
                    if (processedProfile == null) continue;
                    String previouslyLoaded = prefix + "-" + processedProfile + fileExtension;
                    this.load(loader, previouslyLoaded, profile, profileFilter, consumer);
                }
            }
            this.load(loader, prefix + fileExtension, profile, profileFilter, consumer);
        }

        private void load(PropertySourceLoader loader, String location, Profile profile, DocumentFilter filter, DocumentConsumer consumer) {
            Resource[] resources;
            for (Resource resource : resources = this.getResources(location)) {
                try {
                    StringBuilder description;
                    if (resource == null || !resource.exists()) {
                        if (!this.logger.isTraceEnabled()) continue;
                        description = this.getDescription("Skipped missing config ", location, resource, profile);
                        this.logger.trace((Object)description);
                        continue;
                    }
                    if (!StringUtils.hasText((String)StringUtils.getFilenameExtension((String)resource.getFilename()))) {
                        if (!this.logger.isTraceEnabled()) continue;
                        description = this.getDescription("Skipped empty config extension ", location, resource, profile);
                        this.logger.trace((Object)description);
                        continue;
                    }
                    if (resource.isFile() && this.isPatternLocation(location) && this.hasHiddenPathElement(resource)) {
                        if (!this.logger.isTraceEnabled()) continue;
                        description = this.getDescription("Skipped location with hidden path element ", location, resource, profile);
                        this.logger.trace((Object)description);
                        continue;
                    }
                    String name = "applicationConfig: [" + this.getLocationName(location, resource) + "]";
                    List<Document> documents = this.loadDocuments(loader, name, resource);
                    if (CollectionUtils.isEmpty(documents)) {
                        if (!this.logger.isTraceEnabled()) continue;
                        StringBuilder description2 = this.getDescription("Skipped unloaded config ", location, resource, profile);
                        this.logger.trace((Object)description2);
                        continue;
                    }
                    ArrayList<Document> loaded = new ArrayList<Document>();
                    for (Document document2 : documents) {
                        if (!filter.match(document2)) continue;
                        this.addActiveProfiles(document2.getActiveProfiles());
                        this.addIncludedProfiles(document2.getIncludeProfiles());
                        loaded.add(document2);
                    }
                    Collections.reverse(loaded);
                    if (loaded.isEmpty()) continue;
                    loaded.forEach(document -> consumer.accept(profile, (Document)document));
                    if (!this.logger.isDebugEnabled()) continue;
                    StringBuilder description3 = this.getDescription("Loaded config file ", location, resource, profile);
                    this.logger.debug((Object)description3);
                }
                catch (Exception ex) {
                    StringBuilder description = this.getDescription("Failed to load property source from ", location, resource, profile);
                    throw new IllegalStateException(description.toString(), ex);
                }
            }
        }

        private boolean hasHiddenPathElement(Resource resource) throws IOException {
            String cleanPath = StringUtils.cleanPath((String)resource.getFile().getAbsolutePath());
            for (Path value : Paths.get(cleanPath, new String[0])) {
                if (!value.toString().startsWith("..")) continue;
                return true;
            }
            return false;
        }

        private String getLocationName(String locationReference, Resource resource) {
            if (!locationReference.contains("*")) {
                return locationReference;
            }
            if (resource instanceof FileSystemResource) {
                return ((FileSystemResource)resource).getPath();
            }
            return resource.getDescription();
        }

        private Resource[] getResources(String locationReference) {
            try {
                if (this.isPatternLocation(locationReference)) {
                    return this.getResourcesFromPatternLocationReference(locationReference);
                }
                return new Resource[]{this.resourceLoader.getResource(locationReference)};
            }
            catch (Exception ex) {
                return EMPTY_RESOURCES;
            }
        }

        private boolean isPatternLocation(String location) {
            return location.contains("*");
        }

        private Resource[] getResourcesFromPatternLocationReference(String locationReference) throws IOException {
            String directoryPath = locationReference.substring(0, locationReference.indexOf("*/"));
            Resource resource = this.resourceLoader.getResource(directoryPath);
            File[] files = resource.getFile().listFiles(File::isDirectory);
            if (files != null) {
                String fileName = locationReference.substring(locationReference.lastIndexOf("/") + 1);
                Arrays.sort(files, FILE_COMPARATOR);
                return (Resource[])Arrays.stream(files).map(file -> file.listFiles((dir, name) -> name.equals(fileName))).filter(Objects::nonNull).flatMap(Arrays::stream).map(FileSystemResource::new).toArray(Resource[]::new);
            }
            return EMPTY_RESOURCES;
        }

        private void addIncludedProfiles(Set<Profile> includeProfiles) {
            LinkedList<Profile> existingProfiles = new LinkedList<Profile>(this.profiles);
            this.profiles.clear();
            this.profiles.addAll(includeProfiles);
            this.profiles.removeAll(this.processedProfiles);
            this.profiles.addAll(existingProfiles);
        }

        private List<Document> loadDocuments(PropertySourceLoader loader, String name, Resource resource) throws IOException {
            DocumentsCacheKey cacheKey = new DocumentsCacheKey(loader, resource);
            List<Document> documents = this.loadDocumentsCache.get(cacheKey);
            if (documents == null) {
                List<PropertySource<?>> loaded = loader.load(name, resource);
                documents = this.asDocuments(loaded);
                this.loadDocumentsCache.put(cacheKey, documents);
            }
            return documents;
        }

        private List<Document> asDocuments(List<PropertySource<?>> loaded) {
            if (loaded == null) {
                return Collections.emptyList();
            }
            return loaded.stream().map(propertySource -> {
                Binder binder = new Binder(ConfigurationPropertySources.from(propertySource), this.placeholdersResolver);
                String[] profiles = binder.bind("spring.profiles", STRING_ARRAY).orElse(null);
                Set<Profile> activeProfiles = this.getProfiles(binder, ConfigFileApplicationListener.ACTIVE_PROFILES_PROPERTY);
                Set<Profile> includeProfiles = this.getProfiles(binder, ConfigFileApplicationListener.INCLUDE_PROFILES_PROPERTY);
                return new Document((PropertySource<?>)propertySource, profiles, activeProfiles, includeProfiles);
            }).collect(Collectors.toList());
        }

        private StringBuilder getDescription(String prefix, String locationReference, Resource resource, Profile profile) {
            StringBuilder result = new StringBuilder(prefix);
            try {
                if (resource != null) {
                    String uri = resource.getURI().toASCIIString();
                    result.append("'");
                    result.append(uri);
                    result.append("' (");
                    result.append(locationReference);
                    result.append(")");
                }
            }
            catch (IOException ex) {
                result.append(locationReference);
            }
            if (profile != null) {
                result.append(" for profile ");
                result.append(profile);
            }
            return result;
        }

        private Set<Profile> getProfiles(Binder binder, String name) {
            return binder.bind(name, STRING_ARRAY).map(this::asProfileSet).orElse(Collections.emptySet());
        }

        private Set<Profile> asProfileSet(String[] profileNames) {
            ArrayList<Profile> profiles = new ArrayList<Profile>();
            for (String profileName : profileNames) {
                profiles.add(new Profile(profileName));
            }
            return new LinkedHashSet<Profile>(profiles);
        }

        private void addProfileToEnvironment(String profile) {
            for (String activeProfile : this.environment.getActiveProfiles()) {
                if (!activeProfile.equals(profile)) continue;
                return;
            }
            this.environment.addActiveProfile(profile);
        }

        private Set<String> getSearchLocations() {
            Set<String> locations = this.getSearchLocations(ConfigFileApplicationListener.CONFIG_ADDITIONAL_LOCATION_PROPERTY);
            if (this.environment.containsProperty(ConfigFileApplicationListener.CONFIG_LOCATION_PROPERTY)) {
                locations.addAll(this.getSearchLocations(ConfigFileApplicationListener.CONFIG_LOCATION_PROPERTY));
            } else {
                locations.addAll(this.asResolvedSet(ConfigFileApplicationListener.this.searchLocations, ConfigFileApplicationListener.DEFAULT_SEARCH_LOCATIONS));
            }
            return locations;
        }

        private Set<String> getSearchLocations(String propertyName) {
            LinkedHashSet<String> locations = new LinkedHashSet<String>();
            if (this.environment.containsProperty(propertyName)) {
                for (String path : this.asResolvedSet(this.environment.getProperty(propertyName), null)) {
                    if (!path.contains("$")) {
                        Assert.state((!(path = StringUtils.cleanPath((String)path)).startsWith("classpath*:") ? 1 : 0) != 0, (String)"Classpath wildcard patterns cannot be used as a search location");
                        this.validateWildcardLocation(path);
                        if (!ResourceUtils.isUrl((String)path)) {
                            path = "file:" + path;
                        }
                    }
                    locations.add(path);
                }
            }
            return locations;
        }

        private void validateWildcardLocation(String path) {
            if (path.contains("*")) {
                Assert.state((StringUtils.countOccurrencesOf((String)path, (String)"*") == 1 ? 1 : 0) != 0, () -> "Search location '" + path + "' cannot contain multiple wildcards");
                String directoryPath = path.substring(0, path.lastIndexOf("/") + 1);
                Assert.state((boolean)directoryPath.endsWith("*/"), () -> "Search location '" + path + "' must end with '*/'");
            }
        }

        private Set<String> getSearchNames() {
            if (this.environment.containsProperty(ConfigFileApplicationListener.CONFIG_NAME_PROPERTY)) {
                String property = this.environment.getProperty(ConfigFileApplicationListener.CONFIG_NAME_PROPERTY);
                Set<String> names = this.asResolvedSet(property, null);
                names.forEach(this::assertValidConfigName);
                return names;
            }
            return this.asResolvedSet(ConfigFileApplicationListener.this.names, ConfigFileApplicationListener.DEFAULT_NAMES);
        }

        private Set<String> asResolvedSet(String value, String fallback) {
            List<String> list = Arrays.asList(StringUtils.trimArrayElements((String[])StringUtils.commaDelimitedListToStringArray((String)(value != null ? this.environment.resolvePlaceholders(value) : fallback))));
            Collections.reverse(list);
            return new LinkedHashSet<String>(list);
        }

        private void assertValidConfigName(String name) {
            Assert.state((!name.contains("*") ? 1 : 0) != 0, () -> "Config name '" + name + "' cannot contain wildcards");
        }

        private void addLoadedPropertySources() {
            MutablePropertySources destination = this.environment.getPropertySources();
            ArrayList<MutablePropertySources> loaded = new ArrayList<MutablePropertySources>(this.loaded.values());
            Collections.reverse(loaded);
            String lastAdded = null;
            HashSet<String> added = new HashSet<String>();
            for (MutablePropertySources sources : loaded) {
                for (PropertySource source : sources) {
                    if (!added.add(source.getName())) continue;
                    this.addLoadedPropertySource(destination, lastAdded, source);
                    lastAdded = source.getName();
                }
            }
        }

        private void addLoadedPropertySource(MutablePropertySources destination, String lastAdded, PropertySource<?> source) {
            if (lastAdded == null) {
                if (destination.contains("defaultProperties")) {
                    destination.addBefore("defaultProperties", source);
                } else {
                    destination.addLast(source);
                }
            } else {
                destination.addAfter(lastAdded, source);
            }
        }

        private void applyActiveProfiles(PropertySource<?> defaultProperties) {
            ArrayList<String> activeProfiles = new ArrayList<String>();
            if (defaultProperties != null) {
                Binder binder = new Binder(ConfigurationPropertySources.from(defaultProperties), new PropertySourcesPlaceholdersResolver((Environment)this.environment));
                activeProfiles.addAll(this.bindStringList(binder, ConfigFileApplicationListener.INCLUDE_PROFILES_PROPERTY));
                if (!this.activatedProfiles) {
                    activeProfiles.addAll(this.bindStringList(binder, ConfigFileApplicationListener.ACTIVE_PROFILES_PROPERTY));
                }
            }
            this.processedProfiles.stream().filter(this::isDefaultProfile).map(Profile::getName).forEach(activeProfiles::add);
            this.environment.setActiveProfiles(activeProfiles.toArray(new String[0]));
        }

        private boolean isDefaultProfile(Profile profile) {
            return profile != null && !profile.isDefaultProfile();
        }

        private List<String> bindStringList(Binder binder, String property) {
            return binder.bind(property, STRING_LIST).orElse(Collections.emptyList());
        }
    }

    private static class PropertySourceOrderingPostProcessor
    implements BeanFactoryPostProcessor,
    Ordered {
        private final ConfigurableApplicationContext context;

        PropertySourceOrderingPostProcessor(ConfigurableApplicationContext context) {
            this.context = context;
        }

        public int getOrder() {
            return Integer.MIN_VALUE;
        }

        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
            this.reorderSources(this.context.getEnvironment());
        }

        private void reorderSources(ConfigurableEnvironment environment) {
            DefaultPropertiesPropertySource.moveToEnd(environment);
        }
    }
}

