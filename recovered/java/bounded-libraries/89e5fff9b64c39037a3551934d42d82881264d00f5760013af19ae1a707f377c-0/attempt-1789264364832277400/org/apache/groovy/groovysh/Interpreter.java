/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.Binding
 *  groovy.lang.Closure
 *  groovy.lang.GroovyClassLoader
 *  groovy.lang.GroovyObject
 *  groovy.lang.GroovyShell
 *  groovy.lang.MetaClass
 *  groovy.lang.Reference
 *  groovy.lang.Script
 *  groovy.transform.Generated
 *  groovy.transform.Internal
 *  org.codehaus.groovy.control.CompilerConfiguration
 *  org.codehaus.groovy.reflection.ClassInfo
 *  org.codehaus.groovy.runtime.BytecodeInterface8
 *  org.codehaus.groovy.runtime.FormatHelper
 *  org.codehaus.groovy.runtime.GStringImpl
 *  org.codehaus.groovy.runtime.GeneratedClosure
 *  org.codehaus.groovy.runtime.MethodClosure
 *  org.codehaus.groovy.runtime.ScriptBytecodeAdapter
 *  org.codehaus.groovy.runtime.callsite.CallSite
 *  org.codehaus.groovy.runtime.callsite.CallSiteArray
 *  org.codehaus.groovy.runtime.powerassert.AssertionRenderer
 *  org.codehaus.groovy.runtime.powerassert.ValueRecorder
 *  org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation
 *  org.codehaus.groovy.runtime.typehandling.ShortTypeHandling
 *  org.codehaus.groovy.tools.shell.util.Logger
 */
package org.apache.groovy.groovysh;

import groovy.lang.Binding;
import groovy.lang.Closure;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import groovy.lang.GroovyShell;
import groovy.lang.MetaClass;
import groovy.lang.Reference;
import groovy.lang.Script;
import groovy.transform.Generated;
import groovy.transform.Internal;
import java.beans.Transient;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import java.lang.reflect.Method;
import java.util.Collection;
import org.apache.groovy.groovysh.Evaluator;
import org.apache.groovy.groovysh.Parser;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.BytecodeInterface8;
import org.codehaus.groovy.runtime.FormatHelper;
import org.codehaus.groovy.runtime.GStringImpl;
import org.codehaus.groovy.runtime.GeneratedClosure;
import org.codehaus.groovy.runtime.MethodClosure;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.powerassert.AssertionRenderer;
import org.codehaus.groovy.runtime.powerassert.ValueRecorder;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;
import org.codehaus.groovy.runtime.typehandling.ShortTypeHandling;
import org.codehaus.groovy.tools.shell.util.Logger;

