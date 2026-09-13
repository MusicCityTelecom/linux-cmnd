/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.configurationprocessor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import org.springframework.boot.configurationprocessor.MetadataGenerationEnvironment;

class TypeElementMembers {
    private static final String OBJECT_CLASS_NAME = Object.class.getName();
    private final MetadataGenerationEnvironment env;
    private final TypeElement targetType;
    private final Map<String, VariableElement> fields = new LinkedHashMap<String, VariableElement>();
    private final Map<String, List<ExecutableElement>> publicGetters = new LinkedHashMap<String, List<ExecutableElement>>();
    private final Map<String, List<ExecutableElement>> publicSetters = new LinkedHashMap<String, List<ExecutableElement>>();

    TypeElementMembers(MetadataGenerationEnvironment env, TypeElement targetType) {
        this.env = env;
        this.targetType = targetType;
        this.process(targetType);
    }

    private void process(TypeElement element) {
        for (ExecutableElement method : ElementFilter.methodsIn(element.getEnclosedElements())) {
            this.processMethod(method);
        }
        for (VariableElement field : ElementFilter.fieldsIn(element.getEnclosedElements())) {
            this.processField(field);
        }
        Element superType = this.env.getTypeUtils().asElement(element.getSuperclass());
        if (superType instanceof TypeElement && !OBJECT_CLASS_NAME.equals(superType.toString())) {
            this.process((TypeElement)superType);
        }
    }

    private void processMethod(ExecutableElement method) {
        if (this.isPublic(method)) {
            TypeMirror paramType;
            String propertyName;
            List matchingSetters;
            String name = method.getSimpleName().toString();
            if (this.isGetter(method)) {
                TypeMirror returnType;
                String propertyName2 = this.getAccessorName(name);
                List matchingGetters = this.publicGetters.computeIfAbsent(propertyName2, k -> new ArrayList());
                if (this.getMatchingGetter(matchingGetters, returnType = method.getReturnType()) == null) {
                    matchingGetters.add(method);
                }
            } else if (this.isSetter(method) && this.getMatchingSetter(matchingSetters = this.publicSetters.computeIfAbsent(propertyName = this.getAccessorName(name), k -> new ArrayList()), paramType = method.getParameters().get(0).asType()) == null) {
                matchingSetters.add(method);
            }
        }
    }

    private boolean isPublic(ExecutableElement method) {
        Set<Modifier> modifiers = method.getModifiers();
        return modifiers.contains((Object)Modifier.PUBLIC) && !modifiers.contains((Object)Modifier.ABSTRACT) && !modifiers.contains((Object)Modifier.STATIC);
    }

    ExecutableElement getMatchingGetter(List<ExecutableElement> candidates, TypeMirror type) {
        return this.getMatchingAccessor(candidates, type, ExecutableElement::getReturnType);
    }

    private ExecutableElement getMatchingSetter(List<ExecutableElement> candidates, TypeMirror type) {
        return this.getMatchingAccessor(candidates, type, candidate -> candidate.getParameters().get(0).asType());
    }

    private ExecutableElement getMatchingAccessor(List<ExecutableElement> candidates, TypeMirror type, Function<ExecutableElement, TypeMirror> typeExtractor) {
        for (ExecutableElement candidate : candidates) {
            TypeMirror candidateType = typeExtractor.apply(candidate);
            if (!this.env.getTypeUtils().isSameType(candidateType, type)) continue;
            return candidate;
        }
        return null;
    }

    private boolean isGetter(ExecutableElement method) {
        String name = method.getSimpleName().toString();
        return (name.startsWith("get") && name.length() > 3 || name.startsWith("is") && name.length() > 2) && method.getParameters().isEmpty() && TypeKind.VOID != method.getReturnType().getKind();
    }

    private boolean isSetter(ExecutableElement method) {
        String name = method.getSimpleName().toString();
        return name.startsWith("set") && name.length() > 3 && method.getParameters().size() == 1 && this.isSetterReturnType(method);
    }

    private boolean isSetterReturnType(ExecutableElement method) {
        TypeMirror returnType = method.getReturnType();
        if (TypeKind.VOID == returnType.getKind()) {
            return true;
        }
        if (TypeKind.DECLARED == returnType.getKind() && this.env.getTypeUtils().isSameType(method.getEnclosingElement().asType(), returnType)) {
            return true;
        }
        if (TypeKind.TYPEVAR == returnType.getKind()) {
            String resolvedType = this.env.getTypeUtils().getType(this.targetType, returnType);
            return resolvedType != null && resolvedType.equals(this.env.getTypeUtils().getQualifiedName(this.targetType));
        }
        return false;
    }

    private String getAccessorName(String methodName) {
        String name = methodName.startsWith("is") ? methodName.substring(2) : methodName.substring(3);
        name = Character.toLowerCase(name.charAt(0)) + name.substring(1);
        return name;
    }

    private void processField(VariableElement field) {
        String name = field.getSimpleName().toString();
        if (!this.fields.containsKey(name)) {
            this.fields.put(name, field);
        }
    }

    Map<String, VariableElement> getFields() {
        return Collections.unmodifiableMap(this.fields);
    }

    Map<String, List<ExecutableElement>> getPublicGetters() {
        return Collections.unmodifiableMap(this.publicGetters);
    }

    ExecutableElement getPublicGetter(String name, TypeMirror type) {
        List<ExecutableElement> candidates = this.publicGetters.get(name);
        return this.getPublicAccessor(candidates, type, specificType -> this.getMatchingGetter(candidates, (TypeMirror)specificType));
    }

    ExecutableElement getPublicSetter(String name, TypeMirror type) {
        List<ExecutableElement> candidates = this.publicSetters.get(name);
        return this.getPublicAccessor(candidates, type, specificType -> this.getMatchingSetter(candidates, (TypeMirror)specificType));
    }

    private ExecutableElement getPublicAccessor(List<ExecutableElement> candidates, TypeMirror type, Function<TypeMirror, ExecutableElement> matchingAccessorExtractor) {
        if (candidates != null) {
            ExecutableElement matching = matchingAccessorExtractor.apply(type);
            if (matching != null) {
                return matching;
            }
            TypeMirror alternative = this.env.getTypeUtils().getWrapperOrPrimitiveFor(type);
            if (alternative != null) {
                return matchingAccessorExtractor.apply(alternative);
            }
        }
        return null;
    }
}

