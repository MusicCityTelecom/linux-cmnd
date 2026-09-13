/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.control.customizers;

import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import groovy.transform.CompilationUnitAware;
import groovy.transform.Generated;
import groovy.transform.Internal;
import java.beans.Transient;
import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import java.util.List;
import java.util.Map;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.classgen.GeneratorContext;
import org.codehaus.groovy.control.CompilationUnit;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.control.customizers.CompilationCustomizer;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.BytecodeInterface8;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.codehaus.groovy.runtime.GStringImpl;
import org.codehaus.groovy.runtime.GeneratedClosure;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;
import org.codehaus.groovy.runtime.typehandling.ShortTypeHandling;
import org.codehaus.groovy.transform.ASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformationClass;

public class ASTTransformationCustomizer
extends CompilationCustomizer
implements CompilationUnitAware,
GroovyObject {
    private boolean applied;
    protected CompilationUnit compilationUnit;
    private final AnnotationNode annotationNode;
    private final ASTTransformation transformation;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private transient /* synthetic */ MetaClass metaClass;
    private static /* synthetic */ SoftReference $callSiteArray;

    public ASTTransformationCustomizer(Class<? extends Annotation> transformationAnnotation, String astTransformationClassName, ClassLoader transformationClassLoader) {
        super(ASTTransformationCustomizer.findPhase(transformationAnnotation, astTransformationClassName, transformationClassLoader));
        AnnotationNode annotationNode;
        MetaClass metaClass;
        this.metaClass = metaClass = this.$getStaticMetaClass();
        Class<ASTTransformation> clazz = ASTTransformationCustomizer.findASTTransformationClass(transformationAnnotation, astTransformationClassName, transformationClassLoader);
        ASTTransformation aSTTransformation = DefaultGroovyMethods.newInstance(clazz);
        this.transformation = (ASTTransformation)ScriptBytecodeAdapter.castToType(aSTTransformation, ASTTransformation.class);
        this.annotationNode = annotationNode = new AnnotationNode(ClassHelper.make(transformationAnnotation));
    }

    public ASTTransformationCustomizer(Class<? extends Annotation> transformationAnnotation, String astTransformationClassName) {
        this(transformationAnnotation, astTransformationClassName, transformationAnnotation.getClassLoader());
    }

    public ASTTransformationCustomizer(Map annotationParams, Class<? extends Annotation> transformationAnnotation, String astTransformationClassName, ClassLoader transformationClassLoader) {
        super(ASTTransformationCustomizer.findPhase(transformationAnnotation, astTransformationClassName, transformationClassLoader));
        AnnotationNode annotationNode;
        MetaClass metaClass;
        this.metaClass = metaClass = this.$getStaticMetaClass();
        Class<ASTTransformation> clazz = ASTTransformationCustomizer.findASTTransformationClass(transformationAnnotation, astTransformationClassName, transformationClassLoader);
        ASTTransformation aSTTransformation = DefaultGroovyMethods.newInstance(clazz);
        this.transformation = (ASTTransformation)ScriptBytecodeAdapter.castToType(aSTTransformation, ASTTransformation.class);
        this.annotationNode = annotationNode = new AnnotationNode(ClassHelper.make(transformationAnnotation));
        Map map = annotationParams;
        this.setAnnotationParameters(map);
    }

    public ASTTransformationCustomizer(Map annotationParams, Class<? extends Annotation> transformationAnnotation, String astTransformationClassName) {
        this(annotationParams, transformationAnnotation, transformationAnnotation.getClassLoader());
    }

    public ASTTransformationCustomizer(Class<? extends Annotation> transformationAnnotation, ClassLoader transformationClassLoader) {
        super(ASTTransformationCustomizer.findPhase(transformationAnnotation, transformationClassLoader));
        AnnotationNode annotationNode;
        MetaClass metaClass;
        this.metaClass = metaClass = this.$getStaticMetaClass();
        Class<ASTTransformation> clazz = ASTTransformationCustomizer.findASTTransformationClass(transformationAnnotation, transformationClassLoader);
        ASTTransformation aSTTransformation = DefaultGroovyMethods.newInstance(clazz);
        this.transformation = (ASTTransformation)ScriptBytecodeAdapter.castToType(aSTTransformation, ASTTransformation.class);
        this.annotationNode = annotationNode = new AnnotationNode(ClassHelper.make(transformationAnnotation));
    }

    public ASTTransformationCustomizer(Class<? extends Annotation> transformationAnnotation) {
        this(transformationAnnotation, transformationAnnotation.getClassLoader());
    }

    public ASTTransformationCustomizer(ASTTransformation transformation) {
        super(ASTTransformationCustomizer.findPhase(transformation));
        ASTTransformation aSTTransformation;
        MetaClass metaClass;
        this.metaClass = metaClass = this.$getStaticMetaClass();
        this.transformation = aSTTransformation = transformation;
        Object var4_4 = null;
        this.annotationNode = (AnnotationNode)ScriptBytecodeAdapter.castToType(var4_4, AnnotationNode.class);
    }

    public ASTTransformationCustomizer(Map annotationParams, Class<? extends Annotation> transformationAnnotation, ClassLoader transformationClassLoader) {
        super(ASTTransformationCustomizer.findPhase(transformationAnnotation, transformationClassLoader));
        AnnotationNode annotationNode;
        MetaClass metaClass;
        this.metaClass = metaClass = this.$getStaticMetaClass();
        Class<ASTTransformation> clazz = ASTTransformationCustomizer.findASTTransformationClass(transformationAnnotation, transformationClassLoader);
        ASTTransformation aSTTransformation = DefaultGroovyMethods.newInstance(clazz);
        this.transformation = (ASTTransformation)ScriptBytecodeAdapter.castToType(aSTTransformation, ASTTransformation.class);
        this.annotationNode = annotationNode = new AnnotationNode(ClassHelper.make(transformationAnnotation));
        Map map = annotationParams;
        this.setAnnotationParameters(map);
    }

    public ASTTransformationCustomizer(Map annotationParams, Class<? extends Annotation> transformationAnnotation) {
        this(annotationParams, transformationAnnotation, transformationAnnotation.getClassLoader());
    }

    public ASTTransformationCustomizer(Map annotationParams, ASTTransformation transformation) {
        this(transformation);
        Map map = annotationParams;
        this.setAnnotationParameters(map);
    }

    @Override
    public void setCompilationUnit(CompilationUnit unit) {
        CompilationUnit compilationUnit;
        this.compilationUnit = compilationUnit = unit;
    }

    private static Class<ASTTransformation> findASTTransformationClass(Class<? extends Annotation> anAnnotationClass, ClassLoader transformationClassLoader) {
        ClassLoader classLoader;
        GroovyASTTransformationClass annotation = (GroovyASTTransformationClass)ScriptBytecodeAdapter.castToType(anAnnotationClass.getAnnotation(GroovyASTTransformationClass.class), GroovyASTTransformationClass.class);
        if (annotation == null) {
            throw (Throwable)new IllegalArgumentException("Provided class doesn't look like an AST @interface");
        }
        Object[] classes = annotation.classes();
        Object[] classesAsStrings = annotation.value();
        if (classes.length + classesAsStrings.length > 1) {
            throw (Throwable)new IllegalArgumentException("AST transformation customizer doesn't support AST transforms with multiple classes");
        }
        return (classes == null ? false : DefaultTypeTransformation.booleanUnbox(classes)) ? ShortTypeHandling.castToClass(BytecodeInterface8.objectArrayGet(classes, 0)) : Class.forName(ShortTypeHandling.castToString(BytecodeInterface8.objectArrayGet(classesAsStrings, 0)), true, DefaultTypeTransformation.booleanUnbox(classLoader = transformationClassLoader) ? classLoader : anAnnotationClass.getClassLoader());
    }

    private static Class<ASTTransformation> findASTTransformationClass(Class<? extends Annotation> anAnnotationClass, String astTransformationClassName, ClassLoader transformationClassLoader) {
        ClassLoader classLoader = transformationClassLoader;
        return Class.forName(astTransformationClassName, true, DefaultTypeTransformation.booleanUnbox(classLoader) ? classLoader : anAnnotationClass.getClassLoader());
    }

    private static CompilePhase findPhase(ASTTransformation transformation) {
        if (transformation == null) {
            throw (Throwable)new IllegalArgumentException("Provided transformation must not be null");
        }
        Class<?> clazz = transformation.getClass();
        GroovyASTTransformation annotation = (GroovyASTTransformation)ScriptBytecodeAdapter.castToType(clazz.getAnnotation(GroovyASTTransformation.class), GroovyASTTransformation.class);
        if (annotation == null) {
            throw (Throwable)new IllegalArgumentException(ShortTypeHandling.castToString(new GStringImpl(new Object[]{GroovyASTTransformation.class.getName()}, new String[]{"Provided ast transformation is not annotated with ", ""})));
        }
        return annotation.phase();
    }

    private static CompilePhase findPhase(Class<? extends Annotation> annotationClass, ClassLoader transformationClassLoader) {
        Class<ASTTransformation> clazz = ASTTransformationCustomizer.findASTTransformationClass(annotationClass, transformationClassLoader);
        return ASTTransformationCustomizer.findPhase((ASTTransformation)ScriptBytecodeAdapter.castToType(DefaultGroovyMethods.newInstance(clazz), ASTTransformation.class));
    }

    private static CompilePhase findPhase(Class<? extends Annotation> annotationClass, String astTransformationClassName, ClassLoader transformationClassLoader) {
        Class<ASTTransformation> clazz = ASTTransformationCustomizer.findASTTransformationClass(annotationClass, astTransformationClassName, transformationClassLoader);
        return ASTTransformationCustomizer.findPhase((ASTTransformation)ScriptBytecodeAdapter.castToType(DefaultGroovyMethods.newInstance(clazz), ASTTransformation.class));
    }

    public void setAnnotationParameters(Map<String, Object> params) {
        CallSite[] callSiteArray = ASTTransformationCustomizer.$getCallSiteArray();
        if (!BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass() ? ScriptBytecodeAdapter.compareEqual(this.annotationNode, null) || ScriptBytecodeAdapter.compareEqual(params, null) || DefaultTypeTransformation.booleanUnbox(callSiteArray[0].call(params)) : ScriptBytecodeAdapter.compareEqual(this.annotationNode, null) || ScriptBytecodeAdapter.compareEqual(params, null) || DefaultTypeTransformation.booleanUnbox(callSiteArray[1].call(params))) {
            return;
        }
        public final class _setAnnotationParameters_closure1
        extends Closure
        implements GeneratedClosure {
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _setAnnotationParameters_closure1(Object _outerInstance, Object _thisObject) {
                CallSite[] callSiteArray = _setAnnotationParameters_closure1.$getCallSiteArray();
                super(_outerInstance, _thisObject);
            }

            public Object doCall(Object name, Object value) {
                CallSite[] callSiteArray = _setAnnotationParameters_closure1.$getCallSiteArray();
                if (!DefaultTypeTransformation.booleanUnbox(callSiteArray[0].call(callSiteArray[1].callGetProperty(callSiteArray[2].callGroovyObjectGetProperty(this)), name))) {
                    throw (Throwable)callSiteArray[3].callConstructor(IllegalArgumentException.class, new GStringImpl(new Object[]{callSiteArray[4].callGetProperty(callSiteArray[5].callGetProperty(callSiteArray[6].callGroovyObjectGetProperty(this))), name}, new String[]{"", " does not accept any [", "] parameter"}));
                }
                if (value instanceof Closure) {
                    throw (Throwable)callSiteArray[7].callConstructor(IllegalArgumentException.class, "Direct usage of closure is not supported by the AST compilation customizer. Please use ClosureExpression instead.");
                }
                Expression valueExpression = null;
                if (value instanceof Expression) {
                    Object object = value;
                    valueExpression = (Expression)ScriptBytecodeAdapter.castToType(object, Expression.class);
                    int n = 0;
                    ScriptBytecodeAdapter.setProperty(n, null, value, "lineNumber");
                    int n2 = 0;
                    ScriptBytecodeAdapter.setProperty(n2, null, value, "lastLineNumber");
                } else if (value instanceof Class) {
                    Object object = callSiteArray[8].callStatic(GeneralUtils.class, value);
                    valueExpression = (Expression)ScriptBytecodeAdapter.castToType(object, Expression.class);
                } else if (value instanceof Enum) {
                    Object object = callSiteArray[9].callStatic(GeneralUtils.class, callSiteArray[10].callStatic(GeneralUtils.class, callSiteArray[11].call(ClassHelper.class, callSiteArray[12].call(value))), callSiteArray[13].call(value));
                    valueExpression = (Expression)ScriptBytecodeAdapter.castToType(object, Expression.class);
                } else if (value instanceof List || DefaultTypeTransformation.booleanUnbox(callSiteArray[14].call(callSiteArray[15].call(value)))) {
                    public final class _closure2
                    extends Closure
                    implements GeneratedClosure {
                        private static /* synthetic */ ClassInfo $staticClassInfo;
                        public static transient /* synthetic */ boolean __$stMC;
                        private static /* synthetic */ SoftReference $callSiteArray;

                        public _closure2(Object _outerInstance, Object _thisObject) {
                            CallSite[] callSiteArray = _closure2.$getCallSiteArray();
                            super(_outerInstance, _thisObject);
                        }

                        public Object doCall(Object it) {
                            CallSite[] callSiteArray = _closure2.$getCallSiteArray();
                            return it instanceof Class ? callSiteArray[0].callStatic(GeneralUtils.class, it) : callSiteArray[1].callStatic(GeneralUtils.class, it);
                        }

                        @Generated
                        public Object doCall() {
                            CallSite[] callSiteArray = _closure2.$getCallSiteArray();
                            return this.doCall(null);
                        }

                        protected /* synthetic */ MetaClass $getStaticMetaClass() {
                            if (this.getClass() != _closure2.class) {
                                return ScriptBytecodeAdapter.initMetaClass(this);
                            }
                            ClassInfo classInfo = $staticClassInfo;
                            if (classInfo == null) {
                                $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
                            }
                            return classInfo.getMetaClass();
                        }

                        public /* synthetic */ MethodHandles.Lookup $getLookup() {
                            return MethodHandles.lookup();
                        }

                        private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
                            stringArray[0] = "classX";
                            stringArray[1] = "constX";
                        }

                        private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                            String[] stringArray = new String[2];
                            _closure2.$createCallSiteArray_1(stringArray);
                            return new CallSiteArray(_closure2.class, stringArray);
                        }

                        private static /* synthetic */ CallSite[] $getCallSiteArray() {
                            CallSiteArray callSiteArray;
                            if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                                callSiteArray = _closure2.$createCallSiteArray();
                                $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                            }
                            return callSiteArray.array;
                        }
                    }
                    Object object = callSiteArray[16].callStatic(GeneralUtils.class, callSiteArray[17].call(value, new _closure2(this, this.getThisObject())));
                    valueExpression = (Expression)ScriptBytecodeAdapter.castToType(object, Expression.class);
                } else {
                    Object object = callSiteArray[18].callStatic(GeneralUtils.class, value);
                    valueExpression = (Expression)ScriptBytecodeAdapter.castToType(object, Expression.class);
                }
                return callSiteArray[19].call(callSiteArray[20].callGroovyObjectGetProperty(this), name, valueExpression);
            }

            @Generated
            public Object call(Object name, Object value) {
                CallSite[] callSiteArray = _setAnnotationParameters_closure1.$getCallSiteArray();
                return callSiteArray[21].callCurrent(this, name, value);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (this.getClass() != _setAnnotationParameters_closure1.class) {
                    return ScriptBytecodeAdapter.initMetaClass(this);
                }
                ClassInfo classInfo = $staticClassInfo;
                if (classInfo == null) {
                    $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
                }
                return classInfo.getMetaClass();
            }

            public /* synthetic */ MethodHandles.Lookup $getLookup() {
                return MethodHandles.lookup();
            }

            private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
                stringArray[0] = "getMethod";
                stringArray[1] = "classNode";
                stringArray[2] = "annotationNode";
                stringArray[3] = "<$constructor$>";
                stringArray[4] = "name";
                stringArray[5] = "classNode";
                stringArray[6] = "annotationNode";
                stringArray[7] = "<$constructor$>";
                stringArray[8] = "classX";
                stringArray[9] = "propX";
                stringArray[10] = "classX";
                stringArray[11] = "make";
                stringArray[12] = "getClass";
                stringArray[13] = "toString";
                stringArray[14] = "isArray";
                stringArray[15] = "getClass";
                stringArray[16] = "listX";
                stringArray[17] = "collect";
                stringArray[18] = "constX";
                stringArray[19] = "addMember";
                stringArray[20] = "annotationNode";
                stringArray[21] = "doCall";
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[22];
                _setAnnotationParameters_closure1.$createCallSiteArray_1(stringArray);
                return new CallSiteArray(_setAnnotationParameters_closure1.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _setAnnotationParameters_closure1.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        callSiteArray[2].call(params, new _setAnnotationParameters_closure1(this, this));
    }

    @Override
    public void call(SourceUnit source, GeneratorContext context, ClassNode classNode) {
        boolean bl;
        if (this.transformation instanceof CompilationUnitAware) {
            CompilationUnit compilationUnit = this.compilationUnit;
            ((CompilationUnitAware)ScriptBytecodeAdapter.castToType(this.transformation, CompilationUnitAware.class)).setCompilationUnit(compilationUnit);
        }
        if (this.annotationNode != null) {
            ClassNode classNode2 = classNode;
            this.annotationNode.setSourcePosition(classNode2);
            this.transformation.visit(new ASTNode[]{this.annotationNode, classNode}, source);
        } else if (!this.applied) {
            this.transformation.visit(null, source);
        }
        this.applied = bl = true;
    }

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != ASTTransformationCustomizer.class) {
            return ScriptBytecodeAdapter.initMetaClass(this);
        }
        ClassInfo classInfo = $staticClassInfo;
        if (classInfo == null) {
            $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
        }
        return classInfo.getMetaClass();
    }

    @Override
    @Generated
    @Internal
    @Transient
    public MetaClass getMetaClass() {
        MetaClass metaClass = this.metaClass;
        if (metaClass != null) {
            return metaClass;
        }
        this.metaClass = this.$getStaticMetaClass();
        return this.metaClass;
    }

    @Override
    @Generated
    @Internal
    public void setMetaClass(MetaClass metaClass) {
        this.metaClass = metaClass;
    }

    public static /* synthetic */ MethodHandles.Lookup $getLookup() {
        return MethodHandles.lookup();
    }

    @Generated
    public final ASTTransformation getTransformation() {
        return this.transformation;
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
        stringArray[0] = "isEmpty";
        stringArray[1] = "isEmpty";
        stringArray[2] = "each";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[3];
        ASTTransformationCustomizer.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(ASTTransformationCustomizer.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = ASTTransformationCustomizer.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }
}

