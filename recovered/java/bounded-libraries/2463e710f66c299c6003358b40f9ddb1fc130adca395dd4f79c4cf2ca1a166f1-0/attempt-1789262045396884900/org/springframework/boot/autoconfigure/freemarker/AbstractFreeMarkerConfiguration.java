/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.ui.freemarker.FreeMarkerConfigurationFactory
 */
package org.springframework.boot.autoconfigure.freemarker;

import java.util.Properties;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerProperties;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;

abstract class AbstractFreeMarkerConfiguration {
    private final FreeMarkerProperties properties;

    protected AbstractFreeMarkerConfiguration(FreeMarkerProperties properties) {
        this.properties = properties;
    }

    protected final FreeMarkerProperties getProperties() {
        return this.properties;
    }

    protected void applyProperties(FreeMarkerConfigurationFactory factory) {
        factory.setTemplateLoaderPaths(this.properties.getTemplateLoaderPath());
        factory.setPreferFileSystemAccess(this.properties.isPreferFileSystemAccess());
        factory.setDefaultEncoding(this.properties.getCharsetName());
        Properties settings = new Properties();
        settings.put("recognize_standard_file_extensions", "true");
        settings.putAll(this.properties.getSettings());
        factory.setFreemarkerSettings(settings);
    }
}

