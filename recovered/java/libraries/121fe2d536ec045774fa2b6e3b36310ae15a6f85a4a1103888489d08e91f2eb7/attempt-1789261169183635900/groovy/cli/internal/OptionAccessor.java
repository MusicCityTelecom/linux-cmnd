/*
 * Decompiled with CFR 0.152.
 */
package groovy.cli.internal;

import groovy.cli.TypedOption;
import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import groovy.lang.Reference;
import groovy.transform.Generated;
import groovy.transform.Internal;
import groovyjarjarpicocli.CommandLine;
import java.beans.Transient;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.BytecodeInterface8;
import org.codehaus.groovy.runtime.GeneratedClosure;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.StringGroovyMethods;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;
import org.codehaus.groovy.runtime.typehandling.ShortTypeHandling;

public class OptionAccessor
implements GroovyObject {
    private CommandLine.ParseResult parseResult;
    private Map<String, TypedOption> savedTypeOptions;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private transient /* synthetic */ MetaClass metaClass;
    private static /* synthetic */ SoftReference $callSiteArray;

    public OptionAccessor(CommandLine.ParseResult parseResult) {
        CommandLine.ParseResult parseResult2;
        MetaClass metaClass;
        CallSite[] callSiteArray = OptionAccessor.$getCallSiteArray();
        this.metaClass = metaClass = this.$getStaticMetaClass();
        this.parseResult = parseResult2 = parseResult;
    }

    public boolean hasOption(TypedOption typedOption) {
        CallSite[] callSiteArray = OptionAccessor.$getCallSiteArray();
        Object object = callSiteArray[1].callGetProperty(typedOption);
        return DefaultTypeTransformation.booleanUnbox(callSiteArray[0].call((Object)this.parseResult, DefaultTypeTransformation.booleanUnbox(object) ? object : (String)ScriptBytecodeAdapter.asType(callSiteArray[2].callGetProperty(typedOption), String.class)));
    }

    public <T> T defaultValue(String name) {
        CallSite[] callSiteArray = OptionAccessor.$getCallSiteArray();
        Class type = ShortTypeHandling.castToClass(callSiteArray[3].callGetPropertySafe(callSiteArray[4].call(this.savedTypeOptions, name)));
        String value = ShortTypeHandling.castToString(DefaultTypeTransformation.booleanUnbox(callSiteArray[5].callSafe(callSiteArray[6].call(this.savedTypeOptions, name))) ? callSiteArray[7].call(callSiteArray[8].call(this.savedTypeOptions, name)) : null);
        if (__$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            return (T)(DefaultTypeTransformation.booleanUnbox(value) ? callSiteArray[9].callCurrent(this, type, name, value) : null);
        }
        return DefaultTypeTransformation.booleanUnbox(value) ? (T)this.getTypedValue(type, name, value) : null;
    }

    public <T> T getOptionValue(TypedOption<T> typedOption) {
        CallSite[] callSiteArray = OptionAccessor.$getCallSiteArray();
        return (T)callSiteArray[10].callCurrent(this, typedOption, null);
    }

    public <T> T getOptionValue(TypedOption<T> typedOption, T defaultValue) {
        CallSite[] callSiteArray = OptionAccessor.$getCallSiteArray();
        String string = ShortTypeHandling.castToString(callSiteArray[11].callGetProperty(typedOption));
        String optionName = ShortTypeHandling.castToString(DefaultTypeTransformation.booleanUnbox(string) ? string : callSiteArray[12].callGetProperty(typedOption));
        if (DefaultTypeTransformation.booleanUnbox(callSiteArray[13].call((Object)this.parseResult, optionName))) {
            return (T)callSiteArray[14].call(this.parseResult, optionName, defaultValue);
        }
        CommandLine.Model.OptionSpec option = (CommandLine.Model.OptionSpec)ScriptBytecodeAdapter.castToType(callSiteArray[15].call(callSiteArray[16].call(this.parseResult), optionName), CommandLine.Model.OptionSpec.class);
        return (T)(DefaultTypeTransformation.booleanUnbox(option) ? callSiteArray[17].callGetProperty(option) : defaultValue);
    }

    public <T> T getAt(TypedOption<T> typedOption) {
        CallSite[] callSiteArray = OptionAccessor.$getCallSiteArray();
        return (T)callSiteArray[18].callCurrent(this, typedOption, null);
    }

    public <T> T getAt(TypedOption<T> typedOption, T defaultValue) {
        CallSite[] callSiteArray = OptionAccessor.$getCallSiteArray();
        return (T)callSiteArray[19].callCurrent(this, typedOption, defaultValue);
    }

    private <T> T getTypedValue(Class<T> type, String optionName, String optionValue) {
        CallSite[] callSiteArray = OptionAccessor.$getCallSiteArray();
        if (ScriptBytecodeAdapter.compareEqual(callSiteArray[20].callGetPropertySafe(callSiteArray[21].callGetPropertySafe(callSiteArray[22].callGetPropertySafe(callSiteArray[23].call(this.savedTypeOptions, optionName)))), 0)) {
            return (T)callSiteArray[24].call((Object)this.parseResult, optionName);
        }
        Object convert = callSiteArray[25].callGetPropertySafe(callSiteArray[26].call(this.savedTypeOptions, optionName));
        return (T)callSiteArray[27].callCurrent(this, type, optionValue, convert);
    }

    private <T> T getValue(Class<T> type, String optionValue, Closure convert) {
        CallSite[] callSiteArray = OptionAccessor.$getCallSiteArray();
        if (!DefaultTypeTransformation.booleanUnbox(type)) {
            return (T)optionValue;
        }
        if (DefaultTypeTransformation.booleanUnbox(callSiteArray[28].call(Closure.class, type)) && DefaultTypeTransformation.booleanUnbox(convert)) {
            return (T)callSiteArray[29].call((Object)convert, optionValue);
        }
        if (!BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            if (ScriptBytecodeAdapter.compareEqual(type, Boolean.class) || ScriptBytecodeAdapter.compareEqual(type, callSiteArray[30].callGetProperty(Boolean.class))) {
                return (T)callSiteArray[31].call(type, callSiteArray[32].call(Boolean.class, optionValue));
            }
        } else if (ScriptBytecodeAdapter.compareEqual(type, Boolean.class) || ScriptBytecodeAdapter.compareEqual(type, callSiteArray[33].callGetProperty(Boolean.class))) {
            return (T)callSiteArray[34].call(type, callSiteArray[35].call(Boolean.class, optionValue));
        }
        return (T)callSiteArray[36].call(StringGroovyMethods.class, optionValue, ScriptBytecodeAdapter.createPojoWrapper(type, Class.class));
    }

    public Properties getOptionProperties(String name) {
        CallSite[] callSiteArray = OptionAccessor.$getCallSiteArray();
        if (!DefaultTypeTransformation.booleanUnbox(callSiteArray[37].call((Object)this.parseResult, name))) {
            return (Properties)ScriptBytecodeAdapter.castToType(null, Properties.class);
        }
        List keyValues = (List)ScriptBytecodeAdapter.castToType(callSiteArray[38].call(callSiteArray[39].call((Object)this.parseResult, name)), List.class);
        Reference<Properties> result = new Reference<Properties>((Properties)ScriptBytecodeAdapter.castToType(callSiteArray[40].callConstructor(Properties.class), Properties.class));
        public final class _getOptionProperties_closure1
        extends Closure
        implements GeneratedClosure {
            private /* synthetic */ Reference result;
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _getOptionProperties_closure1(Object _outerInstance, Object _thisObject, Reference result) {
                Reference reference;
                CallSite[] callSiteArray = _getOptionProperties_closure1.$getCallSiteArray();
                super(_outerInstance, _thisObject);
                this.result = reference = result;
            }

            public Object doCall(Object k, Object v) {
                CallSite[] callSiteArray = _getOptionProperties_closure1.$getCallSiteArray();
                return callSiteArray[0].call(this.result.get(), k, v);
            }

            @Generated
            public Object call(Object k, Object v) {
                CallSite[] callSiteArray = _getOptionProperties_closure1.$getCallSiteArray();
                return callSiteArray[1].callCurrent(this, k, v);
            }

            @Generated
            public Properties getResult() {
                CallSite[] callSiteArray = _getOptionProperties_closure1.$getCallSiteArray();
                return (Properties)ScriptBytecodeAdapter.castToType(this.result.get(), Properties.class);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (this.getClass() != _getOptionProperties_closure1.class) {
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
                _getOptionProperties_closure1.$createCallSiteArray_1(stringArray);
                return new CallSiteArray(_getOptionProperties_closure1.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _getOptionProperties_closure1.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        callSiteArray[41].call(callSiteArray[42].call(keyValues), new _getOptionProperties_closure1(this, this, result));
        return result.get();
    }

    @Override
    public Object invokeMethod(String name, Object args) {
        CallSite[] callSiteArray = OptionAccessor.$getCallSiteArray();
        if (ScriptBytecodeAdapter.compareEqual(name, "hasOption")) {
            Object object;
            String string;
            name = string = "hasMatchedOption";
            args = object = callSiteArray[43].call(ScriptBytecodeAdapter.createList(new Object[]{callSiteArray[44].call(args, 0)}));
        }
        if (ScriptBytecodeAdapter.compareEqual(name, "getOptionValue")) {
            Object object;
            String string;
            name = string = "matchedOptionValue";
            args = object = callSiteArray[45].call(ScriptBytecodeAdapter.createList(new Object[]{callSiteArray[46].call(args, 0), null}));
        }
        return callSiteArray[47].call(callSiteArray[48].call(InvokerHelper.class, this.parseResult), this.parseResult, name, args);
    }

    @Override
    public Object getProperty(String name) {
        CallSite[] callSiteArray = OptionAccessor.$getCallSiteArray();
        if (ScriptBytecodeAdapter.compareEqual(name, "parseResult")) {
            return this.parseResult;
        }
        if (DefaultTypeTransformation.booleanUnbox(callSiteArray[49].call((Object)this.parseResult, name))) {
            Object result = callSiteArray[50].call(this.parseResult, name, null);
            Class userSpecifiedType = ShortTypeHandling.castToClass(callSiteArray[51].callGetPropertySafe(callSiteArray[52].call(this.savedTypeOptions, name)));
            if (DefaultTypeTransformation.booleanUnbox(callSiteArray[53].callSafe(userSpecifiedType))) {
                return result;
            }
            Class derivedType = ShortTypeHandling.castToClass(callSiteArray[54].call(callSiteArray[55].call((Object)this.parseResult, name)));
            if (DefaultTypeTransformation.booleanUnbox(callSiteArray[56].call(derivedType))) {
                return DefaultTypeTransformation.booleanUnbox(result) ? callSiteArray[57].call(result, 0) : null;
            }
            if (DefaultTypeTransformation.booleanUnbox(callSiteArray[58].call(Collection.class, derivedType))) {
                return callSiteArray[59].callSafe((Collection)ScriptBytecodeAdapter.asType(result, Collection.class));
            }
            if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass() ? !DefaultTypeTransformation.booleanUnbox(userSpecifiedType) && ScriptBytecodeAdapter.compareEqual(result, "") && ScriptBytecodeAdapter.compareEqual(callSiteArray[60].callGetProperty(callSiteArray[61].call(callSiteArray[62].call((Object)this.parseResult, name))), 0) : !DefaultTypeTransformation.booleanUnbox(userSpecifiedType) && ScriptBytecodeAdapter.compareEqual(result, "") && ScriptBytecodeAdapter.compareEqual(callSiteArray[63].callGetProperty(callSiteArray[64].call(callSiteArray[65].call((Object)this.parseResult, name))), 0)) {
                return true;
            }
            return callSiteArray[66].call(callSiteArray[67].call(callSiteArray[68].call((Object)this.parseResult, name)), 0);
        }
        if (DefaultTypeTransformation.booleanUnbox(callSiteArray[69].call(callSiteArray[70].call(this.parseResult), name))) {
            Object object;
            Object option = callSiteArray[71].call(callSiteArray[72].call(this.parseResult), name);
            Object result = callSiteArray[73].callGetProperty(option);
            Object longOpt = callSiteArray[74].call(option);
            longOpt = object = DefaultTypeTransformation.booleanUnbox(callSiteArray[75].callSafe(longOpt, "--")) ? callSiteArray[76].call(longOpt, 2) : longOpt;
            Class userSpecifiedType = ShortTypeHandling.castToClass(callSiteArray[77].callGetPropertySafe(callSiteArray[78].call(this.savedTypeOptions, longOpt)));
            if (DefaultTypeTransformation.booleanUnbox(userSpecifiedType) && ScriptBytecodeAdapter.compareNotEqual(Boolean.class, userSpecifiedType)) {
                return result;
            }
            return DefaultTypeTransformation.booleanUnbox(result) ? result : Boolean.valueOf(false);
        }
        if (ScriptBytecodeAdapter.compareGreaterThan(callSiteArray[79].call(name), 1) && DefaultTypeTransformation.booleanUnbox(callSiteArray[80].call((Object)name, "s"))) {
            Object singularName = callSiteArray[81].call((Object)name, ScriptBytecodeAdapter.createRange(0, -2, false, false));
            if (DefaultTypeTransformation.booleanUnbox(callSiteArray[82].call((Object)this.parseResult, singularName))) {
                Class type = ShortTypeHandling.castToClass(callSiteArray[83].call(callSiteArray[84].call((Object)this.parseResult, singularName)));
                if (DefaultTypeTransformation.booleanUnbox(callSiteArray[85].call(type)) || DefaultTypeTransformation.booleanUnbox(callSiteArray[86].call(Collection.class, type)) || DefaultTypeTransformation.booleanUnbox(callSiteArray[87].call(Map.class, type))) {
                    return callSiteArray[88].call(this.parseResult, singularName, null);
                }
                return callSiteArray[89].call(callSiteArray[90].call((Object)this.parseResult, singularName));
            }
        }
        return false;
    }

    public List<String> arguments() {
        CallSite[] callSiteArray = OptionAccessor.$getCallSiteArray();
        return (List)ScriptBytecodeAdapter.castToType(DefaultTypeTransformation.booleanUnbox(callSiteArray[91].call((Object)this.parseResult, 0)) ? callSiteArray[92].call(callSiteArray[93].call((Object)this.parseResult, 0)) : ScriptBytecodeAdapter.createList(new Object[0]), List.class);
    }

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != OptionAccessor.class) {
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
    public CommandLine.ParseResult getParseResult() {
        return this.parseResult;
    }

    @Generated
    public void setParseResult(CommandLine.ParseResult parseResult) {
        this.parseResult = parseResult;
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

    public /* synthetic */ Object super$1$getProperty(String string) {
        return GroovyObject.super.getProperty(string);
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
        stringArray[0] = "hasMatchedOption";
        stringArray[1] = "longOpt";
        stringArray[2] = "opt";
        stringArray[3] = "type";
        stringArray[4] = "getAt";
        stringArray[5] = "defaultValue";
        stringArray[6] = "getAt";
        stringArray[7] = "defaultValue";
        stringArray[8] = "getAt";
        stringArray[9] = "getTypedValue";
        stringArray[10] = "getOptionValue";
        stringArray[11] = "longOpt";
        stringArray[12] = "opt";
        stringArray[13] = "hasMatchedOption";
        stringArray[14] = "matchedOptionValue";
        stringArray[15] = "findOption";
        stringArray[16] = "commandSpec";
        stringArray[17] = "value";
        stringArray[18] = "getAt";
        stringArray[19] = "getOptionValue";
        stringArray[20] = "min";
        stringArray[21] = "arity";
        stringArray[22] = "cliOption";
        stringArray[23] = "getAt";
        stringArray[24] = "hasMatchedOption";
        stringArray[25] = "convert";
        stringArray[26] = "getAt";
        stringArray[27] = "getValue";
        stringArray[28] = "isAssignableFrom";
        stringArray[29] = "call";
        stringArray[30] = "TYPE";
        stringArray[31] = "cast";
        stringArray[32] = "parseBoolean";
        stringArray[33] = "TYPE";
        stringArray[34] = "cast";
        stringArray[35] = "parseBoolean";
        stringArray[36] = "asType";
        stringArray[37] = "hasMatchedOption";
        stringArray[38] = "stringValues";
        stringArray[39] = "matchedOption";
        stringArray[40] = "<$constructor$>";
        stringArray[41] = "each";
        stringArray[42] = "toSpreadMap";
        stringArray[43] = "toArray";
        stringArray[44] = "getAt";
        stringArray[45] = "toArray";
        stringArray[46] = "getAt";
        stringArray[47] = "invokeMethod";
        stringArray[48] = "getMetaClass";
        stringArray[49] = "hasMatchedOption";
        stringArray[50] = "matchedOptionValue";
        stringArray[51] = "type";
        stringArray[52] = "getAt";
        stringArray[53] = "isArray";
        stringArray[54] = "type";
        stringArray[55] = "matchedOption";
        stringArray[56] = "isArray";
        stringArray[57] = "getAt";
        stringArray[58] = "isAssignableFrom";
        stringArray[59] = "first";
        stringArray[60] = "min";
        stringArray[61] = "arity";
        stringArray[62] = "matchedOption";
        stringArray[63] = "min";
        stringArray[64] = "arity";
        stringArray[65] = "matchedOption";
        stringArray[66] = "get";
        stringArray[67] = "typedValues";
        stringArray[68] = "matchedOption";
        stringArray[69] = "findOption";
        stringArray[70] = "commandSpec";
        stringArray[71] = "findOption";
        stringArray[72] = "commandSpec";
        stringArray[73] = "value";
        stringArray[74] = "longestName";
        stringArray[75] = "startsWith";
        stringArray[76] = "substring";
        stringArray[77] = "type";
        stringArray[78] = "getAt";
        stringArray[79] = "size";
        stringArray[80] = "endsWith";
        stringArray[81] = "getAt";
        stringArray[82] = "hasMatchedOption";
        stringArray[83] = "type";
        stringArray[84] = "matchedOption";
        stringArray[85] = "isArray";
        stringArray[86] = "isAssignableFrom";
        stringArray[87] = "isAssignableFrom";
        stringArray[88] = "matchedOptionValue";
        stringArray[89] = "stringValues";
        stringArray[90] = "matchedOption";
        stringArray[91] = "hasMatchedPositional";
        stringArray[92] = "stringValues";
        stringArray[93] = "matchedPositional";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[94];
        OptionAccessor.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(OptionAccessor.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = OptionAccessor.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }
}

