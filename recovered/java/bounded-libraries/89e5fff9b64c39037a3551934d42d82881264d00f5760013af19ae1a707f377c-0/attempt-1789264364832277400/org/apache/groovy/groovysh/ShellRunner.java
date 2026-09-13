/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.Closure
 *  groovy.lang.GroovyObject
 *  groovy.lang.MetaClass
 *  groovy.transform.Generated
 *  groovy.transform.Internal
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
 *  org.codehaus.groovy.tools.shell.util.Logger
 */
package org.apache.groovy.groovysh;

import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import groovy.transform.Generated;
import groovy.transform.Internal;
import java.beans.Transient;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import org.apache.groovy.groovysh.ExitNotification;
import org.apache.groovy.groovysh.Shell;
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
import org.codehaus.groovy.tools.shell.util.Logger;

public abstract class ShellRunner
implements Runnable,
GroovyObject {
    protected final Logger log;
    private final Shell shell;
    private boolean running;
    private boolean breakOnNull;
    private Closure errorHandler;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private transient /* synthetic */ MetaClass metaClass;
    private static /* synthetic */ SoftReference $callSiteArray;

    protected ShellRunner(Shell shell) {
        Shell shell2;
        MetaClass metaClass;
        boolean bl;
        boolean bl2;
        CallSite[] callSiteArray = ShellRunner.$getCallSiteArray();
        Object object = callSiteArray[0].call(Logger.class, callSiteArray[1].callGroovyObjectGetProperty((Object)this));
        this.log = (Logger)ScriptBytecodeAdapter.castToType((Object)object, Logger.class);
        this.running = bl2 = false;
        this.breakOnNull = bl = true;
        _closure1 _closure12 = new _closure1(this, this);
        this.errorHandler = _closure12;
        this.metaClass = metaClass = this.$getStaticMetaClass();
        ValueRecorder valueRecorder = new ValueRecorder();
        try {
            Shell shell3 = shell;
            valueRecorder.record((Object)shell3, 8);
            boolean bl3 = ScriptBytecodeAdapter.compareNotEqual((Object)shell3, null);
            valueRecorder.record((Object)bl3, 14);
            if (bl3) {
                valueRecorder.clear();
            } else {
                ScriptBytecodeAdapter.assertFailed((Object)AssertionRenderer.render((String)"assert(shell != null)", (ValueRecorder)valueRecorder), null);
            }
        }
        catch (Throwable throwable) {
            valueRecorder.clear();
            throw throwable;
        }
        this.shell = shell2 = shell;
    }

    @Override
    public void run() {
        boolean bl;
        CallSite[] callSiteArray = ShellRunner.$getCallSiteArray();
        callSiteArray[2].call((Object)this.log, (Object)"Running");
        this.running = bl = true;
        while (this.running) {
            try {
                Object object = callSiteArray[3].callCurrent((GroovyObject)this);
                this.running = DefaultTypeTransformation.booleanUnbox((Object)object);
            }
            catch (ExitNotification n) {
                throw (Throwable)n;
            }
            catch (Throwable t) {
                callSiteArray[4].call((Object)this.log, (Object)new GStringImpl(new Object[]{t}, new String[]{"Work failed: ", ""}), (Object)t);
                if (!DefaultTypeTransformation.booleanUnbox((Object)this.errorHandler)) continue;
                try {
                    callSiteArray[5].call((Object)this.errorHandler, (Object)t);
                }
                catch (Throwable t2) {
                    ScriptBytecodeAdapter.invokeClosure((Object)this.errorHandler, (Object[])new Object[]{callSiteArray[6].callConstructor(IllegalArgumentException.class, (Object)new GStringImpl(new Object[]{callSiteArray[7].callGetProperty((Object)t)}, new String[]{"Error when handling error: ", ""}))});
                    callSiteArray[8].call((Object)this.errorHandler, (Object)t2);
                }
            }
        }
        callSiteArray[9].call((Object)this.log, (Object)"Finished");
    }

    protected boolean work() {
        CallSite[] callSiteArray = ShellRunner.$getCallSiteArray();
        Object line = null;
        if (__$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            Object object;
            line = object = callSiteArray[10].callCurrent((GroovyObject)this);
        } else {
            String string = this.readLine();
            line = string;
        }
        if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[11].callGetProperty((Object)this.log))) {
            callSiteArray[12].call((Object)this.log, (Object)new GStringImpl(new Object[]{line}, new String[]{"Read line: ", ""}));
        }
        if (!BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass() ? ScriptBytecodeAdapter.compareEqual((Object)line, null) && this.breakOnNull : ScriptBytecodeAdapter.compareEqual((Object)line, null) && this.breakOnNull) {
            return false;
        }
        if (ScriptBytecodeAdapter.compareGreaterThan((Object)callSiteArray[13].call(callSiteArray[14].call(line)), (Object)0)) {
            callSiteArray[15].call((Object)this.shell, line);
        }
        return true;
    }

    protected abstract String readLine();

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != ShellRunner.class) {
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
    public final Shell getShell() {
        return this.shell;
    }

    @Generated
    public boolean getRunning() {
        return this.running;
    }

    @Generated
    public boolean isRunning() {
        return this.running;
    }

    @Generated
    public void setRunning(boolean bl) {
        this.running = bl;
    }

    @Generated
    public boolean getBreakOnNull() {
        return this.breakOnNull;
    }

    @Generated
    public boolean isBreakOnNull() {
        return this.breakOnNull;
    }

    @Generated
    public void setBreakOnNull(boolean bl) {
        this.breakOnNull = bl;
    }

    @Generated
    public Closure getErrorHandler() {
        return this.errorHandler;
    }

    @Generated
    public void setErrorHandler(Closure closure) {
        this.errorHandler = closure;
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
        stringArray[0] = "create";
        stringArray[1] = "class";
        stringArray[2] = "debug";
        stringArray[3] = "work";
        stringArray[4] = "debug";
        stringArray[5] = "call";
        stringArray[6] = "<$constructor$>";
        stringArray[7] = "message";
        stringArray[8] = "call";
        stringArray[9] = "debug";
        stringArray[10] = "readLine";
        stringArray[11] = "debugEnabled";
        stringArray[12] = "debug";
        stringArray[13] = "size";
        stringArray[14] = "trim";
        stringArray[15] = "leftShift";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[16];
        ShellRunner.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(ShellRunner.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = ShellRunner.$createCallSiteArray();
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

        public Object doCall(Object e) {
            CallSite[] callSiteArray = _closure1.$getCallSiteArray();
            callSiteArray[0].call(callSiteArray[1].callGroovyObjectGetProperty((Object)this), e);
            boolean bl = false;
            ScriptBytecodeAdapter.setGroovyObjectProperty((Object)bl, _closure1.class, (GroovyObject)this, (String)"running");
            return bl;
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
            stringArray[0] = "debug";
            stringArray[1] = "log";
        }

        private static /* synthetic */ CallSiteArray $createCallSiteArray() {
            String[] stringArray = new String[2];
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

