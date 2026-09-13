/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.configurationprocessor;

import java.util.Map;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import org.springframework.boot.configurationprocessor.MetadataGenerationEnvironment;
import org.springframework.boot.configurationprocessor.PropertyDescriptor;
import org.springframework.boot.configurationprocessor.metadata.ItemDeprecation;

class LombokPropertyDescriptor
extends PropertyDescriptor<VariableElement> {
    private static final String LOMBOK_DATA_ANNOTATION = "lombok.Data";
    private static final String LOMBOK_VALUE_ANNOTATION = "lombok.Value";
    private static final String LOMBOK_GETTER_ANNOTATION = "lombok.Getter";
    private static final String LOMBOK_SETTER_ANNOTATION = "lombok.Setter";
    private static final String LOMBOK_ACCESS_LEVEL_PUBLIC = "PUBLIC";

    LombokPropertyDescriptor(TypeElement typeElement, ExecutableElement factoryMethod, VariableElement field, String name, TypeMirror type, ExecutableElement getter, ExecutableElement setter) {
        super(typeElement, factoryMethod, field, name, type, field, getter, setter);
    }

    @Override
    protected boolean isProperty(MetadataGenerationEnvironment env) {
        if (!this.hasLombokPublicAccessor(env, true)) {
            return false;
        }
        boolean isCollection = env.getTypeUtils().isCollectionOrMap(this.getType());
        return !env.isExcluded(this.getType()) && (this.hasSetter(env) || isCollection);
    }

    @Override
    protected Object resolveDefaultValue(MetadataGenerationEnvironment environment) {
        return environment.getFieldDefaultValue(this.getOwnerElement(), this.getName());
    }

    @Override
    protected boolean isNested(MetadataGenerationEnvironment environment) {
        if (!this.hasLombokPublicAccessor(environment, true)) {
            return false;
        }
        return super.isNested(environment);
    }

    @Override
    protected ItemDeprecation resolveItemDeprecation(MetadataGenerationEnvironment environment) {
        boolean deprecated = environment.isDeprecated(this.getField()) || environment.isDeprecated(this.getGetter()) || environment.isDeprecated(this.getFactoryMethod());
        return deprecated ? environment.resolveItemDeprecation(this.getGetter()) : null;
    }

    private boolean hasSetter(MetadataGenerationEnvironment env) {
        boolean nonFinalPublicField = !this.getField().getModifiers().contains((Object)Modifier.FINAL) && this.hasLombokPublicAccessor(env, false);
        return this.getSetter() != null || nonFinalPublicField;
    }

    private boolean hasLombokPublicAccessor(MetadataGenerationEnvironment env, boolean getter) {
        String annotation = getter ? LOMBOK_GETTER_ANNOTATION : LOMBOK_SETTER_ANNOTATION;
        AnnotationMirror lombokMethodAnnotationOnField = env.getAnnotation(this.getField(), annotation);
        if (lombokMethodAnnotationOnField != null) {
            return this.isAccessLevelPublic(env, lombokMethodAnnotationOnField);
        }
        AnnotationMirror lombokMethodAnnotationOnElement = env.getAnnotation(this.getOwnerElement(), annotation);
        if (lombokMethodAnnotationOnElement != null) {
            return this.isAccessLevelPublic(env, lombokMethodAnnotationOnElement);
        }
        return env.hasAnnotation(this.getOwnerElement(), LOMBOK_DATA_ANNOTATION) || env.hasAnnotation(this.getOwnerElement(), LOMBOK_VALUE_ANNOTATION);
    }

    private boolean isAccessLevelPublic(MetadataGenerationEnvironment env, AnnotationMirror lombokAnnotation) {
        Map<String, Object> values = env.getAnnotationElementValues(lombokAnnotation);
        Object value = values.get("value");
        return value == null || value.toString().equals(LOMBOK_ACCESS_LEVEL_PUBLIC);
    }
}

