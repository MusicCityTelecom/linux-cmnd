/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.tool;

import groovyjarjarantlr4.v4.analysis.LeftRecursiveRuleAltInfo;
import groovyjarjarantlr4.v4.misc.OrderedHashMap;
import groovyjarjarantlr4.v4.runtime.misc.Tuple;
import groovyjarjarantlr4.v4.runtime.misc.Tuple2;
import groovyjarjarantlr4.v4.tool.Alternative;
import groovyjarjarantlr4.v4.tool.Grammar;
import groovyjarjarantlr4.v4.tool.Rule;
import groovyjarjarantlr4.v4.tool.ast.AltAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import groovyjarjarantlr4.v4.tool.ast.RuleAST;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeftRecursiveRule
extends Rule {
    public List<LeftRecursiveRuleAltInfo> recPrimaryAlts;
    public OrderedHashMap<Integer, LeftRecursiveRuleAltInfo> recOpAlts;
    public RuleAST originalAST;
    public List<Tuple2<GrammarAST, String>> leftRecursiveRuleRefLabels = new ArrayList<Tuple2<GrammarAST, String>>();

    public LeftRecursiveRule(Grammar g, String name, RuleAST ast) {
        super(g, name, ast, 1);
        this.originalAST = ast;
        this.alt = new Alternative[this.numberOfAlts + 1];
        for (int i = 1; i <= this.numberOfAlts; ++i) {
            this.alt[i] = new Alternative(this, i);
        }
    }

    @Override
    public boolean hasAltSpecificContexts() {
        return super.hasAltSpecificContexts() || this.getAltLabels() != null;
    }

    @Override
    public int getOriginalNumberOfAlts() {
        int n = 0;
        if (this.recPrimaryAlts != null) {
            n += this.recPrimaryAlts.size();
        }
        if (this.recOpAlts != null) {
            n += this.recOpAlts.size();
        }
        return n;
    }

    public RuleAST getOriginalAST() {
        return this.originalAST;
    }

    @Override
    public List<AltAST> getUnlabeledAltASTs() {
        ArrayList<AltAST> alts = new ArrayList<AltAST>();
        for (LeftRecursiveRuleAltInfo altInfo : this.recPrimaryAlts) {
            if (altInfo.altLabel != null) continue;
            alts.add(altInfo.originalAltAST);
        }
        for (int i = 0; i < this.recOpAlts.size(); ++i) {
            LeftRecursiveRuleAltInfo altInfo;
            altInfo = this.recOpAlts.getElement(i);
            if (altInfo.altLabel != null) continue;
            alts.add(altInfo.originalAltAST);
        }
        if (alts.isEmpty()) {
            return null;
        }
        return alts;
    }

    public int[] getPrimaryAlts() {
        if (this.recPrimaryAlts.size() == 0) {
            return null;
        }
        int[] alts = new int[this.recPrimaryAlts.size() + 1];
        for (int i = 0; i < this.recPrimaryAlts.size(); ++i) {
            LeftRecursiveRuleAltInfo altInfo = this.recPrimaryAlts.get(i);
            alts[i + 1] = altInfo.altNum;
        }
        return alts;
    }

    public int[] getRecursiveOpAlts() {
        if (this.recOpAlts.size() == 0) {
            return null;
        }
        int[] alts = new int[this.recOpAlts.size() + 1];
        int alt = 1;
        for (LeftRecursiveRuleAltInfo altInfo : this.recOpAlts.values()) {
            alts[alt] = altInfo.altNum;
            ++alt;
        }
        return alts;
    }

    @Override
    public Map<String, List<Tuple2<Integer, AltAST>>> getAltLabels() {
        List<Tuple2<Integer, AltAST>> pairs;
        HashMap<String, List<Tuple2<Integer, AltAST>>> labels = new HashMap<String, List<Tuple2<Integer, AltAST>>>();
        Map<String, List<Tuple2<Integer, AltAST>>> normalAltLabels = super.getAltLabels();
        if (normalAltLabels != null) {
            labels.putAll(normalAltLabels);
        }
        if (this.recPrimaryAlts != null) {
            for (LeftRecursiveRuleAltInfo altInfo : this.recPrimaryAlts) {
                if (altInfo.altLabel == null) continue;
                pairs = (ArrayList<Tuple2<Integer, AltAST>>)labels.get(altInfo.altLabel);
                if (pairs == null) {
                    pairs = new ArrayList<Tuple2<Integer, AltAST>>();
                    labels.put(altInfo.altLabel, pairs);
                }
                pairs.add(Tuple.create(altInfo.altNum, altInfo.originalAltAST));
            }
        }
        if (this.recOpAlts != null) {
            for (int i = 0; i < this.recOpAlts.size(); ++i) {
                LeftRecursiveRuleAltInfo altInfo;
                altInfo = this.recOpAlts.getElement(i);
                if (altInfo.altLabel == null) continue;
                pairs = (List)labels.get(altInfo.altLabel);
                if (pairs == null) {
                    pairs = new ArrayList();
                    labels.put(altInfo.altLabel, pairs);
                }
                pairs.add(Tuple.create(altInfo.altNum, altInfo.originalAltAST));
            }
        }
        if (labels.isEmpty()) {
            return null;
        }
        return labels;
    }
}

