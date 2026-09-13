/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.Closure
 *  groovy.lang.GroovyObject
 *  groovy.lang.MetaClass
 *  groovy.transform.Generated
 *  groovy.transform.Internal
 *  groovy.util.Node
 *  groovy.util.NodeList
 *  org.codehaus.groovy.reflection.ClassInfo
 *  org.codehaus.groovy.runtime.GeneratedClosure
 *  org.codehaus.groovy.runtime.ScriptBytecodeAdapter
 *  org.codehaus.groovy.runtime.callsite.CallSite
 *  org.codehaus.groovy.runtime.callsite.CallSiteArray
 *  org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation
 *  org.codehaus.groovy.runtime.typehandling.ShortTypeHandling
 */
package org.apache.groovy.groovysh.completion;

import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import groovy.transform.Generated;
import groovy.transform.Internal;
import groovy.util.Node;
import groovy.util.NodeList;
import java.beans.Transient;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.GeneratedClosure;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;
import org.codehaus.groovy.runtime.typehandling.ShortTypeHandling;

public class NavigablePropertiesCompleter
implements GroovyObject {
    private static final Pattern NO_CONTROL_CHARS_PATTERN;
    private static final Pattern INVALID_CHAR_FOR_IDENTIFIER_PATTERN;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private transient /* synthetic */ MetaClass metaClass;
    private static /* synthetic */ SoftReference $callSiteArray;

    @Generated
    public NavigablePropertiesCompleter() {
        MetaClass metaClass;
        CallSite[] callSiteArray = NavigablePropertiesCompleter.$getCallSiteArray();
        this.metaClass = metaClass = this.$getStaticMetaClass();
    }

    public void addCompletions(Object instance, String prefix, Set<CharSequence> candidates) {
        CallSite[] callSiteArray = NavigablePropertiesCompleter.$getCallSiteArray();
        if (ScriptBytecodeAdapter.compareEqual((Object)instance, null)) {
            return;
        }
        callSiteArray[0].callCurrent((GroovyObject)this, instance, (Object)prefix, candidates);
    }

    public void addIndirectObjectMembers(Object instance, String prefix, Set<CharSequence> candidates) {
        CallSite[] callSiteArray = NavigablePropertiesCompleter.$getCallSiteArray();
        if (instance instanceof Map) {
            Map map = (Map)ScriptBytecodeAdapter.castToType((Object)instance, Map.class);
            callSiteArray[1].callStatic(NavigablePropertiesCompleter.class, (Object)map, (Object)prefix, candidates);
        }
        if (instance instanceof Node) {
            Node node = (Node)ScriptBytecodeAdapter.castToType((Object)instance, Node.class);
            callSiteArray[2].callCurrent((GroovyObject)this, (Object)node, (Object)prefix, candidates);
        }
        if (instance instanceof NodeList) {
            NodeList nodeList = (NodeList)ScriptBytecodeAdapter.castToType((Object)instance, NodeList.class);
            callSiteArray[3].callCurrent((GroovyObject)this, (Object)nodeList, (Object)prefix, candidates);
        }
    }

    public static void addMapProperties(Map instance, String prefix, Set<CharSequence> candidates) {
        CallSite[] callSiteArray = NavigablePropertiesCompleter.$getCallSiteArray();
        String key = null;
        public final class _addMapProperties_closure1
        extends Closure
        implements GeneratedClosure {
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _addMapProperties_closure1(Object _outerInstance, Object _thisObject) {
                CallSite[] callSiteArray = _addMapProperties_closure1.$getCallSiteArray();
                super(_outerInstance, _thisObject);
            }

            public Object doCall(Object it) {
                CallSite[] callSiteArray = _addMapProperties_closure1.$getCallSiteArray();
                return it instanceof String;
            }

            @Generated
            public Object doCall() {
                CallSite[] callSiteArray = _addMapProperties_closure1.$getCallSiteArray();
                return this.doCall(null);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (((Object)((Object)this)).getClass() != _addMapProperties_closure1.class) {
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
                String[] stringArray = new String[]{};
                return new CallSiteArray(_addMapProperties_closure1.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _addMapProperties_closure1.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        Iterator iterator = (Iterator)ScriptBytecodeAdapter.castToType((Object)callSiteArray[4].call(callSiteArray[5].call(callSiteArray[6].call((Object)instance), (Object)new _addMapProperties_closure1(NavigablePropertiesCompleter.class, NavigablePropertiesCompleter.class))), Iterator.class);
        if (iterator != null) {
            while (iterator.hasNext()) {
                key = ShortTypeHandling.castToString(iterator.next());
                if (!(DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[7].call((Object)key, (Object)NO_CONTROL_CHARS_PATTERN)) && DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[8].call((Object)key, (Object)prefix)))) continue;
                if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[9].call((Object)key, (Object)INVALID_CHAR_FOR_IDENTIFIER_PATTERN)) || DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[10].call((Object)key, (Object)"$"))) {
                    Object object = callSiteArray[11].call(callSiteArray[12].call((Object)key, (Object)"\\", (Object)"\\\\"), (Object)"'", (Object)"\\'");
                    key = ShortTypeHandling.castToString((Object)object);
                    Object object2 = callSiteArray[13].call(callSiteArray[14].call((Object)"'", (Object)key), (Object)"'");
                    key = ShortTypeHandling.castToString((Object)object2);
                }
                callSiteArray[15].call(candidates, (Object)key);
            }
        }
    }

    public void addNodeListEntries(NodeList instance, String prefix, Set<CharSequence> candidates) {
        CallSite[] callSiteArray = NavigablePropertiesCompleter.$getCallSiteArray();
        Object member = null;
        Iterator iterator = (Iterator)ScriptBytecodeAdapter.castToType((Object)callSiteArray[16].call((Object)instance), Iterator.class);
        if (iterator != null) {
            while (iterator.hasNext()) {
                member = iterator.next();
                callSiteArray[17].callCurrent((GroovyObject)this, member, (Object)prefix, candidates);
            }
        }
    }

    public void addNodeChildren(Node instance, String prefix, Set<CharSequence> candidates) {
        CallSite[] callSiteArray = NavigablePropertiesCompleter.$getCallSiteArray();
        Object child = null;
        Iterator iterator = (Iterator)ScriptBytecodeAdapter.castToType((Object)callSiteArray[18].call(callSiteArray[19].call((Object)instance)), Iterator.class);
        if (iterator != null) {
            while (iterator.hasNext()) {
                child = iterator.next();
                String member = "";
                if (child instanceof String) {
                    String string;
                    member = string = ShortTypeHandling.castToString(child);
                } else if (child instanceof Node) {
                    Object object = callSiteArray[20].call((Object)((Node)ScriptBytecodeAdapter.castToType(child, Node.class)));
                    member = ShortTypeHandling.castToString((Object)object);
                } else {
                    if (!(child instanceof NodeList)) continue;
                    Object node = null;
                    Iterator iterator2 = (Iterator)ScriptBytecodeAdapter.castToType((Object)callSiteArray[21].call((Object)((NodeList)ScriptBytecodeAdapter.castToType(child, NodeList.class))), Iterator.class);
                    if (iterator2 != null) {
                        while (iterator2.hasNext()) {
                            node = iterator2.next();
                            callSiteArray[22].callCurrent((GroovyObject)this, (Object)ScriptBytecodeAdapter.createPojoWrapper((Object)((Node)ScriptBytecodeAdapter.castToType(node, Node.class)), Node.class), (Object)prefix, candidates);
                        }
                    }
                }
                if (!DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[23].call((Object)member, (Object)prefix))) continue;
                callSiteArray[24].call(candidates, (Object)member);
            }
        }
    }

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != NavigablePropertiesCompleter.class) {
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
        Object object = ScriptBytecodeAdapter.bitwiseNegate((Object)"^[^\\p{Cntrl}]+$");
        NO_CONTROL_CHARS_PATTERN = (Pattern)ScriptBytecodeAdapter.castToType((Object)object, Pattern.class);
        Object object2 = ScriptBytecodeAdapter.bitwiseNegate((Object)"[ @#%^&\u00a7()+\\-={}\\[\\]~`\u00b4<>,.\"'/!?:;|\\\\]");
        INVALID_CHAR_FOR_IDENTIFIER_PATTERN = (Pattern)ScriptBytecodeAdapter.castToType((Object)object2, Pattern.class);
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
        stringArray[0] = "addIndirectObjectMembers";
        stringArray[1] = "addMapProperties";
        stringArray[2] = "addNodeChildren";
        stringArray[3] = "addNodeListEntries";
        stringArray[4] = "iterator";
        stringArray[5] = "findAll";
        stringArray[6] = "keySet";
        stringArray[7] = "matches";
        stringArray[8] = "startsWith";
        stringArray[9] = "find";
        stringArray[10] = "endsWith";
        stringArray[11] = "replace";
        stringArray[12] = "replace";
        stringArray[13] = "plus";
        stringArray[14] = "plus";
        stringArray[15] = "add";
        stringArray[16] = "iterator";
        stringArray[17] = "addIndirectObjectMembers";
        stringArray[18] = "iterator";
        stringArray[19] = "children";
        stringArray[20] = "name";
        stringArray[21] = "iterator";
        stringArray[22] = "addNodeChildren";
        stringArray[23] = "startsWith";
        stringArray[24] = "add";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[25];
        NavigablePropertiesCompleter.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(NavigablePropertiesCompleter.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = NavigablePropertiesCompleter.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }
}

