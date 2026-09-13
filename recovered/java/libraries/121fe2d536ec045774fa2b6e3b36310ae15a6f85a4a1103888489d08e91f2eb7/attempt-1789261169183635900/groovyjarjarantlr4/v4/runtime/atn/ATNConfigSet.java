/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.atn;

import groovyjarjarantlr4.v4.runtime.atn.ATNConfig;
import groovyjarjarantlr4.v4.runtime.atn.ATNSimulator;
import groovyjarjarantlr4.v4.runtime.atn.ATNState;
import groovyjarjarantlr4.v4.runtime.atn.ConflictInfo;
import groovyjarjarantlr4.v4.runtime.atn.PredictionContext;
import groovyjarjarantlr4.v4.runtime.atn.PredictionContextCache;
import groovyjarjarantlr4.v4.runtime.atn.SemanticContext;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import groovyjarjarantlr4.v4.runtime.misc.Utils;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

public class ATNConfigSet
implements Set<ATNConfig> {
    private final HashMap<Long, ATNConfig> mergedConfigs;
    private final ArrayList<ATNConfig> unmerged;
    private final ArrayList<ATNConfig> configs;
    private int uniqueAlt;
    private ConflictInfo conflictInfo;
    private boolean hasSemanticContext;
    private boolean dipsIntoOuterContext;
    private boolean outermostConfigSet;
    private int cachedHashCode = -1;

    public ATNConfigSet() {
        this.mergedConfigs = new HashMap();
        this.unmerged = new ArrayList();
        this.configs = new ArrayList();
        this.uniqueAlt = 0;
    }

    protected ATNConfigSet(ATNConfigSet set, boolean readonly) {
        if (readonly) {
            this.mergedConfigs = null;
            this.unmerged = null;
        } else if (!set.isReadOnly()) {
            this.mergedConfigs = (HashMap)set.mergedConfigs.clone();
            this.unmerged = (ArrayList)set.unmerged.clone();
        } else {
            this.mergedConfigs = new HashMap(set.configs.size());
            this.unmerged = new ArrayList();
        }
        this.configs = (ArrayList)set.configs.clone();
        this.dipsIntoOuterContext = set.dipsIntoOuterContext;
        this.hasSemanticContext = set.hasSemanticContext;
        this.outermostConfigSet = set.outermostConfigSet;
        if (readonly || !set.isReadOnly()) {
            this.uniqueAlt = set.uniqueAlt;
            this.conflictInfo = set.conflictInfo;
        }
    }

    @NotNull
    public BitSet getRepresentedAlternatives() {
        if (this.conflictInfo != null) {
            return (BitSet)this.conflictInfo.getConflictedAlts().clone();
        }
        BitSet alts = new BitSet();
        for (ATNConfig config : this) {
            alts.set(config.getAlt());
        }
        return alts;
    }

    public final boolean isReadOnly() {
        return this.mergedConfigs == null;
    }

    public boolean isOutermostConfigSet() {
        return this.outermostConfigSet;
    }

    public void setOutermostConfigSet(boolean outermostConfigSet) {
        if (this.outermostConfigSet && !outermostConfigSet) {
            throw new IllegalStateException();
        }
        assert (!outermostConfigSet || !this.dipsIntoOuterContext);
        this.outermostConfigSet = outermostConfigSet;
    }

    public Set<ATNState> getStates() {
        HashSet<ATNState> states = new HashSet<ATNState>();
        for (ATNConfig c : this.configs) {
            states.add(c.getState());
        }
        return states;
    }

    public void optimizeConfigs(ATNSimulator interpreter) {
        if (this.configs.isEmpty()) {
            return;
        }
        for (int i = 0; i < this.configs.size(); ++i) {
            ATNConfig config = this.configs.get(i);
            config.setContext(interpreter.atn.getCachedContext(config.getContext()));
        }
    }

    public ATNConfigSet clone(boolean readonly) {
        ATNConfigSet copy = new ATNConfigSet(this, readonly);
        if (!readonly && this.isReadOnly()) {
            copy.addAll((Collection<? extends ATNConfig>)this.configs);
        }
        return copy;
    }

    @Override
    public int size() {
        return this.configs.size();
    }

    @Override
    public boolean isEmpty() {
        return this.configs.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        if (!(o instanceof ATNConfig)) {
            return false;
        }
        ATNConfig config = (ATNConfig)o;
        long configKey = this.getKey(config);
        ATNConfig mergedConfig = this.mergedConfigs.get(configKey);
        if (mergedConfig != null && this.canMerge(config, configKey, mergedConfig)) {
            return mergedConfig.contains(config);
        }
        for (ATNConfig c : this.unmerged) {
            if (!c.contains(config)) continue;
            return true;
        }
        return false;
    }

    @Override
    public Iterator<ATNConfig> iterator() {
        return new ATNConfigSetIterator();
    }

    @Override
    public Object[] toArray() {
        return this.configs.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return this.configs.toArray(a);
    }

    @Override
    public boolean add(ATNConfig e) {
        return this.add(e, null);
    }

    public boolean add(ATNConfig e, @Nullable PredictionContextCache contextCache) {
        long key;
        ATNConfig mergedConfig;
        boolean addKey;
        this.ensureWritable();
        assert (!this.outermostConfigSet || !e.getReachesIntoOuterContext());
        if (contextCache == null) {
            contextCache = PredictionContextCache.UNCACHED;
        }
        boolean bl = addKey = (mergedConfig = this.mergedConfigs.get(key = this.getKey(e))) == null;
        if (mergedConfig != null && this.canMerge(e, key, mergedConfig)) {
            mergedConfig.setOuterContextDepth(Math.max(mergedConfig.getOuterContextDepth(), e.getOuterContextDepth()));
            if (e.isPrecedenceFilterSuppressed()) {
                mergedConfig.setPrecedenceFilterSuppressed(true);
            }
            PredictionContext joined = PredictionContext.join(mergedConfig.getContext(), e.getContext(), contextCache);
            this.updatePropertiesForMergedConfig(e);
            if (mergedConfig.getContext() == joined) {
                return false;
            }
            mergedConfig.setContext(joined);
            return true;
        }
        for (int i = 0; i < this.unmerged.size(); ++i) {
            ATNConfig unmergedConfig = this.unmerged.get(i);
            if (!this.canMerge(e, key, unmergedConfig)) continue;
            unmergedConfig.setOuterContextDepth(Math.max(unmergedConfig.getOuterContextDepth(), e.getOuterContextDepth()));
            if (e.isPrecedenceFilterSuppressed()) {
                unmergedConfig.setPrecedenceFilterSuppressed(true);
            }
            PredictionContext joined = PredictionContext.join(unmergedConfig.getContext(), e.getContext(), contextCache);
            this.updatePropertiesForMergedConfig(e);
            if (unmergedConfig.getContext() == joined) {
                return false;
            }
            unmergedConfig.setContext(joined);
            if (addKey) {
                this.mergedConfigs.put(key, unmergedConfig);
                this.unmerged.remove(i);
            }
            return true;
        }
        this.configs.add(e);
        if (addKey) {
            this.mergedConfigs.put(key, e);
        } else {
            this.unmerged.add(e);
        }
        this.updatePropertiesForAddedConfig(e);
        return true;
    }

    private void updatePropertiesForMergedConfig(ATNConfig config) {
        this.dipsIntoOuterContext |= config.getReachesIntoOuterContext();
        assert (!this.outermostConfigSet || !this.dipsIntoOuterContext);
    }

    private void updatePropertiesForAddedConfig(ATNConfig config) {
        if (this.configs.size() == 1) {
            this.uniqueAlt = config.getAlt();
        } else if (this.uniqueAlt != config.getAlt()) {
            this.uniqueAlt = 0;
        }
        this.hasSemanticContext |= !SemanticContext.NONE.equals(config.getSemanticContext());
        this.dipsIntoOuterContext |= config.getReachesIntoOuterContext();
        assert (!this.outermostConfigSet || !this.dipsIntoOuterContext);
    }

    protected boolean canMerge(ATNConfig left, long leftKey, ATNConfig right) {
        if (left.getState().stateNumber != right.getState().stateNumber) {
            return false;
        }
        if (leftKey != this.getKey(right)) {
            return false;
        }
        return left.getSemanticContext().equals(right.getSemanticContext());
    }

    protected long getKey(ATNConfig e) {
        long key = e.getState().stateNumber;
        key = key << 12 | (long)(e.getAlt() & 0xFFF);
        return key;
    }

    @Override
    public boolean remove(Object o) {
        this.ensureWritable();
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!(o instanceof ATNConfig)) {
                return false;
            }
            if (this.contains((ATNConfig)o)) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends ATNConfig> c) {
        return this.addAll(c, null);
    }

    public boolean addAll(Collection<? extends ATNConfig> c, PredictionContextCache contextCache) {
        this.ensureWritable();
        boolean changed = false;
        for (ATNConfig aTNConfig : c) {
            changed |= this.add(aTNConfig, contextCache);
        }
        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        this.ensureWritable();
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        this.ensureWritable();
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void clear() {
        this.ensureWritable();
        this.mergedConfigs.clear();
        this.unmerged.clear();
        this.configs.clear();
        this.dipsIntoOuterContext = false;
        this.hasSemanticContext = false;
        this.uniqueAlt = 0;
        this.conflictInfo = null;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ATNConfigSet)) {
            return false;
        }
        ATNConfigSet other = (ATNConfigSet)obj;
        return this.outermostConfigSet == other.outermostConfigSet && Utils.equals(this.conflictInfo, other.conflictInfo) && this.configs.equals(other.configs);
    }

    @Override
    public int hashCode() {
        if (this.isReadOnly() && this.cachedHashCode != -1) {
            return this.cachedHashCode;
        }
        int hashCode = 1;
        hashCode = 5 * hashCode ^ (this.outermostConfigSet ? 1 : 0);
        hashCode = 5 * hashCode ^ this.configs.hashCode();
        if (this.isReadOnly()) {
            this.cachedHashCode = hashCode;
        }
        return hashCode;
    }

    public String toString() {
        return this.toString(false);
    }

    public String toString(boolean showContext) {
        StringBuilder buf = new StringBuilder();
        ArrayList<ATNConfig> sortedConfigs = new ArrayList<ATNConfig>(this.configs);
        Collections.sort(sortedConfigs, new Comparator<ATNConfig>(){

            @Override
            public int compare(ATNConfig o1, ATNConfig o2) {
                if (o1.getAlt() != o2.getAlt()) {
                    return o1.getAlt() - o2.getAlt();
                }
                if (o1.getState().stateNumber != o2.getState().stateNumber) {
                    return o1.getState().stateNumber - o2.getState().stateNumber;
                }
                return o1.getSemanticContext().toString().compareTo(o2.getSemanticContext().toString());
            }
        });
        buf.append("[");
        for (int i = 0; i < sortedConfigs.size(); ++i) {
            if (i > 0) {
                buf.append(", ");
            }
            buf.append(((ATNConfig)sortedConfigs.get(i)).toString(null, true, showContext));
        }
        buf.append("]");
        if (this.hasSemanticContext) {
            buf.append(",hasSemanticContext=").append(this.hasSemanticContext);
        }
        if (this.uniqueAlt != 0) {
            buf.append(",uniqueAlt=").append(this.uniqueAlt);
        }
        if (this.conflictInfo != null) {
            buf.append(",conflictingAlts=").append(this.conflictInfo.getConflictedAlts());
            if (!this.conflictInfo.isExact()) {
                buf.append("*");
            }
        }
        if (this.dipsIntoOuterContext) {
            buf.append(",dipsIntoOuterContext");
        }
        return buf.toString();
    }

    public int getUniqueAlt() {
        return this.uniqueAlt;
    }

    public boolean hasSemanticContext() {
        return this.hasSemanticContext;
    }

    public void clearExplicitSemanticContext() {
        this.ensureWritable();
        this.hasSemanticContext = false;
    }

    public void markExplicitSemanticContext() {
        this.ensureWritable();
        this.hasSemanticContext = true;
    }

    public ConflictInfo getConflictInfo() {
        return this.conflictInfo;
    }

    public void setConflictInfo(ConflictInfo conflictInfo) {
        this.ensureWritable();
        this.conflictInfo = conflictInfo;
    }

    public BitSet getConflictingAlts() {
        if (this.conflictInfo == null) {
            return null;
        }
        return this.conflictInfo.getConflictedAlts();
    }

    public boolean isExactConflict() {
        if (this.conflictInfo == null) {
            return false;
        }
        return this.conflictInfo.isExact();
    }

    public boolean getDipsIntoOuterContext() {
        return this.dipsIntoOuterContext;
    }

    public ATNConfig get(int index) {
        return this.configs.get(index);
    }

    public void remove(int index) {
        this.ensureWritable();
        ATNConfig config = this.configs.get(index);
        this.configs.remove(config);
        long key = this.getKey(config);
        if (this.mergedConfigs.get(key) == config) {
            this.mergedConfigs.remove(key);
        } else {
            for (int i = 0; i < this.unmerged.size(); ++i) {
                if (this.unmerged.get(i) != config) continue;
                this.unmerged.remove(i);
                return;
            }
        }
    }

    protected final void ensureWritable() {
        if (this.isReadOnly()) {
            throw new IllegalStateException("This ATNConfigSet is read only.");
        }
    }

    private final class ATNConfigSetIterator
    implements Iterator<ATNConfig> {
        int index = -1;
        boolean removed = false;

        private ATNConfigSetIterator() {
        }

        @Override
        public boolean hasNext() {
            return this.index + 1 < ATNConfigSet.this.configs.size();
        }

        @Override
        public ATNConfig next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            ++this.index;
            this.removed = false;
            return (ATNConfig)ATNConfigSet.this.configs.get(this.index);
        }

        @Override
        public void remove() {
            if (this.removed || this.index < 0 || this.index >= ATNConfigSet.this.configs.size()) {
                throw new IllegalStateException();
            }
            ATNConfigSet.this.remove(this.index);
            this.removed = true;
        }
    }
}

