/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.GroovyObject
 *  groovy.lang.MetaClass
 *  groovy.transform.Generated
 *  groovy.transform.Internal
 *  groovyjarjarantlr4.v4.runtime.Token
 *  org.codehaus.groovy.reflection.ClassInfo
 *  org.codehaus.groovy.runtime.BytecodeInterface8
 *  org.codehaus.groovy.runtime.MethodClosure
 *  org.codehaus.groovy.runtime.ScriptBytecodeAdapter
 *  org.codehaus.groovy.runtime.callsite.CallSite
 *  org.codehaus.groovy.runtime.callsite.CallSiteArray
 *  org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation
 *  org.codehaus.groovy.runtime.typehandling.ShortTypeHandling
 */
package org.apache.groovy.groovysh.completion.antlr4;

import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import groovy.transform.Generated;
import groovy.transform.Internal;
import groovyjarjarantlr4.v4.runtime.Token;
import java.beans.Transient;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.groovy.groovysh.Groovysh;
import org.apache.groovy.groovysh.completion.antlr4.IdentifierCompleter;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.BytecodeInterface8;
import org.codehaus.groovy.runtime.MethodClosure;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;
import org.codehaus.groovy.runtime.typehandling.ShortTypeHandling;

public class VariableSyntaxCompleter
implements IdentifierCompleter,
GroovyObject {
    private final Groovysh shell;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private transient /* synthetic */ MetaClass metaClass;
    private static /* synthetic */ SoftReference $callSiteArray;

    public VariableSyntaxCompleter(Groovysh shell) {
        Groovysh groovysh;
        MetaClass metaClass;
        CallSite[] callSiteArray = VariableSyntaxCompleter.$getCallSiteArray();
        this.metaClass = metaClass = this.$getStaticMetaClass();
        this.shell = groovysh = shell;
    }

    @Override
    public boolean complete(List<Token> tokens, List<CharSequence> candidates) {
        boolean foundMatch;
        block6: {
            Map vars;
            String prefix;
            CallSite[] callSiteArray;
            block5: {
                callSiteArray = VariableSyntaxCompleter.$getCallSiteArray();
                prefix = ShortTypeHandling.castToString((Object)callSiteArray[0].callGetProperty(callSiteArray[1].call(tokens)));
                vars = (Map)ScriptBytecodeAdapter.castToType((Object)callSiteArray[2].callGetProperty(callSiteArray[3].callGetProperty(callSiteArray[4].callGroovyObjectGetProperty((Object)this.shell))), Map.class);
                foundMatch = false;
                if (!__$stMC && !BytecodeInterface8.disabledStandardMetaClass()) break block5;
                String varName = null;
                Iterator iterator = (Iterator)ScriptBytecodeAdapter.castToType((Object)callSiteArray[5].call(callSiteArray[6].call((Object)vars)), Iterator.class);
                if (iterator == null) break block6;
                while (iterator.hasNext()) {
                    boolean bl;
                    varName = ShortTypeHandling.castToString(iterator.next());
                    if (!VariableSyntaxCompleter.acceptName(varName, prefix)) continue;
                    if (callSiteArray[7].call((Object)vars, (Object)varName) instanceof MethodClosure) {
                        varName = ScriptBytecodeAdapter.compareGreaterThan((Object)callSiteArray[8].call((Object)((MethodClosure)ScriptBytecodeAdapter.castToType((Object)callSiteArray[9].call((Object)vars, (Object)varName), MethodClosure.class))), (Object)0) ? ShortTypeHandling.castToString((Object)callSiteArray[10].call((Object)varName, (Object)"(")) : ShortTypeHandling.castToString((Object)callSiteArray[11].call((Object)varName, (Object)"()"));
                    }
                    foundMatch = bl = true;
                    callSiteArray[12].call(candidates, (Object)varName);
                }
                break block6;
            }
            String varName = null;
            Iterator iterator = (Iterator)ScriptBytecodeAdapter.castToType((Object)callSiteArray[13].call(callSiteArray[14].call((Object)vars)), Iterator.class);
            if (iterator != null) {
                while (iterator.hasNext()) {
                    boolean bl;
                    varName = ShortTypeHandling.castToString(iterator.next());
                    if (!VariableSyntaxCompleter.acceptName(varName, prefix)) continue;
                    if (callSiteArray[15].call((Object)vars, (Object)varName) instanceof MethodClosure) {
                        varName = ScriptBytecodeAdapter.compareGreaterThan((Object)callSiteArray[16].call((Object)((MethodClosure)ScriptBytecodeAdapter.castToType((Object)callSiteArray[17].call((Object)vars, (Object)varName), MethodClosure.class))), (Object)0) ? ShortTypeHandling.castToString((Object)callSiteArray[18].call((Object)varName, (Object)"(")) : ShortTypeHandling.castToString((Object)callSiteArray[19].call((Object)varName, (Object)"()"));
                    }
                    foundMatch = bl = true;
                    callSiteArray[20].call(candidates, (Object)varName);
                }
            }
        }
        return foundMatch;
    }

    private static boolean acceptName(String name, String prefix) {
        CallSite[] callSiteArray = VariableSyntaxCompleter.$getCallSiteArray();
        if (!BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            return (!DefaultTypeTransformation.booleanUnbox((Object)prefix) || DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[21].call((Object)name, (Object)prefix))) && !DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[22].call((Object)name, (Object)"$")) && !DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[23].call((Object)name, (Object)"_"));
        }
        return (!DefaultTypeTransformation.booleanUnbox((Object)prefix) || DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[24].call((Object)name, (Object)prefix))) && !DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[25].call((Object)name, (Object)"$")) && !DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[26].call((Object)name, (Object)"_"));
    }

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != VariableSyntaxCompleter.class) {
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

    @Generated
    public final Groovysh getShell() {
        return this.shell;
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
        stringArray[0] = "text";
        stringArray[1] = "last";
        stringArray[2] = "variables";
        stringArray[3] = "context";
        stringArray[4] = "interp";
        stringArray[5] = "iterator";
        stringArray[6] = "keySet";
        stringArray[7] = "get";
        stringArray[8] = "getMaximumNumberOfParameters";
        stringArray[9] = "get";
        stringArray[10] = "plus";
        stringArray[11] = "plus";
        stringArray[12] = "leftShift";
        stringArray[13] = "iterator";
        stringArray[14] = "keySet";
        stringArray[15] = "get";
        stringArray[16] = "getMaximumNumberOfParameters";
        stringArray[17] = "get";
        stringArray[18] = "plus";
        stringArray[19] = "plus";
        stringArray[20] = "leftShift";
        stringArray[21] = "startsWith";
        stringArray[22] = "contains";
        stringArray[23] = "startsWith";
        stringArray[24] = "startsWith";
        stringArray[25] = "contains";
        stringArray[26] = "startsWith";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[27];
        VariableSyntaxCompleter.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(VariableSyntaxCompleter.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = VariableSyntaxCompleter.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }
}

