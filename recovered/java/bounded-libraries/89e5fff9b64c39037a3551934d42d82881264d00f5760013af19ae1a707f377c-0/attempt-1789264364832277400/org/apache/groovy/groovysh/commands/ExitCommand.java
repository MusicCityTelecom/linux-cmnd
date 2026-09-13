/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.GroovyObject
 *  groovy.lang.MetaClass
 *  org.codehaus.groovy.reflection.ClassInfo
 *  org.codehaus.groovy.runtime.ScriptBytecodeAdapter
 *  org.codehaus.groovy.runtime.callsite.CallSite
 *  org.codehaus.groovy.runtime.callsite.CallSiteArray
 *  org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation
 *  org.codehaus.groovy.runtime.typehandling.ShortTypeHandling
 */
package org.apache.groovy.groovysh.commands;

import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import java.util.List;
import org.apache.groovy.groovysh.CommandSupport;
import org.apache.groovy.groovysh.ExitNotification;
import org.apache.groovy.groovysh.Groovysh;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;
import org.codehaus.groovy.runtime.typehandling.ShortTypeHandling;

public class ExitCommand
extends CommandSupport {
    public static final String COMMAND_NAME = ":exit";
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private static /* synthetic */ SoftReference $callSiteArray;

    public ExitCommand(Groovysh shell) {
        CallSite[] callSiteArray = ExitCommand.$getCallSiteArray();
        super(shell, ShortTypeHandling.castToString((Object)callSiteArray[0].callGetProperty(ExitCommand.class)), ":x");
        callSiteArray[1].callCurrent((GroovyObject)this, (Object)":quit", (Object)":q");
    }

    @Override
    public Object execute(List<String> args) {
        CallSite[] callSiteArray = ExitCommand.$getCallSiteArray();
        callSiteArray[2].callCurrent((GroovyObject)this, args);
        if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[3].callGetProperty(callSiteArray[4].callGroovyObjectGetProperty((Object)this)))) {
            callSiteArray[5].call(callSiteArray[6].callGetProperty(callSiteArray[7].callGroovyObjectGetProperty((Object)this)), callSiteArray[8].call(callSiteArray[9].callGroovyObjectGetProperty((Object)this), (Object)"info.bye"));
        }
        throw (Throwable)callSiteArray[10].callConstructor(ExitNotification.class, (Object)0);
    }

    @Override
    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != ExitCommand.class) {
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

    public /* synthetic */ MetaClass super$2$$getStaticMetaClass() {
        return super.$getStaticMetaClass();
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
        stringArray[0] = "COMMAND_NAME";
        stringArray[1] = "alias";
        stringArray[2] = "assertNoArguments";
        stringArray[3] = "verbose";
        stringArray[4] = "io";
        stringArray[5] = "println";
        stringArray[6] = "out";
        stringArray[7] = "io";
        stringArray[8] = "getAt";
        stringArray[9] = "messages";
        stringArray[10] = "<$constructor$>";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[11];
        ExitCommand.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(ExitCommand.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = ExitCommand.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }
}

