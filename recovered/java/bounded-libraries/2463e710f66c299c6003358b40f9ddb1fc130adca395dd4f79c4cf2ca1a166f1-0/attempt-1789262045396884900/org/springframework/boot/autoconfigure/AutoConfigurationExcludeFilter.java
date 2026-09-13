/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanClassLoaderAware
 *  org.springframework.boot.context.annotation.ImportCandidates
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.core.io.support.SpringFactoriesLoader
 *  org.springframework.core.type.classreading.MetadataReader
 *  org.springframework.core.type.classreading.MetadataReaderFactory
 *  org.springframework.core.type.filter.TypeFilter
 */
package org.springframework.boot.autoconfigure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.annotation.ImportCandidates;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

public class AutoConfigurationExcludeFilter
implements TypeFilter,
BeanClassLoaderAware {
    private ClassLoader beanClassLoader;
    private volatile List<String> autoConfigurations;

    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.beanClassLoader = beanClassLoader;
    }

    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        return this.isConfiguration(metadataReader) && this.isAutoConfiguration(metadataReader);
    }

    private boolean isConfiguration(MetadataReader metadataReader) {
        return metadataReader.getAnnotationMetadata().isAnnotated(Configuration.class.getName());
    }

    private boolean isAutoConfiguration(MetadataReader metadataReader) {
        boolean annotatedWithAutoConfiguration = metadataReader.getAnnotationMetadata().isAnnotated(AutoConfiguration.class.getName());
        return annotatedWithAutoConfiguration || this.getAutoConfigurations().contains(metadataReader.getClassMetadata().getClassName());
    }

    protected List<String> getAutoConfigurations() {
        if (this.autoConfigurations == null) {
            ArrayList<String> autoConfigurations = new ArrayList<String>(SpringFactoriesLoader.loadFactoryNames(EnableAutoConfiguration.class, (ClassLoader)this.beanClassLoader));
            ImportCandidates.load(AutoConfiguration.class, (ClassLoader)this.beanClassLoader).forEach(autoConfigurations::add);
            this.autoConfigurations = autoConfigurations;
        }
        return this.autoConfigurations;
    }
}

