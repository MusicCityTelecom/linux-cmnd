/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform;

import groovy.lang.Binding;
import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import groovy.lang.GroovyShell;
import groovy.lang.MetaClass;
import groovy.lang.Reference;
import groovy.transform.CompilationUnitAware;
import groovy.transform.Generated;
import groovy.transform.Internal;
import java.beans.Transient;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassCodeVisitorSupport;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.expr.ClosureExpression;
import org.codehaus.groovy.ast.expr.PropertyExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.EmptyStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.control.CompilationUnit;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.Janitor;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.control.customizers.ImportCustomizer;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.BytecodeInterface8;
import org.codehaus.groovy.runtime.GStringImpl;
import org.codehaus.groovy.runtime.GeneratedClosure;
import org.codehaus.groovy.runtime.MethodClosure;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;
import org.codehaus.groovy.runtime.typehandling.ShortTypeHandling;
import org.codehaus.groovy.syntax.SyntaxException;
import org.codehaus.groovy.transform.ASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;

@GroovyASTTransformation(phase=CompilePhase.SEMANTIC_ANALYSIS)
public class ASTTestTransformation
implements ASTTransformation,
CompilationUnitAware,
GroovyObject {
    private CompilationUnit compilationUnit;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private transient /* synthetic */ MetaClass metaClass;
    private static /* synthetic */ SoftReference $callSiteArray;

    @Generated
    public ASTTestTransformation() {
        MetaClass metaClass;
        CallSite[] callSiteArray = ASTTestTransformation.$getCallSiteArray();
        this.metaClass = metaClass = this.$getStaticMetaClass();
    }

    @Override
    public void visit(ASTNode[] nodes, SourceUnit source) {
        Object object;
        CallSite[] callSiteArray = ASTTestTransformation.$getCallSiteArray();
        AnnotationNode annotationNode = null;
        if (!BytecodeInterface8.isOrigInt() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            Object object2 = callSiteArray[0].call((Object)nodes, 0);
            annotationNode = (AnnotationNode)ScriptBytecodeAdapter.castToType(object2, AnnotationNode.class);
        } else {
            Object object3 = BytecodeInterface8.objectArrayGet(nodes, 0);
            annotationNode = (AnnotationNode)ScriptBytecodeAdapter.castToType(object3, AnnotationNode.class);
        }
        Object member = callSiteArray[1].call((Object)annotationNode, "phase");
        CompilePhase phase = null;
        if (DefaultTypeTransformation.booleanUnbox(member)) {
            if (member instanceof VariableExpression) {
                Object object4 = callSiteArray[2].call(CompilePhase.class, callSiteArray[3].callGetProperty(member));
                phase = (CompilePhase)ShortTypeHandling.castToEnum(object4, CompilePhase.class);
            } else if (member instanceof PropertyExpression) {
                Object object5 = callSiteArray[4].call(CompilePhase.class, callSiteArray[5].callGetProperty(member));
                phase = (CompilePhase)ShortTypeHandling.castToEnum(object5, CompilePhase.class);
            }
            callSiteArray[6].call(annotationNode, "phase", callSiteArray[7].callStatic(GeneralUtils.class, callSiteArray[8].callStatic(GeneralUtils.class, callSiteArray[9].call(ClassHelper.class, CompilePhase.class)), callSiteArray[10].call((Object)phase)));
            if (ScriptBytecodeAdapter.compareLessThan(callSiteArray[11].callGetProperty((Object)phase), callSiteArray[12].callGetProperty(this.compilationUnit))) {
                throw (Throwable)callSiteArray[13].callConstructor(SyntaxException.class, callSiteArray[14].call((Object)"ASTTest phase must be at least ", callSiteArray[15].callStatic(CompilePhase.class, callSiteArray[16].callGetProperty(this.compilationUnit))), member);
            }
        }
        member = object = callSiteArray[17].call((Object)annotationNode, "value");
        if (DefaultTypeTransformation.booleanUnbox(member) && !(member instanceof ClosureExpression)) {
            throw (Throwable)callSiteArray[18].callConstructor(SyntaxException.class, "ASTTest value must be a closure", member);
        }
        if (!BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            if (!DefaultTypeTransformation.booleanUnbox(member) && !DefaultTypeTransformation.booleanUnbox(callSiteArray[19].call((Object)annotationNode, ASTTestTransformation.class))) {
                throw (Throwable)callSiteArray[20].callConstructor(SyntaxException.class, "Missing test expression", annotationNode);
            }
        } else if (!DefaultTypeTransformation.booleanUnbox(member) && !DefaultTypeTransformation.booleanUnbox(callSiteArray[21].call((Object)annotationNode, ASTTestTransformation.class))) {
            throw (Throwable)callSiteArray[22].callConstructor(SyntaxException.class, "Missing test expression", annotationNode);
        }
        callSiteArray[23].call(annotationNode, ASTTestTransformation.class, member);
        callSiteArray[24].call(annotationNode, "value", callSiteArray[25].callConstructor(ClosureExpression.class, callSiteArray[26].callGetProperty(Parameter.class), callSiteArray[27].callGetProperty(EmptyStatement.class)));
        Object var12_12 = null;
        ScriptBytecodeAdapter.setField(var12_12, ASTTestTransformation.class, callSiteArray[28].callGetProperty(member), "parent");
        CompilationUnit.ISourceUnitOperation astTester = null;
        if (!BytecodeInterface8.isOrigInt() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            Object object6 = callSiteArray[29].callConstructor(ASTTester.class, this, ScriptBytecodeAdapter.createMap(new Object[]{"astNode", callSiteArray[30].call((Object)nodes, 1), "sourceUnit", source, "testClosure", callSiteArray[31].call((Object)annotationNode, ASTTestTransformation.class)}));
            astTester = (CompilationUnit.ISourceUnitOperation)ScriptBytecodeAdapter.castToType(object6, CompilationUnit.ISourceUnitOperation.class);
        } else {
            Object object7 = callSiteArray[32].callConstructor(ASTTester.class, this, ScriptBytecodeAdapter.createMap(new Object[]{"astNode", BytecodeInterface8.objectArrayGet(nodes, 1), "sourceUnit", source, "testClosure", callSiteArray[33].call((Object)annotationNode, ASTTestTransformation.class)}));
            astTester = (CompilationUnit.ISourceUnitOperation)ScriptBytecodeAdapter.castToType(object7, CompilationUnit.ISourceUnitOperation.class);
        }
        if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            CompilePhase compilePhase = phase;
            int p = DefaultTypeTransformation.intUnbox(callSiteArray[34].callGetProperty(DefaultTypeTransformation.booleanUnbox((Object)compilePhase) ? compilePhase : callSiteArray[35].callGetProperty(CompilePhase.class)));
            CompilePhase compilePhase2 = phase;
            int q = DefaultTypeTransformation.intUnbox(callSiteArray[36].callGetProperty(DefaultTypeTransformation.booleanUnbox((Object)compilePhase2) ? compilePhase2 : callSiteArray[37].callGetProperty(CompilePhase.class)));
            while (p <= q) {
                callSiteArray[38].call(this.compilationUnit, astTester, p);
                p = DefaultTypeTransformation.intUnbox(callSiteArray[39].call((Object)p, 1));
            }
        } else {
            CompilePhase compilePhase = phase;
            int p = DefaultTypeTransformation.intUnbox(callSiteArray[40].callGetProperty(DefaultTypeTransformation.booleanUnbox((Object)compilePhase) ? compilePhase : callSiteArray[41].callGetProperty(CompilePhase.class)));
            CompilePhase compilePhase3 = phase;
            int q = DefaultTypeTransformation.intUnbox(callSiteArray[42].callGetProperty(DefaultTypeTransformation.booleanUnbox((Object)compilePhase3) ? compilePhase3 : callSiteArray[43].callGetProperty(CompilePhase.class)));
            while (p <= q) {
                callSiteArray[44].call(this.compilationUnit, astTester, p);
                int cfr_ignored_0 = p + 1;
            }
        }
    }

    public /* synthetic */ Object this$dist$invoke$1(String name, Object args) {
        CallSite[] callSiteArray = ASTTestTransformation.$getCallSiteArray();
        if (!(args instanceof Object[])) {
            return ScriptBytecodeAdapter.invokeMethodOnCurrentN(ASTTestTransformation.class, this, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{args});
        }
        if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            if (ScriptBytecodeAdapter.compareEqual(callSiteArray[45].callGetProperty((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 1)) {
                return ScriptBytecodeAdapter.invokeMethodOnCurrentN(ASTTestTransformation.class, this, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{callSiteArray[46].call((Object)((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 0)});
            }
        } else if (ScriptBytecodeAdapter.compareEqual(callSiteArray[47].callGetProperty((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 1)) {
            return ScriptBytecodeAdapter.invokeMethodOnCurrentN(ASTTestTransformation.class, this, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{BytecodeInterface8.objectArrayGet((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class), 0)});
        }
        return ScriptBytecodeAdapter.invokeMethodOnCurrentN(ASTTestTransformation.class, this, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), ScriptBytecodeAdapter.despreadList(new Object[0], new Object[]{args}, new int[]{0}));
    }

    public /* synthetic */ void this$dist$set$1(String name, Object value) {
        CallSite[] callSiteArray = ASTTestTransformation.$getCallSiteArray();
        Object object = value;
        ScriptBytecodeAdapter.setGroovyObjectProperty(object, ASTTestTransformation.class, this, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})));
    }

    public /* synthetic */ Object this$dist$get$1(String name) {
        CallSite[] callSiteArray = ASTTestTransformation.$getCallSiteArray();
        return ScriptBytecodeAdapter.getGroovyObjectProperty(ASTTestTransformation.class, this, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})));
    }

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != ASTTestTransformation.class) {
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
    public CompilationUnit getCompilationUnit() {
        return this.compilationUnit;
    }

    @Override
    @Generated
    public void setCompilationUnit(CompilationUnit compilationUnit) {
        this.compilationUnit = compilationUnit;
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
        stringArray[0] = "getAt";
        stringArray[1] = "getMember";
        stringArray[2] = "valueOf";
        stringArray[3] = "text";
        stringArray[4] = "valueOf";
        stringArray[5] = "propertyAsString";
        stringArray[6] = "setMember";
        stringArray[7] = "propX";
        stringArray[8] = "classX";
        stringArray[9] = "make";
        stringArray[10] = "toString";
        stringArray[11] = "phaseNumber";
        stringArray[12] = "phase";
        stringArray[13] = "<$constructor$>";
        stringArray[14] = "plus";
        stringArray[15] = "fromPhaseNumber";
        stringArray[16] = "phase";
        stringArray[17] = "getMember";
        stringArray[18] = "<$constructor$>";
        stringArray[19] = "getNodeMetaData";
        stringArray[20] = "<$constructor$>";
        stringArray[21] = "getNodeMetaData";
        stringArray[22] = "<$constructor$>";
        stringArray[23] = "setNodeMetaData";
        stringArray[24] = "setMember";
        stringArray[25] = "<$constructor$>";
        stringArray[26] = "EMPTY_ARRAY";
        stringArray[27] = "INSTANCE";
        stringArray[28] = "variableScope";
        stringArray[29] = "<$constructor$>";
        stringArray[30] = "getAt";
        stringArray[31] = "getNodeMetaData";
        stringArray[32] = "<$constructor$>";
        stringArray[33] = "getNodeMetaData";
        stringArray[34] = "phaseNumber";
        stringArray[35] = "SEMANTIC_ANALYSIS";
        stringArray[36] = "phaseNumber";
        stringArray[37] = "FINALIZATION";
        stringArray[38] = "addNewPhaseOperation";
        stringArray[39] = "plus";
        stringArray[40] = "phaseNumber";
        stringArray[41] = "SEMANTIC_ANALYSIS";
        stringArray[42] = "phaseNumber";
        stringArray[43] = "FINALIZATION";
        stringArray[44] = "addNewPhaseOperation";
        stringArray[45] = "length";
        stringArray[46] = "getAt";
        stringArray[47] = "length";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[48];
        ASTTestTransformation.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(ASTTestTransformation.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = ASTTestTransformation.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }

    private class ASTTester
    implements CompilationUnit.ISourceUnitOperation,
    GroovyObject {
        private ASTNode astNode;
        private SourceUnit sourceUnit;
        private ClosureExpression testClosure;
        private final Binding binding;
        final /* synthetic */ ASTTestTransformation this$0;
        private static /* synthetic */ ClassInfo $staticClassInfo;
        public static transient /* synthetic */ boolean __$stMC;
        private transient /* synthetic */ MetaClass metaClass;
        private static /* synthetic */ SoftReference $callSiteArray;

        @Generated
        public ASTTester(ASTTestTransformation $p$) {
            MetaClass metaClass;
            ASTTestTransformation aSTTestTransformation;
            CallSite[] callSiteArray = ASTTester.$getCallSiteArray();
            this.this$0 = aSTTestTransformation = $p$;
            Object object = callSiteArray[0].callConstructor(Binding.class, callSiteArray[1].call((Object)ScriptBytecodeAdapter.createMap(new Object[0]), new _closure1(this, this)));
            this.binding = (Binding)ScriptBytecodeAdapter.castToType(object, Binding.class);
            this.metaClass = metaClass = this.$getStaticMetaClass();
        }

        @Override
        public void call(SourceUnit source) {
            CallSite[] callSiteArray = ASTTester.$getCallSiteArray();
            if (!BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                if (ScriptBytecodeAdapter.compareEqual(source, this.sourceUnit)) {
                    callSiteArray[2].callCurrent(this);
                }
            } else if (ScriptBytecodeAdapter.compareEqual(source, this.sourceUnit)) {
                this.test();
            }
        }

        private void test() {
            Object object;
            CallSite[] callSiteArray = ASTTester.$getCallSiteArray();
            Object sb = callSiteArray[3].callConstructor(StringBuilder.class);
            if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                int i = DefaultTypeTransformation.intUnbox(callSiteArray[4].callGetProperty(this.testClosure));
                int n = DefaultTypeTransformation.intUnbox(callSiteArray[5].callGetProperty(this.testClosure));
                while (i <= n) {
                    callSiteArray[6].call(callSiteArray[7].call(sb, callSiteArray[8].call(callSiteArray[9].callGetProperty(this.sourceUnit), i, callSiteArray[10].callConstructor(Janitor.class))), "\n");
                    i = DefaultTypeTransformation.intUnbox(callSiteArray[11].call((Object)i, 1));
                }
            } else {
                int i = DefaultTypeTransformation.intUnbox(callSiteArray[12].callGetProperty(this.testClosure));
                int n = DefaultTypeTransformation.intUnbox(callSiteArray[13].callGetProperty(this.testClosure));
                while (i <= n) {
                    callSiteArray[14].call(callSiteArray[15].call(sb, callSiteArray[16].call(callSiteArray[17].callGetProperty(this.sourceUnit), i, callSiteArray[18].callConstructor(Janitor.class))), "\n");
                    int cfr_ignored_0 = i + 1;
                }
            }
            sb = object = callSiteArray[19].call(sb, ScriptBytecodeAdapter.createRange(callSiteArray[20].callGetProperty(this.testClosure), callSiteArray[21].call(sb), false, true));
            String testSource = ShortTypeHandling.castToString(callSiteArray[22].call(sb, ScriptBytecodeAdapter.createRange(0, callSiteArray[23].call(sb, "}"), false, true)));
            ASTNode aSTNode = this.astNode;
            callSiteArray[24].call(this.binding, "node", aSTNode);
            SourceUnit sourceUnit = this.sourceUnit;
            callSiteArray[25].call(this.binding, "sourceUnit", sourceUnit);
            Object object2 = callSiteArray[26].callGroovyObjectGetProperty(this);
            callSiteArray[27].call(this.binding, "compilationUnit", object2);
            Object object3 = callSiteArray[28].callStatic(CompilePhase.class, callSiteArray[29].callGetProperty(callSiteArray[30].callGroovyObjectGetProperty(this)));
            callSiteArray[31].call(this.binding, "compilePhase", object3);
            Object object4 = callSiteArray[32].call(callSiteArray[33].callConstructor(MethodClosure.class, LabelFinder.class, "lookup"), this.astNode);
            callSiteArray[34].call(this.binding, "lookup", object4);
            Reference<Object> customizer = new Reference<Object>(callSiteArray[35].callConstructor(ImportCustomizer.class));
            public final class _test_closure2
            extends Closure
            implements GeneratedClosure {
                private /* synthetic */ Reference customizer;
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;
                private static /* synthetic */ SoftReference $callSiteArray;

                public _test_closure2(Object _outerInstance, Object _thisObject, Reference customizer) {
                    Reference reference;
                    CallSite[] callSiteArray = _test_closure2.$getCallSiteArray();
                    super(_outerInstance, _thisObject);
                    this.customizer = reference = customizer;
                }

                public Object doCall(Object it) {
                    CallSite[] callSiteArray = _test_closure2.$getCallSiteArray();
                    return callSiteArray[0].call(this.customizer.get(), callSiteArray[1].callGetProperty(it), callSiteArray[2].callGetProperty(callSiteArray[3].callGetProperty(it)));
                }

                @Generated
                public Object getCustomizer() {
                    CallSite[] callSiteArray = _test_closure2.$getCallSiteArray();
                    return this.customizer.get();
                }

                @Generated
                public Object doCall() {
                    CallSite[] callSiteArray = _test_closure2.$getCallSiteArray();
                    return this.doCall(null);
                }

                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                    if (this.getClass() != _test_closure2.class) {
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
                    stringArray[0] = "addImport";
                    stringArray[1] = "alias";
                    stringArray[2] = "name";
                    stringArray[3] = "type";
                }

                private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                    String[] stringArray = new String[4];
                    _test_closure2.$createCallSiteArray_1(stringArray);
                    return new CallSiteArray(_test_closure2.class, stringArray);
                }

                private static /* synthetic */ CallSite[] $getCallSiteArray() {
                    CallSiteArray callSiteArray;
                    if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                        callSiteArray = _test_closure2.$createCallSiteArray();
                        $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                    }
                    return callSiteArray.array;
                }
            }
            callSiteArray[36].call(callSiteArray[37].callGetProperty(callSiteArray[38].callGetProperty(this.sourceUnit)), new _test_closure2(this, this, customizer));
            public final class _test_closure3
            extends Closure
            implements GeneratedClosure {
                private /* synthetic */ Reference customizer;
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;
                private static /* synthetic */ SoftReference $callSiteArray;

                public _test_closure3(Object _outerInstance, Object _thisObject, Reference customizer) {
                    Reference reference;
                    CallSite[] callSiteArray = _test_closure3.$getCallSiteArray();
                    super(_outerInstance, _thisObject);
                    this.customizer = reference = customizer;
                }

                public Object doCall(Object it) {
                    CallSite[] callSiteArray = _test_closure3.$getCallSiteArray();
                    return callSiteArray[0].call(this.customizer.get(), callSiteArray[1].callGetProperty(it));
                }

                @Generated
                public Object getCustomizer() {
                    CallSite[] callSiteArray = _test_closure3.$getCallSiteArray();
                    return this.customizer.get();
                }

                @Generated
                public Object doCall() {
                    CallSite[] callSiteArray = _test_closure3.$getCallSiteArray();
                    return this.doCall(null);
                }

                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                    if (this.getClass() != _test_closure3.class) {
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
                    stringArray[0] = "addStarImports";
                    stringArray[1] = "packageName";
                }

                private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                    String[] stringArray = new String[2];
                    _test_closure3.$createCallSiteArray_1(stringArray);
                    return new CallSiteArray(_test_closure3.class, stringArray);
                }

                private static /* synthetic */ CallSite[] $getCallSiteArray() {
                    CallSiteArray callSiteArray;
                    if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                        callSiteArray = _test_closure3.$createCallSiteArray();
                        $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                    }
                    return callSiteArray.array;
                }
            }
            callSiteArray[39].call(callSiteArray[40].callGetProperty(callSiteArray[41].callGetProperty(this.sourceUnit)), new _test_closure3(this, this, customizer));
            public final class _test_closure4
            extends Closure
            implements GeneratedClosure {
                private /* synthetic */ Reference customizer;
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;
                private static /* synthetic */ SoftReference $callSiteArray;

                public _test_closure4(Object _outerInstance, Object _thisObject, Reference customizer) {
                    Reference reference;
                    CallSite[] callSiteArray = _test_closure4.$getCallSiteArray();
                    super(_outerInstance, _thisObject);
                    this.customizer = reference = customizer;
                }

                public Object doCall(Object it) {
                    CallSite[] callSiteArray = _test_closure4.$getCallSiteArray();
                    return callSiteArray[0].call(this.customizer.get(), callSiteArray[1].callGetProperty(callSiteArray[2].callGetProperty(it)), callSiteArray[3].callGetProperty(callSiteArray[4].callGetProperty(callSiteArray[5].callGetProperty(it))), callSiteArray[6].callGetProperty(callSiteArray[7].callGetProperty(it)));
                }

                @Generated
                public Object getCustomizer() {
                    CallSite[] callSiteArray = _test_closure4.$getCallSiteArray();
                    return this.customizer.get();
                }

                @Generated
                public Object doCall() {
                    CallSite[] callSiteArray = _test_closure4.$getCallSiteArray();
                    return this.doCall(null);
                }

                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                    if (this.getClass() != _test_closure4.class) {
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
                    stringArray[0] = "addStaticImport";
                    stringArray[1] = "alias";
                    stringArray[2] = "value";
                    stringArray[3] = "name";
                    stringArray[4] = "type";
                    stringArray[5] = "value";
                    stringArray[6] = "fieldName";
                    stringArray[7] = "value";
                }

                private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                    String[] stringArray = new String[8];
                    _test_closure4.$createCallSiteArray_1(stringArray);
                    return new CallSiteArray(_test_closure4.class, stringArray);
                }

                private static /* synthetic */ CallSite[] $getCallSiteArray() {
                    CallSiteArray callSiteArray;
                    if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                        callSiteArray = _test_closure4.$createCallSiteArray();
                        $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                    }
                    return callSiteArray.array;
                }
            }
            callSiteArray[42].call(callSiteArray[43].callGetProperty(callSiteArray[44].callGetProperty(this.sourceUnit)), new _test_closure4(this, this, customizer));
            public final class _test_closure5
            extends Closure
            implements GeneratedClosure {
                private /* synthetic */ Reference customizer;
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;
                private static /* synthetic */ SoftReference $callSiteArray;

                public _test_closure5(Object _outerInstance, Object _thisObject, Reference customizer) {
                    Reference reference;
                    CallSite[] callSiteArray = _test_closure5.$getCallSiteArray();
                    super(_outerInstance, _thisObject);
                    this.customizer = reference = customizer;
                }

                public Object doCall(Object it) {
                    CallSite[] callSiteArray = _test_closure5.$getCallSiteArray();
                    return callSiteArray[0].call(this.customizer.get(), callSiteArray[1].callGetProperty(callSiteArray[2].callGetProperty(it)));
                }

                @Generated
                public Object getCustomizer() {
                    CallSite[] callSiteArray = _test_closure5.$getCallSiteArray();
                    return this.customizer.get();
                }

                @Generated
                public Object doCall() {
                    CallSite[] callSiteArray = _test_closure5.$getCallSiteArray();
                    return this.doCall(null);
                }

                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                    if (this.getClass() != _test_closure5.class) {
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
                    stringArray[0] = "addStaticStars";
                    stringArray[1] = "className";
                    stringArray[2] = "value";
                }

                private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                    String[] stringArray = new String[3];
                    _test_closure5.$createCallSiteArray_1(stringArray);
                    return new CallSiteArray(_test_closure5.class, stringArray);
                }

                private static /* synthetic */ CallSite[] $getCallSiteArray() {
                    CallSiteArray callSiteArray;
                    if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                        callSiteArray = _test_closure5.$createCallSiteArray();
                        $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                    }
                    return callSiteArray.array;
                }
            }
            callSiteArray[45].call(callSiteArray[46].callGetProperty(callSiteArray[47].callGetProperty(this.sourceUnit)), new _test_closure5(this, this, customizer));
            Object config = callSiteArray[48].callConstructor(CompilerConfiguration.class);
            callSiteArray[49].call(config, customizer.get());
            Object loader = callSiteArray[50].callGetProperty(callSiteArray[51].callGroovyObjectGetProperty(this));
            callSiteArray[52].call(callSiteArray[53].callConstructor(GroovyShell.class, loader, this.binding, config), testSource);
        }

        public /* synthetic */ Object methodMissing(String name, Object args) {
            CallSite[] callSiteArray = ASTTester.$getCallSiteArray();
            return this.this$0.this$dist$invoke$1(name, args);
        }

        public static /* synthetic */ Object $static_methodMissing(String name, Object args) {
            CallSite[] callSiteArray = ASTTester.$getCallSiteArray();
            if (!(args instanceof Object[])) {
                return ScriptBytecodeAdapter.invokeMethodN(ASTTester.class, ASTTestTransformation.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{args});
            }
            if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                if (ScriptBytecodeAdapter.compareEqual(callSiteArray[54].callGetProperty((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 1)) {
                    return ScriptBytecodeAdapter.invokeMethodN(ASTTester.class, ASTTestTransformation.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{callSiteArray[55].call((Object)((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 0)});
                }
            } else if (ScriptBytecodeAdapter.compareEqual(callSiteArray[56].callGetProperty((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 1)) {
                return ScriptBytecodeAdapter.invokeMethodN(ASTTester.class, ASTTestTransformation.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{BytecodeInterface8.objectArrayGet((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class), 0)});
            }
            return ScriptBytecodeAdapter.invokeMethodN(ASTTester.class, ASTTestTransformation.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), ScriptBytecodeAdapter.despreadList(new Object[0], new Object[]{args}, new int[]{0}));
        }

        public /* synthetic */ void propertyMissing(String name, Object value) {
            CallSite[] callSiteArray = ASTTester.$getCallSiteArray();
            this.this$0.this$dist$set$1(name, value);
        }

        public static /* synthetic */ void $static_propertyMissing(String name, Object value) {
            CallSite[] callSiteArray = ASTTester.$getCallSiteArray();
            Object object = value;
            ScriptBytecodeAdapter.setProperty(object, null, ASTTestTransformation.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

        public /* synthetic */ Object propertyMissing(String name) {
            CallSite[] callSiteArray = ASTTester.$getCallSiteArray();
            return this.this$0.this$dist$get$1(name);
        }

        public static /* synthetic */ Object $static_propertyMissing(String name) {
            CallSite[] callSiteArray = ASTTester.$getCallSiteArray();
            return ScriptBytecodeAdapter.getProperty(ASTTester.class, ASTTestTransformation.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

        protected /* synthetic */ MetaClass $getStaticMetaClass() {
            if (this.getClass() != ASTTester.class) {
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

        public /* synthetic */ MethodHandles.Lookup $getLookup() {
            return MethodHandles.lookup();
        }

        @Generated
        public ASTNode getAstNode() {
            return this.astNode;
        }

        @Generated
        public void setAstNode(ASTNode aSTNode) {
            this.astNode = aSTNode;
        }

        @Generated
        public SourceUnit getSourceUnit() {
            return this.sourceUnit;
        }

        @Generated
        public void setSourceUnit(SourceUnit sourceUnit) {
            this.sourceUnit = sourceUnit;
        }

        @Generated
        public ClosureExpression getTestClosure() {
            return this.testClosure;
        }

        @Generated
        public void setTestClosure(ClosureExpression closureExpression) {
            this.testClosure = closureExpression;
        }

        private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
            stringArray[0] = "<$constructor$>";
            stringArray[1] = "withDefault";
            stringArray[2] = "test";
            stringArray[3] = "<$constructor$>";
            stringArray[4] = "lineNumber";
            stringArray[5] = "lastLineNumber";
            stringArray[6] = "append";
            stringArray[7] = "append";
            stringArray[8] = "getLine";
            stringArray[9] = "source";
            stringArray[10] = "<$constructor$>";
            stringArray[11] = "plus";
            stringArray[12] = "lineNumber";
            stringArray[13] = "lastLineNumber";
            stringArray[14] = "append";
            stringArray[15] = "append";
            stringArray[16] = "getLine";
            stringArray[17] = "source";
            stringArray[18] = "<$constructor$>";
            stringArray[19] = "getAt";
            stringArray[20] = "columnNumber";
            stringArray[21] = "length";
            stringArray[22] = "getAt";
            stringArray[23] = "lastIndexOf";
            stringArray[24] = "putAt";
            stringArray[25] = "putAt";
            stringArray[26] = "compilationUnit";
            stringArray[27] = "putAt";
            stringArray[28] = "fromPhaseNumber";
            stringArray[29] = "phase";
            stringArray[30] = "compilationUnit";
            stringArray[31] = "putAt";
            stringArray[32] = "curry";
            stringArray[33] = "<$constructor$>";
            stringArray[34] = "putAt";
            stringArray[35] = "<$constructor$>";
            stringArray[36] = "each";
            stringArray[37] = "imports";
            stringArray[38] = "AST";
            stringArray[39] = "each";
            stringArray[40] = "starImports";
            stringArray[41] = "AST";
            stringArray[42] = "each";
            stringArray[43] = "staticImports";
            stringArray[44] = "AST";
            stringArray[45] = "each";
            stringArray[46] = "staticStarImports";
            stringArray[47] = "AST";
            stringArray[48] = "<$constructor$>";
            stringArray[49] = "addCompilationCustomizers";
            stringArray[50] = "transformLoader";
            stringArray[51] = "compilationUnit";
            stringArray[52] = "evaluate";
            stringArray[53] = "<$constructor$>";
            stringArray[54] = "length";
            stringArray[55] = "getAt";
            stringArray[56] = "length";
        }

        private static /* synthetic */ CallSiteArray $createCallSiteArray() {
            String[] stringArray = new String[57];
            ASTTester.$createCallSiteArray_1(stringArray);
            return new CallSiteArray(ASTTester.class, stringArray);
        }

        private static /* synthetic */ CallSite[] $getCallSiteArray() {
            CallSiteArray callSiteArray;
            if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                callSiteArray = ASTTester.$createCallSiteArray();
                $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
            }
            return callSiteArray.array;
        }

        public final class _closure1
        extends Closure
        implements GeneratedClosure {
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _closure1(Object _outerInstance, Object _thisObject) {
                CallSite[] callSiteArray = _closure1.$getCallSiteArray();
                super(_outerInstance, _thisObject);
            }

            public Object doCall(Object it) {
                CallSite[] callSiteArray = _closure1.$getCallSiteArray();
                return null;
            }

            @Generated
            public Object doCall() {
                CallSite[] callSiteArray = _closure1.$getCallSiteArray();
                return this.doCall(null);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (this.getClass() != _closure1.class) {
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

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[]{};
                return new CallSiteArray(_closure1.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _closure1.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
    }

    public static class LabelFinder
    extends ClassCodeVisitorSupport
    implements GroovyObject {
        private final String label;
        private final SourceUnit unit;
        private final List<Statement> targets;
        private static /* synthetic */ ClassInfo $staticClassInfo;
        public static transient /* synthetic */ boolean __$stMC;
        private transient /* synthetic */ MetaClass metaClass;
        private static /* synthetic */ SoftReference $callSiteArray;

        public LabelFinder(String label, SourceUnit unit) {
            SourceUnit sourceUnit;
            String string;
            MetaClass metaClass;
            LinkedList linkedList;
            CallSite[] callSiteArray = LabelFinder.$getCallSiteArray();
            this.targets = linkedList = (LinkedList)ScriptBytecodeAdapter.asType(ScriptBytecodeAdapter.createList(new Object[0]), LinkedList.class);
            this.metaClass = metaClass = this.$getStaticMetaClass();
            this.label = string = label;
            this.unit = sourceUnit = unit;
        }

        public static List<Statement> lookup(MethodNode node, String label) {
            CallSite[] callSiteArray = LabelFinder.$getCallSiteArray();
            LabelFinder finder = (LabelFinder)ScriptBytecodeAdapter.castToType(callSiteArray[0].callConstructor(LabelFinder.class, label, null), LabelFinder.class);
            callSiteArray[1].call(callSiteArray[2].callGetProperty(node), finder);
            return (List)ScriptBytecodeAdapter.castToType(callSiteArray[3].callGroovyObjectGetProperty(finder), List.class);
        }

        public static List<Statement> lookup(ClassNode node, String label) {
            CallSite[] callSiteArray = LabelFinder.$getCallSiteArray();
            LabelFinder finder = (LabelFinder)ScriptBytecodeAdapter.castToType(callSiteArray[4].callConstructor(LabelFinder.class, label, null), LabelFinder.class);
            ScriptBytecodeAdapter.invokeMethodNSpreadSafe(LabelFinder.class, ScriptBytecodeAdapter.getPropertySpreadSafe(LabelFinder.class, callSiteArray[5].callGetProperty(node), "code"), "visit", new Object[]{finder});
            ScriptBytecodeAdapter.invokeMethodNSpreadSafe(LabelFinder.class, ScriptBytecodeAdapter.getPropertySpreadSafe(LabelFinder.class, callSiteArray[6].callGetProperty(node), "code"), "visit", new Object[]{finder});
            return (List)ScriptBytecodeAdapter.castToType(callSiteArray[7].callGroovyObjectGetProperty(finder), List.class);
        }

        @Override
        protected SourceUnit getSourceUnit() {
            CallSite[] callSiteArray = LabelFinder.$getCallSiteArray();
            return this.unit;
        }

        @Override
        protected void visitStatement(Statement statement) {
            CallSite[] callSiteArray = LabelFinder.$getCallSiteArray();
            ScriptBytecodeAdapter.invokeMethodOnSuperN(LabelFinder.class, this, "visitStatement", new Object[]{statement});
            if (ScriptBytecodeAdapter.isCase(this.label, callSiteArray[8].callGetProperty(statement))) {
                callSiteArray[9].call(this.targets, statement);
            }
        }

        public List<Statement> getTargets() {
            CallSite[] callSiteArray = LabelFinder.$getCallSiteArray();
            return (List)ScriptBytecodeAdapter.castToType(callSiteArray[10].call(Collections.class, this.targets), List.class);
        }

        public /* synthetic */ Object methodMissing(String name, Object args) {
            CallSite[] callSiteArray = LabelFinder.$getCallSiteArray();
            if (!(args instanceof Object[])) {
                return ScriptBytecodeAdapter.invokeMethodN(LabelFinder.class, ASTTestTransformation.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{args});
            }
            if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                if (ScriptBytecodeAdapter.compareEqual(callSiteArray[11].callGetProperty((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 1)) {
                    return ScriptBytecodeAdapter.invokeMethodN(LabelFinder.class, ASTTestTransformation.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{callSiteArray[12].call((Object)((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 0)});
                }
            } else if (ScriptBytecodeAdapter.compareEqual(callSiteArray[13].callGetProperty((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 1)) {
                return ScriptBytecodeAdapter.invokeMethodN(LabelFinder.class, ASTTestTransformation.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{BytecodeInterface8.objectArrayGet((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class), 0)});
            }
            return ScriptBytecodeAdapter.invokeMethodN(LabelFinder.class, ASTTestTransformation.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), ScriptBytecodeAdapter.despreadList(new Object[0], new Object[]{args}, new int[]{0}));
        }

        public static /* synthetic */ Object $static_methodMissing(String name, Object args) {
            CallSite[] callSiteArray = LabelFinder.$getCallSiteArray();
            if (!(args instanceof Object[])) {
                return ScriptBytecodeAdapter.invokeMethodN(LabelFinder.class, ASTTestTransformation.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{args});
            }
            if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                if (ScriptBytecodeAdapter.compareEqual(callSiteArray[14].callGetProperty((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 1)) {
                    return ScriptBytecodeAdapter.invokeMethodN(LabelFinder.class, ASTTestTransformation.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{callSiteArray[15].call((Object)((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 0)});
                }
            } else if (ScriptBytecodeAdapter.compareEqual(callSiteArray[16].callGetProperty((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 1)) {
                return ScriptBytecodeAdapter.invokeMethodN(LabelFinder.class, ASTTestTransformation.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{BytecodeInterface8.objectArrayGet((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class), 0)});
            }
            return ScriptBytecodeAdapter.invokeMethodN(LabelFinder.class, ASTTestTransformation.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), ScriptBytecodeAdapter.despreadList(new Object[0], new Object[]{args}, new int[]{0}));
        }

        public /* synthetic */ void propertyMissing(String name, Object value) {
            CallSite[] callSiteArray = LabelFinder.$getCallSiteArray();
            Object object = value;
            ScriptBytecodeAdapter.setProperty(object, null, ASTTestTransformation.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

        public static /* synthetic */ void $static_propertyMissing(String name, Object value) {
            CallSite[] callSiteArray = LabelFinder.$getCallSiteArray();
            Object object = value;
            ScriptBytecodeAdapter.setProperty(object, null, ASTTestTransformation.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

        public /* synthetic */ Object propertyMissing(String name) {
            CallSite[] callSiteArray = LabelFinder.$getCallSiteArray();
            return ScriptBytecodeAdapter.getProperty(LabelFinder.class, ASTTestTransformation.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

        public static /* synthetic */ Object $static_propertyMissing(String name) {
            CallSite[] callSiteArray = LabelFinder.$getCallSiteArray();
            return ScriptBytecodeAdapter.getProperty(LabelFinder.class, ASTTestTransformation.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

        protected /* synthetic */ MetaClass $getStaticMetaClass() {
            if (this.getClass() != LabelFinder.class) {
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

        public /* synthetic */ void super$3$visitStatement(Statement statement) {
            super.visitStatement(statement);
        }

        private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
            stringArray[0] = "<$constructor$>";
            stringArray[1] = "visit";
            stringArray[2] = "code";
            stringArray[3] = "targets";
            stringArray[4] = "<$constructor$>";
            stringArray[5] = "methods";
            stringArray[6] = "declaredConstructors";
            stringArray[7] = "targets";
            stringArray[8] = "statementLabels";
            stringArray[9] = "leftShift";
            stringArray[10] = "unmodifiableList";
            stringArray[11] = "length";
            stringArray[12] = "getAt";
            stringArray[13] = "length";
            stringArray[14] = "length";
            stringArray[15] = "getAt";
            stringArray[16] = "length";
        }

        private static /* synthetic */ CallSiteArray $createCallSiteArray() {
            String[] stringArray = new String[17];
            LabelFinder.$createCallSiteArray_1(stringArray);
            return new CallSiteArray(LabelFinder.class, stringArray);
        }

        private static /* synthetic */ CallSite[] $getCallSiteArray() {
            CallSiteArray callSiteArray;
            if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                callSiteArray = LabelFinder.$createCallSiteArray();
                $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
            }
            return callSiteArray.array;
        }
    }
}

