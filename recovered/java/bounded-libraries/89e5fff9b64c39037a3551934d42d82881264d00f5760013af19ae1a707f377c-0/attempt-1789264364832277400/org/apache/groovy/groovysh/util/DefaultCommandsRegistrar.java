/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.GroovyObject
 *  groovy.lang.MetaClass
 *  groovy.transform.Generated
 *  groovy.transform.Internal
 *  org.codehaus.groovy.reflection.ClassInfo
 *  org.codehaus.groovy.runtime.ScriptBytecodeAdapter
 *  org.codehaus.groovy.runtime.callsite.CallSite
 *  org.codehaus.groovy.runtime.callsite.CallSiteArray
 *  org.codehaus.groovy.runtime.powerassert.AssertionRenderer
 *  org.codehaus.groovy.runtime.powerassert.ValueRecorder
 */
package org.apache.groovy.groovysh.util;

import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import groovy.transform.Generated;
import groovy.transform.Internal;
import java.beans.Transient;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import java.util.Iterator;
import org.apache.groovy.groovysh.Command;
import org.apache.groovy.groovysh.Shell;
import org.apache.groovy.groovysh.commands.AliasCommand;
import org.apache.groovy.groovysh.commands.ClearCommand;
import org.apache.groovy.groovysh.commands.DisplayCommand;
import org.apache.groovy.groovysh.commands.DocCommand;
import org.apache.groovy.groovysh.commands.EditCommand;
import org.apache.groovy.groovysh.commands.ExitCommand;
import org.apache.groovy.groovysh.commands.GrabCommand;
import org.apache.groovy.groovysh.commands.HelpCommand;
import org.apache.groovy.groovysh.commands.HistoryCommand;
import org.apache.groovy.groovysh.commands.ImportCommand;
import org.apache.groovy.groovysh.commands.InspectCommand;
import org.apache.groovy.groovysh.commands.LoadCommand;
import org.apache.groovy.groovysh.commands.PurgeCommand;
import org.apache.groovy.groovysh.commands.RecordCommand;
import org.apache.groovy.groovysh.commands.RegisterCommand;
import org.apache.groovy.groovysh.commands.SaveCommand;
import org.apache.groovy.groovysh.commands.SetCommand;
import org.apache.groovy.groovysh.commands.ShowCommand;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.powerassert.AssertionRenderer;
import org.codehaus.groovy.runtime.powerassert.ValueRecorder;

public class DefaultCommandsRegistrar
implements GroovyObject {
    private final Shell shell;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private transient /* synthetic */ MetaClass metaClass;
    private static /* synthetic */ SoftReference $callSiteArray;

    public DefaultCommandsRegistrar(Shell shell) {
        Shell shell2;
        MetaClass metaClass;
        CallSite[] callSiteArray = DefaultCommandsRegistrar.$getCallSiteArray();
        this.metaClass = metaClass = this.$getStaticMetaClass();
        ValueRecorder valueRecorder = new ValueRecorder();
        try {
            Shell shell3 = shell;
            valueRecorder.record((Object)shell3, 8);
            boolean bl = ScriptBytecodeAdapter.compareNotEqual((Object)shell3, null);
            valueRecorder.record((Object)bl, 14);
            if (bl) {
                valueRecorder.clear();
            } else {
                ScriptBytecodeAdapter.assertFailed((Object)AssertionRenderer.render((String)"assert shell != null", (ValueRecorder)valueRecorder), null);
            }
        }
        catch (Throwable throwable) {
            valueRecorder.clear();
            throw throwable;
        }
        this.shell = shell2 = shell;
    }

    public void register() {
        CallSite[] callSiteArray = DefaultCommandsRegistrar.$getCallSiteArray();
        Command classname = null;
        Iterator iterator = (Iterator)ScriptBytecodeAdapter.castToType((Object)callSiteArray[0].call((Object)ScriptBytecodeAdapter.createList((Object[])new Object[]{callSiteArray[1].callConstructor(HelpCommand.class, (Object)this.shell), callSiteArray[2].callConstructor(ExitCommand.class, (Object)this.shell), callSiteArray[3].callConstructor(ImportCommand.class, (Object)this.shell), callSiteArray[4].callConstructor(DisplayCommand.class, (Object)this.shell), callSiteArray[5].callConstructor(ClearCommand.class, (Object)this.shell), callSiteArray[6].callConstructor(ShowCommand.class, (Object)this.shell), callSiteArray[7].callConstructor(InspectCommand.class, (Object)this.shell), callSiteArray[8].callConstructor(PurgeCommand.class, (Object)this.shell), callSiteArray[9].callConstructor(EditCommand.class, (Object)this.shell), callSiteArray[10].callConstructor(LoadCommand.class, (Object)this.shell), callSiteArray[11].callConstructor(SaveCommand.class, (Object)this.shell), callSiteArray[12].callConstructor(RecordCommand.class, (Object)this.shell), callSiteArray[13].callConstructor(HistoryCommand.class, (Object)this.shell), callSiteArray[14].callConstructor(AliasCommand.class, (Object)this.shell), callSiteArray[15].callConstructor(SetCommand.class, (Object)this.shell), callSiteArray[16].callConstructor(GrabCommand.class, (Object)this.shell), callSiteArray[17].callConstructor(RegisterCommand.class, (Object)this.shell), callSiteArray[18].callConstructor(DocCommand.class, (Object)this.shell)})), Iterator.class);
        if (iterator != null) {
            while (iterator.hasNext()) {
                classname = (Command)ScriptBytecodeAdapter.castToType(iterator.next(), Command.class);
                callSiteArray[19].call((Object)this.shell, (Object)classname);
            }
        }
    }

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != DefaultCommandsRegistrar.class) {
            return ScriptBytecodeAdapter.initMetaClass((Object)this);
        }
        ClassInfo classInfo = $staticClassInfo;
        if (classInfo == null) {
            $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
        }
        return classInfo.getMetaClass();
    }

    @Generated
    @Internal
    @Transient
    public MetaClass getMetaClass() {
        MetaClass metaClass = this.metaClass;
        if (metaClass != null) {
            return metaClass;
        }
        this.metaClass = this.$getStaticMetaClass();
        return this.metaClass;
    }

    @Generated
    @Internal
    public void setMetaClass(MetaClass metaClass) {
        this.metaClass = metaClass;
    }

    public static /* synthetic */ MethodHandles.Lookup $getLookup() {
        return MethodHandles.lookup();
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
        stringArray[0] = "iterator";
        stringArray[1] = "<$constructor$>";
        stringArray[2] = "<$constructor$>";
        stringArray[3] = "<$constructor$>";
        stringArray[4] = "<$constructor$>";
        stringArray[5] = "<$constructor$>";
        stringArray[6] = "<$constructor$>";
        stringArray[7] = "<$constructor$>";
        stringArray[8] = "<$constructor$>";
        stringArray[9] = "<$constructor$>";
        stringArray[10] = "<$constructor$>";
        stringArray[11] = "<$constructor$>";
        stringArray[12] = "<$constructor$>";
        stringArray[13] = "<$constructor$>";
        stringArray[14] = "<$constructor$>";
        stringArray[15] = "<$constructor$>";
        stringArray[16] = "<$constructor$>";
        stringArray[17] = "<$constructor$>";
        stringArray[18] = "<$constructor$>";
        stringArray[19] = "register";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[20];
        DefaultCommandsRegistrar.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(DefaultCommandsRegistrar.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = DefaultCommandsRegistrar.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }
}

