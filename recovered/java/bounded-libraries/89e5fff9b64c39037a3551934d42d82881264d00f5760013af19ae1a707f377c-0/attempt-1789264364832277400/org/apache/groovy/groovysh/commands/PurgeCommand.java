/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.Closure
 *  groovy.lang.MetaClass
 *  groovy.transform.Generated
 *  org.codehaus.groovy.reflection.ClassInfo
 *  org.codehaus.groovy.runtime.GeneratedClosure
 *  org.codehaus.groovy.runtime.ScriptBytecodeAdapter
 *  org.codehaus.groovy.runtime.callsite.CallSite
 *  org.codehaus.groovy.runtime.callsite.CallSiteArray
 *  org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation
 *  org.codehaus.groovy.runtime.typehandling.ShortTypeHandling
 *  org.codehaus.groovy.tools.shell.util.Preferences
 */
package org.apache.groovy.groovysh.commands;

import groovy.lang.Closure;
import groovy.lang.MetaClass;
import groovy.transform.Generated;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import org.apache.groovy.groovysh.ComplexCommandSupport;
import org.apache.groovy.groovysh.Groovysh;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.GeneratedClosure;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;
import org.codehaus.groovy.runtime.typehandling.ShortTypeHandling;
import org.codehaus.groovy.tools.shell.util.Preferences;

