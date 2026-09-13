/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.GroovyObject
 *  groovy.lang.MetaClass
 *  groovy.transform.Generated
 *  groovy.transform.Internal
 *  org.codehaus.groovy.reflection.ClassInfo
 *  org.codehaus.groovy.runtime.GStringImpl
 *  org.codehaus.groovy.runtime.ScriptBytecodeAdapter
 *  org.codehaus.groovy.runtime.callsite.CallSite
 *  org.codehaus.groovy.runtime.callsite.CallSiteArray
 *  org.codehaus.groovy.runtime.typehandling.ShortTypeHandling
 *  org.codehaus.groovy.tools.shell.util.Logger
 *  org.codehaus.groovy.tools.shell.util.Preferences
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
import org.apache.groovy.groovysh.ParseStatus;
import org.apache.groovy.groovysh.Parsing;
import org.apache.groovy.groovysh.RigidParser;
import org.apache.groovy.groovysh.antlr4.RelaxedParser;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.GStringImpl;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.typehandling.ShortTypeHandling;
import org.codehaus.groovy.tools.shell.util.Logger;
import org.codehaus.groovy.tools.shell.util.Preferences;

public class Parser
implements GroovyObject {
    private static final String NEWLINE;
    private static final Logger log;
    private final Parsing delegate;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private transient /* synthetic */ MetaClass metaClass;
    private static /* synthetic */ SoftReference $callSiteArray;

    public Parser() {
        MetaClass metaClass;
        CallSite[] callSiteArray = Parser.$getCallSiteArray();
        this.metaClass = metaClass = this.$getStaticMetaClass();
        String flavor = ShortTypeHandling.castToString((Object)callSiteArray[0].call(Preferences.class));
        callSiteArray[1].call((Object)log, (Object)new GStringImpl(new Object[]{flavor}, new String[]{"Using parser flavor: ", ""}));
        String string = flavor;
        if (ScriptBytecodeAdapter.isCase((Object)string, (Object)callSiteArray[2].callGetProperty(Preferences.class))) {
            Object object = callSiteArray[3].callConstructor(RelaxedParser.class);
            this.delegate = (Parsing)ScriptBytecodeAdapter.castToType((Object)object, Parsing.class);
        } else if (ScriptBytecodeAdapter.isCase((Object)string, (Object)callSiteArray[4].callGetProperty(Preferences.class))) {
            Object object = callSiteArray[5].callConstructor(RigidParser.class);
            this.delegate = (Parsing)ScriptBytecodeAdapter.castToType((Object)object, Parsing.class);
        } else {
            callSiteArray[6].call((Object)log, (Object)new GStringImpl(new Object[]{flavor, callSiteArray[7].callGetProperty(Preferences.class)}, new String[]{"Invalid parser flavor: ", "; using default: ", ""}));
            Object object = callSiteArray[8].callConstructor(RigidParser.class);
            this.delegate = (Parsing)ScriptBytecodeAdapter.castToType((Object)object, Parsing.class);
        }
    }

    public ParseStatus parse(Collection<String> buffer) {
        CallSite[] callSiteArray = Parser.$getCallSiteArray();
        return (ParseStatus)ScriptBytecodeAdapter.castToType((Object)callSiteArray[9].call((Object)this.delegate, buffer), ParseStatus.class);
    }

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != Parser.class) {
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
        Object object = Parser.$getCallSiteArray()[10].call(System.class);
        NEWLINE = ShortTypeHandling.castToString((Object)object);
        Object object2 = Parser.$getCallSiteArray()[11].call(Logger.class, Parser.class);
        log = (Logger)ScriptBytecodeAdapter.castToType((Object)object2, Logger.class);
    }

    @Generated
    public static String getNEWLINE() {
        return NEWLINE;
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
        stringArray[0] = "getParserFlavor";
        stringArray[1] = "debug";
        stringArray[2] = "PARSER_RELAXED";
        stringArray[3] = "<$constructor$>";
        stringArray[4] = "PARSER_RIGID";
        stringArray[5] = "<$constructor$>";
        stringArray[6] = "error";
        stringArray[7] = "PARSER_RIGID";
        stringArray[8] = "<$constructor$>";
        stringArray[9] = "parse";
        stringArray[10] = "lineSeparator";
        stringArray[11] = "create";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[12];
        Parser.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(Parser.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = Parser.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }
}

