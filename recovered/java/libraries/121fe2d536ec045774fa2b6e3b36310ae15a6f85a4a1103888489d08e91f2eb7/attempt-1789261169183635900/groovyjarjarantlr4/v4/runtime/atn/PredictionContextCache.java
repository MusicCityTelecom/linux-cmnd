/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.atn;

import groovyjarjarantlr4.v4.runtime.atn.PredictionContext;
import java.util.HashMap;
import java.util.Map;

public class PredictionContextCache {
    public static final PredictionContextCache UNCACHED = new PredictionContextCache(false);
    private final Map<PredictionContext, PredictionContext> contexts = new HashMap<PredictionContext, PredictionContext>();
    private final Map<PredictionContextAndInt, PredictionContext> childContexts = new HashMap<PredictionContextAndInt, PredictionContext>();
    private final Map<IdentityCommutativePredictionContextOperands, PredictionContext> joinContexts = new HashMap<IdentityCommutativePredictionContextOperands, PredictionContext>();
    private final boolean enableCache;

    public PredictionContextCache() {
        this(true);
    }

    private PredictionContextCache(boolean enableCache) {
        this.enableCache = enableCache;
    }

    public PredictionContext getAsCached(PredictionContext context) {
        if (!this.enableCache) {
            return context;
        }
        PredictionContext result = this.contexts.get(context);
        if (result == null) {
            result = context;
            this.contexts.put(context, context);
        }
        return result;
    }

    public PredictionContext getChild(PredictionContext context, int invokingState) {
        if (!this.enableCache) {
            return context.getChild(invokingState);
        }
        PredictionContextAndInt operands = new PredictionContextAndInt(context, invokingState);
        PredictionContext result = this.childContexts.get(operands);
        if (result == null) {
            result = context.getChild(invokingState);
            result = this.getAsCached(result);
            this.childContexts.put(operands, result);
        }
        return result;
    }

    public PredictionContext join(PredictionContext x, PredictionContext y) {
        if (!this.enableCache) {
            return PredictionContext.join(x, y, this);
        }
        IdentityCommutativePredictionContextOperands operands = new IdentityCommutativePredictionContextOperands(x, y);
        PredictionContext result = this.joinContexts.get(operands);
        if (result != null) {
            return result;
        }
        result = PredictionContext.join(x, y, this);
        result = this.getAsCached(result);
        this.joinContexts.put(operands, result);
        return result;
    }

    protected static final class IdentityCommutativePredictionContextOperands {
        private final PredictionContext x;
        private final PredictionContext y;

        public IdentityCommutativePredictionContextOperands(PredictionContext x, PredictionContext y) {
            this.x = x;
            this.y = y;
        }

        public PredictionContext getX() {
            return this.x;
        }

        public PredictionContext getY() {
            return this.y;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof IdentityCommutativePredictionContextOperands)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            IdentityCommutativePredictionContextOperands other = (IdentityCommutativePredictionContextOperands)obj;
            return this.x == other.x && this.y == other.y || this.x == other.y && this.y == other.x;
        }

        public int hashCode() {
            return this.x.hashCode() ^ this.y.hashCode();
        }
    }

    protected static final class PredictionContextAndInt {
        private final PredictionContext obj;
        private final int value;

        public PredictionContextAndInt(PredictionContext obj, int value) {
            this.obj = obj;
            this.value = value;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof PredictionContextAndInt)) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            PredictionContextAndInt other = (PredictionContextAndInt)obj;
            return this.value == other.value && (this.obj == other.obj || this.obj != null && this.obj.equals(other.obj));
        }

        public int hashCode() {
            int hashCode = 5;
            hashCode = 7 * hashCode + (this.obj != null ? this.obj.hashCode() : 0);
            hashCode = 7 * hashCode + this.value;
            return hashCode;
        }
    }
}

