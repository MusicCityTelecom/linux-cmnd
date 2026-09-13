/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.context.properties.bind.Binder
 *  org.springframework.core.env.Environment
 *  org.springframework.core.io.ResourceLoader
 *  org.springframework.util.ClassUtils
 */
package org.springframework.boot.autoconfigure.template;

import java.util.List;
import org.springframework.boot.autoconfigure.template.TemplateAvailabilityProvider;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ClassUtils;

public abstract class PathBasedTemplateAvailabilityProvider
implements TemplateAvailabilityProvider {
    private final String className;
    private final Class<TemplateAvailabilityProperties> propertiesClass;
    private final String propertyPrefix;

    public PathBasedTemplateAvailabilityProvider(String className, Class<? extends TemplateAvailabilityProperties> propertiesClass, String propertyPrefix) {
        this.className = className;
        this.propertiesClass = propertiesClass;
        this.propertyPrefix = propertyPrefix;
    }

    @Override
    public boolean isTemplateAvailable(String view, Environment environment, ClassLoader classLoader, ResourceLoader resourceLoader) {
        if (ClassUtils.isPresent((String)this.className, (ClassLoader)classLoader)) {
            Binder binder = Binder.get((Environment)environment);
            TemplateAvailabilityProperties properties = (TemplateAvailabilityProperties)binder.bindOrCreate(this.propertyPrefix, this.propertiesClass);
            return this.isTemplateAvailable(view, resourceLoader, properties);
        }
        return false;
    }

    private boolean isTemplateAvailable(String view, ResourceLoader resourceLoader, TemplateAvailabilityProperties properties) {
        String location = properties.getPrefix() + view + properties.getSuffix();
        for (String path : properties.getLoaderPath()) {
            if (!resourceLoader.getResource(path + location).exists()) continue;
            return true;
        }
        return false;
    }

    protected static abstract class TemplateAvailabilityProperties {
        private String prefix;
        private String suffix;

        protected TemplateAvailabilityProperties(String prefix, String suffix) {
            this.prefix = prefix;
            this.suffix = suffix;
        }

        protected abstract List<String> getLoaderPath();

        public String getPrefix() {
            return this.prefix;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }

        public String getSuffix() {
            return this.suffix;
        }

        public void setSuffix(String suffix) {
            this.suffix = suffix;
        }
    }
}

