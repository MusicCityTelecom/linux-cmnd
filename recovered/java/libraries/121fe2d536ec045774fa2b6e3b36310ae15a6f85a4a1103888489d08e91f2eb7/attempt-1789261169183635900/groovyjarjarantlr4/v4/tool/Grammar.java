/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.tool;

import groovyjarjarantlr4.runtime.ANTLRStringStream;
import groovyjarjarantlr4.runtime.RecognitionException;
import groovyjarjarantlr4.runtime.TokenStream;
import groovyjarjarantlr4.runtime.tree.CommonTreeNodeStream;
import groovyjarjarantlr4.runtime.tree.Tree;
import groovyjarjarantlr4.runtime.tree.TreeAdaptor;
import groovyjarjarantlr4.runtime.tree.TreeNodeStream;
import groovyjarjarantlr4.runtime.tree.TreeVisitor;
import groovyjarjarantlr4.runtime.tree.TreeVisitorAction;
import groovyjarjarantlr4.runtime.tree.TreeWizard;
import groovyjarjarantlr4.v4.Tool;
import groovyjarjarantlr4.v4.automata.ParserATNFactory;
import groovyjarjarantlr4.v4.misc.CharSupport;
import groovyjarjarantlr4.v4.misc.OrderedHashMap;
import groovyjarjarantlr4.v4.misc.Utils;
import groovyjarjarantlr4.v4.parse.ANTLRParser;
import groovyjarjarantlr4.v4.parse.GrammarASTAdaptor;
import groovyjarjarantlr4.v4.parse.GrammarTreeVisitor;
import groovyjarjarantlr4.v4.parse.TokenVocabParser;
import groovyjarjarantlr4.v4.runtime.CharStream;
import groovyjarjarantlr4.v4.runtime.LexerInterpreter;
import groovyjarjarantlr4.v4.runtime.ParserInterpreter;
import groovyjarjarantlr4.v4.runtime.Vocabulary;
import groovyjarjarantlr4.v4.runtime.VocabularyImpl;
import groovyjarjarantlr4.v4.runtime.atn.ATN;
import groovyjarjarantlr4.v4.runtime.atn.ATNDeserializer;
import groovyjarjarantlr4.v4.runtime.atn.ATNSerializer;
import groovyjarjarantlr4.v4.runtime.atn.SemanticContext;
import groovyjarjarantlr4.v4.runtime.dfa.DFA;
import groovyjarjarantlr4.v4.runtime.misc.IntSet;
import groovyjarjarantlr4.v4.runtime.misc.Interval;
import groovyjarjarantlr4.v4.runtime.misc.IntervalSet;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import groovyjarjarantlr4.v4.runtime.misc.Tuple;
import groovyjarjarantlr4.v4.runtime.misc.Tuple2;
import groovyjarjarantlr4.v4.tool.ANTLRToolListener;
import groovyjarjarantlr4.v4.tool.Attribute;
import groovyjarjarantlr4.v4.tool.AttributeDict;
import groovyjarjarantlr4.v4.tool.AttributeResolver;
import groovyjarjarantlr4.v4.tool.ErrorManager;
import groovyjarjarantlr4.v4.tool.ErrorType;
import groovyjarjarantlr4.v4.tool.GrammarParserInterpreter;
import groovyjarjarantlr4.v4.tool.LeftRecursiveRule;
import groovyjarjarantlr4.v4.tool.LexerGrammar;
import groovyjarjarantlr4.v4.tool.Rule;
import groovyjarjarantlr4.v4.tool.ast.ActionAST;
import groovyjarjarantlr4.v4.tool.ast.AltAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarASTWithOptions;
import groovyjarjarantlr4.v4.tool.ast.GrammarRootAST;
import groovyjarjarantlr4.v4.tool.ast.PredAST;
import groovyjarjarantlr4.v4.tool.ast.RuleAST;
import groovyjarjarantlr4.v4.tool.ast.TerminalAST;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Grammar
implements AttributeResolver {
    public static final String GRAMMAR_FROM_STRING_NAME = "<string>";
    public static final String INVALID_TOKEN_NAME = "<INVALID>";
    public static final String INVALID_RULE_NAME = "<invalid>";
    public static final Set<String> parserOptions = new HashSet<String>();
    public static final Set<String> lexerOptions;
    public static final Set<String> ruleOptions;
    public static final Set<String> ParserBlockOptions;
    public static final Set<String> LexerBlockOptions;
    public static final Set<String> ruleRefOptions;
    public static final Set<String> tokenOptions;
    public static final Set<String> actionOptions;
    public static final Set<String> semPredOptions;
    public static final Set<String> doNotCopyOptionsToLexer;
    public static final Map<String, AttributeDict> grammarAndLabelRefTypeToScope;
    public String name;
    public GrammarRootAST ast;
    @NotNull
    public final TokenStream tokenStream;
    @NotNull
    public final TokenStream originalTokenStream;
    public String text;
    public String fileName;
    public LexerGrammar implicitLexer;
    public Grammar originalGrammar;
    public Grammar parent;
    public List<Grammar> importedGrammars;
    public OrderedHashMap<String, Rule> rules = new OrderedHashMap();
    public List<Rule> indexToRule = new ArrayList<Rule>();
    public final Map<String, List<RuleAST>> contextASTs = new HashMap<String, List<RuleAST>>();
    int ruleNumber = 0;
    int stringLiteralRuleNumber = 0;
    public ATN atn;
    public Map<Integer, Interval> stateToGrammarRegionMap;
    public Map<Integer, DFA> decisionDFAs = new HashMap<Integer, DFA>();
    public List<IntervalSet[]> decisionLOOK;
    @NotNull
    public final Tool tool;
    int maxTokenType = 0;
    public final Map<String, Integer> tokenNameToTypeMap = new LinkedHashMap<String, Integer>();
    public final Map<String, Integer> stringLiteralToTypeMap = new LinkedHashMap<String, Integer>();
    public final List<String> typeToStringLiteralList = new ArrayList<String>();
    public final List<String> typeToTokenList = new ArrayList<String>();
    int maxChannelType = 1;
    public final Map<String, Integer> channelNameToValueMap = new LinkedHashMap<String, Integer>();
    public final List<String> channelValueToNameList = new ArrayList<String>();
    public Map<String, ActionAST> namedActions = new HashMap<String, ActionAST>();
    public LinkedHashMap<ActionAST, Integer> lexerActions = new LinkedHashMap();
    public LinkedHashMap<PredAST, Integer> sempreds = new LinkedHashMap();
    public LinkedHashMap<Integer, PredAST> indexToPredMap;
    public static final String AUTO_GENERATED_TOKEN_NAME_PREFIX = "T__";

    public Grammar(Tool tool, @NotNull GrammarRootAST ast) {
        if (ast == null) {
            throw new NullPointerException("ast");
        }
        if (ast.tokenStream == null) {
            throw new IllegalArgumentException("ast must have a token stream");
        }
        this.tool = tool;
        this.ast = ast;
        this.name = ast.getChild(0).getText();
        this.originalTokenStream = this.tokenStream = ast.tokenStream;
        this.initTokenSymbolTables();
    }

    public Grammar(String grammarText) throws RecognitionException {
        this(GRAMMAR_FROM_STRING_NAME, grammarText, null);
    }

    public Grammar(String grammarText, LexerGrammar tokenVocabSource) throws RecognitionException {
        this(GRAMMAR_FROM_STRING_NAME, grammarText, tokenVocabSource, null);
    }

    public Grammar(String grammarText, ANTLRToolListener listener) throws RecognitionException {
        this(GRAMMAR_FROM_STRING_NAME, grammarText, listener);
    }

    public Grammar(String fileName, String grammarText) throws RecognitionException {
        this(fileName, grammarText, null);
    }

    public Grammar(String fileName, String grammarText, @Nullable ANTLRToolListener listener) throws RecognitionException {
        this(fileName, grammarText, null, listener);
    }

    public Grammar(String fileName, String grammarText, Grammar tokenVocabSource, @Nullable ANTLRToolListener listener) throws RecognitionException {
        this.text = grammarText;
        this.fileName = fileName;
        this.tool = new Tool();
        this.tool.addListener(listener);
        ANTLRStringStream in = new ANTLRStringStream(grammarText);
        in.name = fileName;
        this.ast = this.tool.parse(fileName, in);
        if (this.ast == null) {
            throw new UnsupportedOperationException();
        }
        if (this.ast.tokenStream == null) {
            throw new IllegalStateException("expected ast to have a token stream");
        }
        this.originalTokenStream = this.tokenStream = this.ast.tokenStream;
        final Grammar thiz = this;
        TreeVisitor v = new TreeVisitor(new GrammarASTAdaptor());
        v.visit(this.ast, new TreeVisitorAction(){

            @Override
            public Object pre(Object t) {
                ((GrammarAST)t).g = thiz;
                return t;
            }

            @Override
            public Object post(Object t) {
                return t;
            }
        });
        this.initTokenSymbolTables();
        if (tokenVocabSource != null) {
            this.importVocab(tokenVocabSource);
        }
        this.tool.process(this, false);
    }

    protected void initTokenSymbolTables() {
        this.tokenNameToTypeMap.put("EOF", -1);
        this.typeToTokenList.add(null);
    }

    public void loadImportedGrammars() {
        if (this.ast == null) {
            return;
        }
        GrammarAST i = (GrammarAST)this.ast.getFirstChildWithType(29);
        if (i == null) {
            return;
        }
        HashSet<String> visited = new HashSet<String>();
        visited.add(this.name);
        this.importedGrammars = new ArrayList<Grammar>();
        for (Object object : i.getChildren()) {
            Grammar g;
            GrammarAST t = (GrammarAST)object;
            String importedGrammarName = null;
            if (t.getType() == 10) {
                t = (GrammarAST)t.getChild(1);
                importedGrammarName = t.getText();
            } else if (t.getType() == 28) {
                importedGrammarName = t.getText();
            }
            if (visited.contains(importedGrammarName)) continue;
            try {
                g = this.tool.loadImportedGrammar(this, t);
            }
            catch (IOException ioe) {
                this.tool.errMgr.grammarError(ErrorType.ERROR_READING_IMPORTED_GRAMMAR, importedGrammarName, t.getToken(), importedGrammarName, this.name);
                continue;
            }
            if (g == null) continue;
            g.parent = this;
            this.importedGrammars.add(g);
            g.loadImportedGrammars();
        }
    }

    public void defineAction(GrammarAST atAST) {
        if (atAST.getChildCount() == 2) {
            String name = atAST.getChild(0).getText();
            this.namedActions.put(name, (ActionAST)atAST.getChild(1));
        } else {
            String gtype;
            String scope = atAST.getChild(0).getText();
            if (scope.equals(gtype = this.getTypeString()) || scope.equals("parser") && gtype.equals("combined")) {
                String name = atAST.getChild(1).getText();
                this.namedActions.put(name, (ActionAST)atAST.getChild(2));
            }
        }
    }

    public boolean defineRule(@NotNull Rule r) {
        if (this.rules.get(r.name) != null) {
            return false;
        }
        this.rules.put(r.name, r);
        r.index = this.ruleNumber++;
        this.indexToRule.add(r);
        return true;
    }

    public boolean undefineRule(@NotNull Rule r) {
        if (r.index < 0 || r.index >= this.indexToRule.size() || this.indexToRule.get(r.index) != r) {
            return false;
        }
        assert (this.rules.get(r.name) == r);
        this.rules.remove(r.name);
        this.indexToRule.remove(r.index);
        for (int i = r.index; i < this.indexToRule.size(); ++i) {
            assert (this.indexToRule.get((int)i).index == i + 1);
            --this.indexToRule.get((int)i).index;
        }
        --this.ruleNumber;
        return true;
    }

    public Rule getRule(String name) {
        Rule r = (Rule)this.rules.get(name);
        if (r != null) {
            return r;
        }
        return null;
    }

    public ATN getATN() {
        if (this.atn == null) {
            ParserATNFactory factory = new ParserATNFactory(this);
            this.atn = factory.createATN();
        }
        return this.atn;
    }

    public Rule getRule(int index) {
        return this.indexToRule.get(index);
    }

    public Rule getRule(String grammarName, String ruleName) {
        if (grammarName != null) {
            Grammar g = this.getImportedGrammar(grammarName);
            if (g == null) {
                return null;
            }
            return (Rule)g.rules.get(ruleName);
        }
        return this.getRule(ruleName);
    }

    protected String getBaseContextName(String ruleName) {
        Rule referencedRule = (Rule)this.rules.get(ruleName);
        if (referencedRule != null) {
            ruleName = referencedRule.getBaseContext();
        }
        return ruleName;
    }

    public List<AltAST> getUnlabeledAlternatives(RuleAST ast) throws RecognitionException {
        AltLabelVisitor visitor = new AltLabelVisitor(new CommonTreeNodeStream(new GrammarASTAdaptor(), ast));
        visitor.rule();
        return visitor.getUnlabeledAlternatives();
    }

    public Map<String, List<Tuple2<Integer, AltAST>>> getLabeledAlternatives(RuleAST ast) throws RecognitionException {
        AltLabelVisitor visitor = new AltLabelVisitor(new CommonTreeNodeStream(new GrammarASTAdaptor(), ast));
        visitor.rule();
        return visitor.getLabeledAlternatives();
    }

    public List<Grammar> getAllImportedGrammars() {
        if (this.importedGrammars == null) {
            return null;
        }
        LinkedHashMap<String, Grammar> delegates = new LinkedHashMap<String, Grammar>();
        for (Grammar d : this.importedGrammars) {
            delegates.put(d.fileName, d);
            List<Grammar> ds = d.getAllImportedGrammars();
            if (ds == null) continue;
            for (Grammar imported : ds) {
                delegates.put(imported.fileName, imported);
            }
        }
        return new ArrayList<Grammar>(delegates.values());
    }

    public List<Grammar> getImportedGrammars() {
        return this.importedGrammars;
    }

    public LexerGrammar getImplicitLexer() {
        return this.implicitLexer;
    }

    public static Grammar load(String fileName) {
        Tool antlr = new Tool();
        return antlr.loadGrammar(fileName);
    }

    public List<Grammar> getGrammarAncestors() {
        Grammar root = this.getOutermostGrammar();
        if (this == root) {
            return null;
        }
        ArrayList<Grammar> grammars = new ArrayList<Grammar>();
        Grammar p = this.parent;
        while (p != null) {
            grammars.add(0, p);
            p = p.parent;
        }
        return grammars;
    }

    public Grammar getOutermostGrammar() {
        if (this.parent == null) {
            return this;
        }
        return this.parent.getOutermostGrammar();
    }

    public boolean isAbstract() {
        return Boolean.parseBoolean(this.getOptionString("abstract"));
    }

    public String getRecognizerName() {
        String suffix = "";
        List<Grammar> grammarsFromRootToMe = this.getOutermostGrammar().getGrammarAncestors();
        String qualifiedName = this.name;
        if (grammarsFromRootToMe != null) {
            StringBuilder buf = new StringBuilder();
            for (Grammar g : grammarsFromRootToMe) {
                buf.append(g.name);
                buf.append('_');
            }
            if (this.isAbstract()) {
                buf.append("Abstract");
            }
            buf.append(this.name);
            qualifiedName = buf.toString();
        } else if (this.isAbstract()) {
            qualifiedName = "Abstract" + this.name;
        }
        if (this.isCombined() || this.isLexer() && this.implicitLexer != null) {
            suffix = Grammar.getGrammarTypeToFileNameSuffix(this.getType());
        }
        return qualifiedName + suffix;
    }

    public String getStringLiteralLexerRuleName(String lit) {
        return AUTO_GENERATED_TOKEN_NAME_PREFIX + this.stringLiteralRuleNumber++;
    }

    public Grammar getImportedGrammar(String name) {
        for (Grammar g : this.importedGrammars) {
            if (!g.name.equals(name)) continue;
            return g;
        }
        return null;
    }

    public int getTokenType(String token) {
        Integer I = token.charAt(0) == '\'' ? this.stringLiteralToTypeMap.get(token) : this.tokenNameToTypeMap.get(token);
        int i = I != null ? I : 0;
        return i;
    }

    public String getTokenName(String literal) {
        Grammar grammar = this;
        while (grammar != null) {
            if (grammar.stringLiteralToTypeMap.containsKey(literal)) {
                return grammar.getTokenName(grammar.stringLiteralToTypeMap.get(literal));
            }
            grammar = grammar.parent;
        }
        return null;
    }

    public String getTokenDisplayName(int ttype) {
        if (this.isLexer() && ttype >= 0 && ttype <= 0x10FFFF) {
            return CharSupport.getANTLRCharLiteralForChar(ttype);
        }
        if (ttype == -1) {
            return "EOF";
        }
        if (ttype == 0) {
            return INVALID_TOKEN_NAME;
        }
        if (ttype >= 0 && ttype < this.typeToStringLiteralList.size() && this.typeToStringLiteralList.get(ttype) != null) {
            return this.typeToStringLiteralList.get(ttype);
        }
        if (ttype >= 0 && ttype < this.typeToTokenList.size() && this.typeToTokenList.get(ttype) != null) {
            return this.typeToTokenList.get(ttype);
        }
        return String.valueOf(ttype);
    }

    @NotNull
    public String getTokenName(int ttype) {
        if (this.isLexer() && ttype >= 0 && ttype <= 0x10FFFF) {
            return CharSupport.getANTLRCharLiteralForChar(ttype);
        }
        if (ttype == -1) {
            return "EOF";
        }
        if (ttype >= 0 && ttype < this.typeToTokenList.size() && this.typeToTokenList.get(ttype) != null) {
            return this.typeToTokenList.get(ttype);
        }
        return INVALID_TOKEN_NAME;
    }

    public int getChannelValue(String channel) {
        Integer I = this.channelNameToValueMap.get(channel);
        int i = I != null ? I : -1;
        return i;
    }

    public String[] getRuleNames() {
        Object[] result = new String[this.rules.size()];
        Arrays.fill(result, INVALID_RULE_NAME);
        for (Rule rule : this.rules.values()) {
            result[rule.index] = rule.name;
        }
        return result;
    }

    public String[] getTokenNames() {
        int numTokens = this.getMaxTokenType();
        String[] tokenNames = new String[numTokens + 1];
        for (int i = 0; i < tokenNames.length; ++i) {
            tokenNames[i] = this.getTokenName(i);
        }
        return tokenNames;
    }

    public String[] getTokenDisplayNames() {
        int numTokens = this.getMaxTokenType();
        String[] tokenNames = new String[numTokens + 1];
        for (int i = 0; i < tokenNames.length; ++i) {
            tokenNames[i] = this.getTokenDisplayName(i);
        }
        return tokenNames;
    }

    @NotNull
    public String[] getTokenLiteralNames() {
        int numTokens = this.getMaxTokenType();
        String[] literalNames = new String[numTokens + 1];
        for (int i = 0; i < Math.min(literalNames.length, this.typeToStringLiteralList.size()); ++i) {
            literalNames[i] = this.typeToStringLiteralList.get(i);
        }
        for (Map.Entry<String, Integer> entry : this.stringLiteralToTypeMap.entrySet()) {
            if (entry.getValue() < 0 || entry.getValue() >= literalNames.length || literalNames[entry.getValue()] != null) continue;
            literalNames[entry.getValue().intValue()] = entry.getKey();
        }
        return literalNames;
    }

    @NotNull
    public String[] getTokenSymbolicNames() {
        int numTokens = this.getMaxTokenType();
        String[] symbolicNames = new String[numTokens + 1];
        for (int i = 0; i < Math.min(symbolicNames.length, this.typeToTokenList.size()); ++i) {
            if (this.typeToTokenList.get(i) == null || this.typeToTokenList.get(i).startsWith(AUTO_GENERATED_TOKEN_NAME_PREFIX)) continue;
            symbolicNames[i] = this.typeToTokenList.get(i);
        }
        return symbolicNames;
    }

    @NotNull
    public Vocabulary getVocabulary() {
        return new VocabularyImpl(this.getTokenLiteralNames(), this.getTokenSymbolicNames());
    }

    public String getSemanticContextDisplayString(SemanticContext semctx) {
        if (semctx instanceof SemanticContext.Predicate) {
            return this.getPredicateDisplayString((SemanticContext.Predicate)semctx);
        }
        if (semctx instanceof SemanticContext.AND) {
            SemanticContext.AND and = (SemanticContext.AND)semctx;
            return this.joinPredicateOperands(and, " and ");
        }
        if (semctx instanceof SemanticContext.OR) {
            SemanticContext.OR or = (SemanticContext.OR)semctx;
            return this.joinPredicateOperands(or, " or ");
        }
        return semctx.toString();
    }

    public String joinPredicateOperands(SemanticContext.Operator op, String separator) {
        StringBuilder buf = new StringBuilder();
        for (SemanticContext operand : op.getOperands()) {
            if (buf.length() > 0) {
                buf.append(separator);
            }
            buf.append(this.getSemanticContextDisplayString(operand));
        }
        return buf.toString();
    }

    public LinkedHashMap<Integer, PredAST> getIndexToPredicateMap() {
        LinkedHashMap<Integer, PredAST> indexToPredMap = new LinkedHashMap<Integer, PredAST>();
        for (Rule r : this.rules.values()) {
            for (ActionAST a : r.actions) {
                if (!(a instanceof PredAST)) continue;
                PredAST p = (PredAST)a;
                indexToPredMap.put(this.sempreds.get(p), p);
            }
        }
        return indexToPredMap;
    }

    public String getPredicateDisplayString(SemanticContext.Predicate pred) {
        if (this.indexToPredMap == null) {
            this.indexToPredMap = this.getIndexToPredicateMap();
        }
        ActionAST actionAST = this.indexToPredMap.get(pred.predIndex);
        return actionAST.getText();
    }

    public int getMaxCharValue() {
        return 0x10FFFF;
    }

    public IntSet getTokenTypes() {
        if (this.isLexer()) {
            return this.getAllCharValues();
        }
        return IntervalSet.of(1, this.getMaxTokenType());
    }

    public IntSet getAllCharValues() {
        return IntervalSet.of(0, this.getMaxCharValue());
    }

    public int getMaxTokenType() {
        return this.typeToTokenList.size() - 1;
    }

    public int getNewTokenType() {
        ++this.maxTokenType;
        return this.maxTokenType;
    }

    public int getNewChannelNumber() {
        ++this.maxChannelType;
        return this.maxChannelType;
    }

    public void importTokensFromTokensFile() {
        String vocab = this.getOptionString("tokenVocab");
        if (vocab != null) {
            TokenVocabParser vparser = new TokenVocabParser(this);
            Map<String, Integer> tokens = vparser.load();
            this.tool.log("grammar", "tokens=" + tokens);
            for (String t : tokens.keySet()) {
                if (t.charAt(0) == '\'') {
                    this.defineStringLiteral(t, tokens.get(t));
                    continue;
                }
                this.defineTokenName(t, tokens.get(t));
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    public void importVocab(Grammar importG) {
        void var3_12;
        void var3_10;
        for (String string : importG.tokenNameToTypeMap.keySet()) {
            this.defineTokenName(string, importG.tokenNameToTypeMap.get(string));
        }
        for (String string : importG.stringLiteralToTypeMap.keySet()) {
            this.defineStringLiteral(string, importG.stringLiteralToTypeMap.get(string));
        }
        for (Map.Entry entry : importG.channelNameToValueMap.entrySet()) {
            this.defineChannelName((String)entry.getKey(), (Integer)entry.getValue());
        }
        int max = Math.max(this.typeToTokenList.size(), importG.typeToTokenList.size());
        Utils.setSize(this.typeToTokenList, max);
        boolean bl = false;
        while (var3_10 < importG.typeToTokenList.size()) {
            this.maxTokenType = Math.max(this.maxTokenType, (int)var3_10);
            this.typeToTokenList.set((int)var3_10, importG.typeToTokenList.get((int)var3_10));
            ++var3_10;
        }
        max = Math.max(this.channelValueToNameList.size(), importG.channelValueToNameList.size());
        Utils.setSize(this.channelValueToNameList, max);
        boolean bl2 = false;
        while (var3_12 < importG.channelValueToNameList.size()) {
            this.maxChannelType = Math.max(this.maxChannelType, (int)var3_12);
            this.channelValueToNameList.set((int)var3_12, importG.channelValueToNameList.get((int)var3_12));
            ++var3_12;
        }
    }

    public int defineTokenName(String name) {
        Integer prev = this.tokenNameToTypeMap.get(name);
        if (prev == null) {
            return this.defineTokenName(name, this.getNewTokenType());
        }
        return prev;
    }

    public int defineTokenName(String name, int ttype) {
        Integer prev = this.tokenNameToTypeMap.get(name);
        if (prev != null) {
            return prev;
        }
        this.tokenNameToTypeMap.put(name, ttype);
        this.setTokenForType(ttype, name);
        this.maxTokenType = Math.max(this.maxTokenType, ttype);
        return ttype;
    }

    public int defineStringLiteral(String lit) {
        if (this.stringLiteralToTypeMap.containsKey(lit)) {
            return this.stringLiteralToTypeMap.get(lit);
        }
        return this.defineStringLiteral(lit, this.getNewTokenType());
    }

    public int defineStringLiteral(String lit, int ttype) {
        if (!this.stringLiteralToTypeMap.containsKey(lit)) {
            this.stringLiteralToTypeMap.put(lit, ttype);
            if (ttype >= this.typeToStringLiteralList.size()) {
                Utils.setSize(this.typeToStringLiteralList, ttype + 1);
            }
            this.typeToStringLiteralList.set(ttype, lit);
            this.setTokenForType(ttype, lit);
            return ttype;
        }
        return 0;
    }

    public int defineTokenAlias(String name, String lit) {
        int ttype = this.defineTokenName(name);
        this.stringLiteralToTypeMap.put(lit, ttype);
        this.setTokenForType(ttype, name);
        return ttype;
    }

    public void setTokenForType(int ttype, String text) {
        String prevToken;
        if (ttype == -1) {
            return;
        }
        if (ttype >= this.typeToTokenList.size()) {
            Utils.setSize(this.typeToTokenList, ttype + 1);
        }
        if ((prevToken = this.typeToTokenList.get(ttype)) == null || prevToken.charAt(0) == '\'') {
            this.typeToTokenList.set(ttype, text);
        }
    }

    public int defineChannelName(String name) {
        Integer prev = this.channelNameToValueMap.get(name);
        if (prev == null) {
            return this.defineChannelName(name, this.getNewChannelNumber());
        }
        return prev;
    }

    public int defineChannelName(String name, int value) {
        Integer prev = this.channelNameToValueMap.get(name);
        if (prev != null) {
            return prev;
        }
        this.channelNameToValueMap.put(name, value);
        this.setChannelNameForValue(value, name);
        this.maxChannelType = Math.max(this.maxChannelType, value);
        return value;
    }

    public void setChannelNameForValue(int channelValue, String name) {
        String prevChannel;
        if (channelValue >= this.channelValueToNameList.size()) {
            Utils.setSize(this.channelValueToNameList, channelValue + 1);
        }
        if ((prevChannel = this.channelValueToNameList.get(channelValue)) == null) {
            this.channelValueToNameList.set(channelValue, name);
        }
    }

    @Override
    public Attribute resolveToAttribute(String x, ActionAST node) {
        return null;
    }

    @Override
    public Attribute resolveToAttribute(String x, String y, ActionAST node) {
        return null;
    }

    @Override
    public boolean resolvesToLabel(String x, ActionAST node) {
        return false;
    }

    @Override
    public boolean resolvesToListLabel(String x, ActionAST node) {
        return false;
    }

    @Override
    public boolean resolvesToToken(String x, ActionAST node) {
        return false;
    }

    @Override
    public boolean resolvesToAttributeDict(String x, ActionAST node) {
        return false;
    }

    public String getDefaultActionScope() {
        switch (this.getType()) {
            case 31: {
                return "lexer";
            }
            case 44: 
            case 81: {
                return "parser";
            }
        }
        return null;
    }

    public int getType() {
        if (this.ast != null) {
            return this.ast.grammarType;
        }
        return 0;
    }

    public TokenStream getTokenStream() {
        if (this.ast != null) {
            return this.ast.tokenStream;
        }
        return null;
    }

    public boolean isLexer() {
        return this.getType() == 31;
    }

    public boolean isParser() {
        return this.getType() == 44;
    }

    public boolean isCombined() {
        return this.getType() == 81;
    }

    public static boolean isTokenName(String id) {
        return Character.isUpperCase(id.charAt(0));
    }

    public String getTypeString() {
        if (this.ast == null) {
            return null;
        }
        return ANTLRParser.tokenNames[this.getType()].toLowerCase();
    }

    public static String getGrammarTypeToFileNameSuffix(int type) {
        switch (type) {
            case 31: {
                return "Lexer";
            }
            case 44: {
                return "Parser";
            }
            case 81: {
                return "Parser";
            }
        }
        return INVALID_RULE_NAME;
    }

    public String getOptionString(String key) {
        return this.ast.getOptionString(key);
    }

    public static void setNodeOptions(GrammarAST node, GrammarAST options) {
        if (options == null) {
            return;
        }
        GrammarASTWithOptions t = (GrammarASTWithOptions)node;
        if (t.getChildCount() == 0 || options.getChildCount() == 0) {
            return;
        }
        for (Object object : options.getChildren()) {
            GrammarAST c = (GrammarAST)object;
            if (c.getType() == 10) {
                t.setOption(c.getChild(0).getText(), (GrammarAST)c.getChild(1));
                continue;
            }
            t.setOption(c.getText(), null);
        }
    }

    public static List<Tuple2<GrammarAST, GrammarAST>> getStringLiteralAliasesFromLexerRules(GrammarRootAST ast) {
        String[] patterns = new String[]{"(RULE %name:TOKEN_REF (BLOCK (ALT %lit:STRING_LITERAL)))", "(RULE %name:TOKEN_REF (BLOCK (ALT %lit:STRING_LITERAL ACTION)))", "(RULE %name:TOKEN_REF (BLOCK (ALT %lit:STRING_LITERAL SEMPRED)))", "(RULE %name:TOKEN_REF (BLOCK (LEXER_ALT_ACTION (ALT %lit:STRING_LITERAL) .)))", "(RULE %name:TOKEN_REF (BLOCK (LEXER_ALT_ACTION (ALT %lit:STRING_LITERAL) . .)))", "(RULE %name:TOKEN_REF (BLOCK (LEXER_ALT_ACTION (ALT %lit:STRING_LITERAL) (LEXER_ACTION_CALL . .))))", "(RULE %name:TOKEN_REF (BLOCK (LEXER_ALT_ACTION (ALT %lit:STRING_LITERAL) . (LEXER_ACTION_CALL . .))))", "(RULE %name:TOKEN_REF (BLOCK (LEXER_ALT_ACTION (ALT %lit:STRING_LITERAL) (LEXER_ACTION_CALL . .) .)))"};
        GrammarASTAdaptor adaptor = new GrammarASTAdaptor(ast.token.getInputStream());
        TreeWizard wiz = new TreeWizard((TreeAdaptor)adaptor, ANTLRParser.tokenNames);
        ArrayList<Tuple2<GrammarAST, GrammarAST>> lexerRuleToStringLiteral = new ArrayList<Tuple2<GrammarAST, GrammarAST>>();
        List<GrammarAST> ruleNodes = ast.getNodesWithType(94);
        if (ruleNodes == null || ruleNodes.isEmpty()) {
            return null;
        }
        for (GrammarAST r : ruleNodes) {
            String pattern;
            boolean isLitRule;
            Tree name = r.getChild(0);
            if (name.getType() != 66) continue;
            String[] arr$ = patterns;
            int len$ = arr$.length;
            for (int i$ = 0; i$ < len$ && !(isLitRule = Grammar.defAlias(r, pattern = arr$[i$], wiz, lexerRuleToStringLiteral)); ++i$) {
            }
        }
        return lexerRuleToStringLiteral;
    }

    protected static boolean defAlias(GrammarAST r, String pattern, TreeWizard wiz, List<Tuple2<GrammarAST, GrammarAST>> lexerRuleToStringLiteral) {
        HashMap<String, Object> nodes = new HashMap<String, Object>();
        if (wiz.parse(r, pattern, nodes)) {
            GrammarAST litNode = (GrammarAST)nodes.get("lit");
            GrammarAST nameNode = (GrammarAST)nodes.get("name");
            Tuple2<GrammarAST, GrammarAST> pair = Tuple.create(nameNode, litNode);
            lexerRuleToStringLiteral.add(pair);
            return true;
        }
        return false;
    }

    public Set<String> getStringLiterals() {
        final LinkedHashSet<String> strings = new LinkedHashSet<String>();
        GrammarTreeVisitor collector = new GrammarTreeVisitor(){

            @Override
            public void stringRef(TerminalAST ref) {
                strings.add(ref.getText());
            }

            @Override
            public ErrorManager getErrorManager() {
                return Grammar.this.tool.errMgr;
            }
        };
        collector.visitGrammar(this.ast);
        return strings;
    }

    public void setLookaheadDFA(int decision, DFA lookaheadDFA) {
        this.decisionDFAs.put(decision, lookaheadDFA);
    }

    public static Map<Integer, Interval> getStateToGrammarRegionMap(GrammarRootAST ast, IntervalSet grammarTokenTypes) {
        HashMap<Integer, Interval> stateToGrammarRegionMap = new HashMap<Integer, Interval>();
        if (ast == null) {
            return stateToGrammarRegionMap;
        }
        List<GrammarAST> nodes = ast.getNodesWithType(grammarTokenTypes);
        for (GrammarAST n : nodes) {
            String ruleName;
            Rule r;
            if (n.atnState == null) continue;
            Interval tokenRegion = Interval.of(n.getTokenStartIndex(), n.getTokenStopIndex());
            Tree ruleNode = null;
            switch (n.getType()) {
                case 94: {
                    ruleNode = n;
                    break;
                }
                case 78: 
                case 80: {
                    ruleNode = n.getAncestor(94);
                }
            }
            if (ruleNode instanceof RuleAST && (r = ast.g.getRule(ruleName = ((RuleAST)ruleNode).getRuleName())) instanceof LeftRecursiveRule) {
                RuleAST originalAST = ((LeftRecursiveRule)r).getOriginalAST();
                tokenRegion = Interval.of(originalAST.getTokenStartIndex(), originalAST.getTokenStopIndex());
            }
            stateToGrammarRegionMap.put(n.atnState.stateNumber, tokenRegion);
        }
        return stateToGrammarRegionMap;
    }

    public Interval getStateToGrammarRegion(int atnStateNumber) {
        if (this.stateToGrammarRegionMap == null) {
            this.stateToGrammarRegionMap = Grammar.getStateToGrammarRegionMap(this.ast, null);
        }
        if (this.stateToGrammarRegionMap == null) {
            return Interval.INVALID;
        }
        return this.stateToGrammarRegionMap.get(atnStateNumber);
    }

    public LexerInterpreter createLexerInterpreter(CharStream input) {
        if (this.isParser()) {
            throw new IllegalStateException("A lexer interpreter can only be created for a lexer or combined grammar.");
        }
        if (this.isCombined()) {
            return this.implicitLexer.createLexerInterpreter(input);
        }
        char[] serializedAtn = ATNSerializer.getSerializedAsChars(this.atn, Arrays.asList(this.getRuleNames()));
        ATN deserialized = new ATNDeserializer().deserialize(serializedAtn);
        ArrayList<String> allChannels = new ArrayList<String>();
        allChannels.add("DEFAULT_TOKEN_CHANNEL");
        allChannels.add("HIDDEN");
        allChannels.addAll(this.channelValueToNameList);
        return new LexerInterpreter(this.fileName, this.getVocabulary(), Arrays.asList(this.getRuleNames()), allChannels, ((LexerGrammar)this).modes.keySet(), deserialized, input);
    }

    public GrammarParserInterpreter createGrammarParserInterpreter(groovyjarjarantlr4.v4.runtime.TokenStream tokenStream) {
        if (this.isLexer()) {
            throw new IllegalStateException("A parser interpreter can only be created for a parser or combined grammar.");
        }
        char[] serializedAtn = ATNSerializer.getSerializedAsChars(this.atn, Arrays.asList(this.getRuleNames()));
        ATN deserialized = new ATNDeserializer().deserialize(serializedAtn);
        return new GrammarParserInterpreter(this, deserialized, tokenStream);
    }

    public ParserInterpreter createParserInterpreter(groovyjarjarantlr4.v4.runtime.TokenStream tokenStream) {
        if (this.isLexer()) {
            throw new IllegalStateException("A parser interpreter can only be created for a parser or combined grammar.");
        }
        char[] serializedAtn = ATNSerializer.getSerializedAsChars(this.atn, Arrays.asList(this.getRuleNames()));
        ATN deserialized = new ATNDeserializer().deserialize(serializedAtn);
        return new ParserInterpreter(this.fileName, this.getVocabulary(), Arrays.asList(this.getRuleNames()), deserialized, tokenStream);
    }

    static {
        parserOptions.add("superClass");
        parserOptions.add("contextSuperClass");
        parserOptions.add("TokenLabelType");
        parserOptions.add("abstract");
        parserOptions.add("tokenVocab");
        parserOptions.add("language");
        parserOptions.add("accessLevel");
        parserOptions.add("exportMacro");
        lexerOptions = parserOptions;
        ruleOptions = new HashSet<String>();
        ruleOptions.add("baseContext");
        ParserBlockOptions = new HashSet<String>();
        ParserBlockOptions.add("sll");
        LexerBlockOptions = new HashSet<String>();
        ruleRefOptions = new HashSet<String>();
        ruleRefOptions.add("p");
        ruleRefOptions.add("tokenIndex");
        tokenOptions = new HashSet<String>();
        tokenOptions.add("assoc");
        tokenOptions.add("tokenIndex");
        actionOptions = new HashSet<String>();
        semPredOptions = new HashSet<String>();
        semPredOptions.add("p");
        semPredOptions.add("fail");
        doNotCopyOptionsToLexer = new HashSet<String>();
        doNotCopyOptionsToLexer.add("superClass");
        doNotCopyOptionsToLexer.add("TokenLabelType");
        doNotCopyOptionsToLexer.add("abstract");
        doNotCopyOptionsToLexer.add("tokenVocab");
        grammarAndLabelRefTypeToScope = new HashMap<String, AttributeDict>();
        grammarAndLabelRefTypeToScope.put("parser:RULE_LABEL", Rule.predefinedRulePropertiesDict);
        grammarAndLabelRefTypeToScope.put("parser:TOKEN_LABEL", AttributeDict.predefinedTokenDict);
        grammarAndLabelRefTypeToScope.put("combined:RULE_LABEL", Rule.predefinedRulePropertiesDict);
        grammarAndLabelRefTypeToScope.put("combined:TOKEN_LABEL", AttributeDict.predefinedTokenDict);
    }

    protected static class AltLabelVisitor
    extends GrammarTreeVisitor {
        private final Map<String, List<Tuple2<Integer, AltAST>>> labeledAlternatives = new LinkedHashMap<String, List<Tuple2<Integer, AltAST>>>();
        private final List<AltAST> unlabeledAlternatives = new ArrayList<AltAST>();

        public AltLabelVisitor(TreeNodeStream input) {
            super(input);
        }

        public Map<String, List<Tuple2<Integer, AltAST>>> getLabeledAlternatives() {
            return this.labeledAlternatives;
        }

        public List<AltAST> getUnlabeledAlternatives() {
            return this.unlabeledAlternatives;
        }

        @Override
        public void discoverOuterAlt(AltAST alt) {
            if (alt.altLabel != null) {
                List<Tuple2<Integer, AltAST>> list = this.labeledAlternatives.get(alt.altLabel.getText());
                if (list == null) {
                    list = new ArrayList<Tuple2<Integer, AltAST>>();
                    this.labeledAlternatives.put(alt.altLabel.getText(), list);
                }
                list.add(Tuple.create(this.currentOuterAltNumber, alt));
            } else {
                this.unlabeledAlternatives.add(alt);
            }
        }
    }
}

