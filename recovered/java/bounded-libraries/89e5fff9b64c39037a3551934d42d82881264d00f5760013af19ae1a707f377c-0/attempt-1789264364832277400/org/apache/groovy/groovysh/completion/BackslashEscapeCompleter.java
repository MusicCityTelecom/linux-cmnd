/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.GroovyObject
 *  groovy.lang.MetaClass
 *  groovy.transform.Generated
 *  groovy.transform.Internal
 *  jline.console.completer.Completer
 *  jline.internal.Preconditions
 *  org.codehaus.groovy.reflection.ClassInfo
 *  org.codehaus.groovy.runtime.ScriptBytecodeAdapter
 *  org.codehaus.groovy.runtime.callsite.CallSite
 *  org.codehaus.groovy.runtime.callsite.CallSiteArray
 */
package org.apache.groovy.groovysh.completion;

import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import groovy.transform.Generated;
import groovy.transform.Internal;
import java.beans.Transient;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import java.util.List;
import jline.console.completer.Completer;
import jline.internal.Preconditions;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;

public class BackslashEscapeCompleter
implements Completer,
GroovyObject {
    private static final List<String> VALID_ESCAPEES;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private transient /* synthetic */ MetaClass metaClass;
    private static /* synthetic */ SoftReference $callSiteArray;

    @Generated
    public BackslashEscapeCompleter() {
        MetaClass metaClass;
        CallSite[] callSiteArray = BackslashEscapeCompleter.$getCallSiteArray();
        this.metaClass = metaClass = this.$getStaticMetaClass();
    }

    public int complete(String buffer, int cursor, List<CharSequence> candidates) {
        CallSite[] callSiteArray = BackslashEscapeCompleter.$getCallSiteArray();
        callSiteArray[0].callStatic(Preconditions.class, candidates);
        callSiteArray[1].call(candidates, VALID_ESCAPEES);
        return cursor;
    }

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != BackslashEscapeCompleter.class) {
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
        List list;
        VALID_ESCAPEES = list = ScriptBytecodeAdapter.createList((Object[])new Object[]{"r (return)", "n (newline)", "t (tab)", "\\ (backslash)", "' (single quote)", "\" (double quote)", "b (backspace)", "f (formfeed)", "uXXXX (unicode)"});
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
        stringArray[0] = "checkNotNull";
        stringArray[1] = "addAll";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[2];
        BackslashEscapeCompleter.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(BackslashEscapeCompleter.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = BackslashEscapeCompleter.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }
}

