/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.configurationprocessor;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.util.SimpleTypeVisitor8;
import javax.lang.model.util.Types;

class TypeUtils {
    private static final Map<TypeKind, Class<?>> PRIMITIVE_WRAPPERS;
    private static final Map<String, TypeKind> WRAPPER_TO_PRIMITIVE;
    private static final Pattern NEW_LINE_PATTERN;
    private final ProcessingEnvironment env;
    private final Types types;
    private final TypeExtractor typeExtractor;
    private final TypeMirror collectionType;
    private final TypeMirror mapType;
    private final Map<TypeElement, TypeDescriptor> typeDescriptors = new HashMap<TypeElement, TypeDescriptor>();

    TypeUtils(ProcessingEnvironment env) {
        this.env = env;
        this.types = env.getTypeUtils();
        this.typeExtractor = new TypeExtractor(this.types);
        this.collectionType = this.getDeclaredType(this.types, Collection.class, 1);
        this.mapType = this.getDeclaredType(this.types, Map.class, 2);
    }

    private TypeMirror getDeclaredType(Types types, Class<?> typeClass, int numberOfTypeArgs) {
        TypeMirror[] typeArgs = new TypeMirror[numberOfTypeArgs];
        Arrays.setAll(typeArgs, i -> types.getWildcardType(null, null));
        TypeElement typeElement = this.env.getElementUtils().getTypeElement(typeClass.getName());
        try {
            return types.getDeclaredType(typeElement, typeArgs);
        }
        catch (IllegalArgumentException ex) {
            return types.getDeclaredType(typeElement, new TypeMirror[0]);
        }
    }

    boolean isSameType(TypeMirror t1, TypeMirror t2) {
        return this.types.isSameType(t1, t2);
    }

    Element asElement(TypeMirror type) {
        return this.types.asElement(type);
    }

    String getQualifiedName(Element element) {
        return this.typeExtractor.getQualifiedName(element);
    }

    String getType(TypeElement element, TypeMirror type) {
        if (type == null) {
            return null;
        }
        return type.accept(this.typeExtractor, this.createTypeDescriptor(element));
    }

    TypeMirror extractElementType(TypeMirror type) {
        if (!this.env.getTypeUtils().isAssignable(type, this.collectionType)) {
            return null;
        }
        return this.getCollectionElementType(type);
    }

    private TypeMirror getCollectionElementType(TypeMirror type) {
        if (((TypeElement)this.types.asElement(type)).getQualifiedName().contentEquals(Collection.class.getName())) {
            DeclaredType declaredType = (DeclaredType)type;
            if (declaredType.getTypeArguments().isEmpty()) {
                return this.types.getDeclaredType(this.env.getElementUtils().getTypeElement(Object.class.getName()), new TypeMirror[0]);
            }
            return declaredType.getTypeArguments().get(0);
        }
        for (TypeMirror typeMirror : this.env.getTypeUtils().directSupertypes(type)) {
            if (!this.types.isAssignable(typeMirror, this.collectionType)) continue;
            return this.getCollectionElementType(typeMirror);
        }
        return null;
    }

    boolean isCollectionOrMap(TypeMirror type) {
        return this.env.getTypeUtils().isAssignable(type, this.collectionType) || this.env.getTypeUtils().isAssignable(type, this.mapType);
    }

    String getJavaDoc(Element element) {
        String javadoc;
        String string = javadoc = element != null ? this.env.getElementUtils().getDocComment(element) : null;
        if (javadoc != null) {
            javadoc = NEW_LINE_PATTERN.matcher(javadoc).replaceAll("").trim();
        }
        return javadoc == null || javadoc.isEmpty() ? null : javadoc;
    }

    PrimitiveType getPrimitiveType(TypeMirror typeMirror) {
        if (this.getPrimitiveFor(typeMirror) != null) {
            return this.types.unboxedType(typeMirror);
        }
        return null;
    }

