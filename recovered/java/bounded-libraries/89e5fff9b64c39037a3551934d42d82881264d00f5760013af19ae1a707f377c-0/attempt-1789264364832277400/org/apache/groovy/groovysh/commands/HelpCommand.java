/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.GroovyObject
 *  groovy.lang.MetaClass
 *  jline.console.completer.Completer
 *  org.codehaus.groovy.reflection.ClassInfo
 *  org.codehaus.groovy.runtime.BytecodeInterface8
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
import java.util.Iterator;
import java.util.List;
import jline.console.completer.Completer;
import org.apache.groovy.groovysh.Command;
import org.apache.groovy.groovysh.CommandSupport;
import org.apache.groovy.groovysh.Groovysh;
import org.apache.groovy.groovysh.completion.CommandNameCompleter;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.BytecodeInterface8;
import org.codehaus.groovy.runtime.GStringImpl;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.powerassert.AssertionRenderer;
import org.codehaus.groovy.runtime.powerassert.ValueRecorder;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;
import org.codehaus.groovy.runtime.typehandling.ShortTypeHandling;

public class HelpCommand
extends CommandSupport {
    public static final String COMMAND_NAME = ":help";
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private static /* synthetic */ SoftReference $callSiteArray;

    public HelpCommand(Groovysh shell) {
        CallSite[] callSiteArray = HelpCommand.$getCallSiteArray();
        super(shell, ShortTypeHandling.castToString((Object)callSiteArray[0].callGetProperty(HelpCommand.class)), ":h");
        callSiteArray[1].callCurrent((GroovyObject)this, (Object)"?", (Object)":?");
    }

    @Override
    protected List<Completer> createCompleters() {
        CallSite[] callSiteArray = HelpCommand.$getCallSiteArray();
        return ScriptBytecodeAdapter.createList((Object[])new Object[]{callSiteArray[2].callConstructor(CommandNameCompleter.class, callSiteArray[3].callGroovyObjectGetProperty((Object)this), (Object)false), null});
    }

    @Override
    public Object execute(List<String> args) {
        CallSite[] callSiteArray = HelpCommand.$getCallSiteArray();
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
        if (ScriptBytecodeAdapter.compareGreaterThan((Object)callSiteArray[4].call(args), (Object)1)) {
            callSiteArray[5].callCurrent((GroovyObject)this, callSiteArray[6].call(callSiteArray[7].callGroovyObjectGetProperty((Object)this), (Object)"error.unexpected_args", callSiteArray[8].call(args, (Object)" ")));
        }
        if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[9].call(args), (Object)1)) {
                return callSiteArray[10].callCurrent((GroovyObject)this, callSiteArray[11].call(args, (Object)0));
            }
            return callSiteArray[12].callCurrent((GroovyObject)this);
        }
        if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[13].call(args), (Object)1)) {
            return callSiteArray[14].callCurrent((GroovyObject)this, callSiteArray[15].call(args, (Object)0));
        }
        this.list();
        return null;
    }

    private void help(String name) {
        CallSite[] callSiteArray = HelpCommand.$getCallSiteArray();
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
        Command command = (Command)ScriptBytecodeAdapter.castToType((Object)callSiteArray[16].call(callSiteArray[17].callGroovyObjectGetProperty((Object)this), (Object)name), Command.class);
        if (!DefaultTypeTransformation.booleanUnbox((Object)command)) {
            callSiteArray[18].callCurrent((GroovyObject)this, (Object)new GStringImpl(new Object[]{name}, new String[]{"No such command: ", ""}));
        }
        callSiteArray[19].call(callSiteArray[20].callGetProperty(callSiteArray[21].callGroovyObjectGetProperty((Object)this)));
        callSiteArray[22].call(callSiteArray[23].callGetProperty(callSiteArray[24].callGroovyObjectGetProperty((Object)this)), (Object)new GStringImpl(new Object[]{callSiteArray[25].callGetProperty((Object)command), callSiteArray[26].callGetProperty((Object)command)}, new String[]{"usage: @|bold ", "|@ ", ""}));
        callSiteArray[27].call(callSiteArray[28].callGetProperty(callSiteArray[29].callGroovyObjectGetProperty((Object)this)));
        callSiteArray[30].call(callSiteArray[31].callGetProperty(callSiteArray[32].callGroovyObjectGetProperty((Object)this)), callSiteArray[33].callGetProperty((Object)command));
        callSiteArray[34].call(callSiteArray[35].callGetProperty(callSiteArray[36].callGroovyObjectGetProperty((Object)this)));
    }

    private void list() {
        CallSite[] callSiteArray = HelpCommand.$getCallSiteArray();
        int maxName = 0;
        int maxShortcut = 0;
        Command command = null;
        Iterator iterator = (Iterator)ScriptBytecodeAdapter.castToType((Object)callSiteArray[37].call(callSiteArray[38].call(callSiteArray[39].callGroovyObjectGetProperty((Object)this))), Iterator.class);
        if (iterator != null) {
            while (iterator.hasNext()) {
                command = (Command)ScriptBytecodeAdapter.castToType(iterator.next(), Command.class);
                if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[40].callGetProperty((Object)command))) continue;
                if (ScriptBytecodeAdapter.compareGreaterThan((Object)callSiteArray[41].call(callSiteArray[42].callGetProperty((Object)command)), (Object)maxName)) {
                    Object object = callSiteArray[43].call(callSiteArray[44].callGetProperty((Object)command));
                    maxName = DefaultTypeTransformation.intUnbox((Object)object);
                }
                if (!ScriptBytecodeAdapter.compareGreaterThan((Object)callSiteArray[45].call(callSiteArray[46].callGetProperty((Object)command)), (Object)maxShortcut)) continue;
                Object object = callSiteArray[47].call(callSiteArray[48].callGetProperty((Object)command));
                maxShortcut = DefaultTypeTransformation.intUnbox((Object)object);
            }
        }
        callSiteArray[49].call(callSiteArray[50].callGetProperty(callSiteArray[51].callGroovyObjectGetProperty((Object)this)));
        callSiteArray[52].call(callSiteArray[53].callGetProperty(callSiteArray[54].callGroovyObjectGetProperty((Object)this)), (Object)"For information about @|green Groovy|@, visit:");
        callSiteArray[55].call(callSiteArray[56].callGetProperty(callSiteArray[57].callGroovyObjectGetProperty((Object)this)), (Object)"    @|cyan http://groovy-lang.org|@ ");
        callSiteArray[58].call(callSiteArray[59].callGetProperty(callSiteArray[60].callGroovyObjectGetProperty((Object)this)));
        callSiteArray[61].call(callSiteArray[62].callGetProperty(callSiteArray[63].callGroovyObjectGetProperty((Object)this)), (Object)"Available commands:");
        Command command2 = null;
        Iterator iterator2 = (Iterator)ScriptBytecodeAdapter.castToType((Object)callSiteArray[64].call(callSiteArray[65].call(callSiteArray[66].callGroovyObjectGetProperty((Object)this))), Iterator.class);
        if (iterator2 != null) {
            while (iterator2.hasNext()) {
                command2 = (Command)ScriptBytecodeAdapter.castToType(iterator2.next(), Command.class);
                if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[67].callGetProperty((Object)command2))) continue;
                Object n = callSiteArray[68].call(callSiteArray[69].callGetProperty((Object)command2), (Object)maxName, (Object)" ");
                Object s = callSiteArray[70].call(callSiteArray[71].callGetProperty((Object)command2), (Object)maxShortcut, (Object)" ");
                Object d = callSiteArray[72].callGetProperty((Object)command2);
                callSiteArray[73].call(callSiteArray[74].callGetProperty(callSiteArray[75].callGroovyObjectGetProperty((Object)this)), (Object)new GStringImpl(new Object[]{n, s, d}, new String[]{"  @|bold ", "|@  (@|bold ", "|@) ", ""}));
            }
        }
        callSiteArray[76].call(callSiteArray[77].callGetProperty(callSiteArray[78].callGroovyObjectGetProperty((Object)this)));
        callSiteArray[79].call(callSiteArray[80].callGetProperty(callSiteArray[81].callGroovyObjectGetProperty((Object)this)), (Object)"For help on a specific command type:");
        callSiteArray[82].call(callSiteArray[83].callGetProperty(callSiteArray[84].callGroovyObjectGetProperty((Object)this)), (Object)"    :help @|bold command|@ ");
        callSiteArray[85].call(callSiteArray[86].callGetProperty(callSiteArray[87].callGroovyObjectGetProperty((Object)this)));
    }

    @Override
    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != HelpCommand.class) {
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
        stringArray[1] = "alias";
        stringArray[2] = "<$constructor$>";
        stringArray[3] = "registry";
        stringArray[4] = "size";
        stringArray[5] = "fail";
        stringArray[6] = "format";
        stringArray[7] = "messages";
        stringArray[8] = "join";
        stringArray[9] = "size";
        stringArray[10] = "help";
        stringArray[11] = "getAt";
        stringArray[12] = "list";
        stringArray[13] = "size";
        stringArray[14] = "help";
        stringArray[15] = "getAt";
        stringArray[16] = "find";
        stringArray[17] = "registry";
        stringArray[18] = "fail";
        stringArray[19] = "println";
        stringArray[20] = "out";
        stringArray[21] = "io";
        stringArray[22] = "println";
        stringArray[23] = "out";
        stringArray[24] = "io";
        stringArray[25] = "name";
        stringArray[26] = "usage";
        stringArray[27] = "println";
        stringArray[28] = "out";
        stringArray[29] = "io";
        stringArray[30] = "println";
        stringArray[31] = "out";
        stringArray[32] = "io";
        stringArray[33] = "help";
        stringArray[34] = "println";
        stringArray[35] = "out";
        stringArray[36] = "io";
        stringArray[37] = "iterator";
        stringArray[38] = "commands";
        stringArray[39] = "registry";
        stringArray[40] = "hidden";
        stringArray[41] = "size";
        stringArray[42] = "name";
        stringArray[43] = "size";
        stringArray[44] = "name";
        stringArray[45] = "size";
        stringArray[46] = "shortcut";
        stringArray[47] = "size";
        stringArray[48] = "shortcut";
        stringArray[49] = "println";
        stringArray[50] = "out";
        stringArray[51] = "io";
        stringArray[52] = "println";
        stringArray[53] = "out";
        stringArray[54] = "io";
        stringArray[55] = "println";
        stringArray[56] = "out";
        stringArray[57] = "io";
        stringArray[58] = "println";
        stringArray[59] = "out";
        stringArray[60] = "io";
        stringArray[61] = "println";
        stringArray[62] = "out";
        stringArray[63] = "io";
        stringArray[64] = "iterator";
        stringArray[65] = "commands";
        stringArray[66] = "registry";
        stringArray[67] = "hidden";
        stringArray[68] = "padRight";
        stringArray[69] = "name";
        stringArray[70] = "padRight";
        stringArray[71] = "shortcut";
        stringArray[72] = "description";
        stringArray[73] = "println";
        stringArray[74] = "out";
        stringArray[75] = "io";
        stringArray[76] = "println";
        stringArray[77] = "out";
        stringArray[78] = "io";
        stringArray[79] = "println";
        stringArray[80] = "out";
        stringArray[81] = "io";
        stringArray[82] = "println";
        stringArray[83] = "out";
        stringArray[84] = "io";
        stringArray[85] = "println";
        stringArray[86] = "out";
        stringArray[87] = "io";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[88];
        HelpCommand.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(HelpCommand.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = HelpCommand.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }
}

