/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.Buildable
 *  groovy.lang.Closure
 *  groovy.lang.GroovyObject
 *  groovy.lang.GroovyRuntimeException
 *  groovy.lang.MetaClass
 *  groovy.lang.Reference
 *  groovy.transform.Generated
 *  org.codehaus.groovy.reflection.ClassInfo
 *  org.codehaus.groovy.runtime.ArrayUtil
 *  org.codehaus.groovy.runtime.BytecodeInterface8
 *  org.codehaus.groovy.runtime.GStringImpl
 *  org.codehaus.groovy.runtime.GeneratedClosure
 *  org.codehaus.groovy.runtime.ScriptBytecodeAdapter
 *  org.codehaus.groovy.runtime.callsite.CallSite
 *  org.codehaus.groovy.runtime.callsite.CallSiteArray
 *  org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation
 *  org.codehaus.groovy.runtime.typehandling.ShortTypeHandling
 */
package groovy.xml;

import groovy.lang.Buildable;
import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import groovy.lang.GroovyRuntimeException;
import groovy.lang.MetaClass;
import groovy.lang.Reference;
import groovy.transform.Generated;
import groovy.xml.streamingmarkupsupport.AbstractStreamingBuilder;
import groovy.xml.streamingmarkupsupport.BaseMarkupBuilder;
import groovy.xml.streamingmarkupsupport.StreamingMarkupWriter;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import java.util.List;
import java.util.Map;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.ArrayUtil;
import org.codehaus.groovy.runtime.BytecodeInterface8;
import org.codehaus.groovy.runtime.GStringImpl;
import org.codehaus.groovy.runtime.GeneratedClosure;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;
import org.codehaus.groovy.runtime.typehandling.ShortTypeHandling;

