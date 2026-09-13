/*
 * Decompiled with CFR 0.152.
 */
package groovy.beans;

import groovy.beans.ListenerList;
import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import groovy.lang.Reference;
import groovy.transform.Generated;
import groovy.transform.Internal;
import groovyjarjarasm.asm.Opcodes;
import java.beans.Transient;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Collection;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.GenericsType;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.VariableScope;
import org.codehaus.groovy.ast.expr.ListExpression;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.ForStatement;
import org.codehaus.groovy.ast.stmt.ReturnStatement;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.control.messages.SyntaxErrorMessage;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.ArrayUtil;
import org.codehaus.groovy.runtime.BytecodeInterface8;
import org.codehaus.groovy.runtime.GStringImpl;
import org.codehaus.groovy.runtime.GeneratedClosure;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;
import org.codehaus.groovy.syntax.SyntaxException;
import org.codehaus.groovy.transform.ASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;

@GroovyASTTransformation(phase=CompilePhase.CANONICALIZATION)
public class ListenerListASTTransformation
implements ASTTransformation,
Opcodes,
GroovyObject {
    private static final Class MY_CLASS;
    private static final ClassNode COLLECTION_TYPE;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private transient /* synthetic */ MetaClass metaClass;
    private static /* synthetic */ SoftReference $callSiteArray;

    @Generated
    public ListenerListASTTransformation() {
        MetaClass metaClass;
        CallSite[] callSiteArray = ListenerListASTTransformation.$getCallSiteArray();
        this.metaClass = metaClass = this.$getStaticMetaClass();
    }

    @Override
    public void visit(ASTNode[] nodes, SourceUnit source) {
        Object object;
        int n;
        int n2;
        Reference<SourceUnit> source2 = new Reference<SourceUnit>(source);
        CallSite[] callSiteArray = ListenerListASTTransformation.$getCallSiteArray();
        if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            if (!(callSiteArray[0].call((Object)nodes, 0) instanceof AnnotationNode) || !(callSiteArray[1].call((Object)nodes, 1) instanceof AnnotatedNode)) {
                throw (Throwable)callSiteArray[2].callConstructor(RuntimeException.class, new GStringImpl(new Object[]{callSiteArray[3].callGetProperty(callSiteArray[4].callGroovyObjectGetProperty(this)), callSiteArray[5].callGetProperty(callSiteArray[6].callGroovyObjectGetProperty(this))}, new String[]{"Internal error: wrong types: ", " / ", ""}));
            }
        } else if (!(BytecodeInterface8.objectArrayGet(nodes, 0) instanceof AnnotationNode) || !(BytecodeInterface8.objectArrayGet(nodes, 1) instanceof AnnotatedNode)) {
            throw (Throwable)callSiteArray[7].callConstructor(RuntimeException.class, new GStringImpl(new Object[]{callSiteArray[8].callGetProperty(callSiteArray[9].callGroovyObjectGetProperty(this)), callSiteArray[10].callGetProperty(callSiteArray[11].callGroovyObjectGetProperty(this))}, new String[]{"Internal error: wrong types: ", " / ", ""}));
        }
        Reference<Object> node = new Reference<Object>(null);
        if (!BytecodeInterface8.isOrigInt() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            Object object2 = callSiteArray[12].call((Object)nodes, 0);
            node.set(((AnnotationNode)ScriptBytecodeAdapter.castToType(object2, AnnotationNode.class)));
        } else {
            Object object3 = BytecodeInterface8.objectArrayGet(nodes, 0);
            node.set(((AnnotationNode)ScriptBytecodeAdapter.castToType(object3, AnnotationNode.class)));
        }
        Reference<Object> field = new Reference<Object>(null);
        if (!BytecodeInterface8.isOrigInt() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            Object object4 = callSiteArray[13].call((Object)nodes, 1);
            field.set(((FieldNode)ScriptBytecodeAdapter.castToType(object4, FieldNode.class)));
        } else {
            Object object5 = BytecodeInterface8.objectArrayGet(nodes, 1);
            field.set(((FieldNode)ScriptBytecodeAdapter.castToType(object5, FieldNode.class)));
        }
        Reference<Object> declaringClass = new Reference<Object>(null);
        if (!BytecodeInterface8.isOrigInt() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            Object object6 = callSiteArray[14].callGetProperty(callSiteArray[15].call((Object)nodes, 1));
            declaringClass.set(((ClassNode)ScriptBytecodeAdapter.castToType(object6, ClassNode.class)));
        } else {
            Object object7 = callSiteArray[16].callGetProperty(BytecodeInterface8.objectArrayGet(nodes, 1));
            declaringClass.set(((ClassNode)ScriptBytecodeAdapter.castToType(object7, ClassNode.class)));
        }
        ClassNode parentClass = (ClassNode)ScriptBytecodeAdapter.castToType(callSiteArray[17].callGetProperty(field.get()), ClassNode.class);
        int isCollection = 0;
        isCollection = !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass() ? (n2 = DefaultTypeTransformation.booleanUnbox(callSiteArray[18].call((Object)parentClass, COLLECTION_TYPE)) || DefaultTypeTransformation.booleanUnbox(callSiteArray[19].call((Object)parentClass, COLLECTION_TYPE)) ? 1 : 0) : (n = DefaultTypeTransformation.booleanUnbox(callSiteArray[20].call((Object)parentClass, COLLECTION_TYPE)) || DefaultTypeTransformation.booleanUnbox(callSiteArray[21].call((Object)parentClass, COLLECTION_TYPE)) ? 1 : 0);
        if (isCollection == 0) {
            callSiteArray[22].callStatic(ListenerListASTTransformation.class, node.get(), source2.get(), callSiteArray[23].call(callSiteArray[24].call((Object)"@", callSiteArray[25].callGetProperty(MY_CLASS)), " can only annotate collection properties."));
            return;
        }
        Reference<Object> types = new Reference<Object>(callSiteArray[26].callGetProperty(callSiteArray[27].callGetProperty(field.get())));
        if (!DefaultTypeTransformation.booleanUnbox(types.get())) {
            callSiteArray[28].callStatic(ListenerListASTTransformation.class, node.get(), source2.get(), callSiteArray[29].call(callSiteArray[30].call((Object)"@", callSiteArray[31].callGetProperty(MY_CLASS)), " fields must have a generic type."));
            return;
        }
        if (DefaultTypeTransformation.booleanUnbox(callSiteArray[32].callGetProperty(callSiteArray[33].call(types.get(), 0)))) {
            callSiteArray[34].callStatic(ListenerListASTTransformation.class, node.get(), source2.get(), callSiteArray[35].call(callSiteArray[36].call((Object)"@", callSiteArray[37].callGetProperty(MY_CLASS)), " fields with generic wildcards not yet supported."));
            return;
        }
        Object listener = callSiteArray[38].callGetProperty(callSiteArray[39].call(types.get(), 0));
        if (!DefaultTypeTransformation.booleanUnbox(callSiteArray[40].callGetProperty(field.get()))) {
            Object object8 = callSiteArray[41].callConstructor(ListExpression.class);
            ScriptBytecodeAdapter.setProperty(object8, null, field.get(), "initialValueExpression");
        }
        Object name = DefaultTypeTransformation.booleanUnbox(object = callSiteArray[42].callGetPropertySafe(callSiteArray[43].call((Object)node.get(), "name"))) ? object : callSiteArray[44].callGetProperty(listener);
        public final class _visit_closure1
        extends Closure
        implements GeneratedClosure {
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _visit_closure1(Object _outerInstance, Object _thisObject) {
                CallSite[] callSiteArray = _visit_closure1.$getCallSiteArray();
                super(_outerInstance, _thisObject);
            }

            public Object doCall(MethodNode m) {
                CallSite[] callSiteArray = _visit_closure1.$getCallSiteArray();
                if (!BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                    return DefaultTypeTransformation.booleanUnbox(callSiteArray[0].call(m)) && !DefaultTypeTransformation.booleanUnbox(callSiteArray[1].call(m)) && !DefaultTypeTransformation.booleanUnbox(callSiteArray[2].call(m));
                }
                return DefaultTypeTransformation.booleanUnbox(callSiteArray[3].call(m)) && !DefaultTypeTransformation.booleanUnbox(callSiteArray[4].call(m)) && !DefaultTypeTransformation.booleanUnbox(callSiteArray[5].call(m));
            }

            @Generated
            public Object call(MethodNode m) {
                CallSite[] callSiteArray = _visit_closure1.$getCallSiteArray();
                return callSiteArray[6].callCurrent((GroovyObject)this, m);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (this.getClass() != _visit_closure1.class) {
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
                stringArray[0] = "isPublic";
                stringArray[1] = "isSynthetic";
                stringArray[2] = "isStatic";
                stringArray[3] = "isPublic";
                stringArray[4] = "isSynthetic";
                stringArray[5] = "isStatic";
                stringArray[6] = "doCall";
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[7];
                _visit_closure1.$createCallSiteArray_1(stringArray);
                return new CallSiteArray(_visit_closure1.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _visit_closure1.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        Object fireList = callSiteArray[45].call(callSiteArray[46].callGetProperty(listener), new _visit_closure1(this, this));
        Object object9 = callSiteArray[47].callGetPropertySafe(callSiteArray[48].call((Object)node.get(), "synchronize"));
        Reference<Object> synchronize = new Reference<Object>(DefaultTypeTransformation.booleanUnbox(object9) ? object9 : Boolean.valueOf(false));
        callSiteArray[49].callCurrent((GroovyObject)this, ArrayUtil.createArray(source2.get(), node.get(), declaringClass.get(), field.get(), listener, name, synchronize.get()));
        callSiteArray[50].callCurrent((GroovyObject)this, ArrayUtil.createArray(source2.get(), node.get(), declaringClass.get(), field.get(), listener, name, synchronize.get()));
        callSiteArray[51].callCurrent((GroovyObject)this, ArrayUtil.createArray(source2.get(), node.get(), declaringClass.get(), field.get(), listener, name, synchronize.get()));
        public final class _visit_closure2
        extends Closure
        implements GeneratedClosure {
            private /* synthetic */ Reference source;
            private /* synthetic */ Reference node;
            private /* synthetic */ Reference declaringClass;
            private /* synthetic */ Reference field;
            private /* synthetic */ Reference types;
            private /* synthetic */ Reference synchronize;
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _visit_closure2(Object _outerInstance, Object _thisObject, Reference source, Reference node, Reference declaringClass, Reference field, Reference types, Reference synchronize) {
                Reference reference;
                Reference reference2;
                Reference reference3;
                Reference reference4;
                Reference reference5;
                Reference reference6;
                CallSite[] callSiteArray = _visit_closure2.$getCallSiteArray();
                super(_outerInstance, _thisObject);
                this.source = reference6 = source;
                this.node = reference5 = node;
                this.declaringClass = reference4 = declaringClass;
                this.field = reference3 = field;
                this.types = reference2 = types;
                this.synchronize = reference = synchronize;
            }

            public Object doCall(MethodNode method) {
                CallSite[] callSiteArray = _visit_closure2.$getCallSiteArray();
                return callSiteArray[0].callCurrent((GroovyObject)this, ArrayUtil.createArray(this.source.get(), this.node.get(), this.declaringClass.get(), this.field.get(), this.types.get(), this.synchronize.get(), method));
            }

            @Generated
            public Object call(MethodNode method) {
                CallSite[] callSiteArray = _visit_closure2.$getCallSiteArray();
                return callSiteArray[1].callCurrent((GroovyObject)this, method);
            }

            @Generated
            public SourceUnit getSource() {
                CallSite[] callSiteArray = _visit_closure2.$getCallSiteArray();
                return (SourceUnit)ScriptBytecodeAdapter.castToType(this.source.get(), SourceUnit.class);
            }

            @Generated
            public AnnotationNode getNode() {
                CallSite[] callSiteArray = _visit_closure2.$getCallSiteArray();
                return (AnnotationNode)ScriptBytecodeAdapter.castToType(this.node.get(), AnnotationNode.class);
            }

            @Generated
            public ClassNode getDeclaringClass() {
                CallSite[] callSiteArray = _visit_closure2.$getCallSiteArray();
                return (ClassNode)ScriptBytecodeAdapter.castToType(this.declaringClass.get(), ClassNode.class);
            }

            @Generated
            public FieldNode getField() {
                CallSite[] callSiteArray = _visit_closure2.$getCallSiteArray();
                return (FieldNode)ScriptBytecodeAdapter.castToType(this.field.get(), FieldNode.class);
            }

            @Generated
            public Object getTypes() {
                CallSite[] callSiteArray = _visit_closure2.$getCallSiteArray();
                return this.types.get();
            }

            @Generated
            public Object getSynchronize() {
                CallSite[] callSiteArray = _visit_closure2.$getCallSiteArray();
                return this.synchronize.get();
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (this.getClass() != _visit_closure2.class) {
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
                stringArray[0] = "addFireMethods";
                stringArray[1] = "doCall";
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[2];
                _visit_closure2.$createCallSiteArray_1(stringArray);
                return new CallSiteArray(_visit_closure2.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _visit_closure2.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        callSiteArray[52].call(fireList, new _visit_closure2(this, this, source2, node, declaringClass, field, types, synchronize));
    }

    private static Object addError(AnnotationNode node, SourceUnit source, String message) {
        CallSite[] callSiteArray = ListenerListASTTransformation.$getCallSiteArray();
        return callSiteArray[53].call(callSiteArray[54].callGetProperty(source), callSiteArray[55].callConstructor(SyntaxErrorMessage.class, callSiteArray[56].callConstructor(SyntaxException.class, message, callSiteArray[57].callGetProperty(node), callSiteArray[58].callGetProperty(node)), source));
    }

    public void addAddListener(SourceUnit source, AnnotationNode node, ClassNode declaringClass, FieldNode field, ClassNode listener, String name, Object synchronize) {
        CallSite[] callSiteArray = ListenerListASTTransformation.$getCallSiteArray();
        Integer methodModifiers = null;
        if (!BytecodeInterface8.isOrigInt() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            int n = DefaultTypeTransformation.booleanUnbox(synchronize) ? DefaultTypeTransformation.intUnbox(callSiteArray[59].call(callSiteArray[60].callGroovyObjectGetProperty(this), callSiteArray[61].callGroovyObjectGetProperty(this))) : DefaultTypeTransformation.intUnbox(callSiteArray[62].callGroovyObjectGetProperty(this));
            methodModifiers = n;
        } else {
            int n = DefaultTypeTransformation.booleanUnbox(synchronize) ? DefaultTypeTransformation.intUnbox(callSiteArray[63].callGroovyObjectGetProperty(this)) | DefaultTypeTransformation.intUnbox(callSiteArray[64].callGroovyObjectGetProperty(this)) : DefaultTypeTransformation.intUnbox(callSiteArray[65].callGroovyObjectGetProperty(this));
            methodModifiers = n;
        }
        Object methodReturnType = callSiteArray[66].call(ClassHelper.class, callSiteArray[67].callGetProperty(Void.class));
        GStringImpl methodName = new GStringImpl(new Object[]{callSiteArray[68].call(name)}, new String[]{"add", ""});
        Object cn = callSiteArray[69].call(ClassHelper.class, callSiteArray[70].callGetProperty(listener));
        ClassNode classNode = listener;
        ScriptBytecodeAdapter.setProperty(classNode, null, cn, "redirect");
        Parameter[] methodParameter = (Parameter[])ScriptBytecodeAdapter.asType(ScriptBytecodeAdapter.createList(new Object[]{callSiteArray[71].callStatic(GeneralUtils.class, cn, "listener")}), Parameter[].class);
        if (DefaultTypeTransformation.booleanUnbox(callSiteArray[72].call(declaringClass, methodName, methodParameter))) {
            callSiteArray[73].callStatic(ListenerListASTTransformation.class, node, source, new GStringImpl(new Object[]{callSiteArray[74].callGetProperty(MY_CLASS), callSiteArray[75].callGetProperty(declaringClass), methodName}, new String[]{"Conflict using @", ". Class ", " already has method ", ""}));
            return;
        }
        BlockStatement block = (BlockStatement)ScriptBytecodeAdapter.castToType(callSiteArray[76].callConstructor(BlockStatement.class), BlockStatement.class);
        callSiteArray[77].call((Object)block, ScriptBytecodeAdapter.createList(new Object[]{callSiteArray[78].callStatic(GeneralUtils.class, callSiteArray[79].callStatic(GeneralUtils.class, callSiteArray[80].callStatic(GeneralUtils.class, "listener")), callSiteArray[81].callGetProperty(ReturnStatement.class)), callSiteArray[82].callStatic(GeneralUtils.class, callSiteArray[83].callStatic(GeneralUtils.class, callSiteArray[84].callStatic(GeneralUtils.class, callSiteArray[85].callGetProperty(field))), callSiteArray[86].callStatic(GeneralUtils.class, callSiteArray[87].callStatic(GeneralUtils.class, callSiteArray[88].callGetProperty(field)), callSiteArray[89].callConstructor(ListExpression.class))), callSiteArray[90].callStatic(GeneralUtils.class, callSiteArray[91].callStatic(GeneralUtils.class, callSiteArray[92].callStatic(GeneralUtils.class, callSiteArray[93].callGetProperty(field)), callSiteArray[94].callStatic(GeneralUtils.class, "add"), callSiteArray[95].callStatic(GeneralUtils.class, callSiteArray[96].callStatic(GeneralUtils.class, "listener"))))}));
        callSiteArray[97].call((Object)declaringClass, callSiteArray[98].callConstructor((Object)MethodNode.class, ArrayUtil.createArray(methodName, methodModifiers, methodReturnType, methodParameter, ScriptBytecodeAdapter.createPojoWrapper((ClassNode[])ScriptBytecodeAdapter.asType(ScriptBytecodeAdapter.createList(new Object[0]), ClassNode[].class), ClassNode[].class), block)));
    }

    public void addRemoveListener(SourceUnit source, AnnotationNode node, ClassNode declaringClass, FieldNode field, ClassNode listener, String name, Object synchronize) {
        CallSite[] callSiteArray = ListenerListASTTransformation.$getCallSiteArray();
        Integer methodModifiers = null;
        if (!BytecodeInterface8.isOrigInt() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            int n = DefaultTypeTransformation.booleanUnbox(synchronize) ? DefaultTypeTransformation.intUnbox(callSiteArray[99].call(callSiteArray[100].callGroovyObjectGetProperty(this), callSiteArray[101].callGroovyObjectGetProperty(this))) : DefaultTypeTransformation.intUnbox(callSiteArray[102].callGroovyObjectGetProperty(this));
            methodModifiers = n;
        } else {
            int n = DefaultTypeTransformation.booleanUnbox(synchronize) ? DefaultTypeTransformation.intUnbox(callSiteArray[103].callGroovyObjectGetProperty(this)) | DefaultTypeTransformation.intUnbox(callSiteArray[104].callGroovyObjectGetProperty(this)) : DefaultTypeTransformation.intUnbox(callSiteArray[105].callGroovyObjectGetProperty(this));
            methodModifiers = n;
        }
        Object methodReturnType = callSiteArray[106].call(ClassHelper.class, callSiteArray[107].callGetProperty(Void.class));
        GStringImpl methodName = new GStringImpl(new Object[]{callSiteArray[108].call(name)}, new String[]{"remove", ""});
        Object cn = callSiteArray[109].call(ClassHelper.class, callSiteArray[110].callGetProperty(listener));
        ClassNode classNode = listener;
        ScriptBytecodeAdapter.setProperty(classNode, null, cn, "redirect");
        Parameter[] methodParameter = (Parameter[])ScriptBytecodeAdapter.asType(ScriptBytecodeAdapter.createList(new Object[]{callSiteArray[111].callStatic(GeneralUtils.class, cn, "listener")}), Parameter[].class);
        if (DefaultTypeTransformation.booleanUnbox(callSiteArray[112].call(declaringClass, methodName, methodParameter))) {
            callSiteArray[113].callStatic(ListenerListASTTransformation.class, node, source, new GStringImpl(new Object[]{callSiteArray[114].callGetProperty(MY_CLASS), callSiteArray[115].callGetProperty(declaringClass), methodName}, new String[]{"Conflict using @", ". Class ", " already has method ", ""}));
            return;
        }
        BlockStatement block = (BlockStatement)ScriptBytecodeAdapter.castToType(callSiteArray[116].callConstructor(BlockStatement.class), BlockStatement.class);
        callSiteArray[117].call((Object)block, ScriptBytecodeAdapter.createList(new Object[]{callSiteArray[118].callStatic(GeneralUtils.class, callSiteArray[119].callStatic(GeneralUtils.class, callSiteArray[120].callStatic(GeneralUtils.class, "listener")), callSiteArray[121].callGetProperty(ReturnStatement.class)), callSiteArray[122].callStatic(GeneralUtils.class, callSiteArray[123].callStatic(GeneralUtils.class, callSiteArray[124].callStatic(GeneralUtils.class, callSiteArray[125].callGetProperty(field))), callSiteArray[126].callStatic(GeneralUtils.class, callSiteArray[127].callStatic(GeneralUtils.class, callSiteArray[128].callGetProperty(field)), callSiteArray[129].callConstructor(ListExpression.class))), callSiteArray[130].callStatic(GeneralUtils.class, callSiteArray[131].callStatic(GeneralUtils.class, callSiteArray[132].callStatic(GeneralUtils.class, callSiteArray[133].callGetProperty(field)), callSiteArray[134].callStatic(GeneralUtils.class, "remove"), callSiteArray[135].callStatic(GeneralUtils.class, callSiteArray[136].callStatic(GeneralUtils.class, "listener"))))}));
        callSiteArray[137].call((Object)declaringClass, callSiteArray[138].callConstructor((Object)MethodNode.class, ArrayUtil.createArray(methodName, methodModifiers, methodReturnType, methodParameter, ScriptBytecodeAdapter.createPojoWrapper((ClassNode[])ScriptBytecodeAdapter.asType(ScriptBytecodeAdapter.createList(new Object[0]), ClassNode[].class), ClassNode[].class), block)));
    }

    public void addGetListeners(SourceUnit source, AnnotationNode node, ClassNode declaringClass, FieldNode field, ClassNode listener, String name, Object synchronize) {
        CallSite[] callSiteArray = ListenerListASTTransformation.$getCallSiteArray();
        Integer methodModifiers = null;
        if (!BytecodeInterface8.isOrigInt() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            int n = DefaultTypeTransformation.booleanUnbox(synchronize) ? DefaultTypeTransformation.intUnbox(callSiteArray[139].call(callSiteArray[140].callGroovyObjectGetProperty(this), callSiteArray[141].callGroovyObjectGetProperty(this))) : DefaultTypeTransformation.intUnbox(callSiteArray[142].callGroovyObjectGetProperty(this));
            methodModifiers = n;
        } else {
            int n = DefaultTypeTransformation.booleanUnbox(synchronize) ? DefaultTypeTransformation.intUnbox(callSiteArray[143].callGroovyObjectGetProperty(this)) | DefaultTypeTransformation.intUnbox(callSiteArray[144].callGroovyObjectGetProperty(this)) : DefaultTypeTransformation.intUnbox(callSiteArray[145].callGroovyObjectGetProperty(this));
            methodModifiers = n;
        }
        Object methodReturnType = callSiteArray[146].call(listener);
        GStringImpl methodName = new GStringImpl(new Object[]{callSiteArray[147].call(name)}, new String[]{"get", "s"});
        Parameter[] methodParameter = (Parameter[])ScriptBytecodeAdapter.asType(ScriptBytecodeAdapter.createList(new Object[0]), Parameter[].class);
        if (DefaultTypeTransformation.booleanUnbox(callSiteArray[148].call(declaringClass, methodName, methodParameter))) {
            callSiteArray[149].callStatic(ListenerListASTTransformation.class, node, source, new GStringImpl(new Object[]{callSiteArray[150].callGetProperty(MY_CLASS), callSiteArray[151].callGetProperty(declaringClass), methodName}, new String[]{"Conflict using @", ". Class ", " already has method ", ""}));
            return;
        }
        BlockStatement block = (BlockStatement)ScriptBytecodeAdapter.castToType(callSiteArray[152].callConstructor(BlockStatement.class), BlockStatement.class);
        callSiteArray[153].call((Object)block, ScriptBytecodeAdapter.createList(new Object[]{callSiteArray[154].callStatic(GeneralUtils.class, callSiteArray[155].callStatic(GeneralUtils.class, "__result", callSiteArray[156].call(ClassHelper.class)), callSiteArray[157].callConstructor(ListExpression.class)), callSiteArray[158].callStatic(GeneralUtils.class, callSiteArray[159].callStatic(GeneralUtils.class, callSiteArray[160].callStatic(GeneralUtils.class, callSiteArray[161].callGetProperty(field))), callSiteArray[162].callStatic(GeneralUtils.class, callSiteArray[163].callStatic(GeneralUtils.class, callSiteArray[164].callStatic(GeneralUtils.class, "__result"), callSiteArray[165].callStatic(GeneralUtils.class, "addAll"), callSiteArray[166].callStatic(GeneralUtils.class, callSiteArray[167].callStatic(GeneralUtils.class, callSiteArray[168].callGetProperty(field)))))), callSiteArray[169].callStatic(GeneralUtils.class, callSiteArray[170].callStatic(GeneralUtils.class, methodReturnType, callSiteArray[171].callStatic(GeneralUtils.class, "__result")))}));
        callSiteArray[172].call((Object)declaringClass, callSiteArray[173].callConstructor((Object)MethodNode.class, ArrayUtil.createArray(methodName, methodModifiers, methodReturnType, methodParameter, ScriptBytecodeAdapter.createPojoWrapper((ClassNode[])ScriptBytecodeAdapter.asType(ScriptBytecodeAdapter.createList(new Object[0]), ClassNode[].class), ClassNode[].class), block)));
    }

    public void addFireMethods(SourceUnit source, AnnotationNode node, ClassNode declaringClass, FieldNode field, GenericsType[] types, boolean synchronize, MethodNode method) {
        CallSite[] callSiteArray = ListenerListASTTransformation.$getCallSiteArray();
        Object methodReturnType = callSiteArray[174].call(ClassHelper.class, callSiteArray[175].callGetProperty(Void.class));
        GStringImpl methodName = new GStringImpl(new Object[]{callSiteArray[176].call(callSiteArray[177].callGetProperty(method))}, new String[]{"fire", ""});
        Integer methodModifiers = null;
        if (!BytecodeInterface8.isOrigInt() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            int n = synchronize ? DefaultTypeTransformation.intUnbox(callSiteArray[178].call(callSiteArray[179].callGroovyObjectGetProperty(this), callSiteArray[180].callGroovyObjectGetProperty(this))) : DefaultTypeTransformation.intUnbox(callSiteArray[181].callGroovyObjectGetProperty(this));
            methodModifiers = n;
        } else {
            int n = synchronize ? DefaultTypeTransformation.intUnbox(callSiteArray[182].callGroovyObjectGetProperty(this)) | DefaultTypeTransformation.intUnbox(callSiteArray[183].callGroovyObjectGetProperty(this)) : DefaultTypeTransformation.intUnbox(callSiteArray[184].callGroovyObjectGetProperty(this));
            methodModifiers = n;
        }
        if (DefaultTypeTransformation.booleanUnbox(callSiteArray[185].call(declaringClass, methodName, callSiteArray[186].callGetProperty(method)))) {
            callSiteArray[187].callStatic(ListenerListASTTransformation.class, node, source, new GStringImpl(new Object[]{callSiteArray[188].callGetProperty(MY_CLASS), callSiteArray[189].callGetProperty(declaringClass), methodName}, new String[]{"Conflict using @", ". Class ", " already has method ", ""}));
            return;
        }
        Object methodArgs = callSiteArray[190].callStatic(GeneralUtils.class, callSiteArray[191].callGetProperty(method));
        BlockStatement block = (BlockStatement)ScriptBytecodeAdapter.castToType(callSiteArray[192].callConstructor(BlockStatement.class), BlockStatement.class);
        Object listenerListType = callSiteArray[193].callGetProperty(callSiteArray[194].call(ClassHelper.class, ArrayList.class));
        GenericsType[] genericsTypeArray = types;
        ScriptBytecodeAdapter.setProperty(genericsTypeArray, null, listenerListType, "genericsTypes");
        callSiteArray[195].call((Object)block, ScriptBytecodeAdapter.createList(new Object[]{callSiteArray[196].callStatic(GeneralUtils.class, callSiteArray[197].callStatic(GeneralUtils.class, callSiteArray[198].callStatic(GeneralUtils.class, callSiteArray[199].callGetProperty(field))), callSiteArray[200].callConstructor(BlockStatement.class, ScriptBytecodeAdapter.createList(new Object[]{callSiteArray[201].callStatic(GeneralUtils.class, callSiteArray[202].callStatic(GeneralUtils.class, "__list", listenerListType), callSiteArray[203].callStatic(GeneralUtils.class, listenerListType, callSiteArray[204].callStatic(GeneralUtils.class, callSiteArray[205].callStatic(GeneralUtils.class, callSiteArray[206].callGetProperty(field))))), callSiteArray[207].callConstructor(ForStatement.class, callSiteArray[208].callStatic(GeneralUtils.class, callSiteArray[209].call(ClassHelper.class), "listener"), callSiteArray[210].callStatic(GeneralUtils.class, "__list"), callSiteArray[211].callConstructor(BlockStatement.class, ScriptBytecodeAdapter.createList(new Object[]{callSiteArray[212].callStatic(GeneralUtils.class, callSiteArray[213].callStatic(GeneralUtils.class, callSiteArray[214].callStatic(GeneralUtils.class, "listener"), callSiteArray[215].callGetProperty(method), methodArgs))}), callSiteArray[216].callConstructor(VariableScope.class)))}), callSiteArray[217].callConstructor(VariableScope.class)))}));
        public final class _addFireMethods_closure3
        extends Closure
        implements GeneratedClosure {
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _addFireMethods_closure3(Object _outerInstance, Object _thisObject) {
                CallSite[] callSiteArray = _addFireMethods_closure3.$getCallSiteArray();
                super(_outerInstance, _thisObject);
            }

            public Object doCall(Object it) {
                CallSite[] callSiteArray = _addFireMethods_closure3.$getCallSiteArray();
                Object paramType = callSiteArray[0].call(ClassHelper.class, callSiteArray[1].callGetProperty(it));
                Object cn = callSiteArray[2].callGetProperty(paramType);
                Object object = paramType;
                ScriptBytecodeAdapter.setProperty(object, null, cn, "redirect");
                return callSiteArray[3].callStatic(GeneralUtils.class, cn, callSiteArray[4].callGetProperty(it));
            }

            @Generated
            public Object doCall() {
                CallSite[] callSiteArray = _addFireMethods_closure3.$getCallSiteArray();
                return this.doCall(null);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (this.getClass() != _addFireMethods_closure3.class) {
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
                stringArray[0] = "getWrapper";
                stringArray[1] = "type";
                stringArray[2] = "plainNodeReference";
                stringArray[3] = "param";
                stringArray[4] = "name";
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[5];
                _addFireMethods_closure3.$createCallSiteArray_1(stringArray);
                return new CallSiteArray(_addFireMethods_closure3.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _addFireMethods_closure3.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        Object params = callSiteArray[218].call(callSiteArray[219].callGetProperty(method), new _addFireMethods_closure3(this, this));
        callSiteArray[220].call((Object)declaringClass, ArrayUtil.createArray(methodName, methodModifiers, methodReturnType, ScriptBytecodeAdapter.createPojoWrapper((Parameter[])ScriptBytecodeAdapter.asType(params, Parameter[].class), Parameter[].class), ScriptBytecodeAdapter.createPojoWrapper((ClassNode[])ScriptBytecodeAdapter.asType(ScriptBytecodeAdapter.createList(new Object[0]), ClassNode[].class), ClassNode[].class), block));
    }

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != ListenerListASTTransformation.class) {
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

    static {
        Class<ListenerList> clazz;
        MY_CLASS = clazz = ListenerList.class;
        Object object = ListenerListASTTransformation.$getCallSiteArray()[221].call(ClassHelper.class, Collection.class);
        COLLECTION_TYPE = (ClassNode)ScriptBytecodeAdapter.castToType(object, ClassNode.class);
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
        stringArray[0] = "getAt";
        stringArray[1] = "getAt";
        stringArray[2] = "<$constructor$>";
        stringArray[3] = "class";
        stringArray[4] = "node";
        stringArray[5] = "class";
        stringArray[6] = "parent";
        stringArray[7] = "<$constructor$>";
        stringArray[8] = "class";
        stringArray[9] = "node";
        stringArray[10] = "class";
        stringArray[11] = "parent";
        stringArray[12] = "getAt";
        stringArray[13] = "getAt";
        stringArray[14] = "declaringClass";
        stringArray[15] = "getAt";
        stringArray[16] = "declaringClass";
        stringArray[17] = "type";
        stringArray[18] = "isDerivedFrom";
        stringArray[19] = "implementsInterface";
        stringArray[20] = "isDerivedFrom";
        stringArray[21] = "implementsInterface";
        stringArray[22] = "addError";
        stringArray[23] = "plus";
        stringArray[24] = "plus";
        stringArray[25] = "name";
        stringArray[26] = "genericsTypes";
        stringArray[27] = "type";
        stringArray[28] = "addError";
        stringArray[29] = "plus";
        stringArray[30] = "plus";
        stringArray[31] = "name";
        stringArray[32] = "wildcard";
        stringArray[33] = "getAt";
        stringArray[34] = "addError";
        stringArray[35] = "plus";
        stringArray[36] = "plus";
        stringArray[37] = "name";
        stringArray[38] = "type";
        stringArray[39] = "getAt";
        stringArray[40] = "initialValueExpression";
        stringArray[41] = "<$constructor$>";
        stringArray[42] = "value";
        stringArray[43] = "getMember";
        stringArray[44] = "nameWithoutPackage";
        stringArray[45] = "findAll";
        stringArray[46] = "methods";
        stringArray[47] = "value";
        stringArray[48] = "getMember";
        stringArray[49] = "addAddListener";
        stringArray[50] = "addRemoveListener";
        stringArray[51] = "addGetListeners";
        stringArray[52] = "each";
        stringArray[53] = "addError";
        stringArray[54] = "errorCollector";
        stringArray[55] = "<$constructor$>";
        stringArray[56] = "<$constructor$>";
        stringArray[57] = "lineNumber";
        stringArray[58] = "columnNumber";
        stringArray[59] = "or";
        stringArray[60] = "ACC_PUBLIC";
        stringArray[61] = "ACC_SYNCHRONIZED";
        stringArray[62] = "ACC_PUBLIC";
        stringArray[63] = "ACC_PUBLIC";
        stringArray[64] = "ACC_SYNCHRONIZED";
        stringArray[65] = "ACC_PUBLIC";
        stringArray[66] = "make";
        stringArray[67] = "TYPE";
        stringArray[68] = "capitalize";
        stringArray[69] = "makeWithoutCaching";
        stringArray[70] = "name";
        stringArray[71] = "param";
        stringArray[72] = "hasMethod";
        stringArray[73] = "addError";
        stringArray[74] = "name";
        stringArray[75] = "name";
        stringArray[76] = "<$constructor$>";
        stringArray[77] = "addStatements";
        stringArray[78] = "ifS";
        stringArray[79] = "equalsNullX";
        stringArray[80] = "varX";
        stringArray[81] = "RETURN_NULL_OR_VOID";
        stringArray[82] = "ifS";
        stringArray[83] = "equalsNullX";
        stringArray[84] = "varX";
        stringArray[85] = "name";
        stringArray[86] = "assignS";
        stringArray[87] = "varX";
        stringArray[88] = "name";
        stringArray[89] = "<$constructor$>";
        stringArray[90] = "stmt";
        stringArray[91] = "callX";
        stringArray[92] = "varX";
        stringArray[93] = "name";
        stringArray[94] = "constX";
        stringArray[95] = "args";
        stringArray[96] = "varX";
        stringArray[97] = "addMethod";
        stringArray[98] = "<$constructor$>";
        stringArray[99] = "or";
        stringArray[100] = "ACC_PUBLIC";
        stringArray[101] = "ACC_SYNCHRONIZED";
        stringArray[102] = "ACC_PUBLIC";
        stringArray[103] = "ACC_PUBLIC";
        stringArray[104] = "ACC_SYNCHRONIZED";
        stringArray[105] = "ACC_PUBLIC";
        stringArray[106] = "make";
        stringArray[107] = "TYPE";
        stringArray[108] = "capitalize";
        stringArray[109] = "makeWithoutCaching";
        stringArray[110] = "name";
        stringArray[111] = "param";
        stringArray[112] = "hasMethod";
        stringArray[113] = "addError";
        stringArray[114] = "name";
        stringArray[115] = "name";
        stringArray[116] = "<$constructor$>";
        stringArray[117] = "addStatements";
        stringArray[118] = "ifS";
        stringArray[119] = "equalsNullX";
        stringArray[120] = "varX";
        stringArray[121] = "RETURN_NULL_OR_VOID";
        stringArray[122] = "ifS";
        stringArray[123] = "equalsNullX";
        stringArray[124] = "varX";
        stringArray[125] = "name";
        stringArray[126] = "assignS";
        stringArray[127] = "varX";
        stringArray[128] = "name";
        stringArray[129] = "<$constructor$>";
        stringArray[130] = "stmt";
        stringArray[131] = "callX";
        stringArray[132] = "varX";
        stringArray[133] = "name";
        stringArray[134] = "constX";
        stringArray[135] = "args";
        stringArray[136] = "varX";
        stringArray[137] = "addMethod";
        stringArray[138] = "<$constructor$>";
        stringArray[139] = "or";
        stringArray[140] = "ACC_PUBLIC";
        stringArray[141] = "ACC_SYNCHRONIZED";
        stringArray[142] = "ACC_PUBLIC";
        stringArray[143] = "ACC_PUBLIC";
        stringArray[144] = "ACC_SYNCHRONIZED";
        stringArray[145] = "ACC_PUBLIC";
        stringArray[146] = "makeArray";
        stringArray[147] = "capitalize";
        stringArray[148] = "hasMethod";
        stringArray[149] = "addError";
        stringArray[150] = "name";
        stringArray[151] = "name";
        stringArray[152] = "<$constructor$>";
        stringArray[153] = "addStatements";
        stringArray[154] = "declS";
        stringArray[155] = "localVarX";
        stringArray[156] = "dynamicType";
        stringArray[157] = "<$constructor$>";
        stringArray[158] = "ifS";
        stringArray[159] = "notNullX";
        stringArray[160] = "varX";
        stringArray[161] = "name";
        stringArray[162] = "stmt";
        stringArray[163] = "callX";
        stringArray[164] = "varX";
        stringArray[165] = "constX";
        stringArray[166] = "args";
        stringArray[167] = "varX";
        stringArray[168] = "name";
        stringArray[169] = "returnS";
        stringArray[170] = "castX";
        stringArray[171] = "varX";
        stringArray[172] = "addMethod";
        stringArray[173] = "<$constructor$>";
        stringArray[174] = "make";
        stringArray[175] = "TYPE";
        stringArray[176] = "capitalize";
        stringArray[177] = "name";
        stringArray[178] = "or";
        stringArray[179] = "ACC_PUBLIC";
        stringArray[180] = "ACC_SYNCHRONIZED";
        stringArray[181] = "ACC_PUBLIC";
        stringArray[182] = "ACC_PUBLIC";
        stringArray[183] = "ACC_SYNCHRONIZED";
        stringArray[184] = "ACC_PUBLIC";
        stringArray[185] = "hasMethod";
        stringArray[186] = "parameters";
        stringArray[187] = "addError";
        stringArray[188] = "name";
        stringArray[189] = "name";
        stringArray[190] = "args";
        stringArray[191] = "parameters";
        stringArray[192] = "<$constructor$>";
        stringArray[193] = "plainNodeReference";
        stringArray[194] = "make";
        stringArray[195] = "addStatements";
        stringArray[196] = "ifS";
        stringArray[197] = "notNullX";
        stringArray[198] = "varX";
        stringArray[199] = "name";
        stringArray[200] = "<$constructor$>";
        stringArray[201] = "declS";
        stringArray[202] = "localVarX";
        stringArray[203] = "ctorX";
        stringArray[204] = "args";
        stringArray[205] = "varX";
        stringArray[206] = "name";
        stringArray[207] = "<$constructor$>";
        stringArray[208] = "param";
        stringArray[209] = "dynamicType";
        stringArray[210] = "varX";
        stringArray[211] = "<$constructor$>";
        stringArray[212] = "stmt";
        stringArray[213] = "callX";
        stringArray[214] = "varX";
        stringArray[215] = "name";
        stringArray[216] = "<$constructor$>";
        stringArray[217] = "<$constructor$>";
        stringArray[218] = "collect";
        stringArray[219] = "parameters";
        stringArray[220] = "addMethod";
        stringArray[221] = "make";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[222];
        ListenerListASTTransformation.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(ListenerListASTTransformation.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = ListenerListASTTransformation.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }
}

