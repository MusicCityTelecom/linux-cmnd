/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.GroovyObject
 *  groovy.lang.MetaClass
 *  groovy.transform.Generated
 *  groovy.transform.Internal
 *  org.codehaus.groovy.reflection.ClassInfo
 *  org.codehaus.groovy.runtime.ScriptBytecodeAdapter
 *  org.codehaus.groovy.runtime.callsite.CallSite
 *  org.codehaus.groovy.runtime.callsite.CallSiteArray
 *  org.codehaus.groovy.tools.shell.util.Logger
 */
package org.apache.groovy.groovysh.antlr4;

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
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.tools.shell.util.Logger;

public final class RelaxedParser
implements Parsing,
GroovyObject {
    private final Logger log;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private transient /* synthetic */ MetaClass metaClass;
    private static /* synthetic */ SoftReference $callSiteArray;

    @Generated
    public RelaxedParser() {
        MetaClass metaClass;
        CallSite[] callSiteArray = RelaxedParser.$getCallSiteArray();
        Object object = callSiteArray[0].call(Logger.class, callSiteArray[1].callGroovyObjectGetProperty((Object)this));
        this.log = (Logger)ScriptBytecodeAdapter.castToType((Object)object, Logger.class);
        this.metaClass = metaClass = this.$getStaticMetaClass();
    }

    /*
     * Exception decompiling
     */
    @Override
    public ParseStatus parse(Collection<String> buffer) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [8[CATCHBLOCK]], but top level block is 5[TRYBLOCK]
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:435)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:484)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != RelaxedParser.class) {
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
        stringArray[2] = "join";
        stringArray[3] = "NEWLINE";
        stringArray[4] = "debug";
        stringArray[5] = "fromReader";
        stringArray[6] = "<$constructor$>";
        stringArray[7] = "<$constructor$>";
        stringArray[8] = "removeErrorListener";
        stringArray[9] = "INSTANCE";
        stringArray[10] = "<$constructor$>";
        stringArray[11] = "fill";
        stringArray[12] = "debug";
        stringArray[13] = "<$constructor$>";
        stringArray[14] = "COMPLETE";
        stringArray[15] = "contains";
        stringArray[16] = "message";
        stringArray[17] = "contains";
        stringArray[18] = "message";
        stringArray[19] = "contains";
        stringArray[20] = "message";
        stringArray[21] = "contains";
        stringArray[22] = "message";
        stringArray[23] = "contains";
        stringArray[24] = "message";
        stringArray[25] = "<$constructor$>";
        stringArray[26] = "INCOMPLETE";
        stringArray[27] = "contains";
        stringArray[28] = "message";
        stringArray[29] = "contains";
        stringArray[30] = "message";
        stringArray[31] = "contains";
        stringArray[32] = "message";
        stringArray[33] = "contains";
        stringArray[34] = "message";
        stringArray[35] = "contains";
        stringArray[36] = "message";
        stringArray[37] = "<$constructor$>";
        stringArray[38] = "INCOMPLETE";
        stringArray[39] = "debug";
        stringArray[40] = "name";
        stringArray[41] = "getClass";
        stringArray[42] = "<$constructor$>";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[43];
        RelaxedParser.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(RelaxedParser.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = RelaxedParser.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }
}

