/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.Closure
 *  groovy.lang.GroovyObject
 *  groovy.lang.MetaClass
 *  groovy.transform.Generated
 *  jline.console.ConsoleReader
 *  jline.console.completer.CandidateListCompletionHandler
 *  jline.console.completer.CompletionHandler
 *  jline.console.history.FileHistory
 *  org.codehaus.groovy.reflection.ClassInfo
 *  org.codehaus.groovy.runtime.BytecodeInterface8
 *  org.codehaus.groovy.runtime.GStringImpl
 *  org.codehaus.groovy.runtime.ScriptBytecodeAdapter
 *  org.codehaus.groovy.runtime.callsite.CallSite
 *  org.codehaus.groovy.runtime.callsite.CallSiteArray
 *  org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation
 *  org.codehaus.groovy.runtime.typehandling.ShortTypeHandling
 *  org.codehaus.groovy.tools.shell.IO$Verbosity
 *  org.codehaus.groovy.tools.shell.util.Preferences
 */
package org.apache.groovy.groovysh;

import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import groovy.transform.Generated;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import java.util.Iterator;
import java.util.List;
import jline.console.ConsoleReader;
import jline.console.completer.CandidateListCompletionHandler;
import jline.console.completer.CompletionHandler;
import jline.console.history.FileHistory;
import org.apache.groovy.groovysh.Command;
import org.apache.groovy.groovysh.CommandsMultiCompleter;
import org.apache.groovy.groovysh.Groovysh;
import org.apache.groovy.groovysh.ShellRunner;
import org.apache.groovy.groovysh.completion.FileNameCompleter;
import org.apache.groovy.groovysh.completion.antlr4.CustomClassSyntaxCompleter;
import org.apache.groovy.groovysh.completion.antlr4.GroovySyntaxCompleter;
import org.apache.groovy.groovysh.completion.antlr4.ImportsSyntaxCompleter;
import org.apache.groovy.groovysh.completion.antlr4.KeywordSyntaxCompleter;
import org.apache.groovy.groovysh.completion.antlr4.ReflectionCompleter;
import org.apache.groovy.groovysh.completion.antlr4.VariableSyntaxCompleter;
import org.apache.groovy.groovysh.util.WrappedInputStream;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.BytecodeInterface8;
import org.codehaus.groovy.runtime.GStringImpl;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;
import org.codehaus.groovy.runtime.typehandling.ShortTypeHandling;
import org.codehaus.groovy.tools.shell.IO;
import org.codehaus.groovy.tools.shell.util.Preferences;

