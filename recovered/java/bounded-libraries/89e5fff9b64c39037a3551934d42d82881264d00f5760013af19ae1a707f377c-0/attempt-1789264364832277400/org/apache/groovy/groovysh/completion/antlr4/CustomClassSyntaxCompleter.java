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
import org.apache.groovy.groovysh.Groovysh;
import org.apache.groovy.groovysh.completion.antlr4.IdentifierCompleter;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;
import org.codehaus.groovy.runtime.typehandling.ShortTypeHandling;

public class CustomClassSyntaxCompleter
implements IdentifierCompleter,
GroovyObject {
    private final Groovysh shell;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private transient /* synthetic */ MetaClass metaClass;
    private static /* synthetic */ SoftReference $callSiteArray;

    public CustomClassSyntaxCompleter(Groovysh shell) {
        Groovysh groovysh;
        MetaClass metaClass;
        CallSite[] callSiteArray = CustomClassSyntaxCompleter.$getCallSiteArray();
        this.metaClass = metaClass = this.$getStaticMetaClass();
        this.shell = groovysh = shell;
    }

    @Override
    public boolean complete(List<Token> tokens, List<CharSequence> candidates) {
        CallSite[] callSiteArray = CustomClassSyntaxCompleter.$getCallSiteArray();
        String prefix = ShortTypeHandling.castToString((Object)callSiteArray[0].callGetProperty(callSiteArray[1].call(tokens)));
        boolean foundMatch = false;
        Class[] classes = (Class[])ScriptBytecodeAdapter.castToType((Object)callSiteArray[2].callGetProperty(callSiteArray[3].callGetProperty(callSiteArray[4].callGroovyObjectGetProperty((Object)this.shell))), Class[].class);
        if (ScriptBytecodeAdapter.compareGreaterThan((Object)callSiteArray[5].call((Object)classes), (Object)0)) {
            List classnames = (List)ScriptBytecodeAdapter.castToType((Object)ScriptBytecodeAdapter.getPropertySpreadSafe(CustomClassSyntaxCompleter.class, (Object)classes, (String)"name"), List.class);
            String varName = null;
            Iterator iterator = (Iterator)ScriptBytecodeAdapter.castToType((Object)callSiteArray[6].call((Object)classnames), Iterator.class);
            if (iterator != null) {
                while (iterator.hasNext()) {
                    boolean bl;
                    varName = ShortTypeHandling.castToString(iterator.next());
                    if (!DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[7].call((Object)varName, (Object)prefix))) continue;
                    callSiteArray[8].call(candidates, (Object)varName);
                    foundMatch = bl = true;
                }
            }
        }
        return foundMatch;
    }

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != CustomClassSyntaxCompleter.class) {
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
        stringArray[0] = "text";
        stringArray[1] = "last";
        stringArray[2] = "loadedClasses";
        stringArray[3] = "classLoader";
        stringArray[4] = "interp";
        stringArray[5] = "size";
        stringArray[6] = "iterator";
        stringArray[7] = "startsWith";
        stringArray[8] = "leftShift";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[9];
        CustomClassSyntaxCompleter.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(CustomClassSyntaxCompleter.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = CustomClassSyntaxCompleter.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }
}

