/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.Closure
 *  groovy.lang.GroovyObject
 *  groovy.lang.MetaClass
 *  groovy.lang.MissingPropertyException
 *  groovy.lang.Reference
 *  groovy.transform.Generated
 *  jline.console.completer.Completer
 *  org.codehaus.groovy.reflection.ClassInfo
 *  org.codehaus.groovy.runtime.BytecodeInterface8
 *  org.codehaus.groovy.runtime.GStringImpl
 *  org.codehaus.groovy.runtime.GeneratedClosure
 *  org.codehaus.groovy.runtime.ScriptBytecodeAdapter
 *  org.codehaus.groovy.runtime.callsite.CallSite
 *  org.codehaus.groovy.runtime.callsite.CallSiteArray
 *  org.codehaus.groovy.runtime.powerassert.AssertionRenderer
 *  org.codehaus.groovy.runtime.powerassert.ValueRecorder
 *  org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation
 *  org.codehaus.groovy.runtime.typehandling.ShortTypeHandling
 */
package org.apache.groovy.groovysh;

import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import groovy.lang.MissingPropertyException;
import groovy.lang.Reference;
import groovy.transform.Generated;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import java.util.List;
import jline.console.completer.Completer;
import org.apache.groovy.groovysh.CommandSupport;
import org.apache.groovy.groovysh.Groovysh;
import org.apache.groovy.groovysh.util.SimpleCompleter;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.BytecodeInterface8;
import org.codehaus.groovy.runtime.GStringImpl;
import org.codehaus.groovy.runtime.GeneratedClosure;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.powerassert.AssertionRenderer;
import org.codehaus.groovy.runtime.powerassert.ValueRecorder;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;
import org.codehaus.groovy.runtime.typehandling.ShortTypeHandling;

