/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast.builder;

import groovy.lang.Closure;
import groovy.lang.DelegatesTo;
import groovy.lang.GroovyInterceptable;
import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import groovy.lang.Range;
import groovy.lang.Reference;
import groovy.transform.Generated;
import groovy.transform.Internal;
import groovy.transform.stc.ClosureParams;
import groovy.transform.stc.FromString;
import java.beans.Transient;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.ConstructorNode;
import org.codehaus.groovy.ast.DynamicVariable;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.GenericsType;
import org.codehaus.groovy.ast.ImportNode;
import org.codehaus.groovy.ast.InnerClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.MixinNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.PropertyNode;
import org.codehaus.groovy.ast.VariableScope;
import org.codehaus.groovy.ast.expr.AnnotationConstantExpression;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.ArrayExpression;
import org.codehaus.groovy.ast.expr.AttributeExpression;
import org.codehaus.groovy.ast.expr.BinaryExpression;
import org.codehaus.groovy.ast.expr.BitwiseNegationExpression;
import org.codehaus.groovy.ast.expr.BooleanExpression;
import org.codehaus.groovy.ast.expr.CastExpression;
import org.codehaus.groovy.ast.expr.ClassExpression;
import org.codehaus.groovy.ast.expr.ClosureExpression;
import org.codehaus.groovy.ast.expr.ClosureListExpression;
import org.codehaus.groovy.ast.expr.ConstructorCallExpression;
import org.codehaus.groovy.ast.expr.DeclarationExpression;
import org.codehaus.groovy.ast.expr.ElvisOperatorExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.FieldExpression;
import org.codehaus.groovy.ast.expr.GStringExpression;
import org.codehaus.groovy.ast.expr.ListExpression;
import org.codehaus.groovy.ast.expr.MapEntryExpression;
import org.codehaus.groovy.ast.expr.MapExpression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.MethodPointerExpression;
import org.codehaus.groovy.ast.expr.NamedArgumentListExpression;
import org.codehaus.groovy.ast.expr.NotExpression;
import org.codehaus.groovy.ast.expr.PostfixExpression;
import org.codehaus.groovy.ast.expr.PrefixExpression;
import org.codehaus.groovy.ast.expr.PropertyExpression;
import org.codehaus.groovy.ast.expr.RangeExpression;
import org.codehaus.groovy.ast.expr.SpreadExpression;
import org.codehaus.groovy.ast.expr.SpreadMapExpression;
import org.codehaus.groovy.ast.expr.StaticMethodCallExpression;
import org.codehaus.groovy.ast.expr.TernaryExpression;
import org.codehaus.groovy.ast.expr.TupleExpression;
import org.codehaus.groovy.ast.expr.UnaryMinusExpression;
import org.codehaus.groovy.ast.expr.UnaryPlusExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.AssertStatement;
import org.codehaus.groovy.ast.stmt.BreakStatement;
import org.codehaus.groovy.ast.stmt.CaseStatement;
import org.codehaus.groovy.ast.stmt.CatchStatement;
import org.codehaus.groovy.ast.stmt.ContinueStatement;
import org.codehaus.groovy.ast.stmt.EmptyStatement;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.ast.stmt.ForStatement;
import org.codehaus.groovy.ast.stmt.IfStatement;
import org.codehaus.groovy.ast.stmt.ReturnStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.stmt.SwitchStatement;
import org.codehaus.groovy.ast.stmt.SynchronizedStatement;
import org.codehaus.groovy.ast.stmt.ThrowStatement;
import org.codehaus.groovy.ast.stmt.TryCatchStatement;
import org.codehaus.groovy.ast.stmt.WhileStatement;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.ArrayUtil;
import org.codehaus.groovy.runtime.BytecodeInterface8;
import org.codehaus.groovy.runtime.GStringImpl;
import org.codehaus.groovy.runtime.GeneratedClosure;
import org.codehaus.groovy.runtime.MethodClosure;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;
import org.codehaus.groovy.runtime.typehandling.ShortTypeHandling;
import org.codehaus.groovy.syntax.Token;
import org.codehaus.groovy.syntax.Types;

