/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.GroovyObject
 *  groovy.lang.MetaClass
 *  groovy.transform.Generated
 *  groovy.transform.Internal
 *  org.codehaus.groovy.control.CompilerConfiguration
 *  org.codehaus.groovy.reflection.ClassInfo
 *  org.codehaus.groovy.runtime.BytecodeInterface8
 *  org.codehaus.groovy.runtime.ScriptBytecodeAdapter
 *  org.codehaus.groovy.runtime.callsite.CallSite
 *  org.codehaus.groovy.runtime.callsite.CallSiteArray
 *  org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation
 *  org.codehaus.groovy.vmplugin.VMPlugin
 */
package org.apache.groovy.groovysh.util;

import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import groovy.transform.Generated;
import groovy.transform.Internal;
import java.beans.Transient;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import org.apache.groovy.groovysh.util.NoExitSecurityManager;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.BytecodeInterface8;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;
import org.codehaus.groovy.vmplugin.VMPlugin;

public class SecurityManagerUtil
implements GroovyObject {
    private final SecurityManager saved;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private transient /* synthetic */ MetaClass metaClass;
    private static /* synthetic */ SoftReference $callSiteArray;

    public SecurityManagerUtil() {
        MetaClass metaClass;
        CallSite[] callSiteArray = SecurityManagerUtil.$getCallSiteArray();
        this.metaClass = metaClass = this.$getStaticMetaClass();
        if (!BytecodeInterface8.isOrigZ() || BytecodeInterface8.disabledStandardMetaClass()) {
            if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[0].callCurrent((GroovyObject)this)) || DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[1].callCurrent((GroovyObject)this))) {
                Object object = callSiteArray[2].call(System.class);
                this.saved = (SecurityManager)ScriptBytecodeAdapter.castToType((Object)object, SecurityManager.class);
                callSiteArray[3].call(System.class, callSiteArray[4].callConstructor(NoExitSecurityManager.class));
            }
        } else if (this.explicitlyEnabled() || this.autoEnabledUntilJDK17()) {
            Object object = callSiteArray[5].call(System.class);
            this.saved = (SecurityManager)ScriptBytecodeAdapter.castToType((Object)object, SecurityManager.class);
            callSiteArray[6].call(System.class, callSiteArray[7].callConstructor(NoExitSecurityManager.class));
        }
    }

    private boolean autoEnabledUntilJDK17() {
        CallSite[] callSiteArray = SecurityManagerUtil.$getCallSiteArray();
        return !DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[8].call(CompilerConfiguration.class, callSiteArray[9].call(VMPlugin.class)));
    }

    private boolean explicitlyEnabled() {
        CallSite[] callSiteArray = SecurityManagerUtil.$getCallSiteArray();
        return ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[10].call(System.class, (Object)"java.security.manager", (Object)"disallow"), (Object)"allow");
    }

    public void close() {
        CallSite[] callSiteArray = SecurityManagerUtil.$getCallSiteArray();
        callSiteArray[11].call(System.class, (Object)this.saved);
    }

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != SecurityManagerUtil.class) {
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
        stringArray[0] = "explicitlyEnabled";
        stringArray[1] = "autoEnabledUntilJDK17";
        stringArray[2] = "getSecurityManager";
        stringArray[3] = "setSecurityManager";
        stringArray[4] = "<$constructor$>";
        stringArray[5] = "getSecurityManager";
        stringArray[6] = "setSecurityManager";
        stringArray[7] = "<$constructor$>";
        stringArray[8] = "isPostJDK18";
        stringArray[9] = "getJavaVersion";
        stringArray[10] = "getProperty";
        stringArray[11] = "setSecurityManager";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[12];
        SecurityManagerUtil.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(SecurityManagerUtil.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = SecurityManagerUtil.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }
}

