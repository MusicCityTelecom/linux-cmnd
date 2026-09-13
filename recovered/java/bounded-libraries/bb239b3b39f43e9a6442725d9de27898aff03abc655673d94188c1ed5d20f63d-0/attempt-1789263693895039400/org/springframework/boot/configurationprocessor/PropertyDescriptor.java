/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.configurationprocessor;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import org.springframework.boot.configurationprocessor.MetadataGenerationEnvironment;
import org.springframework.boot.configurationprocessor.metadata.ConfigurationMetadata;
import org.springframework.boot.configurationprocessor.metadata.ItemDeprecation;
import org.springframework.boot.configurationprocessor.metadata.ItemMetadata;

abstract class PropertyDescriptor<S extends Element> {
    private final TypeElement ownerElement;
    private final ExecutableElement factoryMethod;
    private final S source;
    private final String name;
    private final TypeMirror type;
    private final VariableElement field;
    private final ExecutableElement getter;
    private final ExecutableElement setter;

    protected PropertyDescriptor(TypeElement ownerElement, ExecutableElement factoryMethod, S source, String name, TypeMirror type, VariableElement field, ExecutableElement getter, ExecutableElement setter) {
        this.ownerElement = ownerElement;
        this.factoryMethod = factoryMethod;
        this.source = source;
        this.name = name;
        this.type = type;
        this.field = field;
        this.getter = getter;
        this.setter = setter;
    }

    TypeElement getOwnerElement() {
        return this.ownerElement;
    }

    ExecutableElement getFactoryMethod() {
        return this.factoryMethod;
    }

    S getSource() {
        return this.source;
    }

    String getName() {
        return this.name;
    }

    TypeMirror getType() {
        return this.type;
    }

    VariableElement getField() {
        return this.field;
    }

    ExecutableElement getGetter() {
        return this.getter;
    }

    ExecutableElement getSetter() {
        return this.setter;
    }

    protected abstract boolean isProperty(MetadataGenerationEnvironment var1);

    protected abstract Object resolveDefaultValue(MetadataGenerationEnvironment var1);

    protected ItemDeprecation resolveItemDeprecation(MetadataGenerationEnvironment environment) {
        boolean deprecated = environment.isDeprecated(this.getGetter()) || environment.isDeprecated(this.getSetter()) || environment.isDeprecated(this.getField()) || environment.isDeprecated(this.getFactoryMethod());
        return deprecated ? environment.resolveItemDeprecation(this.getGetter()) : null;
    }

    protected boolean isNested(MetadataGenerationEnvironment environment) {
        Element typeElement = environment.getTypeUtils().asElement(this.getType());
        if (!(typeElement instanceof TypeElement) || typeElement.getKind() == ElementKind.ENUM) {
            return false;
        }
        if (environment.getConfigurationPropertiesAnnotation(this.getGetter()) != null) {
            return false;
        }
        if (environment.getNestedConfigurationPropertyAnnotation(this.getField()) != null) {
            return true;
        }
        if (this.isCyclePresent(typeElement, this.getOwnerElement())) {
            return false;
        }
        return this.isParentTheSame(environment, typeElement, this.getOwnerElement());
    }

    ItemMetadata resolveItemMetadata(String prefix, MetadataGenerationEnvironment environment) {
        if (this.isNested(environment)) {
            return this.resolveItemMetadataGroup(prefix, environment);
        }
        if (this.isProperty(environment)) {
            return this.resolveItemMetadataProperty(prefix, environment);
        }
        return null;
    }

    private ItemMetadata resolveItemMetadataProperty(String prefix, MetadataGenerationEnvironment environment) {
        String dataType = this.resolveType(environment);
        String ownerType = environment.getTypeUtils().getQualifiedName(this.getOwnerElement());
        String description = this.resolveDescription(environment);
        Object defaultValue = this.resolveDefaultValue(environment);
        ItemDeprecation deprecation = this.resolveItemDeprecation(environment);
        return ItemMetadata.newProperty(prefix, this.getName(), dataType, ownerType, null, description, defaultValue, deprecation);
    }

    private ItemMetadata resolveItemMetadataGroup(String prefix, MetadataGenerationEnvironment environment) {
        Element propertyElement = environment.getTypeUtils().asElement(this.getType());
        String nestedPrefix = ConfigurationMetadata.nestedPrefix(prefix, this.getName());
        String dataType = environment.getTypeUtils().getQualifiedName(propertyElement);
        String ownerType = environment.getTypeUtils().getQualifiedName(this.getOwnerElement());
        String sourceMethod = this.getGetter() != null ? this.getGetter().toString() : null;
        return ItemMetadata.newGroup(nestedPrefix, dataType, ownerType, sourceMethod);
    }

    private String resolveType(MetadataGenerationEnvironment environment) {
        return environment.getTypeUtils().getType(this.getOwnerElement(), this.getType());
    }

    private String resolveDescription(MetadataGenerationEnvironment environment) {
        return environment.getTypeUtils().getJavaDoc(this.getField());
    }

    private boolean isCyclePresent(Element returnType, Element element) {
        if (!(element.getEnclosingElement() instanceof TypeElement)) {
            return false;
        }
        if (element.getEnclosingElement().equals(returnType)) {
            return true;
        }
        return this.isCyclePresent(returnType, element.getEnclosingElement());
    }

    private boolean isParentTheSame(MetadataGenerationEnvironment environment, Element returnType, TypeElement element) {
        if (returnType == null || element == null) {
            return false;
        }
        returnType = this.getTopLevelType(returnType);
        Element candidate = element;
        while (candidate != null && candidate instanceof TypeElement) {
            if (returnType.equals(this.getTopLevelType(candidate))) {
                return true;
            }
            candidate = environment.getTypeUtils().asElement(candidate.getSuperclass());
        }
        return false;
    }

    private Element getTopLevelType(Element element) {
        if (!(element.getEnclosingElement() instanceof TypeElement)) {
            return element;
        }
        return this.getTopLevelType(element.getEnclosingElement());
    }
}

