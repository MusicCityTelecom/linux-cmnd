/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.io.FileUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.jooq.lambda.fi.util.function.CheckedConsumer
 *  org.springframework.boot.SpringBootVersion
 *  org.springframework.boot.info.GitProperties
 *  org.springframework.core.SpringVersion
 *  org.springframework.core.io.ClassPathResource
 *  org.springframework.core.io.Resource
 *  org.springframework.core.io.support.PropertiesLoaderUtils
 */
package org.apereo.cas.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import lombok.Generated;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.util.CasVersion;
import org.apereo.cas.util.ResourceUtils;
import org.apereo.cas.util.function.FunctionUtils;
import org.jooq.lambda.fi.util.function.CheckedConsumer;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.info.GitProperties;
import org.springframework.core.SpringVersion;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public final class SystemUtils {
    private static final int SYSTEM_INFO_DEFAULT_SIZE = 20;
    private static final GitProperties GIT_PROPERTIES = new GitProperties(SystemUtils.loadGitProperties());

    private static Properties loadGitProperties() {
        Properties properties = new Properties();
        FunctionUtils.doUnchecked((CheckedConsumer<Object>)((CheckedConsumer)unused -> {
            ClassPathResource resource = new ClassPathResource("git.properties");
            if (ResourceUtils.doesResourceExist((Resource)resource)) {
                Properties loaded = PropertiesLoaderUtils.loadProperties((Resource)resource);
                for (String key : loaded.stringPropertyNames()) {
                    if (!key.startsWith("git.")) continue;
                    properties.put(key.substring("git.".length()), loaded.get(key));
                }
            }
        }), new Object[0]);
        return properties;
    }

    public static Map<String, Object> getSystemInfo() {
        Properties properties = System.getProperties();
        LinkedHashMap<String, Object> info = new LinkedHashMap<String, Object>(20);
        info.put("CAS Version", StringUtils.defaultString((String)CasVersion.getVersion(), (String)"Not Available"));
        info.put("CAS Branch", StringUtils.defaultString((String)GIT_PROPERTIES.getBranch(), (String)"master"));
        info.put("CAS Commit Id", StringUtils.defaultString((String)GIT_PROPERTIES.getCommitId(), (String)"Not Available"));
        info.put("CAS Build Date/Time", CasVersion.getDateTime());
        info.put("Spring Boot Version", SpringBootVersion.getVersion());
        info.put("Spring Version", SpringVersion.getVersion());
        info.put("Java Home", properties.get("java.home"));
        info.put("Java Vendor", properties.get("java.vendor"));
        info.put("Java Version", properties.get("java.version"));
        Runtime runtime = Runtime.getRuntime();
        info.put("JVM Free Memory", FileUtils.byteCountToDisplaySize((long)runtime.freeMemory()));
        info.put("JVM Maximum Memory", FileUtils.byteCountToDisplaySize((long)runtime.maxMemory()));
        info.put("JVM Total Memory", FileUtils.byteCountToDisplaySize((long)runtime.totalMemory()));
        info.put("OS Architecture", properties.get("os.arch"));
        info.put("OS Name", properties.get("os.name"));
        info.put("OS Version", properties.get("os.version"));
        info.put("OS Date/Time", LocalDateTime.now(ZoneId.systemDefault()));
        info.put("OS Temp Directory", FileUtils.getTempDirectoryPath());
        return info;
    }

    @Generated
    private SystemUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

