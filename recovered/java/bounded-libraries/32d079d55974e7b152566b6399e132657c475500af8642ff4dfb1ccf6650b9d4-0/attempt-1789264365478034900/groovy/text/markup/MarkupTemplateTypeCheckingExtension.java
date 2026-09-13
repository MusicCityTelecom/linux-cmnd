/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.Closure
 *  groovy.lang.GroovyClassLoader
 *  groovy.lang.GroovyObject
 *  groovy.lang.MetaClass
 *  groovy.lang.Reference
 *  groovy.transform.Generated
 *  groovy.transform.Internal
 *  org.codehaus.groovy.ast.ASTNode
 *  org.codehaus.groovy.ast.ClassCodeExpressionTransformer
 *  org.codehaus.groovy.ast.ClassHelper
 *  org.codehaus.groovy.ast.ClassNode
 *  org.codehaus.groovy.ast.MethodNode
 *  org.codehaus.groovy.ast.ModuleNode
 *  org.codehaus.groovy.ast.Parameter
 *  org.codehaus.groovy.ast.expr.ArgumentListExpression
 *  org.codehaus.groovy.ast.expr.ArrayExpression
 *  org.codehaus.groovy.ast.expr.BinaryExpression
 *  org.codehaus.groovy.ast.expr.ClosureExpression
 *  org.codehaus.groovy.ast.expr.ConstantExpression
 *  org.codehaus.groovy.ast.expr.DeclarationExpression
 *  org.codehaus.groovy.ast.expr.Expression
 *  org.codehaus.groovy.ast.expr.MethodCallExpression
 *  org.codehaus.groovy.ast.expr.TupleExpression
 *  org.codehaus.groovy.ast.expr.VariableExpression
 *  org.codehaus.groovy.ast.stmt.EmptyStatement
 *  org.codehaus.groovy.ast.stmt.ExpressionStatement
 *  org.codehaus.groovy.ast.stmt.Statement
 *  org.codehaus.groovy.control.CompilationUnit
 *  org.codehaus.groovy.control.CompilerConfiguration
 *  org.codehaus.groovy.control.ErrorCollector
 *  org.codehaus.groovy.control.ParserPlugin
 *  org.codehaus.groovy.control.SourceUnit
 *  org.codehaus.groovy.reflection.ClassInfo
 *  org.codehaus.groovy.runtime.ArrayUtil
 *  org.codehaus.groovy.runtime.BytecodeInterface8
 *  org.codehaus.groovy.runtime.DefaultGroovyMethods
 *  org.codehaus.groovy.runtime.GStringImpl
 *  org.codehaus.groovy.runtime.GeneratedClosure
 *  org.codehaus.groovy.runtime.ScriptBytecodeAdapter
 *  org.codehaus.groovy.runtime.callsite.CallSite
 *  org.codehaus.groovy.runtime.callsite.CallSiteArray
 *  org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation
 *  org.codehaus.groovy.runtime.typehandling.ShortTypeHandling
 *  org.codehaus.groovy.syntax.SyntaxException
 *  org.codehaus.groovy.syntax.Types
 *  org.codehaus.groovy.transform.stc.GroovyTypeCheckingExtensionSupport$TypeCheckingDSL
 *  org.codehaus.groovy.transform.stc.StaticTypeCheckingSupport
 *  org.codehaus.groovy.transform.stc.TypeCheckingContext
 */
package groovy.text.markup;

import groovy.lang.Closure;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import groovy.lang.Reference;
import groovy.text.markup.BaseTemplate;
import groovy.text.markup.MarkupTemplateEngine;
import groovy.transform.Generated;
import groovy.transform.Internal;
import java.beans.Transient;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.ClassCodeExpressionTransformer;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.ModuleNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.ArrayExpression;
import org.codehaus.groovy.ast.expr.BinaryExpression;
import org.codehaus.groovy.ast.expr.ClosureExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.DeclarationExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.TupleExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.EmptyStatement;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.control.CompilationUnit;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.ErrorCollector;
import org.codehaus.groovy.control.ParserPlugin;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.ArrayUtil;
import org.codehaus.groovy.runtime.BytecodeInterface8;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.codehaus.groovy.runtime.GStringImpl;
import org.codehaus.groovy.runtime.GeneratedClosure;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;
import org.codehaus.groovy.runtime.typehandling.ShortTypeHandling;
import org.codehaus.groovy.syntax.SyntaxException;
import org.codehaus.groovy.syntax.Types;
import org.codehaus.groovy.transform.stc.GroovyTypeCheckingExtensionSupport;
import org.codehaus.groovy.transform.stc.StaticTypeCheckingSupport;
import org.codehaus.groovy.transform.stc.TypeCheckingContext;

