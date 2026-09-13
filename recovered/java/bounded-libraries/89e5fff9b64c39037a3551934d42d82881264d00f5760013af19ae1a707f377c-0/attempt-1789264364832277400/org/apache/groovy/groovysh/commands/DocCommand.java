/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.Closure
 *  groovy.lang.GroovyObject
 *  groovy.lang.GroovySystem
 *  groovy.lang.MetaClass
 *  groovy.transform.Generated
 *  jline.console.completer.Completer
 *  org.codehaus.groovy.reflection.ClassInfo
 *  org.codehaus.groovy.runtime.BytecodeInterface8
 *  org.codehaus.groovy.runtime.GStringImpl
 *  org.codehaus.groovy.runtime.GeneratedClosure
 *  org.codehaus.groovy.runtime.ScriptBytecodeAdapter
 *  org.codehaus.groovy.runtime.callsite.CallSite
 *  org.codehaus.groovy.runtime.callsite.CallSiteArray
 *  org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation
 *  org.codehaus.groovy.runtime.typehandling.ShortTypeHandling
 */
package org.apache.groovy.groovysh.commands;

import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import groovy.lang.GroovySystem;
import groovy.lang.MetaClass;
import groovy.transform.Generated;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import jline.console.completer.Completer;
import org.apache.groovy.groovysh.CommandSupport;
import org.apache.groovy.groovysh.Groovysh;
import org.apache.groovy.groovysh.commands.ImportCompleter;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.BytecodeInterface8;
import org.codehaus.groovy.runtime.GStringImpl;
import org.codehaus.groovy.runtime.GeneratedClosure;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;
import org.codehaus.groovy.runtime.typehandling.ShortTypeHandling;

