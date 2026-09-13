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
 */
package org.apache.groovy.groovysh.util;

import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import groovy.transform.Generated;
import groovy.transform.Internal;
import java.beans.Transient;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import java.security.Permission;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;

public class NoExitSecurityManager
extends SecurityManager
implements GroovyObject {
    private final SecurityManager parent;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private transient /* synthetic */ MetaClass metaClass;
    private static /* synthetic */ SoftReference $callSiteArray;

    public NoExitSecurityManager(SecurityManager parent) {
        SecurityManager securityManager;
        MetaClass metaClass;
        CallSite[] callSiteArray = NoExitSecurityManager.$getCallSiteArray();
        this.metaClass = metaClass = this.$getStaticMetaClass();
        this.parent = securityManager = parent;
    }

    public NoExitSecurityManager() {
        CallSite[] callSiteArray = NoExitSecurityManager.$getCallSiteArray();
        this((SecurityManager)ScriptBytecodeAdapter.castToType((Object)callSiteArray[0].call(System.class), SecurityManager.class));
    }

    @Override
    public void checkPermission(Permission perm) {
        CallSite[] callSiteArray = NoExitSecurityManager.$getCallSiteArray();
        if (ScriptBytecodeAdapter.compareNotEqual((Object)this.parent, null)) {
            callSiteArray[1].call((Object)this.parent, (Object)perm);
        }
    }

    @Override
    public void checkExit(int code) {
        CallSite[] callSiteArray = NoExitSecurityManager.$getCallSiteArray();
        throw (Throwable)callSiteArray[2].callConstructor(SecurityException.class, (Object)"Use of System.exit() is forbidden!");
    }

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != NoExitSecurityManager.class) {
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

    public /* synthetic */ void super$2$checkExit(int n) {
        super.checkExit(n);
    }

    public /* synthetic */ void super$2$checkPermission(Permission permission) {
        super.checkPermission(permission);
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
        stringArray[0] = "getSecurityManager";
        stringArray[1] = "checkPermission";
        stringArray[2] = "<$constructor$>";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[3];
        NoExitSecurityManager.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(NoExitSecurityManager.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = NoExitSecurityManager.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }
}

