/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.GroovyObject
 *  groovy.lang.MetaClass
 *  groovy.transform.Generated
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
import groovy.transform.Generated;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import java.util.List;
import org.apache.groovy.groovysh.Command;
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

public class RegisterCommand
extends CommandSupport {
    private static final String COMMAND_NAME = ":register";
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private static /* synthetic */ SoftReference $callSiteArray;

    public RegisterCommand(Groovysh shell) {
        CallSite[] callSiteArray = RegisterCommand.$getCallSiteArray();
        super(shell, ShortTypeHandling.castToString((Object)callSiteArray[0].callGetProperty(RegisterCommand.class)), ":rc");
    }

    @Override
    public Object execute(List<String> args) {
        CallSite[] callSiteArray = RegisterCommand.$getCallSiteArray();
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
        if (ScriptBytecodeAdapter.compareLessThan((Object)callSiteArray[1].call(args), (Object)1)) {
            callSiteArray[2].callCurrent((GroovyObject)this, (Object)new GStringImpl(new Object[]{COMMAND_NAME}, new String[]{"Command '", "' requires at least 1 arguments"}));
        }
        String classname = ShortTypeHandling.castToString((Object)callSiteArray[3].call(args, (Object)0));
        Class type = ShortTypeHandling.castToClass((Object)callSiteArray[4].call(callSiteArray[5].callGroovyObjectGetProperty((Object)this), (Object)classname));
        Command command = null;
        if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[6].call(args), (Object)1)) {
            Command command2;
            command = command2 = (Command)ScriptBytecodeAdapter.asType((Object)callSiteArray[7].call((Object)type, callSiteArray[8].callGroovyObjectGetProperty((Object)this)), Command.class);
        } else if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[9].call(args), (Object)2)) {
            Command command3;
            command = command3 = (Command)ScriptBytecodeAdapter.asType((Object)callSiteArray[10].call((Object)type, callSiteArray[11].callGroovyObjectGetProperty((Object)this), callSiteArray[12].call(args, (Object)1), null), Command.class);
        } else if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[13].call(args), (Object)3)) {
            Command command4;
            command = command4 = (Command)ScriptBytecodeAdapter.asType((Object)callSiteArray[14].call((Object)type, callSiteArray[15].callGroovyObjectGetProperty((Object)this), callSiteArray[16].call(args, (Object)1), callSiteArray[17].call(args, (Object)2)), Command.class);
        }
        Object oldcommand = callSiteArray[18].call(callSiteArray[19].callGroovyObjectGetProperty((Object)this), callSiteArray[20].callGetProperty((Object)command));
        if (DefaultTypeTransformation.booleanUnbox((Object)oldcommand)) {
            callSiteArray[21].callCurrent((GroovyObject)this, (Object)new GStringImpl(new Object[]{callSiteArray[22].callGetProperty((Object)command)}, new String[]{"Can not rebind command: ", ""}));
        }
        if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[23].callGetProperty(callSiteArray[24].callGroovyObjectGetProperty((Object)this)))) {
            callSiteArray[25].call(callSiteArray[26].callGroovyObjectGetProperty((Object)this), (Object)new GStringImpl(new Object[]{callSiteArray[27].callGetProperty((Object)command), command}, new String[]{"Created command '", "': ", ""}));
        }
        Object object = callSiteArray[28].call(callSiteArray[29].callGroovyObjectGetProperty((Object)this), (Object)command);
        command = (Command)ScriptBytecodeAdapter.castToType((Object)object, Command.class);
        if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[30].callGroovyObjectGetProperty(callSiteArray[31].callGroovyObjectGetProperty((Object)this)))) {
            return callSiteArray[32].call(callSiteArray[33].callGetProperty(callSiteArray[34].callGroovyObjectGetProperty(callSiteArray[35].callGroovyObjectGetProperty((Object)this))), (Object)command);
        }
        return null;
    }

    @Override
    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != RegisterCommand.class) {
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
    public static String getCOMMAND_NAME() {
        return COMMAND_NAME;
    }

    public /* synthetic */ MetaClass super$2$$getStaticMetaClass() {
        return super.$getStaticMetaClass();
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
        stringArray[0] = "COMMAND_NAME";
        stringArray[1] = "size";
        stringArray[2] = "fail";
        stringArray[3] = "get";
        stringArray[4] = "loadClass";
        stringArray[5] = "classLoader";
        stringArray[6] = "size";
        stringArray[7] = "newInstance";
        stringArray[8] = "shell";
        stringArray[9] = "size";
        stringArray[10] = "newInstance";
        stringArray[11] = "shell";
        stringArray[12] = "get";
        stringArray[13] = "size";
        stringArray[14] = "newInstance";
        stringArray[15] = "shell";
        stringArray[16] = "get";
        stringArray[17] = "get";
        stringArray[18] = "getAt";
        stringArray[19] = "registry";
        stringArray[20] = "name";
        stringArray[21] = "fail";
        stringArray[22] = "name";
        stringArray[23] = "debugEnabled";
        stringArray[24] = "log";
        stringArray[25] = "debug";
        stringArray[26] = "log";
        stringArray[27] = "name";
        stringArray[28] = "leftShift";
        stringArray[29] = "shell";
        stringArray[30] = "runner";
        stringArray[31] = "shell";
        stringArray[32] = "add";
        stringArray[33] = "completer";
        stringArray[34] = "runner";
        stringArray[35] = "shell";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[36];
        RegisterCommand.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(RegisterCommand.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = RegisterCommand.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }
}

