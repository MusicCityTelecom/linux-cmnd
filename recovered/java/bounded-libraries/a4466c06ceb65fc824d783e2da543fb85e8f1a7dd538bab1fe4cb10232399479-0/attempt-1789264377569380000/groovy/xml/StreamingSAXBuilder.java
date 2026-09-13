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
 *  org.codehaus.groovy.runtime.GStringImpl
 *  org.codehaus.groovy.runtime.GeneratedClosure
 *  org.codehaus.groovy.runtime.ScriptBytecodeAdapter
 *  org.codehaus.groovy.runtime.callsite.CallSite
 *  org.codehaus.groovy.runtime.callsite.CallSiteArray
 *  org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation
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
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import java.util.List;
import java.util.Map;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.ArrayUtil;
import org.codehaus.groovy.runtime.GStringImpl;
import org.codehaus.groovy.runtime.GeneratedClosure;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.AttributesImpl;

public class StreamingSAXBuilder
extends AbstractStreamingBuilder {
    private Object pendingStack;
    private Object commentClosure;
    private Object piClosure;
    private Object noopClosure;
    private Object tagClosure;
    private Object builder;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private static /* synthetic */ SoftReference $callSiteArray;

    public StreamingSAXBuilder() {
        Object object;
        CallSite[] callSiteArray = StreamingSAXBuilder.$getCallSiteArray();
        List list = ScriptBytecodeAdapter.createList((Object[])new Object[0]);
        this.pendingStack = list;
        _closure1 _closure16 = new _closure1(this, this);
        this.commentClosure = _closure16;
        _closure2 _closure22 = new _closure2(this, this);
        this.piClosure = _closure22;
        _closure3 _closure32 = new _closure3(this, this);
        this.noopClosure = _closure32;
        _closure4 _closure42 = new _closure4(this, this);
        this.tagClosure = _closure42;
        Object var7_7 = null;
        this.builder = var7_7;
        callSiteArray[0].call(callSiteArray[1].callGroovyObjectGetProperty((Object)this), (Object)ScriptBytecodeAdapter.createMap((Object[])new Object[]{"yield", this.noopClosure, "yieldUnescaped", this.noopClosure, "comment", this.commentClosure, "pi", this.piClosure}));
        Map nsSpecificTags = ScriptBytecodeAdapter.createMap((Object[])new Object[]{":", ScriptBytecodeAdapter.createList((Object[])new Object[]{this.tagClosure, this.tagClosure, ScriptBytecodeAdapter.createMap((Object[])new Object[0])}), "http://www.w3.org/XML/1998/namespace", ScriptBytecodeAdapter.createList((Object[])new Object[]{this.tagClosure, this.tagClosure, ScriptBytecodeAdapter.createMap((Object[])new Object[0])}), "http://www.codehaus.org/Groovy/markup/keywords", ScriptBytecodeAdapter.createList((Object[])new Object[]{callSiteArray[2].callGroovyObjectGetProperty((Object)this), this.tagClosure, callSiteArray[3].callGroovyObjectGetProperty((Object)this)})});
        this.builder = object = callSiteArray[4].callConstructor(BaseMarkupBuilder.class, (Object)nsSpecificTags);
    }

    private Object addAttributes(AttributesImpl attributes, Object key, Object value, Object namespaces) {
        CallSite[] callSiteArray = StreamingSAXBuilder.$getCallSiteArray();
        if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[5].call(key, (Object)"$"))) {
            Object parts = callSiteArray[6].call(key, (Object)"$");
            if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[7].call(namespaces, callSiteArray[8].call(parts, (Object)0)))) {
                Object namespaceUri = callSiteArray[9].call(namespaces, callSiteArray[10].call(parts, (Object)0));
                return callSiteArray[11].call((Object)attributes, ArrayUtil.createArray((Object)namespaceUri, (Object)callSiteArray[12].call(parts, (Object)1), (Object)new GStringImpl(new Object[]{callSiteArray[13].call(parts, (Object)0), callSiteArray[14].call(parts, (Object)1)}, new String[]{"", ":", ""}), (Object)"CDATA", (Object)new GStringImpl(new Object[]{value}, new String[]{"", ""})));
            }
            throw (Throwable)callSiteArray[15].callConstructor(GroovyRuntimeException.class, (Object)new GStringImpl(new Object[]{key}, new String[]{"bad attribute namespace tag in ", ""}));
        }
        return callSiteArray[16].call((Object)attributes, ArrayUtil.createArray((Object)"", (Object)key, (Object)key, (Object)"CDATA", (Object)new GStringImpl(new Object[]{value}, new String[]{"", ""})));
    }

    /*
     * WARNING - void declaration
     */
    private Object processBody(Object body, Object doc, Object contentHandler) {
        void var3_3;
        Reference doc2 = new Reference(doc);
        Reference contentHandler2 = new Reference((Object)var3_3);
        CallSite[] callSiteArray = StreamingSAXBuilder.$getCallSiteArray();
        if (body instanceof Closure) {
            Object body1 = callSiteArray[17].call(body);
            Object object = doc2.get();
            ScriptBytecodeAdapter.setProperty((Object)object, null, (Object)body1, (String)"delegate");
            return callSiteArray[18].call(body1, doc2.get());
        }
        if (body instanceof Buildable) {
            return callSiteArray[19].call(body, doc2.get());
        }
        public final class _processBody_closure5
        extends Closure
        implements GeneratedClosure {
            private /* synthetic */ Reference doc;
            private /* synthetic */ Reference contentHandler;
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _processBody_closure5(Object _outerInstance, Object _thisObject, Reference doc, Reference contentHandler) {
                Reference reference;
                Reference reference2;
                CallSite[] callSiteArray = _processBody_closure5.$getCallSiteArray();
                super(_outerInstance, _thisObject);
                this.doc = reference2 = doc;
                this.contentHandler = reference = contentHandler;
            }

            public Object doCall(Object it) {
                CallSite[] callSiteArray = _processBody_closure5.$getCallSiteArray();
                return callSiteArray[0].callCurrent((GroovyObject)this, it, this.doc.get(), this.contentHandler.get());
            }

            @Generated
            public Object getDoc() {
                CallSite[] callSiteArray = _processBody_closure5.$getCallSiteArray();
                return this.doc.get();
            }

            @Generated
            public Object getContentHandler() {
                CallSite[] callSiteArray = _processBody_closure5.$getCallSiteArray();
                return this.contentHandler.get();
            }

            @Generated
            public Object doCall() {
                CallSite[] callSiteArray = _processBody_closure5.$getCallSiteArray();
                return this.doCall(null);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (((Object)((Object)this)).getClass() != _processBody_closure5.class) {
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
                stringArray[0] = "processBodyPart";
                return new CallSiteArray(_processBody_closure5.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _processBody_closure5.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        return callSiteArray[20].call(body, (Object)new _processBody_closure5(this, this, doc2, contentHandler2));
    }

    private Object processBodyPart(Object part, Object doc, Object contentHandler) {
        CallSite[] callSiteArray = StreamingSAXBuilder.$getCallSiteArray();
        if (part instanceof Closure) {
            Object body1 = callSiteArray[21].call(part);
            Object object = doc;
            ScriptBytecodeAdapter.setProperty((Object)object, null, (Object)body1, (String)"delegate");
            return callSiteArray[22].call(body1, doc);
        }
        if (part instanceof Buildable) {
            return callSiteArray[23].call(part, doc);
        }
        Object chars = callSiteArray[24].call(part);
        return callSiteArray[25].call(contentHandler, chars, (Object)0, callSiteArray[26].call(chars));
    }

    public Object bind(Object closure) {
        CallSite[] callSiteArray = StreamingSAXBuilder.$getCallSiteArray();
        Reference boundClosure = new Reference(callSiteArray[27].call(this.builder, closure));
        public final class _bind_closure6
        extends Closure
        implements GeneratedClosure {
            private /* synthetic */ Reference boundClosure;
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _bind_closure6(Object _outerInstance, Object _thisObject, Reference boundClosure) {
                Reference reference;
                CallSite[] callSiteArray = _bind_closure6.$getCallSiteArray();
                super(_outerInstance, _thisObject);
                this.boundClosure = reference = boundClosure;
            }

            public Object doCall(Object contentHandler) {
                CallSite[] callSiteArray = _bind_closure6.$getCallSiteArray();
                callSiteArray[0].call(contentHandler);
                Object object = contentHandler;
                ScriptBytecodeAdapter.setProperty((Object)object, null, (Object)this.boundClosure.get(), (String)"trigger");
                return callSiteArray[1].call(contentHandler);
            }

            @Generated
            public Object getBoundClosure() {
                CallSite[] callSiteArray = _bind_closure6.$getCallSiteArray();
                return this.boundClosure.get();
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (((Object)((Object)this)).getClass() != _bind_closure6.class) {
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
                stringArray[0] = "startDocument";
                stringArray[1] = "endDocument";
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[2];
                _bind_closure6.$createCallSiteArray_1(stringArray);
                return new CallSiteArray(_bind_closure6.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _bind_closure6.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        return new _bind_closure6(this, this, boundClosure);
    }

    @Override
    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != StreamingSAXBuilder.class) {
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
    public Object getNoopClosure() {
        return this.noopClosure;
    }

    @Generated
    public void setNoopClosure(Object object) {
        this.noopClosure = object;
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
        stringArray[5] = "contains";
        stringArray[6] = "tokenize";
        stringArray[7] = "containsKey";
        stringArray[8] = "getAt";
        stringArray[9] = "getAt";
        stringArray[10] = "getAt";
        stringArray[11] = "addAttribute";
        stringArray[12] = "getAt";
        stringArray[13] = "getAt";
        stringArray[14] = "getAt";
        stringArray[15] = "<$constructor$>";
        stringArray[16] = "addAttribute";
        stringArray[17] = "clone";
        stringArray[18] = "call";
        stringArray[19] = "build";
        stringArray[20] = "each";
        stringArray[21] = "clone";
        stringArray[22] = "call";
        stringArray[23] = "build";
        stringArray[24] = "toCharArray";
        stringArray[25] = "characters";
        stringArray[26] = "size";
        stringArray[27] = "bind";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[28];
        StreamingSAXBuilder.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(StreamingSAXBuilder.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = StreamingSAXBuilder.$createCallSiteArray();
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

        public Object doCall(Object doc, Object pendingNamespaces, Object namespaces, Object namespaceSpecificTags, Object prefix, Object attrs, Object body, Object contentHandler) {
            CallSite[] callSiteArray = _closure1.$getCallSiteArray();
            if (contentHandler instanceof LexicalHandler) {
                return callSiteArray[0].call(contentHandler, callSiteArray[1].call(body), (Object)0, callSiteArray[2].call(body));
            }
            return null;
        }

        @Generated
        public Object call(Object doc, Object pendingNamespaces, Object namespaces, Object namespaceSpecificTags, Object prefix, Object attrs, Object body, Object contentHandler) {
            CallSite[] callSiteArray = _closure1.$getCallSiteArray();
            return callSiteArray[3].callCurrent((GroovyObject)this, ArrayUtil.createArray((Object)doc, (Object)pendingNamespaces, (Object)namespaces, (Object)namespaceSpecificTags, (Object)prefix, (Object)attrs, (Object)body, (Object)contentHandler));
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
            stringArray[0] = "comment";
            stringArray[1] = "toCharArray";
            stringArray[2] = "size";
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

        public Object doCall(Object doc, Object pendingNamespaces, Object namespaces, Object namespaceSpecificTags, Object prefix, Object attrs, Object body, Object contentHandler) {
            Reference contentHandler2 = new Reference(contentHandler);
            CallSite[] callSiteArray = _closure2.$getCallSiteArray();
            public final class _closure7
            extends Closure
            implements GeneratedClosure {
                private /* synthetic */ Reference contentHandler;
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;
                private static /* synthetic */ SoftReference $callSiteArray;

                public _closure7(Object _outerInstance, Object _thisObject, Reference contentHandler) {
                    Reference reference;
                    CallSite[] callSiteArray = _closure7.$getCallSiteArray();
                    super(_outerInstance, _thisObject);
                    this.contentHandler = reference = contentHandler;
                }

                public Object doCall(Object target, Object instruction) {
                    CallSite[] callSiteArray = _closure7.$getCallSiteArray();
                    if (instruction instanceof Map) {
                        return callSiteArray[0].call(this.contentHandler.get(), target, callSiteArray[1].callCurrent((GroovyObject)this, instruction));
                    }
                    return callSiteArray[2].call(this.contentHandler.get(), target, instruction);
                }

                @Generated
                public Object call(Object target, Object instruction) {
                    CallSite[] callSiteArray = _closure7.$getCallSiteArray();
                    return callSiteArray[3].callCurrent((GroovyObject)this, target, instruction);
                }

                @Generated
                public Object getContentHandler() {
                    CallSite[] callSiteArray = _closure7.$getCallSiteArray();
                    return this.contentHandler.get();
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
                    stringArray[0] = "processingInstruction";
                    stringArray[1] = "toMapStringClosure";
                    stringArray[2] = "processingInstruction";
                    stringArray[3] = "doCall";
                }

                private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                    String[] stringArray = new String[4];
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
            return callSiteArray[0].call(attrs, (Object)new _closure7((Object)this, this.getThisObject(), contentHandler2));
        }

        @Generated
        public Object call(Object doc, Object pendingNamespaces, Object namespaces, Object namespaceSpecificTags, Object prefix, Object attrs, Object body, Object contentHandler) {
            Reference contentHandler2 = new Reference(contentHandler);
            CallSite[] callSiteArray = _closure2.$getCallSiteArray();
            return callSiteArray[1].callCurrent((GroovyObject)this, ArrayUtil.createArray((Object)doc, (Object)pendingNamespaces, (Object)namespaces, (Object)namespaceSpecificTags, (Object)prefix, (Object)attrs, (Object)body, (Object)contentHandler2.get()));
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

        public Object doCall(Object doc, Object pendingNamespaces, Object namespaces, Object namespaceSpecificTags, Object prefix, Object attrs, Object body, Object contentHandler) {
            CallSite[] callSiteArray = _closure3.$getCallSiteArray();
            if (ScriptBytecodeAdapter.compareNotEqual((Object)body, null)) {
                return callSiteArray[0].callCurrent((GroovyObject)this, body, doc, contentHandler);
            }
            return null;
        }

        @Generated
        public Object call(Object doc, Object pendingNamespaces, Object namespaces, Object namespaceSpecificTags, Object prefix, Object attrs, Object body, Object contentHandler) {
            CallSite[] callSiteArray = _closure3.$getCallSiteArray();
            return callSiteArray[1].callCurrent((GroovyObject)this, ArrayUtil.createArray((Object)doc, (Object)pendingNamespaces, (Object)namespaces, (Object)namespaceSpecificTags, (Object)prefix, (Object)attrs, (Object)body, (Object)contentHandler));
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
            stringArray[0] = "processBody";
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

        public Object doCall(Object tag, Object doc, Object pendingNamespaces, Object namespaces, Object namespaceSpecificTags, Object prefix, Object attrs, Object body, Object contentHandler) {
            Reference namespaces2 = new Reference(namespaces);
            Reference contentHandler2 = new Reference(contentHandler);
            CallSite[] callSiteArray = _closure4.$getCallSiteArray();
            Reference attributes = new Reference(callSiteArray[0].callConstructor(AttributesImpl.class));
            public final class _closure8
            extends Closure
            implements GeneratedClosure {
                private /* synthetic */ Reference attributes;
                private /* synthetic */ Reference namespaces;
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;
                private static /* synthetic */ SoftReference $callSiteArray;

                public _closure8(Object _outerInstance, Object _thisObject, Reference attributes, Reference namespaces) {
                    Reference reference;
                    Reference reference2;
                    CallSite[] callSiteArray = _closure8.$getCallSiteArray();
                    super(_outerInstance, _thisObject);
                    this.attributes = reference2 = attributes;
                    this.namespaces = reference = namespaces;
                }

                public Object doCall(Object key, Object value) {
                    CallSite[] callSiteArray = _closure8.$getCallSiteArray();
                    return callSiteArray[0].callCurrent((GroovyObject)this, this.attributes.get(), key, value, this.namespaces.get());
                }

                @Generated
                public Object call(Object key, Object value) {
                    CallSite[] callSiteArray = _closure8.$getCallSiteArray();
                    return callSiteArray[1].callCurrent((GroovyObject)this, key, value);
                }

                @Generated
                public Object getAttributes() {
                    CallSite[] callSiteArray = _closure8.$getCallSiteArray();
                    return this.attributes.get();
                }

                @Generated
                public Object getNamespaces() {
                    CallSite[] callSiteArray = _closure8.$getCallSiteArray();
                    return this.namespaces.get();
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
                    stringArray[0] = "addAttributes";
                    stringArray[1] = "doCall";
                }

                private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                    String[] stringArray = new String[2];
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
            callSiteArray[1].call(attrs, (Object)new _closure8((Object)this, this.getThisObject(), attributes, namespaces2));
            Reference hiddenNamespaces = new Reference((Object)ScriptBytecodeAdapter.createMap((Object[])new Object[0]));
            public final class _closure9
            extends Closure
            implements GeneratedClosure {
                private /* synthetic */ Reference hiddenNamespaces;
                private /* synthetic */ Reference namespaces;
                private /* synthetic */ Reference attributes;
                private /* synthetic */ Reference contentHandler;
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;
                private static /* synthetic */ SoftReference $callSiteArray;

                public _closure9(Object _outerInstance, Object _thisObject, Reference hiddenNamespaces, Reference namespaces, Reference attributes, Reference contentHandler) {
                    Reference reference;
                    Reference reference2;
                    Reference reference3;
                    Reference reference4;
                    CallSite[] callSiteArray = _closure9.$getCallSiteArray();
                    super(_outerInstance, _thisObject);
                    this.hiddenNamespaces = reference4 = hiddenNamespaces;
                    this.namespaces = reference3 = namespaces;
                    this.attributes = reference2 = attributes;
                    this.contentHandler = reference = contentHandler;
                }

                public Object doCall(Object key, Object value) {
                    CallSite[] callSiteArray = _closure9.$getCallSiteArray();
                    Object k = ScriptBytecodeAdapter.compareEqual((Object)key, (Object)":") ? "" : key;
                    Object object = callSiteArray[0].call(this.namespaces.get(), key);
                    callSiteArray[1].call(this.hiddenNamespaces.get(), k, object);
                    Object object2 = value;
                    callSiteArray[2].call(this.namespaces.get(), k, object2);
                    callSiteArray[3].call(this.attributes.get(), ArrayUtil.createArray((Object)"http://www.w3.org/2000/xmlns/", (Object)k, (Object)new GStringImpl(new Object[]{ScriptBytecodeAdapter.compareEqual((Object)k, (Object)"") ? "" : new GStringImpl(new Object[]{k}, new String[]{":", ""})}, new String[]{"xmlns", ""}), (Object)"CDATA", (Object)new GStringImpl(new Object[]{value}, new String[]{"", ""})));
                    return callSiteArray[4].call(this.contentHandler.get(), k, value);
                }

                @Generated
                public Object call(Object key, Object value) {
                    CallSite[] callSiteArray = _closure9.$getCallSiteArray();
                    return callSiteArray[5].callCurrent((GroovyObject)this, key, value);
                }

                @Generated
                public Object getHiddenNamespaces() {
                    CallSite[] callSiteArray = _closure9.$getCallSiteArray();
                    return this.hiddenNamespaces.get();
                }

                @Generated
                public Object getNamespaces() {
                    CallSite[] callSiteArray = _closure9.$getCallSiteArray();
                    return this.namespaces.get();
                }

                @Generated
                public Object getAttributes() {
                    CallSite[] callSiteArray = _closure9.$getCallSiteArray();
                    return this.attributes.get();
                }

                @Generated
                public Object getContentHandler() {
                    CallSite[] callSiteArray = _closure9.$getCallSiteArray();
                    return this.contentHandler.get();
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
                    stringArray[0] = "getAt";
                    stringArray[1] = "putAt";
                    stringArray[2] = "putAt";
                    stringArray[3] = "addAttribute";
                    stringArray[4] = "startPrefixMapping";
                    stringArray[5] = "doCall";
                }

                private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                    String[] stringArray = new String[6];
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
            callSiteArray[2].call(pendingNamespaces, (Object)new _closure9((Object)this, this.getThisObject(), hiddenNamespaces, namespaces2, attributes, contentHandler2));
            Object uri = "";
            Object qualifiedName = tag;
            if (ScriptBytecodeAdapter.compareNotEqual((Object)prefix, (Object)"")) {
                if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[3].call(namespaces2.get(), prefix))) {
                    Object object;
                    uri = object = callSiteArray[4].call(namespaces2.get(), prefix);
                } else if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[5].call(pendingNamespaces, prefix))) {
                    Object object;
                    uri = object = callSiteArray[6].call(pendingNamespaces, prefix);
                } else {
                    throw (Throwable)callSiteArray[7].callConstructor(GroovyRuntimeException.class, (Object)new GStringImpl(new Object[]{prefix}, new String[]{"Namespace prefix: ", " is not bound to a URI"}));
                }
                if (ScriptBytecodeAdapter.compareNotEqual((Object)prefix, (Object)":")) {
                    Object object;
                    qualifiedName = object = callSiteArray[8].call(callSiteArray[9].call(prefix, (Object)":"), tag);
                }
            }
            callSiteArray[10].call(contentHandler2.get(), uri, tag, qualifiedName, attributes.get());
            if (ScriptBytecodeAdapter.compareNotEqual((Object)body, null)) {
                callSiteArray[11].call(callSiteArray[12].callGroovyObjectGetProperty((Object)this), callSiteArray[13].call(pendingNamespaces));
                callSiteArray[14].call(pendingNamespaces);
                callSiteArray[15].callCurrent((GroovyObject)this, body, doc, contentHandler2.get());
                callSiteArray[16].call(pendingNamespaces);
                callSiteArray[17].call(pendingNamespaces, callSiteArray[18].call(callSiteArray[19].callGroovyObjectGetProperty((Object)this)));
            }
            callSiteArray[20].call(contentHandler2.get(), uri, tag, qualifiedName);
            public final class _closure10
            extends Closure
            implements GeneratedClosure {
                private /* synthetic */ Reference contentHandler;
                private /* synthetic */ Reference namespaces;
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;
                private static /* synthetic */ SoftReference $callSiteArray;

                public _closure10(Object _outerInstance, Object _thisObject, Reference contentHandler, Reference namespaces) {
                    Reference reference;
                    Reference reference2;
                    CallSite[] callSiteArray = _closure10.$getCallSiteArray();
                    super(_outerInstance, _thisObject);
                    this.contentHandler = reference2 = contentHandler;
                    this.namespaces = reference = namespaces;
                }

                public Object doCall(Object key, Object value) {
                    CallSite[] callSiteArray = _closure10.$getCallSiteArray();
                    callSiteArray[0].call(this.contentHandler.get(), key);
                    if (ScriptBytecodeAdapter.compareEqual((Object)value, null)) {
                        return callSiteArray[1].call(this.namespaces.get(), key);
                    }
                    Object object = value;
                    callSiteArray[2].call(this.namespaces.get(), key, object);
                    return object;
                }

                @Generated
                public Object call(Object key, Object value) {
                    CallSite[] callSiteArray = _closure10.$getCallSiteArray();
                    return callSiteArray[3].callCurrent((GroovyObject)this, key, value);
                }

                @Generated
                public Object getContentHandler() {
                    CallSite[] callSiteArray = _closure10.$getCallSiteArray();
                    return this.contentHandler.get();
                }

                @Generated
                public Object getNamespaces() {
                    CallSite[] callSiteArray = _closure10.$getCallSiteArray();
                    return this.namespaces.get();
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
                    stringArray[0] = "endPrefixMapping";
                    stringArray[1] = "remove";
                    stringArray[2] = "putAt";
                    stringArray[3] = "doCall";
                }

                private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                    String[] stringArray = new String[4];
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
            return callSiteArray[21].call(hiddenNamespaces.get(), (Object)new _closure10((Object)this, this.getThisObject(), contentHandler2, namespaces2));
        }

        @Generated
        public Object call(Object tag, Object doc, Object pendingNamespaces, Object namespaces, Object namespaceSpecificTags, Object prefix, Object attrs, Object body, Object contentHandler) {
            Reference namespaces2 = new Reference(namespaces);
            Reference contentHandler2 = new Reference(contentHandler);
            CallSite[] callSiteArray = _closure4.$getCallSiteArray();
            return callSiteArray[22].callCurrent((GroovyObject)this, ArrayUtil.createArray((Object)tag, (Object)doc, (Object)pendingNamespaces, (Object)namespaces2.get(), (Object)namespaceSpecificTags, (Object)prefix, (Object)attrs, (Object)body, (Object)contentHandler2.get()));
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
            stringArray[0] = "<$constructor$>";
            stringArray[1] = "each";
            stringArray[2] = "each";
            stringArray[3] = "containsKey";
            stringArray[4] = "getAt";
            stringArray[5] = "containsKey";
            stringArray[6] = "getAt";
            stringArray[7] = "<$constructor$>";
            stringArray[8] = "plus";
            stringArray[9] = "plus";
            stringArray[10] = "startElement";
            stringArray[11] = "add";
            stringArray[12] = "pendingStack";
            stringArray[13] = "clone";
            stringArray[14] = "clear";
            stringArray[15] = "processBody";
            stringArray[16] = "clear";
            stringArray[17] = "putAll";
            stringArray[18] = "pop";
            stringArray[19] = "pendingStack";
            stringArray[20] = "endElement";
            stringArray[21] = "each";
            stringArray[22] = "doCall";
        }

        private static /* synthetic */ CallSiteArray $createCallSiteArray() {
            String[] stringArray = new String[23];
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
}

