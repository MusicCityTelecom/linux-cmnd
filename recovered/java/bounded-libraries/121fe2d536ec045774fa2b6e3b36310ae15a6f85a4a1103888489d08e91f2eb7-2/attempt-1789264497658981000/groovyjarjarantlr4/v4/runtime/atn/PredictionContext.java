/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.atn;

import groovyjarjarantlr4.v4.runtime.Recognizer;
import groovyjarjarantlr4.v4.runtime.RuleContext;
import groovyjarjarantlr4.v4.runtime.atn.ATN;
import groovyjarjarantlr4.v4.runtime.atn.ATNState;
import groovyjarjarantlr4.v4.runtime.atn.ArrayPredictionContext;
import groovyjarjarantlr4.v4.runtime.atn.EmptyPredictionContext;
import groovyjarjarantlr4.v4.runtime.atn.PredictionContextCache;
import groovyjarjarantlr4.v4.runtime.atn.RuleTransition;
import groovyjarjarantlr4.v4.runtime.atn.SingletonPredictionContext;
import groovyjarjarantlr4.v4.runtime.misc.AbstractEqualityComparator;
import groovyjarjarantlr4.v4.runtime.misc.FlexibleHashMap;
import groovyjarjarantlr4.v4.runtime.misc.MurmurHash;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ConcurrentMap;

public abstract class PredictionContext {
    @NotNull
    public static final PredictionContext EMPTY_LOCAL = EmptyPredictionContext.LOCAL_CONTEXT;
    @NotNull
    public static final PredictionContext EMPTY_FULL = EmptyPredictionContext.FULL_CONTEXT;
    public static final int EMPTY_LOCAL_STATE_KEY = Integer.MIN_VALUE;
    public static final int EMPTY_FULL_STATE_KEY = Integer.MAX_VALUE;
    private static final int INITIAL_HASH = 1;
    private final int cachedHashCode;

    protected PredictionContext(int cachedHashCode) {
        this.cachedHashCode = cachedHashCode;
    }

    protected static int calculateEmptyHashCode() {
        int hash = MurmurHash.initialize(1);
        hash = MurmurHash.finish(hash, 0);
        return hash;
    }

    protected static int calculateHashCode(PredictionContext parent, int returnState) {
        int hash = MurmurHash.initialize(1);
        hash = MurmurHash.update(hash, parent);
        hash = MurmurHash.update(hash, returnState);
        hash = MurmurHash.finish(hash, 2);
        return hash;
    }

    protected static int calculateHashCode(PredictionContext[] parents, int[] returnStates) {
        int hash = MurmurHash.initialize(1);
        for (PredictionContext parent : parents) {
            hash = MurmurHash.update(hash, parent);
        }
        for (int returnState : returnStates) {
            hash = MurmurHash.update(hash, returnState);
        }
        hash = MurmurHash.finish(hash, 2 * parents.length);
        return hash;
    }

    public abstract int size();

    public abstract int getReturnState(int var1);

    public abstract int findReturnState(int var1);

    @NotNull
    public abstract PredictionContext getParent(int var1);

    protected abstract PredictionContext addEmptyContext();

    protected abstract PredictionContext removeEmptyContext();

    public static PredictionContext fromRuleContext(@NotNull ATN atn, @NotNull RuleContext outerContext) {
        return PredictionContext.fromRuleContext(atn, outerContext, true);
    }

    public static PredictionContext fromRuleContext(@NotNull ATN atn, @NotNull RuleContext outerContext, boolean fullContext) {
        if (outerContext.isEmpty()) {
            return fullContext ? EMPTY_FULL : EMPTY_LOCAL;
        }
        PredictionContext parent = outerContext.parent != null ? PredictionContext.fromRuleContext(atn, outerContext.parent, fullContext) : (fullContext ? EMPTY_FULL : EMPTY_LOCAL);
        ATNState state = atn.states.get(outerContext.invokingState);
        RuleTransition transition = (RuleTransition)state.transition(0);
        return parent.getChild(transition.followState.stateNumber);
    }

    private static PredictionContext addEmptyContext(PredictionContext context) {
        return context.addEmptyContext();
    }

    private static PredictionContext removeEmptyContext(PredictionContext context) {
        return context.removeEmptyContext();
    }

