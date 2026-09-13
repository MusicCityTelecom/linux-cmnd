/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.atn;

import groovyjarjarantlr4.v4.runtime.Recognizer;
import groovyjarjarantlr4.v4.runtime.atn.ATNState;
import groovyjarjarantlr4.v4.runtime.atn.DecisionState;
import groovyjarjarantlr4.v4.runtime.atn.LexerActionExecutor;
import groovyjarjarantlr4.v4.runtime.atn.PredictionContext;
import groovyjarjarantlr4.v4.runtime.atn.PredictionContextCache;
import groovyjarjarantlr4.v4.runtime.atn.SemanticContext;
import groovyjarjarantlr4.v4.runtime.misc.MurmurHash;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import groovyjarjarantlr4.v4.runtime.misc.ObjectEqualityComparator;
import java.util.ArrayDeque;
import java.util.IdentityHashMap;

public class ATNConfig {
    private static final int SUPPRESS_PRECEDENCE_FILTER = Integer.MIN_VALUE;
    @NotNull
    private final ATNState state;
    private int altAndOuterContextDepth;
    @NotNull
    private PredictionContext context;

    protected ATNConfig(@NotNull ATNState state, int alt, @NotNull PredictionContext context) {
        assert ((alt & 0xFFFFFF) == alt);
        this.state = state;
        this.altAndOuterContextDepth = alt;
        this.context = context;
    }

    protected ATNConfig(@NotNull ATNConfig c, @NotNull ATNState state, @NotNull PredictionContext context) {
        this.state = state;
        this.altAndOuterContextDepth = c.altAndOuterContextDepth;
        this.context = context;
    }

    public static ATNConfig create(@NotNull ATNState state, int alt, @Nullable PredictionContext context) {
        return ATNConfig.create(state, alt, context, SemanticContext.NONE, null);
    }

    public static ATNConfig create(@NotNull ATNState state, int alt, @Nullable PredictionContext context, @NotNull SemanticContext semanticContext) {
        return ATNConfig.create(state, alt, context, semanticContext, null);
    }

    public static ATNConfig create(@NotNull ATNState state, int alt, @Nullable PredictionContext context, @NotNull SemanticContext semanticContext, LexerActionExecutor lexerActionExecutor) {
        if (semanticContext != SemanticContext.NONE) {
            if (lexerActionExecutor != null) {
                return new ActionSemanticContextATNConfig(lexerActionExecutor, semanticContext, state, alt, context, false);
            }
            return new SemanticContextATNConfig(semanticContext, state, alt, context);
        }
        if (lexerActionExecutor != null) {
            return new ActionATNConfig(lexerActionExecutor, state, alt, context, false);
        }
        return new ATNConfig(state, alt, context);
    }

    @NotNull
    public final ATNState getState() {
        return this.state;
    }

    public final int getAlt() {
        return this.altAndOuterContextDepth & 0xFFFFFF;
    }

    @NotNull
    public final PredictionContext getContext() {
        return this.context;
    }

    public void setContext(@NotNull PredictionContext context) {
        this.context = context;
    }

    public final boolean getReachesIntoOuterContext() {
        return this.getOuterContextDepth() != 0;
    }

    public final int getOuterContextDepth() {
        return this.altAndOuterContextDepth >>> 24 & 0x7F;
    }

    public void setOuterContextDepth(int outerContextDepth) {
        assert (outerContextDepth >= 0);
        outerContextDepth = Math.min(outerContextDepth, 127);
        this.altAndOuterContextDepth = outerContextDepth << 24 | this.altAndOuterContextDepth & 0x80FFFFFF;
    }

    @Nullable
    public LexerActionExecutor getLexerActionExecutor() {
        return null;
    }

    @NotNull
    public SemanticContext getSemanticContext() {
        return SemanticContext.NONE;
    }

    public boolean hasPassedThroughNonGreedyDecision() {
        return false;
    }

