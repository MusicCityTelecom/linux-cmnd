/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.tool;

import groovyjarjarantlr4.v4.runtime.BailErrorStrategy;
import groovyjarjarantlr4.v4.runtime.DefaultErrorStrategy;
import groovyjarjarantlr4.v4.runtime.InputMismatchException;
import groovyjarjarantlr4.v4.runtime.InterpreterRuleContext;
import groovyjarjarantlr4.v4.runtime.Parser;
import groovyjarjarantlr4.v4.runtime.ParserInterpreter;
import groovyjarjarantlr4.v4.runtime.ParserRuleContext;
import groovyjarjarantlr4.v4.runtime.RecognitionException;
import groovyjarjarantlr4.v4.runtime.Token;
import groovyjarjarantlr4.v4.runtime.TokenStream;
import groovyjarjarantlr4.v4.runtime.Vocabulary;
import groovyjarjarantlr4.v4.runtime.atn.ATN;
import groovyjarjarantlr4.v4.runtime.atn.ATNDeserializer;
import groovyjarjarantlr4.v4.runtime.atn.ATNSerializer;
import groovyjarjarantlr4.v4.runtime.atn.ATNState;
import groovyjarjarantlr4.v4.runtime.atn.DecisionState;
import groovyjarjarantlr4.v4.runtime.atn.ParserATNSimulator;
import groovyjarjarantlr4.v4.runtime.atn.PredictionMode;
import groovyjarjarantlr4.v4.runtime.atn.RuleStartState;
import groovyjarjarantlr4.v4.runtime.atn.StarLoopEntryState;
import groovyjarjarantlr4.v4.runtime.misc.Interval;
import groovyjarjarantlr4.v4.runtime.tree.Trees;
import groovyjarjarantlr4.v4.tool.Grammar;
import groovyjarjarantlr4.v4.tool.GrammarInterpreterRuleContext;
import groovyjarjarantlr4.v4.tool.LeftRecursiveRule;
import groovyjarjarantlr4.v4.tool.Rule;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.List;

