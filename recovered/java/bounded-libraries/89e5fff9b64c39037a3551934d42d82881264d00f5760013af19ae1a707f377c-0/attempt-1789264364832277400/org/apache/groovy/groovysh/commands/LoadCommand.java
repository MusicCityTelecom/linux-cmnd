/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.Closure
 *  groovy.lang.GroovyObject
 *  groovy.lang.MetaClass
 *  groovy.transform.Generated
 *  jline.console.completer.Completer
 *  jline.internal.Configuration
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
 *  org.codehaus.groovy.runtime.typehandling.ShortTypeHandling
 */
package org.apache.groovy.groovysh.commands;

import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import groovy.transform.Generated;
import java.io.File;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import jline.console.completer.Completer;
import jline.internal.Configuration;
import org.apache.groovy.groovysh.CommandSupport;
import org.apache.groovy.groovysh.Groovysh;
import org.apache.groovy.groovysh.completion.FileNameCompleter;
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
import org.codehaus.groovy.runtime.typehandling.ShortTypeHandling;

public class LoadCommand
extends CommandSupport {
    public static final String COMMAND_NAME = ":load";
    private static final boolean isWin;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private static /* synthetic */ SoftReference $callSiteArray;

    public LoadCommand(Groovysh shell) {
        CallSite[] callSiteArray = LoadCommand.$getCallSiteArray();
        super(shell, ShortTypeHandling.castToString((Object)callSiteArray[0].callGetProperty(LoadCommand.class)), ":l");
        callSiteArray[1].callCurrent((GroovyObject)this, (Object)".", (Object)":.");
    }

    @Override
    protected List<Completer> createCompleters() {
        CallSite[] callSiteArray = LoadCommand.$getCallSiteArray();
        return ScriptBytecodeAdapter.createList((Object[])new Object[]{callSiteArray[2].callConstructor(FileNameCompleter.class, (Object)true, (Object)true, (Object)true)});
    }

    @Override
    public Object execute(List<String> args) {
        CallSite[] callSiteArray = LoadCommand.$getCallSiteArray();
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
        if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[3].call(args), (Object)0)) {
            callSiteArray[4].callCurrent((GroovyObject)this, (Object)new GStringImpl(new Object[]{COMMAND_NAME}, new String[]{"Command '", "' requires at least one argument"}));
        }
        Object source = null;
        Iterator iterator = (Iterator)ScriptBytecodeAdapter.castToType((Object)callSiteArray[5].call(args), Iterator.class);
        if (iterator != null) {
            while (iterator.hasNext()) {
                source = iterator.next();
                URL url = null;
                if (isWin) {
                    Object object;
                    source = object = callSiteArray[6].call(source, (Object)"\\\\ ", (Object)" ");
                }
                callSiteArray[7].call(callSiteArray[8].callGroovyObjectGetProperty((Object)this), (Object)new GStringImpl(new Object[]{source}, new String[]{"Attempting to load: \"", "\""}));
                try {
                    Object object = callSiteArray[9].callConstructor(URL.class, (Object)new GStringImpl(new Object[]{source}, new String[]{"", ""}));
                    url = (URL)ScriptBytecodeAdapter.castToType((Object)object, URL.class);
                }
                catch (MalformedURLException e) {
                    Object file = callSiteArray[10].callConstructor(File.class, (Object)new GStringImpl(new Object[]{source}, new String[]{"", ""}));
                    if (!DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[11].call(file))) {
                        callSiteArray[12].callCurrent((GroovyObject)this, (Object)new GStringImpl(new Object[]{file}, new String[]{"File not found: \"", "\""}));
                    }
                    Object object = callSiteArray[13].call(callSiteArray[14].call(file));
                    url = (URL)ScriptBytecodeAdapter.castToType((Object)object, URL.class);
                }
                callSiteArray[15].callCurrent((GroovyObject)this, (Object)url);
            }
        }
        return null;
    }

    public void load(URL url) {
        CallSite[] callSiteArray = LoadCommand.$getCallSiteArray();
        ValueRecorder valueRecorder = new ValueRecorder();
        try {
            URL uRL = url;
            valueRecorder.record((Object)uRL, 8);
            boolean bl = ScriptBytecodeAdapter.compareNotEqual((Object)uRL, null);
            valueRecorder.record((Object)bl, 12);
            if (bl) {
                valueRecorder.clear();
            } else {
                ScriptBytecodeAdapter.assertFailed((Object)AssertionRenderer.render((String)"assert url != null", (ValueRecorder)valueRecorder), null);
            }
        }
        catch (Throwable throwable) {
            valueRecorder.clear();
            throw throwable;
        }
        if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[16].callGetProperty(callSiteArray[17].callGroovyObjectGetProperty((Object)this)))) {
            callSiteArray[18].call(callSiteArray[19].callGetProperty(callSiteArray[20].callGroovyObjectGetProperty((Object)this)), (Object)new GStringImpl(new Object[]{url}, new String[]{"Loading: ", ""}));
        }
        public final class _load_closure1
        extends Closure
        implements GeneratedClosure {
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _load_closure1(Object _outerInstance, Object _thisObject) {
                CallSite[] callSiteArray = _load_closure1.$getCallSiteArray();
                super(_outerInstance, _thisObject);
            }

            public Object doCall(String it, int lineNumber) {
                CallSite[] callSiteArray = _load_closure1.$getCallSiteArray();
                if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass() ? lineNumber == 1 && DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[0].call((Object)it, (Object)"#!")) : lineNumber == 1 && DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[1].call((Object)it, (Object)"#!"))) {
                    return null;
                }
                return (String)ScriptBytecodeAdapter.asType((Object)callSiteArray[2].call(callSiteArray[3].callGroovyObjectGetProperty((Object)this), (Object)it), String.class);
            }

            @Generated
            public Object call(String it, int lineNumber) {
                CallSite[] callSiteArray = _load_closure1.$getCallSiteArray();
                if (__$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                    return callSiteArray[4].callCurrent((GroovyObject)this, (Object)it, (Object)lineNumber);
                }
                return this.doCall(it, lineNumber);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (((Object)((Object)this)).getClass() != _load_closure1.class) {
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
                stringArray[0] = "startsWith";
                stringArray[1] = "startsWith";
                stringArray[2] = "leftShift";
                stringArray[3] = "shell";
                stringArray[4] = "doCall";
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[5];
                _load_closure1.$createCallSiteArray_1(stringArray);
                return new CallSiteArray(_load_closure1.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _load_closure1.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        callSiteArray[21].call((Object)url, (Object)new _load_closure1(this, this));
    }

    @Override
    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != LoadCommand.class) {
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
        Object object = LoadCommand.$getCallSiteArray()[22].call(Configuration.class);
        isWin = DefaultTypeTransformation.booleanUnbox((Object)object);
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
        stringArray[3] = "size";
        stringArray[4] = "fail";
        stringArray[5] = "iterator";
        stringArray[6] = "replaceAll";
        stringArray[7] = "debug";
        stringArray[8] = "log";
        stringArray[9] = "<$constructor$>";
        stringArray[10] = "<$constructor$>";
        stringArray[11] = "exists";
        stringArray[12] = "fail";
        stringArray[13] = "toURL";
        stringArray[14] = "toURI";
        stringArray[15] = "load";
        stringArray[16] = "verbose";
        stringArray[17] = "io";
        stringArray[18] = "println";
        stringArray[19] = "out";
        stringArray[20] = "io";
        stringArray[21] = "eachLine";
        stringArray[22] = "isWindows";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[23];
        LoadCommand.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(LoadCommand.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = LoadCommand.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }
}

