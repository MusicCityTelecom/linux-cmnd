/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.Binding
 *  groovy.lang.Closure
 *  groovy.lang.GroovyObject
 *  groovy.lang.GroovySystem
 *  groovy.lang.MetaClass
 *  groovy.lang.MissingPropertyException
 *  groovy.lang.Reference
 *  groovy.transform.Generated
 *  jline.Terminal
 *  jline.WindowsTerminal
 *  jline.console.history.FileHistory
 *  org.apache.groovy.io.StringBuilderWriter
 *  org.codehaus.groovy.control.CompilationFailedException
 *  org.codehaus.groovy.control.CompilerConfiguration
 *  org.codehaus.groovy.control.ErrorCollector
 *  org.codehaus.groovy.control.MultipleCompilationErrorsException
 *  org.codehaus.groovy.control.messages.Message
 *  org.codehaus.groovy.reflection.ClassInfo
 *  org.codehaus.groovy.runtime.BytecodeInterface8
 *  org.codehaus.groovy.runtime.DefaultGroovyMethods
 *  org.codehaus.groovy.runtime.FormatHelper
 *  org.codehaus.groovy.runtime.GStringImpl
 *  org.codehaus.groovy.runtime.GeneratedClosure
 *  org.codehaus.groovy.runtime.ScriptBytecodeAdapter
 *  org.codehaus.groovy.runtime.StackTraceUtils
 *  org.codehaus.groovy.runtime.StringGroovyMethods
 *  org.codehaus.groovy.runtime.callsite.CallSite
 *  org.codehaus.groovy.runtime.callsite.CallSiteArray
 *  org.codehaus.groovy.runtime.powerassert.AssertionRenderer
 *  org.codehaus.groovy.runtime.powerassert.ValueRecorder
 *  org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation
 *  org.codehaus.groovy.runtime.typehandling.ShortTypeHandling
 *  org.codehaus.groovy.tools.shell.IO
 *  org.codehaus.groovy.tools.shell.IO$Verbosity
 *  org.codehaus.groovy.tools.shell.util.MessageSource
 *  org.codehaus.groovy.tools.shell.util.Preferences
 *  org.fusesource.jansi.AnsiRenderer
 */
package org.apache.groovy.groovysh;

import groovy.lang.Binding;
import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import groovy.lang.GroovySystem;
import groovy.lang.MetaClass;
import groovy.lang.MissingPropertyException;
import groovy.lang.Reference;
import groovy.transform.Generated;
import java.io.File;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import jline.Terminal;
import jline.WindowsTerminal;
import jline.console.history.FileHistory;
import org.apache.groovy.groovysh.BufferManager;
import org.apache.groovy.groovysh.Command;
import org.apache.groovy.groovysh.ExitNotification;
import org.apache.groovy.groovysh.InteractiveShellRunner;
import org.apache.groovy.groovysh.Interpreter;
import org.apache.groovy.groovysh.ParseCode;
import org.apache.groovy.groovysh.Parser;
import org.apache.groovy.groovysh.Shell;
import org.apache.groovy.groovysh.commands.LoadCommand;
import org.apache.groovy.groovysh.commands.RecordCommand;
import org.apache.groovy.groovysh.util.DefaultCommandsRegistrar;
import org.apache.groovy.groovysh.util.PackageHelper;
import org.apache.groovy.groovysh.util.PackageHelperImpl;
import org.apache.groovy.groovysh.util.ScriptVariableAnalyzer;
import org.apache.groovy.groovysh.util.XmlCommandRegistrar;
import org.apache.groovy.groovysh.util.antlr4.CurlyCountingGroovyLexer;
import org.apache.groovy.io.StringBuilderWriter;
import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.ErrorCollector;
import org.codehaus.groovy.control.MultipleCompilationErrorsException;
import org.codehaus.groovy.control.messages.Message;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.BytecodeInterface8;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.codehaus.groovy.runtime.FormatHelper;
import org.codehaus.groovy.runtime.GStringImpl;
import org.codehaus.groovy.runtime.GeneratedClosure;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.StackTraceUtils;
import org.codehaus.groovy.runtime.StringGroovyMethods;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.powerassert.AssertionRenderer;
import org.codehaus.groovy.runtime.powerassert.ValueRecorder;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;
import org.codehaus.groovy.runtime.typehandling.ShortTypeHandling;
import org.codehaus.groovy.tools.shell.IO;
import org.codehaus.groovy.tools.shell.util.MessageSource;
import org.codehaus.groovy.tools.shell.util.Preferences;
import org.fusesource.jansi.AnsiRenderer;