public abstract class ComplexCommandSupport
extends CommandSupport {
    protected final List<String> functions;
    protected final String defaultFunction;
    private Object do_all;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private static /* synthetic */ SoftReference $callSiteArray;

    protected ComplexCommandSupport(Groovysh shell, String name, String shortcut, List<String> comFunctions) {
        CallSite[] callSiteArray = ComplexCommandSupport.$getCallSiteArray();
        this(shell, name, shortcut, comFunctions, null);
    }

    /*
     * Unable to fully structure code
     */
    protected ComplexCommandSupport(Groovysh shell, String name, String shortcut, List<String> comFunctions, String defaultFunction) {
        var6_6 = ComplexCommandSupport.$getCallSiteArray();
        super(shell, name, shortcut);
        var7_7 = new _closure1(this, this);
        this.do_all = var7_7;
        var8_8 = comFunctions;
        this.functions = var8_8;
        this.defaultFunction = var9_9 = defaultFunction;
        var10_10 = new ValueRecorder();
        try {
            v0 = defaultFunction;
            var10_10.record((Object)v0, 8);
            v1 = ScriptBytecodeAdapter.compareEqual((Object)v0, null);
            var10_10.record((Object)v1, 25);
            if (v1) ** GOTO lbl-1000
            v2 = defaultFunction;
            var10_10.record((Object)v2, 36);
            v3 = this.functions;
            var10_10.record(v3, 55);
            var10_10.record(v3, 55);
            v4 = ScriptBytecodeAdapter.isCase((Object)v2, v3);
            var10_10.record((Object)v4, 52);
            if (v4) lbl-1000:
            // 2 sources

            {
                v5 = true;
            } else {
                v5 = false;
            }
            var10_10.record((Object)v5, 33);
            if (v5) {
                var10_10.clear();
            } else {
                ScriptBytecodeAdapter.assertFailed((Object)AssertionRenderer.render((String)"assert(defaultFunction  == null || defaultFunction in functions)", (ValueRecorder)var10_10), null);
            }
        }
        catch (Throwable v6) {
            var10_10.clear();
            throw v6;
        }
    }

    @Override
    protected List<Completer> createCompleters() {
        CallSite[] callSiteArray = ComplexCommandSupport.$getCallSiteArray();
        Reference c = new Reference(callSiteArray[0].callConstructor(SimpleCompleter.class));
        callSiteArray[1].call(c.get(), (Object)false);
        public final class _createCompleters_closure2
        extends Closure
        implements GeneratedClosure {
            private /* synthetic */ Reference c;
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _createCompleters_closure2(Object _outerInstance, Object _thisObject, Reference c) {
                Reference reference;
                CallSite[] callSiteArray = _createCompleters_closure2.$getCallSiteArray();
                super(_outerInstance, _thisObject);
                this.c = reference = c;
            }

            public Object doCall(String it) {
                CallSite[] callSiteArray = _createCompleters_closure2.$getCallSiteArray();
                return callSiteArray[0].call(this.c.get(), (Object)it);
            }

            @Generated
            public Object call(String it) {
                CallSite[] callSiteArray = _createCompleters_closure2.$getCallSiteArray();
                if (__$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                    return callSiteArray[1].callCurrent((GroovyObject)this, (Object)it);
                }
                return this.doCall(it);
            }

            @Generated
            public Object getC() {
                CallSite[] callSiteArray = _createCompleters_closure2.$getCallSiteArray();
                return this.c.get();
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (((Object)((Object)this)).getClass() != _createCompleters_closure2.class) {
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
                stringArray[0] = "add";
                stringArray[1] = "doCall";
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[2];
                _createCompleters_closure2.$createCallSiteArray_1(stringArray);
                return new CallSiteArray(_createCompleters_closure2.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _createCompleters_closure2.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        callSiteArray[2].call(this.functions, (Object)new _createCompleters_closure2(this, this, c));
        return ScriptBytecodeAdapter.createList((Object[])new Object[]{c.get(), null});
    }

    @Override
    public Object execute(List<String> args) {
        CallSite[] callSiteArray = ComplexCommandSupport.$getCallSiteArray();
        ValueRecorder valueRecorder = new ValueRecorder();
        try {
            List list = args;
            valueRecorder.record(list, 8);
            boolean bl = ScriptBytecodeAdapter.compareNotEqual(list, null);
            valueRecorder.record((Object)bl, 13);
            if (bl) {
                valueRecorder.clear();
            } else {
                ScriptBytecodeAdapter.assertFailed((Object)AssertionRenderer.render((String)"assert args != null", (ValueRecorder)valueRecorder), null);
            }
        }
        catch (Throwable throwable) {
            valueRecorder.clear();
            throw throwable;
        }
        if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[3].call(args), (Object)0)) {
            if (DefaultTypeTransformation.booleanUnbox((Object)this.defaultFunction)) {
                List list;
                args = list = ScriptBytecodeAdapter.createList((Object[])new Object[]{this.defaultFunction});
            } else {
                callSiteArray[4].callCurrent((GroovyObject)this, (Object)new GStringImpl(new Object[]{callSiteArray[5].callGroovyObjectGetProperty((Object)this), this.functions}, new String[]{"Command '", "' requires at least one argument of ", ""}));
            }
        }
        return callSiteArray[6].callCurrent((GroovyObject)this, callSiteArray[7].call(args, (Object)0), callSiteArray[8].call((Object)args));
    }

    protected Object executeFunction(String fname, List<String> args) {
        CallSite[] callSiteArray = ComplexCommandSupport.$getCallSiteArray();
        ValueRecorder valueRecorder = new ValueRecorder();
        try {
            List<String> list = args;
            valueRecorder.record(list, 8);
            boolean bl = ScriptBytecodeAdapter.compareNotEqual(list, null);
            valueRecorder.record((Object)bl, 13);
            if (bl) {
                valueRecorder.clear();
            } else {
                ScriptBytecodeAdapter.assertFailed((Object)AssertionRenderer.render((String)"assert args != null", (ValueRecorder)valueRecorder), null);
            }
        }
        catch (Throwable throwable) {
            valueRecorder.clear();
            throw throwable;
        }
        List<String> myFunctions = this.functions;
        if (ScriptBytecodeAdapter.isCase((Object)fname, myFunctions)) {
            Closure func = null;
            if (__$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                Object object = callSiteArray[9].callCurrent((GroovyObject)this, (Object)fname);
                func = (Closure)ScriptBytecodeAdapter.castToType((Object)object, Closure.class);
            } else {
                Closure closure;
                func = closure = this.loadFunction(fname);
            }
            callSiteArray[10].call(callSiteArray[11].callGroovyObjectGetProperty((Object)this), (Object)new GStringImpl(new Object[]{fname, args}, new String[]{"Invoking function '", "' w/args: ", ""}));
            return callSiteArray[12].call((Object)func, args);
        }
        return callSiteArray[13].callCurrent((GroovyObject)this, (Object)new GStringImpl(new Object[]{fname, myFunctions}, new String[]{"Unknown function name: '", "'. Valid arguments: ", ""}));
    }

    protected Closure loadFunction(String name) {
        CallSite[] callSiteArray = ComplexCommandSupport.$getCallSiteArray();
        ValueRecorder valueRecorder = new ValueRecorder();
        try {
            String string = name;
            valueRecorder.record((Object)string, 8);
            if (DefaultTypeTransformation.booleanUnbox((Object)string)) {
                valueRecorder.clear();
            } else {
                ScriptBytecodeAdapter.assertFailed((Object)AssertionRenderer.render((String)"assert name", (ValueRecorder)valueRecorder), null);
            }
        }
        catch (Throwable throwable) {
            valueRecorder.clear();
            throw throwable;
        }
        Closure closure = (Closure)ScriptBytecodeAdapter.castToType((Object)ScriptBytecodeAdapter.getGroovyObjectProperty(ComplexCommandSupport.class, (GroovyObject)this, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"do_", ""}))), Closure.class);
        try {
            return closure;
        }
        catch (MissingPropertyException e) {
            Closure closure2 = (Closure)ScriptBytecodeAdapter.castToType((Object)callSiteArray[14].callCurrent((GroovyObject)this, (Object)new GStringImpl(new Object[]{e}, new String[]{"Failed to load delegate function: ", ""})), Closure.class);
            return closure2;
        }
    }

    @Override
    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != ComplexCommandSupport.class) {
            return ScriptBytecodeAdapter.initMetaClass((Object)this);
        }
        ClassInfo classInfo = $staticClassInfo;
        if (classInfo == null) {
            $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
        }
        return classInfo.getMetaClass();
    }

    public static /* synthetic */ MethodHandles.Lookup $getLookup() {
        return MethodHandles.lookup();
    }

    @Generated
    public Object getDo_all() {
        return this.do_all;
    }

    @Generated
    public void setDo_all(Object object) {
        this.do_all = object;
    }

    public /* synthetic */ List super$2$createCompleters() {
        return super.createCompleters();
    }

    public /* synthetic */ MetaClass super$2$$getStaticMetaClass() {
        return super.$getStaticMetaClass();
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
        stringArray[0] = "<$constructor$>";
        stringArray[1] = "setWithBlank";
        stringArray[2] = "each";
        stringArray[3] = "size";
        stringArray[4] = "fail";
        stringArray[5] = "name";
        stringArray[6] = "executeFunction";
        stringArray[7] = "getAt";
        stringArray[8] = "tail";
        stringArray[9] = "loadFunction";
        stringArray[10] = "debug";
        stringArray[11] = "log";
        stringArray[12] = "call";
        stringArray[13] = "fail";
        stringArray[14] = "fail";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[15];
        ComplexCommandSupport.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(ComplexCommandSupport.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = ComplexCommandSupport.$createCallSiteArray();
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
            public final class _closure3
            extends Closure
            implements GeneratedClosure {
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;
                private static /* synthetic */ SoftReference $callSiteArray;

                public _closure3(Object _outerInstance, Object _thisObject) {
                    CallSite[] callSiteArray = _closure3.$getCallSiteArray();
                    super(_outerInstance, _thisObject);
                }

                public Object doCall(Object it) {
                    CallSite[] callSiteArray = _closure3.$getCallSiteArray();
                    return ScriptBytecodeAdapter.compareNotEqual((Object)it, (Object)"all");
                }

                @Generated
                public Object doCall() {
                    CallSite[] callSiteArray = _closure3.$getCallSiteArray();
                    return this.doCall(null);
                }

                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                    if (((Object)((Object)this)).getClass() != _closure3.class) {
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
                    return new CallSiteArray(_closure3.class, stringArray);
                }

                private static /* synthetic */ CallSite[] $getCallSiteArray() {
                    CallSiteArray callSiteArray;
                    if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                        callSiteArray = _closure3.$createCallSiteArray();
                        $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                    }
                    return callSiteArray.array;
                }
            }
            public final class _closure4
            extends Closure
            implements GeneratedClosure {
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;
                private static /* synthetic */ SoftReference $callSiteArray;

                public _closure4(Object _outerInstance, Object _thisObject) {
                    CallSite[] callSiteArray = _closure4.$getCallSiteArray();
                    super(_outerInstance, _thisObject);
                }

                public Object doCall(Object it) {
                    CallSite[] callSiteArray = _closure4.$getCallSiteArray();
                    return callSiteArray[0].callCurrent((GroovyObject)this, it, (Object)ScriptBytecodeAdapter.createList((Object[])new Object[0]));
                }

                @Generated
                public Object doCall() {
                    CallSite[] callSiteArray = _closure4.$getCallSiteArray();
                    return this.doCall(null);
                }

                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                    if (((Object)((Object)this)).getClass() != _closure4.class) {
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
                    stringArray[0] = "executeFunction";
                    return new CallSiteArray(_closure4.class, stringArray);
                }

                private static /* synthetic */ CallSite[] $getCallSiteArray() {
                    CallSiteArray callSiteArray;
                    if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                        callSiteArray = _closure4.$createCallSiteArray();
                        $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                    }
                    return callSiteArray.array;
                }
            }
            return callSiteArray[0].call(callSiteArray[1].call(callSiteArray[2].callGroovyObjectGetProperty((Object)this), (Object)new _closure3((Object)this, this.getThisObject())), (Object)new _closure4((Object)this, this.getThisObject()));
        }

        @Generated
        public Object doCall() {
            CallSite[] callSiteArray = _closure1.$getCallSiteArray();
            return this.doCall(null);
        }

        protected /* synthetic */ MetaClass $getStaticMetaClass() {
            if (((Object)((Object)this)).getClass() != _closure1.class) {
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
            stringArray[0] = "collect";
            stringArray[1] = "findAll";
            stringArray[2] = "functions";
        }

        private static /* synthetic */ CallSiteArray $createCallSiteArray() {
            String[] stringArray = new String[3];
            _closure1.$createCallSiteArray_1(stringArray);
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

