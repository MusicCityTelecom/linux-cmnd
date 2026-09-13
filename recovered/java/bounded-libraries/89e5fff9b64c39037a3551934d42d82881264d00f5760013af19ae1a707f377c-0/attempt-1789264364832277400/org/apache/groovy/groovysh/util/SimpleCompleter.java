/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.Closure
 *  groovy.lang.GroovyObject
 *  groovy.lang.MetaClass
 *  groovy.transform.Generated
 *  groovy.transform.Internal
 *  jline.console.completer.Completer
 *  org.codehaus.groovy.reflection.ClassInfo
 *  org.codehaus.groovy.runtime.BytecodeInterface8
 *  org.codehaus.groovy.runtime.FormatHelper
 *  org.codehaus.groovy.runtime.ScriptBytecodeAdapter
 *  org.codehaus.groovy.runtime.callsite.CallSite
 *  org.codehaus.groovy.runtime.callsite.CallSiteArray
 *  org.codehaus.groovy.runtime.powerassert.AssertionRenderer
 *  org.codehaus.groovy.runtime.powerassert.ValueRecorder
 *  org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation
 *  org.codehaus.groovy.runtime.typehandling.ShortTypeHandling
 */
package org.apache.groovy.groovysh.util;

import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import groovy.transform.Generated;
import groovy.transform.Internal;
import java.beans.Transient;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import jline.console.completer.Completer;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.BytecodeInterface8;
import org.codehaus.groovy.runtime.FormatHelper;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.powerassert.AssertionRenderer;
import org.codehaus.groovy.runtime.powerassert.ValueRecorder;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;
import org.codehaus.groovy.runtime.typehandling.ShortTypeHandling;

