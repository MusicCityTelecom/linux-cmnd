/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.GroovyObject
 *  groovy.lang.MetaClass
 *  groovy.transform.Generated
 *  groovy.transform.Internal
 *  jline.console.completer.AggregateCompleter
 *  org.codehaus.groovy.reflection.ClassInfo
 *  org.codehaus.groovy.runtime.BytecodeInterface8
 *  org.codehaus.groovy.runtime.GStringImpl
 *  org.codehaus.groovy.runtime.ScriptBytecodeAdapter
 *  org.codehaus.groovy.runtime.callsite.CallSite
 *  org.codehaus.groovy.runtime.callsite.CallSiteArray
 *  org.codehaus.groovy.runtime.powerassert.AssertionRenderer
 *  org.codehaus.groovy.runtime.powerassert.ValueRecorder
 *  org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation
 *  org.codehaus.groovy.tools.shell.util.Logger
 */
package org.apache.groovy.groovysh;

import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import groovy.transform.Generated;
import groovy.transform.Internal;
import java.beans.Transient;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import java.util.List;
import jline.console.completer.AggregateCompleter;
import org.apache.groovy.groovysh.Command;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.BytecodeInterface8;
import org.codehaus.groovy.runtime.GStringImpl;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.powerassert.AssertionRenderer;
import org.codehaus.groovy.runtime.powerassert.ValueRecorder;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;
import org.codehaus.groovy.tools.shell.util.Logger;

public class CommandsMultiCompleter
extends AggregateCompleter
implements GroovyObject {
    protected final Logger log;
    private List list;
    private boolean dirty;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private transient /* synthetic */ MetaClass metaClass;
    private static /* synthetic */ SoftReference $callSiteArray;

    @Generated
    public CommandsMultiCompleter() {
        MetaClass metaClass;
        boolean bl;
        List list;
        CallSite[] callSiteArray = CommandsMultiCompleter.$getCallSiteArray();
        Object object = callSiteArray[0].call(Logger.class, callSiteArray[1].callGroovyObjectGetProperty((Object)this));
        this.log = (Logger)ScriptBytecodeAdapter.castToType((Object)object, Logger.class);
        this.list = list = ScriptBytecodeAdapter.createList((Object[])new Object[0]);
        this.dirty = bl = false;
        this.metaClass = metaClass = this.$getStaticMetaClass();
    }

    public Object add(Command command) {
        CallSite[] callSiteArray = CommandsMultiCompleter.$getCallSiteArray();
        ValueRecorder valueRecorder = new ValueRecorder();
        try {
            Command command2 = command;
            valueRecorder.record((Object)command2, 8);
            if (DefaultTypeTransformation.booleanUnbox((Object)command2)) {
                valueRecorder.clear();
            } else {
                ScriptBytecodeAdapter.assertFailed((Object)AssertionRenderer.render((String)"assert command", (ValueRecorder)valueRecorder), null);
            }
        }
        catch (Throwable throwable) {
            valueRecorder.clear();
            throw throwable;
        }
        Object c = callSiteArray[2].callGetProperty((Object)command);
        if (DefaultTypeTransformation.booleanUnbox((Object)c)) {
            boolean bl;
            callSiteArray[3].call((Object)this.list, c);
            callSiteArray[4].call((Object)this.log, (Object)new GStringImpl(new Object[]{callSiteArray[5].call((Object)this.list), callSiteArray[6].callGetProperty((Object)command)}, new String[]{"Added completer[", "] for command: ", ""}));
            this.dirty = bl = true;
            return bl;
        }
        return null;
    }

    public void refresh() {
        boolean bl;
        CallSite[] callSiteArray = CommandsMultiCompleter.$getCallSiteArray();
        callSiteArray[7].call((Object)this.log, (Object)"Refreshing the completer list");
        callSiteArray[8].call(callSiteArray[9].callGroovyObjectGetProperty((Object)this));
        callSiteArray[10].call(callSiteArray[11].callGroovyObjectGetProperty((Object)this), (Object)this.list);
        this.dirty = bl = false;
    }

    public int complete(String buffer, int pos, List cand) {
        CallSite[] callSiteArray = CommandsMultiCompleter.$getCallSiteArray();
        ValueRecorder valueRecorder = new ValueRecorder();
        try {
            String string = buffer;
            valueRecorder.record((Object)string, 8);
            boolean bl = ScriptBytecodeAdapter.compareNotEqual((Object)string, null);
            valueRecorder.record((Object)bl, 15);
            if (bl) {
                valueRecorder.clear();
            } else {
                ScriptBytecodeAdapter.assertFailed((Object)AssertionRenderer.render((String)"assert buffer != null", (ValueRecorder)valueRecorder), null);
            }
        }
        catch (Throwable throwable) {
            valueRecorder.clear();
            throw throwable;
        }
        if (__$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            if (this.dirty) {
                callSiteArray[12].callCurrent((GroovyObject)this);
            }
        } else if (this.dirty) {
            this.refresh();
        }
        return DefaultTypeTransformation.intUnbox((Object)ScriptBytecodeAdapter.invokeMethodOnSuperN(CommandsMultiCompleter.class, (GroovyObject)this, (String)"complete", (Object[])new Object[]{buffer, pos, cand}));
    }

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (((Object)((Object)this)).getClass() != CommandsMultiCompleter.class) {
            return ScriptBytecodeAdapter.initMetaClass((Object)((Object)this));
        }
        ClassInfo classInfo = $staticClassInfo;
        if (classInfo == null) {
            $staticClassInfo = classInfo = ClassInfo.getClassInfo(((Object)((Object)this)).getClass());
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

    @Generated
    public List getList() {
        return this.list;
    }

    @Generated
    public void setList(List list) {
        this.list = list;
    }

    public /* synthetic */ int super$2$complete(String string, int n, List list) {
        return super.complete(string, n, list);
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
        stringArray[0] = "create";
        stringArray[1] = "class";
        stringArray[2] = "completer";
        stringArray[3] = "leftShift";
        stringArray[4] = "debug";
        stringArray[5] = "size";
        stringArray[6] = "name";
        stringArray[7] = "debug";
        stringArray[8] = "clear";
        stringArray[9] = "completers";
        stringArray[10] = "addAll";
        stringArray[11] = "completers";
        stringArray[12] = "refresh";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[13];
        CommandsMultiCompleter.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(CommandsMultiCompleter.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = CommandsMultiCompleter.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }
}

