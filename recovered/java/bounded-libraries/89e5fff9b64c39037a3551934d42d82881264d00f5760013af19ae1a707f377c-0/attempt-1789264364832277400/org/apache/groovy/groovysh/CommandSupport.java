/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.Binding
 *  groovy.lang.Closure
 *  groovy.lang.GroovyClassLoader
 *  groovy.lang.GroovyObject
 *  groovy.lang.MetaClass
 *  groovy.lang.Reference
 *  groovy.transform.Generated
 *  groovy.transform.Internal
 *  jline.console.completer.Completer
 *  jline.console.completer.NullCompleter
 *  jline.console.completer.StringsCompleter
 *  jline.console.history.FileHistory
 *  org.codehaus.groovy.reflection.ClassInfo
 *  org.codehaus.groovy.runtime.BytecodeInterface8
 *  org.codehaus.groovy.runtime.GeneratedClosure
 *  org.codehaus.groovy.runtime.ScriptBytecodeAdapter
 *  org.codehaus.groovy.runtime.callsite.CallSite
 *  org.codehaus.groovy.runtime.callsite.CallSiteArray
 *  org.codehaus.groovy.runtime.powerassert.AssertionRenderer
 *  org.codehaus.groovy.runtime.powerassert.ValueRecorder
 *  org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation
 *  org.codehaus.groovy.runtime.typehandling.ShortTypeHandling
 *  org.codehaus.groovy.tools.shell.IO
 *  org.codehaus.groovy.tools.shell.util.Logger
 *  org.codehaus.groovy.tools.shell.util.MessageSource
 */
package org.apache.groovy.groovysh;

import groovy.lang.Binding;
import groovy.lang.Closure;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import groovy.lang.Reference;
import groovy.transform.Generated;
import groovy.transform.Internal;
import java.beans.Transient;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import jline.console.completer.Completer;
import jline.console.completer.NullCompleter;
import jline.console.completer.StringsCompleter;
import jline.console.history.FileHistory;
import org.apache.groovy.groovysh.BufferManager;
import org.apache.groovy.groovysh.Command;
import org.apache.groovy.groovysh.CommandAlias;
import org.apache.groovy.groovysh.CommandException;
import org.apache.groovy.groovysh.CommandRegistry;
import org.apache.groovy.groovysh.Groovysh;
import org.apache.groovy.groovysh.completion.StricterArgumentCompleter;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.BytecodeInterface8;
import org.codehaus.groovy.runtime.GeneratedClosure;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.powerassert.AssertionRenderer;
import org.codehaus.groovy.runtime.powerassert.ValueRecorder;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;
import org.codehaus.groovy.runtime.typehandling.ShortTypeHandling;
import org.codehaus.groovy.tools.shell.IO;
import org.codehaus.groovy.tools.shell.util.Logger;
import org.codehaus.groovy.tools.shell.util.MessageSource;