public class AstSpecificationCompiler
implements GroovyInterceptable {
    private final List<ASTNode> expression;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private transient /* synthetic */ MetaClass metaClass;
    private static /* synthetic */ SoftReference $callSiteArray;

    public AstSpecificationCompiler(@DelegatesTo(value=AstSpecificationCompiler.class) Closure spec) {
        MetaClass metaClass;
        List list;
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        this.expression = list = ScriptBytecodeAdapter.createList(new Object[0]);
        this.metaClass = metaClass = this.$getStaticMetaClass();
        AstSpecificationCompiler astSpecificationCompiler = this;
        ScriptBytecodeAdapter.setGroovyObjectProperty(astSpecificationCompiler, AstSpecificationCompiler.class, spec, "delegate");
        callSiteArray[0].call(spec);
    }

    public List<ASTNode> getExpression() {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        return this.expression;
    }

    /*
     * WARNING - void declaration
     */
    private List<ASTNode> enforceConstraints(String methodName, List<Class> spec) {
        void var2_2;
        Reference<String> methodName2 = new Reference<String>(methodName);
        Reference<void> spec2 = new Reference<void>(var2_2);
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        if (ScriptBytecodeAdapter.compareNotEqual(callSiteArray[1].call((List)spec2.get()), callSiteArray[2].call(this.expression))) {
            throw (Throwable)callSiteArray[3].callConstructor(IllegalArgumentException.class, new GStringImpl(new Object[]{methodName2.get(), (List)spec2.get(), ScriptBytecodeAdapter.getPropertySpreadSafe(AstSpecificationCompiler.class, this.expression, "class")}, new String[]{"", " could not be invoked. Expected to receive parameters ", " but found ", ""}));
        }
        public final class _enforceConstraints_closure1
        extends Closure
        implements GeneratedClosure {
            private /* synthetic */ Reference spec;
            private /* synthetic */ Reference methodName;
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _enforceConstraints_closure1(Object _outerInstance, Object _thisObject, Reference spec, Reference methodName) {
                Reference reference;
                Reference reference2;
                CallSite[] callSiteArray = _enforceConstraints_closure1.$getCallSiteArray();
                super(_outerInstance, _thisObject);
                this.spec = reference2 = spec;
                this.methodName = reference = methodName;
            }

            public Object doCall(int it) {
                CallSite[] callSiteArray = _enforceConstraints_closure1.$getCallSiteArray();
                Object actualClass = callSiteArray[0].callGetProperty(callSiteArray[1].call(callSiteArray[2].callGroovyObjectGetProperty(this), it));
                Object expectedClass = callSiteArray[3].call(this.spec.get(), it);
                if (!DefaultTypeTransformation.booleanUnbox(callSiteArray[4].call(expectedClass, actualClass))) {
                    throw (Throwable)callSiteArray[5].callConstructor(IllegalArgumentException.class, new GStringImpl(new Object[]{this.methodName.get(), this.spec.get(), ScriptBytecodeAdapter.getPropertySpreadSafe(_enforceConstraints_closure1.class, callSiteArray[6].callGroovyObjectGetProperty(this), "class")}, new String[]{"", " could not be invoked. Expected to receive parameters ", " but found ", ""}));
                }
                return callSiteArray[7].call(callSiteArray[8].callGroovyObjectGetProperty(this), it);
            }

            @Generated
            public Object call(int it) {
                CallSite[] callSiteArray = _enforceConstraints_closure1.$getCallSiteArray();
                if (__$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                    return callSiteArray[9].callCurrent((GroovyObject)this, it);
                }
                return this.doCall(it);
            }

            @Generated
            public List getSpec() {
                CallSite[] callSiteArray = _enforceConstraints_closure1.$getCallSiteArray();
                return (List)ScriptBytecodeAdapter.castToType(this.spec.get(), List.class);
            }

            @Generated
            public String getMethodName() {
                CallSite[] callSiteArray = _enforceConstraints_closure1.$getCallSiteArray();
                return ShortTypeHandling.castToString(this.methodName.get());
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (this.getClass() != _enforceConstraints_closure1.class) {
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
                stringArray[0] = "class";
                stringArray[1] = "getAt";
                stringArray[2] = "expression";
                stringArray[3] = "getAt";
                stringArray[4] = "isAssignableFrom";
                stringArray[5] = "<$constructor$>";
                stringArray[6] = "expression";
                stringArray[7] = "getAt";
                stringArray[8] = "expression";
                stringArray[9] = "doCall";
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[10];
                _enforceConstraints_closure1.$createCallSiteArray_1(stringArray);
                return new CallSiteArray(_enforceConstraints_closure1.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _enforceConstraints_closure1.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        return (List)ScriptBytecodeAdapter.castToType(callSiteArray[4].call((Object)ScriptBytecodeAdapter.createRange(0, callSiteArray[5].call(callSiteArray[6].call((List)spec2.get()), 1), false, false), new _enforceConstraints_closure1(this, this, spec2, methodName2)), List.class);
    }

    private void captureAndCreateNode(String name, @DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock, @ClosureParams(value=FromString.class, options={"java.util.List<org.codehaus.groovy.ast.ASTNode>"}) Closure ctorBlock) {
        Closure closure = argBlock;
        if (!(closure == null ? false : DefaultTypeTransformation.booleanUnbox(closure))) {
            throw (Throwable)new IllegalArgumentException(ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"nodes of type ", " require arguments to be specified"})));
        }
        ArrayList<ASTNode> oldProps = new ArrayList<ASTNode>(this.expression);
        this.expression.clear();
        new AstSpecificationCompiler(argBlock);
        Object result = ctorBlock.call((Object)this.expression);
        this.expression.clear();
        this.expression.addAll(oldProps);
        this.expression.add((ASTNode)result);
    }

    /*
     * WARNING - void declaration
     */
    private void makeNode(Class target, String typeAlias, List<Class<? super ASTNode>> ctorArgs, @DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        void var3_3;
        void var2_2;
        Reference<Class> target2 = new Reference<Class>(target);
        Reference<void> typeAlias2 = new Reference<void>(var2_2);
        Reference<void> ctorArgs2 = new Reference<void>(var3_3);
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        public final class _makeNode_closure2
        extends Closure
        implements GeneratedClosure {
            private /* synthetic */ Reference target;
            private /* synthetic */ Reference typeAlias;
            private /* synthetic */ Reference ctorArgs;
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _makeNode_closure2(Object _outerInstance, Object _thisObject, Reference target, Reference typeAlias, Reference ctorArgs) {
                Reference reference;
                Reference reference2;
                Reference reference3;
                CallSite[] callSiteArray = _makeNode_closure2.$getCallSiteArray();
                super(_outerInstance, _thisObject);
                this.target = reference3 = target;
                this.typeAlias = reference2 = typeAlias;
                this.ctorArgs = reference = ctorArgs;
            }

            public Object doCall(Object it) {
                CallSite[] callSiteArray = _makeNode_closure2.$getCallSiteArray();
                return callSiteArray[0].call(this.target.get(), ScriptBytecodeAdapter.despreadList(new Object[0], new Object[]{callSiteArray[1].callCurrent(this, this.typeAlias.get(), this.ctorArgs.get())}, new int[]{0}));
            }

            @Generated
            public Class getTarget() {
                CallSite[] callSiteArray = _makeNode_closure2.$getCallSiteArray();
                return ShortTypeHandling.castToClass(this.target.get());
            }

            @Generated
            public String getTypeAlias() {
                CallSite[] callSiteArray = _makeNode_closure2.$getCallSiteArray();
                return ShortTypeHandling.castToString(this.typeAlias.get());
            }

            @Generated
            public List getCtorArgs() {
                CallSite[] callSiteArray = _makeNode_closure2.$getCallSiteArray();
                return (List)ScriptBytecodeAdapter.castToType(this.ctorArgs.get(), List.class);
            }

            @Generated
            public Object doCall() {
                CallSite[] callSiteArray = _makeNode_closure2.$getCallSiteArray();
                return this.doCall(null);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (this.getClass() != _makeNode_closure2.class) {
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
                stringArray[0] = "newInstance";
                stringArray[1] = "enforceConstraints";
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[2];
                _makeNode_closure2.$createCallSiteArray_1(stringArray);
                return new CallSiteArray(_makeNode_closure2.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _makeNode_closure2.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        callSiteArray[7].callCurrent(this, callSiteArray[8].callGetProperty(target2.get()), argBlock, new _makeNode_closure2(this, this, target2, typeAlias2, ctorArgs2));
    }

    private void makeNodeFromList(Class target, @DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        Reference<Class> target2 = new Reference<Class>(target);
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        public final class _makeNodeFromList_closure3
        extends Closure
        implements GeneratedClosure {
            private /* synthetic */ Reference target;
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _makeNodeFromList_closure3(Object _outerInstance, Object _thisObject, Reference target) {
                Reference reference;
                CallSite[] callSiteArray = _makeNodeFromList_closure3.$getCallSiteArray();
                super(_outerInstance, _thisObject);
                this.target = reference = target;
            }

            public Object doCall(Object it) {
                CallSite[] callSiteArray = _makeNodeFromList_closure3.$getCallSiteArray();
                return callSiteArray[0].call(this.target.get(), callSiteArray[1].callConstructor(ArrayList.class, callSiteArray[2].callGroovyObjectGetProperty(this)));
            }

            @Generated
            public Class getTarget() {
                CallSite[] callSiteArray = _makeNodeFromList_closure3.$getCallSiteArray();
                return ShortTypeHandling.castToClass(this.target.get());
            }

            @Generated
            public Object doCall() {
                CallSite[] callSiteArray = _makeNodeFromList_closure3.$getCallSiteArray();
                return this.doCall(null);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (this.getClass() != _makeNodeFromList_closure3.class) {
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
                stringArray[0] = "newInstance";
                stringArray[1] = "<$constructor$>";
                stringArray[2] = "expression";
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[3];
                _makeNodeFromList_closure3.$createCallSiteArray_1(stringArray);
                return new CallSiteArray(_makeNodeFromList_closure3.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _makeNodeFromList_closure3.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        callSiteArray[9].callCurrent(this, callSiteArray[10].callGetProperty(target2.get()), argBlock, new _makeNodeFromList_closure3(this, this, target2));
    }

    private void makeListOfNodes(@DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock, String input) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        public final class _makeListOfNodes_closure4
        extends Closure
        implements GeneratedClosure {
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _makeListOfNodes_closure4(Object _outerInstance, Object _thisObject) {
                CallSite[] callSiteArray = _makeListOfNodes_closure4.$getCallSiteArray();
                super(_outerInstance, _thisObject);
            }

            public Object doCall(Object it) {
                CallSite[] callSiteArray = _makeListOfNodes_closure4.$getCallSiteArray();
                return callSiteArray[0].callConstructor(ArrayList.class, callSiteArray[1].callGroovyObjectGetProperty(this));
            }

            @Generated
            public Object doCall() {
                CallSite[] callSiteArray = _makeListOfNodes_closure4.$getCallSiteArray();
                return this.doCall(null);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (this.getClass() != _makeListOfNodes_closure4.class) {
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
                stringArray[0] = "<$constructor$>";
                stringArray[1] = "expression";
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[2];
                _makeListOfNodes_closure4.$createCallSiteArray_1(stringArray);
                return new CallSiteArray(_makeListOfNodes_closure4.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _makeListOfNodes_closure4.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        callSiteArray[11].callCurrent(this, input, argBlock, new _makeListOfNodes_closure4(this, this));
    }

    private void makeArrayOfNodes(Object target, @DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        Reference<Object> target2 = new Reference<Object>(target);
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        public final class _makeArrayOfNodes_closure5
        extends Closure
        implements GeneratedClosure {
            private /* synthetic */ Reference target;
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _makeArrayOfNodes_closure5(Object _outerInstance, Object _thisObject, Reference target) {
                Reference reference;
                CallSite[] callSiteArray = _makeArrayOfNodes_closure5.$getCallSiteArray();
                super(_outerInstance, _thisObject);
                this.target = reference = target;
            }

            public Object doCall(Object it) {
                CallSite[] callSiteArray = _makeArrayOfNodes_closure5.$getCallSiteArray();
                return callSiteArray[0].call(callSiteArray[1].callGroovyObjectGetProperty(this), this.target.get());
            }

            @Generated
            public Object getTarget() {
                CallSite[] callSiteArray = _makeArrayOfNodes_closure5.$getCallSiteArray();
                return this.target.get();
            }

            @Generated
            public Object doCall() {
                CallSite[] callSiteArray = _makeArrayOfNodes_closure5.$getCallSiteArray();
                return this.doCall(null);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (this.getClass() != _makeArrayOfNodes_closure5.class) {
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
                stringArray[0] = "toArray";
                stringArray[1] = "expression";
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[2];
                _makeArrayOfNodes_closure5.$createCallSiteArray_1(stringArray);
                return new CallSiteArray(_makeArrayOfNodes_closure5.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _makeArrayOfNodes_closure5.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        callSiteArray[12].callCurrent(this, callSiteArray[13].callGetProperty(callSiteArray[14].callGetProperty(target2.get())), argBlock, new _makeArrayOfNodes_closure5(this, this, target2));
    }

    /*
     * WARNING - void declaration
     */
    private void makeNodeWithClassParameter(Class target, String alias, List<Class> spec, @DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock, Class type) {
        void var3_3;
        void var2_2;
        Reference<Class> target2 = new Reference<Class>(target);
        Reference<void> alias2 = new Reference<void>(var2_2);
        Reference<void> spec2 = new Reference<void>(var3_3);
        Reference<Class> type2 = new Reference<Class>(type);
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        public final class _makeNodeWithClassParameter_closure6
        extends Closure
        implements GeneratedClosure {
            private /* synthetic */ Reference type;
            private /* synthetic */ Reference target;
            private /* synthetic */ Reference alias;
            private /* synthetic */ Reference spec;
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _makeNodeWithClassParameter_closure6(Object _outerInstance, Object _thisObject, Reference type, Reference target, Reference alias, Reference spec) {
                Reference reference;
                Reference reference2;
                Reference reference3;
                Reference reference4;
                CallSite[] callSiteArray = _makeNodeWithClassParameter_closure6.$getCallSiteArray();
                super(_outerInstance, _thisObject);
                this.type = reference4 = type;
                this.target = reference3 = target;
                this.alias = reference2 = alias;
                this.spec = reference = spec;
            }

            public Object doCall(Object it) {
                CallSite[] callSiteArray = _makeNodeWithClassParameter_closure6.$getCallSiteArray();
                callSiteArray[0].call(callSiteArray[1].callGroovyObjectGetProperty(this), 0, callSiteArray[2].call(ClassHelper.class, this.type.get()));
                return callSiteArray[3].call(this.target.get(), ScriptBytecodeAdapter.despreadList(new Object[0], new Object[]{callSiteArray[4].callCurrent(this, this.alias.get(), this.spec.get())}, new int[]{0}));
            }

            @Generated
            public Class getType() {
                CallSite[] callSiteArray = _makeNodeWithClassParameter_closure6.$getCallSiteArray();
                return ShortTypeHandling.castToClass(this.type.get());
            }

            @Generated
            public Class getTarget() {
                CallSite[] callSiteArray = _makeNodeWithClassParameter_closure6.$getCallSiteArray();
                return ShortTypeHandling.castToClass(this.target.get());
            }

            @Generated
            public String getAlias() {
                CallSite[] callSiteArray = _makeNodeWithClassParameter_closure6.$getCallSiteArray();
                return ShortTypeHandling.castToString(this.alias.get());
            }

            @Generated
            public List getSpec() {
                CallSite[] callSiteArray = _makeNodeWithClassParameter_closure6.$getCallSiteArray();
                return (List)ScriptBytecodeAdapter.castToType(this.spec.get(), List.class);
            }

            @Generated
            public Object doCall() {
                CallSite[] callSiteArray = _makeNodeWithClassParameter_closure6.$getCallSiteArray();
                return this.doCall(null);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (this.getClass() != _makeNodeWithClassParameter_closure6.class) {
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
                stringArray[0] = "add";
                stringArray[1] = "expression";
                stringArray[2] = "make";
                stringArray[3] = "newInstance";
                stringArray[4] = "enforceConstraints";
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[5];
                _makeNodeWithClassParameter_closure6.$createCallSiteArray_1(stringArray);
                return new CallSiteArray(_makeNodeWithClassParameter_closure6.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _makeNodeWithClassParameter_closure6.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        callSiteArray[15].callCurrent(this, callSiteArray[16].callGetProperty(target2.get()), argBlock, new _makeNodeWithClassParameter_closure6(this, this, type2, target2, alias2, spec2));
    }

    /*
     * WARNING - void declaration
     */
    private void makeNodeWithStringParameter(Class target, String alias, List<Class> spec, @DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock, String text) {
        void var3_3;
        void var2_2;
        Reference<Class> target2 = new Reference<Class>(target);
        Reference<void> alias2 = new Reference<void>(var2_2);
        Reference<void> spec2 = new Reference<void>(var3_3);
        Reference<String> text2 = new Reference<String>(text);
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        public final class _makeNodeWithStringParameter_closure7
        extends Closure
        implements GeneratedClosure {
            private /* synthetic */ Reference text;
            private /* synthetic */ Reference target;
            private /* synthetic */ Reference alias;
            private /* synthetic */ Reference spec;
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _makeNodeWithStringParameter_closure7(Object _outerInstance, Object _thisObject, Reference text, Reference target, Reference alias, Reference spec) {
                Reference reference;
                Reference reference2;
                Reference reference3;
                Reference reference4;
                CallSite[] callSiteArray = _makeNodeWithStringParameter_closure7.$getCallSiteArray();
                super(_outerInstance, _thisObject);
                this.text = reference4 = text;
                this.target = reference3 = target;
                this.alias = reference2 = alias;
                this.spec = reference = spec;
            }

            public Object doCall(Object it) {
                CallSite[] callSiteArray = _makeNodeWithStringParameter_closure7.$getCallSiteArray();
                callSiteArray[0].call(callSiteArray[1].callGroovyObjectGetProperty(this), 0, this.text.get());
                return callSiteArray[2].call(this.target.get(), ScriptBytecodeAdapter.despreadList(new Object[0], new Object[]{callSiteArray[3].callCurrent(this, this.alias.get(), this.spec.get())}, new int[]{0}));
            }

            @Generated
            public String getText() {
                CallSite[] callSiteArray = _makeNodeWithStringParameter_closure7.$getCallSiteArray();
                return ShortTypeHandling.castToString(this.text.get());
            }

            @Generated
            public Class getTarget() {
                CallSite[] callSiteArray = _makeNodeWithStringParameter_closure7.$getCallSiteArray();
                return ShortTypeHandling.castToClass(this.target.get());
            }

            @Generated
            public String getAlias() {
                CallSite[] callSiteArray = _makeNodeWithStringParameter_closure7.$getCallSiteArray();
                return ShortTypeHandling.castToString(this.alias.get());
            }

            @Generated
            public List getSpec() {
                CallSite[] callSiteArray = _makeNodeWithStringParameter_closure7.$getCallSiteArray();
                return (List)ScriptBytecodeAdapter.castToType(this.spec.get(), List.class);
            }

            @Generated
            public Object doCall() {
                CallSite[] callSiteArray = _makeNodeWithStringParameter_closure7.$getCallSiteArray();
                return this.doCall(null);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (this.getClass() != _makeNodeWithStringParameter_closure7.class) {
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
                stringArray[0] = "add";
                stringArray[1] = "expression";
                stringArray[2] = "newInstance";
                stringArray[3] = "enforceConstraints";
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[4];
                _makeNodeWithStringParameter_closure7.$createCallSiteArray_1(stringArray);
                return new CallSiteArray(_makeNodeWithStringParameter_closure7.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _makeNodeWithStringParameter_closure7.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        callSiteArray[17].callCurrent(this, callSiteArray[18].callGetProperty(target2.get()), argBlock, new _makeNodeWithStringParameter_closure7(this, this, text2, target2, alias2, spec2));
    }

    public void cast(Class type, @DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[19].callCurrent((GroovyObject)this, ArrayUtil.createArray(CastExpression.class, "cast", ScriptBytecodeAdapter.createList(new Object[]{ClassNode.class, Expression.class}), argBlock, type));
    }

    public void constructorCall(Class type, @DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[20].callCurrent((GroovyObject)this, ArrayUtil.createArray(ConstructorCallExpression.class, "constructorCall", ScriptBytecodeAdapter.createList(new Object[]{ClassNode.class, Expression.class}), argBlock, type));
    }

    public void methodCall(@DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[21].callCurrent(this, MethodCallExpression.class, "methodCall", ScriptBytecodeAdapter.createList(new Object[]{Expression.class, Expression.class, Expression.class}), argBlock);
    }

    public void annotationConstant(@DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[22].callCurrent(this, AnnotationConstantExpression.class, "annotationConstant", ScriptBytecodeAdapter.createList(new Object[]{AnnotationNode.class}), argBlock);
    }

    public void postfix(@DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[23].callCurrent(this, PostfixExpression.class, "postfix", ScriptBytecodeAdapter.createList(new Object[]{Expression.class, Token.class}), argBlock);
    }

    public void field(@DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[24].callCurrent(this, FieldExpression.class, "field", ScriptBytecodeAdapter.createList(new Object[]{FieldNode.class}), argBlock);
    }

    public void map(@DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[25].callCurrent(this, MapExpression.class, argBlock);
    }

    public void tuple(@DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[26].callCurrent(this, TupleExpression.class, argBlock);
    }

    public void mapEntry(@DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[27].callCurrent(this, MapEntryExpression.class, "mapEntry", ScriptBytecodeAdapter.createList(new Object[]{Expression.class, Expression.class}), argBlock);
    }

    public void gString(String verbatimText, @DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[28].callCurrent((GroovyObject)this, ArrayUtil.createArray(GStringExpression.class, "gString", ScriptBytecodeAdapter.createList(new Object[]{String.class, List.class, List.class}), argBlock, verbatimText));
    }

    public void methodPointer(@DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[29].callCurrent(this, MethodPointerExpression.class, "methodPointer", ScriptBytecodeAdapter.createList(new Object[]{Expression.class, Expression.class}), argBlock);
    }

    public void property(@DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[30].callCurrent(this, PropertyExpression.class, "property", ScriptBytecodeAdapter.createList(new Object[]{Expression.class, Expression.class}), argBlock);
    }

    public void range(@DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[31].callCurrent(this, RangeExpression.class, "range", ScriptBytecodeAdapter.createList(new Object[]{Expression.class, Expression.class, Boolean.class}), argBlock);
    }

    public void empty() {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[32].call(this.expression, callSiteArray[33].callGetProperty(EmptyStatement.class));
    }

    public void label(String label) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[34].call(this.expression, label);
    }

    public void importNode(Class target, String alias) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[35].call(this.expression, callSiteArray[36].callConstructor(ImportNode.class, callSiteArray[37].call(ClassHelper.class, target), alias));
    }

    public void catchStatement(@DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[38].callCurrent(this, CatchStatement.class, "catchStatement", ScriptBytecodeAdapter.createList(new Object[]{Parameter.class, Statement.class}), argBlock);
    }

    public void throwStatement(@DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[39].callCurrent(this, ThrowStatement.class, "throwStatement", ScriptBytecodeAdapter.createList(new Object[]{Expression.class}), argBlock);
    }

    public void synchronizedStatement(@DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[40].callCurrent(this, SynchronizedStatement.class, "synchronizedStatement", ScriptBytecodeAdapter.createList(new Object[]{Expression.class, Statement.class}), argBlock);
    }

    public void returnStatement(@DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[41].callCurrent(this, ReturnStatement.class, "returnStatement", ScriptBytecodeAdapter.createList(new Object[]{Expression.class}), argBlock);
    }

    private void ternary(@DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[42].callCurrent(this, TernaryExpression.class, "ternary", ScriptBytecodeAdapter.createList(new Object[]{BooleanExpression.class, Expression.class, Expression.class}), argBlock);
    }

    public void elvisOperator(@DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[43].callCurrent(this, ElvisOperatorExpression.class, "elvisOperator", ScriptBytecodeAdapter.createList(new Object[]{Expression.class, Expression.class}), argBlock);
    }

    public void breakStatement(String label) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        if (DefaultTypeTransformation.booleanUnbox(label)) {
            callSiteArray[44].call(this.expression, callSiteArray[45].callConstructor(BreakStatement.class, label));
        } else {
            callSiteArray[46].call(this.expression, callSiteArray[47].callConstructor(BreakStatement.class));
        }
    }

    public void continueStatement(@DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        if (!DefaultTypeTransformation.booleanUnbox(argBlock)) {
            callSiteArray[48].call(this.expression, callSiteArray[49].callConstructor(ContinueStatement.class));
        } else {
            callSiteArray[50].callCurrent(this, ContinueStatement.class, "continueStatement", ScriptBytecodeAdapter.createList(new Object[]{String.class}), argBlock);
        }
    }

    public void caseStatement(@DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[51].callCurrent(this, CaseStatement.class, "caseStatement", ScriptBytecodeAdapter.createList(new Object[]{Expression.class, Statement.class}), argBlock);
    }

    public void defaultCase(@DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[52].callCurrent((GroovyObject)this, argBlock);
    }

    public void prefix(@DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[53].callCurrent(this, PrefixExpression.class, "prefix", ScriptBytecodeAdapter.createList(new Object[]{Token.class, Expression.class}), argBlock);
    }

    public void not(@DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[54].callCurrent(this, NotExpression.class, "not", ScriptBytecodeAdapter.createList(new Object[]{Expression.class}), argBlock);
    }

    public void dynamicVariable(String variable, boolean isStatic) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[55].call(this.expression, callSiteArray[56].callConstructor(DynamicVariable.class, variable, isStatic));
    }

    public void exceptions(@DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[57].callCurrent(this, ScriptBytecodeAdapter.createPojoWrapper((ClassNode[])ScriptBytecodeAdapter.asType(ScriptBytecodeAdapter.createList(new Object[0]), ClassNode[].class), ClassNode[].class), argBlock);
    }

    public void annotations(@DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[58].callCurrent(this, argBlock, "List<AnnotationNode>");
    }

    public void methods(@DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[59].callCurrent(this, argBlock, "List<MethodNode>");
    }

    public void constructors(@DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[60].callCurrent(this, argBlock, "List<ConstructorNode>");
    }

    public void properties(@DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[61].callCurrent(this, argBlock, "List<PropertyNode>");
    }

    public void fields(@DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[62].callCurrent(this, argBlock, "List<FieldNode>");
    }

    public void strings(@DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[63].callCurrent(this, argBlock, "List<ConstantExpression>");
    }

    public void values(@DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[64].callCurrent(this, argBlock, "List<Expression>");
    }

    public void inclusive(boolean value) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[65].call(this.expression, value);
    }

    public void constant(Object value) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[66].call(this.expression, callSiteArray[67].callStatic(GeneralUtils.class, value));
    }

    public void ifStatement(@DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[68].callCurrent(this, IfStatement.class, "ifStatement", ScriptBytecodeAdapter.createList(new Object[]{BooleanExpression.class, Statement.class, Statement.class}), argBlock);
    }

    public void spread(@DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[69].callCurrent(this, SpreadExpression.class, "spread", ScriptBytecodeAdapter.createList(new Object[]{Expression.class}), argBlock);
    }

    public void spreadMap(@DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[70].callCurrent(this, SpreadMapExpression.class, "spreadMap", ScriptBytecodeAdapter.createList(new Object[]{Expression.class}), argBlock);
    }

    public void whileStatement(@DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[71].callCurrent(this, WhileStatement.class, "whileStatement", ScriptBytecodeAdapter.createList(new Object[]{BooleanExpression.class, Statement.class}), argBlock);
    }

    public void forStatement(@DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[72].callCurrent(this, ForStatement.class, "forStatement", ScriptBytecodeAdapter.createList(new Object[]{Parameter.class, Expression.class, Statement.class}), argBlock);
    }

    public void closureList(@DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[73].callCurrent(this, ClosureListExpression.class, argBlock);
    }

    public void declaration(@DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[74].callCurrent(this, DeclarationExpression.class, "declaration", ScriptBytecodeAdapter.createList(new Object[]{Expression.class, Token.class, Expression.class}), argBlock);
    }

    public void list(@DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[75].callCurrent(this, ListExpression.class, argBlock);
    }

    public void bitwiseNegation(@DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[76].callCurrent(this, BitwiseNegationExpression.class, "bitwiseNegation", ScriptBytecodeAdapter.createList(new Object[]{Expression.class}), argBlock);
    }

    public void closure(@DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[77].callCurrent(this, ClosureExpression.class, "closure", ScriptBytecodeAdapter.createList(new Object[]{Parameter[].class, Statement.class}), argBlock);
    }

    public void booleanExpression(@DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[78].callCurrent(this, BooleanExpression.class, "booleanExpression", ScriptBytecodeAdapter.createList(new Object[]{Expression.class}), argBlock);
    }

    public void binary(@DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[79].callCurrent(this, BinaryExpression.class, "binary", ScriptBytecodeAdapter.createList(new Object[]{Expression.class, Token.class, Expression.class}), argBlock);
    }

    public void unaryPlus(@DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[80].callCurrent(this, UnaryPlusExpression.class, "unaryPlus", ScriptBytecodeAdapter.createList(new Object[]{Expression.class}), argBlock);
    }

    public void classExpression(Class type) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[81].call(this.expression, callSiteArray[82].callConstructor(ClassExpression.class, callSiteArray[83].call(ClassHelper.class, type)));
    }

    public void unaryMinus(@DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[84].callCurrent(this, UnaryMinusExpression.class, "unaryMinus", ScriptBytecodeAdapter.createList(new Object[]{Expression.class}), argBlock);
    }

    public void attribute(@DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[85].callCurrent(this, AttributeExpression.class, "attribute", ScriptBytecodeAdapter.createList(new Object[]{Expression.class, Expression.class}), argBlock);
    }

    public void expression(@DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[86].callCurrent(this, ExpressionStatement.class, "expression", ScriptBytecodeAdapter.createList(new Object[]{Expression.class}), argBlock);
    }

    public void namedArgumentList(@DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[87].callCurrent(this, NamedArgumentListExpression.class, argBlock);
    }

    public void interfaces(@DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[88].callCurrent(this, argBlock, "List<ClassNode>");
    }

    public void mixins(@DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[89].callCurrent(this, argBlock, "List<MixinNode>");
    }

    public void genericsTypes(@DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[90].callCurrent(this, argBlock, "List<GenericsTypes>");
    }

    public void classNode(Class target) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[91].call(this.expression, callSiteArray[92].call(ClassHelper.class, target, false));
    }

    public void parameters(@DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[93].callCurrent(this, ScriptBytecodeAdapter.createPojoWrapper((Parameter[])ScriptBytecodeAdapter.asType(ScriptBytecodeAdapter.createList(new Object[0]), Parameter[].class), Parameter[].class), argBlock);
    }

    public void block(@DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        public final class _block_closure8
        extends Closure
        implements GeneratedClosure {
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _block_closure8(Object _outerInstance, Object _thisObject) {
                CallSite[] callSiteArray = _block_closure8.$getCallSiteArray();
                super(_outerInstance, _thisObject);
            }

            public Object doCall(Object it) {
                CallSite[] callSiteArray = _block_closure8.$getCallSiteArray();
                return callSiteArray[0].callStatic(GeneralUtils.class, callSiteArray[1].callConstructor(VariableScope.class), callSiteArray[2].callConstructor(ArrayList.class, callSiteArray[3].callGroovyObjectGetProperty(this)));
            }

            @Generated
            public Object doCall() {
                CallSite[] callSiteArray = _block_closure8.$getCallSiteArray();
                return this.doCall(null);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (this.getClass() != _block_closure8.class) {
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
                stringArray[0] = "block";
                stringArray[1] = "<$constructor$>";
                stringArray[2] = "<$constructor$>";
                stringArray[3] = "expression";
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[4];
                _block_closure8.$createCallSiteArray_1(stringArray);
                return new CallSiteArray(_block_closure8.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _block_closure8.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        callSiteArray[94].callCurrent(this, "BlockStatement", argBlock, new _block_closure8(this, this));
    }

    public void parameter(Map<String, Class> args, @DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        Reference<Closure> argBlock2 = new Reference<Closure>(argBlock);
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        if (!DefaultTypeTransformation.booleanUnbox(args)) {
            throw (Throwable)callSiteArray[95].callConstructor(IllegalArgumentException.class);
        }
        if (ScriptBytecodeAdapter.compareGreaterThan(callSiteArray[96].call(args), 1)) {
            throw (Throwable)callSiteArray[97].callConstructor(IllegalArgumentException.class);
        }
        if (DefaultTypeTransformation.booleanUnbox(argBlock2.get())) {
            public final class _parameter_closure9
            extends Closure
            implements GeneratedClosure {
                private /* synthetic */ Reference argBlock;
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;
                private static /* synthetic */ SoftReference $callSiteArray;

                public _parameter_closure9(Object _outerInstance, Object _thisObject, Reference argBlock) {
                    Reference reference;
                    CallSite[] callSiteArray = _parameter_closure9.$getCallSiteArray();
                    super(_outerInstance, _thisObject);
                    this.argBlock = reference = argBlock;
                }

                /*
                 * WARNING - void declaration
                 */
                public Object doCall(Object name, Object type) {
                    void var2_2;
                    Reference<Object> name2 = new Reference<Object>(name);
                    Reference<void> type2 = new Reference<void>(var2_2);
                    CallSite[] callSiteArray = _parameter_closure9.$getCallSiteArray();
                    public final class _closure28
                    extends Closure
                    implements GeneratedClosure {
                        private /* synthetic */ Reference type;
                        private /* synthetic */ Reference name;
                        private static /* synthetic */ ClassInfo $staticClassInfo;
                        public static transient /* synthetic */ boolean __$stMC;
                        private static /* synthetic */ SoftReference $callSiteArray;

                        public _closure28(Object _outerInstance, Object _thisObject, Reference type, Reference name) {
                            Reference reference;
                            Reference reference2;
                            CallSite[] callSiteArray = _closure28.$getCallSiteArray();
                            super(_outerInstance, _thisObject);
                            this.type = reference2 = type;
                            this.name = reference = name;
                        }

                        public Object doCall(Object it) {
                            CallSite[] callSiteArray = _closure28.$getCallSiteArray();
                            return callSiteArray[0].callStatic(GeneralUtils.class, callSiteArray[1].call(ClassHelper.class, this.type.get()), this.name.get(), callSiteArray[2].call(callSiteArray[3].callGroovyObjectGetProperty(this), 0));
                        }

                        @Generated
                        public Object getType() {
                            CallSite[] callSiteArray = _closure28.$getCallSiteArray();
                            return this.type.get();
                        }

                        @Generated
                        public Object getName() {
                            CallSite[] callSiteArray = _closure28.$getCallSiteArray();
                            return this.name.get();
                        }

                        @Generated
                        public Object doCall() {
                            CallSite[] callSiteArray = _closure28.$getCallSiteArray();
                            return this.doCall(null);
                        }

                        protected /* synthetic */ MetaClass $getStaticMetaClass() {
                            if (this.getClass() != _closure28.class) {
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
                            stringArray[0] = "param";
                            stringArray[1] = "make";
                            stringArray[2] = "getAt";
                            stringArray[3] = "expression";
                        }

                        private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                            String[] stringArray = new String[4];
                            _closure28.$createCallSiteArray_1(stringArray);
                            return new CallSiteArray(_closure28.class, stringArray);
                        }

                        private static /* synthetic */ CallSite[] $getCallSiteArray() {
                            CallSiteArray callSiteArray;
                            if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                                callSiteArray = _closure28.$createCallSiteArray();
                                $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                            }
                            return callSiteArray.array;
                        }
                    }
                    return callSiteArray[0].callCurrent(this, "Parameter", this.argBlock.get(), new _closure28(this, this.getThisObject(), type2, name2));
                }

                /*
                 * WARNING - void declaration
                 */
                @Generated
                public Object call(Object name, Object type) {
                    void var2_2;
                    Reference<Object> name2 = new Reference<Object>(name);
                    Reference<void> type2 = new Reference<void>(var2_2);
                    CallSite[] callSiteArray = _parameter_closure9.$getCallSiteArray();
                    return callSiteArray[1].callCurrent(this, name2.get(), type2.get());
                }

                @Generated
                public Closure getArgBlock() {
                    CallSite[] callSiteArray = _parameter_closure9.$getCallSiteArray();
                    return (Closure)ScriptBytecodeAdapter.castToType(this.argBlock.get(), Closure.class);
                }

                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                    if (this.getClass() != _parameter_closure9.class) {
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
                    stringArray[0] = "captureAndCreateNode";
                    stringArray[1] = "doCall";
                }

                private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                    String[] stringArray = new String[2];
                    _parameter_closure9.$createCallSiteArray_1(stringArray);
                    return new CallSiteArray(_parameter_closure9.class, stringArray);
                }

                private static /* synthetic */ CallSite[] $getCallSiteArray() {
                    CallSiteArray callSiteArray;
                    if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                        callSiteArray = _parameter_closure9.$createCallSiteArray();
                        $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                    }
                    return callSiteArray.array;
                }
            }
            callSiteArray[98].call(args, new _parameter_closure9(this, this, argBlock2));
        } else {
            public final class _parameter_closure10
            extends Closure
            implements GeneratedClosure {
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;
                private static /* synthetic */ SoftReference $callSiteArray;

                public _parameter_closure10(Object _outerInstance, Object _thisObject) {
                    CallSite[] callSiteArray = _parameter_closure10.$getCallSiteArray();
                    super(_outerInstance, _thisObject);
                }

                public Object doCall(Object name, Object type) {
                    CallSite[] callSiteArray = _parameter_closure10.$getCallSiteArray();
                    return callSiteArray[0].call(callSiteArray[1].callGroovyObjectGetProperty(this), callSiteArray[2].callStatic(GeneralUtils.class, callSiteArray[3].call(ClassHelper.class, type), name));
                }

                @Generated
                public Object call(Object name, Object type) {
                    CallSite[] callSiteArray = _parameter_closure10.$getCallSiteArray();
                    return callSiteArray[4].callCurrent(this, name, type);
                }

                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                    if (this.getClass() != _parameter_closure10.class) {
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
                    stringArray[0] = "leftShift";
                    stringArray[1] = "expression";
                    stringArray[2] = "param";
                    stringArray[3] = "make";
                    stringArray[4] = "doCall";
                }

                private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                    String[] stringArray = new String[5];
                    _parameter_closure10.$createCallSiteArray_1(stringArray);
                    return new CallSiteArray(_parameter_closure10.class, stringArray);
                }

                private static /* synthetic */ CallSite[] $getCallSiteArray() {
                    CallSiteArray callSiteArray;
                    if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                        callSiteArray = _parameter_closure10.$createCallSiteArray();
                        $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                    }
                    return callSiteArray.array;
                }
            }
            callSiteArray[99].call(args, new _parameter_closure10(this, this));
        }
    }

    public void array(Class type, @DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        Reference<Class> type2 = new Reference<Class>(type);
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        public final class _array_closure11
        extends Closure
        implements GeneratedClosure {
            private /* synthetic */ Reference type;
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _array_closure11(Object _outerInstance, Object _thisObject, Reference type) {
                Reference reference;
                CallSite[] callSiteArray = _array_closure11.$getCallSiteArray();
                super(_outerInstance, _thisObject);
                this.type = reference = type;
            }

            public Object doCall(Object it) {
                CallSite[] callSiteArray = _array_closure11.$getCallSiteArray();
                return callSiteArray[0].callConstructor(ArrayExpression.class, callSiteArray[1].call(ClassHelper.class, this.type.get()), callSiteArray[2].callConstructor(ArrayList.class, callSiteArray[3].callGroovyObjectGetProperty(this)));
            }

            @Generated
            public Class getType() {
                CallSite[] callSiteArray = _array_closure11.$getCallSiteArray();
                return ShortTypeHandling.castToClass(this.type.get());
            }

            @Generated
            public Object doCall() {
                CallSite[] callSiteArray = _array_closure11.$getCallSiteArray();
                return this.doCall(null);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (this.getClass() != _array_closure11.class) {
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
                stringArray[0] = "<$constructor$>";
                stringArray[1] = "make";
                stringArray[2] = "<$constructor$>";
                stringArray[3] = "expression";
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[4];
                _array_closure11.$createCallSiteArray_1(stringArray);
                return new CallSiteArray(_array_closure11.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _array_closure11.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        callSiteArray[100].callCurrent(this, "ArrayExpression", argBlock, new _array_closure11(this, this, type2));
    }

    public void genericsType(Class type, @DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        Reference<Class> type2 = new Reference<Class>(type);
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        if (DefaultTypeTransformation.booleanUnbox(argBlock)) {
            public final class _genericsType_closure12
            extends Closure
            implements GeneratedClosure {
                private /* synthetic */ Reference type;
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;
                private static /* synthetic */ SoftReference $callSiteArray;

                public _genericsType_closure12(Object _outerInstance, Object _thisObject, Reference type) {
                    Reference reference;
                    CallSite[] callSiteArray = _genericsType_closure12.$getCallSiteArray();
                    super(_outerInstance, _thisObject);
                    this.type = reference = type;
                }

                public Object doCall(Object it) {
                    CallSite[] callSiteArray = _genericsType_closure12.$getCallSiteArray();
                    return callSiteArray[0].callConstructor(GenericsType.class, callSiteArray[1].call(ClassHelper.class, this.type.get()), ScriptBytecodeAdapter.createPojoWrapper((ClassNode[])ScriptBytecodeAdapter.asType(callSiteArray[2].call(callSiteArray[3].callGroovyObjectGetProperty(this), 0), ClassNode[].class), ClassNode[].class), callSiteArray[4].call(callSiteArray[5].callGroovyObjectGetProperty(this), 1));
                }

                @Generated
                public Class getType() {
                    CallSite[] callSiteArray = _genericsType_closure12.$getCallSiteArray();
                    return ShortTypeHandling.castToClass(this.type.get());
                }

                @Generated
                public Object doCall() {
                    CallSite[] callSiteArray = _genericsType_closure12.$getCallSiteArray();
                    return this.doCall(null);
                }

                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                    if (this.getClass() != _genericsType_closure12.class) {
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
                    stringArray[0] = "<$constructor$>";
                    stringArray[1] = "make";
                    stringArray[2] = "getAt";
                    stringArray[3] = "expression";
                    stringArray[4] = "getAt";
                    stringArray[5] = "expression";
                }

                private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                    String[] stringArray = new String[6];
                    _genericsType_closure12.$createCallSiteArray_1(stringArray);
                    return new CallSiteArray(_genericsType_closure12.class, stringArray);
                }

                private static /* synthetic */ CallSite[] $getCallSiteArray() {
                    CallSiteArray callSiteArray;
                    if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                        callSiteArray = _genericsType_closure12.$createCallSiteArray();
                        $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                    }
                    return callSiteArray.array;
                }
            }
            callSiteArray[101].callCurrent(this, "GenericsType", argBlock, new _genericsType_closure12(this, this, type2));
        } else {
            callSiteArray[102].call(this.expression, callSiteArray[103].callConstructor(GenericsType.class, callSiteArray[104].call(ClassHelper.class, type2.get())));
        }
    }

    public void upperBound(@DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[105].callCurrent(this, argBlock, "List<ClassNode>");
    }

    public void lowerBound(Class target) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[106].call(this.expression, callSiteArray[107].call(ClassHelper.class, target));
    }

    public void member(String name, @DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        Reference<String> name2 = new Reference<String>(name);
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        public final class _member_closure13
        extends Closure
        implements GeneratedClosure {
            private /* synthetic */ Reference name;
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _member_closure13(Object _outerInstance, Object _thisObject, Reference name) {
                Reference reference;
                CallSite[] callSiteArray = _member_closure13.$getCallSiteArray();
                super(_outerInstance, _thisObject);
                this.name = reference = name;
            }

            public Object doCall(Object it) {
                CallSite[] callSiteArray = _member_closure13.$getCallSiteArray();
                return ScriptBytecodeAdapter.createList(new Object[]{this.name.get(), callSiteArray[0].call(callSiteArray[1].callGroovyObjectGetProperty(this), 0)});
            }

            @Generated
            public String getName() {
                CallSite[] callSiteArray = _member_closure13.$getCallSiteArray();
                return ShortTypeHandling.castToString(this.name.get());
            }

            @Generated
            public Object doCall() {
                CallSite[] callSiteArray = _member_closure13.$getCallSiteArray();
                return this.doCall(null);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (this.getClass() != _member_closure13.class) {
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
                stringArray[0] = "getAt";
                stringArray[1] = "expression";
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[2];
                _member_closure13.$createCallSiteArray_1(stringArray);
                return new CallSiteArray(_member_closure13.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _member_closure13.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        callSiteArray[108].callCurrent(this, "Annotation Member", argBlock, new _member_closure13(this, this, name2));
    }

    public void argumentList(@DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        if (!DefaultTypeTransformation.booleanUnbox(argBlock)) {
            callSiteArray[109].call(this.expression, callSiteArray[110].callConstructor(ArgumentListExpression.class));
        } else {
            callSiteArray[111].callCurrent(this, ArgumentListExpression.class, argBlock);
        }
    }

    public void annotation(Class target, @DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        Reference<Class> target2 = new Reference<Class>(target);
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        if (DefaultTypeTransformation.booleanUnbox(argBlock)) {
            public final class _annotation_closure14
            extends Closure
            implements GeneratedClosure {
                private /* synthetic */ Reference target;
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;
                private static /* synthetic */ SoftReference $callSiteArray;

                public _annotation_closure14(Object _outerInstance, Object _thisObject, Reference target) {
                    Reference reference;
                    CallSite[] callSiteArray = _annotation_closure14.$getCallSiteArray();
                    super(_outerInstance, _thisObject);
                    this.target = reference = target;
                }

                public Object doCall(Object it) {
                    CallSite[] callSiteArray = _annotation_closure14.$getCallSiteArray();
                    Reference<Object> node = new Reference<Object>(callSiteArray[0].callConstructor(AnnotationNode.class, callSiteArray[1].call(ClassHelper.class, this.target.get())));
                    public final class _closure29
                    extends Closure
                    implements GeneratedClosure {
                        private /* synthetic */ Reference node;
                        private static /* synthetic */ ClassInfo $staticClassInfo;
                        public static transient /* synthetic */ boolean __$stMC;
                        private static /* synthetic */ SoftReference $callSiteArray;

                        public _closure29(Object _outerInstance, Object _thisObject, Reference node) {
                            Reference reference;
                            CallSite[] callSiteArray = _closure29.$getCallSiteArray();
                            super(_outerInstance, _thisObject);
                            this.node = reference = node;
                        }

                        public Object doCall(Object it) {
                            CallSite[] callSiteArray = _closure29.$getCallSiteArray();
                            return callSiteArray[0].call(this.node.get(), callSiteArray[1].call(it, 0), callSiteArray[2].call(it, 1));
                        }

                        @Generated
                        public Object getNode() {
                            CallSite[] callSiteArray = _closure29.$getCallSiteArray();
                            return this.node.get();
                        }

                        @Generated
                        public Object doCall() {
                            CallSite[] callSiteArray = _closure29.$getCallSiteArray();
                            return this.doCall(null);
                        }

                        protected /* synthetic */ MetaClass $getStaticMetaClass() {
                            if (this.getClass() != _closure29.class) {
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
                            stringArray[0] = "addMember";
                            stringArray[1] = "getAt";
                            stringArray[2] = "getAt";
                        }

                        private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                            String[] stringArray = new String[3];
                            _closure29.$createCallSiteArray_1(stringArray);
                            return new CallSiteArray(_closure29.class, stringArray);
                        }

                        private static /* synthetic */ CallSite[] $getCallSiteArray() {
                            CallSiteArray callSiteArray;
                            if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                                callSiteArray = _closure29.$createCallSiteArray();
                                $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                            }
                            return callSiteArray.array;
                        }
                    }
                    callSiteArray[2].callSafe(callSiteArray[3].callGroovyObjectGetProperty(this), new _closure29(this, this.getThisObject(), node));
                    return node.get();
                }

                @Generated
                public Class getTarget() {
                    CallSite[] callSiteArray = _annotation_closure14.$getCallSiteArray();
                    return ShortTypeHandling.castToClass(this.target.get());
                }

                @Generated
                public Object doCall() {
                    CallSite[] callSiteArray = _annotation_closure14.$getCallSiteArray();
                    return this.doCall(null);
                }

                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                    if (this.getClass() != _annotation_closure14.class) {
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
                    stringArray[0] = "<$constructor$>";
                    stringArray[1] = "make";
                    stringArray[2] = "each";
                    stringArray[3] = "expression";
                }

                private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                    String[] stringArray = new String[4];
                    _annotation_closure14.$createCallSiteArray_1(stringArray);
                    return new CallSiteArray(_annotation_closure14.class, stringArray);
                }

                private static /* synthetic */ CallSite[] $getCallSiteArray() {
                    CallSiteArray callSiteArray;
                    if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                        callSiteArray = _annotation_closure14.$createCallSiteArray();
                        $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                    }
                    return callSiteArray.array;
                }
            }
            callSiteArray[112].callCurrent(this, "ArgumentListExpression", argBlock, new _annotation_closure14(this, this, target2));
        } else {
            callSiteArray[113].call(this.expression, callSiteArray[114].callConstructor(AnnotationNode.class, callSiteArray[115].call(ClassHelper.class, target2.get())));
        }
    }

    public void mixin(String name, int modifiers, @DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        Reference<String> name2 = new Reference<String>(name);
        Reference<Integer> modifiers2 = new Reference<Integer>(modifiers);
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        public final class _mixin_closure15
        extends Closure
        implements GeneratedClosure {
            private /* synthetic */ Reference name;
            private /* synthetic */ Reference modifiers;
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _mixin_closure15(Object _outerInstance, Object _thisObject, Reference name, Reference modifiers) {
                Reference reference;
                Reference reference2;
                CallSite[] callSiteArray = _mixin_closure15.$getCallSiteArray();
                super(_outerInstance, _thisObject);
                this.name = reference2 = name;
                this.modifiers = reference = modifiers;
            }

            public Object doCall(Object it) {
                CallSite[] callSiteArray = _mixin_closure15.$getCallSiteArray();
                if (ScriptBytecodeAdapter.compareGreaterThan(callSiteArray[0].call(callSiteArray[1].callGroovyObjectGetProperty(this)), 1)) {
                    return callSiteArray[2].callConstructor(MixinNode.class, this.name.get(), this.modifiers.get(), callSiteArray[3].call(callSiteArray[4].callGroovyObjectGetProperty(this), 0), ScriptBytecodeAdapter.createPojoWrapper((ClassNode[])ScriptBytecodeAdapter.asType(callSiteArray[5].callConstructor(ArrayList.class, callSiteArray[6].call(callSiteArray[7].callGroovyObjectGetProperty(this), 1)), ClassNode[].class), ClassNode[].class));
                }
                return callSiteArray[8].callConstructor(MixinNode.class, this.name.get(), this.modifiers.get(), callSiteArray[9].call(callSiteArray[10].callGroovyObjectGetProperty(this), 0));
            }

            @Generated
            public String getName() {
                CallSite[] callSiteArray = _mixin_closure15.$getCallSiteArray();
                return ShortTypeHandling.castToString(this.name.get());
            }

            @Generated
            public int getModifiers() {
                CallSite[] callSiteArray = _mixin_closure15.$getCallSiteArray();
                return DefaultTypeTransformation.intUnbox(this.modifiers.get());
            }

            @Generated
            public Object doCall() {
                CallSite[] callSiteArray = _mixin_closure15.$getCallSiteArray();
                return this.doCall(null);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (this.getClass() != _mixin_closure15.class) {
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
                stringArray[0] = "size";
                stringArray[1] = "expression";
                stringArray[2] = "<$constructor$>";
                stringArray[3] = "getAt";
                stringArray[4] = "expression";
                stringArray[5] = "<$constructor$>";
                stringArray[6] = "getAt";
                stringArray[7] = "expression";
                stringArray[8] = "<$constructor$>";
                stringArray[9] = "getAt";
                stringArray[10] = "expression";
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[11];
                _mixin_closure15.$createCallSiteArray_1(stringArray);
                return new CallSiteArray(_mixin_closure15.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _mixin_closure15.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        callSiteArray[116].callCurrent(this, "AttributeExpression", argBlock, new _mixin_closure15(this, this, name2, modifiers2));
    }

    public void classNode(String name, int modifiers, @DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        Reference<String> name2 = new Reference<String>(name);
        Reference<Integer> modifiers2 = new Reference<Integer>(modifiers);
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        public final class _classNode_closure16
        extends Closure
        implements GeneratedClosure {
            private /* synthetic */ Reference name;
            private /* synthetic */ Reference modifiers;
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _classNode_closure16(Object _outerInstance, Object _thisObject, Reference name, Reference modifiers) {
                Reference reference;
                Reference reference2;
                CallSite[] callSiteArray = _classNode_closure16.$getCallSiteArray();
                super(_outerInstance, _thisObject);
                this.name = reference2 = name;
                this.modifiers = reference = modifiers;
            }

            public Object doCall(Object it) {
                CallSite[] callSiteArray = _classNode_closure16.$getCallSiteArray();
                Reference<Object> result = new Reference<Object>(callSiteArray[0].callConstructor((Object)ClassNode.class, ArrayUtil.createArray(this.name.get(), this.modifiers.get(), callSiteArray[1].call(callSiteArray[2].callGroovyObjectGetProperty(this), 0), ScriptBytecodeAdapter.createPojoWrapper((ClassNode[])ScriptBytecodeAdapter.asType(callSiteArray[3].callConstructor(ArrayList.class, callSiteArray[4].call(callSiteArray[5].callGroovyObjectGetProperty(this), 1)), ClassNode[].class), ClassNode[].class), ScriptBytecodeAdapter.createPojoWrapper((MixinNode[])ScriptBytecodeAdapter.asType(callSiteArray[6].callConstructor(ArrayList.class, callSiteArray[7].call(callSiteArray[8].callGroovyObjectGetProperty(this), 2)), MixinNode[].class), MixinNode[].class))));
                while (ScriptBytecodeAdapter.compareGreaterThan(callSiteArray[9].call(callSiteArray[10].callGroovyObjectGetProperty(this)), 3)) {
                    if (!DefaultTypeTransformation.booleanUnbox(callSiteArray[11].call(List.class, callSiteArray[12].call(callSiteArray[13].call(callSiteArray[14].callGroovyObjectGetProperty(this), 3))))) {
                        throw (Throwable)callSiteArray[15].callConstructor(IllegalArgumentException.class, callSiteArray[16].call((Object)"Expecting to find list of additional items instead found: ", callSiteArray[17].call(callSiteArray[18].call(callSiteArray[19].callGroovyObjectGetProperty(this), 3))));
                    }
                    if (ScriptBytecodeAdapter.compareGreaterThan(callSiteArray[20].call(callSiteArray[21].call(callSiteArray[22].callGroovyObjectGetProperty(this), 3)), 0)) {
                        Object clazz = callSiteArray[23].call(callSiteArray[24].call(callSiteArray[25].call(callSiteArray[26].callGroovyObjectGetProperty(this), 3), 0));
                        Object object = clazz;
                        if (ScriptBytecodeAdapter.isCase(object, GenericsType.class)) {
                            GenericsType[] genericsTypeArray = (GenericsType[])ScriptBytecodeAdapter.asType(callSiteArray[27].callConstructor(ArrayList.class, callSiteArray[28].call(callSiteArray[29].callGroovyObjectGetProperty(this), 3)), GenericsType[].class);
                            ScriptBytecodeAdapter.setProperty(genericsTypeArray, null, result.get(), "genericsTypes");
                        } else if (ScriptBytecodeAdapter.isCase(object, MethodNode.class)) {
                            public final class _closure30
                            extends Closure
                            implements GeneratedClosure {
                                private /* synthetic */ Reference result;
                                private static /* synthetic */ ClassInfo $staticClassInfo;
                                public static transient /* synthetic */ boolean __$stMC;
                                private static /* synthetic */ SoftReference $callSiteArray;

                                public _closure30(Object _outerInstance, Object _thisObject, Reference result) {
                                    Reference reference;
                                    CallSite[] callSiteArray = _closure30.$getCallSiteArray();
                                    super(_outerInstance, _thisObject);
                                    this.result = reference = result;
                                }

                                public Object doCall(Object it) {
                                    CallSite[] callSiteArray = _closure30.$getCallSiteArray();
                                    return callSiteArray[0].call(this.result.get(), it);
                                }

                                @Generated
                                public Object getResult() {
                                    CallSite[] callSiteArray = _closure30.$getCallSiteArray();
                                    return this.result.get();
                                }

                                @Generated
                                public Object doCall() {
                                    CallSite[] callSiteArray = _closure30.$getCallSiteArray();
                                    return this.doCall(null);
                                }

                                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                                    if (this.getClass() != _closure30.class) {
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
                                    String[] stringArray = new String[1];
                                    stringArray[0] = "addMethod";
                                    return new CallSiteArray(_closure30.class, stringArray);
                                }

                                private static /* synthetic */ CallSite[] $getCallSiteArray() {
                                    CallSiteArray callSiteArray;
                                    if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                                        callSiteArray = _closure30.$createCallSiteArray();
                                        $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                                    }
                                    return callSiteArray.array;
                                }
                            }
                            callSiteArray[30].call(callSiteArray[31].call(callSiteArray[32].callGroovyObjectGetProperty(this), 3), new _closure30(this, this.getThisObject(), result));
                        } else if (ScriptBytecodeAdapter.isCase(object, ConstructorNode.class)) {
                            public final class _closure31
                            extends Closure
                            implements GeneratedClosure {
                                private /* synthetic */ Reference result;
                                private static /* synthetic */ ClassInfo $staticClassInfo;
                                public static transient /* synthetic */ boolean __$stMC;
                                private static /* synthetic */ SoftReference $callSiteArray;

                                public _closure31(Object _outerInstance, Object _thisObject, Reference result) {
                                    Reference reference;
                                    CallSite[] callSiteArray = _closure31.$getCallSiteArray();
                                    super(_outerInstance, _thisObject);
                                    this.result = reference = result;
                                }

                                public Object doCall(Object it) {
                                    CallSite[] callSiteArray = _closure31.$getCallSiteArray();
                                    return callSiteArray[0].call(this.result.get(), it);
                                }

                                @Generated
                                public Object getResult() {
                                    CallSite[] callSiteArray = _closure31.$getCallSiteArray();
                                    return this.result.get();
                                }

                                @Generated
                                public Object doCall() {
                                    CallSite[] callSiteArray = _closure31.$getCallSiteArray();
                                    return this.doCall(null);
                                }

                                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                                    if (this.getClass() != _closure31.class) {
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
                                    String[] stringArray = new String[1];
                                    stringArray[0] = "addConstructor";
                                    return new CallSiteArray(_closure31.class, stringArray);
                                }

                                private static /* synthetic */ CallSite[] $getCallSiteArray() {
                                    CallSiteArray callSiteArray;
                                    if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                                        callSiteArray = _closure31.$createCallSiteArray();
                                        $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                                    }
                                    return callSiteArray.array;
                                }
                            }
                            callSiteArray[33].call(callSiteArray[34].call(callSiteArray[35].callGroovyObjectGetProperty(this), 3), new _closure31(this, this.getThisObject(), result));
                        } else if (ScriptBytecodeAdapter.isCase(object, PropertyNode.class)) {
                            public final class _closure32
                            extends Closure
                            implements GeneratedClosure {
                                private /* synthetic */ Reference result;
                                private static /* synthetic */ ClassInfo $staticClassInfo;
                                public static transient /* synthetic */ boolean __$stMC;
                                private static /* synthetic */ SoftReference $callSiteArray;

                                public _closure32(Object _outerInstance, Object _thisObject, Reference result) {
                                    Reference reference;
                                    CallSite[] callSiteArray = _closure32.$getCallSiteArray();
                                    super(_outerInstance, _thisObject);
                                    this.result = reference = result;
                                }

                                public Object doCall(Object it) {
                                    CallSite[] callSiteArray = _closure32.$getCallSiteArray();
                                    Object t = this.result.get();
                                    ScriptBytecodeAdapter.setProperty(t, null, callSiteArray[0].callGetProperty(it), "owner");
                                    return callSiteArray[1].call(this.result.get(), it);
                                }

                                @Generated
                                public Object getResult() {
                                    CallSite[] callSiteArray = _closure32.$getCallSiteArray();
                                    return this.result.get();
                                }

                                @Generated
                                public Object doCall() {
                                    CallSite[] callSiteArray = _closure32.$getCallSiteArray();
                                    return this.doCall(null);
                                }

                                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                                    if (this.getClass() != _closure32.class) {
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
                                    stringArray[0] = "field";
                                    stringArray[1] = "addProperty";
                                }

                                private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                                    String[] stringArray = new String[2];
                                    _closure32.$createCallSiteArray_1(stringArray);
                                    return new CallSiteArray(_closure32.class, stringArray);
                                }

                                private static /* synthetic */ CallSite[] $getCallSiteArray() {
                                    CallSiteArray callSiteArray;
                                    if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                                        callSiteArray = _closure32.$createCallSiteArray();
                                        $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                                    }
                                    return callSiteArray.array;
                                }
                            }
                            callSiteArray[36].call(callSiteArray[37].call(callSiteArray[38].callGroovyObjectGetProperty(this), 3), new _closure32(this, this.getThisObject(), result));
                        } else if (ScriptBytecodeAdapter.isCase(object, FieldNode.class)) {
                            public final class _closure33
                            extends Closure
                            implements GeneratedClosure {
                                private /* synthetic */ Reference result;
                                private static /* synthetic */ ClassInfo $staticClassInfo;
                                public static transient /* synthetic */ boolean __$stMC;
                                private static /* synthetic */ SoftReference $callSiteArray;

                                public _closure33(Object _outerInstance, Object _thisObject, Reference result) {
                                    Reference reference;
                                    CallSite[] callSiteArray = _closure33.$getCallSiteArray();
                                    super(_outerInstance, _thisObject);
                                    this.result = reference = result;
                                }

                                public Object doCall(Object it) {
                                    CallSite[] callSiteArray = _closure33.$getCallSiteArray();
                                    Object t = this.result.get();
                                    ScriptBytecodeAdapter.setProperty(t, null, it, "owner");
                                    return callSiteArray[0].call(this.result.get(), it);
                                }

                                @Generated
                                public Object getResult() {
                                    CallSite[] callSiteArray = _closure33.$getCallSiteArray();
                                    return this.result.get();
                                }

                                @Generated
                                public Object doCall() {
                                    CallSite[] callSiteArray = _closure33.$getCallSiteArray();
                                    return this.doCall(null);
                                }

                                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                                    if (this.getClass() != _closure33.class) {
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
                                    String[] stringArray = new String[1];
                                    stringArray[0] = "addField";
                                    return new CallSiteArray(_closure33.class, stringArray);
                                }

                                private static /* synthetic */ CallSite[] $getCallSiteArray() {
                                    CallSiteArray callSiteArray;
                                    if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                                        callSiteArray = _closure33.$createCallSiteArray();
                                        $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                                    }
                                    return callSiteArray.array;
                                }
                            }
                            callSiteArray[39].call(callSiteArray[40].call(callSiteArray[41].callGroovyObjectGetProperty(this), 3), new _closure33(this, this.getThisObject(), result));
                        } else if (ScriptBytecodeAdapter.isCase(object, AnnotationNode.class)) {
                            callSiteArray[42].call(result.get(), callSiteArray[43].callConstructor(ArrayList.class, callSiteArray[44].call(callSiteArray[45].callGroovyObjectGetProperty(this), 3)));
                        } else {
                            throw (Throwable)callSiteArray[46].callConstructor(IllegalArgumentException.class, new GStringImpl(new Object[]{callSiteArray[47].callGetProperty(clazz)}, new String[]{"Unexpected item found in ClassNode spec. Expecting [Field|Method|Property|Constructor|Annotation|GenericsType] but found: ", ""}));
                        }
                    }
                    callSiteArray[48].call(callSiteArray[49].callGroovyObjectGetProperty(this), 3);
                }
                return result.get();
            }

            @Generated
            public String getName() {
                CallSite[] callSiteArray = _classNode_closure16.$getCallSiteArray();
                return ShortTypeHandling.castToString(this.name.get());
            }

            @Generated
            public int getModifiers() {
                CallSite[] callSiteArray = _classNode_closure16.$getCallSiteArray();
                return DefaultTypeTransformation.intUnbox(this.modifiers.get());
            }

            @Generated
            public Object doCall() {
                CallSite[] callSiteArray = _classNode_closure16.$getCallSiteArray();
                return this.doCall(null);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (this.getClass() != _classNode_closure16.class) {
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
                stringArray[0] = "<$constructor$>";
                stringArray[1] = "getAt";
                stringArray[2] = "expression";
                stringArray[3] = "<$constructor$>";
                stringArray[4] = "getAt";
                stringArray[5] = "expression";
                stringArray[6] = "<$constructor$>";
                stringArray[7] = "getAt";
                stringArray[8] = "expression";
                stringArray[9] = "size";
                stringArray[10] = "expression";
                stringArray[11] = "isAssignableFrom";
                stringArray[12] = "getClass";
                stringArray[13] = "getAt";
                stringArray[14] = "expression";
                stringArray[15] = "<$constructor$>";
                stringArray[16] = "plus";
                stringArray[17] = "getClass";
                stringArray[18] = "getAt";
                stringArray[19] = "expression";
                stringArray[20] = "size";
                stringArray[21] = "getAt";
                stringArray[22] = "expression";
                stringArray[23] = "getClass";
                stringArray[24] = "getAt";
                stringArray[25] = "getAt";
                stringArray[26] = "expression";
                stringArray[27] = "<$constructor$>";
                stringArray[28] = "getAt";
                stringArray[29] = "expression";
                stringArray[30] = "each";
                stringArray[31] = "getAt";
                stringArray[32] = "expression";
                stringArray[33] = "each";
                stringArray[34] = "getAt";
                stringArray[35] = "expression";
                stringArray[36] = "each";
                stringArray[37] = "getAt";
                stringArray[38] = "expression";
                stringArray[39] = "each";
                stringArray[40] = "getAt";
                stringArray[41] = "expression";
                stringArray[42] = "addAnnotations";
                stringArray[43] = "<$constructor$>";
                stringArray[44] = "getAt";
                stringArray[45] = "expression";
                stringArray[46] = "<$constructor$>";
                stringArray[47] = "name";
                stringArray[48] = "remove";
                stringArray[49] = "expression";
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[50];
                _classNode_closure16.$createCallSiteArray_1(stringArray);
                return new CallSiteArray(_classNode_closure16.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _classNode_closure16.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        callSiteArray[117].callCurrent(this, "ClassNode", argBlock, new _classNode_closure16(this, this, name2, modifiers2));
    }

    public void assertStatement(@DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        public final class _assertStatement_closure17
        extends Closure
        implements GeneratedClosure {
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _assertStatement_closure17(Object _outerInstance, Object _thisObject) {
                CallSite[] callSiteArray = _assertStatement_closure17.$getCallSiteArray();
                super(_outerInstance, _thisObject);
            }

            public Object doCall(Object it) {
                CallSite[] callSiteArray = _assertStatement_closure17.$getCallSiteArray();
                if (ScriptBytecodeAdapter.compareLessThan(callSiteArray[0].call(callSiteArray[1].callGroovyObjectGetProperty(this)), 2)) {
                    return callSiteArray[2].callConstructor((Object)AssertStatement.class, ScriptBytecodeAdapter.despreadList(new Object[0], new Object[]{callSiteArray[3].callCurrent(this, "assertStatement", ScriptBytecodeAdapter.createList(new Object[]{BooleanExpression.class}))}, new int[]{0}));
                }
                return callSiteArray[4].callConstructor((Object)AssertStatement.class, ScriptBytecodeAdapter.despreadList(new Object[0], new Object[]{callSiteArray[5].callCurrent(this, "assertStatement", ScriptBytecodeAdapter.createList(new Object[]{BooleanExpression.class, Expression.class}))}, new int[]{0}));
            }

            @Generated
            public Object doCall() {
                CallSite[] callSiteArray = _assertStatement_closure17.$getCallSiteArray();
                return this.doCall(null);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (this.getClass() != _assertStatement_closure17.class) {
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
                stringArray[0] = "size";
                stringArray[1] = "expression";
                stringArray[2] = "<$constructor$>";
                stringArray[3] = "enforceConstraints";
                stringArray[4] = "<$constructor$>";
                stringArray[5] = "enforceConstraints";
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[6];
                _assertStatement_closure17.$createCallSiteArray_1(stringArray);
                return new CallSiteArray(_assertStatement_closure17.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _assertStatement_closure17.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        callSiteArray[118].callCurrent(this, "AssertStatement", argBlock, new _assertStatement_closure17(this, this));
    }

    public void tryCatch(@DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        public final class _tryCatch_closure18
        extends Closure
        implements GeneratedClosure {
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _tryCatch_closure18(Object _outerInstance, Object _thisObject) {
                CallSite[] callSiteArray = _tryCatch_closure18.$getCallSiteArray();
                super(_outerInstance, _thisObject);
            }

            public Object doCall(Object it) {
                CallSite[] callSiteArray = _tryCatch_closure18.$getCallSiteArray();
                Reference<Object> result = new Reference<Object>(callSiteArray[0].callConstructor(TryCatchStatement.class, callSiteArray[1].call(callSiteArray[2].callGroovyObjectGetProperty(this), 0), callSiteArray[3].call(callSiteArray[4].callGroovyObjectGetProperty(this), 1)));
                Object catchStatements = callSiteArray[5].call(callSiteArray[6].call(callSiteArray[7].callGroovyObjectGetProperty(this)));
                public final class _closure34
                extends Closure
                implements GeneratedClosure {
                    private /* synthetic */ Reference result;
                    private static /* synthetic */ ClassInfo $staticClassInfo;
                    public static transient /* synthetic */ boolean __$stMC;
                    private static /* synthetic */ SoftReference $callSiteArray;

                    public _closure34(Object _outerInstance, Object _thisObject, Reference result) {
                        Reference reference;
                        CallSite[] callSiteArray = _closure34.$getCallSiteArray();
                        super(_outerInstance, _thisObject);
                        this.result = reference = result;
                    }

                    public Object doCall(Object statement) {
                        CallSite[] callSiteArray = _closure34.$getCallSiteArray();
                        return callSiteArray[0].call(this.result.get(), statement);
                    }

                    @Generated
                    public Object getResult() {
                        CallSite[] callSiteArray = _closure34.$getCallSiteArray();
                        return this.result.get();
                    }

                    protected /* synthetic */ MetaClass $getStaticMetaClass() {
                        if (this.getClass() != _closure34.class) {
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
                        String[] stringArray = new String[1];
                        stringArray[0] = "addCatch";
                        return new CallSiteArray(_closure34.class, stringArray);
                    }

                    private static /* synthetic */ CallSite[] $getCallSiteArray() {
                        CallSiteArray callSiteArray;
                        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                            callSiteArray = _closure34.$createCallSiteArray();
                            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                        }
                        return callSiteArray.array;
                    }
                }
                callSiteArray[8].call(catchStatements, new _closure34(this, this.getThisObject(), result));
                return result.get();
            }

            @Generated
            public Object doCall() {
                CallSite[] callSiteArray = _tryCatch_closure18.$getCallSiteArray();
                return this.doCall(null);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (this.getClass() != _tryCatch_closure18.class) {
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
                stringArray[0] = "<$constructor$>";
                stringArray[1] = "getAt";
                stringArray[2] = "expression";
                stringArray[3] = "getAt";
                stringArray[4] = "expression";
                stringArray[5] = "tail";
                stringArray[6] = "tail";
                stringArray[7] = "expression";
                stringArray[8] = "each";
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[9];
                _tryCatch_closure18.$createCallSiteArray_1(stringArray);
                return new CallSiteArray(_tryCatch_closure18.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _tryCatch_closure18.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        callSiteArray[119].callCurrent(this, "TryCatchStatement", argBlock, new _tryCatch_closure18(this, this));
    }

    public void variable(String variable) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        callSiteArray[120].call(this.expression, callSiteArray[121].callConstructor(VariableExpression.class, variable));
    }

    /*
     * WARNING - void declaration
     */
    public void method(String name, int modifiers, Class returnType, @DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        void var3_3;
        Reference<String> name2 = new Reference<String>(name);
        Reference<Integer> modifiers2 = new Reference<Integer>(modifiers);
        Reference<void> returnType2 = new Reference<void>(var3_3);
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        public final class _method_closure19
        extends Closure
        implements GeneratedClosure {
            private /* synthetic */ Reference name;
            private /* synthetic */ Reference modifiers;
            private /* synthetic */ Reference returnType;
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _method_closure19(Object _outerInstance, Object _thisObject, Reference name, Reference modifiers, Reference returnType) {
                Reference reference;
                Reference reference2;
                Reference reference3;
                CallSite[] callSiteArray = _method_closure19.$getCallSiteArray();
                super(_outerInstance, _thisObject);
                this.name = reference3 = name;
                this.modifiers = reference2 = modifiers;
                this.returnType = reference = returnType;
            }

            public Object doCall(Object it) {
                CallSite[] callSiteArray = _method_closure19.$getCallSiteArray();
                Object result = callSiteArray[0].callConstructor((Object)MethodNode.class, ArrayUtil.createArray(this.name.get(), this.modifiers.get(), callSiteArray[1].call(ClassHelper.class, this.returnType.get()), callSiteArray[2].call(callSiteArray[3].callGroovyObjectGetProperty(this), 0), callSiteArray[4].call(callSiteArray[5].callGroovyObjectGetProperty(this), 1), callSiteArray[6].call(callSiteArray[7].callGroovyObjectGetProperty(this), 2)));
                if (DefaultTypeTransformation.booleanUnbox(callSiteArray[8].call(callSiteArray[9].callGroovyObjectGetProperty(this), 3))) {
                    callSiteArray[10].call(result, callSiteArray[11].callConstructor(ArrayList.class, callSiteArray[12].call(callSiteArray[13].callGroovyObjectGetProperty(this), 3)));
                }
                return result;
            }

            @Generated
            public String getName() {
                CallSite[] callSiteArray = _method_closure19.$getCallSiteArray();
                return ShortTypeHandling.castToString(this.name.get());
            }

            @Generated
            public int getModifiers() {
                CallSite[] callSiteArray = _method_closure19.$getCallSiteArray();
                return DefaultTypeTransformation.intUnbox(this.modifiers.get());
            }

            @Generated
            public Class getReturnType() {
                CallSite[] callSiteArray = _method_closure19.$getCallSiteArray();
                return ShortTypeHandling.castToClass(this.returnType.get());
            }

            @Generated
            public Object doCall() {
                CallSite[] callSiteArray = _method_closure19.$getCallSiteArray();
                return this.doCall(null);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (this.getClass() != _method_closure19.class) {
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
                stringArray[0] = "<$constructor$>";
                stringArray[1] = "make";
                stringArray[2] = "getAt";
                stringArray[3] = "expression";
                stringArray[4] = "getAt";
                stringArray[5] = "expression";
                stringArray[6] = "getAt";
                stringArray[7] = "expression";
                stringArray[8] = "getAt";
                stringArray[9] = "expression";
                stringArray[10] = "addAnnotations";
                stringArray[11] = "<$constructor$>";
                stringArray[12] = "getAt";
                stringArray[13] = "expression";
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[14];
                _method_closure19.$createCallSiteArray_1(stringArray);
                return new CallSiteArray(_method_closure19.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _method_closure19.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        callSiteArray[122].callCurrent(this, "MethodNode", argBlock, new _method_closure19(this, this, name2, modifiers2, returnType2));
    }

    public void token(String value) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        if (ScriptBytecodeAdapter.compareEqual(value, null)) {
            throw (Throwable)callSiteArray[123].callConstructor(IllegalArgumentException.class, "Null: value");
        }
        Object tokenID = callSiteArray[124].call(Types.class, value);
        if (ScriptBytecodeAdapter.compareEqual(tokenID, callSiteArray[125].callGetProperty(Types.class))) {
            Object object;
            tokenID = object = callSiteArray[126].call(Types.class, value);
        }
        if (ScriptBytecodeAdapter.compareEqual(tokenID, callSiteArray[127].callGetProperty(Types.class))) {
            throw (Throwable)callSiteArray[128].callConstructor(IllegalArgumentException.class, new GStringImpl(new Object[]{value}, new String[]{"could not find token for ", ""}));
        }
        callSiteArray[129].call(this.expression, callSiteArray[130].callConstructor(Token.class, tokenID, value, -1, -1));
    }

    public void range(Range range) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        if (ScriptBytecodeAdapter.compareEqual(range, null)) {
            throw (Throwable)callSiteArray[131].callConstructor(IllegalArgumentException.class, "Null: range");
        }
        callSiteArray[132].call(this.expression, callSiteArray[133].callConstructor(RangeExpression.class, callSiteArray[134].callStatic(GeneralUtils.class, callSiteArray[135].callGetProperty(range)), callSiteArray[136].callStatic(GeneralUtils.class, callSiteArray[137].callGetProperty(range)), true));
    }

    public void switchStatement(@DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        public final class _switchStatement_closure20
        extends Closure
        implements GeneratedClosure {
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _switchStatement_closure20(Object _outerInstance, Object _thisObject) {
                CallSite[] callSiteArray = _switchStatement_closure20.$getCallSiteArray();
                super(_outerInstance, _thisObject);
            }

            public Object doCall(Object it) {
                CallSite[] callSiteArray = _switchStatement_closure20.$getCallSiteArray();
                Object switchExpression = callSiteArray[0].call(callSiteArray[1].callGroovyObjectGetProperty(this));
                Object caseStatements = callSiteArray[2].call(callSiteArray[3].call(callSiteArray[4].callGroovyObjectGetProperty(this)));
                Object defaultExpression = callSiteArray[5].call(callSiteArray[6].call(callSiteArray[7].callGroovyObjectGetProperty(this)));
                return callSiteArray[8].callConstructor(SwitchStatement.class, switchExpression, caseStatements, defaultExpression);
            }

            @Generated
            public Object doCall() {
                CallSite[] callSiteArray = _switchStatement_closure20.$getCallSiteArray();
                return this.doCall(null);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (this.getClass() != _switchStatement_closure20.class) {
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
                stringArray[0] = "head";
                stringArray[1] = "expression";
                stringArray[2] = "tail";
                stringArray[3] = "tail";
                stringArray[4] = "expression";
                stringArray[5] = "head";
                stringArray[6] = "tail";
                stringArray[7] = "expression";
                stringArray[8] = "<$constructor$>";
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[9];
                _switchStatement_closure20.$createCallSiteArray_1(stringArray);
                return new CallSiteArray(_switchStatement_closure20.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _switchStatement_closure20.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        callSiteArray[138].callCurrent(this, "SwitchStatement", argBlock, new _switchStatement_closure20(this, this));
    }

    public void mapEntry(Map map) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        public final class _mapEntry_closure21
        extends Closure
        implements GeneratedClosure {
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _mapEntry_closure21(Object _outerInstance, Object _thisObject) {
                CallSite[] callSiteArray = _mapEntry_closure21.$getCallSiteArray();
                super(_outerInstance, _thisObject);
            }

            public Object doCall(Object it) {
                CallSite[] callSiteArray = _mapEntry_closure21.$getCallSiteArray();
                return callSiteArray[0].call(callSiteArray[1].callGroovyObjectGetProperty(this), callSiteArray[2].callConstructor(MapEntryExpression.class, callSiteArray[3].callStatic(GeneralUtils.class, callSiteArray[4].callGetProperty(it)), callSiteArray[5].callStatic(GeneralUtils.class, callSiteArray[6].callGetProperty(it))));
            }

            @Generated
            public Object doCall() {
                CallSite[] callSiteArray = _mapEntry_closure21.$getCallSiteArray();
                return this.doCall(null);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (this.getClass() != _mapEntry_closure21.class) {
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
                stringArray[0] = "leftShift";
                stringArray[1] = "expression";
                stringArray[2] = "<$constructor$>";
                stringArray[3] = "constX";
                stringArray[4] = "key";
                stringArray[5] = "constX";
                stringArray[6] = "value";
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[7];
                _mapEntry_closure21.$createCallSiteArray_1(stringArray);
                return new CallSiteArray(_mapEntry_closure21.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _mapEntry_closure21.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        callSiteArray[139].call(callSiteArray[140].call(map), new _mapEntry_closure21(this, this));
    }

    /*
     * WARNING - void declaration
     */
    public void fieldNode(String name, int modifiers, Class type, Class owner, @DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        void var3_3;
        Reference<String> name2 = new Reference<String>(name);
        Reference<Integer> modifiers2 = new Reference<Integer>(modifiers);
        Reference<void> type2 = new Reference<void>(var3_3);
        Reference<Class> owner2 = new Reference<Class>(owner);
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        public final class _fieldNode_closure22
        extends Closure
        implements GeneratedClosure {
            private /* synthetic */ Reference owner;
            private /* synthetic */ Reference type;
            private /* synthetic */ Reference modifiers;
            private /* synthetic */ Reference name;
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _fieldNode_closure22(Object _outerInstance, Object _thisObject, Reference owner, Reference type, Reference modifiers, Reference name) {
                Reference reference;
                Reference reference2;
                Reference reference3;
                Reference reference4;
                CallSite[] callSiteArray = _fieldNode_closure22.$getCallSiteArray();
                super(_outerInstance, _thisObject);
                this.owner = reference4 = owner;
                this.type = reference3 = type;
                this.modifiers = reference2 = modifiers;
                this.name = reference = name;
            }

            public Object doCall(Object it) {
                CallSite[] callSiteArray = _fieldNode_closure22.$getCallSiteArray();
                Object annotations = null;
                if (ScriptBytecodeAdapter.compareGreaterThan(callSiteArray[0].call(callSiteArray[1].callGroovyObjectGetProperty(this)), 1)) {
                    Object object;
                    annotations = object = callSiteArray[2].call(callSiteArray[3].callGroovyObjectGetProperty(this), 1);
                    callSiteArray[4].call(callSiteArray[5].callGroovyObjectGetProperty(this), 1);
                }
                callSiteArray[6].call(callSiteArray[7].callGroovyObjectGetProperty(this), 0, callSiteArray[8].call(ClassHelper.class, this.owner.get()));
                callSiteArray[9].call(callSiteArray[10].callGroovyObjectGetProperty(this), 0, callSiteArray[11].call(ClassHelper.class, this.type.get()));
                callSiteArray[12].call(callSiteArray[13].callGroovyObjectGetProperty(this), 0, this.modifiers.get());
                callSiteArray[14].call(callSiteArray[15].callGroovyObjectGetProperty(this), 0, this.name.get());
                Object result = callSiteArray[16].callConstructor((Object)FieldNode.class, ScriptBytecodeAdapter.despreadList(new Object[0], new Object[]{callSiteArray[17].callCurrent(this, "fieldNode", ScriptBytecodeAdapter.createList(new Object[]{String.class, Integer.class, ClassNode.class, ClassNode.class, Expression.class}))}, new int[]{0}));
                if (DefaultTypeTransformation.booleanUnbox(annotations)) {
                    callSiteArray[18].call(result, callSiteArray[19].callConstructor(ArrayList.class, annotations));
                }
                return result;
            }

            @Override
            @Generated
            public Class getOwner() {
                CallSite[] callSiteArray = _fieldNode_closure22.$getCallSiteArray();
                return ShortTypeHandling.castToClass(this.owner.get());
            }

            @Generated
            public Class getType() {
                CallSite[] callSiteArray = _fieldNode_closure22.$getCallSiteArray();
                return ShortTypeHandling.castToClass(this.type.get());
            }

            @Generated
            public int getModifiers() {
                CallSite[] callSiteArray = _fieldNode_closure22.$getCallSiteArray();
                return DefaultTypeTransformation.intUnbox(this.modifiers.get());
            }

            @Generated
            public String getName() {
                CallSite[] callSiteArray = _fieldNode_closure22.$getCallSiteArray();
                return ShortTypeHandling.castToString(this.name.get());
            }

            @Generated
            public Object doCall() {
                CallSite[] callSiteArray = _fieldNode_closure22.$getCallSiteArray();
                return this.doCall(null);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (this.getClass() != _fieldNode_closure22.class) {
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
                stringArray[0] = "size";
                stringArray[1] = "expression";
                stringArray[2] = "getAt";
                stringArray[3] = "expression";
                stringArray[4] = "remove";
                stringArray[5] = "expression";
                stringArray[6] = "add";
                stringArray[7] = "expression";
                stringArray[8] = "make";
                stringArray[9] = "add";
                stringArray[10] = "expression";
                stringArray[11] = "make";
                stringArray[12] = "add";
                stringArray[13] = "expression";
                stringArray[14] = "add";
                stringArray[15] = "expression";
                stringArray[16] = "<$constructor$>";
                stringArray[17] = "enforceConstraints";
                stringArray[18] = "addAnnotations";
                stringArray[19] = "<$constructor$>";
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[20];
                _fieldNode_closure22.$createCallSiteArray_1(stringArray);
                return new CallSiteArray(_fieldNode_closure22.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _fieldNode_closure22.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        callSiteArray[141].callCurrent(this, "FieldNode", argBlock, new _fieldNode_closure22(this, this, owner2, type2, modifiers2, name2));
    }

    public void innerClass(String name, int modifiers, @DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        Reference<String> name2 = new Reference<String>(name);
        Reference<Integer> modifiers2 = new Reference<Integer>(modifiers);
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        public final class _innerClass_closure23
        extends Closure
        implements GeneratedClosure {
            private /* synthetic */ Reference name;
            private /* synthetic */ Reference modifiers;
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _innerClass_closure23(Object _outerInstance, Object _thisObject, Reference name, Reference modifiers) {
                Reference reference;
                Reference reference2;
                CallSite[] callSiteArray = _innerClass_closure23.$getCallSiteArray();
                super(_outerInstance, _thisObject);
                this.name = reference2 = name;
                this.modifiers = reference = modifiers;
            }

            public Object doCall(Object it) {
                CallSite[] callSiteArray = _innerClass_closure23.$getCallSiteArray();
                return callSiteArray[0].callConstructor((Object)InnerClassNode.class, ArrayUtil.createArray(callSiteArray[1].call(callSiteArray[2].callGroovyObjectGetProperty(this), 0), this.name.get(), this.modifiers.get(), callSiteArray[3].call(callSiteArray[4].callGroovyObjectGetProperty(this), 1), ScriptBytecodeAdapter.createPojoWrapper((ClassNode[])ScriptBytecodeAdapter.asType(callSiteArray[5].callConstructor(ArrayList.class, callSiteArray[6].call(callSiteArray[7].callGroovyObjectGetProperty(this), 2)), ClassNode[].class), ClassNode[].class), ScriptBytecodeAdapter.createPojoWrapper((MixinNode[])ScriptBytecodeAdapter.asType(callSiteArray[8].callConstructor(ArrayList.class, callSiteArray[9].call(callSiteArray[10].callGroovyObjectGetProperty(this), 3)), MixinNode[].class), MixinNode[].class)));
            }

            @Generated
            public String getName() {
                CallSite[] callSiteArray = _innerClass_closure23.$getCallSiteArray();
                return ShortTypeHandling.castToString(this.name.get());
            }

            @Generated
            public int getModifiers() {
                CallSite[] callSiteArray = _innerClass_closure23.$getCallSiteArray();
                return DefaultTypeTransformation.intUnbox(this.modifiers.get());
            }

            @Generated
            public Object doCall() {
                CallSite[] callSiteArray = _innerClass_closure23.$getCallSiteArray();
                return this.doCall(null);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (this.getClass() != _innerClass_closure23.class) {
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
                stringArray[0] = "<$constructor$>";
                stringArray[1] = "getAt";
                stringArray[2] = "expression";
                stringArray[3] = "getAt";
                stringArray[4] = "expression";
                stringArray[5] = "<$constructor$>";
                stringArray[6] = "getAt";
                stringArray[7] = "expression";
                stringArray[8] = "<$constructor$>";
                stringArray[9] = "getAt";
                stringArray[10] = "expression";
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[11];
                _innerClass_closure23.$createCallSiteArray_1(stringArray);
                return new CallSiteArray(_innerClass_closure23.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _innerClass_closure23.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        callSiteArray[142].callCurrent(this, "InnerClassNode", argBlock, new _innerClass_closure23(this, this, name2, modifiers2));
    }

    /*
     * WARNING - void declaration
     */
    public void propertyNode(String name, int modifiers, Class type, Class owner, @DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        void var3_3;
        Reference<String> name2 = new Reference<String>(name);
        Reference<Integer> modifiers2 = new Reference<Integer>(modifiers);
        Reference<void> type2 = new Reference<void>(var3_3);
        Reference<Class> owner2 = new Reference<Class>(owner);
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        public final class _propertyNode_closure24
        extends Closure
        implements GeneratedClosure {
            private /* synthetic */ Reference name;
            private /* synthetic */ Reference modifiers;
            private /* synthetic */ Reference type;
            private /* synthetic */ Reference owner;
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _propertyNode_closure24(Object _outerInstance, Object _thisObject, Reference name, Reference modifiers, Reference type, Reference owner) {
                Reference reference;
                Reference reference2;
                Reference reference3;
                Reference reference4;
                CallSite[] callSiteArray = _propertyNode_closure24.$getCallSiteArray();
                super(_outerInstance, _thisObject);
                this.name = reference4 = name;
                this.modifiers = reference3 = modifiers;
                this.type = reference2 = type;
                this.owner = reference = owner;
            }

            public Object doCall(Object it) {
                CallSite[] callSiteArray = _propertyNode_closure24.$getCallSiteArray();
                Object annotations = null;
                if (DefaultTypeTransformation.booleanUnbox(callSiteArray[0].call(List.class, callSiteArray[1].call(callSiteArray[2].call(callSiteArray[3].callGroovyObjectGetProperty(this), -1))))) {
                    Object object;
                    annotations = object = callSiteArray[4].call(callSiteArray[5].callGroovyObjectGetProperty(this), -1);
                    callSiteArray[6].call(callSiteArray[7].callGroovyObjectGetProperty(this), callSiteArray[8].call(callSiteArray[9].call(callSiteArray[10].callGroovyObjectGetProperty(this)), 1));
                }
                Object result = callSiteArray[11].callConstructor((Object)PropertyNode.class, ArrayUtil.createArray(this.name.get(), this.modifiers.get(), callSiteArray[12].call(ClassHelper.class, this.type.get()), callSiteArray[13].call(ClassHelper.class, this.owner.get()), callSiteArray[14].call(callSiteArray[15].callGroovyObjectGetProperty(this), 0), callSiteArray[16].call(callSiteArray[17].callGroovyObjectGetProperty(this), 1), callSiteArray[18].call(callSiteArray[19].callGroovyObjectGetProperty(this), 2)));
                if (DefaultTypeTransformation.booleanUnbox(annotations)) {
                    callSiteArray[20].call(result, callSiteArray[21].callConstructor(ArrayList.class, annotations));
                }
                return result;
            }

            @Generated
            public String getName() {
                CallSite[] callSiteArray = _propertyNode_closure24.$getCallSiteArray();
                return ShortTypeHandling.castToString(this.name.get());
            }

            @Generated
            public int getModifiers() {
                CallSite[] callSiteArray = _propertyNode_closure24.$getCallSiteArray();
                return DefaultTypeTransformation.intUnbox(this.modifiers.get());
            }

            @Generated
            public Class getType() {
                CallSite[] callSiteArray = _propertyNode_closure24.$getCallSiteArray();
                return ShortTypeHandling.castToClass(this.type.get());
            }

            @Override
            @Generated
            public Class getOwner() {
                CallSite[] callSiteArray = _propertyNode_closure24.$getCallSiteArray();
                return ShortTypeHandling.castToClass(this.owner.get());
            }

            @Generated
            public Object doCall() {
                CallSite[] callSiteArray = _propertyNode_closure24.$getCallSiteArray();
                return this.doCall(null);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (this.getClass() != _propertyNode_closure24.class) {
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
                stringArray[0] = "isAssignableFrom";
                stringArray[1] = "getClass";
                stringArray[2] = "getAt";
                stringArray[3] = "expression";
                stringArray[4] = "getAt";
                stringArray[5] = "expression";
                stringArray[6] = "remove";
                stringArray[7] = "expression";
                stringArray[8] = "minus";
                stringArray[9] = "size";
                stringArray[10] = "expression";
                stringArray[11] = "<$constructor$>";
                stringArray[12] = "make";
                stringArray[13] = "make";
                stringArray[14] = "getAt";
                stringArray[15] = "expression";
                stringArray[16] = "getAt";
                stringArray[17] = "expression";
                stringArray[18] = "getAt";
                stringArray[19] = "expression";
                stringArray[20] = "addAnnotations";
                stringArray[21] = "<$constructor$>";
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[22];
                _propertyNode_closure24.$createCallSiteArray_1(stringArray);
                return new CallSiteArray(_propertyNode_closure24.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _propertyNode_closure24.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        callSiteArray[143].callCurrent(this, "PropertyNode", argBlock, new _propertyNode_closure24(this, this, name2, modifiers2, type2, owner2));
    }

    /*
     * WARNING - void declaration
     */
    public void staticMethodCall(Class target, String name, @DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        void var2_2;
        Reference<Class> target2 = new Reference<Class>(target);
        Reference<void> name2 = new Reference<void>(var2_2);
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        public final class _staticMethodCall_closure25
        extends Closure
        implements GeneratedClosure {
            private /* synthetic */ Reference name;
            private /* synthetic */ Reference target;
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _staticMethodCall_closure25(Object _outerInstance, Object _thisObject, Reference name, Reference target) {
                Reference reference;
                Reference reference2;
                CallSite[] callSiteArray = _staticMethodCall_closure25.$getCallSiteArray();
                super(_outerInstance, _thisObject);
                this.name = reference2 = name;
                this.target = reference = target;
            }

            public Object doCall(Object it) {
                CallSite[] callSiteArray = _staticMethodCall_closure25.$getCallSiteArray();
                callSiteArray[0].call(callSiteArray[1].callGroovyObjectGetProperty(this), 0, this.name.get());
                callSiteArray[2].call(callSiteArray[3].callGroovyObjectGetProperty(this), 0, callSiteArray[4].call(ClassHelper.class, this.target.get()));
                return callSiteArray[5].callConstructor((Object)StaticMethodCallExpression.class, ScriptBytecodeAdapter.despreadList(new Object[0], new Object[]{callSiteArray[6].callCurrent(this, "staticMethodCall", ScriptBytecodeAdapter.createList(new Object[]{ClassNode.class, String.class, Expression.class}))}, new int[]{0}));
            }

            @Generated
            public String getName() {
                CallSite[] callSiteArray = _staticMethodCall_closure25.$getCallSiteArray();
                return ShortTypeHandling.castToString(this.name.get());
            }

            @Generated
            public Class getTarget() {
                CallSite[] callSiteArray = _staticMethodCall_closure25.$getCallSiteArray();
                return ShortTypeHandling.castToClass(this.target.get());
            }

            @Generated
            public Object doCall() {
                CallSite[] callSiteArray = _staticMethodCall_closure25.$getCallSiteArray();
                return this.doCall(null);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (this.getClass() != _staticMethodCall_closure25.class) {
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
                stringArray[0] = "add";
                stringArray[1] = "expression";
                stringArray[2] = "add";
                stringArray[3] = "expression";
                stringArray[4] = "make";
                stringArray[5] = "<$constructor$>";
                stringArray[6] = "enforceConstraints";
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[7];
                _staticMethodCall_closure25.$createCallSiteArray_1(stringArray);
                return new CallSiteArray(_staticMethodCall_closure25.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _staticMethodCall_closure25.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        callSiteArray[144].callCurrent(this, "StaticMethodCallExpression", argBlock, new _staticMethodCall_closure25(this, this, name2, target2));
    }

    public void staticMethodCall(MethodClosure target, @DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        Reference<MethodClosure> target2 = new Reference<MethodClosure>(target);
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        public final class _staticMethodCall_closure26
        extends Closure
        implements GeneratedClosure {
            private /* synthetic */ Reference target;
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _staticMethodCall_closure26(Object _outerInstance, Object _thisObject, Reference target) {
                Reference reference;
                CallSite[] callSiteArray = _staticMethodCall_closure26.$getCallSiteArray();
                super(_outerInstance, _thisObject);
                this.target = reference = target;
            }

            public Object doCall(Object it) {
                CallSite[] callSiteArray = _staticMethodCall_closure26.$getCallSiteArray();
                callSiteArray[0].call(callSiteArray[1].callGroovyObjectGetProperty(this), 0, callSiteArray[2].callGroovyObjectGetProperty(this.target.get()));
                callSiteArray[3].call(callSiteArray[4].callGroovyObjectGetProperty(this), 0, callSiteArray[5].call(ClassHelper.class, callSiteArray[6].callGetProperty(callSiteArray[7].callGroovyObjectGetProperty(this.target.get())), false));
                return callSiteArray[8].callConstructor((Object)StaticMethodCallExpression.class, ScriptBytecodeAdapter.despreadList(new Object[0], new Object[]{callSiteArray[9].callCurrent(this, "staticMethodCall", ScriptBytecodeAdapter.createList(new Object[]{ClassNode.class, String.class, Expression.class}))}, new int[]{0}));
            }

            @Generated
            public MethodClosure getTarget() {
                CallSite[] callSiteArray = _staticMethodCall_closure26.$getCallSiteArray();
                return (MethodClosure)ScriptBytecodeAdapter.castToType(this.target.get(), MethodClosure.class);
            }

            @Generated
            public Object doCall() {
                CallSite[] callSiteArray = _staticMethodCall_closure26.$getCallSiteArray();
                return this.doCall(null);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (this.getClass() != _staticMethodCall_closure26.class) {
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
                stringArray[0] = "add";
                stringArray[1] = "expression";
                stringArray[2] = "method";
                stringArray[3] = "add";
                stringArray[4] = "expression";
                stringArray[5] = "makeWithoutCaching";
                stringArray[6] = "class";
                stringArray[7] = "owner";
                stringArray[8] = "<$constructor$>";
                stringArray[9] = "enforceConstraints";
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[10];
                _staticMethodCall_closure26.$createCallSiteArray_1(stringArray);
                return new CallSiteArray(_staticMethodCall_closure26.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _staticMethodCall_closure26.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        callSiteArray[145].callCurrent(this, "StaticMethodCallExpression", argBlock, new _staticMethodCall_closure26(this, this, target2));
    }

    public void constructor(int modifiers, @DelegatesTo(value=AstSpecificationCompiler.class) Closure argBlock) {
        Reference<Integer> modifiers2 = new Reference<Integer>(modifiers);
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        public final class _constructor_closure27
        extends Closure
        implements GeneratedClosure {
            private /* synthetic */ Reference modifiers;
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _constructor_closure27(Object _outerInstance, Object _thisObject, Reference modifiers) {
                Reference reference;
                CallSite[] callSiteArray = _constructor_closure27.$getCallSiteArray();
                super(_outerInstance, _thisObject);
                this.modifiers = reference = modifiers;
            }

            public Object doCall(Object it) {
                CallSite[] callSiteArray = _constructor_closure27.$getCallSiteArray();
                Object annotations = null;
                if (ScriptBytecodeAdapter.compareGreaterThan(callSiteArray[0].call(callSiteArray[1].callGroovyObjectGetProperty(this)), 3)) {
                    Object object;
                    annotations = object = callSiteArray[2].call(callSiteArray[3].callGroovyObjectGetProperty(this), 3);
                    callSiteArray[4].call(callSiteArray[5].callGroovyObjectGetProperty(this), 3);
                }
                callSiteArray[6].call(callSiteArray[7].callGroovyObjectGetProperty(this), 0, this.modifiers.get());
                Object result = callSiteArray[8].callConstructor((Object)ConstructorNode.class, ScriptBytecodeAdapter.despreadList(new Object[0], new Object[]{callSiteArray[9].callCurrent(this, "constructor", ScriptBytecodeAdapter.createList(new Object[]{Integer.class, Parameter[].class, ClassNode[].class, Statement.class}))}, new int[]{0}));
                if (DefaultTypeTransformation.booleanUnbox(annotations)) {
                    callSiteArray[10].call(result, callSiteArray[11].callConstructor(ArrayList.class, annotations));
                }
                return result;
            }

            @Generated
            public int getModifiers() {
                CallSite[] callSiteArray = _constructor_closure27.$getCallSiteArray();
                return DefaultTypeTransformation.intUnbox(this.modifiers.get());
            }

            @Generated
            public Object doCall() {
                CallSite[] callSiteArray = _constructor_closure27.$getCallSiteArray();
                return this.doCall(null);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (this.getClass() != _constructor_closure27.class) {
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
                stringArray[0] = "size";
                stringArray[1] = "expression";
                stringArray[2] = "getAt";
                stringArray[3] = "expression";
                stringArray[4] = "remove";
                stringArray[5] = "expression";
                stringArray[6] = "add";
                stringArray[7] = "expression";
                stringArray[8] = "<$constructor$>";
                stringArray[9] = "enforceConstraints";
                stringArray[10] = "addAnnotations";
                stringArray[11] = "<$constructor$>";
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[12];
                _constructor_closure27.$createCallSiteArray_1(stringArray);
                return new CallSiteArray(_constructor_closure27.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _constructor_closure27.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        callSiteArray[146].callCurrent(this, "ConstructorNode", argBlock, new _constructor_closure27(this, this, modifiers2));
    }

    @Generated
    public void importNode(Class target) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        this.importNode(target, null);
    }

    @Generated
    public void breakStatement() {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        this.breakStatement(null);
    }

    @Generated
    public void continueStatement() {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        this.continueStatement(null);
    }

    @Generated
    public void dynamicVariable(String variable) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        this.dynamicVariable(variable, false);
    }

    @Generated
    public void parameter(Map<String, Class> args) {
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        this.parameter(args, null);
    }

    @Generated
    public void genericsType(Class type) {
        Reference<Class> type2 = new Reference<Class>(type);
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        this.genericsType(type2.get(), null);
    }

    @Generated
    public void annotation(Class target) {
        Reference<Class> target2 = new Reference<Class>(target);
        CallSite[] callSiteArray = AstSpecificationCompiler.$getCallSiteArray();
        this.annotation(target2.get(), null);
    }

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != AstSpecificationCompiler.class) {
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

    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
        stringArray[0] = "call";
        stringArray[1] = "size";
        stringArray[2] = "size";
        stringArray[3] = "<$constructor$>";
        stringArray[4] = "collect";
        stringArray[5] = "minus";
        stringArray[6] = "size";
        stringArray[7] = "captureAndCreateNode";
        stringArray[8] = "simpleName";
        stringArray[9] = "captureAndCreateNode";
        stringArray[10] = "simpleName";
        stringArray[11] = "captureAndCreateNode";
        stringArray[12] = "captureAndCreateNode";
        stringArray[13] = "simpleName";
        stringArray[14] = "class";
        stringArray[15] = "captureAndCreateNode";
        stringArray[16] = "simpleName";
        stringArray[17] = "captureAndCreateNode";
        stringArray[18] = "simpleName";
        stringArray[19] = "makeNodeWithClassParameter";
        stringArray[20] = "makeNodeWithClassParameter";
        stringArray[21] = "makeNode";
        stringArray[22] = "makeNode";
        stringArray[23] = "makeNode";
        stringArray[24] = "makeNode";
        stringArray[25] = "makeNodeFromList";
        stringArray[26] = "makeNodeFromList";
        stringArray[27] = "makeNode";
        stringArray[28] = "makeNodeWithStringParameter";
        stringArray[29] = "makeNode";
        stringArray[30] = "makeNode";
        stringArray[31] = "makeNode";
        stringArray[32] = "leftShift";
        stringArray[33] = "INSTANCE";
        stringArray[34] = "leftShift";
        stringArray[35] = "leftShift";
        stringArray[36] = "<$constructor$>";
        stringArray[37] = "make";
        stringArray[38] = "makeNode";
        stringArray[39] = "makeNode";
        stringArray[40] = "makeNode";
        stringArray[41] = "makeNode";
        stringArray[42] = "makeNode";
        stringArray[43] = "makeNode";
        stringArray[44] = "leftShift";
        stringArray[45] = "<$constructor$>";
        stringArray[46] = "leftShift";
        stringArray[47] = "<$constructor$>";
        stringArray[48] = "leftShift";
        stringArray[49] = "<$constructor$>";
        stringArray[50] = "makeNode";
        stringArray[51] = "makeNode";
        stringArray[52] = "block";
        stringArray[53] = "makeNode";
        stringArray[54] = "makeNode";
        stringArray[55] = "leftShift";
        stringArray[56] = "<$constructor$>";
        stringArray[57] = "makeArrayOfNodes";
        stringArray[58] = "makeListOfNodes";
        stringArray[59] = "makeListOfNodes";
        stringArray[60] = "makeListOfNodes";
        stringArray[61] = "makeListOfNodes";
        stringArray[62] = "makeListOfNodes";
        stringArray[63] = "makeListOfNodes";
        stringArray[64] = "makeListOfNodes";
        stringArray[65] = "leftShift";
        stringArray[66] = "leftShift";
        stringArray[67] = "constX";
        stringArray[68] = "makeNode";
        stringArray[69] = "makeNode";
        stringArray[70] = "makeNode";
        stringArray[71] = "makeNode";
        stringArray[72] = "makeNode";
        stringArray[73] = "makeNodeFromList";
        stringArray[74] = "makeNode";
        stringArray[75] = "makeNodeFromList";
        stringArray[76] = "makeNode";
        stringArray[77] = "makeNode";
        stringArray[78] = "makeNode";
        stringArray[79] = "makeNode";
        stringArray[80] = "makeNode";
        stringArray[81] = "leftShift";
        stringArray[82] = "<$constructor$>";
        stringArray[83] = "make";
        stringArray[84] = "makeNode";
        stringArray[85] = "makeNode";
        stringArray[86] = "makeNode";
        stringArray[87] = "makeNodeFromList";
        stringArray[88] = "makeListOfNodes";
        stringArray[89] = "makeListOfNodes";
        stringArray[90] = "makeListOfNodes";
        stringArray[91] = "leftShift";
        stringArray[92] = "make";
        stringArray[93] = "makeArrayOfNodes";
        stringArray[94] = "captureAndCreateNode";
        stringArray[95] = "<$constructor$>";
        stringArray[96] = "size";
        stringArray[97] = "<$constructor$>";
        stringArray[98] = "each";
        stringArray[99] = "each";
        stringArray[100] = "captureAndCreateNode";
        stringArray[101] = "captureAndCreateNode";
        stringArray[102] = "leftShift";
        stringArray[103] = "<$constructor$>";
        stringArray[104] = "make";
        stringArray[105] = "makeListOfNodes";
        stringArray[106] = "leftShift";
        stringArray[107] = "make";
        stringArray[108] = "captureAndCreateNode";
        stringArray[109] = "leftShift";
        stringArray[110] = "<$constructor$>";
        stringArray[111] = "makeNodeFromList";
        stringArray[112] = "captureAndCreateNode";
        stringArray[113] = "leftShift";
        stringArray[114] = "<$constructor$>";
        stringArray[115] = "make";
        stringArray[116] = "captureAndCreateNode";
        stringArray[117] = "captureAndCreateNode";
        stringArray[118] = "captureAndCreateNode";
        stringArray[119] = "captureAndCreateNode";
        stringArray[120] = "leftShift";
        stringArray[121] = "<$constructor$>";
        stringArray[122] = "captureAndCreateNode";
        stringArray[123] = "<$constructor$>";
        stringArray[124] = "lookupKeyword";
        stringArray[125] = "UNKNOWN";
        stringArray[126] = "lookupSymbol";
        stringArray[127] = "UNKNOWN";
        stringArray[128] = "<$constructor$>";
        stringArray[129] = "leftShift";
        stringArray[130] = "<$constructor$>";
        stringArray[131] = "<$constructor$>";
        stringArray[132] = "leftShift";
        stringArray[133] = "<$constructor$>";
        stringArray[134] = "constX";
        stringArray[135] = "from";
        stringArray[136] = "constX";
        stringArray[137] = "to";
        stringArray[138] = "captureAndCreateNode";
        stringArray[139] = "each";
        stringArray[140] = "entrySet";
        stringArray[141] = "captureAndCreateNode";
        stringArray[142] = "captureAndCreateNode";
        stringArray[143] = "captureAndCreateNode";
        stringArray[144] = "captureAndCreateNode";
        stringArray[145] = "captureAndCreateNode";
        stringArray[146] = "captureAndCreateNode";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[147];
        AstSpecificationCompiler.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(AstSpecificationCompiler.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = AstSpecificationCompiler.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }
}

