/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.Closure
 *  groovy.lang.GroovyObject
 *  groovy.lang.MetaClass
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
 *  org.codehaus.groovy.runtime.typehandling.ShortTypeHandling
 *  org.codehaus.groovy.tools.shell.util.Preferences
 */
package org.apache.groovy.groovysh.commands;

import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import groovy.lang.Reference;
import groovy.transform.Generated;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import java.util.List;
import java.util.Set;
import jline.console.completer.Completer;
import org.apache.groovy.groovysh.CommandSupport;
import org.apache.groovy.groovysh.Groovysh;
import org.apache.groovy.groovysh.util.PackageHelper;
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
import org.codehaus.groovy.runtime.typehandling.ShortTypeHandling;
import org.codehaus.groovy.tools.shell.util.Preferences;

public class SetCommand
extends CommandSupport {
    public static final String COMMAND_NAME = ":set";
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private static /* synthetic */ SoftReference $callSiteArray;

    public SetCommand(Groovysh shell) {
        CallSite[] callSiteArray = SetCommand.$getCallSiteArray();
        super(shell, ShortTypeHandling.castToString((Object)callSiteArray[0].callGetProperty(SetCommand.class)), ":=");
    }

    @Override
    protected List<Completer> createCompleters() {
        CallSite[] callSiteArray = SetCommand.$getCallSiteArray();
        public final class _createCompleters_closure1
        extends Closure
        implements GeneratedClosure {
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _createCompleters_closure1(Object _outerInstance, Object _thisObject) {
                CallSite[] callSiteArray = _createCompleters_closure1.$getCallSiteArray();
                super(_outerInstance, _thisObject);
            }

            public Object doCall(Object it) {
                CallSite[] callSiteArray = _createCompleters_closure1.$getCallSiteArray();
                Reference set = new Reference((Object)((Set)ScriptBytecodeAdapter.asType((Object)ScriptBytecodeAdapter.createList((Object[])new Object[0]), Set.class)));
                String[] keys = (String[])ScriptBytecodeAdapter.castToType((Object)callSiteArray[0].call(Preferences.class), String[].class);
                public final class _closure3
                extends Closure
                implements GeneratedClosure {
                    private /* synthetic */ Reference set;
                    private static /* synthetic */ ClassInfo $staticClassInfo;
                    public static transient /* synthetic */ boolean __$stMC;
                    private static /* synthetic */ SoftReference $callSiteArray;

                    public _closure3(Object _outerInstance, Object _thisObject, Reference set) {
                        Reference reference;
                        CallSite[] callSiteArray = _closure3.$getCallSiteArray();
                        super(_outerInstance, _thisObject);
                        this.set = reference = set;
                    }

                    public Object doCall(String key) {
                        CallSite[] callSiteArray = _closure3.$getCallSiteArray();
                        return callSiteArray[0].call(this.set.get(), (Object)key);
                    }

                    @Generated
                    public Object call(String key) {
                        CallSite[] callSiteArray = _closure3.$getCallSiteArray();
                        if (__$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                            return callSiteArray[1].callCurrent((GroovyObject)this, (Object)key);
                        }
                        return this.doCall(key);
                    }

                    @Generated
                    public Set getSet() {
                        CallSite[] callSiteArray = _closure3.$getCallSiteArray();
                        return (Set)ScriptBytecodeAdapter.castToType((Object)this.set.get(), Set.class);
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

                    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
                        stringArray[0] = "add";
                        stringArray[1] = "doCall";
                    }

                    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                        String[] stringArray = new String[2];
                        _closure3.$createCallSiteArray_1(stringArray);
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
                callSiteArray[1].call((Object)keys, (Object)new _closure3((Object)this, this.getThisObject(), set));
                callSiteArray[2].call((Object)((Set)set.get()), callSiteArray[3].callGetProperty(Preferences.class));
                callSiteArray[4].call((Object)((Set)set.get()), callSiteArray[5].callGetProperty(Preferences.class));
                callSiteArray[6].call((Object)((Set)set.get()), callSiteArray[7].callGetProperty(Preferences.class));
                callSiteArray[8].call((Object)((Set)set.get()), callSiteArray[9].callGetProperty(Preferences.class));
                callSiteArray[10].call((Object)((Set)set.get()), callSiteArray[11].callGetProperty(Preferences.class));
                callSiteArray[12].call((Object)((Set)set.get()), callSiteArray[13].callGetProperty(Groovysh.class));
                callSiteArray[14].call((Object)((Set)set.get()), callSiteArray[15].callGetProperty(Groovysh.class));
                callSiteArray[16].call((Object)((Set)set.get()), callSiteArray[17].callGetProperty(Groovysh.class));
                callSiteArray[18].call((Object)((Set)set.get()), callSiteArray[19].callGetProperty(Groovysh.class));
                callSiteArray[20].call((Object)((Set)set.get()), callSiteArray[21].callGetProperty(PackageHelper.class));
                return callSiteArray[22].call((Object)((Set)set.get()));
            }

            @Generated
            public Object doCall() {
                CallSite[] callSiteArray = _createCompleters_closure1.$getCallSiteArray();
                return this.doCall(null);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (((Object)((Object)this)).getClass() != _createCompleters_closure1.class) {
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
                stringArray[0] = "keys";
                stringArray[1] = "each";
                stringArray[2] = "leftShift";
                stringArray[3] = "VERBOSITY_KEY";
                stringArray[4] = "leftShift";
                stringArray[5] = "EDITOR_KEY";
                stringArray[6] = "leftShift";
                stringArray[7] = "PARSER_FLAVOR_KEY";
                stringArray[8] = "leftShift";
                stringArray[9] = "SANITIZE_STACK_TRACE_KEY";
                stringArray[10] = "leftShift";
                stringArray[11] = "SHOW_LAST_RESULT_KEY";
                stringArray[12] = "leftShift";
                stringArray[13] = "INTERPRETER_MODE_PREFERENCE_KEY";
                stringArray[14] = "leftShift";
                stringArray[15] = "AUTOINDENT_PREFERENCE_KEY";
                stringArray[16] = "leftShift";
                stringArray[17] = "COLORS_PREFERENCE_KEY";
                stringArray[18] = "leftShift";
                stringArray[19] = "METACLASS_COMPLETION_PREFIX_LENGTH_PREFERENCE_KEY";
                stringArray[20] = "leftShift";
                stringArray[21] = "IMPORT_COMPLETION_PREFERENCE_KEY";
                stringArray[22] = "toList";
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[23];
                _createCompleters_closure1.$createCallSiteArray_1(stringArray);
                return new CallSiteArray(_createCompleters_closure1.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _createCompleters_closure1.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        _createCompleters_closure1 loader = new _createCompleters_closure1(this, this);
        return ScriptBytecodeAdapter.createList((Object[])new Object[]{callSiteArray[1].callConstructor(SimpleCompleter.class, (Object)loader), null});
    }

    @Override
    public Object execute(List<String> args) {
        CallSite[] callSiteArray = SetCommand.$getCallSiteArray();
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
        if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[2].call(args), (Object)0)) {
            Object keys = callSiteArray[3].call(Preferences.class);
            if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[4].call(keys), (Object)0)) {
                callSiteArray[5].call(callSiteArray[6].callGetProperty(callSiteArray[7].callGroovyObjectGetProperty((Object)this)), (Object)"No preferences are set");
                return null;
            }
            callSiteArray[8].call(callSiteArray[9].callGetProperty(callSiteArray[10].callGroovyObjectGetProperty((Object)this)), (Object)"Preferences:");
            public final class _execute_closure2
            extends Closure
            implements GeneratedClosure {
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;
                private static /* synthetic */ SoftReference $callSiteArray;

                public _execute_closure2(Object _outerInstance, Object _thisObject) {
                    CallSite[] callSiteArray = _execute_closure2.$getCallSiteArray();
                    super(_outerInstance, _thisObject);
                }

                public Object doCall(String key) {
                    CallSite[] callSiteArray = _execute_closure2.$getCallSiteArray();
                    Object keyvalue = callSiteArray[0].call(Preferences.class, (Object)key, null);
                    return callSiteArray[1].call(callSiteArray[2].callGetProperty(callSiteArray[3].callGroovyObjectGetProperty((Object)this)), (Object)new GStringImpl(new Object[]{key, keyvalue}, new String[]{"    ", "=", ""}));
                }

                @Generated
                public Object call(String key) {
                    CallSite[] callSiteArray = _execute_closure2.$getCallSiteArray();
                    if (__$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                        return callSiteArray[4].callCurrent((GroovyObject)this, (Object)key);
                    }
                    return this.doCall(key);
                }

                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                    if (((Object)((Object)this)).getClass() != _execute_closure2.class) {
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
                    stringArray[1] = "println";
                    stringArray[2] = "out";
                    stringArray[3] = "io";
                    stringArray[4] = "doCall";
                }

                private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                    String[] stringArray = new String[5];
                    _execute_closure2.$createCallSiteArray_1(stringArray);
                    return new CallSiteArray(_execute_closure2.class, stringArray);
                }

                private static /* synthetic */ CallSite[] $getCallSiteArray() {
                    CallSiteArray callSiteArray;
                    if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                        callSiteArray = _execute_closure2.$createCallSiteArray();
                        $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                    }
                    return callSiteArray.array;
                }
            }
            callSiteArray[11].call(keys, (Object)new _execute_closure2(this, this));
            return null;
        }
        if (ScriptBytecodeAdapter.compareGreaterThan((Object)callSiteArray[12].call(args), (Object)2)) {
            callSiteArray[13].callCurrent((GroovyObject)this, (Object)new GStringImpl(new Object[]{callSiteArray[14].callGroovyObjectGetProperty((Object)this)}, new String[]{"Command '", "' requires arguments: <name> [<value>]"}));
        }
        String name = ShortTypeHandling.castToString((Object)callSiteArray[15].call(args, (Object)0));
        Object value = null;
        if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[16].call(args), (Object)1)) {
            boolean bl = true;
            value = bl;
        } else {
            Object object;
            value = object = callSiteArray[17].call(args, (Object)1);
        }
        callSiteArray[18].call(callSiteArray[19].callGroovyObjectGetProperty((Object)this), (Object)new GStringImpl(new Object[]{name, value}, new String[]{"Setting preference: ", "=", ""}));
        return callSiteArray[20].call(Preferences.class, (Object)name, callSiteArray[21].call(String.class, value));
    }

    @Override
    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != SetCommand.class) {
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

    public /* synthetic */ List super$2$createCompleters() {
        return super.createCompleters();
    }

    public /* synthetic */ MetaClass super$2$$getStaticMetaClass() {
        return super.$getStaticMetaClass();
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
        stringArray[0] = "COMMAND_NAME";
        stringArray[1] = "<$constructor$>";
        stringArray[2] = "size";
        stringArray[3] = "keys";
        stringArray[4] = "size";
        stringArray[5] = "println";
        stringArray[6] = "out";
        stringArray[7] = "io";
        stringArray[8] = "println";
        stringArray[9] = "out";
        stringArray[10] = "io";
        stringArray[11] = "each";
        stringArray[12] = "size";
        stringArray[13] = "fail";
        stringArray[14] = "name";
        stringArray[15] = "getAt";
        stringArray[16] = "size";
        stringArray[17] = "getAt";
        stringArray[18] = "debug";
        stringArray[19] = "log";
        stringArray[20] = "put";
        stringArray[21] = "valueOf";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[22];
        SetCommand.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(SetCommand.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = SetCommand.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }
}

