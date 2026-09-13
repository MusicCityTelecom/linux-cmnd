/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.Closure
 *  groovy.lang.GroovyObject
 *  groovy.lang.GroovyShell
 *  groovy.lang.MetaClass
 *  groovy.lang.Reference
 *  groovy.transform.Generated
 *  groovy.transform.Internal
 *  groovyjarjarantlr4.v4.runtime.CharStream
 *  groovyjarjarantlr4.v4.runtime.CharStreams
 *  groovyjarjarantlr4.v4.runtime.CommonTokenStream
 *  groovyjarjarantlr4.v4.runtime.ConsoleErrorListener
 *  groovyjarjarantlr4.v4.runtime.Token
 *  jline.console.completer.Completer
 *  jline.internal.Configuration
 *  org.apache.groovy.parser.antlr4.GroovyLangLexer
 *  org.apache.groovy.parser.antlr4.GroovyLexer
 *  org.codehaus.groovy.reflection.ClassInfo
 *  org.codehaus.groovy.runtime.BytecodeInterface8
 *  org.codehaus.groovy.runtime.GStringImpl
 *  org.codehaus.groovy.runtime.GeneratedClosure
 *  org.codehaus.groovy.runtime.ScriptBytecodeAdapter
 *  org.codehaus.groovy.runtime.callsite.CallSite
 *  org.codehaus.groovy.runtime.callsite.CallSiteArray
 *  org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation
 *  org.codehaus.groovy.runtime.typehandling.ShortTypeHandling
 *  org.codehaus.groovy.tools.shell.util.Logger
 *  org.codehaus.groovy.transform.ImmutableASTTransformation
 */
package org.apache.groovy.groovysh.completion.antlr4;

import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import groovy.lang.GroovyShell;
import groovy.lang.MetaClass;
import groovy.lang.Reference;
import groovy.transform.Generated;
import groovy.transform.Internal;
import groovyjarjarantlr4.v4.runtime.CharStream;
import groovyjarjarantlr4.v4.runtime.CharStreams;
import groovyjarjarantlr4.v4.runtime.CommonTokenStream;
import groovyjarjarantlr4.v4.runtime.ConsoleErrorListener;
import groovyjarjarantlr4.v4.runtime.Token;
import java.beans.Transient;
import java.io.StringReader;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import jline.console.completer.Completer;
import jline.internal.Configuration;
import org.apache.groovy.groovysh.CommandRegistry;
import org.apache.groovy.groovysh.Groovysh;
import org.apache.groovy.groovysh.completion.BackslashEscapeCompleter;
import org.apache.groovy.groovysh.completion.FileNameCompleter;
import org.apache.groovy.groovysh.completion.antlr4.IdentifierCompleter;
import org.apache.groovy.groovysh.completion.antlr4.InfixKeywordSyntaxCompleter;
import org.apache.groovy.groovysh.completion.antlr4.ReflectionCompleter;
import org.apache.groovy.parser.antlr4.GroovyLangLexer;
import org.apache.groovy.parser.antlr4.GroovyLexer;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.BytecodeInterface8;
import org.codehaus.groovy.runtime.GStringImpl;
import org.codehaus.groovy.runtime.GeneratedClosure;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;
import org.codehaus.groovy.runtime.typehandling.ShortTypeHandling;
import org.codehaus.groovy.tools.shell.util.Logger;
import org.codehaus.groovy.transform.ImmutableASTTransformation;