    public final ATNConfig clone() {
        return this.transform(this.getState(), false);
    }

    public final ATNConfig transform(@NotNull ATNState state, boolean checkNonGreedy) {
        return this.transform(state, this.context, this.getSemanticContext(), checkNonGreedy, this.getLexerActionExecutor());
    }

    public final ATNConfig transform(@NotNull ATNState state, @NotNull SemanticContext semanticContext, boolean checkNonGreedy) {
        return this.transform(state, this.context, semanticContext, checkNonGreedy, this.getLexerActionExecutor());
    }

    public final ATNConfig transform(@NotNull ATNState state, @Nullable PredictionContext context, boolean checkNonGreedy) {
        return this.transform(state, context, this.getSemanticContext(), checkNonGreedy, this.getLexerActionExecutor());
    }

    public final ATNConfig transform(@NotNull ATNState state, LexerActionExecutor lexerActionExecutor, boolean checkNonGreedy) {
        return this.transform(state, this.context, this.getSemanticContext(), checkNonGreedy, lexerActionExecutor);
    }

    private ATNConfig transform(@NotNull ATNState state, @Nullable PredictionContext context, @NotNull SemanticContext semanticContext, boolean checkNonGreedy, LexerActionExecutor lexerActionExecutor) {
        boolean passedThroughNonGreedy;
        boolean bl = passedThroughNonGreedy = checkNonGreedy && ATNConfig.checkNonGreedyDecision(this, state);
        if (semanticContext != SemanticContext.NONE) {
            if (lexerActionExecutor != null || passedThroughNonGreedy) {
                return new ActionSemanticContextATNConfig(lexerActionExecutor, semanticContext, this, state, context, passedThroughNonGreedy);
            }
            return new SemanticContextATNConfig(semanticContext, this, state, context);
        }
        if (lexerActionExecutor != null || passedThroughNonGreedy) {
            return new ActionATNConfig(lexerActionExecutor, this, state, context, passedThroughNonGreedy);
        }
        return new ATNConfig(this, state, context);
    }

    private static boolean checkNonGreedyDecision(ATNConfig source, ATNState target) {
        return source.hasPassedThroughNonGreedyDecision() || target instanceof DecisionState && ((DecisionState)target).nonGreedy;
    }

    public ATNConfig appendContext(int context, PredictionContextCache contextCache) {
        PredictionContext appendedContext = this.getContext().appendContext(context, contextCache);
        ATNConfig result = this.transform(this.getState(), appendedContext, false);
        return result;
    }

    public ATNConfig appendContext(PredictionContext context, PredictionContextCache contextCache) {
        PredictionContext appendedContext = this.getContext().appendContext(context, contextCache);
        ATNConfig result = this.transform(this.getState(), appendedContext, false);
        return result;
    }

    public boolean contains(ATNConfig subconfig) {
        if (this.getState().stateNumber != subconfig.getState().stateNumber || this.getAlt() != subconfig.getAlt() || !this.getSemanticContext().equals(subconfig.getSemanticContext())) {
            return false;
        }
        ArrayDeque<PredictionContext> leftWorkList = new ArrayDeque<PredictionContext>();
        ArrayDeque<PredictionContext> rightWorkList = new ArrayDeque<PredictionContext>();
        leftWorkList.add(this.getContext());
        rightWorkList.add(subconfig.getContext());
        while (!leftWorkList.isEmpty()) {
            PredictionContext right;
            PredictionContext left = (PredictionContext)leftWorkList.pop();
            if (left == (right = (PredictionContext)rightWorkList.pop())) {
                return true;
            }
            if (left.size() < right.size()) {
                return false;
            }
            if (right.isEmpty()) {
                return left.hasEmpty();
            }
            for (int i = 0; i < right.size(); ++i) {
                int index = left.findReturnState(right.getReturnState(i));
                if (index < 0) {
                    return false;
                }
                leftWorkList.push(left.getParent(index));
                rightWorkList.push(right.getParent(i));
            }
        }
        return false;
    }