    TypeMirror getWrapperOrPrimitiveFor(TypeMirror typeMirror) {
        Class<?> candidate = this.getWrapperFor(typeMirror);
        if (candidate != null) {
            return this.env.getElementUtils().getTypeElement(candidate.getName()).asType();
        }
        TypeKind primitiveKind = this.getPrimitiveFor(typeMirror);
        if (primitiveKind != null) {
            return this.env.getTypeUtils().getPrimitiveType(primitiveKind);
        }
        return null;
    }

    private Class<?> getWrapperFor(TypeMirror type) {
        return PRIMITIVE_WRAPPERS.get((Object)type.getKind());
    }

    private TypeKind getPrimitiveFor(TypeMirror type) {
        return WRAPPER_TO_PRIMITIVE.get(type.toString());
    }

    TypeDescriptor resolveTypeDescriptor(TypeElement element) {
        if (this.typeDescriptors.containsKey(element)) {
            return this.typeDescriptors.get(element);
        }
        return this.createTypeDescriptor(element);
    }

    private TypeDescriptor createTypeDescriptor(TypeElement element) {
        TypeDescriptor descriptor = new TypeDescriptor();
        this.process(descriptor, element.asType());
        this.typeDescriptors.put(element, descriptor);
        return descriptor;
    }

    private void process(TypeDescriptor descriptor, TypeMirror type) {
        if (type.getKind() == TypeKind.DECLARED) {
            DeclaredType declaredType = (DeclaredType)type;
            DeclaredType freshType = (DeclaredType)this.env.getElementUtils().getTypeElement(this.types.asElement(type).toString()).asType();
            List<? extends TypeMirror> arguments = declaredType.getTypeArguments();
            for (int i = 0; i < arguments.size(); ++i) {
                TypeMirror specificType = arguments.get(i);
                TypeMirror signatureType = freshType.getTypeArguments().get(i);
                descriptor.registerIfNecessary(signatureType, specificType);
            }
            TypeElement element = (TypeElement)this.types.asElement(type);
            this.process(descriptor, element.getSuperclass());
        }
    }

    static {
        EnumMap<TypeKind, Class> wrappers = new EnumMap<TypeKind, Class>(TypeKind.class);
        wrappers.put(TypeKind.BOOLEAN, Boolean.class);
        wrappers.put(TypeKind.BYTE, Byte.class);
        wrappers.put(TypeKind.CHAR, Character.class);
        wrappers.put(TypeKind.DOUBLE, Double.class);
        wrappers.put(TypeKind.FLOAT, Float.class);
        wrappers.put(TypeKind.INT, Integer.class);
        wrappers.put(TypeKind.LONG, Long.class);
        wrappers.put(TypeKind.SHORT, Short.class);
        PRIMITIVE_WRAPPERS = Collections.unmodifiableMap(wrappers);
        NEW_LINE_PATTERN = Pattern.compile("[\r\n]+");
        HashMap<String, TypeKind> primitives = new HashMap<String, TypeKind>();
        PRIMITIVE_WRAPPERS.forEach((kind, wrapperClass) -> primitives.put(wrapperClass.getName(), (TypeKind)((Object)kind)));
        WRAPPER_TO_PRIMITIVE = primitives;
    }

    static class TypeDescriptor {
        private final Map<TypeVariable, TypeMirror> generics = new HashMap<TypeVariable, TypeMirror>();

        TypeDescriptor() {
        }

        Map<TypeVariable, TypeMirror> getGenerics() {
            return Collections.unmodifiableMap(this.generics);
        }

        TypeMirror resolveGeneric(TypeVariable typeVariable) {
            return this.resolveGeneric(this.getParameterName(typeVariable));
        }

        TypeMirror resolveGeneric(String parameterName) {
            return this.generics.entrySet().stream().filter(e -> this.getParameterName((TypeVariable)e.getKey()).equals(parameterName)).findFirst().map(Map.Entry::getValue).orElse(null);
        }

