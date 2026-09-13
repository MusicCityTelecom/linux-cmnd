/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.atn;

import groovyjarjarantlr4.v4.runtime.atn.ATNConfig;
import groovyjarjarantlr4.v4.runtime.atn.ATNConfigSet;
import groovyjarjarantlr4.v4.runtime.atn.ATNState;
import groovyjarjarantlr4.v4.runtime.atn.RuleStopState;
import groovyjarjarantlr4.v4.runtime.atn.SemanticContext;
import groovyjarjarantlr4.v4.runtime.misc.AbstractEqualityComparator;
import groovyjarjarantlr4.v4.runtime.misc.FlexibleHashMap;
import groovyjarjarantlr4.v4.runtime.misc.MurmurHash;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import java.util.BitSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public enum PredictionMode {
    SLL,
    LL,
    LL_EXACT_AMBIG_DETECTION;


    public static boolean hasSLLConflictTerminatingPrediction(PredictionMode mode, @NotNull ATNConfigSet configs) {
        Collection<BitSet> altsets;
        if (PredictionMode.allConfigsInRuleStopStates(configs)) {
            return true;
        }
        if (mode == SLL && configs.hasSemanticContext()) {
            ATNConfigSet dup = new ATNConfigSet();
            for (ATNConfig c : configs) {
                c = c.transform(c.getState(), SemanticContext.NONE, false);
                dup.add(c);
            }
            configs = dup;
        }
        boolean heuristic = PredictionMode.hasConflictingAltSet(altsets = PredictionMode.getConflictingAltSubsets(configs)) && !PredictionMode.hasStateAssociatedWithOneAlt(configs);
        return heuristic;
    }

    public static boolean hasConfigInRuleStopState(ATNConfigSet configs) {
        for (ATNConfig c : configs) {
            if (!(c.getState() instanceof RuleStopState)) continue;
            return true;
        }
        return false;
    }

    public static boolean allConfigsInRuleStopStates(@NotNull ATNConfigSet configs) {
        for (ATNConfig config : configs) {
            if (config.getState() instanceof RuleStopState) continue;
            return false;
        }
        return true;
    }

    public static int resolvesToJustOneViableAlt(@NotNull Collection<BitSet> altsets) {
        return PredictionMode.getSingleViableAlt(altsets);
    }

    public static boolean allSubsetsConflict(@NotNull Collection<BitSet> altsets) {
        return !PredictionMode.hasNonConflictingAltSet(altsets);
    }

    public static boolean hasNonConflictingAltSet(@NotNull Collection<BitSet> altsets) {
        for (BitSet alts : altsets) {
            if (alts.cardinality() != 1) continue;
            return true;
        }
        return false;
    }

    public static boolean hasConflictingAltSet(@NotNull Collection<BitSet> altsets) {
        for (BitSet alts : altsets) {
            if (alts.cardinality() <= 1) continue;
            return true;
        }
        return false;
    }

    public static boolean allSubsetsEqual(@NotNull Collection<BitSet> altsets) {
        Iterator<BitSet> it = altsets.iterator();
        BitSet first = it.next();
        while (it.hasNext()) {
            BitSet next = it.next();
            if (next.equals(first)) continue;
            return false;
        }
        return true;
    }

    public static int getUniqueAlt(@NotNull Collection<BitSet> altsets) {
        BitSet all = PredictionMode.getAlts(altsets);
        if (all.cardinality() == 1) {
            return all.nextSetBit(0);
        }
        return 0;
    }

    public static BitSet getAlts(@NotNull Collection<BitSet> altsets) {
        BitSet all = new BitSet();
        for (BitSet alts : altsets) {
            all.or(alts);
        }
        return all;
    }

    @NotNull
    public static BitSet getAlts(@NotNull ATNConfigSet configs) {
        BitSet alts = new BitSet();
        for (ATNConfig config : configs) {
            alts.set(config.getAlt());
        }
        return alts;
    }

    @NotNull
    public static Collection<BitSet> getConflictingAltSubsets(@NotNull ATNConfigSet configs) {
        AltAndContextMap configToAlts = new AltAndContextMap();
        for (ATNConfig c : configs) {
            BitSet alts = (BitSet)configToAlts.get(c);
            if (alts == null) {
                alts = new BitSet();
                configToAlts.put(c, alts);
            }
            alts.set(c.getAlt());
        }
        return configToAlts.values();
    }

    @NotNull
    public static Map<ATNState, BitSet> getStateToAltMap(@NotNull ATNConfigSet configs) {
        HashMap<ATNState, BitSet> m = new HashMap<ATNState, BitSet>();
        for (ATNConfig c : configs) {
            BitSet alts = (BitSet)m.get(c.getState());
            if (alts == null) {
                alts = new BitSet();
                m.put(c.getState(), alts);
            }
            alts.set(c.getAlt());
        }
        return m;
    }

    public static boolean hasStateAssociatedWithOneAlt(@NotNull ATNConfigSet configs) {
        Map<ATNState, BitSet> x = PredictionMode.getStateToAltMap(configs);
        for (BitSet alts : x.values()) {
            if (alts.cardinality() != 1) continue;
            return true;
        }
        return false;
    }

    public static int getSingleViableAlt(@NotNull Collection<BitSet> altsets) {
        BitSet viableAlts = new BitSet();
        for (BitSet alts : altsets) {
            int minAlt = alts.nextSetBit(0);
            viableAlts.set(minAlt);
            if (viableAlts.cardinality() <= 1) continue;
            return 0;
        }
        return viableAlts.nextSetBit(0);
    }

    private static final class AltAndContextConfigEqualityComparator
    extends AbstractEqualityComparator<ATNConfig> {
        public static final AltAndContextConfigEqualityComparator INSTANCE = new AltAndContextConfigEqualityComparator();

        private AltAndContextConfigEqualityComparator() {
        }

        @Override
        public int hashCode(ATNConfig o) {
            int hashCode = MurmurHash.initialize(7);
            hashCode = MurmurHash.update(hashCode, o.getState().stateNumber);
            hashCode = MurmurHash.update(hashCode, o.getContext());
            hashCode = MurmurHash.finish(hashCode, 2);
            return hashCode;
        }

        @Override
        public boolean equals(ATNConfig a, ATNConfig b) {
            if (a == b) {
                return true;
            }
            if (a == null || b == null) {
                return false;
            }
            return a.getState().stateNumber == b.getState().stateNumber && a.getContext().equals(b.getContext());
        }
    }

    static class AltAndContextMap
    extends FlexibleHashMap<ATNConfig, BitSet> {
        public AltAndContextMap() {
            super(AltAndContextConfigEqualityComparator.INSTANCE);
        }
    }
}

