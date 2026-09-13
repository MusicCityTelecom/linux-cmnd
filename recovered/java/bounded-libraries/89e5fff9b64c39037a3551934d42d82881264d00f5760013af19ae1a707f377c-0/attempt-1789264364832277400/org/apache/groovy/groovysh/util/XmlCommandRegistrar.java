/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.Closure
 *  groovy.lang.GroovyObject
 *  groovy.lang.MetaClass
 *  groovy.transform.Generated
 *  groovy.transform.Internal
 *  groovy.util.Node
 *  groovy.xml.XmlParser
 *  org.codehaus.groovy.reflection.ClassInfo
 *  org.codehaus.groovy.runtime.GStringImpl
 *  org.codehaus.groovy.runtime.GeneratedClosure
 *  org.codehaus.groovy.runtime.ScriptBytecodeAdapter
 *  org.codehaus.groovy.runtime.callsite.CallSite
 *  org.codehaus.groovy.runtime.callsite.CallSiteArray
 *  org.codehaus.groovy.runtime.powerassert.AssertionRenderer
 *  org.codehaus.groovy.runtime.powerassert.ValueRecorder
 *  org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation
 *  org.codehaus.groovy.runtime.typehandling.ShortTypeHandling
 *  org.codehaus.groovy.tools.shell.util.Logger
 */
package org.apache.groovy.groovysh.util;

import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import groovy.transform.Generated;
import groovy.transform.Internal;
import groovy.util.Node;
import groovy.xml.XmlParser;
import java.beans.Transient;
import java.io.Reader;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import java.net.URL;
import org.apache.groovy.groovysh.Command;
import org.apache.groovy.groovysh.Shell;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.GStringImpl;
import org.codehaus.groovy.runtime.GeneratedClosure;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.powerassert.AssertionRenderer;
import org.codehaus.groovy.runtime.powerassert.ValueRecorder;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;
import org.codehaus.groovy.runtime.typehandling.ShortTypeHandling;
import org.codehaus.groovy.tools.shell.util.Logger;

