/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.Closure
 *  groovy.lang.GroovyObject
 *  groovy.lang.MetaClass
 *  groovy.lang.Reference
 *  groovy.transform.Generated
 *  groovy.transform.Internal
 *  groovyjarjarantlr4.v4.runtime.Token
 *  org.codehaus.groovy.control.ResolveVisitor
 *  org.codehaus.groovy.reflection.ClassInfo
 *  org.codehaus.groovy.runtime.BytecodeInterface8
 *  org.codehaus.groovy.runtime.GeneratedClosure
 *  org.codehaus.groovy.runtime.ScriptBytecodeAdapter
 *  org.codehaus.groovy.runtime.callsite.CallSite
 *  org.codehaus.groovy.runtime.callsite.CallSiteArray
 *  org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation
 *  org.codehaus.groovy.runtime.typehandling.ShortTypeHandling
 */
package org.apache.groovy.groovysh.completion.antlr4;

import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import groovy.lang.Reference;
import groovy.transform.Generated;
import groovy.transform.Internal;
import groovyjarjarantlr4.v4.runtime.Token;
import java.beans.Transient;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.apache.groovy.groovysh.Groovysh;
import org.apache.groovy.groovysh.completion.antlr4.IdentifierCompleter;
import org.apache.groovy.groovysh.completion.antlr4.ReflectionCompleter;
import org.codehaus.groovy.control.ResolveVisitor;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.BytecodeInterface8;
import org.codehaus.groovy.runtime.GeneratedClosure;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;
import org.codehaus.groovy.runtime.typehandling.ShortTypeHandling;

