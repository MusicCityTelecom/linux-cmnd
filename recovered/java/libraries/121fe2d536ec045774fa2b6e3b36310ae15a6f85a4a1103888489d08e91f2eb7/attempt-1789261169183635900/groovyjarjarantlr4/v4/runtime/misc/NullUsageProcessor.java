/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.misc;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.NoType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

@SupportedAnnotationTypes(value={"groovyjarjarantlr4.v4.runtime.misc.NotNull", "groovyjarjarantlr4.v4.runtime.misc.Nullable"})
public class NullUsageProcessor
extends AbstractProcessor {
    public static final String NotNullClassName = "groovyjarjarantlr4.v4.runtime.misc.NotNull";
    public static final String NullableClassName = "groovyjarjarantlr4.v4.runtime.misc.Nullable";
    private TypeElement notNullType;
    private TypeElement nullableType;

    @Override
    public SourceVersion getSupportedSourceVersion() {
        SourceVersion latestSupported = SourceVersion.latestSupported();
        if (latestSupported.ordinal() <= 6) {
            return SourceVersion.RELEASE_6;
        }
        if (latestSupported.ordinal() <= 8) {
            return latestSupported;
        }
        return SourceVersion.values()[8];
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (!this.checkClassNameConstants()) {
            return true;
        }
        this.notNullType = this.processingEnv.getElementUtils().getTypeElement(NotNullClassName);
        this.nullableType = this.processingEnv.getElementUtils().getTypeElement(NullableClassName);
        Set<? extends Element> notNullElements = roundEnv.getElementsAnnotatedWith(this.notNullType);
        Set<? extends Element> nullableElements = roundEnv.getElementsAnnotatedWith(this.nullableType);
        HashSet<? extends Element> intersection = new HashSet<Element>(notNullElements);
        intersection.retainAll(nullableElements);
        for (Element element : intersection) {
            String error = String.format("%s cannot be annotated with both %s and %s", element.getKind().toString().replace('_', ' ').toLowerCase(), this.notNullType.getSimpleName(), this.nullableType.getSimpleName());
            this.processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, error, element);
        }
        this.checkVoidMethodAnnotations(notNullElements, this.notNullType);
        this.checkVoidMethodAnnotations(nullableElements, this.nullableType);
        this.checkPrimitiveTypeAnnotations(nullableElements, Diagnostic.Kind.ERROR, this.nullableType);
        this.checkPrimitiveTypeAnnotations(notNullElements, Diagnostic.Kind.WARNING, this.notNullType);
        HashMap<String, Map<ExecutableElement, List<Element>>> namedMethodMap = new HashMap<String, Map<ExecutableElement, List<Element>>>();
        this.addElementsToNamedMethodMap(notNullElements, namedMethodMap);
        this.addElementsToNamedMethodMap(nullableElements, namedMethodMap);
        for (Map.Entry entry : namedMethodMap.entrySet()) {
            for (Map.Entry subentry : ((Map)entry.getValue()).entrySet()) {
                this.checkOverriddenMethods((ExecutableElement)subentry.getKey());
            }
        }
        return true;
    }

    private boolean checkClassNameConstants() {
        boolean success = this.checkClassNameConstant(NotNullClassName, NotNull.class);
        return success &= this.checkClassNameConstant(NullableClassName, Nullable.class);
    }

    private boolean checkClassNameConstant(String className, Class<?> clazz) {
        if (className == null) {
            throw new NullPointerException("className");
        }
        if (clazz == null) {
            throw new NullPointerException("clazz");
        }
        if (!className.equals(clazz.getCanonicalName())) {
            this.processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, String.format("Unable to process null usage annotations due to class name mismatch: %s != %s", className, clazz.getCanonicalName()));
            return false;
        }
        return true;
    }

    private void checkVoidMethodAnnotations(Set<? extends Element> elements, TypeElement annotationType) {
        for (Element element : elements) {
            ExecutableElement executableElement;
            TypeMirror returnType;
            if (element.getKind() != ElementKind.METHOD || !((returnType = (executableElement = (ExecutableElement)element).getReturnType()) instanceof NoType) || returnType.getKind() != TypeKind.VOID) continue;
            String error = String.format("void method cannot be annotated with %s", annotationType.getSimpleName());
            this.processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, error, element, this.getAnnotationMirror(element, annotationType));
        }
    }

    private void checkPrimitiveTypeAnnotations(Set<? extends Element> elements, Diagnostic.Kind kind, TypeElement annotationType) {
        block4: for (Element element : elements) {
            TypeMirror typeToCheck;
            switch (element.getKind()) {
                case FIELD: 
                case PARAMETER: 
                case LOCAL_VARIABLE: {
                    VariableElement variableElement = (VariableElement)element;
                    typeToCheck = variableElement.asType();
                    break;
                }
                case METHOD: {
                    ExecutableElement executableElement = (ExecutableElement)element;
                    typeToCheck = executableElement.getReturnType();
                    break;
                }
                default: {
                    continue block4;
                }
            }
            if (!(typeToCheck instanceof PrimitiveType) || !typeToCheck.getKind().isPrimitive()) continue;
            String error = String.format("%s with a primitive type %s be annotated with %s", element.getKind().toString().replace('_', ' ').toLowerCase(), kind == Diagnostic.Kind.ERROR ? "cannot" : "should not", annotationType.getSimpleName());
            this.processingEnv.getMessager().printMessage(kind, error, element, this.getAnnotationMirror(element, annotationType));
        }
    }

    private void addElementsToNamedMethodMap(Set<? extends Element> elements, Map<String, Map<ExecutableElement, List<Element>>> namedMethodMap) {
        block4: for (Element element : elements) {
            List<Element> annotatedElementsOfMethod;
            Map<ExecutableElement, List<Element>> annotatedMethodWithName;
            ExecutableElement method;
            switch (element.getKind()) {
                case PARAMETER: {
                    method = (ExecutableElement)element.getEnclosingElement();
                    assert (method.getKind() == ElementKind.METHOD);
                    break;
                }
                case METHOD: {
                    method = (ExecutableElement)element;
                    break;
                }
                default: {
                    continue block4;
                }
            }
            if ((annotatedMethodWithName = namedMethodMap.get(method.getSimpleName().toString())) == null) {
                annotatedMethodWithName = new HashMap<ExecutableElement, List<Element>>();
                namedMethodMap.put(method.getSimpleName().toString(), annotatedMethodWithName);
            }
            if ((annotatedElementsOfMethod = annotatedMethodWithName.get(method)) == null) {
                annotatedElementsOfMethod = new ArrayList<Element>();
                annotatedMethodWithName.put(method, annotatedElementsOfMethod);
            }
            annotatedElementsOfMethod.add(element);
        }
    }

    private void checkOverriddenMethods(ExecutableElement method) {
        TypeElement declaringType = (TypeElement)method.getEnclosingElement();
        HashSet<Element> errorElements = new HashSet<Element>();
        HashSet<Element> warnedElements = new HashSet<Element>();
        block0: for (TypeMirror typeMirror : this.getAllSupertypes(this.processingEnv.getTypeUtils().getDeclaredType(declaringType, new TypeMirror[0]))) {
            for (Element element : ((TypeElement)this.processingEnv.getTypeUtils().asElement(typeMirror)).getEnclosedElements()) {
                if (!(element instanceof ExecutableElement) || !this.processingEnv.getElementUtils().overrides(method, (ExecutableElement)element, declaringType)) continue;
                this.checkOverriddenMethod(method, (ExecutableElement)element, errorElements, warnedElements);
                continue block0;
            }
        }
    }

    private List<? extends TypeMirror> getAllSupertypes(TypeMirror type) {
        HashSet<? extends TypeMirror> supertypes = new HashSet<TypeMirror>();
        ArrayDeque<? extends TypeMirror> worklist = new ArrayDeque<TypeMirror>();
        worklist.add(type);
        while (!worklist.isEmpty()) {
            List<? extends TypeMirror> next = this.processingEnv.getTypeUtils().directSupertypes((TypeMirror)worklist.poll());
            if (!supertypes.addAll(next)) continue;
            worklist.addAll(next);
        }
        return new ArrayList(supertypes);
    }

    private void checkOverriddenMethod(ExecutableElement overrider, ExecutableElement overridden, Set<Element> errorElements, Set<Element> warnedElements) {
        String error;
        if (this.isNullable(overrider) && this.isNotNull(overridden) && errorElements.add(overrider)) {
            error = String.format("method annotated with %s cannot override or implement a method annotated with %s", this.nullableType.getSimpleName(), this.notNullType.getSimpleName());
            this.processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, error, overrider, this.getNullableAnnotationMirror(overrider));
        } else if (this.isNullable(overrider) && !this.isNullable(overridden) && !this.isNotNull(overridden) && !errorElements.contains(overrider) && warnedElements.add(overrider)) {
            error = String.format("method annotated with %s overrides a method that is not annotated", this.nullableType.getSimpleName());
            this.processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, error, overrider, this.getNullableAnnotationMirror(overrider));
        }
        List<? extends VariableElement> overriderParameters = overrider.getParameters();
        List<? extends VariableElement> overriddenParameters = overridden.getParameters();
        for (int i = 0; i < overriderParameters.size(); ++i) {
            String error2;
            if (this.isNotNull(overriderParameters.get(i)) && this.isNullable(overriddenParameters.get(i)) && errorElements.add(overriderParameters.get(i))) {
                error2 = String.format("parameter %s annotated with %s cannot override or implement a parameter annotated with %s", overriderParameters.get(i).getSimpleName(), this.notNullType.getSimpleName(), this.nullableType.getSimpleName());
                this.processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, error2, overriderParameters.get(i), this.getNotNullAnnotationMirror(overriderParameters.get(i)));
                continue;
            }
            if (!this.isNotNull(overriderParameters.get(i)) || this.isNullable(overriddenParameters.get(i)) || this.isNotNull(overriddenParameters.get(i)) || errorElements.contains(overriderParameters.get(i)) || !warnedElements.add(overriderParameters.get(i))) continue;
            error2 = String.format("parameter %s annotated with %s overrides a parameter that is not annotated", overriderParameters.get(i).getSimpleName(), this.notNullType.getSimpleName());
            this.processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, error2, overriderParameters.get(i), this.getNotNullAnnotationMirror(overriderParameters.get(i)));
        }
    }

    private boolean isNotNull(Element element) {
        return this.getNotNullAnnotationMirror(element) != null;
    }

    private boolean isNullable(Element element) {
        return this.getNullableAnnotationMirror(element) != null;
    }

    private AnnotationMirror getNotNullAnnotationMirror(Element element) {
        return this.getAnnotationMirror(element, this.notNullType);
    }

    private AnnotationMirror getNullableAnnotationMirror(Element element) {
        return this.getAnnotationMirror(element, this.nullableType);
    }

    private AnnotationMirror getAnnotationMirror(Element element, TypeElement annotationType) {
        for (AnnotationMirror annotationMirror : element.getAnnotationMirrors()) {
            if (annotationMirror.getAnnotationType().asElement() != annotationType) continue;
            return annotationMirror;
        }
        return null;
    }
}