public class GrammarParserInterpreter
extends ParserInterpreter {
    protected final Grammar g;
    protected BitSet decisionStatesThatSetOuterAltNumInContext;
    protected int[][] stateToAltsMap;

    public GrammarParserInterpreter(Grammar g, String grammarFileName, Vocabulary vocabulary, Collection<String> ruleNames, ATN atn, TokenStream input) {
        super(grammarFileName, vocabulary, ruleNames, atn, input);
        this.g = g;
    }

    public GrammarParserInterpreter(Grammar g, ATN atn, TokenStream input) {
        super(g.fileName, g.getVocabulary(), Arrays.asList(g.getRuleNames()), atn, input);
        this.g = g;
        this.decisionStatesThatSetOuterAltNumInContext = this.findOuterMostDecisionStates();
        this.stateToAltsMap = new int[g.atn.states.size()][];
    }

    @Override
    protected InterpreterRuleContext createInterpreterRuleContext(ParserRuleContext parent, int invokingStateNumber, int ruleIndex) {
        return new GrammarInterpreterRuleContext(parent, invokingStateNumber, ruleIndex);
    }

    @Override
    public void reset() {
        super.reset();
        this.overrideDecisionRoot = null;
    }

    public BitSet findOuterMostDecisionStates() {
        BitSet track = new BitSet(this.atn.states.size());
        int numberOfDecisions = this.atn.getNumberOfDecisions();
        for (int i = 0; i < numberOfDecisions; ++i) {
            DecisionState decisionState = this.atn.getDecisionState(i);
            RuleStartState startState = this.atn.ruleToStartState[decisionState.ruleIndex];
            if (decisionState instanceof StarLoopEntryState) {
                StarLoopEntryState loopEntry = (StarLoopEntryState)decisionState;
                if (!loopEntry.precedenceRuleDecision) continue;
                ATNState blockStart = loopEntry.transition((int)0).target;
                track.set(blockStart.stateNumber);
                continue;
            }
            if (startState.transition((int)0).target != decisionState) continue;
            track.set(decisionState.stateNumber);
        }
        return track;
    }

    @Override
    protected int visitDecisionState(DecisionState p) {
        int predictedAlt = super.visitDecisionState(p);
        if (p.getNumberOfTransitions() > 1 && p.decision == this.overrideDecision && this._input.index() == this.overrideDecisionInputIndex) {
            this.overrideDecisionRoot = (GrammarInterpreterRuleContext)this.getContext();
        }
        GrammarInterpreterRuleContext ctx = (GrammarInterpreterRuleContext)this._ctx;
        if (this.decisionStatesThatSetOuterAltNumInContext.get(p.stateNumber)) {
            ctx.outerAltNum = predictedAlt;
            Rule r = this.g.getRule(p.ruleIndex);
            if (this.atn.ruleToStartState[r.index].isPrecedenceRule) {
                int[] alts = this.stateToAltsMap[p.stateNumber];
                LeftRecursiveRule lr = (LeftRecursiveRule)this.g.getRule(p.ruleIndex);
                if (p.getStateType() == 3) {
                    if (alts == null) {
                        alts = lr.getPrimaryAlts();
                        this.stateToAltsMap[p.stateNumber] = alts;
                    }
                } else if (p.getStateType() == 5 && alts == null) {
                    alts = lr.getRecursiveOpAlts();
                    this.stateToAltsMap[p.stateNumber] = alts;
                }
                ctx.outerAltNum = alts[predictedAlt];
            }
        }
        return predictedAlt;
    }

    public static List<ParserRuleContext> getAllPossibleParseTrees(Grammar g, Parser originalParser, TokenStream tokens, int decision, BitSet alts, int startIndex, int stopIndex, int startRuleIndex) throws RecognitionException {
        ArrayList<ParserRuleContext> trees = new ArrayList<ParserRuleContext>();
        ParserInterpreter parser = GrammarParserInterpreter.deriveTempParserInterpreter(g, originalParser, tokens);
        if (stopIndex >= tokens.size() - 1) {
            stopIndex = tokens.size() - 2;
        }
        int alt = alts.nextSetBit(0);
        while (alt >= 0) {
            parser.reset();
            parser.addDecisionOverride(decision, startIndex, alt);
            ParserRuleContext t = parser.parse(startRuleIndex);
            GrammarInterpreterRuleContext ambigSubTree = (GrammarInterpreterRuleContext)Trees.getRootOfSubtreeEnclosingRegion(t, startIndex, stopIndex);
            if (Trees.isAncestorOf(parser.getOverrideDecisionRoot(), ambigSubTree)) {
                ambigSubTree = (GrammarInterpreterRuleContext)parser.getOverrideDecisionRoot();
            }
            trees.add(ambigSubTree);
            alt = alts.nextSetBit(alt + 1);
        }
        return trees;
    }

    public static List<ParserRuleContext> getLookaheadParseTrees(Grammar g, ParserInterpreter originalParser, TokenStream tokens, int startRuleIndex, int decision, int startIndex, int stopIndex) {
        ArrayList<ParserRuleContext> trees = new ArrayList<ParserRuleContext>();
        ParserInterpreter parser = GrammarParserInterpreter.deriveTempParserInterpreter(g, originalParser, tokens);
        DecisionState decisionState = originalParser.getATN().decisionToState.get(decision);
        for (int alt = 1; alt <= decisionState.getTransitions().length; ++alt) {
            BailButConsumeErrorStrategy errorHandler = new BailButConsumeErrorStrategy();
            parser.setErrorHandler(errorHandler);
            parser.reset();
            parser.addDecisionOverride(decision, startIndex, alt);
            ParserRuleContext tt = parser.parse(startRuleIndex);
            int stopTreeAt = stopIndex;
            if (errorHandler.firstErrorTokenIndex >= 0) {
                stopTreeAt = errorHandler.firstErrorTokenIndex;
            }
            Interval overallRange = tt.getSourceInterval();
            if (stopTreeAt > overallRange.b) {
                stopTreeAt = overallRange.b;
            }
            ParserRuleContext subtree = Trees.getRootOfSubtreeEnclosingRegion(tt, startIndex, stopTreeAt);
            if (Trees.isAncestorOf(parser.getOverrideDecisionRoot(), subtree)) {
                subtree = parser.getOverrideDecisionRoot();
            }
            Trees.stripChildrenOutOfRange(subtree, parser.getOverrideDecisionRoot(), startIndex, stopTreeAt);
            trees.add(subtree);
        }
        return trees;
    }

    public static ParserInterpreter deriveTempParserInterpreter(Grammar g, Parser originalParser, TokenStream tokens) {
        ParserInterpreter parser;
        if (originalParser instanceof ParserInterpreter) {
            Class<ParserInterpreter> c = originalParser.getClass().asSubclass(ParserInterpreter.class);
            try {
                Constructor<ParserInterpreter> ctor = c.getConstructor(Grammar.class, ATN.class, TokenStream.class);
                parser = ctor.newInstance(g, originalParser.getATN(), originalParser.getInputStream());
            }
            catch (Exception e) {
                throw new IllegalArgumentException("can't create parser to match incoming " + originalParser.getClass().getSimpleName(), e);
            }
        } else {
            char[] serializedAtn = ATNSerializer.getSerializedAsChars(originalParser.getATN(), Arrays.asList(originalParser.getRuleNames()));
            ATN deserialized = new ATNDeserializer().deserialize(serializedAtn);
            parser = new ParserInterpreter(originalParser.getGrammarFileName(), originalParser.getVocabulary(), Arrays.asList(originalParser.getRuleNames()), deserialized, tokens);
        }
        parser.setInputStream(tokens);
        parser.setErrorHandler(new BailErrorStrategy());
        parser.removeErrorListeners();
        parser.removeParseListeners();
        ((ParserATNSimulator)parser.getInterpreter()).setPredictionMode(PredictionMode.LL_EXACT_AMBIG_DETECTION);
        return parser;
    }

    public static class BailButConsumeErrorStrategy
    extends DefaultErrorStrategy {
        public int firstErrorTokenIndex = -1;

        @Override
        public void recover(Parser recognizer, RecognitionException e) {
            TokenStream input;
            int errIndex = recognizer.getInputStream().index();
            if (this.firstErrorTokenIndex == -1) {
                this.firstErrorTokenIndex = errIndex;
            }
            if ((input = recognizer.getInputStream()).index() < input.size() - 1) {
                recognizer.consume();
            }
        }

        @Override
        public Token recoverInline(Parser recognizer) throws RecognitionException {
            int errIndex = recognizer.getInputStream().index();
            if (this.firstErrorTokenIndex == -1) {
                this.firstErrorTokenIndex = errIndex;
            }
            InputMismatchException e = new InputMismatchException(recognizer);
            throw e;
        }

        @Override
        public void sync(Parser recognizer) {
        }
    }
}

