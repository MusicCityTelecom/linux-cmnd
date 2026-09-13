/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.configurationprocessor;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import org.springframework.boot.configurationprocessor.MetadataGenerationEnvironment;
import org.springframework.boot.configurationprocessor.PropertyDescriptor;

class JavaBeanPropertyDescriptor
extends PropertyDescriptor<ExecutableElement> {
    JavaBeanPropertyDescriptor(TypeElement ownerElement, ExecutableElement factoryMethod, ExecutableElement getter, String name, TypeMirror type, VariableElement field, ExecutableElement setter) {
        super(ownerElement, factoryMethod, getter, name, type, field, getter, setter);
    }

    @Override
    protected boolean isProperty(MetadataGenerationEnvironment env) {
        boolean isCollection = env.getTypeUtils().isCollectionOrMap(this.getType());
        return !env.isExcluded(this.getType()) && this.getGetter() != null && (this.getSetter() != null || isCollection);
    }

    @Override
    protected Object resolveDefaultValue(MetadataGenerationEnvironment environment) {
        return environment.getFieldDefaultValue(this.getOwnerElement(), this.getName());
    }
}