public abstract class CommandSupport
implements Command,
GroovyObject {
    protected static final String NEWLINE;
    protected final Logger log;
    protected final MessageSource messages;
    private final String name;
    private final String shortcut;
    protected final Groovysh shell;
    protected final IO io;
    protected CommandRegistry registry;
    private final List aliases;
    private boolean hidden;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private transient /* synthetic */ MetaClass metaClass;
    private static /* synthetic */ SoftReference $callSiteArray;

    protected CommandSupport(Groovysh shell, String name, String shortcut) {
        String string;
        String string2;
        Groovysh groovysh;
        MetaClass metaClass;
        boolean bl;
        List list;
        CallSite[] callSiteArray = CommandSupport.$getCallSiteArray();
        Object object = callSiteArray[0].callConstructor(MessageSource.class, callSiteArray[1].callGroovyObjectGetProperty((Object)this), CommandSupport.class);
        this.messages = (MessageSource)ScriptBytecodeAdapter.castToType((Object)object, MessageSource.class);
        this.aliases = list = ScriptBytecodeAdapter.createList((Object[])new Object[0]);
        this.hidden = bl = false;
        this.metaClass = metaClass = this.$getStaticMetaClass();
        ValueRecorder valueRecorder = new ValueRecorder();
        try {
            Groovysh groovysh2 = shell;
            valueRecorder.record((Object)groovysh2, 8);
            boolean bl2 = ScriptBytecodeAdapter.compareNotEqual((Object)groovysh2, null);
            valueRecorder.record((Object)bl2, 14);
            if (bl2) {
                valueRecorder.clear();
            } else {
                ScriptBytecodeAdapter.assertFailed((Object)AssertionRenderer.render((String)"assert shell != null", (ValueRecorder)valueRecorder), null);
            }
        }
        catch (Throwable throwable) {
            valueRecorder.clear();
            throw throwable;
        }
        ValueRecorder valueRecorder2 = new ValueRecorder();
        try {
            String string3 = name;
            valueRecorder2.record((Object)string3, 8);
            if (DefaultTypeTransformation.booleanUnbox((Object)string3)) {
                valueRecorder2.clear();
            } else {
                ScriptBytecodeAdapter.assertFailed((Object)AssertionRenderer.render((String)"assert name", (ValueRecorder)valueRecorder2), null);
            }
        }
        catch (Throwable throwable) {
            valueRecorder2.clear();
            throw throwable;
        }
        ValueRecorder valueRecorder3 = new ValueRecorder();
        try {
            String string4 = shortcut;
            valueRecorder3.record((Object)string4, 8);
            if (DefaultTypeTransformation.booleanUnbox((Object)string4)) {
                valueRecorder3.clear();
            } else {
                ScriptBytecodeAdapter.assertFailed((Object)AssertionRenderer.render((String)"assert shortcut", (ValueRecorder)valueRecorder3), null);
            }
        }
        catch (Throwable throwable) {
            valueRecorder3.clear();
            throw throwable;
        }
        Object object2 = callSiteArray[2].call(Logger.class, callSiteArray[3].callGroovyObjectGetProperty((Object)this), (Object)name);
        this.log = (Logger)ScriptBytecodeAdapter.castToType((Object)object2, Logger.class);
        this.shell = groovysh = shell;
        Object object3 = callSiteArray[4].callGroovyObjectGetProperty((Object)shell);
        this.io = (IO)ScriptBytecodeAdapter.castToType((Object)object3, IO.class);
        this.name = string2 = name;
        this.shortcut = string = shortcut;
    }

    @Override
    public String getDescription() {
        CallSite[] callSiteArray = CommandSupport.$getCallSiteArray();
        String string = ShortTypeHandling.castToString((Object)callSiteArray[5].call((Object)this.messages, (Object)"command.description"));
        try {
            return string;
        }
        catch (Exception MissingResourceException) {
            String string2 = "No description";
            return string2;
        }
    }

    @Override
    public String getUsage() {
        CallSite[] callSiteArray = CommandSupport.$getCallSiteArray();
        String string = ShortTypeHandling.castToString((Object)callSiteArray[6].call((Object)this.messages, (Object)"command.usage"));
        try {
            return string;
        }
        catch (Exception MissingResourceException) {
            String string2 = "No usage description";
            return string2;
        }
    }

    @Override
    public String getHelp() {
        CallSite[] callSiteArray = CommandSupport.$getCallSiteArray();
        String string = ShortTypeHandling.castToString((Object)callSiteArray[7].call((Object)this.messages, (Object)"command.help"));
        try {
            return string;
        }
        catch (Exception MissingResourceException) {
            String string2 = "No help";
            return string2;
        }
    }

    @Override
    public boolean getHidden() {
        CallSite[] callSiteArray = CommandSupport.$getCallSiteArray();
        return this.hidden;
    }

    @Override
    public List getAliases() {
        CallSite[] callSiteArray = CommandSupport.$getCallSiteArray();
        return this.aliases;
    }

    @Override
    public String getShortcut() {
        CallSite[] callSiteArray = CommandSupport.$getCallSiteArray();
        return this.shortcut;
    }

    @Override
    public String getName() {
        CallSite[] callSiteArray = CommandSupport.$getCallSiteArray();
        return this.name;
    }

    protected List<Completer> createCompleters() {
        CallSite[] callSiteArray = CommandSupport.$getCallSiteArray();
        return ScriptBytecodeAdapter.createList((Object[])new Object[0]);
    }

    @Override
    public Completer getCompleter() {
        CallSite[] callSiteArray = CommandSupport.$getCallSiteArray();
        if (this.hidden) {
            return (Completer)ScriptBytecodeAdapter.castToType(null, Completer.class);
        }
        Reference list = new Reference((Object)((List)ScriptBytecodeAdapter.castToType((Object)callSiteArray[8].callConstructor(ArrayList.class), List.class)));
        List completers = null;
        if (__$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            Object object = callSiteArray[9].callCurrent((GroovyObject)this);
            completers = (List)ScriptBytecodeAdapter.castToType((Object)object, List.class);
        } else {
            List list2;
            completers = list2 = this.createCompleters();
        }
        StringsCompleter stringCompleter = null;
        if (DefaultTypeTransformation.booleanUnbox((Object)completers)) {
            Object object = callSiteArray[10].callConstructor(StringsCompleter.class, callSiteArray[11].call((Object)this.name, (Object)" "), callSiteArray[12].call((Object)this.shortcut, (Object)" "));
            stringCompleter = (StringsCompleter)ScriptBytecodeAdapter.castToType((Object)object, StringsCompleter.class);
        } else {
            Object object = callSiteArray[13].callConstructor(StringsCompleter.class, (Object)this.name, (Object)this.shortcut);
            stringCompleter = (StringsCompleter)ScriptBytecodeAdapter.castToType((Object)object, StringsCompleter.class);
        }
        callSiteArray[14].call((Object)((List)list.get()), (Object)stringCompleter);
        if (DefaultTypeTransformation.booleanUnbox((Object)completers)) {
            public final class _getCompleter_closure1
            extends Closure
            implements GeneratedClosure {
                private /* synthetic */ Reference list;
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;
                private static /* synthetic */ SoftReference $callSiteArray;

                public _getCompleter_closure1(Object _outerInstance, Object _thisObject, Reference list) {
                    Reference reference;
                    CallSite[] callSiteArray = _getCompleter_closure1.$getCallSiteArray();
                    super(_outerInstance, _thisObject);
                    this.list = reference = list;
                }

                public Object doCall(Completer it) {
                    CallSite[] callSiteArray = _getCompleter_closure1.$getCallSiteArray();
                    if (DefaultTypeTransformation.booleanUnbox((Object)it)) {
                        return callSiteArray[0].call(this.list.get(), (Object)it);
                    }
                    return callSiteArray[1].call(this.list.get(), callSiteArray[2].callConstructor(NullCompleter.class));
                }

                @Generated
                public Object call(Completer it) {
                    CallSite[] callSiteArray = _getCompleter_closure1.$getCallSiteArray();
                    return callSiteArray[3].callCurrent((GroovyObject)this, (Object)it);
                }

                @Generated
                public List getList() {
                    CallSite[] callSiteArray = _getCompleter_closure1.$getCallSiteArray();
                    return (List)ScriptBytecodeAdapter.castToType((Object)this.list.get(), List.class);
                }

                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                    if (((Object)((Object)this)).getClass() != _getCompleter_closure1.class) {
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
                    stringArray[0] = "leftShift";
                    stringArray[1] = "leftShift";
                    stringArray[2] = "<$constructor$>";
                    stringArray[3] = "doCall";
                }

                private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                    String[] stringArray = new String[4];
                    _getCompleter_closure1.$createCallSiteArray_1(stringArray);
                    return new CallSiteArray(_getCompleter_closure1.class, stringArray);
                }

                private static /* synthetic */ CallSite[] $getCallSiteArray() {
                    CallSiteArray callSiteArray;
                    if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                        callSiteArray = _getCompleter_closure1.$createCallSiteArray();
                        $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                    }
                    return callSiteArray.array;
                }
            }
            callSiteArray[15].call((Object)completers, (Object)new _getCompleter_closure1(this, this, list));
        } else {
            callSiteArray[16].call((Object)((List)list.get()), callSiteArray[17].callConstructor(NullCompleter.class));
        }
        return (Completer)ScriptBytecodeAdapter.castToType((Object)callSiteArray[18].callConstructor(StricterArgumentCompleter.class, (Object)((List)list.get())), Completer.class);
    }

    protected void alias(String name, String shortcut) {
        CallSite[] callSiteArray = CommandSupport.$getCallSiteArray();
        callSiteArray[19].call((Object)this.aliases, callSiteArray[20].callConstructor(CommandAlias.class, (Object)this.shell, (Object)name, (Object)shortcut, (Object)this.name));
    }

    protected void fail(String msg) {
        CallSite[] callSiteArray = CommandSupport.$getCallSiteArray();
        throw (Throwable)callSiteArray[21].callConstructor(CommandException.class, (Object)this, (Object)msg);
    }

    protected void fail(String msg, Throwable cause) {
        CallSite[] callSiteArray = CommandSupport.$getCallSiteArray();
        throw (Throwable)callSiteArray[22].callConstructor(CommandException.class, (Object)this, (Object)msg, (Object)cause);
    }

    protected void assertNoArguments(List<String> args) {
        CallSite[] callSiteArray = CommandSupport.$getCallSiteArray();
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
        if (ScriptBytecodeAdapter.compareGreaterThan((Object)callSiteArray[23].call(args), (Object)0)) {
            callSiteArray[24].callCurrent((GroovyObject)this, callSiteArray[25].call((Object)this.messages, (Object)"error.unexpected_args", callSiteArray[26].call(args, (Object)" ")));
        }
    }

    protected BufferManager getBuffers() {
        CallSite[] callSiteArray = CommandSupport.$getCallSiteArray();
        return (BufferManager)ScriptBytecodeAdapter.castToType((Object)callSiteArray[27].callGroovyObjectGetProperty((Object)this.shell), BufferManager.class);
    }

    protected List<String> getBuffer() {
        CallSite[] callSiteArray = CommandSupport.$getCallSiteArray();
        return (List)ScriptBytecodeAdapter.castToType((Object)callSiteArray[28].call(callSiteArray[29].callGroovyObjectGetProperty((Object)this.shell)), List.class);
    }

    protected List<String> getImports() {
        CallSite[] callSiteArray = CommandSupport.$getCallSiteArray();
        return (List)ScriptBytecodeAdapter.castToType((Object)callSiteArray[30].callGroovyObjectGetProperty((Object)this.shell), List.class);
    }

    protected Binding getBinding() {
        CallSite[] callSiteArray = CommandSupport.$getCallSiteArray();
        return (Binding)ScriptBytecodeAdapter.castToType((Object)callSiteArray[31].callGetProperty(callSiteArray[32].callGroovyObjectGetProperty((Object)this.shell)), Binding.class);
    }

    protected Map getVariables() {
        CallSite[] callSiteArray = CommandSupport.$getCallSiteArray();
        return (Map)ScriptBytecodeAdapter.castToType((Object)callSiteArray[33].callGetProperty(callSiteArray[34].callGroovyObjectGetProperty((Object)this)), Map.class);
    }

    protected FileHistory getHistory() {
        CallSite[] callSiteArray = CommandSupport.$getCallSiteArray();
        return (FileHistory)ScriptBytecodeAdapter.castToType((Object)callSiteArray[35].callGroovyObjectGetProperty((Object)this.shell), FileHistory.class);
    }

    protected GroovyClassLoader getClassLoader() {
        CallSite[] callSiteArray = CommandSupport.$getCallSiteArray();
        return (GroovyClassLoader)ScriptBytecodeAdapter.castToType((Object)callSiteArray[36].callGetProperty(callSiteArray[37].callGroovyObjectGetProperty((Object)this.shell)), GroovyClassLoader.class);
    }

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != CommandSupport.class) {
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

    static {
        Object object = CommandSupport.$getCallSiteArray()[38].call(System.class);
        NEWLINE = ShortTypeHandling.castToString((Object)object);
    }

    @Generated
    public void setHidden(boolean bl) {
        this.hidden = bl;
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
        stringArray[0] = "<$constructor$>";
        stringArray[1] = "class";
        stringArray[2] = "create";
        stringArray[3] = "class";
        stringArray[4] = "io";
        stringArray[5] = "getMessage";
        stringArray[6] = "getMessage";
        stringArray[7] = "getMessage";
        stringArray[8] = "<$constructor$>";
        stringArray[9] = "createCompleters";
        stringArray[10] = "<$constructor$>";
        stringArray[11] = "plus";
        stringArray[12] = "plus";
        stringArray[13] = "<$constructor$>";
        stringArray[14] = "leftShift";
        stringArray[15] = "each";
        stringArray[16] = "leftShift";
        stringArray[17] = "<$constructor$>";
        stringArray[18] = "<$constructor$>";
        stringArray[19] = "leftShift";
        stringArray[20] = "<$constructor$>";
        stringArray[21] = "<$constructor$>";
        stringArray[22] = "<$constructor$>";
        stringArray[23] = "size";
        stringArray[24] = "fail";
        stringArray[25] = "format";
        stringArray[26] = "join";
        stringArray[27] = "buffers";
        stringArray[28] = "current";
        stringArray[29] = "buffers";
        stringArray[30] = "imports";
        stringArray[31] = "context";
        stringArray[32] = "interp";
        stringArray[33] = "variables";
        stringArray[34] = "binding";
        stringArray[35] = "history";
        stringArray[36] = "classLoader";
        stringArray[37] = "interp";
        stringArray[38] = "lineSeparator";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[39];
        CommandSupport.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(CommandSupport.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = CommandSupport.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }
}

