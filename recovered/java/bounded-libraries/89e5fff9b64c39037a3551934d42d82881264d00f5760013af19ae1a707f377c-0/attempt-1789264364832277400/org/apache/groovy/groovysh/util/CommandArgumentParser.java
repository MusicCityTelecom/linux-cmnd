/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.GroovyObject
 *  groovy.lang.MetaClass
 *  groovy.transform.Generated
 *  groovy.transform.Internal
 *  org.codehaus.groovy.reflection.ClassInfo
 *  org.codehaus.groovy.runtime.BytecodeInterface8
 *  org.codehaus.groovy.runtime.ScriptBytecodeAdapter
 *  org.codehaus.groovy.runtime.StringGroovyMethods
 *  org.codehaus.groovy.runtime.callsite.CallSite
 *  org.codehaus.groovy.runtime.callsite.CallSiteArray
 *  org.codehaus.groovy.runtime.powerassert.AssertionRenderer
 *  org.codehaus.groovy.runtime.powerassert.ValueRecorder
 *  org.codehaus.groovy.runtime.typehandling.ShortTypeHandling
 */
package org.apache.groovy.groovysh.util;

import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import groovy.transform.Generated;
import groovy.transform.Internal;
import java.beans.Transient;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.BytecodeInterface8;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.StringGroovyMethods;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.powerassert.AssertionRenderer;
import org.codehaus.groovy.runtime.powerassert.ValueRecorder;
import org.codehaus.groovy.runtime.typehandling.ShortTypeHandling;

public class CommandArgumentParser
implements GroovyObject {
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private transient /* synthetic */ MetaClass metaClass;
    private static /* synthetic */ SoftReference $callSiteArray;

    @Generated
    public CommandArgumentParser() {
        MetaClass metaClass;
        this.metaClass = metaClass = this.$getStaticMetaClass();
    }

    public static List<String> parseLine(String untrimmedLine, int numTokensToCollect) {
        ValueRecorder valueRecorder = new ValueRecorder();
        try {
            String string = untrimmedLine;
            valueRecorder.record((Object)string, 8);
            if (string != null) {
                valueRecorder.clear();
            } else {
                ScriptBytecodeAdapter.assertFailed((Object)AssertionRenderer.render((String)"assert untrimmedLine != null", (ValueRecorder)valueRecorder), null);
            }
        }
        catch (Throwable throwable) {
            valueRecorder.clear();
            throw throwable;
        }
        String line = untrimmedLine.trim();
        ArrayList tokens = (ArrayList)ScriptBytecodeAdapter.castToType((Object)ScriptBytecodeAdapter.createList((Object[])new Object[0]), ArrayList.class);
        String currentToken = "";
        boolean singleHyphenOpen = false;
        boolean doubleHyphenOpen = false;
        int index = 0;
        while (index < line.length() && !(tokens.size() == numTokensToCollect)) {
            String ch = ShortTypeHandling.castToString((Object)Character.valueOf(line.charAt(index)));
            if (ScriptBytecodeAdapter.compareEqual((Object)ch, (Object)Character.valueOf('\\'))) {
                if (index >= line.length() - 1) {
                    String string;
                    currentToken = string = StringGroovyMethods.plus((String)currentToken, (CharSequence)ch);
                } else if (singleHyphenOpen || doubleHyphenOpen) {
                    String string;
                    String string2;
                    currentToken = string2 = StringGroovyMethods.plus((String)currentToken, (CharSequence)ch);
                    int n = index;
                    index = n + 1;
                    currentToken = string = StringGroovyMethods.plus((CharSequence)currentToken, (Object)Character.valueOf(line.charAt(index)));
                } else {
                    String string;
                    int n = index;
                    index = n + 1;
                    currentToken = string = StringGroovyMethods.plus((CharSequence)currentToken, (Object)Character.valueOf(line.charAt(index)));
                }
            } else if (ScriptBytecodeAdapter.compareEqual((Object)ch, (Object)Character.valueOf('\"')) && !singleHyphenOpen) {
                if (doubleHyphenOpen) {
                    boolean bl;
                    String string;
                    tokens.add(currentToken);
                    currentToken = string = "";
                    doubleHyphenOpen = bl = false;
                } else {
                    boolean bl;
                    if (StringGroovyMethods.size((CharSequence)currentToken) > 0) {
                        String string;
                        tokens.add(currentToken);
                        currentToken = string = "";
                    }
                    doubleHyphenOpen = bl = true;
                }
            } else if (ScriptBytecodeAdapter.compareEqual((Object)ch, (Object)Character.valueOf('\'')) && !doubleHyphenOpen) {
                if (singleHyphenOpen) {
                    boolean bl;
                    String string;
                    tokens.add(currentToken);
                    currentToken = string = "";
                    singleHyphenOpen = bl = false;
                } else {
                    boolean bl;
                    if (StringGroovyMethods.size((CharSequence)currentToken) > 0) {
                        String string;
                        tokens.add(currentToken);
                        currentToken = string = "";
                    }
                    singleHyphenOpen = bl = true;
                }
            } else if (ScriptBytecodeAdapter.compareEqual((Object)ch, (Object)Character.valueOf(' ')) && !doubleHyphenOpen && !singleHyphenOpen) {
                if (StringGroovyMethods.size((CharSequence)currentToken) > 0) {
                    String string;
                    tokens.add(currentToken);
                    currentToken = string = "";
                }
            } else {
                String string;
                currentToken = string = StringGroovyMethods.plus((String)currentToken, (CharSequence)ch);
            }
            int n = index;
            int cfr_ignored_0 = n + 1;
        }
        if (index == line.length() && doubleHyphenOpen) {
            throw (Throwable)new IllegalArgumentException(StringGroovyMethods.plus((CharSequence)StringGroovyMethods.plus((String)StringGroovyMethods.plus((String)"Missing closing \" in ", (CharSequence)line), (CharSequence)" -- "), (Object)tokens));
        }
        if (index == line.length() && singleHyphenOpen) {
            throw (Throwable)new IllegalArgumentException(StringGroovyMethods.plus((CharSequence)StringGroovyMethods.plus((String)StringGroovyMethods.plus((String)"Missing closing ' in ", (CharSequence)line), (CharSequence)" -- "), (Object)tokens));
        }
        if (StringGroovyMethods.size((CharSequence)currentToken) > 0) {
            tokens.add(currentToken);
        }
        return tokens;
    }

    @Generated
    public static List<String> parseLine(String untrimmedLine) {
        CallSite[] callSiteArray = CommandArgumentParser.$getCallSiteArray();
        if (__$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            return CommandArgumentParser.parseLine(untrimmedLine, -1);
        }
        return CommandArgumentParser.parseLine(untrimmedLine, -1);
    }

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != CommandArgumentParser.class) {
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

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[]{};
        return new CallSiteArray(CommandArgumentParser.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = CommandArgumentParser.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }
}

