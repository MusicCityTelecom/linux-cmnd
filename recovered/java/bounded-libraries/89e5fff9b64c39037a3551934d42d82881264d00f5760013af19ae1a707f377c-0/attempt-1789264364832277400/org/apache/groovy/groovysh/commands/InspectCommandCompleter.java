/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.Binding
 *  groovy.lang.Closure
 *  groovy.lang.GroovyObject
 *  groovy.lang.MetaClass
 *  groovy.lang.Reference
 *  groovy.transform.Generated
 *  org.codehaus.groovy.reflection.ClassInfo
 *  org.codehaus.groovy.runtime.GeneratedClosure
 *  org.codehaus.groovy.runtime.ScriptBytecodeAdapter
 *  org.codehaus.groovy.runtime.callsite.CallSite
 *  org.codehaus.groovy.runtime.callsite.CallSiteArray
 *  org.codehaus.groovy.runtime.powerassert.AssertionRenderer
 *  org.codehaus.groovy.runtime.powerassert.ValueRecorder
 *  org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation
 */
package org.apache.groovy.groovysh.commands;

import groovy.lang.Binding;
import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import groovy.lang.Reference;
import groovy.transform.Generated;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import java.util.SortedSet;
import java.util.TreeSet;
import org.apache.groovy.groovysh.util.SimpleCompleter;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.GeneratedClosure;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.powerassert.AssertionRenderer;
import org.codehaus.groovy.runtime.powerassert.ValueRecorder;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;

public class InspectCommandCompleter
extends SimpleCompleter {
    private final Binding binding;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private static /* synthetic */ SoftReference $callSiteArray;

    public InspectCommandCompleter(Binding binding) {
        Binding binding2;
        CallSite[] callSiteArray = InspectCommandCompleter.$getCallSiteArray();
        ValueRecorder valueRecorder = new ValueRecorder();
        try {
            Binding binding3 = binding;
            valueRecorder.record((Object)binding3, 8);
            if (DefaultTypeTransformation.booleanUnbox((Object)binding3)) {
                valueRecorder.clear();
            } else {
                ScriptBytecodeAdapter.assertFailed((Object)AssertionRenderer.render((String)"assert binding", (ValueRecorder)valueRecorder), null);
            }
        }
        catch (Throwable throwable) {
            valueRecorder.clear();
            throw throwable;
        }
        callSiteArray[0].callCurrent((GroovyObject)this, (Object)false);
        this.binding = binding2 = binding;
    }

    @Override
    public SortedSet<String> getCandidates() {
        CallSite[] callSiteArray = InspectCommandCompleter.$getCallSiteArray();
        Reference set = new Reference((Object)((SortedSet)ScriptBytecodeAdapter.castToType((Object)callSiteArray[1].callConstructor(TreeSet.class), SortedSet.class)));
        public final class _getCandidates_closure1
        extends Closure
        implements GeneratedClosure {
            private /* synthetic */ Reference set;
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _getCandidates_closure1(Object _outerInstance, Object _thisObject, Reference set) {
                Reference reference;
                CallSite[] callSiteArray = _getCandidates_closure1.$getCallSiteArray();
                super(_outerInstance, _thisObject);
                this.set = reference = set;
            }

            public Object doCall(Object it) {
                CallSite[] callSiteArray = _getCandidates_closure1.$getCallSiteArray();
                return callSiteArray[0].call(this.set.get(), it);
            }

            @Generated
            public SortedSet getSet() {
                CallSite[] callSiteArray = _getCandidates_closure1.$getCallSiteArray();
                return (SortedSet)ScriptBytecodeAdapter.castToType((Object)this.set.get(), SortedSet.class);
            }

            @Generated
            public Object doCall() {
                CallSite[] callSiteArray = _getCandidates_closure1.$getCallSiteArray();
                return this.doCall(null);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (((Object)((Object)this)).getClass() != _getCandidates_closure1.class) {
                    return ScriptBytecodeAdapter.initMetaClass((Object)((Object)this));
                }
                ClassInfo classInfo = $staticClassInfo;
                if (classInfo == null) {
                    $staticClassInfo = classInfo = ClassInfo.getClassInfo(((Object)((Object)this)).getClass());
                }
                return classInfo.getMetaClass();
            }

            public /* synthetic */ MethodHandles.Lookup $getLookup() {
                return MethodHandles.lookup();
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[1];
                stringArray[0] = "leftShift";
                return new CallSiteArray(_getCandidates_closure1.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _getCandidates_closure1.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        callSiteArray[2].call(callSiteArray[3].call(callSiteArray[4].callGroovyObjectGetProperty((Object)this.binding)), (Object)new _getCandidates_closure1(this, this, set));
        return (SortedSet)set.get();
    }

    @Override
    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != InspectCommandCompleter.class) {
            return ScriptBytecodeAdapter.initMetaClass((Object)this);
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

    public /* synthetic */ MetaClass super$2$$getStaticMetaClass() {
        return super.$getStaticMetaClass();
    }

    public /* synthetic */ SortedSet super$2$getCandidates() {
        return super.getCandidates();
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
        stringArray[0] = "setWithBlank";
        stringArray[1] = "<$constructor$>";
        stringArray[2] = "each";
        stringArray[3] = "keySet";
        stringArray[4] = "variables";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[5];
        InspectCommandCompleter.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(InspectCommandCompleter.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = InspectCommandCompleter.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }
}

