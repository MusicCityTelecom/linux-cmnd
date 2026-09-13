/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.Closure
 *  groovy.lang.GroovyObject
 *  groovy.lang.MetaClass
 *  groovy.transform.Generated
 *  org.codehaus.groovy.reflection.ClassInfo
 *  org.codehaus.groovy.runtime.BytecodeInterface8
 *  org.codehaus.groovy.runtime.FormatHelper
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
package org.apache.groovy.groovysh.commands;

import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import groovy.transform.Generated;
import java.io.File;
import java.io.PrintWriter;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import java.util.Date;
import java.util.List;
import org.apache.groovy.groovysh.ComplexCommandSupport;
import org.apache.groovy.groovysh.Groovysh;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.BytecodeInterface8;
import org.codehaus.groovy.runtime.FormatHelper;
import org.codehaus.groovy.runtime.GStringImpl;
import org.codehaus.groovy.runtime.GeneratedClosure;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.powerassert.AssertionRenderer;
import org.codehaus.groovy.runtime.powerassert.ValueRecorder;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;
import org.codehaus.groovy.runtime.typehandling.ShortTypeHandling;

public class RecordCommand
extends ComplexCommandSupport {
    public static final String COMMAND_NAME = ":record";
    private File file;
    private PrintWriter writer;
    private Object do_start;
    private Object do_stop;
    private Object do_status;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private static /* synthetic */ SoftReference $callSiteArray;

    public RecordCommand(Groovysh shell) {
        CallSite[] callSiteArray = RecordCommand.$getCallSiteArray();
        super(shell, ShortTypeHandling.castToString((Object)callSiteArray[0].callGetProperty(RecordCommand.class)), ":r", ScriptBytecodeAdapter.createList((Object[])new Object[]{"start", "stop", "status"}), "status");
        _closure1 _closure12 = new _closure1(this, this);
        this.do_start = _closure12;
        _closure2 _closure22 = new _closure2(this, this);
        this.do_stop = _closure22;
        _closure3 _closure32 = new _closure3(this, this);
        this.do_status = _closure32;
        callSiteArray[1].callCurrent((GroovyObject)this, (Object)new _closure4(this, this));
    }

    public boolean isRecording() {
        CallSite[] callSiteArray = RecordCommand.$getCallSiteArray();
        return ScriptBytecodeAdapter.compareNotEqual((Object)this.file, null);
    }

    public Object recordInput(String line) {
        CallSite[] callSiteArray = RecordCommand.$getCallSiteArray();
        if (!BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            ValueRecorder valueRecorder = new ValueRecorder();
            try {
                String string = line;
                valueRecorder.record((Object)string, 8);
                boolean bl = ScriptBytecodeAdapter.compareNotEqual((Object)string, null);
                valueRecorder.record((Object)bl, 13);
                if (bl) {
                    valueRecorder.clear();
                } else {
                    ScriptBytecodeAdapter.assertFailed((Object)AssertionRenderer.render((String)"assert line != null", (ValueRecorder)valueRecorder), null);
                }
            }
            catch (Throwable throwable) {
                valueRecorder.clear();
                throw throwable;
            }
            if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[2].callCurrent((GroovyObject)this))) {
                callSiteArray[3].call((Object)this.writer, (Object)line);
                return callSiteArray[4].call((Object)this.writer);
            }
        } else {
            ValueRecorder valueRecorder = new ValueRecorder();
            try {
                String string = line;
                valueRecorder.record((Object)string, 8);
                boolean bl = ScriptBytecodeAdapter.compareNotEqual((Object)string, null);
                valueRecorder.record((Object)bl, 13);
                if (bl) {
                    valueRecorder.clear();
                } else {
                    ScriptBytecodeAdapter.assertFailed((Object)AssertionRenderer.render((String)"assert line != null", (ValueRecorder)valueRecorder), null);
                }
            }
            catch (Throwable throwable) {
                valueRecorder.clear();
                throw throwable;
            }
            if (this.isRecording()) {
                callSiteArray[5].call((Object)this.writer, (Object)line);
                return callSiteArray[6].call((Object)this.writer);
            }
        }
        return null;
    }

    public Object recordResult(Object result) {
        CallSite[] callSiteArray = RecordCommand.$getCallSiteArray();
        if (__$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[7].callCurrent((GroovyObject)this))) {
                callSiteArray[8].call((Object)this.writer, (Object)new GStringImpl(new Object[]{callSiteArray[9].call(FormatHelper.class, result)}, new String[]{"// RESULT: ", ""}));
                return callSiteArray[10].call((Object)this.writer);
            }
        } else if (this.isRecording()) {
            callSiteArray[11].call((Object)this.writer, (Object)new GStringImpl(new Object[]{callSiteArray[12].call(FormatHelper.class, result)}, new String[]{"// RESULT: ", ""}));
            return callSiteArray[13].call((Object)this.writer);
        }
        return null;
    }

    public Object recordError(Throwable cause) {
        public final class _recordError_closure5
        extends Closure
        implements GeneratedClosure {
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _recordError_closure5(Object _outerInstance, Object _thisObject) {
                CallSite[] callSiteArray = _recordError_closure5.$getCallSiteArray();
                super(_outerInstance, _thisObject);
            }

            public Object doCall(Object it) {
                CallSite[] callSiteArray = _recordError_closure5.$getCallSiteArray();
                return callSiteArray[0].call(callSiteArray[1].callGroovyObjectGetProperty((Object)this), (Object)new GStringImpl(new Object[]{it}, new String[]{"//    ", ""}));
            }

            @Generated
            public Object doCall() {
                CallSite[] callSiteArray = _recordError_closure5.$getCallSiteArray();
                return this.doCall(null);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (((Object)((Object)this)).getClass() != _recordError_closure5.class) {
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
                stringArray[0] = "println";
                stringArray[1] = "writer";
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[2];
                _recordError_closure5.$createCallSiteArray_1(stringArray);
                return new CallSiteArray(_recordError_closure5.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _recordError_closure5.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        CallSite[] callSiteArray = RecordCommand.$getCallSiteArray();
        if (!BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            ValueRecorder valueRecorder = new ValueRecorder();
            try {
                Throwable throwable = cause;
                valueRecorder.record((Object)throwable, 8);
                boolean bl = ScriptBytecodeAdapter.compareNotEqual((Object)throwable, null);
                valueRecorder.record((Object)bl, 14);
                if (bl) {
                    valueRecorder.clear();
                } else {
                    ScriptBytecodeAdapter.assertFailed((Object)AssertionRenderer.render((String)"assert cause != null", (ValueRecorder)valueRecorder), null);
                }
            }
            catch (Throwable throwable) {
                valueRecorder.clear();
                throw throwable;
            }
            if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[14].callCurrent((GroovyObject)this))) {
                callSiteArray[15].call((Object)this.writer, (Object)new GStringImpl(new Object[]{cause}, new String[]{"// ERROR: ", ""}));
                callSiteArray[16].call(callSiteArray[17].callGetProperty((Object)cause), (Object)new _recordError_closure5(this, this));
                return callSiteArray[18].call((Object)this.writer);
            }
        } else {
            ValueRecorder valueRecorder = new ValueRecorder();
            try {
                Throwable throwable = cause;
                valueRecorder.record((Object)throwable, 8);
                boolean bl = ScriptBytecodeAdapter.compareNotEqual((Object)throwable, null);
                valueRecorder.record((Object)bl, 14);
                if (bl) {
                    valueRecorder.clear();
                } else {
                    ScriptBytecodeAdapter.assertFailed((Object)AssertionRenderer.render((String)"assert cause != null", (ValueRecorder)valueRecorder), null);
                }
            }
            catch (Throwable throwable) {
                valueRecorder.clear();
                throw throwable;
            }
            if (this.isRecording()) {
                callSiteArray[19].call((Object)this.writer, (Object)new GStringImpl(new Object[]{cause}, new String[]{"// ERROR: ", ""}));
                callSiteArray[20].call(callSiteArray[21].callGetProperty((Object)cause), (Object)new _recordError_closure5(this, this));
                return callSiteArray[22].call((Object)this.writer);
            }
        }
        return null;
    }

    @Override
    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != RecordCommand.class) {
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
    public Object getDo_start() {
        return this.do_start;
    }

    @Generated
    public void setDo_start(Object object) {
        this.do_start = object;
    }

    @Generated
    public Object getDo_stop() {
        return this.do_stop;
    }

    @Generated
    public void setDo_stop(Object object) {
        this.do_stop = object;
    }

    @Generated
    public Object getDo_status() {
        return this.do_status;
    }

    @Generated
    public void setDo_status(Object object) {
        this.do_status = object;
    }

    public /* synthetic */ MetaClass super$3$$getStaticMetaClass() {
        return super.$getStaticMetaClass();
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
        stringArray[0] = "COMMAND_NAME";
        stringArray[1] = "addShutdownHook";
        stringArray[2] = "isRecording";
        stringArray[3] = "println";
        stringArray[4] = "flush";
        stringArray[5] = "println";
        stringArray[6] = "flush";
        stringArray[7] = "isRecording";
        stringArray[8] = "println";
        stringArray[9] = "toString";
        stringArray[10] = "flush";
        stringArray[11] = "println";
        stringArray[12] = "toString";
        stringArray[13] = "flush";
        stringArray[14] = "isRecording";
        stringArray[15] = "println";
        stringArray[16] = "each";
        stringArray[17] = "stackTrace";
        stringArray[18] = "flush";
        stringArray[19] = "println";
        stringArray[20] = "each";
        stringArray[21] = "stackTrace";
        stringArray[22] = "flush";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[23];
        RecordCommand.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(RecordCommand.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = RecordCommand.$createCallSiteArray();
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

        public Object doCall(List<String> args) {
            CallSite[] callSiteArray = _closure1.$getCallSiteArray();
            if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[0].callCurrent((GroovyObject)this))) {
                callSiteArray[1].callCurrent((GroovyObject)this, (Object)new GStringImpl(new Object[]{callSiteArray[2].callGroovyObjectGetProperty((Object)this)}, new String[]{"Already recording to: \"", "\""}));
            }
            if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[3].call(args), (Object)0)) {
                Object object = callSiteArray[4].call(File.class, (Object)"groovysh-", (Object)".txt");
                ScriptBytecodeAdapter.setGroovyObjectProperty((Object)object, _closure1.class, (GroovyObject)this, (String)"file");
            } else if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[5].call(args), (Object)1)) {
                Object object = callSiteArray[6].callConstructor(File.class, (Object)ScriptBytecodeAdapter.createPojoWrapper((Object)((String)ScriptBytecodeAdapter.asType((Object)callSiteArray[7].call(args, (Object)0), String.class)), String.class));
                ScriptBytecodeAdapter.setGroovyObjectProperty((Object)object, _closure1.class, (GroovyObject)this, (String)"file");
            } else {
                callSiteArray[8].callCurrent((GroovyObject)this, (Object)"Too many arguments. Usage: record start [filename]");
            }
            if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[9].callGetProperty(callSiteArray[10].callGroovyObjectGetProperty((Object)this)))) {
                callSiteArray[11].call(callSiteArray[12].callGetProperty(callSiteArray[13].callGroovyObjectGetProperty((Object)this)));
            }
            Object object = callSiteArray[14].call(callSiteArray[15].callGroovyObjectGetProperty((Object)this));
            ScriptBytecodeAdapter.setGroovyObjectProperty((Object)object, _closure1.class, (GroovyObject)this, (String)"writer");
            callSiteArray[16].call(callSiteArray[17].callGroovyObjectGetProperty((Object)this), callSiteArray[18].call((Object)"// OPENED: ", callSiteArray[19].callConstructor(Date.class)));
            callSiteArray[20].call(callSiteArray[21].callGroovyObjectGetProperty((Object)this));
            callSiteArray[22].call(callSiteArray[23].callGetProperty(callSiteArray[24].callGroovyObjectGetProperty((Object)this)), (Object)new GStringImpl(new Object[]{callSiteArray[25].callGroovyObjectGetProperty((Object)this)}, new String[]{"Recording session to: \"", "\""}));
            return callSiteArray[26].callGroovyObjectGetProperty((Object)this);
        }

        @Generated
        public Object call(List<String> args) {
            CallSite[] callSiteArray = _closure1.$getCallSiteArray();
            return callSiteArray[27].callCurrent((GroovyObject)this, args);
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
            stringArray[0] = "isRecording";
            stringArray[1] = "fail";
            stringArray[2] = "file";
            stringArray[3] = "size";
            stringArray[4] = "createTempFile";
            stringArray[5] = "size";
            stringArray[6] = "<$constructor$>";
            stringArray[7] = "getAt";
            stringArray[8] = "fail";
            stringArray[9] = "parentFile";
            stringArray[10] = "file";
            stringArray[11] = "mkdirs";
            stringArray[12] = "parentFile";
            stringArray[13] = "file";
            stringArray[14] = "newPrintWriter";
            stringArray[15] = "file";
            stringArray[16] = "println";
            stringArray[17] = "writer";
            stringArray[18] = "plus";
            stringArray[19] = "<$constructor$>";
            stringArray[20] = "flush";
            stringArray[21] = "writer";
            stringArray[22] = "println";
            stringArray[23] = "out";
            stringArray[24] = "io";
            stringArray[25] = "file";
            stringArray[26] = "file";
            stringArray[27] = "doCall";
        }

        private static /* synthetic */ CallSiteArray $createCallSiteArray() {
            String[] stringArray = new String[28];
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
            if (!DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[0].callCurrent((GroovyObject)this))) {
                callSiteArray[1].callCurrent((GroovyObject)this, (Object)"Not recording");
            }
            callSiteArray[2].call(callSiteArray[3].callGroovyObjectGetProperty((Object)this), callSiteArray[4].call((Object)"// CLOSED: ", callSiteArray[5].callConstructor(Date.class)));
            callSiteArray[6].call(callSiteArray[7].callGroovyObjectGetProperty((Object)this));
            callSiteArray[8].call(callSiteArray[9].callGroovyObjectGetProperty((Object)this));
            Object var3_3 = null;
            ScriptBytecodeAdapter.setGroovyObjectProperty(var3_3, _closure2.class, (GroovyObject)this, (String)"writer");
            callSiteArray[10].call(callSiteArray[11].callGetProperty(callSiteArray[12].callGroovyObjectGetProperty((Object)this)), (Object)new GStringImpl(new Object[]{callSiteArray[13].callGroovyObjectGetProperty((Object)this), callSiteArray[14].call(callSiteArray[15].callGroovyObjectGetProperty((Object)this))}, new String[]{"Recording stopped; session saved as: \"", "\" (", " bytes)"}));
            Object tmp = callSiteArray[16].callGroovyObjectGetProperty((Object)this);
            Object var5_5 = null;
            ScriptBytecodeAdapter.setGroovyObjectProperty(var5_5, _closure2.class, (GroovyObject)this, (String)"file");
            return tmp;
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
            stringArray[0] = "isRecording";
            stringArray[1] = "fail";
            stringArray[2] = "println";
            stringArray[3] = "writer";
            stringArray[4] = "plus";
            stringArray[5] = "<$constructor$>";
            stringArray[6] = "flush";
            stringArray[7] = "writer";
            stringArray[8] = "close";
            stringArray[9] = "writer";
            stringArray[10] = "println";
            stringArray[11] = "out";
            stringArray[12] = "io";
            stringArray[13] = "file";
            stringArray[14] = "length";
            stringArray[15] = "file";
            stringArray[16] = "file";
        }

        private static /* synthetic */ CallSiteArray $createCallSiteArray() {
            String[] stringArray = new String[17];
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
            if (!DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[0].callCurrent((GroovyObject)this))) {
                callSiteArray[1].call(callSiteArray[2].callGetProperty(callSiteArray[3].callGroovyObjectGetProperty((Object)this)), (Object)"Not recording");
                return null;
            }
            callSiteArray[4].call(callSiteArray[5].callGetProperty(callSiteArray[6].callGroovyObjectGetProperty((Object)this)), (Object)new GStringImpl(new Object[]{callSiteArray[7].callGroovyObjectGetProperty((Object)this), callSiteArray[8].call(callSiteArray[9].callGroovyObjectGetProperty((Object)this))}, new String[]{"Recording to file: \"", "\" (", " bytes)"}));
            return callSiteArray[10].callGroovyObjectGetProperty((Object)this);
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
            stringArray[0] = "isRecording";
            stringArray[1] = "println";
            stringArray[2] = "out";
            stringArray[3] = "io";
            stringArray[4] = "println";
            stringArray[5] = "out";
            stringArray[6] = "io";
            stringArray[7] = "file";
            stringArray[8] = "length";
            stringArray[9] = "file";
            stringArray[10] = "file";
        }

        private static /* synthetic */ CallSiteArray $createCallSiteArray() {
            String[] stringArray = new String[11];
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

        public Object doCall(Object it) {
            CallSite[] callSiteArray = _closure4.$getCallSiteArray();
            if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[0].callCurrent((GroovyObject)this))) {
                return callSiteArray[1].callCurrent((GroovyObject)this.getThisObject());
            }
            return null;
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

        private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
            stringArray[0] = "isRecording";
            stringArray[1] = "do_stop";
        }

        private static /* synthetic */ CallSiteArray $createCallSiteArray() {
            String[] stringArray = new String[2];
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

