/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.analysis;

import groovyjarjarantlr4.v4.analysis.LeftRecursionDetector;
import groovyjarjarantlr4.v4.misc.Utils;
import groovyjarjarantlr4.v4.runtime.atn.DecisionState;
import groovyjarjarantlr4.v4.runtime.atn.LL1Analyzer;
import groovyjarjarantlr4.v4.runtime.atn.PredictionContext;
import groovyjarjarantlr4.v4.runtime.misc.IntervalSet;
import groovyjarjarantlr4.v4.tool.ErrorType;
import groovyjarjarantlr4.v4.tool.Grammar;
import groovyjarjarantlr4.v4.tool.Rule;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import java.util.ArrayList;
import java.util.Arrays;

public class AnalysisPipeline {
    public Grammar g;

    public AnalysisPipeline(Grammar g) {
        this.g = g;
    }

    public void process() {
        LeftRecursionDetector lr = new LeftRecursionDetector(this.g, this.g.atn);
        lr.check();
        if (!lr.listOfRecursiveCycles.isEmpty()) {
            return;
        }
        if (this.g.isLexer()) {
            this.processLexer();
        } else {
            this.processParser();
        }
    }

    protected void processLexer() {
        for (Rule rule : this.g.rules.values()) {
            LL1Analyzer analyzer;
            IntervalSet look;
            if (rule.isFragment() || !(look = (analyzer = new LL1Analyzer(this.g.atn)).LOOK(this.g.atn.ruleToStartState[rule.index], PredictionContext.EMPTY_LOCAL)).contains(-2)) continue;
            this.g.tool.errMgr.grammarError(ErrorType.EPSILON_TOKEN, this.g.fileName, ((GrammarAST)rule.ast.getChild(0)).getToken(), rule.name);
        }
    }

    protected void processParser() {
        this.g.decisionLOOK = new ArrayList<IntervalSet[]>(this.g.atn.getNumberOfDecisions() + 1);
        for (DecisionState s : this.g.atn.decisionToState) {
            Object[] look;
            this.g.tool.log("LL1", "\nDECISION " + s.decision + " in rule " + this.g.getRule((int)s.ruleIndex).name);
            if (s.nonGreedy) {
                look = new IntervalSet[s.getNumberOfTransitions() + 1];
            } else {
                LL1Analyzer anal = new LL1Analyzer(this.g.atn);
                look = anal.getDecisionLookahead(s);
                this.g.tool.log("LL1", "look=" + Arrays.toString(look));
            }
            assert (s.decision + 1 >= this.g.decisionLOOK.size());
            Utils.setSize(this.g.decisionLOOK, s.decision + 1);
            this.g.decisionLOOK.set(s.decision, (IntervalSet[])look);
            this.g.tool.log("LL1", "LL(1)? " + AnalysisPipeline.disjoint((IntervalSet[])look));
        }
    }

    public static boolean disjoint(IntervalSet[] altLook) {
        boolean collision = false;
        IntervalSet combined = new IntervalSet(new int[0]);
        if (altLook == null) {
            return false;
        }
        for (IntervalSet look : altLook) {
            if (look == null) {
                return false;
            }
            if (!look.and(combined).isNil()) {
                collision = true;
                break;
            }
            combined.addAll(look);
        }
        return !collision;
    }
}