public class PurgeCommand
extends ComplexCommandSupport {
    public static final String COMMAND_NAME = ":purge";
    private Object do_variables;
    private Object do_classes;
    private Object do_imports;
    private Object do_preferences;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private static /* synthetic */ SoftReference $callSiteArray;

    public PurgeCommand(Groovysh shell) {
        CallSite[] callSiteArray = PurgeCommand.$getCallSiteArray();
        super(shell, ShortTypeHandling.castToString((Object)callSiteArray[0].callGetProperty(PurgeCommand.class)), ":p", ScriptBytecodeAdapter.createList((Object[])new Object[]{"variables", "classes", "imports", "preferences", "all"}));
        _closure1 _closure12 = new _closure1(this, this);
        this.do_variables = _closure12;
        _closure2 _closure22 = new _closure2(this, this);
        this.do_classes = _closure22;
        _closure3 _closure32 = new _closure3(this, this);
        this.do_imports = _closure32;
        _closure4 _closure42 = new _closure4(this, this);
        this.do_preferences = _closure42;
    }

    @Override
    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != PurgeCommand.class) {
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
    public Object getDo_variables() {
        return this.do_variables;
    }

    @Generated
    public void setDo_variables(Object object) {
        this.do_variables = object;
    }

    @Generated
    public Object getDo_classes() {
        return this.do_classes;
    }

    @Generated
    public void setDo_classes(Object object) {
        this.do_classes = object;
    }

    @Generated
    public Object getDo_imports() {
        return this.do_imports;
    }

    @Generated
    public void setDo_imports(Object object) {
        this.do_imports = object;
    }

    @Generated
    public Object getDo_preferences() {
        return this.do_preferences;
    }

    @Generated
    public void setDo_preferences(Object object) {
        this.do_preferences = object;
    }

    public /* synthetic */ MetaClass super$3$$getStaticMetaClass() {
        return super.$getStaticMetaClass();
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[1];
        stringArray[0] = "COMMAND_NAME";
        return new CallSiteArray(PurgeCommand.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = PurgeCommand.$createCallSiteArray();
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
            if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[0].call(callSiteArray[1].callGroovyObjectGetProperty((Object)this)))) {
                return callSiteArray[2].call(callSiteArray[3].callGetProperty(callSiteArray[4].callGroovyObjectGetProperty((Object)this)), (Object)"No variables defined");
            }
            callSiteArray[5].call(callSiteArray[6].callGroovyObjectGetProperty((Object)this));
            if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[7].callGetProperty(callSiteArray[8].callGroovyObjectGetProperty((Object)this)))) {
                return callSiteArray[9].call(callSiteArray[10].callGetProperty(callSiteArray[11].callGroovyObjectGetProperty((Object)this)), (Object)"Custom variables purged");
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
            stringArray[0] = "isEmpty";
            stringArray[1] = "variables";
            stringArray[2] = "println";
            stringArray[3] = "out";
            stringArray[4] = "io";
            stringArray[5] = "clear";
            stringArray[6] = "variables";
            stringArray[7] = "verbose";
            stringArray[8] = "io";
            stringArray[9] = "println";
            stringArray[10] = "out";
            stringArray[11] = "io";
        }

        private static /* synthetic */ CallSiteArray $createCallSiteArray() {
            String[] stringArray = new String[12];
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
            if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[0].call(callSiteArray[1].callGetProperty(callSiteArray[2].callGroovyObjectGetProperty((Object)this))), (Object)0)) {
                return callSiteArray[3].call(callSiteArray[4].callGetProperty(callSiteArray[5].callGroovyObjectGetProperty((Object)this)), (Object)"No classes have been loaded");
            }
            callSiteArray[6].call(callSiteArray[7].callGroovyObjectGetProperty((Object)this));
            if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[8].callGetProperty(callSiteArray[9].callGroovyObjectGetProperty((Object)this)))) {
                return callSiteArray[10].call(callSiteArray[11].callGetProperty(callSiteArray[12].callGroovyObjectGetProperty((Object)this)), (Object)"Loaded classes purged");
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
            stringArray[0] = "size";
            stringArray[1] = "loadedClasses";
            stringArray[2] = "classLoader";
            stringArray[3] = "println";
            stringArray[4] = "out";
            stringArray[5] = "io";
            stringArray[6] = "clearCache";
            stringArray[7] = "classLoader";
            stringArray[8] = "verbose";
            stringArray[9] = "io";
            stringArray[10] = "println";
            stringArray[11] = "out";
            stringArray[12] = "io";
        }

        private static /* synthetic */ CallSiteArray $createCallSiteArray() {
            String[] stringArray = new String[13];
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
            if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[0].call(callSiteArray[1].callGroovyObjectGetProperty((Object)this)))) {
                return callSiteArray[2].call(callSiteArray[3].callGetProperty(callSiteArray[4].callGroovyObjectGetProperty((Object)this)), (Object)"No custom imports have been defined");
            }
            callSiteArray[5].call(callSiteArray[6].callGroovyObjectGetProperty((Object)this));
            if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[7].callGetProperty(callSiteArray[8].callGroovyObjectGetProperty((Object)this)))) {
                return callSiteArray[9].call(callSiteArray[10].callGetProperty(callSiteArray[11].callGroovyObjectGetProperty((Object)this)), (Object)"Custom imports purged");
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
            stringArray[0] = "isEmpty";
            stringArray[1] = "imports";
            stringArray[2] = "println";
            stringArray[3] = "out";
            stringArray[4] = "io";
            stringArray[5] = "clear";
            stringArray[6] = "imports";
            stringArray[7] = "verbose";
            stringArray[8] = "io";
            stringArray[9] = "println";
            stringArray[10] = "out";
            stringArray[11] = "io";
        }

        private static /* synthetic */ CallSiteArray $createCallSiteArray() {
            String[] stringArray = new String[12];
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
            callSiteArray[0].call(Preferences.class);
            if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[1].callGetProperty(callSiteArray[2].callGroovyObjectGetProperty((Object)this)))) {
                return callSiteArray[3].call(callSiteArray[4].callGetProperty(callSiteArray[5].callGroovyObjectGetProperty((Object)this)), (Object)"Preferences purged");
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
            stringArray[0] = "clear";
            stringArray[1] = "verbose";
            stringArray[2] = "io";
            stringArray[3] = "println";
            stringArray[4] = "out";
            stringArray[5] = "io";
        }

        private static /* synthetic */ CallSiteArray $createCallSiteArray() {
            String[] stringArray = new String[6];
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

