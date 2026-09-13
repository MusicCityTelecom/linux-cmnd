/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.atn;

import groovyjarjarantlr4.v4.runtime.atn.PredictionContext;
import groovyjarjarantlr4.v4.runtime.atn.PredictionContextCache;
import groovyjarjarantlr4.v4.runtime.atn.SingletonPredictionContext;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ArrayPredictionContext
extends PredictionContext {
    @NotNull
    public final PredictionContext[] parents;
    @NotNull
    public final int[] returnStates;

    ArrayPredictionContext(@NotNull PredictionContext[] parents, int[] returnStates) {
        super(ArrayPredictionContext.calculateHashCode(parents, returnStates));
        assert (parents.length == returnStates.length);
        assert (returnStates.length > 1 || returnStates[0] != Integer.MAX_VALUE) : "Should be using PredictionContext.EMPTY instead.";
        this.parents = parents;
        this.returnStates = returnStates;
    }

    ArrayPredictionContext(@NotNull PredictionContext[] parents, int[] returnStates, int hashCode) {
        super(hashCode);
        assert (parents.length == returnStates.length);
        assert (returnStates.length > 1 || returnStates[0] != Integer.MAX_VALUE) : "Should be using PredictionContext.EMPTY instead.";
        this.parents = parents;
        this.returnStates = returnStates;
    }

    @Override
    public PredictionContext getParent(int index) {
        return this.parents[index];
    }

    @Override
    public int getReturnState(int index) {
        return this.returnStates[index];
    }

    @Override
    public int findReturnState(int returnState) {
        return Arrays.binarySearch(this.returnStates, returnState);
    }

    @Override
    public int size() {
        return this.returnStates.length;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean hasEmpty() {
        return this.returnStates[this.returnStates.length - 1] == Integer.MAX_VALUE;
    }

    @Override
    protected PredictionContext addEmptyContext() {
        if (this.hasEmpty()) {
            return this;
        }
        PredictionContext[] parents2 = Arrays.copyOf(this.parents, this.parents.length + 1);
        int[] returnStates2 = Arrays.copyOf(this.returnStates, this.returnStates.length + 1);
        parents2[parents2.length - 1] = PredictionContext.EMPTY_FULL;
        returnStates2[returnStates2.length - 1] = Integer.MAX_VALUE;
        return new ArrayPredictionContext(parents2, returnStates2);
    }

    @Override
    protected PredictionContext removeEmptyContext() {
        if (!this.hasEmpty()) {
            return this;
        }
        if (this.returnStates.length == 2) {
            return new SingletonPredictionContext(this.parents[0], this.returnStates[0]);
        }
        PredictionContext[] parents2 = Arrays.copyOf(this.parents, this.parents.length - 1);
        int[] returnStates2 = Arrays.copyOf(this.returnStates, this.returnStates.length - 1);
        return new ArrayPredictionContext(parents2, returnStates2);
    }

    @Override
    public PredictionContext appendContext(PredictionContext suffix, PredictionContextCache contextCache) {
        return ArrayPredictionContext.appendContext(this, suffix, new PredictionContext.IdentityHashMap());
    }

    private static PredictionContext appendContext(PredictionContext context, PredictionContext suffix, PredictionContext.IdentityHashMap visited) {
        if (suffix.isEmpty()) {
            if (ArrayPredictionContext.isEmptyLocal(suffix)) {
                if (context.hasEmpty()) {
                    return EMPTY_LOCAL;
                }
                throw new UnsupportedOperationException("what to do here?");
            }
            return context;
        }
        if (suffix.size() != 1) {
            throw new UnsupportedOperationException("Appending a tree suffix is not yet supported.");
        }
        PredictionContext result = (PredictionContext)visited.get(context);
        if (result == null) {
            if (context.isEmpty()) {
                result = suffix;
            } else {
                int i;
                int parentCount = context.size();
                if (context.hasEmpty()) {
                    --parentCount;
                }
                PredictionContext[] updatedParents = new PredictionContext[parentCount];
                int[] updatedReturnStates = new int[parentCount];
                for (i = 0; i < parentCount; ++i) {
                    updatedReturnStates[i] = context.getReturnState(i);
                }
                for (i = 0; i < parentCount; ++i) {
                    updatedParents[i] = ArrayPredictionContext.appendContext(context.getParent(i), suffix, visited);
                }
                if (updatedParents.length == 1) {
                    result = new SingletonPredictionContext(updatedParents[0], updatedReturnStates[0]);
                } else {
                    assert (updatedParents.length > 1);
                    result = new ArrayPredictionContext(updatedParents, updatedReturnStates);
                }
                if (context.hasEmpty()) {
                    result = PredictionContext.join(result, suffix);
                }
            }
            visited.put(context, result);
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArrayPredictionContext)) {
            return false;
        }
        if (this.hashCode() != o.hashCode()) {
            return false;
        }
        ArrayPredictionContext other = (ArrayPredictionContext)o;
        return this.equals(other, new HashSet<PredictionContextCache.IdentityCommutativePredictionContextOperands>());
    }

    private boolean equals(ArrayPredictionContext other, Set<PredictionContextCache.IdentityCommutativePredictionContextOperands> visited) {
        ArrayDeque<PredictionContext> selfWorkList = new ArrayDeque<PredictionContext>();
        ArrayDeque<PredictionContext> otherWorkList = new ArrayDeque<PredictionContext>();
        selfWorkList.push(this);
        otherWorkList.push(other);
        while (!selfWorkList.isEmpty()) {
            PredictionContextCache.IdentityCommutativePredictionContextOperands operands = new PredictionContextCache.IdentityCommutativePredictionContextOperands((PredictionContext)selfWorkList.pop(), (PredictionContext)otherWorkList.pop());
            if (!visited.add(operands)) continue;
            int selfSize = operands.getX().size();
            if (selfSize == 0) {
                if (operands.getX().equals(operands.getY())) continue;
                return false;
            }
            int otherSize = operands.getY().size();
            if (selfSize != otherSize) {
                return false;
            }
            for (int i = 0; i < selfSize; ++i) {
                if (operands.getX().getReturnState(i) != operands.getY().getReturnState(i)) {
                    return false;
                }
                PredictionContext selfParent = operands.getX().getParent(i);
                PredictionContext otherParent = operands.getY().getParent(i);
                if (selfParent.hashCode() != otherParent.hashCode()) {
                    return false;
                }
                if (selfParent == otherParent) continue;
                selfWorkList.push(selfParent);
                otherWorkList.push(otherParent);
            }
        }
        return true;
    }
}

