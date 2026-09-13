/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.Closure
 *  groovy.lang.GroovyObject
 *  groovy.lang.IntRange
 *  groovy.lang.MetaClass
 *  groovy.lang.Reference
 *  groovy.transform.Generated
 *  groovy.transform.Internal
 *  jline.console.completer.Completer
 *  org.codehaus.groovy.control.ResolveVisitor
 *  org.codehaus.groovy.reflection.ClassInfo
 *  org.codehaus.groovy.runtime.DefaultGroovyMethods
 *  org.codehaus.groovy.runtime.GeneratedClosure
 *  org.codehaus.groovy.runtime.ScriptBytecodeAdapter
 *  org.codehaus.groovy.runtime.StringGroovyMethods
 *  org.codehaus.groovy.runtime.callsite.CallSite
 *  org.codehaus.groovy.runtime.callsite.CallSiteArray
 *  org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation
 *  org.codehaus.groovy.runtime.typehandling.ShortTypeHandling
 *  org.codehaus.groovy.tools.shell.util.Logger
 */
package org.apache.groovy.groovysh.commands;

import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import groovy.lang.IntRange;
import groovy.lang.MetaClass;
import groovy.lang.Reference;
import groovy.transform.Generated;
import groovy.transform.Internal;
import java.beans.Transient;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import jline.console.completer.Completer;
import org.apache.groovy.groovysh.Evaluator;
import org.apache.groovy.groovysh.Groovysh;
import org.apache.groovy.groovysh.completion.ReflectionCompletionCandidate;
import org.apache.groovy.groovysh.completion.antlr4.ReflectionCompleter;
import org.apache.groovy.groovysh.util.PackageHelper;
import org.codehaus.groovy.control.ResolveVisitor;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.codehaus.groovy.runtime.GeneratedClosure;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.StringGroovyMethods;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;
import org.codehaus.groovy.runtime.typehandling.ShortTypeHandling;
import org.codehaus.groovy.tools.shell.util.Logger;