public class GroovySyntaxCompleter
implements Completer,
GroovyObject {
    protected static final Logger LOG;
    private final Groovysh shell;
    private final List<IdentifierCompleter> identifierCompleters;
    private final IdentifierCompleter classnameCompleter;
    private final ReflectionCompleter reflectionCompleter;
    private final InfixKeywordSyntaxCompleter infixCompleter;
    private final Completer defaultFilenameCompleter;
    private final Completer windowsFilenameCompleter;
    private final Completer instringFilenameCompleter;
    private final Completer backslashCompleter;
    private static final boolean isWin;
    private final GroovyShell gs;
    private static final Object STRING_STARTERS;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private transient /* synthetic */ MetaClass metaClass;
    private static /* synthetic */ SoftReference $callSiteArray;

    public GroovySyntaxCompleter(Groovysh shell, ReflectionCompleter reflectionCompleter, IdentifierCompleter classnameCompleter, List<IdentifierCompleter> identifierCompleters, Completer filenameCompleter) {
        Completer completer;
        ReflectionCompleter reflectionCompleter2;
        IdentifierCompleter identifierCompleter;
        Groovysh groovysh;
        MetaClass metaClass;
        CallSite[] callSiteArray = GroovySyntaxCompleter.$getCallSiteArray();
        Object object = callSiteArray[0].callConstructor(GroovyShell.class);
        this.gs = (GroovyShell)ScriptBytecodeAdapter.castToType((Object)object, GroovyShell.class);
        this.metaClass = metaClass = this.$getStaticMetaClass();
        this.shell = groovysh = shell;
        this.classnameCompleter = identifierCompleter = classnameCompleter;
        List<IdentifierCompleter> list = identifierCompleters;
        this.identifierCompleters = list;
        Object object2 = callSiteArray[1].callConstructor(InfixKeywordSyntaxCompleter.class);
        this.infixCompleter = (InfixKeywordSyntaxCompleter)ScriptBytecodeAdapter.castToType((Object)object2, InfixKeywordSyntaxCompleter.class);
        Object object3 = callSiteArray[2].callConstructor(BackslashEscapeCompleter.class);
        this.backslashCompleter = (Completer)ScriptBytecodeAdapter.castToType((Object)object3, Completer.class);
        this.reflectionCompleter = reflectionCompleter2 = reflectionCompleter;
        this.defaultFilenameCompleter = completer = filenameCompleter;
        Object object4 = callSiteArray[3].callConstructor(FileNameCompleter.class, (Object)false, (Object)true, (Object)false);
        this.windowsFilenameCompleter = (Completer)ScriptBytecodeAdapter.castToType((Object)object4, Completer.class);
        Object object5 = callSiteArray[4].callConstructor(FileNameCompleter.class, (Object)false, (Object)false, (Object)false);
        this.instringFilenameCompleter = (Completer)ScriptBytecodeAdapter.castToType((Object)object5, Completer.class);
    }

    /*
     * Exception decompiling
     */
    public int complete(String bufferLine, int cursor, List<CharSequence> candidates) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [21[CATCHBLOCK], 3[TRYBLOCK]], but top level block is 6[TRYBLOCK]
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:435)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:484)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public static CompletionCase getCompletionCase(List<Token> tokens) {
        CallSite[] callSiteArray = GroovySyntaxCompleter.$getCallSiteArray();
        Token currentToken = (Token)ScriptBytecodeAdapter.castToType((Object)callSiteArray[54].call(tokens, (Object)-1), Token.class);
        if (!BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[55].callGetProperty((Object)currentToken), (Object)callSiteArray[56].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[57].callGetProperty((Object)currentToken), (Object)callSiteArray[58].callGetProperty(GroovyLexer.class))) {
                if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[59].call(tokens), (Object)1)) {
                    return (CompletionCase)ShortTypeHandling.castToEnum((Object)callSiteArray[60].callGetProperty(CompletionCase.class), CompletionCase.class);
                }
                Token previousToken = (Token)ScriptBytecodeAdapter.castToType((Object)callSiteArray[61].call(tokens, (Object)-2), Token.class);
                if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[62].callGetProperty((Object)previousToken), (Object)callSiteArray[63].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[64].callGetProperty((Object)previousToken), (Object)callSiteArray[65].callGetProperty(GroovyLexer.class))) {
                    if (ScriptBytecodeAdapter.compareLessThan((Object)callSiteArray[66].call(tokens), (Object)3)) {
                        return (CompletionCase)ShortTypeHandling.castToEnum((Object)callSiteArray[67].callGetProperty(CompletionCase.class), CompletionCase.class);
                    }
                    return (CompletionCase)ShortTypeHandling.castToEnum((Object)callSiteArray[68].callGetProperty(CompletionCase.class), CompletionCase.class);
                }
                if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[69].callGetProperty((Object)previousToken), (Object)callSiteArray[70].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[71].callGetProperty((Object)previousToken), (Object)callSiteArray[72].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[73].callGetProperty((Object)previousToken), (Object)callSiteArray[74].callGetProperty(GroovyLexer.class))) {
                    if (ScriptBytecodeAdapter.compareLessThan((Object)callSiteArray[75].call(tokens), (Object)3)) {
                        return (CompletionCase)ShortTypeHandling.castToEnum((Object)callSiteArray[76].callGetProperty(CompletionCase.class), CompletionCase.class);
                    }
                    return (CompletionCase)ShortTypeHandling.castToEnum((Object)callSiteArray[77].callGetProperty(CompletionCase.class), CompletionCase.class);
                }
                Object object = callSiteArray[78].callGetProperty((Object)previousToken);
                if (ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[79].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[80].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[81].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[82].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[83].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[84].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[85].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[86].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[87].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[88].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[89].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[90].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[91].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[92].callGetProperty(GroovyLexer.class))) {
                    return (CompletionCase)ShortTypeHandling.castToEnum((Object)callSiteArray[93].callGetProperty(CompletionCase.class), CompletionCase.class);
                }
                if (ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[94].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[95].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[96].callGetProperty(GroovyLexer.class))) {
                    return (CompletionCase)ShortTypeHandling.castToEnum((Object)callSiteArray[97].callGetProperty(CompletionCase.class), CompletionCase.class);
                }
                return (CompletionCase)ShortTypeHandling.castToEnum((Object)callSiteArray[98].callGetProperty(CompletionCase.class), CompletionCase.class);
            }
            if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[99].callGetProperty((Object)currentToken), (Object)callSiteArray[100].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[101].callGetProperty((Object)currentToken), (Object)callSiteArray[102].callGetProperty(GroovyLexer.class))) {
                if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[103].call(tokens), (Object)1)) {
                    return (CompletionCase)ShortTypeHandling.castToEnum((Object)callSiteArray[104].callGetProperty(CompletionCase.class), CompletionCase.class);
                }
                return (CompletionCase)ShortTypeHandling.castToEnum((Object)callSiteArray[105].callGetProperty(CompletionCase.class), CompletionCase.class);
            }
            if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[106].callGetProperty((Object)currentToken), (Object)callSiteArray[107].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[108].callGetProperty((Object)currentToken), (Object)callSiteArray[109].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[110].callGetProperty((Object)currentToken), (Object)callSiteArray[111].callGetProperty(GroovyLexer.class))) {
                if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[112].call(tokens), (Object)1)) {
                    return (CompletionCase)ShortTypeHandling.castToEnum((Object)callSiteArray[113].callGetProperty(CompletionCase.class), CompletionCase.class);
                }
                return (CompletionCase)ShortTypeHandling.castToEnum((Object)callSiteArray[114].callGetProperty(CompletionCase.class), CompletionCase.class);
            }
            if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[115].callGetProperty((Object)currentToken), (Object)callSiteArray[116].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[117].callGetProperty((Object)currentToken), (Object)callSiteArray[118].callGetProperty(GroovyLexer.class))) {
                return (CompletionCase)ShortTypeHandling.castToEnum((Object)callSiteArray[119].callGetProperty(CompletionCase.class), CompletionCase.class);
            }
            callSiteArray[120].call((Object)LOG, callSiteArray[121].call((Object)"Unhandled token type: ", callSiteArray[122].callGetProperty((Object)currentToken)));
        } else {
            if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[123].callGetProperty((Object)currentToken), (Object)callSiteArray[124].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[125].callGetProperty((Object)currentToken), (Object)callSiteArray[126].callGetProperty(GroovyLexer.class))) {
                if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[127].call(tokens), (Object)1)) {
                    return (CompletionCase)ShortTypeHandling.castToEnum((Object)callSiteArray[128].callGetProperty(CompletionCase.class), CompletionCase.class);
                }
                Token previousToken = (Token)ScriptBytecodeAdapter.castToType((Object)callSiteArray[129].call(tokens, (Object)-2), Token.class);
                if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[130].callGetProperty((Object)previousToken), (Object)callSiteArray[131].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[132].callGetProperty((Object)previousToken), (Object)callSiteArray[133].callGetProperty(GroovyLexer.class))) {
                    if (ScriptBytecodeAdapter.compareLessThan((Object)callSiteArray[134].call(tokens), (Object)3)) {
                        return (CompletionCase)ShortTypeHandling.castToEnum((Object)callSiteArray[135].callGetProperty(CompletionCase.class), CompletionCase.class);
                    }
                    return (CompletionCase)ShortTypeHandling.castToEnum((Object)callSiteArray[136].callGetProperty(CompletionCase.class), CompletionCase.class);
                }
                if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[137].callGetProperty((Object)previousToken), (Object)callSiteArray[138].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[139].callGetProperty((Object)previousToken), (Object)callSiteArray[140].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[141].callGetProperty((Object)previousToken), (Object)callSiteArray[142].callGetProperty(GroovyLexer.class))) {
                    if (ScriptBytecodeAdapter.compareLessThan((Object)callSiteArray[143].call(tokens), (Object)3)) {
                        return (CompletionCase)ShortTypeHandling.castToEnum((Object)callSiteArray[144].callGetProperty(CompletionCase.class), CompletionCase.class);
                    }
                    return (CompletionCase)ShortTypeHandling.castToEnum((Object)callSiteArray[145].callGetProperty(CompletionCase.class), CompletionCase.class);
                }
                Object object = callSiteArray[146].callGetProperty((Object)previousToken);
                if (ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[147].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[148].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[149].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[150].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[151].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[152].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[153].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[154].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[155].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[156].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[157].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[158].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[159].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[160].callGetProperty(GroovyLexer.class))) {
                    return (CompletionCase)ShortTypeHandling.castToEnum((Object)callSiteArray[161].callGetProperty(CompletionCase.class), CompletionCase.class);
                }
                if (ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[162].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[163].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.isCase((Object)object, (Object)callSiteArray[164].callGetProperty(GroovyLexer.class))) {
                    return (CompletionCase)ShortTypeHandling.castToEnum((Object)callSiteArray[165].callGetProperty(CompletionCase.class), CompletionCase.class);
                }
                return (CompletionCase)ShortTypeHandling.castToEnum((Object)callSiteArray[166].callGetProperty(CompletionCase.class), CompletionCase.class);
            }
            if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[167].callGetProperty((Object)currentToken), (Object)callSiteArray[168].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[169].callGetProperty((Object)currentToken), (Object)callSiteArray[170].callGetProperty(GroovyLexer.class))) {
                if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[171].call(tokens), (Object)1)) {
                    return (CompletionCase)ShortTypeHandling.castToEnum((Object)callSiteArray[172].callGetProperty(CompletionCase.class), CompletionCase.class);
                }
                return (CompletionCase)ShortTypeHandling.castToEnum((Object)callSiteArray[173].callGetProperty(CompletionCase.class), CompletionCase.class);
            }
            if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[174].callGetProperty((Object)currentToken), (Object)callSiteArray[175].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[176].callGetProperty((Object)currentToken), (Object)callSiteArray[177].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[178].callGetProperty((Object)currentToken), (Object)callSiteArray[179].callGetProperty(GroovyLexer.class))) {
                if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[180].call(tokens), (Object)1)) {
                    return (CompletionCase)ShortTypeHandling.castToEnum((Object)callSiteArray[181].callGetProperty(CompletionCase.class), CompletionCase.class);
                }
                return (CompletionCase)ShortTypeHandling.castToEnum((Object)callSiteArray[182].callGetProperty(CompletionCase.class), CompletionCase.class);
            }
            if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[183].callGetProperty((Object)currentToken), (Object)callSiteArray[184].callGetProperty(GroovyLexer.class)) || ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[185].callGetProperty((Object)currentToken), (Object)callSiteArray[186].callGetProperty(GroovyLexer.class))) {
                return (CompletionCase)ShortTypeHandling.castToEnum((Object)callSiteArray[187].callGetProperty(CompletionCase.class), CompletionCase.class);
            }
            callSiteArray[188].call((Object)LOG, callSiteArray[189].call((Object)"Unhandled token type: ", callSiteArray[190].callGetProperty((Object)currentToken)));
        }
        return (CompletionCase)ShortTypeHandling.castToEnum((Object)callSiteArray[191].callGetProperty(CompletionCase.class), CompletionCase.class);
    }

    public int completeIdentifier(List<Token> tokens, List<CharSequence> candidates) {
        CallSite[] callSiteArray = GroovySyntaxCompleter.$getCallSiteArray();
        boolean foundMatches = false;
        IdentifierCompleter completer = null;
        Iterator iterator = (Iterator)ScriptBytecodeAdapter.castToType((Object)callSiteArray[192].call(this.identifierCompleters), Iterator.class);
        if (iterator != null) {
            while (iterator.hasNext()) {
                completer = (IdentifierCompleter)ScriptBytecodeAdapter.castToType(iterator.next(), IdentifierCompleter.class);
                foundMatches = DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[193].call((Object)foundMatches, callSiteArray[194].call((Object)completer, tokens, candidates)));
            }
        }
        if (foundMatches) {
            return DefaultTypeTransformation.intUnbox((Object)callSiteArray[195].callGetProperty(callSiteArray[196].call(tokens)));
        }
        return -1;
    }

    public static boolean isCommand(String bufferLine, CommandRegistry registry) {
        block6: {
            int commandEnd;
            CallSite[] callSiteArray;
            block5: {
                callSiteArray = GroovySyntaxCompleter.$getCallSiteArray();
                commandEnd = DefaultTypeTransformation.intUnbox((Object)callSiteArray[197].call((Object)bufferLine, (Object)" "));
                if (BytecodeInterface8.isOrigInt() && BytecodeInterface8.isOrigZ() && !__$stMC && !BytecodeInterface8.disabledStandardMetaClass()) break block5;
                if (!(commandEnd != -1)) break block6;
                String commandTokenText = ShortTypeHandling.castToString((Object)callSiteArray[198].call((Object)bufferLine, (Object)0, (Object)commandEnd));
                Object command = null;
                Iterator iterator = (Iterator)ScriptBytecodeAdapter.castToType((Object)callSiteArray[199].call(callSiteArray[200].call((Object)registry)), Iterator.class);
                if (iterator != null) {
                    while (iterator.hasNext()) {
                        command = iterator.next();
                        if (!(ScriptBytecodeAdapter.compareEqual((Object)commandTokenText, (Object)callSiteArray[201].callGetProperty(command)) || ScriptBytecodeAdapter.isCase((Object)commandTokenText, (Object)callSiteArray[202].callGetProperty(command)))) continue;
                        return true;
                    }
                }
                break block6;
            }
            if (commandEnd != -1) {
                String commandTokenText = ShortTypeHandling.castToString((Object)callSiteArray[203].call((Object)bufferLine, (Object)0, (Object)commandEnd));
                Object command = null;
                Iterator iterator = (Iterator)ScriptBytecodeAdapter.castToType((Object)callSiteArray[204].call(callSiteArray[205].call((Object)registry)), Iterator.class);
                if (iterator != null) {
                    while (iterator.hasNext()) {
                        command = iterator.next();
                        if (!(ScriptBytecodeAdapter.compareEqual((Object)commandTokenText, (Object)callSiteArray[206].callGetProperty(command)) || ScriptBytecodeAdapter.isCase((Object)commandTokenText, (Object)callSiteArray[207].callGetProperty(command)))) continue;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static Object createTokenStream(String text) {
        CallSite[] callSiteArray = GroovySyntaxCompleter.$getCallSiteArray();
        CharStream charStream = (CharStream)ScriptBytecodeAdapter.castToType((Object)callSiteArray[208].call(CharStreams.class, callSiteArray[209].callConstructor(StringReader.class, (Object)text)), CharStream.class);
        GroovyLangLexer lexer = (GroovyLangLexer)ScriptBytecodeAdapter.castToType((Object)callSiteArray[210].callConstructor(GroovyLangLexer.class, (Object)charStream), GroovyLangLexer.class);
        callSiteArray[211].call((Object)lexer, callSiteArray[212].callGetProperty(ConsoleErrorListener.class));
        Object tokenStream = callSiteArray[213].callConstructor(CommonTokenStream.class, (Object)lexer);
        callSiteArray[214].call(tokenStream);
        return tokenStream;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean tokenizeBuffer(String bufferLine, List<String> previousLines, List<Token> result) {
        Object tokenStream;
        CallSite[] callSiteArray;
        block19: {
            Object object;
            block21: {
                Object object2;
                block22: {
                    block20: {
                        callSiteArray = GroovySyntaxCompleter.$getCallSiteArray();
                        tokenStream = null;
                        if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) break block20;
                        if (!ScriptBytecodeAdapter.compareGreaterThan((Object)callSiteArray[227].call(previousLines), (Object)0)) break block21;
                        break block22;
                    }
                    if (ScriptBytecodeAdapter.compareGreaterThan((Object)callSiteArray[215].call(previousLines), (Object)0)) {
                        Object object3;
                        StringBuilder src = (StringBuilder)ScriptBytecodeAdapter.castToType((Object)callSiteArray[216].callConstructor(StringBuilder.class), StringBuilder.class);
                        String line = null;
                        Iterator iterator = (Iterator)ScriptBytecodeAdapter.castToType((Object)callSiteArray[217].call(previousLines), Iterator.class);
                        if (iterator != null) {
                            while (iterator.hasNext()) {
                                line = ShortTypeHandling.castToString(iterator.next());
                                callSiteArray[218].call(callSiteArray[219].call((Object)src, (Object)line), (Object)"\n");
                            }
                        }
                        callSiteArray[220].call((Object)src, (Object)bufferLine);
                        tokenStream = object3 = callSiteArray[221].call(callSiteArray[222].call(callSiteArray[223].callStatic(GroovySyntaxCompleter.class, callSiteArray[224].call((Object)src))));
                        break block19;
                    } else {
                        Object object4;
                        tokenStream = object4 = callSiteArray[225].call(callSiteArray[226].call(GroovySyntaxCompleter.createTokenStream(bufferLine)));
                    }
                    break block19;
                }
                StringBuilder src = (StringBuilder)ScriptBytecodeAdapter.castToType((Object)callSiteArray[228].callConstructor(StringBuilder.class), StringBuilder.class);
                String line = null;
                Iterator iterator = (Iterator)ScriptBytecodeAdapter.castToType((Object)callSiteArray[229].call(previousLines), Iterator.class);
                if (iterator != null) {
                    while (iterator.hasNext()) {
                        line = ShortTypeHandling.castToString(iterator.next());
                        callSiteArray[230].call(callSiteArray[231].call((Object)src, (Object)line), (Object)"\n");
                    }
                }
                callSiteArray[232].call((Object)src, (Object)bufferLine);
                tokenStream = object2 = callSiteArray[233].call(callSiteArray[234].call(callSiteArray[235].callStatic(GroovySyntaxCompleter.class, callSiteArray[236].call((Object)src))));
                break block19;
            }
            tokenStream = object = callSiteArray[237].call(callSiteArray[238].call(GroovySyntaxCompleter.createTokenStream(bufferLine)));
        }
        Token nextToken = null;
        Token lastToken = null;
        while (true) {
            try {
                Token token;
                nextToken = token = (Token)ScriptBytecodeAdapter.asType((Object)callSiteArray[239].call(tokenStream), Token.class);
                if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[240].callGetProperty((Object)nextToken), (Object)callSiteArray[241].callGetProperty(GroovyLexer.class))) {
                    if (!DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[242].call(result)) && ScriptBytecodeAdapter.compareGreaterThan((Object)callSiteArray[243].callGetProperty((Object)nextToken), (Object)callSiteArray[244].callGetProperty(callSiteArray[245].call(result)))) {
                        return false;
                    }
                    boolean bl = false;
                    if (!bl) break;
                    return false;
                }
            }
            catch (Exception e) {
                if (ScriptBytecodeAdapter.compareNotEqual(lastToken, null)) {
                    String restline = ShortTypeHandling.castToString((Object)callSiteArray[247].call((Object)bufferLine, callSiteArray[248].call(callSiteArray[249].callGetProperty(lastToken), (Object)1)));
                    int leadingBlanks = DefaultTypeTransformation.intUnbox((Object)callSiteArray[250].call(callSiteArray[251].call((Object)restline, (Object)"^[ ]*")));
                    if (DefaultTypeTransformation.booleanUnbox((Object)restline)) {
                        Reference remainder = new Reference((Object)ShortTypeHandling.castToString((Object)callSiteArray[252].call((Object)restline, (Object)leadingBlanks)));
                        public final class _tokenizeBuffer_closure1
                        extends Closure
                        implements GeneratedClosure {
                            private /* synthetic */ Reference remainder;
                            private static /* synthetic */ ClassInfo $staticClassInfo;
                            public static transient /* synthetic */ boolean __$stMC;
                            private static /* synthetic */ SoftReference $callSiteArray;

                            public _tokenizeBuffer_closure1(Object _outerInstance, Object _thisObject, Reference remainder) {
                                Reference reference;
                                CallSite[] callSiteArray = _tokenizeBuffer_closure1.$getCallSiteArray();
                                super(_outerInstance, _thisObject);
                                this.remainder = reference = remainder;
                            }

                            public Object doCall(Object it) {
                                CallSite[] callSiteArray = _tokenizeBuffer_closure1.$getCallSiteArray();
                                return callSiteArray[0].call(this.remainder.get(), it);
                            }

                            @Generated
                            public String getRemainder() {
                                CallSite[] callSiteArray = _tokenizeBuffer_closure1.$getCallSiteArray();
                                return ShortTypeHandling.castToString((Object)this.remainder.get());
                            }

                            @Generated
                            public Object doCall() {
                                CallSite[] callSiteArray = _tokenizeBuffer_closure1.$getCallSiteArray();
                                return this.doCall(null);
                            }

                            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                                if (((Object)((Object)this)).getClass() != _tokenizeBuffer_closure1.class) {
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
                                return new CallSiteArray(_tokenizeBuffer_closure1.class, stringArray);
                            }

                            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                                CallSiteArray callSiteArray;
                                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                                    callSiteArray = _tokenizeBuffer_closure1.$createCallSiteArray();
                                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                                }
                                return callSiteArray.array;
                            }
                        }
                        String openDelim = ShortTypeHandling.castToString((Object)callSiteArray[253].call(STRING_STARTERS, (Object)new _tokenizeBuffer_closure1(GroovySyntaxCompleter.class, GroovySyntaxCompleter.class, remainder)));
                        if (DefaultTypeTransformation.booleanUnbox((Object)openDelim) && ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[254].call(callSiteArray[255].call(previousLines), (Object)1), (Object)callSiteArray[256].callGetProperty((Object)lastToken))) {
                            throw (Throwable)callSiteArray[257].callConstructor(InStringException.class, callSiteArray[258].call(callSiteArray[259].call(callSiteArray[260].callGetProperty((Object)lastToken), (Object)leadingBlanks), (Object)1), (Object)openDelim);
                        }
                    }
                }
                boolean bl = false;
                return bl;
            }
            {
                Token token;
                callSiteArray[246].call(result, (Object)nextToken);
                lastToken = token = nextToken;
                continue;
            }
            break;
        }
        if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[261].callGetProperty(result))) return false;
        return true;
    }

    public /* synthetic */ Object this$dist$invoke$1(String name, Object args) {
        CallSite[] callSiteArray = GroovySyntaxCompleter.$getCallSiteArray();
        if (!(args instanceof Object[])) {
            return ScriptBytecodeAdapter.invokeMethodOnCurrentN(GroovySyntaxCompleter.class, (GroovyObject)this, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])new Object[]{args});
        }
        if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[262].callGetProperty((Object)((Object[])ScriptBytecodeAdapter.castToType((Object)args, Object[].class))), (Object)1)) {
                return ScriptBytecodeAdapter.invokeMethodOnCurrentN(GroovySyntaxCompleter.class, (GroovyObject)this, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])new Object[]{callSiteArray[263].call((Object)((Object[])ScriptBytecodeAdapter.castToType((Object)args, Object[].class)), (Object)0)});
            }
        } else if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[264].callGetProperty((Object)((Object[])ScriptBytecodeAdapter.castToType((Object)args, Object[].class))), (Object)1)) {
            return ScriptBytecodeAdapter.invokeMethodOnCurrentN(GroovySyntaxCompleter.class, (GroovyObject)this, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])new Object[]{BytecodeInterface8.objectArrayGet((Object[])((Object[])ScriptBytecodeAdapter.castToType((Object)args, Object[].class)), (int)0)});
        }
        return ScriptBytecodeAdapter.invokeMethodOnCurrentN(GroovySyntaxCompleter.class, (GroovyObject)this, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])ScriptBytecodeAdapter.despreadList((Object[])new Object[0], (Object[])new Object[]{args}, (int[])new int[]{0}));
    }

    public /* synthetic */ void this$dist$set$1(String name, Object value) {
        CallSite[] callSiteArray = GroovySyntaxCompleter.$getCallSiteArray();
        Object object = value;
        ScriptBytecodeAdapter.setGroovyObjectProperty((Object)object, GroovySyntaxCompleter.class, (GroovyObject)this, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})));
    }

    public /* synthetic */ Object this$dist$get$1(String name) {
        CallSite[] callSiteArray = GroovySyntaxCompleter.$getCallSiteArray();
        return ScriptBytecodeAdapter.getGroovyObjectProperty(GroovySyntaxCompleter.class, (GroovyObject)this, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})));
    }

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != GroovySyntaxCompleter.class) {
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
        Object object = GroovySyntaxCompleter.$getCallSiteArray()[265].call(Logger.class, GroovySyntaxCompleter.class);
        LOG = (Logger)ScriptBytecodeAdapter.castToType((Object)object, Logger.class);
        Object object2 = GroovySyntaxCompleter.$getCallSiteArray()[266].call(Configuration.class);
        isWin = DefaultTypeTransformation.booleanUnbox((Object)object2);
        List list = ScriptBytecodeAdapter.createList((Object[])new Object[]{"\"\"\"", "'''", "\"", "'", "$/", "/"});
        STRING_STARTERS = list;
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
        stringArray[0] = "<$constructor$>";
        stringArray[1] = "<$constructor$>";
        stringArray[2] = "<$constructor$>";
        stringArray[3] = "<$constructor$>";
        stringArray[4] = "<$constructor$>";
        stringArray[5] = "isCommand";
        stringArray[6] = "registry";
        stringArray[7] = "tokenizeBuffer";
        stringArray[8] = "substring";
        stringArray[9] = "current";
        stringArray[10] = "buffers";
        stringArray[11] = "plus";
        stringArray[12] = "column";
        stringArray[13] = "size";
        stringArray[14] = "openDelim";
        stringArray[15] = "substring";
        stringArray[16] = "contains";
        stringArray[17] = "openDelim";
        stringArray[18] = "contains";
        stringArray[19] = "evaluate";
        stringArray[20] = "evaluate";
        stringArray[21] = "substring";
        stringArray[22] = "minus";
        stringArray[23] = "size";
        stringArray[24] = "complete";
        stringArray[25] = "contains";
        stringArray[26] = "evaluate";
        stringArray[27] = "evaluate";
        stringArray[28] = "substring";
        stringArray[29] = "minus";
        stringArray[30] = "size";
        stringArray[31] = "complete";
        stringArray[32] = "complete";
        stringArray[33] = "minus";
        stringArray[34] = "complete";
        stringArray[35] = "plus";
        stringArray[36] = "getCompletionCase";
        stringArray[37] = "NO_COMPLETION";
        stringArray[38] = "SECOND_IDENT";
        stringArray[39] = "complete";
        stringArray[40] = "startIndex";
        stringArray[41] = "last";
        stringArray[42] = "INSTANCEOF";
        stringArray[43] = "complete";
        stringArray[44] = "startIndex";
        stringArray[45] = "last";
        stringArray[46] = "NO_DOT_PREFIX";
        stringArray[47] = "completeIdentifier";
        stringArray[48] = "DOT_LAST";
        stringArray[49] = "PREFIX_AFTER_DOT";
        stringArray[50] = "SPREAD_DOT_LAST";
        stringArray[51] = "PREFIX_AFTER_SPREAD_DOT";
        stringArray[52] = "complete";
        stringArray[53] = "<$constructor$>";
        stringArray[54] = "getAt";
        stringArray[55] = "type";
        stringArray[56] = "Identifier";
        stringArray[57] = "type";
        stringArray[58] = "CapitalizedIdentifier";
        stringArray[59] = "size";
        stringArray[60] = "NO_DOT_PREFIX";
        stringArray[61] = "getAt";
        stringArray[62] = "type";
        stringArray[63] = "DOT";
        stringArray[64] = "type";
        stringArray[65] = "SAFE_DOT";
        stringArray[66] = "size";
        stringArray[67] = "NO_COMPLETION";
        stringArray[68] = "PREFIX_AFTER_DOT";
        stringArray[69] = "type";
        stringArray[70] = "SPREAD_DOT";
        stringArray[71] = "type";
        stringArray[72] = "METHOD_POINTER";
        stringArray[73] = "type";
        stringArray[74] = "METHOD_REFERENCE";
        stringArray[75] = "size";
        stringArray[76] = "NO_COMPLETION";
        stringArray[77] = "PREFIX_AFTER_SPREAD_DOT";
        stringArray[78] = "type";
        stringArray[79] = "IMPORT";
        stringArray[80] = "CLASS";
        stringArray[81] = "INTERFACE";
        stringArray[82] = "ENUM";
        stringArray[83] = "DEF";
        stringArray[84] = "VOID";
        stringArray[85] = "BuiltInPrimitiveType";
        stringArray[86] = "PACKAGE";
        stringArray[87] = "BooleanLiteral";
        stringArray[88] = "AS";
        stringArray[89] = "THIS";
        stringArray[90] = "TRY";
        stringArray[91] = "FINALLY";
        stringArray[92] = "CATCH";
        stringArray[93] = "NO_COMPLETION";
        stringArray[94] = "NOT";
        stringArray[95] = "CapitalizedIdentifier";
        stringArray[96] = "Identifier";
        stringArray[97] = "SECOND_IDENT";
        stringArray[98] = "NO_DOT_PREFIX";
        stringArray[99] = "type";
        stringArray[100] = "DOT";
        stringArray[101] = "type";
        stringArray[102] = "SAFE_DOT";
        stringArray[103] = "size";
        stringArray[104] = "NO_COMPLETION";
        stringArray[105] = "DOT_LAST";
        stringArray[106] = "type";
        stringArray[107] = "SPREAD_DOT";
        stringArray[108] = "type";
        stringArray[109] = "METHOD_REFERENCE";
        stringArray[110] = "type";
        stringArray[111] = "METHOD_POINTER";
        stringArray[112] = "size";
        stringArray[113] = "NO_COMPLETION";
        stringArray[114] = "SPREAD_DOT_LAST";
        stringArray[115] = "type";
        stringArray[116] = "INSTANCEOF";
        stringArray[117] = "type";
        stringArray[118] = "NOT_INSTANCEOF";
        stringArray[119] = "INSTANCEOF";
        stringArray[120] = "debug";
        stringArray[121] = "plus";
        stringArray[122] = "type";
        stringArray[123] = "type";
        stringArray[124] = "Identifier";
        stringArray[125] = "type";
        stringArray[126] = "CapitalizedIdentifier";
        stringArray[127] = "size";
        stringArray[128] = "NO_DOT_PREFIX";
        stringArray[129] = "getAt";
        stringArray[130] = "type";
        stringArray[131] = "DOT";
        stringArray[132] = "type";
        stringArray[133] = "SAFE_DOT";
        stringArray[134] = "size";
        stringArray[135] = "NO_COMPLETION";
        stringArray[136] = "PREFIX_AFTER_DOT";
        stringArray[137] = "type";
        stringArray[138] = "SPREAD_DOT";
        stringArray[139] = "type";
        stringArray[140] = "METHOD_POINTER";
        stringArray[141] = "type";
        stringArray[142] = "METHOD_REFERENCE";
        stringArray[143] = "size";
        stringArray[144] = "NO_COMPLETION";
        stringArray[145] = "PREFIX_AFTER_SPREAD_DOT";
        stringArray[146] = "type";
        stringArray[147] = "IMPORT";
        stringArray[148] = "CLASS";
        stringArray[149] = "INTERFACE";
        stringArray[150] = "ENUM";
        stringArray[151] = "DEF";
        stringArray[152] = "VOID";
        stringArray[153] = "BuiltInPrimitiveType";
        stringArray[154] = "PACKAGE";
        stringArray[155] = "BooleanLiteral";
        stringArray[156] = "AS";
        stringArray[157] = "THIS";
        stringArray[158] = "TRY";
        stringArray[159] = "FINALLY";
        stringArray[160] = "CATCH";
        stringArray[161] = "NO_COMPLETION";
        stringArray[162] = "NOT";
        stringArray[163] = "CapitalizedIdentifier";
        stringArray[164] = "Identifier";
        stringArray[165] = "SECOND_IDENT";
        stringArray[166] = "NO_DOT_PREFIX";
        stringArray[167] = "type";
        stringArray[168] = "DOT";
        stringArray[169] = "type";
        stringArray[170] = "SAFE_DOT";
        stringArray[171] = "size";
        stringArray[172] = "NO_COMPLETION";
        stringArray[173] = "DOT_LAST";
        stringArray[174] = "type";
        stringArray[175] = "SPREAD_DOT";
        stringArray[176] = "type";
        stringArray[177] = "METHOD_REFERENCE";
        stringArray[178] = "type";
        stringArray[179] = "METHOD_POINTER";
        stringArray[180] = "size";
        stringArray[181] = "NO_COMPLETION";
        stringArray[182] = "SPREAD_DOT_LAST";
        stringArray[183] = "type";
        stringArray[184] = "INSTANCEOF";
        stringArray[185] = "type";
        stringArray[186] = "NOT_INSTANCEOF";
        stringArray[187] = "INSTANCEOF";
        stringArray[188] = "debug";
        stringArray[189] = "plus";
        stringArray[190] = "type";
        stringArray[191] = "NO_COMPLETION";
        stringArray[192] = "iterator";
        stringArray[193] = "or";
        stringArray[194] = "complete";
        stringArray[195] = "startIndex";
        stringArray[196] = "last";
        stringArray[197] = "indexOf";
        stringArray[198] = "substring";
        stringArray[199] = "iterator";
        stringArray[200] = "commands";
        stringArray[201] = "name";
        stringArray[202] = "aliases";
        stringArray[203] = "substring";
        stringArray[204] = "iterator";
        stringArray[205] = "commands";
        stringArray[206] = "name";
        stringArray[207] = "aliases";
        stringArray[208] = "fromReader";
        stringArray[209] = "<$constructor$>";
        stringArray[210] = "<$constructor$>";
        stringArray[211] = "removeErrorListener";
        stringArray[212] = "INSTANCE";
        stringArray[213] = "<$constructor$>";
        stringArray[214] = "fill";
        stringArray[215] = "size";
        stringArray[216] = "<$constructor$>";
        stringArray[217] = "iterator";
        stringArray[218] = "append";
        stringArray[219] = "append";
        stringArray[220] = "append";
        stringArray[221] = "iterator";
        stringArray[222] = "getTokens";
        stringArray[223] = "createTokenStream";
        stringArray[224] = "toString";
        stringArray[225] = "iterator";
        stringArray[226] = "getTokens";
        stringArray[227] = "size";
        stringArray[228] = "<$constructor$>";
        stringArray[229] = "iterator";
        stringArray[230] = "append";
        stringArray[231] = "append";
        stringArray[232] = "append";
        stringArray[233] = "iterator";
        stringArray[234] = "getTokens";
        stringArray[235] = "createTokenStream";
        stringArray[236] = "toString";
        stringArray[237] = "iterator";
        stringArray[238] = "getTokens";
        stringArray[239] = "next";
        stringArray[240] = "type";
        stringArray[241] = "EOF";
        stringArray[242] = "isEmpty";
        stringArray[243] = "line";
        stringArray[244] = "line";
        stringArray[245] = "last";
        stringArray[246] = "leftShift";
        stringArray[247] = "substring";
        stringArray[248] = "minus";
        stringArray[249] = "columnLast";
        stringArray[250] = "length";
        stringArray[251] = "find";
        stringArray[252] = "substring";
        stringArray[253] = "find";
        stringArray[254] = "plus";
        stringArray[255] = "size";
        stringArray[256] = "line";
        stringArray[257] = "<$constructor$>";
        stringArray[258] = "minus";
        stringArray[259] = "plus";
        stringArray[260] = "columnLast";
        stringArray[261] = "empty";
        stringArray[262] = "length";
        stringArray[263] = "getAt";
        stringArray[264] = "length";
        stringArray[265] = "create";
        stringArray[266] = "isWindows";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[267];
        GroovySyntaxCompleter.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(GroovySyntaxCompleter.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = GroovySyntaxCompleter.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }

    public static class InStringException
    extends Exception
    implements GroovyObject {
        private int column;
        private String openDelim;
        private static final long serialVersionUID = -2554041223576762580L;
        private static /* synthetic */ long $const$0;
        private static /* synthetic */ ClassInfo $staticClassInfo;
        public static transient /* synthetic */ boolean __$stMC;
        private transient /* synthetic */ MetaClass metaClass;
        private static /* synthetic */ SoftReference $callSiteArray;

        @Generated
        public InStringException(int column, String openDelim) {
            String string;
            int n;
            MetaClass metaClass;
            CallSite[] callSiteArray = InStringException.$getCallSiteArray();
            this.metaClass = metaClass = this.$getStaticMetaClass();
            this.column = n = column;
            this.openDelim = string = openDelim;
        }

        @Generated
        public InStringException(int column) {
            CallSite[] callSiteArray = InStringException.$getCallSiteArray();
            this(column, null);
        }

        @Generated
        public InStringException() {
            CallSite[] callSiteArray = InStringException.$getCallSiteArray();
            this(0, null);
        }

        @Override
        public String toString() {
            CallSite[] callSiteArray = InStringException.$getCallSiteArray();
            return ShortTypeHandling.castToString((Object)callSiteArray[0].call(ScriptBytecodeAdapter.invokeMethodOnSuper0(InStringException.class, (GroovyObject)this, (String)"toString"), (Object)new GStringImpl(new Object[]{this.column, this.openDelim}, new String[]{"[column=", ", openDelim=", "]"})));
        }

        public /* synthetic */ Object methodMissing(String name, Object args) {
            CallSite[] callSiteArray = InStringException.$getCallSiteArray();
            if (!(args instanceof Object[])) {
                return ScriptBytecodeAdapter.invokeMethodN(InStringException.class, GroovySyntaxCompleter.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])new Object[]{args});
            }
            if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[1].callGetProperty((Object)((Object[])ScriptBytecodeAdapter.castToType((Object)args, Object[].class))), (Object)1)) {
                    return ScriptBytecodeAdapter.invokeMethodN(InStringException.class, GroovySyntaxCompleter.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])new Object[]{callSiteArray[2].call((Object)((Object[])ScriptBytecodeAdapter.castToType((Object)args, Object[].class)), (Object)0)});
                }
            } else if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[3].callGetProperty((Object)((Object[])ScriptBytecodeAdapter.castToType((Object)args, Object[].class))), (Object)1)) {
                return ScriptBytecodeAdapter.invokeMethodN(InStringException.class, GroovySyntaxCompleter.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])new Object[]{BytecodeInterface8.objectArrayGet((Object[])((Object[])ScriptBytecodeAdapter.castToType((Object)args, Object[].class)), (int)0)});
            }
            return ScriptBytecodeAdapter.invokeMethodN(InStringException.class, GroovySyntaxCompleter.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])ScriptBytecodeAdapter.despreadList((Object[])new Object[0], (Object[])new Object[]{args}, (int[])new int[]{0}));
        }

        public static /* synthetic */ Object $static_methodMissing(String name, Object args) {
            CallSite[] callSiteArray = InStringException.$getCallSiteArray();
            if (!(args instanceof Object[])) {
                return ScriptBytecodeAdapter.invokeMethodN(InStringException.class, GroovySyntaxCompleter.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])new Object[]{args});
            }
            if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
                if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[4].callGetProperty((Object)((Object[])ScriptBytecodeAdapter.castToType((Object)args, Object[].class))), (Object)1)) {
                    return ScriptBytecodeAdapter.invokeMethodN(InStringException.class, GroovySyntaxCompleter.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])new Object[]{callSiteArray[5].call((Object)((Object[])ScriptBytecodeAdapter.castToType((Object)args, Object[].class)), (Object)0)});
                }
            } else if (ScriptBytecodeAdapter.compareEqual((Object)callSiteArray[6].callGetProperty((Object)((Object[])ScriptBytecodeAdapter.castToType((Object)args, Object[].class))), (Object)1)) {
                return ScriptBytecodeAdapter.invokeMethodN(InStringException.class, GroovySyntaxCompleter.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])new Object[]{BytecodeInterface8.objectArrayGet((Object[])((Object[])ScriptBytecodeAdapter.castToType((Object)args, Object[].class)), (int)0)});
            }
            return ScriptBytecodeAdapter.invokeMethodN(InStringException.class, GroovySyntaxCompleter.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})), (Object[])ScriptBytecodeAdapter.despreadList((Object[])new Object[0], (Object[])new Object[]{args}, (int[])new int[]{0}));
        }

        public /* synthetic */ void propertyMissing(String name, Object value) {
            CallSite[] callSiteArray = InStringException.$getCallSiteArray();
            Object object = value;
            ScriptBytecodeAdapter.setProperty((Object)object, null, GroovySyntaxCompleter.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

        public static /* synthetic */ void $static_propertyMissing(String name, Object value) {
            CallSite[] callSiteArray = InStringException.$getCallSiteArray();
            Object object = value;
            ScriptBytecodeAdapter.setProperty((Object)object, null, GroovySyntaxCompleter.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

        public /* synthetic */ Object propertyMissing(String name) {
            CallSite[] callSiteArray = InStringException.$getCallSiteArray();
            return ScriptBytecodeAdapter.getProperty(InStringException.class, GroovySyntaxCompleter.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

        public static /* synthetic */ Object $static_propertyMissing(String name) {
            CallSite[] callSiteArray = InStringException.$getCallSiteArray();
            return ScriptBytecodeAdapter.getProperty(InStringException.class, GroovySyntaxCompleter.class, (String)ShortTypeHandling.castToString((Object)new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

        protected /* synthetic */ MetaClass $getStaticMetaClass() {
            if (this.getClass() != InStringException.class) {
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

        public static /* synthetic */ void __$swapInit() {
            long l;
            CallSite[] callSiteArray = InStringException.$getCallSiteArray();
            $callSiteArray = null;
            $const$0 = l = -2554041223576762580L;
        }

        static {
            InStringException.__$swapInit();
        }

        @Generated
        public int getColumn() {
            return this.column;
        }

        @Generated
        public void setColumn(int n) {
            this.column = n;
        }

        @Generated
        public String getOpenDelim() {
            return this.openDelim;
        }

        @Generated
        public void setOpenDelim(String string) {
            this.openDelim = string;
        }

        public /* synthetic */ String super$2$toString() {
            return super.toString();
        }

        private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
            stringArray[0] = "plus";
            stringArray[1] = "length";
            stringArray[2] = "getAt";
            stringArray[3] = "length";
            stringArray[4] = "length";
            stringArray[5] = "getAt";
            stringArray[6] = "length";
        }

        private static /* synthetic */ CallSiteArray $createCallSiteArray() {
            String[] stringArray = new String[7];
            InStringException.$createCallSiteArray_1(stringArray);
            return new CallSiteArray(InStringException.class, stringArray);
        }

        private static /* synthetic */ CallSite[] $getCallSiteArray() {
            CallSiteArray callSiteArray;
            if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                callSiteArray = InStringException.$createCallSiteArray();
                $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
            }
            return callSiteArray.array;
        }
    }

    public static final class CompletionCase
    extends Enum<CompletionCase>
    implements GroovyObject {
        public static final /* enum */ CompletionCase SECOND_IDENT;
        public static final /* enum */ CompletionCase NO_COMPLETION;
        public static final /* enum */ CompletionCase DOT_LAST;
        public static final /* enum */ CompletionCase SPREAD_DOT_LAST;
        public static final /* enum */ CompletionCase PREFIX_AFTER_DOT;
        public static final /* enum */ CompletionCase PREFIX_AFTER_SPREAD_DOT;
        public static final /* enum */ CompletionCase NO_DOT_PREFIX;
        public static final /* enum */ CompletionCase INSTANCEOF;
        public static final CompletionCase MIN_VALUE;
        public static final CompletionCase MAX_VALUE;
        private static final /* synthetic */ CompletionCase[] $VALUES;
        private static /* synthetic */ ClassInfo $staticClassInfo;
        public static transient /* synthetic */ boolean __$stMC;
        private transient /* synthetic */ MetaClass metaClass;
        private static /* synthetic */ SoftReference $callSiteArray;

        @Generated
        private CompletionCase(LinkedHashMap __namedArgs) {
            MetaClass metaClass;
            CallSite[] callSiteArray = CompletionCase.$getCallSiteArray();
            this.metaClass = metaClass = this.$getStaticMetaClass();
            if (ScriptBytecodeAdapter.compareEqual((Object)__namedArgs, null)) {
                throw (Throwable)callSiteArray[0].callConstructor(IllegalArgumentException.class, (Object)"One of the enum constants for enum org.apache.groovy.groovysh.completion.antlr4.GroovySyntaxCompleter$CompletionCase was initialized with null. Please use a non-null value or define your own constructor.");
            }
            callSiteArray[1].callStatic(ImmutableASTTransformation.class, (Object)this, (Object)__namedArgs);
        }

        @Generated
        private CompletionCase() {
            CallSite[] callSiteArray = CompletionCase.$getCallSiteArray();
            this((LinkedHashMap)ScriptBytecodeAdapter.castToType((Object)callSiteArray[2].callConstructor(LinkedHashMap.class), LinkedHashMap.class));
        }

        @Generated
        public static final CompletionCase[] values() {
            CallSite[] callSiteArray = CompletionCase.$getCallSiteArray();
            return (CompletionCase[])ScriptBytecodeAdapter.castToType((Object)$VALUES.clone(), CompletionCase[].class);
        }

        @Generated
        public CompletionCase next() {
            CallSite[] callSiteArray = CompletionCase.$getCallSiteArray();
            Object ordinal = callSiteArray[3].call(callSiteArray[4].callCurrent((GroovyObject)this));
            if (ScriptBytecodeAdapter.compareGreaterThanEqual((Object)ordinal, (Object)callSiteArray[5].call((Object)$VALUES))) {
                Integer n = 0;
                ordinal = n;
            }
            return (CompletionCase)ShortTypeHandling.castToEnum((Object)callSiteArray[6].call((Object)$VALUES, ordinal), CompletionCase.class);
        }

        @Generated
        public CompletionCase previous() {
            CallSite[] callSiteArray = CompletionCase.$getCallSiteArray();
            Object ordinal = callSiteArray[7].call(callSiteArray[8].callCurrent((GroovyObject)this));
            if (ScriptBytecodeAdapter.compareLessThan((Object)ordinal, (Object)0)) {
                Object object;
                ordinal = object = callSiteArray[9].call(callSiteArray[10].call((Object)$VALUES), (Object)1);
            }
            return (CompletionCase)ShortTypeHandling.castToEnum((Object)callSiteArray[11].call((Object)$VALUES, ordinal), CompletionCase.class);
        }

        @Generated
        public static CompletionCase valueOf(String name) {
            CallSite[] callSiteArray = CompletionCase.$getCallSiteArray();
            return (CompletionCase)ShortTypeHandling.castToEnum((Object)callSiteArray[12].callStatic(CompletionCase.class, CompletionCase.class, (Object)name), CompletionCase.class);
        }

        @Generated
        public static final /* synthetic */ CompletionCase $INIT(Object ... para) {
            CompletionCase completionCase;
            CallSite[] callSiteArray = CompletionCase.$getCallSiteArray();
            Object[] objectArray = ScriptBytecodeAdapter.despreadList((Object[])new Object[0], (Object[])new Object[]{para}, (int[])new int[]{0});
            switch (ScriptBytecodeAdapter.selectConstructorAndTransformArguments((Object[])objectArray, (int)-1, CompletionCase.class)) {
                case -1348271900: {
                    CompletionCase completionCase2;
                    completionCase = completionCase2;
                    Object[] objectArray2 = objectArray;
                    completionCase2 = new CompletionCase();
                    break;
                }
                case -242181752: {
                    CompletionCase completionCase2;
                    completionCase = completionCase2;
                    Object[] objectArray2 = objectArray;
                    completionCase2 = new CompletionCase((LinkedHashMap)ScriptBytecodeAdapter.castToType((Object)objectArray[2], LinkedHashMap.class));
                    break;
                }
                default: {
                    throw new IllegalArgumentException("This class has been compiled with a super class which is binary incompatible with the current super class found on classpath. You should recompile this class with the new version.");
                }
            }
            return completionCase;
        }

        static {
            CompletionCase completionCase;
            CompletionCase completionCase2;
            Object object = CompletionCase.$getCallSiteArray()[13].callStatic(CompletionCase.class, (Object)"SECOND_IDENT", (Object)0);
            SECOND_IDENT = (CompletionCase)ShortTypeHandling.castToEnum((Object)object, CompletionCase.class);
            Object object2 = CompletionCase.$getCallSiteArray()[14].callStatic(CompletionCase.class, (Object)"NO_COMPLETION", (Object)1);
            NO_COMPLETION = (CompletionCase)ShortTypeHandling.castToEnum((Object)object2, CompletionCase.class);
            Object object3 = CompletionCase.$getCallSiteArray()[15].callStatic(CompletionCase.class, (Object)"DOT_LAST", (Object)2);
            DOT_LAST = (CompletionCase)ShortTypeHandling.castToEnum((Object)object3, CompletionCase.class);
            Object object4 = CompletionCase.$getCallSiteArray()[16].callStatic(CompletionCase.class, (Object)"SPREAD_DOT_LAST", (Object)3);
            SPREAD_DOT_LAST = (CompletionCase)ShortTypeHandling.castToEnum((Object)object4, CompletionCase.class);
            Object object5 = CompletionCase.$getCallSiteArray()[17].callStatic(CompletionCase.class, (Object)"PREFIX_AFTER_DOT", (Object)4);
            PREFIX_AFTER_DOT = (CompletionCase)ShortTypeHandling.castToEnum((Object)object5, CompletionCase.class);
            Object object6 = CompletionCase.$getCallSiteArray()[18].callStatic(CompletionCase.class, (Object)"PREFIX_AFTER_SPREAD_DOT", (Object)5);
            PREFIX_AFTER_SPREAD_DOT = (CompletionCase)ShortTypeHandling.castToEnum((Object)object6, CompletionCase.class);
            Object object7 = CompletionCase.$getCallSiteArray()[19].callStatic(CompletionCase.class, (Object)"NO_DOT_PREFIX", (Object)6);
            NO_DOT_PREFIX = (CompletionCase)ShortTypeHandling.castToEnum((Object)object7, CompletionCase.class);
            Object object8 = CompletionCase.$getCallSiteArray()[20].callStatic(CompletionCase.class, (Object)"INSTANCEOF", (Object)7);
            INSTANCEOF = (CompletionCase)ShortTypeHandling.castToEnum((Object)object8, CompletionCase.class);
            MIN_VALUE = completionCase2 = SECOND_IDENT;
            MAX_VALUE = completionCase = INSTANCEOF;
            CompletionCase[] completionCaseArray = new CompletionCase[]{SECOND_IDENT, NO_COMPLETION, DOT_LAST, SPREAD_DOT_LAST, PREFIX_AFTER_DOT, PREFIX_AFTER_SPREAD_DOT, NO_DOT_PREFIX, INSTANCEOF};
            $VALUES = completionCaseArray;
        }

        protected /* synthetic */ MetaClass $getStaticMetaClass() {
            if (((Object)((Object)this)).getClass() != CompletionCase.class) {
                return ScriptBytecodeAdapter.initMetaClass((Object)((Object)this));
            }
            ClassInfo classInfo = $staticClassInfo;
            if (classInfo == null) {
                $staticClassInfo = classInfo = ClassInfo.getClassInfo(((Object)((Object)this)).getClass());
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

        private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
            stringArray[0] = "<$constructor$>";
            stringArray[1] = "checkPropNames";
            stringArray[2] = "<$constructor$>";
            stringArray[3] = "next";
            stringArray[4] = "ordinal";
            stringArray[5] = "size";
            stringArray[6] = "getAt";
            stringArray[7] = "previous";
            stringArray[8] = "ordinal";
            stringArray[9] = "minus";
            stringArray[10] = "size";
            stringArray[11] = "getAt";
            stringArray[12] = "valueOf";
            stringArray[13] = "$INIT";
            stringArray[14] = "$INIT";
            stringArray[15] = "$INIT";
            stringArray[16] = "$INIT";
            stringArray[17] = "$INIT";
            stringArray[18] = "$INIT";
            stringArray[19] = "$INIT";
            stringArray[20] = "$INIT";
        }

        private static /* synthetic */ CallSiteArray $createCallSiteArray() {
            String[] stringArray = new String[21];
            CompletionCase.$createCallSiteArray_1(stringArray);
            return new CallSiteArray(CompletionCase.class, stringArray);
        }

        private static /* synthetic */ CallSite[] $getCallSiteArray() {
            CallSiteArray callSiteArray;
            if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                callSiteArray = CompletionCase.$createCallSiteArray();
                $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
            }
            return callSiteArray.array;
        }
    }
}