    public final boolean isPrecedenceFilterSuppressed() {
        return (this.altAndOuterContextDepth & Integer.MIN_VALUE) != 0;
    }

    public final void setPrecedenceFilterSuppressed(boolean value) {
        this.altAndOuterContextDepth = value ? (this.altAndOuterContextDepth |= Integer.MIN_VALUE) : (this.altAndOuterContextDepth &= Integer.MAX_VALUE);
    }

    public boolean equals(Object o) {
        if (!(o instanceof ATNConfig)) {
            return false;
        }
        return this.equals((ATNConfig)o);
    }

    public boolean equals(ATNConfig other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        return this.getState().stateNumber == other.getState().stateNumber && this.getAlt() == other.getAlt() && this.getReachesIntoOuterContext() == other.getReachesIntoOuterContext() && this.getContext().equals(other.getContext()) && this.getSemanticContext().equals(other.getSemanticContext()) && this.isPrecedenceFilterSuppressed() == other.isPrecedenceFilterSuppressed() && this.hasPassedThroughNonGreedyDecision() == other.hasPassedThroughNonGreedyDecision() && ObjectEqualityComparator.INSTANCE.equals(this.getLexerActionExecutor(), other.getLexerActionExecutor());
    }

    public int hashCode() {
        int hashCode = MurmurHash.initialize(7);
        hashCode = MurmurHash.update(hashCode, this.getState().stateNumber);
        hashCode = MurmurHash.update(hashCode, this.getAlt());
        hashCode = MurmurHash.update(hashCode, this.getReachesIntoOuterContext() ? 1 : 0);
        hashCode = MurmurHash.update(hashCode, this.getContext());
        hashCode = MurmurHash.update(hashCode, this.getSemanticContext());
        hashCode = MurmurHash.update(hashCode, this.hasPassedThroughNonGreedyDecision() ? 1 : 0);
        hashCode = MurmurHash.update(hashCode, this.getLexerActionExecutor());
        hashCode = MurmurHash.finish(hashCode, 7);
        return hashCode;
    }

    public String toDotString() {
        StringBuilder builder = new StringBuilder();
        builder.append("digraph G {\n");
        builder.append("rankdir=LR;\n");
        IdentityHashMap<PredictionContext, PredictionContext> visited = new IdentityHashMap<PredictionContext, PredictionContext>();
        ArrayDeque<PredictionContext> workList = new ArrayDeque<PredictionContext>();
        workList.add(this.getContext());
        visited.put(this.getContext(), this.getContext());
        while (!workList.isEmpty()) {
            PredictionContext current = (PredictionContext)workList.pop();
            for (int i = 0; i < current.size(); ++i) {
                builder.append("  s").append(System.identityHashCode(current));
                builder.append("->");
                builder.append("s").append(System.identityHashCode(current.getParent(i)));
                builder.append("[label=\"").append(current.getReturnState(i)).append("\"];\n");
                if (visited.put(current.getParent(i), current.getParent(i)) != null) continue;
                workList.push(current.getParent(i));
            }
        }
        builder.append("}\n");
        return builder.toString();
    }

    public String toString() {
        return this.toString(null, true, false);
    }

    public String toString(@Nullable Recognizer<?, ?> recog, boolean showAlt) {
        return this.toString(recog, showAlt, true);
    }

    public String toString(@Nullable Recognizer<?, ?> recog, boolean showAlt, boolean showContext) {
        StringBuilder buf = new StringBuilder();
        String[] contexts = showContext ? this.getContext().toStrings(recog, this.getState().stateNumber) : new String[]{"?"};
        boolean first = true;
        for (String contextDesc : contexts) {
            if (first) {
                first = false;
            } else {
                buf.append(", ");
            }
            buf.append('(');
            buf.append(this.getState());
            if (showAlt) {
                buf.append(",");
                buf.append(this.getAlt());
            }
            if (this.getContext() != null) {
                buf.append(",");
                buf.append(contextDesc);
            }
            if (this.getSemanticContext() != null && this.getSemanticContext() != SemanticContext.NONE) {
                buf.append(",");
                buf.append(this.getSemanticContext());
            }
            if (this.getReachesIntoOuterContext()) {
                buf.append(",up=").append(this.getOuterContextDepth());
            }
            buf.append(')');
        }
        return buf.toString();
    }

