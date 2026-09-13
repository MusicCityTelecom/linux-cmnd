/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.springframework.core.Ordered
 *  org.springframework.core.io.ClassPathResource
 *  org.springframework.core.io.Resource
 *  org.springframework.core.io.ResourceLoader
 *  org.springframework.core.io.support.SpringFactoriesLoader
 *  org.springframework.core.log.LogMessage
 *  org.springframework.util.Assert
 *  org.springframework.util.ObjectUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.context.config;

import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.commons.logging.Log;
import org.springframework.boot.context.config.ConfigDataLocation;
import org.springframework.boot.context.config.ConfigDataLocationNotFoundException;
import org.springframework.boot.context.config.ConfigDataLocationResolver;
import org.springframework.boot.context.config.ConfigDataLocationResolverContext;
import org.springframework.boot.context.config.ConfigDataNotFoundException;
import org.springframework.boot.context.config.ConfigDataResource;
import org.springframework.boot.context.config.LocationResourceLoader;
import org.springframework.boot.context.config.Profiles;
import org.springframework.boot.context.config.StandardConfigDataReference;
import org.springframework.boot.context.config.StandardConfigDataResource;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.core.Ordered;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.core.log.LogMessage;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class StandardConfigDataLocationResolver
implements ConfigDataLocationResolver<StandardConfigDataResource>,
Ordered {
    private static final String PREFIX = "resource:";
    static final String CONFIG_NAME_PROPERTY = "spring.config.name";
    private static final String[] DEFAULT_CONFIG_NAMES = new String[]{"application"};
    private static final Pattern URL_PREFIX = Pattern.compile("^([a-zA-Z][a-zA-Z0-9*]*?:)(.*$)");
    private static final Pattern EXTENSION_HINT_PATTERN = Pattern.compile("^(.*)\\[(\\.\\w+)\\](?!\\[)$");
    private static final String NO_PROFILE = null;
    private final Log logger;
    private final List<PropertySourceLoader> propertySourceLoaders;
    private final String[] configNames;
    private final LocationResourceLoader resourceLoader;

    public StandardConfigDataLocationResolver(Log logger, Binder binder, ResourceLoader resourceLoader) {
        this.logger = logger;
        this.propertySourceLoaders = SpringFactoriesLoader.loadFactories(PropertySourceLoader.class, (ClassLoader)this.getClass().getClassLoader());
        this.configNames = this.getConfigNames(binder);
        this.resourceLoader = new LocationResourceLoader(resourceLoader);
    }

    private String[] getConfigNames(Binder binder) {
        String[] configNames;
        for (String configName : configNames = binder.bind(CONFIG_NAME_PROPERTY, String[].class).orElse(DEFAULT_CONFIG_NAMES)) {
            this.validateConfigName(configName);
        }
        return configNames;
    }

    private void validateConfigName(String name) {
        Assert.state((!name.contains("*") ? 1 : 0) != 0, () -> "Config name '" + name + "' cannot contain '*'");
    }

    public int getOrder() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isResolvable(ConfigDataLocationResolverContext context, ConfigDataLocation location) {
        return true;
    }

    @Override
    public List<StandardConfigDataResource> resolve(ConfigDataLocationResolverContext context, ConfigDataLocation location) throws ConfigDataNotFoundException {
        return this.resolve(this.getReferences(context, location.split()));
    }

    private Set<StandardConfigDataReference> getReferences(ConfigDataLocationResolverContext context, ConfigDataLocation[] configDataLocations) {
        LinkedHashSet<StandardConfigDataReference> references = new LinkedHashSet<StandardConfigDataReference>();
        for (ConfigDataLocation configDataLocation : configDataLocations) {
            references.addAll(this.getReferences(context, configDataLocation));
        }
        return references;
    }

    private Set<StandardConfigDataReference> getReferences(ConfigDataLocationResolverContext context, ConfigDataLocation configDataLocation) {
        String resourceLocation = this.getResourceLocation(context, configDataLocation);
        try {
            if (this.isDirectory(resourceLocation)) {
                return this.getReferencesForDirectory(configDataLocation, resourceLocation, NO_PROFILE);
            }
            return this.getReferencesForFile(configDataLocation, resourceLocation, NO_PROFILE);
        }
        catch (RuntimeException ex) {
            throw new IllegalStateException("Unable to load config data from '" + configDataLocation + "'", ex);
        }
    }

    @Override
    public List<StandardConfigDataResource> resolveProfileSpecific(ConfigDataLocationResolverContext context, ConfigDataLocation location, Profiles profiles) {
        return this.resolve(this.getProfileSpecificReferences(context, location.split(), profiles));
    }

    private Set<StandardConfigDataReference> getProfileSpecificReferences(ConfigDataLocationResolverContext context, ConfigDataLocation[] configDataLocations, Profiles profiles) {
        LinkedHashSet<StandardConfigDataReference> references = new LinkedHashSet<StandardConfigDataReference>();
        for (String profile : profiles) {
            for (ConfigDataLocation configDataLocation : configDataLocations) {
                String resourceLocation = this.getResourceLocation(context, configDataLocation);
                references.addAll(this.getReferences(configDataLocation, resourceLocation, profile));
            }
        }
        return references;
    }

    private String getResourceLocation(ConfigDataLocationResolverContext context, ConfigDataLocation configDataLocation) {
        boolean isAbsolute;
        String resourceLocation = configDataLocation.getNonPrefixedValue(PREFIX);
        boolean bl = isAbsolute = resourceLocation.startsWith("/") || URL_PREFIX.matcher(resourceLocation).matches();
        if (isAbsolute) {
            return resourceLocation;
        }
        ConfigDataResource parent = context.getParent();
        if (parent instanceof StandardConfigDataResource) {
            String parentResourceLocation = ((StandardConfigDataResource)parent).getReference().getResourceLocation();
            String parentDirectory = parentResourceLocation.substring(0, parentResourceLocation.lastIndexOf("/") + 1);
            return parentDirectory + resourceLocation;
        }
        return resourceLocation;
    }

    private Set<StandardConfigDataReference> getReferences(ConfigDataLocation configDataLocation, String resourceLocation, String profile) {
        if (this.isDirectory(resourceLocation)) {
            return this.getReferencesForDirectory(configDataLocation, resourceLocation, profile);
        }
        return this.getReferencesForFile(configDataLocation, resourceLocation, profile);
    }

    private Set<StandardConfigDataReference> getReferencesForDirectory(ConfigDataLocation configDataLocation, String directory, String profile) {
        LinkedHashSet<StandardConfigDataReference> references = new LinkedHashSet<StandardConfigDataReference>();
        for (String name : this.configNames) {
            Deque<StandardConfigDataReference> referencesForName = this.getReferencesForConfigName(name, configDataLocation, directory, profile);
            references.addAll(referencesForName);
        }
        return references;
    }

    private Deque<StandardConfigDataReference> getReferencesForConfigName(String name, ConfigDataLocation configDataLocation, String directory, String profile) {
        ArrayDeque<StandardConfigDataReference> references = new ArrayDeque<StandardConfigDataReference>();
        for (PropertySourceLoader propertySourceLoader : this.propertySourceLoaders) {
            for (String extension : propertySourceLoader.getFileExtensions()) {
                StandardConfigDataReference reference = new StandardConfigDataReference(configDataLocation, directory, directory + name, profile, extension, propertySourceLoader);
                if (references.contains(reference)) continue;
                references.addFirst(reference);
            }
        }
        return references;
    }

    private Set<StandardConfigDataReference> getReferencesForFile(ConfigDataLocation configDataLocation, String file, String profile) {
        Matcher extensionHintMatcher = EXTENSION_HINT_PATTERN.matcher(file);
        boolean extensionHintLocation = extensionHintMatcher.matches();
        if (extensionHintLocation) {
            file = extensionHintMatcher.group(1) + extensionHintMatcher.group(2);
        }
        for (PropertySourceLoader propertySourceLoader : this.propertySourceLoaders) {
            String extension = this.getLoadableFileExtension(propertySourceLoader, file);
            if (extension == null) continue;
            String root = file.substring(0, file.length() - extension.length() - 1);
            StandardConfigDataReference reference = new StandardConfigDataReference(configDataLocation, null, root, profile, !extensionHintLocation ? extension : null, propertySourceLoader);
            return Collections.singleton(reference);
        }
        throw new IllegalStateException("File extension is not known to any PropertySourceLoader. If the location is meant to reference a directory, it must end in '/' or File.separator");
    }

    private String getLoadableFileExtension(PropertySourceLoader loader, String file) {
        for (String fileExtension : loader.getFileExtensions()) {
            if (!StringUtils.endsWithIgnoreCase((String)file, (String)fileExtension)) continue;
            return fileExtension;
        }
        return null;
    }

    private boolean isDirectory(String resourceLocation) {
        return resourceLocation.endsWith("/") || resourceLocation.endsWith(File.separator);
    }

    private List<StandardConfigDataResource> resolve(Set<StandardConfigDataReference> references) {
        ArrayList<StandardConfigDataResource> resolved = new ArrayList<StandardConfigDataResource>();
        for (StandardConfigDataReference reference : references) {
            resolved.addAll(this.resolve(reference));
        }
        if (resolved.isEmpty()) {
            resolved.addAll(this.resolveEmptyDirectories(references));
        }
        return resolved;
    }

    private Collection<StandardConfigDataResource> resolveEmptyDirectories(Set<StandardConfigDataReference> references) {
        LinkedHashSet<StandardConfigDataResource> empty = new LinkedHashSet<StandardConfigDataResource>();
        for (StandardConfigDataReference reference : references) {
            if (reference.getDirectory() == null) continue;
            empty.addAll(this.resolveEmptyDirectories(reference));
        }
        return empty;
    }

    private Set<StandardConfigDataResource> resolveEmptyDirectories(StandardConfigDataReference reference) {
        if (!this.resourceLoader.isPattern(reference.getResourceLocation())) {
            return this.resolveNonPatternEmptyDirectories(reference);
        }
        return this.resolvePatternEmptyDirectories(reference);
    }

    private Set<StandardConfigDataResource> resolveNonPatternEmptyDirectories(StandardConfigDataReference reference) {
        Resource resource = this.resourceLoader.getResource(reference.getDirectory());
        return resource instanceof ClassPathResource || !resource.exists() ? Collections.emptySet() : Collections.singleton(new StandardConfigDataResource(reference, resource, true));
    }

    private Set<StandardConfigDataResource> resolvePatternEmptyDirectories(StandardConfigDataReference reference) {
        Object[] subdirectories = this.resourceLoader.getResources(reference.getDirectory(), LocationResourceLoader.ResourceType.DIRECTORY);
        ConfigDataLocation location = reference.getConfigDataLocation();
        if (!location.isOptional() && ObjectUtils.isEmpty((Object[])subdirectories)) {
            String message = String.format("Config data location '%s' contains no subdirectories", location);
            throw new ConfigDataLocationNotFoundException(location, message, null);
        }
        return Arrays.stream(subdirectories).filter(Resource::exists).map(resource -> new StandardConfigDataResource(reference, (Resource)resource, true)).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private List<StandardConfigDataResource> resolve(StandardConfigDataReference reference) {
        if (!this.resourceLoader.isPattern(reference.getResourceLocation())) {
            return this.resolveNonPattern(reference);
        }
        return this.resolvePattern(reference);
    }

    private List<StandardConfigDataResource> resolveNonPattern(StandardConfigDataReference reference) {
        Resource resource = this.resourceLoader.getResource(reference.getResourceLocation());
        if (!resource.exists() && reference.isSkippable()) {
            this.logSkippingResource(reference);
            return Collections.emptyList();
        }
        return Collections.singletonList(this.createConfigResourceLocation(reference, resource));
    }

    private List<StandardConfigDataResource> resolvePattern(StandardConfigDataReference reference) {
        ArrayList<StandardConfigDataResource> resolved = new ArrayList<StandardConfigDataResource>();
        for (Resource resource : this.resourceLoader.getResources(reference.getResourceLocation(), LocationResourceLoader.ResourceType.FILE)) {
            if (!resource.exists() && reference.isSkippable()) {
                this.logSkippingResource(reference);
                continue;
            }
            resolved.add(this.createConfigResourceLocation(reference, resource));
        }
        return resolved;
    }

    private void logSkippingResource(StandardConfigDataReference reference) {
        this.logger.trace((Object)LogMessage.format((String)"Skipping missing resource %s", (Object)reference));
    }

    private StandardConfigDataResource createConfigResourceLocation(StandardConfigDataReference reference, Resource resource) {
        return new StandardConfigDataResource(reference, resource);
    }
}