public class XmlCommandRegistrar
implements GroovyObject {
    private final Logger log;
    private final Shell shell;
    private final ClassLoader classLoader;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private transient /* synthetic */ MetaClass metaClass;
    private static /* synthetic */ SoftReference $callSiteArray;

    public XmlCommandRegistrar(Shell shell, ClassLoader classLoader) {
        ClassLoader classLoader2;
        Shell shell2;
        MetaClass metaClass;
        CallSite[] callSiteArray = XmlCommandRegistrar.$getCallSiteArray();
        Object object = callSiteArray[0].call(Logger.class, callSiteArray[1].callGroovyObjectGetProperty((Object)this));
        this.log = (Logger)ScriptBytecodeAdapter.castToType((Object)object, Logger.class);
        this.metaClass = metaClass = this.$getStaticMetaClass();
        ValueRecorder valueRecorder = new ValueRecorder();
        try {
            Shell shell3 = shell;
            valueRecorder.record((Object)shell3, 8);
            boolean bl = ScriptBytecodeAdapter.compareNotEqual((Object)shell3, null);
            valueRecorder.record((Object)bl, 14);
            if (bl) {
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
            ClassLoader classLoader3 = classLoader;
            valueRecorder2.record((Object)classLoader3, 8);
            boolean bl = ScriptBytecodeAdapter.compareNotEqual((Object)classLoader3, null);
            valueRecorder2.record((Object)bl, 20);
            if (bl) {
                valueRecorder2.clear();
            } else {
                ScriptBytecodeAdapter.assertFailed((Object)AssertionRenderer.render((String)"assert classLoader != null", (ValueRecorder)valueRecorder2), null);
            }
        }
        catch (Throwable throwable) {
            valueRecorder2.clear();
            throw throwable;
        }
        this.shell = shell2 = shell;
        this.classLoader = classLoader2 = classLoader;
    }

    public void register(URL url) {
        CallSite[] callSiteArray = XmlCommandRegistrar.$getCallSiteArray();
        ValueRecorder valueRecorder = new ValueRecorder();
        try {
            URL uRL = url;
            valueRecorder.record((Object)uRL, 8);
            if (DefaultTypeTransformation.booleanUnbox((Object)uRL)) {
                valueRecorder.clear();
            } else {
                ScriptBytecodeAdapter.assertFailed((Object)AssertionRenderer.render((String)"assert url", (ValueRecorder)valueRecorder), null);
            }
        }
        catch (Throwable throwable) {
            valueRecorder.clear();
            throw throwable;
        }
        if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[2].callGetProperty((Object)this.log))) {
            callSiteArray[3].call((Object)this.log, (Object)new GStringImpl(new Object[]{url}, new String[]{"Registering commands from: ", ""}));
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

            public Object doCall(Reader reader) {
                CallSite[] callSiteArray = _register_closure1.$getCallSiteArray();
                Node doc = (Node)ScriptBytecodeAdapter.castToType((Object)callSiteArray[0].call(callSiteArray[1].callConstructor(XmlParser.class), (Object)reader), Node.class);
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

                    public Object doCall(Node element) {
                        CallSite[] callSiteArray = _closure2.$getCallSiteArray();
                        String classname = ShortTypeHandling.castToString((Object)callSiteArray[0].call((Object)element));
                        Class type = ShortTypeHandling.castToClass((Object)callSiteArray[1].call(callSiteArray[2].callGroovyObjectGetProperty((Object)this), (Object)classname));
                        Command command = (Command)ScriptBytecodeAdapter.asType((Object)callSiteArray[3].call((Object)type, callSiteArray[4].callGroovyObjectGetProperty((Object)this)), Command.class);
                        if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[5].callGetProperty(callSiteArray[6].callGroovyObjectGetProperty((Object)this)))) {
                            callSiteArray[7].call(callSiteArray[8].callGroovyObjectGetProperty((Object)this), (Object)new GStringImpl(new Object[]{callSiteArray[9].callGetProperty((Object)command), command}, new String[]{"Created command '", "': ", ""}));
                        }
                        return callSiteArray[10].call(callSiteArray[11].callGroovyObjectGetProperty((Object)this), (Object)command);
                    }

                    @Generated
                    public Object call(Node element) {
                        CallSite[] callSiteArray = _closure2.$getCallSiteArray();
                        return callSiteArray[12].callCurrent((GroovyObject)this, (Object)element);
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
                        stringArray[0] = "text";
                        stringArray[1] = "loadClass";
                        stringArray[2] = "classLoader";
                        stringArray[3] = "newInstance";
                        stringArray[4] = "shell";
                        stringArray[5] = "debugEnabled";
                        stringArray[6] = "log";
                        stringArray[7] = "debug";
                        stringArray[8] = "log";
                        stringArray[9] = "name";
                        stringArray[10] = "leftShift";
                        stringArray[11] = "shell";
                        stringArray[12] = "doCall";
                    }

                    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                        String[] stringArray = new String[13];
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
                return callSiteArray[2].call(callSiteArray[3].call((Object)doc), (Object)new _closure2((Object)this, this.getThisObject()));
            }

            @Generated
            public Object call(Reader reader) {
                CallSite[] callSiteArray = _register_closure1.$getCallSiteArray();
                return callSiteArray[4].callCurrent((GroovyObject)this, (Object)reader);
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
                stringArray[0] = "parse";
                stringArray[1] = "<$constructor$>";
                stringArray[2] = "each";
                stringArray[3] = "children";
                stringArray[4] = "doCall";
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[5];
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
        callSiteArray[4].call((Object)url, (Object)new _register_closure1(this, this));
    }

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != XmlCommandRegistrar.class) {
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

    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
        stringArray[0] = "create";
        stringArray[1] = "class";
        stringArray[2] = "debugEnabled";
        stringArray[3] = "debug";
        stringArray[4] = "withReader";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[5];
        XmlCommandRegistrar.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(XmlCommandRegistrar.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = XmlCommandRegistrar.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }
}

