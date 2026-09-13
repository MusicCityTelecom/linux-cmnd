/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.GroovyObject
 *  groovy.lang.MetaClass
 *  jline.console.completer.AggregateCompleter
 *  jline.console.completer.Completer
 *  jline.console.completer.NullCompleter
 *  jline.console.completer.StringsCompleter
 *  org.codehaus.groovy.control.CompilationFailedException
 *  org.codehaus.groovy.reflection.ClassInfo
 *  org.codehaus.groovy.runtime.GStringImpl
 *  org.codehaus.groovy.runtime.ScriptBytecodeAdapter
 *  org.codehaus.groovy.runtime.callsite.CallSite
 *  org.codehaus.groovy.runtime.callsite.CallSiteArray
 *  org.codehaus.groovy.runtime.powerassert.AssertionRenderer
 *  org.codehaus.groovy.runtime.powerassert.ValueRecorder
 *  org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation
 */
package org.apache.groovy.groovysh.commands;

import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import java.util.List;
import java.util.regex.Pattern;
import jline.console.completer.AggregateCompleter;
import jline.console.completer.Completer;
import jline.console.completer.NullCompleter;
import jline.console.completer.StringsCompleter;
import org.apache.groovy.groovysh.CommandSupport;
import org.apache.groovy.groovysh.Groovysh;
import org.apache.groovy.groovysh.Interpreter;
import org.apache.groovy.groovysh.commands.ImportCompleter;
import org.apache.groovy.groovysh.completion.StricterArgumentCompleter;
import org.apache.groovy.groovysh.util.PackageHelper;
import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.GStringImpl;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.powerassert.AssertionRenderer;
import org.codehaus.groovy.runtime.powerassert.ValueRecorder;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;

