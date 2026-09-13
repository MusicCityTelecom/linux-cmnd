/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.env.PropertyResolver
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.logging;

import java.io.File;
import java.util.Properties;
import org.springframework.core.env.PropertyResolver;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class LogFile {
    public static final String FILE_NAME_PROPERTY = "logging.file.name";
    public static final String FILE_PATH_PROPERTY = "logging.file.path";
    private final String file;
    private final String path;

    LogFile(String file) {
        this(file, null);
    }

    LogFile(String file, String path) {
        Assert.isTrue((StringUtils.hasLength((String)file) || StringUtils.hasLength((String)path) ? 1 : 0) != 0, (String)"File or Path must not be empty");
        this.file = file;
        this.path = path;
    }

    public void applyToSystemProperties() {
        this.applyTo(System.getProperties());
    }

    public void applyTo(Properties properties) {
        this.put(properties, "LOG_PATH", this.path);
        this.put(properties, "LOG_FILE", this.toString());
    }

    private void put(Properties properties, String key, String value) {
        if (StringUtils.hasLength((String)value)) {
            properties.put(key, value);
        }
    }

    public String toString() {
        if (StringUtils.hasLength((String)this.file)) {
            return this.file;
        }
        return new File(this.path, "spring.log").getPath();
    }

    public static LogFile get(PropertyResolver propertyResolver) {
        String file = propertyResolver.getProperty(FILE_NAME_PROPERTY);
        String path = propertyResolver.getProperty(FILE_PATH_PROPERTY);
        if (StringUtils.hasLength((String)file) || StringUtils.hasLength((String)path)) {
            return new LogFile(file, path);
        }
        return null;
    }
}

