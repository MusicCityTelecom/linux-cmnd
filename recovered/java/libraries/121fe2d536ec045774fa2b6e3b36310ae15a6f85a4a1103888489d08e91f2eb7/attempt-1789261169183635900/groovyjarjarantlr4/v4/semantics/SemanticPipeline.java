/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.semantics;

import groovyjarjarantlr4.v4.analysis.LeftFactoringRuleTransformer;
import groovyjarjarantlr4.v4.analysis.LeftRecursiveRuleTransformer;
import groovyjarjarantlr4.v4.automata.LexerATNFactory;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.misc.Tuple2;
import groovyjarjarantlr4.v4.semantics.AttributeChecks;
import groovyjarjarantlr4.v4.semantics.BasicSemanticChecks;
import groovyjarjarantlr4.v4.semantics.RuleCollector;
import groovyjarjarantlr4.v4.semantics.SymbolChecks;
import groovyjarjarantlr4.v4.semantics.SymbolCollector;
import groovyjarjarantlr4.v4.semantics.UseDefAnalyzer;
import groovyjarjarantlr4.v4.tool.ErrorType;
import groovyjarjarantlr4.v4.tool.Grammar;
import groovyjarjarantlr4.v4.tool.LexerGrammar;
import groovyjarjarantlr4.v4.tool.Rule;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import groovyjarjarantlr4.v4.tool.ast.RuleAST;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class SemanticPipeline {
    public Grammar g;

    public SemanticPipeline(Grammar g) {
        this.g = g;
    }

    public void process() {
        if (this.g.ast == null) {
            return;
        }
        RuleCollector ruleCollector = new RuleCollector(this.g);
        ruleCollector.process(this.g.ast);
        for (Rule rule : ruleCollector.rules.values()) {
            List<RuleAST> list = this.g.contextASTs.get(rule.getBaseContext());
            if (list == null) {
                list = new ArrayList<RuleAST>();
                this.g.contextASTs.put(rule.getBaseContext(), list);
            }
            list.add((RuleAST)rule.ast.dupTree());
        }
        int prevErrors = this.g.tool.errMgr.getNumErrors();
        BasicSemanticChecks basics = new BasicSemanticChecks(this.g, ruleCollector);
        basics.process();
        if (this.g.tool.errMgr.getNumErrors() > prevErrors) {
            return;
        }
        prevErrors = this.g.tool.errMgr.getNumErrors();
        LeftRecursiveRuleTransformer lrtrans = new LeftRecursiveRuleTransformer(this.g.ast, ruleCollector.rules.values(), this.g);
        lrtrans.translateLeftRecursiveRules();
        if (this.g.tool.errMgr.getNumErrors() > prevErrors) {
            return;
        }
        LeftFactoringRuleTransformer lftrans = new LeftFactoringRuleTransformer(this.g.ast, ruleCollector.rules, this.g);
        lftrans.translateLeftFactoredRules();
        for (Rule r : ruleCollector.rules.values()) {
            this.g.defineRule(r);
        }
        SymbolCollector collector = new SymbolCollector(this.g);
        collector.process(this.g.ast);
        SymbolChecks symcheck = new SymbolChecks(this.g, collector);
        symcheck.process();
        for (GrammarAST a : collector.namedActions) {
            this.g.defineAction(a);
        }
        for (Rule r : this.g.rules.values()) {
            for (int i = 1; i <= r.numberOfAlts; ++i) {
                r.alt[i].ast.alt = r.alt[i];
            }
        }
        this.g.importTokensFromTokensFile();
        if (this.g.isLexer()) {
            this.assignLexerTokenTypes(this.g, collector.tokensDefs);
        } else {
            this.assignTokenTypes(this.g, collector.tokensDefs, collector.tokenIDRefs, collector.terminals);
        }
        symcheck.checkForModeConflicts(this.g);
        symcheck.checkForUnreachableTokens(this.g);
        this.assignChannelTypes(this.g, collector.channelDefs);
        symcheck.checkRuleArgs(this.g, collector.rulerefs);
        this.identifyStartRules(collector);
        symcheck.checkForQualifiedRuleIssues(this.g, collector.qualifiedRulerefs);
        if (this.g.tool.getNumErrors() > 0) {
            return;
        }
        AttributeChecks.checkAllAttributeExpressions(this.g);
        UseDefAnalyzer.trackTokenRuleRefsInActions(this.g);
    }

    void identifyStartRules(SymbolCollector collector) {
        for (GrammarAST ref : collector.rulerefs) {
            String ruleName = ref.getText();
            Rule r = this.g.getRule(ruleName);
            if (r == null) continue;
            r.isStartRule = false;
        }
    }

    void assignLexerTokenTypes(Grammar g, List<GrammarAST> tokensDefs) {
        Grammar G = g.getOutermostGrammar();
        for (GrammarAST def : tokensDefs) {
            if (!Grammar.isTokenName(def.getText())) continue;
            G.defineTokenName(def.getText());
        }
        for (Rule r : g.rules.values()) {
            if (r.isFragment() || this.hasTypeOrMoreCommand(r)) continue;
            G.defineTokenName(r.name);
        }
        List<Tuple2<GrammarAST, GrammarAST>> litAliases = Grammar.getStringLiteralAliasesFromLexerRules(g.ast);
        HashSet<String> conflictingLiterals = new HashSet<String>();
        if (litAliases != null) {
            for (Tuple2<GrammarAST, GrammarAST> pair : litAliases) {
                GrammarAST nameAST = pair.getItem1();
                GrammarAST litAST = pair.getItem2();
                if (!G.stringLiteralToTypeMap.containsKey(litAST.getText())) {
                    G.defineTokenAlias(nameAST.getText(), litAST.getText());
                    continue;
                }
                conflictingLiterals.add(litAST.getText());
            }
            for (String lit : conflictingLiterals) {
                Integer value = G.stringLiteralToTypeMap.remove(lit);
                if (value == null || value <= 0 || value >= G.typeToStringLiteralList.size() || !lit.equals(G.typeToStringLiteralList.get(value))) continue;
                G.typeToStringLiteralList.set(value, null);
            }
        }
    }

    boolean hasTypeOrMoreCommand(@NotNull Rule r) {
        RuleAST ast = r.ast;
        if (ast == null) {
            return false;
        }
        GrammarAST altActionAst = (GrammarAST)ast.getFirstDescendantWithType(87);
        if (altActionAst == null) {
            return false;
        }
        for (int i = 1; i < altActionAst.getChildCount(); ++i) {
            GrammarAST node = (GrammarAST)altActionAst.getChild(i);
            if (!(node.getType() == 86 ? "type".equals(node.getChild(0).getText()) : "more".equals(node.getText()))) continue;
            return true;
        }
        return false;
    }

    void assignTokenTypes(Grammar g, List<GrammarAST> tokensDefs, List<GrammarAST> tokenIDs, List<GrammarAST> terminals) {
        for (GrammarAST alias : tokensDefs) {
            if (g.getTokenType(alias.getText()) != 0) {
                g.tool.errMgr.grammarError(ErrorType.TOKEN_NAME_REASSIGNMENT, g.fileName, alias.token, alias.getText());
            }
            g.defineTokenName(alias.getText());
        }
        for (GrammarAST idAST : tokenIDs) {
            if (g.getTokenType(idAST.getText()) == 0) {
                g.tool.errMgr.grammarError(ErrorType.IMPLICIT_TOKEN_DEFINITION, g.fileName, idAST.token, idAST.getText());
            }
            g.defineTokenName(idAST.getText());
        }
        for (GrammarAST termAST : terminals) {
            if (termAST.getType() != 62 || g.getTokenType(termAST.getText()) != 0) continue;
            g.tool.errMgr.grammarError(ErrorType.IMPLICIT_STRING_DEFINITION, g.fileName, termAST.token, termAST.getText());
        }
        g.tool.log("semantics", "tokens=" + g.tokenNameToTypeMap);
        g.tool.log("semantics", "strings=" + g.stringLiteralToTypeMap);
    }

    void assignChannelTypes(Grammar g, List<GrammarAST> channelDefs) {
        Grammar outermost = g.getOutermostGrammar();
        for (GrammarAST channel : channelDefs) {
            String channelName = channel.getText();
            if (g.getTokenType(channelName) != 0) {
                g.tool.errMgr.grammarError(ErrorType.CHANNEL_CONFLICTS_WITH_TOKEN, g.fileName, channel.token, channelName);
            }
            if (LexerATNFactory.COMMON_CONSTANTS.containsKey(channelName)) {
                g.tool.errMgr.grammarError(ErrorType.CHANNEL_CONFLICTS_WITH_COMMON_CONSTANTS, g.fileName, channel.token, channelName);
            }
            if (outermost instanceof LexerGrammar) {
                LexerGrammar lexerGrammar = (LexerGrammar)outermost;
                if (lexerGrammar.modes.containsKey(channelName)) {
                    g.tool.errMgr.grammarError(ErrorType.CHANNEL_CONFLICTS_WITH_MODE, g.fileName, channel.token, channelName);
                }
            }
            outermost.defineChannelName(channel.getText());
        }
    }
}

