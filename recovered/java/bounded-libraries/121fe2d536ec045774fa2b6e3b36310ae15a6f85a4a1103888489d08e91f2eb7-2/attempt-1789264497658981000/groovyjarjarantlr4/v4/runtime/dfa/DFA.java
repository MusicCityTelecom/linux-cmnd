/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.dfa;

import groovyjarjarantlr4.v4.runtime.Vocabulary;
import groovyjarjarantlr4.v4.runtime.VocabularyImpl;
import groovyjarjarantlr4.v4.runtime.atn.ATNConfigSet;
import groovyjarjarantlr4.v4.runtime.atn.ATNState;
import groovyjarjarantlr4.v4.runtime.atn.ATNType;
import groovyjarjarantlr4.v4.runtime.atn.StarLoopEntryState;
import groovyjarjarantlr4.v4.runtime.dfa.DFASerializer;
import groovyjarjarantlr4.v4.runtime.dfa.DFAState;
import groovyjarjarantlr4.v4.runtime.dfa.EmptyEdgeMap;
import groovyjarjarantlr4.v4.runtime.dfa.LexerDFASerializer;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class DFA {
    @NotNull
    public final ConcurrentMap<DFAState, DFAState> states = new ConcurrentHashMap<DFAState, DFAState>();
    @NotNull
    public final AtomicReference<DFAState> s0 = new AtomicReference();
    @NotNull
    public final AtomicReference<DFAState> s0full = new AtomicReference();
    public final int decision;
    @NotNull
    public final ATNState atnStartState;
    private final AtomicInteger nextStateNumber = new AtomicInteger();
    private final int minDfaEdge;
    private final int maxDfaEdge;
    @NotNull
    private static final EmptyEdgeMap<DFAState> EMPTY_PRECEDENCE_EDGES = new EmptyEdgeMap(0, 200);
    @NotNull
    private final EmptyEdgeMap<DFAState> emptyEdgeMap;
    @NotNull
    private final EmptyEdgeMap<DFAState> emptyContextEdgeMap;
    private final boolean precedenceDfa;

    public DFA(@NotNull ATNState atnStartState) {
        this(atnStartState, 0);
    }

    public DFA(@NotNull ATNState atnStartState, int decision) {
        this.atnStartState = atnStartState;
        this.decision = decision;
        if (this.atnStartState.atn.grammarType == ATNType.LEXER) {
            this.minDfaEdge = 0;
            this.maxDfaEdge = 0x10FFFF;
        } else {
            this.minDfaEdge = -1;
            this.maxDfaEdge = atnStartState.atn.maxTokenType;
        }
        this.emptyEdgeMap = new EmptyEdgeMap(this.minDfaEdge, this.maxDfaEdge);
        this.emptyContextEdgeMap = new EmptyEdgeMap(-1, atnStartState.atn.states.size() - 1);
        boolean isPrecedenceDfa = false;
        if (atnStartState instanceof StarLoopEntryState && ((StarLoopEntryState)atnStartState).precedenceRuleDecision) {
            isPrecedenceDfa = true;
            this.s0.set(new DFAState(EMPTY_PRECEDENCE_EDGES, this.getEmptyContextEdgeMap(), new ATNConfigSet()));
            this.s0full.set(new DFAState(EMPTY_PRECEDENCE_EDGES, this.getEmptyContextEdgeMap(), new ATNConfigSet()));
        }
        this.precedenceDfa = isPrecedenceDfa;
    }

    public final int getMinDfaEdge() {
        return this.minDfaEdge;
    }

    public final int getMaxDfaEdge() {
        return this.maxDfaEdge;
    }

    @NotNull
    public EmptyEdgeMap<DFAState> getEmptyEdgeMap() {
        return this.emptyEdgeMap;
    }

    @NotNull
    public EmptyEdgeMap<DFAState> getEmptyContextEdgeMap() {
        return this.emptyContextEdgeMap;
    }

    public final boolean isPrecedenceDfa() {
        return this.precedenceDfa;
    }

    public final DFAState getPrecedenceStartState(int precedence, boolean fullContext) {
        if (!this.isPrecedenceDfa()) {
            throw new IllegalStateException("Only precedence DFAs may contain a precedence start state.");
        }
        if (fullContext) {
            return this.s0full.get().getTarget(precedence);
        }
        return this.s0.get().getTarget(precedence);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void setPrecedenceStartState(int precedence, boolean fullContext, DFAState startState) {
        if (!this.isPrecedenceDfa()) {
            throw new IllegalStateException("Only precedence DFAs may contain a precedence start state.");
        }
        if (precedence < 0) {
            return;
        }
        if (fullContext) {
            AtomicReference<DFAState> atomicReference = this.s0full;
            synchronized (atomicReference) {
                this.s0full.get().setTarget(precedence, startState);
            }
        }
        AtomicReference<DFAState> atomicReference = this.s0;
        synchronized (atomicReference) {
            this.s0.get().setTarget(precedence, startState);
        }
    }

    @Deprecated
    public final void setPrecedenceDfa(boolean precedenceDfa) {
        if (precedenceDfa != this.isPrecedenceDfa()) {
            throw new UnsupportedOperationException("The precedenceDfa field cannot change after a DFA is constructed.");
        }
    }

    public boolean isEmpty() {
        if (this.isPrecedenceDfa()) {
            return this.s0.get().getEdgeMap().isEmpty() && this.s0full.get().getEdgeMap().isEmpty();
        }
        return this.s0.get() == null && this.s0full.get() == null;
    }

    public boolean isContextSensitive() {
        if (this.isPrecedenceDfa()) {
            return !this.s0full.get().getEdgeMap().isEmpty();
        }
        return this.s0full.get() != null;
    }

    public DFAState addState(DFAState state) {
        state.stateNumber = this.nextStateNumber.getAndIncrement();
        DFAState existing = this.states.putIfAbsent(state, state);
        if (existing != null) {
            return existing;
        }
        return state;
    }

    public String toString() {
        return this.toString(VocabularyImpl.EMPTY_VOCABULARY);
    }

    @Deprecated
    public String toString(@Nullable String[] tokenNames) {
        if (this.s0.get() == null) {
            return "";
        }
        DFASerializer serializer = new DFASerializer(this, tokenNames);
        return serializer.toString();
    }

    public String toString(@NotNull Vocabulary vocabulary) {
        if (this.s0.get() == null) {
            return "";
        }
        DFASerializer serializer = new DFASerializer(this, vocabulary);
        return serializer.toString();
    }

    @Deprecated
    public String toString(@Nullable String[] tokenNames, @Nullable String[] ruleNames) {
        if (this.s0.get() == null) {
            return "";
        }
        DFASerializer serializer = new DFASerializer(this, tokenNames, ruleNames, this.atnStartState.atn);
        return serializer.toString();
    }

    public String toString(@NotNull Vocabulary vocabulary, @Nullable String[] ruleNames) {
        if (this.s0.get() == null) {
            return "";
        }
        DFASerializer serializer = new DFASerializer(this, vocabulary, ruleNames, this.atnStartState.atn);
        return serializer.toString();
    }

    public String toLexerString() {
        if (this.s0.get() == null) {
            return "";
        }
        LexerDFASerializer serializer = new LexerDFASerializer(this);
        return serializer.toString();
    }
}

