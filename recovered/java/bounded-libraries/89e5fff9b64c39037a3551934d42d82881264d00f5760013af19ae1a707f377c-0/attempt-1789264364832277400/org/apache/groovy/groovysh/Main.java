/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.cli.internal.CliBuilderInternal
 *  groovy.cli.internal.OptionAccessor
 *  groovy.lang.Closure
 *  groovy.lang.GroovyObject
 *  groovy.lang.GroovySystem
 *  groovy.lang.MetaClass
 *  groovy.lang.Reference
 *  groovy.transform.Generated
 *  groovy.transform.Internal
 *  jline.TerminalFactory
 *  jline.UnixTerminal
 *  jline.UnsupportedTerminal
 *  jline.WindowsTerminal
 *  jline.console.history.FileHistory
 *  org.apache.groovy.util.SystemUtil
 *  org.codehaus.groovy.control.CompilerConfiguration
 *  org.codehaus.groovy.reflection.ClassInfo
 *  org.codehaus.groovy.runtime.BytecodeInterface8
 *  org.codehaus.groovy.runtime.DefaultGroovyMethods
 *  org.codehaus.groovy.runtime.GStringImpl
 *  org.codehaus.groovy.runtime.GeneratedClosure
 *  org.codehaus.groovy.runtime.ScriptBytecodeAdapter
 *  org.codehaus.groovy.runtime.StringGroovyMethods
 *  org.codehaus.groovy.runtime.callsite.CallSite
 *  org.codehaus.groovy.runtime.callsite.CallSiteArray
 *  org.codehaus.groovy.runtime.powerassert.AssertionRenderer
 *  org.codehaus.groovy.runtime.powerassert.ValueRecorder
 *  org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation
 *  org.codehaus.groovy.runtime.typehandling.ShortTypeHandling
 *  org.codehaus.groovy.tools.shell.IO
 *  org.codehaus.groovy.tools.shell.IO$Verbosity
 *  org.codehaus.groovy.tools.shell.util.Logger
 *  org.codehaus.groovy.tools.shell.util.MessageSource
 *  org.fusesource.jansi.Ansi
 *  org.fusesource.jansi.AnsiConsole
 */
package org.apache.groovy.groovysh;

import groovy.cli.internal.CliBuilderInternal;
import groovy.cli.internal.OptionAccessor;
import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import groovy.lang.GroovySystem;
import groovy.lang.MetaClass;
import groovy.lang.Reference;
import groovy.transform.Generated;
import groovy.transform.Internal;
import java.beans.Transient;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import jline.TerminalFactory;
import jline.UnixTerminal;
import jline.UnsupportedTerminal;
import jline.WindowsTerminal;
import jline.console.history.FileHistory;
import org.apache.groovy.groovysh.AnsiDetector;
import org.apache.groovy.groovysh.Groovysh;
import org.apache.groovy.groovysh.util.SecurityManagerUtil;
import org.apache.groovy.util.SystemUtil;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.BytecodeInterface8;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.codehaus.groovy.runtime.GStringImpl;
import org.codehaus.groovy.runtime.GeneratedClosure;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.StringGroovyMethods;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.powerassert.AssertionRenderer;
import org.codehaus.groovy.runtime.powerassert.ValueRecorder;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;
import org.codehaus.groovy.runtime.typehandling.ShortTypeHandling;
import org.codehaus.groovy.tools.shell.IO;
import org.codehaus.groovy.tools.shell.util.Logger;
import org.codehaus.groovy.tools.shell.util.MessageSource;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

