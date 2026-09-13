/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.env.EnumerablePropertySource
 *  org.springframework.core.io.InputStreamSource
 *  org.springframework.core.io.PathResource
 *  org.springframework.core.io.Resource
 *  org.springframework.util.Assert
 *  org.springframework.util.FileCopyUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.env;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.springframework.boot.origin.Origin;
import org.springframework.boot.origin.OriginLookup;
import org.springframework.boot.origin.OriginProvider;
import org.springframework.boot.origin.TextResourceOrigin;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

public class ConfigTreePropertySource
extends EnumerablePropertySource<Path>
implements OriginLookup<String> {
    private static final int MAX_DEPTH = 100;
    private final Map<String, PropertyFile> propertyFiles;
    private final String[] names;
    private final Set<Option> options;

    public ConfigTreePropertySource(String name, Path sourceDirectory) {
        this(name, sourceDirectory, EnumSet.noneOf(Option.class));
    }

    public ConfigTreePropertySource(String name, Path sourceDirectory, Option ... options) {
        this(name, sourceDirectory, EnumSet.copyOf(Arrays.asList(options)));
    }

    private ConfigTreePropertySource(String name, Path sourceDirectory, Set<Option> options) {
        super(name, (Object)sourceDirectory);
        Assert.isTrue((boolean)Files.exists(sourceDirectory, new LinkOption[0]), () -> "Directory '" + sourceDirectory + "' does not exist");
        Assert.isTrue((boolean)Files.isDirectory(sourceDirectory, new LinkOption[0]), () -> "File '" + sourceDirectory + "' is not a directory");
        this.propertyFiles = PropertyFile.findAll(sourceDirectory, options);
        this.options = options;
        this.names = StringUtils.toStringArray(this.propertyFiles.keySet());
    }

    public String[] getPropertyNames() {
        return (String[])this.names.clone();
    }

    public Value getProperty(String name) {
        PropertyFile propertyFile = this.propertyFiles.get(name);
        return propertyFile != null ? propertyFile.getContent() : null;
    }

    @Override
    public Origin getOrigin(String name) {
        PropertyFile propertyFile = this.propertyFiles.get(name);
        return propertyFile != null ? propertyFile.getOrigin() : null;
    }

    @Override
    public boolean isImmutable() {
        return !this.options.contains((Object)Option.ALWAYS_READ);
    }

    private static final class PropertyFileContent
    implements Value,
    OriginProvider {
        private final Path path;
        private final Resource resource;
        private final Origin origin;
        private final boolean cacheContent;
        private final boolean autoTrimTrailingNewLine;
        private volatile byte[] content;

        private PropertyFileContent(Path path, Resource resource, Origin origin, boolean cacheContent, boolean autoTrimTrailingNewLine) {
            this.path = path;
            this.resource = resource;
            this.origin = origin;
            this.cacheContent = cacheContent;
            this.autoTrimTrailingNewLine = autoTrimTrailingNewLine;
        }

        @Override
        public Origin getOrigin() {
            return this.origin;
        }

        @Override
        public int length() {
            return this.toString().length();
        }

        @Override
        public char charAt(int index) {
            return this.toString().charAt(index);
        }

        @Override
        public CharSequence subSequence(int start, int end) {
            return this.toString().subSequence(start, end);
        }

        @Override
        public String toString() {
            String string = new String(this.getBytes());
            if (this.autoTrimTrailingNewLine) {
                string = this.autoTrimTrailingNewLine(string);
            }
            return string;
        }

        private String autoTrimTrailingNewLine(String string) {
            if (!string.endsWith("\n")) {
                return string;
            }
            int numberOfLines = 0;
            for (int i = 0; i < string.length(); ++i) {
                char ch = string.charAt(i);
                if (ch != '\n') continue;
                ++numberOfLines;
            }
            if (numberOfLines > 1) {
                return string;
            }
            return string.endsWith("\r\n") ? string.substring(0, string.length() - 2) : string.substring(0, string.length() - 1);
        }

        public InputStream getInputStream() throws IOException {
            if (!this.cacheContent) {
                this.assertStillExists();
                return this.resource.getInputStream();
            }
            return new ByteArrayInputStream(this.getBytes());
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private byte[] getBytes() {
            try {
                if (!this.cacheContent) {
                    this.assertStillExists();
                    return FileCopyUtils.copyToByteArray((InputStream)this.resource.getInputStream());
                }
                if (this.content == null) {
                    this.assertStillExists();
                    Resource resource = this.resource;
                    synchronized (resource) {
                        if (this.content == null) {
                            this.content = FileCopyUtils.copyToByteArray((InputStream)this.resource.getInputStream());
                        }
                    }
                }
                return this.content;
            }
            catch (IOException ex) {
                throw new IllegalStateException(ex);
            }
        }

        private void assertStillExists() {
            Assert.state((boolean)Files.exists(this.path, new LinkOption[0]), () -> "The property file '" + this.path + "' no longer exists");
        }
    }

    private static final class PropertyFile {
        private static final TextResourceOrigin.Location START_OF_FILE = new TextResourceOrigin.Location(0, 0);
        private final Path path;
        private final PathResource resource;
        private final Origin origin;
        private final PropertyFileContent cachedContent;
        private final boolean autoTrimTrailingNewLine;

        private PropertyFile(Path path, Set<Option> options) {
            this.path = path;
            this.resource = new PathResource(path);
            this.origin = new TextResourceOrigin((Resource)this.resource, START_OF_FILE);
            this.autoTrimTrailingNewLine = options.contains((Object)Option.AUTO_TRIM_TRAILING_NEW_LINE);
            this.cachedContent = options.contains((Object)Option.ALWAYS_READ) ? null : new PropertyFileContent(path, (Resource)this.resource, this.origin, true, this.autoTrimTrailingNewLine);
        }

        PropertyFileContent getContent() {
            if (this.cachedContent != null) {
                return this.cachedContent;
            }
            return new PropertyFileContent(this.path, (Resource)this.resource, this.origin, false, this.autoTrimTrailingNewLine);
        }

        Origin getOrigin() {
            return this.origin;
        }

        static Map<String, PropertyFile> findAll(Path sourceDirectory, Set<Option> options) {
            try {
                TreeMap propertyFiles = new TreeMap();
                Files.find(sourceDirectory, 100, PropertyFile::isPropertyFile, FileVisitOption.FOLLOW_LINKS).forEach(path -> {
                    String name = PropertyFile.getName(sourceDirectory.relativize((Path)path));
                    if (StringUtils.hasText((String)name)) {
                        if (options.contains((Object)Option.USE_LOWERCASE_NAMES)) {
                            name = name.toLowerCase();
                        }
                        propertyFiles.put(name, new PropertyFile((Path)path, options));
                    }
                });
                return Collections.unmodifiableMap(propertyFiles);
            }
            catch (IOException ex) {
                throw new IllegalStateException("Unable to find files in '" + sourceDirectory + "'", ex);
            }
        }

        private static boolean isPropertyFile(Path path, BasicFileAttributes attributes) {
            return !PropertyFile.hasHiddenPathElement(path) && (attributes.isRegularFile() || attributes.isSymbolicLink());
        }

        private static boolean hasHiddenPathElement(Path path) {
            Iterator<Path> iterator = path.iterator();
            while (iterator.hasNext()) {
                if (!iterator.next().toString().startsWith("..")) continue;
                return true;
            }
            return false;
        }

        private static String getName(Path relativePath) {
            int nameCount = relativePath.getNameCount();
            if (nameCount == 1) {
                return relativePath.toString();
            }
            StringBuilder name = new StringBuilder();
            for (int i = 0; i < nameCount; ++i) {
                name.append(i != 0 ? "." : "");
                name.append(relativePath.getName(i));
            }
            return name.toString();
        }
    }

    public static interface Value
    extends CharSequence,
    InputStreamSource {
    }

    public static enum Option {
        ALWAYS_READ,
        USE_LOWERCASE_NAMES,
        AUTO_TRIM_TRAILING_NEW_LINE;

    }
}