public class ImportCompleter
implements Completer,
GroovyObject {
    protected final Logger log;
    private PackageHelper packageHelper;
    private Groovysh shell;
    private static final Pattern QUALIFIED_CLASS_DOT_PATTERN;
    private static final Pattern PACK_OR_CLASSNAME_PATTERN;
    private static final Pattern PACK_OR_SIMPLE_CLASSNAME_PATTERN;
    private static final Pattern PACK_OR_CLASS_OR_METHODNAME_PATTERN;
    private static final Pattern LOWERCASE_IMPORT_ITEM_PATTERN;
    private final boolean staticImport;
    private final Evaluator interpreter;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private transient /* synthetic */ MetaClass metaClass;
    private static /* synthetic */ SoftReference $callSiteArray;

    public ImportCompleter(PackageHelper packageHelper, Evaluator interp, boolean staticImport) {
        Groovysh groovysh;
        Evaluator evaluator;
        boolean bl;
        PackageHelper packageHelper2;
        MetaClass metaClass;
        CallSite[] callSiteArray = ImportCompleter.$getCallSiteArray();
        Object object = callSiteArray[0].call(Logger.class, ImportCompleter.class);
        this.log = (Logger)ScriptBytecodeAdapter.castToType((Object)object, Logger.class);
        this.metaClass = metaClass = this.$getStaticMetaClass();
        this.packageHelper = packageHelper2 = packageHelper;
        this.staticImport = bl = staticImport;
        this.interpreter = evaluator = interp;
        this.shell = groovysh = this.shell;
    }

    public int complete(String buffer, int cursor, List<CharSequence> result) {
        String string = buffer;
        Reference currentImportExpression = new Reference((Object)((string == null ? false : DefaultTypeTransformation.booleanUnbox((Object)string)) ? buffer.substring(0, cursor) : ""));
        if (this.staticImport ? !StringGroovyMethods.matches((CharSequence)((String)currentImportExpression.get()), (Pattern)PACK_OR_CLASS_OR_METHODNAME_PATTERN) : !StringGroovyMethods.matches((CharSequence)((String)currentImportExpression.get()), (Pattern)PACK_OR_SIMPLE_CLASSNAME_PATTERN)) {
            return -1;
        }
        if (((String)currentImportExpression.get()).contains("..")) {
            return -1;
        }
        if (((String)currentImportExpression.get()).endsWith(".")) {
            if (StringGroovyMethods.matches((CharSequence)((String)currentImportExpression.get()), (Pattern)LOWERCASE_IMPORT_ITEM_PATTERN)) {
                Set<String> classnames = this.packageHelper.getContents(StringGroovyMethods.getAt((String)((String)currentImportExpression.get()), (IntRange)new IntRange(true, true, 0, -2)));
                Set<String> set = classnames;
                if (set == null ? false : DefaultTypeTransformation.booleanUnbox(set)) {
                    if (this.staticImport) {
                        public final class _complete_closure1
                        extends Closure
                        implements GeneratedClosure {
                            private static /* synthetic */ ClassInfo $staticClassInfo;
                            public static transient /* synthetic */ boolean __$stMC;

                            public _complete_closure1(Object _outerInstance, Object _thisObject) {
                                super(_outerInstance, _thisObject);
                            }

                            public String doCall(String it) {
                                return StringGroovyMethods.plus((String)it, (CharSequence)".");
                            }

                            @Generated
                            public String call(String it) {
                                return this.doCall(it);
                            }

                            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                                if (((Object)((Object)this)).getClass() != _complete_closure1.class) {
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
                        }
                        result.addAll(DefaultGroovyMethods.collect(classnames, (Closure)new _complete_closure1(this, this)));
                    } else {
                        public final class _complete_closure2
                        extends Closure
                        implements GeneratedClosure {
                            private static /* synthetic */ ClassInfo $staticClassInfo;
                            public static transient /* synthetic */ boolean __$stMC;

                            public _complete_closure2(Object _outerInstance, Object _thisObject) {
                                super(_outerInstance, _thisObject);
                            }

                            public String doCall(String it) {
                                return ImportCompleter.addDotOrBlank(it);
                            }

                            @Generated
                            public String call(String it) {
                                return this.doCall(it);
                            }

                            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                                if (((Object)((Object)this)).getClass() != _complete_closure2.class) {
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
                        }
                        result.addAll(DefaultGroovyMethods.collect(classnames, (Closure)new _complete_closure2(this, this)));
                    }
                }
                if (!this.staticImport) {
                    result.add("* ");
                }
                return ((String)currentImportExpression.get()).length();
            }
            if (this.staticImport && StringGroovyMethods.matches((CharSequence)((String)currentImportExpression.get()), (Pattern)QUALIFIED_CLASS_DOT_PATTERN)) {
                Class clazz = (Class)ScriptBytecodeAdapter.asType((Object)this.interpreter.evaluate(ScriptBytecodeAdapter.createList((Object[])new Object[]{StringGroovyMethods.getAt((String)((String)currentImportExpression.get()), (IntRange)new IntRange(true, true, 0, -2))})), Class.class);
                if (clazz != null) {
                    Collection<ReflectionCompletionCandidate> members = ReflectionCompleter.getPublicFieldsAndMethods(clazz, "");
                    public final class _complete_closure3
                    extends Closure
                    implements GeneratedClosure {
                        private static /* synthetic */ ClassInfo $staticClassInfo;
                        public static transient /* synthetic */ boolean __$stMC;

                        public _complete_closure3(Object _outerInstance, Object _thisObject) {
                            super(_outerInstance, _thisObject);
                        }

                        public String doCall(ReflectionCompletionCandidate it) {
                            return StringGroovyMethods.plus((String)it.getValue().replace("(", "").replace(")", ""), (CharSequence)" ");
                        }

                        @Generated
                        public String call(ReflectionCompletionCandidate it) {
                            return this.doCall(it);
                        }

                        protected /* synthetic */ MetaClass $getStaticMetaClass() {
                            if (((Object)((Object)this)).getClass() != _complete_closure3.class) {
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
                    }
                    result.addAll(DefaultGroovyMethods.collect(members, (Closure)new _complete_closure3(this, this)));
                }
                result.add("* ");
                return ((String)currentImportExpression.get()).length();
            }
            return -1;
        }
        Reference prefix = new Reference(null);
        String cfr_ignored_0 = (String)prefix.get();
        int lastDot = ((String)currentImportExpression.get()).lastIndexOf(".");
        if (lastDot == -1) {
            String string2 = (String)currentImportExpression.get();
            prefix.set((Object)string2);
        } else {
            String string3 = ((String)currentImportExpression.get()).substring(lastDot + 1);
            prefix.set((Object)string3);
        }
        String baseString = ((String)currentImportExpression.get()).substring(0, Math.max(lastDot, 0));
        if (StringGroovyMethods.matches((CharSequence)((String)currentImportExpression.get()), (Pattern)PACK_OR_CLASSNAME_PATTERN)) {
            Set<String> candidates = this.packageHelper.getContents(baseString);
            if (candidates == null || candidates.size() == 0) {
                public final class _complete_closure4
                extends Closure
                implements GeneratedClosure {
                    private /* synthetic */ Reference currentImportExpression;
                    private static /* synthetic */ ClassInfo $staticClassInfo;
                    public static transient /* synthetic */ boolean __$stMC;

                    public _complete_closure4(Object _outerInstance, Object _thisObject, Reference currentImportExpression) {
                        super(_outerInstance, _thisObject);
                        Reference reference;
                        this.currentImportExpression = reference = currentImportExpression;
                    }

                    public Boolean doCall(String it) {
                        return it.startsWith(ShortTypeHandling.castToString((Object)this.currentImportExpression.get()));
                    }

                    @Generated
                    public Boolean call(String it) {
                        return this.doCall(it);
                    }

                    @Generated
                    public String getCurrentImportExpression() {
                        return ShortTypeHandling.castToString((Object)this.currentImportExpression.get());
                    }

                    protected /* synthetic */ MetaClass $getStaticMetaClass() {
                        if (((Object)((Object)this)).getClass() != _complete_closure4.class) {
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
                }
                List standards = DefaultGroovyMethods.findAll((Object[])ResolveVisitor.DEFAULT_IMPORTS, (Closure)new _complete_closure4(this, this, currentImportExpression));
                List list = standards;
                if (list == null ? false : DefaultTypeTransformation.booleanUnbox((Object)list)) {
                    result.addAll(standards);
                    return 0;
                }
                return -1;
            }
            this.log.debug((Object)((String)prefix.get()));
            public final class _complete_closure5
            extends Closure
            implements GeneratedClosure {
                private /* synthetic */ Reference prefix;
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;

                public _complete_closure5(Object _outerInstance, Object _thisObject, Reference prefix) {
                    super(_outerInstance, _thisObject);
                    Reference reference;
                    this.prefix = reference = prefix;
                }

                public Boolean doCall(String it) {
                    return it.startsWith(ShortTypeHandling.castToString((Object)this.prefix.get()));
                }

                @Generated
                public Boolean call(String it) {
                    return this.doCall(it);
                }

                @Generated
                public String getPrefix() {
                    return ShortTypeHandling.castToString((Object)this.prefix.get());
                }

                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                    if (((Object)((Object)this)).getClass() != _complete_closure5.class) {
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
            }
            Set matches = DefaultGroovyMethods.findAll(candidates, (Closure)new _complete_closure5(this, this, prefix));
            Set set = matches;
            if (set == null ? false : DefaultTypeTransformation.booleanUnbox((Object)set)) {
                public final class _complete_closure6
                extends Closure
                implements GeneratedClosure {
                    private static /* synthetic */ ClassInfo $staticClassInfo;
                    public static transient /* synthetic */ boolean __$stMC;

                    public _complete_closure6(Object _outerInstance, Object _thisObject) {
                        super(_outerInstance, _thisObject);
                    }

                    public String doCall(String it) {
                        return ImportCompleter.addDotOrBlank(it);
                    }

                    @Generated
                    public String call(String it) {
                        return this.doCall(it);
                    }

                    protected /* synthetic */ MetaClass $getStaticMetaClass() {
                        if (((Object)((Object)this)).getClass() != _complete_closure6.class) {
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
                }
                result.addAll(DefaultGroovyMethods.collect((Iterable)matches, (Closure)new _complete_closure6(this, this)));
                return lastDot <= 0 ? 0 : lastDot + 1;
            }
        } else if (this.staticImport) {
            Class clazz = (Class)ScriptBytecodeAdapter.asType((Object)this.interpreter.evaluate(ScriptBytecodeAdapter.createList((Object[])new Object[]{baseString})), Class.class);
            if (clazz != null) {
                Collection<ReflectionCompletionCandidate> members = ReflectionCompleter.getPublicFieldsAndMethods(clazz, (String)prefix.get());
                Collection<ReflectionCompletionCandidate> collection = members;
                if (collection == null ? false : DefaultTypeTransformation.booleanUnbox(collection)) {
                    public final class _complete_closure7
                    extends Closure
                    implements GeneratedClosure {
                        private static /* synthetic */ ClassInfo $staticClassInfo;
                        public static transient /* synthetic */ boolean __$stMC;

                        public _complete_closure7(Object _outerInstance, Object _thisObject) {
                            super(_outerInstance, _thisObject);
                        }

                        public String doCall(ReflectionCompletionCandidate it) {
                            return StringGroovyMethods.plus((String)it.getValue().replace("(", "").replace(")", ""), (CharSequence)" ");
                        }

                        @Generated
                        public String call(ReflectionCompletionCandidate it) {
                            return this.doCall(it);
                        }

                        protected /* synthetic */ MetaClass $getStaticMetaClass() {
                            if (((Object)((Object)this)).getClass() != _complete_closure7.class) {
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
                    }
                    result.addAll(DefaultGroovyMethods.collect(members, (Closure)new _complete_closure7(this, this)));
                    return lastDot <= 0 ? 0 : lastDot + 1;
                }
            }
        }
        return -1;
    }

    private static String addDotOrBlank(String it) {
        CallSite[] callSiteArray = ImportCompleter.$getCallSiteArray();
        if (ScriptBytecodeAdapter.isCase((Object)callSiteArray[1].call((Object)it, (Object)0), (Object)ScriptBytecodeAdapter.createRange((Object)"A", (Object)"Z", (boolean)false, (boolean)false))) {
            return ShortTypeHandling.castToString((Object)callSiteArray[2].call((Object)it, (Object)" "));
        }
        return ShortTypeHandling.castToString((Object)callSiteArray[3].call((Object)it, (Object)"."));
    }

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != ImportCompleter.class) {
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
        Object object = ScriptBytecodeAdapter.bitwiseNegate((Object)"^[a-z_]{1}[a-z0-9_]*(\\.[a-z0-9_]*)*\\.[A-Z][^.]*\\.$");
        QUALIFIED_CLASS_DOT_PATTERN = (Pattern)ScriptBytecodeAdapter.castToType((Object)object, Pattern.class);
        Object object2 = ScriptBytecodeAdapter.bitwiseNegate((Object)"^([a-z_]{1}[a-z0-9_]*(\\.[a-z0-9_]*)*(\\.[A-Z][^.]*)?)?$");
        PACK_OR_CLASSNAME_PATTERN = (Pattern)ScriptBytecodeAdapter.castToType((Object)object2, Pattern.class);
        Object object3 = ScriptBytecodeAdapter.bitwiseNegate((Object)"^([a-z_]{1}[a-z0-9_]*(\\.[a-z0-9_]*)*(\\.[A-Z][^.$_]*)?)?$");
        PACK_OR_SIMPLE_CLASSNAME_PATTERN = (Pattern)ScriptBytecodeAdapter.castToType((Object)object3, Pattern.class);
        Object object4 = ScriptBytecodeAdapter.bitwiseNegate((Object)"^([a-z_]{1}[a-z0-9.]*(\\.[a-z0-9_]*)*(\\.[A-Z][^.$_]*(\\.[a-zA-Z0-9_]*)?)?)?$");
        PACK_OR_CLASS_OR_METHODNAME_PATTERN = (Pattern)ScriptBytecodeAdapter.castToType((Object)object4, Pattern.class);
        Object object5 = ScriptBytecodeAdapter.bitwiseNegate((Object)"^[a-z0-9.]+$");
        LOWERCASE_IMPORT_ITEM_PATTERN = (Pattern)ScriptBytecodeAdapter.castToType((Object)object5, Pattern.class);
    }

    @Generated
    public PackageHelper getPackageHelper() {
        return this.packageHelper;
    }

    @Generated
    public void setPackageHelper(PackageHelper packageHelper) {
        this.packageHelper = packageHelper;
    }

    @Generated
    public Groovysh getShell() {
        return this.shell;
    }

    @Generated
    public void setShell(Groovysh groovysh) {
        this.shell = groovysh;
    }

    @Generated
    public final boolean getStaticImport() {
        return this.staticImport;
    }

    @Generated
    public final boolean isStaticImport() {
        return this.staticImport;
    }

    @Generated
    public final Evaluator getInterpreter() {
        return this.interpreter;
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
        stringArray[0] = "create";
        stringArray[1] = "getAt";
        stringArray[2] = "plus";
        stringArray[3] = "plus";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[4];
        ImportCompleter.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(ImportCompleter.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = ImportCompleter.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }
}

