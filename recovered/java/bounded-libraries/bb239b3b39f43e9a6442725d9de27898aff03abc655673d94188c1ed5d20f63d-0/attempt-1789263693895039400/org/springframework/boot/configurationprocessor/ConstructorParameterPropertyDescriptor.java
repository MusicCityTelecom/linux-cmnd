/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.configurationprocessor;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.TypeKindVisitor8;
import javax.tools.Diagnostic;
import org.springframework.boot.configurationprocessor.MetadataGenerationEnvironment;
import org.springframework.boot.configurationprocessor.PropertyDescriptor;

class ConstructorParameterPropertyDescriptor
extends PropertyDescriptor<VariableElement> {
    ConstructorParameterPropertyDescriptor(TypeElement ownerElement, ExecutableElement factoryMethod, VariableElement source, String name, TypeMirror type, VariableElement field, ExecutableElement getter, ExecutableElement setter) {
        super(ownerElement, factoryMethod, source, name, type, field, getter, setter);
    }

    @Override
    protected boolean isProperty(MetadataGenerationEnvironment env) {
        return !this.isNested(env);
    }

    @Override
    protected Object resolveDefaultValue(MetadataGenerationEnvironment environment) {
        Object defaultValue = this.getDefaultValueFromAnnotation(environment, (Element)this.getSource());
        if (defaultValue != null) {
            return defaultValue;
        }
        return ((VariableElement)this.getSource()).asType().accept(DefaultPrimitiveTypeVisitor.INSTANCE, null);
    }

    private Object getDefaultValueFromAnnotation(MetadataGenerationEnvironment environment, Element element) {
        AnnotationMirror annotation = environment.getDefaultValueAnnotation(element);
        List<String> defaultValue = this.getDefaultValue(environment, annotation);
        if (defaultValue != null) {
            try {
                TypeMirror specificType = this.determineSpecificType(environment);
                if (defaultValue.size() == 1) {
                    return this.coerceValue(specificType, defaultValue.get(0));
                }
                return defaultValue.stream().map(value -> this.coerceValue(specificType, (String)value)).collect(Collectors.toList());
            }
            catch (IllegalArgumentException ex) {
                environment.getMessager().printMessage(Diagnostic.Kind.ERROR, ex.getMessage(), element, annotation);
            }
        }
        return null;
    }

    private List<String> getDefaultValue(MetadataGenerationEnvironment environment, AnnotationMirror annotation) {
        if (annotation == null) {
            return null;
        }
        Map<String, Object> values = environment.getAnnotationElementValues(annotation);
        return (List)values.get("value");
    }

    private TypeMirror determineSpecificType(MetadataGenerationEnvironment environment) {
        PrimitiveType primitiveType;
        TypeMirror candidate = ((VariableElement)this.getSource()).asType();
        TypeMirror elementCandidate = environment.getTypeUtils().extractElementType(candidate);
        if (elementCandidate != null) {
            candidate = elementCandidate;
        }
        return (primitiveType = environment.getTypeUtils().getPrimitiveType(candidate)) != null ? primitiveType : candidate;
    }

    private Object coerceValue(TypeMirror type, String value) {
        Object coercedValue = type.accept(DefaultValueCoercionTypeVisitor.INSTANCE, value);
        return coercedValue != null ? coercedValue : value;
    }

    private static class DefaultPrimitiveTypeVisitor
    extends TypeKindVisitor8<Object, Void> {
        private static final DefaultPrimitiveTypeVisitor INSTANCE = new DefaultPrimitiveTypeVisitor();

        private DefaultPrimitiveTypeVisitor() {
        }

        @Override
        public Object visitPrimitiveAsBoolean(PrimitiveType t, Void ignore) {
            return false;
        }

        @Override
        public Object visitPrimitiveAsByte(PrimitiveType t, Void ignore) {
            return (byte)0;
        }

        @Override
        public Object visitPrimitiveAsShort(PrimitiveType t, Void ignore) {
            return (short)0;
        }

        @Override
        public Object visitPrimitiveAsInt(PrimitiveType t, Void ignore) {
            return 0;
        }

        @Override
        public Object visitPrimitiveAsLong(PrimitiveType t, Void ignore) {
            return 0L;
        }

        @Override
        public Object visitPrimitiveAsChar(PrimitiveType t, Void ignore) {
            return null;
        }

        @Override
        public Object visitPrimitiveAsFloat(PrimitiveType t, Void ignore) {
            return Float.valueOf(0.0f);
        }

        @Override
        public Object visitPrimitiveAsDouble(PrimitiveType t, Void ignore) {
            return 0.0;
        }
    }

    private static class DefaultValueCoercionTypeVisitor
    extends TypeKindVisitor8<Object, String> {
        private static final DefaultValueCoercionTypeVisitor INSTANCE = new DefaultValueCoercionTypeVisitor();

        private DefaultValueCoercionTypeVisitor() {
        }

        private <T extends Number> T parseNumber(String value, Function<String, T> parser, PrimitiveType primitiveType) {
            try {
                return (T)((Number)parser.apply(value));
            }
            catch (NumberFormatException ex) {
                throw new IllegalArgumentException(String.format("Invalid %s representation '%s'", primitiveType, value));
            }
        }

        @Override
        public Object visitPrimitiveAsBoolean(PrimitiveType t, String value) {
            return Boolean.parseBoolean(value);
        }

        @Override
        public Object visitPrimitiveAsByte(PrimitiveType t, String value) {
            return this.parseNumber(value, Byte::parseByte, t);
        }

        @Override
        public Object visitPrimitiveAsShort(PrimitiveType t, String value) {
            return this.parseNumber(value, Short::parseShort, t);
        }

        @Override
        public Object visitPrimitiveAsInt(PrimitiveType t, String value) {
            return this.parseNumber(value, Integer::parseInt, t);
        }

        @Override
        public Object visitPrimitiveAsLong(PrimitiveType t, String value) {
            return this.parseNumber(value, Long::parseLong, t);
        }

        @Override
        public Object visitPrimitiveAsChar(PrimitiveType t, String value) {
            if (value.length() > 1) {
                throw new IllegalArgumentException(String.format("Invalid character representation '%s'", value));
            }
            return value;
        }

        @Override
        public Object visitPrimitiveAsFloat(PrimitiveType t, String value) {
            return this.parseNumber(value, Float::parseFloat, t);
        }

        @Override
        public Object visitPrimitiveAsDouble(PrimitiveType t, String value) {
            return this.parseNumber(value, Double::parseDouble, t);
        }
    }
}