    public static PredictionContext join(PredictionContext context0, PredictionContext context1) {
        return PredictionContext.join(context0, context1, PredictionContextCache.UNCACHED);
    }

    static PredictionContext join(@NotNull PredictionContext context0, @NotNull PredictionContext context1, @NotNull PredictionContextCache contextCache) {
        if (context0 == context1) {
            return context0;
        }
        if (context0.isEmpty()) {
            return PredictionContext.isEmptyLocal(context0) ? context0 : PredictionContext.addEmptyContext(context1);
        }
        if (context1.isEmpty()) {
            return PredictionContext.isEmptyLocal(context1) ? context1 : PredictionContext.addEmptyContext(context0);
        }
        int context0size = context0.size();
        int context1size = context1.size();
        if (context0size == 1 && context1size == 1 && context0.getReturnState(0) == context1.getReturnState(0)) {
            PredictionContext merged = contextCache.join(context0.getParent(0), context1.getParent(0));
            if (merged == context0.getParent(0)) {
                return context0;
            }
            if (merged == context1.getParent(0)) {
                return context1;
            }
            return merged.getChild(context0.getReturnState(0));
        }
        int count = 0;
        PredictionContext[] parentsList = new PredictionContext[context0size + context1size];
        int[] returnStatesList = new int[parentsList.length];
        int leftIndex = 0;
        int rightIndex = 0;
        boolean canReturnLeft = true;
        boolean canReturnRight = true;
        while (leftIndex < context0size && rightIndex < context1size) {
            if (context0.getReturnState(leftIndex) == context1.getReturnState(rightIndex)) {
                parentsList[count] = contextCache.join(context0.getParent(leftIndex), context1.getParent(rightIndex));
                returnStatesList[count] = context0.getReturnState(leftIndex);
                canReturnLeft = canReturnLeft && parentsList[count] == context0.getParent(leftIndex);
                canReturnRight = canReturnRight && parentsList[count] == context1.getParent(rightIndex);
                ++leftIndex;
                ++rightIndex;
            } else if (context0.getReturnState(leftIndex) < context1.getReturnState(rightIndex)) {
                parentsList[count] = context0.getParent(leftIndex);
                returnStatesList[count] = context0.getReturnState(leftIndex);
                canReturnRight = false;
                ++leftIndex;
            } else {
                assert (context1.getReturnState(rightIndex) < context0.getReturnState(leftIndex));
                parentsList[count] = context1.getParent(rightIndex);
                returnStatesList[count] = context1.getReturnState(rightIndex);
                canReturnLeft = false;
                ++rightIndex;
            }
            ++count;
        }
        while (leftIndex < context0size) {
            parentsList[count] = context0.getParent(leftIndex);
            returnStatesList[count] = context0.getReturnState(leftIndex);
            ++leftIndex;
            canReturnRight = false;
            ++count;
        }
        while (rightIndex < context1size) {
            parentsList[count] = context1.getParent(rightIndex);
            returnStatesList[count] = context1.getReturnState(rightIndex);
            ++rightIndex;
            canReturnLeft = false;
            ++count;
        }
        if (canReturnLeft) {
            return context0;
        }
        if (canReturnRight) {
            return context1;
        }
        if (count < parentsList.length) {
            parentsList = Arrays.copyOf(parentsList, count);
            returnStatesList = Arrays.copyOf(returnStatesList, count);
        }
        if (parentsList.length == 0) {
            return EMPTY_FULL;
        }
        if (parentsList.length == 1) {
            return new SingletonPredictionContext(parentsList[0], returnStatesList[0]);
        }
        return new ArrayPredictionContext(parentsList, returnStatesList);
    }

    public static boolean isEmptyLocal(PredictionContext context) {
        return context == EMPTY_LOCAL;
    }

