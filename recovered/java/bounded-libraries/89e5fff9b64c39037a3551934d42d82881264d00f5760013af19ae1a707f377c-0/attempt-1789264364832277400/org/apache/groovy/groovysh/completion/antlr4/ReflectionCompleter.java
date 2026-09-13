/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.Closure
 *  groovy.lang.GroovyObject
 *  groovy.lang.MetaClass
 *  groovy.lang.MetaClassImpl
 *  groovy.lang.MetaMethod
 *  groovy.lang.MissingFieldException
 *  groovy.lang.MissingMethodException
 *  groovy.lang.MissingPropertyException
 *  groovy.lang.Reference
 *  groovy.transform.Generated
 *  groovy.transform.Internal
 *  groovyjarjarantlr4.v4.runtime.Token
 *  org.apache.groovy.parser.antlr4.GroovyLexer
 *  org.codehaus.groovy.control.MultipleCompilationErrorsException
 *  org.codehaus.groovy.reflection.ClassInfo
 *  org.codehaus.groovy.runtime.ArrayUtil
 *  org.codehaus.groovy.runtime.BytecodeInterface8
 *  org.codehaus.groovy.runtime.GeneratedClosure
 *  org.codehaus.groovy.runtime.InvokerHelper
 *  org.codehaus.groovy.runtime.ScriptBytecodeAdapter
 *  org.codehaus.groovy.runtime.callsite.CallSite
 *  org.codehaus.groovy.runtime.callsite.CallSiteArray
 *  org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation
 *  org.codehaus.groovy.runtime.typehandling.ShortTypeHandling
 *  org.codehaus.groovy.tools.shell.util.Preferences
 *  org.fusesource.jansi.Ansi$Attribute
 *  org.fusesource.jansi.AnsiRenderer
 *  org.fusesource.jansi.AnsiRenderer$Code
 */
package org.apache.groovy.groovysh.completion.antlr4;

import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import groovy.lang.MetaClassImpl;
import groovy.lang.MetaMethod;
import groovy.lang.MissingFieldException;
import groovy.lang.MissingMethodException;
import groovy.lang.MissingPropertyException;
import groovy.lang.Reference;
import groovy.transform.Generated;
import groovy.transform.Internal;
import groovyjarjarantlr4.v4.runtime.Token;
import java.beans.Transient;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Pattern;
import org.apache.groovy.groovysh.Groovysh;
import org.apache.groovy.groovysh.completion.NavigablePropertiesCompleter;
import org.apache.groovy.groovysh.completion.ReflectionCompletionCandidate;
import org.apache.groovy.parser.antlr4.GroovyLexer;
import org.codehaus.groovy.control.MultipleCompilationErrorsException;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.ArrayUtil;
import org.codehaus.groovy.runtime.BytecodeInterface8;
import org.codehaus.groovy.runtime.GeneratedClosure;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;
import org.codehaus.groovy.runtime.typehandling.ShortTypeHandling;
import org.codehaus.groovy.tools.shell.util.Preferences;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiRenderer;

