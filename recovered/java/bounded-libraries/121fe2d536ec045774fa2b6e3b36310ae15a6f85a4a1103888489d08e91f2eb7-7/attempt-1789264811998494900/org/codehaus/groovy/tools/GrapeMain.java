/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.ivy.util.DefaultMessageLogger
 *  org.apache.ivy.util.Message
 */
package org.codehaus.groovy.tools;

import groovy.grape.Grape;
import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import groovy.lang.GroovySystem;
import groovy.lang.MetaClass;
import groovy.lang.Reference;
import groovy.transform.Generated;
import groovy.transform.Internal;
import groovyjarjarpicocli.CommandLine;
import java.beans.Transient;
import java.io.File;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.ivy.util.DefaultMessageLogger;
import org.apache.ivy.util.Message;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.BytecodeInterface8;
import org.codehaus.groovy.runtime.GStringImpl;
import org.codehaus.groovy.runtime.GeneratedClosure;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;
import org.codehaus.groovy.runtime.typehandling.ShortTypeHandling;

@CommandLine.Command(name="grape", description={"Allows for the inspection and management of the local grape cache."}, subcommands={Install.class, Uninstall.class, ListCommand.class, Resolve.class, CommandLine.HelpCommand.class})
public class GrapeMain
implements Runnable,
GroovyObject {
    @CommandLine.Option(paramLabel="<name=value>", names={"-D", "--define"}, description={"define a system property"})
    private final Map<String, String> properties;
    @CommandLine.Option(paramLabel="<url>", names={"-r", "--resolver"}, description={"define a grab resolver (for install)"})
    private final List<String> resolvers;
    @CommandLine.Option(names={"-q", "--quiet"}, description={"Log level 0 - only errors"})
    private boolean quiet;
    @CommandLine.Option(names={"-w", "--warn"}, description={"Log level 1 - errors and warnings"})
    private boolean warn;
    @CommandLine.Option(names={"-i", "--info"}, description={"Log level 2 - info"})
    private boolean info;
    @CommandLine.Option(names={"-V", "--verbose"}, description={"Log level 3 - verbose"})
    private boolean verbose;
    @CommandLine.Option(names={"-d", "--debug"}, description={"Log level 4 - debug"})
    private boolean debug;
    @CommandLine.Unmatched
    private List<String> unmatched;
    private CommandLine parser;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private transient /* synthetic */ MetaClass metaClass;
    private static /* synthetic */ SoftReference $callSiteArray;

    @Generated
    public GrapeMain() {
        MetaClass metaClass;
        CallSite[] callSiteArray = GrapeMain.$getCallSiteArray();
        Object object = callSiteArray[0].callConstructor(LinkedHashMap.class);
        this.properties = (Map)ScriptBytecodeAdapter.castToType(object, Map.class);
        Object object2 = callSiteArray[1].callConstructor(ArrayList.class);
        this.resolvers = (List)ScriptBytecodeAdapter.castToType(object2, List.class);
        Object object3 = callSiteArray[2].callConstructor(ArrayList.class);
        this.unmatched = (List)ScriptBytecodeAdapter.castToType(object3, List.class);
        this.metaClass = metaClass = this.$getStaticMetaClass();
    }

    public static void main(String ... args) {
        CallSite[] callSiteArray = GrapeMain.$getCallSiteArray();
        GrapeMain grape = (GrapeMain)ScriptBytecodeAdapter.castToType(callSiteArray[3].callConstructor(GrapeMain.class), GrapeMain.class);
        Object parser = callSiteArray[4].callConstructor(CommandLine.class, grape);
        callSiteArray[5].call(parser, "helpOptions", callSiteArray[6].callConstructor(HelpOptionsMixin.class));
        public final class _main_closure1
        extends Closure
        implements GeneratedClosure {
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _main_closure1(Object _outerInstance, Object _thisObject) {
                CallSite[] callSiteArray = _main_closure1.$getCallSiteArray();
                super(_outerInstance, _thisObject);
            }

            public Object doCall(Object k, Object v) {
                CallSite[] callSiteArray = _main_closure1.$getCallSiteArray();
                return ScriptBytecodeAdapter.compareNotEqual(k, "help");
            }

            @Generated
            public Object call(Object k, Object v) {
                CallSite[] callSiteArray = _main_closure1.$getCallSiteArray();
                return callSiteArray[0].callCurrent(this, k, v);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (this.getClass() != _main_closure1.class) {
                    return ScriptBytecodeAdapter.initMetaClass(this);
                }
                ClassInfo classInfo = $staticClassInfo;
                if (classInfo == null) {
                    $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
                }
                return classInfo.getMetaClass();
            }

            public /* synthetic */ MethodHandles.Lookup $getLookup() {
                return MethodHandles.lookup();
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[1];
                stringArray[0] = "doCall";
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
                return callSiteArray[0].call(v, "helpOptions", callSiteArray[1].callConstructor(HelpOptionsMixin.class));
            }

            @Generated
            public Object call(Object k, Object v) {
                CallSite[] callSiteArray = _main_closure2.$getCallSiteArray();
                return callSiteArray[2].callCurrent(this, k, v);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (this.getClass() != _main_closure2.class) {
                    return ScriptBytecodeAdapter.initMetaClass(this);
                }
                ClassInfo classInfo = $staticClassInfo;
                if (classInfo == null) {
                    $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
                }
                return classInfo.getMetaClass();
            }

            public /* synthetic */ MethodHandles.Lookup $getLookup() {
                return MethodHandles.lookup();
            }

            private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
                stringArray[0] = "addMixin";
                stringArray[1] = "<$constructor$>";
                stringArray[2] = "doCall";
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[3];
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
        callSiteArray[7].call(callSiteArray[8].call(callSiteArray[9].callGetProperty(parser), new _main_closure1(GrapeMain.class, GrapeMain.class)), new _main_closure2(GrapeMain.class, GrapeMain.class));
        Object object = parser;
        ScriptBytecodeAdapter.setGroovyObjectProperty(object, GrapeMain.class, grape, "parser");
        callSiteArray[10].call(parser, callSiteArray[11].callConstructor(CommandLine.RunLast.class), args);
    }

    @Override
    public void run() {
        CallSite[] callSiteArray = GrapeMain.$getCallSiteArray();
        if (DefaultTypeTransformation.booleanUnbox(this.unmatched)) {
            callSiteArray[12].call(callSiteArray[13].callGetProperty(System.class), new GStringImpl(new Object[]{callSiteArray[14].call(this.unmatched, 0)}, new String[]{"grape: '", "' is not a grape command. See 'grape --help'"}));
        } else {
            callSiteArray[15].call((Object)this.parser, callSiteArray[16].callGetProperty(System.class));
        }
    }

    private void init() {
        CallSite[] callSiteArray = GrapeMain.$getCallSiteArray();
        public final class _init_closure3
        extends Closure
        implements GeneratedClosure {
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _init_closure3(Object _outerInstance, Object _thisObject) {
                CallSite[] callSiteArray = _init_closure3.$getCallSiteArray();
                super(_outerInstance, _thisObject);
            }

            public Object doCall(Object k, Object v) {
                CallSite[] callSiteArray = _init_closure3.$getCallSiteArray();
                return callSiteArray[0].call(System.class, k, v);
            }

            @Generated
            public Object call(Object k, Object v) {
                CallSite[] callSiteArray = _init_closure3.$getCallSiteArray();
                return callSiteArray[1].callCurrent(this, k, v);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (this.getClass() != _init_closure3.class) {
                    return ScriptBytecodeAdapter.initMetaClass(this);
                }
                ClassInfo classInfo = $staticClassInfo;
                if (classInfo == null) {
                    $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
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
                _init_closure3.$createCallSiteArray_1(stringArray);
                return new CallSiteArray(_init_closure3.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _init_closure3.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        callSiteArray[17].call(this.properties, new _init_closure3(this, this));
    }

    private void setupLogging(int defaultLevel) {
        CallSite[] callSiteArray = GrapeMain.$getCallSiteArray();
        if (this.quiet) {
            Object object = callSiteArray[18].callConstructor(DefaultMessageLogger.class, callSiteArray[19].callGetProperty(Message.class));
            ScriptBytecodeAdapter.setProperty(object, null, Message.class, "defaultLogger");
        } else if (this.warn) {
            Object object = callSiteArray[20].callConstructor(DefaultMessageLogger.class, callSiteArray[21].callGetProperty(Message.class));
            ScriptBytecodeAdapter.setProperty(object, null, Message.class, "defaultLogger");
        } else if (this.info) {
            Object object = callSiteArray[22].callConstructor(DefaultMessageLogger.class, callSiteArray[23].callGetProperty(Message.class));
            ScriptBytecodeAdapter.setProperty(object, null, Message.class, "defaultLogger");
        } else if (this.verbose) {
            Object object = callSiteArray[24].callConstructor(DefaultMessageLogger.class, callSiteArray[25].callGetProperty(Message.class));
            ScriptBytecodeAdapter.setProperty(object, null, Message.class, "defaultLogger");
        } else if (this.debug) {
            Object object = callSiteArray[26].callConstructor(DefaultMessageLogger.class, callSiteArray[27].callGetProperty(Message.class));
            ScriptBytecodeAdapter.setProperty(object, null, Message.class, "defaultLogger");
        } else {
            Object object = callSiteArray[28].callConstructor(DefaultMessageLogger.class, defaultLevel);
            ScriptBytecodeAdapter.setProperty(object, null, Message.class, "defaultLogger");
        }
    }

    public /* synthetic */ Object this$dist$invoke$1(String name, Object args) {
        CallSite[] callSiteArray = GrapeMain.$getCallSiteArray();
        if (!(args instanceof Object[])) {
            return ScriptBytecodeAdapter.invokeMethodOnCurrentN(GrapeMain.class, this, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{args});
        }
        if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            if (ScriptBytecodeAdapter.compareEqual(callSiteArray[29].callGetProperty((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 1)) {
                return ScriptBytecodeAdapter.invokeMethodOnCurrentN(GrapeMain.class, this, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{callSiteArray[30].call((Object)((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 0)});
            }
        } else if (ScriptBytecodeAdapter.compareEqual(callSiteArray[31].callGetProperty((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 1)) {
            return ScriptBytecodeAdapter.invokeMethodOnCurrentN(GrapeMain.class, this, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{BytecodeInterface8.objectArrayGet((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class), 0)});
        }
        return ScriptBytecodeAdapter.invokeMethodOnCurrentN(GrapeMain.class, this, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), ScriptBytecodeAdapter.despreadList(new Object[0], new Object[]{args}, new int[]{0}));
    }

    public /* synthetic */ void this$dist$set$1(String name, Object value) {
        CallSite[] callSiteArray = GrapeMain.$getCallSiteArray();
        Object object = value;
        ScriptBytecodeAdapter.setGroovyObjectProperty(object, GrapeMain.class, this, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})));
    }

    public /* synthetic */ Object this$dist$get$1(String name) {
        CallSite[] callSiteArray = GrapeMain.$getCallSiteArray();
        return ScriptBytecodeAdapter.getGroovyObjectProperty(GrapeMain.class, this, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})));
    }

    @Generated
    private void setupLogging() {
        CallSite[] callSiteArray = GrapeMain.$getCallSiteArray();
        if (__$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            this.setupLogging(2);
        } else {
            this.setupLogging(2);
        }
    }

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != GrapeMain.class) {
            return ScriptBytecodeAdapter.initMetaClass(this);
        }
        ClassInfo classInfo = $staticClassInfo;
        if (classInfo == null) {
            $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
        }
        return classInfo.getMetaClass();
    }

    @Override
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

    @Override
    @Generated
    @Internal
    public void setMetaClass(MetaClass metaClass) {
        this.metaClass = metaClass;
    }

    public static /* synthetic */ MethodHandles.Lookup $getLookup() {
        return MethodHandles.lookup();
    }

    @Generated
    public List<String> getUnmatched() {
        return this.unmatched;
    }

    @Generated
    public void setUnmatched(List<String> list) {
        this.unmatched = list;
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
        stringArray[0] = "<$constructor$>";
        stringArray[1] = "<$constructor$>";
        stringArray[2] = "<$constructor$>";
        stringArray[3] = "<$constructor$>";
        stringArray[4] = "<$constructor$>";
        stringArray[5] = "addMixin";
        stringArray[6] = "<$constructor$>";
        stringArray[7] = "each";
        stringArray[8] = "findAll";
        stringArray[9] = "subcommands";
        stringArray[10] = "parseWithHandler";
        stringArray[11] = "<$constructor$>";
        stringArray[12] = "println";
        stringArray[13] = "err";
        stringArray[14] = "getAt";
        stringArray[15] = "usage";
        stringArray[16] = "out";
        stringArray[17] = "each";
        stringArray[18] = "<$constructor$>";
        stringArray[19] = "MSG_ERR";
        stringArray[20] = "<$constructor$>";
        stringArray[21] = "MSG_WARN";
        stringArray[22] = "<$constructor$>";
        stringArray[23] = "MSG_INFO";
        stringArray[24] = "<$constructor$>";
        stringArray[25] = "MSG_VERBOSE";
        stringArray[26] = "<$constructor$>";
        stringArray[27] = "MSG_DEBUG";
        stringArray[28] = "<$constructor$>";
        stringArray[29] = "length";
        stringArray[30] = "getAt";
        stringArray[31] = "length";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[32];
        GrapeMain.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(GrapeMain.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = GrapeMain.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }

    @CommandLine.Command(optionListHeading="%nOptions:%n", sortOptions=false, versionProvider=VersionProvider.class, parameterListHeading="%nParameters:%n", descriptionHeading="%n")
    private static class HelpOptionsMixin
    implements GroovyObject {
        @CommandLine.Option(usageHelp=true, names={"-h", "--help"}, description={"usage information"})
        private boolean isHelpRequested;
        @CommandLine.Option(versionHelp=true, names={"-v", "--version"}, description={"display the Groovy and JVM versions"})
        private boolean isVersionRequested;
        private static /* synthetic */ ClassInfo $staticClassInfo;
        public static transient /* synthetic */ boolean __$stMC;
        private transient /* synthetic */ MetaClass metaClass;
        private static /* synthetic */ SoftReference $callSiteArray;

        @Generated
        public HelpOptionsMixin() {
            MetaClass metaClass;
            CallSite[] callSiteArray = HelpOptionsMixin.$getCallSiteArray();
            this.metaClass = metaClass = this.$getStaticMetaClass();
        }

        public /* synthetic */ Object methodMissing(String name, Object args) {
            CallSite[] callSiteArray = HelpOptionsMixin.$getCallSiteArray();
            if (!(args instanceof Object[])) {
                return ScriptBytecodeAdapter.invokeMethodN(HelpOptionsMixin.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{args});
            }
            if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                if (ScriptBytecodeAdapter.compareEqual(callSiteArray[0].callGetProperty((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 1)) {
                    return ScriptBytecodeAdapter.invokeMethodN(HelpOptionsMixin.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{callSiteArray[1].call((Object)((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 0)});
                }
            } else if (ScriptBytecodeAdapter.compareEqual(callSiteArray[2].callGetProperty((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 1)) {
                return ScriptBytecodeAdapter.invokeMethodN(HelpOptionsMixin.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{BytecodeInterface8.objectArrayGet((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class), 0)});
            }
            return ScriptBytecodeAdapter.invokeMethodN(HelpOptionsMixin.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), ScriptBytecodeAdapter.despreadList(new Object[0], new Object[]{args}, new int[]{0}));
        }

        public static /* synthetic */ Object $static_methodMissing(String name, Object args) {
            CallSite[] callSiteArray = HelpOptionsMixin.$getCallSiteArray();
            if (!(args instanceof Object[])) {
                return ScriptBytecodeAdapter.invokeMethodN(HelpOptionsMixin.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{args});
            }
            if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                if (ScriptBytecodeAdapter.compareEqual(callSiteArray[3].callGetProperty((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 1)) {
                    return ScriptBytecodeAdapter.invokeMethodN(HelpOptionsMixin.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{callSiteArray[4].call((Object)((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 0)});
                }
            } else if (ScriptBytecodeAdapter.compareEqual(callSiteArray[5].callGetProperty((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 1)) {
                return ScriptBytecodeAdapter.invokeMethodN(HelpOptionsMixin.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{BytecodeInterface8.objectArrayGet((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class), 0)});
            }
            return ScriptBytecodeAdapter.invokeMethodN(HelpOptionsMixin.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), ScriptBytecodeAdapter.despreadList(new Object[0], new Object[]{args}, new int[]{0}));
        }

        public /* synthetic */ void propertyMissing(String name, Object value) {
            CallSite[] callSiteArray = HelpOptionsMixin.$getCallSiteArray();
            Object object = value;
            ScriptBytecodeAdapter.setProperty(object, null, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

        public static /* synthetic */ void $static_propertyMissing(String name, Object value) {
            CallSite[] callSiteArray = HelpOptionsMixin.$getCallSiteArray();
            Object object = value;
            ScriptBytecodeAdapter.setProperty(object, null, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

        public /* synthetic */ Object propertyMissing(String name) {
            CallSite[] callSiteArray = HelpOptionsMixin.$getCallSiteArray();
            return ScriptBytecodeAdapter.getProperty(HelpOptionsMixin.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

        public static /* synthetic */ Object $static_propertyMissing(String name) {
            CallSite[] callSiteArray = HelpOptionsMixin.$getCallSiteArray();
            return ScriptBytecodeAdapter.getProperty(HelpOptionsMixin.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

        protected /* synthetic */ MetaClass $getStaticMetaClass() {
            if (this.getClass() != HelpOptionsMixin.class) {
                return ScriptBytecodeAdapter.initMetaClass(this);
            }
            ClassInfo classInfo = $staticClassInfo;
            if (classInfo == null) {
                $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
            }
            return classInfo.getMetaClass();
        }

        @Override
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

        @Override
        @Generated
        @Internal
        public void setMetaClass(MetaClass metaClass) {
            this.metaClass = metaClass;
        }

        public static /* synthetic */ MethodHandles.Lookup $getLookup() {
            return MethodHandles.lookup();
        }

        @Generated
        public boolean getIsHelpRequested() {
            return this.isHelpRequested;
        }

        @Generated
        public boolean isIsHelpRequested() {
            return this.isHelpRequested;
        }

        @Generated
        public void setIsHelpRequested(boolean bl) {
            this.isHelpRequested = bl;
        }

        @Generated
        public boolean getIsVersionRequested() {
            return this.isVersionRequested;
        }

        @Generated
        public boolean isIsVersionRequested() {
            return this.isVersionRequested;
        }

        @Generated
        public void setIsVersionRequested(boolean bl) {
            this.isVersionRequested = bl;
        }

        private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
            stringArray[0] = "length";
            stringArray[1] = "getAt";
            stringArray[2] = "length";
            stringArray[3] = "length";
            stringArray[4] = "getAt";
            stringArray[5] = "length";
        }

        private static /* synthetic */ CallSiteArray $createCallSiteArray() {
            String[] stringArray = new String[6];
            HelpOptionsMixin.$createCallSiteArray_1(stringArray);
            return new CallSiteArray(HelpOptionsMixin.class, stringArray);
        }

        private static /* synthetic */ CallSite[] $getCallSiteArray() {
            CallSiteArray callSiteArray;
            if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                callSiteArray = HelpOptionsMixin.$createCallSiteArray();
                $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
            }
            return callSiteArray.array;
        }
    }

    private static class VersionProvider
    implements CommandLine.IVersionProvider,
    GroovyObject {
        private static /* synthetic */ ClassInfo $staticClassInfo;
        public static transient /* synthetic */ boolean __$stMC;
        private transient /* synthetic */ MetaClass metaClass;
        private static /* synthetic */ SoftReference $callSiteArray;

        @Generated
        public VersionProvider() {
            MetaClass metaClass;
            CallSite[] callSiteArray = VersionProvider.$getCallSiteArray();
            this.metaClass = metaClass = this.$getStaticMetaClass();
        }

        @Override
        public String[] getVersion() {
            CallSite[] callSiteArray = VersionProvider.$getCallSiteArray();
            String version = ShortTypeHandling.castToString(callSiteArray[0].callGetProperty(GroovySystem.class));
            return (String[])ScriptBytecodeAdapter.castToType(ScriptBytecodeAdapter.createList(new Object[]{new GStringImpl(new Object[]{version, callSiteArray[1].call(System.class, "java.version")}, new String[]{"Groovy Version: ", " JVM: ", ""})}), String[].class);
        }

        public /* synthetic */ Object methodMissing(String name, Object args) {
            CallSite[] callSiteArray = VersionProvider.$getCallSiteArray();
            if (!(args instanceof Object[])) {
                return ScriptBytecodeAdapter.invokeMethodN(VersionProvider.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{args});
            }
            if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                if (ScriptBytecodeAdapter.compareEqual(callSiteArray[2].callGetProperty((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 1)) {
                    return ScriptBytecodeAdapter.invokeMethodN(VersionProvider.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{callSiteArray[3].call((Object)((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 0)});
                }
            } else if (ScriptBytecodeAdapter.compareEqual(callSiteArray[4].callGetProperty((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 1)) {
                return ScriptBytecodeAdapter.invokeMethodN(VersionProvider.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{BytecodeInterface8.objectArrayGet((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class), 0)});
            }
            return ScriptBytecodeAdapter.invokeMethodN(VersionProvider.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), ScriptBytecodeAdapter.despreadList(new Object[0], new Object[]{args}, new int[]{0}));
        }

        public static /* synthetic */ Object $static_methodMissing(String name, Object args) {
            CallSite[] callSiteArray = VersionProvider.$getCallSiteArray();
            if (!(args instanceof Object[])) {
                return ScriptBytecodeAdapter.invokeMethodN(VersionProvider.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{args});
            }
            if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                if (ScriptBytecodeAdapter.compareEqual(callSiteArray[5].callGetProperty((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 1)) {
                    return ScriptBytecodeAdapter.invokeMethodN(VersionProvider.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{callSiteArray[6].call((Object)((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 0)});
                }
            } else if (ScriptBytecodeAdapter.compareEqual(callSiteArray[7].callGetProperty((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 1)) {
                return ScriptBytecodeAdapter.invokeMethodN(VersionProvider.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{BytecodeInterface8.objectArrayGet((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class), 0)});
            }
            return ScriptBytecodeAdapter.invokeMethodN(VersionProvider.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), ScriptBytecodeAdapter.despreadList(new Object[0], new Object[]{args}, new int[]{0}));
        }

        public /* synthetic */ void propertyMissing(String name, Object value) {
            CallSite[] callSiteArray = VersionProvider.$getCallSiteArray();
            Object object = value;
            ScriptBytecodeAdapter.setProperty(object, null, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

        public static /* synthetic */ void $static_propertyMissing(String name, Object value) {
            CallSite[] callSiteArray = VersionProvider.$getCallSiteArray();
            Object object = value;
            ScriptBytecodeAdapter.setProperty(object, null, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

        public /* synthetic */ Object propertyMissing(String name) {
            CallSite[] callSiteArray = VersionProvider.$getCallSiteArray();
            return ScriptBytecodeAdapter.getProperty(VersionProvider.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

        public static /* synthetic */ Object $static_propertyMissing(String name) {
            CallSite[] callSiteArray = VersionProvider.$getCallSiteArray();
            return ScriptBytecodeAdapter.getProperty(VersionProvider.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

        protected /* synthetic */ MetaClass $getStaticMetaClass() {
            if (this.getClass() != VersionProvider.class) {
                return ScriptBytecodeAdapter.initMetaClass(this);
            }
            ClassInfo classInfo = $staticClassInfo;
            if (classInfo == null) {
                $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
            }
            return classInfo.getMetaClass();
        }

        @Override
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

        @Override
        @Generated
        @Internal
        public void setMetaClass(MetaClass metaClass) {
            this.metaClass = metaClass;
        }

        public static /* synthetic */ MethodHandles.Lookup $getLookup() {
            return MethodHandles.lookup();
        }

        private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
            stringArray[0] = "version";
            stringArray[1] = "getProperty";
            stringArray[2] = "length";
            stringArray[3] = "getAt";
            stringArray[4] = "length";
            stringArray[5] = "length";
            stringArray[6] = "getAt";
            stringArray[7] = "length";
        }

        private static /* synthetic */ CallSiteArray $createCallSiteArray() {
            String[] stringArray = new String[8];
            VersionProvider.$createCallSiteArray_1(stringArray);
            return new CallSiteArray(VersionProvider.class, stringArray);
        }

        private static /* synthetic */ CallSite[] $getCallSiteArray() {
            CallSiteArray callSiteArray;
            if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                callSiteArray = VersionProvider.$createCallSiteArray();
                $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
            }
            return callSiteArray.array;
        }
    }

    @CommandLine.Command(name="install", header={"Installs a particular grape"}, description={"Installs the specified groovy module or maven artifact. If a version is specified that specific version will be installed, otherwise the most recent version will be used (as if `*` was passed in)."})
    private static class Install
    implements Runnable,
    GroovyObject {
        @CommandLine.Parameters(arity="1", index="0", description={"Which module group the module comes from. Translates directly to a Maven groupId or an Ivy Organization. Any group matching /groovy[x][\\..*]^/ is reserved and may have special meaning to the groovy endorsed modules."})
        private String group;
        @CommandLine.Parameters(arity="1", index="1", description={"The name of the module to load. Translated directly to a Maven artifactId or an Ivy artifact."})
        private String module;
        @CommandLine.Parameters(arity="0..1", index="2", description={"The version of the module to use. Either a literal version `1.1-RC3` or an Ivy Range `[2.2.1,)` meaning 2.2.1 or any greater version)."})
        private String version;
        @CommandLine.Parameters(arity="0..1", index="3", description={"The optional classifier to use (for example, jdk15)."})
        private String classifier;
        @CommandLine.ParentCommand
        private GrapeMain parentCommand;
        private static /* synthetic */ ClassInfo $staticClassInfo;
        public static transient /* synthetic */ boolean __$stMC;
        private transient /* synthetic */ MetaClass metaClass;
        private static /* synthetic */ SoftReference $callSiteArray;

        @Generated
        public Install() {
            MetaClass metaClass;
            String string;
            CallSite[] callSiteArray = Install.$getCallSiteArray();
            this.version = string = "*";
            this.metaClass = metaClass = this.$getStaticMetaClass();
        }

        @Override
        public void run() {
            CallSite[] callSiteArray = Install.$getCallSiteArray();
            callSiteArray[0].call(this.parentCommand);
            callSiteArray[1].callGetProperty(Grape.class);
            callSiteArray[2].call(this.parentCommand);
            public final class _run_closure1
            extends Closure
            implements GeneratedClosure {
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;
                private static /* synthetic */ SoftReference $callSiteArray;

                public _run_closure1(Object _outerInstance, Object _thisObject) {
                    CallSite[] callSiteArray = _run_closure1.$getCallSiteArray();
                    super(_outerInstance, _thisObject);
                }

                public Object doCall(String url) {
                    CallSite[] callSiteArray = _run_closure1.$getCallSiteArray();
                    return callSiteArray[0].call(Grape.class, ScriptBytecodeAdapter.createMap(new Object[]{"name", url, "root", url}));
                }

                @Generated
                public Object call(String url) {
                    CallSite[] callSiteArray = _run_closure1.$getCallSiteArray();
                    if (__$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                        return callSiteArray[1].callCurrent((GroovyObject)this, url);
                    }
                    return this.doCall(url);
                }

                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                    if (this.getClass() != _run_closure1.class) {
                        return ScriptBytecodeAdapter.initMetaClass(this);
                    }
                    ClassInfo classInfo = $staticClassInfo;
                    if (classInfo == null) {
                        $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
                    }
                    return classInfo.getMetaClass();
                }

                public /* synthetic */ MethodHandles.Lookup $getLookup() {
                    return MethodHandles.lookup();
                }

                private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
                    stringArray[0] = "addResolver";
                    stringArray[1] = "doCall";
                }

                private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                    String[] stringArray = new String[2];
                    _run_closure1.$createCallSiteArray_1(stringArray);
                    return new CallSiteArray(_run_closure1.class, stringArray);
                }

                private static /* synthetic */ CallSite[] $getCallSiteArray() {
                    CallSiteArray callSiteArray;
                    if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                        callSiteArray = _run_closure1.$createCallSiteArray();
                        $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                    }
                    return callSiteArray.array;
                }
            }
            callSiteArray[3].call(callSiteArray[4].callGroovyObjectGetProperty(this.parentCommand), new _run_closure1(this, this));
            try {
                callSiteArray[5].call(Grape.class, ScriptBytecodeAdapter.createMap(new Object[]{"autoDownload", true, "group", this.group, "module", this.module, "version", this.version, "classifier", this.classifier, "noExceptions", true}));
            }
            catch (Exception ex) {
                callSiteArray[6].call(callSiteArray[7].callGetProperty(System.class), new GStringImpl(new Object[]{ex}, new String[]{"An error occured : ", ""}));
            }
        }

        public /* synthetic */ Object methodMissing(String name, Object args) {
            CallSite[] callSiteArray = Install.$getCallSiteArray();
            if (!(args instanceof Object[])) {
                return ScriptBytecodeAdapter.invokeMethodN(Install.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{args});
            }
            if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                if (ScriptBytecodeAdapter.compareEqual(callSiteArray[8].callGetProperty((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 1)) {
                    return ScriptBytecodeAdapter.invokeMethodN(Install.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{callSiteArray[9].call((Object)((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 0)});
                }
            } else if (ScriptBytecodeAdapter.compareEqual(callSiteArray[10].callGetProperty((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 1)) {
                return ScriptBytecodeAdapter.invokeMethodN(Install.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{BytecodeInterface8.objectArrayGet((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class), 0)});
            }
            return ScriptBytecodeAdapter.invokeMethodN(Install.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), ScriptBytecodeAdapter.despreadList(new Object[0], new Object[]{args}, new int[]{0}));
        }

        public static /* synthetic */ Object $static_methodMissing(String name, Object args) {
            CallSite[] callSiteArray = Install.$getCallSiteArray();
            if (!(args instanceof Object[])) {
                return ScriptBytecodeAdapter.invokeMethodN(Install.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{args});
            }
            if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                if (ScriptBytecodeAdapter.compareEqual(callSiteArray[11].callGetProperty((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 1)) {
                    return ScriptBytecodeAdapter.invokeMethodN(Install.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{callSiteArray[12].call((Object)((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 0)});
                }
            } else if (ScriptBytecodeAdapter.compareEqual(callSiteArray[13].callGetProperty((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 1)) {
                return ScriptBytecodeAdapter.invokeMethodN(Install.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{BytecodeInterface8.objectArrayGet((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class), 0)});
            }
            return ScriptBytecodeAdapter.invokeMethodN(Install.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), ScriptBytecodeAdapter.despreadList(new Object[0], new Object[]{args}, new int[]{0}));
        }

        public /* synthetic */ void propertyMissing(String name, Object value) {
            CallSite[] callSiteArray = Install.$getCallSiteArray();
            Object object = value;
            ScriptBytecodeAdapter.setProperty(object, null, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

        public static /* synthetic */ void $static_propertyMissing(String name, Object value) {
            CallSite[] callSiteArray = Install.$getCallSiteArray();
            Object object = value;
            ScriptBytecodeAdapter.setProperty(object, null, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

        public /* synthetic */ Object propertyMissing(String name) {
            CallSite[] callSiteArray = Install.$getCallSiteArray();
            return ScriptBytecodeAdapter.getProperty(Install.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

        public static /* synthetic */ Object $static_propertyMissing(String name) {
            CallSite[] callSiteArray = Install.$getCallSiteArray();
            return ScriptBytecodeAdapter.getProperty(Install.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

        protected /* synthetic */ MetaClass $getStaticMetaClass() {
            if (this.getClass() != Install.class) {
                return ScriptBytecodeAdapter.initMetaClass(this);
            }
            ClassInfo classInfo = $staticClassInfo;
            if (classInfo == null) {
                $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
            }
            return classInfo.getMetaClass();
        }

        @Override
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

        @Override
        @Generated
        @Internal
        public void setMetaClass(MetaClass metaClass) {
            this.metaClass = metaClass;
        }

        public static /* synthetic */ MethodHandles.Lookup $getLookup() {
            return MethodHandles.lookup();
        }

        @Generated
        public String getGroup() {
            return this.group;
        }

        @Generated
        public void setGroup(String string) {
            this.group = string;
        }

        @Generated
        public String getModule() {
            return this.module;
        }

        @Generated
        public void setModule(String string) {
            this.module = string;
        }

        @Generated
        public String getVersion() {
            return this.version;
        }

        @Generated
        public void setVersion(String string) {
            this.version = string;
        }

        @Generated
        public String getClassifier() {
            return this.classifier;
        }

        @Generated
        public void setClassifier(String string) {
            this.classifier = string;
        }

        @Generated
        public GrapeMain getParentCommand() {
            return this.parentCommand;
        }

        @Generated
        public void setParentCommand(GrapeMain grapeMain) {
            this.parentCommand = grapeMain;
        }

        private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
            stringArray[0] = "init";
            stringArray[1] = "instance";
            stringArray[2] = "setupLogging";
            stringArray[3] = "each";
            stringArray[4] = "resolvers";
            stringArray[5] = "grab";
            stringArray[6] = "println";
            stringArray[7] = "err";
            stringArray[8] = "length";
            stringArray[9] = "getAt";
            stringArray[10] = "length";
            stringArray[11] = "length";
            stringArray[12] = "getAt";
            stringArray[13] = "length";
        }

        private static /* synthetic */ CallSiteArray $createCallSiteArray() {
            String[] stringArray = new String[14];
            Install.$createCallSiteArray_1(stringArray);
            return new CallSiteArray(Install.class, stringArray);
        }

        private static /* synthetic */ CallSite[] $getCallSiteArray() {
            CallSiteArray callSiteArray;
            if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                callSiteArray = Install.$createCallSiteArray();
                $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
            }
            return callSiteArray.array;
        }
    }

    @CommandLine.Command(name="list", header={"Lists all installed grapes"}, description={"Lists locally installed modules (with their full maven name in the case of groovy modules) and versions."})
    private static class ListCommand
    implements Runnable,
    GroovyObject {
        @CommandLine.ParentCommand
        private GrapeMain parentCommand;
        private static /* synthetic */ ClassInfo $staticClassInfo;
        public static transient /* synthetic */ boolean __$stMC;
        private transient /* synthetic */ MetaClass metaClass;
        private static /* synthetic */ SoftReference $callSiteArray;

        @Generated
        public ListCommand() {
            MetaClass metaClass;
            CallSite[] callSiteArray = ListCommand.$getCallSiteArray();
            this.metaClass = metaClass = this.$getStaticMetaClass();
        }

        @Override
        public void run() {
            CallSite[] callSiteArray = ListCommand.$getCallSiteArray();
            callSiteArray[0].call(this.parentCommand);
            callSiteArray[1].callCurrent((GroovyObject)this, "");
            Reference<Integer> moduleCount = new Reference<Integer>(0);
            Reference<Integer> versionCount = new Reference<Integer>(0);
            callSiteArray[2].callGetProperty(Grape.class);
            callSiteArray[3].call(this.parentCommand);
            public final class _run_closure1
            extends Closure
            implements GeneratedClosure {
                private /* synthetic */ Reference moduleCount;
                private /* synthetic */ Reference versionCount;
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;
                private static /* synthetic */ SoftReference $callSiteArray;

                public _run_closure1(Object _outerInstance, Object _thisObject, Reference moduleCount, Reference versionCount) {
                    Reference reference;
                    Reference reference2;
                    CallSite[] callSiteArray = _run_closure1.$getCallSiteArray();
                    super(_outerInstance, _thisObject);
                    this.moduleCount = reference2 = moduleCount;
                    this.versionCount = reference = versionCount;
                }

                public Object doCall(String groupName, Map group) {
                    Reference<String> groupName2 = new Reference<String>(groupName);
                    CallSite[] callSiteArray = _run_closure1.$getCallSiteArray();
                    public final class _closure2
                    extends Closure
                    implements GeneratedClosure {
                        private /* synthetic */ Reference groupName;
                        private /* synthetic */ Reference moduleCount;
                        private /* synthetic */ Reference versionCount;
                        private static /* synthetic */ ClassInfo $staticClassInfo;
                        public static transient /* synthetic */ boolean __$stMC;
                        private static /* synthetic */ SoftReference $callSiteArray;

                        public _closure2(Object _outerInstance, Object _thisObject, Reference groupName, Reference moduleCount, Reference versionCount) {
                            Reference reference;
                            Reference reference2;
                            Reference reference3;
                            CallSite[] callSiteArray = _closure2.$getCallSiteArray();
                            super(_outerInstance, _thisObject);
                            this.groupName = reference3 = groupName;
                            this.moduleCount = reference2 = moduleCount;
                            this.versionCount = reference = versionCount;
                        }

                        public Object doCall(String moduleName, List<String> versions) {
                            CallSite[] callSiteArray = _closure2.$getCallSiteArray();
                            callSiteArray[0].callCurrent((GroovyObject)this, new GStringImpl(new Object[]{this.groupName.get(), moduleName, versions}, new String[]{"", " ", "  ", ""}));
                            Object t = this.moduleCount.get();
                            this.moduleCount.set((Integer)ScriptBytecodeAdapter.castToType(callSiteArray[1].call(t), Integer.class));
                            Object object = callSiteArray[2].call(this.versionCount.get(), callSiteArray[3].call(versions));
                            this.versionCount.set((Integer)ScriptBytecodeAdapter.castToType(object, Integer.class));
                            return object;
                        }

                        @Generated
                        public Object call(String moduleName, List<String> versions) {
                            CallSite[] callSiteArray = _closure2.$getCallSiteArray();
                            return callSiteArray[4].callCurrent(this, moduleName, versions);
                        }

                        @Generated
                        public String getGroupName() {
                            CallSite[] callSiteArray = _closure2.$getCallSiteArray();
                            return ShortTypeHandling.castToString(this.groupName.get());
                        }

                        @Generated
                        public Integer getModuleCount() {
                            CallSite[] callSiteArray = _closure2.$getCallSiteArray();
                            return (Integer)ScriptBytecodeAdapter.castToType(this.moduleCount.get(), Integer.class);
                        }

                        @Generated
                        public Integer getVersionCount() {
                            CallSite[] callSiteArray = _closure2.$getCallSiteArray();
                            return (Integer)ScriptBytecodeAdapter.castToType(this.versionCount.get(), Integer.class);
                        }

                        protected /* synthetic */ MetaClass $getStaticMetaClass() {
                            if (this.getClass() != _closure2.class) {
                                return ScriptBytecodeAdapter.initMetaClass(this);
                            }
                            ClassInfo classInfo = $staticClassInfo;
                            if (classInfo == null) {
                                $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
                            }
                            return classInfo.getMetaClass();
                        }

                        public /* synthetic */ MethodHandles.Lookup $getLookup() {
                            return MethodHandles.lookup();
                        }

                        private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
                            stringArray[0] = "println";
                            stringArray[1] = "next";
                            stringArray[2] = "plus";
                            stringArray[3] = "size";
                            stringArray[4] = "doCall";
                        }

                        private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                            String[] stringArray = new String[5];
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
                    return callSiteArray[0].call((Object)group, new _closure2(this, this.getThisObject(), groupName2, this.moduleCount, this.versionCount));
                }

                @Generated
                public Object call(String groupName, Map group) {
                    Reference<String> groupName2 = new Reference<String>(groupName);
                    CallSite[] callSiteArray = _run_closure1.$getCallSiteArray();
                    return callSiteArray[1].callCurrent(this, groupName2.get(), group);
                }

                @Generated
                public Integer getModuleCount() {
                    CallSite[] callSiteArray = _run_closure1.$getCallSiteArray();
                    return (Integer)ScriptBytecodeAdapter.castToType(this.moduleCount.get(), Integer.class);
                }

                @Generated
                public Integer getVersionCount() {
                    CallSite[] callSiteArray = _run_closure1.$getCallSiteArray();
                    return (Integer)ScriptBytecodeAdapter.castToType(this.versionCount.get(), Integer.class);
                }

                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                    if (this.getClass() != _run_closure1.class) {
                        return ScriptBytecodeAdapter.initMetaClass(this);
                    }
                    ClassInfo classInfo = $staticClassInfo;
                    if (classInfo == null) {
                        $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
                    }
                    return classInfo.getMetaClass();
                }

                public /* synthetic */ MethodHandles.Lookup $getLookup() {
                    return MethodHandles.lookup();
                }

                private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
                    stringArray[0] = "each";
                    stringArray[1] = "doCall";
                }

                private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                    String[] stringArray = new String[2];
                    _run_closure1.$createCallSiteArray_1(stringArray);
                    return new CallSiteArray(_run_closure1.class, stringArray);
                }

                private static /* synthetic */ CallSite[] $getCallSiteArray() {
                    CallSiteArray callSiteArray;
                    if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                        callSiteArray = _run_closure1.$createCallSiteArray();
                        $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                    }
                    return callSiteArray.array;
                }
            }
            callSiteArray[4].call(callSiteArray[5].call(Grape.class), new _run_closure1(this, this, moduleCount, versionCount));
            callSiteArray[6].callCurrent((GroovyObject)this, "");
            callSiteArray[7].callCurrent((GroovyObject)this, new GStringImpl(new Object[]{moduleCount.get()}, new String[]{"", " Grape modules cached"}));
            callSiteArray[8].callCurrent((GroovyObject)this, new GStringImpl(new Object[]{versionCount.get()}, new String[]{"", " Grape module versions cached"}));
        }

        public /* synthetic */ Object methodMissing(String name, Object args) {
            CallSite[] callSiteArray = ListCommand.$getCallSiteArray();
            if (!(args instanceof Object[])) {
                return ScriptBytecodeAdapter.invokeMethodN(ListCommand.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{args});
            }
            if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                if (ScriptBytecodeAdapter.compareEqual(callSiteArray[9].callGetProperty((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 1)) {
                    return ScriptBytecodeAdapter.invokeMethodN(ListCommand.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{callSiteArray[10].call((Object)((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 0)});
                }
            } else if (ScriptBytecodeAdapter.compareEqual(callSiteArray[11].callGetProperty((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 1)) {
                return ScriptBytecodeAdapter.invokeMethodN(ListCommand.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{BytecodeInterface8.objectArrayGet((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class), 0)});
            }
            return ScriptBytecodeAdapter.invokeMethodN(ListCommand.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), ScriptBytecodeAdapter.despreadList(new Object[0], new Object[]{args}, new int[]{0}));
        }

        public static /* synthetic */ Object $static_methodMissing(String name, Object args) {
            CallSite[] callSiteArray = ListCommand.$getCallSiteArray();
            if (!(args instanceof Object[])) {
                return ScriptBytecodeAdapter.invokeMethodN(ListCommand.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{args});
            }
            if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                if (ScriptBytecodeAdapter.compareEqual(callSiteArray[12].callGetProperty((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 1)) {
                    return ScriptBytecodeAdapter.invokeMethodN(ListCommand.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{callSiteArray[13].call((Object)((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 0)});
                }
            } else if (ScriptBytecodeAdapter.compareEqual(callSiteArray[14].callGetProperty((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 1)) {
                return ScriptBytecodeAdapter.invokeMethodN(ListCommand.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{BytecodeInterface8.objectArrayGet((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class), 0)});
            }
            return ScriptBytecodeAdapter.invokeMethodN(ListCommand.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), ScriptBytecodeAdapter.despreadList(new Object[0], new Object[]{args}, new int[]{0}));
        }

        public /* synthetic */ void propertyMissing(String name, Object value) {
            CallSite[] callSiteArray = ListCommand.$getCallSiteArray();
            Object object = value;
            ScriptBytecodeAdapter.setProperty(object, null, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

        public static /* synthetic */ void $static_propertyMissing(String name, Object value) {
            CallSite[] callSiteArray = ListCommand.$getCallSiteArray();
            Object object = value;
            ScriptBytecodeAdapter.setProperty(object, null, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

        public /* synthetic */ Object propertyMissing(String name) {
            CallSite[] callSiteArray = ListCommand.$getCallSiteArray();
            return ScriptBytecodeAdapter.getProperty(ListCommand.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

        public static /* synthetic */ Object $static_propertyMissing(String name) {
            CallSite[] callSiteArray = ListCommand.$getCallSiteArray();
            return ScriptBytecodeAdapter.getProperty(ListCommand.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

        protected /* synthetic */ MetaClass $getStaticMetaClass() {
            if (this.getClass() != ListCommand.class) {
                return ScriptBytecodeAdapter.initMetaClass(this);
            }
            ClassInfo classInfo = $staticClassInfo;
            if (classInfo == null) {
                $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
            }
            return classInfo.getMetaClass();
        }

        @Override
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

        @Override
        @Generated
        @Internal
        public void setMetaClass(MetaClass metaClass) {
            this.metaClass = metaClass;
        }

        public static /* synthetic */ MethodHandles.Lookup $getLookup() {
            return MethodHandles.lookup();
        }

        @Generated
        public GrapeMain getParentCommand() {
            return this.parentCommand;
        }

        @Generated
        public void setParentCommand(GrapeMain grapeMain) {
            this.parentCommand = grapeMain;
        }

        private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
            stringArray[0] = "init";
            stringArray[1] = "println";
            stringArray[2] = "instance";
            stringArray[3] = "setupLogging";
            stringArray[4] = "each";
            stringArray[5] = "enumerateGrapes";
            stringArray[6] = "println";
            stringArray[7] = "println";
            stringArray[8] = "println";
            stringArray[9] = "length";
            stringArray[10] = "getAt";
            stringArray[11] = "length";
            stringArray[12] = "length";
            stringArray[13] = "getAt";
            stringArray[14] = "length";
        }

        private static /* synthetic */ CallSiteArray $createCallSiteArray() {
            String[] stringArray = new String[15];
            ListCommand.$createCallSiteArray_1(stringArray);
            return new CallSiteArray(ListCommand.class, stringArray);
        }

        private static /* synthetic */ CallSite[] $getCallSiteArray() {
            CallSiteArray callSiteArray;
            if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                callSiteArray = ListCommand.$createCallSiteArray();
                $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
            }
            return callSiteArray.array;
        }
    }

    @CommandLine.Command(name="resolve", header={"Enumerates the jars used by a grape"}, description={"Prints the file locations of the jars representing the artifcats for the specified module(s) and the respective transitive dependencies.", "", "Parameters:", "      <group>     Which module group the module comes from. Translates directly", "                    to a Maven groupId or an Ivy Organization. Any group", "                    matching /groovy[x][\\..*]^/ is reserved and may have", "                    special meaning to the groovy endorsed modules.", "      <module>    The name of the module to load. Translated directly to a", "                    Maven artifactId or an Ivy artifact.", "      <version>   The version of the module to use. Either a literal version", "                    `1.1-RC3` or an Ivy Range `[2.2.1,)` meaning 2.2.1 or any", "                    greater version)."}, customSynopsis={"grape resolve [-adhisv] (<groupId> <artifactId> <version>)+"})
    private static class Resolve
    implements Runnable,
    GroovyObject {
        @CommandLine.Option(names={"-a", "--ant"}, description={"Express dependencies in a format applicable for an ant script"})
        private boolean ant;
        @CommandLine.Option(names={"-d", "--dos"}, description={"Express dependencies in a format applicable for a windows batch file"})
        private boolean dos;
        @CommandLine.Option(names={"-s", "--shell"}, description={"Express dependencies in a format applicable for a unix shell script"})
        private boolean shell;
        @CommandLine.Option(names={"-i", "--ivy"}, description={"Express dependencies in an ivy-like format"})
        private boolean ivyFormatRequested;
        @CommandLine.Parameters(hidden=true)
        private List<String> args;
        @CommandLine.ParentCommand
        private GrapeMain parentCommand;
        private static /* synthetic */ ClassInfo $staticClassInfo;
        public static transient /* synthetic */ boolean __$stMC;
        private transient /* synthetic */ MetaClass metaClass;
        private static /* synthetic */ SoftReference $callSiteArray;

        @Generated
        public Resolve() {
            MetaClass metaClass;
            CallSite[] callSiteArray = Resolve.$getCallSiteArray();
            Object object = callSiteArray[0].callConstructor(ArrayList.class);
            this.args = (List)ScriptBytecodeAdapter.castToType(object, List.class);
            this.metaClass = metaClass = this.$getStaticMetaClass();
        }

        @Override
        public void run() {
            block23: {
                CallSite[] callSiteArray = Resolve.$getCallSiteArray();
                callSiteArray[1].call(this.parentCommand);
                callSiteArray[2].callGetProperty(Grape.class);
                callSiteArray[3].call((Object)this.parentCommand, callSiteArray[4].callGetProperty(Message.class));
                if (ScriptBytecodeAdapter.compareNotEqual(callSiteArray[5].call(callSiteArray[6].call(this.args), 3), 0)) {
                    callSiteArray[7].callCurrent((GroovyObject)this, "There needs to be a multiple of three arguments: (group module version)+");
                    return;
                }
                if (ScriptBytecodeAdapter.compareLessThan(callSiteArray[8].call(this.args), 3)) {
                    callSiteArray[9].callCurrent((GroovyObject)this, "At least one Grape reference is required");
                    return;
                }
                String before = null;
                String between = null;
                String after = null;
                if (this.ant) {
                    String string;
                    String string2;
                    String string3;
                    before = string3 = "<pathelement location=\"";
                    between = string2 = "\">\n<pathelement location=\"";
                    after = string = "\">";
                } else if (this.dos) {
                    String string;
                    String string4;
                    String string5;
                    before = string5 = "set CLASSPATH=";
                    between = string4 = ";";
                    after = string = "";
                } else if (this.shell) {
                    String string;
                    String string6;
                    String string7;
                    before = string7 = "export CLASSPATH=";
                    between = string6 = ":";
                    after = string = "";
                } else if (this.ivyFormatRequested) {
                    String string;
                    String string8;
                    String string9;
                    before = string9 = "<dependency ";
                    between = string8 = "\">\n<dependency ";
                    after = string = "\">";
                } else {
                    String string;
                    String string10;
                    String string11;
                    before = string11 = "";
                    between = string10 = "\n";
                    after = string = "\n";
                }
                Object iter = callSiteArray[10].call(this.args);
                List params = ScriptBytecodeAdapter.createList(new Object[]{ScriptBytecodeAdapter.createMap(new Object[0])});
                List depsInfo = ScriptBytecodeAdapter.createList(new Object[0]);
                if (this.ivyFormatRequested) {
                    callSiteArray[11].call((Object)params, depsInfo);
                }
                while (DefaultTypeTransformation.booleanUnbox(callSiteArray[12].call(iter))) {
                    callSiteArray[13].call((Object)params, ScriptBytecodeAdapter.createMap(new Object[]{"group", callSiteArray[14].call(iter), "module", callSiteArray[15].call(iter), "version", callSiteArray[16].call(iter)}));
                }
                try {
                    Reference<List> results = new Reference<List>(ScriptBytecodeAdapter.createList(new Object[0]));
                    Object uris = callSiteArray[17].call((Object)Grape.class, ScriptBytecodeAdapter.despreadList(new Object[0], new Object[]{params}, new int[]{0}));
                    if (!this.ivyFormatRequested) {
                        URI uri = null;
                        Iterator iterator = (Iterator)ScriptBytecodeAdapter.castToType(callSiteArray[18].call(uris), Iterator.class);
                        if (iterator != null) {
                            while (iterator.hasNext()) {
                                uri = (URI)ScriptBytecodeAdapter.castToType(iterator.next(), URI.class);
                                if (ScriptBytecodeAdapter.compareEqual(callSiteArray[19].callGetProperty(uri), "file")) {
                                    results.set((List)callSiteArray[20].call((Object)results.get(), callSiteArray[21].callGetProperty(callSiteArray[22].callConstructor(File.class, uri))));
                                    continue;
                                }
                                results.set((List)callSiteArray[23].call((Object)results.get(), callSiteArray[24].call(uri)));
                            }
                        }
                    } else {
                        public final class _run_closure1
                        extends Closure
                        implements GeneratedClosure {
                            private /* synthetic */ Reference results;
                            private static /* synthetic */ ClassInfo $staticClassInfo;
                            public static transient /* synthetic */ boolean __$stMC;
                            private static /* synthetic */ SoftReference $callSiteArray;

                            public _run_closure1(Object _outerInstance, Object _thisObject, Reference results) {
                                Reference reference;
                                CallSite[] callSiteArray = _run_closure1.$getCallSiteArray();
                                super(_outerInstance, _thisObject);
                                this.results = reference = results;
                            }

                            public Object doCall(Object dep) {
                                CallSite[] callSiteArray = _run_closure1.$getCallSiteArray();
                                Object object = callSiteArray[0].call(this.results.get(), callSiteArray[1].call(callSiteArray[2].call(callSiteArray[3].call(callSiteArray[4].call(callSiteArray[5].call((Object)"org=\"", callSiteArray[6].callGetProperty(dep)), "\" name=\""), callSiteArray[7].callGetProperty(dep)), "\" revision=\""), callSiteArray[8].callGetProperty(dep)));
                                this.results.set(object);
                                return object;
                            }

                            @Generated
                            public Object getResults() {
                                CallSite[] callSiteArray = _run_closure1.$getCallSiteArray();
                                return this.results.get();
                            }

                            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                                if (this.getClass() != _run_closure1.class) {
                                    return ScriptBytecodeAdapter.initMetaClass(this);
                                }
                                ClassInfo classInfo = $staticClassInfo;
                                if (classInfo == null) {
                                    $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
                                }
                                return classInfo.getMetaClass();
                            }

                            public /* synthetic */ MethodHandles.Lookup $getLookup() {
                                return MethodHandles.lookup();
                            }

                            private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
                                stringArray[0] = "plus";
                                stringArray[1] = "plus";
                                stringArray[2] = "plus";
                                stringArray[3] = "plus";
                                stringArray[4] = "plus";
                                stringArray[5] = "plus";
                                stringArray[6] = "group";
                                stringArray[7] = "module";
                                stringArray[8] = "revision";
                            }

                            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                                String[] stringArray = new String[9];
                                _run_closure1.$createCallSiteArray_1(stringArray);
                                return new CallSiteArray(_run_closure1.class, stringArray);
                            }

                            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                                CallSiteArray callSiteArray;
                                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                                    callSiteArray = _run_closure1.$createCallSiteArray();
                                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                                }
                                return callSiteArray.array;
                            }
                        }
                        callSiteArray[25].call((Object)depsInfo, new _run_closure1(this, this, results));
                    }
                    if (DefaultTypeTransformation.booleanUnbox(results.get())) {
                        callSiteArray[26].callCurrent((GroovyObject)this, new GStringImpl(new Object[]{before, callSiteArray[27].call((Object)results.get(), between), after}, new String[]{"", "", "", ""}));
                        break block23;
                    }
                    callSiteArray[28].callCurrent((GroovyObject)this, "Nothing was resolved");
                }
                catch (Exception e) {
                    callSiteArray[29].call(callSiteArray[30].callGetProperty(System.class), new GStringImpl(new Object[]{callSiteArray[31].callGetProperty(e)}, new String[]{"Error in resolve:\n\t", ""}));
                    if (DefaultTypeTransformation.booleanUnbox(ScriptBytecodeAdapter.findRegex(callSiteArray[32].callGetProperty(e), "unresolved dependency"))) {
                        callSiteArray[33].callCurrent((GroovyObject)this, "Perhaps the grape is not installed?");
                    }
                }
            }
        }

        public /* synthetic */ Object methodMissing(String name, Object args) {
            CallSite[] callSiteArray = Resolve.$getCallSiteArray();
            if (!(args instanceof Object[])) {
                return ScriptBytecodeAdapter.invokeMethodN(Resolve.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{args});
            }
            if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                if (ScriptBytecodeAdapter.compareEqual(callSiteArray[34].callGetProperty((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 1)) {
                    return ScriptBytecodeAdapter.invokeMethodN(Resolve.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{callSiteArray[35].call((Object)((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 0)});
                }
            } else if (ScriptBytecodeAdapter.compareEqual(callSiteArray[36].callGetProperty((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 1)) {
                return ScriptBytecodeAdapter.invokeMethodN(Resolve.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{BytecodeInterface8.objectArrayGet((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class), 0)});
            }
            return ScriptBytecodeAdapter.invokeMethodN(Resolve.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), ScriptBytecodeAdapter.despreadList(new Object[0], new Object[]{args}, new int[]{0}));
        }

        public static /* synthetic */ Object $static_methodMissing(String name, Object args) {
            CallSite[] callSiteArray = Resolve.$getCallSiteArray();
            if (!(args instanceof Object[])) {
                return ScriptBytecodeAdapter.invokeMethodN(Resolve.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{args});
            }
            if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                if (ScriptBytecodeAdapter.compareEqual(callSiteArray[37].callGetProperty((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 1)) {
                    return ScriptBytecodeAdapter.invokeMethodN(Resolve.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{callSiteArray[38].call((Object)((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 0)});
                }
            } else if (ScriptBytecodeAdapter.compareEqual(callSiteArray[39].callGetProperty((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 1)) {
                return ScriptBytecodeAdapter.invokeMethodN(Resolve.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{BytecodeInterface8.objectArrayGet((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class), 0)});
            }
            return ScriptBytecodeAdapter.invokeMethodN(Resolve.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), ScriptBytecodeAdapter.despreadList(new Object[0], new Object[]{args}, new int[]{0}));
        }

        public /* synthetic */ void propertyMissing(String name, Object value) {
            CallSite[] callSiteArray = Resolve.$getCallSiteArray();
            Object object = value;
            ScriptBytecodeAdapter.setProperty(object, null, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

        public static /* synthetic */ void $static_propertyMissing(String name, Object value) {
            CallSite[] callSiteArray = Resolve.$getCallSiteArray();
            Object object = value;
            ScriptBytecodeAdapter.setProperty(object, null, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

        public /* synthetic */ Object propertyMissing(String name) {
            CallSite[] callSiteArray = Resolve.$getCallSiteArray();
            return ScriptBytecodeAdapter.getProperty(Resolve.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

        public static /* synthetic */ Object $static_propertyMissing(String name) {
            CallSite[] callSiteArray = Resolve.$getCallSiteArray();
            return ScriptBytecodeAdapter.getProperty(Resolve.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

        protected /* synthetic */ MetaClass $getStaticMetaClass() {
            if (this.getClass() != Resolve.class) {
                return ScriptBytecodeAdapter.initMetaClass(this);
            }
            ClassInfo classInfo = $staticClassInfo;
            if (classInfo == null) {
                $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
            }
            return classInfo.getMetaClass();
        }

        @Override
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

        @Override
        @Generated
        @Internal
        public void setMetaClass(MetaClass metaClass) {
            this.metaClass = metaClass;
        }

        public static /* synthetic */ MethodHandles.Lookup $getLookup() {
            return MethodHandles.lookup();
        }

        @Generated
        public List<String> getArgs() {
            return this.args;
        }

        @Generated
        public void setArgs(List<String> list) {
            this.args = list;
        }

        @Generated
        public GrapeMain getParentCommand() {
            return this.parentCommand;
        }

        @Generated
        public void setParentCommand(GrapeMain grapeMain) {
            this.parentCommand = grapeMain;
        }

        private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
            stringArray[0] = "<$constructor$>";
            stringArray[1] = "init";
            stringArray[2] = "instance";
            stringArray[3] = "setupLogging";
            stringArray[4] = "MSG_ERR";
            stringArray[5] = "mod";
            stringArray[6] = "size";
            stringArray[7] = "println";
            stringArray[8] = "size";
            stringArray[9] = "println";
            stringArray[10] = "iterator";
            stringArray[11] = "leftShift";
            stringArray[12] = "hasNext";
            stringArray[13] = "add";
            stringArray[14] = "next";
            stringArray[15] = "next";
            stringArray[16] = "next";
            stringArray[17] = "resolve";
            stringArray[18] = "iterator";
            stringArray[19] = "scheme";
            stringArray[20] = "plus";
            stringArray[21] = "path";
            stringArray[22] = "<$constructor$>";
            stringArray[23] = "plus";
            stringArray[24] = "toASCIIString";
            stringArray[25] = "each";
            stringArray[26] = "println";
            stringArray[27] = "join";
            stringArray[28] = "println";
            stringArray[29] = "println";
            stringArray[30] = "err";
            stringArray[31] = "message";
            stringArray[32] = "message";
            stringArray[33] = "println";
            stringArray[34] = "length";
            stringArray[35] = "getAt";
            stringArray[36] = "length";
            stringArray[37] = "length";
            stringArray[38] = "getAt";
            stringArray[39] = "length";
        }

        private static /* synthetic */ CallSiteArray $createCallSiteArray() {
            String[] stringArray = new String[40];
            Resolve.$createCallSiteArray_1(stringArray);
            return new CallSiteArray(Resolve.class, stringArray);
        }

        private static /* synthetic */ CallSite[] $getCallSiteArray() {
            CallSiteArray callSiteArray;
            if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                callSiteArray = Resolve.$createCallSiteArray();
                $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
            }
            return callSiteArray.array;
        }
    }

    @CommandLine.Command(name="uninstall", description={"Uninstalls a particular grape (non-transitively removes the respective jar file from the grape cache)."})
    private static class Uninstall
    implements Runnable,
    GroovyObject {
        @CommandLine.Parameters(arity="1", index="0", description={"Which module group the module comes from. Translates directly to a Maven groupId or an Ivy Organization. Any group matching /groovy[x][\\..*]^/ is reserved and may have special meaning to the groovy endorsed modules."})
        private String group;
        @CommandLine.Parameters(arity="1", index="1", description={"The name of the module to load. Translated directly to a Maven artifactId or an Ivy artifact."})
        private String module;
        @CommandLine.Parameters(arity="1", index="2", description={"The version of the module to use. Either a literal version `1.1-RC3` or an Ivy Range `[2.2.1,)` meaning 2.2.1 or any greater version)."})
        private String version;
        @CommandLine.ParentCommand
        private GrapeMain parentCommand;
        private static /* synthetic */ ClassInfo $staticClassInfo;
        public static transient /* synthetic */ boolean __$stMC;
        private transient /* synthetic */ MetaClass metaClass;
        private static /* synthetic */ SoftReference $callSiteArray;

        @Generated
        public Uninstall() {
            MetaClass metaClass;
            CallSite[] callSiteArray = Uninstall.$getCallSiteArray();
            this.metaClass = metaClass = this.$getStaticMetaClass();
        }

        @Override
        public void run() {
            CallSite[] callSiteArray = Uninstall.$getCallSiteArray();
            callSiteArray[0].call(this.parentCommand);
            callSiteArray[1].callGetProperty(Grape.class);
            callSiteArray[2].call(this.parentCommand);
            public final class _run_closure1
            extends Closure
            implements GeneratedClosure {
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;
                private static /* synthetic */ SoftReference $callSiteArray;

                public _run_closure1(Object _outerInstance, Object _thisObject) {
                    CallSite[] callSiteArray = _run_closure1.$getCallSiteArray();
                    super(_outerInstance, _thisObject);
                }

                public Object doCall(String groupName, Map g) {
                    Reference<String> groupName2 = new Reference<String>(groupName);
                    CallSite[] callSiteArray = _run_closure1.$getCallSiteArray();
                    public final class _closure4
                    extends Closure
                    implements GeneratedClosure {
                        private /* synthetic */ Reference groupName;
                        private static /* synthetic */ ClassInfo $staticClassInfo;
                        public static transient /* synthetic */ boolean __$stMC;
                        private static /* synthetic */ SoftReference $callSiteArray;

                        public _closure4(Object _outerInstance, Object _thisObject, Reference groupName) {
                            Reference reference;
                            CallSite[] callSiteArray = _closure4.$getCallSiteArray();
                            super(_outerInstance, _thisObject);
                            this.groupName = reference = groupName;
                        }

                        public Object doCall(String moduleName, List<String> versions) {
                            CallSite[] callSiteArray = _closure4.$getCallSiteArray();
                            if (!BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                                return ScriptBytecodeAdapter.compareEqual(callSiteArray[0].callGroovyObjectGetProperty(this), this.groupName.get()) && ScriptBytecodeAdapter.compareEqual(callSiteArray[1].callGroovyObjectGetProperty(this), moduleName) && ScriptBytecodeAdapter.isCase(callSiteArray[2].callGroovyObjectGetProperty(this), versions);
                            }
                            return ScriptBytecodeAdapter.compareEqual(callSiteArray[3].callGroovyObjectGetProperty(this), this.groupName.get()) && ScriptBytecodeAdapter.compareEqual(callSiteArray[4].callGroovyObjectGetProperty(this), moduleName) && ScriptBytecodeAdapter.isCase(callSiteArray[5].callGroovyObjectGetProperty(this), versions);
                        }

                        @Generated
                        public Object call(String moduleName, List<String> versions) {
                            CallSite[] callSiteArray = _closure4.$getCallSiteArray();
                            return callSiteArray[6].callCurrent(this, moduleName, versions);
                        }

                        @Generated
                        public String getGroupName() {
                            CallSite[] callSiteArray = _closure4.$getCallSiteArray();
                            return ShortTypeHandling.castToString(this.groupName.get());
                        }

                        protected /* synthetic */ MetaClass $getStaticMetaClass() {
                            if (this.getClass() != _closure4.class) {
                                return ScriptBytecodeAdapter.initMetaClass(this);
                            }
                            ClassInfo classInfo = $staticClassInfo;
                            if (classInfo == null) {
                                $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
                            }
                            return classInfo.getMetaClass();
                        }

                        public /* synthetic */ MethodHandles.Lookup $getLookup() {
                            return MethodHandles.lookup();
                        }

                        private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
                            stringArray[0] = "group";
                            stringArray[1] = "module";
                            stringArray[2] = "version";
                            stringArray[3] = "group";
                            stringArray[4] = "module";
                            stringArray[5] = "version";
                            stringArray[6] = "doCall";
                        }

                        private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                            String[] stringArray = new String[7];
                            _closure4.$createCallSiteArray_1(stringArray);
                            return new CallSiteArray(_closure4.class, stringArray);
                        }

                        private static /* synthetic */ CallSite[] $getCallSiteArray() {
                            CallSiteArray callSiteArray;
                            if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                                callSiteArray = _closure4.$createCallSiteArray();
                                $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                            }
                            return callSiteArray.array;
                        }
                    }
                    return callSiteArray[0].call((Object)g, new _closure4(this, this.getThisObject(), groupName2));
                }

                @Generated
                public Object call(String groupName, Map g) {
                    Reference<String> groupName2 = new Reference<String>(groupName);
                    CallSite[] callSiteArray = _run_closure1.$getCallSiteArray();
                    return callSiteArray[1].callCurrent(this, groupName2.get(), g);
                }

                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                    if (this.getClass() != _run_closure1.class) {
                        return ScriptBytecodeAdapter.initMetaClass(this);
                    }
                    ClassInfo classInfo = $staticClassInfo;
                    if (classInfo == null) {
                        $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
                    }
                    return classInfo.getMetaClass();
                }

                public /* synthetic */ MethodHandles.Lookup $getLookup() {
                    return MethodHandles.lookup();
                }

                private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
                    stringArray[0] = "any";
                    stringArray[1] = "doCall";
                }

                private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                    String[] stringArray = new String[2];
                    _run_closure1.$createCallSiteArray_1(stringArray);
                    return new CallSiteArray(_run_closure1.class, stringArray);
                }

                private static /* synthetic */ CallSite[] $getCallSiteArray() {
                    CallSiteArray callSiteArray;
                    if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                        callSiteArray = _run_closure1.$createCallSiteArray();
                        $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                    }
                    return callSiteArray.array;
                }
            }
            if (!DefaultTypeTransformation.booleanUnbox(callSiteArray[3].call(callSiteArray[4].call(Grape.class), new _run_closure1(this, this)))) {
                callSiteArray[5].callCurrent((GroovyObject)this, new GStringImpl(new Object[]{this.group, this.module, this.version}, new String[]{"uninstall did not find grape matching: ", " ", " ", ""}));
                public final class _run_closure2
                extends Closure
                implements GeneratedClosure {
                    private static /* synthetic */ ClassInfo $staticClassInfo;
                    public static transient /* synthetic */ boolean __$stMC;
                    private static /* synthetic */ SoftReference $callSiteArray;

                    public _run_closure2(Object _outerInstance, Object _thisObject) {
                        CallSite[] callSiteArray = _run_closure2.$getCallSiteArray();
                        super(_outerInstance, _thisObject);
                    }

                    public Object doCall(String groupName, Map g) {
                        Reference<String> groupName2 = new Reference<String>(groupName);
                        CallSite[] callSiteArray = _run_closure2.$getCallSiteArray();
                        public final class _closure5
                        extends Closure
                        implements GeneratedClosure {
                            private /* synthetic */ Reference groupName;
                            private static /* synthetic */ ClassInfo $staticClassInfo;
                            public static transient /* synthetic */ boolean __$stMC;
                            private static /* synthetic */ SoftReference $callSiteArray;

                            public _closure5(Object _outerInstance, Object _thisObject, Reference groupName) {
                                Reference reference;
                                CallSite[] callSiteArray = _closure5.$getCallSiteArray();
                                super(_outerInstance, _thisObject);
                                this.groupName = reference = groupName;
                            }

                            public Object doCall(String moduleName, List<String> versions) {
                                CallSite[] callSiteArray = _closure5.$getCallSiteArray();
                                return DefaultTypeTransformation.booleanUnbox(callSiteArray[0].call(this.groupName.get(), callSiteArray[1].callGroovyObjectGetProperty(this))) || DefaultTypeTransformation.booleanUnbox(callSiteArray[2].call((Object)moduleName, callSiteArray[3].callGroovyObjectGetProperty(this))) || DefaultTypeTransformation.booleanUnbox(callSiteArray[4].call(callSiteArray[5].callGroovyObjectGetProperty(this), this.groupName.get())) || DefaultTypeTransformation.booleanUnbox(callSiteArray[6].call(callSiteArray[7].callGroovyObjectGetProperty(this), moduleName));
                            }

                            @Generated
                            public Object call(String moduleName, List<String> versions) {
                                CallSite[] callSiteArray = _closure5.$getCallSiteArray();
                                return callSiteArray[8].callCurrent(this, moduleName, versions);
                            }

                            @Generated
                            public String getGroupName() {
                                CallSite[] callSiteArray = _closure5.$getCallSiteArray();
                                return ShortTypeHandling.castToString(this.groupName.get());
                            }

                            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                                if (this.getClass() != _closure5.class) {
                                    return ScriptBytecodeAdapter.initMetaClass(this);
                                }
                                ClassInfo classInfo = $staticClassInfo;
                                if (classInfo == null) {
                                    $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
                                }
                                return classInfo.getMetaClass();
                            }

                            public /* synthetic */ MethodHandles.Lookup $getLookup() {
                                return MethodHandles.lookup();
                            }

                            private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
                                stringArray[0] = "contains";
                                stringArray[1] = "group";
                                stringArray[2] = "contains";
                                stringArray[3] = "module";
                                stringArray[4] = "contains";
                                stringArray[5] = "group";
                                stringArray[6] = "contains";
                                stringArray[7] = "module";
                                stringArray[8] = "doCall";
                            }

                            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                                String[] stringArray = new String[9];
                                _closure5.$createCallSiteArray_1(stringArray);
                                return new CallSiteArray(_closure5.class, stringArray);
                            }

                            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                                CallSiteArray callSiteArray;
                                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                                    callSiteArray = _closure5.$createCallSiteArray();
                                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                                }
                                return callSiteArray.array;
                            }
                        }
                        return callSiteArray[0].call((Object)g, new _closure5(this, this.getThisObject(), groupName2));
                    }

                    @Generated
                    public Object call(String groupName, Map g) {
                        Reference<String> groupName2 = new Reference<String>(groupName);
                        CallSite[] callSiteArray = _run_closure2.$getCallSiteArray();
                        return callSiteArray[1].callCurrent(this, groupName2.get(), g);
                    }

                    protected /* synthetic */ MetaClass $getStaticMetaClass() {
                        if (this.getClass() != _run_closure2.class) {
                            return ScriptBytecodeAdapter.initMetaClass(this);
                        }
                        ClassInfo classInfo = $staticClassInfo;
                        if (classInfo == null) {
                            $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
                        }
                        return classInfo.getMetaClass();
                    }

                    public /* synthetic */ MethodHandles.Lookup $getLookup() {
                        return MethodHandles.lookup();
                    }

                    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
                        stringArray[0] = "any";
                        stringArray[1] = "doCall";
                    }

                    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                        String[] stringArray = new String[2];
                        _run_closure2.$createCallSiteArray_1(stringArray);
                        return new CallSiteArray(_run_closure2.class, stringArray);
                    }

                    private static /* synthetic */ CallSite[] $getCallSiteArray() {
                        CallSiteArray callSiteArray;
                        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                            callSiteArray = _run_closure2.$createCallSiteArray();
                            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                        }
                        return callSiteArray.array;
                    }
                }
                Object fuzzyMatches = callSiteArray[6].call(callSiteArray[7].call(Grape.class), new _run_closure2(this, this));
                if (DefaultTypeTransformation.booleanUnbox(fuzzyMatches)) {
                    callSiteArray[8].callCurrent((GroovyObject)this, "possible matches:");
                    public final class _run_closure3
                    extends Closure
                    implements GeneratedClosure {
                        private static /* synthetic */ ClassInfo $staticClassInfo;
                        public static transient /* synthetic */ boolean __$stMC;
                        private static /* synthetic */ SoftReference $callSiteArray;

                        public _run_closure3(Object _outerInstance, Object _thisObject) {
                            CallSite[] callSiteArray = _run_closure3.$getCallSiteArray();
                            super(_outerInstance, _thisObject);
                        }

                        public Object doCall(String groupName, Map g) {
                            CallSite[] callSiteArray = _run_closure3.$getCallSiteArray();
                            return callSiteArray[0].callCurrent((GroovyObject)this, new GStringImpl(new Object[]{groupName, g}, new String[]{"    ", ": ", ""}));
                        }

                        @Generated
                        public Object call(String groupName, Map g) {
                            CallSite[] callSiteArray = _run_closure3.$getCallSiteArray();
                            return callSiteArray[1].callCurrent(this, groupName, g);
                        }

                        protected /* synthetic */ MetaClass $getStaticMetaClass() {
                            if (this.getClass() != _run_closure3.class) {
                                return ScriptBytecodeAdapter.initMetaClass(this);
                            }
                            ClassInfo classInfo = $staticClassInfo;
                            if (classInfo == null) {
                                $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
                            }
                            return classInfo.getMetaClass();
                        }

                        public /* synthetic */ MethodHandles.Lookup $getLookup() {
                            return MethodHandles.lookup();
                        }

                        private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
                            stringArray[0] = "println";
                            stringArray[1] = "doCall";
                        }

                        private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                            String[] stringArray = new String[2];
                            _run_closure3.$createCallSiteArray_1(stringArray);
                            return new CallSiteArray(_run_closure3.class, stringArray);
                        }

                        private static /* synthetic */ CallSite[] $getCallSiteArray() {
                            CallSiteArray callSiteArray;
                            if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                                callSiteArray = _run_closure3.$createCallSiteArray();
                                $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                            }
                            return callSiteArray.array;
                        }
                    }
                    callSiteArray[9].call(fuzzyMatches, new _run_closure3(this, this));
                }
                return;
            }
            callSiteArray[10].call(callSiteArray[11].callGetProperty(Grape.class), this.group, this.module, this.version);
        }

        public /* synthetic */ Object methodMissing(String name, Object args) {
            CallSite[] callSiteArray = Uninstall.$getCallSiteArray();
            if (!(args instanceof Object[])) {
                return ScriptBytecodeAdapter.invokeMethodN(Uninstall.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{args});
            }
            if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                if (ScriptBytecodeAdapter.compareEqual(callSiteArray[12].callGetProperty((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 1)) {
                    return ScriptBytecodeAdapter.invokeMethodN(Uninstall.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{callSiteArray[13].call((Object)((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 0)});
                }
            } else if (ScriptBytecodeAdapter.compareEqual(callSiteArray[14].callGetProperty((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 1)) {
                return ScriptBytecodeAdapter.invokeMethodN(Uninstall.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{BytecodeInterface8.objectArrayGet((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class), 0)});
            }
            return ScriptBytecodeAdapter.invokeMethodN(Uninstall.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), ScriptBytecodeAdapter.despreadList(new Object[0], new Object[]{args}, new int[]{0}));
        }

        public static /* synthetic */ Object $static_methodMissing(String name, Object args) {
            CallSite[] callSiteArray = Uninstall.$getCallSiteArray();
            if (!(args instanceof Object[])) {
                return ScriptBytecodeAdapter.invokeMethodN(Uninstall.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{args});
            }
            if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                if (ScriptBytecodeAdapter.compareEqual(callSiteArray[15].callGetProperty((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 1)) {
                    return ScriptBytecodeAdapter.invokeMethodN(Uninstall.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{callSiteArray[16].call((Object)((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 0)});
                }
            } else if (ScriptBytecodeAdapter.compareEqual(callSiteArray[17].callGetProperty((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 1)) {
                return ScriptBytecodeAdapter.invokeMethodN(Uninstall.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{BytecodeInterface8.objectArrayGet((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class), 0)});
            }
            return ScriptBytecodeAdapter.invokeMethodN(Uninstall.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), ScriptBytecodeAdapter.despreadList(new Object[0], new Object[]{args}, new int[]{0}));
        }

        public /* synthetic */ void propertyMissing(String name, Object value) {
            CallSite[] callSiteArray = Uninstall.$getCallSiteArray();
            Object object = value;
            ScriptBytecodeAdapter.setProperty(object, null, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

        public static /* synthetic */ void $static_propertyMissing(String name, Object value) {
            CallSite[] callSiteArray = Uninstall.$getCallSiteArray();
            Object object = value;
            ScriptBytecodeAdapter.setProperty(object, null, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

        public /* synthetic */ Object propertyMissing(String name) {
            CallSite[] callSiteArray = Uninstall.$getCallSiteArray();
            return ScriptBytecodeAdapter.getProperty(Uninstall.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

        public static /* synthetic */ Object $static_propertyMissing(String name) {
            CallSite[] callSiteArray = Uninstall.$getCallSiteArray();
            return ScriptBytecodeAdapter.getProperty(Uninstall.class, GrapeMain.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

        protected /* synthetic */ MetaClass $getStaticMetaClass() {
            if (this.getClass() != Uninstall.class) {
                return ScriptBytecodeAdapter.initMetaClass(this);
            }
            ClassInfo classInfo = $staticClassInfo;
            if (classInfo == null) {
                $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
            }
            return classInfo.getMetaClass();
        }

        @Override
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

        @Override
        @Generated
        @Internal
        public void setMetaClass(MetaClass metaClass) {
            this.metaClass = metaClass;
        }

        public static /* synthetic */ MethodHandles.Lookup $getLookup() {
            return MethodHandles.lookup();
        }

        @Generated
        public String getGroup() {
            return this.group;
        }

        @Generated
        public void setGroup(String string) {
            this.group = string;
        }

        @Generated
        public String getModule() {
            return this.module;
        }

        @Generated
        public void setModule(String string) {
            this.module = string;
        }

        @Generated
        public String getVersion() {
            return this.version;
        }

        @Generated
        public void setVersion(String string) {
            this.version = string;
        }

        @Generated
        public GrapeMain getParentCommand() {
            return this.parentCommand;
        }

        @Generated
        public void setParentCommand(GrapeMain grapeMain) {
            this.parentCommand = grapeMain;
        }

        private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
            stringArray[0] = "init";
            stringArray[1] = "instance";
            stringArray[2] = "setupLogging";
            stringArray[3] = "find";
            stringArray[4] = "enumerateGrapes";
            stringArray[5] = "println";
            stringArray[6] = "findAll";
            stringArray[7] = "enumerateGrapes";
            stringArray[8] = "println";
            stringArray[9] = "each";
            stringArray[10] = "uninstallArtifact";
            stringArray[11] = "instance";
            stringArray[12] = "length";
            stringArray[13] = "getAt";
            stringArray[14] = "length";
            stringArray[15] = "length";
            stringArray[16] = "getAt";
            stringArray[17] = "length";
        }

        private static /* synthetic */ CallSiteArray $createCallSiteArray() {
            String[] stringArray = new String[18];
            Uninstall.$createCallSiteArray_1(stringArray);
            return new CallSiteArray(Uninstall.class, stringArray);
        }

        private static /* synthetic */ CallSite[] $getCallSiteArray() {
            CallSiteArray callSiteArray;
            if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                callSiteArray = Uninstall.$createCallSiteArray();
                $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
            }
            return callSiteArray.array;
        }
    }
}

