/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.runtime.debug;

import groovyjarjarantlr4.runtime.IntStream;
import groovyjarjarantlr4.runtime.RecognitionException;
import groovyjarjarantlr4.runtime.Token;
import groovyjarjarantlr4.runtime.TokenStream;
import groovyjarjarantlr4.runtime.debug.BlankDebugEventListener;
import groovyjarjarantlr4.runtime.debug.DebugParser;
import groovyjarjarantlr4.runtime.misc.DoubleKeyMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class Profiler
extends BlankDebugEventListener {
    public static final String DATA_SEP = "\t";
    public static final String newline = System.getProperty("line.separator");
    static boolean dump = false;
    public static final String Version = "3";
    public static final String RUNTIME_STATS_FILENAME = "runtime.stats";
    public DebugParser parser = null;
    protected int ruleLevel = 0;
    protected Token lastRealTokenTouchedInDecision;
    protected Set<String> uniqueRules = new HashSet<String>();
    protected Stack<String> currentGrammarFileName = new Stack();
    protected Stack<String> currentRuleName = new Stack();
    protected Stack<Integer> currentLine = new Stack();
    protected Stack<Integer> currentPos = new Stack();
    protected DoubleKeyMap<String, Integer, DecisionDescriptor> decisions = new DoubleKeyMap();
    protected List<DecisionEvent> decisionEvents = new ArrayList<DecisionEvent>();
    protected Stack<DecisionEvent> decisionStack = new Stack();
    protected int backtrackDepth;
    ProfileStats stats = new ProfileStats();

    public Profiler() {
    }

    public Profiler(DebugParser parser) {
        this.parser = parser;
    }

    @Override
    public void enterRule(String grammarFileName, String ruleName) {
        ++this.ruleLevel;
        ++this.stats.numRuleInvocations;
        this.uniqueRules.add(grammarFileName + ":" + ruleName);
        this.stats.maxRuleInvocationDepth = Math.max(this.stats.maxRuleInvocationDepth, this.ruleLevel);
        this.currentGrammarFileName.push(grammarFileName);
        this.currentRuleName.push(ruleName);
    }

    @Override
    public void exitRule(String grammarFileName, String ruleName) {
        --this.ruleLevel;
        this.currentGrammarFileName.pop();
        this.currentRuleName.pop();
    }

    public void examineRuleMemoization(IntStream input, int ruleIndex, int stopIndex, String ruleName) {
        if (dump) {
            System.out.println("examine memo " + ruleName + " at " + input.index() + ": " + stopIndex);
        }
        if (stopIndex == -1) {
            ++this.stats.numMemoizationCacheMisses;
            ++this.stats.numGuessingRuleInvocations;
            ++this.currentDecision().numMemoizationCacheMisses;
        } else {
            ++this.stats.numMemoizationCacheHits;
            ++this.currentDecision().numMemoizationCacheHits;
        }
    }

    public void memoize(IntStream input, int ruleIndex, int ruleStartIndex, String ruleName) {
        if (dump) {
            System.out.println("memoize " + ruleName);
        }
        ++this.stats.numMemoizationCacheEntries;
    }

    @Override
    public void location(int line, int pos) {
        this.currentLine.push(line);
        this.currentPos.push(pos);
    }

    @Override
    public void enterDecision(int decisionNumber, boolean couldBacktrack) {
        String g;
        DecisionDescriptor descriptor;
        this.lastRealTokenTouchedInDecision = null;
        ++this.stats.numDecisionEvents;
        int startingLookaheadIndex = this.parser.getTokenStream().index();
        TokenStream input = this.parser.getTokenStream();
        if (dump) {
            System.out.println("enterDecision canBacktrack=" + couldBacktrack + " " + decisionNumber + " backtrack depth " + this.backtrackDepth + " @ " + input.get(input.index()) + " rule " + this.locationDescription());
        }
        if ((descriptor = this.decisions.get(g = this.currentGrammarFileName.peek(), decisionNumber)) == null) {
            descriptor = new DecisionDescriptor();
            this.decisions.put(g, decisionNumber, descriptor);
            descriptor.decision = decisionNumber;
            descriptor.fileName = this.currentGrammarFileName.peek();
            descriptor.ruleName = this.currentRuleName.peek();
            descriptor.line = this.currentLine.peek();
            descriptor.pos = this.currentPos.peek();
            descriptor.couldBacktrack = couldBacktrack;
        }
        ++descriptor.n;
        DecisionEvent d = new DecisionEvent();
        this.decisionStack.push(d);
        d.decision = descriptor;
        d.startTime = System.currentTimeMillis();
        d.startIndex = startingLookaheadIndex;
    }

    @Override
    public void exitDecision(int decisionNumber) {
        int depth;
        DecisionEvent d = this.decisionStack.pop();
        d.stopTime = System.currentTimeMillis();
        int lastTokenIndex = this.lastRealTokenTouchedInDecision.getTokenIndex();
        int numHidden = this.getNumberOfHiddenTokens(d.startIndex, lastTokenIndex);
        d.k = depth = lastTokenIndex - d.startIndex - numHidden + 1;
        d.decision.maxk = Math.max(d.decision.maxk, depth);
        if (dump) {
            System.out.println("exitDecision " + decisionNumber + " in " + d.decision.ruleName + " lookahead " + d.k + " max token " + this.lastRealTokenTouchedInDecision);
        }
        this.decisionEvents.add(d);
    }

    @Override
    public void consumeToken(Token token) {
        if (dump) {
            System.out.println("consume token " + token);
        }
        if (!this.inDecision()) {
            ++this.stats.numTokens;
            return;
        }
        if (this.lastRealTokenTouchedInDecision == null || this.lastRealTokenTouchedInDecision.getTokenIndex() < token.getTokenIndex()) {
            this.lastRealTokenTouchedInDecision = token;
        }
        DecisionEvent d = this.currentDecision();
        int thisRefIndex = token.getTokenIndex();
        int numHidden = this.getNumberOfHiddenTokens(d.startIndex, thisRefIndex);
        int depth = thisRefIndex - d.startIndex - numHidden + 1;
        if (dump) {
            System.out.println("consume " + thisRefIndex + " " + depth + " tokens ahead in " + d.decision.ruleName + "-" + d.decision.decision + " start index " + d.startIndex);
        }
    }

    public boolean inDecision() {
        return this.decisionStack.size() > 0;
    }

    @Override
    public void consumeHiddenToken(Token token) {
        if (!this.inDecision()) {
            ++this.stats.numHiddenTokens;
        }
    }

    @Override
    public void LT(int i, Token t) {
        if (this.inDecision() && i > 0) {
            DecisionEvent d = this.currentDecision();
            if (dump) {
                System.out.println("LT(" + i + ")=" + t + " index " + t.getTokenIndex() + " relative to " + d.decision.ruleName + "-" + d.decision.decision + " start index " + d.startIndex);
            }
            if (this.lastRealTokenTouchedInDecision == null || this.lastRealTokenTouchedInDecision.getTokenIndex() < t.getTokenIndex()) {
                this.lastRealTokenTouchedInDecision = t;
                if (dump) {
                    System.out.println("set last token " + this.lastRealTokenTouchedInDecision);
                }
            }
        }
    }

    @Override
    public void beginBacktrack(int level) {
        if (dump) {
            System.out.println("enter backtrack " + level);
        }
        ++this.backtrackDepth;
        DecisionEvent e = this.currentDecision();
        if (e.decision.couldBacktrack) {
            ++this.stats.numBacktrackOccurrences;
            ++e.decision.numBacktrackOccurrences;
            e.backtracks = true;
        }
    }

    @Override
    public void endBacktrack(int level, boolean successful) {
        if (dump) {
            System.out.println("exit backtrack " + level + ": " + successful);
        }
        --this.backtrackDepth;
    }

    @Override
    public void mark(int i) {
        if (dump) {
            System.out.println("mark " + i);
        }
    }

    @Override
    public void rewind(int i) {
        if (dump) {
            System.out.println("rewind " + i);
        }
    }

    @Override
    public void rewind() {
        if (dump) {
            System.out.println("rewind");
        }
    }

    protected DecisionEvent currentDecision() {
        return this.decisionStack.peek();
    }

    @Override
    public void recognitionException(RecognitionException e) {
        ++this.stats.numReportedErrors;
    }

    @Override
    public void semanticPredicate(boolean result, String predicate) {
        ++this.stats.numSemanticPredicates;
        if (this.inDecision()) {
            DecisionEvent d = this.currentDecision();
            d.evalSemPred = true;
            ++d.decision.numSemPredEvals;
            if (dump) {
                System.out.println("eval " + predicate + " in " + d.decision.ruleName + "-" + d.decision.decision);
            }
        }
    }

    @Override
    public void terminate() {
        for (DecisionEvent e : this.decisionEvents) {
            e.decision.avgk += (float)e.k;
            this.stats.avgkPerDecisionEvent += (float)e.k;
            if (!e.backtracks) continue;
            this.stats.avgkPerBacktrackingDecisionEvent += (float)e.k;
        }
        this.stats.averageDecisionPercentBacktracks = 0.0f;
        for (DecisionDescriptor d : this.decisions.values()) {
            ++this.stats.numDecisionsCovered;
            d.avgk = (float)((double)d.avgk / (double)d.n);
            if (d.couldBacktrack) {
                ++this.stats.numDecisionsThatPotentiallyBacktrack;
                float percentBacktracks = (float)d.numBacktrackOccurrences / (float)d.n;
                this.stats.averageDecisionPercentBacktracks += percentBacktracks;
            }
            if (d.numBacktrackOccurrences <= 0) continue;
            ++this.stats.numDecisionsThatDoBacktrack;
        }
        this.stats.averageDecisionPercentBacktracks /= (float)this.stats.numDecisionsThatPotentiallyBacktrack;
        this.stats.averageDecisionPercentBacktracks *= 100.0f;
        this.stats.avgkPerDecisionEvent /= (float)this.stats.numDecisionEvents;
        this.stats.avgkPerBacktrackingDecisionEvent = (float)((double)this.stats.avgkPerBacktrackingDecisionEvent / (double)this.stats.numBacktrackOccurrences);
        System.err.println(this.toString());
        System.err.println(this.getDecisionStatsDump());
    }

    public void setParser(DebugParser parser) {
        this.parser = parser;
    }

    public String toNotifyString() {
        StringBuilder buf = new StringBuilder();
        buf.append(Version);
        buf.append('\t');
        buf.append(this.parser.getClass().getName());
        return buf.toString();
    }

    public String toString() {
        return Profiler.toString(this.getReport());
    }

    public ProfileStats getReport() {
        this.stats.Version = Version;
        this.stats.name = this.parser.getClass().getName();
        this.stats.numUniqueRulesInvoked = this.uniqueRules.size();
        return this.stats;
    }

    public DoubleKeyMap<String, Integer, DecisionDescriptor> getDecisionStats() {
        return this.decisions;
    }

    public List<DecisionEvent> getDecisionEvents() {
        return this.decisionEvents;
    }

    public static String toString(ProfileStats stats) {
        StringBuilder buf = new StringBuilder();
        buf.append("ANTLR Runtime Report; Profile Version ");
        buf.append(stats.Version);
        buf.append(newline);
        buf.append("parser name ");
        buf.append(stats.name);
        buf.append(newline);
        buf.append("Number of rule invocations ");
        buf.append(stats.numRuleInvocations);
        buf.append(newline);
        buf.append("Number of unique rules visited ");
        buf.append(stats.numUniqueRulesInvoked);
        buf.append(newline);
        buf.append("Number of decision events ");
        buf.append(stats.numDecisionEvents);
        buf.append(newline);
        buf.append("Overall average k per decision event ");
        buf.append(stats.avgkPerDecisionEvent);
        buf.append(newline);
        buf.append("Number of backtracking occurrences (can be multiple per decision) ");
        buf.append(stats.numBacktrackOccurrences);
        buf.append(newline);
        buf.append("Overall average k per decision event that backtracks ");
        buf.append(stats.avgkPerBacktrackingDecisionEvent);
        buf.append(newline);
        buf.append("Number of rule invocations while backtracking ");
        buf.append(stats.numGuessingRuleInvocations);
        buf.append(newline);
        buf.append("num decisions that potentially backtrack ");
        buf.append(stats.numDecisionsThatPotentiallyBacktrack);
        buf.append(newline);
        buf.append("num decisions that do backtrack ");
        buf.append(stats.numDecisionsThatDoBacktrack);
        buf.append(newline);
        buf.append("num decisions that potentially backtrack but don't ");
        buf.append(stats.numDecisionsThatPotentiallyBacktrack - stats.numDecisionsThatDoBacktrack);
        buf.append(newline);
        buf.append("average % of time a potentially backtracking decision backtracks ");
        buf.append(stats.averageDecisionPercentBacktracks);
        buf.append(newline);
        buf.append("num unique decisions covered ");
        buf.append(stats.numDecisionsCovered);
        buf.append(newline);
        buf.append("max rule invocation nesting depth ");
        buf.append(stats.maxRuleInvocationDepth);
        buf.append(newline);
        buf.append("rule memoization cache size ");
        buf.append(stats.numMemoizationCacheEntries);
        buf.append(newline);
        buf.append("number of rule memoization cache hits ");
        buf.append(stats.numMemoizationCacheHits);
        buf.append(newline);
        buf.append("number of rule memoization cache misses ");
        buf.append(stats.numMemoizationCacheMisses);
        buf.append(newline);
        buf.append("number of tokens ");
        buf.append(stats.numTokens);
        buf.append(newline);
        buf.append("number of hidden tokens ");
        buf.append(stats.numHiddenTokens);
        buf.append(newline);
        buf.append("number of char ");
        buf.append(stats.numCharsMatched);
        buf.append(newline);
        buf.append("number of hidden char ");
        buf.append(stats.numHiddenCharsMatched);
        buf.append(newline);
        buf.append("number of syntax errors ");
        buf.append(stats.numReportedErrors);
        buf.append(newline);
        return buf.toString();
    }

    public String getDecisionStatsDump() {
        StringBuilder buf = new StringBuilder();
        buf.append("location");
        buf.append(DATA_SEP);
        buf.append("n");
        buf.append(DATA_SEP);
        buf.append("avgk");
        buf.append(DATA_SEP);
        buf.append("maxk");
        buf.append(DATA_SEP);
        buf.append("synpred");
        buf.append(DATA_SEP);
        buf.append("sempred");
        buf.append(DATA_SEP);
        buf.append("canbacktrack");
        buf.append("\n");
        for (String fileName : this.decisions.keySet()) {
            for (int d : this.decisions.keySet(fileName)) {
                DecisionDescriptor s = this.decisions.get(fileName, d);
                buf.append(s.decision);
                buf.append("@");
                buf.append(this.locationDescription(s.fileName, s.ruleName, s.line, s.pos));
                buf.append(DATA_SEP);
                buf.append(s.n);
                buf.append(DATA_SEP);
                buf.append(String.format("%.2f", Float.valueOf(s.avgk)));
                buf.append(DATA_SEP);
                buf.append(s.maxk);
                buf.append(DATA_SEP);
                buf.append(s.numBacktrackOccurrences);
                buf.append(DATA_SEP);
                buf.append(s.numSemPredEvals);
                buf.append(DATA_SEP);
                buf.append(s.couldBacktrack ? "1" : "0");
                buf.append(newline);
            }
        }
        return buf.toString();
    }

    protected int[] trim(int[] X, int n) {
        if (n < X.length) {
            int[] trimmed = new int[n];
            System.arraycopy(X, 0, trimmed, 0, n);
            X = trimmed;
        }
        return X;
    }

    protected int[] toArray(List<Integer> a) {
        int[] x = new int[a.size()];
        for (int i = 0; i < a.size(); ++i) {
            Integer I = a.get(i);
            x[i] = I;
        }
        return x;
    }

    public int getNumberOfHiddenTokens(int i, int j) {
        int n = 0;
        TokenStream input = this.parser.getTokenStream();
        for (int ti = i; ti < input.size() && ti <= j; ++ti) {
            Token t = input.get(ti);
            if (t.getChannel() == 0) continue;
            ++n;
        }
        return n;
    }

    protected String locationDescription() {
        return this.locationDescription(this.currentGrammarFileName.peek(), this.currentRuleName.peek(), this.currentLine.peek(), this.currentPos.peek());
    }

    protected String locationDescription(String file, String rule, int line, int pos) {
        return file + ":" + line + ":" + pos + "(" + rule + ")";
    }

    public static class DecisionEvent {
        public DecisionDescriptor decision;
        public int startIndex;
        public int k;
        public boolean backtracks;
        public boolean evalSemPred;
        public long startTime;
        public long stopTime;
        public int numMemoizationCacheHits;
        public int numMemoizationCacheMisses;
    }

    public static class DecisionDescriptor {
        public int decision;
        public String fileName;
        public String ruleName;
        public int line;
        public int pos;
        public boolean couldBacktrack;
        public int n;
        public float avgk;
        public int maxk;
        public int numBacktrackOccurrences;
        public int numSemPredEvals;
    }

    public static class ProfileStats {
        public String Version;
        public String name;
        public int numRuleInvocations;
        public int numUniqueRulesInvoked;
        public int numDecisionEvents;
        public int numDecisionsCovered;
        public int numDecisionsThatPotentiallyBacktrack;
        public int numDecisionsThatDoBacktrack;
        public int maxRuleInvocationDepth;
        public float avgkPerDecisionEvent;
        public float avgkPerBacktrackingDecisionEvent;
        public float averageDecisionPercentBacktracks;
        public int numBacktrackOccurrences;
        public int numFixedDecisions;
        public int minDecisionMaxFixedLookaheads;
        public int maxDecisionMaxFixedLookaheads;
        public int avgDecisionMaxFixedLookaheads;
        public int stddevDecisionMaxFixedLookaheads;
        public int numCyclicDecisions;
        public int minDecisionMaxCyclicLookaheads;
        public int maxDecisionMaxCyclicLookaheads;
        public int avgDecisionMaxCyclicLookaheads;
        public int stddevDecisionMaxCyclicLookaheads;
        public int numSemanticPredicates;
        public int numTokens;
        public int numHiddenTokens;
        public int numCharsMatched;
        public int numHiddenCharsMatched;
        public int numReportedErrors;
        public int numMemoizationCacheHits;
        public int numMemoizationCacheMisses;
        public int numGuessingRuleInvocations;
        public int numMemoizationCacheEntries;
    }
}

