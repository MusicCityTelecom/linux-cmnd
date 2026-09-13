/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.type.AnnotationMetadata
 *  org.springframework.core.type.classreading.MetadataReader
 *  org.springframework.core.type.classreading.MetadataReaderFactory
 *  org.springframework.util.Assert
 */
package org.springframework.boot.autoconfigure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.boot.autoconfigure.AutoConfigurationMetadata;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.Assert;

class AutoConfigurationSorter {
    private final MetadataReaderFactory metadataReaderFactory;
    private final AutoConfigurationMetadata autoConfigurationMetadata;

    AutoConfigurationSorter(MetadataReaderFactory metadataReaderFactory, AutoConfigurationMetadata autoConfigurationMetadata) {
        Assert.notNull((Object)metadataReaderFactory, (String)"MetadataReaderFactory must not be null");
        this.metadataReaderFactory = metadataReaderFactory;
        this.autoConfigurationMetadata = autoConfigurationMetadata;
    }

    List<String> getInPriorityOrder(Collection<String> classNames) {
        AutoConfigurationClasses classes = new AutoConfigurationClasses(this.metadataReaderFactory, this.autoConfigurationMetadata, classNames);
        List<String> orderedClassNames = new ArrayList<String>(classNames);
        Collections.sort(orderedClassNames);
        orderedClassNames.sort((o1, o2) -> {
            int i1 = classes.get((String)o1).getOrder();
            int i2 = classes.get((String)o2).getOrder();
            return Integer.compare(i1, i2);
        });
        orderedClassNames = this.sortByAnnotation(classes, orderedClassNames);
        return orderedClassNames;
    }

    private List<String> sortByAnnotation(AutoConfigurationClasses classes, List<String> classNames) {
        ArrayList<String> toSort = new ArrayList<String>(classNames);
        toSort.addAll(classes.getAllNames());
        LinkedHashSet<String> sorted = new LinkedHashSet<String>();
        LinkedHashSet<String> processing = new LinkedHashSet<String>();
        while (!toSort.isEmpty()) {
            this.doSortByAfterAnnotation(classes, toSort, sorted, processing, null);
        }
        sorted.retainAll(classNames);
        return new ArrayList<String>(sorted);
    }

    private void doSortByAfterAnnotation(AutoConfigurationClasses classes, List<String> toSort, Set<String> sorted, Set<String> processing, String current) {
        if (current == null) {
            current = toSort.remove(0);
        }
        processing.add(current);
        for (String after : classes.getClassesRequestedAfter(current)) {
            this.checkForCycles(processing, current, after);
            if (sorted.contains(after) || !toSort.contains(after)) continue;
            this.doSortByAfterAnnotation(classes, toSort, sorted, processing, after);
        }
        processing.remove(current);
        sorted.add(current);
    }

    private void checkForCycles(Set<String> processing, String current, String after) {
        Assert.state((!processing.contains(after) ? 1 : 0) != 0, () -> "AutoConfigure cycle detected between " + current + " and " + after);
    }

    private static class AutoConfigurationClass {
        private final String className;
        private final MetadataReaderFactory metadataReaderFactory;
        private final AutoConfigurationMetadata autoConfigurationMetadata;
        private volatile AnnotationMetadata annotationMetadata;
        private volatile Set<String> before;
        private volatile Set<String> after;

        AutoConfigurationClass(String className, MetadataReaderFactory metadataReaderFactory, AutoConfigurationMetadata autoConfigurationMetadata) {
            this.className = className;
            this.metadataReaderFactory = metadataReaderFactory;
            this.autoConfigurationMetadata = autoConfigurationMetadata;
        }

        boolean isAvailable() {
            try {
                if (!this.wasProcessed()) {
                    this.getAnnotationMetadata();
                }
                return true;
            }
            catch (Exception ex) {
                return false;
            }
        }

