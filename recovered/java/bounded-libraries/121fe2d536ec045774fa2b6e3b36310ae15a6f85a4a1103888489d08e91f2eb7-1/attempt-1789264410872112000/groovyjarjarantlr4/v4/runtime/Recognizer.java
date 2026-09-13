/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime;

import groovyjarjarantlr4.v4.runtime.ANTLRErrorListener;
import groovyjarjarantlr4.v4.runtime.ConsoleErrorListener;
import groovyjarjarantlr4.v4.runtime.IntStream;
import groovyjarjarantlr4.v4.runtime.ProxyErrorListener;
import groovyjarjarantlr4.v4.runtime.RecognitionException;
import groovyjarjarantlr4.v4.runtime.RuleContext;
import groovyjarjarantlr4.v4.runtime.Token;
import groovyjarjarantlr4.v4.runtime.Vocabulary;
import groovyjarjarantlr4.v4.runtime.VocabularyImpl;
import groovyjarjarantlr4.v4.runtime.atn.ATN;
import groovyjarjarantlr4.v4.runtime.atn.ATNSimulator;
import groovyjarjarantlr4.v4.runtime.atn.ParseInfo;
import groovyjarjarantlr4.v4.runtime.misc.Args;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import groovyjarjarantlr4.v4.runtime.misc.Utils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Recognizer<Symbol, ATNInterpreter extends ATNSimulator> {
    public static final int EOF = -1;
    private static final Map<Vocabulary, Map<String, Integer>> tokenTypeMapCache = new WeakHashMap<Vocabulary, Map<String, Integer>>();
    private static final Map<String[], Map<String, Integer>> ruleIndexMapCache = new WeakHashMap<String[], Map<String, Integer>>();
    @NotNull
    private List<ANTLRErrorListener<? super Symbol>> _listeners = new CopyOnWriteArrayList<ANTLRErrorListener<? super Symbol>>(){
        {
            this.add(ConsoleErrorListener.INSTANCE);
        }
    };
    protected ATNInterpreter _interp;
    private int _stateNumber = -1;

    @Deprecated
    public abstract String[] getTokenNames();

    public abstract String[] getRuleNames();

    @NotNull
    public Vocabulary getVocabulary() {
        return VocabularyImpl.fromTokenNames(this.getTokenNames());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NotNull
    public Map<String, Integer> getTokenTypeMap() {
        Vocabulary vocabulary = this.getVocabulary();
        Map<Vocabulary, Map<String, Integer>> map = tokenTypeMapCache;
        synchronized (map) {
            Map<String, Integer> result = tokenTypeMapCache.get(vocabulary);
            if (result == null) {
                result = new HashMap<String, Integer>();
                for (int i = 0; i <= this.getATN().maxTokenType; ++i) {
                    String symbolicName;
                    String literalName = vocabulary.getLiteralName(i);
                    if (literalName != null) {
                        result.put(literalName, i);
                    }
                    if ((symbolicName = vocabulary.getSymbolicName(i)) == null) continue;
                    result.put(symbolicName, i);
                }
                result.put("EOF", -1);
                result = Collections.unmodifiableMap(result);
                tokenTypeMapCache.put(vocabulary, result);
            }
            return result;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NotNull
    public Map<String, Integer> getRuleIndexMap() {
        String[] ruleNames = this.getRuleNames();
        if (ruleNames == null) {
            throw new UnsupportedOperationException("The current recognizer does not provide a list of rule names.");
        }
        Map<String[], Map<String, Integer>> map = ruleIndexMapCache;
        synchronized (map) {
            Map<String, Integer> result = ruleIndexMapCache.get(ruleNames);
            if (result == null) {
                result = Collections.unmodifiableMap(Utils.toMap(ruleNames));
                ruleIndexMapCache.put(ruleNames, result);
            }
            return result;
        }
    }

    public int getTokenType(String tokenName) {
        Integer ttype = this.getTokenTypeMap().get(tokenName);
        if (ttype != null) {
            return ttype;
        }
        return 0;
    }

    @NotNull
    public String getSerializedATN() {
        throw new UnsupportedOperationException("there is no serialized ATN");
    }

    public abstract String getGrammarFileName();

    @NotNull
    public ATN getATN() {
        return ((ATNSimulator)this._interp).atn;
    }

    @NotNull
    public ATNInterpreter getInterpreter() {
        return this._interp;
    }

    public ParseInfo getParseInfo() {
        return null;
    }

    public void setInterpreter(@NotNull ATNInterpreter interpreter) {
        this._interp = interpreter;
    }

    @NotNull
    public String getErrorHeader(@NotNull RecognitionException e) {
        int line = e.getOffendingToken().getLine();
        int charPositionInLine = e.getOffendingToken().getCharPositionInLine();
        return "line " + line + ":" + charPositionInLine;
    }

    @Deprecated
    public String getTokenErrorDisplay(Token t) {
        if (t == null) {
            return "<no token>";
        }
        String s = t.getText();
        if (s == null) {
            s = t.getType() == -1 ? "<EOF>" : "<" + t.getType() + ">";
        }
        s = s.replace("\n", "\\n");
        s = s.replace("\r", "\\r");
        s = s.replace("\t", "\\t");
        return "'" + s + "'";
    }

    public void addErrorListener(@NotNull ANTLRErrorListener<? super Symbol> listener) {
        Args.notNull("listener", listener);
        this._listeners.add(listener);
    }

    public void removeErrorListener(@NotNull ANTLRErrorListener<? super Symbol> listener) {
        this._listeners.remove(listener);
    }

    public void removeErrorListeners() {
        this._listeners.clear();
    }

    @NotNull
    public List<? extends ANTLRErrorListener<? super Symbol>> getErrorListeners() {
        return new ArrayList<ANTLRErrorListener<? super Symbol>>(this._listeners);
    }

    public ANTLRErrorListener<? super Symbol> getErrorListenerDispatch() {
        return new ProxyErrorListener<Symbol>(this.getErrorListeners());
    }

    public boolean sempred(@Nullable RuleContext _localctx, int ruleIndex, int actionIndex) {
        return true;
    }

    public boolean precpred(@Nullable RuleContext localctx, int precedence) {
        return true;
    }

    public void action(@Nullable RuleContext _localctx, int ruleIndex, int actionIndex) {
    }

    public final int getState() {
        return this._stateNumber;
    }

    public final void setState(int atnState) {
        this._stateNumber = atnState;
    }

    public abstract IntStream getInputStream();
}

