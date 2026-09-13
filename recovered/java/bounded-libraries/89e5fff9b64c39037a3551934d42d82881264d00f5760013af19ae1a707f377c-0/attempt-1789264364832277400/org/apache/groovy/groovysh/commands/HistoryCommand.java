/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.Closure
 *  groovy.lang.GroovyObject
 *  groovy.lang.MetaClass
 *  groovy.transform.Generated
 *  jline.console.history.History$Entry
 *  org.codehaus.groovy.reflection.ClassInfo
 *  org.codehaus.groovy.runtime.BytecodeInterface8
 *  org.codehaus.groovy.runtime.GStringImpl
 *  org.codehaus.groovy.runtime.GeneratedClosure
 *  org.codehaus.groovy.runtime.ScriptBytecodeAdapter
 *  org.codehaus.groovy.runtime.callsite.CallSite
 *  org.codehaus.groovy.runtime.callsite.CallSiteArray
 *  org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation
 *  org.codehaus.groovy.runtime.typehandling.ShortTypeHandling
 */
package org.apache.groovy.groovysh.commands;

import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import groovy.transform.Generated;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import java.util.Iterator;
import java.util.List;
import jline.console.history.History;
import org.apache.groovy.groovysh.ComplexCommandSupport;
import org.apache.groovy.groovysh.Groovysh;
import org.apache.groovy.groovysh.util.SimpleCompleter;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.BytecodeInterface8;
import org.codehaus.groovy.runtime.GStringImpl;
import org.codehaus.groovy.runtime.GeneratedClosure;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;
import org.codehaus.groovy.runtime.typehandling.ShortTypeHandling;

