/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.atn;

import groovyjarjarantlr4.v4.runtime.atn.ATN;
import groovyjarjarantlr4.v4.runtime.atn.ATNDeserializationOptions;
import groovyjarjarantlr4.v4.runtime.atn.ATNState;
import groovyjarjarantlr4.v4.runtime.atn.ATNType;
import groovyjarjarantlr4.v4.runtime.atn.ActionTransition;
import groovyjarjarantlr4.v4.runtime.atn.AtomTransition;
import groovyjarjarantlr4.v4.runtime.atn.BasicBlockStartState;
import groovyjarjarantlr4.v4.runtime.atn.BasicState;
import groovyjarjarantlr4.v4.runtime.atn.BlockEndState;
import groovyjarjarantlr4.v4.runtime.atn.BlockStartState;
import groovyjarjarantlr4.v4.runtime.atn.DecisionState;
import groovyjarjarantlr4.v4.runtime.atn.EpsilonTransition;
import groovyjarjarantlr4.v4.runtime.atn.LexerAction;
import groovyjarjarantlr4.v4.runtime.atn.LexerActionType;
import groovyjarjarantlr4.v4.runtime.atn.LexerChannelAction;
import groovyjarjarantlr4.v4.runtime.atn.LexerCustomAction;
import groovyjarjarantlr4.v4.runtime.atn.LexerModeAction;
import groovyjarjarantlr4.v4.runtime.atn.LexerMoreAction;
import groovyjarjarantlr4.v4.runtime.atn.LexerPopModeAction;
import groovyjarjarantlr4.v4.runtime.atn.LexerPushModeAction;
import groovyjarjarantlr4.v4.runtime.atn.LexerSkipAction;
import groovyjarjarantlr4.v4.runtime.atn.LexerTypeAction;
import groovyjarjarantlr4.v4.runtime.atn.LoopEndState;
import groovyjarjarantlr4.v4.runtime.atn.NotSetTransition;
import groovyjarjarantlr4.v4.runtime.atn.PlusBlockStartState;
import groovyjarjarantlr4.v4.runtime.atn.PlusLoopbackState;
import groovyjarjarantlr4.v4.runtime.atn.PrecedencePredicateTransition;
import groovyjarjarantlr4.v4.runtime.atn.PredicateTransition;
import groovyjarjarantlr4.v4.runtime.atn.RangeTransition;
import groovyjarjarantlr4.v4.runtime.atn.RuleStartState;
import groovyjarjarantlr4.v4.runtime.atn.RuleStopState;
import groovyjarjarantlr4.v4.runtime.atn.RuleTransition;
import groovyjarjarantlr4.v4.runtime.atn.SetTransition;
import groovyjarjarantlr4.v4.runtime.atn.StarBlockStartState;
import groovyjarjarantlr4.v4.runtime.atn.StarLoopEntryState;
import groovyjarjarantlr4.v4.runtime.atn.StarLoopbackState;
import groovyjarjarantlr4.v4.runtime.atn.TokensStartState;
import groovyjarjarantlr4.v4.runtime.atn.Transition;
import groovyjarjarantlr4.v4.runtime.atn.WildcardTransition;
import groovyjarjarantlr4.v4.runtime.dfa.DFA;
import groovyjarjarantlr4.v4.runtime.misc.Interval;
import groovyjarjarantlr4.v4.runtime.misc.IntervalSet;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import groovyjarjarantlr4.v4.runtime.misc.Tuple;
import groovyjarjarantlr4.v4.runtime.misc.Tuple2;
import groovyjarjarantlr4.v4.runtime.misc.Tuple3;
import java.io.InvalidClassException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class ATNDeserializer {
    public static final int SERIALIZED_VERSION = 3;
    private static final UUID BASE_SERIALIZED_UUID = UUID.fromString("E4178468-DF95-44D0-AD87-F22A5D5FB6D3");
    private static final UUID ADDED_LEXER_ACTIONS = UUID.fromString("AB35191A-1603-487E-B75A-479B831EAF6D");
    private static final UUID ADDED_UNICODE_SMP = UUID.fromString("C23FEA89-0605-4f51-AFB8-058BCAB8C91B");
    private static final List<UUID> SUPPORTED_UUIDS = new ArrayList<UUID>();
    public static final UUID SERIALIZED_UUID;
    @NotNull
    private final ATNDeserializationOptions deserializationOptions;

    static UnicodeDeserializer getUnicodeDeserializer(UnicodeDeserializingMode mode) {
        if (mode == UnicodeDeserializingMode.UNICODE_BMP) {
            return new UnicodeDeserializer(){

                @Override
                public int readUnicode(char[] data, int p) {
                    return ATNDeserializer.toInt(data[p]);
                }

                @Override
                public int size() {
                    return 1;
                }
            };
        }
        return new UnicodeDeserializer(){

            @Override
            public int readUnicode(char[] data, int p) {
                return ATNDeserializer.toInt32(data, p);
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }

    public ATNDeserializer() {
        this(ATNDeserializationOptions.getDefaultOptions());
    }

    public ATNDeserializer(@Nullable ATNDeserializationOptions deserializationOptions) {
        if (deserializationOptions == null) {
            deserializationOptions = ATNDeserializationOptions.getDefaultOptions();
        }
        this.deserializationOptions = deserializationOptions;
    }

    protected boolean isFeatureSupported(UUID feature, UUID actualUuid) {
        int featureIndex = SUPPORTED_UUIDS.indexOf(feature);
        if (featureIndex < 0) {
            return false;
        }
        return SUPPORTED_UUIDS.indexOf(actualUuid) >= featureIndex;
    }

    /*
     * WARNING - void declaration
     */
    public ATN deserialize(@NotNull char[] data) {
        void var21_53;
        void var21_48;
        void var13_21;
        int version;
        data = (char[])data.clone();
        for (int i5 = 1; i5 < data.length; ++i5) {
            data[i5] = (char)(data[i5] - 2);
        }
        int p = 0;
        if ((version = ATNDeserializer.toInt(data[p++])) != SERIALIZED_VERSION) {
            String reason = String.format(Locale.getDefault(), "Could not deserialize ATN with version %d (expected %d).", version, SERIALIZED_VERSION);
            throw new UnsupportedOperationException(new InvalidClassException(ATN.class.getName(), reason));
        }
        UUID uuid = ATNDeserializer.toUUID(data, p);
        p += 8;
        if (!SUPPORTED_UUIDS.contains(uuid)) {
            String reason = String.format(Locale.getDefault(), "Could not deserialize ATN with UUID %s (expected %s or a legacy UUID).", uuid, SERIALIZED_UUID);
            throw new UnsupportedOperationException(new InvalidClassException(ATN.class.getName(), reason));
        }
        boolean supportsLexerActions = this.isFeatureSupported(ADDED_LEXER_ACTIONS, uuid);
        ATNType grammarType = ATNType.values()[ATNDeserializer.toInt(data[p++])];
        int maxTokenType = ATNDeserializer.toInt(data[p++]);
        ATN atn = new ATN(grammarType, maxTokenType);
        ArrayList<Tuple2<LoopEndState, Integer>> loopBackStateNumbers = new ArrayList<Tuple2<LoopEndState, Integer>>();
        ArrayList<Tuple2<BlockStartState, Integer>> endStateNumbers = new ArrayList<Tuple2<BlockStartState, Integer>>();
        int nstates = ATNDeserializer.toInt(data[p++]);
        for (int i6 = 0; i6 < nstates; ++i6) {
            int ruleIndex;
            int n;
            if ((n = ATNDeserializer.toInt(data[p++])) == 0) {
                atn.addState(null);
                continue;
            }
            if ((ruleIndex = ATNDeserializer.toInt(data[p++])) == 65535) {
                ruleIndex = -1;
            }
            ATNState s = this.stateFactory(n, ruleIndex);
            if (n == 12) {
                int loopBackStateNumber = ATNDeserializer.toInt(data[p++]);
                loopBackStateNumbers.add(Tuple.create((LoopEndState)s, loopBackStateNumber));
            } else if (s instanceof BlockStartState) {
                int endStateNumber = ATNDeserializer.toInt(data[p++]);
                endStateNumbers.add(Tuple.create((BlockStartState)s, endStateNumber));
            }
            atn.addState(s);
        }
        for (Tuple2 tuple2 : loopBackStateNumbers) {
            ((LoopEndState)tuple2.getItem1()).loopBackState = atn.states.get((Integer)tuple2.getItem2());
        }
        for (Tuple2 tuple2 : endStateNumbers) {
            ((BlockStartState)tuple2.getItem1()).endState = (BlockEndState)atn.states.get((Integer)tuple2.getItem2());
        }
        int numNonGreedyStates = ATNDeserializer.toInt(data[p++]);
        boolean bl = false;
        while (var13_21 < numNonGreedyStates) {
            int stateNumber = ATNDeserializer.toInt(data[p++]);
            ((DecisionState)atn.states.get((int)stateNumber)).nonGreedy = true;
            ++var13_21;
        }
        int n = ATNDeserializer.toInt(data[p++]);
        for (int i8 = 0; i8 < n; ++i8) {
            int stateNumber = ATNDeserializer.toInt(data[p++]);
            ((DecisionState)atn.states.get((int)stateNumber)).sll = true;
        }
        int numPrecedenceStates = ATNDeserializer.toInt(data[p++]);
        for (int i9 = 0; i9 < numPrecedenceStates; ++i9) {
            int stateNumber = ATNDeserializer.toInt(data[p++]);
            ((RuleStartState)atn.states.get((int)stateNumber)).isPrecedenceRule = true;
        }
        int nrules = ATNDeserializer.toInt(data[p++]);
        if (atn.grammarType == ATNType.LEXER) {
            atn.ruleToTokenType = new int[nrules];
        }
        atn.ruleToStartState = new RuleStartState[nrules];
        for (int i10 = 0; i10 < nrules; ++i10) {
            int actionIndexIgnored;
            int tokenType;
            int s = ATNDeserializer.toInt(data[p++]);
            RuleStartState startState = (RuleStartState)atn.states.get(s);
            startState.leftFactored = ATNDeserializer.toInt(data[p++]) != 0;
            atn.ruleToStartState[i10] = startState;
            if (atn.grammarType != ATNType.LEXER) continue;
            if ((tokenType = ATNDeserializer.toInt(data[p++])) == 65535) {
                tokenType = -1;
            }
            atn.ruleToTokenType[i10] = tokenType;
            if (this.isFeatureSupported(ADDED_LEXER_ACTIONS, uuid) || (actionIndexIgnored = ATNDeserializer.toInt(data[p++])) != 65535) continue;
            actionIndexIgnored = -1;
        }
        atn.ruleToStopState = new RuleStopState[nrules];
        for (ATNState state : atn.states) {
            RuleStopState stopState;
            if (!(state instanceof RuleStopState)) continue;
            atn.ruleToStopState[state.ruleIndex] = stopState = (RuleStopState)state;
            atn.ruleToStartState[state.ruleIndex].stopState = stopState;
        }
        int nmodes = ATNDeserializer.toInt(data[p++]);
        for (int i4 = 0; i4 < nmodes; ++i4) {
            int s = ATNDeserializer.toInt(data[p++]);
            atn.modeToStartState.add((TokensStartState)atn.states.get(s));
        }
        atn.modeToDFA = new DFA[nmodes];
        for (int i = 0; i < nmodes; ++i) {
            atn.modeToDFA[i] = new DFA(atn.modeToStartState.get(i));
        }
        ArrayList<IntervalSet> sets = new ArrayList<IntervalSet>();
        p = this.deserializeSets(data, p, sets, ATNDeserializer.getUnicodeDeserializer(UnicodeDeserializingMode.UNICODE_BMP));
        if (this.isFeatureSupported(ADDED_UNICODE_SMP, uuid)) {
            int previousSetCount = sets.size();
            p = this.deserializeSets(data, p, sets, ATNDeserializer.getUnicodeDeserializer(UnicodeDeserializingMode.UNICODE_SMP));
            atn.setHasUnicodeSMPTransitions(sets.size() > previousSetCount);
        }
        int nedges = ATNDeserializer.toInt(data[p++]);
        for (int i11 = 0; i11 < nedges; ++i11) {
            int src = ATNDeserializer.toInt(data[p]);
            int n2 = ATNDeserializer.toInt(data[p + 1]);
            int ttype = ATNDeserializer.toInt(data[p + 2]);
            int arg1 = ATNDeserializer.toInt(data[p + 3]);
            int arg2 = ATNDeserializer.toInt(data[p + 4]);
            int arg3 = ATNDeserializer.toInt(data[p + 5]);
            Transition trans = this.edgeFactory(atn, ttype, src, n2, arg1, arg2, arg3, sets);
            ATNState srcState = atn.states.get(src);
            srcState.addTransition(trans);
            p += 6;
        }
        LinkedHashSet<Tuple3<Integer, Integer, Integer>> returnTransitions = new LinkedHashSet<Tuple3<Integer, Integer, Integer>>();
        for (ATNState aTNState : atn.states) {
            boolean returningToLeftFactored = aTNState.ruleIndex >= 0 && atn.ruleToStartState[aTNState.ruleIndex].leftFactored;
            for (int i3 = 0; i3 < aTNState.getNumberOfTransitions(); ++i3) {
                Transition t = aTNState.transition(i3);
                if (!(t instanceof RuleTransition)) continue;
                RuleTransition ruleTransition = (RuleTransition)t;
                boolean returningFromLeftFactored = atn.ruleToStartState[ruleTransition.target.ruleIndex].leftFactored;
                if (!returningFromLeftFactored && returningToLeftFactored) continue;
                int outermostPrecedenceReturn = -1;
                if (atn.ruleToStartState[ruleTransition.target.ruleIndex].isPrecedenceRule && ruleTransition.precedence == 0) {
                    outermostPrecedenceReturn = ruleTransition.target.ruleIndex;
                }
                returnTransitions.add(Tuple.create(ruleTransition.target.ruleIndex, ruleTransition.followState.stateNumber, outermostPrecedenceReturn));
            }
        }
        for (Tuple3 tuple3 : returnTransitions) {
            EpsilonTransition transition = new EpsilonTransition(atn.states.get((Integer)tuple3.getItem2()), (Integer)tuple3.getItem3());
            atn.ruleToStopState[(Integer)tuple3.getItem1()].addTransition(transition);
        }
        for (ATNState aTNState : atn.states) {
            ATNState target;
            int i;
            ATNState loopbackState;
            if (aTNState instanceof BlockStartState) {
                if (((BlockStartState)aTNState).endState == null) {
                    throw new IllegalStateException();
                }
                if (((BlockStartState)aTNState).endState.startState != null) {
                    throw new IllegalStateException();
                }
                ((BlockStartState)aTNState).endState.startState = (BlockStartState)aTNState;
            }
            if (aTNState instanceof PlusLoopbackState) {
                loopbackState = (PlusLoopbackState)aTNState;
                for (i = 0; i < loopbackState.getNumberOfTransitions(); ++i) {
                    target = loopbackState.transition((int)i).target;
                    if (!(target instanceof PlusBlockStartState)) continue;
                    ((PlusBlockStartState)target).loopBackState = loopbackState;
                }
                continue;
            }
            if (!(aTNState instanceof StarLoopbackState)) continue;
            loopbackState = (StarLoopbackState)aTNState;
            for (i = 0; i < loopbackState.getNumberOfTransitions(); ++i) {
                target = loopbackState.transition((int)i).target;
                if (!(target instanceof StarLoopEntryState)) continue;
                ((StarLoopEntryState)target).loopBackState = loopbackState;
            }
        }
        int ndecisions = ATNDeserializer.toInt(data[p++]);
        boolean bl2 = true;
        while (var21_48 <= ndecisions) {
            int s = ATNDeserializer.toInt(data[p++]);
            DecisionState decState = (DecisionState)atn.states.get(s);
            atn.decisionToState.add(decState);
            decState.decision = var21_48 - true;
            ++var21_48;
        }
        if (atn.grammarType == ATNType.LEXER) {
            if (supportsLexerActions) {
                void var21_50;
                atn.lexerActions = new LexerAction[ATNDeserializer.toInt(data[p++])];
                boolean bl3 = false;
                while (var21_50 < atn.lexerActions.length) {
                    LexerAction lexerAction;
                    int data2;
                    int data1;
                    LexerActionType actionType = LexerActionType.values()[ATNDeserializer.toInt(data[p++])];
                    if ((data1 = ATNDeserializer.toInt(data[p++])) == 65535) {
                        data1 = -1;
                    }
                    if ((data2 = ATNDeserializer.toInt(data[p++])) == 65535) {
                        data2 = -1;
                    }
                    atn.lexerActions[var21_50] = lexerAction = this.lexerActionFactory(actionType, data1, data2);
                    ++var21_50;
                }
            } else {
                ArrayList<LexerCustomAction> arrayList = new ArrayList<LexerCustomAction>();
                for (ATNState state : atn.states) {
                    for (int i12 = 0; i12 < state.getNumberOfTransitions(); ++i12) {
                        Transition transition = state.transition(i12);
                        if (!(transition instanceof ActionTransition)) continue;
                        int ruleIndex = ((ActionTransition)transition).ruleIndex;
                        int actionIndex = ((ActionTransition)transition).actionIndex;
                        LexerCustomAction lexerAction = new LexerCustomAction(ruleIndex, actionIndex);
                        state.setTransition(i12, new ActionTransition(transition.target, ruleIndex, arrayList.size(), false));
                        arrayList.add(lexerAction);
                    }
                }
                atn.lexerActions = arrayList.toArray(new LexerAction[arrayList.size()]);
            }
        }
        this.markPrecedenceDecisions(atn);
        atn.decisionToDFA = new DFA[ndecisions];
        boolean bl4 = false;
        while (var21_53 < ndecisions) {
            atn.decisionToDFA[var21_53] = new DFA(atn.decisionToState.get((int)var21_53), (int)var21_53);
            ++var21_53;
        }
        if (this.deserializationOptions.isVerifyATN()) {
            this.verifyATN(atn);
        }
        if (this.deserializationOptions.isGenerateRuleBypassTransitions() && atn.grammarType == ATNType.PARSER) {
            void var21_57;
            void var21_55;
            atn.ruleToTokenType = new int[atn.ruleToStartState.length];
            boolean bl5 = false;
            while (var21_55 < atn.ruleToStartState.length) {
                atn.ruleToTokenType[var21_55] = atn.maxTokenType + var21_55 + 1;
                ++var21_55;
            }
            boolean bl6 = false;
            while (var21_57 < atn.ruleToStartState.length) {
                ATNState endState;
                BasicBlockStartState bypassStart = new BasicBlockStartState();
                bypassStart.ruleIndex = var21_57;
                atn.addState(bypassStart);
                BlockEndState bypassStop = new BlockEndState();
                bypassStop.ruleIndex = var21_57;
                atn.addState(bypassStop);
                bypassStart.endState = bypassStop;
                atn.defineDecisionState(bypassStart);
                bypassStop.startState = bypassStart;
                Transition excludeTransition = null;
                if (atn.ruleToStartState[var21_57].isPrecedenceRule) {
                    endState = null;
                    for (ATNState state : atn.states) {
                        ATNState maybeLoopEndState;
                        if (state.ruleIndex != var21_57 || !(state instanceof StarLoopEntryState) || !((maybeLoopEndState = state.transition((int)(state.getNumberOfTransitions() - 1)).target) instanceof LoopEndState) || !maybeLoopEndState.epsilonOnlyTransitions || !(maybeLoopEndState.transition((int)0).target instanceof RuleStopState)) continue;
                        endState = state;
                        break;
                    }
                    if (endState == null) {
                        throw new UnsupportedOperationException("Couldn't identify final state of the precedence rule prefix section.");
                    }
                    excludeTransition = ((StarLoopEntryState)endState).loopBackState.transition(0);
                } else {
                    endState = atn.ruleToStopState[var21_57];
                }
                for (ATNState state : atn.states) {
                    for (Transition transition : state.transitions) {
                        if (transition == excludeTransition || transition.target != endState) continue;
                        transition.target = bypassStop;
                    }
                }
                while (atn.ruleToStartState[var21_57].getNumberOfTransitions() > 0) {
                    Transition transition = atn.ruleToStartState[var21_57].removeTransition(atn.ruleToStartState[var21_57].getNumberOfTransitions() - 1);
                    bypassStart.addTransition(transition);
                }
                atn.ruleToStartState[var21_57].addTransition(new EpsilonTransition(bypassStart));
                bypassStop.addTransition(new EpsilonTransition(endState));
                BasicState matchState = new BasicState();
                atn.addState(matchState);
                matchState.addTransition(new AtomTransition(bypassStop, atn.ruleToTokenType[var21_57]));
                bypassStart.addTransition(new EpsilonTransition(matchState));
                ++var21_57;
            }
            if (this.deserializationOptions.isVerifyATN()) {
                this.verifyATN(atn);
            }
        }
        if (this.deserializationOptions.isOptimize()) {
            boolean preserveOrder;
            do {
                int n3 = 0;
                n3 += ATNDeserializer.inlineSetRules(atn);
                n3 += ATNDeserializer.combineChainedEpsilons(atn);
            } while ((n3 += ATNDeserializer.optimizeSets(atn, preserveOrder = atn.grammarType == ATNType.LEXER)) != 0);
            if (this.deserializationOptions.isVerifyATN()) {
                this.verifyATN(atn);
            }
        }
        ATNDeserializer.identifyTailCalls(atn);
        return atn;
    }

    private int deserializeSets(char[] data, int p, List<IntervalSet> sets, UnicodeDeserializer unicodeDeserializer) {
        int nsets = ATNDeserializer.toInt(data[p++]);
        for (int i = 0; i < nsets; ++i) {
            boolean containsEof;
            int nintervals = ATNDeserializer.toInt(data[p]);
            IntervalSet set = new IntervalSet(new int[0]);
            sets.add(set);
            int n = ++p;
            ++p;
            boolean bl = containsEof = ATNDeserializer.toInt(data[n]) != 0;
            if (containsEof) {
                set.add(-1);
            }
            for (int j = 0; j < nintervals; ++j) {
                int a = unicodeDeserializer.readUnicode(data, p);
                int b = unicodeDeserializer.readUnicode(data, p += unicodeDeserializer.size());
                p += unicodeDeserializer.size();
                set.add(a, b);
            }
        }
        return p;
    }

    protected void markPrecedenceDecisions(@NotNull ATN atn) {
        HashMap<Integer, StarLoopEntryState> rulePrecedenceDecisions = new HashMap<Integer, StarLoopEntryState>();
        for (ATNState aTNState : atn.states) {
            ATNState maybeLoopEndState;
            if (!(aTNState instanceof StarLoopEntryState) || !atn.ruleToStartState[aTNState.ruleIndex].isPrecedenceRule || !((maybeLoopEndState = aTNState.transition((int)(aTNState.getNumberOfTransitions() - 1)).target) instanceof LoopEndState) || !maybeLoopEndState.epsilonOnlyTransitions || !(maybeLoopEndState.transition((int)0).target instanceof RuleStopState)) continue;
            rulePrecedenceDecisions.put(aTNState.ruleIndex, (StarLoopEntryState)aTNState);
            ((StarLoopEntryState)aTNState).precedenceRuleDecision = true;
            ((StarLoopEntryState)aTNState).precedenceLoopbackStates = new BitSet(atn.states.size());
        }
        for (Map.Entry entry : rulePrecedenceDecisions.entrySet()) {
            for (Transition transition : atn.ruleToStopState[((Integer)entry.getKey()).intValue()].transitions) {
                EpsilonTransition epsilonTransition;
                if (transition.getSerializationType() != 1 || (epsilonTransition = (EpsilonTransition)transition).outermostPrecedenceReturn() != -1) continue;
                ((StarLoopEntryState)entry.getValue()).precedenceLoopbackStates.set(transition.target.stateNumber);
            }
        }
    }

    protected void verifyATN(ATN atn) {
        for (ATNState state : atn.states) {
            if (state == null) continue;
            this.checkCondition(state.onlyHasEpsilonTransitions() || state.getNumberOfTransitions() <= 1);
            if (state instanceof PlusBlockStartState) {
                this.checkCondition(((PlusBlockStartState)state).loopBackState != null);
            }
            if (state instanceof StarLoopEntryState) {
                StarLoopEntryState starLoopEntryState = (StarLoopEntryState)state;
                this.checkCondition(starLoopEntryState.loopBackState != null);
                this.checkCondition(starLoopEntryState.getNumberOfTransitions() == 2);
                if (starLoopEntryState.transition((int)0).target instanceof StarBlockStartState) {
                    this.checkCondition(starLoopEntryState.transition((int)1).target instanceof LoopEndState);
                    this.checkCondition(!starLoopEntryState.nonGreedy);
                } else if (starLoopEntryState.transition((int)0).target instanceof LoopEndState) {
                    this.checkCondition(starLoopEntryState.transition((int)1).target instanceof StarBlockStartState);
                    this.checkCondition(starLoopEntryState.nonGreedy);
                } else {
                    throw new IllegalStateException();
                }
            }
            if (state instanceof StarLoopbackState) {
                this.checkCondition(state.getNumberOfTransitions() == 1);
                this.checkCondition(state.transition((int)0).target instanceof StarLoopEntryState);
            }
            if (state instanceof LoopEndState) {
                this.checkCondition(((LoopEndState)state).loopBackState != null);
            }
            if (state instanceof RuleStartState) {
                this.checkCondition(((RuleStartState)state).stopState != null);
            }
            if (state instanceof BlockStartState) {
                this.checkCondition(((BlockStartState)state).endState != null);
            }
            if (state instanceof BlockEndState) {
                this.checkCondition(((BlockEndState)state).startState != null);
            }
            if (state instanceof DecisionState) {
                DecisionState decisionState = (DecisionState)state;
                this.checkCondition(decisionState.getNumberOfTransitions() <= 1 || decisionState.decision >= 0);
                continue;
            }
            this.checkCondition(state.getNumberOfTransitions() <= 1 || state instanceof RuleStopState);
        }
    }

    protected void checkCondition(boolean condition) {
        this.checkCondition(condition, null);
    }

    protected void checkCondition(boolean condition, String message) {
        if (!condition) {
            throw new IllegalStateException(message);
        }
    }

    private static int inlineSetRules(ATN atn) {
        int inlinedCalls = 0;
        Transition[] ruleToInlineTransition = new Transition[atn.ruleToStartState.length];
        block9: for (int i = 0; i < atn.ruleToStartState.length; ++i) {
            RuleStartState startState;
            ATNState middleState = startState = atn.ruleToStartState[i];
            while (middleState.onlyHasEpsilonTransitions() && middleState.getNumberOfOptimizedTransitions() == 1 && middleState.getOptimizedTransition(0).getSerializationType() == 1) {
                middleState = middleState.getOptimizedTransition((int)0).target;
            }
            if (middleState.getNumberOfOptimizedTransitions() != 1) continue;
            Transition matchTransition = middleState.getOptimizedTransition(0);
            ATNState matchTarget = matchTransition.target;
            if (matchTransition.isEpsilon() || !matchTarget.onlyHasEpsilonTransitions() || matchTarget.getNumberOfOptimizedTransitions() != 1 || !(matchTarget.getOptimizedTransition((int)0).target instanceof RuleStopState)) continue;
            switch (matchTransition.getSerializationType()) {
                case 2: 
                case 5: 
                case 7: {
                    ruleToInlineTransition[i] = matchTransition;
                    continue block9;
                }
                case 8: 
                case 9: {
                    continue block9;
                }
                default: {
                    continue block9;
                }
            }
        }
        for (int stateNumber = 0; stateNumber < atn.states.size(); ++stateNumber) {
            ATNState state = atn.states.get(stateNumber);
            if (state.ruleIndex < 0) continue;
            ArrayList<Transition> optimizedTransitions = null;
            block12: for (int i = 0; i < state.getNumberOfOptimizedTransitions(); ++i) {
                Transition transition = state.getOptimizedTransition(i);
                if (!(transition instanceof RuleTransition)) {
                    if (optimizedTransitions == null) continue;
                    optimizedTransitions.add(transition);
                    continue;
                }
                RuleTransition ruleTransition = (RuleTransition)transition;
                Transition effective = ruleToInlineTransition[ruleTransition.target.ruleIndex];
                if (effective == null) {
                    if (optimizedTransitions == null) continue;
                    optimizedTransitions.add(transition);
                    continue;
                }
                if (optimizedTransitions == null) {
                    optimizedTransitions = new ArrayList<Transition>();
                    for (int j = 0; j < i; ++j) {
                        optimizedTransitions.add(state.getOptimizedTransition(i));
                    }
                }
                ++inlinedCalls;
                ATNState target = ruleTransition.followState;
                BasicState intermediateState = new BasicState();
                intermediateState.setRuleIndex(target.ruleIndex);
                atn.addState(intermediateState);
                optimizedTransitions.add(new EpsilonTransition(intermediateState));
                switch (effective.getSerializationType()) {
                    case 5: {
                        intermediateState.addTransition(new AtomTransition(target, ((AtomTransition)effective).label));
                        continue block12;
                    }
                    case 2: {
                        intermediateState.addTransition(new RangeTransition(target, ((RangeTransition)effective).from, ((RangeTransition)effective).to));
                        continue block12;
                    }
                    case 7: {
                        intermediateState.addTransition(new SetTransition(target, effective.label()));
                        continue block12;
                    }
                    default: {
                        throw new UnsupportedOperationException();
                    }
                }
            }
            if (optimizedTransitions == null) continue;
            if (state.isOptimized()) {
                while (state.getNumberOfOptimizedTransitions() > 0) {
                    state.removeOptimizedTransition(state.getNumberOfOptimizedTransitions() - 1);
                }
            }
            for (Transition transition : optimizedTransitions) {
                state.addOptimizedTransition(transition);
            }
        }
        return inlinedCalls;
    }

    private static int combineChainedEpsilons(ATN atn) {
        int removedEdges = 0;
        for (ATNState state : atn.states) {
            if (!state.onlyHasEpsilonTransitions() || state instanceof RuleStopState) continue;
            ArrayList<Transition> optimizedTransitions = null;
            block1: for (int i = 0; i < state.getNumberOfOptimizedTransitions(); ++i) {
                int j;
                Transition transition = state.getOptimizedTransition(i);
                ATNState intermediate = transition.target;
                if (transition.getSerializationType() != 1 || ((EpsilonTransition)transition).outermostPrecedenceReturn() != -1 || intermediate.getStateType() != 1 || !intermediate.onlyHasEpsilonTransitions()) {
                    if (optimizedTransitions == null) continue;
                    optimizedTransitions.add(transition);
                    continue;
                }
                for (j = 0; j < intermediate.getNumberOfOptimizedTransitions(); ++j) {
                    if (intermediate.getOptimizedTransition(j).getSerializationType() == 1 && ((EpsilonTransition)intermediate.getOptimizedTransition(j)).outermostPrecedenceReturn() == -1) continue;
                    if (optimizedTransitions == null) continue block1;
                    optimizedTransitions.add(transition);
                    continue block1;
                }
                ++removedEdges;
                if (optimizedTransitions == null) {
                    optimizedTransitions = new ArrayList<Transition>();
                    for (j = 0; j < i; ++j) {
                        optimizedTransitions.add(state.getOptimizedTransition(j));
                    }
                }
                for (j = 0; j < intermediate.getNumberOfOptimizedTransitions(); ++j) {
                    ATNState target = intermediate.getOptimizedTransition((int)j).target;
                    optimizedTransitions.add(new EpsilonTransition(target));
                }
            }
            if (optimizedTransitions == null) continue;
            if (state.isOptimized()) {
                while (state.getNumberOfOptimizedTransitions() > 0) {
                    state.removeOptimizedTransition(state.getNumberOfOptimizedTransitions() - 1);
                }
            }
            for (Transition transition : optimizedTransitions) {
                state.addOptimizedTransition(transition);
            }
        }
        return removedEdges;
    }

    private static int optimizeSets(ATN atn, boolean preserveOrder) {
        if (preserveOrder) {
            return 0;
        }
        int removedPaths = 0;
        List<DecisionState> decisions = atn.decisionToState;
        for (DecisionState decision : decisions) {
            Transition newTransition;
            IntervalSet setTransitions = new IntervalSet(new int[0]);
            for (int i = 0; i < decision.getNumberOfOptimizedTransitions(); ++i) {
                Transition epsTransition = decision.getOptimizedTransition(i);
                if (!(epsTransition instanceof EpsilonTransition) || epsTransition.target.getNumberOfOptimizedTransitions() != 1) continue;
                Transition transition = epsTransition.target.getOptimizedTransition(0);
                if (!(transition.target instanceof BlockEndState) || transition instanceof NotSetTransition || !(transition instanceof AtomTransition) && !(transition instanceof RangeTransition) && !(transition instanceof SetTransition)) continue;
                setTransitions.add(i);
            }
            if (setTransitions.size() <= 1) continue;
            ArrayList<Transition> optimizedTransitions = new ArrayList<Transition>();
            for (int i = 0; i < decision.getNumberOfOptimizedTransitions(); ++i) {
                if (setTransitions.contains(i)) continue;
                optimizedTransitions.add(decision.getOptimizedTransition(i));
            }
            ATNState blockEndState = decision.getOptimizedTransition((int)setTransitions.getMinElement()).target.getOptimizedTransition((int)0).target;
            IntervalSet matchSet = new IntervalSet(new int[0]);
            for (int i = 0; i < setTransitions.getIntervals().size(); ++i) {
                Interval interval = setTransitions.getIntervals().get(i);
                for (int j = interval.a; j <= interval.b; ++j) {
                    Transition matchTransition = decision.getOptimizedTransition((int)j).target.getOptimizedTransition(0);
                    if (matchTransition instanceof NotSetTransition) {
                        throw new UnsupportedOperationException("Not yet implemented.");
                    }
                    matchSet.addAll(matchTransition.label());
                }
            }
            if (matchSet.getIntervals().size() == 1) {
                if (matchSet.size() == 1) {
                    newTransition = new AtomTransition(blockEndState, matchSet.getMinElement());
                } else {
                    Interval matchInterval = matchSet.getIntervals().get(0);
                    newTransition = new RangeTransition(blockEndState, matchInterval.a, matchInterval.b);
                }
            } else {
                newTransition = new SetTransition(blockEndState, matchSet);
            }
            BasicState setOptimizedState = new BasicState();
            setOptimizedState.setRuleIndex(decision.ruleIndex);
            atn.addState(setOptimizedState);
            setOptimizedState.addTransition(newTransition);
            optimizedTransitions.add(new EpsilonTransition(setOptimizedState));
            removedPaths += decision.getNumberOfOptimizedTransitions() - optimizedTransitions.size();
            if (decision.isOptimized()) {
                while (decision.getNumberOfOptimizedTransitions() > 0) {
                    decision.removeOptimizedTransition(decision.getNumberOfOptimizedTransitions() - 1);
                }
            }
            for (Transition transition : optimizedTransitions) {
                decision.addOptimizedTransition(transition);
            }
        }
        return removedPaths;
    }

    private static void identifyTailCalls(ATN atn) {
        for (ATNState state : atn.states) {
            RuleTransition ruleTransition;
            for (Transition transition : state.transitions) {
                if (!(transition instanceof RuleTransition)) continue;
                ruleTransition = (RuleTransition)transition;
                ruleTransition.tailCall = ATNDeserializer.testTailCall(atn, ruleTransition, false);
                ruleTransition.optimizedTailCall = ATNDeserializer.testTailCall(atn, ruleTransition, true);
            }
            if (!state.isOptimized()) continue;
            for (Transition transition : state.optimizedTransitions) {
                if (!(transition instanceof RuleTransition)) continue;
                ruleTransition = (RuleTransition)transition;
                ruleTransition.tailCall = ATNDeserializer.testTailCall(atn, ruleTransition, false);
                ruleTransition.optimizedTailCall = ATNDeserializer.testTailCall(atn, ruleTransition, true);
            }
        }
    }

    private static boolean testTailCall(ATN atn, RuleTransition transition, boolean optimizedPath) {
        if (!optimizedPath && transition.tailCall) {
            return true;
        }
        if (optimizedPath && transition.optimizedTailCall) {
            return true;
        }
        BitSet reachable = new BitSet(atn.states.size());
        ArrayDeque<ATNState> worklist = new ArrayDeque<ATNState>();
        worklist.add(transition.followState);
        while (!worklist.isEmpty()) {
            ATNState state = (ATNState)worklist.pop();
            if (reachable.get(state.stateNumber) || state instanceof RuleStopState) continue;
            if (!state.onlyHasEpsilonTransitions()) {
                return false;
            }
            List<Transition> transitions = optimizedPath ? state.optimizedTransitions : state.transitions;
            for (Transition t : transitions) {
                if (t.getSerializationType() != 1) {
                    return false;
                }
                worklist.add(t.target);
            }
        }
        return true;
    }

    protected static int toInt(char c) {
        return c;
    }

    protected static int toInt32(char[] data, int offset) {
        return data[offset] | data[offset + 1] << 16;
    }

    protected static long toLong(char[] data, int offset) {
        long lowOrder = (long)ATNDeserializer.toInt32(data, offset) & 0xFFFFFFFFL;
        return lowOrder | (long)ATNDeserializer.toInt32(data, offset + 2) << 32;
    }

    protected static UUID toUUID(char[] data, int offset) {
        long leastSigBits = ATNDeserializer.toLong(data, offset);
        long mostSigBits = ATNDeserializer.toLong(data, offset + 4);
        return new UUID(mostSigBits, leastSigBits);
    }

    @NotNull
    protected Transition edgeFactory(@NotNull ATN atn, int type, int src, int trg, int arg1, int arg2, int arg3, List<IntervalSet> sets) {
        ATNState target = atn.states.get(trg);
        switch (type) {
            case 1: {
                return new EpsilonTransition(target);
            }
            case 2: {
                if (arg3 != 0) {
                    return new RangeTransition(target, -1, arg2);
                }
                return new RangeTransition(target, arg1, arg2);
            }
            case 3: {
                RuleTransition rt = new RuleTransition((RuleStartState)atn.states.get(arg1), arg2, arg3, target);
                return rt;
            }
            case 4: {
                PredicateTransition pt = new PredicateTransition(target, arg1, arg2, arg3 != 0);
                return pt;
            }
            case 10: {
                return new PrecedencePredicateTransition(target, arg1);
            }
            case 5: {
                if (arg3 != 0) {
                    return new AtomTransition(target, -1);
                }
                return new AtomTransition(target, arg1);
            }
            case 6: {
                ActionTransition a = new ActionTransition(target, arg1, arg2, arg3 != 0);
                return a;
            }
            case 7: {
                return new SetTransition(target, sets.get(arg1));
            }
            case 8: {
                return new NotSetTransition(target, sets.get(arg1));
            }
            case 9: {
                return new WildcardTransition(target);
            }
        }
        throw new IllegalArgumentException("The specified transition type is not valid.");
    }

    protected ATNState stateFactory(int type, int ruleIndex) {
        ATNState s;
        switch (type) {
            case 0: {
                return null;
            }
            case 1: {
                s = new BasicState();
                break;
            }
            case 2: {
                s = new RuleStartState();
                break;
            }
            case 3: {
                s = new BasicBlockStartState();
                break;
            }
            case 4: {
                s = new PlusBlockStartState();
                break;
            }
            case 5: {
                s = new StarBlockStartState();
                break;
            }
            case 6: {
                s = new TokensStartState();
                break;
            }
            case 7: {
                s = new RuleStopState();
                break;
            }
            case 8: {
                s = new BlockEndState();
                break;
            }
            case 9: {
                s = new StarLoopbackState();
                break;
            }
            case 10: {
                s = new StarLoopEntryState();
                break;
            }
            case 11: {
                s = new PlusLoopbackState();
                break;
            }
            case 12: {
                s = new LoopEndState();
                break;
            }
            default: {
                String message = String.format(Locale.getDefault(), "The specified state type %d is not valid.", type);
                throw new IllegalArgumentException(message);
            }
        }
        s.ruleIndex = ruleIndex;
        return s;
    }

    protected LexerAction lexerActionFactory(LexerActionType type, int data1, int data2) {
        switch (type) {
            case CHANNEL: {
                return new LexerChannelAction(data1);
            }
            case CUSTOM: {
                return new LexerCustomAction(data1, data2);
            }
            case MODE: {
                return new LexerModeAction(data1);
            }
            case MORE: {
                return LexerMoreAction.INSTANCE;
            }
            case POP_MODE: {
                return LexerPopModeAction.INSTANCE;
            }
            case PUSH_MODE: {
                return new LexerPushModeAction(data1);
            }
            case SKIP: {
                return LexerSkipAction.INSTANCE;
            }
            case TYPE: {
                return new LexerTypeAction(data1);
            }
        }
        String message = String.format(Locale.getDefault(), "The specified lexer action type %d is not valid.", new Object[]{type});
        throw new IllegalArgumentException(message);
    }

    static {
        SUPPORTED_UUIDS.add(BASE_SERIALIZED_UUID);
        SUPPORTED_UUIDS.add(ADDED_LEXER_ACTIONS);
        SUPPORTED_UUIDS.add(ADDED_UNICODE_SMP);
        SERIALIZED_UUID = ADDED_UNICODE_SMP;
    }

    static enum UnicodeDeserializingMode {
        UNICODE_BMP,
        UNICODE_SMP;

    }

    static interface UnicodeDeserializer {
        public int readUnicode(char[] var1, int var2);

        public int size();
    }
}

