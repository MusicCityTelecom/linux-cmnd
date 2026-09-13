/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.github.classgraph.ClassGraph
 *  io.github.classgraph.ClassInfo
 *  io.github.classgraph.ScanResult
 *  lombok.Generated
 *  org.springframework.lang.NonNull
 */
package org.apereo.cas.util;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Generated;
import org.springframework.lang.NonNull;

public final class ReflectionUtils {
    @NonNull
    public static <T> Collection<Class<? extends T>> findSubclassesInPackage(Class<T> superclass, String packageName) {
        try (ScanResult scanResult = new ClassGraph().acceptPackages(new String[]{packageName}).enableClassInfo().scan();){
            if (superclass.isInterface()) {
                ArrayList<Class<? extends T>> arrayList = new ArrayList<Class<? extends T>>(scanResult.getClassesImplementing(superclass).loadClasses(superclass));
                return arrayList;
            }
            ArrayList<Class<? extends T>> arrayList = new ArrayList<Class<? extends T>>(scanResult.getSubclasses(superclass).loadClasses(superclass));
            return arrayList;
        }
    }

    public static Collection<Class<?>> findClassesWithAnnotationsInPackage(Collection<Class<? extends Annotation>> annotations, String packageName) {
        try (ScanResult scanResult = new ClassGraph().acceptPackages(new String[]{packageName}).enableAnnotationInfo().scan();){
            Collection collection = annotations.stream().map(annotation -> scanResult.getClassesWithAnnotation(annotation).loadClasses()).flatMap(Collection::stream).collect(Collectors.toList());
            return collection;
        }
    }

    public static Optional<Class<?>> findClassBySimpleNameInPackage(String simpleName, String packageName) {
        try (ScanResult scanResult = new ClassGraph().acceptPackages(new String[]{packageName}).enableClassInfo().scan();){
            Optional<Class<?>> optional = scanResult.getAllClasses().stream().filter(c -> c.getSimpleName().equalsIgnoreCase(simpleName)).findFirst().map(ClassInfo::loadClass);
            return optional;
        }
    }

    @Generated
    private ReflectionUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

