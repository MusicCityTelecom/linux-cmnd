/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.GroovyClassLoader
 *  groovy.lang.GroovyObject
 *  groovy.lang.MetaClass
 *  groovy.transform.Generated
 *  groovy.transform.Internal
 *  org.codehaus.groovy.ast.ClassCodeVisitorSupport
 *  org.codehaus.groovy.ast.ClassNode
 *  org.codehaus.groovy.ast.DynamicVariable
 *  org.codehaus.groovy.ast.GroovyClassVisitor
 *  org.codehaus.groovy.ast.expr.VariableExpression
 *  org.codehaus.groovy.classgen.GeneratorContext
 *  org.codehaus.groovy.control.CompilationFailedException
 *  org.codehaus.groovy.control.CompilationUnit
 *  org.codehaus.groovy.control.CompilationUnit$IPrimaryClassNodeOperation
 *  org.codehaus.groovy.control.CompilerConfiguration
 *  org.codehaus.groovy.control.Phases
 *  org.codehaus.groovy.control.SourceUnit
 *  org.codehaus.groovy.reflection.ClassInfo
 *  org.codehaus.groovy.runtime.BytecodeInterface8
 *  org.codehaus.groovy.runtime.GStringImpl
 *  org.codehaus.groovy.runtime.ScriptBytecodeAdapter
 *  org.codehaus.groovy.runtime.callsite.CallSite
 *  org.codehaus.groovy.runtime.callsite.CallSiteArray
 *  org.codehaus.groovy.runtime.powerassert.AssertionRenderer
 *  org.codehaus.groovy.runtime.powerassert.ValueRecorder
 *  org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation
 *  org.codehaus.groovy.runtime.typehandling.ShortTypeHandling
 */
package org.apache.groovy.groovysh.util;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import groovy.transform.Generated;
import groovy.transform.Internal;
import java.beans.Transient;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import java.security.CodeSource;
import java.util.HashSet;
import java.util.Set;
import org.codehaus.groovy.ast.ClassCodeVisitorSupport;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.DynamicVariable;
import org.codehaus.groovy.ast.GroovyClassVisitor;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.classgen.GeneratorContext;
import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.control.CompilationUnit;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.Phases;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.BytecodeInterface8;
import org.codehaus.groovy.runtime.GStringImpl;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.powerassert.AssertionRenderer;
import org.codehaus.groovy.runtime.powerassert.ValueRecorder;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;
import org.codehaus.groovy.runtime.typehandling.ShortTypeHandling;

