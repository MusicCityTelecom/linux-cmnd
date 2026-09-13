/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.DefaultParameterNameDiscoverer
 *  org.springframework.core.ParameterNameDiscoverer
 *  org.springframework.core.annotation.MergedAnnotations
 *  org.springframework.lang.Nullable
 */
package org.springframework.data.mapping.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mapping.FactoryMethod;
import org.springframework.data.mapping.InstanceCreatorMetadata;
import org.springframework.data.mapping.MappingException;
import org.springframework.data.mapping.Parameter;
import org.springframework.data.mapping.PersistentEntity;
import org.springframework.data.mapping.PersistentProperty;
import org.springframework.data.mapping.model.PreferredConstructorDiscoverer;
import org.springframework.data.util.TypeInformation;
import org.springframework.lang.Nullable;

class InstanceCreatorMetadataDiscoverer {
    private static final ParameterNameDiscoverer PARAMETER_NAME_DISCOVERER = new DefaultParameterNameDiscoverer();

    InstanceCreatorMetadataDiscoverer() {
    }

    @Nullable
    public static <T, P extends PersistentProperty<P>> InstanceCreatorMetadata<P> discover(PersistentEntity<T, P> entity) {
        List<Method> candidates;
        AnnotatedElement[] declaredConstructors = entity.getType().getDeclaredConstructors();
        AnnotatedElement[] declaredMethods = entity.getType().getDeclaredMethods();
        boolean hasAnnotatedFactoryMethod = InstanceCreatorMetadataDiscoverer.findAnnotation(PersistenceCreator.class, declaredMethods);
        boolean hasAnnotatedConstructor = InstanceCreatorMetadataDiscoverer.findAnnotation(PersistenceCreator.class, declaredConstructors);
        if (hasAnnotatedConstructor && hasAnnotatedFactoryMethod) {
            throw new MappingException(String.format("Invalid usage of @Factory and @PersistenceConstructor on %s; Only one annotation type permitted to indicate how entity instances should be created", entity.getType().getName()));
        }
        if (hasAnnotatedFactoryMethod && (candidates = InstanceCreatorMetadataDiscoverer.discoverFactoryMethods(entity, (Method[])declaredMethods)).size() == 1) {
            return InstanceCreatorMetadataDiscoverer.getFactoryMethod(entity, candidates.get(0));
        }
        return PreferredConstructorDiscoverer.discover(entity);
    }

    private static <T, P extends PersistentProperty<P>> List<Method> discoverFactoryMethods(PersistentEntity<T, P> entity, Method[] declaredMethods) {
        ArrayList<Method> candidates = new ArrayList<Method>();
        for (Method method : declaredMethods) {
            InstanceCreatorMetadataDiscoverer.validateMethod(method);
            if (!InstanceCreatorMetadataDiscoverer.isFactoryMethod(method, entity.getType())) continue;
            if (!InstanceCreatorMetadataDiscoverer.findAnnotation(PersistenceCreator.class, method)) continue;
            candidates.add(method);
        }
        return candidates;
    }

    private static <T, P extends PersistentProperty<P>> FactoryMethod<Object, P> getFactoryMethod(PersistentEntity<T, P> entity, Method method) {
        Parameter[] parameters = new Parameter[method.getParameterCount()];
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        List<TypeInformation<?>> types = entity.getTypeInformation().getParameterTypes(method);
        String[] parameterNames = PARAMETER_NAME_DISCOVERER.getParameterNames(method);
        for (int i = 0; i < parameters.length; ++i) {
            String name = parameterNames == null || parameterNames.length <= i ? null : parameterNames[i];
            TypeInformation<?> type = types.get(i);
            Annotation[] annotations = parameterAnnotations[i];
            parameters[i] = new Parameter(name, type, annotations, entity);
        }
        return new FactoryMethod(method, parameters);
    }

    private static void validateMethod(Method method) {
        if (MergedAnnotations.from((AnnotatedElement)method).isPresent(PersistenceCreator.class) && !Modifier.isStatic(method.getModifiers())) {
            throw new MappingException(String.format("@ PersistenceCreator can only be used on static methods; Offending method: %s", method));
        }
    }

    private static <T> boolean isFactoryMethod(Method method, Class<T> type) {
        if (Modifier.isPrivate(method.getModifiers())) {
            return false;
        }
        if (method.isSynthetic()) {
            return false;
        }
        return Modifier.isStatic(method.getModifiers()) && method.getReturnType().isAssignableFrom(type);
    }

    private static boolean findAnnotation(Class<? extends Annotation> annotationType, AnnotatedElement ... elements) {
        for (AnnotatedElement element : elements) {
            if (!MergedAnnotations.from((AnnotatedElement)element).isPresent(annotationType)) continue;
            return true;
        }
        return false;
    }
}