        Set<String> getBefore() {
            if (this.before == null) {
                this.before = this.wasProcessed() ? this.autoConfigurationMetadata.getSet(this.className, "AutoConfigureBefore", Collections.emptySet()) : this.getAnnotationValue(AutoConfigureBefore.class);
            }
            return this.before;
        }

        Set<String> getAfter() {
            if (this.after == null) {
                this.after = this.wasProcessed() ? this.autoConfigurationMetadata.getSet(this.className, "AutoConfigureAfter", Collections.emptySet()) : this.getAnnotationValue(AutoConfigureAfter.class);
            }
            return this.after;
        }

        private int getOrder() {
            if (this.wasProcessed()) {
                return this.autoConfigurationMetadata.getInteger(this.className, "AutoConfigureOrder", 0);
            }
            Map attributes = this.getAnnotationMetadata().getAnnotationAttributes(AutoConfigureOrder.class.getName());
            return attributes != null ? (Integer)attributes.get("value") : 0;
        }

        private boolean wasProcessed() {
            return this.autoConfigurationMetadata != null && this.autoConfigurationMetadata.wasProcessed(this.className);
        }

        private Set<String> getAnnotationValue(Class<?> annotation) {
            Map attributes = this.getAnnotationMetadata().getAnnotationAttributes(annotation.getName(), true);
            if (attributes == null) {
                return Collections.emptySet();
            }
            LinkedHashSet<String> value = new LinkedHashSet<String>();
            Collections.addAll(value, (String[])attributes.get("value"));
            Collections.addAll(value, (String[])attributes.get("name"));
            return value;
        }

        private AnnotationMetadata getAnnotationMetadata() {
            if (this.annotationMetadata == null) {
                try {
                    MetadataReader metadataReader = this.metadataReaderFactory.getMetadataReader(this.className);
                    this.annotationMetadata = metadataReader.getAnnotationMetadata();
                }
                catch (IOException ex) {
                    throw new IllegalStateException("Unable to read meta-data for class " + this.className, ex);
                }
            }
            return this.annotationMetadata;
        }
    }

    private static class AutoConfigurationClasses {
        private final Map<String, AutoConfigurationClass> classes = new HashMap<String, AutoConfigurationClass>();

        AutoConfigurationClasses(MetadataReaderFactory metadataReaderFactory, AutoConfigurationMetadata autoConfigurationMetadata, Collection<String> classNames) {
            this.addToClasses(metadataReaderFactory, autoConfigurationMetadata, classNames, true);
        }

        Set<String> getAllNames() {
            return this.classes.keySet();
        }

        private void addToClasses(MetadataReaderFactory metadataReaderFactory, AutoConfigurationMetadata autoConfigurationMetadata, Collection<String> classNames, boolean required) {
            for (String className : classNames) {
                if (this.classes.containsKey(className)) continue;
                AutoConfigurationClass autoConfigurationClass = new AutoConfigurationClass(className, metadataReaderFactory, autoConfigurationMetadata);
                boolean available = autoConfigurationClass.isAvailable();
                if (required || available) {
                    this.classes.put(className, autoConfigurationClass);
                }
                if (!available) continue;
                this.addToClasses(metadataReaderFactory, autoConfigurationMetadata, autoConfigurationClass.getBefore(), false);
                this.addToClasses(metadataReaderFactory, autoConfigurationMetadata, autoConfigurationClass.getAfter(), false);
            }
        }

        AutoConfigurationClass get(String className) {
            return this.classes.get(className);
        }

        Set<String> getClassesRequestedAfter(String className) {
            LinkedHashSet<String> classesRequestedAfter = new LinkedHashSet<String>(this.get(className).getAfter());
            this.classes.forEach((name, autoConfigurationClass) -> {
                if (autoConfigurationClass.getBefore().contains(className)) {
                    classesRequestedAfter.add((String)name);
                }
            });
            return classesRequestedAfter;
        }
    }
}