public class ImportsSyntaxCompleter
implements IdentifierCompleter,
GroovyObject {
    private final Groovysh shell;
    private List<String> preimportedClassNames;
    private final Map<String, Collection<String>> cachedImports;
    private static final String STATIC_IMPORT_PATTERN;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private transient /* synthetic */ MetaClass metaClass;
    private static /* synthetic */ SoftReference $callSiteArray;

    public ImportsSyntaxCompleter(Groovysh shell) {
        Groovysh groovysh;
        MetaClass metaClass;
        CallSite[] callSiteArray = ImportsSyntaxCompleter.$getCallSiteArray();
        Object object = callSiteArray[0].call(callSiteArray[1].callConstructor(HashMap.class), (Object)new _closure1(this, this));
        this.cachedImports = (Map)ScriptBytecodeAdapter.castToType((Object)object, Map.class);
        this.metaClass = metaClass = this.$getStaticMetaClass();
        this.shell = groovysh = shell;
    }

    @Override
    public boolean complete(List<Token> tokens, List<CharSequence> candidates) {
        CallSite[] callSiteArray = ImportsSyntaxCompleter.$getCallSiteArray();
        String prefix = ShortTypeHandling.castToString((Object)callSiteArray[2].call(callSiteArray[3].call(tokens)));
        boolean foundMatch = DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[4].callCurrent((GroovyObject)this, (Object)prefix, candidates));
        String importSpec = null;
        Iterator iterator = (Iterator)ScriptBytecodeAdapter.castToType((Object)callSiteArray[5].call(callSiteArray[6].callGroovyObjectGetProperty((Object)this.shell)), Iterator.class);
        if (iterator != null) {
            while (iterator.hasNext()) {
                importSpec = ShortTypeHandling.castToString(iterator.next());
                foundMatch = DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[7].call((Object)foundMatch, callSiteArray[8].callCurrent((GroovyObject)this, (Object)prefix, (Object)importSpec, candidates)));
            }
        }
        return foundMatch;
    }

    public boolean findMatchingImportedClassesCached(String prefix, String importSpec, List<String> candidates) {
        Reference prefix2 = new Reference((Object)prefix);
        CallSite[] callSiteArray = ImportsSyntaxCompleter.$getCallSiteArray();
        public final class _findMatchingImportedClassesCached_closure2
        extends Closure
        implements GeneratedClosure {
            private /* synthetic */ Reference prefix;
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _findMatchingImportedClassesCached_closure2(Object _outerInstance, Object _thisObject, Reference prefix) {
                Reference reference;
                CallSite[] callSiteArray = _findMatchingImportedClassesCached_closure2.$getCallSiteArray();
                super(_outerInstance, _thisObject);
                this.prefix = reference = prefix;
            }

            public Object doCall(String it) {
                CallSite[] callSiteArray = _findMatchingImportedClassesCached_closure2.$getCallSiteArray();
                return callSiteArray[0].call((Object)it, this.prefix.get());
            }

            @Generated
            public Object call(String it) {
                CallSite[] callSiteArray = _findMatchingImportedClassesCached_closure2.$getCallSiteArray();
                if (__$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                    return callSiteArray[1].callCurrent((GroovyObject)this, (Object)it);
                }
                return this.doCall(it);
            }

            @Generated
            public String getPrefix() {
                CallSite[] callSiteArray = _findMatchingImportedClassesCached_closure2.$getCallSiteArray();
                return ShortTypeHandling.castToString((Object)this.prefix.get());
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (((Object)((Object)this)).getClass() != _findMatchingImportedClassesCached_closure2.class) {
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
                stringArray[0] = "startsWith";
                stringArray[1] = "doCall";
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[2];
                _findMatchingImportedClassesCached_closure2.$createCallSiteArray_1(stringArray);
                return new CallSiteArray(_findMatchingImportedClassesCached_closure2.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _findMatchingImportedClassesCached_closure2.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        return DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[9].call(candidates, callSiteArray[10].call(callSiteArray[11].call(this.cachedImports, (Object)importSpec), (Object)new _findMatchingImportedClassesCached_closure2(this, this, prefix2))));
    }

    public boolean findMatchingPreImportedClasses(String prefix, Collection<String> matches) {
        CallSite[] callSiteArray = ImportsSyntaxCompleter.$getCallSiteArray();
        boolean foundMatch = false;
        if (ScriptBytecodeAdapter.compareEqual(this.preimportedClassNames, null)) {
            List list;
            this.preimportedClassNames = list = ScriptBytecodeAdapter.createList((Object[])new Object[0]);
            Object packname = null;
            Iterator iterator = (Iterator)ScriptBytecodeAdapter.castToType((Object)callSiteArray[12].call(callSiteArray[13].callGetProperty(ResolveVisitor.class)), Iterator.class);
            if (iterator != null) {
                while (iterator.hasNext()) {
                    packname = iterator.next();
                    Set packnames = (Set)ScriptBytecodeAdapter.castToType((Object)callSiteArray[14].call(callSiteArray[15].callGroovyObjectGetProperty((Object)this.shell), callSiteArray[16].call(packname, (Object)ScriptBytecodeAdapter.createRange((Object)0, (Object)-2, (boolean)false, (boolean)false))), Set.class);
                    if (!DefaultTypeTransformation.booleanUnbox((Object)packnames)) continue;
                    public final class _findMatchingPreImportedClasses_closure3
                    extends Closure
                    implements GeneratedClosure {
                        private static /* synthetic */ ClassInfo $staticClassInfo;
                        public static transient /* synthetic */ boolean __$stMC;
                        private static /* synthetic */ SoftReference $callSiteArray;

                        public _findMatchingPreImportedClasses_closure3(Object _outerInstance, Object _thisObject) {
                            CallSite[] callSiteArray = _findMatchingPreImportedClasses_closure3.$getCallSiteArray();
                            super(_outerInstance, _thisObject);
                        }

                        public Object doCall(String it) {
                            CallSite[] callSiteArray = _findMatchingPreImportedClasses_closure3.$getCallSiteArray();
                            return ScriptBytecodeAdapter.isCase((Object)callSiteArray[0].call((Object)it, (Object)0), (Object)ScriptBytecodeAdapter.createRange((Object)"A", (Object)"Z", (boolean)false, (boolean)false));
                        }

                        @Generated
                        public Object call(String it) {
                            CallSite[] callSiteArray = _findMatchingPreImportedClasses_closure3.$getCallSiteArray();
                            if (__$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                                return callSiteArray[1].callCurrent((GroovyObject)this, (Object)it);
                            }
                            return this.doCall(it);
                        }

                        protected /* synthetic */ MetaClass $getStaticMetaClass() {
                            if (((Object)((Object)this)).getClass() != _findMatchingPreImportedClasses_closure3.class) {
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
                            stringArray[1] = "doCall";
                        }

                        private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                            String[] stringArray = new String[2];
                            _findMatchingPreImportedClasses_closure3.$createCallSiteArray_1(stringArray);
                            return new CallSiteArray(_findMatchingPreImportedClasses_closure3.class, stringArray);
                        }

                        private static /* synthetic */ CallSite[] $getCallSiteArray() {
                            CallSiteArray callSiteArray;
                            if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                                callSiteArray = _findMatchingPreImportedClasses_closure3.$createCallSiteArray();
                                $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                            }
                            return callSiteArray.array;
                        }
                    }
                    callSiteArray[17].call(this.preimportedClassNames, callSiteArray[18].call((Object)packnames, (Object)new _findMatchingPreImportedClasses_closure3(this, this)));
                }
            }
            callSiteArray[19].call(this.preimportedClassNames, (Object)"BigInteger");
            callSiteArray[20].call(this.preimportedClassNames, (Object)"BigDecimal");
        }
        String preImpClassname = null;
        Iterator iterator = (Iterator)ScriptBytecodeAdapter.castToType((Object)callSiteArray[21].call(this.preimportedClassNames), Iterator.class);
        if (iterator != null) {
            while (iterator.hasNext()) {
                boolean bl;
                preImpClassname = ShortTypeHandling.castToString(iterator.next());
                if (!DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[22].call((Object)preImpClassname, (Object)prefix))) continue;
                callSiteArray[23].call(matches, (Object)preImpClassname);
                foundMatch = bl = true;
            }
        }
        return foundMatch;
    }

    public void collectImportedSymbols(String importSpec, Collection<String> matches) {
        CallSite[] callSiteArray = ImportsSyntaxCompleter.$getCallSiteArray();
        String asKeyword = " as ";
        int asIndex = DefaultTypeTransformation.intUnbox((Object)callSiteArray[24].call((Object)importSpec, (Object)asKeyword));
        if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            if (asIndex > -1) {
                String alias = ShortTypeHandling.castToString((Object)callSiteArray[25].call((Object)importSpec, callSiteArray[26].call((Object)asIndex, callSiteArray[27].call((Object)asKeyword))));
                callSiteArray[28].call(matches, (Object)alias);
                return;
            }
        } else if (asIndex > -1) {
            String alias = ShortTypeHandling.castToString((Object)callSiteArray[29].call((Object)importSpec, callSiteArray[30].call((Object)asIndex, callSiteArray[31].call((Object)asKeyword))));
            callSiteArray[32].call(matches, (Object)alias);
            return;
        }
        int lastDotIndex = DefaultTypeTransformation.intUnbox((Object)callSiteArray[33].call((Object)importSpec, (Object)"."));
        String symbolName = null;
        if (!BytecodeInterface8.isOrigInt() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            Object object = callSiteArray[34].call((Object)importSpec, callSiteArray[35].call((Object)lastDotIndex, (Object)1));
            symbolName = ShortTypeHandling.castToString((Object)object);
        } else {
            Object object = callSiteArray[36].call((Object)importSpec, (Object)(lastDotIndex + 1));
            symbolName = ShortTypeHandling.castToString((Object)object);
        }
        String staticPrefix = "static ";
        if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[37].call((Object)importSpec, (Object)staticPrefix))) {
            if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[38].call((Object)importSpec, (Object)STATIC_IMPORT_PATTERN))) {
                String className = ShortTypeHandling.castToString((Object)callSiteArray[39].call((Object)importSpec, callSiteArray[40].call((Object)staticPrefix), (Object)lastDotIndex));
                Class clazz = (Class)ScriptBytecodeAdapter.asType((Object)callSiteArray[41].call(callSiteArray[42].callGroovyObjectGetProperty((Object)this.shell), (Object)ScriptBytecodeAdapter.createList((Object[])new Object[]{className})), Class.class);
                if (ScriptBytecodeAdapter.compareNotEqual((Object)clazz, null)) {
                    Set clazzSymbols = (Set)ScriptBytecodeAdapter.castToType((Object)ScriptBytecodeAdapter.getPropertySpreadSafe(ImportsSyntaxCompleter.class, (Object)callSiteArray[43].call(ReflectionCompleter.class, (Object)clazz, (Object)""), (String)"value"), Set.class);
                    Collection importedSymbols = null;
                    if (ScriptBytecodeAdapter.compareEqual((Object)symbolName, (Object)"*")) {
                        Set set;
                        importedSymbols = set = clazzSymbols;
                    } else {
                        Set acceptableMatches = (Set)ScriptBytecodeAdapter.castToType((Object)ScriptBytecodeAdapter.createList((Object[])new Object[]{symbolName, callSiteArray[44].call((Object)symbolName, (Object)"("), callSiteArray[45].call((Object)symbolName, (Object)"()")}), Set.class);
                        Object object = callSiteArray[46].call((Object)acceptableMatches, (Object)clazzSymbols);
                        importedSymbols = (Collection)ScriptBytecodeAdapter.castToType((Object)object, Collection.class);
                    }
                    callSiteArray[47].call(matches, (Object)importedSymbols);
                }
            }
        } else if (ScriptBytecodeAdapter.compareEqual((Object)symbolName, (Object)"*")) {
            callSiteArray[48].call(matches, callSiteArray[49].call(callSiteArray[50].callGroovyObjectGetProperty((Object)this.shell), (Object)importSpec));
        } else {
            callSiteArray[51].call(matches, (Object)symbolName);
        }
    }

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != ImportsSyntaxCompleter.class) {
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
        Object object = ScriptBytecodeAdapter.bitwiseNegate((Object)"^static ([a-zA-Z_][a-zA-Z_0-9]*\\.)+([a-zA-Z_][a-zA-Z_0-9]*|\\*)$");
        STATIC_IMPORT_PATTERN = ShortTypeHandling.castToString((Object)object);
    }

    @Generated
    public final Groovysh getShell() {
        return this.shell;
    }

    @Generated
    public List<String> getPreimportedClassNames() {
        return this.preimportedClassNames;
    }

    @Generated
    public void setPreimportedClassNames(List<String> list) {
        this.preimportedClassNames = list;
    }

    @Generated
    public final Map<String, Collection<String>> getCachedImports() {
        return this.cachedImports;
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
        stringArray[0] = "withDefault";
        stringArray[1] = "<$constructor$>";
        stringArray[2] = "getText";
        stringArray[3] = "last";
        stringArray[4] = "findMatchingPreImportedClasses";
        stringArray[5] = "iterator";
        stringArray[6] = "imports";
        stringArray[7] = "or";
        stringArray[8] = "findMatchingImportedClassesCached";
        stringArray[9] = "addAll";
        stringArray[10] = "findAll";
        stringArray[11] = "get";
        stringArray[12] = "iterator";
        stringArray[13] = "DEFAULT_IMPORTS";
        stringArray[14] = "getContents";
        stringArray[15] = "packageHelper";
        stringArray[16] = "getAt";
        stringArray[17] = "addAll";
        stringArray[18] = "findAll";
        stringArray[19] = "add";
        stringArray[20] = "add";
        stringArray[21] = "iterator";
        stringArray[22] = "startsWith";
        stringArray[23] = "add";
        stringArray[24] = "indexOf";
        stringArray[25] = "substring";
        stringArray[26] = "plus";
        stringArray[27] = "length";
        stringArray[28] = "leftShift";
        stringArray[29] = "substring";
        stringArray[30] = "plus";
        stringArray[31] = "length";
        stringArray[32] = "leftShift";
        stringArray[33] = "lastIndexOf";
        stringArray[34] = "substring";
        stringArray[35] = "plus";
        stringArray[36] = "substring";
        stringArray[37] = "startsWith";
        stringArray[38] = "matches";
        stringArray[39] = "substring";
        stringArray[40] = "length";
        stringArray[41] = "evaluate";
        stringArray[42] = "interp";
        stringArray[43] = "getPublicFieldsAndMethods";
        stringArray[44] = "plus";
        stringArray[45] = "plus";
        stringArray[46] = "intersect";
        stringArray[47] = "addAll";
        stringArray[48] = "addAll";
        stringArray[49] = "getContents";
        stringArray[50] = "packageHelper";
        stringArray[51] = "leftShift";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[52];
        ImportsSyntaxCompleter.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(ImportsSyntaxCompleter.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = ImportsSyntaxCompleter.$createCallSiteArray();
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

        public Object doCall(String key) {
            CallSite[] callSiteArray = _closure1.$getCallSiteArray();
            Collection matchingImports = (Collection)ScriptBytecodeAdapter.castToType((Object)callSiteArray[0].callConstructor(TreeSet.class), Collection.class);
            callSiteArray[1].callCurrent((GroovyObject)this, (Object)key, (Object)matchingImports);
            return matchingImports;
        }

        @Generated
        public Object call(String key) {
            CallSite[] callSiteArray = _closure1.$getCallSiteArray();
            if (__$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                return callSiteArray[2].callCurrent((GroovyObject)this, (Object)key);
            }
            return this.doCall(key);
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
            stringArray[0] = "<$constructor$>";
            stringArray[1] = "collectImportedSymbols";
            stringArray[2] = "doCall";
        }

        private static /* synthetic */ CallSiteArray $createCallSiteArray() {
            String[] stringArray = new String[3];
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
}

