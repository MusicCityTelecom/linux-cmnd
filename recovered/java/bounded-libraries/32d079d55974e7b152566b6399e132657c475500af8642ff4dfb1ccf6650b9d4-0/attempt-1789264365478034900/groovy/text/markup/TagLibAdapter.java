/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.Closure
 *  groovy.lang.GroovyObject
 *  groovy.lang.MetaClass
 *  groovy.lang.MissingMethodException
 *  groovy.transform.Generated
 *  groovy.transform.Internal
 *  org.codehaus.groovy.reflection.ClassInfo
 *  org.codehaus.groovy.runtime.GStringImpl
 *  org.codehaus.groovy.runtime.ScriptBytecodeAdapter
 *  org.codehaus.groovy.runtime.callsite.CallSite
 *  org.codehaus.groovy.runtime.callsite.CallSiteArray
 *  org.codehaus.groovy.runtime.typehandling.ShortTypeHandling
 */
package groovy.text.markup;

import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import groovy.lang.MissingMethodException;
import groovy.text.markup.BaseTemplate;
import groovy.transform.Generated;
import groovy.transform.Internal;
import java.beans.Transient;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import java.util.Iterator;
import java.util.List;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.GStringImpl;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.typehandling.ShortTypeHandling;

public class TagLibAdapter
implements GroovyObject {
    private final BaseTemplate template;
    private final List<Object> tagLibs;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private transient /* synthetic */ MetaClass metaClass;
    private static /* synthetic */ ClassInfo $staticClassInfo$;
    private static /* synthetic */ SoftReference $callSiteArray;

    public TagLibAdapter(BaseTemplate tpl) {
        BaseTemplate baseTemplate;
        MetaClass metaClass;
        List list;
        CallSite[] callSiteArray = TagLibAdapter.$getCallSiteArray();
        this.tagLibs = list = ScriptBytecodeAdapter.createList((Object[])new Object[0]);
        this.metaClass = metaClass = this.$getStaticMetaClass();
        this.template = baseTemplate = tpl;
    }

    public void registerTagLib(Class tagLibClass) {
        CallSite[] callSiteArray = TagLibAdapter.$getCallSiteArray();
        callSiteArray[0].call(this.tagLibs, callSiteArray[1].call((Object)tagLibClass));
    }

    public void registerTagLib(Object tagLib) {
        CallSite[] callSiteArray = TagLibAdapter.$getCallSiteArray();
        callSiteArray[2].call(this.tagLibs, tagLib);
    }

    public Object methodMissing(String name, Object args) {
        CallSite[] callSiteArray = TagLibAdapter.$getCallSiteArray();
        Object tagLib = null;
        Iterator iterator = (Iterator)ScriptBytecodeAdapter.castToType((Object)callSiteArray[3].call(this.tagLibs), Iterator.class);
        if (iterator != null) {
            while (iterator.hasNext()) {
                tagLib = iterator.next();
                Object p = ScriptBytecodeAdapter.getProperty(TagLibAdapter.class, tagLib, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})));
                if (!(p instanceof Closure)) continue;
                Object clone = callSiteArray[4].call(p, (Object)this.template, (Object)this.template, (Object)this.template);
                return callSiteArray[5].call(clone, ScriptBytecodeAdapter.despreadList((Object[])new Object[0], (Object[])new Object[]{args}, (int[])new int[]{0}));
            }
        }
        throw (Throwable)callSiteArray[6].callConstructor(MissingMethodException.class, (Object)name, TagLibAdapter.class, args);
    }

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != TagLibAdapter.class) {
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

    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
        stringArray[0] = "add";
        stringArray[1] = "newInstance";
        stringArray[2] = "add";
        stringArray[3] = "iterator";
        stringArray[4] = "rehydrate";
        stringArray[5] = "call";
        stringArray[6] = "<$constructor$>";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[7];
        TagLibAdapter.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(TagLibAdapter.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = TagLibAdapter.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }
}

