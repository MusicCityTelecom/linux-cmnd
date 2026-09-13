/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.io.FileSystemResource
 *  org.springframework.core.io.Resource
 *  org.springframework.core.io.ResourceLoader
 *  org.springframework.util.Assert
 *  org.springframework.util.ResourceUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.context.config;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

class LocationResourceLoader {
    private static final Resource[] EMPTY_RESOURCES = new Resource[0];
    private static final Comparator<File> FILE_PATH_COMPARATOR = Comparator.comparing(File::getAbsolutePath);
    private static final Comparator<File> FILE_NAME_COMPARATOR = Comparator.comparing(File::getName);
    private final ResourceLoader resourceLoader;

    LocationResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    boolean isPattern(String location) {
        return StringUtils.hasLength((String)location) && location.contains("*");
    }

    Resource getResource(String location) {
        this.validateNonPattern(location);
        location = StringUtils.cleanPath((String)location);
        if (!ResourceUtils.isUrl((String)location)) {
            location = "file:" + location;
        }
        return this.resourceLoader.getResource(location);
    }

    private void validateNonPattern(String location) {
        Assert.state((!this.isPattern(location) ? 1 : 0) != 0, () -> String.format("Location '%s' must not be a pattern", location));
    }

    Resource[] getResources(String location, ResourceType type) {
        this.validatePattern(location, type);
        String directoryPath = location.substring(0, location.indexOf("*/"));
        String fileName = location.substring(location.lastIndexOf("/") + 1);
        Resource resource = this.getResource(directoryPath);
        if (!resource.exists()) {
            return EMPTY_RESOURCES;
        }
        File file = this.getFile(location, resource);
        if (!file.isDirectory()) {
            return EMPTY_RESOURCES;
        }
        File[] subDirectories = file.listFiles(this::isVisibleDirectory);
        if (subDirectories == null) {
            return EMPTY_RESOURCES;
        }
        Arrays.sort(subDirectories, FILE_PATH_COMPARATOR);
        if (type == ResourceType.DIRECTORY) {
            return (Resource[])Arrays.stream(subDirectories).map(FileSystemResource::new).toArray(Resource[]::new);
        }
        ArrayList resources = new ArrayList();
        FilenameFilter filter = (dir, name) -> name.equals(fileName);
        for (File subDirectory : subDirectories) {
            File[] files = subDirectory.listFiles(filter);
            if (files == null) continue;
            Arrays.sort(files, FILE_NAME_COMPARATOR);
            Arrays.stream(files).map(FileSystemResource::new).forEach(resources::add);
        }
        return resources.toArray(EMPTY_RESOURCES);
    }

    private void validatePattern(String location, ResourceType type) {
        Assert.state((boolean)this.isPattern(location), () -> String.format("Location '%s' must be a pattern", location));
        Assert.state((!location.startsWith("classpath*:") ? 1 : 0) != 0, () -> String.format("Location '%s' cannot use classpath wildcards", location));
        Assert.state((StringUtils.countOccurrencesOf((String)location, (String)"*") == 1 ? 1 : 0) != 0, () -> String.format("Location '%s' cannot contain multiple wildcards", location));
        String directoryPath = type != ResourceType.DIRECTORY ? location.substring(0, location.lastIndexOf("/") + 1) : location;
        Assert.state((boolean)directoryPath.endsWith("*/"), () -> String.format("Location '%s' must end with '*/'", location));
    }

    private File getFile(String patternLocation, Resource resource) {
        try {
            return resource.getFile();
        }
        catch (Exception ex) {
            throw new IllegalStateException("Unable to load config data resource from pattern '" + patternLocation + "'", ex);
        }
    }

    private boolean isVisibleDirectory(File file) {
        return file.isDirectory() && !file.getName().startsWith("..");
    }

    static enum ResourceType {
        FILE,
        DIRECTORY;

    }
}

