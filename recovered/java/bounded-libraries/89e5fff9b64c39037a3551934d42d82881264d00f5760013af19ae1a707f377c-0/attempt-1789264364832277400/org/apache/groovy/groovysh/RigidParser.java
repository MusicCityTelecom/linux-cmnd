/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.GroovyObject
 *  groovy.lang.MetaClass
 *  groovy.transform.Generated
 *  groovy.transform.Internal
 *  org.codehaus.groovy.control.CompilationFailedException
 *  org.codehaus.groovy.control.SourceUnit
 *  org.codehaus.groovy.reflection.ClassInfo
 *  org.codehaus.groovy.runtime.BytecodeInterface8
 *  org.codehaus.groovy.runtime.GStringImpl
 *  org.codehaus.groovy.runtime.ScriptBytecodeAdapter
 *  org.codehaus.groovy.runtime.callsite.CallSite
 *  org.codehaus.groovy.runtime.callsite.CallSiteArray
 *  org.codehaus.groovy.runtime.powerassert.AssertionRenderer
 *  org.codehaus.groovy.runtime.powerassert.ValueRecorder
 *  org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation
 *  org.codehaus.groovy.runtime.typehandling.ShortTypeHandling
 *  org.codehaus.groovy.tools.shell.util.Logger
 */
package org.apache.groovy.groovysh;

import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import groovy.transform.Generated;
import groovy.transform.Internal;
import java.beans.Transient;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.groovy.groovysh.ParseCode;
import org.apache.groovy.groovysh.ParseStatus;
import org.apache.groovy.groovysh.Parser;
import org.apache.groovy.groovysh.Parsing;
import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.BytecodeInterface8;
import org.codehaus.groovy.runtime.GStringImpl;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.powerassert.AssertionRenderer;
import org.codehaus.groovy.runtime.powerassert.ValueRecorder;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;
import org.codehaus.groovy.runtime.typehandling.ShortTypeHandling;
import org.codehaus.groovy.tools.shell.util.Logger;