public class ReflectionCompleter
implements GroovyObject {
    private static final NavigablePropertiesCompleter PROPERTIES_COMPLETER;
    private static final Pattern BEAN_ACCESSOR_PATTERN;
    private final Groovysh shell;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private transient /* synthetic */ MetaClass metaClass;
    private static /* synthetic */ SoftReference $callSiteArray;

    public ReflectionCompleter(Groovysh shell) {
        Groovysh groovysh;
        MetaClass metaClass;
        CallSite[] callSiteArray = ReflectionCompleter.$getCallSiteArray();
        this.metaClass = metaClass = this.$getStaticMetaClass();
        this.shell = groovysh = shell;
    }

    public int complete(List<Token> tokens, List<CharSequence> candidates) {
        CallSite[] callSiteArray = ReflectionCompleter.$getCallSiteArray();
        Token currentElementToken = null;
        Token dotToken = null;
        List previousTokens = null;
        if (ScriptBytecodeAdapter.compareLessThan((Object)callSiteArray[0].call(tokens), (Object)2)) {
            throw (Throwable)callSiteArray[1].callConstructor(IllegalArgumentException.class, callSiteArray[2].call((Object)"Must be invoked with at least 2 tokens, one of which is a dot-like operator: ", ScriptBytecodeAdapter.getPropertySpreadSafe(ReflectionCompleter.class, tokens, (String)"text")));
        }
        if (!BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[3].callGetProperty(callSiteArray[4].call(tokens)), (Object)callSiteArray[5].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[6].callGetProperty(callSiteArray[7].call(tokens)), (Object)callSiteArray[8].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[9].callGetProperty(callSiteArray[10].call(tokens)), (Object)callSiteArray[11].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[12].callGetProperty(callSiteArray[13].call(tokens)), (Object)callSiteArray[14].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[15].callGetProperty(callSiteArray[16].call(tokens)), (Object)callSiteArray[17].callGetProperty(GroovyLexer.class))) {
                Object object = callSiteArray[18].call(tokens);
                dotToken = (Token)ScriptBytecodeAdapter.castToType((Object)object, Token.class);
                Object object2 = callSiteArray[19].call(tokens, (Object)ScriptBytecodeAdapter.createRange((Object)0, (Object)-2, (boolean)false, (boolean)false));
                previousTokens = (List)ScriptBytecodeAdapter.castToType((Object)object2, List.class);
            } else {
                if (ScriptBytecodeAdapter.compareNotEqual((Object)callSiteArray[20].callGetProperty(callSiteArray[21].call(tokens, (Object)-2)), (Object)callSiteArray[22].callGetProperty(GroovyLexer.class)) && ScriptBytecodeAdapter.compareNotEqual((Object)callSiteArray[23].callGetProperty(callSiteArray[24].call(tokens, (Object)-2)), (Object)callSiteArray[25].callGetProperty(GroovyLexer.class)) && ScriptBytecodeAdapter.compareNotEqual((Object)callSiteArray[26].callGetProperty(callSiteArray[27].call(tokens, (Object)-2)), (Object)callSiteArray[28].callGetProperty(GroovyLexer.class)) && ScriptBytecodeAdapter.compareNotEqual((Object)callSiteArray[29].callGetProperty(callSiteArray[30].call(tokens, (Object)-2)), (Object)callSiteArray[31].callGetProperty(GroovyLexer.class)) && ScriptBytecodeAdapter.compareNotEqual((Object)callSiteArray[32].callGetProperty(callSiteArray[33].call(tokens, (Object)-2)), (Object)callSiteArray[34].callGetProperty(GroovyLexer.class))) {
                    throw (Throwable)callSiteArray[35].callConstructor(IllegalArgumentException.class, callSiteArray[36].call((Object)"Must be invoked with token list with dot-like operator at last position or one position before: ", ScriptBytecodeAdapter.getPropertySpreadSafe(ReflectionCompleter.class, tokens, (String)"text")));
                }
                Object object = callSiteArray[37].call(tokens);
                currentElementToken = (Token)ScriptBytecodeAdapter.castToType((Object)object, Token.class);
                Object object3 = callSiteArray[38].call(tokens, (Object)-2);
                dotToken = (Token)ScriptBytecodeAdapter.castToType((Object)object3, Token.class);
                Object object4 = callSiteArray[39].call(tokens, (Object)ScriptBytecodeAdapter.createRange((Object)0, (Object)-3, (boolean)false, (boolean)false));
                previousTokens = (List)ScriptBytecodeAdapter.castToType((Object)object4, List.class);
            }
        } else if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[40].callGetProperty(callSiteArray[41].call(tokens)), (Object)callSiteArray[42].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[43].callGetProperty(callSiteArray[44].call(tokens)), (Object)callSiteArray[45].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[46].callGetProperty(callSiteArray[47].call(tokens)), (Object)callSiteArray[48].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[49].callGetProperty(callSiteArray[50].call(tokens)), (Object)callSiteArray[51].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[52].callGetProperty(callSiteArray[53].call(tokens)), (Object)callSiteArray[54].callGetProperty(GroovyLexer.class))) {
            Object object = callSiteArray[55].call(tokens);
            dotToken = (Token)ScriptBytecodeAdapter.castToType((Object)object, Token.class);
            Object object5 = callSiteArray[56].call(tokens, (Object)ScriptBytecodeAdapter.createRange((Object)0, (Object)-2, (boolean)false, (boolean)false));
            previousTokens = (List)ScriptBytecodeAdapter.castToType((Object)object5, List.class);
        } else {
            if (ScriptBytecodeAdapter.compareNotEqual((Object)callSiteArray[57].callGetProperty(callSiteArray[58].call(tokens, (Object)-2)), (Object)callSiteArray[59].callGetProperty(GroovyLexer.class)) && ScriptBytecodeAdapter.compareNotEqual((Object)callSiteArray[60].callGetProperty(callSiteArray[61].call(tokens, (Object)-2)), (Object)callSiteArray[62].callGetProperty(GroovyLexer.class)) && ScriptBytecodeAdapter.compareNotEqual((Object)callSiteArray[63].callGetProperty(callSiteArray[64].call(tokens, (Object)-2)), (Object)callSiteArray[65].callGetProperty(GroovyLexer.class)) && ScriptBytecodeAdapter.compareNotEqual((Object)callSiteArray[66].callGetProperty(callSiteArray[67].call(tokens, (Object)-2)), (Object)callSiteArray[68].callGetProperty(GroovyLexer.class)) && ScriptBytecodeAdapter.compareNotEqual((Object)callSiteArray[69].callGetProperty(callSiteArray[70].call(tokens, (Object)-2)), (Object)callSiteArray[71].callGetProperty(GroovyLexer.class))) {
                throw (Throwable)callSiteArray[72].callConstructor(IllegalArgumentException.class, callSiteArray[73].call((Object)"Must be invoked with token list with dot-like operator at last position or one position before: ", ScriptBytecodeAdapter.getPropertySpreadSafe(ReflectionCompleter.class, tokens, (String)"text")));
            }
            Object object = callSiteArray[74].call(tokens);
            currentElementToken = (Token)ScriptBytecodeAdapter.castToType((Object)object, Token.class);
            Object object6 = callSiteArray[75].call(tokens, (Object)-2);
            dotToken = (Token)ScriptBytecodeAdapter.castToType((Object)object6, Token.class);
            Object object7 = callSiteArray[76].call(tokens, (Object)ScriptBytecodeAdapter.createRange((Object)0, (Object)-3, (boolean)false, (boolean)false));
            previousTokens = (List)ScriptBytecodeAdapter.castToType((Object)object7, List.class);
        }
        Object instanceOrClass = callSiteArray[77].callCurrent((GroovyObject)this, (Object)previousTokens);
        if (ScriptBytecodeAdapter.compareEqual((Object)instanceOrClass, null)) {
            return -1;
        }
        if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[78].callGetProperty((Object)dotToken), (Object)callSiteArray[79].callGetProperty(GroovyLexer.class))) {
            Object object;
            instanceOrClass = object = callSiteArray[80].call(instanceOrClass);
            if (ScriptBytecodeAdapter.compareEqual((Object)instanceOrClass, null)) {
                return -1;
            }
        }
        String identifierPrefix = null;
        if (DefaultTypeTransformation.booleanUnbox((Object)currentElementToken)) {
            Object object = callSiteArray[81].callGetProperty((Object)currentElementToken);
            identifierPrefix = ShortTypeHandling.castToString((Object)object);
        } else {
            String string;
            identifierPrefix = string = "";
        }
        return DefaultTypeTransformation.intUnbox((Object)callSiteArray[82].callCurrent((GroovyObject)this, ArrayUtil.createArray((Object)instanceOrClass, (Object)identifierPrefix, candidates, (Object)currentElementToken, (Object)dotToken)));
    }

    private int completeInstanceMembers(Object instanceOrClass, String identifierPrefix, List<CharSequence> candidates, Token currentElementToken, Token dotToken) {
        CallSite[] callSiteArray = ReflectionCompleter.$getCallSiteArray();
        Boolean methodRef = null;
        if (!BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            boolean bl = ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[83].callGetProperty((Object)dotToken), (Object)callSiteArray[84].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[85].callGetProperty((Object)dotToken), (Object)callSiteArray[86].callGetProperty(GroovyLexer.class));
            methodRef = bl;
        } else {
            boolean bl = ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[87].callGetProperty((Object)dotToken), (Object)callSiteArray[88].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[89].callGetProperty((Object)dotToken), (Object)callSiteArray[90].callGetProperty(GroovyLexer.class));
            methodRef = bl;
        }
        Collection myCandidates = (Collection)ScriptBytecodeAdapter.castToType((Object)callSiteArray[91].callStatic(ReflectionCompleter.class, instanceOrClass, (Object)identifierPrefix, (Object)methodRef), Collection.class);
        boolean showAllMethods = false;
        if (!BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            boolean bl = ScriptBytecodeAdapter.compareGreaterThanEqual((Object)callSiteArray[92].call((Object)identifierPrefix), (Object)callSiteArray[93].call(Integer.class, callSiteArray[94].call(Preferences.class, callSiteArray[95].callGetProperty(Groovysh.class), (Object)"3")));
            showAllMethods = bl;
        } else {
            boolean bl = ScriptBytecodeAdapter.compareGreaterThanEqual((Object)callSiteArray[96].call((Object)identifierPrefix), (Object)callSiteArray[97].call(Integer.class, callSiteArray[98].call(Preferences.class, callSiteArray[99].callGetProperty(Groovysh.class), (Object)"3")));
            showAllMethods = bl;
        }
        public final class _completeInstanceMembers_closure1
        extends Closure
        implements GeneratedClosure {
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _completeInstanceMembers_closure1(Object _outerInstance, Object _thisObject) {
                CallSite[] callSiteArray = _completeInstanceMembers_closure1.$getCallSiteArray();
                super(_outerInstance, _thisObject);
            }

            public Object doCall(String it) {
                CallSite[] callSiteArray = _completeInstanceMembers_closure1.$getCallSiteArray();
                return callSiteArray[0].callConstructor(ReflectionCompletionCandidate.class, (Object)it);
            }

            @Generated
            public Object call(String it) {
                CallSite[] callSiteArray = _completeInstanceMembers_closure1.$getCallSiteArray();
                if (__$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                    return callSiteArray[1].callCurrent((GroovyObject)this, (Object)it);
                }
                return this.doCall(it);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (((Object)((Object)this)).getClass() != _completeInstanceMembers_closure1.class) {
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
                stringArray[1] = "doCall";
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[2];
                _completeInstanceMembers_closure1.$createCallSiteArray_1(stringArray);
                return new CallSiteArray(_completeInstanceMembers_closure1.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _completeInstanceMembers_closure1.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        callSiteArray[100].call((Object)myCandidates, callSiteArray[101].call(callSiteArray[102].callStatic(ReflectionCompleter.class, instanceOrClass, (Object)identifierPrefix, (Object)showAllMethods), (Object)new _completeInstanceMembers_closure1(this, this)));
        if (!showAllMethods) {
            callSiteArray[103].callStatic(ReflectionCompleter.class, (Object)myCandidates);
        }
        public final class _completeInstanceMembers_closure2
        extends Closure
        implements GeneratedClosure {
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _completeInstanceMembers_closure2(Object _outerInstance, Object _thisObject) {
                CallSite[] callSiteArray = _completeInstanceMembers_closure2.$getCallSiteArray();
                super(_outerInstance, _thisObject);
            }

            public Object doCall(String it) {
                CallSite[] callSiteArray = _completeInstanceMembers_closure2.$getCallSiteArray();
                return callSiteArray[0].callConstructor(ReflectionCompletionCandidate.class, (Object)it, callSiteArray[1].call(callSiteArray[2].callGetProperty(AnsiRenderer.Code.class)));
            }

            @Generated
            public Object call(String it) {
                CallSite[] callSiteArray = _completeInstanceMembers_closure2.$getCallSiteArray();
                if (__$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                    return callSiteArray[3].callCurrent((GroovyObject)this, (Object)it);
                }
                return this.doCall(it);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (((Object)((Object)this)).getClass() != _completeInstanceMembers_closure2.class) {
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
                stringArray[1] = "name";
                stringArray[2] = "BLUE";
                stringArray[3] = "doCall";
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[4];
                _completeInstanceMembers_closure2.$createCallSiteArray_1(stringArray);
                return new CallSiteArray(_completeInstanceMembers_closure2.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _completeInstanceMembers_closure2.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        callSiteArray[104].call((Object)myCandidates, callSiteArray[105].call(callSiteArray[106].callStatic(ReflectionCompleter.class, instanceOrClass, (Object)identifierPrefix), (Object)new _completeInstanceMembers_closure2(this, this)));
        if (ScriptBytecodeAdapter.compareGreaterThan((Object)callSiteArray[107].call((Object)myCandidates), (Object)0)) {
            Object object = callSiteArray[108].call((Object)myCandidates);
            myCandidates = (Collection)ScriptBytecodeAdapter.castToType((Object)object, Collection.class);
            if (DefaultTypeTransformation.booleanUnbox((Object)methodRef)) {
                public final class _completeInstanceMembers_closure3
                extends Closure
                implements GeneratedClosure {
                    private static /* synthetic */ ClassInfo $staticClassInfo;
                    public static transient /* synthetic */ boolean __$stMC;
                    private static /* synthetic */ SoftReference $callSiteArray;

                    public _completeInstanceMembers_closure3(Object _outerInstance, Object _thisObject) {
                        CallSite[] callSiteArray = _completeInstanceMembers_closure3.$getCallSiteArray();
                        super(_outerInstance, _thisObject);
                    }

                    public Object doCall(Object cand) {
                        Object object;
                        CallSite[] callSiteArray = _completeInstanceMembers_closure3.$getCallSiteArray();
                        Object val = callSiteArray[0].callGetProperty(cand);
                        val = object = DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[1].call(val, (Object)"()")) ? callSiteArray[2].call(val, (Object)ScriptBytecodeAdapter.createRange((Object)0, (Object)-3, (boolean)false, (boolean)false)) : (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[3].call(val, (Object)"(")) ? callSiteArray[4].call(val, (Object)ScriptBytecodeAdapter.createRange((Object)0, (Object)-2, (boolean)false, (boolean)false)) : val);
                        return callSiteArray[5].callConstructor(ReflectionCompletionCandidate.class, ScriptBytecodeAdapter.despreadList((Object[])new Object[]{val}, (Object[])new Object[]{callSiteArray[6].callGetProperty(cand)}, (int[])new int[]{1}));
                    }

                    protected /* synthetic */ MetaClass $getStaticMetaClass() {
                        if (((Object)((Object)this)).getClass() != _completeInstanceMembers_closure3.class) {
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
                        stringArray[0] = "value";
                        stringArray[1] = "endsWith";
                        stringArray[2] = "getAt";
                        stringArray[3] = "endsWith";
                        stringArray[4] = "getAt";
                        stringArray[5] = "<$constructor$>";
                        stringArray[6] = "jAnsiCodes";
                    }

                    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                        String[] stringArray = new String[7];
                        _completeInstanceMembers_closure3.$createCallSiteArray_1(stringArray);
                        return new CallSiteArray(_completeInstanceMembers_closure3.class, stringArray);
                    }

                    private static /* synthetic */ CallSite[] $getCallSiteArray() {
                        CallSiteArray callSiteArray;
                        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                            callSiteArray = _completeInstanceMembers_closure3.$createCallSiteArray();
                            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                        }
                        return callSiteArray.array;
                    }
                }
                Object object2 = callSiteArray[109].call((Object)myCandidates, (Object)new _completeInstanceMembers_closure3(this, this));
                myCandidates = (Collection)ScriptBytecodeAdapter.castToType((Object)object2, Collection.class);
            }
            if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[110].call(Boolean.class, callSiteArray[111].call(Preferences.class, callSiteArray[112].callGetProperty(Groovysh.class), (Object)"true")))) {
                public final class _completeInstanceMembers_closure4
                extends Closure
                implements GeneratedClosure {
                    private static /* synthetic */ ClassInfo $staticClassInfo;
                    public static transient /* synthetic */ boolean __$stMC;
                    private static /* synthetic */ SoftReference $callSiteArray;

                    public _completeInstanceMembers_closure4(Object _outerInstance, Object _thisObject) {
                        CallSite[] callSiteArray = _completeInstanceMembers_closure4.$getCallSiteArray();
                        super(_outerInstance, _thisObject);
                    }

                    public Object doCall(ReflectionCompletionCandidate it) {
                        CallSite[] callSiteArray = _completeInstanceMembers_closure4.$getCallSiteArray();
                        return callSiteArray[0].call(AnsiRenderer.class, callSiteArray[1].callGroovyObjectGetProperty((Object)it), callSiteArray[2].call(callSiteArray[3].callGroovyObjectGetProperty((Object)it), (Object)new String[DefaultTypeTransformation.intUnbox((Object)callSiteArray[4].call(callSiteArray[5].callGroovyObjectGetProperty((Object)it)))]));
                    }

                    @Generated
                    public Object call(ReflectionCompletionCandidate it) {
                        CallSite[] callSiteArray = _completeInstanceMembers_closure4.$getCallSiteArray();
                        return callSiteArray[6].callCurrent((GroovyObject)this, (Object)it);
                    }

                    protected /* synthetic */ MetaClass $getStaticMetaClass() {
                        if (((Object)((Object)this)).getClass() != _completeInstanceMembers_closure4.class) {
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
                        stringArray[0] = "render";
                        stringArray[1] = "value";
                        stringArray[2] = "toArray";
                        stringArray[3] = "jAnsiCodes";
                        stringArray[4] = "size";
                        stringArray[5] = "jAnsiCodes";
                        stringArray[6] = "doCall";
                    }

                    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                        String[] stringArray = new String[7];
                        _completeInstanceMembers_closure4.$createCallSiteArray_1(stringArray);
                        return new CallSiteArray(_completeInstanceMembers_closure4.class, stringArray);
                    }

                    private static /* synthetic */ CallSite[] $getCallSiteArray() {
                        CallSiteArray callSiteArray;
                        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                            callSiteArray = _completeInstanceMembers_closure4.$createCallSiteArray();
                            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                        }
                        return callSiteArray.array;
                    }
                }
                callSiteArray[113].call(candidates, callSiteArray[114].call((Object)myCandidates, (Object)new _completeInstanceMembers_closure4(this, this)));
            } else {
                callSiteArray[115].call(candidates, ScriptBytecodeAdapter.getPropertySpreadSafe(ReflectionCompleter.class, (Object)myCandidates, (String)"value"));
            }
            int lastDot = 0;
            if (DefaultTypeTransformation.booleanUnbox((Object)currentElementToken) && ScriptBytecodeAdapter.compareNotEqual((Object)callSiteArray[116].callGetProperty((Object)dotToken), (Object)callSiteArray[117].callGetProperty((Object)currentElementToken))) {
                Object object3 = callSiteArray[118].callGetProperty((Object)currentElementToken);
                lastDot = DefaultTypeTransformation.intUnbox((Object)object3);
            } else {
                Object object4 = callSiteArray[119].call(callSiteArray[120].callGetProperty((Object)dotToken), callSiteArray[121].call(callSiteArray[122].callGetProperty((Object)dotToken)));
                lastDot = DefaultTypeTransformation.intUnbox((Object)object4);
            }
            return lastDot;
        }
        return -1;
    }

    public Object getInvokerClassOrInstance(List<Token> groovySourceTokens) {
        CallSite[] callSiteArray = ReflectionCompleter.$getCallSiteArray();
        if (!BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass() ? !DefaultTypeTransformation.booleanUnbox(groovySourceTokens) || ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[123].callGetProperty(callSiteArray[124].call(groovySourceTokens)), (Object)callSiteArray[125].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[126].callGetProperty(callSiteArray[127].call(groovySourceTokens)), (Object)callSiteArray[128].callGetProperty(GroovyLexer.class)) : !DefaultTypeTransformation.booleanUnbox(groovySourceTokens) || ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[129].callGetProperty(callSiteArray[130].call(groovySourceTokens)), (Object)callSiteArray[131].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[132].callGetProperty(callSiteArray[133].call(groovySourceTokens)), (Object)callSiteArray[134].callGetProperty(GroovyLexer.class))) {
            return null;
        }
        List invokerTokens = (List)ScriptBytecodeAdapter.castToType((Object)callSiteArray[135].callStatic(ReflectionCompleter.class, groovySourceTokens), List.class);
        if (__$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            if (DefaultTypeTransformation.booleanUnbox((Object)invokerTokens)) {
                String instanceRefExpression = ShortTypeHandling.castToString((Object)callSiteArray[136].callStatic(ReflectionCompleter.class, (Object)invokerTokens));
                Object object = callSiteArray[137].call((Object)instanceRefExpression, (Object)"\n", (Object)"");
                instanceRefExpression = ShortTypeHandling.castToString((Object)object);
                Object instance = callSiteArray[138].call(callSiteArray[139].callGroovyObjectGetProperty((Object)this.shell), callSiteArray[140].call(callSiteArray[141].call((Object)ScriptBytecodeAdapter.createList((Object[])new Object[]{callSiteArray[142].call((Object)this.shell)}), (Object)ScriptBytecodeAdapter.createList((Object[])new Object[]{"true"})), (Object)ScriptBytecodeAdapter.createList((Object[])new Object[]{instanceRefExpression})));
                Object object2 = instance;
                try {
                    return object2;
                }
                catch (MissingPropertyException e) {
                }
                catch (MissingMethodException e) {
                }
                catch (MissingFieldException e) {
                }
                catch (MultipleCompilationErrorsException e) {
                }
            }
        } else if (DefaultTypeTransformation.booleanUnbox((Object)invokerTokens)) {
            String instanceRefExpression = ShortTypeHandling.castToString((Object)callSiteArray[143].callStatic(ReflectionCompleter.class, (Object)invokerTokens));
            Object object = callSiteArray[144].call((Object)instanceRefExpression, (Object)"\n", (Object)"");
            instanceRefExpression = ShortTypeHandling.castToString((Object)object);
            Object instance = callSiteArray[145].call(callSiteArray[146].callGroovyObjectGetProperty((Object)this.shell), callSiteArray[147].call(callSiteArray[148].call((Object)ScriptBytecodeAdapter.createList((Object[])new Object[]{callSiteArray[149].call((Object)this.shell)}), (Object)ScriptBytecodeAdapter.createList((Object[])new Object[]{"true"})), (Object)ScriptBytecodeAdapter.createList((Object[])new Object[]{instanceRefExpression})));
            Object object3 = instance;
            try {
                return object3;
            }
            catch (MissingPropertyException e) {
            }
            catch (MissingMethodException e) {
            }
            catch (MissingFieldException e) {
            }
            catch (MultipleCompilationErrorsException e) {
            }
        }
        return null;
    }

    public static List<Token> getInvokerTokens(List<Token> tokens) {
        CallSite[] callSiteArray = ReflectionCompleter.$getCallSiteArray();
        int validIndex = DefaultTypeTransformation.intUnbox((Object)callSiteArray[150].call(tokens));
        if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass() ? validIndex == 0 : validIndex == 0) {
            return ScriptBytecodeAdapter.createList((Object[])new Object[0]);
        }
        Stack expectedOpeners = (Stack)ScriptBytecodeAdapter.castToType((Object)callSiteArray[151].callConstructor(Stack.class), Stack.class);
        Token lastToken = null;
        Token loopToken = null;
        Iterator iterator = (Iterator)ScriptBytecodeAdapter.castToType((Object)callSiteArray[152].call(callSiteArray[153].call(tokens)), Iterator.class);
        if (iterator != null) {
            while (iterator.hasNext()) {
                Token token;
                loopToken = (Token)ScriptBytecodeAdapter.castToType(iterator.next(), Token.class);
                Object object = callSiteArray[154].callGetProperty((Object)loopToken);
                if (!ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[155].callGetProperty(GroovyLexer.class))) {
                    if (ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[156].callGetProperty(GroovyLexer.class))) {
                        if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[157].call((Object)expectedOpeners))) break;
                        if (ScriptBytecodeAdapter.compareNotEqual((Object)callSiteArray[158].call((Object)expectedOpeners), (Object)callSiteArray[159].callGetProperty(GroovyLexer.class))) {
                            return ScriptBytecodeAdapter.createList((Object[])new Object[0]);
                        }
                    } else if (ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[160].callGetProperty(GroovyLexer.class))) {
                        if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[161].call((Object)expectedOpeners))) break;
                        if (ScriptBytecodeAdapter.compareNotEqual((Object)callSiteArray[162].call((Object)expectedOpeners), (Object)callSiteArray[163].callGetProperty(GroovyLexer.class))) {
                            return ScriptBytecodeAdapter.createList((Object[])new Object[0]);
                        }
                    } else if (ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[164].callGetProperty(GroovyLexer.class))) {
                        callSiteArray[165].call((Object)expectedOpeners, callSiteArray[166].callGetProperty(GroovyLexer.class));
                    } else if (ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[167].callGetProperty(GroovyLexer.class))) {
                        callSiteArray[168].call((Object)expectedOpeners, callSiteArray[169].callGetProperty(GroovyLexer.class));
                    } else if (ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[170].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[171].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[172].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[173].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[174].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[175].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[176].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[177].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[178].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[179].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[180].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[181].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[182].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[183].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[184].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[185].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[186].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[187].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[188].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[189].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[190].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[191].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[192].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[193].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[194].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[195].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[196].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[197].callGetProperty(GroovyLexer.class))) {
                        if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[198].call((Object)expectedOpeners))) {
                            break;
                        }
                    } else {
                        if (ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[199].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[200].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[201].callGetProperty(GroovyLexer.class))) break;
                        if (ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[202].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[203].callGetProperty(GroovyLexer.class))) {
                            if (DefaultTypeTransformation.booleanUnbox((Object)lastToken)) {
                                if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[204].callGetProperty((Object)lastToken), (Object)callSiteArray[205].callGetProperty(GroovyLexer.class))) {
                                    return ScriptBytecodeAdapter.createList((Object[])new Object[0]);
                                }
                                if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[206].callGetProperty((Object)lastToken), (Object)callSiteArray[207].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[208].callGetProperty((Object)lastToken), (Object)callSiteArray[209].callGetProperty(GroovyLexer.class))) {
                                    return ScriptBytecodeAdapter.createList((Object[])new Object[0]);
                                }
                            }
                        } else if (ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[210].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[211].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[212].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[213].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[214].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[215].callGetProperty(GroovyLexer.class))) {
                            if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[216].call((Object)expectedOpeners))) {
                                break;
                            }
                        } else if (!(ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[217].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[218].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[219].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[220].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[221].callGetProperty(GroovyLexer.class)))) {
                            return (List)ScriptBytecodeAdapter.castToType(null, List.class);
                        }
                    }
                }
                int n = validIndex;
                validIndex = DefaultTypeTransformation.intUnbox((Object)callSiteArray[222].call((Object)n));
                lastToken = token = loopToken;
            }
        }
        return (List)ScriptBytecodeAdapter.castToType((Object)callSiteArray[223].call(tokens, (Object)ScriptBytecodeAdapter.createRange((Object)validIndex, (Object)-1, (boolean)false, (boolean)false)), List.class);
    }

    public static String tokenListToEvalString(List<Token> groovySourceTokens) {
        CallSite[] callSiteArray = ReflectionCompleter.$getCallSiteArray();
        StringBuilder builder = (StringBuilder)ScriptBytecodeAdapter.castToType((Object)callSiteArray[224].callConstructor(StringBuilder.class), StringBuilder.class);
        Token token = null;
        Iterator iterator = (Iterator)ScriptBytecodeAdapter.castToType((Object)callSiteArray[225].call(groovySourceTokens), Iterator.class);
        if (iterator != null) {
            while (iterator.hasNext()) {
                token = (Token)ScriptBytecodeAdapter.castToType(iterator.next(), Token.class);
                if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[226].callGetProperty((Object)token), (Object)callSiteArray[227].callGetProperty(GroovyLexer.class))) {
                    callSiteArray[228].call(callSiteArray[229].call(callSiteArray[230].call((Object)builder, (Object)"'"), callSiteArray[231].callGetProperty((Object)token)), (Object)"'");
                    continue;
                }
                callSiteArray[232].call((Object)builder, callSiteArray[233].callGetProperty((Object)token));
            }
        }
        return ShortTypeHandling.castToString((Object)callSiteArray[234].call((Object)builder));
    }

    public static boolean acceptName(String name, String prefix) {
        CallSite[] callSiteArray = ReflectionCompleter.$getCallSiteArray();
        if (!BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            return (!DefaultTypeTransformation.booleanUnbox((Object)prefix) || DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[235].call((Object)name, (Object)prefix))) && !DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[236].call((Object)name, (Object)"$")) && !DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[237].call((Object)name, (Object)"_"));
        }
        return (!DefaultTypeTransformation.booleanUnbox((Object)prefix) || DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[238].call((Object)name, (Object)prefix))) && !DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[239].call((Object)name, (Object)"$")) && !DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[240].call((Object)name, (Object)"_"));
    }

    public static Collection<String> getMetaclassMethods(Object instance, String prefix, boolean includeMetaClassImplMethods) {
        public final class _getMetaclassMethods_closure5
        extends Closure
        implements GeneratedClosure {
            private /* synthetic */ Reference prefix;
            private /* synthetic */ Reference rv;
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _getMetaclassMethods_closure5(Object _outerInstance, Object _thisObject, Reference prefix, Reference rv) {
                Reference reference;
                Reference reference2;
                CallSite[] callSiteArray = _getMetaclassMethods_closure5.$getCallSiteArray();
                super(_outerInstance, _thisObject);
                this.prefix = reference2 = prefix;
                this.rv = reference = rv;
            }

            public Object doCall(MetaMethod mmit) {
                CallSite[] callSiteArray = _getMetaclassMethods_closure5.$getCallSiteArray();
                if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[0].callCurrent((GroovyObject)this, callSiteArray[1].callGetProperty((Object)mmit), this.prefix.get()))) {
                    return callSiteArray[2].call(this.rv.get(), callSiteArray[3].call(callSiteArray[4].call((Object)mmit), (Object)(ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[5].callGetProperty(callSiteArray[6].callGetProperty((Object)mmit)), (Object)0) ? "()" : "(")));
                }
                return null;
            }

            @Generated
            public Object call(MetaMethod mmit) {
                CallSite[] callSiteArray = _getMetaclassMethods_closure5.$getCallSiteArray();
                return callSiteArray[7].callCurrent((GroovyObject)this, (Object)mmit);
            }

            @Generated
            public String getPrefix() {
                CallSite[] callSiteArray = _getMetaclassMethods_closure5.$getCallSiteArray();
                return ShortTypeHandling.castToString((Object)this.prefix.get());
            }

            @Generated
            public Set getRv() {
                CallSite[] callSiteArray = _getMetaclassMethods_closure5.$getCallSiteArray();
                return (Set)ScriptBytecodeAdapter.castToType((Object)this.rv.get(), Set.class);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (((Object)((Object)this)).getClass() != _getMetaclassMethods_closure5.class) {
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
                stringArray[0] = "acceptName";
                stringArray[1] = "name";
                stringArray[2] = "leftShift";
                stringArray[3] = "plus";
                stringArray[4] = "getName";
                stringArray[5] = "length";
                stringArray[6] = "parameterTypes";
                stringArray[7] = "doCall";
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[8];
                _getMetaclassMethods_closure5.$createCallSiteArray_1(stringArray);
                return new CallSiteArray(_getMetaclassMethods_closure5.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _getMetaclassMethods_closure5.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        Reference prefix2 = new Reference((Object)prefix);
        CallSite[] callSiteArray = ReflectionCompleter.$getCallSiteArray();
        Reference rv = new Reference((Object)((Set)ScriptBytecodeAdapter.castToType((Object)callSiteArray[241].callConstructor(HashSet.class), Set.class)));
        MetaClass metaclass = (MetaClass)ScriptBytecodeAdapter.castToType((Object)callSiteArray[242].call(InvokerHelper.class, instance), MetaClass.class);
        if (!BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            if (includeMetaClassImplMethods || !(metaclass instanceof MetaClassImpl)) {
                callSiteArray[243].call(callSiteArray[244].callGetProperty((Object)metaclass), (Object)new _getMetaclassMethods_closure5(ReflectionCompleter.class, ReflectionCompleter.class, prefix2, rv));
            }
        } else if (includeMetaClassImplMethods || !(metaclass instanceof MetaClassImpl)) {
            callSiteArray[245].call(callSiteArray[246].callGetProperty((Object)metaclass), (Object)new _getMetaclassMethods_closure5(ReflectionCompleter.class, ReflectionCompleter.class, prefix2, rv));
        }
        return (Collection)ScriptBytecodeAdapter.castToType((Object)callSiteArray[247].call((Object)((Set)rv.get())), Collection.class);
    }

    public static Collection<ReflectionCompletionCandidate> getPublicFieldsAndMethods(Object instance, String prefix, boolean all) {
        boolean bl;
        boolean bl2;
        boolean bl3;
        boolean bl4;
        boolean bl5;
        boolean bl6;
        CallSite[] callSiteArray = ReflectionCompleter.$getCallSiteArray();
        Set rv = (Set)ScriptBytecodeAdapter.castToType((Object)callSiteArray[248].callConstructor(HashSet.class), Set.class);
        Class clazz = ShortTypeHandling.castToClass((Object)callSiteArray[249].call(instance));
        if (ScriptBytecodeAdapter.compareEqual((Object)clazz, null)) {
            return rv;
        }
        int isClass = 0;
        if (!BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            boolean bl7 = ScriptBytecodeAdapter.compareEqual((Object)clazz, Class.class);
            isClass = bl7 ? 1 : 0;
        } else {
            boolean bl8 = ScriptBytecodeAdapter.compareEqual((Object)clazz, Class.class);
            isClass = bl8 ? 1 : 0;
        }
        if (isClass != 0) {
            Class clazz2;
            clazz = clazz2 = (Class)ScriptBytecodeAdapter.asType((Object)instance, Class.class);
        }
        Class loopclazz = clazz;
        boolean renderBold = false;
        renderBold = !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass() ? (bl6 = isClass == 0) : (bl5 = isClass == 0);
        boolean showStatic = false;
        showStatic = !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass() ? (bl4 = isClass != 0 || all || ScriptBytecodeAdapter.compareGreaterThanEqual((Object)callSiteArray[250].call((Object)prefix), (Object)callSiteArray[251].call(Integer.class, callSiteArray[252].call(Preferences.class, callSiteArray[253].callGetProperty(Groovysh.class), (Object)"3")))) : (bl3 = isClass != 0 || all || ScriptBytecodeAdapter.compareGreaterThanEqual((Object)callSiteArray[254].call((Object)prefix), (Object)callSiteArray[255].call(Integer.class, callSiteArray[256].call(Preferences.class, callSiteArray[257].callGetProperty(Groovysh.class), (Object)"3"))));
        boolean showInstance = false;
        showInstance = !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass() ? (bl2 = isClass == 0 || all) : (bl = isClass == 0 || all);
        while (ScriptBytecodeAdapter.compareNotEqual((Object)loopclazz, null) && ScriptBytecodeAdapter.compareNotEqual((Object)loopclazz, Object.class) && ScriptBytecodeAdapter.compareNotEqual((Object)loopclazz, GroovyObject.class)) {
            boolean bl9;
            callSiteArray[258].callStatic(ReflectionCompleter.class, ArrayUtil.createArray((Object)loopclazz, (Object)showStatic, (Object)showInstance, (Object)prefix, (Object)rv, (Object)renderBold));
            renderBold = bl9 = false;
            Object object = callSiteArray[259].callGetProperty((Object)loopclazz);
            loopclazz = ShortTypeHandling.castToClass((Object)object);
        }
        if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[260].call((Object)clazz)) && showInstance) {
            String member = null;
            Iterator iterator = (Iterator)ScriptBytecodeAdapter.castToType((Object)callSiteArray[261].call((Object)ScriptBytecodeAdapter.createList((Object[])new Object[]{"length", "clone()"})), Iterator.class);
            if (iterator != null) {
                while (iterator.hasNext()) {
                    member = ShortTypeHandling.castToString(iterator.next());
                    if (!DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[262].call((Object)member, (Object)prefix))) continue;
                    callSiteArray[263].call((Object)rv, callSiteArray[264].callConstructor(ReflectionCompletionCandidate.class, (Object)member, callSiteArray[265].call(callSiteArray[266].callGetProperty(Ansi.Attribute.class))));
                }
            }
        }
        if (showInstance) {
            Set candidates = (Set)ScriptBytecodeAdapter.castToType((Object)callSiteArray[267].callConstructor(HashSet.class), Set.class);
            callSiteArray[268].call((Object)PROPERTIES_COMPLETER, instance, (Object)prefix, (Object)candidates);
            public final class _getPublicFieldsAndMethods_closure6
            extends Closure
            implements GeneratedClosure {
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;
                private static /* synthetic */ SoftReference $callSiteArray;

                public _getPublicFieldsAndMethods_closure6(Object _outerInstance, Object _thisObject) {
                    CallSite[] callSiteArray = _getPublicFieldsAndMethods_closure6.$getCallSiteArray();
                    super(_outerInstance, _thisObject);
                }

                public Object doCall(String it) {
                    CallSite[] callSiteArray = _getPublicFieldsAndMethods_closure6.$getCallSiteArray();
                    return callSiteArray[0].callConstructor(ReflectionCompletionCandidate.class, (Object)it, callSiteArray[1].call(callSiteArray[2].callGetProperty(AnsiRenderer.Code.class)));
                }

                @Generated
                public Object call(String it) {
                    CallSite[] callSiteArray = _getPublicFieldsAndMethods_closure6.$getCallSiteArray();
                    if (__$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                        return callSiteArray[3].callCurrent((GroovyObject)this, (Object)it);
                    }
                    return this.doCall(it);
                }

                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                    if (((Object)((Object)this)).getClass() != _getPublicFieldsAndMethods_closure6.class) {
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
                    stringArray[1] = "name";
                    stringArray[2] = "MAGENTA";
                    stringArray[3] = "doCall";
                }

                private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                    String[] stringArray = new String[4];
                    _getPublicFieldsAndMethods_closure6.$createCallSiteArray_1(stringArray);
                    return new CallSiteArray(_getPublicFieldsAndMethods_closure6.class, stringArray);
                }

                private static /* synthetic */ CallSite[] $getCallSiteArray() {
                    CallSiteArray callSiteArray;
                    if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                        callSiteArray = _getPublicFieldsAndMethods_closure6.$createCallSiteArray();
                        $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                    }
                    return callSiteArray.array;
                }
            }
            callSiteArray[269].call((Object)rv, callSiteArray[270].call((Object)candidates, (Object)new _getPublicFieldsAndMethods_closure6(ReflectionCompleter.class, ReflectionCompleter.class)));
        }
        return (Collection)ScriptBytecodeAdapter.castToType((Object)callSiteArray[271].call((Object)rv), Collection.class);
    }

    public static Object removeStandardMethods(Collection<ReflectionCompletionCandidate> candidates) {
        CallSite[] callSiteArray = ReflectionCompleter.$getCallSiteArray();
        String defaultMethod = null;
        Iterator iterator = (Iterator)ScriptBytecodeAdapter.castToType((Object)callSiteArray[272].call((Object)ScriptBytecodeAdapter.createList((Object[])new Object[]{"clone()", "finalize()", "getClass()", "getMetaClass()", "getProperty(", "invokeMethod(", "setMetaClass(", "setProperty(", "equals(", "hashCode()", "toString()", "notify()", "notifyAll()", "wait(", "wait()"})), Iterator.class);
        if (iterator != null) {
            block0: while (iterator.hasNext()) {
                defaultMethod = ShortTypeHandling.castToString(iterator.next());
                ReflectionCompletionCandidate candidate = null;
                Iterator iterator2 = (Iterator)ScriptBytecodeAdapter.castToType((Object)callSiteArray[273].call(candidates), Iterator.class);
                if (iterator2 == null) continue;
                while (iterator2.hasNext()) {
                    candidate = (ReflectionCompletionCandidate)ScriptBytecodeAdapter.castToType(iterator2.next(), ReflectionCompletionCandidate.class);
                    if (!DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[274].call((Object)defaultMethod, callSiteArray[275].callGroovyObjectGetProperty((Object)candidate)))) continue;
                    callSiteArray[276].call(candidates, (Object)candidate);
                    continue block0;
                }
            }
        }
        return null;
    }

    public static List<String> getDefaultMethods(Object instance, String prefix) {
        public final class _getDefaultMethods_closure28
        extends Closure
        implements GeneratedClosure {
            private /* synthetic */ Reference candidates;
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _getDefaultMethods_closure28(Object _outerInstance, Object _thisObject, Reference candidates) {
                Reference reference;
                CallSite[] callSiteArray = _getDefaultMethods_closure28.$getCallSiteArray();
                super(_outerInstance, _thisObject);
                this.candidates = reference = candidates;
            }

            public Object doCall(Object it) {
                CallSite[] callSiteArray = _getDefaultMethods_closure28.$getCallSiteArray();
                return callSiteArray[0].call(this.candidates.get(), it);
            }

            @Generated
            public List getCandidates() {
                CallSite[] callSiteArray = _getDefaultMethods_closure28.$getCallSiteArray();
                return (List)ScriptBytecodeAdapter.castToType((Object)this.candidates.get(), List.class);
            }

            @Generated
            public Object doCall() {
                CallSite[] callSiteArray = _getDefaultMethods_closure28.$getCallSiteArray();
                return this.doCall(null);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (((Object)((Object)this)).getClass() != _getDefaultMethods_closure28.class) {
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
                stringArray[0] = "add";
                return new CallSiteArray(_getDefaultMethods_closure28.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _getDefaultMethods_closure28.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        public final class _getDefaultMethods_closure27
        extends Closure
        implements GeneratedClosure {
            private /* synthetic */ Reference prefix;
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _getDefaultMethods_closure27(Object _outerInstance, Object _thisObject, Reference prefix) {
                Reference reference;
                CallSite[] callSiteArray = _getDefaultMethods_closure27.$getCallSiteArray();
                super(_outerInstance, _thisObject);
                this.prefix = reference = prefix;
            }

            public Object doCall(Object it) {
                CallSite[] callSiteArray = _getDefaultMethods_closure27.$getCallSiteArray();
                return callSiteArray[0].call(it, this.prefix.get());
            }

            @Generated
            public String getPrefix() {
                CallSite[] callSiteArray = _getDefaultMethods_closure27.$getCallSiteArray();
                return ShortTypeHandling.castToString((Object)this.prefix.get());
            }

            @Generated
            public Object doCall() {
                CallSite[] callSiteArray = _getDefaultMethods_closure27.$getCallSiteArray();
                return this.doCall(null);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (((Object)((Object)this)).getClass() != _getDefaultMethods_closure27.class) {
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
                stringArray[0] = "startsWith";
                return new CallSiteArray(_getDefaultMethods_closure27.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _getDefaultMethods_closure27.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        Reference prefix2 = new Reference((Object)prefix);
        CallSite[] callSiteArray = ReflectionCompleter.$getCallSiteArray();
        Reference candidates = new Reference((Object)ScriptBytecodeAdapter.createList((Object[])new Object[0]));
        if (instance instanceof Iterable) {
            public final class _getDefaultMethods_closure7
            extends Closure
            implements GeneratedClosure {
                private /* synthetic */ Reference prefix;
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;
                private static /* synthetic */ SoftReference $callSiteArray;

                public _getDefaultMethods_closure7(Object _outerInstance, Object _thisObject, Reference prefix) {
                    Reference reference;
                    CallSite[] callSiteArray = _getDefaultMethods_closure7.$getCallSiteArray();
                    super(_outerInstance, _thisObject);
                    this.prefix = reference = prefix;
                }

                public Object doCall(Object it) {
                    CallSite[] callSiteArray = _getDefaultMethods_closure7.$getCallSiteArray();
                    return callSiteArray[0].call(it, this.prefix.get());
                }

                @Generated
                public String getPrefix() {
                    CallSite[] callSiteArray = _getDefaultMethods_closure7.$getCallSiteArray();
                    return ShortTypeHandling.castToString((Object)this.prefix.get());
                }

                @Generated
                public Object doCall() {
                    CallSite[] callSiteArray = _getDefaultMethods_closure7.$getCallSiteArray();
                    return this.doCall(null);
                }

                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                    if (((Object)((Object)this)).getClass() != _getDefaultMethods_closure7.class) {
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
                    stringArray[0] = "startsWith";
                    return new CallSiteArray(_getDefaultMethods_closure7.class, stringArray);
                }

                private static /* synthetic */ CallSite[] $getCallSiteArray() {
                    CallSiteArray callSiteArray;
                    if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                        callSiteArray = _getDefaultMethods_closure7.$createCallSiteArray();
                        $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                    }
                    return callSiteArray.array;
                }
            }
            public final class _getDefaultMethods_closure8
            extends Closure
            implements GeneratedClosure {
                private /* synthetic */ Reference candidates;
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;
                private static /* synthetic */ SoftReference $callSiteArray;

                public _getDefaultMethods_closure8(Object _outerInstance, Object _thisObject, Reference candidates) {
                    Reference reference;
                    CallSite[] callSiteArray = _getDefaultMethods_closure8.$getCallSiteArray();
                    super(_outerInstance, _thisObject);
                    this.candidates = reference = candidates;
                }

                public Object doCall(Object it) {
                    CallSite[] callSiteArray = _getDefaultMethods_closure8.$getCallSiteArray();
                    return callSiteArray[0].call(this.candidates.get(), it);
                }

                @Generated
                public List getCandidates() {
                    CallSite[] callSiteArray = _getDefaultMethods_closure8.$getCallSiteArray();
                    return (List)ScriptBytecodeAdapter.castToType((Object)this.candidates.get(), List.class);
                }

                @Generated
                public Object doCall() {
                    CallSite[] callSiteArray = _getDefaultMethods_closure8.$getCallSiteArray();
                    return this.doCall(null);
                }

                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                    if (((Object)((Object)this)).getClass() != _getDefaultMethods_closure8.class) {
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
                    stringArray[0] = "add";
                    return new CallSiteArray(_getDefaultMethods_closure8.class, stringArray);
                }

                private static /* synthetic */ CallSite[] $getCallSiteArray() {
                    CallSiteArray callSiteArray;
                    if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                        callSiteArray = _getDefaultMethods_closure8.$createCallSiteArray();
                        $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                    }
                    return callSiteArray.array;
                }
            }
            callSiteArray[277].call(callSiteArray[278].call((Object)ScriptBytecodeAdapter.createList((Object[])new Object[]{"any()", "any(", "collect()", "collect(", "combinations()", "count(", "countBy(", "drop(", "dropRight(", "dropWhile(", "each()", "each(", "eachPermutation(", "every()", "every(", "find(", "findResult(", "findResults(", "flatten()", "init()", "inject(", "intersect(", "join(", "max()", "min()", "reverse()", "size()", "sort()", "split(", "take(", "takeRight(", "takeWhile(", "toSet()", "retainAll(", "removeAll(", "unique()", "unique("}), (Object)new _getDefaultMethods_closure7(ReflectionCompleter.class, ReflectionCompleter.class, prefix2)), (Object)new _getDefaultMethods_closure8(ReflectionCompleter.class, ReflectionCompleter.class, candidates));
            if (instance instanceof Collection) {
                public final class _getDefaultMethods_closure9
                extends Closure
                implements GeneratedClosure {
                    private /* synthetic */ Reference prefix;
                    private static /* synthetic */ ClassInfo $staticClassInfo;
                    public static transient /* synthetic */ boolean __$stMC;
                    private static /* synthetic */ SoftReference $callSiteArray;

                    public _getDefaultMethods_closure9(Object _outerInstance, Object _thisObject, Reference prefix) {
                        Reference reference;
                        CallSite[] callSiteArray = _getDefaultMethods_closure9.$getCallSiteArray();
                        super(_outerInstance, _thisObject);
                        this.prefix = reference = prefix;
                    }

                    public Object doCall(Object it) {
                        CallSite[] callSiteArray = _getDefaultMethods_closure9.$getCallSiteArray();
                        return callSiteArray[0].call(it, this.prefix.get());
                    }

                    @Generated
                    public String getPrefix() {
                        CallSite[] callSiteArray = _getDefaultMethods_closure9.$getCallSiteArray();
                        return ShortTypeHandling.castToString((Object)this.prefix.get());
                    }

                    @Generated
                    public Object doCall() {
                        CallSite[] callSiteArray = _getDefaultMethods_closure9.$getCallSiteArray();
                        return this.doCall(null);
                    }

                    protected /* synthetic */ MetaClass $getStaticMetaClass() {
                        if (((Object)((Object)this)).getClass() != _getDefaultMethods_closure9.class) {
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
                        stringArray[0] = "startsWith";
                        return new CallSiteArray(_getDefaultMethods_closure9.class, stringArray);
                    }

                    private static /* synthetic */ CallSite[] $getCallSiteArray() {
                        CallSiteArray callSiteArray;
                        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                            callSiteArray = _getDefaultMethods_closure9.$createCallSiteArray();
                            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                        }
                        return callSiteArray.array;
                    }
                }
                public final class _getDefaultMethods_closure10
                extends Closure
                implements GeneratedClosure {
                    private /* synthetic */ Reference candidates;
                    private static /* synthetic */ ClassInfo $staticClassInfo;
                    public static transient /* synthetic */ boolean __$stMC;
                    private static /* synthetic */ SoftReference $callSiteArray;

                    public _getDefaultMethods_closure10(Object _outerInstance, Object _thisObject, Reference candidates) {
                        Reference reference;
                        CallSite[] callSiteArray = _getDefaultMethods_closure10.$getCallSiteArray();
                        super(_outerInstance, _thisObject);
                        this.candidates = reference = candidates;
                    }

                    public Object doCall(Object it) {
                        CallSite[] callSiteArray = _getDefaultMethods_closure10.$getCallSiteArray();
                        return callSiteArray[0].call(this.candidates.get(), it);
                    }

                    @Generated
                    public List getCandidates() {
                        CallSite[] callSiteArray = _getDefaultMethods_closure10.$getCallSiteArray();
                        return (List)ScriptBytecodeAdapter.castToType((Object)this.candidates.get(), List.class);
                    }

                    @Generated
                    public Object doCall() {
                        CallSite[] callSiteArray = _getDefaultMethods_closure10.$getCallSiteArray();
                        return this.doCall(null);
                    }

                    protected /* synthetic */ MetaClass $getStaticMetaClass() {
                        if (((Object)((Object)this)).getClass() != _getDefaultMethods_closure10.class) {
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
                        stringArray[0] = "add";
                        return new CallSiteArray(_getDefaultMethods_closure10.class, stringArray);
                    }

                    private static /* synthetic */ CallSite[] $getCallSiteArray() {
                        CallSiteArray callSiteArray;
                        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                            callSiteArray = _getDefaultMethods_closure10.$createCallSiteArray();
                            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                        }
                        return callSiteArray.array;
                    }
                }
                callSiteArray[279].call(callSiteArray[280].call((Object)ScriptBytecodeAdapter.createList((Object[])new Object[]{"grep("}), (Object)new _getDefaultMethods_closure9(ReflectionCompleter.class, ReflectionCompleter.class, prefix2)), (Object)new _getDefaultMethods_closure10(ReflectionCompleter.class, ReflectionCompleter.class, candidates));
            }
            if (instance instanceof List) {
                public final class _getDefaultMethods_closure11
                extends Closure
                implements GeneratedClosure {
                    private /* synthetic */ Reference prefix;
                    private static /* synthetic */ ClassInfo $staticClassInfo;
                    public static transient /* synthetic */ boolean __$stMC;
                    private static /* synthetic */ SoftReference $callSiteArray;

                    public _getDefaultMethods_closure11(Object _outerInstance, Object _thisObject, Reference prefix) {
                        Reference reference;
                        CallSite[] callSiteArray = _getDefaultMethods_closure11.$getCallSiteArray();
                        super(_outerInstance, _thisObject);
                        this.prefix = reference = prefix;
                    }

                    public Object doCall(Object it) {
                        CallSite[] callSiteArray = _getDefaultMethods_closure11.$getCallSiteArray();
                        return callSiteArray[0].call(it, this.prefix.get());
                    }

                    @Generated
                    public String getPrefix() {
                        CallSite[] callSiteArray = _getDefaultMethods_closure11.$getCallSiteArray();
                        return ShortTypeHandling.castToString((Object)this.prefix.get());
                    }

                    @Generated
                    public Object doCall() {
                        CallSite[] callSiteArray = _getDefaultMethods_closure11.$getCallSiteArray();
                        return this.doCall(null);
                    }

                    protected /* synthetic */ MetaClass $getStaticMetaClass() {
                        if (((Object)((Object)this)).getClass() != _getDefaultMethods_closure11.class) {
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
                        stringArray[0] = "startsWith";
                        return new CallSiteArray(_getDefaultMethods_closure11.class, stringArray);
                    }

                    private static /* synthetic */ CallSite[] $getCallSiteArray() {
                        CallSiteArray callSiteArray;
                        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                            callSiteArray = _getDefaultMethods_closure11.$createCallSiteArray();
                            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                        }
                        return callSiteArray.array;
                    }
                }
                public final class _getDefaultMethods_closure12
                extends Closure
                implements GeneratedClosure {
                    private /* synthetic */ Reference candidates;
                    private static /* synthetic */ ClassInfo $staticClassInfo;
                    public static transient /* synthetic */ boolean __$stMC;
                    private static /* synthetic */ SoftReference $callSiteArray;

                    public _getDefaultMethods_closure12(Object _outerInstance, Object _thisObject, Reference candidates) {
                        Reference reference;
                        CallSite[] callSiteArray = _getDefaultMethods_closure12.$getCallSiteArray();
                        super(_outerInstance, _thisObject);
                        this.candidates = reference = candidates;
                    }

                    public Object doCall(Object it) {
                        CallSite[] callSiteArray = _getDefaultMethods_closure12.$getCallSiteArray();
                        return callSiteArray[0].call(this.candidates.get(), it);
                    }

                    @Generated
                    public List getCandidates() {
                        CallSite[] callSiteArray = _getDefaultMethods_closure12.$getCallSiteArray();
                        return (List)ScriptBytecodeAdapter.castToType((Object)this.candidates.get(), List.class);
                    }

                    @Generated
                    public Object doCall() {
                        CallSite[] callSiteArray = _getDefaultMethods_closure12.$getCallSiteArray();
                        return this.doCall(null);
                    }

                    protected /* synthetic */ MetaClass $getStaticMetaClass() {
                        if (((Object)((Object)this)).getClass() != _getDefaultMethods_closure12.class) {
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
                        stringArray[0] = "add";
                        return new CallSiteArray(_getDefaultMethods_closure12.class, stringArray);
                    }

                    private static /* synthetic */ CallSite[] $getCallSiteArray() {
                        CallSiteArray callSiteArray;
                        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                            callSiteArray = _getDefaultMethods_closure12.$createCallSiteArray();
                            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                        }
                        return callSiteArray.array;
                    }
                }
                callSiteArray[281].call(callSiteArray[282].call((Object)ScriptBytecodeAdapter.createList((Object[])new Object[]{"collate(", "execute()", "execute(", "pop()", "transpose()"}), (Object)new _getDefaultMethods_closure11(ReflectionCompleter.class, ReflectionCompleter.class, prefix2)), (Object)new _getDefaultMethods_closure12(ReflectionCompleter.class, ReflectionCompleter.class, candidates));
            }
        }
        if (instance instanceof Map) {
            public final class _getDefaultMethods_closure13
            extends Closure
            implements GeneratedClosure {
                private /* synthetic */ Reference prefix;
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;
                private static /* synthetic */ SoftReference $callSiteArray;

                public _getDefaultMethods_closure13(Object _outerInstance, Object _thisObject, Reference prefix) {
                    Reference reference;
                    CallSite[] callSiteArray = _getDefaultMethods_closure13.$getCallSiteArray();
                    super(_outerInstance, _thisObject);
                    this.prefix = reference = prefix;
                }

                public Object doCall(Object it) {
                    CallSite[] callSiteArray = _getDefaultMethods_closure13.$getCallSiteArray();
                    return callSiteArray[0].call(it, this.prefix.get());
                }

                @Generated
                public String getPrefix() {
                    CallSite[] callSiteArray = _getDefaultMethods_closure13.$getCallSiteArray();
                    return ShortTypeHandling.castToString((Object)this.prefix.get());
                }

                @Generated
                public Object doCall() {
                    CallSite[] callSiteArray = _getDefaultMethods_closure13.$getCallSiteArray();
                    return this.doCall(null);
                }

                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                    if (((Object)((Object)this)).getClass() != _getDefaultMethods_closure13.class) {
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
                    stringArray[0] = "startsWith";
                    return new CallSiteArray(_getDefaultMethods_closure13.class, stringArray);
                }

                private static /* synthetic */ CallSite[] $getCallSiteArray() {
                    CallSiteArray callSiteArray;
                    if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                        callSiteArray = _getDefaultMethods_closure13.$createCallSiteArray();
                        $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                    }
                    return callSiteArray.array;
                }
            }
            public final class _getDefaultMethods_closure14
            extends Closure
            implements GeneratedClosure {
                private /* synthetic */ Reference candidates;
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;
                private static /* synthetic */ SoftReference $callSiteArray;

                public _getDefaultMethods_closure14(Object _outerInstance, Object _thisObject, Reference candidates) {
                    Reference reference;
                    CallSite[] callSiteArray = _getDefaultMethods_closure14.$getCallSiteArray();
                    super(_outerInstance, _thisObject);
                    this.candidates = reference = candidates;
                }

                public Object doCall(Object it) {
                    CallSite[] callSiteArray = _getDefaultMethods_closure14.$getCallSiteArray();
                    return callSiteArray[0].call(this.candidates.get(), it);
                }

                @Generated
                public List getCandidates() {
                    CallSite[] callSiteArray = _getDefaultMethods_closure14.$getCallSiteArray();
                    return (List)ScriptBytecodeAdapter.castToType((Object)this.candidates.get(), List.class);
                }

                @Generated
                public Object doCall() {
                    CallSite[] callSiteArray = _getDefaultMethods_closure14.$getCallSiteArray();
                    return this.doCall(null);
                }

                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                    if (((Object)((Object)this)).getClass() != _getDefaultMethods_closure14.class) {
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
                    stringArray[0] = "add";
                    return new CallSiteArray(_getDefaultMethods_closure14.class, stringArray);
                }

                private static /* synthetic */ CallSite[] $getCallSiteArray() {
                    CallSiteArray callSiteArray;
                    if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                        callSiteArray = _getDefaultMethods_closure14.$createCallSiteArray();
                        $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                    }
                    return callSiteArray.array;
                }
            }
            callSiteArray[283].call(callSiteArray[284].call((Object)ScriptBytecodeAdapter.createList((Object[])new Object[]{"any(", "collect(", "collectEntries(", "collectMany(", "count(", "drop(", "each(", "every(", "find(", "findAll(", "findResult(", "findResults(", "groupEntriesBy(", "groupBy(", "inject(", "intersect(", "max(", "min(", "sort(", "spread()", "subMap(", "take(", "takeWhile("}), (Object)new _getDefaultMethods_closure13(ReflectionCompleter.class, ReflectionCompleter.class, prefix2)), (Object)new _getDefaultMethods_closure14(ReflectionCompleter.class, ReflectionCompleter.class, candidates));
        }
        if (instance instanceof File) {
            public final class _getDefaultMethods_closure15
            extends Closure
            implements GeneratedClosure {
                private /* synthetic */ Reference prefix;
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;
                private static /* synthetic */ SoftReference $callSiteArray;

                public _getDefaultMethods_closure15(Object _outerInstance, Object _thisObject, Reference prefix) {
                    Reference reference;
                    CallSite[] callSiteArray = _getDefaultMethods_closure15.$getCallSiteArray();
                    super(_outerInstance, _thisObject);
                    this.prefix = reference = prefix;
                }

                public Object doCall(Object it) {
                    CallSite[] callSiteArray = _getDefaultMethods_closure15.$getCallSiteArray();
                    return callSiteArray[0].call(it, this.prefix.get());
                }

                @Generated
                public String getPrefix() {
                    CallSite[] callSiteArray = _getDefaultMethods_closure15.$getCallSiteArray();
                    return ShortTypeHandling.castToString((Object)this.prefix.get());
                }

                @Generated
                public Object doCall() {
                    CallSite[] callSiteArray = _getDefaultMethods_closure15.$getCallSiteArray();
                    return this.doCall(null);
                }

                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                    if (((Object)((Object)this)).getClass() != _getDefaultMethods_closure15.class) {
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
                    stringArray[0] = "startsWith";
                    return new CallSiteArray(_getDefaultMethods_closure15.class, stringArray);
                }

                private static /* synthetic */ CallSite[] $getCallSiteArray() {
                    CallSiteArray callSiteArray;
                    if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                        callSiteArray = _getDefaultMethods_closure15.$createCallSiteArray();
                        $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                    }
                    return callSiteArray.array;
                }
            }
            public final class _getDefaultMethods_closure16
            extends Closure
            implements GeneratedClosure {
                private /* synthetic */ Reference candidates;
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;
                private static /* synthetic */ SoftReference $callSiteArray;

                public _getDefaultMethods_closure16(Object _outerInstance, Object _thisObject, Reference candidates) {
                    Reference reference;
                    CallSite[] callSiteArray = _getDefaultMethods_closure16.$getCallSiteArray();
                    super(_outerInstance, _thisObject);
                    this.candidates = reference = candidates;
                }

                public Object doCall(Object it) {
                    CallSite[] callSiteArray = _getDefaultMethods_closure16.$getCallSiteArray();
                    return callSiteArray[0].call(this.candidates.get(), it);
                }

                @Generated
                public List getCandidates() {
                    CallSite[] callSiteArray = _getDefaultMethods_closure16.$getCallSiteArray();
                    return (List)ScriptBytecodeAdapter.castToType((Object)this.candidates.get(), List.class);
                }

                @Generated
                public Object doCall() {
                    CallSite[] callSiteArray = _getDefaultMethods_closure16.$getCallSiteArray();
                    return this.doCall(null);
                }

                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                    if (((Object)((Object)this)).getClass() != _getDefaultMethods_closure16.class) {
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
                    stringArray[0] = "add";
                    return new CallSiteArray(_getDefaultMethods_closure16.class, stringArray);
                }

                private static /* synthetic */ CallSite[] $getCallSiteArray() {
                    CallSiteArray callSiteArray;
                    if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                        callSiteArray = _getDefaultMethods_closure16.$createCallSiteArray();
                        $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                    }
                    return callSiteArray.array;
                }
            }
            callSiteArray[285].call(callSiteArray[286].call((Object)ScriptBytecodeAdapter.createList((Object[])new Object[]{"append(", "createTempDir()", "createTempDir(", "deleteDir()", "directorySize()", "eachByte(", "eachDir(", "eachDirMatch(", "eachDirRecurse(", "eachFile(", "eachFileMatch(", "eachFileRecurse(", "eachLine(", "filterLine(", "getBytes()", "getText()", "getText(", "newInputStream()", "newOutputStream()", "newPrintWriter()", "newPrintWriter(", "newReader()", "newReader(", "newWriter()", "newWriter(", "readBytes()", "readLines(", "setBytes(", "setText(", "size()", "splitEachLine(", "traverse(", "withInputStream(", "withOutputStream(", "withPrintWriter(", "withReader(", "withWriter(", "withWriterAppend(", "write("}), (Object)new _getDefaultMethods_closure15(ReflectionCompleter.class, ReflectionCompleter.class, prefix2)), (Object)new _getDefaultMethods_closure16(ReflectionCompleter.class, ReflectionCompleter.class, candidates));
        }
        if (instance instanceof String) {
            public final class _getDefaultMethods_closure17
            extends Closure
            implements GeneratedClosure {
                private /* synthetic */ Reference prefix;
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;
                private static /* synthetic */ SoftReference $callSiteArray;

                public _getDefaultMethods_closure17(Object _outerInstance, Object _thisObject, Reference prefix) {
                    Reference reference;
                    CallSite[] callSiteArray = _getDefaultMethods_closure17.$getCallSiteArray();
                    super(_outerInstance, _thisObject);
                    this.prefix = reference = prefix;
                }

                public Object doCall(Object it) {
                    CallSite[] callSiteArray = _getDefaultMethods_closure17.$getCallSiteArray();
                    return callSiteArray[0].call(it, this.prefix.get());
                }

                @Generated
                public String getPrefix() {
                    CallSite[] callSiteArray = _getDefaultMethods_closure17.$getCallSiteArray();
                    return ShortTypeHandling.castToString((Object)this.prefix.get());
                }

                @Generated
                public Object doCall() {
                    CallSite[] callSiteArray = _getDefaultMethods_closure17.$getCallSiteArray();
                    return this.doCall(null);
                }

                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                    if (((Object)((Object)this)).getClass() != _getDefaultMethods_closure17.class) {
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
                    stringArray[0] = "startsWith";
                    return new CallSiteArray(_getDefaultMethods_closure17.class, stringArray);
                }

                private static /* synthetic */ CallSite[] $getCallSiteArray() {
                    CallSiteArray callSiteArray;
                    if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                        callSiteArray = _getDefaultMethods_closure17.$createCallSiteArray();
                        $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                    }
                    return callSiteArray.array;
                }
            }
            public final class _getDefaultMethods_closure18
            extends Closure
            implements GeneratedClosure {
                private /* synthetic */ Reference candidates;
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;
                private static /* synthetic */ SoftReference $callSiteArray;

                public _getDefaultMethods_closure18(Object _outerInstance, Object _thisObject, Reference candidates) {
                    Reference reference;
                    CallSite[] callSiteArray = _getDefaultMethods_closure18.$getCallSiteArray();
                    super(_outerInstance, _thisObject);
                    this.candidates = reference = candidates;
                }

                public Object doCall(Object it) {
                    CallSite[] callSiteArray = _getDefaultMethods_closure18.$getCallSiteArray();
                    return callSiteArray[0].call(this.candidates.get(), it);
                }

                @Generated
                public List getCandidates() {
                    CallSite[] callSiteArray = _getDefaultMethods_closure18.$getCallSiteArray();
                    return (List)ScriptBytecodeAdapter.castToType((Object)this.candidates.get(), List.class);
                }

                @Generated
                public Object doCall() {
                    CallSite[] callSiteArray = _getDefaultMethods_closure18.$getCallSiteArray();
                    return this.doCall(null);
                }

                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                    if (((Object)((Object)this)).getClass() != _getDefaultMethods_closure18.class) {
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
                    stringArray[0] = "add";
                    return new CallSiteArray(_getDefaultMethods_closure18.class, stringArray);
                }

                private static /* synthetic */ CallSite[] $getCallSiteArray() {
                    CallSiteArray callSiteArray;
                    if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                        callSiteArray = _getDefaultMethods_closure18.$createCallSiteArray();
                        $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                    }
                    return callSiteArray.array;
                }
            }
            callSiteArray[287].call(callSiteArray[288].call((Object)ScriptBytecodeAdapter.createList((Object[])new Object[]{"capitalize()", "center(", "collectReplacements(", "count(", "decodeBase64()", "decodeHex()", "denormalize()", "eachLine(", "eachMatch(", "execute()", "execute(", "find(", "findAll(", "isAllWhitespace()", "isBigDecimal()", "isBigInteger()", "isDouble()", "isFloat()", "isInteger()", "isLong()", "isNumber()", "normalize()", "padLeft(", "padRight(", "readLines()", "reverse()", "size()", "splitEachLine(", "stripIndent(", "stripMargin(", "toBigDecimal()", "toBigInteger()", "toBoolean()", "toCharacter()", "toDouble()", "toFloat()", "toInteger()", "toList()", "toLong()", "toSet()", "toShort()", "toURI()", "toURL()", "tokenize(", "tr("}), (Object)new _getDefaultMethods_closure17(ReflectionCompleter.class, ReflectionCompleter.class, prefix2)), (Object)new _getDefaultMethods_closure18(ReflectionCompleter.class, ReflectionCompleter.class, candidates));
        }
        if (instance instanceof URL) {
            public final class _getDefaultMethods_closure19
            extends Closure
            implements GeneratedClosure {
                private /* synthetic */ Reference prefix;
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;
                private static /* synthetic */ SoftReference $callSiteArray;

                public _getDefaultMethods_closure19(Object _outerInstance, Object _thisObject, Reference prefix) {
                    Reference reference;
                    CallSite[] callSiteArray = _getDefaultMethods_closure19.$getCallSiteArray();
                    super(_outerInstance, _thisObject);
                    this.prefix = reference = prefix;
                }

                public Object doCall(Object it) {
                    CallSite[] callSiteArray = _getDefaultMethods_closure19.$getCallSiteArray();
                    return callSiteArray[0].call(it, this.prefix.get());
                }

                @Generated
                public String getPrefix() {
                    CallSite[] callSiteArray = _getDefaultMethods_closure19.$getCallSiteArray();
                    return ShortTypeHandling.castToString((Object)this.prefix.get());
                }

                @Generated
                public Object doCall() {
                    CallSite[] callSiteArray = _getDefaultMethods_closure19.$getCallSiteArray();
                    return this.doCall(null);
                }

                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                    if (((Object)((Object)this)).getClass() != _getDefaultMethods_closure19.class) {
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
                    stringArray[0] = "startsWith";
                    return new CallSiteArray(_getDefaultMethods_closure19.class, stringArray);
                }

                private static /* synthetic */ CallSite[] $getCallSiteArray() {
                    CallSiteArray callSiteArray;
                    if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                        callSiteArray = _getDefaultMethods_closure19.$createCallSiteArray();
                        $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                    }
                    return callSiteArray.array;
                }
            }
            public final class _getDefaultMethods_closure20
            extends Closure
            implements GeneratedClosure {
                private /* synthetic */ Reference candidates;
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;
                private static /* synthetic */ SoftReference $callSiteArray;

                public _getDefaultMethods_closure20(Object _outerInstance, Object _thisObject, Reference candidates) {
                    Reference reference;
                    CallSite[] callSiteArray = _getDefaultMethods_closure20.$getCallSiteArray();
                    super(_outerInstance, _thisObject);
                    this.candidates = reference = candidates;
                }

                public Object doCall(Object it) {
                    CallSite[] callSiteArray = _getDefaultMethods_closure20.$getCallSiteArray();
                    return callSiteArray[0].call(this.candidates.get(), it);
                }

                @Generated
                public List getCandidates() {
                    CallSite[] callSiteArray = _getDefaultMethods_closure20.$getCallSiteArray();
                    return (List)ScriptBytecodeAdapter.castToType((Object)this.candidates.get(), List.class);
                }

                @Generated
                public Object doCall() {
                    CallSite[] callSiteArray = _getDefaultMethods_closure20.$getCallSiteArray();
                    return this.doCall(null);
                }

                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                    if (((Object)((Object)this)).getClass() != _getDefaultMethods_closure20.class) {
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
                    stringArray[0] = "add";
                    return new CallSiteArray(_getDefaultMethods_closure20.class, stringArray);
                }

                private static /* synthetic */ CallSite[] $getCallSiteArray() {
                    CallSiteArray callSiteArray;
                    if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                        callSiteArray = _getDefaultMethods_closure20.$createCallSiteArray();
                        $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                    }
                    return callSiteArray.array;
                }
            }
            callSiteArray[289].call(callSiteArray[290].call((Object)ScriptBytecodeAdapter.createList((Object[])new Object[]{"eachLine(", "filterLine(", "getBytes()", "getBytes(", "getText()", "getText(", "newInputStream()", "newInputStream(", "newReader()", "newReader(", "readLines()", "readLines(", "splitEachLine(", "withInputStream(", "withReader("}), (Object)new _getDefaultMethods_closure19(ReflectionCompleter.class, ReflectionCompleter.class, prefix2)), (Object)new _getDefaultMethods_closure20(ReflectionCompleter.class, ReflectionCompleter.class, candidates));
        }
        if (instance instanceof InputStream) {
            public final class _getDefaultMethods_closure21
            extends Closure
            implements GeneratedClosure {
                private /* synthetic */ Reference prefix;
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;
                private static /* synthetic */ SoftReference $callSiteArray;

                public _getDefaultMethods_closure21(Object _outerInstance, Object _thisObject, Reference prefix) {
                    Reference reference;
                    CallSite[] callSiteArray = _getDefaultMethods_closure21.$getCallSiteArray();
                    super(_outerInstance, _thisObject);
                    this.prefix = reference = prefix;
                }

                public Object doCall(Object it) {
                    CallSite[] callSiteArray = _getDefaultMethods_closure21.$getCallSiteArray();
                    return callSiteArray[0].call(it, this.prefix.get());
                }

                @Generated
                public String getPrefix() {
                    CallSite[] callSiteArray = _getDefaultMethods_closure21.$getCallSiteArray();
                    return ShortTypeHandling.castToString((Object)this.prefix.get());
                }

                @Generated
                public Object doCall() {
                    CallSite[] callSiteArray = _getDefaultMethods_closure21.$getCallSiteArray();
                    return this.doCall(null);
                }

                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                    if (((Object)((Object)this)).getClass() != _getDefaultMethods_closure21.class) {
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
                    stringArray[0] = "startsWith";
                    return new CallSiteArray(_getDefaultMethods_closure21.class, stringArray);
                }

                private static /* synthetic */ CallSite[] $getCallSiteArray() {
                    CallSiteArray callSiteArray;
                    if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                        callSiteArray = _getDefaultMethods_closure21.$createCallSiteArray();
                        $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                    }
                    return callSiteArray.array;
                }
            }
            public final class _getDefaultMethods_closure22
            extends Closure
            implements GeneratedClosure {
                private /* synthetic */ Reference candidates;
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;
                private static /* synthetic */ SoftReference $callSiteArray;

                public _getDefaultMethods_closure22(Object _outerInstance, Object _thisObject, Reference candidates) {
                    Reference reference;
                    CallSite[] callSiteArray = _getDefaultMethods_closure22.$getCallSiteArray();
                    super(_outerInstance, _thisObject);
                    this.candidates = reference = candidates;
                }

                public Object doCall(Object it) {
                    CallSite[] callSiteArray = _getDefaultMethods_closure22.$getCallSiteArray();
                    return callSiteArray[0].call(this.candidates.get(), it);
                }

                @Generated
                public List getCandidates() {
                    CallSite[] callSiteArray = _getDefaultMethods_closure22.$getCallSiteArray();
                    return (List)ScriptBytecodeAdapter.castToType((Object)this.candidates.get(), List.class);
                }

                @Generated
                public Object doCall() {
                    CallSite[] callSiteArray = _getDefaultMethods_closure22.$getCallSiteArray();
                    return this.doCall(null);
                }

                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                    if (((Object)((Object)this)).getClass() != _getDefaultMethods_closure22.class) {
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
                    stringArray[0] = "add";
                    return new CallSiteArray(_getDefaultMethods_closure22.class, stringArray);
                }

                private static /* synthetic */ CallSite[] $getCallSiteArray() {
                    CallSiteArray callSiteArray;
                    if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                        callSiteArray = _getDefaultMethods_closure22.$createCallSiteArray();
                        $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                    }
                    return callSiteArray.array;
                }
            }
            callSiteArray[291].call(callSiteArray[292].call((Object)ScriptBytecodeAdapter.createList((Object[])new Object[]{"eachLine(", "filterLine(", "getBytes()", "getText()", "getText(", "newReader()", "newReader(", "readLines()", "readLines(", "splitEachLine(", "withReader(", "withStream("}), (Object)new _getDefaultMethods_closure21(ReflectionCompleter.class, ReflectionCompleter.class, prefix2)), (Object)new _getDefaultMethods_closure22(ReflectionCompleter.class, ReflectionCompleter.class, candidates));
        }
        if (instance instanceof OutputStream) {
            public final class _getDefaultMethods_closure23
            extends Closure
            implements GeneratedClosure {
                private /* synthetic */ Reference prefix;
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;
                private static /* synthetic */ SoftReference $callSiteArray;

                public _getDefaultMethods_closure23(Object _outerInstance, Object _thisObject, Reference prefix) {
                    Reference reference;
                    CallSite[] callSiteArray = _getDefaultMethods_closure23.$getCallSiteArray();
                    super(_outerInstance, _thisObject);
                    this.prefix = reference = prefix;
                }

                public Object doCall(Object it) {
                    CallSite[] callSiteArray = _getDefaultMethods_closure23.$getCallSiteArray();
                    return callSiteArray[0].call(it, this.prefix.get());
                }

                @Generated
                public String getPrefix() {
                    CallSite[] callSiteArray = _getDefaultMethods_closure23.$getCallSiteArray();
                    return ShortTypeHandling.castToString((Object)this.prefix.get());
                }

                @Generated
                public Object doCall() {
                    CallSite[] callSiteArray = _getDefaultMethods_closure23.$getCallSiteArray();
                    return this.doCall(null);
                }

                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                    if (((Object)((Object)this)).getClass() != _getDefaultMethods_closure23.class) {
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
                    stringArray[0] = "startsWith";
                    return new CallSiteArray(_getDefaultMethods_closure23.class, stringArray);
                }

                private static /* synthetic */ CallSite[] $getCallSiteArray() {
                    CallSiteArray callSiteArray;
                    if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                        callSiteArray = _getDefaultMethods_closure23.$createCallSiteArray();
                        $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                    }
                    return callSiteArray.array;
                }
            }
            public final class _getDefaultMethods_closure24
            extends Closure
            implements GeneratedClosure {
                private /* synthetic */ Reference candidates;
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;
                private static /* synthetic */ SoftReference $callSiteArray;

                public _getDefaultMethods_closure24(Object _outerInstance, Object _thisObject, Reference candidates) {
                    Reference reference;
                    CallSite[] callSiteArray = _getDefaultMethods_closure24.$getCallSiteArray();
                    super(_outerInstance, _thisObject);
                    this.candidates = reference = candidates;
                }

                public Object doCall(Object it) {
                    CallSite[] callSiteArray = _getDefaultMethods_closure24.$getCallSiteArray();
                    return callSiteArray[0].call(this.candidates.get(), it);
                }

                @Generated
                public List getCandidates() {
                    CallSite[] callSiteArray = _getDefaultMethods_closure24.$getCallSiteArray();
                    return (List)ScriptBytecodeAdapter.castToType((Object)this.candidates.get(), List.class);
                }

                @Generated
                public Object doCall() {
                    CallSite[] callSiteArray = _getDefaultMethods_closure24.$getCallSiteArray();
                    return this.doCall(null);
                }

                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                    if (((Object)((Object)this)).getClass() != _getDefaultMethods_closure24.class) {
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
                    stringArray[0] = "add";
                    return new CallSiteArray(_getDefaultMethods_closure24.class, stringArray);
                }

                private static /* synthetic */ CallSite[] $getCallSiteArray() {
                    CallSiteArray callSiteArray;
                    if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                        callSiteArray = _getDefaultMethods_closure24.$createCallSiteArray();
                        $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                    }
                    return callSiteArray.array;
                }
            }
            callSiteArray[293].call(callSiteArray[294].call((Object)ScriptBytecodeAdapter.createList((Object[])new Object[]{"newPrintWriter()", "newWriter()", "newWriter(", "setBytes(", "withPrintWriter(", "withStream(", "withWriter("}), (Object)new _getDefaultMethods_closure23(ReflectionCompleter.class, ReflectionCompleter.class, prefix2)), (Object)new _getDefaultMethods_closure24(ReflectionCompleter.class, ReflectionCompleter.class, candidates));
        }
        if (instance instanceof Number) {
            public final class _getDefaultMethods_closure25
            extends Closure
            implements GeneratedClosure {
                private /* synthetic */ Reference prefix;
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;
                private static /* synthetic */ SoftReference $callSiteArray;

                public _getDefaultMethods_closure25(Object _outerInstance, Object _thisObject, Reference prefix) {
                    Reference reference;
                    CallSite[] callSiteArray = _getDefaultMethods_closure25.$getCallSiteArray();
                    super(_outerInstance, _thisObject);
                    this.prefix = reference = prefix;
                }

                public Object doCall(Object it) {
                    CallSite[] callSiteArray = _getDefaultMethods_closure25.$getCallSiteArray();
                    return callSiteArray[0].call(it, this.prefix.get());
                }

                @Generated
                public String getPrefix() {
                    CallSite[] callSiteArray = _getDefaultMethods_closure25.$getCallSiteArray();
                    return ShortTypeHandling.castToString((Object)this.prefix.get());
                }

                @Generated
                public Object doCall() {
                    CallSite[] callSiteArray = _getDefaultMethods_closure25.$getCallSiteArray();
                    return this.doCall(null);
                }

                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                    if (((Object)((Object)this)).getClass() != _getDefaultMethods_closure25.class) {
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
                    stringArray[0] = "startsWith";
                    return new CallSiteArray(_getDefaultMethods_closure25.class, stringArray);
                }

                private static /* synthetic */ CallSite[] $getCallSiteArray() {
                    CallSiteArray callSiteArray;
                    if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                        callSiteArray = _getDefaultMethods_closure25.$createCallSiteArray();
                        $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                    }
                    return callSiteArray.array;
                }
            }
            public final class _getDefaultMethods_closure26
            extends Closure
            implements GeneratedClosure {
                private /* synthetic */ Reference candidates;
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;
                private static /* synthetic */ SoftReference $callSiteArray;

                public _getDefaultMethods_closure26(Object _outerInstance, Object _thisObject, Reference candidates) {
                    Reference reference;
                    CallSite[] callSiteArray = _getDefaultMethods_closure26.$getCallSiteArray();
                    super(_outerInstance, _thisObject);
                    this.candidates = reference = candidates;
                }

                public Object doCall(Object it) {
                    CallSite[] callSiteArray = _getDefaultMethods_closure26.$getCallSiteArray();
                    return callSiteArray[0].call(this.candidates.get(), it);
                }

                @Generated
                public List getCandidates() {
                    CallSite[] callSiteArray = _getDefaultMethods_closure26.$getCallSiteArray();
                    return (List)ScriptBytecodeAdapter.castToType((Object)this.candidates.get(), List.class);
                }

                @Generated
                public Object doCall() {
                    CallSite[] callSiteArray = _getDefaultMethods_closure26.$getCallSiteArray();
                    return this.doCall(null);
                }

                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                    if (((Object)((Object)this)).getClass() != _getDefaultMethods_closure26.class) {
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
                    stringArray[0] = "add";
                    return new CallSiteArray(_getDefaultMethods_closure26.class, stringArray);
                }

                private static /* synthetic */ CallSite[] $getCallSiteArray() {
                    CallSiteArray callSiteArray;
                    if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                        callSiteArray = _getDefaultMethods_closure26.$createCallSiteArray();
                        $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                    }
                    return callSiteArray.array;
                }
            }
            callSiteArray[295].call(callSiteArray[296].call((Object)ScriptBytecodeAdapter.createList((Object[])new Object[]{"abs()", "downto(", "times(", "power(", "upto("}), (Object)new _getDefaultMethods_closure25(ReflectionCompleter.class, ReflectionCompleter.class, prefix2)), (Object)new _getDefaultMethods_closure26(ReflectionCompleter.class, ReflectionCompleter.class, candidates));
        }
        Class clazz = ShortTypeHandling.castToClass((Object)callSiteArray[297].call(instance));
        if (!BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            if (ScriptBytecodeAdapter.compareNotEqual((Object)clazz, null) && ScriptBytecodeAdapter.compareNotEqual((Object)clazz, Class.class) && DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[298].call((Object)clazz))) {
                callSiteArray[299].call(callSiteArray[300].call((Object)ScriptBytecodeAdapter.createList((Object[])new Object[]{"any()", "any(", "collect()", "collect(", "count(", "countBy(", "drop(", "dropRight(", "dropWhile(", "each()", "each(", "every()", "every(", "find(", "findResult(", "flatten()", "init()", "inject(", "join(", "max()", "min()", "reverse()", "size()", "sort()", "split(", "take(", "takeRight(", "takeWhile("}), (Object)new _getDefaultMethods_closure27(ReflectionCompleter.class, ReflectionCompleter.class, prefix2)), (Object)new _getDefaultMethods_closure28(ReflectionCompleter.class, ReflectionCompleter.class, candidates));
            }
        } else if (ScriptBytecodeAdapter.compareNotEqual((Object)clazz, null) && ScriptBytecodeAdapter.compareNotEqual((Object)clazz, Class.class) && DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[301].call((Object)clazz))) {
            callSiteArray[302].call(callSiteArray[303].call((Object)ScriptBytecodeAdapter.createList((Object[])new Object[]{"any()", "any(", "collect()", "collect(", "count(", "countBy(", "drop(", "dropRight(", "dropWhile(", "each()", "each(", "every()", "every(", "find(", "findResult(", "flatten()", "init()", "inject(", "join(", "max()", "min()", "reverse()", "size()", "sort()", "split(", "take(", "takeRight(", "takeWhile("}), (Object)new _getDefaultMethods_closure27(ReflectionCompleter.class, ReflectionCompleter.class, prefix2)), (Object)new _getDefaultMethods_closure28(ReflectionCompleter.class, ReflectionCompleter.class, candidates));
        }
        return (List)candidates.get();
    }

    /*
     * WARNING - void declaration
     */
    private static Collection<ReflectionCompletionCandidate> addClassFieldsAndMethods(Class clazz, boolean includeStatic, boolean includeNonStatic, String prefix, Collection<ReflectionCompletionCandidate> rv, boolean renderBold) {
        void var3_3;
        Reference clazz2 = new Reference((Object)clazz);
        Reference includeStatic2 = new Reference((Object)includeStatic);
        Reference includeNonStatic2 = new Reference((Object)includeNonStatic);
        Reference prefix2 = new Reference((Object)var3_3);
        Reference rv2 = new Reference(rv);
        Reference renderBold2 = new Reference((Object)renderBold);
        CallSite[] callSiteArray = ReflectionCompleter.$getCallSiteArray();
        Field[] fields = null;
        if (!BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            Object object = DefaultTypeTransformation.booleanUnbox((Object)includeStatic2.get()) && !DefaultTypeTransformation.booleanUnbox((Object)includeNonStatic2.get()) ? callSiteArray[304].callGetProperty((Object)((Class)clazz2.get())) : callSiteArray[305].call((Object)((Class)clazz2.get()));
            fields = (Field[])ScriptBytecodeAdapter.castToType((Object)object, Field[].class);
        } else {
            Object object = DefaultTypeTransformation.booleanUnbox((Object)includeStatic2.get()) && !DefaultTypeTransformation.booleanUnbox((Object)includeNonStatic2.get()) ? callSiteArray[306].callGetProperty((Object)((Class)clazz2.get())) : callSiteArray[307].call((Object)((Class)clazz2.get()));
            fields = (Field[])ScriptBytecodeAdapter.castToType((Object)object, Field[].class);
        }
        public final class _addClassFieldsAndMethods_closure29
        extends Closure
        implements GeneratedClosure {
            private /* synthetic */ Reference prefix;
            private /* synthetic */ Reference includeStatic;
            private /* synthetic */ Reference includeNonStatic;
            private /* synthetic */ Reference clazz;
            private /* synthetic */ Reference renderBold;
            private /* synthetic */ Reference rv;
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _addClassFieldsAndMethods_closure29(Object _outerInstance, Object _thisObject, Reference prefix, Reference includeStatic, Reference includeNonStatic, Reference clazz, Reference renderBold, Reference rv) {
                Reference reference;
                Reference reference2;
                Reference reference3;
                Reference reference4;
                Reference reference5;
                Reference reference6;
                CallSite[] callSiteArray = _addClassFieldsAndMethods_closure29.$getCallSiteArray();
                super(_outerInstance, _thisObject);
                this.prefix = reference6 = prefix;
                this.includeStatic = reference5 = includeStatic;
                this.includeNonStatic = reference4 = includeNonStatic;
                this.clazz = reference3 = clazz;
                this.renderBold = reference2 = renderBold;
                this.rv = reference = rv;
            }

            public Object doCall(Field fit) {
                CallSite[] callSiteArray = _addClassFieldsAndMethods_closure29.$getCallSiteArray();
                if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[0].callCurrent((GroovyObject)this, callSiteArray[1].callGetProperty((Object)fit), this.prefix.get()))) {
                    int modifiers = DefaultTypeTransformation.intUnbox((Object)callSiteArray[2].call((Object)fit));
                    if (!BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                        if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[3].call(Modifier.class, (Object)modifiers)) && (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[4].call(Modifier.class, (Object)modifiers)) ? DefaultTypeTransformation.booleanUnbox((Object)this.includeStatic.get()) : DefaultTypeTransformation.booleanUnbox((Object)this.includeNonStatic.get())) && (!DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[5].call(this.clazz.get())) || !(!DefaultTypeTransformation.booleanUnbox((Object)this.includeStatic.get()) && DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[6].call(Modifier.class, (Object)modifiers)) && DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[7].call(Modifier.class, (Object)modifiers)) && DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[8].call(Modifier.class, (Object)modifiers)) && ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[9].callGetProperty((Object)fit), (Object)this.clazz.get())))) {
                            ReflectionCompletionCandidate candidate = (ReflectionCompletionCandidate)ScriptBytecodeAdapter.castToType((Object)callSiteArray[10].callConstructor(ReflectionCompletionCandidate.class, callSiteArray[11].callGetProperty((Object)fit)), ReflectionCompletionCandidate.class);
                            if (!DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[12].call(Modifier.class, (Object)modifiers)) && DefaultTypeTransformation.booleanUnbox((Object)this.renderBold.get())) {
                                callSiteArray[13].call(callSiteArray[14].callGroovyObjectGetProperty((Object)candidate), callSiteArray[15].call(callSiteArray[16].callGetProperty(Ansi.Attribute.class)));
                            }
                            return callSiteArray[17].call(this.rv.get(), (Object)candidate);
                        }
                    } else if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[18].call(Modifier.class, (Object)modifiers)) && (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[19].call(Modifier.class, (Object)modifiers)) ? DefaultTypeTransformation.booleanUnbox((Object)this.includeStatic.get()) : DefaultTypeTransformation.booleanUnbox((Object)this.includeNonStatic.get())) && (!DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[20].call(this.clazz.get())) || !(!DefaultTypeTransformation.booleanUnbox((Object)this.includeStatic.get()) && DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[21].call(Modifier.class, (Object)modifiers)) && DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[22].call(Modifier.class, (Object)modifiers)) && DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[23].call(Modifier.class, (Object)modifiers)) && ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[24].callGetProperty((Object)fit), (Object)this.clazz.get())))) {
                        ReflectionCompletionCandidate candidate = (ReflectionCompletionCandidate)ScriptBytecodeAdapter.castToType((Object)callSiteArray[25].callConstructor(ReflectionCompletionCandidate.class, callSiteArray[26].callGetProperty((Object)fit)), ReflectionCompletionCandidate.class);
                        if (!DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[27].call(Modifier.class, (Object)modifiers)) && DefaultTypeTransformation.booleanUnbox((Object)this.renderBold.get())) {
                            callSiteArray[28].call(callSiteArray[29].callGroovyObjectGetProperty((Object)candidate), callSiteArray[30].call(callSiteArray[31].callGetProperty(Ansi.Attribute.class)));
                        }
                        return callSiteArray[32].call(this.rv.get(), (Object)candidate);
                    }
                }
                return null;
            }

            @Generated
            public Object call(Field fit) {
                CallSite[] callSiteArray = _addClassFieldsAndMethods_closure29.$getCallSiteArray();
                if (__$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                    return callSiteArray[33].callCurrent((GroovyObject)this, (Object)fit);
                }
                return this.doCall(fit);
            }

            @Generated
            public String getPrefix() {
                CallSite[] callSiteArray = _addClassFieldsAndMethods_closure29.$getCallSiteArray();
                return ShortTypeHandling.castToString((Object)this.prefix.get());
            }

            @Generated
            public boolean getIncludeStatic() {
                CallSite[] callSiteArray = _addClassFieldsAndMethods_closure29.$getCallSiteArray();
                return DefaultTypeTransformation.booleanUnbox((Object)this.includeStatic.get());
            }

            @Generated
            public boolean getIncludeNonStatic() {
                CallSite[] callSiteArray = _addClassFieldsAndMethods_closure29.$getCallSiteArray();
                return DefaultTypeTransformation.booleanUnbox((Object)this.includeNonStatic.get());
            }

            @Generated
            public Class getClazz() {
                CallSite[] callSiteArray = _addClassFieldsAndMethods_closure29.$getCallSiteArray();
                return ShortTypeHandling.castToClass((Object)this.clazz.get());
            }

            @Generated
            public boolean getRenderBold() {
                CallSite[] callSiteArray = _addClassFieldsAndMethods_closure29.$getCallSiteArray();
                return DefaultTypeTransformation.booleanUnbox((Object)this.renderBold.get());
            }

            @Generated
            public Collection getRv() {
                CallSite[] callSiteArray = _addClassFieldsAndMethods_closure29.$getCallSiteArray();
                return (Collection)ScriptBytecodeAdapter.castToType((Object)this.rv.get(), Collection.class);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (((Object)((Object)this)).getClass() != _addClassFieldsAndMethods_closure29.class) {
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
                stringArray[0] = "acceptName";
                stringArray[1] = "name";
                stringArray[2] = "getModifiers";
                stringArray[3] = "isPublic";
                stringArray[4] = "isStatic";
                stringArray[5] = "isEnum";
                stringArray[6] = "isPublic";
                stringArray[7] = "isFinal";
                stringArray[8] = "isStatic";
                stringArray[9] = "type";
                stringArray[10] = "<$constructor$>";
                stringArray[11] = "name";
                stringArray[12] = "isStatic";
                stringArray[13] = "add";
                stringArray[14] = "jAnsiCodes";
                stringArray[15] = "name";
                stringArray[16] = "INTENSITY_BOLD";
                stringArray[17] = "leftShift";
                stringArray[18] = "isPublic";
                stringArray[19] = "isStatic";
                stringArray[20] = "isEnum";
                stringArray[21] = "isPublic";
                stringArray[22] = "isFinal";
                stringArray[23] = "isStatic";
                stringArray[24] = "type";
                stringArray[25] = "<$constructor$>";
                stringArray[26] = "name";
                stringArray[27] = "isStatic";
                stringArray[28] = "add";
                stringArray[29] = "jAnsiCodes";
                stringArray[30] = "name";
                stringArray[31] = "INTENSITY_BOLD";
                stringArray[32] = "leftShift";
                stringArray[33] = "doCall";
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[34];
                _addClassFieldsAndMethods_closure29.$createCallSiteArray_1(stringArray);
                return new CallSiteArray(_addClassFieldsAndMethods_closure29.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _addClassFieldsAndMethods_closure29.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        callSiteArray[308].call((Object)fields, (Object)new _addClassFieldsAndMethods_closure29(ReflectionCompleter.class, ReflectionCompleter.class, prefix2, includeStatic2, includeNonStatic2, clazz2, renderBold2, rv2));
        Method[] methods = null;
        if (!BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            Object object = DefaultTypeTransformation.booleanUnbox((Object)includeStatic2.get()) && !DefaultTypeTransformation.booleanUnbox((Object)includeNonStatic2.get()) ? callSiteArray[309].callGetProperty((Object)((Class)clazz2.get())) : callSiteArray[310].call((Object)((Class)clazz2.get()));
            methods = (Method[])ScriptBytecodeAdapter.castToType((Object)object, Method[].class);
        } else {
            Object object = DefaultTypeTransformation.booleanUnbox((Object)includeStatic2.get()) && !DefaultTypeTransformation.booleanUnbox((Object)includeNonStatic2.get()) ? callSiteArray[311].callGetProperty((Object)((Class)clazz2.get())) : callSiteArray[312].call((Object)((Class)clazz2.get()));
            methods = (Method[])ScriptBytecodeAdapter.castToType((Object)object, Method[].class);
        }
        Method methIt = null;
        Iterator iterator = (Iterator)ScriptBytecodeAdapter.castToType((Object)callSiteArray[313].call((Object)methods), Iterator.class);
        if (iterator != null) {
            while (iterator.hasNext()) {
                methIt = (Method)ScriptBytecodeAdapter.castToType(iterator.next(), Method.class);
                String name = ShortTypeHandling.castToString((Object)callSiteArray[314].call((Object)methIt));
                if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[315].call((Object)name, (Object)"super$"))) {
                    Object object = callSiteArray[316].call((Object)name, callSiteArray[317].call(callSiteArray[318].call((Object)name, (Object)"^super\\$.*\\$")));
                    name = ShortTypeHandling.castToString((Object)object);
                }
                int modifiers = DefaultTypeTransformation.intUnbox((Object)callSiteArray[319].call((Object)methIt));
                if (!(DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[320].call(Modifier.class, (Object)modifiers)) && (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[321].call(Modifier.class, (Object)modifiers)) ? DefaultTypeTransformation.booleanUnbox((Object)includeStatic2.get()) : DefaultTypeTransformation.booleanUnbox((Object)includeNonStatic2.get())))) continue;
                boolean fieldnameSuggested = false;
                if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[322].call((Object)name, (Object)BEAN_ACCESSOR_PATTERN))) {
                    String fieldname = ShortTypeHandling.castToString((Object)callSiteArray[323].callStatic(ReflectionCompleter.class, (Object)name, callSiteArray[324].callGetProperty(callSiteArray[325].callGetProperty((Object)methIt))));
                    if (ScriptBytecodeAdapter.compareNotEqual((Object)fieldname, null) && ScriptBytecodeAdapter.compareNotEqual((Object)fieldname, (Object)"metaClass") && ScriptBytecodeAdapter.compareNotEqual((Object)fieldname, (Object)"property") && ReflectionCompleter.acceptName(fieldname, (String)prefix2.get())) {
                        boolean bl;
                        fieldnameSuggested = bl = true;
                        ReflectionCompletionCandidate fieldCandidate = (ReflectionCompletionCandidate)ScriptBytecodeAdapter.castToType((Object)callSiteArray[326].callConstructor(ReflectionCompletionCandidate.class, (Object)fieldname), ReflectionCompletionCandidate.class);
                        if (!DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[327].call((Object)((Collection)rv2.get()), (Object)fieldCandidate))) {
                            if (!DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[328].call(Modifier.class, (Object)modifiers)) && DefaultTypeTransformation.booleanUnbox((Object)renderBold2.get())) {
                                callSiteArray[329].call(callSiteArray[330].callGroovyObjectGetProperty((Object)fieldCandidate), callSiteArray[331].call(callSiteArray[332].callGetProperty(Ansi.Attribute.class)));
                            }
                            callSiteArray[333].call((Object)((Collection)rv2.get()), (Object)fieldCandidate);
                        }
                    }
                }
                if (!(!fieldnameSuggested && ReflectionCompleter.acceptName(name, (String)prefix2.get()))) continue;
                ReflectionCompletionCandidate candidate = (ReflectionCompletionCandidate)ScriptBytecodeAdapter.castToType((Object)callSiteArray[334].callConstructor(ReflectionCompletionCandidate.class, callSiteArray[335].call((Object)name, (Object)(ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[336].callGetProperty(callSiteArray[337].callGetProperty((Object)methIt)), (Object)0) ? "()" : "("))), ReflectionCompletionCandidate.class);
                if (!DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[338].call(Modifier.class, (Object)modifiers)) && DefaultTypeTransformation.booleanUnbox((Object)renderBold2.get())) {
                    callSiteArray[339].call(callSiteArray[340].callGroovyObjectGetProperty((Object)candidate), callSiteArray[341].call(callSiteArray[342].callGetProperty(Ansi.Attribute.class)));
                }
                callSiteArray[343].call((Object)((Collection)rv2.get()), (Object)candidate);
            }
        }
        Class interface_ = null;
        Iterator iterator2 = (Iterator)ScriptBytecodeAdapter.castToType((Object)callSiteArray[344].call(callSiteArray[345].call((Object)((Class)clazz2.get()))), Iterator.class);
        if (iterator2 != null) {
            while (iterator2.hasNext()) {
                interface_ = ShortTypeHandling.castToClass(iterator2.next());
                callSiteArray[346].callStatic(ReflectionCompleter.class, ArrayUtil.createArray((Object)interface_, (Object)DefaultTypeTransformation.booleanUnbox((Object)includeStatic2.get()), (Object)DefaultTypeTransformation.booleanUnbox((Object)includeNonStatic2.get()), (Object)((String)prefix2.get()), (Object)((Collection)rv2.get()), (Object)false));
            }
        }
        return (Collection)ScriptBytecodeAdapter.castToType(null, Collection.class);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static CharSequence getFieldnameForAccessor(String accessor, int parameterLength) {
        CallSite[] callSiteArray = ReflectionCompleter.$getCallSiteArray();
        String fieldname = null;
        if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[347].call((Object)accessor, (Object)"get"))) {
                if (parameterLength == 0) {
                    Object object = callSiteArray[348].call((Object)accessor, (Object)3);
                    fieldname = ShortTypeHandling.castToString((Object)object);
                }
            } else if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[349].call((Object)accessor, (Object)"set"))) {
                if (parameterLength == 1) {
                    Object object = callSiteArray[350].call((Object)accessor, (Object)3);
                    fieldname = ShortTypeHandling.castToString((Object)object);
                }
            } else {
                if (!DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[351].call((Object)accessor, (Object)"is"))) throw (Throwable)callSiteArray[353].callConstructor(IllegalStateException.class, callSiteArray[354].call((Object)"getFieldnameForAccessor called with invalid accessor : ", (Object)accessor));
                if (parameterLength == 0) {
                    Object object = callSiteArray[352].call((Object)accessor, (Object)2);
                    fieldname = ShortTypeHandling.castToString((Object)object);
                }
            }
        } else if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[355].call((Object)accessor, (Object)"get"))) {
            if (parameterLength == 0) {
                Object object = callSiteArray[356].call((Object)accessor, (Object)3);
                fieldname = ShortTypeHandling.castToString((Object)object);
            }
        } else if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[357].call((Object)accessor, (Object)"set"))) {
            if (parameterLength == 1) {
                Object object = callSiteArray[358].call((Object)accessor, (Object)3);
                fieldname = ShortTypeHandling.castToString((Object)object);
            }
        } else {
            if (!DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[359].call((Object)accessor, (Object)"is"))) throw (Throwable)callSiteArray[361].callConstructor(IllegalStateException.class, callSiteArray[362].call((Object)"getFieldnameForAccessor called with invalid accessor : ", (Object)accessor));
            if (parameterLength == 0) {
                Object object = callSiteArray[360].call((Object)accessor, (Object)2);
                fieldname = ShortTypeHandling.castToString((Object)object);
            }
        }
        if (!ScriptBytecodeAdapter.compareEqual(fieldname, null)) return (CharSequence)ScriptBytecodeAdapter.castToType((Object)callSiteArray[363].call(callSiteArray[364].call(callSiteArray[365].call((Object)fieldname, (Object)0)), callSiteArray[366].call((Object)fieldname, (Object)1)), CharSequence.class);
        return (CharSequence)ScriptBytecodeAdapter.castToType(null, CharSequence.class);
    }

    @Generated
    public static Collection<ReflectionCompletionCandidate> getPublicFieldsAndMethods(Object instance, String prefix) {
        CallSite[] callSiteArray = ReflectionCompleter.$getCallSiteArray();
        return ReflectionCompleter.getPublicFieldsAndMethods(instance, prefix, false);
    }

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != ReflectionCompleter.class) {
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
        Object object = ReflectionCompleter.$getCallSiteArray()[367].callConstructor(NavigablePropertiesCompleter.class);
        PROPERTIES_COMPLETER = (NavigablePropertiesCompleter)ScriptBytecodeAdapter.castToType((Object)object, NavigablePropertiesCompleter.class);
        Object object2 = ScriptBytecodeAdapter.bitwiseNegate((Object)"^(get|set|is)[A-Z].*");
        BEAN_ACCESSOR_PATTERN = (Pattern)ScriptBytecodeAdapter.castToType((Object)object2, Pattern.class);
    }

    @Generated
    public final Groovysh getShell() {
        return this.shell;
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
        stringArray[0] = "size";
        stringArray[1] = "<$constructor$>";
        stringArray[2] = "plus";
        stringArray[3] = "type";
        stringArray[4] = "last";
        stringArray[5] = "DOT";
        stringArray[6] = "type";
        stringArray[7] = "last";
        stringArray[8] = "SAFE_DOT";
        stringArray[9] = "type";
        stringArray[10] = "last";
        stringArray[11] = "SPREAD_DOT";
        stringArray[12] = "type";
        stringArray[13] = "last";
        stringArray[14] = "METHOD_POINTER";
        stringArray[15] = "type";
        stringArray[16] = "last";
        stringArray[17] = "METHOD_REFERENCE";
        stringArray[18] = "last";
        stringArray[19] = "getAt";
        stringArray[20] = "type";
        stringArray[21] = "getAt";
        stringArray[22] = "DOT";
        stringArray[23] = "type";
        stringArray[24] = "getAt";
        stringArray[25] = "SAFE_DOT";
        stringArray[26] = "type";
        stringArray[27] = "getAt";
        stringArray[28] = "SPREAD_DOT";
        stringArray[29] = "type";
        stringArray[30] = "getAt";
        stringArray[31] = "METHOD_POINTER";
        stringArray[32] = "type";
        stringArray[33] = "getAt";
        stringArray[34] = "METHOD_REFERENCE";
        stringArray[35] = "<$constructor$>";
        stringArray[36] = "plus";
        stringArray[37] = "last";
        stringArray[38] = "getAt";
        stringArray[39] = "getAt";
        stringArray[40] = "type";
        stringArray[41] = "last";
        stringArray[42] = "DOT";
        stringArray[43] = "type";
        stringArray[44] = "last";
        stringArray[45] = "SAFE_DOT";
        stringArray[46] = "type";
        stringArray[47] = "last";
        stringArray[48] = "SPREAD_DOT";
        stringArray[49] = "type";
        stringArray[50] = "last";
        stringArray[51] = "METHOD_POINTER";
        stringArray[52] = "type";
        stringArray[53] = "last";
        stringArray[54] = "METHOD_REFERENCE";
        stringArray[55] = "last";
        stringArray[56] = "getAt";
        stringArray[57] = "type";
        stringArray[58] = "getAt";
        stringArray[59] = "DOT";
        stringArray[60] = "type";
        stringArray[61] = "getAt";
        stringArray[62] = "SAFE_DOT";
        stringArray[63] = "type";
        stringArray[64] = "getAt";
        stringArray[65] = "SPREAD_DOT";
        stringArray[66] = "type";
        stringArray[67] = "getAt";
        stringArray[68] = "METHOD_POINTER";
        stringArray[69] = "type";
        stringArray[70] = "getAt";
        stringArray[71] = "METHOD_REFERENCE";
        stringArray[72] = "<$constructor$>";
        stringArray[73] = "plus";
        stringArray[74] = "last";
        stringArray[75] = "getAt";
        stringArray[76] = "getAt";
        stringArray[77] = "getInvokerClassOrInstance";
        stringArray[78] = "type";
        stringArray[79] = "SPREAD_DOT";
        stringArray[80] = "find";
        stringArray[81] = "text";
        stringArray[82] = "completeInstanceMembers";
        stringArray[83] = "type";
        stringArray[84] = "METHOD_POINTER";
        stringArray[85] = "type";
        stringArray[86] = "METHOD_REFERENCE";
        stringArray[87] = "type";
        stringArray[88] = "METHOD_POINTER";
        stringArray[89] = "type";
        stringArray[90] = "METHOD_REFERENCE";
        stringArray[91] = "getPublicFieldsAndMethods";
        stringArray[92] = "length";
        stringArray[93] = "valueOf";
        stringArray[94] = "get";
        stringArray[95] = "METACLASS_COMPLETION_PREFIX_LENGTH_PREFERENCE_KEY";
        stringArray[96] = "length";
        stringArray[97] = "valueOf";
        stringArray[98] = "get";
        stringArray[99] = "METACLASS_COMPLETION_PREFIX_LENGTH_PREFERENCE_KEY";
        stringArray[100] = "addAll";
        stringArray[101] = "collect";
        stringArray[102] = "getMetaclassMethods";
        stringArray[103] = "removeStandardMethods";
        stringArray[104] = "addAll";
        stringArray[105] = "collect";
        stringArray[106] = "getDefaultMethods";
        stringArray[107] = "size";
        stringArray[108] = "sort";
        stringArray[109] = "collect";
        stringArray[110] = "valueOf";
        stringArray[111] = "get";
        stringArray[112] = "COLORS_PREFERENCE_KEY";
        stringArray[113] = "addAll";
        stringArray[114] = "collect";
        stringArray[115] = "addAll";
        stringArray[116] = "line";
        stringArray[117] = "line";
        stringArray[118] = "startIndex";
        stringArray[119] = "plus";
        stringArray[120] = "startIndex";
        stringArray[121] = "size";
        stringArray[122] = "text";
        stringArray[123] = "type";
        stringArray[124] = "last";
        stringArray[125] = "DOT";
        stringArray[126] = "type";
        stringArray[127] = "last";
        stringArray[128] = "SAFE_DOT";
        stringArray[129] = "type";
        stringArray[130] = "last";
        stringArray[131] = "DOT";
        stringArray[132] = "type";
        stringArray[133] = "last";
        stringArray[134] = "SAFE_DOT";
        stringArray[135] = "getInvokerTokens";
        stringArray[136] = "tokenListToEvalString";
        stringArray[137] = "replace";
        stringArray[138] = "evaluate";
        stringArray[139] = "interp";
        stringArray[140] = "plus";
        stringArray[141] = "plus";
        stringArray[142] = "getImportStatements";
        stringArray[143] = "tokenListToEvalString";
        stringArray[144] = "replace";
        stringArray[145] = "evaluate";
        stringArray[146] = "interp";
        stringArray[147] = "plus";
        stringArray[148] = "plus";
        stringArray[149] = "getImportStatements";
        stringArray[150] = "size";
        stringArray[151] = "<$constructor$>";
        stringArray[152] = "iterator";
        stringArray[153] = "reverse";
        stringArray[154] = "type";
        stringArray[155] = "StringLiteral";
        stringArray[156] = "LPAREN";
        stringArray[157] = "empty";
        stringArray[158] = "pop";
        stringArray[159] = "LPAREN";
        stringArray[160] = "LBRACK";
        stringArray[161] = "empty";
        stringArray[162] = "pop";
        stringArray[163] = "LBRACK";
        stringArray[164] = "RBRACK";
        stringArray[165] = "push";
        stringArray[166] = "LBRACK";
        stringArray[167] = "RPAREN";
        stringArray[168] = "push";
        stringArray[169] = "LPAREN";
        stringArray[170] = "SPACESHIP";
        stringArray[171] = "EQUAL";
        stringArray[172] = "NOTEQUAL";
        stringArray[173] = "ASSIGN";
        stringArray[174] = "GT";
        stringArray[175] = "LT";
        stringArray[176] = "GE";
        stringArray[177] = "LE";
        stringArray[178] = "ADD";
        stringArray[179] = "ADD_ASSIGN";
        stringArray[180] = "SUB";
        stringArray[181] = "SUB_ASSIGN";
        stringArray[182] = "MUL";
        stringArray[183] = "MUL_ASSIGN";
        stringArray[184] = "DIV";
        stringArray[185] = "DIV_ASSIGN";
        stringArray[186] = "BITOR";
        stringArray[187] = "OR_ASSIGN";
        stringArray[188] = "BITAND";
        stringArray[189] = "AND_ASSIGN";
        stringArray[190] = "XOR";
        stringArray[191] = "XOR_ASSIGN";
        stringArray[192] = "BITNOT";
        stringArray[193] = "OR";
        stringArray[194] = "AND";
        stringArray[195] = "NOT";
        stringArray[196] = "IN";
        stringArray[197] = "INSTANCEOF";
        stringArray[198] = "empty";
        stringArray[199] = "LBRACE";
        stringArray[200] = "SEMI";
        stringArray[201] = "GStringBegin";
        stringArray[202] = "Identifier";
        stringArray[203] = "CapitalizedIdentifier";
        stringArray[204] = "type";
        stringArray[205] = "LPAREN";
        stringArray[206] = "type";
        stringArray[207] = "Identifier";
        stringArray[208] = "type";
        stringArray[209] = "CapitalizedIdentifier";
        stringArray[210] = "RANGE_INCLUSIVE";
        stringArray[211] = "RANGE_EXCLUSIVE_LEFT";
        stringArray[212] = "RANGE_EXCLUSIVE_RIGHT";
        stringArray[213] = "RANGE_EXCLUSIVE_FULL";
        stringArray[214] = "COLON";
        stringArray[215] = "COMMA";
        stringArray[216] = "empty";
        stringArray[217] = "BooleanLiteral";
        stringArray[218] = "BuiltInPrimitiveType";
        stringArray[219] = "METHOD_POINTER";
        stringArray[220] = "DOT";
        stringArray[221] = "SAFE_DOT";
        stringArray[222] = "previous";
        stringArray[223] = "getAt";
        stringArray[224] = "<$constructor$>";
        stringArray[225] = "iterator";
        stringArray[226] = "type";
        stringArray[227] = "StringLiteral";
        stringArray[228] = "append";
        stringArray[229] = "append";
        stringArray[230] = "append";
        stringArray[231] = "text";
        stringArray[232] = "append";
        stringArray[233] = "text";
        stringArray[234] = "toString";
        stringArray[235] = "startsWith";
        stringArray[236] = "contains";
        stringArray[237] = "startsWith";
        stringArray[238] = "startsWith";
        stringArray[239] = "contains";
        stringArray[240] = "startsWith";
        stringArray[241] = "<$constructor$>";
        stringArray[242] = "getMetaClass";
        stringArray[243] = "each";
        stringArray[244] = "metaMethods";
        stringArray[245] = "each";
        stringArray[246] = "metaMethods";
        stringArray[247] = "sort";
        stringArray[248] = "<$constructor$>";
        stringArray[249] = "getClass";
        stringArray[250] = "length";
        stringArray[251] = "valueOf";
        stringArray[252] = "get";
        stringArray[253] = "METACLASS_COMPLETION_PREFIX_LENGTH_PREFERENCE_KEY";
        stringArray[254] = "length";
        stringArray[255] = "valueOf";
        stringArray[256] = "get";
        stringArray[257] = "METACLASS_COMPLETION_PREFIX_LENGTH_PREFERENCE_KEY";
        stringArray[258] = "addClassFieldsAndMethods";
        stringArray[259] = "superclass";
        stringArray[260] = "isArray";
        stringArray[261] = "iterator";
        stringArray[262] = "startsWith";
        stringArray[263] = "add";
        stringArray[264] = "<$constructor$>";
        stringArray[265] = "name";
        stringArray[266] = "INTENSITY_BOLD";
        stringArray[267] = "<$constructor$>";
        stringArray[268] = "addCompletions";
        stringArray[269] = "addAll";
        stringArray[270] = "collect";
        stringArray[271] = "sort";
        stringArray[272] = "iterator";
        stringArray[273] = "iterator";
        stringArray[274] = "equals";
        stringArray[275] = "value";
        stringArray[276] = "remove";
        stringArray[277] = "each";
        stringArray[278] = "findAll";
        stringArray[279] = "each";
        stringArray[280] = "findAll";
        stringArray[281] = "each";
        stringArray[282] = "findAll";
        stringArray[283] = "each";
        stringArray[284] = "findAll";
        stringArray[285] = "each";
        stringArray[286] = "findAll";
        stringArray[287] = "each";
        stringArray[288] = "findAll";
        stringArray[289] = "each";
        stringArray[290] = "findAll";
        stringArray[291] = "each";
        stringArray[292] = "findAll";
        stringArray[293] = "each";
        stringArray[294] = "findAll";
        stringArray[295] = "each";
        stringArray[296] = "findAll";
        stringArray[297] = "getClass";
        stringArray[298] = "isArray";
        stringArray[299] = "each";
        stringArray[300] = "findAll";
        stringArray[301] = "isArray";
        stringArray[302] = "each";
        stringArray[303] = "findAll";
        stringArray[304] = "fields";
        stringArray[305] = "getDeclaredFields";
        stringArray[306] = "fields";
        stringArray[307] = "getDeclaredFields";
        stringArray[308] = "each";
        stringArray[309] = "methods";
        stringArray[310] = "getDeclaredMethods";
        stringArray[311] = "methods";
        stringArray[312] = "getDeclaredMethods";
        stringArray[313] = "iterator";
        stringArray[314] = "getName";
        stringArray[315] = "startsWith";
        stringArray[316] = "substring";
        stringArray[317] = "length";
        stringArray[318] = "find";
        stringArray[319] = "getModifiers";
        stringArray[320] = "isPublic";
        stringArray[321] = "isStatic";
        stringArray[322] = "matches";
        stringArray[323] = "getFieldnameForAccessor";
        stringArray[324] = "length";
        stringArray[325] = "parameterTypes";
        stringArray[326] = "<$constructor$>";
        stringArray[327] = "contains";
        stringArray[328] = "isStatic";
        stringArray[329] = "add";
        stringArray[330] = "jAnsiCodes";
        stringArray[331] = "name";
        stringArray[332] = "INTENSITY_BOLD";
        stringArray[333] = "add";
        stringArray[334] = "<$constructor$>";
        stringArray[335] = "plus";
        stringArray[336] = "length";
        stringArray[337] = "parameterTypes";
        stringArray[338] = "isStatic";
        stringArray[339] = "add";
        stringArray[340] = "jAnsiCodes";
        stringArray[341] = "name";
        stringArray[342] = "INTENSITY_BOLD";
        stringArray[343] = "add";
        stringArray[344] = "iterator";
        stringArray[345] = "getInterfaces";
        stringArray[346] = "addClassFieldsAndMethods";
        stringArray[347] = "startsWith";
        stringArray[348] = "substring";
        stringArray[349] = "startsWith";
        stringArray[350] = "substring";
        stringArray[351] = "startsWith";
        stringArray[352] = "substring";
        stringArray[353] = "<$constructor$>";
        stringArray[354] = "plus";
        stringArray[355] = "startsWith";
        stringArray[356] = "substring";
        stringArray[357] = "startsWith";
        stringArray[358] = "substring";
        stringArray[359] = "startsWith";
        stringArray[360] = "substring";
        stringArray[361] = "<$constructor$>";
        stringArray[362] = "plus";
        stringArray[363] = "plus";
        stringArray[364] = "toLowerCase";
        stringArray[365] = "getAt";
        stringArray[366] = "substring";
        stringArray[367] = "<$constructor$>";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[368];
        ReflectionCompleter.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(ReflectionCompleter.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = ReflectionCompleter.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }
}

