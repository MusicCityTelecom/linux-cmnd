/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.io.FileUtils
 *  org.apache.commons.io.IOUtils
 *  org.apache.commons.io.input.ReaderInputStream
 *  org.apache.commons.lang3.ArrayUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.jooq.lambda.fi.util.function.CheckedConsumer
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.core.io.AbstractResource
 *  org.springframework.core.io.ByteArrayResource
 *  org.springframework.core.io.ClassPathResource
 *  org.springframework.core.io.FileSystemResource
 *  org.springframework.core.io.InputStreamResource
 *  org.springframework.core.io.Resource
 *  org.springframework.core.io.ResourceLoader
 *  org.springframework.core.io.UrlResource
 *  org.springframework.util.ResourceUtils
 */
package org.apereo.cas.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.util.Enumeration;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import lombok.Generated;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.ReaderInputStream;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.util.LoggingUtils;
import org.apereo.cas.util.RegexUtils;
import org.apereo.cas.util.function.FunctionUtils;
import org.jooq.lambda.fi.util.function.CheckedConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.AbstractResource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;

public final class ResourceUtils {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceUtils.class);
    public static final Resource EMPTY_RESOURCE = new ByteArrayResource(ArrayUtils.EMPTY_BYTE_ARRAY);
    private static final String HTTP_URL_PREFIX = "http";

    public static AbstractResource getRawResourceFrom(String location) throws IOException {
        if (StringUtils.isBlank((CharSequence)location)) {
            throw new IllegalArgumentException("Provided location does not exist and is empty");
        }
        if (location.toLowerCase().startsWith(HTTP_URL_PREFIX)) {
            return new UrlResource(location);
        }
        if (location.toLowerCase().startsWith("classpath:")) {
            return new ClassPathResource(location.substring("classpath:".length()));
        }
        return new FileSystemResource(StringUtils.remove((String)location, (String)"file:"));
    }

    public static boolean doesResourceExist(String resource, ResourceLoader resourceLoader) {
        try {
            if (StringUtils.isNotBlank((CharSequence)resource)) {
                Resource res = resourceLoader.getResource(resource);
                return ResourceUtils.doesResourceExist(res);
            }
        }
        catch (Exception e) {
            LoggingUtils.warn(LOGGER, e);
        }
        return false;
    }

    public static boolean doesResourceExist(Resource res) {
        boolean bl;
        block11: {
            if (res == null) {
                return false;
            }
            if (res.isFile() && FileUtils.isDirectory((File)res.getFile(), (LinkOption[])new LinkOption[0])) {
                return true;
            }
            InputStream input = res.getInputStream();
            try {
                IOUtils.read((InputStream)input, (byte[])new byte[1]);
                boolean bl2 = bl = res.contentLength() > 0L;
                if (input == null) break block11;
            }
            catch (Throwable throwable) {
                try {
                    if (input != null) {
                        try {
                            input.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                catch (FileNotFoundException e) {
                    LOGGER.trace(e.getMessage());
                    return false;
                }
                catch (Exception e) {
                    LOGGER.trace(e.getMessage(), (Throwable)e);
                    return false;
                }
            }
            input.close();
        }
        return bl;
    }

    public static boolean doesResourceExist(String location) {
        try {
            AbstractResource resource = ResourceUtils.getResourceFrom(location);
            return ResourceUtils.doesResourceExist((Resource)resource);
        }
        catch (Exception e) {
            LOGGER.trace(e.getMessage());
            return false;
        }
    }

    public static AbstractResource getResourceFrom(String location) throws IOException {
        AbstractResource resource = ResourceUtils.getRawResourceFrom(location);
        if (!resource.exists() || resource.isFile() && resource.getFile().isFile() && !resource.isReadable()) {
            throw new FileNotFoundException("Resource " + location + " does not exist or is unreadable");
        }
        return resource;
    }

    public static Resource exportClasspathResourceToFile(File parentDirectory, Resource resource) {
        LOGGER.trace("Preparing classpath resource [{}]", (Object)resource);
        if (resource == null) {
            LOGGER.warn("No resource defined to prepare. Returning null");
            return null;
        }
        if (!parentDirectory.exists() && !parentDirectory.mkdirs()) {
            LOGGER.warn("Unable to create folder [{}]", (Object)parentDirectory);
        }
        File destination = new File(parentDirectory, Objects.requireNonNull(resource.getFilename()));
        FunctionUtils.doUnchecked((CheckedConsumer<Object>)((CheckedConsumer)unused -> {
            if (destination.exists()) {
                LOGGER.trace("Deleting resource directory [{}]", (Object)destination);
                FileUtils.forceDelete((File)destination);
            }
            try (FileOutputStream out = new FileOutputStream(destination);
                 InputStream input = resource.getInputStream();){
                input.transferTo(out);
            }
        }), new Object[0]);
        return new FileSystemResource(destination);
    }

    public static Resource prepareClasspathResourceIfNeeded(Resource resource) {
        if (resource == null) {
            LOGGER.debug("No resource defined to prepare. Returning null");
            return null;
        }
        return ResourceUtils.prepareClasspathResourceIfNeeded(resource, false, resource.getFilename());
    }

    public static Resource prepareClasspathResourceIfNeeded(Resource resource, boolean isDirectory, String containsName) {
        LOGGER.trace("Preparing possible classpath resource [{}]", (Object)resource);
        if (resource == null) {
            LOGGER.debug("No resource defined to prepare. Returning null");
            return null;
        }
        if (org.springframework.util.ResourceUtils.isFileURL((URL)resource.getURL())) {
            return resource;
        }
        URL url = org.springframework.util.ResourceUtils.extractArchiveURL((URL)resource.getURL());
        File file = org.springframework.util.ResourceUtils.getFile((URL)url);
        File destination = new File(FileUtils.getTempDirectory(), Objects.requireNonNull(resource.getFilename()));
        if (isDirectory) {
            LOGGER.trace("Creating resource directory [{}]", (Object)destination);
            FileUtils.forceMkdir((File)destination);
            FileUtils.cleanDirectory((File)destination);
        } else if (destination.exists()) {
            LOGGER.trace("Deleting resource directory [{}]", (Object)destination);
            FileUtils.forceDelete((File)destination);
        }
        LOGGER.trace("Processing file [{}]", (Object)file);
        try (JarFile jFile = new JarFile(file);){
            Enumeration<JarEntry> e = jFile.entries();
            while (e.hasMoreElements()) {
                JarEntry entry = e.nextElement();
                String name = entry.getName();
                LOGGER.trace("Comparing [{}] against [{}] and pattern [{}]", new Object[]{name, resource.getFilename(), containsName});
                if (!name.contains(resource.getFilename()) || !RegexUtils.find(containsName, name)) continue;
                InputStream stream = jFile.getInputStream(entry);
                try {
                    File copyDestination = destination;
                    if (isDirectory) {
                        File entryFileName = new File(name);
                        copyDestination = new File(destination, entryFileName.getName());
                    }
                    LOGGER.trace("Copying resource entry [{}] to [{}]", (Object)name, (Object)copyDestination);
                    BufferedWriter writer = Files.newBufferedWriter(copyDestination.toPath(), StandardCharsets.UTF_8, new OpenOption[0]);
                    try {
                        IOUtils.copy((InputStream)stream, (Writer)writer, (Charset)StandardCharsets.UTF_8);
                    }
                    finally {
                        if (writer == null) continue;
                        writer.close();
                    }
                }
                finally {
                    if (stream == null) continue;
                    stream.close();
                }
            }
        }
        return new FileSystemResource(destination);
    }

    public static InputStreamResource buildInputStreamResourceFrom(String value, String description) {
        StringReader reader = new StringReader(value);
        ReaderInputStream is = new ReaderInputStream((Reader)reader, StandardCharsets.UTF_8);
        return new InputStreamResource((InputStream)is, description);
    }

    public static boolean isFile(String resource) {
        return StringUtils.isNotBlank((CharSequence)resource) && resource.startsWith("file:");
    }

    public static boolean isFile(Resource resource) {
        try {
            resource.getFile();
            return true;
        }
        catch (Exception e) {
            LOGGER.trace(e.getMessage());
            return false;
        }
    }

    public static boolean isJarResource(Resource resource) {
        try {
            return "jar".equals(resource.getURI().getScheme());
        }
        catch (IOException e) {
            LOGGER.trace(e.getMessage(), (Throwable)e);
            return false;
        }
    }

    public static boolean isUrl(String resource) {
        return StringUtils.isNotBlank((CharSequence)resource) && resource.startsWith(HTTP_URL_PREFIX);
    }

    @Generated
    private ResourceUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

