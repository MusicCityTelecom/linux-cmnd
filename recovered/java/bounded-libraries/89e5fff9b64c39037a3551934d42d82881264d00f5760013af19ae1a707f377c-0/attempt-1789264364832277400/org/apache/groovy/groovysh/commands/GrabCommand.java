/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.grape.Grape
 *  groovy.lang.GroovyObject
 *  groovy.lang.MetaClass
 *  jline.console.completer.Completer
 *  org.codehaus.groovy.reflection.ClassInfo
 *  org.codehaus.groovy.runtime.BytecodeInterface8
 *  org.codehaus.groovy.runtime.GStringImpl
 *  org.codehaus.groovy.runtime.ScriptBytecodeAdapter
 *  org.codehaus.groovy.runtime.callsite.CallSite
 *  org.codehaus.groovy.runtime.callsite.CallSiteArray
 *  org.codehaus.groovy.runtime.typehandling.ShortTypeHandling
 *  org.codehaus.groovy.tools.GrapeUtil
 */
package org.apache.groovy.groovysh.commands;

import groovy.grape.Grape;
import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import java.util.List;
import java.util.Map;
import jline.console.completer.Completer;
import org.apache.groovy.groovysh.CommandSupport;
import org.apache.groovy.groovysh.Groovysh;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.BytecodeInterface8;
import org.codehaus.groovy.runtime.GStringImpl;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.typehandling.ShortTypeHandling;
import org.codehaus.groovy.tools.GrapeUtil;

public class GrabCommand
extends CommandSupport {
    public static final String COMMAND_NAME = ":grab";
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private static /* synthetic */ SoftReference $callSiteArray;

    public GrabCommand(Groovysh shell) {
        CallSite[] callSiteArray = GrabCommand.$getCallSiteArray();
        super(shell, ShortTypeHandling.castToString((Object)callSiteArray[0].callGetProperty(GrabCommand.class)), ":g");
    }

    @Override
    protected List<Completer> createCompleters() {
        CallSite[] callSiteArray = GrabCommand.$getCallSiteArray();
        return ScriptBytecodeAdapter.createList((Object[])new Object[]{null});
    }

    @Override
    public Object execute(List<String> args) {
        CallSite[] callSiteArray = GrabCommand.$getCallSiteArray();
        callSiteArray[1].callCurrent((GroovyObject)this, args);
        callSiteArray[2].callCurrent((GroovyObject)this, callSiteArray[3].callCurrent((GroovyObject)this, args));
        return callSiteArray[4].call(callSiteArray[5].callGroovyObjectGetProperty(callSiteArray[6].callGroovyObjectGetProperty((Object)this)));
    }

    private void validate(List<String> args) {
        CallSite[] callSiteArray = GrabCommand.$getCallSiteArray();
        if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            if (ScriptBytecodeAdapter.compareNotEqual((Object)callSiteArray[7].callSafe(args), (Object)1) || !ScriptBytecodeAdapter.matchRegex((Object)callSiteArray[8].call(args, (Object)0), (Object)"^(\\w|\\.|-)+:(\\w|\\.|-)+(\\w|\\.|-)(:+(\\w|\\.|-|\\*)+){0,2}$")) {
                callSiteArray[9].callCurrent((GroovyObject)this, (Object)new GStringImpl(new Object[]{COMMAND_NAME, callSiteArray[10].callGroovyObjectGetProperty((Object)this)}, new String[]{"usage: @|bold ", "|@ ", ""}));
            }
        } else if (ScriptBytecodeAdapter.compareNotEqual((Object)callSiteArray[11].callSafe(args), (Object)1) || !ScriptBytecodeAdapter.matchRegex((Object)callSiteArray[12].call(args, (Object)0), (Object)"^(\\w|\\.|-)+:(\\w|\\.|-)+(\\w|\\.|-)(:+(\\w|\\.|-|\\*)+){0,2}$")) {
            callSiteArray[13].callCurrent((GroovyObject)this, (Object)new GStringImpl(new Object[]{COMMAND_NAME, callSiteArray[14].callGroovyObjectGetProperty((Object)this)}, new String[]{"usage: @|bold ", "|@ ", ""}));
        }
    }

    private String dependency(List<String> args) {
        CallSite[] callSiteArray = GrabCommand.$getCallSiteArray();
        callSiteArray[15].callCurrent((GroovyObject)this, args);
        return ShortTypeHandling.castToString((Object)callSiteArray[16].call(args, (Object)0));
    }

    private Map<String, Object> dependencyMap(String dependency) {
        CallSite[] callSiteArray = GrabCommand.$getCallSiteArray();
        return (Map)ScriptBytecodeAdapter.castToType((Object)callSiteArray[17].call(GrapeUtil.class, (Object)dependency), Map.class);
    }

    private void grab(String dependency) {
        CallSite[] callSiteArray = GrabCommand.$getCallSiteArray();
        if (__$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            callSiteArray[18].call(Grape.class, (Object)ScriptBytecodeAdapter.createMap((Object[])new Object[]{"classLoader", callSiteArray[19].callGetProperty(callSiteArray[20].callGetProperty(callSiteArray[21].callGroovyObjectGetProperty(callSiteArray[22].callGroovyObjectGetProperty((Object)this)))), "refObject", callSiteArray[23].callGroovyObjectGetProperty(callSiteArray[24].callGroovyObjectGetProperty((Object)this))}), callSiteArray[25].callCurrent((GroovyObject)this, (Object)dependency));
        } else {
            callSiteArray[26].call(Grape.class, (Object)ScriptBytecodeAdapter.createMap((Object[])new Object[]{"classLoader", callSiteArray[27].callGetProperty(callSiteArray[28].callGetProperty(callSiteArray[29].callGroovyObjectGetProperty(callSiteArray[30].callGroovyObjectGetProperty((Object)this)))), "refObject", callSiteArray[31].callGroovyObjectGetProperty(callSiteArray[32].callGroovyObjectGetProperty((Object)this))}), this.dependencyMap(dependency));
        }
    }

    @Override
    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != GrabCommand.class) {
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
        stringArray[1] = "validate";
        stringArray[2] = "grab";
        stringArray[3] = "dependency";
        stringArray[4] = "reset";
        stringArray[5] = "packageHelper";
        stringArray[6] = "shell";
        stringArray[7] = "size";
        stringArray[8] = "getAt";
        stringArray[9] = "fail";
        stringArray[10] = "usage";
        stringArray[11] = "size";
        stringArray[12] = "getAt";
        stringArray[13] = "fail";
        stringArray[14] = "usage";
        stringArray[15] = "validate";
        stringArray[16] = "getAt";
        stringArray[17] = "getIvyParts";
        stringArray[18] = "grab";
        stringArray[19] = "parent";
        stringArray[20] = "classLoader";
        stringArray[21] = "interp";
        stringArray[22] = "shell";
        stringArray[23] = "interp";
        stringArray[24] = "shell";
        stringArray[25] = "dependencyMap";
        stringArray[26] = "grab";
        stringArray[27] = "parent";
        stringArray[28] = "classLoader";
        stringArray[29] = "interp";
        stringArray[30] = "shell";
        stringArray[31] = "interp";
        stringArray[32] = "shell";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[33];
        GrabCommand.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(GrabCommand.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = GrabCommand.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }
}

