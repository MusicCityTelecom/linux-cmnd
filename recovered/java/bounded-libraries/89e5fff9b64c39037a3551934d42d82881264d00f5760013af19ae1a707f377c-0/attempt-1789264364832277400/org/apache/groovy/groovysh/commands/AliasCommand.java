/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.GroovyObject
 *  groovy.lang.MetaClass
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

import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import java.util.List;
import jline.console.completer.Completer;
import org.apache.groovy.groovysh.Command;
import org.apache.groovy.groovysh.CommandSupport;
import org.apache.groovy.groovysh.Groovysh;
import org.apache.groovy.groovysh.commands.AliasTargetProxyCommand;
import org.apache.groovy.groovysh.completion.CommandNameCompleter;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.GStringImpl;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.powerassert.AssertionRenderer;
import org.codehaus.groovy.runtime.powerassert.ValueRecorder;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;
import org.codehaus.groovy.runtime.typehandling.ShortTypeHandling;

public class AliasCommand
extends CommandSupport {
    public static final String COMMAND_NAME = ":alias";
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private static /* synthetic */ SoftReference $callSiteArray;

    public AliasCommand(Groovysh shell) {
        CallSite[] callSiteArray = AliasCommand.$getCallSiteArray();
        super(shell, ShortTypeHandling.castToString((Object)callSiteArray[0].callGetProperty(AliasCommand.class)), ":a");
    }

    @Override
    protected List<Completer> createCompleters() {
        CallSite[] callSiteArray = AliasCommand.$getCallSiteArray();
        return ScriptBytecodeAdapter.createList((Object[])new Object[]{callSiteArray[1].callConstructor(CommandNameCompleter.class, callSiteArray[2].callGroovyObjectGetProperty((Object)this), (Object)true), null});
    }

    @Override
    public Object execute(List<String> args) {
        CallSite[] callSiteArray = AliasCommand.$getCallSiteArray();
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
        if (ScriptBytecodeAdapter.compareLessThan((Object)callSiteArray[3].call(args), (Object)2)) {
            callSiteArray[4].callCurrent((GroovyObject)this, (Object)"Command 'alias' requires at least 2 arguments");
        }
        String name = ShortTypeHandling.castToString((Object)callSiteArray[5].call(args, (Object)0));
        List target = (List)ScriptBytecodeAdapter.castToType((Object)callSiteArray[6].call(args, (Object)ScriptBytecodeAdapter.createRange((Object)1, (Object)-1, (boolean)false, (boolean)false)), List.class);
        Command command = (Command)ScriptBytecodeAdapter.castToType((Object)callSiteArray[7].call(callSiteArray[8].callGroovyObjectGetProperty((Object)this), (Object)name), Command.class);
        if (ScriptBytecodeAdapter.compareEqual((Object)command, null)) {
            Object object = callSiteArray[9].call(callSiteArray[10].callGroovyObjectGetProperty((Object)this), (Object)name);
            command = (Command)ScriptBytecodeAdapter.castToType((Object)object, Command.class);
        }
        if (ScriptBytecodeAdapter.compareNotEqual((Object)command, null)) {
            if (command instanceof AliasTargetProxyCommand) {
                callSiteArray[11].call(callSiteArray[12].callGroovyObjectGetProperty((Object)this), (Object)new GStringImpl(new Object[]{name}, new String[]{"Rebinding alias: ", ""}));
                callSiteArray[13].call(callSiteArray[14].callGroovyObjectGetProperty((Object)this), (Object)command);
            } else {
                callSiteArray[15].callCurrent((GroovyObject)this, (Object)new GStringImpl(new Object[]{callSiteArray[16].callGetProperty((Object)command)}, new String[]{"Can not rebind non-user aliased command: ", ""}));
            }
        }
        callSiteArray[17].call(callSiteArray[18].callGroovyObjectGetProperty((Object)this), (Object)new GStringImpl(new Object[]{name, target}, new String[]{"Creating alias '", "' to: ", ""}));
        Object object = callSiteArray[19].call(callSiteArray[20].callGroovyObjectGetProperty((Object)this), callSiteArray[21].callConstructor(AliasTargetProxyCommand.class, callSiteArray[22].callGroovyObjectGetProperty((Object)this), (Object)name, (Object)target));
        command = (Command)ScriptBytecodeAdapter.castToType((Object)object, Command.class);
        if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[23].callGroovyObjectGetProperty(callSiteArray[24].callGroovyObjectGetProperty((Object)this)))) {
            return callSiteArray[25].call(callSiteArray[26].callGetProperty(callSiteArray[27].callGroovyObjectGetProperty(callSiteArray[28].callGroovyObjectGetProperty((Object)this))), (Object)command);
        }
        return null;
    }

    @Override
    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != AliasCommand.class) {
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
        stringArray[2] = "registry";
        stringArray[3] = "size";
        stringArray[4] = "fail";
        stringArray[5] = "getAt";
        stringArray[6] = "getAt";
        stringArray[7] = "find";
        stringArray[8] = "registry";
        stringArray[9] = "find";
        stringArray[10] = "registry";
        stringArray[11] = "debug";
        stringArray[12] = "log";
        stringArray[13] = "remove";
        stringArray[14] = "registry";
        stringArray[15] = "fail";
        stringArray[16] = "name";
        stringArray[17] = "debug";
        stringArray[18] = "log";
        stringArray[19] = "leftShift";
        stringArray[20] = "shell";
        stringArray[21] = "<$constructor$>";
        stringArray[22] = "shell";
        stringArray[23] = "runner";
        stringArray[24] = "shell";
        stringArray[25] = "add";
        stringArray[26] = "completer";
        stringArray[27] = "runner";
        stringArray[28] = "shell";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[29];
        AliasCommand.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(AliasCommand.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = AliasCommand.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }
}