public class MarkupTemplateTypeCheckingExtension
extends GroovyTypeCheckingExtensionSupport.TypeCheckingDSL {
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private static /* synthetic */ ClassInfo $staticClassInfo$;
    private static /* synthetic */ SoftReference $callSiteArray;

    @Generated
    public MarkupTemplateTypeCheckingExtension() {
        CallSite[] callSiteArray = MarkupTemplateTypeCheckingExtension.$getCallSiteArray();
    }

    public Object run() {
        CallSite[] callSiteArray = MarkupTemplateTypeCheckingExtension.$getCallSiteArray();
        Reference modelTypesClassNodes = new Reference(null);
        public final class _run_closure1
        extends Closure
        implements GeneratedClosure {
            private /* synthetic */ Reference modelTypesClassNodes;
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;
            private static /* synthetic */ Class $class$groovy$text$markup$MarkupTemplateEngine$TemplateGroovyClassLoader;

            public _run_closure1(Object _outerInstance, Object _thisObject, Reference modelTypesClassNodes) {
                Reference reference;
                CallSite[] callSiteArray = _run_closure1.$getCallSiteArray();
                super(_outerInstance, _thisObject);
                this.modelTypesClassNodes = reference = modelTypesClassNodes;
            }

            public Object doCall(Object classNode) {
                CallSite[] callSiteArray = _run_closure1.$getCallSiteArray();
                Object modelTypes = callSiteArray[0].call(callSiteArray[1].callGetProperty((Object)_run_closure1.$get$$class$groovy$text$markup$MarkupTemplateEngine$TemplateGroovyClassLoader()));
                if (ScriptBytecodeAdapter.compareNotEqual((Object)modelTypes, null)) {
                    Map map = ScriptBytecodeAdapter.createMap((Object[])new Object[0]);
                    this.modelTypesClassNodes.set((Object)map);
                    public final class _closure7
                    extends Closure
                    implements GeneratedClosure {
                        private /* synthetic */ Reference modelTypesClassNodes;
                        private static /* synthetic */ ClassInfo $staticClassInfo;
                        public static transient /* synthetic */ boolean __$stMC;
                        private static /* synthetic */ SoftReference $callSiteArray;

                        public _closure7(Object _outerInstance, Object _thisObject, Reference modelTypesClassNodes) {
                            Reference reference;
                            CallSite[] callSiteArray = _closure7.$getCallSiteArray();
                            super(_outerInstance, _thisObject);
                            this.modelTypesClassNodes = reference = modelTypesClassNodes;
                        }

                        public Object doCall(Object k, Object v) {
                            CallSite[] callSiteArray = _closure7.$getCallSiteArray();
                            Object object = callSiteArray[0].callCurrent((GroovyObject)this, v, callSiteArray[1].callGroovyObjectGetProperty((Object)this));
                            callSiteArray[2].call(this.modelTypesClassNodes.get(), k, object);
                            return object;
                        }

                        @Generated
                        public Object call(Object k, Object v) {
                            CallSite[] callSiteArray = _closure7.$getCallSiteArray();
                            return callSiteArray[3].callCurrent((GroovyObject)this, k, v);
                        }

                        @Generated
                        public Object getModelTypesClassNodes() {
                            CallSite[] callSiteArray = _closure7.$getCallSiteArray();
                            return this.modelTypesClassNodes.get();
                        }

                        protected /* synthetic */ MetaClass $getStaticMetaClass() {
                            if (((Object)((Object)this)).getClass() != _closure7.class) {
                                return ScriptBytecodeAdapter.initMetaClass((Object)((Object)this));
                            }
                            ClassInfo classInfo = $staticClassInfo;
                            if (classInfo == null) {
                                $staticClassInfo = classInfo = ClassInfo.getClassInfo(((Object)((Object)this)).getClass());
                            }
                            return classInfo.getMetaClass();
                        }

                        public /* synthetic */ MethodHandles.Lookup $getLookup() {
                            return MethodHandles.lookup();
                        }

                        private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
                            stringArray[0] = "buildNodeFromString";
                            stringArray[1] = "context";
                            stringArray[2] = "putAt";
                            stringArray[3] = "doCall";
                        }

                        private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                            String[] stringArray = new String[4];
                            _closure7.$createCallSiteArray_1(stringArray);
                            return new CallSiteArray(_closure7.class, stringArray);
                        }

                        private static /* synthetic */ CallSite[] $getCallSiteArray() {
                            CallSiteArray callSiteArray;
                            if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                                callSiteArray = _closure7.$createCallSiteArray();
                                $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                            }
                            return callSiteArray.array;
                        }
                    }
                    callSiteArray[2].call(modelTypes, (Object)new _closure7((Object)this, this.getThisObject(), this.modelTypesClassNodes));
                }
                Object modelTypesFromTemplate = callSiteArray[3].call(classNode, callSiteArray[4].callGetProperty(MarkupTemplateEngine.class));
                if (DefaultTypeTransformation.booleanUnbox((Object)modelTypesFromTemplate)) {
                    if (ScriptBytecodeAdapter.compareEqual((Object)this.modelTypesClassNodes.get(), null)) {
                        Object object = modelTypesFromTemplate;
                        this.modelTypesClassNodes.set(object);
                    } else {
                        callSiteArray[5].call(this.modelTypesClassNodes.get(), modelTypesFromTemplate);
                    }
                }
                if (ScriptBytecodeAdapter.compareEqual((Object)this.modelTypesClassNodes.get(), null)) {
                    return callSiteArray[6].call(callSiteArray[7].callGroovyObjectGetProperty((Object)this));
                }
                return null;
            }

            @Generated
            public Object getModelTypesClassNodes() {
                CallSite[] callSiteArray = _run_closure1.$getCallSiteArray();
                return this.modelTypesClassNodes.get();
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (((Object)((Object)this)).getClass() != _run_closure1.class) {
                    return ScriptBytecodeAdapter.initMetaClass((Object)((Object)this));
                }
                ClassInfo classInfo = $staticClassInfo;
                if (classInfo == null) {
                    $staticClassInfo = classInfo = ClassInfo.getClassInfo(((Object)((Object)this)).getClass());
                }
                return classInfo.getMetaClass();
            }

            public /* synthetic */ MethodHandles.Lookup $getLookup() {
                return MethodHandles.lookup();
            }

            private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
                stringArray[0] = "get";
                stringArray[1] = "modelTypes";
                stringArray[2] = "each";
                stringArray[3] = "getNodeMetaData";
                stringArray[4] = "MODELTYPES_ASTKEY";
                stringArray[5] = "putAll";
                stringArray[6] = "pushErrorCollector";
                stringArray[7] = "context";
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[8];
                _run_closure1.$createCallSiteArray_1(stringArray);
                return new CallSiteArray(_run_closure1.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _run_closure1.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }

            private static /* synthetic */ Class $get$$class$groovy$text$markup$MarkupTemplateEngine$TemplateGroovyClassLoader() {
                Class clazz = $class$groovy$text$markup$MarkupTemplateEngine$TemplateGroovyClassLoader;
                if (clazz == null) {
                    clazz = $class$groovy$text$markup$MarkupTemplateEngine$TemplateGroovyClassLoader = _run_closure1.class$("groovy.text.markup.MarkupTemplateEngine$TemplateGroovyClassLoader");
                }
                return clazz;
            }

            static /* synthetic */ Class class$(String string) {
                try {
                    return Class.forName(string);
                }
                catch (ClassNotFoundException classNotFoundException) {
                    throw new NoClassDefFoundError(classNotFoundException.getMessage());
                }
            }
        }
        callSiteArray[0].callCurrent((GroovyObject)this, (Object)new _run_closure1((Object)this, (Object)this, modelTypesClassNodes));
        public final class _run_closure2
        extends Closure
        implements GeneratedClosure {
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _run_closure2(Object _outerInstance, Object _thisObject) {
                CallSite[] callSiteArray = _run_closure2.$getCallSiteArray();
                super(_outerInstance, _thisObject);
            }

            public Object doCall(Object it) {
                CallSite[] callSiteArray = _run_closure2.$getCallSiteArray();
                public final class _closure8
                extends Closure
                implements GeneratedClosure {
                    private static /* synthetic */ ClassInfo $staticClassInfo;
                    public static transient /* synthetic */ boolean __$stMC;
                    private static /* synthetic */ SoftReference $callSiteArray;

                    public _closure8(Object _outerInstance, Object _thisObject) {
                        CallSite[] callSiteArray = _closure8.$getCallSiteArray();
                        super(_outerInstance, _thisObject);
                    }

                    public Object doCall(Object it) {
                        CallSite[] callSiteArray = _closure8.$getCallSiteArray();
                        List list = ScriptBytecodeAdapter.createList((Object[])new Object[0]);
                        ScriptBytecodeAdapter.setGroovyObjectProperty((Object)list, _closure8.class, (GroovyObject)this, (String)"builderCalls");
                        Map map = ScriptBytecodeAdapter.createMap((Object[])new Object[0]);
                        ScriptBytecodeAdapter.setGroovyObjectProperty((Object)map, _closure8.class, (GroovyObject)this, (String)"binaryExpressions");
                        return map;
                    }

                    @Generated
                    public Object doCall() {
                        CallSite[] callSiteArray = _closure8.$getCallSiteArray();
                        return this.doCall(null);
                    }

                    protected /* synthetic */ MetaClass $getStaticMetaClass() {
                        if (((Object)((Object)this)).getClass() != _closure8.class) {
                            return ScriptBytecodeAdapter.initMetaClass((Object)((Object)this));
                        }
                        ClassInfo classInfo = $staticClassInfo;
                        if (classInfo == null) {
                            $staticClassInfo = classInfo = ClassInfo.getClassInfo(((Object)((Object)this)).getClass());
                        }
                        return classInfo.getMetaClass();
                    }

                    public /* synthetic */ MethodHandles.Lookup $getLookup() {
                        return MethodHandles.lookup();
                    }

                    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                        String[] stringArray = new String[]{};
                        return new CallSiteArray(_closure8.class, stringArray);
                    }

                    private static /* synthetic */ CallSite[] $getCallSiteArray() {
                        CallSiteArray callSiteArray;
                        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                            callSiteArray = _closure8.$createCallSiteArray();
                            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                        }
                        return callSiteArray.array;
                    }
                }
                return callSiteArray[0].callCurrent((GroovyObject)this, (Object)new _closure8((Object)this, this.getThisObject()));
            }

            @Generated
            public Object doCall() {
                CallSite[] callSiteArray = _run_closure2.$getCallSiteArray();
                return this.doCall(null);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (((Object)((Object)this)).getClass() != _run_closure2.class) {
                    return ScriptBytecodeAdapter.initMetaClass((Object)((Object)this));
                }
                ClassInfo classInfo = $staticClassInfo;
                if (classInfo == null) {
                    $staticClassInfo = classInfo = ClassInfo.getClassInfo(((Object)((Object)this)).getClass());
                }
                return classInfo.getMetaClass();
            }

            public /* synthetic */ MethodHandles.Lookup $getLookup() {
                return MethodHandles.lookup();
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[1];
                stringArray[0] = "newScope";
                return new CallSiteArray(_run_closure2.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _run_closure2.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        callSiteArray[1].callCurrent((GroovyObject)this, (Object)new _run_closure2((Object)this, (Object)this));
        public final class _run_closure3
        extends Closure
        implements GeneratedClosure {
            private /* synthetic */ Reference modelTypesClassNodes;
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _run_closure3(Object _outerInstance, Object _thisObject, Reference modelTypesClassNodes) {
                Reference reference;
                CallSite[] callSiteArray = _run_closure3.$getCallSiteArray();
                super(_outerInstance, _thisObject);
                this.modelTypesClassNodes = reference = modelTypesClassNodes;
            }

            public Object doCall(Object receiver, Object name, Object argList, Object argTypes, Object call) {
                CallSite[] callSiteArray = _run_closure3.$getCallSiteArray();
                if (!BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                    if (ScriptBytecodeAdapter.compareEqual((Object)"getAt", (Object)name) && ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[0].callGetProperty(ClassHelper.class), (Object)receiver)) {
                        Object enclosingBinaryExpression = callSiteArray[1].callGetProperty(callSiteArray[2].callGroovyObjectGetProperty((Object)this));
                        if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[3].call(callSiteArray[4].callGetProperty(enclosingBinaryExpression), callSiteArray[5].callGetProperty(call)))) {
                            Object stack = callSiteArray[6].callGetProperty(callSiteArray[7].callGroovyObjectGetProperty((Object)this));
                            if (ScriptBytecodeAdapter.compareGreaterThan((Object)callSiteArray[8].call(stack), (Object)1)) {
                                Object superEnclosing = callSiteArray[9].call(stack, (Object)1);
                                Object opType = callSiteArray[10].callGetProperty(callSiteArray[11].callGetProperty(superEnclosing));
                                if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[12].call(callSiteArray[13].callGetProperty(superEnclosing), enclosingBinaryExpression)) && DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[14].callStatic(StaticTypeCheckingSupport.class, opType))) {
                                    if (ScriptBytecodeAdapter.compareEqual((Object)opType, (Object)callSiteArray[15].callGetProperty(Types.class))) {
                                        Object mce = callSiteArray[16].callConstructor(MethodCallExpression.class, callSiteArray[17].callGetProperty(enclosingBinaryExpression), (Object)"putAt", callSiteArray[18].callConstructor(ArgumentListExpression.class, callSiteArray[19].callGetProperty(enclosingBinaryExpression), callSiteArray[20].callGetProperty(superEnclosing)));
                                        callSiteArray[21].callCurrent((GroovyObject)this, mce);
                                        callSiteArray[22].call(callSiteArray[23].callGetProperty(callSiteArray[24].callGroovyObjectGetProperty((Object)this)), superEnclosing, mce);
                                        return null;
                                    }
                                    throw (Throwable)callSiteArray[25].callConstructor(UnsupportedOperationException.class, (Object)new GStringImpl(new Object[]{callSiteArray[26].callGetProperty(superEnclosing)}, new String[]{"Operation not supported in templates: ", ". Please declare an explicit type for the variable."}));
                                }
                            }
                            callSiteArray[27].call(callSiteArray[28].callGetProperty(callSiteArray[29].callGroovyObjectGetProperty((Object)this)), enclosingBinaryExpression, call);
                            return callSiteArray[30].callCurrent((GroovyObject)this, call);
                        }
                    }
                } else if (ScriptBytecodeAdapter.compareEqual((Object)"getAt", (Object)name) && ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[31].callGetProperty(ClassHelper.class), (Object)receiver)) {
                    Object enclosingBinaryExpression = callSiteArray[32].callGetProperty(callSiteArray[33].callGroovyObjectGetProperty((Object)this));
                    if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[34].call(callSiteArray[35].callGetProperty(enclosingBinaryExpression), callSiteArray[36].callGetProperty(call)))) {
                        Object stack = callSiteArray[37].callGetProperty(callSiteArray[38].callGroovyObjectGetProperty((Object)this));
                        if (ScriptBytecodeAdapter.compareGreaterThan((Object)callSiteArray[39].call(stack), (Object)1)) {
                            Object superEnclosing = callSiteArray[40].call(stack, (Object)1);
                            Object opType = callSiteArray[41].callGetProperty(callSiteArray[42].callGetProperty(superEnclosing));
                            if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[43].call(callSiteArray[44].callGetProperty(superEnclosing), enclosingBinaryExpression)) && DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[45].callStatic(StaticTypeCheckingSupport.class, opType))) {
                                if (ScriptBytecodeAdapter.compareEqual((Object)opType, (Object)callSiteArray[46].callGetProperty(Types.class))) {
                                    Object mce = callSiteArray[47].callConstructor(MethodCallExpression.class, callSiteArray[48].callGetProperty(enclosingBinaryExpression), (Object)"putAt", callSiteArray[49].callConstructor(ArgumentListExpression.class, callSiteArray[50].callGetProperty(enclosingBinaryExpression), callSiteArray[51].callGetProperty(superEnclosing)));
                                    callSiteArray[52].callCurrent((GroovyObject)this, mce);
                                    callSiteArray[53].call(callSiteArray[54].callGetProperty(callSiteArray[55].callGroovyObjectGetProperty((Object)this)), superEnclosing, mce);
                                    return null;
                                }
                                throw (Throwable)callSiteArray[56].callConstructor(UnsupportedOperationException.class, (Object)new GStringImpl(new Object[]{callSiteArray[57].callGetProperty(superEnclosing)}, new String[]{"Operation not supported in templates: ", ". Please declare an explicit type for the variable."}));
                            }
                        }
                        callSiteArray[58].call(callSiteArray[59].callGetProperty(callSiteArray[60].callGroovyObjectGetProperty((Object)this)), enclosingBinaryExpression, call);
                        return callSiteArray[61].callCurrent((GroovyObject)this, call);
                    }
                }
                if (ScriptBytecodeAdapter.compareGreaterThan((Object)callSiteArray[62].callGetProperty(call), (Object)0)) {
                    if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[63].callGetProperty(call))) {
                        callSiteArray[64].call(callSiteArray[65].callGetProperty(callSiteArray[66].callGroovyObjectGetProperty((Object)this)), call);
                        return callSiteArray[67].callCurrent((GroovyObject)this, call, callSiteArray[68].callGetProperty(ClassHelper.class));
                    }
                    if (ScriptBytecodeAdapter.compareEqual((Object)this.modelTypesClassNodes.get(), null)) {
                        return callSiteArray[69].callCurrent((GroovyObject)this, call, callSiteArray[70].callGetProperty(ClassHelper.class));
                    }
                }
                return null;
            }

            @Generated
            public Object call(Object receiver, Object name, Object argList, Object argTypes, Object call) {
                CallSite[] callSiteArray = _run_closure3.$getCallSiteArray();
                return callSiteArray[71].callCurrent((GroovyObject)this, ArrayUtil.createArray((Object)receiver, (Object)name, (Object)argList, (Object)argTypes, (Object)call));
            }

            @Generated
            public Object getModelTypesClassNodes() {
                CallSite[] callSiteArray = _run_closure3.$getCallSiteArray();
                return this.modelTypesClassNodes.get();
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (((Object)((Object)this)).getClass() != _run_closure3.class) {
                    return ScriptBytecodeAdapter.initMetaClass((Object)((Object)this));
                }
                ClassInfo classInfo = $staticClassInfo;
                if (classInfo == null) {
                    $staticClassInfo = classInfo = ClassInfo.getClassInfo(((Object)((Object)this)).getClass());
                }
                return classInfo.getMetaClass();
            }

            public /* synthetic */ MethodHandles.Lookup $getLookup() {
                return MethodHandles.lookup();
            }

            private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
                stringArray[0] = "OBJECT_TYPE";
                stringArray[1] = "enclosingBinaryExpression";
                stringArray[2] = "context";
                stringArray[3] = "is";
                stringArray[4] = "leftExpression";
                stringArray[5] = "objectExpression";
                stringArray[6] = "enclosingBinaryExpressionStack";
                stringArray[7] = "context";
                stringArray[8] = "size";
                stringArray[9] = "get";
                stringArray[10] = "type";
                stringArray[11] = "operation";
                stringArray[12] = "is";
                stringArray[13] = "leftExpression";
                stringArray[14] = "isAssignment";
                stringArray[15] = "ASSIGN";
                stringArray[16] = "<$constructor$>";
                stringArray[17] = "leftExpression";
                stringArray[18] = "<$constructor$>";
                stringArray[19] = "rightExpression";
                stringArray[20] = "rightExpression";
                stringArray[21] = "makeDynamic";
                stringArray[22] = "put";
                stringArray[23] = "binaryExpressions";
                stringArray[24] = "currentScope";
                stringArray[25] = "<$constructor$>";
                stringArray[26] = "text";
                stringArray[27] = "put";
                stringArray[28] = "binaryExpressions";
                stringArray[29] = "currentScope";
                stringArray[30] = "makeDynamic";
                stringArray[31] = "OBJECT_TYPE";
                stringArray[32] = "enclosingBinaryExpression";
                stringArray[33] = "context";
                stringArray[34] = "is";
                stringArray[35] = "leftExpression";
                stringArray[36] = "objectExpression";
                stringArray[37] = "enclosingBinaryExpressionStack";
                stringArray[38] = "context";
                stringArray[39] = "size";
                stringArray[40] = "get";
                stringArray[41] = "type";
                stringArray[42] = "operation";
                stringArray[43] = "is";
                stringArray[44] = "leftExpression";
                stringArray[45] = "isAssignment";
                stringArray[46] = "ASSIGN";
                stringArray[47] = "<$constructor$>";
                stringArray[48] = "leftExpression";
                stringArray[49] = "<$constructor$>";
                stringArray[50] = "rightExpression";
                stringArray[51] = "rightExpression";
                stringArray[52] = "makeDynamic";
                stringArray[53] = "put";
                stringArray[54] = "binaryExpressions";
                stringArray[55] = "currentScope";
                stringArray[56] = "<$constructor$>";
                stringArray[57] = "text";
                stringArray[58] = "put";
                stringArray[59] = "binaryExpressions";
                stringArray[60] = "currentScope";
                stringArray[61] = "makeDynamic";
                stringArray[62] = "lineNumber";
                stringArray[63] = "implicitThis";
                stringArray[64] = "leftShift";
                stringArray[65] = "builderCalls";
                stringArray[66] = "currentScope";
                stringArray[67] = "makeDynamic";
                stringArray[68] = "OBJECT_TYPE";
                stringArray[69] = "makeDynamic";
                stringArray[70] = "OBJECT_TYPE";
                stringArray[71] = "doCall";
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[72];
                _run_closure3.$createCallSiteArray_1(stringArray);
                return new CallSiteArray(_run_closure3.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _run_closure3.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        callSiteArray[2].callCurrent((GroovyObject)this, (Object)new _run_closure3((Object)this, (Object)this, modelTypesClassNodes));
        public final class _run_closure4
        extends Closure
        implements GeneratedClosure {
            private /* synthetic */ Reference modelTypesClassNodes;
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;
            private static /* synthetic */ Class $class$groovy$text$markup$MarkupBuilderCodeTransformer;

            public _run_closure4(Object _outerInstance, Object _thisObject, Reference modelTypesClassNodes) {
                Reference reference;
                CallSite[] callSiteArray = _run_closure4.$getCallSiteArray();
                super(_outerInstance, _thisObject);
                this.modelTypesClassNodes = reference = modelTypesClassNodes;
            }

            public Object doCall(Object call, Object node) {
                CallSite[] callSiteArray = _run_closure4.$getCallSiteArray();
                if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[0].callCurrent((GroovyObject)this, call)) && ScriptBytecodeAdapter.compareNotEqual((Object)this.modelTypesClassNodes.get(), null)) {
                    Object args = callSiteArray[1].callGetProperty(callSiteArray[2].callCurrent((GroovyObject)this, call));
                    if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[3].call(args), (Object)1)) {
                        String varName = ShortTypeHandling.castToString((Object)(DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[4].callCurrent((GroovyObject)this, callSiteArray[5].call(args, (Object)0))) ? callSiteArray[6].callGetProperty(callSiteArray[7].call(args, (Object)0)) : callSiteArray[8].call(call, callSiteArray[9].callGetProperty((Object)_run_closure4.$get$$class$groovy$text$markup$MarkupBuilderCodeTransformer()))));
                        Object type = callSiteArray[10].call(this.modelTypesClassNodes.get(), (Object)varName);
                        if (DefaultTypeTransformation.booleanUnbox((Object)type)) {
                            if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[11].callGetProperty(callSiteArray[12].callGetProperty(call)), (Object)"this.getModel()")) {
                                return callSiteArray[13].callCurrent((GroovyObject)this, call, type);
                            }
                            if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[14].callGetProperty(call), (Object)"tryEscape")) {
                                return callSiteArray[15].callCurrent((GroovyObject)this, call, type);
                            }
                        }
                    }
                }
                return null;
            }

            @Generated
            public Object call(Object call, Object node) {
                CallSite[] callSiteArray = _run_closure4.$getCallSiteArray();
                return callSiteArray[16].callCurrent((GroovyObject)this, call, node);
            }

            @Generated
            public Object getModelTypesClassNodes() {
                CallSite[] callSiteArray = _run_closure4.$getCallSiteArray();
                return this.modelTypesClassNodes.get();
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (((Object)((Object)this)).getClass() != _run_closure4.class) {
                    return ScriptBytecodeAdapter.initMetaClass((Object)((Object)this));
                }
                ClassInfo classInfo = $staticClassInfo;
                if (classInfo == null) {
                    $staticClassInfo = classInfo = ClassInfo.getClassInfo(((Object)((Object)this)).getClass());
                }
                return classInfo.getMetaClass();
            }

            public /* synthetic */ MethodHandles.Lookup $getLookup() {
                return MethodHandles.lookup();
            }

            private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
                stringArray[0] = "isMethodCallExpression";
                stringArray[1] = "expressions";
                stringArray[2] = "getArguments";
                stringArray[3] = "size";
                stringArray[4] = "isConstantExpression";
                stringArray[5] = "getAt";
                stringArray[6] = "text";
                stringArray[7] = "getAt";
                stringArray[8] = "getNodeMetaData";
                stringArray[9] = "TARGET_VARIABLE";
                stringArray[10] = "getAt";
                stringArray[11] = "text";
                stringArray[12] = "objectExpression";
                stringArray[13] = "storeType";
                stringArray[14] = "methodAsString";
                stringArray[15] = "storeType";
                stringArray[16] = "doCall";
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[17];
                _run_closure4.$createCallSiteArray_1(stringArray);
                return new CallSiteArray(_run_closure4.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _run_closure4.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }

            private static /* synthetic */ Class $get$$class$groovy$text$markup$MarkupBuilderCodeTransformer() {
                Class clazz = $class$groovy$text$markup$MarkupBuilderCodeTransformer;
                if (clazz == null) {
                    clazz = $class$groovy$text$markup$MarkupBuilderCodeTransformer = _run_closure4.class$("groovy.text.markup.MarkupBuilderCodeTransformer");
                }
                return clazz;
            }

            static /* synthetic */ Class class$(String string) {
                try {
                    return Class.forName(string);
                }
                catch (ClassNotFoundException classNotFoundException) {
                    throw new NoClassDefFoundError(classNotFoundException.getMessage());
                }
            }
        }
        callSiteArray[3].callCurrent((GroovyObject)this, (Object)new _run_closure4((Object)this, (Object)this, modelTypesClassNodes));
        public final class _run_closure5
        extends Closure
        implements GeneratedClosure {
            private /* synthetic */ Reference modelTypesClassNodes;
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _run_closure5(Object _outerInstance, Object _thisObject, Reference modelTypesClassNodes) {
                Reference reference;
                CallSite[] callSiteArray = _run_closure5.$getCallSiteArray();
                super(_outerInstance, _thisObject);
                this.modelTypesClassNodes = reference = modelTypesClassNodes;
            }

            /*
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            public Object doCall(Object pexp) {
                CallSite[] callSiteArray = _run_closure5.$getCallSiteArray();
                if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[0].callGetProperty(callSiteArray[1].callGetProperty(pexp)), (Object)"this.getModel()")) {
                    if (!ScriptBytecodeAdapter.compareNotEqual((Object)this.modelTypesClassNodes.get(), null)) return callSiteArray[5].callCurrent((GroovyObject)this, pexp);
                    Object type = callSiteArray[2].call(this.modelTypesClassNodes.get(), callSiteArray[3].callGetProperty(pexp));
                    if (!DefaultTypeTransformation.booleanUnbox((Object)type)) return null;
                    return callSiteArray[4].callCurrent((GroovyObject)this, pexp, type);
                }
                if (!ScriptBytecodeAdapter.compareEqual((Object)this.modelTypesClassNodes.get(), null)) return null;
                return callSiteArray[6].callCurrent((GroovyObject)this, pexp);
            }

            @Generated
            public Object getModelTypesClassNodes() {
                CallSite[] callSiteArray = _run_closure5.$getCallSiteArray();
                return this.modelTypesClassNodes.get();
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (((Object)((Object)this)).getClass() != _run_closure5.class) {
                    return ScriptBytecodeAdapter.initMetaClass((Object)((Object)this));
                }
                ClassInfo classInfo = $staticClassInfo;
                if (classInfo == null) {
                    $staticClassInfo = classInfo = ClassInfo.getClassInfo(((Object)((Object)this)).getClass());
                }
                return classInfo.getMetaClass();
            }

            public /* synthetic */ MethodHandles.Lookup $getLookup() {
                return MethodHandles.lookup();
            }

            private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
                stringArray[0] = "text";
                stringArray[1] = "objectExpression";
                stringArray[2] = "getAt";
                stringArray[3] = "propertyAsString";
                stringArray[4] = "makeDynamic";
                stringArray[5] = "makeDynamic";
                stringArray[6] = "makeDynamic";
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[7];
                _run_closure5.$createCallSiteArray_1(stringArray);
                return new CallSiteArray(_run_closure5.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _run_closure5.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        callSiteArray[4].callCurrent((GroovyObject)this, (Object)new _run_closure5((Object)this, (Object)this, modelTypesClassNodes));
        public final class _run_closure6
        extends Closure
        implements GeneratedClosure {
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _run_closure6(Object _outerInstance, Object _thisObject) {
                CallSite[] callSiteArray = _run_closure6.$getCallSiteArray();
                super(_outerInstance, _thisObject);
            }

            public Object doCall(Object mn) {
                Reference mn2 = new Reference(mn);
                CallSite[] callSiteArray = _run_closure6.$getCallSiteArray();
                public final class _closure9
                extends Closure
                implements GeneratedClosure {
                    private /* synthetic */ Reference mn;
                    private static /* synthetic */ ClassInfo $staticClassInfo;
                    public static transient /* synthetic */ boolean __$stMC;
                    private static /* synthetic */ SoftReference $callSiteArray;

                    public _closure9(Object _outerInstance, Object _thisObject, Reference mn) {
                        Reference reference;
                        CallSite[] callSiteArray = _closure9.$getCallSiteArray();
                        super(_outerInstance, _thisObject);
                        this.mn = reference = mn;
                    }

                    public Object doCall(Object it) {
                        CallSite[] callSiteArray = _closure9.$getCallSiteArray();
                        return callSiteArray[0].call(callSiteArray[1].callConstructor(BuilderMethodReplacer.class, callSiteArray[2].callGetProperty(callSiteArray[3].callGroovyObjectGetProperty((Object)this)), callSiteArray[4].callGroovyObjectGetProperty((Object)this), callSiteArray[5].callGroovyObjectGetProperty((Object)this)), this.mn.get());
                    }

                    @Generated
                    public Object getMn() {
                        CallSite[] callSiteArray = _closure9.$getCallSiteArray();
                        return this.mn.get();
                    }

                    @Generated
                    public Object doCall() {
                        CallSite[] callSiteArray = _closure9.$getCallSiteArray();
                        return this.doCall(null);
                    }

                    protected /* synthetic */ MetaClass $getStaticMetaClass() {
                        if (((Object)((Object)this)).getClass() != _closure9.class) {
                            return ScriptBytecodeAdapter.initMetaClass((Object)((Object)this));
                        }
                        ClassInfo classInfo = $staticClassInfo;
                        if (classInfo == null) {
                            $staticClassInfo = classInfo = ClassInfo.getClassInfo(((Object)((Object)this)).getClass());
                        }
                        return classInfo.getMetaClass();
                    }

                    public /* synthetic */ MethodHandles.Lookup $getLookup() {
                        return MethodHandles.lookup();
                    }

                    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
                        stringArray[0] = "visitMethod";
                        stringArray[1] = "<$constructor$>";
                        stringArray[2] = "source";
                        stringArray[3] = "context";
                        stringArray[4] = "builderCalls";
                        stringArray[5] = "binaryExpressions";
                    }

                    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                        String[] stringArray = new String[6];
                        _closure9.$createCallSiteArray_1(stringArray);
                        return new CallSiteArray(_closure9.class, stringArray);
                    }

                    private static /* synthetic */ CallSite[] $getCallSiteArray() {
                        CallSiteArray callSiteArray;
                        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                            callSiteArray = _closure9.$createCallSiteArray();
                            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                        }
                        return callSiteArray.array;
                    }
                }
                return callSiteArray[0].callCurrent((GroovyObject)this, (Object)new _closure9((Object)this, this.getThisObject(), mn2));
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (((Object)((Object)this)).getClass() != _run_closure6.class) {
                    return ScriptBytecodeAdapter.initMetaClass((Object)((Object)this));
                }
                ClassInfo classInfo = $staticClassInfo;
                if (classInfo == null) {
                    $staticClassInfo = classInfo = ClassInfo.getClassInfo(((Object)((Object)this)).getClass());
                }
                return classInfo.getMetaClass();
            }

            public /* synthetic */ MethodHandles.Lookup $getLookup() {
                return MethodHandles.lookup();
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[1];
                stringArray[0] = "scopeExit";
                return new CallSiteArray(_run_closure6.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _run_closure6.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        return callSiteArray[5].callCurrent((GroovyObject)this, (Object)new _run_closure6((Object)this, (Object)this));
    }

    private static ClassNode buildNodeFromString(String option, TypeCheckingContext ctx) {
        Reference ctx2 = new Reference((Object)ctx);
        ModuleNode moduleNode = ParserPlugin.buildAST((CharSequence)new GStringImpl(new Object[]{option}, new String[]{"", " dummy;"}), (CompilerConfiguration)((TypeCheckingContext)ctx2.get()).getCompilationUnit().getConfiguration(), (GroovyClassLoader)((TypeCheckingContext)ctx2.get()).getCompilationUnit().getClassLoader(), (ErrorCollector)((TypeCheckingContext)ctx2.get()).getErrorCollector());
        ClassNode optionNode = ((DeclarationExpression)ScriptBytecodeAdapter.castToType((Object)((ExpressionStatement)ScriptBytecodeAdapter.castToType((Object)DefaultGroovyMethods.getAt((List)moduleNode.getStatementBlock().getStatements(), (int)0), ExpressionStatement.class)).getExpression(), DeclarationExpression.class)).getLeftExpression().getType();
        ClassNode dummyClass = new ClassNode("dummy", 0, ClassHelper.OBJECT_TYPE);
        ModuleNode moduleNode2 = new ModuleNode(((TypeCheckingContext)ctx2.get()).getSource());
        dummyClass.setModule(moduleNode2);
        MethodNode dummyMethod = new MethodNode("dummy", 0, optionNode, Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, (Statement)EmptyStatement.INSTANCE);
        dummyClass.addMethod(dummyMethod);
        GroovyObject visitor = new GroovyObject(ctx2, ((TypeCheckingContext)ctx2.get()).getCompilationUnit()){
            public /* synthetic */ Reference ctx;
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private transient /* synthetic */ MetaClass metaClass;
            {
                MetaClass metaClass;
                Reference reference;
                this.ctx = reference = p0;
                super((CompilationUnit)ScriptBytecodeAdapter.castToType((Object)p10, CompilationUnit.class));
                this.metaClass = metaClass = this.$getStaticMetaClass();
            }

            public void addError(String msg, ASTNode expr) {
                ((TypeCheckingContext)this.ctx.get()).getErrorCollector().addErrorAndContinue(new SyntaxException(DefaultGroovyMethods.plus((String)msg, (Object)"\n"), expr.getLineNumber(), expr.getColumnNumber(), expr.getLastLineNumber(), expr.getLastColumnNumber()), ((TypeCheckingContext)this.ctx.get()).getSource());
            }

            public /* synthetic */ Object methodMissing(String name, Object args) {
                if (!(args instanceof Object[])) {
                    return ScriptBytecodeAdapter.invokeMethodN(1.class, MarkupTemplateTypeCheckingExtension.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])new Object[]{args});
                }
                if (((Object[])ScriptBytecodeAdapter.castToType((Object)args, Object[].class)).length == 1) {
                    return ScriptBytecodeAdapter.invokeMethodN(1.class, MarkupTemplateTypeCheckingExtension.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])new Object[]{BytecodeInterface8.objectArrayGet((Object[])((Object[])ScriptBytecodeAdapter.castToType((Object)args, Object[].class)), (int)0)});
                }
                return ScriptBytecodeAdapter.invokeMethodN(1.class, MarkupTemplateTypeCheckingExtension.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])ScriptBytecodeAdapter.despreadList((Object[])new Object[0], (Object[])new Object[]{args}, (int[])new int[]{0}));
            }

            public static /* synthetic */ Object $static_methodMissing(String name, Object args) {
                if (!(args instanceof Object[])) {
                    return ScriptBytecodeAdapter.invokeMethodN(1.class, MarkupTemplateTypeCheckingExtension.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])new Object[]{args});
                }
                if (((Object[])ScriptBytecodeAdapter.castToType((Object)args, Object[].class)).length == 1) {
                    return ScriptBytecodeAdapter.invokeMethodN(1.class, MarkupTemplateTypeCheckingExtension.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])new Object[]{BytecodeInterface8.objectArrayGet((Object[])((Object[])ScriptBytecodeAdapter.castToType((Object)args, Object[].class)), (int)0)});
                }
                return ScriptBytecodeAdapter.invokeMethodN(1.class, MarkupTemplateTypeCheckingExtension.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])ScriptBytecodeAdapter.despreadList((Object[])new Object[0], (Object[])new Object[]{args}, (int[])new int[]{0}));
            }

            public /* synthetic */ void propertyMissing(String name, Object value) {
                Object object = value;
                ScriptBytecodeAdapter.setProperty((Object)object, null, MarkupTemplateTypeCheckingExtension.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})));
            }

            public static /* synthetic */ void $static_propertyMissing(String name, Object value) {
                Object object = value;
                ScriptBytecodeAdapter.setProperty((Object)object, null, MarkupTemplateTypeCheckingExtension.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})));
            }

            public /* synthetic */ Object propertyMissing(String name) {
                return ScriptBytecodeAdapter.getProperty(1.class, MarkupTemplateTypeCheckingExtension.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})));
            }

            public static /* synthetic */ Object $static_propertyMissing(String name) {
                return ScriptBytecodeAdapter.getProperty(1.class, MarkupTemplateTypeCheckingExtension.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})));
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (((Object)((Object)this)).getClass() != 1.class) {
                    return ScriptBytecodeAdapter.initMetaClass((Object)((Object)this));
                }
                ClassInfo classInfo = $staticClassInfo;
                if (classInfo == null) {
                    $staticClassInfo = classInfo = ClassInfo.getClassInfo(((Object)((Object)this)).getClass());
                }
                return classInfo.getMetaClass();
            }

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

            @Generated
            @Internal
            public void setMetaClass(MetaClass metaClass) {
                this.metaClass = metaClass;
            }

            public /* synthetic */ MethodHandles.Lookup $getLookup() {
                return MethodHandles.lookup();
            }
        };
        visitor.startResolving(dummyClass, ((TypeCheckingContext)ctx2.get()).getSource());
        return dummyMethod.getReturnType();
    }

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (((Object)((Object)this)).getClass() != MarkupTemplateTypeCheckingExtension.class) {
            return ScriptBytecodeAdapter.initMetaClass((Object)((Object)this));
        }
        ClassInfo classInfo = $staticClassInfo;
        if (classInfo == null) {
            $staticClassInfo = classInfo = ClassInfo.getClassInfo(((Object)((Object)this)).getClass());
        }
        return classInfo.getMetaClass();
    }

    public static /* synthetic */ MethodHandles.Lookup $getLookup() {
        return MethodHandles.lookup();
    }

    public /* synthetic */ Object this$dist$invoke$4(String name, Object args) {
        CallSite[] callSiteArray = MarkupTemplateTypeCheckingExtension.$getCallSiteArray();
        if (!(args instanceof Object[])) {
            return ScriptBytecodeAdapter.invokeMethodOnCurrentN(MarkupTemplateTypeCheckingExtension.class, (GroovyObject)this, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])new Object[]{args});
        }
        if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[6].callGetProperty((Object)((Object[])ScriptBytecodeAdapter.castToType((Object)args, Object[].class))), (Object)1)) {
                return ScriptBytecodeAdapter.invokeMethodOnCurrentN(MarkupTemplateTypeCheckingExtension.class, (GroovyObject)this, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])new Object[]{callSiteArray[7].call((Object)((Object[])ScriptBytecodeAdapter.castToType((Object)args, Object[].class)), (Object)0)});
            }
        } else if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[8].callGetProperty((Object)((Object[])ScriptBytecodeAdapter.castToType((Object)args, Object[].class))), (Object)1)) {
            return ScriptBytecodeAdapter.invokeMethodOnCurrentN(MarkupTemplateTypeCheckingExtension.class, (GroovyObject)this, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])new Object[]{BytecodeInterface8.objectArrayGet((Object[])((Object[])ScriptBytecodeAdapter.castToType((Object)args, Object[].class)), (int)0)});
        }
        return ScriptBytecodeAdapter.invokeMethodOnCurrentN(MarkupTemplateTypeCheckingExtension.class, (GroovyObject)this, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])ScriptBytecodeAdapter.despreadList((Object[])new Object[0], (Object[])new Object[]{args}, (int[])new int[]{0}));
    }

    public /* synthetic */ void this$dist$set$4(String name, Object value) {
        CallSite[] callSiteArray = MarkupTemplateTypeCheckingExtension.$getCallSiteArray();
        Object object = value;
        ScriptBytecodeAdapter.setGroovyObjectProperty((Object)object, MarkupTemplateTypeCheckingExtension.class, (GroovyObject)this, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})));
    }

    public /* synthetic */ Object this$dist$get$4(String name) {
        CallSite[] callSiteArray = MarkupTemplateTypeCheckingExtension.$getCallSiteArray();
        return ScriptBytecodeAdapter.getGroovyObjectProperty(MarkupTemplateTypeCheckingExtension.class, (GroovyObject)this, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})));
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
        stringArray[0] = "beforeVisitClass";
        stringArray[1] = "beforeVisitMethod";
        stringArray[2] = "methodNotFound";
        stringArray[3] = "onMethodSelection";
        stringArray[4] = "unresolvedProperty";
        stringArray[5] = "afterVisitMethod";
        stringArray[6] = "length";
        stringArray[7] = "getAt";
        stringArray[8] = "length";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[9];
        MarkupTemplateTypeCheckingExtension.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(MarkupTemplateTypeCheckingExtension.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = MarkupTemplateTypeCheckingExtension.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }

    private static class BuilderMethodReplacer
    extends ClassCodeExpressionTransformer
    implements GroovyObject {
        private static final MethodNode METHOD_MISSING;
        private final SourceUnit unit;
        private final Set<MethodCallExpression> callsToBeReplaced;
        private final Map<BinaryExpression, MethodCallExpression> binaryExpressionsToBeReplaced;
        private static /* synthetic */ ClassInfo $staticClassInfo;
        public static transient /* synthetic */ boolean __$stMC;
        private transient /* synthetic */ MetaClass metaClass;
        private static /* synthetic */ ClassInfo $staticClassInfo$;
        private static /* synthetic */ SoftReference $callSiteArray;

        public BuilderMethodReplacer(SourceUnit unit, Collection<MethodCallExpression> calls, Map<BinaryExpression, MethodCallExpression> binExpressionsWithReplacements) {
            Set set;
            SourceUnit sourceUnit;
            MetaClass metaClass;
            CallSite[] callSiteArray = BuilderMethodReplacer.$getCallSiteArray();
            this.metaClass = metaClass = this.$getStaticMetaClass();
            this.unit = sourceUnit = unit;
            this.callsToBeReplaced = set = (Set)ScriptBytecodeAdapter.asType(calls, Set.class);
            Map<BinaryExpression, MethodCallExpression> map = binExpressionsWithReplacements;
            this.binaryExpressionsToBeReplaced = map;
        }

        protected SourceUnit getSourceUnit() {
            CallSite[] callSiteArray = BuilderMethodReplacer.$getCallSiteArray();
            return this.unit;
        }

        public void visitClosureExpression(ClosureExpression expression) {
            CallSite[] callSiteArray = BuilderMethodReplacer.$getCallSiteArray();
            ScriptBytecodeAdapter.invokeMethodOnSuperN(BuilderMethodReplacer.class, (GroovyObject)this, (String)"visitClosureExpression", (Object[])new Object[]{expression});
        }

        public Expression transform(Expression exp) {
            CallSite[] callSiteArray = BuilderMethodReplacer.$getCallSiteArray();
            if (exp instanceof BinaryExpression && DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[0].call(this.binaryExpressionsToBeReplaced, (Object)exp))) {
                return (Expression)ScriptBytecodeAdapter.castToType((Object)callSiteArray[1].call(this.binaryExpressionsToBeReplaced, (Object)exp), Expression.class);
            }
            if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[2].call(this.callsToBeReplaced, (Object)exp))) {
                Object args = callSiteArray[3].callGetProperty((Object)exp) instanceof TupleExpression ? callSiteArray[4].callGetProperty(callSiteArray[5].callGetProperty((Object)exp)) : ScriptBytecodeAdapter.createList((Object[])new Object[]{callSiteArray[6].callGetProperty((Object)exp)});
                ScriptBytecodeAdapter.invokeMethodNSpreadSafe(BuilderMethodReplacer.class, (Object)args, (String)"visit", (Object[])new Object[]{this});
                Object call = callSiteArray[7].callConstructor(MethodCallExpression.class, callSiteArray[8].callConstructor(VariableExpression.class, (Object)"this"), (Object)"methodMissing", callSiteArray[9].callConstructor(ArgumentListExpression.class, callSiteArray[10].callConstructor(ConstantExpression.class, callSiteArray[11].call((Object)exp)), callSiteArray[12].callConstructor(ArrayExpression.class, callSiteArray[13].callGetProperty(ClassHelper.class), (Object)ScriptBytecodeAdapter.createList((Object[])ScriptBytecodeAdapter.despreadList((Object[])new Object[0], (Object[])new Object[]{args}, (int[])new int[]{0})))));
                boolean bl = true;
                ScriptBytecodeAdapter.setProperty((Object)bl, null, (Object)call, (String)"implicitThis");
                Object object = callSiteArray[14].callGetProperty((Object)exp);
                ScriptBytecodeAdapter.setProperty((Object)object, null, (Object)call, (String)"safe");
                Object object2 = callSiteArray[15].callGetProperty((Object)exp);
                ScriptBytecodeAdapter.setProperty((Object)object2, null, (Object)call, (String)"spreadSafe");
                MethodNode methodNode = METHOD_MISSING;
                ScriptBytecodeAdapter.setProperty((Object)methodNode, null, (Object)call, (String)"methodTarget");
                return (Expression)ScriptBytecodeAdapter.castToType((Object)call, Expression.class);
            }
            if (exp instanceof ClosureExpression) {
                callSiteArray[16].call(callSiteArray[17].callGetProperty((Object)exp), (Object)this);
                return (Expression)ScriptBytecodeAdapter.castToType((Object)ScriptBytecodeAdapter.invokeMethodOnSuperN(BuilderMethodReplacer.class, (GroovyObject)this, (String)"transform", (Object[])new Object[]{exp}), Expression.class);
            }
            return (Expression)ScriptBytecodeAdapter.castToType((Object)ScriptBytecodeAdapter.invokeMethodOnSuperN(BuilderMethodReplacer.class, (GroovyObject)this, (String)"transform", (Object[])new Object[]{exp}), Expression.class);
        }

        protected /* synthetic */ MetaClass $getStaticMetaClass() {
            if (((Object)((Object)this)).getClass() != BuilderMethodReplacer.class) {
                return ScriptBytecodeAdapter.initMetaClass((Object)((Object)this));
            }
            ClassInfo classInfo = $staticClassInfo;
            if (classInfo == null) {
                $staticClassInfo = classInfo = ClassInfo.getClassInfo(((Object)((Object)this)).getClass());
            }
            return classInfo.getMetaClass();
        }

        public static /* synthetic */ MethodHandles.Lookup $getLookup() {
            return MethodHandles.lookup();
        }

        public /* synthetic */ Object methodMissing(String name, Object args) {
            CallSite[] callSiteArray = BuilderMethodReplacer.$getCallSiteArray();
            if (!(args instanceof Object[])) {
                return ScriptBytecodeAdapter.invokeMethodN(BuilderMethodReplacer.class, MarkupTemplateTypeCheckingExtension.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])new Object[]{args});
            }
            if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[18].callGetProperty((Object)((Object[])ScriptBytecodeAdapter.castToType((Object)args, Object[].class))), (Object)1)) {
                    return ScriptBytecodeAdapter.invokeMethodN(BuilderMethodReplacer.class, MarkupTemplateTypeCheckingExtension.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])new Object[]{callSiteArray[19].call((Object)((Object[])ScriptBytecodeAdapter.castToType((Object)args, Object[].class)), (Object)0)});
                }
            } else if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[20].callGetProperty((Object)((Object[])ScriptBytecodeAdapter.castToType((Object)args, Object[].class))), (Object)1)) {
                return ScriptBytecodeAdapter.invokeMethodN(BuilderMethodReplacer.class, MarkupTemplateTypeCheckingExtension.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])new Object[]{BytecodeInterface8.objectArrayGet((Object[])((Object[])ScriptBytecodeAdapter.castToType((Object)args, Object[].class)), (int)0)});
            }
            return ScriptBytecodeAdapter.invokeMethodN(BuilderMethodReplacer.class, MarkupTemplateTypeCheckingExtension.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])ScriptBytecodeAdapter.despreadList((Object[])new Object[0], (Object[])new Object[]{args}, (int[])new int[]{0}));
        }

        public static /* synthetic */ Object $static_methodMissing(String name, Object args) {
            CallSite[] callSiteArray = BuilderMethodReplacer.$getCallSiteArray();
            if (!(args instanceof Object[])) {
                return ScriptBytecodeAdapter.invokeMethodN(BuilderMethodReplacer.class, MarkupTemplateTypeCheckingExtension.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])new Object[]{args});
            }
            if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[21].callGetProperty((Object)((Object[])ScriptBytecodeAdapter.castToType((Object)args, Object[].class))), (Object)1)) {
                    return ScriptBytecodeAdapter.invokeMethodN(BuilderMethodReplacer.class, MarkupTemplateTypeCheckingExtension.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])new Object[]{callSiteArray[22].call((Object)((Object[])ScriptBytecodeAdapter.castToType((Object)args, Object[].class)), (Object)0)});
                }
            } else if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[23].callGetProperty((Object)((Object[])ScriptBytecodeAdapter.castToType((Object)args, Object[].class))), (Object)1)) {
                return ScriptBytecodeAdapter.invokeMethodN(BuilderMethodReplacer.class, MarkupTemplateTypeCheckingExtension.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])new Object[]{BytecodeInterface8.objectArrayGet((Object[])((Object[])ScriptBytecodeAdapter.castToType((Object)args, Object[].class)), (int)0)});
            }
            return ScriptBytecodeAdapter.invokeMethodN(BuilderMethodReplacer.class, MarkupTemplateTypeCheckingExtension.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])ScriptBytecodeAdapter.despreadList((Object[])new Object[0], (Object[])new Object[]{args}, (int[])new int[]{0}));
        }

        public /* synthetic */ void propertyMissing(String name, Object value) {
            CallSite[] callSiteArray = BuilderMethodReplacer.$getCallSiteArray();
            Object object = value;
            ScriptBytecodeAdapter.setProperty((Object)object, null, MarkupTemplateTypeCheckingExtension.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

        public static /* synthetic */ void $static_propertyMissing(String name, Object value) {
            CallSite[] callSiteArray = BuilderMethodReplacer.$getCallSiteArray();
            Object object = value;
            ScriptBytecodeAdapter.setProperty((Object)object, null, MarkupTemplateTypeCheckingExtension.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

        public /* synthetic */ Object propertyMissing(String name) {
            CallSite[] callSiteArray = BuilderMethodReplacer.$getCallSiteArray();
            return ScriptBytecodeAdapter.getProperty(BuilderMethodReplacer.class, MarkupTemplateTypeCheckingExtension.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

        public static /* synthetic */ Object $static_propertyMissing(String name) {
            CallSite[] callSiteArray = BuilderMethodReplacer.$getCallSiteArray();
            return ScriptBytecodeAdapter.getProperty(BuilderMethodReplacer.class, MarkupTemplateTypeCheckingExtension.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

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

        @Generated
        @Internal
        public void setMetaClass(MetaClass metaClass) {
            this.metaClass = metaClass;
        }

        static {
            Object object = BuilderMethodReplacer.$getCallSiteArray()[24].call(BuilderMethodReplacer.$getCallSiteArray()[25].call(BuilderMethodReplacer.$getCallSiteArray()[26].call(ClassHelper.class, BaseTemplate.class), (Object)"methodMissing"), (Object)0);
            METHOD_MISSING = (MethodNode)ScriptBytecodeAdapter.castToType((Object)object, MethodNode.class);
        }

        public /* synthetic */ Expression super$4$transform(Expression expression) {
            return super.transform(expression);
        }

        public /* synthetic */ void super$2$visitClosureExpression(ClosureExpression closureExpression) {
            super.visitClosureExpression(closureExpression);
        }

        private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
            stringArray[0] = "containsKey";
            stringArray[1] = "get";
            stringArray[2] = "contains";
            stringArray[3] = "arguments";
            stringArray[4] = "expressions";
            stringArray[5] = "arguments";
            stringArray[6] = "arguments";
            stringArray[7] = "<$constructor$>";
            stringArray[8] = "<$constructor$>";
            stringArray[9] = "<$constructor$>";
            stringArray[10] = "<$constructor$>";
            stringArray[11] = "getMethodAsString";
            stringArray[12] = "<$constructor$>";
            stringArray[13] = "OBJECT_TYPE";
            stringArray[14] = "safe";
            stringArray[15] = "spreadSafe";
            stringArray[16] = "visit";
            stringArray[17] = "code";
            stringArray[18] = "length";
            stringArray[19] = "getAt";
            stringArray[20] = "length";
            stringArray[21] = "length";
            stringArray[22] = "getAt";
            stringArray[23] = "length";
            stringArray[24] = "getAt";
            stringArray[25] = "getMethods";
            stringArray[26] = "make";
        }

        private static /* synthetic */ CallSiteArray $createCallSiteArray() {
            String[] stringArray = new String[27];
            BuilderMethodReplacer.$createCallSiteArray_1(stringArray);
            return new CallSiteArray(BuilderMethodReplacer.class, stringArray);
        }

        private static /* synthetic */ CallSite[] $getCallSiteArray() {
            CallSiteArray callSiteArray;
            if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                callSiteArray = BuilderMethodReplacer.$createCallSiteArray();
                $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
            }
            return callSiteArray.array;
        }
    }
}

