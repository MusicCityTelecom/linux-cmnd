/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.dfa;

import groovyjarjarantlr4.v4.runtime.atn.ATN;
import groovyjarjarantlr4.v4.runtime.atn.ATNConfigSet;
import groovyjarjarantlr4.v4.runtime.atn.LexerActionExecutor;
import groovyjarjarantlr4.v4.runtime.atn.SemanticContext;
import groovyjarjarantlr4.v4.runtime.dfa.AbstractEdgeMap;
import groovyjarjarantlr4.v4.runtime.dfa.AcceptStateInfo;
import groovyjarjarantlr4.v4.runtime.dfa.DFA;
import groovyjarjarantlr4.v4.runtime.dfa.EmptyEdgeMap;
import groovyjarjarantlr4.v4.runtime.misc.MurmurHash;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class DFAState {
    public int stateNumber = -1;
    @NotNull
    public final ATNConfigSet configs;
    @NotNull
    private volatile AbstractEdgeMap<DFAState> edges;
    private AcceptStateInfo acceptStateInfo;
    @NotNull
    private volatile AbstractEdgeMap<DFAState> contextEdges;
    @Nullable
    private BitSet contextSymbols;
    @Nullable
    public PredPrediction[] predicates;

    public DFAState(@NotNull DFA dfa, @NotNull ATNConfigSet configs) {
        this(dfa.getEmptyEdgeMap(), dfa.getEmptyContextEdgeMap(), configs);
    }

    public DFAState(@NotNull EmptyEdgeMap<DFAState> emptyEdges, @NotNull EmptyEdgeMap<DFAState> emptyContextEdges, @NotNull ATNConfigSet configs) {
        this.configs = configs;
        this.edges = emptyEdges;
        this.contextEdges = emptyContextEdges;
    }

    public final boolean isContextSensitive() {
        return this.contextSymbols != null;
    }

    public final boolean isContextSymbol(int symbol) {
        if (!this.isContextSensitive() || symbol < this.edges.minIndex) {
            return false;
        }
        return this.contextSymbols.get(symbol - this.edges.minIndex);
    }

    public final void setContextSymbol(int symbol) {
        assert (this.isContextSensitive());
        if (symbol < this.edges.minIndex) {
            return;
        }
        this.contextSymbols.set(symbol - this.edges.minIndex);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setContextSensitive(ATN atn) {
        assert (!this.configs.isOutermostConfigSet());
        if (this.isContextSensitive()) {
            return;
        }
        DFAState dFAState = this;
        synchronized (dFAState) {
            if (this.contextSymbols == null) {
                this.contextSymbols = new BitSet();
            }
        }
    }

    public final AcceptStateInfo getAcceptStateInfo() {
        return this.acceptStateInfo;
    }

    public final void setAcceptState(AcceptStateInfo acceptStateInfo) {
        this.acceptStateInfo = acceptStateInfo;
    }

    public final boolean isAcceptState() {
        return this.acceptStateInfo != null;
    }

    public final int getPrediction() {
        if (this.acceptStateInfo == null) {
            return 0;
        }
        return this.acceptStateInfo.getPrediction();
    }

    public final LexerActionExecutor getLexerActionExecutor() {
        if (this.acceptStateInfo == null) {
            return null;
        }
        return this.acceptStateInfo.getLexerActionExecutor();
    }

    public DFAState getTarget(int symbol) {
        return (DFAState)this.edges.get(symbol);
    }

    public void setTarget(int symbol, DFAState target) {
        this.edges = this.edges.put(symbol, (Object)target);
    }

    public Map<Integer, DFAState> getEdgeMap() {
        return this.edges.toMap();
    }

    public synchronized DFAState getContextTarget(int invokingState) {
        if (invokingState == Integer.MAX_VALUE) {
            invokingState = -1;
        }
        return (DFAState)this.contextEdges.get(invokingState);
    }

    public synchronized void setContextTarget(int invokingState, DFAState target) {
        if (!this.isContextSensitive()) {
            throw new IllegalStateException("The state is not context sensitive.");
        }
        if (invokingState == Integer.MAX_VALUE) {
            invokingState = -1;
        }
        this.contextEdges = this.contextEdges.put(invokingState, (Object)target);
    }

    public Map<Integer, DFAState> getContextEdgeMap() {
        Map<Integer, DFAState> map = this.contextEdges.toMap();
        if (map.containsKey(-1)) {
            if (map.size() == 1) {
                return Collections.singletonMap(Integer.MAX_VALUE, map.get(-1));
            }
            try {
                map.put(Integer.MAX_VALUE, map.remove(-1));
            }
            catch (UnsupportedOperationException ex) {
                map = new LinkedHashMap<Integer, DFAState>(map);
                map.put(Integer.MAX_VALUE, map.remove(-1));
            }
        }
        return map;
    }

    public int hashCode() {
        int hash = MurmurHash.initialize(7);
        hash = MurmurHash.update(hash, this.configs.hashCode());
        hash = MurmurHash.finish(hash, 1);
        return hash;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DFAState)) {
            return false;
        }
        DFAState other = (DFAState)o;
        boolean sameSet = this.configs.equals(other.configs);
        return sameSet;
    }

    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(this.stateNumber).append(":").append(this.configs);
        if (this.isAcceptState()) {
            buf.append("=>");
            if (this.predicates != null) {
                buf.append(Arrays.toString(this.predicates));
            } else {
                buf.append(this.getPrediction());
            }
        }
        return buf.toString();
    }

    public static class PredPrediction {
        @NotNull
        public SemanticContext pred;
        public int alt;

        public PredPrediction(@NotNull SemanticContext pred, int alt) {
            this.alt = alt;
            this.pred = pred;
        }

        public String toString() {
            return "(" + this.pred + ", " + this.alt + ")";
        }
    }
}