public class Main
implements GroovyObject {
    private final Groovysh groovysh;
    private static final java.util.logging.Logger LOGGER;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private transient /* synthetic */ MetaClass metaClass;
    private static /* synthetic */ SoftReference $callSiteArray;

    public Main(IO io) {
        Groovysh groovysh;
        MetaClass metaClass;
        this.metaClass = metaClass = this.$getStaticMetaClass();
        IO iO = io;
        ScriptBytecodeAdapter.setProperty((Object)iO, null, Logger.class, (String)"io");
        this.groovysh = groovysh = new Groovysh(io);
    }

    public Main(IO io, CompilerConfiguration configuration) {
        Groovysh groovysh;
        MetaClass metaClass;
        this.metaClass = metaClass = this.$getStaticMetaClass();
        IO iO = io;
        ScriptBytecodeAdapter.setProperty((Object)iO, null, Logger.class, (String)"io");
        this.groovysh = groovysh = new Groovysh(io, configuration);
    }

    public static void main(String ... args) {
        OptionAccessor options;
        CallSite[] callSiteArray;
        block16: {
            callSiteArray = Main.$getCallSiteArray();
            Reference messages = new Reference((Object)((MessageSource)ScriptBytecodeAdapter.castToType((Object)callSiteArray[0].callConstructor(MessageSource.class, Main.class), MessageSource.class)));
            Object cli = callSiteArray[1].callConstructor(CliBuilderInternal.class, (Object)ScriptBytecodeAdapter.createMap((Object[])new Object[]{"usage", "groovysh [options] [...]", "stopAtNonOption", false, "header", callSiteArray[2].call((Object)((MessageSource)messages.get()), (Object)"cli.option.header")}));
            public final class _main_closure1
            extends Closure
            implements GeneratedClosure {
                private /* synthetic */ Reference messages;
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;
                private static /* synthetic */ SoftReference $callSiteArray;

                public _main_closure1(Object _outerInstance, Object _thisObject, Reference messages) {
                    Reference reference;
                    CallSite[] callSiteArray = _main_closure1.$getCallSiteArray();
                    super(_outerInstance, _thisObject);
                    this.messages = reference = messages;
                }

                public Object doCall(Object it) {
                    CallSite[] callSiteArray = _main_closure1.$getCallSiteArray();
                    callSiteArray[0].callCurrent((GroovyObject)this, (Object)ScriptBytecodeAdapter.createMap((Object[])new Object[]{"names", ScriptBytecodeAdapter.createList((Object[])new Object[]{"-cp", "-classpath", "--classpath"})}), callSiteArray[1].call(this.messages.get(), (Object)"cli.option.classpath.description"));
                    callSiteArray[2].callCurrent((GroovyObject)this, (Object)ScriptBytecodeAdapter.createMap((Object[])new Object[]{"longOpt", "help"}), callSiteArray[3].call(this.messages.get(), (Object)"cli.option.help.description"));
                    callSiteArray[4].callCurrent((GroovyObject)this, (Object)ScriptBytecodeAdapter.createMap((Object[])new Object[]{"longOpt", "version"}), callSiteArray[5].call(this.messages.get(), (Object)"cli.option.version.description"));
                    callSiteArray[6].callCurrent((GroovyObject)this, (Object)ScriptBytecodeAdapter.createMap((Object[])new Object[]{"longOpt", "verbose"}), callSiteArray[7].call(this.messages.get(), (Object)"cli.option.verbose.description"));
                    callSiteArray[8].callCurrent((GroovyObject)this, (Object)ScriptBytecodeAdapter.createMap((Object[])new Object[]{"longOpt", "quiet"}), callSiteArray[9].call(this.messages.get(), (Object)"cli.option.quiet.description"));
                    callSiteArray[10].callCurrent((GroovyObject)this, (Object)ScriptBytecodeAdapter.createMap((Object[])new Object[]{"longOpt", "debug"}), callSiteArray[11].call(this.messages.get(), (Object)"cli.option.debug.description"));
                    callSiteArray[12].callCurrent((GroovyObject)this, (Object)ScriptBytecodeAdapter.createMap((Object[])new Object[]{"longOpt", "evaluate", "args", 1, "argName", "CODE", "optionalArg", false}), callSiteArray[13].call(this.messages.get(), (Object)"cli.option.evaluate.description"));
                    callSiteArray[14].callCurrent((GroovyObject)this, (Object)ScriptBytecodeAdapter.createMap((Object[])new Object[]{"longOpt", "color", "args", 1, "argName", "FLAG", "optionalArg", true}), callSiteArray[15].call(this.messages.get(), (Object)"cli.option.color.description"));
                    callSiteArray[16].callCurrent((GroovyObject)this, (Object)ScriptBytecodeAdapter.createMap((Object[])new Object[]{"longOpt", "define", "type", Map.class, "argName", "name=value"}), callSiteArray[17].call(this.messages.get(), (Object)"cli.option.define.description"));
                    callSiteArray[18].callCurrent((GroovyObject)this, (Object)ScriptBytecodeAdapter.createMap((Object[])new Object[]{"longOpt", "terminal", "args", 1, "argName", "TYPE"}), callSiteArray[19].call(this.messages.get(), (Object)"cli.option.terminal.description"));
                    callSiteArray[20].callCurrent((GroovyObject)this, (Object)ScriptBytecodeAdapter.createMap((Object[])new Object[]{"longOpt", "parameters"}), callSiteArray[21].call(this.messages.get(), (Object)"cli.option.parameters.description"));
                    return callSiteArray[22].callCurrent((GroovyObject)this, (Object)ScriptBytecodeAdapter.createMap((Object[])new Object[]{"longOpt", "enable-preview"}), callSiteArray[23].call(this.messages.get(), (Object)"cli.option.enable.preview.description"));
                }

                @Generated
                public MessageSource getMessages() {
                    CallSite[] callSiteArray = _main_closure1.$getCallSiteArray();
                    return (MessageSource)ScriptBytecodeAdapter.castToType((Object)this.messages.get(), MessageSource.class);
                }

                @Generated
                public Object doCall() {
                    CallSite[] callSiteArray = _main_closure1.$getCallSiteArray();
                    return this.doCall(null);
                }

                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                    if (((Object)((Object)this)).getClass() != _main_closure1.class) {
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
                    stringArray[0] = "_";
                    stringArray[1] = "getAt";
                    stringArray[2] = "h";
                    stringArray[3] = "getAt";
                    stringArray[4] = "V";
                    stringArray[5] = "getAt";
                    stringArray[6] = "v";
                    stringArray[7] = "getAt";
                    stringArray[8] = "q";
                    stringArray[9] = "getAt";
                    stringArray[10] = "d";
                    stringArray[11] = "getAt";
                    stringArray[12] = "e";
                    stringArray[13] = "getAt";
                    stringArray[14] = "C";
                    stringArray[15] = "getAt";
                    stringArray[16] = "D";
                    stringArray[17] = "getAt";
                    stringArray[18] = "T";
                    stringArray[19] = "getAt";
                    stringArray[20] = "pa";
                    stringArray[21] = "getAt";
                    stringArray[22] = "pr";
                    stringArray[23] = "getAt";
                }

                private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                    String[] stringArray = new String[24];
                    _main_closure1.$createCallSiteArray_1(stringArray);
                    return new CallSiteArray(_main_closure1.class, stringArray);
                }

                private static /* synthetic */ CallSite[] $getCallSiteArray() {
                    CallSiteArray callSiteArray;
                    if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                        callSiteArray = _main_closure1.$createCallSiteArray();
                        $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                    }
                    return callSiteArray.array;
                }
            }
            callSiteArray[3].call(cli, (Object)new _main_closure1(Main.class, Main.class, messages));
            options = (OptionAccessor)ScriptBytecodeAdapter.castToType((Object)callSiteArray[4].call(cli, (Object)args), OptionAccessor.class);
            if (ScriptBytecodeAdapter.compareEqual((Object)options, null)) {
                callSiteArray[5].call(System.class, (Object)22);
            }
            if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[6].callGroovyObjectGetProperty((Object)options))) {
                callSiteArray[7].call(cli);
                callSiteArray[8].call(System.class, (Object)0);
            }
            if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[9].callGroovyObjectGetProperty((Object)options))) {
                callSiteArray[10].call(callSiteArray[11].callGetProperty(System.class), callSiteArray[12].call((Object)((MessageSource)messages.get()), (Object)"cli.info.version", callSiteArray[13].callGetProperty(GroovySystem.class)));
                callSiteArray[14].call(System.class, (Object)0);
            }
            boolean suppressColor = false;
            if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[15].call((Object)options, (Object)"C"))) {
                Object value = callSiteArray[16].call((Object)options, (Object)"C");
                if (ScriptBytecodeAdapter.compareNotEqual((Object)value, null)) {
                    boolean bl;
                    suppressColor = bl = !DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[17].call(callSiteArray[18].call(Boolean.class, value)));
                }
            }
            String type = ShortTypeHandling.castToString((Object)callSiteArray[19].callGetProperty(TerminalFactory.class));
            if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[20].call((Object)options, (Object)"T"))) {
                Object object = callSiteArray[21].call((Object)options, (Object)"T");
                type = ShortTypeHandling.castToString((Object)object);
            }
            try {
                if (__$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                    Main.setTerminalType(type, suppressColor);
                    break block16;
                }
                Main.setTerminalType(type, suppressColor);
            }
            catch (IllegalArgumentException e) {
                callSiteArray[22].call(callSiteArray[23].callGetProperty(System.class), callSiteArray[24].call((Object)e));
                callSiteArray[25].call(cli);
                callSiteArray[26].call(System.class, (Object)22);
            }
        }
        IO io = (IO)ScriptBytecodeAdapter.castToType((Object)callSiteArray[27].callConstructor(IO.class), IO.class);
        if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[28].call((Object)options, (Object)"D"))) {
            public final class _main_closure2
            extends Closure
            implements GeneratedClosure {
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;
                private static /* synthetic */ SoftReference $callSiteArray;

                public _main_closure2(Object _outerInstance, Object _thisObject) {
                    CallSite[] callSiteArray = _main_closure2.$getCallSiteArray();
                    super(_outerInstance, _thisObject);
                }

                public Object doCall(Object k, Object v) {
                    CallSite[] callSiteArray = _main_closure2.$getCallSiteArray();
                    return callSiteArray[0].call(System.class, k, v);
                }

                @Generated
                public Object call(Object k, Object v) {
                    CallSite[] callSiteArray = _main_closure2.$getCallSiteArray();
                    return callSiteArray[1].callCurrent((GroovyObject)this, k, v);
                }

                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                    if (((Object)((Object)this)).getClass() != _main_closure2.class) {
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
                    stringArray[0] = "setProperty";
                    stringArray[1] = "doCall";
                }

                private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                    String[] stringArray = new String[2];
                    _main_closure2.$createCallSiteArray_1(stringArray);
                    return new CallSiteArray(_main_closure2.class, stringArray);
                }

                private static /* synthetic */ CallSite[] $getCallSiteArray() {
                    CallSiteArray callSiteArray;
                    if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                        callSiteArray = _main_closure2.$createCallSiteArray();
                        $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                    }
                    return callSiteArray.array;
                }
            }
            callSiteArray[29].call(callSiteArray[30].callGroovyObjectGetProperty((Object)options), (Object)new _main_closure2(Main.class, Main.class));
        }
        if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[31].callGroovyObjectGetProperty((Object)options))) {
            Object object = callSiteArray[32].callGetProperty(IO.Verbosity.class);
            ScriptBytecodeAdapter.setProperty((Object)object, null, (Object)io, (String)"verbosity");
        }
        if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[33].callGroovyObjectGetProperty((Object)options))) {
            Object object = callSiteArray[34].callGetProperty(IO.Verbosity.class);
            ScriptBytecodeAdapter.setProperty((Object)object, null, (Object)io, (String)"verbosity");
        }
        if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[35].callGroovyObjectGetProperty((Object)options))) {
            Object object = callSiteArray[36].callGetProperty(IO.Verbosity.class);
            ScriptBytecodeAdapter.setProperty((Object)object, null, (Object)io, (String)"verbosity");
        }
        String evalString = null;
        if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[37].callGroovyObjectGetProperty((Object)options))) {
            Object object = callSiteArray[38].call((Object)options, (Object)"e");
            evalString = ShortTypeHandling.castToString((Object)object);
        }
        Object configuration = callSiteArray[39].callConstructor(CompilerConfiguration.class, callSiteArray[40].call(System.class));
        callSiteArray[41].call(configuration, (Object)ScriptBytecodeAdapter.createPojoWrapper((Object)DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[42].call((Object)options, (Object)"pa")), Boolean.TYPE));
        List filenames = (List)ScriptBytecodeAdapter.castToType((Object)callSiteArray[43].call((Object)options), List.class);
        Main main = (Main)ScriptBytecodeAdapter.castToType((Object)callSiteArray[44].callConstructor(Main.class, (Object)io, configuration), Main.class);
        callSiteArray[45].call((Object)main, (Object)evalString, (Object)filenames);
    }

    protected void startGroovysh(String evalString, List<String> filenames) {
        Reference code = new Reference((Object)0);
        Integer cfr_ignored_0 = (Integer)code.get();
        Reference shell = new Reference((Object)this.getGroovysh());
        public final class _startGroovysh_closure3
        extends Closure
        implements GeneratedClosure {
            private /* synthetic */ Reference code;
            private /* synthetic */ Reference shell;
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;

            public _startGroovysh_closure3(Object _outerInstance, Object _thisObject, Reference code, Reference shell) {
                super(_outerInstance, _thisObject);
                Reference reference;
                Reference reference2;
                this.code = reference2 = code;
                this.shell = reference = shell;
            }

            public Object doCall(Object it) {
                if (this.code.get() == null) {
                    DefaultGroovyMethods.println((Object)this.getThisObject(), (Object)"WARNING: Abnormal JVM shutdown detected");
                }
                FileHistory fileHistory = ((Groovysh)this.shell.get()).getHistory();
                if (fileHistory == null ? false : DefaultTypeTransformation.booleanUnbox((Object)fileHistory)) {
                    ((Groovysh)this.shell.get()).getHistory().flush();
                    return null;
                }
                return null;
            }

            @Generated
            public Integer getCode() {
                return (Integer)ScriptBytecodeAdapter.castToType((Object)this.code.get(), Integer.class);
            }

            @Generated
            public Groovysh getShell() {
                return (Groovysh)ScriptBytecodeAdapter.castToType((Object)this.shell.get(), Groovysh.class);
            }

            @Generated
            public Object call(Object args) {
                return this.doCall(args);
            }

            @Generated
            public Object call() {
                return this.doCall(null);
            }

            @Generated
            public Object doCall() {
                return this.doCall(null);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (((Object)((Object)this)).getClass() != _startGroovysh_closure3.class) {
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
        }
        DefaultGroovyMethods.addShutdownHook((Object)this, (Closure)new _startGroovysh_closure3(this, this, code, shell));
        try (SecurityManagerUtil sm = new SecurityManagerUtil();){
            int n = ((Groovysh)shell.get()).run(evalString, filenames);
            code.set((Object)n);
        }
        System.exit((Integer)code.get());
    }

    public static void setTerminalType(String type, boolean suppressColor) {
        String string;
        ValueRecorder valueRecorder = new ValueRecorder();
        try {
            String string2 = type;
            valueRecorder.record((Object)string2, 8);
            if (string2 != null) {
                valueRecorder.clear();
            } else {
                ScriptBytecodeAdapter.assertFailed((Object)AssertionRenderer.render((String)"assert type != null", (ValueRecorder)valueRecorder), null);
            }
        }
        catch (Throwable throwable) {
            valueRecorder.clear();
            throw throwable;
        }
        type = string = type.toLowerCase();
        boolean enableAnsi = true;
        String string3 = type;
        if (ScriptBytecodeAdapter.isCase((Object)string3, (Object)TerminalFactory.AUTO)) {
            Object var6_6 = null;
            type = ShortTypeHandling.castToString(var6_6);
        } else if (ScriptBytecodeAdapter.isCase((Object)string3, (Object)TerminalFactory.UNIX)) {
            String string4;
            type = string4 = UnixTerminal.class.getCanonicalName();
        } else if (ScriptBytecodeAdapter.isCase((Object)string3, (Object)TerminalFactory.WIN) || ScriptBytecodeAdapter.isCase((Object)string3, (Object)TerminalFactory.WINDOWS)) {
            String string5;
            type = string5 = WindowsTerminal.class.getCanonicalName();
        } else if (ScriptBytecodeAdapter.isCase((Object)string3, (Object)TerminalFactory.FALSE) || ScriptBytecodeAdapter.isCase((Object)string3, (Object)TerminalFactory.OFF) || ScriptBytecodeAdapter.isCase((Object)string3, (Object)TerminalFactory.NONE)) {
            boolean bl;
            String string6;
            type = string6 = UnsupportedTerminal.class.getCanonicalName();
            enableAnsi = bl = false;
        } else {
            throw (Throwable)new IllegalArgumentException(ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{type}, new String[]{"Invalid Terminal type: ", ""})));
        }
        boolean bl = false;
        Ansi.setEnabled((boolean)bl);
        if (enableAnsi) {
            try {
                Main.installAnsi();
                boolean bl2 = !suppressColor;
                Ansi.setEnabled((boolean)bl2);
            }
            catch (Throwable t) {
                LOGGER.warning(StringGroovyMethods.plus((String)"ansi will be disabled because an error occurred while installing ansi: ", (CharSequence)t.getMessage()));
            }
        }
        if (type != null) {
            System.setProperty(TerminalFactory.JLINE_TERMINAL, type);
        }
    }

    public static void installAnsi() {
        AnsiConsole.systemInstall();
        Ansi.setDetector((Callable)new AnsiDetector());
    }

    @Deprecated
    public static void setSystemProperty(String nameValue) {
        SystemUtil.setSystemPropertyFrom((String)nameValue);
    }

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != Main.class) {
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
        java.util.logging.Logger logger;
        LOGGER = logger = java.util.logging.Logger.getLogger(Main.class.getName());
    }

    @Generated
    public final Groovysh getGroovysh() {
        return this.groovysh;
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
        stringArray[0] = "<$constructor$>";
        stringArray[1] = "<$constructor$>";
        stringArray[2] = "getAt";
        stringArray[3] = "with";
        stringArray[4] = "parse";
        stringArray[5] = "exit";
        stringArray[6] = "h";
        stringArray[7] = "usage";
        stringArray[8] = "exit";
        stringArray[9] = "V";
        stringArray[10] = "println";
        stringArray[11] = "out";
        stringArray[12] = "format";
        stringArray[13] = "version";
        stringArray[14] = "exit";
        stringArray[15] = "hasOption";
        stringArray[16] = "getOptionValue";
        stringArray[17] = "booleanValue";
        stringArray[18] = "valueOf";
        stringArray[19] = "AUTO";
        stringArray[20] = "hasOption";
        stringArray[21] = "getOptionValue";
        stringArray[22] = "println";
        stringArray[23] = "err";
        stringArray[24] = "getMessage";
        stringArray[25] = "usage";
        stringArray[26] = "exit";
        stringArray[27] = "<$constructor$>";
        stringArray[28] = "hasOption";
        stringArray[29] = "each";
        stringArray[30] = "Ds";
        stringArray[31] = "v";
        stringArray[32] = "VERBOSE";
        stringArray[33] = "d";
        stringArray[34] = "DEBUG";
        stringArray[35] = "q";
        stringArray[36] = "QUIET";
        stringArray[37] = "e";
        stringArray[38] = "getOptionValue";
        stringArray[39] = "<$constructor$>";
        stringArray[40] = "getProperties";
        stringArray[41] = "setParameters";
        stringArray[42] = "hasOption";
        stringArray[43] = "arguments";
        stringArray[44] = "<$constructor$>";
        stringArray[45] = "startGroovysh";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[46];
        Main.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(Main.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = Main.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }
}

