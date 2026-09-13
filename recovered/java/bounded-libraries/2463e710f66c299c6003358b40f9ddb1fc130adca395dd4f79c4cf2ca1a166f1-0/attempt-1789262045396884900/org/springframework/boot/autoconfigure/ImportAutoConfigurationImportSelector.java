/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.context.annotation.DeterminableImports
 *  org.springframework.boot.context.annotation.ImportCandidates
 *  org.springframework.core.annotation.AnnotatedElementUtils
 *  org.springframework.core.annotation.AnnotationAttributes
 *  org.springframework.core.annotation.AnnotationUtils
 *  org.springframework.core.io.support.SpringFactoriesLoader
 *  org.springframework.core.type.AnnotationMetadata
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.LinkedMultiValueMap
 *  org.springframework.util.MultiValueMap
 *  org.springframework.util.ObjectUtils
 */
package org.springframework.boot.autoconfigure;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.boot.autoconfigure.AutoConfigurationImportSelector;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.context.annotation.DeterminableImports;
import org.springframework.boot.context.annotation.ImportCandidates;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;

class ImportAutoConfigurationImportSelector
extends AutoConfigurationImportSelector
implements DeterminableImports {
    private static final Set<String> ANNOTATION_NAMES;

    ImportAutoConfigurationImportSelector() {
    }

    public Set<Object> determineImports(AnnotationMetadata metadata) {
        List<String> candidateConfigurations = this.getCandidateConfigurations(metadata, null);
        LinkedHashSet<String> result = new LinkedHashSet<String>(candidateConfigurations);
        result.removeAll(this.getExclusions(metadata, null));
        return Collections.unmodifiableSet(result);
    }

    @Override
    protected AnnotationAttributes getAttributes(AnnotationMetadata metadata) {
        return null;
    }

    @Override
    protected List<String> getCandidateConfigurations(AnnotationMetadata metadata, AnnotationAttributes attributes) {
        ArrayList<String> candidates = new ArrayList<String>();
        Map<Class<?>, List<Annotation>> annotations = this.getAnnotations(metadata);
        annotations.forEach((source, sourceAnnotations) -> this.collectCandidateConfigurations((Class<?>)source, (List<Annotation>)sourceAnnotations, (List<String>)candidates));
        return candidates;
    }

    private void collectCandidateConfigurations(Class<?> source, List<Annotation> annotations, List<String> candidates) {
        for (Annotation annotation : annotations) {
            candidates.addAll(this.getConfigurationsForAnnotation(source, annotation));
        }
    }

    private Collection<String> getConfigurationsForAnnotation(Class<?> source, Annotation annotation) {
        String[] classes = (String[])AnnotationUtils.getAnnotationAttributes((Annotation)annotation, (boolean)true).get("classes");
        if (classes.length > 0) {
            return Arrays.asList(classes);
        }
        return this.loadFactoryNames(source);
    }

    protected Collection<String> loadFactoryNames(Class<?> source) {
        ArrayList<String> factoryNames = new ArrayList<String>(SpringFactoriesLoader.loadFactoryNames(source, (ClassLoader)this.getBeanClassLoader()));
        ImportCandidates.load(source, (ClassLoader)this.getBeanClassLoader()).forEach(factoryNames::add);
        return factoryNames;
    }

    @Override
    protected Set<String> getExclusions(AnnotationMetadata metadata, AnnotationAttributes attributes) {
        LinkedHashSet<String> exclusions = new LinkedHashSet<String>();
        Class source = ClassUtils.resolveClassName((String)metadata.getClassName(), (ClassLoader)this.getBeanClassLoader());
        for (String string : ANNOTATION_NAMES) {
            AnnotationAttributes merged = AnnotatedElementUtils.getMergedAnnotationAttributes((AnnotatedElement)source, (String)string);
            Class[] exclude = merged != null ? merged.getClassArray("exclude") : null;
            if (exclude == null) continue;
            for (Class excludeClass : exclude) {
                exclusions.add(excludeClass.getName());
            }
        }
        for (List list : this.getAnnotations(metadata).values()) {
            for (Annotation annotation : list) {
                Object[] exclude = (String[])AnnotationUtils.getAnnotationAttributes((Annotation)annotation, (boolean)true).get("exclude");
                if (ObjectUtils.isEmpty((Object[])exclude)) continue;
                exclusions.addAll(Arrays.asList(exclude));
            }
        }
        exclusions.addAll(this.getExcludeAutoConfigurationsProperty());
        return exclusions;
    }

    protected final Map<Class<?>, List<Annotation>> getAnnotations(AnnotationMetadata metadata) {
        LinkedMultiValueMap annotations = new LinkedMultiValueMap();
        Class source = ClassUtils.resolveClassName((String)metadata.getClassName(), (ClassLoader)this.getBeanClassLoader());
        this.collectAnnotations(source, (MultiValueMap<Class<?>, Annotation>)annotations, new HashSet());
        return Collections.unmodifiableMap(annotations);
    }

    private void collectAnnotations(Class<?> source, MultiValueMap<Class<?>, Annotation> annotations, HashSet<Class<?>> seen) {
        if (source != null && seen.add(source)) {
            for (Annotation annotation : source.getDeclaredAnnotations()) {
                if (AnnotationUtils.isInJavaLangAnnotationPackage((Annotation)annotation)) continue;
                if (ANNOTATION_NAMES.contains(annotation.annotationType().getName())) {
                    annotations.add(source, (Object)annotation);
                }
                this.collectAnnotations(annotation.annotationType(), annotations, seen);
            }
            this.collectAnnotations(source.getSuperclass(), annotations, seen);
        }
    }

    @Override
    public int getOrder() {
        return super.getOrder() - 1;
    }

    @Override
    protected void handleInvalidExcludes(List<String> invalidExcludes) {
    }

    static {
        LinkedHashSet<String> names = new LinkedHashSet<String>();
        names.add(ImportAutoConfiguration.class.getName());
        names.add("org.springframework.boot.autoconfigure.test.ImportAutoConfiguration");
        ANNOTATION_NAMES = Collections.unmodifiableSet(names);
    }
}

