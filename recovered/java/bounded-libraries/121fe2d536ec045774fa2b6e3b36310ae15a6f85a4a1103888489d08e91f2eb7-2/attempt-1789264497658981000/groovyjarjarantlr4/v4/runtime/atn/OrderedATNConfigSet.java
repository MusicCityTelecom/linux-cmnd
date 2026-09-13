/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.atn;

import groovyjarjarantlr4.v4.runtime.atn.ATNConfig;
import groovyjarjarantlr4.v4.runtime.atn.ATNConfigSet;

public class OrderedATNConfigSet
extends ATNConfigSet {
    public OrderedATNConfigSet() {
    }

    public OrderedATNConfigSet(ATNConfigSet set, boolean readonly) {
        super(set, readonly);
    }

    @Override
    public ATNConfigSet clone(boolean readonly) {
        OrderedATNConfigSet copy = new OrderedATNConfigSet(this, readonly);
        if (!readonly && this.isReadOnly()) {
            copy.addAll(this);
        }
        return copy;
    }

    @Override
    protected long getKey(ATNConfig e) {
        return e.hashCode();
    }

    @Override
    protected boolean canMerge(ATNConfig left, long leftKey, ATNConfig right) {
        return left.equals(right);
    }
}