    public static PredictionContext getCachedContext(@NotNull PredictionContext context, @NotNull ConcurrentMap<PredictionContext, PredictionContext> contextCache, @NotNull IdentityHashMap visited) {
        PredictionContext updated;
        if (context.isEmpty()) {
            return context;
        }
        PredictionContext existing = (PredictionContext)visited.get(context);
        if (existing != null) {
            return existing;
        }
        existing = (PredictionContext)contextCache.get(context);
        if (existing != null) {
            visited.put(context, existing);
            return existing;
        }
        boolean changed = false;
        PredictionContext[] parents = new PredictionContext[context.size()];
        for (int i = 0; i < parents.length; ++i) {
            PredictionContext parent = PredictionContext.getCachedContext(context.getParent(i), contextCache, visited);
            if (!changed && parent == context.getParent(i)) continue;
            if (!changed) {
                parents = new PredictionContext[context.size()];
                for (int j = 0; j < context.size(); ++j) {
                    parents[j] = context.getParent(j);
                }
                changed = true;
            }
            parents[i] = parent;
        }
        if (!changed) {
            existing = contextCache.putIfAbsent(context, context);
            visited.put(context, existing != null ? existing : context);
            return context;
        }
        if (parents.length == 1) {
            updated = new SingletonPredictionContext(parents[0], context.getReturnState(0));
        } else {
            ArrayPredictionContext arrayPredictionContext = (ArrayPredictionContext)context;
            updated = new ArrayPredictionContext(parents, arrayPredictionContext.returnStates, context.cachedHashCode);
        }
        existing = contextCache.putIfAbsent(updated, updated);
        visited.put(updated, existing != null ? existing : updated);
        visited.put(context, existing != null ? existing : updated);
        return updated;
    }

    public PredictionContext appendContext(int returnContext, PredictionContextCache contextCache) {
        return this.appendContext(EMPTY_FULL.getChild(returnContext), contextCache);
    }

    public abstract PredictionContext appendContext(PredictionContext var1, PredictionContextCache var2);

    public PredictionContext getChild(int returnState) {
        return new SingletonPredictionContext(this, returnState);
    }

    public abstract boolean isEmpty();

    public abstract boolean hasEmpty();

    public final int hashCode() {
        return this.cachedHashCode;
    }

    public abstract boolean equals(Object var1);

    public String[] toStrings(Recognizer<?, ?> recognizer, int currentState) {
        return this.toStrings(recognizer, EMPTY_FULL, currentState);
    }

    public String[] toStrings(Recognizer<?, ?> recognizer, PredictionContext stop, int currentState) {
        ArrayList<String> result = new ArrayList<String>();
        int perm = 0;
        while (true) {
            block10: {
                int index;
                int offset = 0;
                boolean last = true;
                int stateNumber = currentState;
                StringBuilder localBuffer = new StringBuilder();
                localBuffer.append("[");
                for (PredictionContext p = this; !p.isEmpty() && p != stop; p = p.getParent(index)) {
                    index = 0;
                    if (p.size() > 0) {
                        int bits = 1;
                        while (1 << bits < p.size()) {
                            ++bits;
                        }
                        int mask = (1 << bits) - 1;
                        index = perm >> offset & mask;
                        last &= index >= p.size() - 1;
                        if (index >= p.size()) break block10;
                        offset += bits;
                    }
                    if (recognizer != null) {
                        if (localBuffer.length() > 1) {
                            localBuffer.append(' ');
                        }
                        ATN atn = recognizer.getATN();
                        ATNState s = atn.states.get(stateNumber);
                        String ruleName = recognizer.getRuleNames()[s.ruleIndex];
                        localBuffer.append(ruleName);
                    } else if (p.getReturnState(index) != Integer.MAX_VALUE && !p.isEmpty()) {
                        if (localBuffer.length() > 1) {
                            localBuffer.append(' ');
                        }
                        localBuffer.append(p.getReturnState(index));
                    }
                    stateNumber = p.getReturnState(index);
                }
                localBuffer.append("]");
                result.add(localBuffer.toString());
                if (last) break;
            }
            ++perm;
        }
        return result.toArray(new String[result.size()]);
    }

    public static final class IdentityEqualityComparator
    extends AbstractEqualityComparator<PredictionContext> {
        public static final IdentityEqualityComparator INSTANCE = new IdentityEqualityComparator();

        private IdentityEqualityComparator() {
        }

        @Override
        public int hashCode(PredictionContext obj) {
            return obj.hashCode();
        }

        @Override
        public boolean equals(PredictionContext a, PredictionContext b) {
            return a == b;
        }
    }

    public static final class IdentityHashMap
    extends FlexibleHashMap<PredictionContext, PredictionContext> {
        public IdentityHashMap() {
            super(IdentityEqualityComparator.INSTANCE);
        }
    }
}