public class Interpreter
implements Evaluator,
GroovyObject {
    private static final String SCRIPT_FILENAME = "groovysh_evaluate";
    private final Logger log;
    private final GroovyShell shell;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private transient /* synthetic */ MetaClass metaClass;
    private static /* synthetic */ SoftReference $callSiteArray;

    public Interpreter(ClassLoader classLoader, Binding binding) {
        CallSite[] callSiteArray = Interpreter.$getCallSiteArray();
        this(classLoader, binding, (CompilerConfiguration)ScriptBytecodeAdapter.castToType((Object)callSiteArray[0].callGetProperty(CompilerConfiguration.class), CompilerConfiguration.class));
    }

    public Interpreter(ClassLoader classLoader, Binding binding, CompilerConfiguration configuration) {
        MetaClass metaClass;
        CallSite[] callSiteArray = Interpreter.$getCallSiteArray();
        Object object = callSiteArray[1].call(Logger.class, callSiteArray[2].callGroovyObjectGetProperty((Object)this));
        this.log = (Logger)ScriptBytecodeAdapter.castToType((Object)object, Logger.class);
        this.metaClass = metaClass = this.$getStaticMetaClass();
        ValueRecorder valueRecorder = new ValueRecorder();
        try {
            ClassLoader classLoader2 = classLoader;
            valueRecorder.record((Object)classLoader2, 8);
            if (DefaultTypeTransformation.booleanUnbox((Object)classLoader2)) {
                valueRecorder.clear();
            } else {
                ScriptBytecodeAdapter.assertFailed((Object)AssertionRenderer.render((String)"assert classLoader", (ValueRecorder)valueRecorder), null);
            }
        }
        catch (Throwable throwable) {
            valueRecorder.clear();
            throw throwable;
        }
        ValueRecorder valueRecorder2 = new ValueRecorder();
        try {
            Binding binding2 = binding;
            valueRecorder2.record((Object)binding2, 8);
            if (DefaultTypeTransformation.booleanUnbox((Object)binding2)) {
                valueRecorder2.clear();
            } else {
                ScriptBytecodeAdapter.assertFailed((Object)AssertionRenderer.render((String)"assert binding", (ValueRecorder)valueRecorder2), null);
            }
        }
        catch (Throwable throwable) {
            valueRecorder2.clear();
            throw throwable;
        }
        Object object2 = callSiteArray[3].callConstructor(GroovyShell.class, (Object)classLoader, (Object)binding, (Object)configuration);
        this.shell = (GroovyShell)ScriptBytecodeAdapter.castToType((Object)object2, GroovyShell.class);
    }

    public Binding getContext() {
        CallSite[] callSiteArray = Interpreter.$getCallSiteArray();
        return (Binding)ScriptBytecodeAdapter.castToType((Object)callSiteArray[4].call((Object)this.shell), Binding.class);
    }

    public GroovyClassLoader getClassLoader() {
        CallSite[] callSiteArray = Interpreter.$getCallSiteArray();
        return (GroovyClassLoader)ScriptBytecodeAdapter.castToType((Object)callSiteArray[5].callGroovyObjectGetProperty((Object)this.shell), GroovyClassLoader.class);
    }

    public GroovyShell getShell() {
        CallSite[] callSiteArray = Interpreter.$getCallSiteArray();
        return this.shell;
    }

    @Override
    public Object evaluate(Collection<String> buffer) {
        CallSite[] callSiteArray = Interpreter.$getCallSiteArray();
        ValueRecorder valueRecorder = new ValueRecorder();
        try {
            Collection<String> collection = buffer;
            valueRecorder.record(collection, 8);
            if (DefaultTypeTransformation.booleanUnbox(collection)) {
                valueRecorder.clear();
            } else {
                ScriptBytecodeAdapter.assertFailed((Object)AssertionRenderer.render((String)"assert buffer", (ValueRecorder)valueRecorder), null);
            }
        }
        catch (Throwable throwable) {
            valueRecorder.clear();
            throw throwable;
        }
        Object source = callSiteArray[6].call(buffer, callSiteArray[7].callGetProperty(Parser.class));
        Object result = null;
        Reference type = new Reference(null);
        Class cfr_ignored_0 = (Class)type.get();
        try {
            Script script = (Script)ScriptBytecodeAdapter.castToType((Object)callSiteArray[8].call((Object)this.shell, source, (Object)SCRIPT_FILENAME), Script.class);
            Object object = callSiteArray[9].call((Object)script);
            type.set((Object)ShortTypeHandling.castToClass((Object)object));
            callSiteArray[10].call((Object)this.log, (Object)new GStringImpl(new Object[]{script}, new String[]{"Compiled script: ", ""}));
            public final class _evaluate_closure1
            extends Closure
            implements GeneratedClosure {
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;
                private static /* synthetic */ SoftReference $callSiteArray;

                public _evaluate_closure1(Object _outerInstance, Object _thisObject) {
                    CallSite[] callSiteArray = _evaluate_closure1.$getCallSiteArray();
                    super(_outerInstance, _thisObject);
                }

                public Object doCall(Method it) {
                    CallSite[] callSiteArray = _evaluate_closure1.$getCallSiteArray();
                    return ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[0].callGetProperty((Object)it), (Object)"main");
                }

                @Generated
                public Object call(Method it) {
                    CallSite[] callSiteArray = _evaluate_closure1.$getCallSiteArray();
                    if (__$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                        return callSiteArray[1].callCurrent((GroovyObject)this, (Object)it);
                    }
                    return this.doCall(it);
                }

                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                    if (((Object)((Object)this)).getClass() != _evaluate_closure1.class) {
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
                    stringArray[0] = "name";
                    stringArray[1] = "doCall";
                }

                private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                    String[] stringArray = new String[2];
                    _evaluate_closure1.$createCallSiteArray_1(stringArray);
                    return new CallSiteArray(_evaluate_closure1.class, stringArray);
                }

                private static /* synthetic */ CallSite[] $getCallSiteArray() {
                    CallSiteArray callSiteArray;
                    if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                        callSiteArray = _evaluate_closure1.$createCallSiteArray();
                        $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                    }
                    return callSiteArray.array;
                }
            }
            if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[11].call(callSiteArray[12].callGetProperty((Object)((Class)type.get())), (Object)new _evaluate_closure1(this, this)))) {
                Object object2;
                result = object2 = callSiteArray[13].call((Object)script);
            }
            callSiteArray[14].call((Object)this.log, (Object)new GStringImpl(new Object[]{callSiteArray[15].call(FormatHelper.class, result), callSiteArray[16].callSafe(result)}, new String[]{"Evaluation result: ", " (", ")"}));
            public final class _evaluate_closure2
            extends Closure
            implements GeneratedClosure {
                private /* synthetic */ Reference type;
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;
                private static /* synthetic */ SoftReference $callSiteArray;

                public _evaluate_closure2(Object _outerInstance, Object _thisObject, Reference type) {
                    Reference reference;
                    CallSite[] callSiteArray = _evaluate_closure2.$getCallSiteArray();
                    super(_outerInstance, _thisObject);
                    this.type = reference = type;
                }

                public Object doCall(Method m) {
                    CallSite[] callSiteArray = _evaluate_closure2.$getCallSiteArray();
                    if (!(ScriptBytecodeAdapter.isCase((Object)callSiteArray[0].callGetProperty((Object)m), (Object)ScriptBytecodeAdapter.createList((Object[])new Object[]{"main", "run"})) || DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[1].call(callSiteArray[2].callGetProperty((Object)m), (Object)"super$")) || DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[3].call(callSiteArray[4].callGetProperty((Object)m), (Object)"class$")) || DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[5].call(callSiteArray[6].callGetProperty((Object)m), (Object)"$")))) {
                        callSiteArray[7].call(callSiteArray[8].callGroovyObjectGetProperty((Object)this), (Object)new GStringImpl(new Object[]{callSiteArray[9].callGetProperty((Object)m)}, new String[]{"Saving method definition: ", ""}));
                        Object object = callSiteArray[10].callConstructor(MethodClosure.class, callSiteArray[11].call(this.type.get()), callSiteArray[12].callGetProperty((Object)m));
                        callSiteArray[13].call(callSiteArray[14].callGroovyObjectGetProperty((Object)this), (Object)new GStringImpl(new Object[]{callSiteArray[15].callGetProperty((Object)m)}, new String[]{"", ""}), object);
                        return object;
                    }
                    return null;
                }

                @Generated
                public Object call(Method m) {
                    CallSite[] callSiteArray = _evaluate_closure2.$getCallSiteArray();
                    if (__$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                        return callSiteArray[16].callCurrent((GroovyObject)this, (Object)m);
                    }
                    return this.doCall(m);
                }

                @Generated
                public Class getType() {
                    CallSite[] callSiteArray = _evaluate_closure2.$getCallSiteArray();
                    return ShortTypeHandling.castToClass((Object)this.type.get());
                }

                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                    if (((Object)((Object)this)).getClass() != _evaluate_closure2.class) {
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
                    stringArray[0] = "name";
                    stringArray[1] = "startsWith";
                    stringArray[2] = "name";
                    stringArray[3] = "startsWith";
                    stringArray[4] = "name";
                    stringArray[5] = "startsWith";
                    stringArray[6] = "name";
                    stringArray[7] = "debug";
                    stringArray[8] = "log";
                    stringArray[9] = "name";
                    stringArray[10] = "<$constructor$>";
                    stringArray[11] = "newInstance";
                    stringArray[12] = "name";
                    stringArray[13] = "putAt";
                    stringArray[14] = "context";
                    stringArray[15] = "name";
                    stringArray[16] = "doCall";
                }

                private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                    String[] stringArray = new String[17];
                    _evaluate_closure2.$createCallSiteArray_1(stringArray);
                    return new CallSiteArray(_evaluate_closure2.class, stringArray);
                }

                private static /* synthetic */ CallSite[] $getCallSiteArray() {
                    CallSiteArray callSiteArray;
                    if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                        callSiteArray = _evaluate_closure2.$createCallSiteArray();
                        $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                    }
                    return callSiteArray.array;
                }
            }
            callSiteArray[17].call(callSiteArray[18].callGetProperty((Object)((Class)type.get())), (Object)new _evaluate_closure2(this, this, type));
        }
        catch (Throwable throwable) {
            if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[25].callGetPropertySafe((Object)((Class)type.get())))) {
                callSiteArray[26].call(callSiteArray[27].callGroovyObjectGetProperty((Object)this), callSiteArray[28].callGetPropertySafe((Object)((Class)type.get())));
            }
            callSiteArray[29].call(callSiteArray[30].callGroovyObjectGetProperty((Object)this), (Object)"$_run_closure");
            throw throwable;
        }
        if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[19].callGetPropertySafe((Object)((Class)type.get())))) {
            callSiteArray[20].call(callSiteArray[21].callGroovyObjectGetProperty((Object)this), callSiteArray[22].callGetPropertySafe((Object)((Class)type.get())));
        }
        callSiteArray[23].call(callSiteArray[24].callGroovyObjectGetProperty((Object)this), (Object)"$_run_closure");
        return result;
    }

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != Interpreter.class) {
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
    public static String getSCRIPT_FILENAME() {
        return SCRIPT_FILENAME;
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
        stringArray[0] = "DEFAULT";
        stringArray[1] = "create";
        stringArray[2] = "class";
        stringArray[3] = "<$constructor$>";
        stringArray[4] = "getContext";
        stringArray[5] = "classLoader";
        stringArray[6] = "join";
        stringArray[7] = "NEWLINE";
        stringArray[8] = "parse";
        stringArray[9] = "getClass";
        stringArray[10] = "debug";
        stringArray[11] = "any";
        stringArray[12] = "declaredMethods";
        stringArray[13] = "run";
        stringArray[14] = "debug";
        stringArray[15] = "toString";
        stringArray[16] = "getClass";
        stringArray[17] = "each";
        stringArray[18] = "declaredMethods";
        stringArray[19] = "name";
        stringArray[20] = "removeClassCacheEntry";
        stringArray[21] = "classLoader";
        stringArray[22] = "name";
        stringArray[23] = "removeClassCacheEntry";
        stringArray[24] = "classLoader";
        stringArray[25] = "name";
        stringArray[26] = "removeClassCacheEntry";
        stringArray[27] = "classLoader";
        stringArray[28] = "name";
        stringArray[29] = "removeClassCacheEntry";
        stringArray[30] = "classLoader";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[31];
        Interpreter.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(Interpreter.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = Interpreter.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }
}