public class SimpleCompleter
implements Completer,
GroovyObject {
    private SortedSet<String> candidates;
    protected String delimiter;
    private boolean withBlank;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private transient /* synthetic */ MetaClass metaClass;
    private static /* synthetic */ SoftReference $callSiteArray;

    public SimpleCompleter(String ... candidates) {
        MetaClass metaClass;
        boolean bl;
        CallSite[] callSiteArray = SimpleCompleter.$getCallSiteArray();
        this.withBlank = bl = true;
        this.metaClass = metaClass = this.$getStaticMetaClass();
        if (BytecodeInterface8.disabledStandardMetaClass()) {
            callSiteArray[0].callCurrent((GroovyObject)this, (Object)candidates);
        } else {
            this.setCandidateStrings(candidates);
        }
    }

    public SimpleCompleter() {
        CallSite[] callSiteArray = SimpleCompleter.$getCallSiteArray();
        Object[] objectArray = new Object[]{new String[0]};
        SimpleCompleter simpleCompleter = this;
        switch (ScriptBytecodeAdapter.selectConstructorAndTransformArguments((Object[])objectArray, (int)-1, SimpleCompleter.class)) {
            case 39797: {
                Object[] objectArray2 = objectArray;
                simpleCompleter();
                break;
            }
            case 842122122: {
                Object[] objectArray2 = objectArray;
                simpleCompleter((String[])DefaultTypeTransformation.castToVargsArray((Object[])objectArray, (int)0, String[].class));
                break;
            }
            case 1692375985: {
                Object[] objectArray2 = objectArray;
                simpleCompleter((Closure)ScriptBytecodeAdapter.castToType((Object)objectArray[0], Closure.class));
                break;
            }
            default: {
                throw new IllegalArgumentException("This class has been compiled with a super class which is binary incompatible with the current super class found on classpath. You should recompile this class with the new version.");
            }
        }
    }

    public SimpleCompleter(Closure loader) {
        CallSite[] callSiteArray = SimpleCompleter.$getCallSiteArray();
        this();
        ValueRecorder valueRecorder = new ValueRecorder();
        try {
            Closure closure = loader;
            valueRecorder.record((Object)closure, 8);
            boolean bl = ScriptBytecodeAdapter.compareNotEqual((Object)closure, null);
            valueRecorder.record((Object)bl, 15);
            if (bl) {
                valueRecorder.clear();
            } else {
                ScriptBytecodeAdapter.assertFailed((Object)AssertionRenderer.render((String)"assert loader != null", (ValueRecorder)valueRecorder), null);
            }
        }
        catch (Throwable throwable) {
            valueRecorder.clear();
            throw throwable;
        }
        Object obj = callSiteArray[1].call((Object)loader);
        List list = null;
        if (obj instanceof List) {
            List list2;
            list = list2 = (List)ScriptBytecodeAdapter.castToType((Object)obj, List.class);
        }
        if (ScriptBytecodeAdapter.compareEqual(list, null)) {
            throw (Throwable)callSiteArray[2].callConstructor(IllegalStateException.class, callSiteArray[3].call((Object)"The loader closure did not return a list of candidates; found: ", obj));
        }
        Iterator iter = (Iterator)ScriptBytecodeAdapter.castToType((Object)callSiteArray[4].call((Object)list), Iterator.class);
        while (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[5].call((Object)iter))) {
            callSiteArray[6].callCurrent((GroovyObject)this, callSiteArray[7].call(FormatHelper.class, callSiteArray[8].call((Object)iter)));
        }
    }

    public void setWithBlank(boolean withBlank) {
        boolean bl;
        CallSite[] callSiteArray = SimpleCompleter.$getCallSiteArray();
        this.withBlank = bl = withBlank;
    }

    public void add(String candidate) {
        CallSite[] callSiteArray = SimpleCompleter.$getCallSiteArray();
        if (__$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            callSiteArray[9].callCurrent((GroovyObject)this, (Object)candidate);
        } else {
            this.addCandidateString(candidate);
        }
    }

    public Object leftShift(String s) {
        CallSite[] callSiteArray = SimpleCompleter.$getCallSiteArray();
        if (__$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            callSiteArray[10].callCurrent((GroovyObject)this, (Object)s);
        } else {
            this.add(s);
        }
        return null;
    }

    public int complete(String buffer, int cursor, List<CharSequence> clist) {
        CallSite[] callSiteArray = SimpleCompleter.$getCallSiteArray();
        String start = ScriptBytecodeAdapter.compareEqual((Object)buffer, null) ? "" : buffer;
        SortedSet matches = null;
        if (__$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            Object object = callSiteArray[11].call(callSiteArray[12].callCurrent((GroovyObject)this), (Object)start);
            matches = (SortedSet)ScriptBytecodeAdapter.castToType((Object)object, SortedSet.class);
        } else {
            Object object = callSiteArray[13].call(this.getCandidates(), (Object)start);
            matches = (SortedSet)ScriptBytecodeAdapter.castToType((Object)object, SortedSet.class);
        }
        Iterator i = (Iterator)ScriptBytecodeAdapter.castToType((Object)callSiteArray[14].call((Object)matches), Iterator.class);
        while (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[15].call((Object)i))) {
            String can = ShortTypeHandling.castToString((Object)callSiteArray[16].call((Object)i));
            if (!DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[17].call((Object)can, (Object)start))) break;
            String delim = this.delimiter;
            if (ScriptBytecodeAdapter.compareNotEqual((Object)delim, null)) {
                int index = DefaultTypeTransformation.intUnbox((Object)callSiteArray[18].call((Object)can, (Object)delim, (Object)cursor));
                if (index != -1) {
                    Object object = callSiteArray[19].call((Object)can, (Object)0, callSiteArray[20].call((Object)index, (Object)1));
                    can = ShortTypeHandling.castToString((Object)object);
                }
            }
            if (this.withBlank) {
                can = ShortTypeHandling.castToString((Object)callSiteArray[21].call((Object)can, (Object)" "));
            }
            callSiteArray[22].call(clist, (Object)can);
        }
        return ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[23].call(clist), (Object)0) ? -1 : 0;
    }

    public void setCandidates(SortedSet<String> candidates) {
        CallSite[] callSiteArray = SimpleCompleter.$getCallSiteArray();
        SortedSet<String> sortedSet = candidates;
        this.candidates = sortedSet;
    }

    public SortedSet<String> getCandidates() {
        CallSite[] callSiteArray = SimpleCompleter.$getCallSiteArray();
        return (SortedSet)ScriptBytecodeAdapter.castToType((Object)callSiteArray[24].call(Collections.class, this.candidates), SortedSet.class);
    }

    public void setCandidateStrings(String ... strings) {
        CallSite[] callSiteArray = SimpleCompleter.$getCallSiteArray();
        callSiteArray[25].callCurrent((GroovyObject)this, callSiteArray[26].callConstructor(TreeSet.class, callSiteArray[27].call(Arrays.class, (Object)strings)));
    }

    public void addCandidateString(String string) {
        CallSite[] callSiteArray = SimpleCompleter.$getCallSiteArray();
        if (ScriptBytecodeAdapter.compareNotEqual((Object)string, null)) {
            callSiteArray[28].call(this.candidates, (Object)string);
        }
    }

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != SimpleCompleter.class) {
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
    public boolean getWithBlank() {
        return this.withBlank;
    }

    @Generated
    public boolean isWithBlank() {
        return this.withBlank;
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
        stringArray[0] = "setCandidateStrings";
        stringArray[1] = "call";
        stringArray[2] = "<$constructor$>";
        stringArray[3] = "plus";
        stringArray[4] = "iterator";
        stringArray[5] = "hasNext";
        stringArray[6] = "add";
        stringArray[7] = "toString";
        stringArray[8] = "next";
        stringArray[9] = "addCandidateString";
        stringArray[10] = "add";
        stringArray[11] = "tailSet";
        stringArray[12] = "getCandidates";
        stringArray[13] = "tailSet";
        stringArray[14] = "iterator";
        stringArray[15] = "hasNext";
        stringArray[16] = "next";
        stringArray[17] = "startsWith";
        stringArray[18] = "indexOf";
        stringArray[19] = "substring";
        stringArray[20] = "plus";
        stringArray[21] = "plus";
        stringArray[22] = "add";
        stringArray[23] = "size";
        stringArray[24] = "unmodifiableSortedSet";
        stringArray[25] = "setCandidates";
        stringArray[26] = "<$constructor$>";
        stringArray[27] = "asList";
        stringArray[28] = "add";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[29];
        SimpleCompleter.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(SimpleCompleter.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = SimpleCompleter.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }
}