public class ScriptVariableAnalyzer
implements GroovyObject {
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private transient /* synthetic */ MetaClass metaClass;
    private static /* synthetic */ SoftReference $callSiteArray;

    @Generated
    public ScriptVariableAnalyzer() {
        MetaClass metaClass;
        CallSite[] callSiteArray = ScriptVariableAnalyzer.$getCallSiteArray();
        this.metaClass = metaClass = this.$getStaticMetaClass();
    }

    public static Set<String> getBoundVars(String scriptText, ClassLoader parent) {
        CallSite[] callSiteArray = ScriptVariableAnalyzer.$getCallSiteArray();
        ValueRecorder valueRecorder = new ValueRecorder();
        try {
            String string = scriptText;
            valueRecorder.record((Object)string, 8);
            boolean bl = ScriptBytecodeAdapter.compareNotEqual((Object)string, null);
            valueRecorder.record((Object)bl, 19);
            if (bl) {
                valueRecorder.clear();
            } else {
                ScriptBytecodeAdapter.assertFailed((Object)AssertionRenderer.render((String)"assert scriptText != null", (ValueRecorder)valueRecorder), null);
            }
        }
        catch (Throwable throwable) {
            valueRecorder.clear();
            throw throwable;
        }
        GroovyClassVisitor visitor = (GroovyClassVisitor)ScriptBytecodeAdapter.castToType((Object)callSiteArray[0].callConstructor(VariableVisitor.class), GroovyClassVisitor.class);
        VisitorClassLoader myCL = (VisitorClassLoader)((Object)ScriptBytecodeAdapter.castToType((Object)callSiteArray[1].callConstructor(VisitorClassLoader.class, (Object)visitor, (Object)parent), VisitorClassLoader.class));
        callSiteArray[2].call((Object)myCL, (Object)scriptText);
        return (Set)ScriptBytecodeAdapter.castToType((Object)callSiteArray[3].callGetProperty((Object)visitor), Set.class);
    }

    public /* synthetic */ Object this$dist$invoke$1(String name, Object args) {
        CallSite[] callSiteArray = ScriptVariableAnalyzer.$getCallSiteArray();
        if (!(args instanceof Object[])) {
            return ScriptBytecodeAdapter.invokeMethodOnCurrentN(ScriptVariableAnalyzer.class, (GroovyObject)this, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])new Object[]{args});
        }
        if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[4].callGetProperty((Object)((Object[])ScriptBytecodeAdapter.castToType((Object)args, Object[].class))), (Object)1)) {
                return ScriptBytecodeAdapter.invokeMethodOnCurrentN(ScriptVariableAnalyzer.class, (GroovyObject)this, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])new Object[]{callSiteArray[5].call((Object)((Object[])ScriptBytecodeAdapter.castToType((Object)args, Object[].class)), (Object)0)});
            }
        } else if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[6].callGetProperty((Object)((Object[])ScriptBytecodeAdapter.castToType((Object)args, Object[].class))), (Object)1)) {
            return ScriptBytecodeAdapter.invokeMethodOnCurrentN(ScriptVariableAnalyzer.class, (GroovyObject)this, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])new Object[]{BytecodeInterface8.objectArrayGet((Object[])((Object[])ScriptBytecodeAdapter.castToType((Object)args, Object[].class)), (int)0)});
        }
        return ScriptBytecodeAdapter.invokeMethodOnCurrentN(ScriptVariableAnalyzer.class, (GroovyObject)this, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])ScriptBytecodeAdapter.despreadList((Object[])new Object[0], (Object[])new Object[]{args}, (int[])new int[]{0}));
    }

    public /* synthetic */ void this$dist$set$1(String name, Object value) {
        CallSite[] callSiteArray = ScriptVariableAnalyzer.$getCallSiteArray();
        Object object = value;
        ScriptBytecodeAdapter.setGroovyObjectProperty((Object)object, ScriptVariableAnalyzer.class, (GroovyObject)this, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})));
    }

    public /* synthetic */ Object this$dist$get$1(String name) {
        CallSite[] callSiteArray = ScriptVariableAnalyzer.$getCallSiteArray();
        return ScriptBytecodeAdapter.getGroovyObjectProperty(ScriptVariableAnalyzer.class, (GroovyObject)this, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})));
    }

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != ScriptVariableAnalyzer.class) {
            return ScriptBytecodeAdapter.initMetaClass((Object)this);
        }
        ClassInfo classInfo = $staticClassInfo;
        if (classInfo == null) {
            $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
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

    public static /* synthetic */ MethodHandles.Lookup $getLookup() {
        return MethodHandles.lookup();
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
        stringArray[0] = "<$constructor$>";
        stringArray[1] = "<$constructor$>";
        stringArray[2] = "parseClass";
        stringArray[3] = "bound";
        stringArray[4] = "length";
        stringArray[5] = "getAt";
        stringArray[6] = "length";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[7];
        ScriptVariableAnalyzer.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(ScriptVariableAnalyzer.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = ScriptVariableAnalyzer.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }

    public static class VariableVisitor
    extends ClassCodeVisitorSupport
    implements GroovyClassVisitor,
    GroovyObject {
        private Set<String> bound;
        private Set<String> unbound;
        private static /* synthetic */ ClassInfo $staticClassInfo;
        public static transient /* synthetic */ boolean __$stMC;
        private transient /* synthetic */ MetaClass metaClass;
        private static /* synthetic */ SoftReference $callSiteArray;

        @Generated
        public VariableVisitor() {
            MetaClass metaClass;
            CallSite[] callSiteArray = VariableVisitor.$getCallSiteArray();
            Object object = callSiteArray[0].callConstructor(HashSet.class);
            this.bound = (Set)ScriptBytecodeAdapter.castToType((Object)object, Set.class);
            Object object2 = callSiteArray[1].callConstructor(HashSet.class);
            this.unbound = (Set)ScriptBytecodeAdapter.castToType((Object)object2, Set.class);
            this.metaClass = metaClass = this.$getStaticMetaClass();
        }

        public void visitVariableExpression(VariableExpression expression) {
            CallSite[] callSiteArray = VariableVisitor.$getCallSiteArray();
            if (!ScriptBytecodeAdapter.isCase((Object)callSiteArray[2].callGetProperty((Object)expression), (Object)ScriptBytecodeAdapter.createList((Object[])new Object[]{"args", "context", "this", "super"}))) {
                if (callSiteArray[3].callGetProperty((Object)expression) instanceof DynamicVariable) {
                    callSiteArray[4].call(this.unbound, callSiteArray[5].callGetProperty((Object)expression));
                } else {
                    callSiteArray[6].call(this.bound, callSiteArray[7].callGetProperty((Object)expression));
                }
            }
            ScriptBytecodeAdapter.invokeMethodOnSuperN(VariableVisitor.class, (GroovyObject)this, (String)"visitVariableExpression", (Object[])new Object[]{expression});
        }

        protected SourceUnit getSourceUnit() {
            CallSite[] callSiteArray = VariableVisitor.$getCallSiteArray();
            return (SourceUnit)ScriptBytecodeAdapter.castToType(null, SourceUnit.class);
        }

        public /* synthetic */ Object methodMissing(String name, Object args) {
            CallSite[] callSiteArray = VariableVisitor.$getCallSiteArray();
            if (!(args instanceof Object[])) {
                return ScriptBytecodeAdapter.invokeMethodN(VariableVisitor.class, ScriptVariableAnalyzer.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])new Object[]{args});
            }
            if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[8].callGetProperty((Object)((Object[])ScriptBytecodeAdapter.castToType((Object)args, Object[].class))), (Object)1)) {
                    return ScriptBytecodeAdapter.invokeMethodN(VariableVisitor.class, ScriptVariableAnalyzer.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])new Object[]{callSiteArray[9].call((Object)((Object[])ScriptBytecodeAdapter.castToType((Object)args, Object[].class)), (Object)0)});
                }
            } else if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[10].callGetProperty((Object)((Object[])ScriptBytecodeAdapter.castToType((Object)args, Object[].class))), (Object)1)) {
                return ScriptBytecodeAdapter.invokeMethodN(VariableVisitor.class, ScriptVariableAnalyzer.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])new Object[]{BytecodeInterface8.objectArrayGet((Object[])((Object[])ScriptBytecodeAdapter.castToType((Object)args, Object[].class)), (int)0)});
            }
            return ScriptBytecodeAdapter.invokeMethodN(VariableVisitor.class, ScriptVariableAnalyzer.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])ScriptBytecodeAdapter.despreadList((Object[])new Object[0], (Object[])new Object[]{args}, (int[])new int[]{0}));
        }

        public static /* synthetic */ Object $static_methodMissing(String name, Object args) {
            CallSite[] callSiteArray = VariableVisitor.$getCallSiteArray();
            if (!(args instanceof Object[])) {
                return ScriptBytecodeAdapter.invokeMethodN(VariableVisitor.class, ScriptVariableAnalyzer.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])new Object[]{args});
            }
            if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[11].callGetProperty((Object)((Object[])ScriptBytecodeAdapter.castToType((Object)args, Object[].class))), (Object)1)) {
                    return ScriptBytecodeAdapter.invokeMethodN(VariableVisitor.class, ScriptVariableAnalyzer.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])new Object[]{callSiteArray[12].call((Object)((Object[])ScriptBytecodeAdapter.castToType((Object)args, Object[].class)), (Object)0)});
                }
            } else if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[13].callGetProperty((Object)((Object[])ScriptBytecodeAdapter.castToType((Object)args, Object[].class))), (Object)1)) {
                return ScriptBytecodeAdapter.invokeMethodN(VariableVisitor.class, ScriptVariableAnalyzer.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])new Object[]{BytecodeInterface8.objectArrayGet((Object[])((Object[])ScriptBytecodeAdapter.castToType((Object)args, Object[].class)), (int)0)});
            }
            return ScriptBytecodeAdapter.invokeMethodN(VariableVisitor.class, ScriptVariableAnalyzer.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])ScriptBytecodeAdapter.despreadList((Object[])new Object[0], (Object[])new Object[]{args}, (int[])new int[]{0}));
        }

        public /* synthetic */ void propertyMissing(String name, Object value) {
            CallSite[] callSiteArray = VariableVisitor.$getCallSiteArray();
            Object object = value;
            ScriptBytecodeAdapter.setProperty((Object)object, null, ScriptVariableAnalyzer.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

        public static /* synthetic */ void $static_propertyMissing(String name, Object value) {
            CallSite[] callSiteArray = VariableVisitor.$getCallSiteArray();
            Object object = value;
            ScriptBytecodeAdapter.setProperty((Object)object, null, ScriptVariableAnalyzer.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

        public /* synthetic */ Object propertyMissing(String name) {
            CallSite[] callSiteArray = VariableVisitor.$getCallSiteArray();
            return ScriptBytecodeAdapter.getProperty(VariableVisitor.class, ScriptVariableAnalyzer.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

        public static /* synthetic */ Object $static_propertyMissing(String name) {
            CallSite[] callSiteArray = VariableVisitor.$getCallSiteArray();
            return ScriptBytecodeAdapter.getProperty(VariableVisitor.class, ScriptVariableAnalyzer.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

        protected /* synthetic */ MetaClass $getStaticMetaClass() {
            if (((Object)((Object)this)).getClass() != VariableVisitor.class) {
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

        public static /* synthetic */ MethodHandles.Lookup $getLookup() {
            return MethodHandles.lookup();
        }

        @Generated
        public Set<String> getBound() {
            return this.bound;
        }

        @Generated
        public void setBound(Set<String> set) {
            this.bound = set;
        }

        @Generated
        public Set<String> getUnbound() {
            return this.unbound;
        }

        @Generated
        public void setUnbound(Set<String> set) {
            this.unbound = set;
        }

        public /* synthetic */ void super$2$visitVariableExpression(VariableExpression variableExpression) {
            super.visitVariableExpression(variableExpression);
        }

        private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
            stringArray[0] = "<$constructor$>";
            stringArray[1] = "<$constructor$>";
            stringArray[2] = "variable";
            stringArray[3] = "accessedVariable";
            stringArray[4] = "leftShift";
            stringArray[5] = "variable";
            stringArray[6] = "leftShift";
            stringArray[7] = "variable";
            stringArray[8] = "length";
            stringArray[9] = "getAt";
            stringArray[10] = "length";
            stringArray[11] = "length";
            stringArray[12] = "getAt";
            stringArray[13] = "length";
        }

        private static /* synthetic */ CallSiteArray $createCallSiteArray() {
            String[] stringArray = new String[14];
            VariableVisitor.$createCallSiteArray_1(stringArray);
            return new CallSiteArray(VariableVisitor.class, stringArray);
        }

        private static /* synthetic */ CallSite[] $getCallSiteArray() {
            CallSiteArray callSiteArray;
            if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                callSiteArray = VariableVisitor.$createCallSiteArray();
                $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
            }
            return callSiteArray.array;
        }
    }

    public static class VisitorClassLoader
    extends GroovyClassLoader
    implements GroovyObject {
        private final GroovyClassVisitor visitor;
        private static /* synthetic */ ClassInfo $staticClassInfo;
        public static transient /* synthetic */ boolean __$stMC;
        private transient /* synthetic */ MetaClass metaClass;
        private static /* synthetic */ SoftReference $callSiteArray;

        public VisitorClassLoader(GroovyClassVisitor visitor, ClassLoader parent) {
            GroovyClassVisitor groovyClassVisitor;
            MetaClass metaClass;
            CallSite[] callSiteArray = VisitorClassLoader.$getCallSiteArray();
            Object[] objectArray = new Object[]{ScriptBytecodeAdapter.compareEqual((Object)parent, null) ? callSiteArray[0].call(callSiteArray[1].call(Thread.class)) : parent};
            VisitorClassLoader visitorClassLoader = this;
            switch (ScriptBytecodeAdapter.selectConstructorAndTransformArguments((Object[])objectArray, (int)-1, GroovyClassLoader.class)) {
                case -2044833963: {
                    Object[] objectArray2 = objectArray;
                    super((ClassLoader)ScriptBytecodeAdapter.castToType((Object)objectArray[0], ClassLoader.class));
                    break;
                }
                case -1374445560: {
                    Object[] objectArray2 = objectArray;
                    super((ClassLoader)ScriptBytecodeAdapter.castToType((Object)objectArray[0], ClassLoader.class), (CompilerConfiguration)ScriptBytecodeAdapter.castToType((Object)objectArray[1], CompilerConfiguration.class));
                    break;
                }
                case -991719697: {
                    Object[] objectArray2 = objectArray;
                    super((GroovyClassLoader)ScriptBytecodeAdapter.castToType((Object)objectArray[0], GroovyClassLoader.class));
                    break;
                }
                case 39797: {
                    Object[] objectArray2 = objectArray;
                    super();
                    break;
                }
                case 341906380: {
                    Object[] objectArray2 = objectArray;
                    super((ClassLoader)ScriptBytecodeAdapter.castToType((Object)objectArray[0], ClassLoader.class), (CompilerConfiguration)ScriptBytecodeAdapter.castToType((Object)objectArray[1], CompilerConfiguration.class), DefaultTypeTransformation.booleanUnbox((Object)objectArray[2]));
                    break;
                }
                default: {
                    throw new IllegalArgumentException("This class has been compiled with a super class which is binary incompatible with the current super class found on classpath. You should recompile this class with the new version.");
                }
            }
            this.metaClass = metaClass = this.$getStaticMetaClass();
            this.visitor = groovyClassVisitor = visitor;
        }

        protected CompilationUnit createCompilationUnit(CompilerConfiguration config, CodeSource source) {
            CallSite[] callSiteArray = VisitorClassLoader.$getCallSiteArray();
            CompilationUnit cu = (CompilationUnit)ScriptBytecodeAdapter.castToType((Object)ScriptBytecodeAdapter.invokeMethodOnSuperN(VisitorClassLoader.class, (GroovyObject)this, (String)"createCompilationUnit", (Object[])new Object[]{config, source}), CompilationUnit.class);
            callSiteArray[2].call((Object)cu, callSiteArray[3].callConstructor(VisitorSourceOperation.class, (Object)this.visitor), callSiteArray[4].callGetProperty(Phases.class));
            return cu;
        }

        public /* synthetic */ Object methodMissing(String name, Object args) {
            CallSite[] callSiteArray = VisitorClassLoader.$getCallSiteArray();
            if (!(args instanceof Object[])) {
                return ScriptBytecodeAdapter.invokeMethodN(VisitorClassLoader.class, ScriptVariableAnalyzer.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])new Object[]{args});
            }
            if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[5].callGetProperty((Object)((Object[])ScriptBytecodeAdapter.castToType((Object)args, Object[].class))), (Object)1)) {
                    return ScriptBytecodeAdapter.invokeMethodN(VisitorClassLoader.class, ScriptVariableAnalyzer.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])new Object[]{callSiteArray[6].call((Object)((Object[])ScriptBytecodeAdapter.castToType((Object)args, Object[].class)), (Object)0)});
                }
            } else if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[7].callGetProperty((Object)((Object[])ScriptBytecodeAdapter.castToType((Object)args, Object[].class))), (Object)1)) {
                return ScriptBytecodeAdapter.invokeMethodN(VisitorClassLoader.class, ScriptVariableAnalyzer.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])new Object[]{BytecodeInterface8.objectArrayGet((Object[])((Object[])ScriptBytecodeAdapter.castToType((Object)args, Object[].class)), (int)0)});
            }
            return ScriptBytecodeAdapter.invokeMethodN(VisitorClassLoader.class, ScriptVariableAnalyzer.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])ScriptBytecodeAdapter.despreadList((Object[])new Object[0], (Object[])new Object[]{args}, (int[])new int[]{0}));
        }

        public static /* synthetic */ Object $static_methodMissing(String name, Object args) {
            CallSite[] callSiteArray = VisitorClassLoader.$getCallSiteArray();
            if (!(args instanceof Object[])) {
                return ScriptBytecodeAdapter.invokeMethodN(VisitorClassLoader.class, ScriptVariableAnalyzer.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])new Object[]{args});
            }
            if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[8].callGetProperty((Object)((Object[])ScriptBytecodeAdapter.castToType((Object)args, Object[].class))), (Object)1)) {
                    return ScriptBytecodeAdapter.invokeMethodN(VisitorClassLoader.class, ScriptVariableAnalyzer.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])new Object[]{callSiteArray[9].call((Object)((Object[])ScriptBytecodeAdapter.castToType((Object)args, Object[].class)), (Object)0)});
                }
            } else if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[10].callGetProperty((Object)((Object[])ScriptBytecodeAdapter.castToType((Object)args, Object[].class))), (Object)1)) {
                return ScriptBytecodeAdapter.invokeMethodN(VisitorClassLoader.class, ScriptVariableAnalyzer.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])new Object[]{BytecodeInterface8.objectArrayGet((Object[])((Object[])ScriptBytecodeAdapter.castToType((Object)args, Object[].class)), (int)0)});
            }
            return ScriptBytecodeAdapter.invokeMethodN(VisitorClassLoader.class, ScriptVariableAnalyzer.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])ScriptBytecodeAdapter.despreadList((Object[])new Object[0], (Object[])new Object[]{args}, (int[])new int[]{0}));
        }

        public /* synthetic */ void propertyMissing(String name, Object value) {
            CallSite[] callSiteArray = VisitorClassLoader.$getCallSiteArray();
            Object object = value;
            ScriptBytecodeAdapter.setProperty((Object)object, null, ScriptVariableAnalyzer.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

        public static /* synthetic */ void $static_propertyMissing(String name, Object value) {
            CallSite[] callSiteArray = VisitorClassLoader.$getCallSiteArray();
            Object object = value;
            ScriptBytecodeAdapter.setProperty((Object)object, null, ScriptVariableAnalyzer.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

        public /* synthetic */ Object propertyMissing(String name) {
            CallSite[] callSiteArray = VisitorClassLoader.$getCallSiteArray();
            return ScriptBytecodeAdapter.getProperty(VisitorClassLoader.class, ScriptVariableAnalyzer.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

        public static /* synthetic */ Object $static_propertyMissing(String name) {
            CallSite[] callSiteArray = VisitorClassLoader.$getCallSiteArray();
            return ScriptBytecodeAdapter.getProperty(VisitorClassLoader.class, ScriptVariableAnalyzer.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

        protected /* synthetic */ MetaClass $getStaticMetaClass() {
            if (((Object)((Object)this)).getClass() != VisitorClassLoader.class) {
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

        public static /* synthetic */ MethodHandles.Lookup $getLookup() {
            return MethodHandles.lookup();
        }

        @Generated
        public final GroovyClassVisitor getVisitor() {
            return this.visitor;
        }

        public /* synthetic */ CompilationUnit super$5$createCompilationUnit(CompilerConfiguration compilerConfiguration, CodeSource codeSource) {
            return super.createCompilationUnit(compilerConfiguration, codeSource);
        }

        private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
            stringArray[0] = "getContextClassLoader";
            stringArray[1] = "currentThread";
            stringArray[2] = "addPhaseOperation";
            stringArray[3] = "<$constructor$>";
            stringArray[4] = "CLASS_GENERATION";
            stringArray[5] = "length";
            stringArray[6] = "getAt";
            stringArray[7] = "length";
            stringArray[8] = "length";
            stringArray[9] = "getAt";
            stringArray[10] = "length";
        }

        private static /* synthetic */ CallSiteArray $createCallSiteArray() {
            String[] stringArray = new String[11];
            VisitorClassLoader.$createCallSiteArray_1(stringArray);
            return new CallSiteArray(VisitorClassLoader.class, stringArray);
        }

        private static /* synthetic */ CallSite[] $getCallSiteArray() {
            CallSiteArray callSiteArray;
            if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                callSiteArray = VisitorClassLoader.$createCallSiteArray();
                $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
            }
            return callSiteArray.array;
        }
    }

    public static class VisitorSourceOperation
    implements CompilationUnit.IPrimaryClassNodeOperation,
    GroovyObject {
        private final GroovyClassVisitor visitor;
        private static /* synthetic */ ClassInfo $staticClassInfo;
        public static transient /* synthetic */ boolean __$stMC;
        private transient /* synthetic */ MetaClass metaClass;
        private static /* synthetic */ SoftReference $callSiteArray;

        public VisitorSourceOperation(GroovyClassVisitor visitor) {
            GroovyClassVisitor groovyClassVisitor;
            MetaClass metaClass;
            CallSite[] callSiteArray = VisitorSourceOperation.$getCallSiteArray();
            this.metaClass = metaClass = this.$getStaticMetaClass();
            this.visitor = groovyClassVisitor = visitor;
        }

        public void call(SourceUnit source, GeneratorContext context, ClassNode classNode) throws CompilationFailedException {
            CallSite[] callSiteArray = VisitorSourceOperation.$getCallSiteArray();
            callSiteArray[0].call((Object)classNode, (Object)this.visitor);
        }

        public /* synthetic */ Object methodMissing(String name, Object args) {
            CallSite[] callSiteArray = VisitorSourceOperation.$getCallSiteArray();
            if (!(args instanceof Object[])) {
                return ScriptBytecodeAdapter.invokeMethodN(VisitorSourceOperation.class, ScriptVariableAnalyzer.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])new Object[]{args});
            }
            if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[1].callGetProperty((Object)((Object[])ScriptBytecodeAdapter.castToType((Object)args, Object[].class))), (Object)1)) {
                    return ScriptBytecodeAdapter.invokeMethodN(VisitorSourceOperation.class, ScriptVariableAnalyzer.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])new Object[]{callSiteArray[2].call((Object)((Object[])ScriptBytecodeAdapter.castToType((Object)args, Object[].class)), (Object)0)});
                }
            } else if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[3].callGetProperty((Object)((Object[])ScriptBytecodeAdapter.castToType((Object)args, Object[].class))), (Object)1)) {
                return ScriptBytecodeAdapter.invokeMethodN(VisitorSourceOperation.class, ScriptVariableAnalyzer.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])new Object[]{BytecodeInterface8.objectArrayGet((Object[])((Object[])ScriptBytecodeAdapter.castToType((Object)args, Object[].class)), (int)0)});
            }
            return ScriptBytecodeAdapter.invokeMethodN(VisitorSourceOperation.class, ScriptVariableAnalyzer.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])ScriptBytecodeAdapter.despreadList((Object[])new Object[0], (Object[])new Object[]{args}, (int[])new int[]{0}));
        }

        public static /* synthetic */ Object $static_methodMissing(String name, Object args) {
            CallSite[] callSiteArray = VisitorSourceOperation.$getCallSiteArray();
            if (!(args instanceof Object[])) {
                return ScriptBytecodeAdapter.invokeMethodN(VisitorSourceOperation.class, ScriptVariableAnalyzer.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])new Object[]{args});
            }
            if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[4].callGetProperty((Object)((Object[])ScriptBytecodeAdapter.castToType((Object)args, Object[].class))), (Object)1)) {
                    return ScriptBytecodeAdapter.invokeMethodN(VisitorSourceOperation.class, ScriptVariableAnalyzer.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])new Object[]{callSiteArray[5].call((Object)((Object[])ScriptBytecodeAdapter.castToType((Object)args, Object[].class)), (Object)0)});
                }
            } else if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[6].callGetProperty((Object)((Object[])ScriptBytecodeAdapter.castToType((Object)args, Object[].class))), (Object)1)) {
                return ScriptBytecodeAdapter.invokeMethodN(VisitorSourceOperation.class, ScriptVariableAnalyzer.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])new Object[]{BytecodeInterface8.objectArrayGet((Object[])((Object[])ScriptBytecodeAdapter.castToType((Object)args, Object[].class)), (int)0)});
            }
            return ScriptBytecodeAdapter.invokeMethodN(VisitorSourceOperation.class, ScriptVariableAnalyzer.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])ScriptBytecodeAdapter.despreadList((Object[])new Object[0], (Object[])new Object[]{args}, (int[])new int[]{0}));
        }

        public /* synthetic */ void propertyMissing(String name, Object value) {
            CallSite[] callSiteArray = VisitorSourceOperation.$getCallSiteArray();
            Object object = value;
            ScriptBytecodeAdapter.setProperty((Object)object, null, ScriptVariableAnalyzer.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

        public static /* synthetic */ void $static_propertyMissing(String name, Object value) {
            CallSite[] callSiteArray = VisitorSourceOperation.$getCallSiteArray();
            Object object = value;
            ScriptBytecodeAdapter.setProperty((Object)object, null, ScriptVariableAnalyzer.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

        public /* synthetic */ Object propertyMissing(String name) {
            CallSite[] callSiteArray = VisitorSourceOperation.$getCallSiteArray();
            return ScriptBytecodeAdapter.getProperty(VisitorSourceOperation.class, ScriptVariableAnalyzer.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

        public static /* synthetic */ Object $static_propertyMissing(String name) {
            CallSite[] callSiteArray = VisitorSourceOperation.$getCallSiteArray();
            return ScriptBytecodeAdapter.getProperty(VisitorSourceOperation.class, ScriptVariableAnalyzer.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

        protected /* synthetic */ MetaClass $getStaticMetaClass() {
            if (this.getClass() != VisitorSourceOperation.class) {
                return ScriptBytecodeAdapter.initMetaClass((Object)this);
            }
            ClassInfo classInfo = $staticClassInfo;
            if (classInfo == null) {
                $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
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

        public static /* synthetic */ MethodHandles.Lookup $getLookup() {
            return MethodHandles.lookup();
        }

        @Generated
        public final GroovyClassVisitor getVisitor() {
            return this.visitor;
        }

        private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
            stringArray[0] = "visitContents";
            stringArray[1] = "length";
            stringArray[2] = "getAt";
            stringArray[3] = "length";
            stringArray[4] = "length";
            stringArray[5] = "getAt";
            stringArray[6] = "length";
        }

        private static /* synthetic */ CallSiteArray $createCallSiteArray() {
            String[] stringArray = new String[7];
            VisitorSourceOperation.$createCallSiteArray_1(stringArray);
            return new CallSiteArray(VisitorSourceOperation.class, stringArray);
        }

        private static /* synthetic */ CallSite[] $getCallSiteArray() {
            CallSiteArray callSiteArray;
            if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                callSiteArray = VisitorSourceOperation.$createCallSiteArray();
                $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
            }
            return callSiteArray.array;
        }
    }
}

