/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.context.annotation.Configurations
 *  org.springframework.core.Ordered
 *  org.springframework.core.type.classreading.MetadataReaderFactory
 *  org.springframework.core.type.classreading.SimpleMetadataReaderFactory
 *  org.springframework.util.ClassUtils
 */
package org.springframework.boot.autoconfigure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.boot.autoconfigure.AutoConfigurationImportSelector;
import org.springframework.boot.autoconfigure.AutoConfigurationSorter;
import org.springframework.boot.context.annotation.Configurations;
import org.springframework.core.Ordered;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;
import org.springframework.util.ClassUtils;

public class AutoConfigurations
extends Configurations
implements Ordered {
    private static final AutoConfigurationSorter SORTER = new AutoConfigurationSorter((MetadataReaderFactory)new SimpleMetadataReaderFactory(), null);
    private static final Ordered ORDER = new AutoConfigurationImportSelector();

    protected AutoConfigurations(Collection<Class<?>> classes) {
        super(classes);
    }

    protected Collection<Class<?>> sort(Collection<Class<?>> classes) {
        List<String> names = classes.stream().map(Class::getName).collect(Collectors.toList());
        List<String> sorted = SORTER.getInPriorityOrder(names);
        return sorted.stream().map(className -> ClassUtils.resolveClassName((String)className, null)).collect(Collectors.toCollection(ArrayList::new));
    }

    public int getOrder() {
        return ORDER.getOrder();
    }

    protected AutoConfigurations merge(Set<Class<?>> mergedClasses) {
        return new AutoConfigurations(mergedClasses);
    }

    public static AutoConfigurations of(Class<?> ... classes) {
        return new AutoConfigurations(Arrays.asList(classes));
    }
}

