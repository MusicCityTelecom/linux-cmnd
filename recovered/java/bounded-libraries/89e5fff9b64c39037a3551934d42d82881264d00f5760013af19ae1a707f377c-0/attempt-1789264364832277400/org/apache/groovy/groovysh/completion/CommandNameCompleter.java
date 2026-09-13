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
 *  org.codehaus.groovy.runtime.powerassert.AssertionRenderer
 *  org.codehaus.groovy.runtime.powerassert.ValueRecorder
 *  org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation
 */
package org.apache.groovy.groovysh.completion;

import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import org.apache.groovy.groovysh.Command;
import org.apache.groovy.groovysh.CommandRegistry;
import org.apache.groovy.groovysh.util.SimpleCompleter;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.powerassert.AssertionRenderer;
import org.codehaus.groovy.runtime.powerassert.ValueRecorder;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;

public class CommandNameCompleter
extends SimpleCompleter {
    private final CommandRegistry registry;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private static /* synthetic */ SoftReference $callSiteArray;

    public CommandNameCompleter(CommandRegistry registry, boolean withBlank) {
        CommandRegistry commandRegistry;
        CallSite[] callSiteArray = CommandNameCompleter.$getCallSiteArray();
        ValueRecorder valueRecorder = new ValueRecorder();
        try {
            CommandRegistry commandRegistry2 = registry;
            valueRecorder.record((Object)commandRegistry2, 8);
            if (DefaultTypeTransformation.booleanUnbox((Object)commandRegistry2)) {
                valueRecorder.clear();
            } else {
                ScriptBytecodeAdapter.assertFailed((Object)AssertionRenderer.render((String)"assert registry", (ValueRecorder)valueRecorder), null);
            }
        }
        catch (Throwable throwable) {
            valueRecorder.clear();
            throw throwable;
        }
        callSiteArray[0].callCurrent((GroovyObject)this, (Object)withBlank);
        this.registry = commandRegistry = registry;
    }

    @Override
    public SortedSet<String> getCandidates() {
        CallSite[] callSiteArray = CommandNameCompleter.$getCallSiteArray();
        SortedSet set = (SortedSet)ScriptBytecodeAdapter.castToType((Object)callSiteArray[1].callConstructor(TreeSet.class), SortedSet.class);
        Command command = null;
        Iterator iterator = (Iterator)ScriptBytecodeAdapter.castToType((Object)callSiteArray[2].call(callSiteArray[3].call((Object)this.registry)), Iterator.class);
        if (iterator != null) {
            while (iterator.hasNext()) {
                command = (Command)ScriptBytecodeAdapter.castToType(iterator.next(), Command.class);
                if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[4].callGetProperty((Object)command))) continue;
                callSiteArray[5].call((Object)set, callSiteArray[6].callGetProperty((Object)command));
                callSiteArray[7].call((Object)set, callSiteArray[8].callGetProperty((Object)command));
            }
        }
        return set;
    }

    @Override
    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != CommandNameCompleter.class) {
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

    public /* synthetic */ SortedSet super$2$getCandidates() {
        return super.getCandidates();
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
        stringArray[0] = "setWithBlank";
        stringArray[1] = "<$constructor$>";
        stringArray[2] = "iterator";
        stringArray[3] = "commands";
        stringArray[4] = "hidden";
        stringArray[5] = "leftShift";
        stringArray[6] = "name";
        stringArray[7] = "leftShift";
        stringArray[8] = "shortcut";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[9];
        CommandNameCompleter.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(CommandNameCompleter.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = CommandNameCompleter.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }
}

