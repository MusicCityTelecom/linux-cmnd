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
 *  org.codehaus.groovy.runtime.typehandling.ShortTypeHandling
 */
package org.apache.groovy.groovysh;

import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import groovy.transform.Generated;
import groovy.transform.Internal;
import java.beans.Transient;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.typehandling.ShortTypeHandling;

public final class ParseCode
implements GroovyObject {
    private static final ParseCode COMPLETE;
    private static final ParseCode INCOMPLETE;
    private static final ParseCode ERROR;
    private final int code;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private transient /* synthetic */ MetaClass metaClass;
    private static /* synthetic */ SoftReference $callSiteArray;

    private ParseCode(int code) {
        int n;
        MetaClass metaClass;
        CallSite[] callSiteArray = ParseCode.$getCallSiteArray();
        this.metaClass = metaClass = this.$getStaticMetaClass();
        this.code = n = code;
    }

    public String toString() {
        CallSite[] callSiteArray = ParseCode.$getCallSiteArray();
        return ShortTypeHandling.castToString((Object)this.code);
    }

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != ParseCode.class) {
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
        Object object = ParseCode.$getCallSiteArray()[0].callConstructor(ParseCode.class, (Object)0);
        COMPLETE = (ParseCode)ScriptBytecodeAdapter.castToType((Object)object, ParseCode.class);
        Object object2 = ParseCode.$getCallSiteArray()[1].callConstructor(ParseCode.class, (Object)1);
        INCOMPLETE = (ParseCode)ScriptBytecodeAdapter.castToType((Object)object2, ParseCode.class);
        Object object3 = ParseCode.$getCallSiteArray()[2].callConstructor(ParseCode.class, (Object)2);
        ERROR = (ParseCode)ScriptBytecodeAdapter.castToType((Object)object3, ParseCode.class);
    }

    @Generated
    public static ParseCode getCOMPLETE() {
        return COMPLETE;
    }

    @Generated
    public static ParseCode getINCOMPLETE() {
        return INCOMPLETE;
    }

    @Generated
    public static ParseCode getERROR() {
        return ERROR;
    }

    @Generated
    public final int getCode() {
        return this.code;
    }

    public /* synthetic */ String super$1$toString() {
        return super.toString();
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
        stringArray[0] = "<$constructor$>";
        stringArray[1] = "<$constructor$>";
        stringArray[2] = "<$constructor$>";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[3];
        ParseCode.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(ParseCode.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = ParseCode.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }
}

