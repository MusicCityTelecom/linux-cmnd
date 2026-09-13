/*
 * Decompiled with CFR 0.152.
 */
package groovy.cli.internal;

import groovy.cli.CliBuilderException;
import groovy.cli.TypedOption;
import groovy.cli.internal.OptionAccessor;
import groovy.lang.Closure;
import groovy.lang.GString;
import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import groovy.lang.Reference;
import groovy.transform.Generated;
import groovy.transform.Internal;
import groovyjarjarpicocli.CommandLine;
import java.beans.Transient;
import java.io.PrintWriter;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.BytecodeInterface8;
import org.codehaus.groovy.runtime.GStringImpl;
import org.codehaus.groovy.runtime.GeneratedClosure;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;
import org.codehaus.groovy.runtime.typehandling.ShortTypeHandling;

public class CliBuilderInternal
implements GroovyObject {
    private String usage;
    private String name;
    private Boolean posix;
    private boolean expandArgumentFiles;
    private boolean stopAtNonOption;
    private boolean acceptLongOptionsWithSingleHyphen;
    private PrintWriter writer;
    private PrintWriter errorWriter;
    private String header;
    private String footer;
    private int width;
    private final CommandLine.Model.ParserSpec parser;
    private final CommandLine.Model.UsageMessageSpec usageMessage;
    private Map<String, TypedOption> savedTypeOptions;
    private CommandLine.Model.CommandSpec commandSpec;
    private static final int COMMONS_CLI_UNLIMITED_VALUES = -2;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private transient /* synthetic */ MetaClass metaClass;
    private static /* synthetic */ SoftReference $callSiteArray;

    @Generated
    public CliBuilderInternal() {
        MetaClass metaClass;
        boolean bl;
        boolean bl2;
        boolean bl3;
        String string;
        String string2;
        CallSite[] callSiteArray = CliBuilderInternal.$getCallSiteArray();
        this.usage = string2 = "groovy";
        this.name = string = "groovy";
        boolean bl4 = true;
        this.posix = bl4;
        this.expandArgumentFiles = bl3 = true;
        this.stopAtNonOption = bl2 = true;
        this.acceptLongOptionsWithSingleHyphen = bl = false;
        Object object = callSiteArray[0].callConstructor(PrintWriter.class, callSiteArray[1].callGetProperty(System.class));
        this.writer = (PrintWriter)ScriptBytecodeAdapter.castToType(object, PrintWriter.class);
        Object object2 = callSiteArray[2].callConstructor(PrintWriter.class, callSiteArray[3].callGetProperty(System.class));
        this.errorWriter = (PrintWriter)ScriptBytecodeAdapter.castToType(object2, PrintWriter.class);
        Object var10_10 = null;
        this.header = ShortTypeHandling.castToString(var10_10);
        Object var11_11 = null;
        this.footer = ShortTypeHandling.castToString(var11_11);
        Object object3 = callSiteArray[4].callGetProperty(CommandLine.Model.UsageMessageSpec.class);
        this.width = DefaultTypeTransformation.intUnbox(object3);
        Object object4 = callSiteArray[5].call(callSiteArray[6].call(callSiteArray[7].call(callSiteArray[8].call(callSiteArray[9].call(callSiteArray[10].call(callSiteArray[11].callConstructor(CommandLine.Model.ParserSpec.class), true), true), true), true), true), false);
        this.parser = (CommandLine.Model.ParserSpec)ScriptBytecodeAdapter.castToType(object4, CommandLine.Model.ParserSpec.class);
        Object object5 = callSiteArray[12].callConstructor(CommandLine.Model.UsageMessageSpec.class);
        this.usageMessage = (CommandLine.Model.UsageMessageSpec)ScriptBytecodeAdapter.castToType(object5, CommandLine.Model.UsageMessageSpec.class);
        Object object6 = callSiteArray[13].callConstructor(HashMap.class);
        this.savedTypeOptions = (Map)ScriptBytecodeAdapter.castToType(object6, Map.class);
        Object object7 = callSiteArray[14].call(CommandLine.Model.CommandSpec.class);
        this.commandSpec = (CommandLine.Model.CommandSpec)ScriptBytecodeAdapter.castToType(object7, CommandLine.Model.CommandSpec.class);
        this.metaClass = metaClass = this.$getStaticMetaClass();
    }

    public void setUsage(String usage) {
        String string;
        CallSite[] callSiteArray = CliBuilderInternal.$getCallSiteArray();
        this.usage = string = usage;
        callSiteArray[15].call((Object)this.usageMessage, usage);
    }

    public void setFooter(String footer) {
        String string;
        CallSite[] callSiteArray = CliBuilderInternal.$getCallSiteArray();
        this.footer = string = footer;
        callSiteArray[16].call((Object)this.usageMessage, footer);
    }

    public void setHeader(String header) {
        String string;
        CallSite[] callSiteArray = CliBuilderInternal.$getCallSiteArray();
        this.header = string = header;
        callSiteArray[17].call((Object)this.usageMessage, header);
    }

    public void setWidth(int width) {
        int n;
        CallSite[] callSiteArray = CliBuilderInternal.$getCallSiteArray();
        this.width = n = width;
        callSiteArray[18].call((Object)this.usageMessage, width);
    }

    public void setExpandArgumentFiles(boolean expand) {
        boolean bl;
        CallSite[] callSiteArray = CliBuilderInternal.$getCallSiteArray();
        this.expandArgumentFiles = bl = expand;
        callSiteArray[19].call((Object)this.parser, expand);
    }

    public void setPosix(Boolean posix) {
        Boolean bl;
        CallSite[] callSiteArray = CliBuilderInternal.$getCallSiteArray();
        this.posix = bl = posix;
        Boolean bl2 = posix;
        callSiteArray[20].call((Object)this.parser, DefaultTypeTransformation.booleanUnbox(bl2) ? bl2 : Boolean.valueOf(false));
    }

    public void setStopAtNonOption(boolean stopAtNonOption) {
        boolean bl;
        CallSite[] callSiteArray = CliBuilderInternal.$getCallSiteArray();
        this.stopAtNonOption = bl = stopAtNonOption;
        callSiteArray[21].call((Object)this.parser, stopAtNonOption);
        callSiteArray[22].call((Object)this.parser, stopAtNonOption);
    }

    public void setWriter(PrintWriter writer) {
        PrintWriter printWriter;
        PrintWriter printWriter2;
        CallSite[] callSiteArray = CliBuilderInternal.$getCallSiteArray();
        this.writer = printWriter2 = writer;
        this.errorWriter = printWriter = writer;
    }

    public <T> TypedOption<T> option(Map args, Class<T> type, String description) {
        CallSite[] callSiteArray = CliBuilderInternal.$getCallSiteArray();
        Object object = callSiteArray[23].callGetProperty(args);
        Object name = DefaultTypeTransformation.booleanUnbox(object) ? object : "_";
        Class<T> clazz = type;
        ScriptBytecodeAdapter.setProperty(clazz, null, args, "type");
        callSiteArray[24].call((Object)args, "opt");
        return (TypedOption)ScriptBytecodeAdapter.castToType(ScriptBytecodeAdapter.invokeMethodOnCurrentN(CliBuilderInternal.class, this, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{args, description}), TypedOption.class);
    }

    @Override
    public Object invokeMethod(String name, Object args) {
        CallSite[] callSiteArray = CliBuilderInternal.$getCallSiteArray();
        if (args instanceof Object[]) {
            if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                if (ScriptBytecodeAdapter.compareEqual(callSiteArray[25].call(args), 1) && (callSiteArray[26].call(args, 0) instanceof String || callSiteArray[27].call(args, 0) instanceof GString)) {
                    Object option = callSiteArray[28].callCurrent(this, name, ScriptBytecodeAdapter.createMap(new Object[0]), callSiteArray[29].call(args, 0));
                    callSiteArray[30].call((Object)this.commandSpec, option);
                    return callSiteArray[31].callCurrent(this, option, null, null, null);
                }
            } else if (ScriptBytecodeAdapter.compareEqual(callSiteArray[32].call(args), 1) && (callSiteArray[33].call(args, 0) instanceof String || callSiteArray[34].call(args, 0) instanceof GString)) {
                Object option = callSiteArray[35].callCurrent(this, name, ScriptBytecodeAdapter.createMap(new Object[0]), callSiteArray[36].call(args, 0));
                callSiteArray[37].call((Object)this.commandSpec, option);
                return callSiteArray[38].callCurrent(this, option, null, null, null);
            }
            if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                if (ScriptBytecodeAdapter.compareEqual(callSiteArray[39].call(args), 1) && callSiteArray[40].call(args, 0) instanceof CommandLine.Model.OptionSpec && ScriptBytecodeAdapter.compareEqual(name, "leftShift")) {
                    CommandLine.Model.OptionSpec option = (CommandLine.Model.OptionSpec)ScriptBytecodeAdapter.asType(callSiteArray[41].call(args, 0), CommandLine.Model.OptionSpec.class);
                    callSiteArray[42].call((Object)this.commandSpec, option);
                    return callSiteArray[43].callCurrent(this, option, null, null, null);
                }
            } else if (ScriptBytecodeAdapter.compareEqual(callSiteArray[44].call(args), 1) && callSiteArray[45].call(args, 0) instanceof CommandLine.Model.OptionSpec && ScriptBytecodeAdapter.compareEqual(name, "leftShift")) {
                CommandLine.Model.OptionSpec option = (CommandLine.Model.OptionSpec)ScriptBytecodeAdapter.asType(callSiteArray[46].call(args, 0), CommandLine.Model.OptionSpec.class);
                callSiteArray[47].call((Object)this.commandSpec, option);
                return callSiteArray[48].callCurrent(this, option, null, null, null);
            }
            if (ScriptBytecodeAdapter.compareEqual(callSiteArray[49].call(args), 2) && callSiteArray[50].call(args, 0) instanceof Map) {
                Map m = (Map)ScriptBytecodeAdapter.asType(callSiteArray[51].call(args, 0), Map.class);
                if (DefaultTypeTransformation.booleanUnbox(callSiteArray[52].callGetProperty(m)) && !(callSiteArray[53].callGetProperty(m) instanceof Class)) {
                    throw (Throwable)callSiteArray[54].callConstructor(CliBuilderException.class, "'type' must be a Class");
                }
                Object option = callSiteArray[55].callCurrent(this, name, m, callSiteArray[56].call(args, 1));
                callSiteArray[57].call((Object)this.commandSpec, option);
                return callSiteArray[58].callCurrent(this, option, callSiteArray[59].callGetProperty(m), callSiteArray[60].call(option), callSiteArray[61].call(option));
            }
        }
        return callSiteArray[62].call(callSiteArray[63].call(InvokerHelper.class, this), this, name, args);
    }

    private TypedOption create(CommandLine.Model.OptionSpec o, Class theType, Object defaultValue, Object convert) {
        CallSite[] callSiteArray = CliBuilderInternal.$getCallSiteArray();
        public final class _create_closure1
        extends Closure
        implements GeneratedClosure {
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _create_closure1(Object _outerInstance, Object _thisObject) {
                CallSite[] callSiteArray = _create_closure1.$getCallSiteArray();
                super(_outerInstance, _thisObject);
            }

            public Object doCall(Object a, Object b) {
                CallSite[] callSiteArray = _create_closure1.$getCallSiteArray();
                return callSiteArray[0].call(callSiteArray[1].call(a), callSiteArray[2].call(b));
            }

            @Generated
            public Object call(Object a, Object b) {
                CallSite[] callSiteArray = _create_closure1.$getCallSiteArray();
                return callSiteArray[3].callCurrent(this, a, b);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (this.getClass() != _create_closure1.class) {
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
                stringArray[0] = "minus";
                stringArray[1] = "length";
                stringArray[2] = "length";
                stringArray[3] = "doCall";
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[4];
                _create_closure1.$createCallSiteArray_1(stringArray);
                return new CallSiteArray(_create_closure1.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _create_closure1.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        String opt = ShortTypeHandling.castToString(callSiteArray[64].call(callSiteArray[65].call(callSiteArray[66].call(o), new _create_closure1(this, this))));
        Object object = ScriptBytecodeAdapter.compareEqual(callSiteArray[67].callSafe(opt), 2) ? callSiteArray[68].call((Object)opt, 1) : null;
        opt = ShortTypeHandling.castToString(object);
        public final class _create_closure2
        extends Closure
        implements GeneratedClosure {
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _create_closure2(Object _outerInstance, Object _thisObject) {
                CallSite[] callSiteArray = _create_closure2.$getCallSiteArray();
                super(_outerInstance, _thisObject);
            }

            public Object doCall(Object a, Object b) {
                CallSite[] callSiteArray = _create_closure2.$getCallSiteArray();
                return callSiteArray[0].call(callSiteArray[1].call(b), callSiteArray[2].call(a));
            }

            @Generated
            public Object call(Object a, Object b) {
                CallSite[] callSiteArray = _create_closure2.$getCallSiteArray();
                return callSiteArray[3].callCurrent(this, a, b);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (this.getClass() != _create_closure2.class) {
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
                stringArray[0] = "minus";
                stringArray[1] = "length";
                stringArray[2] = "length";
                stringArray[3] = "doCall";
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[4];
                _create_closure2.$createCallSiteArray_1(stringArray);
                return new CallSiteArray(_create_closure2.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _create_closure2.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        String longOpt = ShortTypeHandling.castToString(callSiteArray[69].call(callSiteArray[70].call(callSiteArray[71].call(o), new _create_closure2(this, this))));
        Object object2 = DefaultTypeTransformation.booleanUnbox(callSiteArray[72].callSafe((Object)longOpt, "--")) ? callSiteArray[73].call((Object)longOpt, 2) : null;
        longOpt = ShortTypeHandling.castToString(object2);
        Map result = (Map)ScriptBytecodeAdapter.castToType(callSiteArray[74].callConstructor(TypedOption.class), Map.class);
        if (ScriptBytecodeAdapter.compareNotEqual(opt, null)) {
            callSiteArray[75].call(result, "opt", opt);
        }
        callSiteArray[76].call(result, "longOpt", longOpt);
        callSiteArray[77].call(result, "cliOption", o);
        if (DefaultTypeTransformation.booleanUnbox(defaultValue)) {
            callSiteArray[78].call(result, "defaultValue", defaultValue);
        }
        if (DefaultTypeTransformation.booleanUnbox(convert)) {
            if (DefaultTypeTransformation.booleanUnbox(theType)) {
                throw (Throwable)callSiteArray[79].callConstructor(CliBuilderException.class, "You can't specify 'type' when using 'convert'");
            }
            callSiteArray[80].call(result, "convert", convert);
            callSiteArray[81].call(result, "type", convert instanceof Class ? convert : callSiteArray[82].call(convert));
        } else {
            callSiteArray[83].call(result, "type", theType);
        }
        Map map = result;
        String string = longOpt;
        callSiteArray[84].call(this.savedTypeOptions, DefaultTypeTransformation.booleanUnbox(string) ? string : opt, map);
        return (TypedOption)ScriptBytecodeAdapter.castToType(result, TypedOption.class);
    }

    public OptionAccessor parse(Object args) {
        CallSite[] callSiteArray = CliBuilderInternal.$getCallSiteArray();
        CommandLine commandLine = null;
        if (__$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            Object object = callSiteArray[85].callCurrent(this);
            commandLine = (CommandLine)ScriptBytecodeAdapter.castToType(object, CommandLine.class);
        } else {
            CommandLine commandLine2;
            commandLine = commandLine2 = this.createCommandLine();
        }
        Object accessor = callSiteArray[86].callConstructor(OptionAccessor.class, callSiteArray[87].call((Object)commandLine, ScriptBytecodeAdapter.createPojoWrapper((String[])ScriptBytecodeAdapter.asType(args, String[].class), String[].class)));
        Map<String, TypedOption> map = this.savedTypeOptions;
        ScriptBytecodeAdapter.setProperty(map, null, accessor, "savedTypeOptions");
        OptionAccessor optionAccessor = (OptionAccessor)ScriptBytecodeAdapter.castToType(accessor, OptionAccessor.class);
        try {
            return optionAccessor;
        }
        catch (CommandLine.ParameterException pe) {
            callSiteArray[88].call((Object)this.errorWriter, callSiteArray[89].call((Object)"error: ", callSiteArray[90].callGetProperty(pe)));
            callSiteArray[91].callCurrent(this, callSiteArray[92].callGetProperty(pe), this.errorWriter);
            OptionAccessor optionAccessor2 = (OptionAccessor)ScriptBytecodeAdapter.castToType(null, OptionAccessor.class);
            return optionAccessor2;
        }
    }

    private CommandLine createCommandLine() {
        CallSite[] callSiteArray = CliBuilderInternal.$getCallSiteArray();
        callSiteArray[93].call((Object)this.commandSpec, this.parser);
        callSiteArray[94].call(callSiteArray[95].call((Object)this.commandSpec, this.name), this.usageMessage);
        if (DefaultTypeTransformation.booleanUnbox(callSiteArray[96].callGetProperty(callSiteArray[97].call(this.commandSpec)))) {
            callSiteArray[98].call((Object)this.commandSpec, callSiteArray[99].call(callSiteArray[100].call(callSiteArray[101].call(callSiteArray[102].call(callSiteArray[103].call(callSiteArray[104].call(CommandLine.Model.PositionalParamSpec.class), String[].class), "*"), "P"), true)));
        }
        return (CommandLine)ScriptBytecodeAdapter.castToType(callSiteArray[105].callConstructor(CommandLine.class, this.commandSpec), CommandLine.class);
    }

    public void usage() {
        CallSite[] callSiteArray = CliBuilderInternal.$getCallSiteArray();
        if (__$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            Object object = callSiteArray[107].call(this.commandSpec);
            callSiteArray[106].callCurrent(this, DefaultTypeTransformation.booleanUnbox(object) ? object : callSiteArray[108].callCurrent(this), this.writer);
        } else {
            Object object = callSiteArray[110].call(this.commandSpec);
            callSiteArray[109].callCurrent(this, DefaultTypeTransformation.booleanUnbox(object) ? object : this.createCommandLine(), this.writer);
        }
    }

    private void printUsage(CommandLine commandLine, PrintWriter pw) {
        CallSite[] callSiteArray = CliBuilderInternal.$getCallSiteArray();
        callSiteArray[111].call((Object)commandLine, pw);
        callSiteArray[112].call(pw);
    }

    public CommandLine.Model.OptionSpec option(Object shortname, Map details, Object description) {
        CallSite[] callSiteArray = CliBuilderInternal.$getCallSiteArray();
        Reference<Object> builder = new Reference<Object>(null);
        CommandLine.Model.OptionSpec.Builder cfr_ignored_0 = builder.get();
        if (ScriptBytecodeAdapter.compareEqual(shortname, "_")) {
            Object object = callSiteArray[113].call(callSiteArray[114].call(CommandLine.Model.OptionSpec.class, new GStringImpl(new Object[]{callSiteArray[115].callGetProperty(details)}, new String[]{"--", ""})), description);
            builder.set(((CommandLine.Model.OptionSpec.Builder)ScriptBytecodeAdapter.castToType(object, CommandLine.Model.OptionSpec.Builder.class)));
            if (this.acceptLongOptionsWithSingleHyphen) {
                callSiteArray[116].call(builder.get(), new GStringImpl(new Object[]{callSiteArray[117].callGetProperty(details)}, new String[]{"-", ""}), new GStringImpl(new Object[]{callSiteArray[118].callGetProperty(details)}, new String[]{"--", ""}));
            }
            callSiteArray[119].call((Object)details, "longOpt");
        } else {
            Object object = callSiteArray[120].call(callSiteArray[121].call(CommandLine.Model.OptionSpec.class, new GStringImpl(new Object[]{shortname}, new String[]{"-", ""})), description);
            builder.set(((CommandLine.Model.OptionSpec.Builder)ScriptBytecodeAdapter.castToType(object, CommandLine.Model.OptionSpec.Builder.class)));
        }
        public final class _option_closure3
        extends Closure
        implements GeneratedClosure {
            private /* synthetic */ Reference builder;
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _option_closure3(Object _outerInstance, Object _thisObject, Reference builder) {
                Reference reference;
                CallSite[] callSiteArray = _option_closure3.$getCallSiteArray();
                super(_outerInstance, _thisObject);
                this.builder = reference = builder;
            }

            public Object doCall(Object key, Object value) {
                CallSite[] callSiteArray = _option_closure3.$getCallSiteArray();
                if (DefaultTypeTransformation.booleanUnbox(callSiteArray[0].call(this.builder.get(), key))) {
                    Object object = value;
                    callSiteArray[1].call(this.builder.get(), key, object);
                    return object;
                }
                if (ScriptBytecodeAdapter.compareNotEqual(key, "opt")) {
                    return callSiteArray[2].call(this.builder.get(), key, value);
                }
                return null;
            }

            @Generated
            public Object call(Object key, Object value) {
                CallSite[] callSiteArray = _option_closure3.$getCallSiteArray();
                return callSiteArray[3].callCurrent(this, key, value);
            }

            @Generated
            public CommandLine.Model.OptionSpec.Builder getBuilder() {
                CallSite[] callSiteArray = _option_closure3.$getCallSiteArray();
                return (CommandLine.Model.OptionSpec.Builder)ScriptBytecodeAdapter.castToType(this.builder.get(), CommandLine.Model.OptionSpec.Builder.class);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (this.getClass() != _option_closure3.class) {
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
                stringArray[0] = "hasProperty";
                stringArray[1] = "putAt";
                stringArray[2] = "invokeMethod";
                stringArray[3] = "doCall";
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[4];
                _option_closure3.$createCallSiteArray_1(stringArray);
                return new CallSiteArray(_option_closure3.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _option_closure3.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        callSiteArray[122].call(callSiteArray[123].callCurrent(this, shortname, details), new _option_closure3(this, this, builder));
        if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            if (!DefaultTypeTransformation.booleanUnbox(callSiteArray[124].call(builder.get())) && !DefaultTypeTransformation.booleanUnbox(callSiteArray[125].call(builder.get())) && ScriptBytecodeAdapter.compareGreaterThan(callSiteArray[126].callGetPropertySafe(callSiteArray[127].call(builder.get())), 0)) {
                callSiteArray[128].call(callSiteArray[129].call((Object)builder.get(), "1"), DefaultTypeTransformation.booleanUnbox(callSiteArray[130].callGetProperty(details)) ? Object.class : String[].class);
            }
        } else if (!DefaultTypeTransformation.booleanUnbox(callSiteArray[131].call(builder.get())) && !DefaultTypeTransformation.booleanUnbox(callSiteArray[132].call(builder.get())) && ScriptBytecodeAdapter.compareGreaterThan(callSiteArray[133].callGetPropertySafe(callSiteArray[134].call(builder.get())), 0)) {
            callSiteArray[135].call(callSiteArray[136].call((Object)builder.get(), "1"), DefaultTypeTransformation.booleanUnbox(callSiteArray[137].callGetProperty(details)) ? Object.class : String[].class);
        }
        return (CommandLine.Model.OptionSpec)ScriptBytecodeAdapter.castToType(callSiteArray[138].call(builder.get()), CommandLine.Model.OptionSpec.class);
    }

    private Map commons2picocli(Object shortname, Map m) {
        Reference<Object> shortname2 = new Reference<Object>(shortname);
        CallSite[] callSiteArray = CliBuilderInternal.$getCallSiteArray();
        if (DefaultTypeTransformation.booleanUnbox(callSiteArray[139].callGetProperty(m)) && DefaultTypeTransformation.booleanUnbox(callSiteArray[140].callGetProperty(m))) {
            GStringImpl gStringImpl = new GStringImpl(new Object[]{callSiteArray[141].callGetProperty(m)}, new String[]{"0..", ""});
            ScriptBytecodeAdapter.setProperty(gStringImpl, null, m, "arity");
            callSiteArray[142].call((Object)m, "args");
            callSiteArray[143].call((Object)m, "optionalArg");
        }
        if (!DefaultTypeTransformation.booleanUnbox(callSiteArray[144].callGetProperty(m))) {
            callSiteArray[145].call((Object)m, "defaultValue");
        }
        public final class _commons2picocli_closure4
        extends Closure
        implements GeneratedClosure {
            private /* synthetic */ Reference shortname;
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _commons2picocli_closure4(Object _outerInstance, Object _thisObject, Reference shortname) {
                Reference reference;
                CallSite[] callSiteArray = _commons2picocli_closure4.$getCallSiteArray();
                super(_outerInstance, _thisObject);
                this.shortname = reference = shortname;
            }

            public Object doCall(Object k, Object v) {
                CallSite[] callSiteArray = _commons2picocli_closure4.$getCallSiteArray();
                if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                    if (ScriptBytecodeAdapter.compareEqual(k, "args") && ScriptBytecodeAdapter.compareEqual(v, "+")) {
                        return ScriptBytecodeAdapter.createList(new Object[]{ScriptBytecodeAdapter.createMap(new Object[]{"arity", "1..*"})});
                    }
                    if (ScriptBytecodeAdapter.compareEqual(k, "args") && ScriptBytecodeAdapter.compareEqual(v, 0)) {
                        return ScriptBytecodeAdapter.createList(new Object[]{ScriptBytecodeAdapter.createMap(new Object[]{"arity", "0"})});
                    }
                    if (ScriptBytecodeAdapter.compareEqual(k, "args")) {
                        return ScriptBytecodeAdapter.compareEqual(v, callSiteArray[0].callGroovyObjectGetProperty(this)) ? ScriptBytecodeAdapter.createList(new Object[]{ScriptBytecodeAdapter.createMap(new Object[]{"arity", "*"})}) : ScriptBytecodeAdapter.createList(new Object[]{ScriptBytecodeAdapter.createMap(new Object[]{"arity", new GStringImpl(new Object[]{v}, new String[]{"", ""})})});
                    }
                    if (ScriptBytecodeAdapter.compareEqual(k, "optionalArg")) {
                        return DefaultTypeTransformation.booleanUnbox(v) ? ScriptBytecodeAdapter.createList(new Object[]{ScriptBytecodeAdapter.createMap(new Object[]{"arity", "0..1"})}) : ScriptBytecodeAdapter.createList(new Object[]{ScriptBytecodeAdapter.createMap(new Object[]{"arity", "1"})});
                    }
                    if (ScriptBytecodeAdapter.compareEqual(k, "argName")) {
                        return ScriptBytecodeAdapter.createList(new Object[]{ScriptBytecodeAdapter.createMap(new Object[]{"paramLabel", new GStringImpl(new Object[]{v}, new String[]{"<", ">"})})});
                    }
                    if (ScriptBytecodeAdapter.compareEqual(k, "longOpt")) {
                        return DefaultTypeTransformation.booleanUnbox(callSiteArray[1].callGroovyObjectGetProperty(this)) ? ScriptBytecodeAdapter.createList(new Object[]{ScriptBytecodeAdapter.createMap(new Object[]{"names", (String[])ScriptBytecodeAdapter.asType(ScriptBytecodeAdapter.createList(new Object[]{new GStringImpl(new Object[]{this.shortname.get()}, new String[]{"-", ""}), new GStringImpl(new Object[]{v}, new String[]{"-", ""}), new GStringImpl(new Object[]{v}, new String[]{"--", ""})}), String[].class)})}) : ScriptBytecodeAdapter.createList(new Object[]{ScriptBytecodeAdapter.createMap(new Object[]{"names", (String[])ScriptBytecodeAdapter.asType(ScriptBytecodeAdapter.createList(new Object[]{new GStringImpl(new Object[]{this.shortname.get()}, new String[]{"-", ""}), new GStringImpl(new Object[]{v}, new String[]{"--", ""})}), String[].class)})});
                    }
                    if (ScriptBytecodeAdapter.compareEqual(k, "valueSeparator")) {
                        return ScriptBytecodeAdapter.createList(new Object[]{ScriptBytecodeAdapter.createMap(new Object[]{"splitRegex", new GStringImpl(new Object[]{v}, new String[]{"", ""})})});
                    }
                    if (ScriptBytecodeAdapter.compareEqual(k, "convert")) {
                        return ScriptBytecodeAdapter.createList(new Object[]{ScriptBytecodeAdapter.createMap(new Object[]{"converters", (CommandLine.ITypeConverter[])ScriptBytecodeAdapter.asType(ScriptBytecodeAdapter.createList(new Object[]{v}), CommandLine.ITypeConverter[].class)})});
                    }
                    return ScriptBytecodeAdapter.createList(new Object[]{ScriptBytecodeAdapter.createMap(new Object[]{k, v})});
                }
                if (ScriptBytecodeAdapter.compareEqual(k, "args") && ScriptBytecodeAdapter.compareEqual(v, "+")) {
                    return ScriptBytecodeAdapter.createList(new Object[]{ScriptBytecodeAdapter.createMap(new Object[]{"arity", "1..*"})});
                }
                if (ScriptBytecodeAdapter.compareEqual(k, "args") && ScriptBytecodeAdapter.compareEqual(v, 0)) {
                    return ScriptBytecodeAdapter.createList(new Object[]{ScriptBytecodeAdapter.createMap(new Object[]{"arity", "0"})});
                }
                if (ScriptBytecodeAdapter.compareEqual(k, "args")) {
                    return ScriptBytecodeAdapter.compareEqual(v, callSiteArray[2].callGroovyObjectGetProperty(this)) ? ScriptBytecodeAdapter.createList(new Object[]{ScriptBytecodeAdapter.createMap(new Object[]{"arity", "*"})}) : ScriptBytecodeAdapter.createList(new Object[]{ScriptBytecodeAdapter.createMap(new Object[]{"arity", new GStringImpl(new Object[]{v}, new String[]{"", ""})})});
                }
                if (ScriptBytecodeAdapter.compareEqual(k, "optionalArg")) {
                    return DefaultTypeTransformation.booleanUnbox(v) ? ScriptBytecodeAdapter.createList(new Object[]{ScriptBytecodeAdapter.createMap(new Object[]{"arity", "0..1"})}) : ScriptBytecodeAdapter.createList(new Object[]{ScriptBytecodeAdapter.createMap(new Object[]{"arity", "1"})});
                }
                if (ScriptBytecodeAdapter.compareEqual(k, "argName")) {
                    return ScriptBytecodeAdapter.createList(new Object[]{ScriptBytecodeAdapter.createMap(new Object[]{"paramLabel", new GStringImpl(new Object[]{v}, new String[]{"<", ">"})})});
                }
                if (ScriptBytecodeAdapter.compareEqual(k, "longOpt")) {
                    return DefaultTypeTransformation.booleanUnbox(callSiteArray[3].callGroovyObjectGetProperty(this)) ? ScriptBytecodeAdapter.createList(new Object[]{ScriptBytecodeAdapter.createMap(new Object[]{"names", (String[])ScriptBytecodeAdapter.asType(ScriptBytecodeAdapter.createList(new Object[]{new GStringImpl(new Object[]{this.shortname.get()}, new String[]{"-", ""}), new GStringImpl(new Object[]{v}, new String[]{"-", ""}), new GStringImpl(new Object[]{v}, new String[]{"--", ""})}), String[].class)})}) : ScriptBytecodeAdapter.createList(new Object[]{ScriptBytecodeAdapter.createMap(new Object[]{"names", (String[])ScriptBytecodeAdapter.asType(ScriptBytecodeAdapter.createList(new Object[]{new GStringImpl(new Object[]{this.shortname.get()}, new String[]{"-", ""}), new GStringImpl(new Object[]{v}, new String[]{"--", ""})}), String[].class)})});
                }
                if (ScriptBytecodeAdapter.compareEqual(k, "valueSeparator")) {
                    return ScriptBytecodeAdapter.createList(new Object[]{ScriptBytecodeAdapter.createMap(new Object[]{"splitRegex", new GStringImpl(new Object[]{v}, new String[]{"", ""})})});
                }
                if (ScriptBytecodeAdapter.compareEqual(k, "convert")) {
                    return ScriptBytecodeAdapter.createList(new Object[]{ScriptBytecodeAdapter.createMap(new Object[]{"converters", (CommandLine.ITypeConverter[])ScriptBytecodeAdapter.asType(ScriptBytecodeAdapter.createList(new Object[]{v}), CommandLine.ITypeConverter[].class)})});
                }
                return ScriptBytecodeAdapter.createList(new Object[]{ScriptBytecodeAdapter.createMap(new Object[]{k, v})});
            }

            @Generated
            public Object call(Object k, Object v) {
                CallSite[] callSiteArray = _commons2picocli_closure4.$getCallSiteArray();
                return callSiteArray[4].callCurrent(this, k, v);
            }

            @Generated
            public Object getShortname() {
                CallSite[] callSiteArray = _commons2picocli_closure4.$getCallSiteArray();
                return this.shortname.get();
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (this.getClass() != _commons2picocli_closure4.class) {
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
                stringArray[0] = "COMMONS_CLI_UNLIMITED_VALUES";
                stringArray[1] = "acceptLongOptionsWithSingleHyphen";
                stringArray[2] = "COMMONS_CLI_UNLIMITED_VALUES";
                stringArray[3] = "acceptLongOptionsWithSingleHyphen";
                stringArray[4] = "doCall";
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[5];
                _commons2picocli_closure4.$createCallSiteArray_1(stringArray);
                return new CallSiteArray(_commons2picocli_closure4.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _commons2picocli_closure4.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        Map result = (Map)ScriptBytecodeAdapter.asType(callSiteArray[146].call(callSiteArray[147].call((Object)m, new _commons2picocli_closure4(this, this, shortname2))), Map.class);
        return (Map)ScriptBytecodeAdapter.castToType(result, Map.class);
    }

    public /* synthetic */ Object this$dist$invoke$1(String name, Object args) {
        CallSite[] callSiteArray = CliBuilderInternal.$getCallSiteArray();
        if (!(args instanceof Object[])) {
            return ScriptBytecodeAdapter.invokeMethodOnCurrentN(CliBuilderInternal.class, this, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{args});
        }
        if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            if (ScriptBytecodeAdapter.compareEqual(callSiteArray[148].callGetProperty((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 1)) {
                return ScriptBytecodeAdapter.invokeMethodOnCurrentN(CliBuilderInternal.class, this, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{callSiteArray[149].call((Object)((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 0)});
            }
        } else if (ScriptBytecodeAdapter.compareEqual(callSiteArray[150].callGetProperty((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 1)) {
            return ScriptBytecodeAdapter.invokeMethodOnCurrentN(CliBuilderInternal.class, this, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{BytecodeInterface8.objectArrayGet((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class), 0)});
        }
        return ScriptBytecodeAdapter.invokeMethodOnCurrentN(CliBuilderInternal.class, this, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), ScriptBytecodeAdapter.despreadList(new Object[0], new Object[]{args}, new int[]{0}));
    }

    public /* synthetic */ void this$dist$set$1(String name, Object value) {
        CallSite[] callSiteArray = CliBuilderInternal.$getCallSiteArray();
        Object object = value;
        ScriptBytecodeAdapter.setGroovyObjectProperty(object, CliBuilderInternal.class, this, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})));
    }

    public /* synthetic */ Object this$dist$get$1(String name) {
        CallSite[] callSiteArray = CliBuilderInternal.$getCallSiteArray();
        return ScriptBytecodeAdapter.getGroovyObjectProperty(CliBuilderInternal.class, this, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})));
    }

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != CliBuilderInternal.class) {
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
    public String getUsage() {
        return this.usage;
    }

    @Generated
    public String getName() {
        return this.name;
    }

    @Generated
    public void setName(String string) {
        this.name = string;
    }

    @Generated
    public Boolean getPosix() {
        return this.posix;
    }

    @Generated
    public boolean getExpandArgumentFiles() {
        return this.expandArgumentFiles;
    }

    @Generated
    public boolean isExpandArgumentFiles() {
        return this.expandArgumentFiles;
    }

    @Generated
    public boolean getStopAtNonOption() {
        return this.stopAtNonOption;
    }

    @Generated
    public boolean isStopAtNonOption() {
        return this.stopAtNonOption;
    }

    @Generated
    public boolean getAcceptLongOptionsWithSingleHyphen() {
        return this.acceptLongOptionsWithSingleHyphen;
    }

    @Generated
    public boolean isAcceptLongOptionsWithSingleHyphen() {
        return this.acceptLongOptionsWithSingleHyphen;
    }

    @Generated
    public void setAcceptLongOptionsWithSingleHyphen(boolean bl) {
        this.acceptLongOptionsWithSingleHyphen = bl;
    }

    @Generated
    public PrintWriter getWriter() {
        return this.writer;
    }

    @Generated
    public PrintWriter getErrorWriter() {
        return this.errorWriter;
    }

    @Generated
    public void setErrorWriter(PrintWriter printWriter) {
        this.errorWriter = printWriter;
    }

    @Generated
    public String getHeader() {
        return this.header;
    }

    @Generated
    public String getFooter() {
        return this.footer;
    }

    @Generated
    public int getWidth() {
        return this.width;
    }

    @Generated
    public final CommandLine.Model.ParserSpec getParser() {
        return this.parser;
    }

    @Generated
    public final CommandLine.Model.UsageMessageSpec getUsageMessage() {
        return this.usageMessage;
    }

    @Generated
    public Map<String, TypedOption> getSavedTypeOptions() {
        return this.savedTypeOptions;
    }

    @Generated
    public void setSavedTypeOptions(Map<String, TypedOption> map) {
        this.savedTypeOptions = map;
    }

    public /* synthetic */ Object super$1$invokeMethod(String string, Object object) {
        return GroovyObject.super.invokeMethod(string, object);
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
        stringArray[0] = "<$constructor$>";
        stringArray[1] = "out";
        stringArray[2] = "<$constructor$>";
        stringArray[3] = "err";
        stringArray[4] = "DEFAULT_USAGE_WIDTH";
        stringArray[5] = "toggleBooleanFlags";
        stringArray[6] = "overwrittenOptionsAllowed";
        stringArray[7] = "limitSplit";
        stringArray[8] = "aritySatisfiedByAttachedOptionParam";
        stringArray[9] = "unmatchedOptionsArePositionalParams";
        stringArray[10] = "stopAtPositional";
        stringArray[11] = "<$constructor$>";
        stringArray[12] = "<$constructor$>";
        stringArray[13] = "<$constructor$>";
        stringArray[14] = "create";
        stringArray[15] = "customSynopsis";
        stringArray[16] = "footer";
        stringArray[17] = "description";
        stringArray[18] = "width";
        stringArray[19] = "expandAtFiles";
        stringArray[20] = "posixClusteredShortOptionsAllowed";
        stringArray[21] = "stopAtPositional";
        stringArray[22] = "unmatchedOptionsArePositionalParams";
        stringArray[23] = "opt";
        stringArray[24] = "remove";
        stringArray[25] = "size";
        stringArray[26] = "getAt";
        stringArray[27] = "getAt";
        stringArray[28] = "option";
        stringArray[29] = "getAt";
        stringArray[30] = "addOption";
        stringArray[31] = "create";
        stringArray[32] = "size";
        stringArray[33] = "getAt";
        stringArray[34] = "getAt";
        stringArray[35] = "option";
        stringArray[36] = "getAt";
        stringArray[37] = "addOption";
        stringArray[38] = "create";
        stringArray[39] = "size";
        stringArray[40] = "getAt";
        stringArray[41] = "getAt";
        stringArray[42] = "addOption";
        stringArray[43] = "create";
        stringArray[44] = "size";
        stringArray[45] = "getAt";
        stringArray[46] = "getAt";
        stringArray[47] = "addOption";
        stringArray[48] = "create";
        stringArray[49] = "size";
        stringArray[50] = "getAt";
        stringArray[51] = "getAt";
        stringArray[52] = "type";
        stringArray[53] = "type";
        stringArray[54] = "<$constructor$>";
        stringArray[55] = "option";
        stringArray[56] = "getAt";
        stringArray[57] = "addOption";
        stringArray[58] = "create";
        stringArray[59] = "type";
        stringArray[60] = "defaultValue";
        stringArray[61] = "converters";
        stringArray[62] = "invokeMethod";
        stringArray[63] = "getMetaClass";
        stringArray[64] = "first";
        stringArray[65] = "sort";
        stringArray[66] = "names";
        stringArray[67] = "length";
        stringArray[68] = "substring";
        stringArray[69] = "first";
        stringArray[70] = "sort";
        stringArray[71] = "names";
        stringArray[72] = "startsWith";
        stringArray[73] = "substring";
        stringArray[74] = "<$constructor$>";
        stringArray[75] = "put";
        stringArray[76] = "put";
        stringArray[77] = "put";
        stringArray[78] = "put";
        stringArray[79] = "<$constructor$>";
        stringArray[80] = "put";
        stringArray[81] = "put";
        stringArray[82] = "getClass";
        stringArray[83] = "put";
        stringArray[84] = "putAt";
        stringArray[85] = "createCommandLine";
        stringArray[86] = "<$constructor$>";
        stringArray[87] = "parseArgs";
        stringArray[88] = "println";
        stringArray[89] = "plus";
        stringArray[90] = "message";
        stringArray[91] = "printUsage";
        stringArray[92] = "commandLine";
        stringArray[93] = "parser";
        stringArray[94] = "usageMessage";
        stringArray[95] = "name";
        stringArray[96] = "empty";
        stringArray[97] = "positionalParameters";
        stringArray[98] = "addPositional";
        stringArray[99] = "build";
        stringArray[100] = "hidden";
        stringArray[101] = "paramLabel";
        stringArray[102] = "arity";
        stringArray[103] = "type";
        stringArray[104] = "builder";
        stringArray[105] = "<$constructor$>";
        stringArray[106] = "printUsage";
        stringArray[107] = "commandLine";
        stringArray[108] = "createCommandLine";
        stringArray[109] = "printUsage";
        stringArray[110] = "commandLine";
        stringArray[111] = "usage";
        stringArray[112] = "flush";
        stringArray[113] = "description";
        stringArray[114] = "builder";
        stringArray[115] = "longOpt";
        stringArray[116] = "names";
        stringArray[117] = "longOpt";
        stringArray[118] = "longOpt";
        stringArray[119] = "remove";
        stringArray[120] = "description";
        stringArray[121] = "builder";
        stringArray[122] = "each";
        stringArray[123] = "commons2picocli";
        stringArray[124] = "type";
        stringArray[125] = "arity";
        stringArray[126] = "length";
        stringArray[127] = "converters";
        stringArray[128] = "type";
        stringArray[129] = "arity";
        stringArray[130] = "convert";
        stringArray[131] = "type";
        stringArray[132] = "arity";
        stringArray[133] = "length";
        stringArray[134] = "converters";
        stringArray[135] = "type";
        stringArray[136] = "arity";
        stringArray[137] = "convert";
        stringArray[138] = "build";
        stringArray[139] = "args";
        stringArray[140] = "optionalArg";
        stringArray[141] = "args";
        stringArray[142] = "remove";
        stringArray[143] = "remove";
        stringArray[144] = "defaultValue";
        stringArray[145] = "remove";
        stringArray[146] = "sum";
        stringArray[147] = "collectMany";
        stringArray[148] = "length";
        stringArray[149] = "getAt";
        stringArray[150] = "length";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[151];
        CliBuilderInternal.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(CliBuilderInternal.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = CliBuilderInternal.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }

    private static class ArgSpecAttributes
    implements GroovyObject {
        private Class type;
        private Class[] auxiliaryTypes;
        private String label;
        private CommandLine.Model.IGetter getter;
        private CommandLine.Model.ISetter setter;
        private Object initialValue;
        private boolean hasInitialValue;
        private static /* synthetic */ ClassInfo $staticClassInfo;
        public static transient /* synthetic */ boolean __$stMC;
        private transient /* synthetic */ MetaClass metaClass;
        private static /* synthetic */ SoftReference $callSiteArray;

        @Generated
        public ArgSpecAttributes() {
            MetaClass metaClass;
            CallSite[] callSiteArray = ArgSpecAttributes.$getCallSiteArray();
            this.metaClass = metaClass = this.$getStaticMetaClass();
        }

        public /* synthetic */ Object methodMissing(String name, Object args) {
            CallSite[] callSiteArray = ArgSpecAttributes.$getCallSiteArray();
            if (!(args instanceof Object[])) {
                return ScriptBytecodeAdapter.invokeMethodN(ArgSpecAttributes.class, CliBuilderInternal.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{args});
            }
            if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                if (ScriptBytecodeAdapter.compareEqual(callSiteArray[0].callGetProperty((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 1)) {
                    return ScriptBytecodeAdapter.invokeMethodN(ArgSpecAttributes.class, CliBuilderInternal.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{callSiteArray[1].call((Object)((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 0)});
                }
            } else if (ScriptBytecodeAdapter.compareEqual(callSiteArray[2].callGetProperty((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 1)) {
                return ScriptBytecodeAdapter.invokeMethodN(ArgSpecAttributes.class, CliBuilderInternal.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{BytecodeInterface8.objectArrayGet((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class), 0)});
            }
            return ScriptBytecodeAdapter.invokeMethodN(ArgSpecAttributes.class, CliBuilderInternal.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), ScriptBytecodeAdapter.despreadList(new Object[0], new Object[]{args}, new int[]{0}));
        }

        public static /* synthetic */ Object $static_methodMissing(String name, Object args) {
            CallSite[] callSiteArray = ArgSpecAttributes.$getCallSiteArray();
            if (!(args instanceof Object[])) {
                return ScriptBytecodeAdapter.invokeMethodN(ArgSpecAttributes.class, CliBuilderInternal.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{args});
            }
            if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                if (ScriptBytecodeAdapter.compareEqual(callSiteArray[3].callGetProperty((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 1)) {
                    return ScriptBytecodeAdapter.invokeMethodN(ArgSpecAttributes.class, CliBuilderInternal.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{callSiteArray[4].call((Object)((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 0)});
                }
            } else if (ScriptBytecodeAdapter.compareEqual(callSiteArray[5].callGetProperty((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)), 1)) {
                return ScriptBytecodeAdapter.invokeMethodN(ArgSpecAttributes.class, CliBuilderInternal.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{BytecodeInterface8.objectArrayGet((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class), 0)});
            }
            return ScriptBytecodeAdapter.invokeMethodN(ArgSpecAttributes.class, CliBuilderInternal.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), ScriptBytecodeAdapter.despreadList(new Object[0], new Object[]{args}, new int[]{0}));
        }

        public /* synthetic */ void propertyMissing(String name, Object value) {
            CallSite[] callSiteArray = ArgSpecAttributes.$getCallSiteArray();
            Object object = value;
            ScriptBytecodeAdapter.setProperty(object, null, CliBuilderInternal.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

        public static /* synthetic */ void $static_propertyMissing(String name, Object value) {
            CallSite[] callSiteArray = ArgSpecAttributes.$getCallSiteArray();
            Object object = value;
            ScriptBytecodeAdapter.setProperty(object, null, CliBuilderInternal.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

        public /* synthetic */ Object propertyMissing(String name) {
            CallSite[] callSiteArray = ArgSpecAttributes.$getCallSiteArray();
            return ScriptBytecodeAdapter.getProperty(ArgSpecAttributes.class, CliBuilderInternal.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

        public static /* synthetic */ Object $static_propertyMissing(String name) {
            CallSite[] callSiteArray = ArgSpecAttributes.$getCallSiteArray();
            return ScriptBytecodeAdapter.getProperty(ArgSpecAttributes.class, CliBuilderInternal.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

        protected /* synthetic */ MetaClass $getStaticMetaClass() {
            if (this.getClass() != ArgSpecAttributes.class) {
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
        public Class getType() {
            return this.type;
        }

        @Generated
        public void setType(Class clazz) {
            this.type = clazz;
        }

        @Generated
        public Class[] getAuxiliaryTypes() {
            return this.auxiliaryTypes;
        }

        @Generated
        public void setAuxiliaryTypes(Class ... classArray) {
            this.auxiliaryTypes = classArray;
        }

        @Generated
        public String getLabel() {
            return this.label;
        }

        @Generated
        public void setLabel(String string) {
            this.label = string;
        }

        @Generated
        public CommandLine.Model.IGetter getGetter() {
            return this.getter;
        }

        @Generated
        public void setGetter(CommandLine.Model.IGetter iGetter) {
            this.getter = iGetter;
        }

        @Generated
        public CommandLine.Model.ISetter getSetter() {
            return this.setter;
        }

        @Generated
        public void setSetter(CommandLine.Model.ISetter iSetter) {
            this.setter = iSetter;
        }

        @Generated
        public Object getInitialValue() {
            return this.initialValue;
        }

        @Generated
        public void setInitialValue(Object object) {
            this.initialValue = object;
        }

        @Generated
        public boolean getHasInitialValue() {
            return this.hasInitialValue;
        }

        @Generated
        public boolean isHasInitialValue() {
            return this.hasInitialValue;
        }

        @Generated
        public void setHasInitialValue(boolean bl) {
            this.hasInitialValue = bl;
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
            ArgSpecAttributes.$createCallSiteArray_1(stringArray);
            return new CallSiteArray(ArgSpecAttributes.class, stringArray);
        }

        private static /* synthetic */ CallSite[] $getCallSiteArray() {
            CallSiteArray callSiteArray;
            if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                callSiteArray = ArgSpecAttributes.$createCallSiteArray();
                $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
            }
            return callSiteArray.array;
        }
    }
}

