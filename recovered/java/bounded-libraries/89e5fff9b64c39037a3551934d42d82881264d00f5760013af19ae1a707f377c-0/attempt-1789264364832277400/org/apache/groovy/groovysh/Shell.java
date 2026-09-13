/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.GroovyObject
 *  groovy.lang.MetaClass
 *  groovy.transform.Generated
 *  groovy.transform.Internal
 *  org.codehaus.groovy.reflection.ClassInfo
 *  org.codehaus.groovy.runtime.BytecodeInterface8
 *  org.codehaus.groovy.runtime.FormatHelper
 *  org.codehaus.groovy.runtime.GStringImpl
 *  org.codehaus.groovy.runtime.ScriptBytecodeAdapter
 *  org.codehaus.groovy.runtime.callsite.CallSite
 *  org.codehaus.groovy.runtime.callsite.CallSiteArray
 *  org.codehaus.groovy.runtime.powerassert.AssertionRenderer
 *  org.codehaus.groovy.runtime.powerassert.ValueRecorder
 *  org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation
 *  org.codehaus.groovy.tools.shell.IO
 *  org.codehaus.groovy.tools.shell.util.Logger
 *  org.fusesource.jansi.Ansi
 *  org.fusesource.jansi.Ansi$Attribute
 *  org.fusesource.jansi.Ansi$Color
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
import org.apache.groovy.groovysh.Command;
import org.apache.groovy.groovysh.CommandException;
import org.apache.groovy.groovysh.CommandRegistry;
import org.apache.groovy.groovysh.util.CommandArgumentParser;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.BytecodeInterface8;
import org.codehaus.groovy.runtime.FormatHelper;
import org.codehaus.groovy.runtime.GStringImpl;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.powerassert.AssertionRenderer;
import org.codehaus.groovy.runtime.powerassert.ValueRecorder;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;
import org.codehaus.groovy.tools.shell.IO;
import org.codehaus.groovy.tools.shell.util.Logger;
import org.fusesource.jansi.Ansi;

