/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.Closure
 *  groovy.lang.GroovyObject
 *  groovy.lang.MetaClass
 *  groovy.transform.Generated
 *  groovy.transform.Internal
 *  org.codehaus.groovy.reflection.ClassInfo
 *  org.codehaus.groovy.runtime.BytecodeInterface8
 *  org.codehaus.groovy.runtime.GStringImpl
 *  org.codehaus.groovy.runtime.GeneratedClosure
 *  org.codehaus.groovy.runtime.ScriptBytecodeAdapter
 *  org.codehaus.groovy.runtime.callsite.CallSite
 *  org.codehaus.groovy.runtime.callsite.CallSiteArray
 *  org.codehaus.groovy.runtime.powerassert.AssertionRenderer
 *  org.codehaus.groovy.runtime.powerassert.ValueRecorder
 *  org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation
 *  org.codehaus.groovy.tools.shell.util.Logger
 */
package org.apache.groovy.groovysh;

import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import groovy.transform.Generated;
import groovy.transform.Internal;
import java.beans.Transient;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.apache.groovy.groovysh.Command;
import org.apache.groovy.groovysh.CommandSupport;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.BytecodeInterface8;
import org.codehaus.groovy.runtime.GStringImpl;
import org.codehaus.groovy.runtime.GeneratedClosure;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.powerassert.AssertionRenderer;
import org.codehaus.groovy.runtime.powerassert.ValueRecorder;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;
import org.codehaus.groovy.tools.shell.util.Logger;