public class InteractiveShellRunner
extends ShellRunner
implements Runnable {
    private ConsoleReader reader;
    private final Closure prompt;
    private final CommandsMultiCompleter completer;
    private WrappedInputStream wrappedInputStream;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private static /* synthetic */ SoftReference $callSiteArray;

    public InteractiveShellRunner(Groovysh shell, Closure prompt) {
        Closure closure;
        CallSite[] callSiteArray = InteractiveShellRunner.$getCallSiteArray();
        super(shell);
        this.prompt = closure = prompt;
        Object object = callSiteArray[0].callConstructor(WrappedInputStream.class, callSiteArray[1].callGetProperty(callSiteArray[2].callGroovyObjectGetProperty((Object)shell)));
        this.wrappedInputStream = (WrappedInputStream)ScriptBytecodeAdapter.castToType((Object)object, WrappedInputStream.class);
        Object object2 = callSiteArray[3].callConstructor(ConsoleReader.class, (Object)this.wrappedInputStream, callSiteArray[4].callGetProperty(callSiteArray[5].callGroovyObjectGetProperty((Object)shell)));
        this.reader = (ConsoleReader)ScriptBytecodeAdapter.castToType((Object)object2, ConsoleReader.class);
        CompletionHandler currentCompletionHandler = (CompletionHandler)ScriptBytecodeAdapter.castToType((Object)callSiteArray[6].call((Object)this.reader), CompletionHandler.class);
        if (currentCompletionHandler instanceof CandidateListCompletionHandler) {
            callSiteArray[7].call((Object)((CandidateListCompletionHandler)ScriptBytecodeAdapter.castToType((Object)currentCompletionHandler, CandidateListCompletionHandler.class)), (Object)true);
            callSiteArray[8].call((Object)((CandidateListCompletionHandler)ScriptBytecodeAdapter.castToType((Object)currentCompletionHandler, CandidateListCompletionHandler.class)), (Object)false);
        }
        boolean bl = false;
        ScriptBytecodeAdapter.setProperty((Object)bl, null, (Object)this.reader, (String)"expandEvents");
        Object object3 = callSiteArray[9].callConstructor(CommandsMultiCompleter.class);
        this.completer = (CommandsMultiCompleter)((Object)ScriptBytecodeAdapter.castToType((Object)object3, CommandsMultiCompleter.class));
        callSiteArray[10].call((Object)this.reader, (Object)this.completer);
        Object reflectionCompleter = callSiteArray[11].callConstructor(ReflectionCompleter.class, (Object)shell);
        Object classnameCompleter = callSiteArray[12].callConstructor(CustomClassSyntaxCompleter.class, (Object)shell);
        List identifierCompleters = ScriptBytecodeAdapter.createList((Object[])new Object[]{callSiteArray[13].callConstructor(KeywordSyntaxCompleter.class), callSiteArray[14].callConstructor(VariableSyntaxCompleter.class, (Object)shell), classnameCompleter, callSiteArray[15].callConstructor(ImportsSyntaxCompleter.class, (Object)shell)});
        Object filenameCompleter = callSiteArray[16].callConstructor(FileNameCompleter.class, (Object)false);
        List completerArgs = ScriptBytecodeAdapter.createList((Object[])new Object[]{shell, reflectionCompleter, classnameCompleter, identifierCompleters, filenameCompleter});
        callSiteArray[17].call((Object)this.reader, callSiteArray[18].callConstructor(GroovySyntaxCompleter.class, ScriptBytecodeAdapter.despreadList((Object[])new Object[0], (Object[])new Object[]{completerArgs}, (int[])new int[]{0})));
    }

    @Override
    public void run() {
        CallSite[] callSiteArray = InteractiveShellRunner.$getCallSiteArray();
        Command command = null;
        Iterator iterator = (Iterator)ScriptBytecodeAdapter.castToType((Object)callSiteArray[19].call(callSiteArray[20].call(callSiteArray[21].callGroovyObjectGetProperty(callSiteArray[22].callGroovyObjectGetProperty((Object)this)))), Iterator.class);
        if (iterator != null) {
            while (iterator.hasNext()) {
                command = (Command)ScriptBytecodeAdapter.castToType(iterator.next(), Command.class);
                callSiteArray[23].call((Object)this.completer, (Object)command);
            }
        }
        callSiteArray[24].call((Object)this.completer);
        if (__$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            callSiteArray[25].callCurrent((GroovyObject)this);
        } else {
            this.adjustHistory();
        }
        ScriptBytecodeAdapter.invokeMethodOnSuper0(InteractiveShellRunner.class, (GroovyObject)this, (String)"run");
    }

    public void setHistory(FileHistory history) {
        CallSite[] callSiteArray = InteractiveShellRunner.$getCallSiteArray();
        FileHistory fileHistory = history;
        ScriptBytecodeAdapter.setProperty((Object)fileHistory, null, (Object)this.reader, (String)"history");
        Object dir = callSiteArray[26].callGetProperty(callSiteArray[27].callGetProperty((Object)history));
        if (!DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[28].call(dir))) {
            callSiteArray[29].call(dir);
            callSiteArray[30].call(callSiteArray[31].callGroovyObjectGetProperty((Object)this), (Object)new GStringImpl(new Object[]{dir}, new String[]{"Created base directory for history file: ", ""}));
        }
        callSiteArray[32].call(callSiteArray[33].callGroovyObjectGetProperty((Object)this), (Object)new GStringImpl(new Object[]{callSiteArray[34].callGetProperty((Object)history)}, new String[]{"Using history file: ", ""}));
    }

    /*
     * Loose catch block
     */
    @Override
    protected String readLine() {
        CallSite[] callSiteArray = InteractiveShellRunner.$getCallSiteArray();
        if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[35].call(Boolean.class, callSiteArray[36].call(Preferences.class, callSiteArray[37].callGetProperty(Groovysh.class)))) && ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[38].call(callSiteArray[39].callGetProperty(callSiteArray[40].callGroovyObjectGetProperty(callSiteArray[41].callGroovyObjectGetProperty((Object)this)))), (Object)0)) {
            callSiteArray[42].call((Object)this.wrappedInputStream, callSiteArray[43].callGroovyObjectGetProperty((Object)((Groovysh)ScriptBytecodeAdapter.castToType((Object)callSiteArray[44].callGroovyObjectGetProperty((Object)this), Groovysh.class))));
        }
        String string = ShortTypeHandling.castToString((Object)callSiteArray[45].call((Object)this.reader, (Object)ScriptBytecodeAdapter.createPojoWrapper((Object)((String)ScriptBytecodeAdapter.asType((Object)callSiteArray[46].call((Object)this.prompt), String.class)), String.class)));
        try {
            return string;
        }
        catch (StringIndexOutOfBoundsException e) {
            callSiteArray[47].call(callSiteArray[48].callGroovyObjectGetProperty((Object)this), (Object)"HACK: Try and work around GROOVY-2152 for now", (Object)e);
            callSiteArray[49].call((Object)this.reader);
            String string2 = "";
            return string2;
        }
        catch (Throwable t) {
            if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[50].callGetProperty(callSiteArray[51].callGroovyObjectGetProperty(callSiteArray[52].callGroovyObjectGetProperty((Object)this))), (Object)callSiteArray[53].callGetProperty(IO.Verbosity.class))) {
                throw t;
            }
            callSiteArray[54].call((Object)this.reader);
            String string3 = "";
            {
                catch (Throwable throwable) {
                    throw throwable;
                }
            }
            return string3;
        }
    }

    @Override
    protected boolean work() {
        CallSite[] callSiteArray = InteractiveShellRunner.$getCallSiteArray();
        boolean result = DefaultTypeTransformation.booleanUnbox((Object)ScriptBytecodeAdapter.invokeMethodOnSuper0(InteractiveShellRunner.class, (GroovyObject)this, (String)"work"));
        if (__$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            callSiteArray[55].callCurrent((GroovyObject)this);
        } else {
            this.adjustHistory();
        }
        return result;
    }

    private void adjustHistory() {
        CallSite[] callSiteArray = InteractiveShellRunner.$getCallSiteArray();
        if (callSiteArray[56].callGroovyObjectGetProperty((Object)this) instanceof Groovysh) {
            Object history = callSiteArray[57].callGroovyObjectGetProperty(callSiteArray[58].callGroovyObjectGetProperty((Object)this));
            if (!BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                boolean bl = ScriptBytecodeAdapter.compareNotEqual((Object)history, null) && ScriptBytecodeAdapter.compareGreaterThanEqual((Object)callSiteArray[59].call(history), (Object)callSiteArray[60].callGetProperty(history));
                ScriptBytecodeAdapter.setGroovyObjectProperty((Object)bl, InteractiveShellRunner.class, (GroovyObject)callSiteArray[61].callGroovyObjectGetProperty((Object)this), (String)"historyFull");
            } else {
                boolean bl = ScriptBytecodeAdapter.compareNotEqual((Object)history, null) && ScriptBytecodeAdapter.compareGreaterThanEqual((Object)callSiteArray[62].call(history), (Object)callSiteArray[63].callGetProperty(history));
                ScriptBytecodeAdapter.setGroovyObjectProperty((Object)bl, InteractiveShellRunner.class, (GroovyObject)callSiteArray[64].callGroovyObjectGetProperty((Object)this), (String)"historyFull");
            }
            if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[65].callGroovyObjectGetProperty(callSiteArray[66].callGroovyObjectGetProperty((Object)this)))) {
                Object first = callSiteArray[67].call(history);
                if (DefaultTypeTransformation.booleanUnbox((Object)first)) {
                    Object object = callSiteArray[68].call(first);
                    ScriptBytecodeAdapter.setGroovyObjectProperty((Object)object, InteractiveShellRunner.class, (GroovyObject)callSiteArray[69].callGroovyObjectGetProperty((Object)this), (String)"evictedLine");
                }
            }
        }
    }

    @Override
    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != InteractiveShellRunner.class) {
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

    @Generated
    public ConsoleReader getReader() {
        return this.reader;
    }

    @Generated
    public void setReader(ConsoleReader consoleReader) {
        this.reader = consoleReader;
    }

    @Generated
    public final Closure getPrompt() {
        return this.prompt;
    }

    @Generated
    public final CommandsMultiCompleter getCompleter() {
        return this.completer;
    }

    @Generated
    public WrappedInputStream getWrappedInputStream() {
        return this.wrappedInputStream;
    }

    @Generated
    public void setWrappedInputStream(WrappedInputStream wrappedInputStream) {
        this.wrappedInputStream = wrappedInputStream;
    }

    public /* synthetic */ boolean super$2$work() {
        return super.work();
    }

    public /* synthetic */ MetaClass super$2$$getStaticMetaClass() {
        return super.$getStaticMetaClass();
    }

    public /* synthetic */ void super$2$run() {
        super.run();
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
        stringArray[0] = "<$constructor$>";
        stringArray[1] = "inputStream";
        stringArray[2] = "io";
        stringArray[3] = "<$constructor$>";
        stringArray[4] = "outputStream";
        stringArray[5] = "io";
        stringArray[6] = "getCompletionHandler";
        stringArray[7] = "setStripAnsi";
        stringArray[8] = "setPrintSpaceAfterFullCompletion";
        stringArray[9] = "<$constructor$>";
        stringArray[10] = "addCompleter";
        stringArray[11] = "<$constructor$>";
        stringArray[12] = "<$constructor$>";
        stringArray[13] = "<$constructor$>";
        stringArray[14] = "<$constructor$>";
        stringArray[15] = "<$constructor$>";
        stringArray[16] = "<$constructor$>";
        stringArray[17] = "addCompleter";
        stringArray[18] = "<$constructor$>";
        stringArray[19] = "iterator";
        stringArray[20] = "commands";
        stringArray[21] = "registry";
        stringArray[22] = "shell";
        stringArray[23] = "add";
        stringArray[24] = "refresh";
        stringArray[25] = "adjustHistory";
        stringArray[26] = "parentFile";
        stringArray[27] = "file";
        stringArray[28] = "exists";
        stringArray[29] = "mkdirs";
        stringArray[30] = "debug";
        stringArray[31] = "log";
        stringArray[32] = "debug";
        stringArray[33] = "log";
        stringArray[34] = "file";
        stringArray[35] = "valueOf";
        stringArray[36] = "get";
        stringArray[37] = "AUTOINDENT_PREFERENCE_KEY";
        stringArray[38] = "available";
        stringArray[39] = "inputStream";
        stringArray[40] = "io";
        stringArray[41] = "shell";
        stringArray[42] = "insert";
        stringArray[43] = "indentPrefix";
        stringArray[44] = "shell";
        stringArray[45] = "readLine";
        stringArray[46] = "call";
        stringArray[47] = "debug";
        stringArray[48] = "log";
        stringArray[49] = "println";
        stringArray[50] = "verbosity";
        stringArray[51] = "io";
        stringArray[52] = "shell";
        stringArray[53] = "DEBUG";
        stringArray[54] = "println";
        stringArray[55] = "adjustHistory";
        stringArray[56] = "shell";
        stringArray[57] = "history";
        stringArray[58] = "shell";
        stringArray[59] = "size";
        stringArray[60] = "maxSize";
        stringArray[61] = "shell";
        stringArray[62] = "size";
        stringArray[63] = "maxSize";
        stringArray[64] = "shell";
        stringArray[65] = "historyFull";
        stringArray[66] = "shell";
        stringArray[67] = "first";
        stringArray[68] = "value";
        stringArray[69] = "shell";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[70];
        InteractiveShellRunner.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(InteractiveShellRunner.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = InteractiveShellRunner.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }
}