    private static class ActionSemanticContextATNConfig
    extends SemanticContextATNConfig {
        private final LexerActionExecutor lexerActionExecutor;
        private final boolean passedThroughNonGreedyDecision;

        public ActionSemanticContextATNConfig(LexerActionExecutor lexerActionExecutor, @NotNull SemanticContext semanticContext, @NotNull ATNState state, int alt, @Nullable PredictionContext context, boolean passedThroughNonGreedyDecision) {
            super(semanticContext, state, alt, context);
            this.lexerActionExecutor = lexerActionExecutor;
            this.passedThroughNonGreedyDecision = passedThroughNonGreedyDecision;
        }

        public ActionSemanticContextATNConfig(LexerActionExecutor lexerActionExecutor, @NotNull SemanticContext semanticContext, @NotNull ATNConfig c, @NotNull ATNState state, @Nullable PredictionContext context, boolean passedThroughNonGreedyDecision) {
            super(semanticContext, c, state, context);
            this.lexerActionExecutor = lexerActionExecutor;
            this.passedThroughNonGreedyDecision = passedThroughNonGreedyDecision;
        }

        @Override
        public LexerActionExecutor getLexerActionExecutor() {
            return this.lexerActionExecutor;
        }

        @Override
        public boolean hasPassedThroughNonGreedyDecision() {
            return this.passedThroughNonGreedyDecision;
        }
    }

    private static class ActionATNConfig
    extends ATNConfig {
        private final LexerActionExecutor lexerActionExecutor;
        private final boolean passedThroughNonGreedyDecision;

        public ActionATNConfig(LexerActionExecutor lexerActionExecutor, @NotNull ATNState state, int alt, @Nullable PredictionContext context, boolean passedThroughNonGreedyDecision) {
            super(state, alt, context);
            this.lexerActionExecutor = lexerActionExecutor;
            this.passedThroughNonGreedyDecision = passedThroughNonGreedyDecision;
        }

        protected ActionATNConfig(LexerActionExecutor lexerActionExecutor, @NotNull ATNConfig c, @NotNull ATNState state, @Nullable PredictionContext context, boolean passedThroughNonGreedyDecision) {
            super(c, state, context);
            if (c.getSemanticContext() != SemanticContext.NONE) {
                throw new UnsupportedOperationException();
            }
            this.lexerActionExecutor = lexerActionExecutor;
            this.passedThroughNonGreedyDecision = passedThroughNonGreedyDecision;
        }

        @Override
        public LexerActionExecutor getLexerActionExecutor() {
            return this.lexerActionExecutor;
        }

        @Override
        public boolean hasPassedThroughNonGreedyDecision() {
            return this.passedThroughNonGreedyDecision;
        }
    }

    private static class SemanticContextATNConfig
    extends ATNConfig {
        @NotNull
        private final SemanticContext semanticContext;

        public SemanticContextATNConfig(SemanticContext semanticContext, @NotNull ATNState state, int alt, @Nullable PredictionContext context) {
            super(state, alt, context);
            this.semanticContext = semanticContext;
        }

        public SemanticContextATNConfig(SemanticContext semanticContext, @NotNull ATNConfig c, @NotNull ATNState state, @Nullable PredictionContext context) {
            super(c, state, context);
            this.semanticContext = semanticContext;
        }

        @Override
        public SemanticContext getSemanticContext() {
            return this.semanticContext;
        }
    }
}