public class Shell
implements GroovyObject {
    protected final Logger log;
    private final CommandRegistry registry;
    private final IO io;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private transient /* synthetic */ MetaClass metaClass;
    private static /* synthetic */ SoftReference $callSiteArray;

    public Shell(IO io) {
        IO iO;
        MetaClass metaClass;
        CallSite[] callSiteArray = Shell.$getCallSiteArray();
        Object object = callSiteArray[0].call(Logger.class, callSiteArray[1].callGroovyObjectGetProperty((Object)this));
        this.log = (Logger)ScriptBytecodeAdapter.castToType((Object)object, Logger.class);
        Object object2 = callSiteArray[2].callConstructor(CommandRegistry.class);
        this.registry = (CommandRegistry)ScriptBytecodeAdapter.castToType((Object)object2, CommandRegistry.class);
        this.metaClass = metaClass = this.$getStaticMetaClass();
        ValueRecorder valueRecorder = new ValueRecorder();
        try {
            IO iO2 = io;
            valueRecorder.record((Object)iO2, 8);
            boolean bl = ScriptBytecodeAdapter.compareNotEqual((Object)iO2, null);
            valueRecorder.record((Object)bl, 11);
            if (bl) {
                valueRecorder.clear();
            } else {
                ScriptBytecodeAdapter.assertFailed((Object)AssertionRenderer.render((String)"assert(io != null)", (ValueRecorder)valueRecorder), null);
            }
        }
        catch (Throwable throwable) {
            valueRecorder.clear();
            throw throwable;
        }
        this.io = iO = io;
    }

    public Shell() {
        CallSite[] callSiteArray = Shell.$getCallSiteArray();
        this((IO)ScriptBytecodeAdapter.castToType((Object)callSiteArray[3].callConstructor(IO.class), IO.class));
    }

    public Command findCommand(String line, List<String> parsedArgs) {
        CallSite[] callSiteArray = Shell.$getCallSiteArray();
        ValueRecorder valueRecorder = new ValueRecorder();
        try {
            String string = line;
            valueRecorder.record((Object)string, 8);
            if (DefaultTypeTransformation.booleanUnbox((Object)string)) {
                valueRecorder.clear();
            } else {
                ScriptBytecodeAdapter.assertFailed((Object)AssertionRenderer.render((String)"assert line", (ValueRecorder)valueRecorder), null);
            }
        }
        catch (Throwable throwable) {
            valueRecorder.clear();
            throw throwable;
        }
        Command command = null;
        List linetokens = (List)ScriptBytecodeAdapter.castToType((Object)callSiteArray[4].call(callSiteArray[5].call((Object)line)), List.class);
        ValueRecorder valueRecorder2 = new ValueRecorder();
        try {
            CallSite callSite = callSiteArray[6];
            List list = linetokens;
            valueRecorder2.record((Object)list, 8);
            Object object = callSite.call((Object)list);
            valueRecorder2.record(object, 19);
            boolean bl = ScriptBytecodeAdapter.compareGreaterThan((Object)object, (Object)0);
            valueRecorder2.record((Object)bl, 26);
            if (bl) {
                valueRecorder2.clear();
            } else {
                ScriptBytecodeAdapter.assertFailed((Object)AssertionRenderer.render((String)"assert linetokens.size() > 0", (ValueRecorder)valueRecorder2), null);
            }
        }
        catch (Throwable throwable) {
            valueRecorder2.clear();
            throw throwable;
        }
        if (ScriptBytecodeAdapter.compareGreaterThan((Object)callSiteArray[7].call(callSiteArray[8].call((Object)linetokens, (Object)0)), (Object)0)) {
            Object object = callSiteArray[9].call((Object)this.registry, callSiteArray[10].call((Object)linetokens, (Object)0));
            command = (Command)ScriptBytecodeAdapter.castToType((Object)object, Command.class);
            if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                if (ScriptBytecodeAdapter.compareNotEqual((Object)command, null) && ScriptBytecodeAdapter.compareGreaterThan((Object)callSiteArray[11].call((Object)linetokens), (Object)1) && ScriptBytecodeAdapter.compareNotEqual(parsedArgs, null)) {
                    List args = (List)ScriptBytecodeAdapter.castToType((Object)callSiteArray[12].call(CommandArgumentParser.class, (Object)line, (Object)(ScriptBytecodeAdapter.compareEqual(parsedArgs, null) ? 1 : -1)), List.class);
                    callSiteArray[13].call(parsedArgs, callSiteArray[14].call((Object)args, (Object)ScriptBytecodeAdapter.createRange((Object)1, (Object)-1, (boolean)false, (boolean)false)));
                }
            } else if (ScriptBytecodeAdapter.compareNotEqual((Object)command, null) && ScriptBytecodeAdapter.compareGreaterThan((Object)callSiteArray[15].call((Object)linetokens), (Object)1) && ScriptBytecodeAdapter.compareNotEqual(parsedArgs, null)) {
                List args = (List)ScriptBytecodeAdapter.castToType((Object)callSiteArray[16].call(CommandArgumentParser.class, (Object)line, (Object)(ScriptBytecodeAdapter.compareEqual(parsedArgs, null) ? 1 : -1)), List.class);
                callSiteArray[17].call(parsedArgs, callSiteArray[18].call((Object)args, (Object)ScriptBytecodeAdapter.createRange((Object)1, (Object)-1, (boolean)false, (boolean)false)));
            }
        }
        return command;
    }

    public boolean isExecutable(String line) {
        CallSite[] callSiteArray = Shell.$getCallSiteArray();
        if (!BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            return ScriptBytecodeAdapter.compareNotEqual((Object)callSiteArray[19].callCurrent((GroovyObject)this, (Object)line), null);
        }
        return ScriptBytecodeAdapter.compareNotEqual((Object)this.findCommand(line), null);
    }

    public Object execute(String line) {
        CallSite[] callSiteArray = Shell.$getCallSiteArray();
        ValueRecorder valueRecorder = new ValueRecorder();
        try {
            String string = line;
            valueRecorder.record((Object)string, 8);
            if (DefaultTypeTransformation.booleanUnbox((Object)string)) {
                valueRecorder.clear();
            } else {
                ScriptBytecodeAdapter.assertFailed((Object)AssertionRenderer.render((String)"assert line", (ValueRecorder)valueRecorder), null);
            }
        }
        catch (Throwable throwable) {
            valueRecorder.clear();
            throw throwable;
        }
        List args = ScriptBytecodeAdapter.createList((Object[])new Object[0]);
        Command command = (Command)ScriptBytecodeAdapter.castToType((Object)callSiteArray[20].callCurrent((GroovyObject)this, (Object)line, (Object)args), Command.class);
        Object result = null;
        if (DefaultTypeTransformation.booleanUnbox((Object)command)) {
            callSiteArray[21].call((Object)this.log, (Object)new GStringImpl(new Object[]{callSiteArray[22].callGetProperty((Object)command), command, args}, new String[]{"Executing command(", "): ", "; w/args: ", ""}));
            try {
                Object object;
                result = object = callSiteArray[23].call((Object)command, (Object)args);
            }
            catch (CommandException e) {
                callSiteArray[24].call(callSiteArray[25].callGetProperty((Object)this.io), callSiteArray[26].call(callSiteArray[27].call(callSiteArray[28].call(callSiteArray[29].call(callSiteArray[30].callStatic(Ansi.class), callSiteArray[31].callGetProperty(Ansi.Attribute.class)), callSiteArray[32].callGetProperty(Ansi.Color.class)), callSiteArray[33].callGroovyObjectGetProperty((Object)e))));
            }
            callSiteArray[34].call((Object)this.log, (Object)new GStringImpl(new Object[]{callSiteArray[35].call(FormatHelper.class, result)}, new String[]{"Result: ", ""}));
        }
        return result;
    }

    public Command register(Command command) {
        CallSite[] callSiteArray = Shell.$getCallSiteArray();
        return (Command)ScriptBytecodeAdapter.castToType((Object)callSiteArray[36].call((Object)this.registry, (Object)command), Command.class);
    }

    public Object leftShift(String line) {
        CallSite[] callSiteArray = Shell.$getCallSiteArray();
        if (__$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            return callSiteArray[37].callCurrent((GroovyObject)this, (Object)line);
        }
        return this.execute(line);
    }

    public Command leftShift(Command command) {
        CallSite[] callSiteArray = Shell.$getCallSiteArray();
        return (Command)ScriptBytecodeAdapter.castToType((Object)callSiteArray[38].callCurrent((GroovyObject)this, (Object)command), Command.class);
    }

    @Generated
    public Command findCommand(String line) {
        CallSite[] callSiteArray = Shell.$getCallSiteArray();
        return this.findCommand(line, null);
    }

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != Shell.class) {
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
    public final CommandRegistry getRegistry() {
        return this.registry;
    }

    @Generated
    public final IO getIo() {
        return this.io;
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
        stringArray[0] = "create";
        stringArray[1] = "class";
        stringArray[2] = "<$constructor$>";
        stringArray[3] = "<$constructor$>";
        stringArray[4] = "tokenize";
        stringArray[5] = "trim";
        stringArray[6] = "size";
        stringArray[7] = "length";
        stringArray[8] = "getAt";
        stringArray[9] = "find";
        stringArray[10] = "getAt";
        stringArray[11] = "size";
        stringArray[12] = "parseLine";
        stringArray[13] = "addAll";
        stringArray[14] = "getAt";
        stringArray[15] = "size";
        stringArray[16] = "parseLine";
        stringArray[17] = "addAll";
        stringArray[18] = "getAt";
        stringArray[19] = "findCommand";
        stringArray[20] = "findCommand";
        stringArray[21] = "debug";
        stringArray[22] = "name";
        stringArray[23] = "execute";
        stringArray[24] = "println";
        stringArray[25] = "err";
        stringArray[26] = "reset";
        stringArray[27] = "a";
        stringArray[28] = "fg";
        stringArray[29] = "a";
        stringArray[30] = "ansi";
        stringArray[31] = "INTENSITY_BOLD";
        stringArray[32] = "RED";
        stringArray[33] = "message";
        stringArray[34] = "debug";
        stringArray[35] = "toString";
        stringArray[36] = "register";
        stringArray[37] = "execute";
        stringArray[38] = "register";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[39];
        Shell.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(Shell.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = Shell.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }
}

