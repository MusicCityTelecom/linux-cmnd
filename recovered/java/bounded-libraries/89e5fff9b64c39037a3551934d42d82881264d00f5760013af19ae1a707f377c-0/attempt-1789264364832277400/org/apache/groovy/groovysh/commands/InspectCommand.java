/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.console.ui.ObjectBrowser
 *  groovy.lang.GroovyObject
 *  groovy.lang.MetaClass
 *  groovy.transform.Generated
 *  jline.console.completer.Completer
 *  org.codehaus.groovy.reflection.ClassInfo
 *  org.codehaus.groovy.runtime.GStringImpl
 *  org.codehaus.groovy.runtime.ScriptBytecodeAdapter
 *  org.codehaus.groovy.runtime.callsite.CallSite
 *  org.codehaus.groovy.runtime.callsite.CallSiteArray
 *  org.codehaus.groovy.runtime.powerassert.AssertionRenderer
 *  org.codehaus.groovy.runtime.powerassert.ValueRecorder
 *  org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation
 *  org.codehaus.groovy.runtime.typehandling.ShortTypeHandling
 */
package org.apache.groovy.groovysh.commands;

import groovy.console.ui.ObjectBrowser;
import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import groovy.transform.Generated;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import java.util.List;
import javax.swing.UIManager;
import jline.console.completer.Completer;
import org.apache.groovy.groovysh.CommandSupport;
import org.apache.groovy.groovysh.Groovysh;
import org.apache.groovy.groovysh.commands.InspectCommandCompleter;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.GStringImpl;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.powerassert.AssertionRenderer;
import org.codehaus.groovy.runtime.powerassert.ValueRecorder;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;
import org.codehaus.groovy.runtime.typehandling.ShortTypeHandling;

public class InspectCommand
extends CommandSupport {
    public static final String COMMAND_NAME = ":inspect";
    private Object lafInitialized;
    private Object headless;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private static /* synthetic */ SoftReference $callSiteArray;

    public InspectCommand(Groovysh shell) {
        CallSite[] callSiteArray = InspectCommand.$getCallSiteArray();
        super(shell, ShortTypeHandling.castToString((Object)callSiteArray[0].callGetProperty(InspectCommand.class)), ":n");
        boolean bl = false;
        this.lafInitialized = bl;
    }

    @Override
    protected List<Completer> createCompleters() {
        CallSite[] callSiteArray = InspectCommand.$getCallSiteArray();
        return ScriptBytecodeAdapter.createList((Object[])new Object[]{callSiteArray[1].callConstructor(InspectCommandCompleter.class, callSiteArray[2].callGroovyObjectGetProperty((Object)this)), null});
    }

    @Override
    public Object execute(List<String> args) {
        Object object;
        Object object2;
        CallSite[] callSiteArray = InspectCommand.$getCallSiteArray();
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
        callSiteArray[3].call(callSiteArray[4].callGroovyObjectGetProperty((Object)this), (Object)new GStringImpl(new Object[]{args}, new String[]{"Inspecting w/args: ", ""}));
        if (ScriptBytecodeAdapter.compareGreaterThan((Object)callSiteArray[5].call(args), (Object)1)) {
            callSiteArray[6].callCurrent((GroovyObject)this, callSiteArray[7].call(callSiteArray[8].callGroovyObjectGetProperty((Object)this), (Object)"error.unexpected_args", callSiteArray[9].call(args, (Object)" ")));
        }
        Object subject = null;
        subject = ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[10].call(args), (Object)1) ? (object2 = callSiteArray[11].call(callSiteArray[12].callGetProperty(callSiteArray[13].callGroovyObjectGetProperty((Object)this)), callSiteArray[14].call(args, (Object)0))) : (object = callSiteArray[15].call(callSiteArray[16].callGetProperty(callSiteArray[17].callGroovyObjectGetProperty((Object)this)), (Object)"_"));
        if (!DefaultTypeTransformation.booleanUnbox((Object)subject)) {
            return callSiteArray[18].call(callSiteArray[19].callGetProperty(callSiteArray[20].callGroovyObjectGetProperty((Object)this)), (Object)"Subject is null, false or empty; nothing to inspect");
        }
        if (!DefaultTypeTransformation.booleanUnbox((Object)this.lafInitialized)) {
            boolean bl = true;
            this.lafInitialized = bl;
            try {
                callSiteArray[21].call(UIManager.class, callSiteArray[22].callGetProperty(UIManager.class));
                callSiteArray[23].call(callSiteArray[24].callConstructor(Frame.class));
                boolean bl2 = false;
                this.headless = bl2;
            }
            catch (HeadlessException he) {
                boolean bl3 = true;
                this.headless = bl3;
            }
        }
        if (DefaultTypeTransformation.booleanUnbox((Object)this.headless)) {
            callSiteArray[25].call(callSiteArray[26].callGetProperty(callSiteArray[27].callGroovyObjectGetProperty((Object)this)), (Object)"@|red ERROR:|@ Running in AWT Headless mode, 'inspect' is not available.");
            return null;
        }
        if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[28].callGetProperty(callSiteArray[29].callGroovyObjectGetProperty((Object)this)))) {
            callSiteArray[30].call(callSiteArray[31].callGetProperty(callSiteArray[32].callGroovyObjectGetProperty((Object)this)), (Object)new GStringImpl(new Object[]{subject}, new String[]{"Launching object browser to inspect: ", ""}));
        }
        return callSiteArray[33].call(ObjectBrowser.class, subject);
    }

    @Override
    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != InspectCommand.class) {
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
    public Object getLafInitialized() {
        return this.lafInitialized;
    }

    @Generated
    public void setLafInitialized(Object object) {
        this.lafInitialized = object;
    }

    @Generated
    public Object getHeadless() {
        return this.headless;
    }

    @Generated
    public void setHeadless(Object object) {
        this.headless = object;
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
        stringArray[2] = "binding";
        stringArray[3] = "debug";
        stringArray[4] = "log";
        stringArray[5] = "size";
        stringArray[6] = "fail";
        stringArray[7] = "format";
        stringArray[8] = "messages";
        stringArray[9] = "join";
        stringArray[10] = "size";
        stringArray[11] = "getAt";
        stringArray[12] = "variables";
        stringArray[13] = "binding";
        stringArray[14] = "getAt";
        stringArray[15] = "getAt";
        stringArray[16] = "variables";
        stringArray[17] = "binding";
        stringArray[18] = "println";
        stringArray[19] = "out";
        stringArray[20] = "io";
        stringArray[21] = "setLookAndFeel";
        stringArray[22] = "systemLookAndFeelClassName";
        stringArray[23] = "dispose";
        stringArray[24] = "<$constructor$>";
        stringArray[25] = "println";
        stringArray[26] = "err";
        stringArray[27] = "io";
        stringArray[28] = "verbose";
        stringArray[29] = "io";
        stringArray[30] = "println";
        stringArray[31] = "out";
        stringArray[32] = "io";
        stringArray[33] = "inspect";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[34];
        InspectCommand.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(InspectCommand.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = InspectCommand.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }
}