public class CommandRegistry
implements GroovyObject {
    protected final Logger log;
    private final List<Command> commandList;
    private final Set<String> names;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private transient /* synthetic */ MetaClass metaClass;
    private static /* synthetic */ SoftReference $callSiteArray;

    @Generated
    public CommandRegistry() {
        MetaClass metaClass;
        List list;
        CallSite[] callSiteArray = CommandRegistry.$getCallSiteArray();
        Object object = callSiteArray[0].call(Logger.class, CommandRegistry.class);
        this.log = (Logger)ScriptBytecodeAdapter.castToType((Object)object, Logger.class);
        this.commandList = list = ScriptBytecodeAdapter.createList((Object[])new Object[0]);
        Object object2 = callSiteArray[1].callConstructor(TreeSet.class);
        this.names = (Set)ScriptBytecodeAdapter.castToType((Object)object2, Set.class);
        this.metaClass = metaClass = this.$getStaticMetaClass();
    }

    public Command register(Command command) {
        CallSite[] callSiteArray = CommandRegistry.$getCallSiteArray();
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
        if (!(!DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[2].call(this.names, callSiteArray[3].callGetProperty((Object)command))))) {
            ScriptBytecodeAdapter.assertFailed((Object)"names.contains(command.name)", (Object)new GStringImpl(new Object[]{callSiteArray[4].callGetProperty((Object)command)}, new String[]{"Duplicate command name: ", ""}));
        }
        callSiteArray[5].call(this.names, callSiteArray[6].callGetProperty((Object)command));
        if (!(!DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[7].call(this.names, callSiteArray[8].callGetProperty((Object)command))))) {
            ScriptBytecodeAdapter.assertFailed((Object)"names.contains(command.shortcut)", (Object)new GStringImpl(new Object[]{callSiteArray[9].callGetProperty((Object)command)}, new String[]{"Duplicate command shortcut: ", ""}));
        }
        callSiteArray[10].call(this.names, callSiteArray[11].callGetProperty((Object)command));
        callSiteArray[12].call(this.commandList, (Object)command);
        if (command instanceof CommandSupport) {
            CommandRegistry commandRegistry = this;
            ScriptBytecodeAdapter.setGroovyObjectProperty((Object)commandRegistry, CommandRegistry.class, (GroovyObject)((CommandSupport)ScriptBytecodeAdapter.castToType((Object)command, CommandSupport.class)), (String)"registry");
        }
        public final class _register_closure1
        extends Closure
        implements GeneratedClosure {
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _register_closure1(Object _outerInstance, Object _thisObject) {
                CallSite[] callSiteArray = _register_closure1.$getCallSiteArray();
                super(_outerInstance, _thisObject);
            }

            public Object doCall(Command it) {
                CallSite[] callSiteArray = _register_closure1.$getCallSiteArray();
                return callSiteArray[0].callCurrent((GroovyObject)this.getThisObject(), (Object)it);
            }

            @Generated
            public Object call(Command it) {
                CallSite[] callSiteArray = _register_closure1.$getCallSiteArray();
                return callSiteArray[1].callCurrent((GroovyObject)this, (Object)it);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (((Object)((Object)this)).getClass() != _register_closure1.class) {
                    return ScriptBytecodeAdapter.initMetaClass((Object)((Object)this));
                }
                ClassInfo classInfo = $staticClassInfo;
                if (classInfo == null) {
                    $staticClassInfo = classInfo = ClassInfo.getClassInfo(((Object)((Object)this)).getClass());
                }
                return classInfo.getMetaClass();
            }

            public /* synthetic */ MethodHandles.Lookup $getLookup() {
                return MethodHandles.lookup();
            }

            private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
                stringArray[0] = "register";
                stringArray[1] = "doCall";
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[2];
                _register_closure1.$createCallSiteArray_1(stringArray);
                return new CallSiteArray(_register_closure1.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _register_closure1.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        callSiteArray[13].callSafe(callSiteArray[14].callGetProperty((Object)command), (Object)new _register_closure1(this, this));
        if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[15].callGetProperty((Object)this.log))) {
            callSiteArray[16].call((Object)this.log, (Object)new GStringImpl(new Object[]{callSiteArray[17].callGetProperty((Object)command)}, new String[]{"Registered command: ", ""}));
        }
        return command;
    }

    public Command find(String name) {
        CallSite[] callSiteArray = CommandRegistry.$getCallSiteArray();
        ValueRecorder valueRecorder = new ValueRecorder();
        try {
            String string = name;
            valueRecorder.record((Object)string, 8);
            if (DefaultTypeTransformation.booleanUnbox((Object)string)) {
                valueRecorder.clear();
            } else {
                ScriptBytecodeAdapter.assertFailed((Object)AssertionRenderer.render((String)"assert name", (ValueRecorder)valueRecorder), null);
            }
        }
        catch (Throwable throwable) {
            valueRecorder.clear();
            throw throwable;
        }
        Object c = null;
        Iterator iterator = (Iterator)ScriptBytecodeAdapter.castToType((Object)callSiteArray[18].call(this.commandList), Iterator.class);
        if (iterator != null) {
            while (iterator.hasNext()) {
                c = iterator.next();
                if (ScriptBytecodeAdapter.isCase((Object)name, (Object)ScriptBytecodeAdapter.createList((Object[])new Object[]{callSiteArray[19].callGetProperty(c), callSiteArray[20].callGetProperty(c)}))) {
                    return (Command)ScriptBytecodeAdapter.castToType(c, Command.class);
                }
                if (!(!DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[21].call(callSiteArray[22].callGetProperty(c), (Object)":")) && DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[23].call((Object)name, callSiteArray[24].call((Object)":", callSiteArray[25].callGetProperty(c)))))) continue;
                return (Command)ScriptBytecodeAdapter.castToType(c, Command.class);
            }
        }
        return (Command)ScriptBytecodeAdapter.castToType(null, Command.class);
    }

    public void remove(Command command) {
        CallSite[] callSiteArray = CommandRegistry.$getCallSiteArray();
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
        callSiteArray[26].call(this.commandList, (Object)command);
        callSiteArray[27].call(this.names, callSiteArray[28].callGetProperty((Object)command));
        callSiteArray[29].call(this.names, callSiteArray[30].callGetProperty((Object)command));
        if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[31].callGetProperty((Object)this.log))) {
            callSiteArray[32].call((Object)this.log, (Object)new GStringImpl(new Object[]{callSiteArray[33].callGetProperty((Object)command)}, new String[]{"Removed command: ", ""}));
        }
    }

    public List<Command> commands() {
        CallSite[] callSiteArray = CommandRegistry.$getCallSiteArray();
        return this.commandList;
    }

    public Command getProperty(String name) {
        CallSite[] callSiteArray = CommandRegistry.$getCallSiteArray();
        if (__$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            return (Command)ScriptBytecodeAdapter.castToType((Object)callSiteArray[34].callCurrent((GroovyObject)this, (Object)name), Command.class);
        }
        return this.find(name);
    }

    public Iterator iterator() {
        CallSite[] callSiteArray = CommandRegistry.$getCallSiteArray();
        if (__$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            return (Iterator)ScriptBytecodeAdapter.castToType((Object)callSiteArray[35].call(callSiteArray[36].callCurrent((GroovyObject)this)), Iterator.class);
        }
        return (Iterator)ScriptBytecodeAdapter.castToType((Object)callSiteArray[37].call(this.commands()), Iterator.class);
    }

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != CommandRegistry.class) {
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

    @Generated
    public final List<Command> getCommandList() {
        return this.commandList;
    }

    public /* synthetic */ Object super$1$getProperty(String string) {
        return super.getProperty(string);
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
        stringArray[0] = "create";
        stringArray[1] = "<$constructor$>";
        stringArray[2] = "contains";
        stringArray[3] = "name";
        stringArray[4] = "name";
        stringArray[5] = "leftShift";
        stringArray[6] = "name";
        stringArray[7] = "contains";
        stringArray[8] = "shortcut";
        stringArray[9] = "shortcut";
        stringArray[10] = "leftShift";
        stringArray[11] = "shortcut";
        stringArray[12] = "leftShift";
        stringArray[13] = "each";
        stringArray[14] = "aliases";
        stringArray[15] = "debugEnabled";
        stringArray[16] = "debug";
        stringArray[17] = "name";
        stringArray[18] = "iterator";
        stringArray[19] = "name";
        stringArray[20] = "shortcut";
        stringArray[21] = "startsWith";
        stringArray[22] = "name";
        stringArray[23] = "equals";
        stringArray[24] = "plus";
        stringArray[25] = "name";
        stringArray[26] = "remove";
        stringArray[27] = "remove";
        stringArray[28] = "name";
        stringArray[29] = "remove";
        stringArray[30] = "shortcut";
        stringArray[31] = "debugEnabled";
        stringArray[32] = "debug";
        stringArray[33] = "name";
        stringArray[34] = "find";
        stringArray[35] = "iterator";
        stringArray[36] = "commands";
        stringArray[37] = "iterator";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[38];
        CommandRegistry.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(CommandRegistry.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = CommandRegistry.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }
}

