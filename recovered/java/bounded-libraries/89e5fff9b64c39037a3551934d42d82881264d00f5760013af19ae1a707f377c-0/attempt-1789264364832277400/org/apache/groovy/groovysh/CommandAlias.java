/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.MetaClass
 *  groovy.transform.Generated
 *  jline.console.completer.Completer
 *  org.codehaus.groovy.reflection.ClassInfo
 *  org.codehaus.groovy.runtime.ScriptBytecodeAdapter
 *  org.codehaus.groovy.runtime.callsite.CallSite
 *  org.codehaus.groovy.runtime.callsite.CallSiteArray
 *  org.codehaus.groovy.runtime.powerassert.AssertionRenderer
 *  org.codehaus.groovy.runtime.powerassert.ValueRecorder
 *  org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation
 *  org.codehaus.groovy.runtime.typehandling.ShortTypeHandling
 */
package org.apache.groovy.groovysh;

import groovy.lang.MetaClass;
import groovy.transform.Generated;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import java.util.List;
import jline.console.completer.Completer;
import org.apache.groovy.groovysh.Command;
import org.apache.groovy.groovysh.CommandSupport;
import org.apache.groovy.groovysh.Groovysh;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.powerassert.AssertionRenderer;
import org.codehaus.groovy.runtime.powerassert.ValueRecorder;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;
import org.codehaus.groovy.runtime.typehandling.ShortTypeHandling;

public class CommandAlias
extends CommandSupport {
    private final String targetName;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private static /* synthetic */ SoftReference $callSiteArray;

    public CommandAlias(Groovysh shell, String name, String shortcut, String target) {
        String string;
        CallSite[] callSiteArray = CommandAlias.$getCallSiteArray();
        super(shell, name, shortcut);
        ValueRecorder valueRecorder = new ValueRecorder();
        try {
            String string2 = target;
            valueRecorder.record((Object)string2, 8);
            if (DefaultTypeTransformation.booleanUnbox((Object)string2)) {
                valueRecorder.clear();
            } else {
                ScriptBytecodeAdapter.assertFailed((Object)AssertionRenderer.render((String)"assert target", (ValueRecorder)valueRecorder), null);
            }
        }
        catch (Throwable throwable) {
            valueRecorder.clear();
            throw throwable;
        }
        this.targetName = string = target;
    }

    public Command getTarget() {
        CallSite[] callSiteArray = CommandAlias.$getCallSiteArray();
        Command command = (Command)ScriptBytecodeAdapter.castToType((Object)callSiteArray[0].call(callSiteArray[1].callGroovyObjectGetProperty((Object)this), (Object)this.targetName), Command.class);
        ValueRecorder valueRecorder = new ValueRecorder();
        try {
            Command command2 = command;
            valueRecorder.record((Object)command2, 8);
            boolean bl = ScriptBytecodeAdapter.compareNotEqual((Object)command2, null);
            valueRecorder.record((Object)bl, 16);
            if (bl) {
                valueRecorder.clear();
            } else {
                ScriptBytecodeAdapter.assertFailed((Object)AssertionRenderer.render((String)"assert command != null", (ValueRecorder)valueRecorder), null);
            }
        }
        catch (Throwable throwable) {
            valueRecorder.clear();
            throw throwable;
        }
        return command;
    }

    @Override
    protected List<Completer> createCompleters() {
        block6: {
            CallSite[] callSiteArray = CommandAlias.$getCallSiteArray();
            if (!(callSiteArray[2].callGroovyObjectGetProperty((Object)this) instanceof CommandSupport)) break block6;
            CommandSupport support = (CommandSupport)ScriptBytecodeAdapter.castToType((Object)callSiteArray[3].callGroovyObjectGetProperty((Object)this), CommandSupport.class);
            List list = (List)ScriptBytecodeAdapter.castToType((Object)callSiteArray[4].call((Object)support), List.class);
            try {
                return list;
            }
            catch (Exception MissingMethodException) {
                List list2 = (List)ScriptBytecodeAdapter.castToType((Object)callSiteArray[5].call(callSiteArray[6].callGroovyObjectGetProperty((Object)this), (Object)"Aliased Command without createCompleters Method"), List.class);
                return list2;
            }
        }
        return null;
    }

    @Override
    public String getDescription() {
        CallSite[] callSiteArray = CommandAlias.$getCallSiteArray();
        return ShortTypeHandling.castToString((Object)callSiteArray[7].call(callSiteArray[8].callGroovyObjectGetProperty((Object)this), (Object)"info.alias_to", (Object)this.targetName));
    }

    @Override
    public String getUsage() {
        CallSite[] callSiteArray = CommandAlias.$getCallSiteArray();
        return ShortTypeHandling.castToString((Object)callSiteArray[9].callGetProperty(callSiteArray[10].callGroovyObjectGetProperty((Object)this)));
    }

    @Override
    public String getHelp() {
        CallSite[] callSiteArray = CommandAlias.$getCallSiteArray();
        return ShortTypeHandling.castToString((Object)callSiteArray[11].callGetProperty(callSiteArray[12].callGroovyObjectGetProperty((Object)this)));
    }

    @Override
    public boolean getHidden() {
        CallSite[] callSiteArray = CommandAlias.$getCallSiteArray();
        return DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[13].callGetProperty(callSiteArray[14].callGroovyObjectGetProperty((Object)this)));
    }

    @Override
    public Object execute(List<String> args) {
        CallSite[] callSiteArray = CommandAlias.$getCallSiteArray();
        return callSiteArray[15].call(callSiteArray[16].callGroovyObjectGetProperty((Object)this), args);
    }

    @Override
    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != CommandAlias.class) {
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
    public final String getTargetName() {
        return this.targetName;
    }

    public /* synthetic */ String super$2$getHelp() {
        return super.getHelp();
    }

    public /* synthetic */ List super$2$createCompleters() {
        return super.createCompleters();
    }

    public /* synthetic */ String super$2$getUsage() {
        return super.getUsage();
    }

    public /* synthetic */ MetaClass super$2$$getStaticMetaClass() {
        return super.$getStaticMetaClass();
    }

    public /* synthetic */ String super$2$getDescription() {
        return super.getDescription();
    }

    public /* synthetic */ boolean super$2$getHidden() {
        return super.getHidden();
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
        stringArray[0] = "find";
        stringArray[1] = "registry";
        stringArray[2] = "target";
        stringArray[3] = "target";
        stringArray[4] = "createCompleters";
        stringArray[5] = "warn";
        stringArray[6] = "log";
        stringArray[7] = "format";
        stringArray[8] = "messages";
        stringArray[9] = "usage";
        stringArray[10] = "target";
        stringArray[11] = "help";
        stringArray[12] = "target";
        stringArray[13] = "hidden";
        stringArray[14] = "target";
        stringArray[15] = "execute";
        stringArray[16] = "target";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[17];
        CommandAlias.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(CommandAlias.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = CommandAlias.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }
}