public final class RigidParser
implements Parsing,
GroovyObject {
    private static final Pattern ANNOTATION_PATTERN;
    private static final String SCRIPT_FILENAME = "groovysh_parse";
    private final Logger log;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private transient /* synthetic */ MetaClass metaClass;
    private static /* synthetic */ SoftReference $callSiteArray;

    @Generated
    public RigidParser() {
        MetaClass metaClass;
        CallSite[] callSiteArray = RigidParser.$getCallSiteArray();
        Object object = callSiteArray[0].call(Logger.class, callSiteArray[1].callGroovyObjectGetProperty((Object)this));
        this.log = (Logger)ScriptBytecodeAdapter.castToType((Object)object, Logger.class);
        this.metaClass = metaClass = this.$getStaticMetaClass();
    }

    @Override
    public ParseStatus parse(Collection<String> buffer) {
        CallSite[] callSiteArray = RigidParser.$getCallSiteArray();
        ValueRecorder valueRecorder = new ValueRecorder();
        try {
            Collection<String> collection = buffer;
            valueRecorder.record(collection, 8);
            if (DefaultTypeTransformation.booleanUnbox(collection)) {
                valueRecorder.clear();
            } else {
                ScriptBytecodeAdapter.assertFailed((Object)AssertionRenderer.render((String)"assert buffer", (ValueRecorder)valueRecorder), null);
            }
        }
        catch (Throwable throwable) {
            valueRecorder.clear();
            throw throwable;
        }
        String source = ShortTypeHandling.castToString((Object)callSiteArray[2].call(buffer, callSiteArray[3].callGetProperty(Parser.class)));
        callSiteArray[4].call((Object)this.log, (Object)new GStringImpl(new Object[]{source}, new String[]{"Parsing: ", ""}));
        SourceUnit parser = null;
        Throwable error = null;
        Object object = callSiteArray[5].call(SourceUnit.class, (Object)SCRIPT_FILENAME, (Object)source, (Object)1);
        parser = (SourceUnit)ScriptBytecodeAdapter.castToType((Object)object, SourceUnit.class);
        callSiteArray[6].call((Object)parser);
        callSiteArray[7].call((Object)this.log, (Object)"Parse complete");
        ParseStatus parseStatus = (ParseStatus)ScriptBytecodeAdapter.castToType((Object)callSiteArray[8].callConstructor(ParseStatus.class, callSiteArray[9].callGetProperty(ParseCode.class)), ParseStatus.class);
        try {
            return parseStatus;
        }
        catch (CompilationFailedException e) {
            if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                if (ScriptBytecodeAdapter.compareGreaterThan((Object)callSiteArray[10].callGetProperty(callSiteArray[11].callGetProperty(parser)), (Object)1) || !DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[12].call((Object)parser))) {
                    if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[13].callStatic(RigidParser.class, callSiteArray[14].call(callSiteArray[15].call(buffer, (Object)-1)))) || DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[16].callStatic(RigidParser.class, (Object)e, callSiteArray[17].call(callSiteArray[18].call(buffer, (Object)-1)))) || RigidParser.hasUnmatchedOpenBracketOrParen(source)) {
                        callSiteArray[19].call((Object)this.log, (Object)new GStringImpl(new Object[]{e}, new String[]{"Ignoring parse failure; might be valid: ", ""}));
                    } else {
                        CompilationFailedException compilationFailedException = e;
                        error = compilationFailedException;
                    }
                }
            } else if (ScriptBytecodeAdapter.compareGreaterThan((Object)callSiteArray[20].callGetProperty(callSiteArray[21].callGetProperty((Object)parser)), (Object)1) || !DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[22].call((Object)parser))) {
                if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[23].callStatic(RigidParser.class, callSiteArray[24].call(callSiteArray[25].call(buffer, (Object)-1)))) || DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[26].callStatic(RigidParser.class, (Object)e, callSiteArray[27].call(callSiteArray[28].call(buffer, (Object)-1)))) || RigidParser.hasUnmatchedOpenBracketOrParen(source)) {
                    callSiteArray[29].call((Object)this.log, (Object)new GStringImpl(new Object[]{e}, new String[]{"Ignoring parse failure; might be valid: ", ""}));
                } else {
                    CompilationFailedException compilationFailedException = e;
                    error = compilationFailedException;
                }
            }
        }
        catch (Throwable e) {
            Throwable throwable;
            error = throwable = e;
        }
        if (DefaultTypeTransformation.booleanUnbox((Object)error)) {
            callSiteArray[30].call((Object)this.log, (Object)new GStringImpl(new Object[]{error}, new String[]{"Parse error: ", ""}));
            return (ParseStatus)ScriptBytecodeAdapter.castToType((Object)callSiteArray[31].callConstructor(ParseStatus.class, (Object)error), ParseStatus.class);
        }
        callSiteArray[32].call((Object)this.log, (Object)"Parse incomplete");
        return (ParseStatus)ScriptBytecodeAdapter.castToType((Object)callSiteArray[33].callConstructor(ParseStatus.class, callSiteArray[34].callGetProperty(ParseCode.class)), ParseStatus.class);
    }

    public static boolean ignoreSyntaxErrorForLineEnding(String line) {
        CallSite[] callSiteArray = RigidParser.$getCallSiteArray();
        List lineEndings = ScriptBytecodeAdapter.createList((Object[])new Object[]{"{", "[", "(", ",", ".", "-", "+", "/", "*", "%", "&", "|", "?", "<", ">", "=", ":", "'''", "\"\"\"", "\\"});
        String lineEnding = null;
        Iterator iterator = (Iterator)ScriptBytecodeAdapter.castToType((Object)callSiteArray[35].call((Object)lineEndings), Iterator.class);
        if (iterator != null) {
            while (iterator.hasNext()) {
                lineEnding = ShortTypeHandling.castToString(iterator.next());
                if (!DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[36].call((Object)line, (Object)lineEnding))) continue;
                return true;
            }
        }
        return false;
    }

    public static boolean hasUnmatchedOpenBracketOrParen(String source) {
        CallSite[] callSiteArray = RigidParser.$getCallSiteArray();
        if (!DefaultTypeTransformation.booleanUnbox((Object)source)) {
            return false;
        }
        int parens = 0;
        int brackets = 0;
        Object ch = null;
        Iterator iterator = (Iterator)ScriptBytecodeAdapter.castToType((Object)callSiteArray[37].call((Object)source), Iterator.class);
        if (iterator != null) {
            while (iterator.hasNext()) {
                ch = iterator.next();
                Object var6_6 = ch;
                if (ScriptBytecodeAdapter.isCase(var6_6, (Object)"[")) {
                    brackets = DefaultTypeTransformation.intUnbox((Object)callSiteArray[38].call((Object)brackets));
                    continue;
                }
                if (ScriptBytecodeAdapter.isCase(var6_6, (Object)"]")) {
                    brackets = DefaultTypeTransformation.intUnbox((Object)callSiteArray[39].call((Object)brackets));
                    continue;
                }
                if (ScriptBytecodeAdapter.isCase(var6_6, (Object)"(")) {
                    parens = DefaultTypeTransformation.intUnbox((Object)callSiteArray[40].call((Object)parens));
                    continue;
                }
                if (!ScriptBytecodeAdapter.isCase(var6_6, (Object)")")) continue;
                parens = DefaultTypeTransformation.intUnbox((Object)callSiteArray[41].call((Object)parens));
            }
        }
        if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            return brackets > 0 || parens > 0;
        }
        return brackets > 0 || parens > 0;
    }

    public static boolean isAnnotationExpression(CompilationFailedException e, String line) {
        CallSite[] callSiteArray = RigidParser.$getCallSiteArray();
        return DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[42].call(callSiteArray[43].call((Object)e), (Object)"unexpected token: @")) && DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[44].call(callSiteArray[45].call((Object)ANNOTATION_PATTERN, (Object)line)));
    }

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != RigidParser.class) {
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
        Object object = RigidParser.$getCallSiteArray()[46].call(Pattern.class, (Object)"^@[a-zA-Z_][a-zA-Z_0-9]*(.*)$");
        ANNOTATION_PATTERN = (Pattern)ScriptBytecodeAdapter.castToType((Object)object, Pattern.class);
    }

    @Generated
    public static String getSCRIPT_FILENAME() {
        return SCRIPT_FILENAME;
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
        stringArray[0] = "create";
        stringArray[1] = "class";
        stringArray[2] = "join";
        stringArray[3] = "NEWLINE";
        stringArray[4] = "debug";
        stringArray[5] = "create";
        stringArray[6] = "parse";
        stringArray[7] = "debug";
        stringArray[8] = "<$constructor$>";
        stringArray[9] = "COMPLETE";
        stringArray[10] = "errorCount";
        stringArray[11] = "errorCollector";
        stringArray[12] = "failedWithUnexpectedEOF";
        stringArray[13] = "ignoreSyntaxErrorForLineEnding";
        stringArray[14] = "trim";
        stringArray[15] = "getAt";
        stringArray[16] = "isAnnotationExpression";
        stringArray[17] = "trim";
        stringArray[18] = "getAt";
        stringArray[19] = "debug";
        stringArray[20] = "errorCount";
        stringArray[21] = "errorCollector";
        stringArray[22] = "failedWithUnexpectedEOF";
        stringArray[23] = "ignoreSyntaxErrorForLineEnding";
        stringArray[24] = "trim";
        stringArray[25] = "getAt";
        stringArray[26] = "isAnnotationExpression";
        stringArray[27] = "trim";
        stringArray[28] = "getAt";
        stringArray[29] = "debug";
        stringArray[30] = "debug";
        stringArray[31] = "<$constructor$>";
        stringArray[32] = "debug";
        stringArray[33] = "<$constructor$>";
        stringArray[34] = "INCOMPLETE";
        stringArray[35] = "iterator";
        stringArray[36] = "endsWith";
        stringArray[37] = "iterator";
        stringArray[38] = "next";
        stringArray[39] = "previous";
        stringArray[40] = "next";
        stringArray[41] = "previous";
        stringArray[42] = "contains";
        stringArray[43] = "getMessage";
        stringArray[44] = "find";
        stringArray[45] = "matcher";
        stringArray[46] = "compile";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[47];
        RigidParser.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(RigidParser.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = RigidParser.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }
}