public class ImportCommand
extends CommandSupport {
    private static final Pattern IMPORTED_ITEM_PATTERN;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private static /* synthetic */ SoftReference $callSiteArray;

    public ImportCommand(Groovysh shell) {
        CallSite[] callSiteArray = ImportCommand.$getCallSiteArray();
        super(shell, "import", ":i");
    }

    @Override
    public Completer getCompleter() {
        CallSite[] callSiteArray = ImportCommand.$getCallSiteArray();
        Completer impCompleter = (Completer)ScriptBytecodeAdapter.castToType((Object)callSiteArray[0].callConstructor(StringsCompleter.class, callSiteArray[1].call(callSiteArray[2].callGroovyObjectGetProperty((Object)this), (Object)" "), callSiteArray[3].call(callSiteArray[4].callGroovyObjectGetProperty((Object)this), (Object)" ")), Completer.class);
        Completer asCompleter = (Completer)ScriptBytecodeAdapter.castToType((Object)callSiteArray[5].callConstructor(StringsCompleter.class, (Object)"as "), Completer.class);
        Completer nullCompleter = (Completer)ScriptBytecodeAdapter.castToType((Object)callSiteArray[6].callConstructor(NullCompleter.class), Completer.class);
        PackageHelper packageHelper = (PackageHelper)ScriptBytecodeAdapter.castToType((Object)callSiteArray[7].callGroovyObjectGetProperty(callSiteArray[8].callGroovyObjectGetProperty((Object)this)), PackageHelper.class);
        Interpreter interp = (Interpreter)ScriptBytecodeAdapter.castToType((Object)callSiteArray[9].callGroovyObjectGetProperty(callSiteArray[10].callGroovyObjectGetProperty((Object)this)), Interpreter.class);
        Completer nonStaticCompleter = (Completer)ScriptBytecodeAdapter.castToType((Object)callSiteArray[11].callConstructor(StricterArgumentCompleter.class, (Object)ScriptBytecodeAdapter.createList((Object[])new Object[]{impCompleter, callSiteArray[12].callConstructor(ImportCompleter.class, (Object)packageHelper, (Object)interp, (Object)false), asCompleter, nullCompleter})), Completer.class);
        Completer staticCompleter = (Completer)ScriptBytecodeAdapter.castToType((Object)callSiteArray[13].callConstructor(StricterArgumentCompleter.class, (Object)ScriptBytecodeAdapter.createList((Object[])new Object[]{impCompleter, callSiteArray[14].callConstructor(StringsCompleter.class, (Object)"static "), callSiteArray[15].callConstructor(ImportCompleter.class, (Object)packageHelper, (Object)interp, (Object)true), asCompleter, nullCompleter})), Completer.class);
        List argCompleters = ScriptBytecodeAdapter.createList((Object[])new Object[]{nonStaticCompleter, staticCompleter});
        return (Completer)ScriptBytecodeAdapter.castToType((Object)callSiteArray[16].callConstructor(AggregateCompleter.class, (Object)argCompleters), Completer.class);
    }

    @Override
    public Object execute(List<String> args) {
        Object object;
        Object object2;
        CallSite[] callSiteArray = ImportCommand.$getCallSiteArray();
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
        if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[17].call(args))) {
            callSiteArray[18].callCurrent((GroovyObject)this, (Object)"Command 'import' requires one or more arguments");
        }
        Object importSpec = callSiteArray[19].call(args, (Object)" ");
        if (!DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[20].call(importSpec, (Object)IMPORTED_ITEM_PATTERN))) {
            GStringImpl msg = new GStringImpl(new Object[]{importSpec}, new String[]{"Invalid import definition: '", "'"});
            callSiteArray[21].call(callSiteArray[22].callGroovyObjectGetProperty((Object)this), (Object)msg);
            callSiteArray[23].callCurrent((GroovyObject)this, (Object)msg);
        }
        importSpec = object2 = callSiteArray[24].call(importSpec, (Object)";", (Object)"");
        List buff = ScriptBytecodeAdapter.createList((Object[])new Object[]{callSiteArray[25].call((Object)"import ", callSiteArray[26].call(args, (Object)" "))});
        callSiteArray[27].call((Object)buff, (Object)"def dummp = false");
        Object type = null;
        type = object = callSiteArray[28].call(callSiteArray[29].callGroovyObjectGetProperty((Object)this), callSiteArray[30].call((Object)buff, callSiteArray[31].callGroovyObjectGetProperty((Object)this)));
        if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[32].call(callSiteArray[33].callGroovyObjectGetProperty((Object)this), importSpec))) {
            callSiteArray[34].call(callSiteArray[35].callGroovyObjectGetProperty((Object)this), (Object)"Removed duplicate import from list");
        }
        callSiteArray[36].call(callSiteArray[37].callGroovyObjectGetProperty((Object)this), (Object)new GStringImpl(new Object[]{importSpec}, new String[]{"Adding import: ", ""}));
        callSiteArray[38].call(callSiteArray[39].callGroovyObjectGetProperty((Object)this), importSpec);
        Object object3 = callSiteArray[40].call(callSiteArray[41].callGroovyObjectGetProperty((Object)this), (Object)", ");
        if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[42].callGetPropertySafe(type))) {
            callSiteArray[43].call(callSiteArray[44].callGroovyObjectGetProperty((Object)this), callSiteArray[45].callGetProperty(type));
        }
        try {
            return object3;
        }
        catch (CompilationFailedException e) {
            GStringImpl msg = new GStringImpl(new Object[]{importSpec, callSiteArray[46].callGetProperty((Object)e)}, new String[]{"Invalid import definition: '", "'; reason: ", ""});
            callSiteArray[47].call(callSiteArray[48].callGroovyObjectGetProperty((Object)this), (Object)msg, (Object)e);
            Object object4 = callSiteArray[49].callCurrent((GroovyObject)this, (Object)msg);
            if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[50].callGetPropertySafe(type))) {
                callSiteArray[51].call(callSiteArray[52].callGroovyObjectGetProperty((Object)this), callSiteArray[53].callGetProperty(type));
            }
            try {
                return object4;
            }
            catch (Throwable throwable) {
                if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[54].callGetPropertySafe(type))) {
                    callSiteArray[55].call(callSiteArray[56].callGroovyObjectGetProperty((Object)this), callSiteArray[57].callGetProperty(type));
                }
                throw throwable;
            }
        }
    }

    @Override
    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != ImportCommand.class) {
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
        Object object = ScriptBytecodeAdapter.bitwiseNegate((Object)"[a-zA-Z0-9_. *]+;?$");
        IMPORTED_ITEM_PATTERN = (Pattern)ScriptBytecodeAdapter.castToType((Object)object, Pattern.class);
    }

    public /* synthetic */ MetaClass super$2$$getStaticMetaClass() {
        return super.$getStaticMetaClass();
    }

    public /* synthetic */ Completer super$2$getCompleter() {
        return super.getCompleter();
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
        stringArray[0] = "<$constructor$>";
        stringArray[1] = "plus";
        stringArray[2] = "name";
        stringArray[3] = "plus";
        stringArray[4] = "shortcut";
        stringArray[5] = "<$constructor$>";
        stringArray[6] = "<$constructor$>";
        stringArray[7] = "packageHelper";
        stringArray[8] = "shell";
        stringArray[9] = "interp";
        stringArray[10] = "shell";
        stringArray[11] = "<$constructor$>";
        stringArray[12] = "<$constructor$>";
        stringArray[13] = "<$constructor$>";
        stringArray[14] = "<$constructor$>";
        stringArray[15] = "<$constructor$>";
        stringArray[16] = "<$constructor$>";
        stringArray[17] = "isEmpty";
        stringArray[18] = "fail";
        stringArray[19] = "join";
        stringArray[20] = "matches";
        stringArray[21] = "debug";
        stringArray[22] = "log";
        stringArray[23] = "fail";
        stringArray[24] = "replace";
        stringArray[25] = "plus";
        stringArray[26] = "join";
        stringArray[27] = "leftShift";
        stringArray[28] = "parseClass";
        stringArray[29] = "classLoader";
        stringArray[30] = "join";
        stringArray[31] = "NEWLINE";
        stringArray[32] = "remove";
        stringArray[33] = "imports";
        stringArray[34] = "debug";
        stringArray[35] = "log";
        stringArray[36] = "debug";
        stringArray[37] = "log";
        stringArray[38] = "add";
        stringArray[39] = "imports";
        stringArray[40] = "join";
        stringArray[41] = "imports";
        stringArray[42] = "name";
        stringArray[43] = "removeClassCacheEntry";
        stringArray[44] = "classLoader";
        stringArray[45] = "name";
        stringArray[46] = "message";
        stringArray[47] = "debug";
        stringArray[48] = "log";
        stringArray[49] = "fail";
        stringArray[50] = "name";
        stringArray[51] = "removeClassCacheEntry";
        stringArray[52] = "classLoader";
        stringArray[53] = "name";
        stringArray[54] = "name";
        stringArray[55] = "removeClassCacheEntry";
        stringArray[56] = "classLoader";
        stringArray[57] = "name";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[58];
        ImportCommand.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(ImportCommand.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = ImportCommand.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }
}

