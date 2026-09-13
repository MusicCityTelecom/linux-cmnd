/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.GroovyObject
 *  groovy.lang.MetaClass
 *  org.codehaus.groovy.reflection.ClassInfo
 *  org.codehaus.groovy.runtime.GStringImpl
 *  org.codehaus.groovy.runtime.ScriptBytecodeAdapter
 *  org.codehaus.groovy.runtime.callsite.CallSite
 *  org.codehaus.groovy.runtime.callsite.CallSiteArray
 *  org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation
 *  org.codehaus.groovy.runtime.typehandling.ShortTypeHandling
 *  org.codehaus.groovy.tools.shell.util.Preferences
 */
package org.apache.groovy.groovysh.commands;

import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import java.io.File;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import org.apache.groovy.groovysh.CommandSupport;
import org.apache.groovy.groovysh.Groovysh;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.GStringImpl;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;
import org.codehaus.groovy.runtime.typehandling.ShortTypeHandling;
import org.codehaus.groovy.tools.shell.util.Preferences;

public class EditCommand
extends CommandSupport {
    public static final String COMMAND_NAME = ":edit";
    private static /* synthetic */ BigDecimal $const$0;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private static /* synthetic */ SoftReference $callSiteArray;

    public EditCommand(Groovysh shell) {
        CallSite[] callSiteArray = EditCommand.$getCallSiteArray();
        super(shell, ShortTypeHandling.castToString((Object)callSiteArray[0].callGetProperty(EditCommand.class)), ":e");
    }

    public ProcessBuilder getEditorProcessBuilder(String editCommand, String tempFilename) {
        CallSite[] callSiteArray = EditCommand.$getCallSiteArray();
        Object pb = callSiteArray[1].callConstructor(ProcessBuilder.class, (Object)editCommand, (Object)tempFilename);
        callSiteArray[2].call(pb, (Object)true);
        Object javaVer = callSiteArray[3].call(Double.class, callSiteArray[4].call(System.class, (Object)"java.specification.version"));
        if (ScriptBytecodeAdapter.compareGreaterThanEqual((Object)javaVer, (Object)$const$0)) {
            callSiteArray[5].call(pb, callSiteArray[6].callGetProperty(ProcessBuilder.Redirect.class));
            callSiteArray[7].call(pb, callSiteArray[8].callGetProperty(ProcessBuilder.Redirect.class));
        }
        return (ProcessBuilder)ScriptBytecodeAdapter.castToType((Object)pb, ProcessBuilder.class);
    }

    private String getEditorCommand() {
        CallSite[] callSiteArray = EditCommand.$getCallSiteArray();
        Object editor = callSiteArray[9].callGetProperty(Preferences.class);
        callSiteArray[10].call(callSiteArray[11].callGroovyObjectGetProperty((Object)this), (Object)new GStringImpl(new Object[]{editor}, new String[]{"Using editor: ", ""}));
        if (!DefaultTypeTransformation.booleanUnbox((Object)editor)) {
            callSiteArray[12].callCurrent((GroovyObject)this, (Object)"Unable to determine which editor to use; check $EDITOR");
        }
        return ShortTypeHandling.castToString((Object)editor);
    }

    @Override
    public Object execute(List<String> args) {
        Object object;
        CallSite[] callSiteArray = EditCommand.$getCallSiteArray();
        callSiteArray[13].callCurrent((GroovyObject)this, args);
        File file = (File)ScriptBytecodeAdapter.castToType((Object)callSiteArray[14].call(File.class, (Object)"groovysh-buffer", (Object)".groovy"), File.class);
        callSiteArray[15].call((Object)file);
        try {
            callSiteArray[16].call((Object)file, callSiteArray[17].call(callSiteArray[18].callGroovyObjectGetProperty((Object)this), callSiteArray[19].callGroovyObjectGetProperty((Object)this)));
            callSiteArray[20].call(callSiteArray[21].callGroovyObjectGetProperty((Object)this), (Object)new GStringImpl(new Object[]{callSiteArray[22].callGroovyObjectGetProperty((Object)this), file}, new String[]{"Executing: ", " ", ""}));
            Object pb = callSiteArray[23].callCurrent((GroovyObject)this, (Object)new GStringImpl(new Object[]{callSiteArray[24].callGroovyObjectGetProperty((Object)this)}, new String[]{"", ""}), (Object)new GStringImpl(new Object[]{file}, new String[]{"", ""}));
            Object p = callSiteArray[25].call(pb);
            callSiteArray[26].call(callSiteArray[27].callGroovyObjectGetProperty((Object)this), (Object)new GStringImpl(new Object[]{p}, new String[]{"Waiting for process: ", ""}));
            callSiteArray[28].call(p);
            callSiteArray[29].call(callSiteArray[30].callGroovyObjectGetProperty((Object)this), (Object)new GStringImpl(new Object[]{callSiteArray[31].callGetProperty((Object)file)}, new String[]{"Editor contents: ", ""}));
            object = callSiteArray[32].callCurrent((GroovyObject)this, callSiteArray[33].call((Object)file));
        }
        catch (Throwable throwable) {
            callSiteArray[35].call((Object)file);
            throw throwable;
        }
        callSiteArray[34].call((Object)file);
        return object;
    }

    public void replaceCurrentBuffer(List<String> contents) {
        CallSite[] callSiteArray = EditCommand.$getCallSiteArray();
        callSiteArray[36].call(callSiteArray[37].callGroovyObjectGetProperty(callSiteArray[38].callGroovyObjectGetProperty((Object)this)));
        String line = null;
        Iterator iterator = (Iterator)ScriptBytecodeAdapter.castToType((Object)callSiteArray[39].call(contents), Iterator.class);
        if (iterator != null) {
            while (iterator.hasNext()) {
                line = ShortTypeHandling.castToString(iterator.next());
                callSiteArray[40].call(callSiteArray[41].callGroovyObjectGetProperty((Object)this), (Object)line);
            }
        }
    }

    @Override
    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != EditCommand.class) {
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

    public static /* synthetic */ void __$swapInit() {
        BigDecimal bigDecimal;
        CallSite[] callSiteArray = EditCommand.$getCallSiteArray();
        $callSiteArray = null;
        $const$0 = bigDecimal = new BigDecimal("1.7");
    }

    static {
        EditCommand.__$swapInit();
    }

    public /* synthetic */ MetaClass super$2$$getStaticMetaClass() {
        return super.$getStaticMetaClass();
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
        stringArray[0] = "COMMAND_NAME";
        stringArray[1] = "<$constructor$>";
        stringArray[2] = "redirectErrorStream";
        stringArray[3] = "valueOf";
        stringArray[4] = "getProperty";
        stringArray[5] = "redirectInput";
        stringArray[6] = "INHERIT";
        stringArray[7] = "redirectOutput";
        stringArray[8] = "INHERIT";
        stringArray[9] = "editor";
        stringArray[10] = "debug";
        stringArray[11] = "log";
        stringArray[12] = "fail";
        stringArray[13] = "assertNoArguments";
        stringArray[14] = "createTempFile";
        stringArray[15] = "deleteOnExit";
        stringArray[16] = "write";
        stringArray[17] = "join";
        stringArray[18] = "buffer";
        stringArray[19] = "NEWLINE";
        stringArray[20] = "debug";
        stringArray[21] = "log";
        stringArray[22] = "editorCommand";
        stringArray[23] = "getEditorProcessBuilder";
        stringArray[24] = "editorCommand";
        stringArray[25] = "start";
        stringArray[26] = "debug";
        stringArray[27] = "log";
        stringArray[28] = "waitFor";
        stringArray[29] = "debug";
        stringArray[30] = "log";
        stringArray[31] = "text";
        stringArray[32] = "replaceCurrentBuffer";
        stringArray[33] = "readLines";
        stringArray[34] = "delete";
        stringArray[35] = "delete";
        stringArray[36] = "clearSelected";
        stringArray[37] = "buffers";
        stringArray[38] = "shell";
        stringArray[39] = "iterator";
        stringArray[40] = "execute";
        stringArray[41] = "shell";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[42];
        EditCommand.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(EditCommand.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = EditCommand.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }
}

