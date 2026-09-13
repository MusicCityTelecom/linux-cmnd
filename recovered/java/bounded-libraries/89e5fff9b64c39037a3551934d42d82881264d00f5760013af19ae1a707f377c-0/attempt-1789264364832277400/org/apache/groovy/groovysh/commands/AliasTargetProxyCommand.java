/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
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

public class AliasTargetProxyCommand
extends CommandSupport
implements Command {
    private static int counter;
    private final List<String> args;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private static /* synthetic */ SoftReference $callSiteArray;

    public AliasTargetProxyCommand(Groovysh shell, String name, List args) {
        List list;
        CallSite[] callSiteArray = AliasTargetProxyCommand.$getCallSiteArray();
        CallSite callSite = callSiteArray[0];
        Object object = callSiteArray[1].callGetProperty(AliasTargetProxyCommand.class);
        ScriptBytecodeAdapter.setProperty((Object)callSiteArray[2].call(object), null, AliasTargetProxyCommand.class, (String)"counter");
        super(shell, name, ShortTypeHandling.castToString((Object)callSite.call((Object)":a", object)));
        ValueRecorder valueRecorder = new ValueRecorder();
        try {
            List list2 = args;
            valueRecorder.record((Object)list2, 8);
            if (DefaultTypeTransformation.booleanUnbox((Object)list2)) {
                valueRecorder.clear();
            } else {
                ScriptBytecodeAdapter.assertFailed((Object)AssertionRenderer.render((String)"assert args", (ValueRecorder)valueRecorder), null);
            }
        }
        catch (Throwable throwable) {
            valueRecorder.clear();
            throw throwable;
        }
        this.args = list = args;
    }

    @Override
    public String getDescription() {
        CallSite[] callSiteArray = AliasTargetProxyCommand.$getCallSiteArray();
        return ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{callSiteArray[3].call(this.args, (Object)" ")}, new String[]{"User defined alias to: @|bold ", "|@"}));
    }

    @Override
    public String getUsage() {
        CallSite[] callSiteArray = AliasTargetProxyCommand.$getCallSiteArray();
        return "";
    }

    @Override
    public String getHelp() {
        CallSite[] callSiteArray = AliasTargetProxyCommand.$getCallSiteArray();
        return ShortTypeHandling.castToString((Object)callSiteArray[4].callGroovyObjectGetProperty((Object)this));
    }

    @Override
    public Object execute(List<String> args) {
        CallSite[] callSiteArray = AliasTargetProxyCommand.$getCallSiteArray();
        List allArgs = (List)ScriptBytecodeAdapter.castToType((Object)callSiteArray[5].call(this.args, args), List.class);
        callSiteArray[6].call(callSiteArray[7].callGroovyObjectGetProperty((Object)this), (Object)new GStringImpl(new Object[]{allArgs}, new String[]{"Executing with args: ", ""}));
        return callSiteArray[8].call(callSiteArray[9].callGroovyObjectGetProperty((Object)this), callSiteArray[10].call((Object)allArgs, (Object)" "));
    }

    @Override
    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != AliasTargetProxyCommand.class) {
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

    static {
        int n;
        counter = n = 0;
    }

    @Generated
    public final List<String> getArgs() {
        return this.args;
    }

    public /* synthetic */ String super$2$getHelp() {
        return super.getHelp();
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

    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
        stringArray[0] = "plus";
        stringArray[1] = "counter";
        stringArray[2] = "next";
        stringArray[3] = "join";
        stringArray[4] = "description";
        stringArray[5] = "plus";
        stringArray[6] = "debug";
        stringArray[7] = "log";
        stringArray[8] = "executeCommand";
        stringArray[9] = "shell";
        stringArray[10] = "join";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[11];
        AliasTargetProxyCommand.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(AliasTargetProxyCommand.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = AliasTargetProxyCommand.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }
}

