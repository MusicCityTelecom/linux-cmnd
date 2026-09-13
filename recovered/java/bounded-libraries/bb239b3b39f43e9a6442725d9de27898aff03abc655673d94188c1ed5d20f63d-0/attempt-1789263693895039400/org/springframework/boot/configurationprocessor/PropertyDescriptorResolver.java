/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.configurationprocessor;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.NestingKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import org.springframework.boot.configurationprocessor.ConstructorParameterPropertyDescriptor;
import org.springframework.boot.configurationprocessor.JavaBeanPropertyDescriptor;
import org.springframework.boot.configurationprocessor.LombokPropertyDescriptor;
import org.springframework.boot.configurationprocessor.MetadataGenerationEnvironment;
import org.springframework.boot.configurationprocessor.PropertyDescriptor;
import org.springframework.boot.configurationprocessor.TypeElementMembers;

class PropertyDescriptorResolver {
    private final MetadataGenerationEnvironment environment;

    PropertyDescriptorResolver(MetadataGenerationEnvironment environment) {
        this.environment = environment;
    }

    Stream<PropertyDescriptor<?>> resolve(TypeElement type, ExecutableElement factoryMethod) {
        TypeElementMembers members = new TypeElementMembers(this.environment, type);
        if (factoryMethod != null) {
            return this.resolveJavaBeanProperties(type, factoryMethod, members);
        }
        return this.resolve(ConfigurationPropertiesTypeElement.of(type, this.environment), factoryMethod, members);
    }

    private Stream<PropertyDescriptor<?>> resolve(ConfigurationPropertiesTypeElement type, ExecutableElement factoryMethod, TypeElementMembers members) {
        if (type.isConstructorBindingEnabled()) {
            ExecutableElement constructor = type.getBindConstructor();
            if (constructor != null) {
                return this.resolveConstructorProperties(type.getType(), factoryMethod, members, constructor);
            }
            return Stream.empty();
        }
        return this.resolveJavaBeanProperties(type.getType(), factoryMethod, members);
    }

    Stream<PropertyDescriptor<?>> resolveConstructorProperties(TypeElement type, ExecutableElement factoryMethod, TypeElementMembers members, ExecutableElement constructor) {
        LinkedHashMap candidates = new LinkedHashMap();
        constructor.getParameters().forEach(parameter -> {
            String name = this.getParameterName((VariableElement)parameter);
            TypeMirror propertyType = parameter.asType();
            ExecutableElement getter = members.getPublicGetter(name, propertyType);
            ExecutableElement setter = members.getPublicSetter(name, propertyType);
            VariableElement field = members.getFields().get(name);
            this.register(candidates, new ConstructorParameterPropertyDescriptor(type, factoryMethod, (VariableElement)parameter, name, propertyType, field, getter, setter));
        });
        return candidates.values().stream();
    }

    private String getParameterName(VariableElement parameter) {
        AnnotationMirror nameAnnotation = this.environment.getNameAnnotation(parameter);
        if (nameAnnotation != null) {
            return (String)this.environment.getAnnotationElementValues(nameAnnotation).get("value");
        }
        return parameter.getSimpleName().toString();
    }

    Stream<PropertyDescriptor<?>> resolveJavaBeanProperties(TypeElement type, ExecutableElement factoryMethod, TypeElementMembers members) {
        LinkedHashMap candidates = new LinkedHashMap();
        members.getPublicGetters().forEach((name, getters) -> {
            VariableElement field = members.getFields().get(name);
            ExecutableElement getter = this.findMatchingGetter(members, (List<ExecutableElement>)getters, field);
            TypeMirror propertyType = getter.getReturnType();
            this.register(candidates, new JavaBeanPropertyDescriptor(type, factoryMethod, getter, (String)name, propertyType, field, members.getPublicSetter((String)name, propertyType)));
        });
        members.getFields().forEach((name, field) -> {
            TypeMirror propertyType = field.asType();
            ExecutableElement getter = members.getPublicGetter((String)name, propertyType);
            ExecutableElement setter = members.getPublicSetter((String)name, propertyType);
            this.register(candidates, new LombokPropertyDescriptor(type, factoryMethod, (VariableElement)field, (String)name, propertyType, getter, setter));
        });
        return candidates.values().stream();
    }

    private ExecutableElement findMatchingGetter(TypeElementMembers members, List<ExecutableElement> candidates, VariableElement field) {
        if (candidates.size() > 1 && field != null) {
            return members.getMatchingGetter(candidates, field.asType());
        }
        return candidates.get(0);
    }

    private void register(Map<String, PropertyDescriptor<?>> candidates, PropertyDescriptor<?> descriptor) {
        if (!candidates.containsKey(descriptor.getName()) && this.isCandidate(descriptor)) {
            candidates.put(descriptor.getName(), descriptor);
        }
    }

    private boolean isCandidate(PropertyDescriptor<?> descriptor) {
        return descriptor.isProperty(this.environment) || descriptor.isNested(this.environment);
    }

    private static class ConfigurationPropertiesTypeElement {
        private final TypeElement type;
        private final boolean constructorBoundType;
        private final List<ExecutableElement> constructors;
        private final List<ExecutableElement> boundConstructors;

        ConfigurationPropertiesTypeElement(TypeElement type, boolean constructorBoundType, List<ExecutableElement> constructors, List<ExecutableElement> boundConstructors) {
            this.type = type;
            this.constructorBoundType = constructorBoundType;
            this.constructors = constructors;
            this.boundConstructors = boundConstructors;
        }

        TypeElement getType() {
            return this.type;
        }

        boolean isConstructorBindingEnabled() {
            return this.constructorBoundType || !this.boundConstructors.isEmpty();
        }

        ExecutableElement getBindConstructor() {
            if (this.constructorBoundType && this.boundConstructors.isEmpty()) {
                return this.findBoundConstructor();
            }
            if (this.boundConstructors.size() == 1) {
                return this.boundConstructors.get(0);
            }
            return null;
        }

        private ExecutableElement findBoundConstructor() {
            ExecutableElement boundConstructor = null;
            for (ExecutableElement candidate : this.constructors) {
                if (candidate.getParameters().isEmpty()) continue;
                if (boundConstructor != null) {
                    return null;
                }
                boundConstructor = candidate;
            }
            return boundConstructor;
        }

        static ConfigurationPropertiesTypeElement of(TypeElement type, MetadataGenerationEnvironment env) {
            boolean constructorBoundType = ConfigurationPropertiesTypeElement.isConstructorBoundType(type, env);
            List<ExecutableElement> constructors = ElementFilter.constructorsIn(type.getEnclosedElements());
            List<ExecutableElement> boundConstructors = constructors.stream().filter(env::hasConstructorBindingAnnotation).collect(Collectors.toList());
            return new ConfigurationPropertiesTypeElement(type, constructorBoundType, constructors, boundConstructors);
        }

        private static boolean isConstructorBoundType(TypeElement type, MetadataGenerationEnvironment env) {
            if (env.hasConstructorBindingAnnotation(type) || "java.lang.Record".equals(type.getSuperclass().toString())) {
                return true;
            }
            if (type.getNestingKind() == NestingKind.MEMBER) {
                return ConfigurationPropertiesTypeElement.isConstructorBoundType((TypeElement)type.getEnclosingElement(), env);
            }
            return false;
        }
    }
}

