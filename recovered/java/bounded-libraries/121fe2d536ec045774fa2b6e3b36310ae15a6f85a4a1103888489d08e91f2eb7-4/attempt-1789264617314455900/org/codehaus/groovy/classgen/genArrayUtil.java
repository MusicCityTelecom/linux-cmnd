/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.classgen;

import groovy.lang.Binding;
import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import groovy.lang.Script;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import java.util.Iterator;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.BytecodeInterface8;
import org.codehaus.groovy.runtime.GStringImpl;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;

public class genArrayUtil
extends Script {
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private static /* synthetic */ SoftReference $callSiteArray;

    public genArrayUtil() {
        CallSite[] callSiteArray = genArrayUtil.$getCallSiteArray();
    }

    public genArrayUtil(Binding context) {
        CallSite[] callSiteArray = genArrayUtil.$getCallSiteArray();
        super(context);
    }

    public static void main(String ... args) {
        CallSite[] callSiteArray = genArrayUtil.$getCallSiteArray();
        callSiteArray[0].callStatic(InvokerHelper.class, genArrayUtil.class, args);
    }

    @Override
    public Object run() {
        CallSite[] callSiteArray = genArrayUtil.$getCallSiteArray();
        if (__$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            return callSiteArray[1].callCurrent((GroovyObject)this, new GStringImpl(new Object[]{callSiteArray[2].callCurrent(this)}, new String[]{"\n\npublic class ArrayUtil {\n   ", "\n}\n\n"}));
        }
        return callSiteArray[3].callCurrent((GroovyObject)this, new GStringImpl(new Object[]{this.genMethods()}, new String[]{"\n\npublic class ArrayUtil {\n   ", "\n}\n\n"}));
    }

    public Object genMethods() {
        CallSite[] callSiteArray = genArrayUtil.$getCallSiteArray();
        String res = "";
        Object i = null;
        Iterator iterator = (Iterator)ScriptBytecodeAdapter.castToType(callSiteArray[4].call(ScriptBytecodeAdapter.createRange(1, 250, false, false)), Iterator.class);
        if (iterator != null) {
            while (iterator.hasNext()) {
                i = iterator.next();
                callSiteArray[5].call((Object)res, callSiteArray[6].call((Object)"\n\n", callSiteArray[7].callCurrent((GroovyObject)this, (Object)i)));
            }
        }
        return res;
    }

    public Object genMethod(int paramNum) {
        CallSite[] callSiteArray = genArrayUtil.$getCallSiteArray();
        Object res = "public static Object [] createArray (";
        Object k = null;
        Iterator iterator = (Iterator)ScriptBytecodeAdapter.castToType(callSiteArray[8].call(ScriptBytecodeAdapter.createRange(0, paramNum, false, true)), Iterator.class);
        if (iterator != null) {
            while (iterator.hasNext()) {
                k = iterator.next();
                callSiteArray[9].call(res, callSiteArray[10].call((Object)"Object arg", (Object)k));
                if (!ScriptBytecodeAdapter.compareNotEqual(k, callSiteArray[11].call((Object)paramNum, 1))) continue;
                res = callSiteArray[12].call(res, ", ");
            }
        }
        res = callSiteArray[13].call(res, ") {\n");
        res = callSiteArray[14].call(res, "return new Object [] {\n");
        Object k2 = null;
        Iterator iterator2 = (Iterator)ScriptBytecodeAdapter.castToType(callSiteArray[15].call(ScriptBytecodeAdapter.createRange(0, paramNum, false, true)), Iterator.class);
        if (iterator2 != null) {
            while (iterator2.hasNext()) {
                k2 = iterator2.next();
                callSiteArray[16].call(res, callSiteArray[17].call((Object)"arg", (Object)k2));
                if (!ScriptBytecodeAdapter.compareNotEqual(k2, callSiteArray[18].call((Object)paramNum, 1))) continue;
                res = callSiteArray[19].call(res, ", ");
            }
        }
        res = callSiteArray[20].call(res, "};\n");
        res = callSiteArray[21].call(res, "}");
        return res;
    }

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != genArrayUtil.class) {
            return ScriptBytecodeAdapter.initMetaClass(this);
        }
        ClassInfo classInfo = $staticClassInfo;
        if (classInfo == null) {
            $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
        }
        return classInfo.getMetaClass();
    }

    public static /* synthetic */ MethodHandles.Lookup $getLookup() {
        return MethodHandles.lookup();
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
        stringArray[0] = "runScript";
        stringArray[1] = "print";
        stringArray[2] = "genMethods";
        stringArray[3] = "print";
        stringArray[4] = "iterator";
        stringArray[5] = "plus";
        stringArray[6] = "plus";
        stringArray[7] = "genMethod";
        stringArray[8] = "iterator";
        stringArray[9] = "plus";
        stringArray[10] = "plus";
        stringArray[11] = "minus";
        stringArray[12] = "plus";
        stringArray[13] = "plus";
        stringArray[14] = "plus";
        stringArray[15] = "iterator";
        stringArray[16] = "plus";
        stringArray[17] = "plus";
        stringArray[18] = "minus";
        stringArray[19] = "plus";
        stringArray[20] = "plus";
        stringArray[21] = "plus";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[22];
        genArrayUtil.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(genArrayUtil.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = genArrayUtil.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }
}

