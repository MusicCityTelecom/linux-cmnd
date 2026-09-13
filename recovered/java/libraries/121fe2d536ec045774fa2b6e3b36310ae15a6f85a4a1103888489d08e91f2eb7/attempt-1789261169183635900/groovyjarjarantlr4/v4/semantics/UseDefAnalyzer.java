/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.semantics;

import groovyjarjarantlr4.runtime.ANTLRStringStream;
import groovyjarjarantlr4.runtime.CharStream;
import groovyjarjarantlr4.runtime.Token;
import groovyjarjarantlr4.v4.parse.ActionSplitter;
import groovyjarjarantlr4.v4.semantics.ActionSniffer;
import groovyjarjarantlr4.v4.semantics.BlankActionSplitterListener;
import groovyjarjarantlr4.v4.tool.Alternative;
import groovyjarjarantlr4.v4.tool.Grammar;
import groovyjarjarantlr4.v4.tool.LexerGrammar;
import groovyjarjarantlr4.v4.tool.Rule;
import groovyjarjarantlr4.v4.tool.ast.ActionAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UseDefAnalyzer {
    public static void trackTokenRuleRefsInActions(Grammar g) {
        for (Rule r : g.rules.values()) {
            for (int i = 1; i <= r.numberOfAlts; ++i) {
                Alternative alt = r.alt[i];
                for (ActionAST a : alt.actions) {
                    ActionSniffer sniffer = new ActionSniffer(g, r, alt, a, a.token);
                    sniffer.examineAction();
                }
            }
        }
    }

    public static boolean actionIsContextDependent(ActionAST actionAST) {
        ANTLRStringStream in = new ANTLRStringStream(actionAST.token.getText());
        in.setLine(actionAST.token.getLine());
        in.setCharPositionInLine(actionAST.token.getCharPositionInLine());
        final boolean[] dependent = new boolean[]{false};
        BlankActionSplitterListener listener = new BlankActionSplitterListener(){

            @Override
            public void nonLocalAttr(String expr, Token x, Token y) {
                dependent[0] = true;
            }

            @Override
            public void qualifiedAttr(String expr, Token x, Token y) {
                dependent[0] = true;
            }

            @Override
            public void setAttr(String expr, Token x, Token rhs) {
                dependent[0] = true;
            }

            @Override
            public void setExprAttribute(String expr) {
                dependent[0] = true;
            }

            @Override
            public void setNonLocalAttr(String expr, Token x, Token y, Token rhs) {
                dependent[0] = true;
            }

            @Override
            public void attr(String expr, Token x) {
                dependent[0] = true;
            }
        };
        ActionSplitter splitter = new ActionSplitter((CharStream)in, listener);
        splitter.getActionTokens();
        return dependent[0];
    }

    public static Map<Rule, Set<Rule>> getRuleDependencies(Grammar g) {
        return UseDefAnalyzer.getRuleDependencies(g, g.rules.values());
    }

    public static Map<Rule, Set<Rule>> getRuleDependencies(LexerGrammar g, String modeName) {
        return UseDefAnalyzer.getRuleDependencies((Grammar)g, (Collection)g.modes.get(modeName));
    }

    public static Map<Rule, Set<Rule>> getRuleDependencies(Grammar g, Collection<Rule> rules) {
        HashMap<Rule, Set<Rule>> dependencies = new HashMap<Rule, Set<Rule>>();
        for (Rule r : rules) {
            List<GrammarAST> tokenRefs = r.ast.getNodesWithType(66);
            for (GrammarAST tref : tokenRefs) {
                HashSet<Rule> calls = (HashSet<Rule>)dependencies.get(r);
                if (calls == null) {
                    calls = new HashSet<Rule>();
                    dependencies.put(r, calls);
                }
                calls.add(g.getRule(tref.getText()));
            }
        }
        return dependencies;
    }
}

