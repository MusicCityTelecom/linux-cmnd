/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.analysis;

import groovyjarjarantlr4.v4.runtime.atn.ATN;
import groovyjarjarantlr4.v4.runtime.atn.ATNState;
import groovyjarjarantlr4.v4.runtime.atn.RuleStartState;
import groovyjarjarantlr4.v4.runtime.atn.RuleStopState;
import groovyjarjarantlr4.v4.runtime.atn.RuleTransition;
import groovyjarjarantlr4.v4.runtime.atn.Transition;
import groovyjarjarantlr4.v4.runtime.misc.OrderedHashSet;
import groovyjarjarantlr4.v4.tool.Grammar;
import groovyjarjarantlr4.v4.tool.Rule;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LeftRecursionDetector {
    Grammar g;
    public ATN atn;
    public List<Set<Rule>> listOfRecursiveCycles = new ArrayList<Set<Rule>>();
    Set<RuleStartState> rulesVisitedPerRuleCheck = new HashSet<RuleStartState>();

    public LeftRecursionDetector(Grammar g, ATN atn) {
        this.g = g;
        this.atn = atn;
    }

    public void check() {
        for (RuleStartState start : this.atn.ruleToStartState) {
            this.rulesVisitedPerRuleCheck.clear();
            this.rulesVisitedPerRuleCheck.add(start);
            this.check(this.g.getRule(start.ruleIndex), start, new HashSet<ATNState>());
        }
        if (!this.listOfRecursiveCycles.isEmpty()) {
            this.g.tool.errMgr.leftRecursionCycles(this.g.fileName, this.listOfRecursiveCycles);
        }
    }

    public boolean check(Rule enclosingRule, ATNState s, Set<ATNState> visitedStates) {
        if (s instanceof RuleStopState) {
            return true;
        }
        if (visitedStates.contains(s)) {
            return false;
        }
        visitedStates.add(s);
        int n = s.getNumberOfTransitions();
        boolean stateReachesStopState = false;
        for (int i = 0; i < n; ++i) {
            Transition t = s.transition(i);
            if (t instanceof RuleTransition) {
                RuleTransition rt = (RuleTransition)t;
                Rule r = this.g.getRule(rt.ruleIndex);
                if (this.rulesVisitedPerRuleCheck.contains((RuleStartState)t.target)) {
                    this.addRulesToCycle(enclosingRule, r);
                    continue;
                }
                this.rulesVisitedPerRuleCheck.add((RuleStartState)t.target);
                boolean nullable = this.check(r, t.target, new HashSet<ATNState>());
                this.rulesVisitedPerRuleCheck.remove((RuleStartState)t.target);
                if (!nullable) continue;
                stateReachesStopState |= this.check(enclosingRule, rt.followState, visitedStates);
                continue;
            }
            if (!t.isEpsilon()) continue;
            stateReachesStopState |= this.check(enclosingRule, t.target, visitedStates);
        }
        return stateReachesStopState;
    }

    protected void addRulesToCycle(Rule enclosingRule, Rule targetRule) {
        boolean foundCycle = false;
        for (Set<Rule> rulesInCycle : this.listOfRecursiveCycles) {
            if (rulesInCycle.contains(targetRule)) {
                rulesInCycle.add(enclosingRule);
                foundCycle = true;
            }
            if (!rulesInCycle.contains(enclosingRule)) continue;
            rulesInCycle.add(targetRule);
            foundCycle = true;
        }
        if (!foundCycle) {
            OrderedHashSet cycle = new OrderedHashSet();
            cycle.add(targetRule);
            cycle.add(enclosingRule);
            this.listOfRecursiveCycles.add(cycle);
        }
    }
}

