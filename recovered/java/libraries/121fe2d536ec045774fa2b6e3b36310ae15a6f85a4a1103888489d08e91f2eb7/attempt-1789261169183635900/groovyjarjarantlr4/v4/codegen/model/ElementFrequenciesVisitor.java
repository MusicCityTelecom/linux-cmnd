/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model;

import groovyjarjarantlr4.runtime.tree.TreeNodeStream;
import groovyjarjarantlr4.v4.codegen.model.RuleFunction;
import groovyjarjarantlr4.v4.misc.FrequencySet;
import groovyjarjarantlr4.v4.misc.MutableInt;
import groovyjarjarantlr4.v4.parse.GrammarTreeVisitor;
import groovyjarjarantlr4.v4.tool.Grammar;
import groovyjarjarantlr4.v4.tool.ast.ActionAST;
import groovyjarjarantlr4.v4.tool.ast.AltAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarASTWithOptions;
import groovyjarjarantlr4.v4.tool.ast.TerminalAST;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

public class ElementFrequenciesVisitor
extends GrammarTreeVisitor {
    private static final FrequencySet<String> SENTINEL = new FrequencySet();
    final Grammar grammar;
    final Deque<FrequencySet<String>> frequencies;
    private final Deque<FrequencySet<String>> minFrequencies;

    public ElementFrequenciesVisitor(Grammar grammar, TreeNodeStream input) {
        super(input);
        this.grammar = grammar;
        this.frequencies = new ArrayDeque<FrequencySet<String>>();
        this.frequencies.push(new FrequencySet());
        this.minFrequencies = new ArrayDeque<FrequencySet<String>>();
        this.minFrequencies.push(SENTINEL);
    }

    FrequencySet<String> getMinFrequencies() {
        assert (this.minFrequencies.size() == 1);
        assert (this.minFrequencies.peek() != SENTINEL);
        assert (SENTINEL.isEmpty());
        return this.minFrequencies.peek();
    }

    protected static FrequencySet<String> combineMax(FrequencySet<String> a, FrequencySet<String> b) {
        FrequencySet<String> result = ElementFrequenciesVisitor.combineAndClip(a, b, 1);
        for (Map.Entry entry : a.entrySet()) {
            ((MutableInt)result.get(entry.getKey())).v = ((MutableInt)entry.getValue()).v;
        }
        for (Map.Entry entry : b.entrySet()) {
            MutableInt slot = (MutableInt)result.get(entry.getKey());
            slot.v = Math.max(slot.v, ((MutableInt)entry.getValue()).v);
        }
        return result;
    }

    protected static FrequencySet<String> combineMin(FrequencySet<String> a, FrequencySet<String> b) {
        if (b == SENTINEL) {
            return a;
        }
        assert (a != SENTINEL);
        FrequencySet<String> result = ElementFrequenciesVisitor.combineAndClip(a, b, Integer.MAX_VALUE);
        for (Map.Entry entry : result.entrySet()) {
            ((MutableInt)entry.getValue()).v = Math.min(a.count((String)entry.getKey()), b.count((String)entry.getKey()));
        }
        return result;
    }

    protected static FrequencySet<String> combineAndClip(FrequencySet<String> a, FrequencySet<String> b, int clip) {
        int i;
        FrequencySet<String> result = new FrequencySet<String>();
        for (Map.Entry entry : a.entrySet()) {
            for (i = 0; i < ((MutableInt)entry.getValue()).v; ++i) {
                result.add((String)entry.getKey());
            }
        }
        for (Map.Entry entry : b.entrySet()) {
            for (i = 0; i < ((MutableInt)entry.getValue()).v; ++i) {
                result.add((String)entry.getKey());
            }
        }
        for (Map.Entry entry : result.entrySet()) {
            ((MutableInt)entry.getValue()).v = Math.min(((MutableInt)entry.getValue()).v, clip);
        }
        return result;
    }

    @Override
    public void tokenRef(TerminalAST ref) {
        this.frequencies.peek().add(ref.getText());
        this.minFrequencies.peek().add(ref.getText());
    }

    @Override
    public void ruleRef(GrammarAST ref, ActionAST arg) {
        GrammarASTWithOptions grammarASTWithOptions;
        if (ref instanceof GrammarASTWithOptions && Boolean.parseBoolean((grammarASTWithOptions = (GrammarASTWithOptions)ref).getOptionString("suppressAccessor"))) {
            return;
        }
        this.frequencies.peek().add(RuleFunction.getLabelName(this.grammar, ref));
        this.minFrequencies.peek().add(RuleFunction.getLabelName(this.grammar, ref));
    }

    @Override
    public void stringRef(TerminalAST ref) {
        String tokenName = ref.g.getTokenName(ref.getText());
        if (tokenName != null && !tokenName.startsWith("T__")) {
            this.frequencies.peek().add(tokenName);
            this.minFrequencies.peek().add(tokenName);
        }
    }

    @Override
    protected void enterAlternative(AltAST tree) {
        this.frequencies.push(new FrequencySet());
        this.minFrequencies.push(new FrequencySet());
    }

    @Override
    protected void exitAlternative(AltAST tree) {
        this.frequencies.push(ElementFrequenciesVisitor.combineMax(this.frequencies.pop(), this.frequencies.pop()));
        this.minFrequencies.push(ElementFrequenciesVisitor.combineMin(this.minFrequencies.pop(), this.minFrequencies.pop()));
    }

    @Override
    protected void enterElement(GrammarAST tree) {
        this.frequencies.push(new FrequencySet());
        this.minFrequencies.push(new FrequencySet());
    }

    @Override
    protected void exitElement(GrammarAST tree) {
        this.frequencies.push(ElementFrequenciesVisitor.combineAndClip(this.frequencies.pop(), this.frequencies.pop(), 2));
        this.minFrequencies.push(ElementFrequenciesVisitor.combineAndClip(this.minFrequencies.pop(), this.minFrequencies.pop(), 2));
    }

    @Override
    protected void enterBlockSet(GrammarAST tree) {
        this.frequencies.push(new FrequencySet());
        this.minFrequencies.push(new FrequencySet());
    }

    @Override
    protected void exitBlockSet(GrammarAST tree) {
        for (Map.Entry entry : this.frequencies.peek().entrySet()) {
            ((MutableInt)entry.getValue()).v = 1;
        }
        if (this.minFrequencies.peek().size() > 1) {
            this.minFrequencies.peek().clear();
        }
        this.frequencies.push(ElementFrequenciesVisitor.combineAndClip(this.frequencies.pop(), this.frequencies.pop(), 2));
        this.minFrequencies.push(ElementFrequenciesVisitor.combineAndClip(this.minFrequencies.pop(), this.minFrequencies.pop(), 2));
    }

    @Override
    protected void exitSubrule(GrammarAST tree) {
        if (tree.getType() == 80 || tree.getType() == 90) {
            for (Map.Entry entry : this.frequencies.peek().entrySet()) {
                ((MutableInt)entry.getValue()).v = 2;
            }
        }
        if (tree.getType() == 80 || tree.getType() == 89) {
            this.minFrequencies.peek().clear();
        }
    }

    @Override
    protected void enterLexerAlternative(GrammarAST tree) {
        this.frequencies.push(new FrequencySet());
        this.minFrequencies.push(new FrequencySet());
    }

    @Override
    protected void exitLexerAlternative(GrammarAST tree) {
        this.frequencies.push(ElementFrequenciesVisitor.combineMax(this.frequencies.pop(), this.frequencies.pop()));
        this.minFrequencies.push(ElementFrequenciesVisitor.combineMin(this.minFrequencies.pop(), this.minFrequencies.pop()));
    }

    @Override
    protected void enterLexerElement(GrammarAST tree) {
        this.frequencies.push(new FrequencySet());
        this.minFrequencies.push(new FrequencySet());
    }

    @Override
    protected void exitLexerElement(GrammarAST tree) {
        this.frequencies.push(ElementFrequenciesVisitor.combineAndClip(this.frequencies.pop(), this.frequencies.pop(), 2));
        this.minFrequencies.push(ElementFrequenciesVisitor.combineAndClip(this.minFrequencies.pop(), this.minFrequencies.pop(), 2));
    }

    @Override
    protected void exitLexerSubrule(GrammarAST tree) {
        if (tree.getType() == 80 || tree.getType() == 90) {
            for (Map.Entry entry : this.frequencies.peek().entrySet()) {
                ((MutableInt)entry.getValue()).v = 2;
            }
        }
        if (tree.getType() == 80) {
            this.minFrequencies.peek().clear();
        }
    }
}