public class DocCommand
extends CommandSupport {
    public static final String COMMAND_NAME = ":doc";
    private static final String ENV_BROWSER = "BROWSER";
    private static final String ENV_BROWSER_GROOVYSH = "GROOVYSH_BROWSER";
    private static final int TIMEOUT_CONN = 5000;
    private static final int TIMEOUT_READ = 5000;
    private static boolean hasAWTDesktopPlatformSupport;
    private static Object desktop;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private static /* synthetic */ SoftReference $callSiteArray;

    public DocCommand(Groovysh shell) {
        CallSite[] callSiteArray = DocCommand.$getCallSiteArray();
        super(shell, ShortTypeHandling.castToString((Object)callSiteArray[0].callGetProperty(DocCommand.class)), ":D");
    }

    static {
        try {
            boolean bl;
            Object object;
            Class<?> desktopClass = Class.forName("java.awt.Desktop");
            desktop = object = DefaultTypeTransformation.booleanUnbox((Object)DocCommand.$getCallSiteArray()[1].callGetProperty(desktopClass)) ? DocCommand.$getCallSiteArray()[2].callGetProperty(desktopClass) : null;
            public final class __clinit__closure1
            extends Closure
            implements GeneratedClosure {
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;
                private static /* synthetic */ SoftReference $callSiteArray;

                public __clinit__closure1(Object _outerInstance, Object _thisObject) {
                    CallSite[] callSiteArray = __clinit__closure1.$getCallSiteArray();
                    super(_outerInstance, _thisObject);
                }

                public Object doCall(Object it) {
                    CallSite[] callSiteArray = __clinit__closure1.$getCallSiteArray();
                    return ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[0].callGetProperty(it), (Object)"Action");
                }

                @Generated
                public Object doCall() {
                    CallSite[] callSiteArray = __clinit__closure1.$getCallSiteArray();
                    return this.doCall(null);
                }

                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                    if (((Object)((Object)this)).getClass() != __clinit__closure1.class) {
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
                    stringArray[0] = "simpleName";
                    return new CallSiteArray(__clinit__closure1.class, stringArray);
                }

                private static /* synthetic */ CallSite[] $getCallSiteArray() {
                    CallSiteArray callSiteArray;
                    if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                        callSiteArray = __clinit__closure1.$createCallSiteArray();
                        $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                    }
                    return callSiteArray.array;
                }
            }
            hasAWTDesktopPlatformSupport = bl = ScriptBytecodeAdapter.compareNotEqual((Object)desktop, null) && DefaultTypeTransformation.booleanUnbox((Object)DocCommand.$getCallSiteArray()[3].call(desktop, DocCommand.$getCallSiteArray()[4].callGetProperty(DocCommand.$getCallSiteArray()[5].call(DocCommand.$getCallSiteArray()[6].callGetProperty(desktopClass), (Object)new __clinit__closure1(DocCommand.class, DocCommand.class)))));
        }
        catch (Exception e) {
            boolean bl;
            hasAWTDesktopPlatformSupport = bl = false;
            Object var5_5 = null;
            desktop = var5_5;
        }
    }

    @Override
    protected List<Completer> createCompleters() {
        CallSite[] callSiteArray = DocCommand.$getCallSiteArray();
        return ScriptBytecodeAdapter.createList((Object[])new Object[]{callSiteArray[7].callConstructor(ImportCompleter.class, callSiteArray[8].callGroovyObjectGetProperty(callSiteArray[9].callGroovyObjectGetProperty((Object)this)), callSiteArray[10].callGroovyObjectGetProperty(callSiteArray[11].callGroovyObjectGetProperty((Object)this)), (Object)false), null});
    }

    @Override
    public Object execute(List<String> args) {
        CallSite[] callSiteArray = DocCommand.$getCallSiteArray();
        if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[12].callSafe(args), (Object)1)) {
            callSiteArray[13].callCurrent((GroovyObject)this, callSiteArray[14].call(args, (Object)0));
            return null;
        }
        return callSiteArray[15].callCurrent((GroovyObject)this, callSiteArray[16].call(callSiteArray[17].callGroovyObjectGetProperty((Object)this), (Object)"error.unexpected_args", DefaultTypeTransformation.booleanUnbox(args) ? callSiteArray[18].call(args, (Object)" ") : "no arguments"));
    }

    public void doc(String className) {
        CallSite[] callSiteArray = DocCommand.$getCallSiteArray();
        Object normalizedClassName = null;
        if (__$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            Object object;
            normalizedClassName = object = callSiteArray[19].callCurrent((GroovyObject)this, (Object)className);
        } else {
            String string = this.normalizeClassName(className);
            normalizedClassName = string;
        }
        Object urls = callSiteArray[20].callCurrent((GroovyObject)this, normalizedClassName);
        if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[21].callGetProperty(urls))) {
            callSiteArray[22].callCurrent((GroovyObject)this, (Object)new GStringImpl(new Object[]{normalizedClassName}, new String[]{"Documentation for \"", "\" could not be found."}));
        }
        public final class _doc_closure2
        extends Closure
        implements GeneratedClosure {
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _doc_closure2(Object _outerInstance, Object _thisObject) {
                CallSite[] callSiteArray = _doc_closure2.$getCallSiteArray();
                super(_outerInstance, _thisObject);
            }

            public Object doCall(Object url) {
                CallSite[] callSiteArray = _doc_closure2.$getCallSiteArray();
                return callSiteArray[0].call(callSiteArray[1].callGetProperty(callSiteArray[2].callGroovyObjectGetProperty((Object)this)), url);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (((Object)((Object)this)).getClass() != _doc_closure2.class) {
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
                stringArray[0] = "println";
                stringArray[1] = "out";
                stringArray[2] = "io";
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[3];
                _doc_closure2.$createCallSiteArray_1(stringArray);
                return new CallSiteArray(_doc_closure2.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _doc_closure2.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        callSiteArray[23].call(urls, (Object)new _doc_closure2(this, this));
        callSiteArray[24].callCurrent((GroovyObject)this, urls);
    }

    protected String normalizeClassName(String className) {
        CallSite[] callSiteArray = DocCommand.$getCallSiteArray();
        return ShortTypeHandling.castToString((Object)callSiteArray[25].call(callSiteArray[26].call((Object)className, (Object)"\"", (Object)""), (Object)"'", (Object)""));
    }

    protected void browse(List urls) {
        CallSite[] callSiteArray = DocCommand.$getCallSiteArray();
        Object browser = callSiteArray[27].callGroovyObjectGetProperty((Object)this);
        if (DefaultTypeTransformation.booleanUnbox((Object)browser)) {
            callSiteArray[28].callCurrent((GroovyObject)this, browser, (Object)urls);
        } else if (hasAWTDesktopPlatformSupport) {
            callSiteArray[29].callCurrent((GroovyObject)this, (Object)urls);
        } else {
            callSiteArray[30].callCurrent((GroovyObject)this, callSiteArray[31].call(callSiteArray[32].call((Object)"Browser could not be opened due to missing platform support for \"java.awt.Desktop\". Please set ", (Object)new GStringImpl(new Object[]{ENV_BROWSER_GROOVYSH, ENV_BROWSER}, new String[]{"a ", " or ", " environment variable referring to the browser binary to "})), (Object)"solve this issue."));
        }
    }

    protected String getBrowserEnvironmentVariable() {
        CallSite[] callSiteArray = DocCommand.$getCallSiteArray();
        Object object = callSiteArray[33].call(System.class, (Object)ENV_BROWSER_GROOVYSH);
        return ShortTypeHandling.castToString((Object)(DefaultTypeTransformation.booleanUnbox((Object)object) ? object : callSiteArray[34].call(System.class, (Object)ENV_BROWSER)));
    }

    protected void browseWithAWT(List urls) {
        CallSite[] callSiteArray = DocCommand.$getCallSiteArray();
        try {
            public final class _browseWithAWT_closure3
            extends Closure
            implements GeneratedClosure {
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;
                private static /* synthetic */ SoftReference $callSiteArray;

                public _browseWithAWT_closure3(Object _outerInstance, Object _thisObject) {
                    CallSite[] callSiteArray = _browseWithAWT_closure3.$getCallSiteArray();
                    super(_outerInstance, _thisObject);
                }

                public Object doCall(Object url) {
                    CallSite[] callSiteArray = _browseWithAWT_closure3.$getCallSiteArray();
                    return callSiteArray[0].call(callSiteArray[1].callGroovyObjectGetProperty((Object)this), callSiteArray[2].call(url));
                }

                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                    if (((Object)((Object)this)).getClass() != _browseWithAWT_closure3.class) {
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
                    stringArray[0] = "browse";
                    stringArray[1] = "desktop";
                    stringArray[2] = "toURI";
                }

                private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                    String[] stringArray = new String[3];
                    _browseWithAWT_closure3.$createCallSiteArray_1(stringArray);
                    return new CallSiteArray(_browseWithAWT_closure3.class, stringArray);
                }

                private static /* synthetic */ CallSite[] $getCallSiteArray() {
                    CallSiteArray callSiteArray;
                    if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                        callSiteArray = _browseWithAWT_closure3.$createCallSiteArray();
                        $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                    }
                    return callSiteArray.array;
                }
            }
            callSiteArray[35].call((Object)urls, (Object)new _browseWithAWT_closure3(this, this));
        }
        catch (Exception e) {
            callSiteArray[36].callCurrent((GroovyObject)this, callSiteArray[37].call((Object)new GStringImpl(new Object[]{e}, new String[]{"Browser could not be opened, an unexpected error occured (", "). You can add a "}), (Object)new GStringImpl(new Object[]{ENV_BROWSER_GROOVYSH, ENV_BROWSER}, new String[]{"", " or ", " environment variable to explicitly specify a browser binary."})));
        }
    }

    protected void browseWithNativeBrowser(String browser, List urls) {
        CallSite[] callSiteArray = DocCommand.$getCallSiteArray();
        try {
            callSiteArray[38].call((Object)new GStringImpl(new Object[]{browser, callSiteArray[39].call((Object)urls, (Object)" ")}, new String[]{"", " ", ""}));
        }
        catch (Exception e) {
            callSiteArray[40].callCurrent((GroovyObject)this, callSiteArray[41].call((Object)new GStringImpl(new Object[]{e, ENV_BROWSER_GROOVYSH, ENV_BROWSER}, new String[]{"Browser could not be opened (", "). Please check the ", " or ", " "}), (Object)"environment variable."));
        }
    }

    protected List urlsFor(String className) {
        CallSite[] callSiteArray = DocCommand.$getCallSiteArray();
        String groovyVersion = ShortTypeHandling.castToString((Object)callSiteArray[42].callGetProperty(GroovySystem.class));
        Object path = callSiteArray[43].call(callSiteArray[44].call((Object)className, (Object)".", (Object)"/"), (Object)".html");
        List urls = ScriptBytecodeAdapter.createList((Object[])new Object[0]);
        if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[45].call((Object)className, (Object)"^(groovy|org\\.codehaus\\.groovy|)\\..+"))) {
            Object url = callSiteArray[46].callConstructor(URL.class, (Object)new GStringImpl(new Object[]{groovyVersion, path}, new String[]{"http://docs.groovy-lang.org/", "/html/gapi/", ""}));
            if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[47].callCurrent((GroovyObject)this, url))) {
                callSiteArray[48].call((Object)urls, url);
            }
        } else {
            Object object;
            Object object2;
            Object url = null;
            url = __$stMC || BytecodeInterface8.disabledStandardMetaClass() ? (object2 = callSiteArray[49].callConstructor(URL.class, (Object)new GStringImpl(new Object[]{DocCommand.simpleVersion(), path}, new String[]{"http://docs.oracle.com/javase/", "/docs/api/", ""}))) : (object = callSiteArray[50].callConstructor(URL.class, (Object)new GStringImpl(new Object[]{DocCommand.simpleVersion(), path}, new String[]{"http://docs.oracle.com/javase/", "/docs/api/", ""})));
            if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[51].callCurrent((GroovyObject)this, url))) {
                Object object3;
                callSiteArray[52].call((Object)urls, url);
                url = object3 = callSiteArray[53].callConstructor(URL.class, (Object)new GStringImpl(new Object[]{groovyVersion, path}, new String[]{"http://docs.groovy-lang.org/", "/html/groovy-jdk/", ""}));
                if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[54].callCurrent((GroovyObject)this, url))) {
                    callSiteArray[55].call((Object)urls, url);
                }
            }
        }
        return (List)ScriptBytecodeAdapter.castToType((Object)urls, List.class);
    }

    private static Object simpleVersion() {
        CallSite[] callSiteArray = DocCommand.$getCallSiteArray();
        String javaVersion = ShortTypeHandling.castToString((Object)callSiteArray[56].call(System.class, (Object)"java.version"));
        if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[57].call((Object)javaVersion, (Object)"1."))) {
            return callSiteArray[58].call(callSiteArray[59].call((Object)javaVersion, (Object)"\\."), (Object)1);
        }
        return callSiteArray[60].call(callSiteArray[61].call(callSiteArray[62].call((Object)javaVersion, (Object)"-.*", (Object)""), (Object)"\\."), (Object)0);
    }

    protected boolean sendHEADRequest(URL url) {
        CallSite[] callSiteArray = DocCommand.$getCallSiteArray();
        HttpURLConnection conn = (HttpURLConnection)ScriptBytecodeAdapter.asType((Object)callSiteArray[63].call((Object)url), HttpURLConnection.class);
        String string = "HEAD";
        ScriptBytecodeAdapter.setProperty((Object)string, null, (Object)conn, (String)"requestMethod");
        int n = TIMEOUT_CONN;
        ScriptBytecodeAdapter.setProperty((Object)n, null, (Object)conn, (String)"connectTimeout");
        int n2 = TIMEOUT_READ;
        ScriptBytecodeAdapter.setProperty((Object)n2, null, (Object)conn, (String)"readTimeout");
        boolean bl = true;
        ScriptBytecodeAdapter.setProperty((Object)bl, null, (Object)conn, (String)"instanceFollowRedirects");
        boolean bl2 = ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[64].callGetProperty((Object)conn), (Object)200);
        try {
            return bl2;
        }
        catch (IOException e) {
            boolean bl3 = DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[65].callCurrent((GroovyObject)this, (Object)new GStringImpl(new Object[]{url, e}, new String[]{"Sending a HEAD request to ", " failed (", "). Please check your network settings."})));
            return bl3;
        }
    }

    @Override
    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != DocCommand.class) {
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
        stringArray[1] = "desktopSupported";
        stringArray[2] = "desktop";
        stringArray[3] = "isSupported";
        stringArray[4] = "BROWSE";
        stringArray[5] = "find";
        stringArray[6] = "declaredClasses";
        stringArray[7] = "<$constructor$>";
        stringArray[8] = "packageHelper";
        stringArray[9] = "shell";
        stringArray[10] = "interp";
        stringArray[11] = "shell";
        stringArray[12] = "size";
        stringArray[13] = "doc";
        stringArray[14] = "getAt";
        stringArray[15] = "fail";
        stringArray[16] = "format";
        stringArray[17] = "messages";
        stringArray[18] = "join";
        stringArray[19] = "normalizeClassName";
        stringArray[20] = "urlsFor";
        stringArray[21] = "empty";
        stringArray[22] = "fail";
        stringArray[23] = "each";
        stringArray[24] = "browse";
        stringArray[25] = "replace";
        stringArray[26] = "replace";
        stringArray[27] = "browserEnvironmentVariable";
        stringArray[28] = "browseWithNativeBrowser";
        stringArray[29] = "browseWithAWT";
        stringArray[30] = "fail";
        stringArray[31] = "plus";
        stringArray[32] = "plus";
        stringArray[33] = "getenv";
        stringArray[34] = "getenv";
        stringArray[35] = "each";
        stringArray[36] = "fail";
        stringArray[37] = "plus";
        stringArray[38] = "execute";
        stringArray[39] = "join";
        stringArray[40] = "fail";
        stringArray[41] = "plus";
        stringArray[42] = "version";
        stringArray[43] = "plus";
        stringArray[44] = "replace";
        stringArray[45] = "matches";
        stringArray[46] = "<$constructor$>";
        stringArray[47] = "sendHEADRequest";
        stringArray[48] = "leftShift";
        stringArray[49] = "<$constructor$>";
        stringArray[50] = "<$constructor$>";
        stringArray[51] = "sendHEADRequest";
        stringArray[52] = "leftShift";
        stringArray[53] = "<$constructor$>";
        stringArray[54] = "sendHEADRequest";
        stringArray[55] = "leftShift";
        stringArray[56] = "getProperty";
        stringArray[57] = "startsWith";
        stringArray[58] = "getAt";
        stringArray[59] = "split";
        stringArray[60] = "getAt";
        stringArray[61] = "split";
        stringArray[62] = "replaceAll";
        stringArray[63] = "openConnection";
        stringArray[64] = "responseCode";
        stringArray[65] = "fail";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[66];
        DocCommand.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(DocCommand.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = DocCommand.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }
}

