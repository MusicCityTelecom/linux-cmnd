/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.Closure
 *  groovy.lang.GroovyObject
 *  groovy.lang.GroovyRuntimeException
 *  groovy.lang.MetaClass
 *  groovy.lang.Reference
 *  groovy.transform.Generated
 *  groovy.transform.Internal
 *  org.codehaus.groovy.reflection.ClassInfo
 *  org.codehaus.groovy.runtime.ArrayUtil
 *  org.codehaus.groovy.runtime.GStringImpl
 *  org.codehaus.groovy.runtime.GeneratedClosure
 *  org.codehaus.groovy.runtime.ScriptBytecodeAdapter
 *  org.codehaus.groovy.runtime.callsite.CallSite
 *  org.codehaus.groovy.runtime.callsite.CallSiteArray
 *  org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation
 */
package groovy.xml.streamingmarkupsupport;

import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import groovy.lang.GroovyRuntimeException;
import groovy.lang.MetaClass;
import groovy.lang.Reference;
import groovy.transform.Generated;
import groovy.transform.Internal;
import java.beans.Transient;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import java.util.Map;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.ArrayUtil;
import org.codehaus.groovy.runtime.GStringImpl;
import org.codehaus.groovy.runtime.GeneratedClosure;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;

public class AbstractStreamingBuilder
implements GroovyObject {
    private Object badTagClosure;
    private Object namespaceSetupClosure;
    private Object aliasSetupClosure;
    private Object getNamespaceClosure;
    private Object toMapStringClosure;
    private Object specialTags;
    private Object builder;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private transient /* synthetic */ MetaClass metaClass;
    private static /* synthetic */ SoftReference $callSiteArray;

    @Generated
    public AbstractStreamingBuilder() {
        MetaClass metaClass;
        CallSite[] callSiteArray = AbstractStreamingBuilder.$getCallSiteArray();
        _closure1 _closure16 = new _closure1(this, this);
        this.badTagClosure = _closure16;
        _closure2 _closure22 = new _closure2(this, this);
        this.namespaceSetupClosure = _closure22;
        _closure3 _closure32 = new _closure3(this, this);
        this.aliasSetupClosure = _closure32;
        _closure4 _closure42 = new _closure4(this, this);
        this.getNamespaceClosure = _closure42;
        _closure5 _closure52 = new _closure5(this, this);
        this.toMapStringClosure = _closure52;
        Map map = ScriptBytecodeAdapter.createMap((Object[])new Object[]{"declareNamespace", this.namespaceSetupClosure, "declareAlias", this.aliasSetupClosure, "getNamespaces", this.getNamespaceClosure});
        this.specialTags = map;
        Object var8_8 = null;
        this.builder = var8_8;
        this.metaClass = metaClass = this.$getStaticMetaClass();
    }

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != AbstractStreamingBuilder.class) {
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
    public Object getBadTagClosure() {
        return this.badTagClosure;
    }

    @Generated
    public void setBadTagClosure(Object object) {
        this.badTagClosure = object;
    }

    @Generated
    public Object getNamespaceSetupClosure() {
        return this.namespaceSetupClosure;
    }

    @Generated
    public void setNamespaceSetupClosure(Object object) {
        this.namespaceSetupClosure = object;
    }

    @Generated
    public Object getAliasSetupClosure() {
        return this.aliasSetupClosure;
    }

    @Generated
    public void setAliasSetupClosure(Object object) {
        this.aliasSetupClosure = object;
    }

    @Generated
    public Object getGetNamespaceClosure() {
        return this.getNamespaceClosure;
    }

    @Generated
    public void setGetNamespaceClosure(Object object) {
        this.getNamespaceClosure = object;
    }

    @Generated
    public Object getToMapStringClosure() {
        return this.toMapStringClosure;
    }

    @Generated
    public void setToMapStringClosure(Object object) {
        this.toMapStringClosure = object;
    }

    @Generated
    public Object getSpecialTags() {
        return this.specialTags;
    }

    @Generated
    public void setSpecialTags(Object object) {
        this.specialTags = object;
    }

    @Generated
    public Object getBuilder() {
        return this.builder;
    }

    @Generated
    public void setBuilder(Object object) {
        this.builder = object;
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[]{};
        return new CallSiteArray(AbstractStreamingBuilder.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = AbstractStreamingBuilder.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }

    public final class _closure1
    extends Closure
    implements GeneratedClosure {
        private static /* synthetic */ ClassInfo $staticClassInfo;
        public static transient /* synthetic */ boolean __$stMC;
        private static /* synthetic */ SoftReference $callSiteArray;

        public _closure1(Object _outerInstance, Object _thisObject) {
            CallSite[] callSiteArray = _closure1.$getCallSiteArray();
            super(_outerInstance, _thisObject);
        }

        public Object doCall(Object tag, Object doc, Object pendingNamespaces, Object namespaces, Object namespaceSpecificTags, Object prefix, Object ... rest) {
            CallSite[] callSiteArray = _closure1.$getCallSiteArray();
            Object uri = callSiteArray[0].call(pendingNamespaces, prefix);
            if (ScriptBytecodeAdapter.compareEqual((Object)uri, null)) {
                Object object;
                uri = object = callSiteArray[1].call(namespaces, prefix);
            }
            throw (Throwable)callSiteArray[2].callConstructor(GroovyRuntimeException.class, (Object)new GStringImpl(new Object[]{tag, uri}, new String[]{"Tag ", " is not allowed in namespace ", ""}));
        }

        @Generated
        public Object call(Object tag, Object doc, Object pendingNamespaces, Object namespaces, Object namespaceSpecificTags, Object prefix, Object ... rest) {
            CallSite[] callSiteArray = _closure1.$getCallSiteArray();
            return callSiteArray[3].callCurrent((GroovyObject)this, ArrayUtil.createArray((Object)tag, (Object)doc, (Object)pendingNamespaces, (Object)namespaces, (Object)namespaceSpecificTags, (Object)prefix, (Object)rest));
        }

        protected /* synthetic */ MetaClass $getStaticMetaClass() {
            if (((Object)((Object)this)).getClass() != _closure1.class) {
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

        private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
            stringArray[0] = "getAt";
            stringArray[1] = "getAt";
            stringArray[2] = "<$constructor$>";
            stringArray[3] = "doCall";
        }

        private static /* synthetic */ CallSiteArray $createCallSiteArray() {
            String[] stringArray = new String[4];
            _closure1.$createCallSiteArray_1(stringArray);
            return new CallSiteArray(_closure1.class, stringArray);
        }

        private static /* synthetic */ CallSite[] $getCallSiteArray() {
            CallSiteArray callSiteArray;
            if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                callSiteArray = _closure1.$createCallSiteArray();
                $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
            }
            return callSiteArray.array;
        }
    }

    public final class _closure2
    extends Closure
    implements GeneratedClosure {
        private static /* synthetic */ ClassInfo $staticClassInfo;
        public static transient /* synthetic */ boolean __$stMC;
        private static /* synthetic */ SoftReference $callSiteArray;

        public _closure2(Object _outerInstance, Object _thisObject) {
            CallSite[] callSiteArray = _closure2.$getCallSiteArray();
            super(_outerInstance, _thisObject);
        }

        /*
         * WARNING - void declaration
         */
        public Object doCall(Object doc, Object pendingNamespaces, Object namespaces, Object namespaceSpecificTags, Object prefix, Object attrs, Object ... rest) {
            void var3_3;
            Reference pendingNamespaces2 = new Reference(pendingNamespaces);
            Reference namespaces2 = new Reference((Object)var3_3);
            Reference namespaceSpecificTags2 = new Reference(namespaceSpecificTags);
            CallSite[] callSiteArray = _closure2.$getCallSiteArray();
            public final class _closure6
            extends Closure
            implements GeneratedClosure {
                private /* synthetic */ Reference namespaces;
                private /* synthetic */ Reference pendingNamespaces;
                private /* synthetic */ Reference namespaceSpecificTags;
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;
                private static /* synthetic */ SoftReference $callSiteArray;

                public _closure6(Object _outerInstance, Object _thisObject, Reference namespaces, Reference pendingNamespaces, Reference namespaceSpecificTags) {
                    Reference reference;
                    Reference reference2;
                    Reference reference3;
                    CallSite[] callSiteArray = _closure6.$getCallSiteArray();
                    super(_outerInstance, _thisObject);
                    this.namespaces = reference3 = namespaces;
                    this.pendingNamespaces = reference2 = pendingNamespaces;
                    this.namespaceSpecificTags = reference = namespaceSpecificTags;
                }

                public Object doCall(Object key, Object value) {
                    Object object;
                    CallSite[] callSiteArray = _closure6.$getCallSiteArray();
                    if (ScriptBytecodeAdapter.compareEqual((Object)key, (Object)"")) {
                        String string = ":";
                        key = string;
                    }
                    value = object = callSiteArray[0].call(value);
                    if (ScriptBytecodeAdapter.compareNotEqual((Object)callSiteArray[1].call(this.namespaces.get(), key), (Object)value)) {
                        Object object2 = value;
                        callSiteArray[2].call(this.pendingNamespaces.get(), key, object2);
                    }
                    if (!DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[3].call(this.namespaceSpecificTags.get(), value))) {
                        Object baseEntry = callSiteArray[4].call(this.namespaceSpecificTags.get(), (Object)":");
                        Object object3 = callSiteArray[5].call((Object)ScriptBytecodeAdapter.createList((Object[])new Object[]{callSiteArray[6].call(baseEntry, (Object)0), callSiteArray[7].call(baseEntry, (Object)1), ScriptBytecodeAdapter.createMap((Object[])new Object[0])}));
                        callSiteArray[8].call(this.namespaceSpecificTags.get(), value, object3);
                        return object3;
                    }
                    return null;
                }

                @Generated
                public Object call(Object key, Object value) {
                    CallSite[] callSiteArray = _closure6.$getCallSiteArray();
                    return callSiteArray[9].callCurrent((GroovyObject)this, key, value);
                }

                @Generated
                public Object getNamespaces() {
                    CallSite[] callSiteArray = _closure6.$getCallSiteArray();
                    return this.namespaces.get();
                }

                @Generated
                public Object getPendingNamespaces() {
                    CallSite[] callSiteArray = _closure6.$getCallSiteArray();
                    return this.pendingNamespaces.get();
                }

                @Generated
                public Object getNamespaceSpecificTags() {
                    CallSite[] callSiteArray = _closure6.$getCallSiteArray();
                    return this.namespaceSpecificTags.get();
                }

                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                    if (((Object)((Object)this)).getClass() != _closure6.class) {
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

                private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
                    stringArray[0] = "toString";
                    stringArray[1] = "getAt";
                    stringArray[2] = "putAt";
                    stringArray[3] = "containsKey";
                    stringArray[4] = "getAt";
                    stringArray[5] = "toArray";
                    stringArray[6] = "getAt";
                    stringArray[7] = "getAt";
                    stringArray[8] = "putAt";
                    stringArray[9] = "doCall";
                }

                private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                    String[] stringArray = new String[10];
                    _closure6.$createCallSiteArray_1(stringArray);
                    return new CallSiteArray(_closure6.class, stringArray);
                }

                private static /* synthetic */ CallSite[] $getCallSiteArray() {
                    CallSiteArray callSiteArray;
                    if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                        callSiteArray = _closure6.$createCallSiteArray();
                        $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                    }
                    return callSiteArray.array;
                }
            }
            return callSiteArray[0].call(attrs, (Object)new _closure6((Object)this, this.getThisObject(), namespaces2, pendingNamespaces2, namespaceSpecificTags2));
        }

        /*
         * WARNING - void declaration
         */
        @Generated
        public Object call(Object doc, Object pendingNamespaces, Object namespaces, Object namespaceSpecificTags, Object prefix, Object attrs, Object ... rest) {
            void var3_3;
            Reference pendingNamespaces2 = new Reference(pendingNamespaces);
            Reference namespaces2 = new Reference((Object)var3_3);
            Reference namespaceSpecificTags2 = new Reference(namespaceSpecificTags);
            CallSite[] callSiteArray = _closure2.$getCallSiteArray();
            return callSiteArray[1].callCurrent((GroovyObject)this, ArrayUtil.createArray((Object)doc, (Object)pendingNamespaces2.get(), (Object)namespaces2.get(), (Object)namespaceSpecificTags2.get(), (Object)prefix, (Object)attrs, (Object)rest));
        }

        protected /* synthetic */ MetaClass $getStaticMetaClass() {
            if (((Object)((Object)this)).getClass() != _closure2.class) {
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

        private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
            stringArray[0] = "each";
            stringArray[1] = "doCall";
        }

        private static /* synthetic */ CallSiteArray $createCallSiteArray() {
            String[] stringArray = new String[2];
            _closure2.$createCallSiteArray_1(stringArray);
            return new CallSiteArray(_closure2.class, stringArray);
        }

        private static /* synthetic */ CallSite[] $getCallSiteArray() {
            CallSiteArray callSiteArray;
            if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                callSiteArray = _closure2.$createCallSiteArray();
                $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
            }
            return callSiteArray.array;
        }
    }

    public final class _closure3
    extends Closure
    implements GeneratedClosure {
        private static /* synthetic */ ClassInfo $staticClassInfo;
        public static transient /* synthetic */ boolean __$stMC;
        private static /* synthetic */ SoftReference $callSiteArray;

        public _closure3(Object _outerInstance, Object _thisObject) {
            CallSite[] callSiteArray = _closure3.$getCallSiteArray();
            super(_outerInstance, _thisObject);
        }

        /*
         * WARNING - void declaration
         */
        public Object doCall(Object doc, Object pendingNamespaces, Object namespaces, Object namespaceSpecificTags, Object prefix, Object attrs, Object ... rest) {
            void var3_3;
            Reference pendingNamespaces2 = new Reference(pendingNamespaces);
            Reference namespaces2 = new Reference((Object)var3_3);
            Reference namespaceSpecificTags2 = new Reference(namespaceSpecificTags);
            CallSite[] callSiteArray = _closure3.$getCallSiteArray();
            public final class _closure7
            extends Closure
            implements GeneratedClosure {
                private /* synthetic */ Reference namespaces;
                private /* synthetic */ Reference namespaceSpecificTags;
                private /* synthetic */ Reference pendingNamespaces;
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;
                private static /* synthetic */ SoftReference $callSiteArray;

                public _closure7(Object _outerInstance, Object _thisObject, Reference namespaces, Reference namespaceSpecificTags, Reference pendingNamespaces) {
                    Reference reference;
                    Reference reference2;
                    Reference reference3;
                    CallSite[] callSiteArray = _closure7.$getCallSiteArray();
                    super(_outerInstance, _thisObject);
                    this.namespaces = reference3 = namespaces;
                    this.namespaceSpecificTags = reference2 = namespaceSpecificTags;
                    this.pendingNamespaces = reference = pendingNamespaces;
                }

                public Object doCall(Object key, Object value) {
                    CallSite[] callSiteArray = _closure7.$getCallSiteArray();
                    if (value instanceof Map) {
                        Reference info = new Reference(null);
                        if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[0].call(this.namespaces.get(), key))) {
                            Object object = callSiteArray[1].call(this.namespaceSpecificTags.get(), callSiteArray[2].call(this.namespaces.get(), key));
                            info.set(object);
                        } else if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[3].call(this.pendingNamespaces.get(), key))) {
                            Object object = callSiteArray[4].call(this.namespaceSpecificTags.get(), callSiteArray[5].call(this.pendingNamespaces.get(), key));
                            info.set(object);
                        } else {
                            throw (Throwable)callSiteArray[6].callConstructor(GroovyRuntimeException.class, (Object)new GStringImpl(new Object[]{key}, new String[]{"namespace prefix ", " has not been declared"}));
                        }
                        public final class _closure8
                        extends Closure
                        implements GeneratedClosure {
                            private /* synthetic */ Reference info;
                            private static /* synthetic */ ClassInfo $staticClassInfo;
                            public static transient /* synthetic */ boolean __$stMC;
                            private static /* synthetic */ SoftReference $callSiteArray;

                            public _closure8(Object _outerInstance, Object _thisObject, Reference info) {
                                Reference reference;
                                CallSite[] callSiteArray = _closure8.$getCallSiteArray();
                                super(_outerInstance, _thisObject);
                                this.info = reference = info;
                            }

                            public Object doCall(Object to, Object from) {
                                CallSite[] callSiteArray = _closure8.$getCallSiteArray();
                                Object object = callSiteArray[0].call(callSiteArray[1].call(this.info.get(), (Object)1), from);
                                callSiteArray[2].call(callSiteArray[3].call(this.info.get(), (Object)2), to, object);
                                return object;
                            }

                            @Generated
                            public Object call(Object to, Object from) {
                                CallSite[] callSiteArray = _closure8.$getCallSiteArray();
                                return callSiteArray[4].callCurrent((GroovyObject)this, to, from);
                            }

                            @Generated
                            public Object getInfo() {
                                CallSite[] callSiteArray = _closure8.$getCallSiteArray();
                                return this.info.get();
                            }

                            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                                if (((Object)((Object)this)).getClass() != _closure8.class) {
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

                            private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
                                stringArray[0] = "curry";
                                stringArray[1] = "getAt";
                                stringArray[2] = "putAt";
                                stringArray[3] = "getAt";
                                stringArray[4] = "doCall";
                            }

                            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                                String[] stringArray = new String[5];
                                _closure8.$createCallSiteArray_1(stringArray);
                                return new CallSiteArray(_closure8.class, stringArray);
                            }

                            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                                CallSiteArray callSiteArray;
                                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                                    callSiteArray = _closure8.$createCallSiteArray();
                                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                                }
                                return callSiteArray.array;
                            }
                        }
                        return callSiteArray[7].call(value, (Object)new _closure8((Object)this, this.getThisObject(), info));
                    }
                    Object info = callSiteArray[8].call(this.namespaceSpecificTags.get(), (Object)":");
                    Object object = callSiteArray[9].call(callSiteArray[10].call(info, (Object)1), value);
                    callSiteArray[11].call(callSiteArray[12].call(info, (Object)2), key, object);
                    return object;
                }

                @Generated
                public Object call(Object key, Object value) {
                    CallSite[] callSiteArray = _closure7.$getCallSiteArray();
                    return callSiteArray[13].callCurrent((GroovyObject)this, key, value);
                }

                @Generated
                public Object getNamespaces() {
                    CallSite[] callSiteArray = _closure7.$getCallSiteArray();
                    return this.namespaces.get();
                }

                @Generated
                public Object getNamespaceSpecificTags() {
                    CallSite[] callSiteArray = _closure7.$getCallSiteArray();
                    return this.namespaceSpecificTags.get();
                }

                @Generated
                public Object getPendingNamespaces() {
                    CallSite[] callSiteArray = _closure7.$getCallSiteArray();
                    return this.pendingNamespaces.get();
                }

                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                    if (((Object)((Object)this)).getClass() != _closure7.class) {
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

                private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
                    stringArray[0] = "containsKey";
                    stringArray[1] = "getAt";
                    stringArray[2] = "getAt";
                    stringArray[3] = "containsKey";
                    stringArray[4] = "getAt";
                    stringArray[5] = "getAt";
                    stringArray[6] = "<$constructor$>";
                    stringArray[7] = "each";
                    stringArray[8] = "getAt";
                    stringArray[9] = "curry";
                    stringArray[10] = "getAt";
                    stringArray[11] = "putAt";
                    stringArray[12] = "getAt";
                    stringArray[13] = "doCall";
                }

                private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                    String[] stringArray = new String[14];
                    _closure7.$createCallSiteArray_1(stringArray);
                    return new CallSiteArray(_closure7.class, stringArray);
                }

                private static /* synthetic */ CallSite[] $getCallSiteArray() {
                    CallSiteArray callSiteArray;
                    if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                        callSiteArray = _closure7.$createCallSiteArray();
                        $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                    }
                    return callSiteArray.array;
                }
            }
            return callSiteArray[0].call(attrs, (Object)new _closure7((Object)this, this.getThisObject(), namespaces2, namespaceSpecificTags2, pendingNamespaces2));
        }

        /*
         * WARNING - void declaration
         */
        @Generated
        public Object call(Object doc, Object pendingNamespaces, Object namespaces, Object namespaceSpecificTags, Object prefix, Object attrs, Object ... rest) {
            void var3_3;
            Reference pendingNamespaces2 = new Reference(pendingNamespaces);
            Reference namespaces2 = new Reference((Object)var3_3);
            Reference namespaceSpecificTags2 = new Reference(namespaceSpecificTags);
            CallSite[] callSiteArray = _closure3.$getCallSiteArray();
            return callSiteArray[1].callCurrent((GroovyObject)this, ArrayUtil.createArray((Object)doc, (Object)pendingNamespaces2.get(), (Object)namespaces2.get(), (Object)namespaceSpecificTags2.get(), (Object)prefix, (Object)attrs, (Object)rest));
        }

        protected /* synthetic */ MetaClass $getStaticMetaClass() {
            if (((Object)((Object)this)).getClass() != _closure3.class) {
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

        private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
            stringArray[0] = "each";
            stringArray[1] = "doCall";
        }

        private static /* synthetic */ CallSiteArray $createCallSiteArray() {
            String[] stringArray = new String[2];
            _closure3.$createCallSiteArray_1(stringArray);
            return new CallSiteArray(_closure3.class, stringArray);
        }

        private static /* synthetic */ CallSite[] $getCallSiteArray() {
            CallSiteArray callSiteArray;
            if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                callSiteArray = _closure3.$createCallSiteArray();
                $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
            }
            return callSiteArray.array;
        }
    }

    public final class _closure4
    extends Closure
    implements GeneratedClosure {
        private static /* synthetic */ ClassInfo $staticClassInfo;
        public static transient /* synthetic */ boolean __$stMC;
        private static /* synthetic */ SoftReference $callSiteArray;

        public _closure4(Object _outerInstance, Object _thisObject) {
            CallSite[] callSiteArray = _closure4.$getCallSiteArray();
            super(_outerInstance, _thisObject);
        }

        public Object doCall(Object doc, Object pendingNamespaces, Object namespaces, Object ... rest) {
            CallSite[] callSiteArray = _closure4.$getCallSiteArray();
            return ScriptBytecodeAdapter.createList((Object[])new Object[]{namespaces, pendingNamespaces});
        }

        @Generated
        public Object call(Object doc, Object pendingNamespaces, Object namespaces, Object ... rest) {
            CallSite[] callSiteArray = _closure4.$getCallSiteArray();
            return callSiteArray[0].callCurrent((GroovyObject)this, doc, pendingNamespaces, namespaces, (Object)rest);
        }

        protected /* synthetic */ MetaClass $getStaticMetaClass() {
            if (((Object)((Object)this)).getClass() != _closure4.class) {
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
            stringArray[0] = "doCall";
            return new CallSiteArray(_closure4.class, stringArray);
        }

        private static /* synthetic */ CallSite[] $getCallSiteArray() {
            CallSiteArray callSiteArray;
            if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                callSiteArray = _closure4.$createCallSiteArray();
                $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
            }
            return callSiteArray.array;
        }
    }

    public final class _closure5
    extends Closure
    implements GeneratedClosure {
        private static /* synthetic */ ClassInfo $staticClassInfo;
        public static transient /* synthetic */ boolean __$stMC;
        private static /* synthetic */ SoftReference $callSiteArray;

        public _closure5(Object _outerInstance, Object _thisObject) {
            CallSite[] callSiteArray = _closure5.$getCallSiteArray();
            super(_outerInstance, _thisObject);
        }

        public Object doCall(Map instruction, Object checkDoubleQuotationMarks) {
            Reference checkDoubleQuotationMarks2 = new Reference(checkDoubleQuotationMarks);
            CallSite[] callSiteArray = _closure5.$getCallSiteArray();
            Reference buf = new Reference(callSiteArray[0].callConstructor(StringBuilder.class));
            public final class _closure9
            extends Closure
            implements GeneratedClosure {
                private /* synthetic */ Reference checkDoubleQuotationMarks;
                private /* synthetic */ Reference buf;
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;
                private static /* synthetic */ SoftReference $callSiteArray;

                public _closure9(Object _outerInstance, Object _thisObject, Reference checkDoubleQuotationMarks, Reference buf) {
                    Reference reference;
                    Reference reference2;
                    CallSite[] callSiteArray = _closure9.$getCallSiteArray();
                    super(_outerInstance, _thisObject);
                    this.checkDoubleQuotationMarks = reference2 = checkDoubleQuotationMarks;
                    this.buf = reference = buf;
                }

                public Object doCall(Object name, Object value) {
                    CallSite[] callSiteArray = _closure9.$getCallSiteArray();
                    if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[0].call(this.checkDoubleQuotationMarks.get(), value))) {
                        return callSiteArray[1].call(this.buf.get(), (Object)new GStringImpl(new Object[]{name, value}, new String[]{" ", "=\"", "\""}));
                    }
                    return callSiteArray[2].call(this.buf.get(), (Object)new GStringImpl(new Object[]{name, value}, new String[]{" ", "='", "'"}));
                }

                @Generated
                public Object call(Object name, Object value) {
                    CallSite[] callSiteArray = _closure9.$getCallSiteArray();
                    return callSiteArray[3].callCurrent((GroovyObject)this, name, value);
                }

                @Generated
                public Object getCheckDoubleQuotationMarks() {
                    CallSite[] callSiteArray = _closure9.$getCallSiteArray();
                    return this.checkDoubleQuotationMarks.get();
                }

                @Generated
                public Object getBuf() {
                    CallSite[] callSiteArray = _closure9.$getCallSiteArray();
                    return this.buf.get();
                }

                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                    if (((Object)((Object)this)).getClass() != _closure9.class) {
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

                private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
                    stringArray[0] = "call";
                    stringArray[1] = "append";
                    stringArray[2] = "append";
                    stringArray[3] = "doCall";
                }

                private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                    String[] stringArray = new String[4];
                    _closure9.$createCallSiteArray_1(stringArray);
                    return new CallSiteArray(_closure9.class, stringArray);
                }

                private static /* synthetic */ CallSite[] $getCallSiteArray() {
                    CallSiteArray callSiteArray;
                    if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                        callSiteArray = _closure9.$createCallSiteArray();
                        $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                    }
                    return callSiteArray.array;
                }
            }
            callSiteArray[1].call((Object)instruction, (Object)new _closure9((Object)this, this.getThisObject(), checkDoubleQuotationMarks2, buf));
            return callSiteArray[2].call(buf.get());
        }

        @Generated
        public Object call(Map instruction, Object checkDoubleQuotationMarks) {
            Reference checkDoubleQuotationMarks2 = new Reference(checkDoubleQuotationMarks);
            CallSite[] callSiteArray = _closure5.$getCallSiteArray();
            return callSiteArray[3].callCurrent((GroovyObject)this, (Object)instruction, checkDoubleQuotationMarks2.get());
        }

        @Generated
        public Object doCall(Map instruction) {
            CallSite[] callSiteArray = _closure5.$getCallSiteArray();
            public final class _closure10
            extends Closure
            implements GeneratedClosure {
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;
                private static /* synthetic */ SoftReference $callSiteArray;

                public _closure10(Object _outerInstance, Object _thisObject) {
                    CallSite[] callSiteArray = _closure10.$getCallSiteArray();
                    super(_outerInstance, _thisObject);
                }

                public Object doCall(Object value) {
                    CallSite[] callSiteArray = _closure10.$getCallSiteArray();
                    return !DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[0].call(callSiteArray[1].call(value), (Object)"\""));
                }

                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                    if (((Object)((Object)this)).getClass() != _closure10.class) {
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

                private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
                    stringArray[0] = "contains";
                    stringArray[1] = "toString";
                }

                private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                    String[] stringArray = new String[2];
                    _closure10.$createCallSiteArray_1(stringArray);
                    return new CallSiteArray(_closure10.class, stringArray);
                }

                private static /* synthetic */ CallSite[] $getCallSiteArray() {
                    CallSiteArray callSiteArray;
                    if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                        callSiteArray = _closure10.$createCallSiteArray();
                        $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                    }
                    return callSiteArray.array;
                }
            }
            return this.doCall(instruction, (Object)new _closure10((Object)this, this.getThisObject()));
        }

        protected /* synthetic */ MetaClass $getStaticMetaClass() {
            if (((Object)((Object)this)).getClass() != _closure5.class) {
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

        private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
            stringArray[0] = "<$constructor$>";
            stringArray[1] = "each";
            stringArray[2] = "toString";
            stringArray[3] = "doCall";
        }

        private static /* synthetic */ CallSiteArray $createCallSiteArray() {
            String[] stringArray = new String[4];
            _closure5.$createCallSiteArray_1(stringArray);
            return new CallSiteArray(_closure5.class, stringArray);
        }

        private static /* synthetic */ CallSite[] $getCallSiteArray() {
            CallSiteArray callSiteArray;
            if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                callSiteArray = _closure5.$createCallSiteArray();
                $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
            }
            return callSiteArray.array;
        }
    }
}