public class Groovysh
extends Shell {
    private static final MessageSource messages;
    private static final Pattern TYPEDEF_PATTERN;
    private static final Pattern METHODDEF_PATTERN;
    public static final String COLLECTED_BOUND_VARS_MAP_VARNAME = "groovysh_collected_boundvars";
    public static final String INTERPRETER_MODE_PREFERENCE_KEY = "interpreterMode";
    public static final String AUTOINDENT_PREFERENCE_KEY = "autoindent";
    public static final String COLORS_PREFERENCE_KEY = "colors";
    public static final String SANITIZE_PREFERENCE_KEY = "sanitizeStackTrace";
    public static final String SHOW_LAST_RESULT_PREFERENCE_KEY = "showLastResult";
    public static final String METACLASS_COMPLETION_PREFIX_LENGTH_PREFERENCE_KEY = "meta-completion-prefix-length";
    private final BufferManager buffers;
    private final Parser parser;
    private final Interpreter interp;
    private final List<String> imports;
    private int indentSize;
    private InteractiveShellRunner runner;
    private FileHistory history;
    private boolean historyFull;
    private String evictedLine;
    private PackageHelper packageHelper;
    private CompilerConfiguration configuration;
    private final AnsiRenderer prompt;
    private final Closure defaultResultHook;
    private Closure resultHook;
    private final Closure defaultErrorHook;
    private Closure errorHook;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private static /* synthetic */ SoftReference $callSiteArray;

    public Groovysh(ClassLoader classLoader, Binding binding, IO io, Closure registrar) {
        CallSite[] callSiteArray = Groovysh.$getCallSiteArray();
        this(classLoader, binding, io, registrar, (CompilerConfiguration)ScriptBytecodeAdapter.castToType((Object)callSiteArray[0].callGetProperty(CompilerConfiguration.class), CompilerConfiguration.class));
    }

    public Groovysh(ClassLoader classLoader, Binding binding, IO io, Closure registrar, CompilerConfiguration configuration) {
        CallSite[] callSiteArray = Groovysh.$getCallSiteArray();
        this(classLoader, binding, io, registrar, configuration, (Interpreter)ScriptBytecodeAdapter.castToType((Object)callSiteArray[1].callConstructor(Interpreter.class, (Object)classLoader, (Object)binding, (Object)configuration), Interpreter.class));
    }

    public Groovysh(ClassLoader classLoader, Binding binding, IO io, Closure registrar, CompilerConfiguration configuration, Interpreter interpreter) {
        CompilerConfiguration compilerConfiguration;
        Interpreter interpreter2;
        Closure closure;
        Closure closure2;
        int n;
        List list;
        CallSite[] callSiteArray = Groovysh.$getCallSiteArray();
        super(io);
        Object object = callSiteArray[2].callConstructor(BufferManager.class);
        this.buffers = (BufferManager)ScriptBytecodeAdapter.castToType((Object)object, BufferManager.class);
        this.imports = list = ScriptBytecodeAdapter.createList((Object[])new Object[0]);
        this.indentSize = n = 2;
        Object object2 = callSiteArray[3].callConstructor(AnsiRenderer.class);
        this.prompt = (AnsiRenderer)ScriptBytecodeAdapter.castToType((Object)object2, AnsiRenderer.class);
        _closure1 _closure12 = new _closure1(this, this);
        this.defaultResultHook = _closure12;
        this.resultHook = closure2 = this.defaultResultHook;
        _closure2 _closure22 = new _closure2(this, this);
        this.defaultErrorHook = _closure22;
        this.errorHook = closure = this.defaultErrorHook;
        ValueRecorder valueRecorder = new ValueRecorder();
        try {
            ClassLoader classLoader2 = classLoader;
            valueRecorder.record((Object)classLoader2, 8);
            if (DefaultTypeTransformation.booleanUnbox((Object)classLoader2)) {
                valueRecorder.clear();
            } else {
                ScriptBytecodeAdapter.assertFailed((Object)AssertionRenderer.render((String)"assert classLoader", (ValueRecorder)valueRecorder), null);
            }
        }
        catch (Throwable throwable) {
            valueRecorder.clear();
            throw throwable;
        }
        ValueRecorder valueRecorder2 = new ValueRecorder();
        try {
            Binding binding2 = binding;
            valueRecorder2.record((Object)binding2, 8);
            if (DefaultTypeTransformation.booleanUnbox((Object)binding2)) {
                valueRecorder2.clear();
            } else {
                ScriptBytecodeAdapter.assertFailed((Object)AssertionRenderer.render((String)"assert binding", (ValueRecorder)valueRecorder2), null);
            }
        }
        catch (Throwable throwable) {
            valueRecorder2.clear();
            throw throwable;
        }
        Closure closure3 = registrar;
        Closure actualRegistrar = DefaultTypeTransformation.booleanUnbox((Object)closure3) ? closure3 : callSiteArray[4].callStatic(Groovysh.class, (Object)classLoader);
        Object object3 = callSiteArray[5].callConstructor(Parser.class);
        this.parser = (Parser)ScriptBytecodeAdapter.castToType((Object)object3, Parser.class);
        this.interp = interpreter2 = interpreter;
        callSiteArray[6].call((Object)actualRegistrar, (Object)this);
        Object object4 = callSiteArray[7].callConstructor(PackageHelperImpl.class, (Object)classLoader);
        this.packageHelper = (PackageHelper)ScriptBytecodeAdapter.castToType((Object)object4, PackageHelper.class);
        this.configuration = compilerConfiguration = configuration;
    }

    public Groovysh(ClassLoader classLoader, Binding binding, IO io) {
        CallSite[] callSiteArray = Groovysh.$getCallSiteArray();
        this(classLoader, binding, io, null);
    }

    public Groovysh(Binding binding, IO io) {
        CallSite[] callSiteArray = Groovysh.$getCallSiteArray();
        this((ClassLoader)ScriptBytecodeAdapter.castToType((Object)callSiteArray[8].callGetProperty(callSiteArray[9].call(Thread.class)), ClassLoader.class), binding, io);
    }

    public Groovysh(IO io) {
        CallSite[] callSiteArray = Groovysh.$getCallSiteArray();
        Object[] objectArray = new Object[]{callSiteArray[10].callConstructor(Binding.class), io};
        Groovysh groovysh = this;
        switch (ScriptBytecodeAdapter.selectConstructorAndTransformArguments((Object[])objectArray, (int)-1, Groovysh.class)) {
            case -1777154199: {
                Object[] objectArray2 = objectArray;
                groovysh((ClassLoader)ScriptBytecodeAdapter.castToType((Object)objectArray[0], ClassLoader.class), (Binding)ScriptBytecodeAdapter.castToType((Object)objectArray[1], Binding.class), (IO)ScriptBytecodeAdapter.castToType((Object)objectArray[2], IO.class), (Closure)ScriptBytecodeAdapter.castToType((Object)objectArray[3], Closure.class), (CompilerConfiguration)ScriptBytecodeAdapter.castToType((Object)objectArray[4], CompilerConfiguration.class), (Interpreter)ScriptBytecodeAdapter.castToType((Object)objectArray[5], Interpreter.class));
                break;
            }
            case -916931588: {
                Object[] objectArray2 = objectArray;
                groovysh((ClassLoader)ScriptBytecodeAdapter.castToType((Object)objectArray[0], ClassLoader.class), (Binding)ScriptBytecodeAdapter.castToType((Object)objectArray[1], Binding.class), (IO)ScriptBytecodeAdapter.castToType((Object)objectArray[2], IO.class));
                break;
            }
            case -633941028: {
                Object[] objectArray2 = objectArray;
                groovysh((Binding)ScriptBytecodeAdapter.castToType((Object)objectArray[0], Binding.class), (IO)ScriptBytecodeAdapter.castToType((Object)objectArray[1], IO.class));
                break;
            }
            case -555925581: {
                Object[] objectArray2 = objectArray;
                groovysh((ClassLoader)ScriptBytecodeAdapter.castToType((Object)objectArray[0], ClassLoader.class), (Binding)ScriptBytecodeAdapter.castToType((Object)objectArray[1], Binding.class), (IO)ScriptBytecodeAdapter.castToType((Object)objectArray[2], IO.class), (Closure)ScriptBytecodeAdapter.castToType((Object)objectArray[3], Closure.class), (CompilerConfiguration)ScriptBytecodeAdapter.castToType((Object)objectArray[4], CompilerConfiguration.class));
                break;
            }
            case 39797: {
                Object[] objectArray2 = objectArray;
                groovysh();
                break;
            }
            case 174122378: {
                Object[] objectArray2 = objectArray;
                groovysh((ClassLoader)ScriptBytecodeAdapter.castToType((Object)objectArray[0], ClassLoader.class), (Binding)ScriptBytecodeAdapter.castToType((Object)objectArray[1], Binding.class), (IO)ScriptBytecodeAdapter.castToType((Object)objectArray[2], IO.class), (Closure)ScriptBytecodeAdapter.castToType((Object)objectArray[3], Closure.class));
                break;
            }
            case 899761450: {
                Object[] objectArray2 = objectArray;
                groovysh((IO)ScriptBytecodeAdapter.castToType((Object)objectArray[0], IO.class));
                break;
            }
            case 1950809107: {
                Object[] objectArray2 = objectArray;
                groovysh((IO)ScriptBytecodeAdapter.castToType((Object)objectArray[0], IO.class), (CompilerConfiguration)ScriptBytecodeAdapter.castToType((Object)objectArray[1], CompilerConfiguration.class));
                break;
            }
            default: {
                throw new IllegalArgumentException("This class has been compiled with a super class which is binary incompatible with the current super class found on classpath. You should recompile this class with the new version.");
            }
        }
    }

    public Groovysh(IO io, CompilerConfiguration configuration) {
        CallSite[] callSiteArray = Groovysh.$getCallSiteArray();
        this((ClassLoader)ScriptBytecodeAdapter.castToType((Object)callSiteArray[11].callGetProperty(callSiteArray[12].call(Thread.class)), ClassLoader.class), (Binding)ScriptBytecodeAdapter.castToType((Object)callSiteArray[13].callConstructor(Binding.class), Binding.class), io, null, configuration);
    }

    public Groovysh() {
        CallSite[] callSiteArray = Groovysh.$getCallSiteArray();
        this((IO)ScriptBytecodeAdapter.castToType((Object)callSiteArray[14].callConstructor(IO.class), IO.class));
    }

    private static Closure createDefaultRegistrar(ClassLoader classLoader) {
        Reference classLoader2 = new Reference((Object)classLoader);
        CallSite[] callSiteArray = Groovysh.$getCallSiteArray();
        public final class _createDefaultRegistrar_closure3
        extends Closure
        implements GeneratedClosure {
            private /* synthetic */ Reference classLoader;
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _createDefaultRegistrar_closure3(Object _outerInstance, Object _thisObject, Reference classLoader) {
                Reference reference;
                CallSite[] callSiteArray = _createDefaultRegistrar_closure3.$getCallSiteArray();
                super(_outerInstance, _thisObject);
                this.classLoader = reference = classLoader;
            }

            public Object doCall(Groovysh shell) {
                CallSite[] callSiteArray = _createDefaultRegistrar_closure3.$getCallSiteArray();
                URL xmlCommandResource = (URL)ScriptBytecodeAdapter.castToType((Object)callSiteArray[0].call(callSiteArray[1].callCurrent((GroovyObject)this), (Object)"commands.xml"), URL.class);
                if (ScriptBytecodeAdapter.compareNotEqual((Object)xmlCommandResource, null)) {
                    Object r = callSiteArray[2].callConstructor(XmlCommandRegistrar.class, (Object)shell, this.classLoader.get());
                    return callSiteArray[3].call(r, (Object)xmlCommandResource);
                }
                return callSiteArray[4].call(callSiteArray[5].callConstructor(DefaultCommandsRegistrar.class, (Object)shell));
            }

            @Generated
            public Object call(Groovysh shell) {
                CallSite[] callSiteArray = _createDefaultRegistrar_closure3.$getCallSiteArray();
                return callSiteArray[6].callCurrent((GroovyObject)this, (Object)shell);
            }

            @Generated
            public ClassLoader getClassLoader() {
                CallSite[] callSiteArray = _createDefaultRegistrar_closure3.$getCallSiteArray();
                return (ClassLoader)ScriptBytecodeAdapter.castToType((Object)this.classLoader.get(), ClassLoader.class);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (((Object)((Object)this)).getClass() != _createDefaultRegistrar_closure3.class) {
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
                stringArray[0] = "getResource";
                stringArray[1] = "getClass";
                stringArray[2] = "<$constructor$>";
                stringArray[3] = "register";
                stringArray[4] = "register";
                stringArray[5] = "<$constructor$>";
                stringArray[6] = "doCall";
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[7];
                _createDefaultRegistrar_closure3.$createCallSiteArray_1(stringArray);
                return new CallSiteArray(_createDefaultRegistrar_closure3.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _createDefaultRegistrar_closure3.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        return new _createDefaultRegistrar_closure3(Groovysh.class, Groovysh.class, classLoader2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public Object execute(String line) {
        CallSite[] callSiteArray = Groovysh.$getCallSiteArray();
        ValueRecorder valueRecorder = new ValueRecorder();
        try {
            String string = line;
            valueRecorder.record((Object)string, 8);
            boolean bl = ScriptBytecodeAdapter.compareNotEqual((Object)string, null);
            valueRecorder.record((Object)bl, 13);
            if (bl) {
                valueRecorder.clear();
            } else {
                ScriptBytecodeAdapter.assertFailed((Object)AssertionRenderer.render((String)"assert line != null", (ValueRecorder)valueRecorder), null);
            }
        }
        catch (Throwable throwable) {
            valueRecorder.clear();
            throw throwable;
        }
        if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[15].call(callSiteArray[16].call((Object)line)), (Object)0)) {
            return null;
        }
        if (__$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            callSiteArray[17].callCurrent((GroovyObject)this, (Object)line);
        } else {
            this.maybeRecordInput(line);
        }
        Object result = null;
        if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[18].callCurrent((GroovyObject)this, (Object)line))) {
            Object object;
            Object object2;
            result = __$stMC || BytecodeInterface8.disabledStandardMetaClass() ? (object2 = callSiteArray[19].callCurrent((GroovyObject)this, (Object)line)) : (object = this.executeCommand(line));
            if (!ScriptBytecodeAdapter.compareNotEqual((Object)result, null)) return result;
            callSiteArray[20].callCurrent((GroovyObject)this, result);
            return result;
        }
        List current = (List)ScriptBytecodeAdapter.castToType((Object)callSiteArray[21].callConstructor(ArrayList.class, callSiteArray[22].call((Object)this.buffers)), List.class);
        callSiteArray[23].call((Object)current, (Object)line);
        String importsSpec = null;
        if (__$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            Object object = callSiteArray[24].callCurrent((GroovyObject)this);
            importsSpec = ShortTypeHandling.castToString((Object)object);
        } else {
            String string;
            importsSpec = string = this.getImportStatements();
        }
        Object status = callSiteArray[25].call((Object)this.parser, callSiteArray[26].call((Object)ScriptBytecodeAdapter.createList((Object[])new Object[]{importsSpec}), (Object)current));
        Object object = callSiteArray[27].callGetProperty(status);
        if (ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[28].callGetProperty(ParseCode.class))) {
            block37: {
                callSiteArray[29].call(callSiteArray[30].callGroovyObjectGetProperty((Object)this), (Object)"Evaluating buffer...");
                if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[31].callGetProperty(callSiteArray[32].callGroovyObjectGetProperty((Object)this)))) {
                    callSiteArray[33].callCurrent((GroovyObject)this, (Object)current);
                }
                if (!BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                    if (!DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[34].call(Boolean.class, callSiteArray[35].callCurrent((GroovyObject)this, (Object)INTERPRETER_MODE_PREFERENCE_KEY, (Object)"false"))) || DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[36].callStatic(Groovysh.class, (Object)current))) {
                        List buff = (List)ScriptBytecodeAdapter.castToType((Object)callSiteArray[37].call(callSiteArray[38].call((Object)ScriptBytecodeAdapter.createList((Object[])new Object[]{importsSpec}), (Object)ScriptBytecodeAdapter.createList((Object[])new Object[]{"true"})), (Object)current), List.class);
                        try {
                            Object object3;
                            result = object3 = callSiteArray[40].call((Object)this.interp, (Object)buff);
                            callSiteArray[39].callCurrent((GroovyObject)this, object3);
                            break block37;
                        }
                        catch (MultipleCompilationErrorsException t) {
                            if (!DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[41].callCurrent((GroovyObject)this, (Object)t))) throw (Throwable)t;
                            callSiteArray[42].call((Object)this.buffers, (Object)current);
                            return result;
                        }
                    }
                    try {
                        Object object4;
                        result = object4 = callSiteArray[43].callCurrent((GroovyObject)this, (Object)importsSpec, (Object)current);
                        break block37;
                    }
                    catch (MultipleCompilationErrorsException t) {
                        if (!DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[44].callCurrent((GroovyObject)this, (Object)t))) throw (Throwable)t;
                        callSiteArray[45].call((Object)this.buffers, (Object)current);
                        return result;
                    }
                }
                if (!DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[46].call(Boolean.class, (Object)this.getPreference(INTERPRETER_MODE_PREFERENCE_KEY, "false"))) || DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[47].callStatic(Groovysh.class, (Object)current))) {
                    List buff = (List)ScriptBytecodeAdapter.castToType((Object)callSiteArray[48].call(callSiteArray[49].call((Object)ScriptBytecodeAdapter.createList((Object[])new Object[]{importsSpec}), (Object)ScriptBytecodeAdapter.createList((Object[])new Object[]{"true"})), (Object)current), List.class);
                    try {
                        Object object5;
                        result = object5 = callSiteArray[51].call((Object)this.interp, (Object)buff);
                        callSiteArray[50].callCurrent((GroovyObject)this, object5);
                        break block37;
                    }
                    catch (MultipleCompilationErrorsException t) {
                        if (!DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[52].callCurrent((GroovyObject)this, (Object)t))) throw (Throwable)t;
                        callSiteArray[53].call((Object)this.buffers, (Object)current);
                        return result;
                    }
                }
                try {
                    Object object6;
                    result = object6 = callSiteArray[54].callCurrent((GroovyObject)this, (Object)importsSpec, (Object)current);
                }
                catch (MultipleCompilationErrorsException t) {
                    if (!DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[55].callCurrent((GroovyObject)this, (Object)t))) throw (Throwable)t;
                    callSiteArray[56].call((Object)this.buffers, (Object)current);
                    return result;
                }
            }
            callSiteArray[57].call((Object)this.buffers);
            return result;
        }
        if (ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[58].callGetProperty(ParseCode.class))) {
            callSiteArray[59].call((Object)this.buffers, (Object)current);
            return result;
        } else {
            if (!ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[60].callGetProperty(ParseCode.class))) throw (Throwable)callSiteArray[62].callConstructor(Error.class, (Object)new GStringImpl(new Object[]{callSiteArray[63].callGetProperty(status)}, new String[]{"Invalid parse status: ", ""}));
            throw (Throwable)callSiteArray[61].callGetProperty(status);
        }
    }

    private boolean isIncompleteCaseOfAntlr4(MultipleCompilationErrorsException t) {
        return (t.getMessage().contains("Unexpected input: ") || t.getMessage().contains("Unexpected character: ")) && !(t.getMessage().contains("Unexpected input: '}'") || t.getMessage().contains("Unexpected input: ')'") || t.getMessage().contains("Unexpected input: ']'"));
    }

    public static boolean isTypeOrMethodDeclaration(List<String> buffer) {
        String joined = DefaultGroovyMethods.join(buffer, (String)"");
        return StringGroovyMethods.matches((CharSequence)joined, (Pattern)TYPEDEF_PATTERN) || StringGroovyMethods.matches((CharSequence)joined, (Pattern)METHODDEF_PATTERN);
    }

    private Object evaluateWithStoredBoundVars(String importsSpec, List<String> current) {
        Object object;
        CallSite[] callSiteArray = Groovysh.$getCallSiteArray();
        Object result = null;
        Reference variableBlocks = new Reference(null);
        Set boundVars = (Set)ScriptBytecodeAdapter.castToType((Object)callSiteArray[64].call(ScriptVariableAnalyzer.class, callSiteArray[65].call(callSiteArray[66].call((Object)importsSpec, callSiteArray[67].callGetProperty(Parser.class)), callSiteArray[68].call(current, callSiteArray[69].callGetProperty(Parser.class))), callSiteArray[70].callGroovyObjectGetProperty((Object)this.interp)), Set.class);
        if (DefaultTypeTransformation.booleanUnbox((Object)boundVars)) {
            GStringImpl gStringImpl = new GStringImpl(new Object[]{COLLECTED_BOUND_VARS_MAP_VARNAME}, new String[]{"", " = new HashMap();"});
            variableBlocks.set((Object)ShortTypeHandling.castToString((Object)gStringImpl));
            public final class _evaluateWithStoredBoundVars_closure4
            extends Closure
            implements GeneratedClosure {
                private /* synthetic */ Reference variableBlocks;
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;
                private static /* synthetic */ SoftReference $callSiteArray;

                public _evaluateWithStoredBoundVars_closure4(Object _outerInstance, Object _thisObject, Reference variableBlocks) {
                    Reference reference;
                    CallSite[] callSiteArray = _evaluateWithStoredBoundVars_closure4.$getCallSiteArray();
                    super(_outerInstance, _thisObject);
                    this.variableBlocks = reference = variableBlocks;
                }

                public Object doCall(String varname) {
                    CallSite[] callSiteArray = _evaluateWithStoredBoundVars_closure4.$getCallSiteArray();
                    Object object = callSiteArray[0].call(this.variableBlocks.get(), (Object)new GStringImpl(new Object[]{callSiteArray[1].callGroovyObjectGetProperty((Object)this), varname, varname}, new String[]{"\ntry {", "[\"", "\"] = ", ";\n} catch (MissingPropertyException e){}"}));
                    this.variableBlocks.set((Object)ShortTypeHandling.castToString((Object)object));
                    return object;
                }

                @Generated
                public Object call(String varname) {
                    CallSite[] callSiteArray = _evaluateWithStoredBoundVars_closure4.$getCallSiteArray();
                    if (__$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                        return callSiteArray[2].callCurrent((GroovyObject)this, (Object)varname);
                    }
                    return this.doCall(varname);
                }

                @Generated
                public String getVariableBlocks() {
                    CallSite[] callSiteArray = _evaluateWithStoredBoundVars_closure4.$getCallSiteArray();
                    return ShortTypeHandling.castToString((Object)this.variableBlocks.get());
                }

                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                    if (((Object)((Object)this)).getClass() != _evaluateWithStoredBoundVars_closure4.class) {
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
                    stringArray[0] = "plus";
                    stringArray[1] = "COLLECTED_BOUND_VARS_MAP_VARNAME";
                    stringArray[2] = "doCall";
                }

                private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                    String[] stringArray = new String[3];
                    _evaluateWithStoredBoundVars_closure4.$createCallSiteArray_1(stringArray);
                    return new CallSiteArray(_evaluateWithStoredBoundVars_closure4.class, stringArray);
                }

                private static /* synthetic */ CallSite[] $getCallSiteArray() {
                    CallSiteArray callSiteArray;
                    if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                        callSiteArray = _evaluateWithStoredBoundVars_closure4.$createCallSiteArray();
                        $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                    }
                    return callSiteArray.array;
                }
            }
            callSiteArray[71].call((Object)boundVars, (Object)new _evaluateWithStoredBoundVars_closure4(this, this, variableBlocks));
        }
        List buff = null;
        if (DefaultTypeTransformation.booleanUnbox((Object)((String)variableBlocks.get()))) {
            Object object2 = callSiteArray[72].call(callSiteArray[73].call(callSiteArray[74].call((Object)ScriptBytecodeAdapter.createList((Object[])new Object[]{importsSpec}), (Object)ScriptBytecodeAdapter.createList((Object[])new Object[]{"try {", "true"})), current), (Object)ScriptBytecodeAdapter.createList((Object[])new Object[]{callSiteArray[75].call(callSiteArray[76].call((Object)"} finally {", (Object)((String)variableBlocks.get())), (Object)"}")}));
            buff = (List)ScriptBytecodeAdapter.castToType((Object)object2, List.class);
        } else {
            Object object3 = callSiteArray[77].call(callSiteArray[78].call((Object)ScriptBytecodeAdapter.createList((Object[])new Object[]{importsSpec}), (Object)ScriptBytecodeAdapter.createList((Object[])new Object[]{"true"})), current);
            buff = (List)ScriptBytecodeAdapter.castToType((Object)object3, List.class);
        }
        result = object = callSiteArray[80].call((Object)this.interp, (Object)buff);
        callSiteArray[79].callCurrent((GroovyObject)this, object);
        if (DefaultTypeTransformation.booleanUnbox((Object)((String)variableBlocks.get()))) {
            Map boundVarValues = (Map)ScriptBytecodeAdapter.castToType((Object)callSiteArray[81].call(callSiteArray[82].callGroovyObjectGetProperty((Object)this.interp), (Object)COLLECTED_BOUND_VARS_MAP_VARNAME), Map.class);
            public final class _evaluateWithStoredBoundVars_closure5
            extends Closure
            implements GeneratedClosure {
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;
                private static /* synthetic */ SoftReference $callSiteArray;

                public _evaluateWithStoredBoundVars_closure5(Object _outerInstance, Object _thisObject) {
                    CallSite[] callSiteArray = _evaluateWithStoredBoundVars_closure5.$getCallSiteArray();
                    super(_outerInstance, _thisObject);
                }

                public Object doCall(String name, Object value) {
                    CallSite[] callSiteArray = _evaluateWithStoredBoundVars_closure5.$getCallSiteArray();
                    return callSiteArray[0].call(callSiteArray[1].callGroovyObjectGetProperty(callSiteArray[2].callGroovyObjectGetProperty((Object)this)), (Object)name, value);
                }

                @Generated
                public Object call(String name, Object value) {
                    CallSite[] callSiteArray = _evaluateWithStoredBoundVars_closure5.$getCallSiteArray();
                    return callSiteArray[3].callCurrent((GroovyObject)this, (Object)name, value);
                }

                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                    if (((Object)((Object)this)).getClass() != _evaluateWithStoredBoundVars_closure5.class) {
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
                    stringArray[0] = "setVariable";
                    stringArray[1] = "context";
                    stringArray[2] = "interp";
                    stringArray[3] = "doCall";
                }

                private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                    String[] stringArray = new String[4];
                    _evaluateWithStoredBoundVars_closure5.$createCallSiteArray_1(stringArray);
                    return new CallSiteArray(_evaluateWithStoredBoundVars_closure5.class, stringArray);
                }

                private static /* synthetic */ CallSite[] $getCallSiteArray() {
                    CallSiteArray callSiteArray;
                    if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                        callSiteArray = _evaluateWithStoredBoundVars_closure5.$createCallSiteArray();
                        $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                    }
                    return callSiteArray.array;
                }
            }
            callSiteArray[83].call((Object)boundVarValues, (Object)new _evaluateWithStoredBoundVars_closure5(this, this));
        }
        return result;
    }

    protected Object executeCommand(String line) {
        CallSite[] callSiteArray = Groovysh.$getCallSiteArray();
        return ScriptBytecodeAdapter.invokeMethodOnSuperN(Groovysh.class, (GroovyObject)this, (String)"execute", (Object[])new Object[]{line});
    }

    public void displayBuffer(List buffer) {
        CallSite[] callSiteArray = Groovysh.$getCallSiteArray();
        ValueRecorder valueRecorder = new ValueRecorder();
        try {
            List list = buffer;
            valueRecorder.record((Object)list, 8);
            if (DefaultTypeTransformation.booleanUnbox((Object)list)) {
                valueRecorder.clear();
            } else {
                ScriptBytecodeAdapter.assertFailed((Object)AssertionRenderer.render((String)"assert buffer", (ValueRecorder)valueRecorder), null);
            }
        }
        catch (Throwable throwable) {
            valueRecorder.clear();
            throw throwable;
        }
        public final class _displayBuffer_closure6
        extends Closure
        implements GeneratedClosure {
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _displayBuffer_closure6(Object _outerInstance, Object _thisObject) {
                CallSite[] callSiteArray = _displayBuffer_closure6.$getCallSiteArray();
                super(_outerInstance, _thisObject);
            }

            public Object doCall(Object line, Object index) {
                CallSite[] callSiteArray = _displayBuffer_closure6.$getCallSiteArray();
                Object lineNum = callSiteArray[0].callCurrent((GroovyObject)this, index);
                return callSiteArray[1].call(callSiteArray[2].callGetProperty(callSiteArray[3].callGroovyObjectGetProperty((Object)this)), (Object)new GStringImpl(new Object[]{lineNum, line}, new String[]{" ", "@|bold >|@ ", ""}));
            }

            @Generated
            public Object call(Object line, Object index) {
                CallSite[] callSiteArray = _displayBuffer_closure6.$getCallSiteArray();
                return callSiteArray[4].callCurrent((GroovyObject)this, line, index);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (((Object)((Object)this)).getClass() != _displayBuffer_closure6.class) {
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
                stringArray[0] = "formatLineNumber";
                stringArray[1] = "println";
                stringArray[2] = "out";
                stringArray[3] = "io";
                stringArray[4] = "doCall";
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[5];
                _displayBuffer_closure6.$createCallSiteArray_1(stringArray);
                return new CallSiteArray(_displayBuffer_closure6.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _displayBuffer_closure6.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        callSiteArray[84].call((Object)buffer, (Object)new _displayBuffer_closure6(this, this));
    }

    public String getImportStatements() {
        CallSite[] callSiteArray = Groovysh.$getCallSiteArray();
        public final class _getImportStatements_closure7
        extends Closure
        implements GeneratedClosure {
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _getImportStatements_closure7(Object _outerInstance, Object _thisObject) {
                CallSite[] callSiteArray = _getImportStatements_closure7.$getCallSiteArray();
                super(_outerInstance, _thisObject);
            }

            public Object doCall(String it) {
                CallSite[] callSiteArray = _getImportStatements_closure7.$getCallSiteArray();
                return new GStringImpl(new Object[]{it}, new String[]{"import ", ";"});
            }

            @Generated
            public Object call(String it) {
                CallSite[] callSiteArray = _getImportStatements_closure7.$getCallSiteArray();
                if (__$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                    return callSiteArray[0].callCurrent((GroovyObject)this, (Object)it);
                }
                return this.doCall(it);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (((Object)((Object)this)).getClass() != _getImportStatements_closure7.class) {
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

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[1];
                stringArray[0] = "doCall";
                return new CallSiteArray(_getImportStatements_closure7.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _getImportStatements_closure7.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        return ShortTypeHandling.castToString((Object)callSiteArray[85].call(callSiteArray[86].call(this.imports, (Object)new _getImportStatements_closure7(this, this)), (Object)""));
    }

    private String buildPrompt() {
        CallSite[] callSiteArray = Groovysh.$getCallSiteArray();
        Object lineNum = callSiteArray[87].callCurrent((GroovyObject)this, callSiteArray[88].call(callSiteArray[89].call((Object)this.buffers)));
        Object groovyshellProperty = callSiteArray[90].call(System.class, (Object)"groovysh.prompt");
        if (DefaultTypeTransformation.booleanUnbox((Object)groovyshellProperty)) {
            return ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{groovyshellProperty, lineNum}, new String[]{"@|bold ", ":|@", "@|bold >|@ "}));
        }
        Object groovyshellEnv = callSiteArray[91].call(System.class, (Object)"GROOVYSH_PROMPT");
        if (DefaultTypeTransformation.booleanUnbox((Object)groovyshellEnv)) {
            return ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{groovyshellEnv, lineNum}, new String[]{"@|bold ", ":|@", "@|bold >|@ "}));
        }
        return ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{lineNum}, new String[]{"@|bold groovy:|@", "@|bold >|@ "}));
    }

    public String getIndentPrefix() {
        CallSite[] callSiteArray = Groovysh.$getCallSiteArray();
        List buffer = (List)ScriptBytecodeAdapter.castToType((Object)callSiteArray[92].call((Object)this.buffers), List.class);
        if (ScriptBytecodeAdapter.compareLessThan((Object)callSiteArray[93].call((Object)buffer), (Object)1)) {
            return "";
        }
        StringBuilder src = (StringBuilder)ScriptBytecodeAdapter.castToType((Object)callSiteArray[94].callConstructor(StringBuilder.class), StringBuilder.class);
        String line = null;
        Iterator iterator = (Iterator)ScriptBytecodeAdapter.castToType((Object)callSiteArray[95].call((Object)buffer), Iterator.class);
        if (iterator != null) {
            while (iterator.hasNext()) {
                line = ShortTypeHandling.castToString(iterator.next());
                callSiteArray[96].call(callSiteArray[97].call((Object)src, (Object)line), (Object)"\n");
            }
        }
        Object lexer = callSiteArray[98].call(CurlyCountingGroovyLexer.class, callSiteArray[99].call((Object)src));
        int curlyIndent = DefaultTypeTransformation.intUnbox((Object)callSiteArray[100].call(callSiteArray[101].call(lexer), (Object)this.indentSize));
        return ShortTypeHandling.castToString((Object)callSiteArray[102].call((Object)" ", callSiteArray[103].call(Math.class, (Object)curlyIndent, (Object)0)));
    }

    public String renderPrompt() {
        CallSite[] callSiteArray = Groovysh.$getCallSiteArray();
        if (__$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            return ShortTypeHandling.castToString((Object)callSiteArray[104].call((Object)this.prompt, callSiteArray[105].callCurrent((GroovyObject)this)));
        }
        return ShortTypeHandling.castToString((Object)callSiteArray[106].call((Object)this.prompt, (Object)this.buildPrompt()));
    }

    protected String formatLineNumber(int num) {
        CallSite[] callSiteArray = Groovysh.$getCallSiteArray();
        ValueRecorder valueRecorder = new ValueRecorder();
        try {
            int n = num;
            valueRecorder.record((Object)n, 8);
            boolean bl = n >= 0;
            valueRecorder.record((Object)bl, 12);
            if (bl) {
                valueRecorder.clear();
            } else {
                ScriptBytecodeAdapter.assertFailed((Object)AssertionRenderer.render((String)"assert num >= 0", (ValueRecorder)valueRecorder), null);
            }
        }
        catch (Throwable throwable) {
            valueRecorder.clear();
            throw throwable;
        }
        return ShortTypeHandling.castToString((Object)callSiteArray[107].call(callSiteArray[108].call((Object)num), (Object)3, (Object)"0"));
    }

    public File getUserStateDirectory() {
        CallSite[] callSiteArray = Groovysh.$getCallSiteArray();
        Object userHome = callSiteArray[109].callConstructor(File.class, callSiteArray[110].call(System.class, (Object)"user.home"));
        Object dir = callSiteArray[111].callConstructor(File.class, userHome, (Object)".groovy");
        return (File)ScriptBytecodeAdapter.castToType((Object)callSiteArray[112].callGetProperty(dir), File.class);
    }

    protected void loadUserScript(String filename) {
        CallSite[] callSiteArray = Groovysh.$getCallSiteArray();
        ValueRecorder valueRecorder = new ValueRecorder();
        try {
            String string = filename;
            valueRecorder.record((Object)string, 8);
            if (DefaultTypeTransformation.booleanUnbox((Object)string)) {
                valueRecorder.clear();
            } else {
                ScriptBytecodeAdapter.assertFailed((Object)AssertionRenderer.render((String)"assert filename", (ValueRecorder)valueRecorder), null);
            }
        }
        catch (Throwable throwable) {
            valueRecorder.clear();
            throw throwable;
        }
        File file = null;
        if (__$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            Object object = callSiteArray[113].callConstructor(File.class, callSiteArray[114].callCurrent((GroovyObject)this), (Object)filename);
            file = (File)ScriptBytecodeAdapter.castToType((Object)object, File.class);
        } else {
            Object object = callSiteArray[115].callConstructor(File.class, (Object)this.getUserStateDirectory(), (Object)filename);
            file = (File)ScriptBytecodeAdapter.castToType((Object)object, File.class);
        }
        if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[116].call((Object)file))) {
            Command command = (Command)ScriptBytecodeAdapter.asType((Object)callSiteArray[117].call(callSiteArray[118].callGroovyObjectGetProperty((Object)this), callSiteArray[119].callGetProperty(LoadCommand.class)), Command.class);
            if (DefaultTypeTransformation.booleanUnbox((Object)command)) {
                callSiteArray[120].call(callSiteArray[121].callGroovyObjectGetProperty((Object)this), (Object)new GStringImpl(new Object[]{file}, new String[]{"Loading user-script: ", ""}));
                Closure previousHook = this.resultHook;
                public final class _loadUserScript_closure8
                extends Closure
                implements GeneratedClosure {
                    private static /* synthetic */ ClassInfo $staticClassInfo;
                    public static transient /* synthetic */ boolean __$stMC;
                    private static /* synthetic */ SoftReference $callSiteArray;

                    public _loadUserScript_closure8(Object _outerInstance, Object _thisObject) {
                        CallSite[] callSiteArray = _loadUserScript_closure8.$getCallSiteArray();
                        super(_outerInstance, _thisObject);
                    }

                    public Object doCall(Object result) {
                        CallSite[] callSiteArray = _loadUserScript_closure8.$getCallSiteArray();
                        return null;
                    }

                    protected /* synthetic */ MetaClass $getStaticMetaClass() {
                        if (((Object)((Object)this)).getClass() != _loadUserScript_closure8.class) {
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

                    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                        String[] stringArray = new String[]{};
                        return new CallSiteArray(_loadUserScript_closure8.class, stringArray);
                    }

                    private static /* synthetic */ CallSite[] $getCallSiteArray() {
                        CallSiteArray callSiteArray;
                        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                            callSiteArray = _loadUserScript_closure8.$createCallSiteArray();
                            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                        }
                        return callSiteArray.array;
                    }
                }
                _loadUserScript_closure8 _loadUserScript_closure82 = new _loadUserScript_closure8(this, this);
                this.resultHook = _loadUserScript_closure82;
                try {
                    callSiteArray[122].call((Object)command, callSiteArray[123].call(callSiteArray[124].call((Object)file)));
                }
                finally {
                    Closure closure = previousHook;
                    this.resultHook = (Closure)ScriptBytecodeAdapter.castToType((Object)closure, Closure.class);
                }
            } else {
                callSiteArray[125].call(callSiteArray[126].callGroovyObjectGetProperty((Object)this), (Object)new GStringImpl(new Object[]{callSiteArray[127].callGetProperty(LoadCommand.class)}, new String[]{"Unable to load user-script, missing '", "' command"}));
            }
        }
    }

    protected void maybeRecordInput(String line) {
        CallSite[] callSiteArray = Groovysh.$getCallSiteArray();
        RecordCommand record = (RecordCommand)ScriptBytecodeAdapter.castToType((Object)callSiteArray[128].call(callSiteArray[129].callGroovyObjectGetProperty((Object)this), callSiteArray[130].callGetProperty(RecordCommand.class)), RecordCommand.class);
        if (ScriptBytecodeAdapter.compareNotEqual((Object)record, null)) {
            callSiteArray[131].call((Object)record, (Object)line);
        }
    }

    protected void maybeRecordResult(Object result) {
        CallSite[] callSiteArray = Groovysh.$getCallSiteArray();
        RecordCommand record = (RecordCommand)ScriptBytecodeAdapter.castToType((Object)callSiteArray[132].call(callSiteArray[133].callGroovyObjectGetProperty((Object)this), callSiteArray[134].callGetProperty(RecordCommand.class)), RecordCommand.class);
        if (ScriptBytecodeAdapter.compareNotEqual((Object)record, null)) {
            callSiteArray[135].call((Object)record, result);
        }
    }

    protected void maybeRecordError(Throwable cause) {
        CallSite[] callSiteArray = Groovysh.$getCallSiteArray();
        RecordCommand record = (RecordCommand)ScriptBytecodeAdapter.castToType((Object)callSiteArray[136].call(callSiteArray[137].callGroovyObjectGetProperty((Object)this), callSiteArray[138].callGetProperty(RecordCommand.class)), RecordCommand.class);
        if (ScriptBytecodeAdapter.compareNotEqual((Object)record, null)) {
            if (__$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[139].callCurrent((GroovyObject)this, (Object)SANITIZE_PREFERENCE_KEY, (Object)"false"))) {
                    Object object = callSiteArray[140].call(StackTraceUtils.class, (Object)cause);
                    cause = (Throwable)ScriptBytecodeAdapter.castToType((Object)object, Throwable.class);
                }
            } else if (DefaultTypeTransformation.booleanUnbox((Object)this.getPreference(SANITIZE_PREFERENCE_KEY, "false"))) {
                Object object = callSiteArray[141].call(StackTraceUtils.class, (Object)cause);
                cause = (Throwable)ScriptBytecodeAdapter.castToType((Object)object, Throwable.class);
            }
            callSiteArray[142].call((Object)record, (Object)cause);
        }
    }

    private void setLastResult(Object result) {
        CallSite[] callSiteArray = Groovysh.$getCallSiteArray();
        if (ScriptBytecodeAdapter.compareEqual((Object)this.resultHook, null)) {
            throw (Throwable)callSiteArray[143].callConstructor(IllegalStateException.class, (Object)"Result hook is not set");
        }
        callSiteArray[144].call((Object)this.resultHook, (Object)ScriptBytecodeAdapter.createPojoWrapper((Object)result, Object.class));
        Object object = result;
        callSiteArray[145].call(callSiteArray[146].callGroovyObjectGetProperty((Object)this.interp), (Object)"_", object);
        callSiteArray[147].callCurrent((GroovyObject)this, result);
    }

    protected String getPreference(String key, String theDefault) {
        CallSite[] callSiteArray = Groovysh.$getCallSiteArray();
        return ShortTypeHandling.castToString((Object)callSiteArray[148].call(Preferences.class, (Object)key, (Object)theDefault));
    }

    private void displayError(Throwable cause) {
        CallSite[] callSiteArray = Groovysh.$getCallSiteArray();
        if (ScriptBytecodeAdapter.compareEqual((Object)this.errorHook, null)) {
            throw (Throwable)callSiteArray[149].callConstructor(IllegalStateException.class, (Object)"Error hook is not set");
        }
        if (cause instanceof MissingPropertyException && DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[150].callGetProperty((Object)cause)) && ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[151].callGetProperty(callSiteArray[152].callGetProperty((Object)cause)), (Object)callSiteArray[153].callGetProperty(Interpreter.class))) {
            callSiteArray[154].call(callSiteArray[155].callGetProperty(callSiteArray[156].callGroovyObjectGetProperty((Object)this)), callSiteArray[157].call((Object)"@|bold,red Unknown property|@: ", callSiteArray[158].callGetProperty((Object)cause)));
            return;
        }
        callSiteArray[159].call((Object)this.errorHook, (Object)cause);
    }

    public int run(String evalString, List<String> filenames) {
        public final class _run_closure9
        extends Closure
        implements GeneratedClosure {
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _run_closure9(Object _outerInstance, Object _thisObject) {
                CallSite[] callSiteArray = _run_closure9.$getCallSiteArray();
                super(_outerInstance, _thisObject);
            }

            public Object doCall(String it) {
                CallSite[] callSiteArray = _run_closure9.$getCallSiteArray();
                return new GStringImpl(new Object[]{callSiteArray[0].callGetProperty(LoadCommand.class), it}, new String[]{"", " ", ""});
            }

            @Generated
            public Object call(String it) {
                CallSite[] callSiteArray = _run_closure9.$getCallSiteArray();
                if (__$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                    return callSiteArray[1].callCurrent((GroovyObject)this, (Object)it);
                }
                return this.doCall(it);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (((Object)((Object)this)).getClass() != _run_closure9.class) {
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
                stringArray[0] = "COMMAND_NAME";
                stringArray[1] = "doCall";
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[2];
                _run_closure9.$createCallSiteArray_1(stringArray);
                return new CallSiteArray(_run_closure9.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _run_closure9.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        CallSite[] callSiteArray = Groovysh.$getCallSiteArray();
        List startCommands = ScriptBytecodeAdapter.createList((Object[])new Object[0]);
        if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            if (ScriptBytecodeAdapter.compareNotEqual((Object)evalString, null) && ScriptBytecodeAdapter.compareGreaterThan((Object)callSiteArray[160].call(callSiteArray[161].call((Object)evalString)), (Object)0)) {
                callSiteArray[162].call((Object)startCommands, (Object)evalString);
            }
        } else if (ScriptBytecodeAdapter.compareNotEqual((Object)evalString, null) && ScriptBytecodeAdapter.compareGreaterThan((Object)callSiteArray[163].call(callSiteArray[164].call((Object)evalString)), (Object)0)) {
            callSiteArray[165].call((Object)startCommands, (Object)evalString);
        }
        if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            if (ScriptBytecodeAdapter.compareNotEqual(filenames, null) && ScriptBytecodeAdapter.compareGreaterThan((Object)callSiteArray[166].call(filenames), (Object)0)) {
                callSiteArray[167].call((Object)startCommands, callSiteArray[168].call(filenames, (Object)new _run_closure9(this, this)));
            }
        } else if (ScriptBytecodeAdapter.compareNotEqual(filenames, null) && ScriptBytecodeAdapter.compareGreaterThan((Object)callSiteArray[169].call(filenames), (Object)0)) {
            callSiteArray[170].call((Object)startCommands, callSiteArray[171].call(filenames, (Object)new _run_closure9(this, this)));
        }
        return DefaultTypeTransformation.intUnbox((Object)callSiteArray[172].callCurrent((GroovyObject)this, callSiteArray[173].call((Object)startCommands, (Object)"\n")));
    }

    public int run(String commandLine) {
        CallSite[] callSiteArray = Groovysh.$getCallSiteArray();
        Object code = null;
        try {
            if (__$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                callSiteArray[174].callCurrent((GroovyObject)this, (Object)"groovysh.profile");
            } else {
                this.loadUserScript("groovysh.profile");
            }
            if (__$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                callSiteArray[175].callCurrent((GroovyObject)this, (Object)"groovysh.rc");
            } else {
                this.loadUserScript("groovysh.rc");
            }
            Object object = callSiteArray[176].callConstructor(InteractiveShellRunner.class, (Object)this, (Object)ScriptBytecodeAdapter.createGroovyObjectWrapper((GroovyObject)ScriptBytecodeAdapter.getMethodPointer((Object)this, (String)"renderPrompt"), Closure.class));
            this.runner = (InteractiveShellRunner)ScriptBytecodeAdapter.castToType((Object)object, InteractiveShellRunner.class);
            if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                if (ScriptBytecodeAdapter.compareNotEqual((Object)commandLine, null) && ScriptBytecodeAdapter.compareGreaterThan((Object)callSiteArray[177].call(callSiteArray[178].call((Object)commandLine)), (Object)0)) {
                    callSiteArray[179].call(callSiteArray[180].callGroovyObjectGetProperty((Object)this.runner), callSiteArray[181].call((Object)commandLine, (Object)"\n"));
                }
            } else if (ScriptBytecodeAdapter.compareNotEqual((Object)commandLine, null) && ScriptBytecodeAdapter.compareGreaterThan((Object)callSiteArray[182].call(callSiteArray[183].call((Object)commandLine)), (Object)0)) {
                callSiteArray[184].call(callSiteArray[185].callGroovyObjectGetProperty((Object)this.runner), callSiteArray[186].call((Object)commandLine, (Object)"\n"));
            }
            File histFile = (File)ScriptBytecodeAdapter.castToType((Object)callSiteArray[187].callConstructor(File.class, callSiteArray[188].callGroovyObjectGetProperty((Object)this), (Object)"groovysh.history"), File.class);
            Object object2 = callSiteArray[189].callConstructor(FileHistory.class, (Object)histFile);
            this.history = (FileHistory)ScriptBytecodeAdapter.castToType((Object)object2, FileHistory.class);
            callSiteArray[190].call((Object)this.runner, (Object)this.history);
            Closure closure = ScriptBytecodeAdapter.getMethodPointer((Object)this, (String)"displayError");
            ScriptBytecodeAdapter.setGroovyObjectProperty((Object)closure, Groovysh.class, (GroovyObject)this.runner, (String)"errorHandler");
            callSiteArray[191].callCurrent((GroovyObject)this, (Object)this.runner);
            callSiteArray[192].call((Object)this.runner);
            int n = 0;
            code = n;
        }
        catch (ExitNotification n) {
            Object object;
            callSiteArray[193].call(callSiteArray[194].callGroovyObjectGetProperty((Object)this), (Object)new GStringImpl(new Object[]{callSiteArray[195].callGroovyObjectGetProperty((Object)n)}, new String[]{"Exiting w/code: ", ""}));
            code = object = callSiteArray[196].callGroovyObjectGetProperty((Object)n);
        }
        catch (Throwable t) {
            callSiteArray[197].call(callSiteArray[198].callGetProperty(callSiteArray[199].callGroovyObjectGetProperty((Object)this)), callSiteArray[200].call((Object)messages, (Object)"info.fatal", (Object)t));
            callSiteArray[201].call((Object)t, callSiteArray[202].callGetProperty(callSiteArray[203].callGroovyObjectGetProperty((Object)this)));
            int n = 1;
            code = n;
        }
        ValueRecorder valueRecorder = new ValueRecorder();
        try {
            Object object = code;
            valueRecorder.record(object, 8);
            boolean bl = ScriptBytecodeAdapter.compareNotEqual((Object)object, null);
            valueRecorder.record((Object)bl, 13);
            if (bl) {
                valueRecorder.clear();
            } else {
                ScriptBytecodeAdapter.assertFailed((Object)AssertionRenderer.render((String)"assert code != null", (ValueRecorder)valueRecorder), null);
            }
        }
        catch (Throwable throwable) {
            valueRecorder.clear();
            throw throwable;
        }
        return DefaultTypeTransformation.intUnbox((Object)code);
    }

    public void displayWelcomeBanner(InteractiveShellRunner runner) {
        CallSite[] callSiteArray = Groovysh.$getCallSiteArray();
        if (!DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[204].callGetProperty(callSiteArray[205].callGroovyObjectGetProperty((Object)this))) && DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[206].callGetProperty(callSiteArray[207].callGroovyObjectGetProperty((Object)this)))) {
            return;
        }
        Terminal term = (Terminal)ScriptBytecodeAdapter.castToType((Object)callSiteArray[208].callGetProperty(callSiteArray[209].callGroovyObjectGetProperty((Object)runner)), Terminal.class);
        if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[210].callGetProperty(callSiteArray[211].callGroovyObjectGetProperty((Object)this)))) {
            callSiteArray[212].call(callSiteArray[213].callGroovyObjectGetProperty((Object)this), (Object)new GStringImpl(new Object[]{term}, new String[]{"Terminal (", ")"}));
            callSiteArray[214].call(callSiteArray[215].callGroovyObjectGetProperty((Object)this), (Object)new GStringImpl(new Object[]{callSiteArray[216].callGetProperty((Object)term)}, new String[]{"    Supported:  ", ""}));
            callSiteArray[217].call(callSiteArray[218].callGroovyObjectGetProperty((Object)this), (Object)new GStringImpl(new Object[]{callSiteArray[219].callGetProperty((Object)term)}, new String[]{"    ECHO:       (enabled: ", ")"}));
            callSiteArray[220].call(callSiteArray[221].callGroovyObjectGetProperty((Object)this), (Object)new GStringImpl(new Object[]{callSiteArray[222].call((Object)term), callSiteArray[223].call((Object)term)}, new String[]{"    H x W:      ", " x ", ""}));
            callSiteArray[224].call(callSiteArray[225].callGroovyObjectGetProperty((Object)this), (Object)new GStringImpl(new Object[]{callSiteArray[226].call((Object)term)}, new String[]{"    ANSI:       ", ""}));
            if (term instanceof WindowsTerminal) {
                WindowsTerminal winterm = (WindowsTerminal)ScriptBytecodeAdapter.castToType((Object)term, WindowsTerminal.class);
                callSiteArray[227].call(callSiteArray[228].callGroovyObjectGetProperty((Object)this), (Object)new GStringImpl(new Object[]{callSiteArray[229].callGetProperty((Object)winterm)}, new String[]{"    Direct:     ", ""}));
            }
        }
        if (!DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[230].callGetProperty(callSiteArray[231].callGroovyObjectGetProperty((Object)this)))) {
            int width = DefaultTypeTransformation.intUnbox((Object)callSiteArray[232].call((Object)term));
            if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                if (width < 1) {
                    int n;
                    width = n = 80;
                }
            } else if (width < 1) {
                int n;
                width = n = 80;
            }
            callSiteArray[233].call(callSiteArray[234].callGetProperty(callSiteArray[235].callGroovyObjectGetProperty((Object)this)), callSiteArray[236].call((Object)messages, (Object)"startup_banner.0", callSiteArray[237].callGetProperty(GroovySystem.class), callSiteArray[238].call(callSiteArray[239].callGetProperty(System.class), (Object)"java.version")));
            callSiteArray[240].call(callSiteArray[241].callGetProperty(callSiteArray[242].callGroovyObjectGetProperty((Object)this)), callSiteArray[243].call((Object)messages, (Object)"startup_banner.1"));
            if (!BytecodeInterface8.isOrigInt() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                callSiteArray[244].call(callSiteArray[245].callGetProperty(callSiteArray[246].callGroovyObjectGetProperty((Object)this)), callSiteArray[247].call((Object)"-", callSiteArray[248].call((Object)width, (Object)1)));
            } else {
                callSiteArray[249].call(callSiteArray[250].callGetProperty(callSiteArray[251].callGroovyObjectGetProperty((Object)this)), callSiteArray[252].call((Object)"-", (Object)(width - 1)));
            }
        }
    }

    @Override
    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != Groovysh.class) {
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
        Object object = Groovysh.$getCallSiteArray()[253].callConstructor(MessageSource.class, Groovysh.class);
        messages = (MessageSource)ScriptBytecodeAdapter.castToType((Object)object, MessageSource.class);
        Object object2 = ScriptBytecodeAdapter.bitwiseNegate((Object)"^\\s*((?:public|protected|private|static|abstract|final)\\s+)*(?:class|enum|interface).*");
        TYPEDEF_PATTERN = (Pattern)ScriptBytecodeAdapter.castToType((Object)object2, Pattern.class);
        Object object3 = ScriptBytecodeAdapter.bitwiseNegate((Object)"^\\s*((?:public|protected|private|static|abstract|final|synchronized)\\s+)*[a-zA-Z_.]+[a-zA-Z_.<>]+\\s+[a-zA-Z_]+\\(.*");
        METHODDEF_PATTERN = (Pattern)ScriptBytecodeAdapter.castToType((Object)object3, Pattern.class);
    }

    @Generated
    public final BufferManager getBuffers() {
        return this.buffers;
    }

    @Generated
    public final Parser getParser() {
        return this.parser;
    }

    @Generated
    public final Interpreter getInterp() {
        return this.interp;
    }

    @Generated
    public final List<String> getImports() {
        return this.imports;
    }

    @Generated
    public int getIndentSize() {
        return this.indentSize;
    }

    @Generated
    public void setIndentSize(int n) {
        this.indentSize = n;
    }

    @Generated
    public InteractiveShellRunner getRunner() {
        return this.runner;
    }

    @Generated
    public void setRunner(InteractiveShellRunner interactiveShellRunner) {
        this.runner = interactiveShellRunner;
    }

    @Generated
    public FileHistory getHistory() {
        return this.history;
    }

    @Generated
    public void setHistory(FileHistory fileHistory) {
        this.history = fileHistory;
    }

    @Generated
    public boolean getHistoryFull() {
        return this.historyFull;
    }

    @Generated
    public boolean isHistoryFull() {
        return this.historyFull;
    }

    @Generated
    public void setHistoryFull(boolean bl) {
        this.historyFull = bl;
    }

    @Generated
    public String getEvictedLine() {
        return this.evictedLine;
    }

    @Generated
    public void setEvictedLine(String string) {
        this.evictedLine = string;
    }

    @Generated
    public PackageHelper getPackageHelper() {
        return this.packageHelper;
    }

    @Generated
    public void setPackageHelper(PackageHelper packageHelper) {
        this.packageHelper = packageHelper;
    }

    @Generated
    public final Closure getDefaultResultHook() {
        return this.defaultResultHook;
    }

    @Generated
    public Closure getResultHook() {
        return this.resultHook;
    }

    @Generated
    public void setResultHook(Closure closure) {
        this.resultHook = closure;
    }

    @Generated
    public final Closure getDefaultErrorHook() {
        return this.defaultErrorHook;
    }

    @Generated
    public Closure getErrorHook() {
        return this.errorHook;
    }

    @Generated
    public void setErrorHook(Closure closure) {
        this.errorHook = closure;
    }

    public /* synthetic */ Object super$2$execute(String string) {
        return super.execute(string);
    }

    public /* synthetic */ MetaClass super$2$$getStaticMetaClass() {
        return super.$getStaticMetaClass();
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
        stringArray[0] = "DEFAULT";
        stringArray[1] = "<$constructor$>";
        stringArray[2] = "<$constructor$>";
        stringArray[3] = "<$constructor$>";
        stringArray[4] = "createDefaultRegistrar";
        stringArray[5] = "<$constructor$>";
        stringArray[6] = "call";
        stringArray[7] = "<$constructor$>";
        stringArray[8] = "contextClassLoader";
        stringArray[9] = "currentThread";
        stringArray[10] = "<$constructor$>";
        stringArray[11] = "contextClassLoader";
        stringArray[12] = "currentThread";
        stringArray[13] = "<$constructor$>";
        stringArray[14] = "<$constructor$>";
        stringArray[15] = "size";
        stringArray[16] = "trim";
        stringArray[17] = "maybeRecordInput";
        stringArray[18] = "isExecutable";
        stringArray[19] = "executeCommand";
        stringArray[20] = "setLastResult";
        stringArray[21] = "<$constructor$>";
        stringArray[22] = "current";
        stringArray[23] = "leftShift";
        stringArray[24] = "getImportStatements";
        stringArray[25] = "parse";
        stringArray[26] = "plus";
        stringArray[27] = "code";
        stringArray[28] = "COMPLETE";
        stringArray[29] = "debug";
        stringArray[30] = "log";
        stringArray[31] = "verbose";
        stringArray[32] = "io";
        stringArray[33] = "displayBuffer";
        stringArray[34] = "valueOf";
        stringArray[35] = "getPreference";
        stringArray[36] = "isTypeOrMethodDeclaration";
        stringArray[37] = "plus";
        stringArray[38] = "plus";
        stringArray[39] = "setLastResult";
        stringArray[40] = "evaluate";
        stringArray[41] = "isIncompleteCaseOfAntlr4";
        stringArray[42] = "updateSelected";
        stringArray[43] = "evaluateWithStoredBoundVars";
        stringArray[44] = "isIncompleteCaseOfAntlr4";
        stringArray[45] = "updateSelected";
        stringArray[46] = "valueOf";
        stringArray[47] = "isTypeOrMethodDeclaration";
        stringArray[48] = "plus";
        stringArray[49] = "plus";
        stringArray[50] = "setLastResult";
        stringArray[51] = "evaluate";
        stringArray[52] = "isIncompleteCaseOfAntlr4";
        stringArray[53] = "updateSelected";
        stringArray[54] = "evaluateWithStoredBoundVars";
        stringArray[55] = "isIncompleteCaseOfAntlr4";
        stringArray[56] = "updateSelected";
        stringArray[57] = "clearSelected";
        stringArray[58] = "INCOMPLETE";
        stringArray[59] = "updateSelected";
        stringArray[60] = "ERROR";
        stringArray[61] = "cause";
        stringArray[62] = "<$constructor$>";
        stringArray[63] = "code";
        stringArray[64] = "getBoundVars";
        stringArray[65] = "plus";
        stringArray[66] = "plus";
        stringArray[67] = "NEWLINE";
        stringArray[68] = "join";
        stringArray[69] = "NEWLINE";
        stringArray[70] = "classLoader";
        stringArray[71] = "each";
        stringArray[72] = "plus";
        stringArray[73] = "plus";
        stringArray[74] = "plus";
        stringArray[75] = "plus";
        stringArray[76] = "plus";
        stringArray[77] = "plus";
        stringArray[78] = "plus";
        stringArray[79] = "setLastResult";
        stringArray[80] = "evaluate";
        stringArray[81] = "getVariable";
        stringArray[82] = "context";
        stringArray[83] = "each";
        stringArray[84] = "eachWithIndex";
        stringArray[85] = "join";
        stringArray[86] = "collect";
        stringArray[87] = "formatLineNumber";
        stringArray[88] = "size";
        stringArray[89] = "current";
        stringArray[90] = "getProperty";
        stringArray[91] = "getenv";
        stringArray[92] = "current";
        stringArray[93] = "size";
        stringArray[94] = "<$constructor$>";
        stringArray[95] = "iterator";
        stringArray[96] = "append";
        stringArray[97] = "append";
        stringArray[98] = "createGroovyLexer";
        stringArray[99] = "toString";
        stringArray[100] = "multiply";
        stringArray[101] = "countCurlyLevel";
        stringArray[102] = "multiply";
        stringArray[103] = "max";
        stringArray[104] = "render";
        stringArray[105] = "buildPrompt";
        stringArray[106] = "render";
        stringArray[107] = "padLeft";
        stringArray[108] = "toString";
        stringArray[109] = "<$constructor$>";
        stringArray[110] = "getProperty";
        stringArray[111] = "<$constructor$>";
        stringArray[112] = "canonicalFile";
        stringArray[113] = "<$constructor$>";
        stringArray[114] = "getUserStateDirectory";
        stringArray[115] = "<$constructor$>";
        stringArray[116] = "exists";
        stringArray[117] = "getAt";
        stringArray[118] = "registry";
        stringArray[119] = "COMMAND_NAME";
        stringArray[120] = "debug";
        stringArray[121] = "log";
        stringArray[122] = "load";
        stringArray[123] = "toURL";
        stringArray[124] = "toURI";
        stringArray[125] = "error";
        stringArray[126] = "log";
        stringArray[127] = "COMMAND_NAME";
        stringArray[128] = "getAt";
        stringArray[129] = "registry";
        stringArray[130] = "COMMAND_NAME";
        stringArray[131] = "recordInput";
        stringArray[132] = "getAt";
        stringArray[133] = "registry";
        stringArray[134] = "COMMAND_NAME";
        stringArray[135] = "recordResult";
        stringArray[136] = "getAt";
        stringArray[137] = "registry";
        stringArray[138] = "COMMAND_NAME";
        stringArray[139] = "getPreference";
        stringArray[140] = "deepSanitize";
        stringArray[141] = "deepSanitize";
        stringArray[142] = "recordError";
        stringArray[143] = "<$constructor$>";
        stringArray[144] = "call";
        stringArray[145] = "putAt";
        stringArray[146] = "context";
        stringArray[147] = "maybeRecordResult";
        stringArray[148] = "get";
        stringArray[149] = "<$constructor$>";
        stringArray[150] = "type";
        stringArray[151] = "canonicalName";
        stringArray[152] = "type";
        stringArray[153] = "SCRIPT_FILENAME";
        stringArray[154] = "println";
        stringArray[155] = "err";
        stringArray[156] = "io";
        stringArray[157] = "plus";
        stringArray[158] = "property";
        stringArray[159] = "call";
        stringArray[160] = "size";
        stringArray[161] = "trim";
        stringArray[162] = "add";
        stringArray[163] = "size";
        stringArray[164] = "trim";
        stringArray[165] = "add";
        stringArray[166] = "size";
        stringArray[167] = "addAll";
        stringArray[168] = "collect";
        stringArray[169] = "size";
        stringArray[170] = "addAll";
        stringArray[171] = "collect";
        stringArray[172] = "run";
        stringArray[173] = "join";
        stringArray[174] = "loadUserScript";
        stringArray[175] = "loadUserScript";
        stringArray[176] = "<$constructor$>";
        stringArray[177] = "size";
        stringArray[178] = "trim";
        stringArray[179] = "insert";
        stringArray[180] = "wrappedInputStream";
        stringArray[181] = "plus";
        stringArray[182] = "size";
        stringArray[183] = "trim";
        stringArray[184] = "insert";
        stringArray[185] = "wrappedInputStream";
        stringArray[186] = "plus";
        stringArray[187] = "<$constructor$>";
        stringArray[188] = "userStateDirectory";
        stringArray[189] = "<$constructor$>";
        stringArray[190] = "setHistory";
        stringArray[191] = "displayWelcomeBanner";
        stringArray[192] = "run";
        stringArray[193] = "debug";
        stringArray[194] = "log";
        stringArray[195] = "code";
        stringArray[196] = "code";
        stringArray[197] = "println";
        stringArray[198] = "err";
        stringArray[199] = "io";
        stringArray[200] = "format";
        stringArray[201] = "printStackTrace";
        stringArray[202] = "err";
        stringArray[203] = "io";
        stringArray[204] = "debug";
        stringArray[205] = "log";
        stringArray[206] = "quiet";
        stringArray[207] = "io";
        stringArray[208] = "terminal";
        stringArray[209] = "reader";
        stringArray[210] = "debug";
        stringArray[211] = "log";
        stringArray[212] = "debug";
        stringArray[213] = "log";
        stringArray[214] = "debug";
        stringArray[215] = "log";
        stringArray[216] = "supported";
        stringArray[217] = "debug";
        stringArray[218] = "log";
        stringArray[219] = "echoEnabled";
        stringArray[220] = "debug";
        stringArray[221] = "log";
        stringArray[222] = "getHeight";
        stringArray[223] = "getWidth";
        stringArray[224] = "debug";
        stringArray[225] = "log";
        stringArray[226] = "isAnsiSupported";
        stringArray[227] = "debug";
        stringArray[228] = "log";
        stringArray[229] = "directConsole";
        stringArray[230] = "quiet";
        stringArray[231] = "io";
        stringArray[232] = "getWidth";
        stringArray[233] = "println";
        stringArray[234] = "out";
        stringArray[235] = "io";
        stringArray[236] = "format";
        stringArray[237] = "version";
        stringArray[238] = "getAt";
        stringArray[239] = "properties";
        stringArray[240] = "println";
        stringArray[241] = "out";
        stringArray[242] = "io";
        stringArray[243] = "getAt";
        stringArray[244] = "println";
        stringArray[245] = "out";
        stringArray[246] = "io";
        stringArray[247] = "multiply";
        stringArray[248] = "minus";
        stringArray[249] = "println";
        stringArray[250] = "out";
        stringArray[251] = "io";
        stringArray[252] = "multiply";
        stringArray[253] = "<$constructor$>";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[254];
        Groovysh.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(Groovysh.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = Groovysh.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }

    public final class _closure1
    extends Closure
    implements GeneratedClosure {
        private static /* synthetic */ ClassInfo $staticClassInfo;
        public static transient /* synthetic */ boolean __$stMC;
        private static /* synthetic */ SoftReference $callSiteArray;

        public _closure1(Object _outerInstance, Object _thisObject) {
            CallSite[] callSiteArray = _closure1.$getCallSiteArray();
            super(_outerInstance, _thisObject);
        }

        public Object doCall(Object result) {
            int n;
            int n2;
            CallSite[] callSiteArray = _closure1.$getCallSiteArray();
            int showLastResult = 0;
            showLastResult = !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass() ? (n2 = !DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[0].callGetProperty(callSiteArray[1].callGroovyObjectGetProperty((Object)this))) && (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[2].callGetProperty(callSiteArray[3].callGroovyObjectGetProperty((Object)this))) || DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[4].callCurrent((GroovyObject)this, callSiteArray[5].callGroovyObjectGetProperty((Object)this), (Object)"false"))) ? 1 : 0) : (n = !DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[6].callGetProperty(callSiteArray[7].callGroovyObjectGetProperty((Object)this))) && (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[8].callGetProperty(callSiteArray[9].callGroovyObjectGetProperty((Object)this))) || DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[10].callCurrent((GroovyObject)this, callSiteArray[11].callGroovyObjectGetProperty((Object)this), (Object)"false"))) ? 1 : 0);
            if (showLastResult != 0) {
                return callSiteArray[12].call(callSiteArray[13].callGetProperty(callSiteArray[14].callGroovyObjectGetProperty((Object)this)), (Object)new GStringImpl(new Object[]{callSiteArray[15].call(FormatHelper.class, result)}, new String[]{"@|bold ===>|@ ", ""}));
            }
            return null;
        }

        protected /* synthetic */ MetaClass $getStaticMetaClass() {
            if (((Object)((Object)this)).getClass() != _closure1.class) {
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
            stringArray[0] = "quiet";
            stringArray[1] = "io";
            stringArray[2] = "verbose";
            stringArray[3] = "io";
            stringArray[4] = "getPreference";
            stringArray[5] = "SHOW_LAST_RESULT_PREFERENCE_KEY";
            stringArray[6] = "quiet";
            stringArray[7] = "io";
            stringArray[8] = "verbose";
            stringArray[9] = "io";
            stringArray[10] = "getPreference";
            stringArray[11] = "SHOW_LAST_RESULT_PREFERENCE_KEY";
            stringArray[12] = "println";
            stringArray[13] = "out";
            stringArray[14] = "io";
            stringArray[15] = "toString";
        }

        private static /* synthetic */ CallSiteArray $createCallSiteArray() {
            String[] stringArray = new String[16];
            _closure1.$createCallSiteArray_1(stringArray);
            return new CallSiteArray(_closure1.class, stringArray);
        }

        private static /* synthetic */ CallSite[] $getCallSiteArray() {
            CallSiteArray callSiteArray;
            if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                callSiteArray = _closure1.$createCallSiteArray();
                $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
            }
            return callSiteArray.array;
        }
    }

    public final class _closure2
    extends Closure
    implements GeneratedClosure {
        private static /* synthetic */ ClassInfo $staticClassInfo;
        public static transient /* synthetic */ boolean __$stMC;
        private static /* synthetic */ SoftReference $callSiteArray;

        public _closure2(Object _outerInstance, Object _thisObject) {
            CallSite[] callSiteArray = _closure2.$getCallSiteArray();
            super(_outerInstance, _thisObject);
        }

        public Object doCall(Throwable cause) {
            CallSite[] callSiteArray = _closure2.$getCallSiteArray();
            ValueRecorder valueRecorder = new ValueRecorder();
            try {
                Throwable throwable = cause;
                valueRecorder.record((Object)throwable, 8);
                boolean bl = ScriptBytecodeAdapter.compareNotEqual((Object)throwable, null);
                valueRecorder.record((Object)bl, 14);
                if (bl) {
                    valueRecorder.clear();
                } else {
                    ScriptBytecodeAdapter.assertFailed((Object)AssertionRenderer.render((String)"assert cause != null", (ValueRecorder)valueRecorder), null);
                }
            }
            catch (Throwable throwable) {
                valueRecorder.clear();
                throw throwable;
            }
            if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[0].callGetProperty(callSiteArray[1].callGroovyObjectGetProperty((Object)this))) || !(cause instanceof CompilationFailedException)) {
                callSiteArray[2].call(callSiteArray[3].callGetProperty(callSiteArray[4].callGroovyObjectGetProperty((Object)this)), (Object)new GStringImpl(new Object[]{callSiteArray[5].callGetProperty(callSiteArray[6].call((Object)cause))}, new String[]{"@|bold,red ERROR|@ ", ":"}));
            }
            if (cause instanceof MultipleCompilationErrorsException) {
                Writer data = (Writer)ScriptBytecodeAdapter.castToType((Object)callSiteArray[7].callConstructor(StringBuilderWriter.class), Writer.class);
                PrintWriter writer = (PrintWriter)ScriptBytecodeAdapter.castToType((Object)callSiteArray[8].callConstructor(PrintWriter.class, (Object)data), PrintWriter.class);
                ErrorCollector collector = (ErrorCollector)ScriptBytecodeAdapter.castToType((Object)callSiteArray[9].call((Object)((MultipleCompilationErrorsException)ScriptBytecodeAdapter.castToType((Object)cause, MultipleCompilationErrorsException.class))), ErrorCollector.class);
                Iterator msgIterator = (Iterator)ScriptBytecodeAdapter.castToType((Object)callSiteArray[10].call(callSiteArray[11].call((Object)collector)), Iterator.class);
                while (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[12].call((Object)msgIterator))) {
                    Message errorMsg = (Message)ScriptBytecodeAdapter.castToType((Object)callSiteArray[13].call((Object)msgIterator), Message.class);
                    callSiteArray[14].call((Object)errorMsg, (Object)writer);
                    if (!DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[15].call((Object)msgIterator))) continue;
                    callSiteArray[16].call((Object)writer);
                }
                return callSiteArray[17].call(callSiteArray[18].callGetProperty(callSiteArray[19].callGroovyObjectGetProperty((Object)this)), (Object)new GStringImpl(new Object[]{callSiteArray[20].call((Object)data)}, new String[]{"@|bold,red ", "|@"}));
            }
            callSiteArray[21].call(callSiteArray[22].callGetProperty(callSiteArray[23].callGroovyObjectGetProperty((Object)this)), (Object)new GStringImpl(new Object[]{callSiteArray[24].callGetProperty((Object)cause)}, new String[]{"@|bold,red ", "|@"}));
            callSiteArray[25].callCurrent((GroovyObject)this, (Object)cause);
            if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[26].callGetProperty(callSiteArray[27].callGroovyObjectGetProperty((Object)this)))) {
                return callSiteArray[28].call(callSiteArray[29].callGroovyObjectGetProperty((Object)this), (Object)cause);
            }
            boolean sanitize = DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[30].callCurrent((GroovyObject)this, callSiteArray[31].callGroovyObjectGetProperty((Object)this), (Object)"false"));
            if (!BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                if (!DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[32].callGetProperty(callSiteArray[33].callGroovyObjectGetProperty((Object)this))) && sanitize) {
                    Object object = callSiteArray[34].call(StackTraceUtils.class, (Object)cause);
                    cause = (Throwable)ScriptBytecodeAdapter.castToType((Object)object, Throwable.class);
                }
            } else if (!DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[35].callGetProperty(callSiteArray[36].callGroovyObjectGetProperty((Object)this))) && sanitize) {
                Object object = callSiteArray[37].call(StackTraceUtils.class, (Object)cause);
                cause = (Throwable)ScriptBytecodeAdapter.castToType((Object)object, Throwable.class);
            }
            Object trace = callSiteArray[38].callGetProperty((Object)cause);
            Object buff = callSiteArray[39].callConstructor(StringBuilder.class);
            boolean doBreak = false;
            Object e = null;
            Iterator iterator = (Iterator)ScriptBytecodeAdapter.castToType((Object)callSiteArray[40].call(trace), Iterator.class);
            if (iterator != null) {
                while (iterator.hasNext()) {
                    e = iterator.next();
                    if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[41].callGetProperty(e), (Object)callSiteArray[42].callGetProperty(Interpreter.class)) && ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[43].callGetProperty(e), (Object)"run")) {
                        boolean bl;
                        if (ScriptBytecodeAdapter.compareNotEqual((Object)callSiteArray[44].callGetProperty(callSiteArray[45].callGroovyObjectGetProperty((Object)this)), (Object)callSiteArray[46].callGetProperty(IO.Verbosity.class)) && ScriptBytecodeAdapter.compareNotEqual((Object)callSiteArray[47].callGetProperty(callSiteArray[48].callGroovyObjectGetProperty((Object)this)), (Object)callSiteArray[49].callGetProperty(IO.Verbosity.class))) break;
                        doBreak = bl = true;
                    }
                    callSiteArray[50].call(buff, (Object)new GStringImpl(new Object[]{callSiteArray[51].callGetProperty(e), callSiteArray[52].callGetProperty(e)}, new String[]{"        @|bold at|@ ", ".", " (@|bold "}));
                    callSiteArray[53].call(buff, DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[54].callGetProperty(e)) ? "Native Method" : (ScriptBytecodeAdapter.compareNotEqual((Object)callSiteArray[55].callGetProperty(e), null) && ScriptBytecodeAdapter.compareNotEqual((Object)callSiteArray[56].callGetProperty(e), (Object)-1) ? new GStringImpl(new Object[]{callSiteArray[57].callGetProperty(e), callSiteArray[58].callGetProperty(e)}, new String[]{"", ":", ""}) : (ScriptBytecodeAdapter.compareNotEqual((Object)callSiteArray[59].callGetProperty(e), null) ? callSiteArray[60].callGetProperty(e) : "Unknown Source")));
                    callSiteArray[61].call(buff, (Object)"|@)");
                    callSiteArray[62].call(callSiteArray[63].callGetProperty(callSiteArray[64].callGroovyObjectGetProperty((Object)this)), buff);
                    callSiteArray[65].call(buff, (Object)0);
                    if (!doBreak) continue;
                    callSiteArray[66].call(callSiteArray[67].callGetProperty(callSiteArray[68].callGroovyObjectGetProperty((Object)this)), (Object)"        @|bold ...|@");
                    break;
                }
            }
            return null;
        }

        @Generated
        public Object call(Throwable cause) {
            CallSite[] callSiteArray = _closure2.$getCallSiteArray();
            return callSiteArray[69].callCurrent((GroovyObject)this, (Object)cause);
        }

        protected /* synthetic */ MetaClass $getStaticMetaClass() {
            if (((Object)((Object)this)).getClass() != _closure2.class) {
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
            stringArray[0] = "debug";
            stringArray[1] = "log";
            stringArray[2] = "println";
            stringArray[3] = "err";
            stringArray[4] = "io";
            stringArray[5] = "name";
            stringArray[6] = "getClass";
            stringArray[7] = "<$constructor$>";
            stringArray[8] = "<$constructor$>";
            stringArray[9] = "getErrorCollector";
            stringArray[10] = "iterator";
            stringArray[11] = "getErrors";
            stringArray[12] = "hasNext";
            stringArray[13] = "next";
            stringArray[14] = "write";
            stringArray[15] = "hasNext";
            stringArray[16] = "println";
            stringArray[17] = "println";
            stringArray[18] = "err";
            stringArray[19] = "io";
            stringArray[20] = "toString";
            stringArray[21] = "println";
            stringArray[22] = "err";
            stringArray[23] = "io";
            stringArray[24] = "message";
            stringArray[25] = "maybeRecordError";
            stringArray[26] = "debug";
            stringArray[27] = "log";
            stringArray[28] = "debug";
            stringArray[29] = "log";
            stringArray[30] = "getPreference";
            stringArray[31] = "SANITIZE_PREFERENCE_KEY";
            stringArray[32] = "verbose";
            stringArray[33] = "io";
            stringArray[34] = "deepSanitize";
            stringArray[35] = "verbose";
            stringArray[36] = "io";
            stringArray[37] = "deepSanitize";
            stringArray[38] = "stackTrace";
            stringArray[39] = "<$constructor$>";
            stringArray[40] = "iterator";
            stringArray[41] = "className";
            stringArray[42] = "SCRIPT_FILENAME";
            stringArray[43] = "methodName";
            stringArray[44] = "verbosity";
            stringArray[45] = "io";
            stringArray[46] = "DEBUG";
            stringArray[47] = "verbosity";
            stringArray[48] = "io";
            stringArray[49] = "VERBOSE";
            stringArray[50] = "leftShift";
            stringArray[51] = "className";
            stringArray[52] = "methodName";
            stringArray[53] = "leftShift";
            stringArray[54] = "nativeMethod";
            stringArray[55] = "fileName";
            stringArray[56] = "lineNumber";
            stringArray[57] = "fileName";
            stringArray[58] = "lineNumber";
            stringArray[59] = "fileName";
            stringArray[60] = "fileName";
            stringArray[61] = "leftShift";
            stringArray[62] = "println";
            stringArray[63] = "err";
            stringArray[64] = "io";
            stringArray[65] = "setLength";
            stringArray[66] = "println";
            stringArray[67] = "err";
            stringArray[68] = "io";
            stringArray[69] = "doCall";
        }

        private static /* synthetic */ CallSiteArray $createCallSiteArray() {
            String[] stringArray = new String[70];
            _closure2.$createCallSiteArray_1(stringArray);
            return new CallSiteArray(_closure2.class, stringArray);
        }

        private static /* synthetic */ CallSite[] $getCallSiteArray() {
            CallSiteArray callSiteArray;
            if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                callSiteArray = _closure2.$createCallSiteArray();
                $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
            }
            return callSiteArray.array;
        }
    }
}