public class StreamingMarkupBuilder
extends AbstractStreamingBuilder {
    private boolean useDoubleQuotes;
    private boolean expandEmptyElements;
    private Object pendingStack;
    private Object commentClosure;
    private Object piClosure;
    private Object declarationClosure;
    private Object noopClosure;
    private Object unescapedClosure;
    private Object tagClosure;
    private Object builder;
    private Object encoding;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private static /* synthetic */ SoftReference $callSiteArray;

    public StreamingMarkupBuilder() {
        Object object;
        boolean bl;
        boolean bl2;
        CallSite[] callSiteArray = StreamingMarkupBuilder.$getCallSiteArray();
        this.useDoubleQuotes = bl2 = false;
        this.expandEmptyElements = bl = false;
        List list = ScriptBytecodeAdapter.createList((Object[])new Object[0]);
        this.pendingStack = list;
        _closure1 _closure16 = new _closure1(this, this);
        this.commentClosure = _closure16;
        _closure2 _closure22 = new _closure2(this, this);
        this.piClosure = _closure22;
        _closure3 _closure32 = new _closure3(this, this);
        this.declarationClosure = _closure32;
        _closure4 _closure42 = new _closure4(this, this);
        this.noopClosure = _closure42;
        _closure5 _closure52 = new _closure5(this, this);
        this.unescapedClosure = _closure52;
        _closure6 _closure62 = new _closure6(this, this);
        this.tagClosure = _closure62;
        Object var11_11 = null;
        this.builder = var11_11;
        Object var12_12 = null;
        this.encoding = var12_12;
        callSiteArray[0].call(callSiteArray[1].callGroovyObjectGetProperty((Object)this), (Object)ScriptBytecodeAdapter.createMap((Object[])new Object[]{"yield", this.noopClosure, "yieldUnescaped", this.unescapedClosure, "xmlDeclaration", this.declarationClosure, "comment", this.commentClosure, "pi", this.piClosure}));
        Map nsSpecificTags = ScriptBytecodeAdapter.createMap((Object[])new Object[]{":", ScriptBytecodeAdapter.createList((Object[])new Object[]{this.tagClosure, this.tagClosure, ScriptBytecodeAdapter.createMap((Object[])new Object[0])}), "http://www.w3.org/XML/1998/namespace", ScriptBytecodeAdapter.createList((Object[])new Object[]{this.tagClosure, this.tagClosure, ScriptBytecodeAdapter.createMap((Object[])new Object[0])}), "http://www.codehaus.org/Groovy/markup/keywords", ScriptBytecodeAdapter.createList((Object[])new Object[]{callSiteArray[2].callGroovyObjectGetProperty((Object)this), this.tagClosure, callSiteArray[3].callGroovyObjectGetProperty((Object)this)})});
        this.builder = object = callSiteArray[4].callConstructor(BaseMarkupBuilder.class, (Object)nsSpecificTags);
    }

    public Object getQt() {
        CallSite[] callSiteArray = StreamingMarkupBuilder.$getCallSiteArray();
        return this.useDoubleQuotes ? "\"" : "'";
    }

    public Object bind(Object closure) {
        CallSite[] callSiteArray = StreamingMarkupBuilder.$getCallSiteArray();
        Reference boundClosure = new Reference(callSiteArray[5].call(this.builder, closure));
        Reference enc = new Reference(this.encoding);
        public final class _bind_closure7
        extends Closure
        implements GeneratedClosure {
            private /* synthetic */ Reference enc;
            private /* synthetic */ Reference boundClosure;
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _bind_closure7(Object _outerInstance, Object _thisObject, Reference enc, Reference boundClosure) {
                Reference reference;
                Reference reference2;
                CallSite[] callSiteArray = _bind_closure7.$getCallSiteArray();
                super(_outerInstance, _thisObject);
                this.enc = reference2 = enc;
                this.boundClosure = reference = boundClosure;
            }

            public Object doCall(Object out) {
                Object object;
                CallSite[] callSiteArray = _bind_closure7.$getCallSiteArray();
                out = object = callSiteArray[0].callConstructor(StreamingMarkupWriter.class, out, this.enc.get(), callSiteArray[1].callGroovyObjectGetProperty((Object)this));
                Object object2 = out;
                ScriptBytecodeAdapter.setProperty((Object)object2, null, (Object)this.boundClosure.get(), (String)"trigger");
                return callSiteArray[2].call(out);
            }

            @Generated
            public Object getEnc() {
                CallSite[] callSiteArray = _bind_closure7.$getCallSiteArray();
                return this.enc.get();
            }

            @Generated
            public Object getBoundClosure() {
                CallSite[] callSiteArray = _bind_closure7.$getCallSiteArray();
                return this.boundClosure.get();
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (((Object)((Object)this)).getClass() != _bind_closure7.class) {
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
                stringArray[1] = "useDoubleQuotes";
                stringArray[2] = "flush";
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[3];
                _bind_closure7.$createCallSiteArray_1(stringArray);
                return new CallSiteArray(_bind_closure7.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _bind_closure7.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        return callSiteArray[6].call((Object)new _bind_closure7(this, this, enc, boundClosure));
    }

    public Object bindNode(Object node) {
        Reference node2 = new Reference(node);
        CallSite[] callSiteArray = StreamingMarkupBuilder.$getCallSiteArray();
        public final class _bindNode_closure8
        extends Closure
        implements GeneratedClosure {
            private /* synthetic */ Reference node;
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _bindNode_closure8(Object _outerInstance, Object _thisObject, Reference node) {
                Reference reference;
                CallSite[] callSiteArray = _bindNode_closure8.$getCallSiteArray();
                super(_outerInstance, _thisObject);
                this.node = reference = node;
            }

            public Object doCall(Object it) {
                CallSite[] callSiteArray = _bindNode_closure8.$getCallSiteArray();
                return callSiteArray[0].call(callSiteArray[1].callGroovyObjectGetProperty((Object)this), this.node.get());
            }

            @Generated
            public Object getNode() {
                CallSite[] callSiteArray = _bindNode_closure8.$getCallSiteArray();
                return this.node.get();
            }

            @Generated
            public Object doCall() {
                CallSite[] callSiteArray = _bindNode_closure8.$getCallSiteArray();
                return this.doCall(null);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (((Object)((Object)this)).getClass() != _bindNode_closure8.class) {
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
                stringArray[0] = "leftShift";
                stringArray[1] = "out";
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[2];
                _bindNode_closure8.$createCallSiteArray_1(stringArray);
                return new CallSiteArray(_bindNode_closure8.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _bindNode_closure8.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        return callSiteArray[7].callCurrent((GroovyObject)this, (Object)new _bindNode_closure8(this, this, node2));
    }

    @Override
    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != StreamingMarkupBuilder.class) {
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
    public boolean getUseDoubleQuotes() {
        return this.useDoubleQuotes;
    }

    @Generated
    public boolean isUseDoubleQuotes() {
        return this.useDoubleQuotes;
    }

    @Generated
    public void setUseDoubleQuotes(boolean bl) {
        this.useDoubleQuotes = bl;
    }

    @Generated
    public boolean getExpandEmptyElements() {
        return this.expandEmptyElements;
    }

    @Generated
    public boolean isExpandEmptyElements() {
        return this.expandEmptyElements;
    }

    @Generated
    public void setExpandEmptyElements(boolean bl) {
        this.expandEmptyElements = bl;
    }

    @Generated
    public Object getPendingStack() {
        return this.pendingStack;
    }

    @Generated
    public void setPendingStack(Object object) {
        this.pendingStack = object;
    }

    @Generated
    public Object getCommentClosure() {
        return this.commentClosure;
    }

    @Generated
    public void setCommentClosure(Object object) {
        this.commentClosure = object;
    }

    @Generated
    public Object getPiClosure() {
        return this.piClosure;
    }

    @Generated
    public void setPiClosure(Object object) {
        this.piClosure = object;
    }

    @Generated
    public Object getDeclarationClosure() {
        return this.declarationClosure;
    }

    @Generated
    public void setDeclarationClosure(Object object) {
        this.declarationClosure = object;
    }

    @Generated
    public Object getNoopClosure() {
        return this.noopClosure;
    }

    @Generated
    public void setNoopClosure(Object object) {
        this.noopClosure = object;
    }

    @Generated
    public Object getUnescapedClosure() {
        return this.unescapedClosure;
    }

    @Generated
    public void setUnescapedClosure(Object object) {
        this.unescapedClosure = object;
    }

    @Generated
    public Object getTagClosure() {
        return this.tagClosure;
    }

    @Generated
    public void setTagClosure(Object object) {
        this.tagClosure = object;
    }

    @Override
    @Generated
    public Object getBuilder() {
        return this.builder;
    }

    @Override
    @Generated
    public void setBuilder(Object object) {
        this.builder = object;
    }

    @Generated
    public Object getEncoding() {
        return this.encoding;
    }

    @Generated
    public void setEncoding(Object object) {
        this.encoding = object;
    }

    public /* synthetic */ MetaClass super$2$$getStaticMetaClass() {
        return super.$getStaticMetaClass();
    }

    public /* synthetic */ Object super$2$getBuilder() {
        return super.getBuilder();
    }

    public /* synthetic */ void super$2$setBuilder(Object object) {
        super.setBuilder(object);
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
        stringArray[0] = "putAll";
        stringArray[1] = "specialTags";
        stringArray[2] = "badTagClosure";
        stringArray[3] = "specialTags";
        stringArray[4] = "<$constructor$>";
        stringArray[5] = "bind";
        stringArray[6] = "asWritable";
        stringArray[7] = "bind";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[8];
        StreamingMarkupBuilder.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(StreamingMarkupBuilder.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = StreamingMarkupBuilder.$createCallSiteArray();
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

        public Object doCall(Object doc, Object pendingNamespaces, Object namespaces, Object namespaceSpecificTags, Object prefix, Object attrs, Object body, Object out) {
            CallSite[] callSiteArray = _closure1.$getCallSiteArray();
            callSiteArray[0].call(callSiteArray[1].call(out), (Object)"<!--");
            callSiteArray[2].call(callSiteArray[3].call(out), body);
            return callSiteArray[4].call(callSiteArray[5].call(out), (Object)"-->");
        }

        @Generated
        public Object call(Object doc, Object pendingNamespaces, Object namespaces, Object namespaceSpecificTags, Object prefix, Object attrs, Object body, Object out) {
            CallSite[] callSiteArray = _closure1.$getCallSiteArray();
            return callSiteArray[6].callCurrent((GroovyObject)this, ArrayUtil.createArray((Object)doc, (Object)pendingNamespaces, (Object)namespaces, (Object)namespaceSpecificTags, (Object)prefix, (Object)attrs, (Object)body, (Object)out));
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
            stringArray[0] = "leftShift";
            stringArray[1] = "unescaped";
            stringArray[2] = "leftShift";
            stringArray[3] = "escaped";
            stringArray[4] = "leftShift";
            stringArray[5] = "unescaped";
            stringArray[6] = "doCall";
        }

        private static /* synthetic */ CallSiteArray $createCallSiteArray() {
            String[] stringArray = new String[7];
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

        public Object doCall(Object doc, Object pendingNamespaces, Object namespaces, Object namespaceSpecificTags, Object prefix, Object attrs, Object body, Object out) {
            Reference out2 = new Reference(out);
            CallSite[] callSiteArray = _closure2.$getCallSiteArray();
            public final class _closure9
            extends Closure
            implements GeneratedClosure {
                private /* synthetic */ Reference out;
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;
                private static /* synthetic */ SoftReference $callSiteArray;

                public _closure9(Object _outerInstance, Object _thisObject, Reference out) {
                    Reference reference;
                    CallSite[] callSiteArray = _closure9.$getCallSiteArray();
                    super(_outerInstance, _thisObject);
                    this.out = reference = out;
                }

                public Object doCall(Object target, Object instruction) {
                    CallSite[] callSiteArray = _closure9.$getCallSiteArray();
                    callSiteArray[0].call(callSiteArray[1].call(this.out.get()), (Object)"<?");
                    if (instruction instanceof Map) {
                        callSiteArray[2].call(callSiteArray[3].call(this.out.get()), target);
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
                                Object valueStr = callSiteArray[0].call(value);
                                if (!BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                                    return DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[1].call(valueStr, (Object)"'")) || DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[2].callGroovyObjectGetProperty((Object)this)) && !DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[3].call(valueStr, (Object)"\""));
                                }
                                return DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[4].call(valueStr, (Object)"'")) || DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[5].callGroovyObjectGetProperty((Object)this)) && !DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[6].call(valueStr, (Object)"\""));
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
                                stringArray[0] = "toString";
                                stringArray[1] = "contains";
                                stringArray[2] = "useDoubleQuotes";
                                stringArray[3] = "contains";
                                stringArray[4] = "contains";
                                stringArray[5] = "useDoubleQuotes";
                                stringArray[6] = "contains";
                            }

                            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                                String[] stringArray = new String[7];
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
                        callSiteArray[4].call(callSiteArray[5].call(this.out.get()), callSiteArray[6].callCurrent((GroovyObject)this, instruction, (Object)new _closure10((Object)this, this.getThisObject())));
                    } else {
                        callSiteArray[7].call(callSiteArray[8].call(this.out.get()), (Object)new GStringImpl(new Object[]{target, instruction}, new String[]{"", " ", ""}));
                    }
                    return callSiteArray[9].call(callSiteArray[10].call(this.out.get()), (Object)"?>");
                }

                @Generated
                public Object call(Object target, Object instruction) {
                    CallSite[] callSiteArray = _closure9.$getCallSiteArray();
                    return callSiteArray[11].callCurrent((GroovyObject)this, target, instruction);
                }

                @Generated
                public Object getOut() {
                    CallSite[] callSiteArray = _closure9.$getCallSiteArray();
                    return this.out.get();
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
                    stringArray[0] = "leftShift";
                    stringArray[1] = "unescaped";
                    stringArray[2] = "leftShift";
                    stringArray[3] = "unescaped";
                    stringArray[4] = "leftShift";
                    stringArray[5] = "unescaped";
                    stringArray[6] = "toMapStringClosure";
                    stringArray[7] = "leftShift";
                    stringArray[8] = "unescaped";
                    stringArray[9] = "leftShift";
                    stringArray[10] = "unescaped";
                    stringArray[11] = "doCall";
                }

                private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                    String[] stringArray = new String[12];
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
            return callSiteArray[0].call(attrs, (Object)new _closure9((Object)this, this.getThisObject(), out2));
        }

        @Generated
        public Object call(Object doc, Object pendingNamespaces, Object namespaces, Object namespaceSpecificTags, Object prefix, Object attrs, Object body, Object out) {
            Reference out2 = new Reference(out);
            CallSite[] callSiteArray = _closure2.$getCallSiteArray();
            return callSiteArray[1].callCurrent((GroovyObject)this, ArrayUtil.createArray((Object)doc, (Object)pendingNamespaces, (Object)namespaces, (Object)namespaceSpecificTags, (Object)prefix, (Object)attrs, (Object)body, (Object)out2.get()));
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

        public Object doCall(Object doc, Object pendingNamespaces, Object namespaces, Object namespaceSpecificTags, Object prefix, Object attrs, Object body, Object out) {
            CallSite[] callSiteArray = _closure3.$getCallSiteArray();
            callSiteArray[0].call(callSiteArray[1].call(out), callSiteArray[2].call(callSiteArray[3].call(callSiteArray[4].call((Object)"<?xml version=", callSiteArray[5].callGroovyObjectGetProperty((Object)this)), (Object)"1.0"), callSiteArray[6].callGroovyObjectGetProperty((Object)this)));
            if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[7].callGetProperty(out))) {
                callSiteArray[8].call(callSiteArray[9].call(out), callSiteArray[10].call(callSiteArray[11].call(callSiteArray[12].call((Object)" encoding=", callSiteArray[13].callGroovyObjectGetProperty((Object)this)), callSiteArray[14].callGetProperty(out)), callSiteArray[15].callGroovyObjectGetProperty((Object)this)));
            }
            return callSiteArray[16].call(callSiteArray[17].call(out), (Object)"?>\n");
        }

        @Generated
        public Object call(Object doc, Object pendingNamespaces, Object namespaces, Object namespaceSpecificTags, Object prefix, Object attrs, Object body, Object out) {
            CallSite[] callSiteArray = _closure3.$getCallSiteArray();
            return callSiteArray[18].callCurrent((GroovyObject)this, ArrayUtil.createArray((Object)doc, (Object)pendingNamespaces, (Object)namespaces, (Object)namespaceSpecificTags, (Object)prefix, (Object)attrs, (Object)body, (Object)out));
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
            stringArray[0] = "leftShift";
            stringArray[1] = "unescaped";
            stringArray[2] = "plus";
            stringArray[3] = "plus";
            stringArray[4] = "plus";
            stringArray[5] = "qt";
            stringArray[6] = "qt";
            stringArray[7] = "encodingKnown";
            stringArray[8] = "leftShift";
            stringArray[9] = "escaped";
            stringArray[10] = "plus";
            stringArray[11] = "plus";
            stringArray[12] = "plus";
            stringArray[13] = "qt";
            stringArray[14] = "encoding";
            stringArray[15] = "qt";
            stringArray[16] = "leftShift";
            stringArray[17] = "unescaped";
            stringArray[18] = "doCall";
        }

        private static /* synthetic */ CallSiteArray $createCallSiteArray() {
            String[] stringArray = new String[19];
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

        public Object doCall(Object doc, Object pendingNamespaces, Object namespaces, Object namespaceSpecificTags, Object prefix, Object attrs, Object body, Object out) {
            Reference doc2 = new Reference(doc);
            Reference out2 = new Reference(out);
            CallSite[] callSiteArray = _closure4.$getCallSiteArray();
            public final class _closure11
            extends Closure
            implements GeneratedClosure {
                private /* synthetic */ Reference doc;
                private /* synthetic */ Reference out;
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;
                private static /* synthetic */ SoftReference $callSiteArray;

                public _closure11(Object _outerInstance, Object _thisObject, Reference doc, Reference out) {
                    Reference reference;
                    Reference reference2;
                    CallSite[] callSiteArray = _closure11.$getCallSiteArray();
                    super(_outerInstance, _thisObject);
                    this.doc = reference2 = doc;
                    this.out = reference = out;
                }

                public Object doCall(Object it) {
                    CallSite[] callSiteArray = _closure11.$getCallSiteArray();
                    if (it instanceof Closure) {
                        Object body1 = callSiteArray[0].call(it);
                        Object object = this.doc.get();
                        ScriptBytecodeAdapter.setProperty((Object)object, null, (Object)body1, (String)"delegate");
                        return callSiteArray[1].call(body1, this.doc.get());
                    }
                    if (it instanceof Buildable) {
                        return callSiteArray[2].call(it, this.doc.get());
                    }
                    return callSiteArray[3].call(callSiteArray[4].call(this.out.get()), it);
                }

                @Generated
                public Object getDoc() {
                    CallSite[] callSiteArray = _closure11.$getCallSiteArray();
                    return this.doc.get();
                }

                @Generated
                public Object getOut() {
                    CallSite[] callSiteArray = _closure11.$getCallSiteArray();
                    return this.out.get();
                }

                @Generated
                public Object doCall() {
                    CallSite[] callSiteArray = _closure11.$getCallSiteArray();
                    return this.doCall(null);
                }

                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                    if (((Object)((Object)this)).getClass() != _closure11.class) {
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
                    stringArray[0] = "clone";
                    stringArray[1] = "call";
                    stringArray[2] = "build";
                    stringArray[3] = "leftShift";
                    stringArray[4] = "escaped";
                }

                private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                    String[] stringArray = new String[5];
                    _closure11.$createCallSiteArray_1(stringArray);
                    return new CallSiteArray(_closure11.class, stringArray);
                }

                private static /* synthetic */ CallSite[] $getCallSiteArray() {
                    CallSiteArray callSiteArray;
                    if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                        callSiteArray = _closure11.$createCallSiteArray();
                        $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                    }
                    return callSiteArray.array;
                }
            }
            return callSiteArray[0].call(body, (Object)new _closure11((Object)this, this.getThisObject(), doc2, out2));
        }

        @Generated
        public Object call(Object doc, Object pendingNamespaces, Object namespaces, Object namespaceSpecificTags, Object prefix, Object attrs, Object body, Object out) {
            Reference doc2 = new Reference(doc);
            Reference out2 = new Reference(out);
            CallSite[] callSiteArray = _closure4.$getCallSiteArray();
            return callSiteArray[1].callCurrent((GroovyObject)this, ArrayUtil.createArray((Object)doc2.get(), (Object)pendingNamespaces, (Object)namespaces, (Object)namespaceSpecificTags, (Object)prefix, (Object)attrs, (Object)body, (Object)out2.get()));
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

        private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
            stringArray[0] = "each";
            stringArray[1] = "doCall";
        }

        private static /* synthetic */ CallSiteArray $createCallSiteArray() {
            String[] stringArray = new String[2];
            _closure4.$createCallSiteArray_1(stringArray);
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

        public Object doCall(Object doc, Object pendingNamespaces, Object namespaces, Object namespaceSpecificTags, Object prefix, Object attrs, Object body, Object out) {
            CallSite[] callSiteArray = _closure5.$getCallSiteArray();
            return callSiteArray[0].call(callSiteArray[1].call(out), body);
        }

        @Generated
        public Object call(Object doc, Object pendingNamespaces, Object namespaces, Object namespaceSpecificTags, Object prefix, Object attrs, Object body, Object out) {
            CallSite[] callSiteArray = _closure5.$getCallSiteArray();
            return callSiteArray[2].callCurrent((GroovyObject)this, ArrayUtil.createArray((Object)doc, (Object)pendingNamespaces, (Object)namespaces, (Object)namespaceSpecificTags, (Object)prefix, (Object)attrs, (Object)body, (Object)out));
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
            stringArray[0] = "leftShift";
            stringArray[1] = "unescaped";
            stringArray[2] = "doCall";
        }

        private static /* synthetic */ CallSiteArray $createCallSiteArray() {
            String[] stringArray = new String[3];
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

    public final class _closure6
    extends Closure
    implements GeneratedClosure {
        private static /* synthetic */ ClassInfo $staticClassInfo;
        public static transient /* synthetic */ boolean __$stMC;
        private static /* synthetic */ SoftReference $callSiteArray;

        public _closure6(Object _outerInstance, Object _thisObject) {
            CallSite[] callSiteArray = _closure6.$getCallSiteArray();
            super(_outerInstance, _thisObject);
        }

        /*
         * WARNING - void declaration
         */
        public Object doCall(Object tag, Object doc, Object pendingNamespaces, Object namespaces, Object namespaceSpecificTags, Object prefix, Object attrs, Object body, Object out) {
            public final class _closure14
            extends Closure
            implements GeneratedClosure {
                private /* synthetic */ Reference doc;
                private /* synthetic */ Reference out;
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;
                private static /* synthetic */ SoftReference $callSiteArray;

                public _closure14(Object _outerInstance, Object _thisObject, Reference doc, Reference out) {
                    Reference reference;
                    Reference reference2;
                    CallSite[] callSiteArray = _closure14.$getCallSiteArray();
                    super(_outerInstance, _thisObject);
                    this.doc = reference2 = doc;
                    this.out = reference = out;
                }

                public Object doCall(Object it) {
                    CallSite[] callSiteArray = _closure14.$getCallSiteArray();
                    if (it instanceof Closure) {
                        Object body1 = callSiteArray[0].call(it);
                        Object object = this.doc.get();
                        ScriptBytecodeAdapter.setProperty((Object)object, null, (Object)body1, (String)"delegate");
                        return callSiteArray[1].call(body1, this.doc.get());
                    }
                    if (it instanceof Buildable) {
                        return callSiteArray[2].call(it, this.doc.get());
                    }
                    return callSiteArray[3].call(callSiteArray[4].call(this.out.get()), it);
                }

                @Generated
                public Object getDoc() {
                    CallSite[] callSiteArray = _closure14.$getCallSiteArray();
                    return this.doc.get();
                }

                @Generated
                public Object getOut() {
                    CallSite[] callSiteArray = _closure14.$getCallSiteArray();
                    return this.out.get();
                }

                @Generated
                public Object doCall() {
                    CallSite[] callSiteArray = _closure14.$getCallSiteArray();
                    return this.doCall(null);
                }

                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                    if (((Object)((Object)this)).getClass() != _closure14.class) {
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
                    stringArray[0] = "clone";
                    stringArray[1] = "call";
                    stringArray[2] = "build";
                    stringArray[3] = "leftShift";
                    stringArray[4] = "escaped";
                }

                private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                    String[] stringArray = new String[5];
                    _closure14.$createCallSiteArray_1(stringArray);
                    return new CallSiteArray(_closure14.class, stringArray);
                }

                private static /* synthetic */ CallSite[] $getCallSiteArray() {
                    CallSiteArray callSiteArray;
                    if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                        callSiteArray = _closure14.$createCallSiteArray();
                        $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                    }
                    return callSiteArray.array;
                }
            }
            int n;
            int n2;
            void var3_3;
            Reference doc2 = new Reference(doc);
            Reference pendingNamespaces2 = new Reference((Object)var3_3);
            Reference namespaces2 = new Reference(namespaces);
            Reference out2 = new Reference(out);
            CallSite[] callSiteArray = _closure6.$getCallSiteArray();
            int pendingIsDefaultNamespace = 0;
            pendingIsDefaultNamespace = !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass() ? (n2 = DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[0].call(pendingNamespaces2.get(), prefix)) && !DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[1].call(pendingNamespaces2.get(), prefix)) ? 1 : 0) : (n = DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[2].call(pendingNamespaces2.get(), prefix)) && !DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[3].call(pendingNamespaces2.get(), prefix)) ? 1 : 0);
            if (ScriptBytecodeAdapter.compareNotEqual((Object)prefix, (Object)"")) {
                if (!(DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[4].call(namespaces2.get(), prefix)) || DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[5].call(pendingNamespaces2.get(), prefix)))) {
                    throw (Throwable)callSiteArray[6].callConstructor(GroovyRuntimeException.class, (Object)new GStringImpl(new Object[]{prefix}, new String[]{"Namespace prefix: ", " is not bound to a URI"}));
                }
                if (!BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                    if (ScriptBytecodeAdapter.compareNotEqual((Object)prefix, (Object)":") && pendingIsDefaultNamespace == 0) {
                        Object object;
                        tag = object = callSiteArray[7].call(callSiteArray[8].call(prefix, (Object)":"), tag);
                    }
                } else if (ScriptBytecodeAdapter.compareNotEqual((Object)prefix, (Object)":") && pendingIsDefaultNamespace == 0) {
                    Object object;
                    tag = object = callSiteArray[9].call(callSiteArray[10].call(prefix, (Object)":"), tag);
                }
            }
            Object object = callSiteArray[11].call(callSiteArray[12].call(out2.get()), (Object)new GStringImpl(new Object[]{tag}, new String[]{"<", ""}));
            out2.set(object);
            public final class _closure12
            extends Closure
            implements GeneratedClosure {
                private /* synthetic */ Reference namespaces;
                private /* synthetic */ Reference pendingNamespaces;
                private /* synthetic */ Reference out;
                private /* synthetic */ Reference doc;
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;
                private static /* synthetic */ SoftReference $callSiteArray;

                public _closure12(Object _outerInstance, Object _thisObject, Reference namespaces, Reference pendingNamespaces, Reference out, Reference doc) {
                    Reference reference;
                    Reference reference2;
                    Reference reference3;
                    Reference reference4;
                    CallSite[] callSiteArray = _closure12.$getCallSiteArray();
                    super(_outerInstance, _thisObject);
                    this.namespaces = reference4 = namespaces;
                    this.pendingNamespaces = reference3 = pendingNamespaces;
                    this.out = reference2 = out;
                    this.doc = reference = doc;
                }

                public Object doCall(Object key, Object value) {
                    CallSite[] callSiteArray = _closure12.$getCallSiteArray();
                    if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[0].call(key, (Object)"$"))) {
                        Object parts = callSiteArray[1].call(key, (Object)"$");
                        String localpart = ShortTypeHandling.castToString((Object)(DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[2].call(callSiteArray[3].call(parts, (Object)1), (Object)"}")) ? callSiteArray[4].call(callSiteArray[5].call(callSiteArray[6].call(parts, (Object)1), (Object)"}"), (Object)1) : callSiteArray[7].call(parts, (Object)1)));
                        if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[8].call(this.namespaces.get(), callSiteArray[9].call(parts, (Object)0))) || DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[10].call(this.pendingNamespaces.get(), callSiteArray[11].call(parts, (Object)0)))) {
                            Object object;
                            key = object = callSiteArray[12].call(callSiteArray[13].call(callSiteArray[14].call(parts, (Object)0), (Object)":"), (Object)localpart);
                        } else {
                            throw (Throwable)callSiteArray[15].callConstructor(GroovyRuntimeException.class, (Object)new GStringImpl(new Object[]{callSiteArray[16].call(parts, (Object)0), key}, new String[]{"bad attribute namespace tag: ", " in ", ""}));
                        }
                    }
                    callSiteArray[17].call(this.out.get(), callSiteArray[18].call((Object)new GStringImpl(new Object[]{key}, new String[]{" ", "="}), callSiteArray[19].callGroovyObjectGetProperty((Object)this)));
                    boolean bl = true;
                    ScriptBytecodeAdapter.setProperty((Object)bl, null, (Object)this.out.get(), (String)"writingAttribute");
                    callSiteArray[20].call((Object)new GStringImpl(new Object[]{value}, new String[]{"", ""}), this.doc.get());
                    boolean bl2 = false;
                    ScriptBytecodeAdapter.setProperty((Object)bl2, null, (Object)this.out.get(), (String)"writingAttribute");
                    return callSiteArray[21].call(this.out.get(), callSiteArray[22].callGroovyObjectGetProperty((Object)this));
                }

                @Generated
                public Object call(Object key, Object value) {
                    CallSite[] callSiteArray = _closure12.$getCallSiteArray();
                    return callSiteArray[23].callCurrent((GroovyObject)this, key, value);
                }

                @Generated
                public Object getNamespaces() {
                    CallSite[] callSiteArray = _closure12.$getCallSiteArray();
                    return this.namespaces.get();
                }

                @Generated
                public Object getPendingNamespaces() {
                    CallSite[] callSiteArray = _closure12.$getCallSiteArray();
                    return this.pendingNamespaces.get();
                }

                @Generated
                public Object getOut() {
                    CallSite[] callSiteArray = _closure12.$getCallSiteArray();
                    return this.out.get();
                }

                @Generated
                public Object getDoc() {
                    CallSite[] callSiteArray = _closure12.$getCallSiteArray();
                    return this.doc.get();
                }

                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                    if (((Object)((Object)this)).getClass() != _closure12.class) {
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
                    stringArray[1] = "tokenize";
                    stringArray[2] = "contains";
                    stringArray[3] = "getAt";
                    stringArray[4] = "getAt";
                    stringArray[5] = "tokenize";
                    stringArray[6] = "getAt";
                    stringArray[7] = "getAt";
                    stringArray[8] = "containsKey";
                    stringArray[9] = "getAt";
                    stringArray[10] = "containsKey";
                    stringArray[11] = "getAt";
                    stringArray[12] = "plus";
                    stringArray[13] = "plus";
                    stringArray[14] = "getAt";
                    stringArray[15] = "<$constructor$>";
                    stringArray[16] = "getAt";
                    stringArray[17] = "leftShift";
                    stringArray[18] = "plus";
                    stringArray[19] = "qt";
                    stringArray[20] = "build";
                    stringArray[21] = "leftShift";
                    stringArray[22] = "qt";
                    stringArray[23] = "doCall";
                }

                private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                    String[] stringArray = new String[24];
                    _closure12.$createCallSiteArray_1(stringArray);
                    return new CallSiteArray(_closure12.class, stringArray);
                }

                private static /* synthetic */ CallSite[] $getCallSiteArray() {
                    CallSiteArray callSiteArray;
                    if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                        callSiteArray = _closure12.$createCallSiteArray();
                        $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                    }
                    return callSiteArray.array;
                }
            }
            callSiteArray[13].call(attrs, (Object)new _closure12((Object)this, this.getThisObject(), namespaces2, pendingNamespaces2, out2, doc2));
            Reference hiddenNamespaces = new Reference((Object)ScriptBytecodeAdapter.createMap((Object[])new Object[0]));
            public final class _closure13
            extends Closure
            implements GeneratedClosure {
                private /* synthetic */ Reference hiddenNamespaces;
                private /* synthetic */ Reference namespaces;
                private /* synthetic */ Reference out;
                private /* synthetic */ Reference doc;
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;
                private static /* synthetic */ SoftReference $callSiteArray;

                public _closure13(Object _outerInstance, Object _thisObject, Reference hiddenNamespaces, Reference namespaces, Reference out, Reference doc) {
                    Reference reference;
                    Reference reference2;
                    Reference reference3;
                    Reference reference4;
                    CallSite[] callSiteArray = _closure13.$getCallSiteArray();
                    super(_outerInstance, _thisObject);
                    this.hiddenNamespaces = reference4 = hiddenNamespaces;
                    this.namespaces = reference3 = namespaces;
                    this.out = reference2 = out;
                    this.doc = reference = doc;
                }

                public Object doCall(Object key, Object value) {
                    CallSite[] callSiteArray = _closure13.$getCallSiteArray();
                    if (DefaultTypeTransformation.booleanUnbox((Object)value)) {
                        Object object = callSiteArray[0].call(this.namespaces.get(), key);
                        callSiteArray[1].call(this.hiddenNamespaces.get(), key, object);
                        Object object2 = value;
                        callSiteArray[2].call(this.namespaces.get(), key, object2);
                        callSiteArray[3].call(this.out.get(), ScriptBytecodeAdapter.compareEqual((Object)key, (Object)":") ? callSiteArray[4].call((Object)" xmlns=", callSiteArray[5].callGroovyObjectGetProperty((Object)this)) : callSiteArray[6].call((Object)new GStringImpl(new Object[]{key}, new String[]{" xmlns:", "="}), callSiteArray[7].callGroovyObjectGetProperty((Object)this)));
                        boolean bl = true;
                        ScriptBytecodeAdapter.setProperty((Object)bl, null, (Object)this.out.get(), (String)"writingAttribute");
                        callSiteArray[8].call((Object)new GStringImpl(new Object[]{value}, new String[]{"", ""}), this.doc.get());
                        boolean bl2 = false;
                        ScriptBytecodeAdapter.setProperty((Object)bl2, null, (Object)this.out.get(), (String)"writingAttribute");
                        return callSiteArray[9].call(this.out.get(), callSiteArray[10].callGroovyObjectGetProperty((Object)this));
                    }
                    return null;
                }

                @Generated
                public Object call(Object key, Object value) {
                    CallSite[] callSiteArray = _closure13.$getCallSiteArray();
                    return callSiteArray[11].callCurrent((GroovyObject)this, key, value);
                }

                @Generated
                public Object getHiddenNamespaces() {
                    CallSite[] callSiteArray = _closure13.$getCallSiteArray();
                    return this.hiddenNamespaces.get();
                }

                @Generated
                public Object getNamespaces() {
                    CallSite[] callSiteArray = _closure13.$getCallSiteArray();
                    return this.namespaces.get();
                }

                @Generated
                public Object getOut() {
                    CallSite[] callSiteArray = _closure13.$getCallSiteArray();
                    return this.out.get();
                }

                @Generated
                public Object getDoc() {
                    CallSite[] callSiteArray = _closure13.$getCallSiteArray();
                    return this.doc.get();
                }

                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                    if (((Object)((Object)this)).getClass() != _closure13.class) {
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
                    stringArray[1] = "putAt";
                    stringArray[2] = "putAt";
                    stringArray[3] = "leftShift";
                    stringArray[4] = "plus";
                    stringArray[5] = "qt";
                    stringArray[6] = "plus";
                    stringArray[7] = "qt";
                    stringArray[8] = "build";
                    stringArray[9] = "leftShift";
                    stringArray[10] = "qt";
                    stringArray[11] = "doCall";
                }

                private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                    String[] stringArray = new String[12];
                    _closure13.$createCallSiteArray_1(stringArray);
                    return new CallSiteArray(_closure13.class, stringArray);
                }

                private static /* synthetic */ CallSite[] $getCallSiteArray() {
                    CallSiteArray callSiteArray;
                    if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                        callSiteArray = _closure13.$createCallSiteArray();
                        $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                    }
                    return callSiteArray.array;
                }
            }
            callSiteArray[14].call(pendingNamespaces2.get(), (Object)new _closure13((Object)this, this.getThisObject(), hiddenNamespaces, namespaces2, out2, doc2));
            if (!BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                if (ScriptBytecodeAdapter.compareEqual((Object)body, null) && !DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[15].callGroovyObjectGetProperty((Object)this))) {
                    callSiteArray[16].call(out2.get(), (Object)"/>");
                } else {
                    callSiteArray[17].call(out2.get(), (Object)">");
                    callSiteArray[18].call(callSiteArray[19].callGroovyObjectGetProperty((Object)this), callSiteArray[20].call(pendingNamespaces2.get()));
                    callSiteArray[21].call(pendingNamespaces2.get());
                    callSiteArray[22].call(body, (Object)new _closure14((Object)this, this.getThisObject(), doc2, out2));
                    callSiteArray[23].call(pendingNamespaces2.get());
                    callSiteArray[24].call(pendingNamespaces2.get(), callSiteArray[25].call(callSiteArray[26].callGroovyObjectGetProperty((Object)this)));
                    callSiteArray[27].call(out2.get(), (Object)new GStringImpl(new Object[]{tag}, new String[]{"</", ">"}));
                }
            } else if (ScriptBytecodeAdapter.compareEqual((Object)body, null) && !DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[28].callGroovyObjectGetProperty((Object)this))) {
                callSiteArray[29].call(out2.get(), (Object)"/>");
            } else {
                callSiteArray[30].call(out2.get(), (Object)">");
                callSiteArray[31].call(callSiteArray[32].callGroovyObjectGetProperty((Object)this), callSiteArray[33].call(pendingNamespaces2.get()));
                callSiteArray[34].call(pendingNamespaces2.get());
                callSiteArray[35].call(body, (Object)new _closure14((Object)this, this.getThisObject(), doc2, out2));
                callSiteArray[36].call(pendingNamespaces2.get());
                callSiteArray[37].call(pendingNamespaces2.get(), callSiteArray[38].call(callSiteArray[39].callGroovyObjectGetProperty((Object)this)));
                callSiteArray[40].call(out2.get(), (Object)new GStringImpl(new Object[]{tag}, new String[]{"</", ">"}));
            }
            public final class _closure15
            extends Closure
            implements GeneratedClosure {
                private /* synthetic */ Reference namespaces;
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;
                private static /* synthetic */ SoftReference $callSiteArray;

                public _closure15(Object _outerInstance, Object _thisObject, Reference namespaces) {
                    Reference reference;
                    CallSite[] callSiteArray = _closure15.$getCallSiteArray();
                    super(_outerInstance, _thisObject);
                    this.namespaces = reference = namespaces;
                }

                public Object doCall(Object key, Object value) {
                    CallSite[] callSiteArray = _closure15.$getCallSiteArray();
                    if (ScriptBytecodeAdapter.compareEqual((Object)value, null)) {
                        return callSiteArray[0].call(this.namespaces.get(), key);
                    }
                    Object object = value;
                    callSiteArray[1].call(this.namespaces.get(), key, object);
                    return object;
                }

                @Generated
                public Object call(Object key, Object value) {
                    CallSite[] callSiteArray = _closure15.$getCallSiteArray();
                    return callSiteArray[2].callCurrent((GroovyObject)this, key, value);
                }

                @Generated
                public Object getNamespaces() {
                    CallSite[] callSiteArray = _closure15.$getCallSiteArray();
                    return this.namespaces.get();
                }

                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                    if (((Object)((Object)this)).getClass() != _closure15.class) {
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
                    stringArray[0] = "remove";
                    stringArray[1] = "putAt";
                    stringArray[2] = "doCall";
                }

                private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                    String[] stringArray = new String[3];
                    _closure15.$createCallSiteArray_1(stringArray);
                    return new CallSiteArray(_closure15.class, stringArray);
                }

                private static /* synthetic */ CallSite[] $getCallSiteArray() {
                    CallSiteArray callSiteArray;
                    if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                        callSiteArray = _closure15.$createCallSiteArray();
                        $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                    }
                    return callSiteArray.array;
                }
            }
            return callSiteArray[41].call(hiddenNamespaces.get(), (Object)new _closure15((Object)this, this.getThisObject(), namespaces2));
        }

        /*
         * WARNING - void declaration
         */
        @Generated
        public Object call(Object tag, Object doc, Object pendingNamespaces, Object namespaces, Object namespaceSpecificTags, Object prefix, Object attrs, Object body, Object out) {
            void var3_3;
            Reference doc2 = new Reference(doc);
            Reference pendingNamespaces2 = new Reference((Object)var3_3);
            Reference namespaces2 = new Reference(namespaces);
            Reference out2 = new Reference(out);
            CallSite[] callSiteArray = _closure6.$getCallSiteArray();
            return callSiteArray[42].callCurrent((GroovyObject)this, ArrayUtil.createArray((Object)tag, (Object)doc2.get(), (Object)pendingNamespaces2.get(), (Object)namespaces2.get(), (Object)namespaceSpecificTags, (Object)prefix, (Object)attrs, (Object)body, (Object)out2.get()));
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
            stringArray[0] = "containsKey";
            stringArray[1] = "getAt";
            stringArray[2] = "containsKey";
            stringArray[3] = "getAt";
            stringArray[4] = "containsKey";
            stringArray[5] = "containsKey";
            stringArray[6] = "<$constructor$>";
            stringArray[7] = "plus";
            stringArray[8] = "plus";
            stringArray[9] = "plus";
            stringArray[10] = "plus";
            stringArray[11] = "leftShift";
            stringArray[12] = "unescaped";
            stringArray[13] = "each";
            stringArray[14] = "each";
            stringArray[15] = "expandEmptyElements";
            stringArray[16] = "leftShift";
            stringArray[17] = "leftShift";
            stringArray[18] = "push";
            stringArray[19] = "pendingStack";
            stringArray[20] = "clone";
            stringArray[21] = "clear";
            stringArray[22] = "each";
            stringArray[23] = "clear";
            stringArray[24] = "putAll";
            stringArray[25] = "pop";
            stringArray[26] = "pendingStack";
            stringArray[27] = "leftShift";
            stringArray[28] = "expandEmptyElements";
            stringArray[29] = "leftShift";
            stringArray[30] = "leftShift";
            stringArray[31] = "push";
            stringArray[32] = "pendingStack";
            stringArray[33] = "clone";
            stringArray[34] = "clear";
            stringArray[35] = "each";
            stringArray[36] = "clear";
            stringArray[37] = "putAll";
            stringArray[38] = "pop";
            stringArray[39] = "pendingStack";
            stringArray[40] = "leftShift";
            stringArray[41] = "each";
            stringArray[42] = "doCall";
        }

        private static /* synthetic */ CallSiteArray $createCallSiteArray() {
            String[] stringArray = new String[43];
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
}

