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
import org.apache.groovy.groovysh.completion.antlr4.IdentifierCompleter;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;
import org.codehaus.groovy.runtime.typehandling.ShortTypeHandling;

public class KeywordSyntaxCompleter
implements IdentifierCompleter,
GroovyObject {
    private static final String[] KEYWORDS;
    private static final String[] VALUE_KEYWORDS;
    private static final String[] SPECIAL_FUNCTIONS;
    private static final String[] DEFAULT_METHODS;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private transient /* synthetic */ MetaClass metaClass;
    private static /* synthetic */ SoftReference $callSiteArray;

    @Generated
    public KeywordSyntaxCompleter() {
        MetaClass metaClass;
        CallSite[] callSiteArray = KeywordSyntaxCompleter.$getCallSiteArray();
        this.metaClass = metaClass = this.$getStaticMetaClass();
    }

    @Override
    public boolean complete(List<Token> tokens, List<CharSequence> candidates) {
        CallSite[] callSiteArray = KeywordSyntaxCompleter.$getCallSiteArray();
        String prefix = ShortTypeHandling.castToString((Object)callSiteArray[0].callGetProperty(callSiteArray[1].call(tokens)));
        boolean foundMatch = false;
        String varName = null;
        Iterator iterator = (Iterator)ScriptBytecodeAdapter.castToType((Object)callSiteArray[2].call((Object)KEYWORDS), Iterator.class);
        if (iterator != null) {
            while (iterator.hasNext()) {
                boolean bl;
                varName = ShortTypeHandling.castToString(iterator.next());
                if (!DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[3].call((Object)varName, (Object)prefix))) continue;
                callSiteArray[4].call(candidates, callSiteArray[5].call((Object)varName, (Object)" "));
                foundMatch = bl = true;
            }
        }
        String varName2 = null;
        Iterator iterator2 = (Iterator)ScriptBytecodeAdapter.castToType((Object)callSiteArray[6].call((Object)VALUE_KEYWORDS), Iterator.class);
        if (iterator2 != null) {
            while (iterator2.hasNext()) {
                boolean bl;
                varName2 = ShortTypeHandling.castToString(iterator2.next());
                if (!DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[7].call((Object)varName2, (Object)prefix))) continue;
                callSiteArray[8].call(candidates, (Object)varName2);
                foundMatch = bl = true;
            }
        }
        String varName3 = null;
        Iterator iterator3 = (Iterator)ScriptBytecodeAdapter.castToType((Object)callSiteArray[9].call((Object)SPECIAL_FUNCTIONS), Iterator.class);
        if (iterator3 != null) {
            while (iterator3.hasNext()) {
                boolean bl;
                varName3 = ShortTypeHandling.castToString(iterator3.next());
                if (!DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[10].call((Object)varName3, (Object)prefix))) continue;
                callSiteArray[11].call(candidates, (Object)varName3);
                foundMatch = bl = true;
            }
        }
        String varName4 = null;
        Iterator iterator4 = (Iterator)ScriptBytecodeAdapter.castToType((Object)callSiteArray[12].call((Object)DEFAULT_METHODS), Iterator.class);
        if (iterator4 != null) {
            while (iterator4.hasNext()) {
                boolean bl;
                varName4 = ShortTypeHandling.castToString(iterator4.next());
                if (!DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[13].call((Object)varName4, (Object)prefix))) continue;
                callSiteArray[14].call(candidates, (Object)varName4);
                foundMatch = bl = true;
            }
        }
        return foundMatch;
    }

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != KeywordSyntaxCompleter.class) {
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
        String[] stringArray = new String[]{"abstract", "assert", "boolean", "break", "byte", "case", "char", "class", "continue", "def", "default", "do", "double", "else", "enum", "final", "float", "int", "interface", "long", "new", "private", "protected", "public", "return", "short", "static", "synchronized", "throw", "throws", "transient", "var", "void", "volatile"};
        KEYWORDS = stringArray;
        String[] stringArray2 = new String[]{"true", "false", "this", "super", "null"};
        VALUE_KEYWORDS = stringArray2;
        String[] stringArray3 = new String[]{"catch (", "finally {", "for (", "if (", "switch (", "try {", "while ("};
        SPECIAL_FUNCTIONS = stringArray3;
        String[] stringArray4 = new String[]{"use (", "print ", "println ", "printf ", "sprintf "};
        DEFAULT_METHODS = stringArray4;
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
        stringArray[0] = "text";
        stringArray[1] = "last";
        stringArray[2] = "iterator";
        stringArray[3] = "startsWith";
        stringArray[4] = "leftShift";
        stringArray[5] = "plus";
        stringArray[6] = "iterator";
        stringArray[7] = "startsWith";
        stringArray[8] = "leftShift";
        stringArray[9] = "iterator";
        stringArray[10] = "startsWith";
        stringArray[11] = "leftShift";
        stringArray[12] = "iterator";
        stringArray[13] = "startsWith";
        stringArray[14] = "leftShift";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[15];
        KeywordSyntaxCompleter.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(KeywordSyntaxCompleter.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = KeywordSyntaxCompleter.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }
}

