/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.GroovyObject
 *  groovy.lang.MetaClass
 *  jline.console.completer.Completer
 *  jline.console.completer.FileNameCompleter
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

import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import java.io.File;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import java.util.List;
import jline.console.completer.Completer;
import jline.console.completer.FileNameCompleter;
import org.apache.groovy.groovysh.CommandSupport;
import org.apache.groovy.groovysh.Groovysh;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.GStringImpl;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.powerassert.AssertionRenderer;
import org.codehaus.groovy.runtime.powerassert.ValueRecorder;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;
import org.codehaus.groovy.runtime.typehandling.ShortTypeHandling;

public class SaveCommand
extends CommandSupport {
    public static final String COMMAND_NAME = ":save";
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private static /* synthetic */ SoftReference $callSiteArray;

    public SaveCommand(Groovysh shell) {
        CallSite[] callSiteArray = SaveCommand.$getCallSiteArray();
        super(shell, ShortTypeHandling.castToString((Object)callSiteArray[0].callGetProperty(SaveCommand.class)), ":s");
    }

    @Override
    protected List<Completer> createCompleters() {
        CallSite[] callSiteArray = SaveCommand.$getCallSiteArray();
        return ScriptBytecodeAdapter.createList((Object[])new Object[]{callSiteArray[1].callConstructor(FileNameCompleter.class), null});
    }

    @Override
    public Object execute(List<String> args) {
        CallSite[] callSiteArray = SaveCommand.$getCallSiteArray();
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
        if (ScriptBytecodeAdapter.compareNotEqual((Object)callSiteArray[2].call(args), (Object)1)) {
            callSiteArray[3].callCurrent((GroovyObject)this, (Object)new GStringImpl(new Object[]{COMMAND_NAME}, new String[]{"Command '", "' requires a single file argument"}));
        }
        if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[4].call(callSiteArray[5].callGroovyObjectGetProperty((Object)this)))) {
            callSiteArray[6].call(callSiteArray[7].callGetProperty(callSiteArray[8].callGroovyObjectGetProperty((Object)this)), (Object)"Buffer is empty");
            return null;
        }
        Object file = callSiteArray[9].callConstructor(File.class, (Object)new GStringImpl(new Object[]{callSiteArray[10].call(args, (Object)0)}, new String[]{"", ""}));
        if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[11].callGetProperty(callSiteArray[12].callGroovyObjectGetProperty((Object)this)))) {
            callSiteArray[13].call(callSiteArray[14].callGetProperty(callSiteArray[15].callGroovyObjectGetProperty((Object)this)), (Object)new GStringImpl(new Object[]{file}, new String[]{"Saving current buffer to file: \"", "\""}));
        }
        Object dir = callSiteArray[16].callGetProperty(file);
        if (DefaultTypeTransformation.booleanUnbox((Object)dir) && !DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[17].call(dir))) {
            callSiteArray[18].call(callSiteArray[19].callGroovyObjectGetProperty((Object)this), (Object)new GStringImpl(new Object[]{dir}, new String[]{"Creating parent directory path: \"", "\""}));
            callSiteArray[20].call(dir);
        }
        return callSiteArray[21].call(file, callSiteArray[22].call(callSiteArray[23].callGroovyObjectGetProperty((Object)this), callSiteArray[24].callGroovyObjectGetProperty((Object)this)));
    }

    @Override
    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != SaveCommand.class) {
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
        stringArray[3] = "fail";
        stringArray[4] = "isEmpty";
        stringArray[5] = "buffer";
        stringArray[6] = "println";
        stringArray[7] = "out";
        stringArray[8] = "io";
        stringArray[9] = "<$constructor$>";
        stringArray[10] = "getAt";
        stringArray[11] = "verbose";
        stringArray[12] = "io";
        stringArray[13] = "println";
        stringArray[14] = "out";
        stringArray[15] = "io";
        stringArray[16] = "parentFile";
        stringArray[17] = "exists";
        stringArray[18] = "debug";
        stringArray[19] = "log";
        stringArray[20] = "mkdirs";
        stringArray[21] = "write";
        stringArray[22] = "join";
        stringArray[23] = "buffer";
        stringArray[24] = "NEWLINE";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[25];
        SaveCommand.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(SaveCommand.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = SaveCommand.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }
}