        private void registerIfNecessary(TypeMirror variable, TypeMirror resolution) {
            if (variable instanceof TypeVariable) {
                TypeVariable typeVariable = (TypeVariable)variable;
                if (this.generics.keySet().stream().noneMatch(candidate -> this.getParameterName((TypeVariable)candidate).equals(this.getParameterName(typeVariable)))) {
                    this.generics.put(typeVariable, resolution);
                }
            }
        }

        private String getParameterName(TypeVariable typeVariable) {
            return typeVariable.asElement().getSimpleName().toString();
        }
    }

    private static class TypeExtractor
    extends SimpleTypeVisitor8<String, TypeDescriptor> {
        private final Types types;

        TypeExtractor(Types types) {
            this.types = types;
        }

        @Override
        public String visitDeclared(DeclaredType type, TypeDescriptor descriptor) {
            TypeElement enclosingElement = this.getEnclosingTypeElement(type);
            String qualifiedName = this.determineQualifiedName(type, enclosingElement);
            if (type.getTypeArguments().isEmpty()) {
                return qualifiedName;
            }
            StringBuilder name = new StringBuilder();
            name.append(qualifiedName);
            name.append("<").append(type.getTypeArguments().stream().map(t -> (String)this.visit((TypeMirror)t, descriptor)).collect(Collectors.joining(","))).append(">");
            return name.toString();
        }

        private String determineQualifiedName(DeclaredType type, TypeElement enclosingElement) {
            if (enclosingElement != null) {
                return this.getQualifiedName(enclosingElement) + "$" + type.asElement().getSimpleName();
            }
            return this.getQualifiedName(type.asElement());
        }

        @Override
        public String visitTypeVariable(TypeVariable t, TypeDescriptor descriptor) {
            TypeMirror typeMirror = descriptor.resolveGeneric(t);
            if (typeMirror != null) {
                if (typeMirror instanceof TypeVariable) {
                    TypeVariable typeVariable = (TypeVariable)typeMirror;
                    if (!this.hasCycle(typeVariable)) {
                        return (String)this.visit(typeVariable.getUpperBound(), descriptor);
                    }
                } else {
                    return (String)this.visit(typeMirror, descriptor);
                }
            }
            return this.defaultAction(t.getUpperBound(), descriptor);
        }

        private boolean hasCycle(TypeVariable variable) {
            TypeMirror upperBound = variable.getUpperBound();
            if (upperBound instanceof DeclaredType) {
                return ((DeclaredType)upperBound).getTypeArguments().stream().anyMatch(candidate -> candidate.equals(variable));
            }
            return false;
        }

        @Override
        public String visitArray(ArrayType t, TypeDescriptor descriptor) {
            return t.getComponentType().accept(this, descriptor) + "[]";
        }

        @Override
        public String visitPrimitive(PrimitiveType t, TypeDescriptor descriptor) {
            return this.types.boxedClass(t).getQualifiedName().toString();
        }

        @Override
        protected String defaultAction(TypeMirror t, TypeDescriptor descriptor) {
            return t.toString();
        }

        String getQualifiedName(Element element) {
            if (element == null) {
                return null;
            }
            TypeElement enclosingElement = this.getEnclosingTypeElement(element.asType());
            if (enclosingElement != null) {
                return this.getQualifiedName(enclosingElement) + "$" + ((DeclaredType)element.asType()).asElement().getSimpleName();
            }
            if (element instanceof TypeElement) {
                return ((TypeElement)element).getQualifiedName().toString();
            }
            throw new IllegalStateException("Could not extract qualified name from " + element);
        }

        private TypeElement getEnclosingTypeElement(TypeMirror type) {
            DeclaredType declaredType;
            Element enclosingElement;
            if (type instanceof DeclaredType && (enclosingElement = (declaredType = (DeclaredType)type).asElement().getEnclosingElement()) instanceof TypeElement) {
                return (TypeElement)enclosingElement;
            }
            return null;
        }
    }
}