public class HistoryCommand
extends ComplexCommandSupport {
    public static final String COMMAND_NAME = ":history";
    private Object do_show;
    private Object do_clear;
    private Object do_flush;
    private Object do_recall;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private static /* synthetic */ SoftReference $callSiteArray;

    public HistoryCommand(Groovysh shell) {
        CallSite[] callSiteArray = HistoryCommand.$getCallSiteArray();
        super(shell, ShortTypeHandling.castToString((Object)callSiteArray[0].callGetProperty(HistoryCommand.class)), ":H", ScriptBytecodeAdapter.createList((Object[])new Object[]{"show", "clear", "flush", "recall"}), "show");
        _closure1 _closure12 = new _closure1(this, this);
        this.do_show = _closure12;
        _closure2 _closure22 = new _closure2(this, this);
        this.do_clear = _closure22;
        _closure3 _closure32 = new _closure3(this, this);
        this.do_flush = _closure32;
        _closure4 _closure42 = new _closure4(this, this);
        this.do_recall = _closure42;
    }

    protected List createCompleters() {
        CallSite[] callSiteArray = HistoryCommand.$getCallSiteArray();
        public final class _createCompleters_closure5
        extends Closure
        implements GeneratedClosure {
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _createCompleters_closure5(Object _outerInstance, Object _thisObject) {
                CallSite[] callSiteArray = _createCompleters_closure5.$getCallSiteArray();
                super(_outerInstance, _thisObject);
            }

            public Object doCall(Object it) {
                CallSite[] callSiteArray = _createCompleters_closure5.$getCallSiteArray();
                List list = ScriptBytecodeAdapter.createList((Object[])new Object[0]);
                callSiteArray[0].call((Object)list, callSiteArray[1].callGroovyObjectGetProperty((Object)this));
                return list;
            }

            @Generated
            public Object doCall() {
                CallSite[] callSiteArray = _createCompleters_closure5.$getCallSiteArray();
                return this.doCall(null);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (((Object)((Object)this)).getClass() != _createCompleters_closure5.class) {
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
                stringArray[0] = "addAll";
                stringArray[1] = "functions";
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[2];
                _createCompleters_closure5.$createCallSiteArray_1(stringArray);
                return new CallSiteArray(_createCompleters_closure5.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _createCompleters_closure5.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        _createCompleters_closure5 loader = new _createCompleters_closure5(this, this);
        SimpleCompleter subCommandsCompleter = (SimpleCompleter)ScriptBytecodeAdapter.castToType((Object)callSiteArray[1].callConstructor(SimpleCompleter.class, (Object)loader), SimpleCompleter.class);
        callSiteArray[2].call((Object)subCommandsCompleter, (Object)false);
        return ScriptBytecodeAdapter.createList((Object[])new Object[]{subCommandsCompleter, null});
    }

    @Override
    public Object execute(List<String> args) {
        CallSite[] callSiteArray = HistoryCommand.$getCallSiteArray();
        if (!DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[3].callGroovyObjectGetProperty((Object)this))) {
            callSiteArray[4].callCurrent((GroovyObject)this, (Object)"Shell does not appear to be interactive; Can not query history");
        }
        ScriptBytecodeAdapter.invokeMethodOnSuperN(HistoryCommand.class, (GroovyObject)this, (String)"execute", (Object[])new Object[]{args});
        return null;
    }

    @Override
    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != HistoryCommand.class) {
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
    public Object getDo_show() {
        return this.do_show;
    }

    @Generated
    public void setDo_show(Object object) {
        this.do_show = object;
    }

    @Generated
    public Object getDo_clear() {
        return this.do_clear;
    }

    @Generated
    public void setDo_clear(Object object) {
        this.do_clear = object;
    }

    @Generated
    public Object getDo_flush() {
        return this.do_flush;
    }

    @Generated
    public void setDo_flush(Object object) {
        this.do_flush = object;
    }

    @Generated
    public Object getDo_recall() {
        return this.do_recall;
    }

    @Generated
    public void setDo_recall(Object object) {
        this.do_recall = object;
    }

    public /* synthetic */ Object super$3$execute(List list) {
        return super.execute(list);
    }

    public /* synthetic */ List super$3$createCompleters() {
        return super.createCompleters();
    }

    public /* synthetic */ MetaClass super$3$$getStaticMetaClass() {
        return super.$getStaticMetaClass();
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
        stringArray[0] = "COMMAND_NAME";
        stringArray[1] = "<$constructor$>";
        stringArray[2] = "setWithBlank";
        stringArray[3] = "history";
        stringArray[4] = "fail";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[5];
        HistoryCommand.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(HistoryCommand.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = HistoryCommand.$createCallSiteArray();
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
            Iterator histIt = (Iterator)ScriptBytecodeAdapter.castToType((Object)callSiteArray[0].call(callSiteArray[1].callGroovyObjectGetProperty((Object)this)), Iterator.class);
            while (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[2].call((Object)histIt))) {
                History.Entry next = (History.Entry)ScriptBytecodeAdapter.castToType((Object)callSiteArray[3].call((Object)histIt), History.Entry.class);
                if (!DefaultTypeTransformation.booleanUnbox((Object)next)) continue;
                callSiteArray[4].call(callSiteArray[5].callGetProperty(callSiteArray[6].callGroovyObjectGetProperty((Object)this)), (Object)new GStringImpl(new Object[]{callSiteArray[7].call(callSiteArray[8].call(callSiteArray[9].call((Object)next)), (Object)3, (Object)" "), callSiteArray[10].call((Object)next)}, new String[]{" @|bold ", "|@  ", ""}));
            }
            return null;
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
            stringArray[0] = "iterator";
            stringArray[1] = "history";
            stringArray[2] = "hasNext";
            stringArray[3] = "next";
            stringArray[4] = "println";
            stringArray[5] = "out";
            stringArray[6] = "io";
            stringArray[7] = "padLeft";
            stringArray[8] = "toString";
            stringArray[9] = "index";
            stringArray[10] = "value";
        }

        private static /* synthetic */ CallSiteArray $createCallSiteArray() {
            String[] stringArray = new String[11];
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
            callSiteArray[0].call(callSiteArray[1].callGroovyObjectGetProperty((Object)this));
            if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[2].callGetProperty(callSiteArray[3].callGroovyObjectGetProperty((Object)this)))) {
                return callSiteArray[4].call(callSiteArray[5].callGetProperty(callSiteArray[6].callGroovyObjectGetProperty((Object)this)), (Object)"History cleared");
            }
            return null;
        }

        @Generated
        public Object doCall() {
            CallSite[] callSiteArray = _closure2.$getCallSiteArray();
            return this.doCall(null);
        }

        protected /* synthetic */ MetaClass $getStaticMetaClass() {
            if (((Object)((Object)this)).getClass() != _closure2.class) {
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
            stringArray[0] = "clear";
            stringArray[1] = "history";
            stringArray[2] = "verbose";
            stringArray[3] = "io";
            stringArray[4] = "println";
            stringArray[5] = "out";
            stringArray[6] = "io";
        }

        private static /* synthetic */ CallSiteArray $createCallSiteArray() {
            String[] stringArray = new String[7];
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
            callSiteArray[0].call(callSiteArray[1].callGroovyObjectGetProperty((Object)this));
            if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[2].callGetProperty(callSiteArray[3].callGroovyObjectGetProperty((Object)this)))) {
                return callSiteArray[4].call(callSiteArray[5].callGetProperty(callSiteArray[6].callGroovyObjectGetProperty((Object)this)), (Object)"History flushed");
            }
            return null;
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

        private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
            stringArray[0] = "flush";
            stringArray[1] = "history";
            stringArray[2] = "verbose";
            stringArray[3] = "io";
            stringArray[4] = "println";
            stringArray[5] = "out";
            stringArray[6] = "io";
        }

        private static /* synthetic */ CallSiteArray $createCallSiteArray() {
            String[] stringArray = new String[7];
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

        public Object doCall(Object args) {
            CallSite[] callSiteArray = _closure4.$getCallSiteArray();
            String line = null;
            if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                if (!DefaultTypeTransformation.booleanUnbox((Object)args) || ScriptBytecodeAdapter.compareNotEqual((Object)callSiteArray[0].call((Object)((List)ScriptBytecodeAdapter.castToType((Object)args, List.class))), (Object)1)) {
                    callSiteArray[1].callCurrent((GroovyObject)this, (Object)"History recall requires a single history identifer");
                }
            } else if (!DefaultTypeTransformation.booleanUnbox((Object)args) || ScriptBytecodeAdapter.compareNotEqual((Object)callSiteArray[2].call((Object)((List)ScriptBytecodeAdapter.castToType((Object)args, List.class))), (Object)1)) {
                callSiteArray[3].callCurrent((GroovyObject)this, (Object)"History recall requires a single history identifer");
            }
            String ids = ShortTypeHandling.castToString((Object)callSiteArray[4].call((Object)((List)ScriptBytecodeAdapter.castToType((Object)args, List.class)), (Object)0));
            try {
                int id = DefaultTypeTransformation.intUnbox((Object)callSiteArray[5].call(Integer.class, (Object)ids));
                if (!BytecodeInterface8.isOrigInt() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                    if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[6].callGroovyObjectGetProperty(callSiteArray[7].callGroovyObjectGetProperty((Object)this)))) {
                        int n = id;
                        id = DefaultTypeTransformation.intUnbox((Object)callSiteArray[8].call((Object)n));
                    }
                } else if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[9].callGroovyObjectGetProperty(callSiteArray[10].callGroovyObjectGetProperty((Object)this)))) {
                    int n = id;
                    id = n - 1;
                }
                Iterator listEntryIt = (Iterator)ScriptBytecodeAdapter.castToType((Object)callSiteArray[11].call(callSiteArray[12].callGroovyObjectGetProperty((Object)this)), Iterator.class);
                if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[13].call((Object)listEntryIt))) {
                    History.Entry next = (History.Entry)ScriptBytecodeAdapter.castToType((Object)callSiteArray[14].call((Object)listEntryIt), History.Entry.class);
                    if (ScriptBytecodeAdapter.compareLessThan((Object)id, (Object)callSiteArray[15].call(callSiteArray[16].call((Object)next), (Object)1))) {
                        callSiteArray[17].callCurrent((GroovyObject)this, (Object)new GStringImpl(new Object[]{ids}, new String[]{"Unknown index: ", ""}));
                    } else if (ScriptBytecodeAdapter.compareEqual((Object)id, (Object)callSiteArray[18].call(callSiteArray[19].call((Object)next), (Object)1))) {
                        Object object = callSiteArray[20].callGroovyObjectGetProperty(callSiteArray[21].callGroovyObjectGetProperty((Object)this));
                        line = ShortTypeHandling.castToString((Object)object);
                    } else if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[22].call((Object)next), (Object)id)) {
                        Object object = callSiteArray[23].call((Object)next);
                        line = ShortTypeHandling.castToString((Object)object);
                    } else {
                        while (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[24].call((Object)listEntryIt))) {
                            Object object = callSiteArray[25].call((Object)listEntryIt);
                            next = (History.Entry)ScriptBytecodeAdapter.castToType((Object)object, History.Entry.class);
                            if (!ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[26].call((Object)next), (Object)id)) continue;
                            Object object2 = callSiteArray[27].call((Object)next);
                            line = ShortTypeHandling.castToString((Object)object2);
                        }
                    }
                }
            }
            catch (NumberFormatException e) {
                callSiteArray[28].callCurrent((GroovyObject)this, (Object)new GStringImpl(new Object[]{ids}, new String[]{"Invalid history identifier: ", ""}), (Object)e);
            }
            callSiteArray[29].call(callSiteArray[30].callGroovyObjectGetProperty((Object)this), (Object)new GStringImpl(new Object[]{ids, line}, new String[]{"Recalling history item #", ": ", ""}));
            if (DefaultTypeTransformation.booleanUnbox(line)) {
                return callSiteArray[31].call(callSiteArray[32].callGroovyObjectGetProperty((Object)this), line);
            }
            return null;
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

        private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
            stringArray[0] = "size";
            stringArray[1] = "fail";
            stringArray[2] = "size";
            stringArray[3] = "fail";
            stringArray[4] = "getAt";
            stringArray[5] = "parseInt";
            stringArray[6] = "historyFull";
            stringArray[7] = "shell";
            stringArray[8] = "previous";
            stringArray[9] = "historyFull";
            stringArray[10] = "shell";
            stringArray[11] = "iterator";
            stringArray[12] = "history";
            stringArray[13] = "hasNext";
            stringArray[14] = "next";
            stringArray[15] = "minus";
            stringArray[16] = "index";
            stringArray[17] = "fail";
            stringArray[18] = "minus";
            stringArray[19] = "index";
            stringArray[20] = "evictedLine";
            stringArray[21] = "shell";
            stringArray[22] = "index";
            stringArray[23] = "value";
            stringArray[24] = "hasNext";
            stringArray[25] = "next";
            stringArray[26] = "index";
            stringArray[27] = "value";
            stringArray[28] = "fail";
            stringArray[29] = "debug";
            stringArray[30] = "log";
            stringArray[31] = "execute";
            stringArray[32] = "shell";
        }

        private static /* synthetic */ CallSiteArray $createCallSiteArray() {
            String[] stringArray = new String[33];
            _closure4.$createCallSiteArray_1(stringArray);
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
}

